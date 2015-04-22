package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;

import ar.uba.fi.fiubappREST.domain.Credentials;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.StudentSession;
import ar.uba.fi.fiubappREST.exceptions.InvalidCredentialsException;
import ar.uba.fi.fiubappREST.exceptions.OperationNotAllowedFotStudentSessionException;
import ar.uba.fi.fiubappREST.exceptions.StudentSessionNotFoundException;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.persistance.StudentSessionRepository;

public class StudentSessionServiceImplTest {
	
	private static final String A_TOKEN = "A_TOKEN";
	private static final String AN_USER_NAME = "AN_USER_NAME";
	private static final String AN_EXCHANGE_STUDENT_USER_NAME = "I" + AN_USER_NAME;
	private static final String A_MATE_USER_NAME = "A_MATE_USER_NAME";
	private static final String A_RAW_PASSWORD = "A_RAW_PASSWORD";
	private static final String AN_ENCRYPTED_PASSWORD = "AN_ENCRYPTED_PASSWORD";
	private static final String ANOTHER_USER_NAME = "ANOTHER_USER_NAME";
	@Mock
	private StudentRepository studentRepository;
	@Mock
	private StudentSessionRepository studentSessionRepository;
	@Mock
	private Md5PasswordEncoder passwordEncoder;
	@Mock
	private Student student;
	@Mock
	private StudentSession session;
	private Credentials credentials;
			
	private StudentSessionService service;
	
		
	@Before
	public void setUp(){
		this.studentSessionRepository = mock(StudentSessionRepository.class);
		this.studentRepository = mock(StudentRepository.class);
		this.passwordEncoder = mock(Md5PasswordEncoder.class);
		
		this.service = new StudentSessionServiceImpl(studentSessionRepository, studentRepository, passwordEncoder);
		
		this.student = mock(Student.class);
		this.session = mock(StudentSession.class);
		
		this.credentials = new Credentials();
		this.credentials.setUserName(AN_USER_NAME);
		this.credentials.setPassword(A_RAW_PASSWORD);
		this.credentials.setIsExchangeStudent(false);
	}

