package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.AdminSession;
import ar.uba.fi.fiubappREST.domain.Credentials;
import ar.uba.fi.fiubappREST.services.AdminSessionService;

public class AdminSessionControllerTest {
	
	private static final String A_TOKEN = "A_TOKEN";
	
	@Mock
	private AdminSessionService service;
	@Mock
	private Credentials credentials;
	@Mock
	private AdminSession session;
		
	private AdminSessionController controller;
	
	@Before
	public void setUp(){
		this.service = mock(AdminSessionService.class);
		
		this.controller = new AdminSessionController(service);
		
		this.credentials = mock(Credentials.class);
		this.session = mock(AdminSession.class);
	}

	@Test
	public void testCreateSession() {
		when(this.service.create(credentials)).thenReturn(session);
		
		AdminSession createdSession = this.controller.createSession(credentials);
		
		assertNotNull(createdSession);
	}
	
	@Test
	public void testFindSession() {
		when(this.service.find(A_TOKEN)).thenReturn(session);
		
		AdminSession foundSession = this.controller.findSession(A_TOKEN);
		
		assertNotNull(foundSession);
	}
	
	@Test
	public void testDeleteSession() {
		doNothing().when(this.service).delete(A_TOKEN);
		
		this.controller.deleSession(A_TOKEN);
		
		assertTrue(true);
	}

}
