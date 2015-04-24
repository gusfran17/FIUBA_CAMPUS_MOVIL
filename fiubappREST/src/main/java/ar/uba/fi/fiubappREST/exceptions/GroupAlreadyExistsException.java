package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class GroupAlreadyExistsException extends BusinessException {
	
	private static final String CODE = "8001";

	public GroupAlreadyExistsException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}
