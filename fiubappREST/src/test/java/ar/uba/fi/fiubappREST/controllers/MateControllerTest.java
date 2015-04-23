package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.representations.MateRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;
import ar.uba.fi.fiubappREST.services.MateService;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

public class MateControllerTest {
	
	private static final String AN_USER_NAME = "anUserName";
	private static final String A_MATE_USER_NAME = "aMateUserName";
	private static final String A_TOKEN = "aToken";

	private MateController controller;
	
	@Mock
	private MateService service;
	@Mock
	private StudentSessionService studentSessionService;
	
	
	@Before
	public void setUp(){
		this.service = mock(MateService.class);
		this.studentSessionService = mock(StudentSessionService.class);
		this.controller = new MateController(service, studentSessionService);
	}

	@Test
	public void testBecomeMates(){
		doNothing().when(this.studentSessionService).validateMine(A_TOKEN, AN_USER_NAME);
		MateRepresentation mate = new MateRepresentation();
		mate.setUserName(A_MATE_USER_NAME);
		StudentProfileRepresentation representation = mock(StudentProfileRepresentation.class);
		when(this.service.becomeMates(AN_USER_NAME, A_MATE_USER_NAME)).thenReturn(representation);
		
		StudentProfileRepresentation createdProfile = this.controller.becomeMates(A_TOKEN, AN_USER_NAME, mate);
		
		assertEquals(representation, createdProfile);
	}
	
	@Test
	public void testGetMates(){
		doNothing().when(this.studentSessionService).validateMine(A_TOKEN, AN_USER_NAME);
		StudentProfileRepresentation representation = mock(StudentProfileRepresentation.class);
		StudentProfileRepresentation anotherRepresentation = mock(StudentProfileRepresentation.class);
		List<StudentProfileRepresentation> profiles = new ArrayList<StudentProfileRepresentation>();
		profiles.add(representation);
		profiles.add(anotherRepresentation);
		when(this.service.getMates(AN_USER_NAME)).thenReturn(profiles);
		
		List<StudentProfileRepresentation> foundMates = this.controller.getMates(A_TOKEN, AN_USER_NAME);
		
		assertEquals(profiles, foundMates);
	}
	
	@Test
	public void testDeleteMate(){
		doNothing().when(this.studentSessionService).validateMine(A_TOKEN, AN_USER_NAME);
		MateRepresentation mate = new MateRepresentation();
		mate.setUserName(A_MATE_USER_NAME);
		doNothing().when(this.service).deleteMate(AN_USER_NAME, A_MATE_USER_NAME);
		
		this.controller.deleteMate(A_TOKEN, AN_USER_NAME, mate);
		
		assertTrue(true);
	}
}


