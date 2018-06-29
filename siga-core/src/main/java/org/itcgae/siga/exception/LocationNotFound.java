package org.itcgae.siga.exception;

public class LocationNotFound extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7814397743997160065L;
	/**
	 * 
	 */
	
	
	private Object[] params = null;
	public LocationNotFound(String string) {
		super(string);
	}
	
	public LocationNotFound(String string, Object... params) {
		super(String.format(string, params));
		this.params = params;
	}

	public LocationNotFound(String string, Exception e) {
		super(string, e);
	}

	/**
	 * @return the params
	 */
	public Object[] getParams() {
		return params;
	}
}