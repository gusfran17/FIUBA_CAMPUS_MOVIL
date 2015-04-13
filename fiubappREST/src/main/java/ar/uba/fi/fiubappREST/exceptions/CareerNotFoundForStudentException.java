package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class CareerNotFoundForStudentException extends BusinessException {
	
	private static final String CODE = "5001";

	public CareerNotFoundForStudentException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}