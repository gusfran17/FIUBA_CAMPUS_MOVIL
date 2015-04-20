package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class NotificationNotFoundForStudentException extends BusinessException {
	
	private static final String CODE = "6001";

	public NotificationNotFoundForStudentException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}