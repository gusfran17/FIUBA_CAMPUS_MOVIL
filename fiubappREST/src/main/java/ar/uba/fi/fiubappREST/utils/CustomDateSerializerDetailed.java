package ar.uba.fi.fiubappREST.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class CustomDateSerializerDetailed  extends JsonSerializer<Date> {  

	private final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		String result = "";
		if(date != null){
			result = new SimpleDateFormat(DATE_FORMAT).format(date);
		}
		jsonGenerator.writeString(result);
	}
}