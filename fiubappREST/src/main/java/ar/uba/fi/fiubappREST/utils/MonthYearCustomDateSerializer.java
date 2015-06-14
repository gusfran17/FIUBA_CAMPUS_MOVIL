package ar.uba.fi.fiubappREST.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class MonthYearCustomDateSerializer extends JsonSerializer<Date> {  

	private final String DATE_FORMAT = "MMMM yyyy";
	private final Locale ARGENTINA_LOCALE = new Locale("es","AR");
	
	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		String result = "";
		if(date != null){
			result = new SimpleDateFormat(DATE_FORMAT, ARGENTINA_LOCALE).format(date);
		}
		jsonGenerator.writeString(result);
	}
}