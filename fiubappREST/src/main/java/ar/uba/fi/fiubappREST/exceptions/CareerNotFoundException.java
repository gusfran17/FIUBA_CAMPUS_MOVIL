package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class CareerNotFoundException extends BusinessException {
	
	private static final String CODE = "1001";

	public CareerNotFoundException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}