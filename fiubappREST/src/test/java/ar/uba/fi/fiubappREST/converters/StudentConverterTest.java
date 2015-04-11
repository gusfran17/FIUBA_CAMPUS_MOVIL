package ar.uba.fi.fiubappREST.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.representations.StudentCreationRepresentation;

public class StudentConverterTest {
	
	private static final String A_NAME = "aName";
	private static final String A_LAST_NAME = "aLastName";
	private static final String A_PASSWORD = "aPassword";
	private static final String AN_EMAIL = "anEmail";
	private static final int CAREER_CODE = 10;
	private static final String A_FILE_NUMBER = "aFileNumber";
	private static final String A_PASSPORT_NUMBER = "aPassportNumber";
	private static final String EXCHANGE_STUDENT_PREFIX = "I";
	
	private StudentCreationRepresentation representation;
	private StudentConverter studentConverter;
	
	@Before
	public void setUp(){
		this.studentConverter= new StudentConverter();
		this.representation = new StudentCreationRepresentation();
		this.representation.setName(A_NAME);
		this.representation.setLastName(A_LAST_NAME);
		this.representation.setPassword(A_PASSWORD);
		this.representation.setEmail(AN_EMAIL);
		this.representation.setCareerCode(CAREER_CODE);
		this.representation.setFileNumber(A_FILE_NUMBER);
		this.representation.setPassportNumber(A_PASSPORT_NUMBER);
	}

	@Test
	public void testConvertRegularStudent() {
		this.representation.setIsExchangeStudent(false);
		
		Student student = this.studentConverter.convert(representation);
		
		assertEquals(A_NAME, student.getName());
		assertEquals(A_LAST_NAME, student.getLastName());
		assertEquals(A_PASSWORD, student.getPassword());
		assertEquals(AN_EMAIL, student.getEmail());
		assertEquals(A_FILE_NUMBER, student.getFileNumber());
		assertEquals(A_PASSPORT_NUMBER, student.getPassportNumber());
		assertEquals(A_FILE_NUMBER, student.getUserName());
		assertFalse(student.getIsExchangeStudent());
		assertNotNull(student.getCareers());
	}
	
	@Test
	public void testConvertExchangeStudent() {
		this.representation.setIsExchangeStudent(true);
		
		Student student = this.studentConverter.convert(representation);
		
		assertEquals(A_NAME, student.getName());
		assertEquals(A_LAST_NAME, student.getLastName());
		assertEquals(A_PASSWORD, student.getPassword());
		assertEquals(AN_EMAIL, student.getEmail());
		assertEquals(A_FILE_NUMBER, student.getFileNumber());
		assertEquals(A_PASSPORT_NUMBER, student.getPassportNumber());
		assertEquals(EXCHANGE_STUDENT_PREFIX + A_PASSPORT_NUMBER, student.getUserName());
		assertTrue(student.getIsExchangeStudent());
		assertNotNull(student.getCareers());
	}

}



