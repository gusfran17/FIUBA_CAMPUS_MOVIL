package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;

import ar.uba.fi.fiubappREST.domain.Admin;
import ar.uba.fi.fiubappREST.domain.Credentials;
import ar.uba.fi.fiubappREST.domain.Session;
import ar.uba.fi.fiubappREST.domain.SessionRole;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.InvalidCredentialsException;
import ar.uba.fi.fiubappREST.exceptions.OperationNotAllowedFotStudentSessionException;
import ar.uba.fi.fiubappREST.exceptions.SessionNotFoundException;
import ar.uba.fi.fiubappREST.persistance.AdminRepository;
import ar.uba.fi.fiubappREST.persistance.SessionRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;

public class SessionServiceImplTest {
	
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
	private AdminRepository adminRepository;
	@Mock
	private SessionRepository sessionRepository;
	@Mock
	private Md5PasswordEncoder passwordEncoder;
	@Mock
	private Student student;
	@Mock
	private Admin admin;
	@Mock
	private Session session;
	private Credentials credentials;
			
	private SessionService service;
	
		
	@Before
	public void setUp(){
		this.sessionRepository = mock(SessionRepository.class);
		this.studentRepository = mock(StudentRepository.class);
		this.adminRepository = mock(AdminRepository.class);
		this.passwordEncoder = mock(Md5PasswordEncoder.class);
		
		this.service = new SessionServiceImpl(sessionRepository, studentRepository, adminRepository, passwordEncoder);
		
		this.student = mock(Student.class);
		this.session = mock(Session.class);
		
		this.credentials = new Credentials();
		this.credentials.setUserName(AN_USER_NAME);
		this.credentials.setPassword(A_RAW_PASSWORD);
		this.credentials.setIsExchangeStudent(false);
		
		this.admin = mock(Admin.class);
	}

