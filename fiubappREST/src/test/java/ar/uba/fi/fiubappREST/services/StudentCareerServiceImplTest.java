package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
import ar.uba.fi.fiubappREST.exceptions.CareerNotFoundForStudentException;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.persistance.CareerRepository;
import ar.uba.fi.fiubappREST.persistance.StudentCareerRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;

public class StudentCareerServiceImplTest {
	
	private static final String AN_USER_NAME = "anUserName";
	private static final Integer A_CAREER_CODE = 2;
	private static final Integer ANOTHER_CAREER_CODE = 3;
	
	@Mock
	private CareerRepository careerRepository;
	@Mock
	private StudentRepository studentRepository;
	@Mock
	private StudentCareerRepository studentCareerRepository;
		
	private Student student;
	
	private Career career;
	
	private StudentCareer studentCareer;
		
	private StudentCareerService service;
	
	@Before
	public void setUp() throws ParseException{
		this.careerRepository = mock(CareerRepository.class);
		this.studentRepository = mock(StudentRepository.class);		
		this.studentCareerRepository = mock(StudentCareerRepository.class);
		this.service= new StudentCareerServiceImpl(careerRepository, studentRepository, studentCareerRepository);
		
		this.student = new Student();
		this.student.setUserName(AN_USER_NAME);
		this.student.setCareers(new ArrayList<StudentCareer>());
		this.career = new Career();
		this.career.setCode(A_CAREER_CODE);
		this.studentCareer = new StudentCareer();
		studentCareer.setStudent(student);
		studentCareer.setCareer(career);
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
	
	@Test
	public void testDelete() {
		StudentCareer studentCareer = new StudentCareer();
		studentCareer.setCareer(career);
		student.addCareer(studentCareer);		
		Career anotherCareer =new Career();
		StudentCareer anotherStudentCareer = new StudentCareer();
		anotherCareer.setCode(ANOTHER_CAREER_CODE);
		anotherStudentCareer.setCareer(anotherCareer);
		student.addCareer(anotherStudentCareer);
		when(this.studentCareerRepository.findByCodeAndUserName(A_CAREER_CODE, AN_USER_NAME)).thenReturn(anotherStudentCareer);
		when(this.studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		when(this.studentRepository.save(student)).thenReturn(student);
		
		this.service.delete(AN_USER_NAME, A_CAREER_CODE);
		
		assertTrue(true);
	}
	
	@Test(expected=CareerNotFoundForStudentException.class)
	public void testDeleteNotFound() {
		when(this.studentCareerRepository.findByCodeAndUserName(A_CAREER_CODE, AN_USER_NAME)).thenReturn(null);
		
		this.service.delete(AN_USER_NAME, A_CAREER_CODE);
	}
	
}
