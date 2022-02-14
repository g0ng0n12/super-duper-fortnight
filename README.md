# Wallets Service

----------------------
# Introduction
We have a service to manage our wallets. Our players can top-up their wallets using a credit card and spend that money on the platform (bookings, racket rentals, ...)

This service has the following operations:
- You can query your balance.
- You can top-up your wallet. In this case, we charge the amount using a third-party payments platform (stripe, paypal, redsys).
- You can spend your balance on purchases in Playtomic. 
- You can return these purchases, and your money is refunded.
- You can check your history of transactions.

This exercise consists of building a proof of concept of this wallet service.
You have to code endpoints for these operations:
1. Get a wallet using its identifier.
1. Top-up money in that wallet using a credit card number. It has to charge that amount internally using a third-party platform.

The basic structure of a wallet is its identifier and its current balance. If you think you need extra fields, add them. We will discuss it in the interview. 

So you can focus on these problems, you have here a maven project with a Spring Boot application. It already contains
the basic dependencies and an H2 database. There are development and test profiles.

You can also find an implementation of the service that would call to the real payments platform (StripePaymentService).
This implementation is calling to a simulator deployed in one of our environments. Take into account
that this simulator will return 422 http error codes under certain conditions.

Consider that this service must work in a microservices environment in high availability. You should care about concurrency.

You can spend as much time as you need but we think that 4 hours is enough to show [the requirements of this job.](OFFER.md)
You don't have to document your code, but you can write down anything you want to explain or anything you have skipped.
You don't need to write tests for everything, but we would like to see different types of tests.
-----------------------
## My Thinking

I decided to create 3 endpoints. 
    - One to get a Wallet by Id that uses a GET HTTP Method.
    - Another endpoint to add balance to the wallet (topup) using a Patch method because it's only update part of the Resource to all of it.
    - The last endpoint that is the one that you can use to Spend or refund the payments. 
I modeled The transactions, so the TopUp, Spend and Refund is one type of transaction each, 
so when you generate one of those functionalities and everything is ok you will save a transaction in your wallet.
Nowadays the refund functionality is against your balance and not validating against an old transaction that is in the wallet

Also I fixed the StripServiceTest by using several mocks and in integration-test happy path that the external endpoint is tested.
and I created an endpoint to create an empty wallet in order to have a wallet to test the endpoints.

-----------------------
## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/es/java/technologies/javase/jdk11-archive-downloads.html)
- [Maven 3](https://maven.apache.org)
- [DOCKER](https://docs.docker.com/install/) - If you want to build and run a release with Mysql

----------------------
# Endpoints

## Swagger-UI
 - http://localhost:8090/swagger-ui/#/wallet-controller

## App Health-Check
 - http://localhost:8090/actuator/health
## Postman
 - Wallet.postman_collection.json file... this file is a Postman Collection v2.1
----------------------
## Running the application locally

There are several ways to start the application Locally

For development purposes you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:
in the command line from the root path of the project run:

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=develop
```
This will fire up the application with an H2 on-memory database loaded. When you start the app you can access the
h2 db using this url: http://localhost:8090/h2-console
the user and pass are the default

```
to check the status of the application please use this url: http://localhost:8090/actuator/health

to see the API endpoints documentation you please check the swagger ui URL: http://localhost:8090/swagger-ui/#/wallet-controller
```
-------------

## Build and Run a Release with Docker and Docker-compose in your LocalHost

You can use docker-compose to load the app with a MYSQL db.
All the environment variables for docker were place in the .env-sample file.
To generate a build and run with docker you need to create a .env file and copy and paste the vars from the .env-sample
file into the new .env file

in the command line from the root path of the project run:
```shell
docker-compose up
```

```
to check the status of the application please use this url: http://localhost:8090/actuator/health
to see the API endpoints documentation you please check the swagger ui URL: http://localhost:8090/swagger-ui.html
```

if you need to re-run (without delete and stop all the process) the build and deploy
new changes you can run in the command line from the root path:
```shell
docker-compose up --build
```

in order to stop the containers you should run in the command line from the root path:
```shell
docker-compose down --rmi local
```
this will stop the containers and remove the images from your computer.
### Other Docker tips

When you use the build using the docker-compose file you will see that for every build you are going to generate
a new image... so in order to delete the unused image please use this command:

```shell
docker image prune -f
```

-------------------------

## Running the Integration Tests
I only added one integration test that contains the happy path, that is:
    - Create an empty wallet
    - TopUp the Wallet
    - Spend some balance of the wallet
    - Get that wallet with the 2 transactions made (TopUp and Spend)

in order to run the integration tests you should run in the command line from the root path:
```shell
mvn integration-test verify  
```
### When integration-test ends you will see this.
```
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 9.186 s - in com.playtomic.tests.wallet.integration.WalletApplicationIT
```

## Running Tests
In order to run Unit Tests with mocks you should run in the command line from the root path:
```shell
mvn -DSPRING_PROFILE_ACTIVE=test test
```

-------------------
## Things to improve
* Improve the refund transaction functionality. Now to generate a refund you will refund balance and not against an old transaction. There is no validation for this

* Creating a .travis.yml file in order to create pipeline

* creating a deployment.yml and a service.yml file in order to deploy this in EKS

* Adding an initialisation script to add Data when the app boots up, and try flyway or liquidbase

* Improve the Integration Test:
    - Add error path in the TopUp to test the refund external endpoint
    - Improve the maven process to run the integration tests with maven profile
    - Generate Fixtures to load specific data before the test runs
    - Use [Rest-Assured](http://rest-assured.io/) to fluently create HTTP requests and assertions about the response.
    

* Performance:
    - Add a Jmeter file to run load tests

-------------------
## Common Errors

    1. Initially there are no data, so I added a create wallet endpoint (POST) that creates an empty wallet
    2. When you run docker-compose up... if you see warnings like this: WARN[0000] The "MYSQL_USER" variable, you should create the .env file