package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class StudentsAreNotMatesException extends BusinessException {
	
	private static final String CODE = "7002";

	public StudentsAreNotMatesException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}