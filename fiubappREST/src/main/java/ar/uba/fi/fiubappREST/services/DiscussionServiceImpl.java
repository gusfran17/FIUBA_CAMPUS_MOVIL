package ar.uba.fi.fiubappREST.services;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.uba.fi.fiubappREST.converters.DiscussionConverter;
import ar.uba.fi.fiubappREST.converters.GroupConverter;
import ar.uba.fi.fiubappREST.domain.Discussion;
import ar.uba.fi.fiubappREST.domain.Group;
import ar.uba.fi.fiubappREST.domain.DiscussionMessage;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.DiscussionNotFoundInGroupException;
import ar.uba.fi.fiubappREST.exceptions.GroupNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.StudentIsNotMemberOfGroupException;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.persistance.DiscussionRepository;
import ar.uba.fi.fiubappREST.persistance.GroupRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.representations.DiscussionCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionRepresentation;
import ar.uba.fi.fiubappREST.representations.DiscussionMessageCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupRepresentation;

@Service
public class DiscussionServiceImpl implements DiscussionService{

	private static final Logger LOGGER = LoggerFactory.getLogger(DiscussionServiceImpl.class);
	
	private DiscussionRepository discussionRepository;
	private GroupRepository groupRepository;
	private StudentRepository studentRepository;
	private DiscussionConverter discussionConverter;
	private GroupConverter groupConverter;
	
	@Autowired
	public DiscussionServiceImpl(DiscussionRepository discussionRepository, GroupRepository groupRepository, StudentRepository studentRepository, DiscussionConverter discussionConverter, GroupConverter groupConverter){
		this.discussionRepository = discussionRepository;
		this.groupRepository = groupRepository;
		this.studentRepository = studentRepository;
		this.discussionConverter = discussionConverter;
		this.groupConverter = groupConverter;
	}

	@Override
	public DiscussionRepresentation create(DiscussionCreationRepresentation discussionRepresentation, Integer groupID) {
		Student creator = this.findStudent(discussionRepresentation.getCreatorUserName());
		Group group = this.findGroup(groupID);
		Discussion discussion = this.createDiscussion(discussionRepresentation, creator, group);
		this.groupRepository.save(group);
		return this.discussionConverter.convert(discussion);
	}

	private Discussion createDiscussion(DiscussionCreationRepresentation discussionRepresentation, Student creator, Group group) {
		Discussion discussion = new Discussion(discussionRepresentation.getDiscussionName(), creator, discussionRepresentation.getFirstMessage());
		this.discussionRepository.save(discussion);
		group.addDiscussion(discussion);
		return discussion;
		
	}

	private Group findGroup(Integer groupID) {
		Group group = groupRepository.findOne(groupID);
		if (group == null){
			LOGGER.error(String.format("Group with ID %s was not found.", groupID));
			throw new GroupNotFoundException();
		}
		return group;
	}

	private Student findStudent(String userName) {
		Student student = this.studentRepository.findOne(userName);
		if(student==null){
			LOGGER.error(String.format("Student with userName %s was not found.", userName));
			throw new StudentNotFoundException(userName); 
		}
		return student;
	}

	@Override
	public DiscussionMessage createMessage(DiscussionMessageCreationRepresentation messageRepresentation, Integer groupId, Integer discussionId) {
		Group group = this.findGroup(groupId);
		Student student = this.findStudent(messageRepresentation.getCreatorUserName());
		Discussion discussion = findDiscussion(groupId, discussionId, group);
		
		DiscussionMessage message = new DiscussionMessage();
		message.setCreationDate(new Date());
		message.setCreator(student);
		message.setMessage(messageRepresentation.getMessage());
		discussion.addMessage(message);
		
		discussionRepository.save(discussion);
		groupRepository.save(group);
		return message;
	}

	private Discussion findDiscussion(Integer groupId, Integer discussionId, Group group) {
		Set <Discussion> discussions = group.getDiscussions();
		Iterator<Discussion> iterator = discussions.iterator();
		Discussion discussion = null;
		boolean found = false;
		while(iterator.hasNext()){
			discussion = iterator.next();
			if (discussion.getId()==discussionId) found = true;
		}
		if (found==false){
			LOGGER.error(String.format("Discussion with id %s does not exist in discussion %s.", groupId, discussionId ));
			throw new DiscussionNotFoundInGroupException(discussionId, groupId);	
		}
		return discussion;
	}
	
	@Override
	public Set<DiscussionRepresentation> findGroupDiscussionsForMember(Integer groupId, String userName) {
		//verifyGroupMember(groupId, userName);
		LOGGER.info(String.format("Finding sicussions for groupId " + groupId + "."));
		Group group = this.groupRepository.findOne(groupId);
		if(group==null){
			LOGGER.error(String.format("Group with id %s does not exist.", groupId ));
			throw new GroupNotFoundException(groupId);
		}
		Set<Discussion> discussions = group.getDiscussions();
		Set<DiscussionRepresentation> discussionsRepresentation = new HashSet<DiscussionRepresentation>();
		Iterator<Discussion> iterator = discussions.iterator();
		Discussion discussion = new Discussion();
		while (iterator.hasNext()){
			discussion = iterator.next();
			discussionsRepresentation.add(discussionConverter.convert(discussion));
		}
		
		
		LOGGER.info(String.format("All discussions for groupId "+ groupId + " were found."));
		return discussionsRepresentation;
	}
	
	private void verifyGroupMember(Integer groupId, String userName) {
		GroupRepresentation groupRepresentation = this.findGroupForStudent(groupId, userName);
		if (!groupRepresentation.getAmIAMember()){
			throw new StudentIsNotMemberOfGroupException(userName, groupId);
		}
		LOGGER.info(String.format(userName + " is a member of group " + groupId + "."));
	}
	
	@Override
	public GroupRepresentation findGroupForStudent(Integer groupId, String userName) {
		LOGGER.info(String.format("Finding group with id %s for student with userName %s.", groupId, userName));
		Group group = this.groupRepository.findOne(groupId);
		if(group==null){
			LOGGER.error(String.format("Group with id %s does not exist.", userName, groupId ));
			throw new GroupNotFoundException(groupId);
		}
		Student student = this.findStudent(userName);
		GroupRepresentation representation = this.groupConverter.convert(student, group);
		LOGGER.info(String.format("Group with id %s was found for student with userName %s.", groupId, userName));
		return representation;
	}

}
