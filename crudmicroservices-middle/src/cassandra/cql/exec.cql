CREATE KEYSPACE IF NOT EXISTS crudmicroservices WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3};

CREATE TABLE IF NOT EXISTS crudmicroservices.person (
	id bigint,
	name text,
	phone_number text,
	PRIMARY KEY (id));
	
CREATE TABLE IF NOT EXISTS crudmicroservices.person_by_name (
	id bigint,
	name text,
	phone_number text,
	PRIMARY KEY (id, name));