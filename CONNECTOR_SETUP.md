# Connector setup quick guide

The REST Connector is an outbound connector. A Camunda 8 runtime must have the REST Connector installed/enabled. The BPMN service tasks carry the connector task type `io.camunda:http-json:1` and input mappings for URL, method, headers, body and timeouts.

In Modeler, select each connector task and check the **Send REST Request / REST Connector** template. Set Authentication = None, Response format = As JSON, and Result variable = `creditApiResponse`, `inventoryResponse`, or `paymentResponse` respectively. If Modeler shows a template configuration panel, use it to verify the imported values. If it doesn't show a template, install the Camunda REST Connector element template supported by your Modeler/runtime version; don't change these tasks into Java workers.

The BPMN currently uses `http://localhost:8081`. When Connector runtime is in Docker and the Spring Boot API runs on the host, change the URL in the connector task to `http://host.docker.internal:8081/...` (or your reachable LAN hostname). For a cloud connector runtime, deploy the mock API to a reachable host; localhost will not work from cloud.

Official REST Connector documentation: https://docs.camunda.io/docs/components/connectors/protocol/rest/
