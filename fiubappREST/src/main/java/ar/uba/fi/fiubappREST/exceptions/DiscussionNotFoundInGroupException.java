package ar.uba.fi.fiubappREST.exceptions;

@SuppressWarnings("serial")
public class DiscussionNotFoundInGroupException extends BusinessException {
	
	private static final String CODE = "8006";

	public DiscussionNotFoundInGroupException(Object... params){
		super(params);
	}
	
	@Override
	public String getCode() {
		return CODE;
	}
}