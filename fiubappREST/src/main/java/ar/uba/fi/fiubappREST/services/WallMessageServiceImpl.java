package ar.uba.fi.fiubappREST.services;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.WallMessage;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.WallMessageNotFoundForStudentException;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.persistance.WallMessageRepository;
import ar.uba.fi.fiubappREST.representations.WallMessageCreationRepresentation;

@Service
public class WallMessageServiceImpl implements WallMessageService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WallMessageServiceImpl.class);
	
	private StudentRepository studentRepository;
	private WallMessageRepository wallMessageRepository;
		
	@Autowired
	public WallMessageServiceImpl(StudentRepository studentRepository, WallMessageRepository wallMessageRepository){
		this.studentRepository = studentRepository;
		this.wallMessageRepository = wallMessageRepository;
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
	public WallMessage create(String userName, WallMessageCreationRepresentation message) {
		Student student = this.findStudent(userName);
		Student creatorStudent = this.findStudent(message.getCreatorUserName());
		LOGGER.info(String.format("Creating wall message for student with userName %s from student with userName %s.", userName, message.getCreatorUserName()));
		WallMessage wallMessage = createWallMessage(message, student, creatorStudent);		
		this.wallMessageRepository.save(wallMessage);
		LOGGER.info(String.format("Wall message for student with userName %s from student with userName %s was created.", userName, message.getCreatorUserName()));
		return wallMessage;
	}

	private WallMessage createWallMessage(WallMessageCreationRepresentation message, Student student, Student creatorStudent) {
		WallMessage wallMessage = new WallMessage();
		wallMessage.setStudent(student);
		wallMessage.setCreator(creatorStudent);
		wallMessage.setMessage(message.getMessage());
		wallMessage.setIsPrivate(message.getIsPrivate());
		wallMessage.setCreationDate(new Date());
		return wallMessage;
	}

	@Override
	public Set<WallMessage> find(final String ownerUserName, final String petitionerUserName) {
		LOGGER.info(String.format("Finding wall messages of student with userName %s for student with userName %s.", ownerUserName, petitionerUserName));
		Set<WallMessage> messages = this.wallMessageRepository.findByUserName(ownerUserName);
		CollectionUtils.filter(messages, new Predicate() {			
			@Override
			public boolean evaluate(Object o) {
				WallMessage message = (WallMessage)o;
				return !message.getIsPrivate() || message.getStudent().equals(ownerUserName) || message.getCreator().getUserName().equals(petitionerUserName);
			}
		});				
		LOGGER.info(String.format("Wall messages of student with userName %s for student with userNames %s were found.", ownerUserName, petitionerUserName));
		return messages;
	}

	@Override
	public void delete(String userName, Integer id) {
		LOGGER.info(String.format("Deleting wall message with id %s for student with userName %s.", id, userName));
		WallMessage message = getWallMessageForStudent(userName, id);
		this.wallMessageRepository.delete(message);
		LOGGER.info(String.format("Wall message with id %s for student with userName %s was deleted.", id, userName));
		
	}

	private WallMessage getWallMessageForStudent(String userName, Integer id) {
		LOGGER.info(String.format("Finding wall message with id %s for student with userName %s.", id, userName));
		List<WallMessage> messages = this.wallMessageRepository.findByIdAndUserName(id, userName);
		if(messages.size()!=1){
			LOGGER.error(String.format("Wall message with id %s for student with userName %s was not found.", id, userName));
			throw new WallMessageNotFoundForStudentException(id, userName);			
		}
		LOGGER.info(String.format("Wall message with id %s for student with userName %s was found.", id, userName));
		return messages.get(0);
	}
	
}
