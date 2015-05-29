package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import ar.uba.fi.fiubappREST.domain.GroupPicture;
import ar.uba.fi.fiubappREST.domain.StudentSession;
import ar.uba.fi.fiubappREST.representations.DiscussionCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupUpdateRepresentation;
import ar.uba.fi.fiubappREST.services.DiscussionService;
import ar.uba.fi.fiubappREST.services.GroupService;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

public class GroupControllerTest {
	
	private static final String A_NAME = "aName";
	private static final String AN_USER_NAME = "anUserName";
	private static final String A_TOKEN = "aToken";
	private static final Integer A_GROUP_ID = 14;

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
	
	@Test
	public void testGet(){
		StudentSession session = mock(StudentSession.class);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
		when(this.studentSessionService.find(A_TOKEN)).thenReturn(session);
		GroupRepresentation aRepresentation = mock(GroupRepresentation.class);
		when(this.service.findGroupForStudent(A_GROUP_ID, AN_USER_NAME)).thenReturn(aRepresentation);
		
		GroupRepresentation group = this.controller.get(A_TOKEN, A_GROUP_ID);
		
		assertEquals(aRepresentation, group);		
	}
	
	@Test
	public void testGetPicture() {
		GroupPicture picture = mock(GroupPicture.class);
		byte[] bytes = "Mock".getBytes(); 
		doReturn(bytes).when(picture).getImage();
		doReturn(MediaType.IMAGE_PNG_VALUE).when(picture).getContentType();
		doReturn(picture).when(service).getPicture(A_GROUP_ID);
				
		ResponseEntity<byte[]> response = this.controller.getGroupPicture(A_GROUP_ID);
		
		assertEquals(bytes, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(MediaType.IMAGE_PNG, response.getHeaders().getContentType());
	}
	
	@Test
	public void testUpdatePicture() {
		StudentSession session = mock(StudentSession.class);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
		when(studentSessionService.find(A_TOKEN)).thenReturn(session);
		MultipartFile image = mock(MultipartFile.class);
		doNothing().when(service).updatePicture(A_GROUP_ID, image, AN_USER_NAME);
				
		this.controller.updateGroupPicture(A_TOKEN, A_GROUP_ID, image);
		
		assertTrue(true);		
	}
	
	@Test
	public void testUpdate(){
		StudentSession session = mock(StudentSession.class);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
		when(studentSessionService.find(A_TOKEN)).thenReturn(session);
		GroupUpdateRepresentation updatingGroup = mock(GroupUpdateRepresentation.class);
		GroupRepresentation representation = mock(GroupRepresentation.class);
		when(this.service.updateGroup(A_GROUP_ID, updatingGroup, AN_USER_NAME)).thenReturn(representation);
		
		GroupRepresentation updatedGroup = this.controller.update(A_TOKEN, A_GROUP_ID, updatingGroup);
		
		assertEquals(representation, updatedGroup);
	}
	

	
}


