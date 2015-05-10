package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class UnsupportedMediaTypeForProfilePictureException extends BusinessException {
	
	private static final String CODE = "10001";

	public UnsupportedMediaTypeForProfilePictureException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}