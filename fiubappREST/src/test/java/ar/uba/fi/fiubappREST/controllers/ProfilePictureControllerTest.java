package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import ar.uba.fi.fiubappREST.domain.ProfilePicture;
import ar.uba.fi.fiubappREST.services.ProfilePictureService;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

public class ProfilePictureControllerTest {
	
	private static final String AN_USER_NAME = "anUserName";
	private static final String A_TOKEN = "aToken";

	private ProfilePictureController controller;
	
	@Mock
	private ProfilePictureService service;
	@Mock
	private StudentSessionService studentSessionService;
		
	@Before
	public void setUp(){
		this.service = mock(ProfilePictureService.class);
		this.studentSessionService = mock(StudentSessionService.class);
		this.controller = new ProfilePictureController(service, studentSessionService);
	}

	@Test
	public void testUpdateProfilePicture() {
		doNothing().when(studentSessionService).validateMine(A_TOKEN, AN_USER_NAME);
		MultipartFile image = mock(MultipartFile.class);
		doNothing().when(service).update(AN_USER_NAME, image);
				
		this.controller.updateProfilePicture(A_TOKEN, AN_USER_NAME, image);
		
		assertTrue(true);		
	}
	
	@Test
	public void testGetPicture() {
		ProfilePicture picture = mock(ProfilePicture.class);
		byte[] bytes = "Mock".getBytes(); 
		doReturn(bytes).when(picture).getImage();
		doReturn(MediaType.IMAGE_PNG_VALUE).when(picture).getContentType();
		doReturn(picture).when(service).findByUserName(AN_USER_NAME);
				
		ResponseEntity<byte[]> response = this.controller.getProfilePicture(AN_USER_NAME);
		
		assertEquals(bytes, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(MediaType.IMAGE_PNG, response.getHeaders().getContentType());
	}
}

