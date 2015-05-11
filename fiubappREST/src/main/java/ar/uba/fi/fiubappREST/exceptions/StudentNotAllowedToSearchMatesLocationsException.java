package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class StudentNotAllowedToSearchMatesLocationsException extends BusinessException {
	
	private static final String CODE = "11001";

	public StudentNotAllowedToSearchMatesLocationsException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}