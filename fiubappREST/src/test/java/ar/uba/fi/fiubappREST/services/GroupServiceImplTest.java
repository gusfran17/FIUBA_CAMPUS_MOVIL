package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import ar.uba.fi.fiubappREST.converters.DiscussionConverter;
import ar.uba.fi.fiubappREST.converters.GroupConverter;
import ar.uba.fi.fiubappREST.converters.StudentProfileConverter;
import ar.uba.fi.fiubappREST.domain.Group;
import ar.uba.fi.fiubappREST.domain.GroupPicture;
import ar.uba.fi.fiubappREST.domain.GroupState;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.GroupAlreadyExistsException;
import ar.uba.fi.fiubappREST.exceptions.GroupNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.StudentNotCreatorOfGroupException;
import ar.uba.fi.fiubappREST.exceptions.UnexpectedErrorReadingProfilePictureFileException;
import ar.uba.fi.fiubappREST.exceptions.UnsupportedMediaTypeForProfilePictureException;
import ar.uba.fi.fiubappREST.persistance.GroupPictureRepository;
import ar.uba.fi.fiubappREST.persistance.GroupRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.representations.GroupCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupUpdateRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;

public class GroupServiceImplTest {
	
	private static final String A_NAME = "aName";
	private static final String A_DESCRIPTION = "aDescription";
	private static final String AN_USER_NAME = "anUserName";
	private static final String ANOTHER_USER_NAME = "anotherUserName";
	private static final Integer A_GROUP_ID = 12;
	
	@Mock
	private StudentRepository studentRepository;
	@Mock
	private GroupRepository groupRepository;
	@Mock
	private GroupPictureRepository groupPictureRepository;
	@Mock
	private GroupConverter converter;
	@Mock
	private DiscussionConverter discussionConverter;
	@Mock
	private GroupRepresentation representation;
	@Mock
	private StudentProfileConverter studentProfileConverter; 
	
	
	private Student student;
	private Group group;
		
	private GroupServiceImpl service;
	
	@Before
	public void setUp() throws FileNotFoundException, IOException {
		this.studentRepository = mock(StudentRepository.class);
		this.groupRepository = mock(GroupRepository.class);
		this.groupPictureRepository = mock(GroupPictureRepository.class);
		this.converter = mock(GroupConverter.class);
		this.discussionConverter = mock(DiscussionConverter.class);
		this.studentProfileConverter = mock(StudentProfileConverter.class);
		this.service= new GroupServiceImpl(groupRepository, studentRepository, groupPictureRepository, null, converter, discussionConverter, studentProfileConverter);
		this.student = new Student();
		this.student.setUserName(AN_USER_NAME);
		Set<Group> groups = new HashSet<Group>();
		this.student.setGroups(groups);
		
		this.group = new Group();
		this.group.setMembers(new HashSet<Student>());
		
		this.representation = mock(GroupRepresentation.class);
		
		Resource resource = mock(Resource.class);
		when(resource.getInputStream()).thenReturn(new FileInputStream("src/test/resources/defaultGroupPicture.png"));
		this.service.setDefaultGroupPicture(resource);
	}
		
	@Test
	public void testAddGroup(){
		when(this.studentRepository.findByUserNameAndFetchMatesAndGroupsEagerly(AN_USER_NAME)).thenReturn(student);
		when(this.studentRepository.save(student)).thenReturn(student);
		when(this.groupRepository.save(Mockito.any(Group.class))).thenReturn(group);
		when(this.converter.convert(Mockito.any(Student.class), Mockito.any(Group.class))).thenReturn(representation);
		GroupCreationRepresentation creationRepresentation = new GroupCreationRepresentation();
		creationRepresentation.setDescription(A_DESCRIPTION);
		creationRepresentation.setName(A_NAME);
		creationRepresentation.setOwnerUserName(AN_USER_NAME);
		
		GroupRepresentation createdGroup = this.service.create(creationRepresentation);
		
		assertNotNull(createdGroup);
	}
	
