[![Build Status](https://travis-ci.org/materasystems/crud-microservices.svg?branch=master)](https://travis-ci.org/materasystems/crud-microservices)

## CRUD-Microservices
==========================================

This is a example project using Netflix OSS technologies to build Microservices that will run at AWS cloud. The used technologies are:

* Maven
* Java 8
* Google Guice
* Netflix OSS
  * Governator
  * Karyon
  * Hystrix
  * Eureka
  * Archaius
  * Ribbon
  * EVCache
* Jersey
* Jackson
* Cassandra
* RXJava (Observable)
* Guava
* JUnit
* Groovy / Spock
* AngularJS
* Bootstrap
* Less
* Bower
* NPM

## Running with Docker
The docker-compose file starts everything needed to have the 'crudmicroservices' up and running. First we have to build the project and generate the images for each service, for this just run the script 'build-docker-images'

```sh
$ sh build-docker-images.sh
```

Having it done we are ready to start all of services (ServiceDiscovery, APIGateway, EdgeService, MiddleService, Cassandra..)

```sh
$ sh docker-compose up
```

This single line command will start everything, just wait a moment (+/- 1 min).
Once started we can verify the Up services at Eureka console:
* http://localhost:8080/eureka

The API Gateway can be reached at: 
* http://localhost:9090

Requests can be sent to the Gateway:
* http://localhost:9090/crudmicroservicesedge

#### Examples
