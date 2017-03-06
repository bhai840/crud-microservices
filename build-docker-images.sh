#!bin/sh

mvn clean install crudmicroservices-edge
docker build -t crudmicroservices-edge/crudmicroservices-edge:latest .

mvn clean install crudmicroservices-middle
docker build -t crudmicroservices-middle/crudmicroservices-middle:latest .
