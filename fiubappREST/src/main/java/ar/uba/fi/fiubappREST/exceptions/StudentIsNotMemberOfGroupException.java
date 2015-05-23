package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class StudentIsNotMemberOfGroupException extends BusinessException{

	private static final String CODE = "8005";
	
	public StudentIsNotMemberOfGroupException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}

}
