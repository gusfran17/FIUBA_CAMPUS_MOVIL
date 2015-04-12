package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class HighSchoolAlreadyExistsForStudentException extends BusinessException {
	
	private static final String CODE = "4001";

	public HighSchoolAlreadyExistsForStudentException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}
