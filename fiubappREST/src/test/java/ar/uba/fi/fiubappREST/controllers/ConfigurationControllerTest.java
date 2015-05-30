package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.LocationConfiguration;
import ar.uba.fi.fiubappREST.services.ConfigurationService;
import ar.uba.fi.fiubappREST.services.SessionService;

public class ConfigurationControllerTest {
	
	private static final String AN_USER_NAME = "anUserName";
	private static final String A_TOKEN = "aToken";

	private ConfigurationController controller;
	
	@Mock
	private ConfigurationService service;
	@Mock
	private SessionService studentSessionService;
	@Mock
	private LocationConfiguration locationConfiguration;
	
	@Before
	public void setUp(){
		this.service = mock(ConfigurationService.class);
		this.studentSessionService = mock(SessionService.class);
		this.controller = new ConfigurationController(service, studentSessionService);
		
		this.locationConfiguration = mock(LocationConfiguration.class);
	}

	@Test
	public void testGetLocationConfiguration() {
		doNothing().when(studentSessionService).validateThisStudent(A_TOKEN, AN_USER_NAME);
		when(service.getLocationConfiguration(AN_USER_NAME)).thenReturn(locationConfiguration);
				
		LocationConfiguration savedLocationConfiguration = this.controller.getLocationConfiguration(A_TOKEN, AN_USER_NAME);
		
		assertEquals(locationConfiguration, savedLocationConfiguration);		
	}
		
	@Test
	public void testUpdateLocationConfiguration() {
		doNothing().when(studentSessionService).validateThisStudent(A_TOKEN, AN_USER_NAME);
		when(service.updateLocationConfiguration(AN_USER_NAME, locationConfiguration)).thenReturn(locationConfiguration);
				
		LocationConfiguration updatedLocationConfiguration = this.controller.updateLocationNotification(A_TOKEN, AN_USER_NAME, locationConfiguration);
		
		assertEquals(locationConfiguration, updatedLocationConfiguration);		
	}
	
}

