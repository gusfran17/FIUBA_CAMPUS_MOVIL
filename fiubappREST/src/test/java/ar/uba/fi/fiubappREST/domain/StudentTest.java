package ar.uba.fi.fiubappREST.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ar.uba.fi.fiubappREST.exceptions.CareerAlreadyExistsForStudent;

public class StudentTest {
	
	private static final int CAREER_CODE = 10;
	private static final int ANOTHER_CAREER_CODE = 12;
	
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
	
	@Test(expected=CareerAlreadyExistsForStudent.class)
	public void testAddDuplicatedCareer() {
		Career career = new Career();
		career.setCode(CAREER_CODE);
		studentCareer = new StudentCareer();
		studentCareer.setCareer(career);
		
		this.student.addCareer(studentCareer);
	}

}
