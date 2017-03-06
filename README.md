[![Build Status](https://travis-ci.org/materasystems/crud-microservices.svg?branch=master)](https://travis-ci.org/materasystems/crud-microservices)

## CRUD-Microservices
==========================================

This is a example project using Netflix OSS technologies to build Microservices that will run at AWS cloud.

| Frameworks/Tools | -                  | -            |
| ---------------- |:------------------:| ------------:|
| Maven            | Netflix Governator | Google Guava |
| Java 8           | Netflix Karyon     | jUnit        |
| Google Guice     | Netflix Hystrix    | AngularJS    |
| Jersey           | Netflix Eureka     | Bootstrap    |
| Jackson          | Netflix Archaius   | Less         |
| Cassandra        | Netflix Ribbon     | Bower        |
| RxJava           | Netflix EVCache    | NPM          |

## Running with Docker
The [docker-compose](https://github.com/rochapaulo/crud-microservices/blob/master/docker-compose.yml) file starts everything needed to have the 'crudmicroservices' up and running. First we have to build the project and generate the images for each service, for this just run the script [build-docker-images](https://github.com/rochapaulo/crud-microservices/blob/master/build-docker-images.sh)

```sh
$ sh build-docker-images.sh
```

Having it done we are ready to start all of services (ServiceDiscovery, APIGateway, EdgeService, MiddleService, Cassandra..)

```sh
$ sh docker-compose up
```

This single line command will start everything, just wait a moment (+/- 1 min).

### Scaling Up
```sh
$ sh docker-compose scale {serviceName}={numberOfInstances} ...
```

Once started we can verify the Up services at Eureka console: [Eureka Console](http://localhost:8080/eureka)

The API Gateway can be reached at: [Zuul](http://localhost:9090)

Requests can be sent to the Gateway: http://localhost:9090/crudmicroservicesedge

### Requests Examples
#### Create Person
Request
```sh
$ curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{
	"id": 1,
	"name": "Paulo Almeida",
	"phoneNumber": "99513131"
}' "http://localhost:9090/crudmicroservicesedge/services/v1/persons/"
```

#### Query Person by ID
Request:
```sh
curl -X GET -H "Cache-Control: no-cache" "http://localhost:9090/crudmicroservicesedge/services/v1/persons/1"
```
Response:
```javascript
{
  "id": 1,
  "name": "Paulo Almeida",
  "phoneNumber": "99513131"
}
```

#### Retrieve all Persons
Request:
```sh
curl -X GET -H "Cache-Control: no-cache" "http://localhost:9090/crudmicroservicesedge/services/v1/persons"
```
Response:
```javascript
{
	"totalAssets": 2,
	"assets": [{
		"id": 1,
		"name": "Paulo Almeida",
		"phoneNumber": "99513131"
	}, {
		"id": 2,
		"name": "Joaquim da Silva",
		"phoneNumber": "98183229"
	}]
}
```
--------------------------------------------------
## Running without Docker
#### crudmicroservices-ui
```
$ gulp serve
```

#### crudmicroservices-edge
```
$ mvn tomcat7:run {for eureka add: -P eureka-local}
```

#### crudmicroservices-middle
```
$ mvn cassandra:start tomcat7:run {for eureka add: -P eureka-local}
```
