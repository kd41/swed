### Decisions:
- API call are synchronous. There is possible to make them asynchronous - REST API will return "message accepted", and server will process add/debit/exchange using Scheduler. 
- PATCH vs POST. Add/debit are using patch, exchange is using post. 
- API endpoints "get-all" are made for testing/visualization.
- Lombok is using.
- Integer is using as amount. 1 means one cent.


### Project flow:
1. generated project with IntelliJ
2. database model
3. entity model
4. spring.jpa.open-in-view
5. project structure
6. demo rest api
7. favicon.ico and banner.txt
8. added @ControllerAdvice, exceptions
9. @JsonDeserialize, @JsonSerialize, @JsonFormat
10. Transactions (internal package)
11. Committed to Github
12. exchange
13. external call simulation


### Working links:
* http://localhost:8080/h2-console
* http://localhost:8080/api/client/get-all
* http://localhost:8080/api/client-account/get-all
* http://localhost:8080/api/client-account/get/1/eur
* PATCH:
  * http://localhost:8080/api/client-account/add/1
  * Header: Content-Type:application/json
  * Body: {"amount":1,"currency":"usd"}
* PATCH:
  * http://localhost:8080/api/client-account/debit/1
  * Header: Content-Type:application/json
  * Body: {"amount":1,"currency":"usd"}
* POST:
  * http://localhost:8080/api/client-account/exchange/1
  * Header: Content-Type:application/json
  * Body: {"amount":100,"fromCurrency":"eur","toCurrency":"usd"}