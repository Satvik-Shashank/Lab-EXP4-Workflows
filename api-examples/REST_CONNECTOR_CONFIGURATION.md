# REST Connector configuration

The BPMN REST tasks use the Camunda outbound REST Connector (`io.camunda:http-json:1`). They are connector tasks, not Java URL-calling service tasks.

## Lab 1 — credit assessment
- HTTP method: `POST`
- URL: `http://localhost:8081/api/credit-assessment`
- Authentication: None (synthetic local API)
- Content-Type: `application/json`
- Body FEEL: `= {"applicantRef": applicantRef, "monthlyIncome": monthlyIncome, "requestedAmount": requestedAmount}`
- Result variable: `creditApiResponse`
- Expected JSON body: `{ "status": "CLEAR", "score": 740, "assessmentId": "MOCK-CREDIT-001" }`

## Lab 2 — inventory
- HTTP method: `POST`
- URL: `http://localhost:8081/api/inventory/check`
- Body FEEL: `= {"productId": productId, "quantity": quantity}`
- Result variable: `inventoryResponse`
- Gateway condition: `= inventoryResponse.body.available = true`

## Lab 2 — payment
- HTTP method: `POST`
- URL: `http://localhost:8081/api/payment`
- Body FEEL: `= {"paymentToken": paymentToken, "amount": orderTotal}`
- Result variable: `paymentResponse`
- Gateway condition: `= paymentResponse.body.status = "SUCCESS"`

For each task, select authentication `None`, response format `As JSON`, and set connection/read timeout to 20 seconds. The BPMN stores the result in unique variables. Do not create process variables named `body`, `url`, `method`, `headers`, `authentication`, `queryParameters`, `connectionTimeoutInSeconds`, or `readTimeoutInSeconds` because these names are reserved by the connector.

## Sample cURL requests

```bash
curl -X POST http://localhost:8081/api/credit-assessment -H 'Content-Type: application/json' -d '{"applicantRef":"APP-1001","monthlyIncome":65000,"requestedAmount":300000}'
curl -X POST http://localhost:8081/api/inventory/check -H 'Content-Type: application/json' -d '{"productId":"SKU-01","quantity":2}'
curl -X POST http://localhost:8081/api/payment -H 'Content-Type: application/json' -d '{"paymentToken":"TEST_APPROVED","amount":2400}'
```