	@Test(expected=GroupAlreadyExistsException.class)
	public void testAddGroupAlreadyExists(){
		when(this.studentRepository.findByUserNameAndFetchMatesAndGroupsEagerly(AN_USER_NAME)).thenReturn(student);
		List<Group> groups = new ArrayList<Group>();
		groups.add(new Group());
		when(this.groupRepository.findByName(A_NAME)).thenReturn(groups);
		GroupCreationRepresentation creationRepresentation = new GroupCreationRepresentation();
		creationRepresentation.setDescription(A_DESCRIPTION);
		creationRepresentation.setName(A_NAME);
		creationRepresentation.setOwnerUserName(AN_USER_NAME);
		
		this.service.create(creationRepresentation);
	}
	
	@Test
	public void findByProperties(){
		when(this.studentRepository.findByUserNameAndFetchMatesAndGroupsEagerly(AN_USER_NAME)).thenReturn(student);
		List<Group> groups = new ArrayList<Group>();
		Group aGroup = mock(Group.class);
		when(aGroup.getState()).thenReturn(GroupState.ENABLED);
		Group anotherGroup = mock(Group.class);
		when(anotherGroup.getState()).thenReturn(GroupState.ENABLED);
		groups.add(aGroup);
		groups.add(anotherGroup);
		when(this.groupRepository.findByProperties(A_NAME, GroupState.ENABLED.getId())).thenReturn(groups);
		GroupRepresentation aRepresentation = mock(GroupRepresentation.class);
		GroupRepresentation anotherRepresentation = mock(GroupRepresentation.class);
		when(this.converter.convert(student, aGroup)).thenReturn(aRepresentation);
		when(this.converter.convert(student, anotherGroup)).thenReturn(anotherRepresentation);
		
		List<GroupRepresentation> representations = this.service.findByPropertiesForStudent(AN_USER_NAME, A_NAME);
		
		assertEquals(2, representations.size());
	}
	
	@Test
	public void registerStudent(){
		when(this.studentRepository.findByUserNameAndFetchMatesAndGroupsEagerly(AN_USER_NAME)).thenReturn(student);
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(group);
		when(this.studentRepository.save(student)).thenReturn(student);
		when(this.groupRepository.save(Mockito.any(Group.class))).thenReturn(group);
		GroupRepresentation aRepresentation = mock(GroupRepresentation.class);
		when(this.converter.convert(student, group)).thenReturn(aRepresentation);
		
		GroupRepresentation registeredGroup = this.service.registerStudent(AN_USER_NAME, A_GROUP_ID);
		
		assertEquals(aRepresentation, registeredGroup);
	}
	
	@Test(expected= GroupNotFoundException.class)
	public void registerStudentGroupNotFound(){
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(null);
		
		this.service.registerStudent(AN_USER_NAME, A_GROUP_ID);
	}
	
	@Test
	public void testGetStudentGroups(){
		when(this.studentRepository.findByUserNameAndFetchMatesAndGroupsEagerly(AN_USER_NAME)).thenReturn(student);
		Group aGroup = mock(Group.class);
		when(aGroup.getState()).thenReturn(GroupState.ENABLED);
		Group anotherGroup = mock(Group.class);
		when(anotherGroup.getState()).thenReturn(GroupState.ENABLED);
		Set<Group> groups = new HashSet<Group>();
		groups.add(aGroup);
		groups.add(anotherGroup);
		this.student.setGroups(groups);
		GroupRepresentation aRepresentation = mock(GroupRepresentation.class);
		when(this.converter.convert(student, aGroup)).thenReturn(aRepresentation);
		GroupRepresentation anotherRepresentation = mock(GroupRepresentation.class);
		when(this.converter.convert(student, anotherGroup)).thenReturn(anotherRepresentation);
		
		List<GroupRepresentation> representations = this.service.getStudentGroups(AN_USER_NAME);
		
		assertTrue(representations.contains(aRepresentation));
		assertTrue(representations.contains(anotherRepresentation));
	}
	
	@Test
	public void testGetGroupForStudent(){
		when(this.studentRepository.findByUserNameAndFetchMatesAndGroupsEagerly(AN_USER_NAME)).thenReturn(student);
		Group aGroup = mock(Group.class);
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(aGroup);
		GroupRepresentation aRepresentation = mock(GroupRepresentation.class);
		when(this.converter.convert(student, aGroup)).thenReturn(aRepresentation);
		
		GroupRepresentation representation = this.service.findGroupForStudent(A_GROUP_ID, AN_USER_NAME);
		
		assertEquals(aRepresentation, representation);
	}
	
