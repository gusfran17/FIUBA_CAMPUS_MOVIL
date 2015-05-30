package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class SessionNotFoundException extends BusinessException {
	
	private static final String CODE = "3002";

	public SessionNotFoundException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}