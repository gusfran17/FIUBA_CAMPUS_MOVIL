package ar.uba.fi.fiubappREST.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.Career;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.StudentCareer;
import ar.uba.fi.fiubappREST.domain.StudentState;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;

public class StudentProfileConverterTest {
	
	private static final String A_PICTURE_PROFILE_URL = "aPictureProfileUrl";
	private static final String AN_USER_NAME = "anUserName";
	private static final String A_NAME = "aName";
	private static final String A_LAST_NAME = "aLastName";
	private static final String A_FILE_NUMBER = "aFileNumber";
	private static final String A_PASSPORT_NUMBER = "aPassportNumber";
	private static final String A_CAREER_NAME = "aCareerName";
	private static final String ANOTHER_CAREER_NAME = "anotherCareerName";
	private static final String A_COMMENTS = "aComments";
	private static final String AN_EMAIL = "anEmail";
	
	@Mock
	private Student mate;
	private Student me;
	private StudentProfileConverter studentProfileConverter;
	@Mock
	private Career aCareer;
	@Mock
	private Career anotherCareer;
	
	@Before
	public void setUp(){
		this.studentProfileConverter= new StudentProfileConverter();
		this.mate = mock(Student.class);
		when(this.mate.getUserName()).thenReturn(AN_USER_NAME);
		when(this.mate.getName()).thenReturn(A_NAME);
		when(this.mate.getLastName()).thenReturn(A_LAST_NAME);
		when(this.mate.getIsExchangeStudent()).thenReturn(false);
		when(this.mate.getFileNumber()).thenReturn(A_FILE_NUMBER);
		when(this.mate.getPassportNumber()).thenReturn(A_PASSPORT_NUMBER);
		when(this.mate.getComments()).thenReturn(A_COMMENTS);
		when(this.mate.getEmail()).thenReturn(AN_EMAIL);
		when(this.mate.getState()).thenReturn(StudentState.APPROVED);
		when(this.mate.getProfilePictureUrl()).thenReturn(A_PICTURE_PROFILE_URL);
		
		this.aCareer = mock(Career.class);
		this.anotherCareer = mock(Career.class);
		when(aCareer.getName()).thenReturn(A_CAREER_NAME);
		when(anotherCareer.getName()).thenReturn(ANOTHER_CAREER_NAME);
		StudentCareer aStudentCareer = mock(StudentCareer.class);
		StudentCareer anotherStudentCareer = mock(StudentCareer.class);
		when(aStudentCareer.getCareer()).thenReturn(aCareer);
		when(anotherStudentCareer.getCareer()).thenReturn(anotherCareer);
		List<StudentCareer> careers = new ArrayList<StudentCareer>();
		careers.add(aStudentCareer);
		careers.add(anotherStudentCareer);
		when(this.mate.getCareers()).thenReturn(careers);
		me = new Student();
		List<Student> mates = new ArrayList<Student>();
		mates.add(mate);
		this.me.setMates(mates);
	}
	
	@Test
	public void testConvertForStudent() {
			
		StudentProfileRepresentation profile = studentProfileConverter.convert(me, mate);
		
		assertEquals(A_NAME, profile.getName());
		assertEquals(A_LAST_NAME, profile.getLastName());
		assertEquals(A_FILE_NUMBER, profile.getFileNumber());
		assertEquals(A_PASSPORT_NUMBER, profile.getPassportNumber());
		assertEquals(AN_USER_NAME, profile.getUserName());
		assertFalse(profile.getIsExchangeStudent());
		assertTrue(profile.getCareers().contains(A_CAREER_NAME));
		assertTrue(profile.getCareers().contains(ANOTHER_CAREER_NAME));
		assertTrue(profile.getIsMyMate());
		assertEquals(A_COMMENTS, profile.getComments());
		assertEquals(A_PICTURE_PROFILE_URL, profile.getProfilePicture());
	}
}
