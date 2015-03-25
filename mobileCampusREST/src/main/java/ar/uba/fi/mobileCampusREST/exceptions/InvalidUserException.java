package ar.uba.fi.mobileCampusREST.exceptions;

@SuppressWarnings("serial")
public class InvalidUserException extends RuntimeException {
	
	private static final String CODE = "1003";

	public InvalidUserException(String message){
		super(message);
	}
	
	public String getCode() {
		return CODE;
	}
}
