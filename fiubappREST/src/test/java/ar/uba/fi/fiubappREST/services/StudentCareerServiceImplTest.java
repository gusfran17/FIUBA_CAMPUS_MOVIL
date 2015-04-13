package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.Career;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.StudentCareer;
import ar.uba.fi.fiubappREST.exceptions.CareerNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.persistance.CareerRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;

public class StudentCareerServiceImplTest {
	
	private static final String AN_USER_NAME = "anUserName";
	private static final Integer A_CAREER_CODE = 2;
	private static final Integer ANOTHER_CAREER_CODE = 3;
	
	@Mock
	private CareerRepository careerRepository;
	@Mock
	private StudentRepository studentRepository;
		
	private Student student;
	
	private Career career;
		
	private StudentCareerService service;
	
	@Before
	public void setUp() throws ParseException{
		this.careerRepository = mock(CareerRepository.class);
		this.studentRepository = mock(StudentRepository.class);		
		this.service= new StudentCareerServiceImpl(careerRepository, studentRepository);
		
		this.student = new Student();
		this.student.setUserName(AN_USER_NAME);
		this.student.setCareers(new ArrayList<StudentCareer>());
		this.career = new Career();
		this.career.setCode(A_CAREER_CODE);
	}
		
	@Test
	public void testCreate() {
		when(this.careerRepository.findByCode(A_CAREER_CODE)).thenReturn(career);
		when(this.studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		when(this.studentRepository.save(student)).thenReturn(student);
		
		StudentCareer savedCareer = this.service.create(AN_USER_NAME, A_CAREER_CODE);
		
		assertEquals(career, savedCareer.getCareer());
	}
	
	@Test(expected=CareerNotFoundException.class)
	public void testCreateCareerNotFound() {
		when(this.careerRepository.findByCode(A_CAREER_CODE)).thenReturn(null);
		
		this.service.create(AN_USER_NAME, A_CAREER_CODE);
	}
	
	@Test(expected=StudentNotFoundException.class)
	public void testCreateStudentNotFound() {
		when(this.careerRepository.findByCode(A_CAREER_CODE)).thenReturn(career);
		when(this.studentRepository.findOne(AN_USER_NAME)).thenReturn(null);
		
		this.service.create(AN_USER_NAME, A_CAREER_CODE);
	}
	
	@Test
	public void testFindAll() {
		StudentCareer studentCareer = new StudentCareer();
		studentCareer.setCareer(career);
		student.addCareer(studentCareer);
		StudentCareer anotherStudentCareer = new StudentCareer();
		Career anotherCareer =new Career();
		anotherCareer.setCode(ANOTHER_CAREER_CODE);
		anotherStudentCareer.setCareer(anotherCareer);
		student.addCareer(anotherStudentCareer);
		when(this.studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		
		List<StudentCareer> careers = this.service.findAll(AN_USER_NAME);
		
		assertEquals(careers, student.getCareers());
	}
	
}
