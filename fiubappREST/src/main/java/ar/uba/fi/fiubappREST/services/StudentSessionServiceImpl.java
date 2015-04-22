package ar.uba.fi.fiubappREST.services;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.domain.Credentials;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.StudentSession;
import ar.uba.fi.fiubappREST.exceptions.InvalidCredentialsException;
import ar.uba.fi.fiubappREST.exceptions.OperationNotAllowedFotStudentSessionException;
import ar.uba.fi.fiubappREST.exceptions.StudentSessionNotFoundException;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.persistance.StudentSessionRepository;

@Service
public class StudentSessionServiceImpl implements StudentSessionService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentSessionServiceImpl.class);
	
	private StudentSessionRepository studentSessionRepository;
	private StudentRepository studentRepository;
	private Md5PasswordEncoder passwordEncoder;
	
	@Autowired
	public StudentSessionServiceImpl(StudentSessionRepository studentSessionRepository, StudentRepository studentRepository, Md5PasswordEncoder passwordEncoder){
		this.studentSessionRepository = studentSessionRepository;
		this.studentRepository = studentRepository;
		this.passwordEncoder = passwordEncoder;	
	}

	@Override
	public StudentSession create(Credentials credentials) {
		LOGGER.info(String.format("Creating session for student with userName %s.", credentials.getUserName()));
		Student student = this.getStudent(credentials);
		this.verifyPassword(student.getUserName(), student.getPassword(), credentials.getPassword());
		StudentSession session = generateSession(student);
		session = this.studentSessionRepository.save(session);
		LOGGER.info(String.format("Session for student with userName %s was created.", student.getUserName()));
		return session;
	}

	private StudentSession generateSession(Student student) {
		StudentSession session = new StudentSession();
		session.setUserName(student.getUserName());
		session.setCreationDate(new Date());
		session.setToken(UUID.randomUUID().toString());
		return session;
	}

	private void verifyPassword(String userName, String encryptedPassword, String rawPassword) {
		LOGGER.info(String.format("Verifyig password for student with userName %s.", userName));
		if(!this.passwordEncoder.isPasswordValid(encryptedPassword, rawPassword, null)){
			LOGGER.info(String.format("Password for student with userName %s isn't valid.", userName));
			throw new InvalidCredentialsException();
		}
		LOGGER.info(String.format("Password for student with userName %s is valid.", userName));
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
	public StudentSession find(String token) {
		LOGGER.info(String.format("Finding session for token %s.", token));
		StudentSession session = this.studentSessionRepository.findByToken(token);
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

	@Override
	public void validateMine(String token, String userName) {
		StudentSession studentSession = this.find(token);
		LOGGER.info(String.format("Validating token for userName %s.", userName));
		if(!this.isMyInformation(userName, studentSession.getUserName())){
			LOGGER.error(String.format("Token not valid for userName %s.", userName));
			throw new OperationNotAllowedFotStudentSessionException(token);
		}
		LOGGER.info(String.format("Token valid for userName %s.", userName));
	}

	@Override
	public void validateMineOrMate(String token, String userName) {
		StudentSession studentSession = this.find(token);
		if(!this.isMyInformation(userName, studentSession.getUserName()) && !this.isMyMateInformation(userName, studentSession.getUserName())){
			LOGGER.error(String.format("Token not valid for userName %s.", userName));
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
}
