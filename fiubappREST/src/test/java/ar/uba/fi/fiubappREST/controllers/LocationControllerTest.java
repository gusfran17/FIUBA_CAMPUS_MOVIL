package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.Location;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.representations.MateLocationRepresentation;
import ar.uba.fi.fiubappREST.services.LocationService;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

public class LocationControllerTest {
	
	private static final double LONGITUDE = -58.123456;
	private static final double LATITUDE = -34.123456;
	private static final String AN_USER_NAME = "anUserName";
	private static final String A_TOKEN = "aToken";

	private LocationController controller;
	
	@Mock
	private LocationService service;
	@Mock
	private StudentSessionService studentSessionService;
	@Mock
	private Student student;
	
	private Location location;
	
	
	@Before
	public void setUp(){
		this.service = mock(LocationService.class);
		this.studentSessionService = mock(StudentSessionService.class);
		this.controller = new LocationController(service, studentSessionService);
		this.student = mock(Student.class);
		
		this.location = new Location();
		this.location.setLatitude(LATITUDE);
		this.location.setLongitude(LONGITUDE);
		this.location.setStudent(student);
	}

	@Test
	public void testUpdateLocation() {
		doNothing().when(studentSessionService).validateMine(A_TOKEN, AN_USER_NAME);
		when(service.updateLocation(AN_USER_NAME, location)).thenReturn(location);
				
		Location savedLocation = this.controller.updateLocation(A_TOKEN, AN_USER_NAME, location);
		
		assertEquals(location, savedLocation);		
	}
	
	@Test
	public void getMatesLocation(){
		doNothing().when(studentSessionService).validateMine(A_TOKEN, AN_USER_NAME);
		List<MateLocationRepresentation> locations = new ArrayList<MateLocationRepresentation>();
		locations.add(mock(MateLocationRepresentation.class));
		locations.add(mock(MateLocationRepresentation.class));
		locations.add(mock(MateLocationRepresentation.class));
		when(service.findMatesLocations(AN_USER_NAME, LATITUDE-10, LATITUDE+10, LONGITUDE-10, LONGITUDE+10)).thenReturn(locations);
				
		List<MateLocationRepresentation> foundMatesLocations = this.controller.getMatesLocation(A_TOKEN, AN_USER_NAME, LATITUDE-10, LATITUDE+10, LONGITUDE-10, LONGITUDE+10);
		
		
		assertEquals(locations, foundMatesLocations);		
	}
}

