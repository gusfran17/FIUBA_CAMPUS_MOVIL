package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class StudentAlreadyMateException extends BusinessException {
	
	private static final String CODE = "7001";

	public StudentAlreadyMateException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}