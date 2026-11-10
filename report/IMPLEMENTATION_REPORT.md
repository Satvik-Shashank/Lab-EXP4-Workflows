# Implementation Report — Camunda 8 Labs 1 & 2

## Scope
Two assignment tasks are implemented: Lab 1 Loan Approval and Lab 2 E-Commerce Order Processing. Lab 3 is not part of this submission.

## Architecture
Camunda 8 BPMN orchestrates the process; DMN evaluates loan outcomes and order priority; Java 17/Spring Boot external workers perform data validation and calculation; Camunda REST Connector invokes synthetic REST endpoints exposed by the Spring Boot mock API; Tasklist user tasks provide human review; Operate is used to inspect process instances and incidents.

**Connector design:** The credit assessment, inventory check and payment HTTP calls are represented as Camunda REST Connector tasks (`io.camunda:http-json:1`). The Java workers do not make HTTP calls. This matches the demo instruction to use REST API Connector rather than a worker/service task that directly calls a URL.

## Lab 1 flow
Start → validate applicant worker → validation gateway → credit REST connector → loan DMN → exclusive gateway → approval / rejection / manual review Tasklist route. Educational rules: income below 25,000 is rejected; eligible income with amount at or below 500,000 and CLEAR credit is approved; REFER or unmatched cases go to manual review.

## Lab 2 flow
Start → validate order and calculate total worker → valid-order gateway → inventory REST connector → stock gateway → payment REST connector → payment gateway → priority DMN → high-value Tasklist review or fulfilment worker. Invalid orders, insufficient stock and declined payments go to exception review.

## Failure/recovery design
Use `API_ERROR` in the relevant synthetic identifier/token to make the mock API return HTTP 503. The REST Connector task is configured with retries. In Operate inspect the connector incident/error response. Correct the input to a valid synthetic value and retry the failed connector job where the runtime supports it. Capture before/after screenshots.

## Assumptions and limitations
- All data is synthetic and for education only.
- Mock API runs on port 8081; the connector runtime must be able to reach that host. In Docker, `localhost` refers to the connector container, so replace with a reachable hostname such as `host.docker.internal` where applicable.
- Camunda Modeler does not execute workflows. A running Camunda 8 cluster, deployed REST Connector runtime, and Tasklist/Operate are required for execution and screenshots.
- The source package is structured for local configuration. Cluster authentication and deployment are environment-specific and must be configured without committing secrets.
- Actual results and screenshots must be recorded by the student after running the project; none are fabricated in this archive.
