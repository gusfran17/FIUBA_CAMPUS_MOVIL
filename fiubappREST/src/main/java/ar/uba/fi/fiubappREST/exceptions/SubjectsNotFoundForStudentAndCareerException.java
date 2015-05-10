package ar.uba.fi.fiubappREST.exceptions;
@SuppressWarnings("serial")
public class SubjectsNotFoundForStudentAndCareerException extends BusinessException {

	private static final String CODE = "12001";

	public SubjectsNotFoundForStudentAndCareerException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}

}
