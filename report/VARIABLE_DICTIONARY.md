# Process variable dictionary

## Lab 1 — Loan Approval
| Variable | Type | Purpose |
|---|---|---|
| applicantRef | string | Synthetic applicant identifier |
| monthlyIncome | number | Monthly income in demo currency units |
| requestedAmount | number | Requested loan amount |
| loanValidationPassed | boolean | Result from Java validation worker |
| validationMessage | string | Validation explanation |
| creditApiResponse | context/object | Complete REST Connector response (status, headers, body) |
| creditStatus | string | DMN input: CLEAR or REFER; populated by the connector result expression |
| creditScore | number | Mock credit score returned by API |
| decisionResult | context/object | DMN result containing outcome |

## Lab 2 — E-Commerce
| Variable | Type | Purpose |
|---|---|---|
| customerRef | string | Synthetic customer identifier |
| productId | string | SKU; OUT_OF_STOCK and API_ERROR are test values |
| quantity | number | Units ordered |
| unitPrice | number | Synthetic unit price |
| orderValid | boolean | Java worker validation result |
| orderTotal | number | quantity × unitPrice |
| customerCategory | string | REGULAR or VIP |
| deliveryPreference | string | STANDARD or EXPRESS |
| paymentToken | string | TEST_APPROVED / TEST_DECLINED / API_ERROR only |
| inventoryResponse | context/object | REST Connector inventory response |
| paymentResponse | context/object | REST Connector payment response |
| decisionResult | context/object | DMN result containing priority |
| fulfilmentStatus | string | READY_FOR_FULFILMENT after worker completion |

Connector response body is available under `<resultVariable>.body` when Response format is JSON. If your connector template version uses a different output mapping UI, set its Result variable to the names above and align gateway expressions with the resulting response shape.
