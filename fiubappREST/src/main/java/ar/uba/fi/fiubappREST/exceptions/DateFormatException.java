package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class DateFormatException extends BusinessException {

	private static final String CODE = "0001";
	
	public DateFormatException(Exception e, Object... params){
		super(e, params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}

}
