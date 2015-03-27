package ar.uba.fi.fiubappREST.services;

import java.text.MessageFormat;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class MessageService{
	
	@Resource(name="errorMessages")
	private Properties errorMessages;
	
	public String getMessage(String code, Object... args){
		return MessageFormat.format(errorMessages.getProperty(code), args);
	}

	public void setErrorMessages(Properties errorMessages) {
		this.errorMessages = errorMessages;
	}
	
}
