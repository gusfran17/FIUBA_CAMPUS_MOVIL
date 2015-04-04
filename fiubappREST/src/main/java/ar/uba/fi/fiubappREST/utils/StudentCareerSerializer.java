package ar.uba.fi.fiubappREST.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ar.uba.fi.fiubappREST.domain.StudentCareer;

public class StudentCareerSerializer extends JsonSerializer<StudentCareer> {

	@Override
	public void serialize(StudentCareer studentCareer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeNumberField("careerCode", studentCareer.getCareer().getCode());
		jsonGenerator.writeStringField("careerName", studentCareer.getCareer().getName());
		jsonGenerator.writeEndObject();
	}
}