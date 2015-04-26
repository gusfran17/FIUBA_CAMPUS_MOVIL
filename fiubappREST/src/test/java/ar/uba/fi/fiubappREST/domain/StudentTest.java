package ar.uba.fi.fiubappREST.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import ar.uba.fi.fiubappREST.exceptions.CareerAlreadyExistsForStudentException;
import ar.uba.fi.fiubappREST.exceptions.CareerNotFoundForStudentException;
import ar.uba.fi.fiubappREST.exceptions.StudentAlreadyMateException;
import ar.uba.fi.fiubappREST.exceptions.StudentsAreNotMatesException;
import ar.uba.fi.fiubappREST.exceptions.UnableToDeleteTheOnlyCareerForStudentException;

public class StudentTest {
	
	private static final int A_GROUP_ID = 10;
	private static final int CAREER_CODE = 10;
	private static final int ANOTHER_CAREER_CODE = 12;
	private static final String AN_USER_NAME = "anUserName";
	
	private StudentCareer studentCareer;
	
	private Student student;
	
	@Before
	public void setUp(){
		Career career = new Career();
		career.setCode(CAREER_CODE);
		
		studentCareer = new StudentCareer();
		studentCareer.setCareer(career);
		
		student = new Student();
		student.setCareers(new ArrayList<StudentCareer>());
		student.getCareers().add(studentCareer);
		
		this.student.setGroups(new HashSet<Group>());
	}

	@Test
	public void testAddCareerOK() {
		Career career = new Career();
		career.setCode(ANOTHER_CAREER_CODE);
		studentCareer = new StudentCareer();
		studentCareer.setCareer(career);
		
		this.student.addCareer(studentCareer);
				
		assertEquals(2, this.student.getCareers().size());
	}
	
	@Test(expected=CareerAlreadyExistsForStudentException.class)
	public void testAddDuplicatedCareer() {
		Career career = new Career();
		career.setCode(CAREER_CODE);
		studentCareer = new StudentCareer();
		studentCareer.setCareer(career);
		
		this.student.addCareer(studentCareer);
	}

	@Test
	public void testAddMateOK() {
		student.setMates(new ArrayList<Student>());
		Student mate = new Student();
		mate.setMates(new ArrayList<Student>());
		
		this.student.addMate(mate);
				
		assertEquals(1, this.student.getMates().size());
	}
	
	@Test(expected=StudentAlreadyMateException.class)
	public void testAddMateAlreadyExists() {
		student.setMates(new ArrayList<Student>());
		Student mate = new Student();
		mate.setUserName(AN_USER_NAME);
		mate.setMates(new ArrayList<Student>());
		this.student.getMates().add(mate);
				
		this.student.addMate(mate);
	}

	@Test
	public void testRemoveCareerOK() {
		Career career = new Career();
		career.setCode(ANOTHER_CAREER_CODE);
		studentCareer = new StudentCareer();
		studentCareer.setCareer(career);		
		this.student.addCareer(studentCareer);
		
		this.student.removeCareer(studentCareer);
				
		assertEquals(1, this.student.getCareers().size());
	}
	
	@Test(expected=CareerNotFoundForStudentException.class)
	public void testRemoveCareerNotFound() {
		Career career = new Career();
		career.setCode(ANOTHER_CAREER_CODE);
		studentCareer = new StudentCareer();
		studentCareer.setCareer(career);		
		
		this.student.removeCareer(studentCareer);
	}
	
	@Test(expected=UnableToDeleteTheOnlyCareerForStudentException.class)
	public void testRemoveTheOnlyCareer() {		
		this.student.removeCareer(studentCareer);
	}
	
	@Test
	public void testAddNotification(){
		Notification notification = new ApplicationNotification();
		
		this.student.addNotification(notification);
		
		assertEquals(1, this.student.getNotifications().size());
	}
	
	@Test
	public void testDeleteMate(){
		student.setMates(new ArrayList<Student>());
		Student mate = new Student();
		mate.setUserName(AN_USER_NAME);
		mate.setMates(new ArrayList<Student>());
		this.student.getMates().add(mate);
		
		this.student.deleteMate(mate);
		
		assertEquals(0, this.student.getMates().size());
	}
	
	@Test(expected=StudentsAreNotMatesException.class)
	public void testDeleteMateNotExists(){
		student.setMates(new ArrayList<Student>());
		Student mate = new Student();
		mate.setUserName(AN_USER_NAME);
		
		this.student.deleteMate(mate);
	}
	
	@Test
	public void testIsMemberOf(){
		Group aGroup = new Group();
		aGroup.setId(A_GROUP_ID);
		aGroup.setMembers(new HashSet<Student>());
		aGroup.addMember(student);
		
		boolean isMemberOf = student.isMemberOf(aGroup);
		
		assertTrue(isMemberOf);
	}
	
	@Test
	public void testIsNotMemberOf(){
		Group aGroup = new Group();
		aGroup.setId(A_GROUP_ID);
		
		boolean isMemberOf = student.isMemberOf(aGroup);
		
		assertFalse(isMemberOf);
	}

}

