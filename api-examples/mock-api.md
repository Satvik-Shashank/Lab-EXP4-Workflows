# Mock REST API

Spring Boot app exposes three synthetic endpoints on port 8081.

| Method + endpoint | Request | Success response | Failure simulation |
|---|---|---|---|
| POST `/api/credit-assessment` | applicantRef, monthlyIncome, requestedAmount | status CLEAR/REFER, score, assessmentId | applicantRef=`API_ERROR` returns HTTP 503 |
| POST `/api/inventory/check` | productId, quantity | available, availableQuantity, inventoryCheckId | productId=`API_ERROR` returns HTTP 503; `OUT_OF_STOCK` returns available=false |
| POST `/api/payment` | paymentToken, amount | status SUCCESS/DECLINED, reference | token=`API_ERROR` returns HTTP 503; `TEST_DECLINED` returns DECLINED |

All values are synthetic. No external payment provider is contacted.
