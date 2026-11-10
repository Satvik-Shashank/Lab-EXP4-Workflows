# Camunda 8 Practical Assignment — Labs 1 & 2

This repository implements **two of the three labs** from the supplied PBJ-2 assignment PDF: (1) Loan Approval and (2) E-Commerce Order Processing. Lab 3 is intentionally not included.

## Important demo design decision: REST Connector

**All URL/API calls are performed by Camunda's REST Connector (`io.camunda:http-json:1`) in BPMN.** There are no Java worker tasks that call a URL. Java workers only validate inputs/calculate values and set process variables. The connector tasks call the local mock API:
- Loan credit assessment: `POST http://localhost:8081/api/credit-assessment`
- E-commerce inventory: `POST http://localhost:8081/api/inventory/check`
- E-commerce payment: `POST http://localhost:8081/api/payment`

The mock API is implemented in the same Spring Boot app as the workers, on port 8081.

## Folder map

- `models/`: editable BPMN and DMN files for both labs.
- `worker/`: Java 17 / Spring Boot worker and mock API.
- `tests/`: synthetic JSON inputs and scenario tables.
- `api-examples/`: REST request/response examples and connector settings.
- `report/`: implementation report, variable dictionary, test result sheet and evidence checklist.
- `submission/`: copy-ready submission steps.

## Open the models

1. Extract this ZIP.
2. Open Camunda 8 Desktop Modeler.
3. Open `models/lab1-loan/loanApproval.bpmn` and `loanDecision.dmn`.
4. Open `models/lab2-ecommerce/ecommerceFulfilment.bpmn` and `orderPriority.dmn`.
5. Install/select the **REST Connector / Send REST Request** element template if Modeler does not recognize connector properties. Connector task type is `io.camunda:http-json:1`.

## Run the mock API and workers

Requires Java 17+, Maven, and access to a Camunda 8 cluster with Zeebe gateway. In `worker/`:

```bash
mvn spring-boot:run
```

Default local settings are `ZEEBE_GATEWAY_ADDRESS=localhost:26500` and plaintext enabled. For Camunda SaaS or secured clusters, set the Zeebe client connection values and credentials as appropriate; never commit credentials. The mock API becomes available at `http://localhost:8081`.

## Runtime requirement

Camunda Modeler is a modeling application, not a workflow runtime. Deploy the BPMN and DMN to a Camunda 8 cluster with the REST Connector runtime enabled. For a local Self-Managed environment, configure the outbound REST connector in the Connectors runtime. For SaaS, ensure the cluster has the REST Connector available and the mock API host is reachable from that connector runtime. `localhost` in the BPMN means the connector container itself, not your host machine; use a reachable host name/IP (for Docker Desktop often `host.docker.internal`) when running connector runtime in Docker.

## Test and evidence

Use `tests/` and the steps in `report/EVIDENCE_CHECKLIST.md`. Capture your own Tasklist and Operate screenshots after execution. No fabricated screenshots or fake actual results are included. Fill in `report/TEST_RESULTS.md` with observed outcomes.

## Synthetic data only

All tokens and references are fake demo values. Never use real payment credentials, personal financial data, or real applicant information.