	@Test
	public void testCreateOK() {
		when(student.getUserName()).thenReturn(AN_USER_NAME);
		when(student.getPassword()).thenReturn(AN_ENCRYPTED_PASSWORD);
		when(student.getIsExchangeStudent()).thenReturn(false);
		when(studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		when(passwordEncoder.isPasswordValid(AN_ENCRYPTED_PASSWORD, A_RAW_PASSWORD, null)).thenReturn(true);
		when(studentSessionRepository.save(any(StudentSession.class))).thenReturn(session);
	
		StudentSession createdSesion = this.service.create(credentials);
				
		assertNotNull(createdSesion);
	}
	
	@Test
	public void testCreateOKForExchangeStudent() {
		this.credentials.setIsExchangeStudent(true);
		when(student.getUserName()).thenReturn(AN_EXCHANGE_STUDENT_USER_NAME);
		when(student.getPassword()).thenReturn(AN_ENCRYPTED_PASSWORD);
		when(student.getIsExchangeStudent()).thenReturn(true);
		when(studentRepository.findOne(AN_EXCHANGE_STUDENT_USER_NAME)).thenReturn(student);
		when(passwordEncoder.isPasswordValid(AN_ENCRYPTED_PASSWORD, A_RAW_PASSWORD, null)).thenReturn(true);
		when(studentSessionRepository.save(any(StudentSession.class))).thenReturn(session);
	
		StudentSession createdSesion = this.service.create(credentials);
				
		assertNotNull(createdSesion);
	}
	
	@Test(expected=InvalidCredentialsException.class)
	public void testCreateInvalidUserName() {
		when(student.getUserName()).thenReturn(AN_USER_NAME);
		when(studentRepository.findOne(AN_USER_NAME)).thenReturn(null);
	
		this.service.create(credentials);
	}
	
	@Test(expected=InvalidCredentialsException.class)
	public void testCreateInvalidPassword() {
		when(student.getUserName()).thenReturn(AN_USER_NAME);
		when(student.getPassword()).thenReturn(AN_ENCRYPTED_PASSWORD);
		when(studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		when(passwordEncoder.isPasswordValid(AN_ENCRYPTED_PASSWORD, A_RAW_PASSWORD, null)).thenReturn(false);
	
		this.service.create(credentials);
	}
	
	@Test
	public void testFindOK() {
		when(studentSessionRepository.findByToken(A_TOKEN)).thenReturn(session);
	
		StudentSession foundSesion = this.service.find(A_TOKEN);
				
		assertNotNull(foundSesion);
	}
	
	@Test(expected=StudentSessionNotFoundException.class)
	public void testFindInvalidToken() {
		when(studentSessionRepository.findByToken(A_TOKEN)).thenReturn(null);
	
		this.service.find(A_TOKEN);
	}
	
	@Test
	public void testValidateOK() {
		when(studentSessionRepository.findByToken(A_TOKEN)).thenReturn(session);
	
		this.service.validate(A_TOKEN);
				
		assertTrue(true);
	}
	
	@Test(expected=StudentSessionNotFoundException.class)
	public void testValidateInvalidToken() {
		when(studentSessionRepository.findByToken(A_TOKEN)).thenReturn(null);
	
		this.service.validate(A_TOKEN);
	}
	
	@Test
	public void testValidateMineOK() {
		when(studentSessionRepository.findByToken(A_TOKEN)).thenReturn(session);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
	
		this.service.validateMine(A_TOKEN, AN_USER_NAME);
				
		assertTrue(true);
	}
	
	@Test(expected=OperationNotAllowedFotStudentSessionException.class)
	public void testValidateMineInvalidUser() {
		when(studentSessionRepository.findByToken(A_TOKEN)).thenReturn(session);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
	
		this.service.validateMine(A_TOKEN, ANOTHER_USER_NAME);
	}
	
	@Test
	public void testValidateMineOrMateForMeOK() {
		when(studentSessionRepository.findByToken(A_TOKEN)).thenReturn(session);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
	
		this.service.validateMineOrMate(A_TOKEN, AN_USER_NAME);
				
		assertTrue(true);
	}
	
	@Test(expected=OperationNotAllowedFotStudentSessionException.class)
	public void testValidateMineOrMateForMeError() {
		when(studentSessionRepository.findByToken(A_TOKEN)).thenReturn(session);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
	
		this.service.validateMineOrMate(A_TOKEN, ANOTHER_USER_NAME);
	}
	
	@Test
	public void testValidateMineOrMateForMateOK() {
		when(studentSessionRepository.findByToken(A_TOKEN)).thenReturn(session);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
		Student me = new Student();
		me.setUserName(AN_USER_NAME);
		when(studentRepository.findOne(AN_USER_NAME)).thenReturn(me);
		Student mate = new Student();
		mate.setUserName(A_MATE_USER_NAME);
		List<Student> mates = new ArrayList<Student>();
		mates.add(me);
		mate.setMates(mates);
		when(studentRepository.findByUserNameAndFetchMatesEagerly(A_MATE_USER_NAME)).thenReturn(mate);
	
		this.service.validateMineOrMate(A_TOKEN, A_MATE_USER_NAME);
				
		assertTrue(true);
	}
	
	@Test(expected=OperationNotAllowedFotStudentSessionException.class)
	public void testValidateMineOrMateForMateError() {
		when(studentSessionRepository.findByToken(A_TOKEN)).thenReturn(session);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
		Student me = new Student();
		me.setUserName(AN_USER_NAME);
		when(studentRepository.findOne(AN_USER_NAME)).thenReturn(me);
		Student mate = new Student();
		mate.setUserName(A_MATE_USER_NAME);
		List<Student> mates = new ArrayList<Student>();
		mate.setMates(mates);
		when(studentRepository.findByUserNameAndFetchMatesEagerly(A_MATE_USER_NAME)).thenReturn(mate);
	
		this.service.validateMineOrMate(A_TOKEN, A_MATE_USER_NAME);
	}

}
