package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class StudentSessionNotFoundException extends BusinessException {
	
	private static final String CODE = "3002";

	public StudentSessionNotFoundException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}