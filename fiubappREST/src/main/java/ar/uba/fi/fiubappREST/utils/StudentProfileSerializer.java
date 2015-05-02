package ar.uba.fi.fiubappREST.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ar.uba.fi.fiubappREST.converters.StudentProfileConverter;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.representations.StudentProfileRepresentation;

public class StudentProfileSerializer extends JsonSerializer<Student> {  
	
	@Override
	public void serialize(Student student, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		StudentProfileRepresentation result = null;
		if(student != null){
			StudentProfileConverter converter = new StudentProfileConverter();
			result = converter.convert(student);
		}
		jsonGenerator.writeObject(result);
	}

}