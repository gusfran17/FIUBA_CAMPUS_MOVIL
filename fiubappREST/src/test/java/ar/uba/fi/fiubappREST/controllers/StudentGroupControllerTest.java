package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.representations.GroupRepresentation;
import ar.uba.fi.fiubappREST.services.GroupService;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

public class StudentGroupControllerTest {
	
	private static final Integer A_GROUP_ID = 2;
	private static final String AN_USER_NAME = "anUserName";
	private static final String A_TOKEN = "aToken";

	private StudentGroupController controller;
	
	@Mock
	private GroupService service;
	@Mock
	private StudentSessionService studentSessionService;
	
	
	@Before
	public void setUp(){
		this.service = mock(GroupService.class);
		this.studentSessionService = mock(StudentSessionService.class);
		this.controller = new StudentGroupController(service, studentSessionService);
	}

	@Test
	public void testRegisterStudentInGroup(){
		doNothing().when(this.studentSessionService).validateMine(A_TOKEN, AN_USER_NAME);
		GroupRepresentation representation = mock(GroupRepresentation.class);
		when(this.service.registerStudent(AN_USER_NAME, A_GROUP_ID)).thenReturn(representation);
		
		GroupRepresentation group = this.controller.signOnGroup(A_TOKEN, AN_USER_NAME, A_GROUP_ID);
		
		assertEquals(representation, group);
	}
	
}


