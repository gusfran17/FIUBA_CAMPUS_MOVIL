package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class DiscussionMessageFileNotFound extends BusinessException {
	
	private static final String CODE = "8007";

	public DiscussionMessageFileNotFound(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}