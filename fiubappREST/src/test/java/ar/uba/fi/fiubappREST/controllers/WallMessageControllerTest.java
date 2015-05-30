package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.Session;
import ar.uba.fi.fiubappREST.domain.WallMessage;
import ar.uba.fi.fiubappREST.representations.WallMessageCreationRepresentation;
import ar.uba.fi.fiubappREST.services.SessionService;
import ar.uba.fi.fiubappREST.services.WallMessageService;

public class WallMessageControllerTest {
	
	private static final String A_WALL_MESSAGE = "aWallMessage";
	private static final String AN_USER_NAME = "anUserName";
	private static final String WRITER_USER_NAME = "aWriterUserName";
	private static final String PETITIONER_USER_NAME = "aPetitionerUserName";
	private static final String A_TOKEN = "aToken";
	private static final Integer AN_ID = 12;

	private WallMessageController controller;
	
	@Mock
	private WallMessageService service;
	@Mock
	private SessionService sessionService;
		
	@Before
	public void setUp(){
		this.service = mock(WallMessageService.class);
		this.sessionService = mock(SessionService.class);
		this.controller = new WallMessageController(service, sessionService);
	}

	@Test
	public void testCreateWallMessage() {
		WallMessageCreationRepresentation representation = new WallMessageCreationRepresentation();
		representation.setCreatorUserName(WRITER_USER_NAME);
		representation.setIsPrivate(false);
		representation.setMessage(A_WALL_MESSAGE);
		doNothing().when(sessionService).validateThisStudentOrMate(A_TOKEN, WRITER_USER_NAME);
		WallMessage message = mock (WallMessage.class);
		when(this.service.create(AN_USER_NAME, representation)).thenReturn(message);
						
		WallMessage createdWallMessage = this.controller.createWallMessage(A_TOKEN, AN_USER_NAME, representation);
		
		assertEquals(message, createdWallMessage);		
	}
	
	@Test
	public void testGetWallMessage() {
		doNothing().when(sessionService).validateThisStudentOrMate(A_TOKEN, PETITIONER_USER_NAME);
		Session session = mock(Session.class);
		when(session.getUserName()).thenReturn(PETITIONER_USER_NAME);
		when(this.sessionService.findStudentSession(A_TOKEN)).thenReturn(session);
		WallMessage aMessage = mock (WallMessage.class);
		WallMessage anotherMessage = mock (WallMessage.class);
		WallMessage yetAnotherMessage = mock (WallMessage.class);
		Set<WallMessage> messages = new HashSet<WallMessage>();
		messages.add(aMessage);
		messages.add(anotherMessage);
		messages.add(yetAnotherMessage);
		when(this.service.find(AN_USER_NAME, PETITIONER_USER_NAME)).thenReturn(messages);
		
		Set<WallMessage> foundMessages = this.controller.getWallMessages(A_TOKEN, AN_USER_NAME);
		
		assertEquals(messages, foundMessages);		
	}
	
	@Test
	public void testDeleteWallMessage() {
		doNothing().when(sessionService).validateThisStudent(A_TOKEN, AN_USER_NAME);
		doNothing().when(this.service).delete(AN_USER_NAME, AN_ID);
			
		this.controller.deleteMessage(A_TOKEN, AN_USER_NAME, AN_ID);
		
		assertTrue(true);		
	}

}

