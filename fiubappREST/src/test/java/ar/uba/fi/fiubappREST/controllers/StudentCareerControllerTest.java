package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.Career;
import ar.uba.fi.fiubappREST.services.StudentCareerService;
import ar.uba.fi.fiubappREST.services.StudentSessionService;

public class StudentCareerControllerTest {
	
	private static final String AN_USER_NAME = "anUserName";
	private static final Integer A_CAREER_CODE = 12;
	private static final String A_TOKEN = "aToken";

	private StudentCareerController controller;
	
	@Mock
	private StudentCareerService service;
	@Mock
	private StudentSessionService studentSessionService;
	@Mock
	private Career career;
		
	@Before
	public void setUp(){
		this.service = mock(StudentCareerService.class);
		this.studentSessionService = mock(StudentSessionService.class);
		this.controller = new StudentCareerController(service, studentSessionService);
		
		this.career = mock(Career.class);
	}

	@Test
	public void testAddCareer() {
		doNothing().when(studentSessionService).validateMine(A_TOKEN, AN_USER_NAME);
		when(service.create(AN_USER_NAME, A_CAREER_CODE)).thenReturn(career);
				
		Career createdCareer = this.controller.addCareer(A_TOKEN, AN_USER_NAME, A_CAREER_CODE);
		
		assertEquals(createdCareer, career);		
	}
}

