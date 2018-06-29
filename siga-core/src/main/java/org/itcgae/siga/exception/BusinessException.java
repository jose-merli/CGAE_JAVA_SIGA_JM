package org.itcgae.siga.exception;

public class BusinessException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2825923363893627924L; 
	
	private Object[] params = null;
	public BusinessException(String string) {
		super(string);
	}
	
	public BusinessException(String string, Object... params) {
		super(String.format(string, params));
		this.params = params;
	}

	public BusinessException(String string, Exception e) {
		super(string, e);
	}

	/**
	 * @return the params
	 */
	public Object[] getParams() {
		return params;
	}
}