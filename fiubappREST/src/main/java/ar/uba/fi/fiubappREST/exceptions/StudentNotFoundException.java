package ar.uba.fi.fiubappREST.exceptions;
@SuppressWarnings("serial")
public class StudentNotFoundException extends BusinessException {
	
	private static final String CODE = "2003";

	public StudentNotFoundException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}

}