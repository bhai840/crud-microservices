package com.matera.crudmicroservices.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.matera.crudmicroservices.cache.Cache;
import com.matera.crudmicroservices.cache.CacheStub;
import com.matera.crudmicroservices.store.PersonStore;
import com.matera.crudmicroservices.store.impl.PersonStoreCassandra;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.governator.guice.lazy.LazySingletonScope;

public class CrudMicroservicesLocalModule extends AbstractModule {

	private static final DynamicStringProperty cassandraHost;
	private static final DynamicStringProperty cassandraKeyspace;

	static {
		DynamicPropertyFactory factory = DynamicPropertyFactory.getInstance();
		cassandraHost = factory.getStringProperty("crudmicroservicesmiddle.cassandra.host", "");
		cassandraKeyspace = factory.getStringProperty("crudmicroservicesmiddle.cassandra.keyspace", "");
	}

	@Override
	protected void configure() {
		bind(PersonStore.class).to(PersonStoreCassandra.class).in(LazySingletonScope.get());
		bind(Cache.class).to(CacheStub.class).in(LazySingletonScope.get());
	}

	@Provides
	public Session cassandraSession() {
		return Cluster.builder().addContactPoint(cassandraHost.get()).build().connect(cassandraKeyspace.get());
	}
	
}
