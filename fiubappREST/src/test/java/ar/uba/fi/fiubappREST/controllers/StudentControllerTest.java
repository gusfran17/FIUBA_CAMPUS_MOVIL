package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.Session;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.StudentState;
import ar.uba.fi.fiubappREST.representations.StudentCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentStateRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentUpdateRepresentation;
import ar.uba.fi.fiubappREST.services.SessionService;
import ar.uba.fi.fiubappREST.services.StudentService;

public class StudentControllerTest {
	
	private static final String A_TOKEN = "aToken";
	private static final String AN_USER_NAME = "anUserName";
	
	@Mock
	private StudentService service;
	@Mock
	private SessionService sessionService;
	@Mock
	private StudentCreationRepresentation representation;
	@Mock
	private Student student;
	
	private StudentController controller;
	
	@Before
	public void setUp(){
		this.service = mock(StudentService.class);
		this.sessionService = mock(SessionService.class);
		
		this.controller = new StudentController(service, sessionService);
		
		this.representation = mock(StudentCreationRepresentation.class);
		this.student = mock(Student.class);
	}

	@Test
	public void testAddStudent() {
		when(this.service.create(representation)).thenReturn(student);
		
		Student createdStudent = this.controller.addStudent(representation);
		
		assertNotNull(createdStudent);
	}
	
	@Test
	public void testGetStudentsForStudent(){
		Session session = mock(Session.class);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
		when(session.isAdminSession()).thenReturn(false);
		when(sessionService.findSession(A_TOKEN)).thenReturn(session);
		when(service.findByProperties(AN_USER_NAME, null, null, null, null, null, null)).thenReturn(new ArrayList<StudentProfileRepresentation>());
		
		List<StudentProfileRepresentation> students = this.controller.findStudents(A_TOKEN, null, null, null, null, null, null, null);
		
		assertNotNull(students);
	}
	
	@Test
	public void testGetStudentsForAdmin(){
		Session session = mock(Session.class);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
		when(session.isAdminSession()).thenReturn(true);
		when(sessionService.findSession(A_TOKEN)).thenReturn(session);
		when(service.findByProperties(null, null, null, null, null)).thenReturn(new ArrayList<StudentProfileRepresentation>());
		
		List<StudentProfileRepresentation> students = this.controller.findStudents(A_TOKEN, null, null, null, null, null, null, null);
		
		assertNotNull(students);
	}
	
	@Test
	public void testGetStudentsForAdminWithNotNullState(){
		Session session = mock(Session.class);
		when(session.getUserName()).thenReturn(AN_USER_NAME);
		when(session.isAdminSession()).thenReturn(true);
		when(sessionService.findSession(A_TOKEN)).thenReturn(session);
		when(service.findByProperties(null, null, null, null, StudentState.APPROVED)).thenReturn(new ArrayList<StudentProfileRepresentation>());
		
		List<StudentProfileRepresentation> students = this.controller.findStudents(A_TOKEN, null, null, null, null, null, null, StudentState.APPROVED.getName());
		
		assertNotNull(students);
	}
	
	@Test
	public void testGetStudent(){
		doNothing().when(sessionService).validateStudentSession(A_TOKEN);
		when(service.findOne(AN_USER_NAME)).thenReturn(student);
		
		Student foundStudent = this.controller.getStudent(A_TOKEN, AN_USER_NAME);
		
		assertEquals(student, foundStudent);
	}
	
	@Test
	public void updateStudent(){
		doNothing().when(sessionService).validateThisStudent(A_TOKEN, AN_USER_NAME);
		StudentUpdateRepresentation representation = mock(StudentUpdateRepresentation.class);
		when(service.update(AN_USER_NAME, representation)).thenReturn(student);
		
		Student updatedStudent = this.controller.updateStudent(A_TOKEN, AN_USER_NAME, representation);
		
		assertEquals(student, updatedStudent);
	}
	
	@Test
	public void updateStudentState(){
		doNothing().when(sessionService).validateAdminSession(A_TOKEN);
		StudentStateRepresentation representation = mock(StudentStateRepresentation.class);
		when(representation.getState()).thenReturn(StudentState.APPROVED);
		when(service.updateStudentState(AN_USER_NAME, representation)).thenReturn(representation);
		
		StudentStateRepresentation updatedStudentState = this.controller.updateStudentState(A_TOKEN, AN_USER_NAME, representation);
		
		assertEquals(representation, updatedStudentState);
	}

}
