package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.StudentCareer;
import ar.uba.fi.fiubappREST.services.StudentCareerService;
import ar.uba.fi.fiubappREST.services.SessionService;

public class StudentCareerControllerTest {
	
	private static final String AN_USER_NAME = "anUserName";
	private static final Integer A_CAREER_CODE = 12;
	private static final String A_TOKEN = "aToken";

	private StudentCareerController controller;
	
	@Mock
	private StudentCareerService service;
	@Mock
	private SessionService studentSessionService;
	@Mock
	private StudentCareer career;
		
	@Before
	public void setUp(){
		this.service = mock(StudentCareerService.class);
		this.studentSessionService = mock(SessionService.class);
		this.controller = new StudentCareerController(service, studentSessionService);
		
		this.career = mock(StudentCareer.class);
	}

	@Test
	public void testAddCareer() {
		doNothing().when(studentSessionService).validateThisStudent(A_TOKEN, AN_USER_NAME);
		when(service.create(AN_USER_NAME, A_CAREER_CODE)).thenReturn(career);
				
		StudentCareer createdCareer = this.controller.addCareer(A_TOKEN, AN_USER_NAME, A_CAREER_CODE);
		
		assertEquals(career, createdCareer);		
	}
	
	@Test
	public void testGetCareers() {
		doNothing().when(studentSessionService).validateStudentSession(A_TOKEN);
		StudentCareer anotherCareer = mock(StudentCareer.class);
		List<StudentCareer> careers = new ArrayList<StudentCareer>();
		careers.add(career);
		careers.add(anotherCareer);
		when(service.findAll(AN_USER_NAME)).thenReturn(careers);
				
		List<StudentCareer> foundCareers = this.controller.getCareers(A_TOKEN, AN_USER_NAME);
		
		assertEquals(careers, foundCareers);		
	}
	
	@Test
	public void testDeleteCareer() {
		doNothing().when(studentSessionService).validateThisStudent(A_TOKEN, AN_USER_NAME);
		doNothing().when(service).delete(AN_USER_NAME, A_CAREER_CODE);
				
		this.controller.deleteCareer(A_TOKEN, AN_USER_NAME, A_CAREER_CODE);
		
		assertTrue(true);		
	}
}

