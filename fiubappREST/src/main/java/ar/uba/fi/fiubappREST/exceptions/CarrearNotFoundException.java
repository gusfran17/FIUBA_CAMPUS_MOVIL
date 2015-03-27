package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class CarrearNotFoundException extends BusinessException {
	
	private static final String CODE = "2001";

	public CarrearNotFoundException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}