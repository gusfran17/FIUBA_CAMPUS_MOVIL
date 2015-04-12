package ar.uba.fi.fiubappREST.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import org.codehaus.jackson.JsonGenerator;
import org.junit.Before;
import org.junit.Test;

import ar.uba.fi.fiubappREST.domain.Career;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.domain.StudentCareer;

public class StudentCareerSerielizerTest {
	
	private StudentCareerSerializer serializer;
	private JsonGenerator mockJsonGenerator;
	
	@Before
	public void setUp(){
		serializer = new StudentCareerSerializer();
		mockJsonGenerator = mock(JsonGenerator.class);
	}

	@Test
	public void testSerializeOK() {
		Career career = new Career();
		career.setCode(13);
		career.setName("aName");
		Student student = new Student();
		student.setUserName("anUserName");
		StudentCareer studentCareer = new StudentCareer();
		studentCareer.setCareer(career);
		studentCareer.setStudent(student);
		try {
			serializer.serialize(studentCareer, mockJsonGenerator, null);
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}
}

