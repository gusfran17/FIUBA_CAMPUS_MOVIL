package ar.uba.fi.mobileCampusREST.exceptions;
@SuppressWarnings("serial")
public class UserNotFoundException extends BusinessException {
	
	private static final String CODE = "1001";

	public UserNotFoundException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}

}