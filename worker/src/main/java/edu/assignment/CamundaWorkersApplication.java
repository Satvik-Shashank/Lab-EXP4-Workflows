package edu.assignment;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@SpringBootApplication
public class CamundaWorkersApplication {
  public static void main(String[] args) { SpringApplication.run(CamundaWorkersApplication.class, args); }

  @Bean ZeebeClient zeebeClient() {
    String gateway = System.getenv().getOrDefault("ZEEBE_GATEWAY_ADDRESS", "localhost:26500");
    var builder = ZeebeClient.newClientBuilder().gatewayAddress(gateway);
    if (Boolean.parseBoolean(System.getenv().getOrDefault("ZEEBE_PLAINTEXT", "true"))) builder.usePlaintext();
    return builder.build();
  }

  @Bean List<JobWorker> workers(ZeebeClient client) {
    List<JobWorker> workers = new ArrayList<>();
    workers.add(client.newWorker().jobType("validate-loan").handler((c,j) -> {
      Map<String,Object> v = j.getVariablesAsMap();
      boolean valid = has(v,"applicantRef") && number(v,"monthlyIncome") > 0 && number(v,"requestedAmount") > 0;
      c.newCompleteCommand(j.getKey()).variables(Map.of("loanValidationPassed", valid,
        "validationMessage", valid ? "VALID" : "Applicant reference, monthly income and requested amount are required" )).send().join();
    }).open());
    workers.add(client.newWorker().jobType("validate-order").handler((c,j) -> {
      Map<String,Object> v = j.getVariablesAsMap();
      int quantity = (int) number(v,"quantity");
      boolean valid = has(v,"customerRef") && has(v,"productId") && quantity > 0 && number(v,"unitPrice") > 0;
      double total = valid ? quantity * number(v,"unitPrice") : 0;
      c.newCompleteCommand(j.getKey()).variables(Map.of("orderValid",valid,"orderTotal",total,
        "orderValidationMessage",valid ? "VALID" : "Customer, product, positive quantity and positive unitPrice are required")).send().join();
    }).open());
    workers.add(client.newWorker().jobType("mark-fulfilment").handler((c,j) -> {
      c.newCompleteCommand(j.getKey()).variables(Map.of("fulfilmentStatus","READY_FOR_FULFILMENT")).send().join();
    }).open());
    return workers;
  }
  static boolean has(Map<String,Object> m,String k) { return m.get(k)!=null && !m.get(k).toString().isBlank(); }
  static double number(Map<String,Object> m,String k) { try { return Double.parseDouble(String.valueOf(m.get(k))); } catch(Exception e) { return 0; } }
}

@RestController
class MockApiController {
  @PostMapping("/api/credit-assessment") Map<String,Object> credit(@RequestBody Map<String,Object> b) {
    if ("API_ERROR".equals(b.get("applicantRef"))) throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE,"Synthetic credit API outage; change applicantRef and retry");
    double income = Double.parseDouble(String.valueOf(b.getOrDefault("monthlyIncome",0)));
    double amount = Double.parseDouble(String.valueOf(b.getOrDefault("requestedAmount",0)));
    boolean clear = income >= 25000 && amount <= 500000;
    return Map.of("status",clear ? "CLEAR":"REFER","score",clear ? 740:620,"assessmentId","MOCK-CREDIT-001");
  }
  @PostMapping("/api/inventory/check") Map<String,Object> inventory(@RequestBody Map<String,Object> b) {
    if ("API_ERROR".equals(b.get("productId"))) throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE,"Synthetic inventory API outage; change productId and retry");
    int qty = Integer.parseInt(String.valueOf(b.getOrDefault("quantity",0)));
    boolean available = !"OUT_OF_STOCK".equals(b.get("productId")) && qty <= 10;
    return Map.of("available",available,"availableQuantity",available ? 10 : 0,"inventoryCheckId","MOCK-STOCK-001");
  }
  @PostMapping("/api/payment") Map<String,Object> payment(@RequestBody Map<String,Object> b) {
    String token = String.valueOf(b.getOrDefault("paymentToken",""));
    if ("API_ERROR".equals(token)) throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE,"Synthetic payment gateway outage; change paymentToken and retry");
    boolean success = !"TEST_DECLINED".equals(token);
    return Map.of("status",success ? "SUCCESS":"DECLINED","reference",success ? "MOCK-PAY-1001":"MOCK-DECLINED");
  }
}
