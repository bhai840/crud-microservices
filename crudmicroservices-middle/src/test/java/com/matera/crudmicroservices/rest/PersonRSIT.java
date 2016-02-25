package com.matera.crudmicroservices.rest;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.util.Providers;
import com.matera.crudmicroservices.core.domain.Person;
import com.matera.crudmicroservices.store.PersonStore;
import com.matera.crudmicroservices.store.impl.PersonStoreImpl;

public class PersonRSIT {

	@BeforeClass
	public static void before() {
		
		Session session = Cluster.builder().addContactPoint("127.0.0.1").build().connect("crudmicroservices");
		PersonStore store = new PersonStoreImpl(Providers.of(session));
		
		store.save(Person.builder().withId(1L).withName("Andre Grant").withPhoneNumber("202-555-0166").build());
		store.save(Person.builder().withId(2L).withName("Rachael Mccormick").withPhoneNumber("202-555-0187").build());
		store.save(Person.builder().withId(3L).withName("Willie Barrett").withPhoneNumber("202-555-0155").build());
		store.save(Person.builder().withId(4L).withName("Marcus Martinez").withPhoneNumber("202-555-0187").build());
		store.save(Person.builder().withId(5L).withName("Andre Grant").withPhoneNumber("202-555-0155").build());
		
	}
	
	@Test
	public void byId() throws Exception {
		
		final String uri = "http://localhost:9080/crudmicroservices/person/3";
		HttpResponse response = doGET(uri);
		com.matera.crudmicroservices.core.entities.Person person = getEntity(response.getEntity(), com.matera.crudmicroservices.core.entities.Person.class);
		
		Assert.assertEquals(Long.valueOf(3), person.getId());
		Assert.assertEquals("Willie Barrett", person.getName());
		Assert.assertEquals("202-555-0155", person.getPhoneNumber());
	}
	
	@Test
	public void all() throws Exception {
	
		final String uri = "http://localhost:9080/crudmicroservices/person/all";
		HttpResponse response = doGET(uri);

		final Type type = new TypeToken<List<com.matera.crudmicroservices.core.entities.Person>>() {}.getType();
		List<com.matera.crudmicroservices.core.entities.Person> persons = getEntity(response.getEntity(), type);
		Assert.assertEquals(5, persons.size());
	}
	
	@Ignore
	@Test
	public void filterByName() throws Exception {
		
		final String uri = "http://localhost:9080/crudmicroservices/person/all?name=Andre%20Grant";
		HttpResponse response = doGET(uri);
		
		final Type type = new TypeToken<List<com.matera.crudmicroservices.core.entities.Person>>() {}.getType();
		List<com.matera.crudmicroservices.core.entities.Person> persons = getEntity(response.getEntity(), type);
		Assert.assertEquals(2, persons.size());
	}
	
	@Ignore
	@Test
	public void filterByPhoneNumber() throws Exception {
		
		final String uri = "http://localhost:9080/crudmicroservices/person/all?phoneNumber=202-555-0155";
		HttpResponse response = doGET(uri);
		
		final Type type = new TypeToken<List<com.matera.crudmicroservices.core.entities.Person>>() {}.getType();
		List<com.matera.crudmicroservices.core.entities.Person> persons = getEntity(response.getEntity(), type);
		Assert.assertEquals(2, persons.size());
	}
	
	@Ignore
	@Test
	public void filterByNameAndPhoneNumber() throws Exception {
		
		final String uri = "http://localhost:9080/crudmicroservices/person/all?name=Andre%20Grant?phoneNumber=202-555-0155";
		HttpResponse response = doGET(uri);
		
		final Type type = new TypeToken<List<com.matera.crudmicroservices.core.entities.Person>>() {}.getType();
		List<com.matera.crudmicroservices.core.entities.Person> persons = getEntity(response.getEntity(), type);
		Assert.assertEquals(1, persons.size());
		
		Assert.assertEquals(Long.valueOf(5), persons.get(0).getId());
		Assert.assertEquals("Andre Grant", persons.get(0).getName());
		Assert.assertEquals("202-555-0155", persons.get(0).getPhoneNumber());
	}
	
	private HttpResponse doGET(String uri) throws Exception {
		HttpGet request = new HttpGet(uri);
		request.addHeader("Content-Type", "application/json");
		return new DefaultHttpClient().execute(request);
	}
	
	private <T> T getEntity(HttpEntity entity, Class<T> klass) throws Exception {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteStreams.copy(entity.getContent(), out);
		
        String json = out.toString();
        System.out.println("-------------> " + json);
        
		return new Gson().fromJson(json, klass);
	}
	
	private <T> T getEntity(HttpEntity entity, Type type) throws Exception {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteStreams.copy(entity.getContent(), out);
		
		return new Gson().fromJson(out.toString(), type);
	}
	
}
