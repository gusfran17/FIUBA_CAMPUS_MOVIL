package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ar.uba.fi.fiubappREST.converters.GroupConverter;
import ar.uba.fi.fiubappREST.domain.Group;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.GroupAlreadyExistsException;
import ar.uba.fi.fiubappREST.persistance.GroupRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.representations.GroupCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.GroupRepresentation;

public class GroupServiceImplTest {
	
	private static final String A_NAME = "aName";
	private static final String A_DESCRIPTION = "aDescription";
	private static final String AN_USER_NAME = "anUserName";
	
	@Mock
	private StudentRepository studentRepository;
	@Mock
	private GroupRepository groupRepository;
	@Mock
	private GroupConverter converter;
	@Mock
	private GroupRepresentation representation;
	
	private Student student;
	private Group group;
		
	private GroupService service;
	
	@Before
	public void setUp() {
		this.studentRepository = mock(StudentRepository.class);
		this.groupRepository = mock(GroupRepository.class);
		this.converter = mock(GroupConverter.class);
		this.service= new GroupServiceImpl(groupRepository, studentRepository, converter);
				
		this.student = new Student();
		this.student.setUserName(AN_USER_NAME);
		List<Group> groups = new ArrayList<Group>();
		this.student.setGroups(groups);
		
		this.group = new Group();
		
		this.representation = mock(GroupRepresentation.class);
	}
		
	@Test
	public void testAddGroup(){
		when(this.studentRepository.findByUserNameAndFetchGroupsEagerly(AN_USER_NAME)).thenReturn(student);
		when(this.studentRepository.findByUserNameAndFetchMatesEagerly(AN_USER_NAME)).thenReturn(student);
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
		when(this.studentRepository.findByUserNameAndFetchGroupsEagerly(AN_USER_NAME)).thenReturn(student);
		List<Group> groups = new ArrayList<Group>();
		groups.add(new Group());
		when(this.groupRepository.findByName(A_NAME)).thenReturn(groups);
		GroupCreationRepresentation creationRepresentation = new GroupCreationRepresentation();
		creationRepresentation.setDescription(A_DESCRIPTION);
		creationRepresentation.setName(A_NAME);
		creationRepresentation.setOwnerUserName(AN_USER_NAME);
		
		this.service.create(creationRepresentation);
	}
	
}
