package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;

import ar.uba.fi.fiubappREST.domain.Credentials;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.StudentSession;
import ar.uba.fi.fiubappREST.exceptions.InvalidCredentialsException;
import ar.uba.fi.fiubappREST.exceptions.StudentSessionNotFoundException;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.persistance.StudentSessionRepository;

public class StudentSessionServiceImplTest {
	
	private static final String A_TOKEN = "A_TOKEN";
	private static final String AN_USER_NAME = "AN_USER_NAME";
	private static final String A_RAW_PASSWORD = "A_RAW_PASSWORD";
	private static final String AN_ENCRYPTED_PASSWORD = "AN_ENCRYPTED_PASSWORD";
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
		when(studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
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

}
