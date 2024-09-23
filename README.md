Decisions:
API call are synchronous. There is possible to make them asynchronous - REST API will return "message accepted", and server will process add/debit/exchange using Scheduler. 
PATCH vs POST. Add/debit are using patch, exchange is using post. 
API endpoints "get-all" are made for testing/visualization.
Lombock is using.
Integer is using as amount. 1 means one cent.


Project flow:
generated project with IntelliJ
database model
entity model
spring.jpa.open-in-view
project structure
demo rest api
favicon.ico and banner.txt
added @ControllerAdvice, exceptions
@JsonDeserialize, @JsonSerialize, @JsonFormat
Transactions (internal package)
Committed to Github
exchange
external call simulation


Working links:
http://localhost:8080/h2-console
http://localhost:8080/api/client/get-all
http://localhost:8080/api/client-account/get-all
http://localhost:8080/api/client-account/get/1/eur
PATCH:
http://localhost:8080/api/client-account/add/1
Content-Type:application/json
{"amount":1,"currency":"usd"}
PATCH:
http://localhost:8080/api/client-account/debit/1
Content-Type:application/json
{"amount":1,"currency":"usd"}
