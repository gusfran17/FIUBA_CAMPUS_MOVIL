package ar.uba.fi.mobileCampusREST.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class CustomDateSerializer extends JsonSerializer<Date> {  

	private final String DATE_FORMAT = "yyyy-MM-dd";
	
	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		String result = "";
		if(date != null){
			result = new SimpleDateFormat(DATE_FORMAT).format(date);
		}
		jsonGenerator.writeString(result);
	}
}