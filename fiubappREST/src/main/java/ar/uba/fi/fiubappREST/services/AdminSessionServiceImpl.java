package ar.uba.fi.fiubappREST.services;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.domain.Admin;
import ar.uba.fi.fiubappREST.domain.AdminSession;
import ar.uba.fi.fiubappREST.domain.Credentials;
import ar.uba.fi.fiubappREST.exceptions.InvalidCredentialsException;
import ar.uba.fi.fiubappREST.exceptions.StudentSessionNotFoundException;
import ar.uba.fi.fiubappREST.persistance.AdminRepository;
import ar.uba.fi.fiubappREST.persistance.AdminSessionRepository;

@Service
public class AdminSessionServiceImpl implements AdminSessionService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminSessionServiceImpl.class);
	
	private AdminSessionRepository adminSessionRepository;
	private AdminRepository adminRepository;
	private Md5PasswordEncoder passwordEncoder;
	
	@Autowired
	public AdminSessionServiceImpl(AdminSessionRepository adminSessionRepository, AdminRepository adminRepository, Md5PasswordEncoder passwordEncoder){
		this.adminSessionRepository = adminSessionRepository;
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;	
	}

	@Override
	public AdminSession create(Credentials credentials) {
		LOGGER.info(String.format("Creating session for admin with userName %s.", credentials.getUserName()));
		Admin admin = this.getAdmin(credentials);
		this.verifyPassword(admin.getUserName(), admin.getPassword(), credentials.getPassword());
		AdminSession session = generateSession(admin);
		session = this.adminSessionRepository.save(session);
		LOGGER.info(String.format("Session for admin with userName %s was created.", admin.getUserName()));
		return session;
	}

	private AdminSession generateSession(Admin admin) {
		AdminSession session = new AdminSession();
		session.setUserName(admin.getUserName());
		session.setCreationDate(new Date());
		session.setToken(UUID.randomUUID().toString());
		return session;
	}

	private void verifyPassword(String userName, String encryptedPassword, String rawPassword) {
		LOGGER.info(String.format("Verifyig password for admin with userName %s.", userName));
		if(!this.passwordEncoder.isPasswordValid(encryptedPassword, rawPassword, null)){
			LOGGER.info(String.format("Password for admin with userName %s isn't valid.", userName));
			throw new InvalidCredentialsException();
		}
		LOGGER.info(String.format("Password for student with userName %s is valid.", userName));
	}

	private Admin getAdmin(Credentials credentials) {
		LOGGER.info(String.format("Finding admin with userName %s.", credentials.getUserName()));
		Admin admin = this.adminRepository.findOne(credentials.getUserName());
		if(admin == null){
			LOGGER.error(String.format("Admin with userName %s was not found.", credentials.getUserName()));
			throw new InvalidCredentialsException();
		}
		LOGGER.info(String.format("Admin with userName %s was found.", credentials.getUserName()));
		return admin;
	}

	@Override
	public AdminSession find(String token) {
		LOGGER.info(String.format("Finding session for token %s.", token));
		AdminSession session = this.adminSessionRepository.findByToken(token);
		if(session == null){
			LOGGER.info(String.format("Session for token %s was not found.", token));
			throw new StudentSessionNotFoundException(token);
		}
		LOGGER.info(String.format("Session for token %s was found.", token));
		return session;
	}

	@Override
	public void validate(String token) {
		this.find(token);		
	}
}
