package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class CareerAlreadyExistsForStudentException extends BusinessException {
	
	private static final String CODE = "2002";

	public CareerAlreadyExistsForStudentException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}
