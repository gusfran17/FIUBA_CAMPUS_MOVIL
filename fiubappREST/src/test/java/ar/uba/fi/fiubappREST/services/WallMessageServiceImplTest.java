package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.WallMessage;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.WallMessageNotFoundForStudentException;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.persistance.WallMessageRepository;
import ar.uba.fi.fiubappREST.representations.WallMessageCreationRepresentation;

public class WallMessageServiceImplTest {
	
	private static final String A_MESSAGE = "aMessage";
	private static final String AN_USER_NAME = "anUserName";
	private static final String ANOTHER_USER_NAME = "anotherUserName";
	private static final String PETITIONER_USER_NAME = "petionerUserName";
	private static final Integer AN_ID = 12;
	
	@Mock
	private WallMessageRepository wallMessageRepository;
	@Mock
	private StudentRepository studentRepository;
			
	private Student student;
	private Student anotherStudent;
	private Student petionerStudent;
		
	private WallMessageService service;
	
	@Before
	public void setUp() throws ParseException{
		this.wallMessageRepository = mock(WallMessageRepository.class);
		this.studentRepository = mock(StudentRepository.class);		
		this.service= new WallMessageServiceImpl(studentRepository, wallMessageRepository);

		this.student = new Student();
		this.student.setUserName(AN_USER_NAME);
		
		this.anotherStudent = new Student();
		this.anotherStudent.setUserName(ANOTHER_USER_NAME);
		
		this.petionerStudent = new Student();
		this.petionerStudent.setUserName(PETITIONER_USER_NAME);
		
		when(this.studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		when(this.studentRepository.findOne(ANOTHER_USER_NAME)).thenReturn(anotherStudent);
		when(this.studentRepository.findOne(PETITIONER_USER_NAME)).thenReturn(petionerStudent);
	}
		
	@Test
	public void testCreate() {
		WallMessageCreationRepresentation representation = new WallMessageCreationRepresentation();
		representation.setCreatorUserName(ANOTHER_USER_NAME);
		representation.setIsPrivate(false);
		representation.setMessage(A_MESSAGE);
		WallMessage message = mock(WallMessage.class);		
		when(wallMessageRepository.save(any(WallMessage.class))).thenReturn(message);
				
		WallMessage createdWallMessage = this.service.create(AN_USER_NAME, representation);
		
		assertEquals(student, createdWallMessage.getStudent());
		assertEquals(anotherStudent, createdWallMessage.getCreator());
		assertFalse( createdWallMessage.getIsPrivate());
		assertEquals(A_MESSAGE, createdWallMessage.getMessage());
	}
	
	@Test(expected= StudentNotFoundException.class)
	public void testCreateApplicationNotificationWhenStudentNotFound() {
		when(this.studentRepository.findOne(AN_USER_NAME)).thenReturn(null);
		
		this.service.create(AN_USER_NAME, mock(WallMessageCreationRepresentation.class));
	}
	
	@Test
	public void testDelete() {
		WallMessage message = mock(WallMessage.class);
		List<WallMessage> messages = new ArrayList<WallMessage>();
		messages.add(message);
		when(this.wallMessageRepository.findByIdAndUserName(AN_ID, AN_USER_NAME)).thenReturn(messages);
		doNothing().when(wallMessageRepository).delete(message);
				
		this.service.delete(AN_USER_NAME, AN_ID);
		
		assertTrue(true);
		verify(wallMessageRepository).delete(message);
	}
	
	@Test(expected = WallMessageNotFoundForStudentException.class)
	public void testDeleteWallMessageNotFound() {
		when(this.wallMessageRepository.findByIdAndUserName(AN_ID, AN_USER_NAME)).thenReturn(new ArrayList<WallMessage>());
				
		this.service.delete(AN_USER_NAME, AN_ID);
	}
	
	@Test
	public void testFindForMate() {
		WallMessage studentPublicMessage = new WallMessage();
		studentPublicMessage.setCreator(student);
		studentPublicMessage.setStudent(student);
		studentPublicMessage.setIsPrivate(false);
		
		WallMessage petionerPublicMessage = new WallMessage();
		petionerPublicMessage.setCreator(petionerStudent);
		petionerPublicMessage.setStudent(student);
		petionerPublicMessage.setIsPrivate(false);
		
		WallMessage petionerPrivateMessage = new WallMessage();
		petionerPrivateMessage.setCreator(petionerStudent);
		petionerPrivateMessage.setStudent(student);
		petionerPrivateMessage.setIsPrivate(true);
		
		WallMessage anotherStudentPublicMessage = new WallMessage();
		anotherStudentPublicMessage.setCreator(anotherStudent);
		anotherStudentPublicMessage.setStudent(student);
		anotherStudentPublicMessage.setIsPrivate(false);
		
		WallMessage anotherStudentPrivateMessage = new WallMessage();
		anotherStudentPrivateMessage.setCreator(anotherStudent);
		anotherStudentPrivateMessage.setStudent(student);
		anotherStudentPrivateMessage.setIsPrivate(true);
		
		
		Set<WallMessage> messages = new HashSet<WallMessage>();
		messages.add(studentPublicMessage);
		messages.add(petionerPublicMessage);
		messages.add(petionerPrivateMessage);
		messages.add(anotherStudentPublicMessage);
		messages.add(anotherStudentPrivateMessage);
		
		when(this.wallMessageRepository.findByUserName(AN_USER_NAME)).thenReturn(messages);
				
		Set<WallMessage> foundMessages = this.service.find(AN_USER_NAME, PETITIONER_USER_NAME);
		
		assertEquals(4, foundMessages.size());
		assertTrue(foundMessages.contains(studentPublicMessage));
		assertTrue(foundMessages.contains(petionerPublicMessage));
		assertTrue(foundMessages.contains(petionerPrivateMessage));
		assertTrue(foundMessages.contains(anotherStudentPublicMessage));
		assertFalse(foundMessages.contains(anotherStudentPrivateMessage));
	}
	
	@Test
	public void testFindForMe() {
		WallMessage studentPublicMessage = new WallMessage();
		studentPublicMessage.setCreator(student);
		studentPublicMessage.setStudent(student);
		studentPublicMessage.setIsPrivate(false);
		
		WallMessage petionerPublicMessage = new WallMessage();
		petionerPublicMessage.setCreator(petionerStudent);
		petionerPublicMessage.setStudent(student);
		petionerPublicMessage.setIsPrivate(false);
		
		WallMessage petionerPrivateMessage = new WallMessage();
		petionerPrivateMessage.setCreator(petionerStudent);
		petionerPrivateMessage.setStudent(student);
		petionerPrivateMessage.setIsPrivate(true);
		
		WallMessage anotherStudentPublicMessage = new WallMessage();
		anotherStudentPublicMessage.setCreator(anotherStudent);
		anotherStudentPublicMessage.setStudent(student);
		anotherStudentPublicMessage.setIsPrivate(false);
		
		WallMessage anotherStudentPrivateMessage = new WallMessage();
		anotherStudentPrivateMessage.setCreator(anotherStudent);
		anotherStudentPrivateMessage.setStudent(student);
		anotherStudentPrivateMessage.setIsPrivate(true);
		
		
		Set<WallMessage> messages = new HashSet<WallMessage>();
		messages.add(studentPublicMessage);
		messages.add(petionerPublicMessage);
		messages.add(petionerPrivateMessage);
		messages.add(anotherStudentPublicMessage);
		messages.add(anotherStudentPrivateMessage);
		
		when(this.wallMessageRepository.findByUserName(AN_USER_NAME)).thenReturn(messages);
				
		Set<WallMessage> foundMessages = this.service.find(AN_USER_NAME, AN_USER_NAME);
		
		assertEquals(5, foundMessages.size());
		assertTrue(foundMessages.contains(studentPublicMessage));
		assertTrue(foundMessages.contains(petionerPublicMessage));
		assertTrue(foundMessages.contains(petionerPrivateMessage));
		assertTrue(foundMessages.contains(anotherStudentPublicMessage));
		assertTrue(foundMessages.contains(anotherStudentPrivateMessage));
	}
}



