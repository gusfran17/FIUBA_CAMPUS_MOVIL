package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.Application;
import ar.uba.fi.fiubappREST.services.NotificationService;
import ar.uba.fi.fiubappREST.services.SessionService;

public class ApplicationControllerTest {
	
	private static final String AN_USER_NAME = "anUserName";
	private static final String APPLICANT_USER_NAME = "anApplicantUserName";
	private static final String A_TOKEN = "aToken";

	private ApplicationController controller;
	
	@Mock
	private NotificationService service;
	@Mock
	private SessionService studentSessionService;
	
	private Application application;
	
	@Before
	public void setUp(){
		this.service = mock(NotificationService.class);
		this.studentSessionService = mock(SessionService.class);
		this.controller = new ApplicationController(service, studentSessionService);
		
		this.application = new Application();
		this.application.setApplicantUserName(APPLICANT_USER_NAME);
	}

	@Test
	public void testCreateApplication() {
		doNothing().when(studentSessionService).validateThisStudent(A_TOKEN, AN_USER_NAME);
		when(service.createApplicationNotification(AN_USER_NAME, application)).thenReturn(application);
				
		Application createdApplication = this.controller.createApplication(A_TOKEN, AN_USER_NAME, application);
		
		assertEquals(application, createdApplication);		
	}

}

