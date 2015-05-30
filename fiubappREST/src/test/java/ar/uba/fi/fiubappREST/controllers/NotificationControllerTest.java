package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.ApplicationNotification;
import ar.uba.fi.fiubappREST.domain.Notification;
import ar.uba.fi.fiubappREST.services.NotificationService;
import ar.uba.fi.fiubappREST.services.SessionService;

public class NotificationControllerTest {
	
	private static final int NOTIFICATION_ID = 4;
	private static final String AN_USER_NAME = "anUserName";
	private static final String A_TOKEN = "aToken";

	private NotificationController controller;
	
	@Mock
	private NotificationService service;
	@Mock
	private SessionService studentSessionService;
	@Mock
	private Notification aNotification;
	@Mock
	private Notification anotherNotification;
	
	@Before
	public void setUp(){
		this.service = mock(NotificationService.class);
		this.studentSessionService = mock(SessionService.class);
		this.controller = new NotificationController(service, studentSessionService);
		
		this.aNotification = mock (Notification.class);
		this.anotherNotification = mock (Notification.class);
	}

	@Test
	public void testGetNotifications() {
		doNothing().when(studentSessionService).validateThisStudent(A_TOKEN, AN_USER_NAME);
		List<Notification> notifications = new ArrayList<Notification>();
		notifications.add(aNotification);
		notifications.add(anotherNotification);
		when(service.find(AN_USER_NAME, null)).thenReturn(notifications);
				
		List<Notification> foundNotifications = this.controller.getNotifications(A_TOKEN, AN_USER_NAME, null);
		
		assertEquals(notifications, foundNotifications);		
	}
	
	@Test
	public void testMarkNotificationAsViewed() {
		doNothing().when(studentSessionService).validateThisStudent(A_TOKEN, AN_USER_NAME);
		Notification viewedNotification = new ApplicationNotification();
		viewedNotification.setId(NOTIFICATION_ID);
		viewedNotification.setIsViewed(true);
		when(service.markAsViewed(AN_USER_NAME, NOTIFICATION_ID)).thenReturn(viewedNotification);
				
		Notification notification = this.controller.markNotificationAsViewed(A_TOKEN, AN_USER_NAME, NOTIFICATION_ID);
		
		assertEquals(viewedNotification, notification);
		assertTrue(notification.getIsViewed());
	}
	
	@Test
	public void testDeleteNotification() {
		doNothing().when(studentSessionService).validateThisStudent(A_TOKEN, AN_USER_NAME);
		doNothing().when(service).delete(AN_USER_NAME, NOTIFICATION_ID);
				
		this.controller.deleteNotification(A_TOKEN, AN_USER_NAME, NOTIFICATION_ID);
		
		assertTrue(true);
		verify(this.service).delete(AN_USER_NAME, NOTIFICATION_ID);
	}

}


