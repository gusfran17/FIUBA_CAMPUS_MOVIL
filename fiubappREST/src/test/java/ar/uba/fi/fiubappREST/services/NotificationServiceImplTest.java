package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.Application;
import ar.uba.fi.fiubappREST.domain.ApplicationNotification;
import ar.uba.fi.fiubappREST.domain.Notification;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.NotificationNotFoundForStudentException;
import ar.uba.fi.fiubappREST.exceptions.NotificationNotViewedAlreadyExistsForStudentException;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.persistance.NotificationRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;

public class NotificationServiceImplTest {
	
	private static final int NOTIFICATION_ID = 3;
	private static final String AN_USER_NAME = "anUserName";
	private static final String AN_APPLICANT_USER_NAME = "anApplicantUserName";
	
	@Mock
	private NotificationRepository notificationRepository;
	@Mock
	private StudentRepository studentRepository;
	
	private Notification aNotification;
	private Notification anotherNotification;
		
	private Student student;
	private Student applicantStudent;
		
	private NotificationService service;
	
	@Before
	public void setUp() throws ParseException{
		this.notificationRepository = mock(NotificationRepository.class);
		this.studentRepository = mock(StudentRepository.class);		
		this.service= new NotificationServiceImpl(studentRepository, notificationRepository);

		this.student = new Student();
		this.student.setUserName(AN_USER_NAME);
		
		this.applicantStudent = new Student();
		this.applicantStudent.setUserName(AN_APPLICANT_USER_NAME);
		
		this.aNotification = new ApplicationNotification();
		this.anotherNotification = new ApplicationNotification();
		
		when(this.studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		when(this.studentRepository.findOne(AN_APPLICANT_USER_NAME)).thenReturn(applicantStudent);
	}
		
	@Test
	public void testCreateApplicationNotification() {
		when(notificationRepository.save(any(Notification.class))).thenReturn(aNotification);
		Application application = new Application();
		application.setApplicantUserName(AN_APPLICANT_USER_NAME);
		
		Application createdApplication = this.service.createApplicationNotification(AN_USER_NAME, application);
		
		assertEquals(createdApplication, application);
	}
	
	@Test(expected= StudentNotFoundException.class)
	public void testCreateApplicationNotificationWhenStudentNotFound() {
		when(this.studentRepository.findOne(AN_USER_NAME)).thenReturn(null);
		Application application = new Application();
		application.setApplicantUserName(AN_APPLICANT_USER_NAME);
		
		this.service.createApplicationNotification(AN_USER_NAME, application);
	}
	
	@Test(expected=NotificationNotViewedAlreadyExistsForStudentException.class)
	public void testCreateApplicationNotificationAlreadyExists() {
		Application application = new Application();
		application.setApplicantUserName(AN_APPLICANT_USER_NAME);
		List<ApplicationNotification> notifications = new ArrayList<ApplicationNotification>();
		notifications.add(new ApplicationNotification());
		when(notificationRepository.findByUserNameAndApplicantUserNameAndNotIsViewed(AN_USER_NAME, AN_APPLICANT_USER_NAME)).thenReturn(notifications);
		
		this.service.createApplicationNotification(AN_USER_NAME, application);
	}
	
	@Test
	public void testFind() {
		List<Notification> notifications = new ArrayList<Notification>();
		notifications.add(aNotification);
		notifications.add(anotherNotification);
		when(notificationRepository.findByUserName(AN_USER_NAME)).thenReturn(notifications);
				
		List<Notification> foundNotifications = this.service.find(AN_USER_NAME, null);
		
		assertEquals(notifications, foundNotifications);
	}
	
	@Test
	public void testFindViewed() {
		List<Notification> notifications = new ArrayList<Notification>();
		notifications.add(aNotification);
		notifications.add(anotherNotification);
		when(notificationRepository.findByUserNameAndIsViewed(AN_USER_NAME, true)).thenReturn(notifications);
				
		List<Notification> foundNotifications = this.service.find(AN_USER_NAME, true);
		
		assertEquals(notifications, foundNotifications);
	}
	
	@Test
	public void testMarkAsViewed() {
		aNotification.setId(NOTIFICATION_ID);
		aNotification.setIsViewed(false);
		List<Notification> notifications = new ArrayList<Notification>();
		notifications.add(aNotification);
		when(notificationRepository.findByIdAndUserName(NOTIFICATION_ID, AN_USER_NAME)).thenReturn(notifications);
		when(notificationRepository.save(aNotification)).thenReturn(aNotification);
				
		Notification updatedNotification = this.service.markAsViewed(AN_USER_NAME, NOTIFICATION_ID);
		
		assertEquals(aNotification, updatedNotification);
		assertTrue(updatedNotification.getIsViewed());
	}
	
	@Test(expected = NotificationNotFoundForStudentException.class)
	public void testMarkAsViewedNotFound() {
		List<Notification> notifications = new ArrayList<Notification>();
		when(notificationRepository.findByIdAndUserName(NOTIFICATION_ID, AN_USER_NAME)).thenReturn(notifications);
				
		this.service.markAsViewed(AN_USER_NAME, NOTIFICATION_ID);
	}
	
	@Test
	public void testDelete() {
		aNotification.setId(NOTIFICATION_ID);
		aNotification.setIsViewed(false);
		List<Notification> notifications = new ArrayList<Notification>();
		notifications.add(aNotification);
		when(notificationRepository.findByIdAndUserName(NOTIFICATION_ID, AN_USER_NAME)).thenReturn(notifications);
		doNothing().when(notificationRepository).delete(aNotification);
				
		this.service.delete(AN_USER_NAME, NOTIFICATION_ID);
		
		assertTrue(true);
		verify(notificationRepository).delete(aNotification);
	}
}


