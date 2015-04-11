package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class InvalidCredentialsException extends BusinessException {
	
	private static final String CODE = "3001";

	public InvalidCredentialsException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}