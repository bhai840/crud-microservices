FROM tomcat:7-jre8-alpine
MAINTAINER rochapaulo

RUN apk add --no-cache bash gawk sed grep bc coreutils
RUN apk add --update \
    python \
    python-dev \
    py-pip 
RUN apk add --update curl && \
    rm -rf /var/cache/apk/*

RUN curl -O https://pypi.python.org/packages/12/a7/13aff4ad358ff4abef6823d872154d0955ff6796739fcaaa2c80a6940aa6/cqlsh-5.0.3.tar.gz && \
    tar -zxvf cqlsh-5.0.3.tar.gz && \
    pip install -U pip setuptools

RUN cd cqlsh-5.0.3 && pip install . && cd ..

COPY wait-for-it.sh wait-for-it.sh
COPY src/cassandra/cql/exec.cql schema.cql
ADD target/crudmicroservicesmiddle.war /usr/local/tomcat/webapps/crudmicroservicesmiddle.war

EXPOSE 8080