	@Test
	public void testCreateStudentSessionOK() {
		when(student.getUserName()).thenReturn(AN_USER_NAME);
		when(student.getPassword()).thenReturn(AN_ENCRYPTED_PASSWORD);
		when(student.getIsExchangeStudent()).thenReturn(false);
		when(studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		when(passwordEncoder.isPasswordValid(AN_ENCRYPTED_PASSWORD, A_RAW_PASSWORD, null)).thenReturn(true);
		when(sessionRepository.save(any(Session.class))).thenReturn(session);
	
		Session createdSesion = this.service.createStudentStudent(credentials);
				
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
		when(sessionRepository.save(any(Session.class))).thenReturn(session);
	
		Session createdSesion = this.service.createStudentStudent(credentials);
				
		assertNotNull(createdSesion);
	}
	
	@Test(expected=InvalidCredentialsException.class)
	public void testCreateInvalidUserNameForStudent() {
		when(student.getUserName()).thenReturn(AN_USER_NAME);
		when(studentRepository.findOne(AN_USER_NAME)).thenReturn(null);
	
		this.service.createStudentStudent(credentials);
	}
	
	@Test(expected=InvalidCredentialsException.class)
	public void testCreateInvalidPasswordForStudent() {
		when(student.getUserName()).thenReturn(AN_USER_NAME);
		when(student.getPassword()).thenReturn(AN_ENCRYPTED_PASSWORD);
		when(studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		when(passwordEncoder.isPasswordValid(AN_ENCRYPTED_PASSWORD, A_RAW_PASSWORD, null)).thenReturn(false);
	
		this.service.createStudentStudent(credentials);
	}
	
	@Test
	public void testFindOKForStudent() {
		when(sessionRepository.findByTokenAndRole(A_TOKEN, SessionRole.STUDENT)).thenReturn(session);
	
		Session foundSesion = this.service.findStudentSession(A_TOKEN);
				
		assertNotNull(foundSesion);
	}
	
	@Test(expected=SessionNotFoundException.class)
	public void testFindInvalidTokenForStudent() {
		when(sessionRepository.findByTokenAndRole(A_TOKEN, SessionRole.STUDENT)).thenReturn(null);
	
		this.service.findStudentSession(A_TOKEN);
	}
	
	@Test
	public void testValidateOKForStudent() {
		when(sessionRepository.findByTokenAndRole(A_TOKEN, SessionRole.STUDENT)).thenReturn(session);
	
		this.service.validateStudentSession(A_TOKEN);
				
		assertTrue(true);
	}
	
	@Test(expected=SessionNotFoundException.class)
	public void testValidateInvalidTokenForStudent() {
		when(sessionRepository.findByTokenAndRole(A_TOKEN, SessionRole.STUDENT)).thenReturn(null);
	
		this.service.validateStudentSession(A_TOKEN);
	}
	
	@Test
	public void testValidateThisStudentOK() {
		when(sessionRepository.findByTokenAndRole(A_TOKEN, SessionRole.STUDENT)).thenReturn(session);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
	
		this.service.validateThisStudent(A_TOKEN, AN_USER_NAME);
				
		assertTrue(true);
	}
	
	@Test(expected=OperationNotAllowedFotStudentSessionException.class)
	public void testValidateThisStudentInvalidUser() {
		when(sessionRepository.findByTokenAndRole(A_TOKEN, SessionRole.STUDENT)).thenReturn(session);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
	
		this.service.validateThisStudent(A_TOKEN, ANOTHER_USER_NAME);
	}
	
	@Test
	public void testValidateThisStudentOrMateForMeOK() {
		when(sessionRepository.findByTokenAndRole(A_TOKEN, SessionRole.STUDENT)).thenReturn(session);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
	
		this.service.validateThisStudentOrMate(A_TOKEN, AN_USER_NAME);
				
		assertTrue(true);
	}
	
	@Test(expected=OperationNotAllowedFotStudentSessionException.class)
	public void testValidateThisStudentOrMateForMeError() {
		when(sessionRepository.findByTokenAndRole(A_TOKEN, SessionRole.STUDENT)).thenReturn(session);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
	
		this.service.validateThisStudentOrMate(A_TOKEN, ANOTHER_USER_NAME);
	}
	
	@Test
	public void testValidateThisStudentOrMateForMateOK() {
		when(sessionRepository.findByTokenAndRole(A_TOKEN, SessionRole.STUDENT)).thenReturn(session);
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
	
		this.service.validateThisStudentOrMate(A_TOKEN, A_MATE_USER_NAME);
				
		assertTrue(true);
	}
	
	@Test(expected=OperationNotAllowedFotStudentSessionException.class)
	public void testValidateThisStudentOrMateForMateError() {
		when(sessionRepository.findByTokenAndRole(A_TOKEN, SessionRole.STUDENT)).thenReturn(session);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
		Student me = new Student();
		me.setUserName(AN_USER_NAME);
		when(studentRepository.findOne(AN_USER_NAME)).thenReturn(me);
		Student mate = new Student();
		mate.setUserName(A_MATE_USER_NAME);
		List<Student> mates = new ArrayList<Student>();
		mate.setMates(mates);
		when(studentRepository.findByUserNameAndFetchMatesEagerly(A_MATE_USER_NAME)).thenReturn(mate);
	
		this.service.validateThisStudentOrMate(A_TOKEN, A_MATE_USER_NAME);
	}
	
	
	
	@Test
	public void testCreateOKForAdmin() {
		when(admin.getUserName()).thenReturn(AN_USER_NAME);
		when(admin.getPassword()).thenReturn(AN_ENCRYPTED_PASSWORD);
		when(adminRepository.findOne(AN_USER_NAME)).thenReturn(admin);
		when(passwordEncoder.isPasswordValid(AN_ENCRYPTED_PASSWORD, A_RAW_PASSWORD, null)).thenReturn(true);
		when(sessionRepository.save(any(Session.class))).thenReturn(session);
	
		Session createdSesion = this.service.createAdminSession(credentials);
				
		assertNotNull(createdSesion);
	}
	
	@Test(expected=InvalidCredentialsException.class)
	public void testCreateInvalidUserNameForAdmin() {
		when(admin.getUserName()).thenReturn(AN_USER_NAME);
		when(adminRepository.findOne(AN_USER_NAME)).thenReturn(null);
	
		this.service.createAdminSession(credentials);
	}
	
	@Test(expected=InvalidCredentialsException.class)
	public void testCreateInvalidPasswordForAdmin() {
		when(admin.getUserName()).thenReturn(AN_USER_NAME);
		when(admin.getPassword()).thenReturn(AN_ENCRYPTED_PASSWORD);
		when(adminRepository.findOne(AN_USER_NAME)).thenReturn(admin);
		when(passwordEncoder.isPasswordValid(AN_ENCRYPTED_PASSWORD, A_RAW_PASSWORD, null)).thenReturn(false);
	
		this.service.createAdminSession(credentials);
	}
	
	@Test
	public void testFindOKForAdmin() {
		when(sessionRepository.findByTokenAndRole(A_TOKEN, SessionRole.ADMIN)).thenReturn(session);
	
		Session foundSesion = this.service.findAdminSession(A_TOKEN);
				
		assertNotNull(foundSesion);
	}
	
	@Test(expected=SessionNotFoundException.class)
	public void testFindInvalidTokenForAdmin() {
		when(sessionRepository.findByTokenAndRole(A_TOKEN, SessionRole.ADMIN)).thenReturn(null);
	
		this.service.findAdminSession(A_TOKEN);
	}
	
	@Test
	public void testValidateOKForAdmin() {
		when(sessionRepository.findByTokenAndRole(A_TOKEN, SessionRole.ADMIN)).thenReturn(session);
	
		this.service.validateAdminSession(A_TOKEN);
				
		assertTrue(true);
	}
	
	@Test(expected=SessionNotFoundException.class)
	public void testValidateInvalidTokenForAdmin() {
		when(sessionRepository.findByTokenAndRole(A_TOKEN, SessionRole.ADMIN)).thenReturn(null);
	
		this.service.validateAdminSession(A_TOKEN);
	}
		
	@Test
	public void testDeleteForAdmin() {
		when(sessionRepository.findByTokenAndRole(A_TOKEN, SessionRole.ADMIN)).thenReturn(session);
		doNothing().when(sessionRepository).delete(session);
	
		this.service.deleteAdminSession(A_TOKEN);
				
		assertTrue(true);
	}

}
