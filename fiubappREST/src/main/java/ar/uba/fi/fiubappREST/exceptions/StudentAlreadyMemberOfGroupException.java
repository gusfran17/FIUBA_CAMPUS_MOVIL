package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class StudentAlreadyMemberOfGroupException extends BusinessException {
	
	private static final String CODE = "8002";

	public StudentAlreadyMemberOfGroupException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}