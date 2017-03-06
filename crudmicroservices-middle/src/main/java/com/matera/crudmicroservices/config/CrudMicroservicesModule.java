package com.matera.crudmicroservices.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.matera.crudmicroservices.cache.Cache;
import com.matera.crudmicroservices.cache.CacheImpl;
import com.matera.crudmicroservices.service.PersonService;
import com.matera.crudmicroservices.store.PersonStore;
import com.matera.crudmicroservices.store.impl.PersonStoreCassandra;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.evcache.EVCache;
import com.netflix.governator.guice.lazy.LazySingletonScope;

public class CrudMicroservicesModule extends AbstractModule {

	private static final DynamicStringProperty cassandraHost;
	private static final DynamicStringProperty cassandraKeyspace;
	private static final DynamicStringProperty cacheAppName;
	private static final DynamicStringProperty cachePrefix;

	static {

		DynamicPropertyFactory factory = DynamicPropertyFactory.getInstance();

		cassandraHost = factory.getStringProperty("crudmicroservicesmiddle.cassandra.host", "");
		cassandraKeyspace = factory.getStringProperty("crudmicroservicesmiddle.cassandra.keyspace", "");
		cacheAppName = factory.getStringProperty("crudmicroservicesmiddle.evcache.appname", "");
		cachePrefix = factory.getStringProperty("crudmicroservicesmiddle.evcache.prefix", "");

	}

	@Override
	protected void configure() {
		bind(PersonStore.class).to(PersonStoreCassandra.class).in(LazySingletonScope.get());
		bind(PersonService.class).to(PersonService.class).in(LazySingletonScope.get());
		bind(Cache.class).to(CacheImpl.class).in(LazySingletonScope.get());
	}

	@Provides
	public Session cassandraSession() {
		return Cluster.builder().addContactPoint(cassandraHost.get()).build().connect(cassandraKeyspace.get());
	}
	
	@Provides
	public EVCache cache() {
		final EVCache cache = 
				(new EVCache.Builder()).setAppName(cacheAppName.get()).setCacheName(cachePrefix.get()).enableZoneFallback().build();
		return cache;
	}
	
}
