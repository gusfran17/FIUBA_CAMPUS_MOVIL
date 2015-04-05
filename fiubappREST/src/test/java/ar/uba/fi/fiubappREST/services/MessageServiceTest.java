package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class MessageServiceTest {
	
	private MessageService service;
	private Properties mockErrorMessages;
	
	@Before
	public void setUp(){
		mockErrorMessages = mock(Properties.class);
		service = new MessageService();
		service.setErrorMessages(mockErrorMessages);
	}

	@Test
	public void testGetMessage() {
		when(mockErrorMessages.getProperty("CODE")).thenReturn("A{0}C{1}");
		
		String message = service.getMessage("CODE", "B", "D");
		
		assertEquals("ABCD", message);
	}

}
