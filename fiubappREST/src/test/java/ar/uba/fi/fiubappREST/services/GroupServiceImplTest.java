package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		Set<Group> groups = new HashSet<Group>();
		this.student.setGroups(groups);
		
		this.group = new Group();
		
		this.representation = mock(GroupRepresentation.class);
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
		Group anotherGroup = mock(Group.class);
		groups.add(aGroup);
		groups.add(anotherGroup);
		when(this.groupRepository.findByProperties(A_NAME)).thenReturn(groups);
		GroupRepresentation aRepresentation = mock(GroupRepresentation.class);
		GroupRepresentation anotherRepresentation = mock(GroupRepresentation.class);
		when(this.converter.convert(student, aGroup)).thenReturn(aRepresentation);
		when(this.converter.convert(student, anotherGroup)).thenReturn(anotherRepresentation);
		
		List<GroupRepresentation> representations = this.service.findByProperties(AN_USER_NAME, A_NAME);
		
		assertEquals(2, representations.size());
	}
	
}
