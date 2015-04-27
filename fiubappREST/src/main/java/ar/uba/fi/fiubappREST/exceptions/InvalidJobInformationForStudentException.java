package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class InvalidJobInformationForStudentException extends BusinessException {
	
	private static final String CODE = "9001";

	public InvalidJobInformationForStudentException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}
