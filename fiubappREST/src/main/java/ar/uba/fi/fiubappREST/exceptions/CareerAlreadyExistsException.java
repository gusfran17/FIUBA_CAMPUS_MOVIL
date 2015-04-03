package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class CareerAlreadyExistsException extends BusinessException {
	
	private static final String CODE = "1002";

	public CareerAlreadyExistsException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}