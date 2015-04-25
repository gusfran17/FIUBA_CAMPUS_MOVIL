package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class NotificationNotViewedAlreadyExistsForStudentException extends BusinessException {
	
	private static final String CODE = "6002";

	public NotificationNotViewedAlreadyExistsForStudentException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}