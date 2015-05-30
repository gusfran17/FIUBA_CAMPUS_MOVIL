package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class WallMessageNotFoundForStudentException extends BusinessException {
	
	private static final String CODE = "13001";

	public WallMessageNotFoundForStudentException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}