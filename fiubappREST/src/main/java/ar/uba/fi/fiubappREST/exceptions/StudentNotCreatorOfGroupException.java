package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class StudentNotCreatorOfGroupException extends BusinessException {
	
	private static final String CODE = "8004";

	public StudentNotCreatorOfGroupException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}
