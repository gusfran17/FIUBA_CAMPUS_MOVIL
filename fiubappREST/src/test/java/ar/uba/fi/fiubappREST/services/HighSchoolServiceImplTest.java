package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.HighSchool;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.HighSchoolAlreadyExistsForStudentException;
import ar.uba.fi.fiubappREST.exceptions.HighSchoolNotFoundForStudentException;
import ar.uba.fi.fiubappREST.exceptions.InvalidDateRangeException;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.persistance.HighSchoolRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;

public class HighSchoolServiceImplTest {
	
	private static final String A_SCHOOL_NAME = "aSchoolName";

	private static final String A_DEGREE = "aDegree";

	private static final String AN_USER_NAME = "anUserName";
	
	@Mock
	private HighSchoolRepository highSchoolRepository;
	@Mock
	private StudentRepository studentRepository;
	
	private HighSchool highSchool;
	
	private SimpleDateFormat sdf;
	
	private Student student;
		
	private HighSchoolService service;
	
	@Before
	public void setUp() throws ParseException{
		this.highSchoolRepository = mock(HighSchoolRepository.class);
		this.studentRepository = mock(StudentRepository.class);		
		this.service= new HighSchoolServiceImpl(highSchoolRepository, studentRepository);
		
		this.highSchool = new HighSchool();
		this.highSchool.setSchoolName(A_SCHOOL_NAME);
		this.highSchool.setDegree(A_DEGREE);
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		this.highSchool.setDateFrom(sdf.parse("01/01/2001"));
		this.highSchool.setDateTo(sdf.parse("01/01/2001"));
		
		this.student = new Student(); 
	}
		
	@Test
	public void testCreate() {
		when(this.highSchoolRepository.findByUserName(AN_USER_NAME)).thenReturn(null);
		when(this.studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		when(this.studentRepository.save(student)).thenReturn(student);
		
		HighSchool savedHighSchool = this.service.create(AN_USER_NAME, highSchool);
		
		assertEquals(savedHighSchool, highSchool);
	}
	
	@Test(expected=HighSchoolAlreadyExistsForStudentException.class)
	public void testCreateAlreadyExist() {
		when(this.highSchoolRepository.findByUserName(AN_USER_NAME)).thenReturn(highSchool);
		
		this.service.create(AN_USER_NAME, highSchool);
	}
	
	@Test(expected=InvalidDateRangeException.class)
	public void testCreateFutureDateFrom() throws ParseException {
		this.highSchool.setDateFrom(sdf.parse("01/01/2030"));
		when(this.highSchoolRepository.findByUserName(AN_USER_NAME)).thenReturn(null);
		
		this.service.create(AN_USER_NAME, highSchool);
	}
	
	@Test(expected=InvalidDateRangeException.class)
	public void testCreateFutureDateTo() throws ParseException {
		this.highSchool.setDateTo(sdf.parse("01/01/2030"));
		when(this.highSchoolRepository.findByUserName(AN_USER_NAME)).thenReturn(null);
		
		this.service.create(AN_USER_NAME, highSchool);
	}
	
	@Test(expected=InvalidDateRangeException.class)
	public void testCreateInvalidDateRange() throws ParseException {
		this.highSchool.setDateFrom(new Date());
		when(this.highSchoolRepository.findByUserName(AN_USER_NAME)).thenReturn(null);
		
		this.service.create(AN_USER_NAME, highSchool);
	}
	
	@Test
	public void testFindByUserName() {
		when(this.highSchoolRepository.findByUserName(AN_USER_NAME)).thenReturn(highSchool);
		when(this.studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		
		HighSchool foundHighSchool = this.service.findByUserName(AN_USER_NAME);
		
		assertEquals(foundHighSchool, highSchool);
	}
	
	@Test(expected=StudentNotFoundException.class)
	public void testFindByUserNameInvalidStudent() {
		when(this.studentRepository.findOne(AN_USER_NAME)).thenReturn(null);
		
		this.service.findByUserName(AN_USER_NAME);
	}
	
	@Test(expected=HighSchoolNotFoundForStudentException.class)
	public void testFindByUserNameNotFound() {
		when(this.studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		when(this.highSchoolRepository.findByUserName(AN_USER_NAME)).thenReturn(null);
		
		this.service.findByUserName(AN_USER_NAME);
	}
	
}



