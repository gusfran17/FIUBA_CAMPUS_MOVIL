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

import ar.uba.fi.fiubappREST.representations.GroupRepresentation;
import ar.uba.fi.fiubappREST.services.GroupService;
import ar.uba.fi.fiubappREST.services.SessionService;

public class StudentGroupControllerTest {
	
	private static final Integer A_GROUP_ID = 2;
	private static final String AN_USER_NAME = "anUserName";
	private static final String A_TOKEN = "aToken";

	private StudentGroupController controller;
	
	@Mock
	private GroupService service;
	@Mock
	private SessionService studentSessionService;
	
	
	@Before
	public void setUp(){
		this.service = mock(GroupService.class);
		this.studentSessionService = mock(SessionService.class);
		this.controller = new StudentGroupController(service, studentSessionService);
	}

	@Test
	public void testRegisterStudentInGroup(){
		doNothing().when(this.studentSessionService).validateThisStudent(A_TOKEN, AN_USER_NAME);
		GroupRepresentation representation = mock(GroupRepresentation.class);
		when(this.service.registerStudent(AN_USER_NAME, A_GROUP_ID)).thenReturn(representation);
		
		GroupRepresentation group = this.controller.signOnGroup(A_TOKEN, AN_USER_NAME, A_GROUP_ID);
		
		assertEquals(representation, group);
	}
	
	@Test
	public void testGetGroups(){
		doNothing().when(this.studentSessionService).validateThisStudent(A_TOKEN, AN_USER_NAME);
		GroupRepresentation aRepresentation = mock(GroupRepresentation.class);
		GroupRepresentation anotherRepresentation = mock(GroupRepresentation.class);
		List<GroupRepresentation> representations = new ArrayList<GroupRepresentation>();
		representations.add(aRepresentation);
		representations.add(anotherRepresentation);
		when(this.service.getStudentGroups(AN_USER_NAME)).thenReturn(representations);
		
		List<GroupRepresentation> groups = this.controller.getGroups(A_TOKEN, AN_USER_NAME);
		
		assertEquals(representations, groups);
	}
	
	@Test
	public void testDeleteGroups(){
		doNothing().when(this.studentSessionService).validateThisStudent(A_TOKEN, AN_USER_NAME);
		doNothing().when(this.service).unregisterStudent(AN_USER_NAME, A_GROUP_ID);
		
		this.controller.signOffGroup(A_TOKEN, AN_USER_NAME, A_GROUP_ID);
		
		assertTrue(true);
	}
	
}


