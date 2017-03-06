#!bin/sh

mvn clean install -DskipTests -o
docker build -t crudmicroservices-edge:latest crudmicroservices-edge/
docker build -t crudmicroservices-middle:latest crudmicroservices-middle/
