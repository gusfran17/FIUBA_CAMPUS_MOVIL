package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;

import ar.uba.fi.fiubappREST.domain.Career;
import ar.uba.fi.fiubappREST.domain.Orientation;
import ar.uba.fi.fiubappREST.exceptions.CareerAlreadyExistsException;
import ar.uba.fi.fiubappREST.exceptions.CareerNotFoundException;
import ar.uba.fi.fiubappREST.persistance.CareerRepository;
import ar.uba.fi.fiubappREST.persistance.OrientationRepository;

public class CareerServiceImplTest {
	
	private static final String A_CAREER_NAME = "Ingeniería en Informática";
	private static final int A_CAREER_CODE = 1;
	private static final int ANOTHER_CAREER_CODE = 1;
	
	@Mock
	private CareerRepository careerRepository;
	@Mock
	private OrientationRepository orientationRepository;
	@Mock
	private Career aCareer;
	@Mock
	private Career anotherCareer;
	
	private CareerService service;
	
	@Before
	public void setUp(){
		this.careerRepository = mock(CareerRepository.class);
		this.orientationRepository = mock(OrientationRepository.class);		
		this.service= new CareerServiceImpl(careerRepository, orientationRepository);
		this.aCareer = mock(Career.class);
		this.anotherCareer = mock(Career.class);
		when(this.aCareer.getCode()).thenReturn(A_CAREER_CODE);
		when(this.anotherCareer.getCode()).thenReturn(ANOTHER_CAREER_CODE);
	}

	@Test
	public void testFindAll() {
		List<Career> careers = new ArrayList<Career>();
		careers.add(this.aCareer);
		careers.add(this.anotherCareer);
		when(this.careerRepository.findAll()).thenReturn(careers);
		
		List<Career> foundCareers = this.service.findAll();
		
		assertEquals(foundCareers.size(), 2);
	}
	
	@Test
	public void testFindByCode() {
		when(this.careerRepository.findByCode(A_CAREER_CODE)).thenReturn(aCareer);
		
		Career foundCareer = this.service.findByCode(A_CAREER_CODE);
		
		assertEquals(foundCareer, aCareer);
	}
	
	@Test(expected=CareerNotFoundException.class)
	public void testFindByCodeNotFound() {
		when(this.careerRepository.findByCode(A_CAREER_CODE)).thenReturn(null);
		
		this.service.findByCode(A_CAREER_CODE);
	}
	
	@Test
	public void testSave() {
		when(this.careerRepository.save(aCareer)).thenReturn(aCareer);
		
		Career savedCareer = this.service.save(aCareer);
		
		assertEquals(savedCareer, aCareer);
	}
	
	@Test(expected=CareerAlreadyExistsException.class)
	public void testSaveDuplicatedCode() {
		when(this.careerRepository.exists(A_CAREER_CODE)).thenReturn(true);
		
		this.service.save(aCareer);
	}
	
	@Test(expected=CareerAlreadyExistsException.class)
	public void testSaveDuplicatedName() {
		when(this.careerRepository.save(aCareer)).thenThrow(new DataIntegrityViolationException(A_CAREER_NAME));
		
		this.service.save(aCareer);
	}
	
	@Test
	public void testAddOrientation() {
		Orientation orientation = new Orientation();
		when(this.careerRepository.findByCode(A_CAREER_CODE)).thenReturn(aCareer);
		doNothing().when(aCareer).addOrientation(orientation);
		when(this.orientationRepository.save(orientation)).thenReturn(orientation);
		
		Orientation savedOrientation = this.service.addOrientation(A_CAREER_CODE, orientation);
				
		assertEquals(savedOrientation, orientation);
	}
	
	@Test
	public void testFindOrientations() {
		Orientation anOrientation = mock(Orientation.class);
		Orientation anotherOrientation = mock(Orientation.class);
		List<Orientation> orientations = new ArrayList<Orientation>();
		orientations.add(anOrientation);
		orientations.add(anotherOrientation);
		when(aCareer.getOrientations()).thenReturn(orientations);
		when(this.orientationRepository.findByCode(A_CAREER_CODE)).thenReturn(orientations);
		when(this.careerRepository.findByCode(A_CAREER_CODE)).thenReturn(aCareer);

		List<Orientation> foundOrientations = this.service.findOrientations(A_CAREER_CODE);
				
		assertEquals(foundOrientations.size(), orientations.size());
	}

}



