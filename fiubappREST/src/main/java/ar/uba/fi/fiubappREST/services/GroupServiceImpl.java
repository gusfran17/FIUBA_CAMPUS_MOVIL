package ar.uba.fi.fiubappREST.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.converters.GroupConverter;
import ar.uba.fi.fiubappREST.domain.Group;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.GroupAlreadyExistsException;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.persistance.GroupRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.representations.GroupCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupRepresentation;

@Service
public class GroupServiceImpl implements GroupService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);
	
	private GroupRepository groupRepository;
	private StudentRepository studentRepository;
	private GroupConverter groupConverter;
		
	@Autowired
	public GroupServiceImpl(GroupRepository groupRepository, StudentRepository studentRepository, GroupConverter groupConverter){
		this.groupRepository = groupRepository;
		this.studentRepository = studentRepository;
		this.groupConverter = groupConverter;
	}

	@Override
	public GroupRepresentation create(GroupCreationRepresentation groupRepresentation) {
		Student owner = this.findStudent(groupRepresentation.getOwnerUserName());
		this.verifyUnusedGroupName(groupRepresentation.getName());
		Group group = this.createGroup(groupRepresentation, owner);
		this.groupRepository.save(group);
		this.studentRepository.save(owner);
		return this.groupConverter.convert(this.findStudentWithMates(groupRepresentation.getOwnerUserName()), group);
	}

	private Group createGroup(GroupCreationRepresentation groupRepresentation, Student owner) {
		Group group = new Group();
		group.setName(groupRepresentation.getName());
		group.setDescription(groupRepresentation.getDescription());
		group.setCreationDate(new Date());
		group.setMembers(new ArrayList<Student>());
		group.setOwner(owner);
		group.addMember(owner);
		return group;
	}

	private void verifyUnusedGroupName(String name) {
		List<Group> groups = this.groupRepository.findByName(name);
		if(groups.size()!=0){
			throw new GroupAlreadyExistsException(name);
		}
	}

	private Student findStudent(String userName) {
		Student student = this.studentRepository.findByUserNameAndFetchGroupsEagerly(userName);
		if(student==null){
			LOGGER.error(String.format("Student with userName %s was not found.", userName));
			throw new StudentNotFoundException(userName); 
		}
		return student;
	}
	
	private Student findStudentWithMates(String userName) {
		Student student = this.studentRepository.findByUserNameAndFetchMatesEagerly(userName);
		if(student==null){
			LOGGER.error(String.format("Student with userName %s was not found.", userName));
			throw new StudentNotFoundException(userName); 
		}
		return student;
	}
}
