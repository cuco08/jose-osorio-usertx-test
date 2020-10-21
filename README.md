Description
==============================
This repo contains an API that provides operations related to user transactions. The operations are implemented as REST 
services to add, read, calculate and get reports of the user transactions from an in-memory database. For more details
please have a look at the original [requirements](https://github.com/cesaralcancio/simple-test).


Dev Requirement
==============================
* java version 1.8.x
* maven version 3.x.x
* Unix based operating system or Cygwin on Windows (or something similar to be able to run bash scripts)

User Guide - Quick Start
==============================

Clone the repo and execute the following commands on a terminal.

```
$git clone git@github.com:cuco08/jose-osorio-usertx-test.git
$cd jose-osorio-usertx-test
$mvn clean install
$./start.sh
```

On startup the application loads initial sample-data into an in-memory database. In order to add more data to populate the DB
on startup you need to modify this [commandLiner](web/src/main/java/mx/clip/assessment/user/tx/UserTransactionsApplication.java).

The following HTTP operations are implemented:

```
* POST    /clip/v1/user/transaction
    > Adds a new user transaction given the user and transaction information. 
    Returns 200 and the transaction information along with a unique transaction identifier.
    Returns 400 if the request params are not valid.
    
    NOTE: All the request params are mandatory:
        - userId [string]
        - amount [numeric]
        - description [string]
        - date [string formatted as 'yyyy-mm-dd']
    
    > Sample request:
    $curl -X POST "http://localhost:8080/clip/v1/user/transaction" \
       -H 'Content-Type: application/json' \
       -d '{
             "userId":"01",
             "amount":"5.00",
             "description":"third attempt",
             "date":"2020-10-19"
           }'

* GET   /clip/v1/user/{userId}/transaction/{transactionId}
    > Finds a single transaction by userId and transactionId.
    Returns 200 and the user transaction information.
    Returns 404 if there is not any transaction with that identifier associated to a given userId.
    
    > Sample request:
    $curl -X GET "http://localhost:8080/clip/v1/user/01/transaction/e6d0fe8f-ba15-4933-8ca1-153dc5ff233b" \
       -H 'Content-Type: application/json'

* GET    /clip/v1/user/{userId}/transaction
    > Finds all the transactions for a given user. 
    Returns 200 and the list of user transactions. If the user does not exist or he/she does not have any transactions
    the response will be and empty list.

    > Sample request:
    $curl -X GET "http://localhost:8080/clip/v1/user/01/transaction" -H 'Content-Type: application/json' 
    
* GET  /clip/v1/user/{userId}/transaction/sum
    > Gets the total sum of all the transaction amounts associated to a given user.
    Returns 200 and the total sum along with the userId. If the user does not have any transactions then the total 
    amount will be 0.0.
    
    > Sample request:
    $curl -X GET "http://localhost:8080/clip/v1/user/01/transaction/sum" -H 'Content-Type: application/json'
    
* GET /clip/v1/user/{userId}/transaction/report
    > Gets a weekly report of all the transactions associated to a given user.
    Returns 200 and the report information. If the user does not have any transactions then the response will be an
    empty list.
    
    > Sample request:
    $curl -X GET "http://localhost:8080/clip/v1/user/01/transaction/report" -H 'Content-Type: application/json'
    
* GET /clip/v1/user/transaction/random
    > Gets one random transaction amongst all the existing transactions of all users.
    Returns 200 and the random user transaction information.
    Returns 404 if there are no transactions in the DB.
    
    > Sample request:
    $curl -X GET "http://localhost:8080/clip/v1/user/transaction/random" -H 'Content-Type: application/json'
```

You can use postman, curl or any other application that allows you to hit the above endpoints.
Locally the server runs at port 8080.

Implementation details
==============================

This a SOA based architecture project.

* `web` Holds the public resources accessible via http methods.
* `api` This module defines the interfaces to be implemented.
* `service` The service module that holds the business logic implementation of the api.
* `dao` The data access object module to access to database information.

This is a common approach very useful when we work with micro-services as it provides a very well structured and modularized
application. This approach provides us with a good solution to maintain independent modules that interact
with each other.

Some other patterns:
* `DAO` Very common pattern to keep the details of access to database independently.
* `Singleton` This allows us to create single instances of object that can be used across all the application.
* `Dependency Injection` Depending on interfaces rather than implementations is a very good practice. So we declare
interfaces and inject the actual implementation object taking advantage of the spring mechanism.
* `Builders` This pattern helps us to keep the object construction separately and transparently. So we don't have
to worry about common boilerplate code and instead we focus on the actual business logic. Builders also contribute
to reuse code.
* `Factory` This pattern allows us to create instances of objects depending on a given argument. It is very
useful to create object in a single factory and contributes to a better design to perform unit testing.
