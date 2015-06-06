package ar.uba.fi.fiubappREST.domain;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ar.uba.fi.fiubappREST.exceptions.StudentAlreadyMemberOfGroupException;
import ar.uba.fi.fiubappREST.exceptions.StudentIsNotMemberOfGroupException;

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
		Set<Group> groups = new HashSet<Group>();
		groups.add(group);
		owner.setGroups(groups);
		Set<Student> members = new HashSet<Student>();
		members.add(owner);
		group.setMembers(members);
	}

	@Test
	public void testAddMemberOK() {
		Student aMember = new Student();
		aMember.setUserName(AN_USER_NAME);
		aMember.setGroups(new HashSet<Group>());
		
		this.group.addMember(aMember);
				
		assertEquals(2, this.group.getMembers().size());
	}
	
	@Test(expected=StudentAlreadyMemberOfGroupException.class)
	public void testAddStudentAlreadyMember() {
			
		this.group.addMember(owner);
	}
	
	@Test
	public void testRemoveMemberOK() {		
		this.group.removeMember(owner);
				
		assertEquals(0, this.group.getMembers().size());
	}
	
	@Test(expected=StudentIsNotMemberOfGroupException.class)
	public void testRemoveStudentNotMember() {
		Student notAMember = new Student();
		notAMember.setUserName(AN_USER_NAME);
		notAMember.setGroups(new HashSet<Group>());
		
			
		this.group.removeMember(notAMember);
	}
}

