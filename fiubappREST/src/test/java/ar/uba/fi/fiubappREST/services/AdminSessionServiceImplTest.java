package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;

import ar.uba.fi.fiubappREST.domain.Admin;
import ar.uba.fi.fiubappREST.domain.AdminSession;
import ar.uba.fi.fiubappREST.domain.Credentials;
import ar.uba.fi.fiubappREST.exceptions.InvalidCredentialsException;
import ar.uba.fi.fiubappREST.exceptions.StudentSessionNotFoundException;
import ar.uba.fi.fiubappREST.persistance.AdminRepository;
import ar.uba.fi.fiubappREST.persistance.AdminSessionRepository;

public class AdminSessionServiceImplTest {
	
	private static final String A_TOKEN = "A_TOKEN";
	private static final String AN_USER_NAME = "AN_USER_NAME";
	private static final String A_RAW_PASSWORD = "A_RAW_PASSWORD";
	private static final String AN_ENCRYPTED_PASSWORD = "AN_ENCRYPTED_PASSWORD";
	
	@Mock
	private AdminRepository adminRepository;
	@Mock
	private AdminSessionRepository adminSessionRepository;
	@Mock
	private Md5PasswordEncoder passwordEncoder;
	@Mock
	private Admin admin;
	@Mock
	private AdminSession session;
	private Credentials credentials;
			
	private AdminSessionService service;
	
		
	@Before
	public void setUp(){
		this.adminSessionRepository = mock(AdminSessionRepository.class);
		this.adminRepository = mock(AdminRepository.class);
		this.passwordEncoder = mock(Md5PasswordEncoder.class);
		
		this.service = new AdminSessionServiceImpl(adminSessionRepository, adminRepository, passwordEncoder);
		
		this.admin = mock(Admin.class);
		this.session = mock(AdminSession.class);
		
		this.credentials = new Credentials();
		this.credentials.setUserName(AN_USER_NAME);
		this.credentials.setPassword(A_RAW_PASSWORD);
	}

	@Test
	public void testCreateOK() {
		when(admin.getUserName()).thenReturn(AN_USER_NAME);
		when(admin.getPassword()).thenReturn(AN_ENCRYPTED_PASSWORD);
		when(adminRepository.findOne(AN_USER_NAME)).thenReturn(admin);
		when(passwordEncoder.isPasswordValid(AN_ENCRYPTED_PASSWORD, A_RAW_PASSWORD, null)).thenReturn(true);
		when(adminSessionRepository.save(any(AdminSession.class))).thenReturn(session);
	
		AdminSession createdSesion = this.service.create(credentials);
				
		assertNotNull(createdSesion);
	}
	
	@Test(expected=InvalidCredentialsException.class)
	public void testCreateInvalidUserName() {
		when(admin.getUserName()).thenReturn(AN_USER_NAME);
		when(adminRepository.findOne(AN_USER_NAME)).thenReturn(null);
	
		this.service.create(credentials);
	}
	
	@Test(expected=InvalidCredentialsException.class)
	public void testCreateInvalidPassword() {
		when(admin.getUserName()).thenReturn(AN_USER_NAME);
		when(admin.getPassword()).thenReturn(AN_ENCRYPTED_PASSWORD);
		when(adminRepository.findOne(AN_USER_NAME)).thenReturn(admin);
		when(passwordEncoder.isPasswordValid(AN_ENCRYPTED_PASSWORD, A_RAW_PASSWORD, null)).thenReturn(false);
	
		this.service.create(credentials);
	}
	
	@Test
	public void testFindOK() {
		when(adminSessionRepository.findByToken(A_TOKEN)).thenReturn(session);
	
		AdminSession foundSesion = this.service.find(A_TOKEN);
				
		assertNotNull(foundSesion);
	}
	
	@Test(expected=StudentSessionNotFoundException.class)
	public void testFindInvalidToken() {
		when(adminSessionRepository.findByToken(A_TOKEN)).thenReturn(null);
	
		this.service.find(A_TOKEN);
	}
	
	@Test
	public void testValidateOK() {
		when(adminSessionRepository.findByToken(A_TOKEN)).thenReturn(session);
	
		this.service.validate(A_TOKEN);
				
		assertTrue(true);
	}
	
	@Test(expected=StudentSessionNotFoundException.class)
	public void testValidateInvalidToken() {
		when(adminSessionRepository.findByToken(A_TOKEN)).thenReturn(null);
	
		this.service.validate(A_TOKEN);
	}
		
	@Test
	public void testDelete() {
		when(adminSessionRepository.findByToken(A_TOKEN)).thenReturn(session);
		doNothing().when(adminSessionRepository).delete(session);
	
		this.service.delete(A_TOKEN);
				
		assertTrue(true);
	}

}
