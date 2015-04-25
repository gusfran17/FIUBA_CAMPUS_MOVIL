package ar.uba.fi.fiubappREST.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.junit.Before;
import org.junit.Test;

import ar.uba.fi.fiubappREST.exceptions.DateFormatException;


public class CustomDateDeserializerTest {

	private CustomDateDeserializer deserializer;
	private JsonParser mockJsonParser;
	
	private static final String CORRECT_FOMAT_AND_VALUE = "13/10/2001";
	private static final String INVALID_FORMAT = "2000-11-10";
	private static final String INVALID_VALUE = "10/13/2001";
	
	@Before
	public void setUp(){
		deserializer = new CustomDateDeserializer();
		mockJsonParser = mock(JsonParser.class);
	}

	@Test
	public void testDeserializeOK() throws JsonParseException, IOException {
		when(mockJsonParser.getText()).thenReturn(CORRECT_FOMAT_AND_VALUE);
		
		Date date = deserializer.deserialize(mockJsonParser, null);
		
		assertNotNull(date);
	}
	
	@Test
	public void testDeserializeNull() throws JsonParseException, IOException {
		when(mockJsonParser.getText()).thenReturn(null);
		
		Date date = deserializer.deserialize(mockJsonParser, null);
		
		assertNull(date);
	}
	
	@Test(expected=DateFormatException.class)
	public void testDeserializeInvalidFormat() throws JsonParseException, IOException {
		when(mockJsonParser.getText()).thenReturn(INVALID_FORMAT);
		
		deserializer.deserialize(mockJsonParser, null);
	}
	
	@Test(expected=DateFormatException.class)
	public void testDeserializeInvalidvalue() throws JsonParseException, IOException {
		when(mockJsonParser.getText()).thenReturn(INVALID_VALUE);
		
		deserializer.deserialize(mockJsonParser, null);
	}

}
