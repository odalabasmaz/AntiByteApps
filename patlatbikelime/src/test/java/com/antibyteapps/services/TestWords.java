package com.antibyteapps.services;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * @author Orhun Dalabasmaz
 */
public class TestWords {
	private static ClientService CLIENT;

	private void initClientService() {
		if (CLIENT == null) {
			CLIENT = ClientService.getInstance();
		}
	}

	@Test
	public void testWordsViaServices() {
		initClientService();
		assertTrue(CLIENT.plainRequest().equals("You should give me a word to check!"));
		assertTrue(CLIENT.plainRequest("aba").contains("is a word"));
		assertTrue(CLIENT.plainRequest("abaş").contains("is not a word"));
		assertTrue(CLIENT.plainRequest("abay").contains("is not a word"));
		assertTrue(CLIENT.plainRequest("abayı").contains("is not a word"));
		assertTrue(CLIENT.plainRequest("abar").contains("is not a word"));
		assertTrue(CLIENT.plainRequest("abart").contains("is not a word"));
		assertTrue(CLIENT.plainRequest("abartı").contains("is a word"));
		assertTrue(CLIENT.plainRequest("kalemi").contains("is not a word"));
		assertTrue(CLIENT.plainRequest("atkı").contains("is a word"));
	}
}
