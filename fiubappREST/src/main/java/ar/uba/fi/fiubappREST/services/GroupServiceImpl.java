package ar.uba.fi.fiubappREST.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ar.uba.fi.fiubappREST.converters.GroupConverter;
import ar.uba.fi.fiubappREST.domain.Discussion;
import ar.uba.fi.fiubappREST.domain.Group;
import ar.uba.fi.fiubappREST.domain.GroupPicture;
import ar.uba.fi.fiubappREST.domain.DiscussionMessage;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.DiscussionNotFoundInGroupException;
import ar.uba.fi.fiubappREST.exceptions.GroupAlreadyExistsException;
import ar.uba.fi.fiubappREST.exceptions.GroupNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.StudentIsNotMemberOfGroupException;
import ar.uba.fi.fiubappREST.exceptions.StudentNotCreatorOfGroupException;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.UnexpectedErrorReadingProfilePictureFileException;
import ar.uba.fi.fiubappREST.exceptions.UnsupportedMediaTypeForProfilePictureException;
import ar.uba.fi.fiubappREST.persistance.GroupPictureRepository;
import ar.uba.fi.fiubappREST.persistance.GroupRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.representations.GroupCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupUpdateRepresentation;

@Service
public class GroupServiceImpl implements GroupService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);
	
	private GroupRepository groupRepository;
	private StudentRepository studentRepository;
	private GroupPictureRepository groupPictureRepository;
	private GroupConverter groupConverter;
	
	@Value("classpath:defaultGroupPicture.png")
	private Resource defaultGroupPicture;
		
	@Autowired
	public GroupServiceImpl(GroupRepository groupRepository, StudentRepository studentRepository, GroupPictureRepository groupPictureRepository, GroupConverter groupConverter){
		this.groupRepository = groupRepository;
		this.studentRepository = studentRepository;
		this.groupPictureRepository = groupPictureRepository;
		this.groupConverter = groupConverter;
	}

	@Override
	public GroupRepresentation create(GroupCreationRepresentation groupRepresentation) {
		Student owner = this.findStudent(groupRepresentation.getOwnerUserName());
		this.verifyUnusedGroupName(groupRepresentation.getName());
		Group group = this.createGroup(groupRepresentation, owner);
		this.groupRepository.save(group);
		this.studentRepository.save(owner);
		this.createDefaultGroupImage(group);
		return this.groupConverter.convert(owner, group);
	}
	
	private void createDefaultGroupImage(Group group) {
		GroupPicture picture = new GroupPicture();
		picture.setGroup(group);
		picture.setContentType(MediaType.IMAGE_PNG_VALUE);
		byte[] image;
		try {
			image = IOUtils.toByteArray(this.defaultGroupPicture.getInputStream());
			picture.setImage(image);
		} catch (IOException e) {
			LOGGER.error(String.format("File %s for group picture for group with is %s was not read.", this.defaultGroupPicture.getFilename(), group.getId()));
			throw new UnexpectedErrorReadingProfilePictureFileException(this.defaultGroupPicture.getFilename());
		}
		this.groupPictureRepository.save(picture);
	}

	private Group createGroup(GroupCreationRepresentation groupRepresentation, Student owner) {
		Group group = new Group();
		group.setName(groupRepresentation.getName());
		group.setDescription(groupRepresentation.getDescription());
		group.setCreationDate(new Date());
		group.setMembers(new HashSet<Student>());
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
		Student student = this.studentRepository.findByUserNameAndFetchMatesAndGroupsEagerly(userName);
		if(student==null){
			LOGGER.error(String.format("Student with userName %s was not found.", userName));
			throw new StudentNotFoundException(userName); 
		}
		return student;
	}
	
	@Override
	public List<GroupRepresentation> findByProperties(String userName, String name) {
		LOGGER.info(String.format("Finding groups by criteria."));
		Student me = this.findStudent(userName);
		List<Group> groups = this.groupRepository.findByProperties(name);
		List<GroupRepresentation> groupRepresentations = new ArrayList<GroupRepresentation>();
		for (Group group : groups) {
			groupRepresentations.add(this.groupConverter.convert(me, group));
		}
		LOGGER.info(String.format("All groups meeting the criteria were found."));
		return groupRepresentations;
	}

	@Override
	public GroupRepresentation registerStudent(String userName, Integer groupId) {
		LOGGER.info(String.format("Registering student with userName %s to group with id %s.", userName, groupId ));
		Group group = this.groupRepository.findOne(groupId);
		if(group==null){
			LOGGER.error(String.format("Group with id %s does not exist.", userName, groupId ));
			throw new GroupNotFoundException(groupId);
		}
		Student student = this.findStudent(userName);
		group.addMember(student);
		this.groupRepository.save(group);
		this.studentRepository.save(student);
		GroupRepresentation representation = this.groupConverter.convert(student, group);
		LOGGER.info(String.format("Student with userName %s was registered to group with id %s.", userName, groupId ));
		return representation;
	}

	@Override
	public List<GroupRepresentation> getStudentGroups(String userName) {
		LOGGER.info(String.format("Finding all groups for student with userName %s.", userName));
		Student student = this.findStudent(userName);
		List<GroupRepresentation> groupRepresentations = new ArrayList<GroupRepresentation>();
		for (Group group : student.getGroups()) {
			groupRepresentations.add(this.groupConverter.convert(student, group));
		}
		LOGGER.info(String.format("All groups for student with userName %s were found.", userName));
		return groupRepresentations;
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

	@Override
	public GroupPicture getPicture(Integer groupId) {
		LOGGER.info(String.format("Finding picture for group with id %s.", groupId));
		GroupPicture picture = this.groupPictureRepository.findByGroupId(groupId);
		if(picture==null){
			LOGGER.error(String.format("Group with id %s does not exist.", groupId ));
			throw new GroupNotFoundException(groupId); 
		}		
		LOGGER.info(String.format("Picture for for group with id %s was found.", groupId));
		return picture;
	}

	@Override
	public void updatePicture(Integer groupId, MultipartFile image, String userName) {
		LOGGER.info(String.format("Saving file %s as group picture for group with id %s.", image.getName(), groupId));
		this.validateFileContentType(image);
		Group group = this.groupRepository.findOne(groupId);
		GroupPicture picture = this.groupPictureRepository.findByGroupId(groupId);
		if(group==null || picture==null){
			LOGGER.error(String.format("Group with id %s does not exist.", userName, groupId ));
			throw new GroupNotFoundException(groupId);
		}
		this.validateOwner(group, userName);
		picture.setGroup(group);
		picture.setContentType(image.getContentType());
		try {
			picture.setImage(image.getBytes());
		} catch (IOException e) {
			LOGGER.error(String.format("File %s for picture for group with id %s was not read.", image.getName(), groupId));
			throw new UnexpectedErrorReadingProfilePictureFileException(image.getName());
		}
		LOGGER.info(String.format("File %s was saved as group picture for group with id %s.", image.getName(), groupId));
		this.groupPictureRepository.save(picture);
	}

	private void validateOwner(Group group, String userName) {
		if(!group.getOwner().getUserName().equals(userName)){
			throw new StudentNotCreatorOfGroupException(userName, group.getName());
		}
	}

	private void validateFileContentType(MultipartFile image) {
		if(!image.getContentType().equals(MediaType.IMAGE_JPEG_VALUE) 
				&& !image.getContentType().equals(MediaType.IMAGE_PNG_VALUE)){
			LOGGER.error(String.format("Media Type %s of file %s is not allowed for profile picture.", image.getContentType(), image.getName()));
			throw new UnsupportedMediaTypeForProfilePictureException(image.getContentType());
		}		
	}

	@Override
	public GroupRepresentation updateGroup(Integer groupId,	GroupUpdateRepresentation groupRepresentation, String userName) {
		LOGGER.info(String.format("Updating group with id %s.", groupId ));
		Group group = this.groupRepository.findOne(groupId);
		if(group==null){
			LOGGER.error(String.format("Group with id %s does not exist.", userName, groupId ));
			throw new GroupNotFoundException(groupId);
		}
		this.validateOwner(group, userName);
		group.setName(groupRepresentation.getName());
		group.setDescription(groupRepresentation.getDescription());
		this.groupRepository.save(group);
		Student student = this.findStudent(userName);
		GroupRepresentation representation = this.groupConverter.convert(student, group);
		LOGGER.info(String.format("Group with id %s was updated.", groupId ));
		return representation;
	}
	
	public Resource getDefaultGroupPicture() {
		return defaultGroupPicture;
	}
	
	public void setDefaultGroupPicture(Resource defaultProfilePicture) {
		this.defaultGroupPicture = defaultProfilePicture;
	}
	
	@Override
	public Set<Discussion> findGroupDiscussionsForMember(Integer groupId, String userName) {
		verifyGroupMember(groupId, userName);
		LOGGER.info(String.format("Finding sicussions for groupId " + groupId + "."));
		Group group = this.groupRepository.findOne(groupId);
		if(group==null){
			LOGGER.error(String.format("Group with id %s does not exist.", groupId ));
			throw new GroupNotFoundException(groupId);
		}
		Set<Discussion> discussions = group.getDiscussions();
		
		LOGGER.info(String.format("All discussions for groupId "+ groupId + " were found."));
		return discussions;
	}

	private void verifyGroupMember(Integer groupId, String userName) {
		GroupRepresentation groupRepresentation = this.findGroupForStudent(groupId, userName);
		if (!groupRepresentation.getAmIAMember()){
			throw new StudentIsNotMemberOfGroupException(userName, groupId);
		}
		LOGGER.info(String.format(userName + " is a member of group " + groupId + "."));
	}
	
	@Override
	public Set<DiscussionMessage> findGroupDiscussionMessagesForMember(Integer groupId, Integer discussionId, String userName) {
		verifyGroupMember(groupId, userName);
		LOGGER.info(String.format("Finding discussions for groupId " + groupId + "."));
		Group group = this.groupRepository.findOne(groupId);
		if(group==null){
			LOGGER.error(String.format("Group with id %s does not exist.", userName, groupId ));
			throw new GroupNotFoundException(groupId);
		}
		Set <Discussion> discussions = group.getDiscussions();
		Iterator<Discussion> iterator = discussions.iterator();
		Discussion discussion = null;
		boolean found = false;
		while(iterator.hasNext()){
			discussion = iterator.next();
			if (discussion.getId()==discussionId) {
				found = true;
				break;
			}
		}
		if (found==false){
			LOGGER.error(String.format("Discussion with id %s does not exist in discussion %s.", groupId, discussionId ));
			throw new DiscussionNotFoundInGroupException(discussionId, groupId);	
		}
		LOGGER.info(String.format("Discussion " + discussionId + " was found for groupId "+ groupId + "."));
		return discussion.getMessages();
	}

}
