package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class StudentAlreadyExistsException extends BusinessException {
	
	private static final String CODE = "2001";

	public StudentAlreadyExistsException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}
