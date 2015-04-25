package ar.uba.fi.fiubappREST.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.web.bind.annotation.ControllerAdvice;

import ar.uba.fi.fiubappREST.exceptions.DateFormatException;

@ControllerAdvice
public class CustomDateDeserializer extends JsonDeserializer<Date>{
	
	private final String DATE_FORMAT = "dd/MM/yyyy";
	
    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        SimpleDateFormat format = new SimpleDateFormat(this.DATE_FORMAT);
        format.setLenient(false);
        String date = jsonparser.getText();
        if(date != null){
        	try {
        		return format.parse(date);
        	} catch (ParseException e) {		
        		throw new DateFormatException(e, date, DATE_FORMAT);
        	}        	
        }
        return null;
    }
}
