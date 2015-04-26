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

import ar.uba.fi.fiubappREST.domain.StudentSession;
import ar.uba.fi.fiubappREST.representations.GroupCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupRepresentation;
import ar.uba.fi.fiubappREST.services.GroupService;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

public class GroupControllerTest {
	
	private static final String A_NAME = "aName";
	private static final String AN_USER_NAME = "anUserName";
	private static final String A_TOKEN = "aToken";

	private GroupController controller;
	
	@Mock
	private GroupService service;
	@Mock
	private StudentSessionService studentSessionService;
	
	
	@Before
	public void setUp(){
		this.service = mock(GroupService.class);
		this.studentSessionService = mock(StudentSessionService.class);
		this.controller = new GroupController(service, studentSessionService);
	}

	@Test
	public void testCreateGroup(){
		doNothing().when(this.studentSessionService).validateMine(A_TOKEN, AN_USER_NAME);
		GroupCreationRepresentation groupRepresentation = new GroupCreationRepresentation();
		groupRepresentation.setOwnerUserName(AN_USER_NAME);
		GroupRepresentation representation = mock(GroupRepresentation.class);
		when(this.service.create(groupRepresentation)).thenReturn(representation);
		
		GroupRepresentation createdGroup = this.controller.createGroup(A_TOKEN, groupRepresentation);
		
		assertEquals(representation, createdGroup);
	}
	
	@Test
	public void testFindGroups(){
		StudentSession session = mock(StudentSession.class);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
		when(this.studentSessionService.find(A_TOKEN)).thenReturn(session);
		GroupRepresentation aRepresentation = mock(GroupRepresentation.class);
		GroupRepresentation anotherRepresentation = mock(GroupRepresentation.class);
		List<GroupRepresentation> representations = new ArrayList<GroupRepresentation>();
		representations.add(aRepresentation);
		representations.add(anotherRepresentation);
		when(this.service.findByProperties(AN_USER_NAME, A_NAME)).thenReturn(representations);
		
		List<GroupRepresentation> foundGroups = this.controller.findGroups(A_TOKEN, A_NAME);
		
		assertEquals(2, foundGroups.size());
	}
}


