package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class CarrearAlreadyExistsException extends BusinessException {
	
	private static final String CODE = "2002";

	public CarrearAlreadyExistsException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}