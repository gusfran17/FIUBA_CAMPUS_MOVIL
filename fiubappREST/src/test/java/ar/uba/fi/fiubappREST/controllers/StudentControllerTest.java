package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.representations.StudentCreationRepresentation;
import ar.uba.fi.fiubappREST.services.StudentService;

public class StudentControllerTest {
	
	@Mock
	private StudentService service;
	@Mock
	private StudentCreationRepresentation representation;
	@Mock
	private Student student;
	
	private StudentController controller;
	
	@Before
	public void setUp(){
		this.service = mock(StudentService.class);
		
		this.controller = new StudentController(service);
		
		this.representation = mock(StudentCreationRepresentation.class);
		this.student = mock(Student.class);
	}

	@Test
	public void testAddStudent() {
		when(this.service.create(representation)).thenReturn(student);
		
		Student createdStudent = this.controller.addStudent(representation);
		
		assertNotNull(createdStudent);
	}

}
