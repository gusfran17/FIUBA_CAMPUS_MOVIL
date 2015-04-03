package ar.uba.fi.fiubappREST.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.Career;
import ar.uba.fi.fiubappREST.domain.Orientation;
import ar.uba.fi.fiubappREST.services.CareerService;

public class CareerControllerTest {
	
	private static final int A_CAREER_CODE = 1;

	private CareerController controller;
	
	@Mock
	private CareerService service;
	@Mock
	private Career aCareer;
	@Mock
	private Career anotherCareer;
	
	@Before
	public void setUp(){
		this.service = mock(CareerService.class);
		this.controller = new CareerController(service);
		
		this.aCareer = mock(Career.class);
		when(aCareer.getCode()).thenReturn(A_CAREER_CODE);
		this.anotherCareer = mock(Career.class);
	}

	@Test
	public void testAddCareer() {
		when(service.save(aCareer)).thenReturn(aCareer);
				
		Career createdCareer = this.controller.addCareer(aCareer);
		
		assertEquals(aCareer, createdCareer);		
	}
	
	@Test
	public void tesGetCareers() {
		List<Career> careers = new ArrayList<Career>();
		careers.add(aCareer);
		careers.add(anotherCareer);
		when(service.findAll()).thenReturn(careers);
				
		List<Career> foundCareers = this.controller.getCareers();
		
		assertEquals(careers.size(), foundCareers.size());
		assertEquals(careers, foundCareers);
	}
	
	@Test
	public void tesGetCareer() {
		
		when(service.findByCode(A_CAREER_CODE)).thenReturn(aCareer);
				
		Career foundCareer = controller.getCareer(A_CAREER_CODE);
		
		assertEquals(aCareer, foundCareer);
	}
	
	@Test
	public void tesAddOrientation() {
		Orientation orientation = new Orientation();
		when(service.addOrientation(A_CAREER_CODE, orientation)).thenReturn(orientation);
				
		Orientation createdOrientation = this.controller.addOrientation(A_CAREER_CODE, orientation);
		
		assertEquals(orientation, createdOrientation);
	}
	
	@Test
	public void tesGetOrientations() {
		Orientation anOrientation = new Orientation();
		Orientation anotherOrientation = new Orientation();
		List<Orientation> orientations = new ArrayList<Orientation>();
		orientations.add(anOrientation);
		orientations.add(anotherOrientation);
		when(service.findOrientations(A_CAREER_CODE)).thenReturn(orientations);
				
		List<Orientation> foundOrientations = this.controller.getOrientations(A_CAREER_CODE);
		
		assertEquals(orientations, foundOrientations);
	}

}

