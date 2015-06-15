package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class GroupFileNotFoundException extends BusinessException {
	
	private static final String CODE = "14001";

	public GroupFileNotFoundException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}