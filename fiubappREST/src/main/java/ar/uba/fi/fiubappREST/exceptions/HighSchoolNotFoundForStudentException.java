package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class HighSchoolNotFoundForStudentException extends BusinessException {
	
	private static final String CODE = "4002";

	public HighSchoolNotFoundForStudentException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}
