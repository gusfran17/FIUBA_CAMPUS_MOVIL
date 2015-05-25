package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.Credentials;
import ar.uba.fi.fiubappREST.domain.Session;
import ar.uba.fi.fiubappREST.services.SessionService;

public class StudentSessionControllerTest {
	
	private static final String A_TOKEN = "A_TOKEN";
	
	@Mock
	private SessionService service;
	@Mock
	private Credentials credentials;
	@Mock
	private Session session;
		
	private StudentSessionController controller;
	
	@Before
	public void setUp(){
		this.service = mock(SessionService.class);
		
		this.controller = new StudentSessionController(service);
		
		this.credentials = mock(Credentials.class);
		this.session = mock(Session.class);
	}

	@Test
	public void testCreateSession() {
		when(this.service.createStudentSession(credentials)).thenReturn(session);
		
		Session createdSession = this.controller.createSession(credentials);
		
		assertNotNull(createdSession);
	}
	
	@Test
	public void testFindSession() {
		when(this.service.findStudentSession(A_TOKEN)).thenReturn(session);
		
		Session foundSession = this.controller.findSession(A_TOKEN);
		
		assertNotNull(foundSession);
	}

}
