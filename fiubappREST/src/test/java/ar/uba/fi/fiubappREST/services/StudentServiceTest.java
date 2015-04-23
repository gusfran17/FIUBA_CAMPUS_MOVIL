package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;

import ar.uba.fi.fiubappREST.converters.StudentConverter;
import ar.uba.fi.fiubappREST.converters.StudentProfileConverter;
import ar.uba.fi.fiubappREST.domain.Career;
import ar.uba.fi.fiubappREST.domain.Gender;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.StudentCareer;
import ar.uba.fi.fiubappREST.exceptions.CareerNotFoundException;
import ar.uba.fi.fiubappREST.exceptions.StudentAlreadyExistsException;
import ar.uba.fi.fiubappREST.exceptions.StudentNotFoundException;
import ar.uba.fi.fiubappREST.persistance.CareerRepository;
import ar.uba.fi.fiubappREST.persistance.StudentRepository;
import ar.uba.fi.fiubappREST.representations.StudentCreationRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;
import ar.uba.fi.fiubappREST.representations.StudentUpdateRepresentation;

public class StudentServiceTest {
	
	private static final int A_CAREER_CODE = 12;
	private static final String AN_USER_NAME = "AN_USER_NAME";
	private static final String A_VALUE = "aValue";
	private static final String ANOTHER_VALUE = "anotherValue";
	@Mock
	private StudentRepository studentRepository;
	@Mock
	private CareerRepository careerRepository;
	@Mock
	private StudentConverter studentConverter;
	@Mock
	private StudentProfileConverter studentProfileConverter;
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
		this.studentProfileConverter = mock(StudentProfileConverter.class);
		this.passwordEncoder = mock(Md5PasswordEncoder.class);
		
		this.service = new StudentServiceImpl(studentRepository, careerRepository, studentConverter, passwordEncoder, studentProfileConverter);
		
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
	
	@Test
	public void testFindByProperties(){
		when(student.getUserName()).thenReturn(A_VALUE);
		Student anotherStudent = mock(Student.class);
		when(anotherStudent.getUserName()).thenReturn(ANOTHER_VALUE);
		List<Student> students = new ArrayList<Student>();
		students.add(student);
		students.add(anotherStudent);
		when(studentRepository.findByUserNameAndFetchMatesEagerly(AN_USER_NAME)).thenReturn(student);
		when(studentRepository.findByProperties(A_VALUE, A_VALUE, A_VALUE, A_VALUE, A_VALUE, A_VALUE)).thenReturn(students);
		StudentProfileRepresentation profile = mock(StudentProfileRepresentation.class);
		StudentProfileRepresentation anotherProfile = mock(StudentProfileRepresentation.class);
		when(studentProfileConverter.convert(student, student)).thenReturn(profile);
		when(studentProfileConverter.convert(student, anotherStudent)).thenReturn(anotherProfile);
		
		List<StudentProfileRepresentation> foundProfiles = this.service.findByProperties(AN_USER_NAME, A_VALUE, A_VALUE, A_VALUE, A_VALUE, A_VALUE, A_VALUE);
		
		assertEquals(1, foundProfiles.size());	
	}
	
