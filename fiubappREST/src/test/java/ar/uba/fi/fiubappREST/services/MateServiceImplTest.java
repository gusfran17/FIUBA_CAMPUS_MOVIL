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

import ar.uba.fi.fiubappREST.converters.StudentProfileConverter;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;

public class MateServiceImplTest {
	
	private static final String AN_USER_NAME = "anUserName";
	private static final String A_MATE_USER_NAME = "aMateUserName";
	
	@Mock
	private StudentRepository studentRepository;
	@Mock
	private StudentProfileConverter converter;
	@Mock
	private StudentProfileRepresentation profile;
	
	private Student student;
		
	private MateService service;
	
	@Before
	public void setUp() throws ParseException{
		this.studentRepository = mock(StudentRepository.class);
		this.converter = mock(StudentProfileConverter.class);
		this.service= new MateServiceImpl(studentRepository, null, converter);
				
		this.student = new Student();
		this.student.setUserName(AN_USER_NAME);
		this.student.setMates(new ArrayList<Student>());
		
		this.profile = mock(StudentProfileRepresentation.class);
	}
		
	@Test
	public void testBecomeMates(){
		Student mate = new Student();
		mate.setUserName(A_MATE_USER_NAME);
		mate.setMates(new ArrayList<Student>());
		when(this.studentRepository.findByUserNameAndFetchMatesEagerly(AN_USER_NAME)).thenReturn(student);
		when(this.studentRepository.findByUserNameAndFetchMatesEagerly(A_MATE_USER_NAME)).thenReturn(mate);
		when(this.studentRepository.save(student)).thenReturn(student);
		when(this.studentRepository.save(mate)).thenReturn(mate);
		when(this.converter.convert(student, mate)).thenReturn(profile);
		
		StudentProfileRepresentation mateProfile = this.service.becomeMates(AN_USER_NAME, A_MATE_USER_NAME);
		
		assertEquals(profile, mateProfile);
	}
	
	@Test(expected=StudentNotFoundException.class)
	public void testBecomeMatesStudentNotFound(){
		when(this.studentRepository.findByUserNameAndFetchMatesEagerly(AN_USER_NAME)).thenReturn(null);
		
		this.service.becomeMates(AN_USER_NAME, A_MATE_USER_NAME);
	}
	
	@Test
	public void testGetMates(){
		Student mate = new Student();
		mate.setUserName(A_MATE_USER_NAME);
		mate.setMates(new ArrayList<Student>());
		student.addMate(mate);
		when(this.studentRepository.findByUserNameAndFetchMatesEagerly(AN_USER_NAME)).thenReturn(student);
		when(this.converter.convert(student, mate)).thenReturn(profile);
		
		List<StudentProfileRepresentation> mates = this.service.getMates(AN_USER_NAME);
		
		assertEquals(1, mates.size());
	}
}
