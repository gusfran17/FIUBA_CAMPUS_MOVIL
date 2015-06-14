package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import ar.uba.fi.fiubappREST.domain.GroupFile;
import ar.uba.fi.fiubappREST.domain.Session;
import ar.uba.fi.fiubappREST.services.GroupFileService;
import ar.uba.fi.fiubappREST.services.SessionService;

public class GroupFileControllerTest {
	
	private static final int A_FILE_ID = 15;
	private static final int A_GROUP_ID = 12;
	private static final String AN_USER_NAME = "anUserName";
	private static final String A_TOKEN = "aToken";

	private GroupFileController controller;
	
	@Mock
	private GroupFileService service;
	@Mock
	private SessionService sessionService;
	@Mock
	private Session session;
		
	@Before
	public void setUp(){
		this.service = mock(GroupFileService.class);
		this.sessionService = mock(SessionService.class);
		this.controller = new GroupFileController(service, sessionService);
		
		this.session = mock(Session.class);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
		when(sessionService.findStudentSession(A_TOKEN)).thenReturn(session);
	}

	@Test
	public void testLoadGroupFile() throws IOException {
		MultipartFile file = mock(MultipartFile.class);
		GroupFile groupFile = mock(GroupFile.class);
		when(service.loadFile(A_GROUP_ID, AN_USER_NAME, file)).thenReturn(groupFile);
				
		GroupFile savedGroupFile = this.controller.loadGroupFile(A_TOKEN, A_GROUP_ID, file);
		
		assertEquals(groupFile, savedGroupFile);		
	}
	
	@Test
	public void testGetGroupFile() {
		GroupFile groupFile = mock(GroupFile.class);
		byte[] bytes = "Mock".getBytes(); 
		doReturn(bytes).when(groupFile).getFile();
		doReturn("aName.png").when(groupFile).getName();
		doReturn(MediaType.IMAGE_PNG_VALUE).when(groupFile).getContentType();
		doReturn(groupFile).when(service).findGroupFile(A_GROUP_ID, A_FILE_ID, AN_USER_NAME);
				
		ResponseEntity<byte[]> response = this.controller.getGroupFile(A_TOKEN, A_GROUP_ID, A_FILE_ID);
		
		assertEquals(bytes, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(MediaType.IMAGE_PNG, response.getHeaders().getContentType());
	}
	
	@Test 
	public void testGetGroupFiles(){
		GroupFile aGroupFile = mock(GroupFile.class);
		GroupFile anotherGroupFile = mock(GroupFile.class);
		GroupFile yetAnotherGroupFile = mock(GroupFile.class);
		List<GroupFile> files = new ArrayList<GroupFile>();
		files.add(aGroupFile);
		files.add(anotherGroupFile);
		files.add(yetAnotherGroupFile);
		doReturn(files).when(service).getFiles(A_GROUP_ID, AN_USER_NAME);
		
		List<GroupFile> foundFiles = this.controller.getGroupFiles(A_TOKEN, A_GROUP_ID);
		
		assertEquals(files, foundFiles);
	}
}

