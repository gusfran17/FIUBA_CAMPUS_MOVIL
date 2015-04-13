package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class UnableToDeleteTheOnlyCareerForStudentException extends BusinessException {
	
	private static final String CODE = "5002";

	public UnableToDeleteTheOnlyCareerForStudentException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}