package ar.uba.fi.fiubappREST.services;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

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

@Service
public class SessionServiceImpl implements SessionService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionServiceImpl.class);
	
	private SessionRepository sessionRepository;
	private StudentRepository studentRepository;
	private AdminRepository adminRepository;
	private Md5PasswordEncoder passwordEncoder;
	
	@Autowired
	public SessionServiceImpl(SessionRepository sessionRepository, StudentRepository studentRepository, AdminRepository adminRepository, Md5PasswordEncoder passwordEncoder){
		this.sessionRepository = sessionRepository;
		this.studentRepository = studentRepository;
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;	
	}
	
	@Override
	public Session findSession(String token) {
		LOGGER.info(String.format("Finding session for token %s.", token));
		Session session = this.sessionRepository.findByToken(token);
		if(session == null){
			LOGGER.info(String.format("Session for token %s was not found.", token));
			throw new SessionNotFoundException(token);
		}
		LOGGER.info(String.format("Session for token %s was found.", token));
		return session;
	}

	@Override
	public Session createStudentStudent(Credentials credentials) {
		LOGGER.info(String.format("Creating session for student with userName %s.", credentials.getUserName()));
		Student student = this.getStudent(credentials);
		this.verifyPassword(student.getUserName(), student.getPassword(), credentials.getPassword());
		Session session = generateStudentSession(student);
		session = this.sessionRepository.save(session);
		LOGGER.info(String.format("Session for student with userName %s was created.", student.getUserName()));
		return session;
	}

	private Session generateStudentSession(Student student) {
		Session session = new Session();
		session.setUserName(student.getUserName());
		session.setCreationDate(new Date());
		session.setToken(UUID.randomUUID().toString());
		session.setRole(SessionRole.STUDENT);
		return session;
	}

	private void verifyPassword(String userName, String encryptedPassword, String rawPassword) {
		LOGGER.info(String.format("Verifyig password for userName %s.", userName));
		if(!this.passwordEncoder.isPasswordValid(encryptedPassword, rawPassword, null)){
			LOGGER.info(String.format("Password for userName %s isn't valid.", userName));
			throw new InvalidCredentialsException();
		}
		LOGGER.info(String.format("Password for userName %s is valid.", userName));
	}

	private Student getStudent(Credentials credentials) {
		String userName = (credentials.getIsExchangeStudent()) ? "I".concat(credentials.getUserName()) : credentials.getUserName();
		LOGGER.info(String.format("Finding student with userName %s.", userName));
		Student student = this.studentRepository.findOne(userName);
		if(student == null){
			LOGGER.error(String.format("Student with userName %s was not found.", userName));
			throw new InvalidCredentialsException();
		}
		LOGGER.info(String.format("Student with userName %s was found.", userName));
		return student;
	}

	@Override
	public Session findStudentSession(String token) {
		LOGGER.info(String.format("Finding session for token %s.", token));
		Session session = this.sessionRepository.findByTokenAndRole(token, SessionRole.STUDENT);
		if(session == null){
			LOGGER.info(String.format("Session for token %s was not found.", token));
			throw new SessionNotFoundException(token);
		}
		LOGGER.info(String.format("Session for token %s was found.", token));
		return session;
	}

	@Override
	public void validateStudentSession(String token) {
		this.findStudentSession(token);		
	}

	@Override
	public void validateThisStudent(String token, String userName) {
		Session studentSession = this.findStudentSession(token);
		LOGGER.info(String.format("Validating token for student with userName %s.", userName));
		if(!this.isMyInformation(userName, studentSession.getUserName())){
			LOGGER.error(String.format("Token not valid for studetn with userName %s.", userName));
			throw new OperationNotAllowedFotStudentSessionException(token);
		}
		LOGGER.info(String.format("Token valid for userName %s.", userName));
	}

	@Override
	public void validateThisStudentOrMate(String token, String userName) {
		Session studentSession = this.findStudentSession(token);
		if(!this.isMyInformation(userName, studentSession.getUserName()) && !this.isMyMateInformation(userName, studentSession.getUserName())){
			LOGGER.error(String.format("Token not valid for student with userName %s.", userName));
			throw new OperationNotAllowedFotStudentSessionException(token);
		}		
	}

	private boolean isMyInformation(String userName, String sessionUserName) {		
		return userName.equals(sessionUserName);
	}

	private boolean isMyMateInformation(String userName, String sessionUserName) {
		Student mate = this.studentRepository.findByUserNameAndFetchMatesEagerly(userName);
		if(mate==null){
			return false;
		}
		Student me = this.studentRepository.findOne(sessionUserName);
		return mate.isMateWith(me);
	}
	
	
	

	@Override
	public Session createAdminSession(Credentials credentials) {
		LOGGER.info(String.format("Creating session for admin with userName %s.", credentials.getUserName()));
		Admin admin = this.getAdmin(credentials);
		this.verifyPassword(admin.getUserName(), admin.getPassword(), credentials.getPassword());
		Session session = generateAdminSession(admin);
		session = this.sessionRepository.save(session);
		LOGGER.info(String.format("Session for admin with userName %s was created.", admin.getUserName()));
		return session;
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
	
	private Session generateAdminSession(Admin admin) {
		Session session = new Session();
		session.setUserName(admin.getUserName());
		session.setCreationDate(new Date());
		session.setToken(UUID.randomUUID().toString());
		session.setRole(SessionRole.ADMIN);
		return session;
	}
	
	@Override
	public Session findAdminSession(String token) {
		LOGGER.info(String.format("Finding admin session for token %s.", token));
		Session session = this.sessionRepository.findByTokenAndRole(token, SessionRole.ADMIN);
		if(session == null){
			LOGGER.info(String.format("Admin session for token %s was not found.", token));
			throw new SessionNotFoundException(token);
		}
		LOGGER.info(String.format("Admin session for token %s was found.", token));
		return session;
	}

	@Override
	public void validateAdminSession(String token){
		this.findAdminSession(token);
	}

	@Override
	public void deleteAdminSession(String token) {
		LOGGER.info(String.format("Deleting session for token %s.", token));
		Session session = this.findAdminSession(token);
		this.sessionRepository.delete(session);
		LOGGER.info(String.format("Admin session for token %s was deleted.", token));
	}
}
