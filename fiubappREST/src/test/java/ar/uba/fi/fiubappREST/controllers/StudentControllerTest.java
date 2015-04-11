package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.representations.StudentCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;
import ar.uba.fi.fiubappREST.services.StudentService;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

public class StudentControllerTest {
	
	private static final String A_TOKEN = "aToken";
	
	@Mock
	private StudentService service;
	@Mock
	private StudentSessionService sessionService;
	@Mock
	private StudentCreationRepresentation representation;
	@Mock
	private Student student;
	
	private StudentController controller;
	
	@Before
	public void setUp(){
		this.service = mock(StudentService.class);
		this.sessionService = mock(StudentSessionService.class);
		
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
	public void testGetStudents(){
		doNothing().when(sessionService).validate(A_TOKEN);
		when(service.findAll()).thenReturn(new ArrayList<StudentProfileRepresentation>());
		
		List<StudentProfileRepresentation> students = this.controller.getStudent(A_TOKEN);
		
		assertNotNull(students);
	}

}
