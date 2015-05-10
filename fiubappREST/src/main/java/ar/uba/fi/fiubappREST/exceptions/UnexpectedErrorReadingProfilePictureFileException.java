package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class UnexpectedErrorReadingProfilePictureFileException extends BusinessException {
	
	private static final String CODE = "10003";

	public UnexpectedErrorReadingProfilePictureFileException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}