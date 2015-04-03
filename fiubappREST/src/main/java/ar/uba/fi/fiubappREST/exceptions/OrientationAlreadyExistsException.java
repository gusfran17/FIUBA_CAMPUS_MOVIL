package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class OrientationAlreadyExistsException extends BusinessException {
	
	private static final String CODE = "1003";

	public OrientationAlreadyExistsException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}