package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class OperationNotAllowedFotStudentSessionException extends BusinessException {
	
	private static final String CODE = "3003";

	public OperationNotAllowedFotStudentSessionException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}