	@Test
	public void testFindOneOk(){
		when(studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		
		Student foundStudent = this.service.findOne(AN_USER_NAME);
		
		assertEquals(student, foundStudent);	
	}
	
	@Test(expected=StudentNotFoundException.class)
	public void testFindOneFail(){
		when(studentRepository.findOne(AN_USER_NAME)).thenReturn(null);
		
		this.service.findOne(AN_USER_NAME);	
	}
	
	@Test
	public void testUpdate(){
		Student student = new Student();
		student.setUserName(AN_USER_NAME);
		student.setName(A_VALUE);
		student.setLastName(A_VALUE);
		student.setEmail(A_VALUE);
		student.setPhoneNumber(A_VALUE);
		student.setDateOfBirth(new Date());
		student.setCurrentCity(A_VALUE);
		student.setNationality(A_VALUE);
		student.setComments(A_VALUE);
		student.setGender(Gender.MALE);
		when(studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		StudentUpdateRepresentation representation = new StudentUpdateRepresentation();
		representation.setName(ANOTHER_VALUE);
		representation.setLastName(ANOTHER_VALUE);
		representation.setEmail(ANOTHER_VALUE);
		representation.setPhoneNumber(ANOTHER_VALUE);
		Date newDate = new Date();
		representation.setDateOfBirth(newDate);
		representation.setCurrentCity(ANOTHER_VALUE);
		representation.setNationality(ANOTHER_VALUE);
		representation.setComments(ANOTHER_VALUE);
		representation.setGender(Gender.FEMALE);
		Student savedStudent = new Student();
		savedStudent.setGender(Gender.FEMALE);
		savedStudent.setName(ANOTHER_VALUE);
		savedStudent.setLastName(ANOTHER_VALUE);
		savedStudent.setEmail(ANOTHER_VALUE);
		savedStudent.setPhoneNumber(ANOTHER_VALUE);
		savedStudent.setDateOfBirth(newDate);
		savedStudent.setCurrentCity(ANOTHER_VALUE);
		savedStudent.setNationality(ANOTHER_VALUE);
		savedStudent.setComments(ANOTHER_VALUE);
		savedStudent.setGender(Gender.FEMALE);
		when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);
		
		Student updatedStudent = this.service.update(AN_USER_NAME, representation);
		
		assertEquals(ANOTHER_VALUE, updatedStudent.getName());
		assertEquals(ANOTHER_VALUE, updatedStudent.getLastName());
		assertEquals(ANOTHER_VALUE, updatedStudent.getEmail());
		assertEquals(ANOTHER_VALUE, updatedStudent.getPhoneNumber());
		assertEquals(newDate, updatedStudent.getDateOfBirth());
		assertEquals(ANOTHER_VALUE, updatedStudent.getCurrentCity());
		assertEquals(ANOTHER_VALUE, updatedStudent.getNationality());
		assertEquals(ANOTHER_VALUE, updatedStudent.getComments());
		assertEquals(Gender.FEMALE, updatedStudent.getGender());
	}
	
	@Test
	public void testUpdateNullValues(){
		Student student = new Student();
		student.setUserName(AN_USER_NAME);
		student.setName(A_VALUE);
		student.setLastName(A_VALUE);
		student.setEmail(A_VALUE);
		student.setPhoneNumber(A_VALUE);
		Date aDate = new Date();
		student.setDateOfBirth(aDate);
		student.setCurrentCity(A_VALUE);
		student.setNationality(A_VALUE);
		student.setComments(A_VALUE);
		student.setGender(Gender.MALE);
		when(studentRepository.findOne(AN_USER_NAME)).thenReturn(student);
		StudentUpdateRepresentation representation = new StudentUpdateRepresentation();
		Student savedStudent = new Student();
		savedStudent.setUserName(AN_USER_NAME);
		savedStudent.setName(A_VALUE);
		savedStudent.setLastName(A_VALUE);
		savedStudent.setEmail(A_VALUE);
		savedStudent.setPhoneNumber(A_VALUE);
		savedStudent.setDateOfBirth(aDate);
		savedStudent.setCurrentCity(A_VALUE);
		savedStudent.setNationality(A_VALUE);
		savedStudent.setComments(A_VALUE);
		savedStudent.setGender(Gender.MALE);
		when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);
		
		Student updatedStudent = this.service.update(AN_USER_NAME, representation);
		
		assertEquals(A_VALUE, updatedStudent.getName());
		assertEquals(A_VALUE, updatedStudent.getLastName());
		assertEquals(A_VALUE, updatedStudent.getEmail());
		assertEquals(A_VALUE, updatedStudent.getPhoneNumber());
		assertEquals(aDate, updatedStudent.getDateOfBirth());
		assertEquals(A_VALUE, updatedStudent.getCurrentCity());
		assertEquals(A_VALUE, updatedStudent.getNationality());
		assertEquals(A_VALUE, updatedStudent.getComments());
		assertEquals(Gender.MALE, updatedStudent.getGender());
	}

}
