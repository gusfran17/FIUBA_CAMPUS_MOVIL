package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class InvalidDateRangeException extends BusinessException {
	
	private static final String CODE = "0002";

	public InvalidDateRangeException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}