	@Test(expected = GroupNotFoundException.class)
	public void testGetGroupForStudentNotFound(){
		when(this.studentRepository.findByUserNameAndFetchMatesAndGroupsEagerly(AN_USER_NAME)).thenReturn(student);
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(null);
		
		this.service.findGroupForStudent(A_GROUP_ID, AN_USER_NAME);
	}
	
	@Test
	public void testFindPictureById() {
		GroupPicture picture = mock(GroupPicture.class);
		when(this.groupPictureRepository.findByGroupId(A_GROUP_ID)).thenReturn(picture);
		
		GroupPicture foundPicture = this.service.getPicture(A_GROUP_ID);
		
		assertEquals(picture, foundPicture);
	}
	
	@Test(expected=GroupNotFoundException.class)
	public void testFindPictureByIdNotFound() {
		when(this.groupPictureRepository.findByGroupId(A_GROUP_ID)).thenReturn(null);
		
		this.service.getPicture(A_GROUP_ID);
	}
	
	@Test
	public void testUpdatePicturePNG() throws IOException {
		MultipartFile image = mock(MultipartFile.class);
		when(image.getContentType()).thenReturn(MediaType.IMAGE_PNG_VALUE);
		when(image.getBytes()).thenReturn("Mock".getBytes());
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(group);
		GroupPicture picture = mock(GroupPicture.class);
		group.setOwner(student);
		when(this.groupPictureRepository.findByGroupId(A_GROUP_ID)).thenReturn(picture);
		when(this.groupPictureRepository.save(picture)).thenReturn(picture);
		
		this.service.updatePicture(A_GROUP_ID, image, AN_USER_NAME);
		
		assertTrue(true);
	}
	
	@Test
	public void testUpdatePictureJPEG() throws IOException {
		MultipartFile image = mock(MultipartFile.class);
		when(image.getContentType()).thenReturn(MediaType.IMAGE_JPEG_VALUE);
		when(image.getBytes()).thenReturn("Mock".getBytes());
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(group);
		GroupPicture picture = mock(GroupPicture.class);
		group.setOwner(student);
		when(this.groupPictureRepository.findByGroupId(A_GROUP_ID)).thenReturn(picture);
		when(this.groupPictureRepository.save(picture)).thenReturn(picture);
		
		this.service.updatePicture(A_GROUP_ID, image, AN_USER_NAME);
		
		assertTrue(true);
	}
	
	@Test(expected=UnsupportedMediaTypeForProfilePictureException.class)
	public void testUpdatePictureNotAllowedContent() throws IOException {
		MultipartFile image = mock(MultipartFile.class);
		when(image.getContentType()).thenReturn(MediaType.IMAGE_GIF_VALUE);
				
		this.service.updatePicture(A_GROUP_ID, image, AN_USER_NAME);
	}
	
