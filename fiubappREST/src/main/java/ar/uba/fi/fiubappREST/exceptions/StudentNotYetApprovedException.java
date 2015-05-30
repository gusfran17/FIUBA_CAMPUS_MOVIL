package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class StudentNotYetApprovedException extends BusinessException {
	
	private static final String CODE = "3004";

	public StudentNotYetApprovedException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}