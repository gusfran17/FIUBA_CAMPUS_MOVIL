package ar.uba.fi.mobileCampusREST.exceptions;

@SuppressWarnings("serial")
public abstract class BusinessException extends RuntimeException {

	private Object[] params = null;
	
	public BusinessException(Object... params){
		super();
		this.params = params;
	}
	
	public BusinessException(Exception cause, Object... params){
		super(cause);
		this.params = params;
	}

	public abstract String getCode();
		
	public Object[] getParams() {
		return params;
	}
}
