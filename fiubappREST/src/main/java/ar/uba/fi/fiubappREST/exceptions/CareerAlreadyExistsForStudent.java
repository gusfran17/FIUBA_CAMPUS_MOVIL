package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class CareerAlreadyExistsForStudent extends BusinessException {
	
	private static final String CODE = "2002";

	public CareerAlreadyExistsForStudent(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}
