package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.Credentials;
import ar.uba.fi.fiubappREST.domain.StudentSession;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

public class StudentSessionControllerTest {
	
	@Mock
	private StudentSessionService service;
	@Mock
	private Credentials credentials;
	@Mock
	private StudentSession session;
		
	private StudentSessionController controller;
	
	@Before
	public void setUp(){
		this.service = mock(StudentSessionService.class);
		
		this.controller = new StudentSessionController(service);
		
		this.credentials = mock(Credentials.class);
		this.session = mock(StudentSession.class);
	}

	@Test
	public void testCreateSession() {
		when(this.service.create(credentials)).thenReturn(session);
		
		StudentSession createdSession = this.controller.createSession(credentials);
		
		assertNotNull(createdSession);
	}

}
