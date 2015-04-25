package ar.uba.fi.fiubappREST.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ar.uba.fi.fiubappREST.exceptions.StudentAlreadyMemberOfGroupException;

public class GroupTest {
	
	private static final String OWNER_USER_NAME = "ownerUserName";
	private static final String AN_USER_NAME = "anUserName";
	
	private Group group;
	private Student owner;
		
	@Before
	public void setUp(){
		group = new Group();
		owner = new Student();
		owner.setUserName(OWNER_USER_NAME);
		List<Group> groups = new ArrayList<Group>();
		groups.add(group);
		owner.setGroups(groups);
		List<Student> members = new ArrayList<Student>();
		members.add(owner);
		group.setMembers(members);
	}

	@Test
	public void testAddMemberOK() {
		Student aMember = new Student();
		aMember.setUserName(AN_USER_NAME);
		aMember.setGroups(new ArrayList<Group>());
		
		this.group.addMember(aMember);
				
		assertEquals(2, this.group.getMembers().size());
	}
	
	@Test(expected=StudentAlreadyMemberOfGroupException.class)
	public void testAddStudentAlreadyMember() {
			
		this.group.addMember(owner);
	}
}