	@Test(expected=UnexpectedErrorReadingProfilePictureFileException.class)
	public void testUpdatePictureError() throws IOException {
		MultipartFile image = mock(MultipartFile.class);
		when(image.getContentType()).thenReturn(MediaType.IMAGE_JPEG_VALUE);
		when(image.getBytes()).thenThrow(new IOException());
		when(this.studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(group);
		GroupPicture picture = mock(GroupPicture.class);
		group.setOwner(student);
		when(this.groupPictureRepository.findByGroupId(A_GROUP_ID)).thenReturn(picture);
		
		this.service.updatePicture(A_GROUP_ID, image, AN_USER_NAME);
	}
	
	@Test(expected = StudentNotCreatorOfGroupException.class)
	public void testUpdatePictureNotOwner() throws IOException {
		MultipartFile image = mock(MultipartFile.class);
		when(image.getContentType()).thenReturn(MediaType.IMAGE_PNG_VALUE);
		when(image.getBytes()).thenReturn("Mock".getBytes());
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(group);
		GroupPicture picture = mock(GroupPicture.class);
		group.setOwner(student);
		when(this.groupPictureRepository.findByGroupId(A_GROUP_ID)).thenReturn(picture);
		
		this.service.updatePicture(A_GROUP_ID, image, ANOTHER_USER_NAME);
	}
	
	@Test(expected = GroupNotFoundException.class)
	public void testUpdatePictureGroupNotFound() throws IOException {
		MultipartFile image = mock(MultipartFile.class);
		when(image.getContentType()).thenReturn(MediaType.IMAGE_PNG_VALUE);
		when(image.getBytes()).thenReturn("Mock".getBytes());
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(null);
		GroupPicture picture = mock(GroupPicture.class);
		group.setOwner(student);
		when(this.groupPictureRepository.findByGroupId(A_GROUP_ID)).thenReturn(picture);
		
		this.service.updatePicture(A_GROUP_ID, image, ANOTHER_USER_NAME);
	}
	
	@Test(expected = GroupNotFoundException.class)
	public void testUpdatePictureNotFound() throws IOException {
		MultipartFile image = mock(MultipartFile.class);
		when(image.getContentType()).thenReturn(MediaType.IMAGE_PNG_VALUE);
		when(image.getBytes()).thenReturn("Mock".getBytes());
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(group);
		when(this.groupPictureRepository.findByGroupId(A_GROUP_ID)).thenReturn(null);
		
		this.service.updatePicture(A_GROUP_ID, image, ANOTHER_USER_NAME);
	}
	
	@Test
	public void testUpdateGroup(){
		GroupUpdateRepresentation updatingGroup = mock(GroupUpdateRepresentation.class);
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(group);
		group.setOwner(student);
		when(this.groupRepository.save(group)).thenReturn(group);
		when(this.studentRepository.findByUserNameAndFetchMatesAndGroupsEagerly(AN_USER_NAME)).thenReturn(student);
		GroupRepresentation representation = mock(GroupRepresentation.class);
		when(this.converter.convert(student, group)).thenReturn(representation);
		
		GroupRepresentation updatedGroup = this.service.updateGroup(A_GROUP_ID, updatingGroup, AN_USER_NAME);
		
		assertEquals(representation, updatedGroup);
	}
	
	@Test(expected = GroupNotFoundException.class)
	public void testUpdateGroupNotFound(){
		GroupUpdateRepresentation updatingGroup = mock(GroupUpdateRepresentation.class);
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(null);
				
		this.service.updateGroup(A_GROUP_ID, updatingGroup, AN_USER_NAME);
	}
	
	@Test
	public void unregisterStudent(){
		when(this.studentRepository.findByUserNameAndFetchMatesAndGroupsEagerly(AN_USER_NAME)).thenReturn(student);
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(group);
		when(this.studentRepository.save(student)).thenReturn(student);
		when(this.groupRepository.save(group)).thenReturn(group);
		this.group.addMember(student);
		
		this.service.unregisterStudent(AN_USER_NAME, A_GROUP_ID);
		
		assertTrue(true);
	}
	
	@Test(expected = GroupNotFoundException.class)
	public void unregisterStudentGroupNotFound(){
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(null);
		
		this.service.unregisterStudent(AN_USER_NAME, A_GROUP_ID);
	}

	@Test
	public void testGetMembers(){
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(group);
		when(this.studentRepository.findByUserNameAndFetchMatesAndGroupsEagerly(AN_USER_NAME)).thenReturn(student);
		when(this.converter.convert(student, group)).thenReturn(representation);
		when(representation.getAmIAMember()).thenReturn(true);
		Student aMember = mock(Student.class);
		Student anotherMember = mock(Student.class);
		group.getMembers().add(student);
		student.getGroups().add(group);
		group.getMembers().add(aMember);
		group.getMembers().add(anotherMember);
		StudentProfileRepresentation studentProfile = mock(StudentProfileRepresentation.class);
		StudentProfileRepresentation aMemberProfile = mock(StudentProfileRepresentation.class);
		StudentProfileRepresentation anotherMemberProfile = mock(StudentProfileRepresentation.class);
		when(this.studentProfileConverter.convert(student, student)).thenReturn(studentProfile);
		when(this.studentProfileConverter.convert(student, aMember)).thenReturn(aMemberProfile);
		when(this.studentProfileConverter.convert(student, anotherMember)).thenReturn(anotherMemberProfile);
		
		List<StudentProfileRepresentation> profiles = this.service.getMembers(A_GROUP_ID, AN_USER_NAME);
		
		assertEquals(3, profiles.size());
	}
	
	@Test(expected= GroupNotFoundException.class)
	public void testGetMembersGroupNotFound(){
		when(this.groupRepository.findOne(A_GROUP_ID)).thenReturn(null);
		
		this.service.getMembers(A_GROUP_ID, AN_USER_NAME);
	}
	
}
