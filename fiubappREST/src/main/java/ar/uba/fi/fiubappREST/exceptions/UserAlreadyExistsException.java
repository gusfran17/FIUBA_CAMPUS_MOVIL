package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class UserAlreadyExistsException extends BusinessException {
	
	private static final String CODE = "1002";

	public UserAlreadyExistsException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}
