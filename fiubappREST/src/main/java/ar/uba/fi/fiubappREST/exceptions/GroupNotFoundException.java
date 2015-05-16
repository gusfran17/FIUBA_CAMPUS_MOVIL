package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class GroupNotFoundException extends BusinessException {
	
	private static final String CODE = "8003";

	public GroupNotFoundException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}