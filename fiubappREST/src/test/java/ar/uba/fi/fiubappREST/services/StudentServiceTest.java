package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;

import ar.uba.fi.fiubappREST.converters.StudentConverter;
import ar.uba.fi.fiubappREST.domain.Career;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.StudentCareer;
import ar.uba.fi.fiubappREST.exceptions.CareerNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.StudentAlreadyExistsException;
import ar.uba.fi.fiubappREST.persistance.CareerRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.representations.StudentCreationRepresentation;

public class StudentServiceTest {
	
	private static final int A_CAREER_CODE = 12;
	private static final String AN_USER_NAME = "AN_USER_NAME";
	@Mock
	private StudentRepository studentRepository;
	@Mock
	private CareerRepository careerRepository;
	@Mock
	private StudentConverter studentConverter;
	@Mock
	private Md5PasswordEncoder passwordEncoder;
	@Mock
	private Student student;
	@Mock
	private StudentCreationRepresentation representation;
	@Mock
	private Career career;
	
	private StudentService service;
		
	@Before
	public void setUp(){
		this.studentRepository = mock(StudentRepository.class);
		this.careerRepository = mock(CareerRepository.class);
		this.studentConverter = mock(StudentConverter.class);
		this.passwordEncoder = mock(Md5PasswordEncoder.class);
		
		this.service = new StudentServiceImpl(studentRepository, careerRepository, studentConverter, passwordEncoder);
		
		this.student = mock(Student.class);
		this.representation = mock(StudentCreationRepresentation.class);
		this.career = mock(Career.class);
	}

	@Test
	public void testCreateOK() {
		when(passwordEncoder.encodePassword(anyString(), any())).thenReturn(anyString());
		when(studentConverter.convert(representation)).thenReturn(student);
		when(student.getUserName()).thenReturn(AN_USER_NAME);
		when(studentRepository.findOne(anyString())).thenReturn(null);
		when(representation.getCareerCode()).thenReturn(A_CAREER_CODE);
		when(careerRepository.findByCode(anyInt())).thenReturn(career);
		doNothing().when(student).addCareer(any(StudentCareer.class));
		when(studentRepository.save(student)).thenReturn(student);
		
		Student createdStudent = this.service.create(representation);
		
		assertNotNull(createdStudent);
	}
	
	@Test(expected=StudentAlreadyExistsException.class)
	public void testCreateForDuplicatedUserName() {
		when(passwordEncoder.encodePassword(anyString(), any())).thenReturn(anyString());
		when(studentConverter.convert(representation)).thenReturn(student);
		when(student.getUserName()).thenReturn(AN_USER_NAME);
		Student anotherStudent = mock(Student.class);
		when(studentRepository.findOne(anyString())).thenReturn(anotherStudent);
		
		this.service.create(representation);

	}
	
	@Test(expected=CareerNotFoundException.class)
	public void testCreateInvalidCareer() {
		when(passwordEncoder.encodePassword(anyString(), any())).thenReturn(anyString());
		when(studentConverter.convert(representation)).thenReturn(student);
		when(student.getUserName()).thenReturn(AN_USER_NAME);
		when(studentRepository.findOne(anyString())).thenReturn(null);
		when(representation.getCareerCode()).thenReturn(A_CAREER_CODE);
		when(careerRepository.findByCode(anyInt())).thenReturn(null);
		
		this.service.create(representation);
	}

}
