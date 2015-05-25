package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class StudentSuspendedException extends BusinessException {
	
	private static final String CODE = "3005";

	public StudentSuspendedException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}