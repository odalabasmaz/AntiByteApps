package com.antibyteapps.services;

import org.glassfish.jersey.client.ClientConfig;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 * @author Orhun Dalabasmaz
 */
public class ClientService {
	private static final String ENDPOINT = "http://wrkodalabasmaz:8080/AntiByteApps";
	private static final String SERVICES = "services";
	private static final String SERVICE_PATH = "dictionary";

	private static ClientService service;
	private static WebTarget target;

	private ClientService() {
		initService();
	}

	public static ClientService getInstance() {
		if (service == null) {
			service = new ClientService();
		}
		return service;
	}

	private void initService() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		target = client.target(getBaseURI());
	}

	private URI getBaseURI() {
		return UriBuilder.fromUri(ENDPOINT).build();
	}

	public String plainRequest() {
		return target.path(SERVICES).path(SERVICE_PATH).request().accept(MediaType.TEXT_PLAIN).get(String.class);
	}

	public String plainRequest(String param) {
		return target.path(SERVICES).path(SERVICE_PATH).path(param).request().accept(MediaType.TEXT_PLAIN).get(String.class);
	}
}
