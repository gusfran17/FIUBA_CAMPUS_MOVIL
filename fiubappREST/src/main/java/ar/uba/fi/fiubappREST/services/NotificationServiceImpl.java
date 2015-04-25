package ar.uba.fi.fiubappREST.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.domain.Application;
import ar.uba.fi.fiubappREST.domain.ApplicationNotification;
import ar.uba.fi.fiubappREST.domain.Notification;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.NotificationNotFoundForStudentException;
import ar.uba.fi.fiubappREST.exceptions.NotificationNotViewedAlreadyExistsForStudentException;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.persistance.NotificationRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	private StudentRepository studentRepository;
	private NotificationRepository notificationRepository;
		
	@Autowired
	public NotificationServiceImpl(StudentRepository studentRepository, NotificationRepository notificationRepository){
		this.studentRepository = studentRepository;
		this.notificationRepository = notificationRepository;
	}

	@Override
	public Application createApplicationNotification(String userName, Application application) {
		Student student = this.findStudent(userName);
		Student applicantStudent = this.findStudent(application.getApplicantUserName());
		LOGGER.info(String.format("Creating application notification for student with userName %s from student with userName %s.", userName, application.getApplicantUserName()));
		this.verifyNotExistingNotViewedApplicationNotification(userName, application.getApplicantUserName());
		ApplicationNotification notification = createApplicationNotification(student, applicantStudent);		
		this.notificationRepository.save(notification);
		LOGGER.info(String.format("Application notification for student with userName %s from student with userName %s was created.", userName, application.getApplicantUserName()));
		return application;
	}

	private void verifyNotExistingNotViewedApplicationNotification(String userName, String applicantUserName) {
		List<ApplicationNotification> notifications = this.notificationRepository.findByUserNameAndApplicantUserNameAndNotIsViewed(userName, applicantUserName);
		if(notifications.size()>0){
			throw new NotificationNotViewedAlreadyExistsForStudentException(userName, applicantUserName);
		}
		
	}

	private ApplicationNotification createApplicationNotification(Student student, Student applicantStudent) {
		ApplicationNotification notification = new ApplicationNotification();
		notification.setCreationDate(new Date());
		notification.setIsViewed(false);
		notification.setStudent(student);
		notification.setApplicantUserName(applicantStudent.getUserName());
		notification.setApplicantName(applicantStudent.getName());
		notification.setApplicantLastName(applicantStudent.getLastName());
		return notification;
	}

	private Student findStudent(String userName) {
		LOGGER.info(String.format("Finding student with userName %s.", userName));
		Student student = this.studentRepository.findOne(userName);
		if(student==null){
			LOGGER.error(String.format("Student with userName %s was not found.", userName));
			throw new StudentNotFoundException(userName);
		}
		LOGGER.info(String.format("Student with userName %s was found.", userName));
		return student;
	}

	@Override
	public List<Notification> find(String userName, Boolean isViewed) {
		LOGGER.info(String.format("Finding notifications for student with userName %s.", userName));
		List<Notification> notifications = new ArrayList<Notification>();
		if(isViewed!=null){
			notifications = this.notificationRepository.findByUserNameAndIsViewed(userName, isViewed);
		} else{
			notifications = this.notificationRepository.findByUserName(userName);
		}
		LOGGER.info(String.format("Notifications for student with userName %s were found.", userName));
		return notifications;
	}

	@Override
	public Notification markAsViewed(String userName, Integer notificationId) {
		LOGGER.info(String.format("Marking notification with id %s for student with userName %s as viewed.", notificationId, userName));
		Notification notification = findNotificationForStudent(userName, notificationId);
		notification.setIsViewed(true);
		this.notificationRepository.save(notification);
		LOGGER.info(String.format("Notification with id %s for student with userName %s was marked as viewed.", notificationId, userName));		
		return notification;
	}

	private Notification findNotificationForStudent(String userName, Integer notificationId) {
		LOGGER.info(String.format("Finding notification with id %s for student with userName %s.", notificationId, userName));
		List<Notification> notifications = this.notificationRepository.findByIdAndUserName(notificationId, userName);
		if(notifications.size()!=1){
			LOGGER.error(String.format("Notification with id %s for student with userName %s was not found.", notificationId, userName));
			throw new NotificationNotFoundForStudentException(notificationId, userName);			
		}
		LOGGER.info(String.format("Notification with id %s for student with userName %s was found.", notificationId, userName));
		return notifications.get(0);
	}

	@Override
	public void delete(String userName, Integer notificationId) {
		LOGGER.info(String.format("Deleting notification with id %s for student with userName %s.", notificationId, userName));
		Notification notification = findNotificationForStudent(userName, notificationId);
		this.notificationRepository.delete(notification);
		LOGGER.info(String.format("Notification with id %s for student with userName %s was deleted.", notificationId, userName));
	}
	
}
