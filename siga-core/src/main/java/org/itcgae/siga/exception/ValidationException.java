package org.itcgae.siga.exception;

public class ValidationException extends Exception {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7710402220133208467L; 
	
	private String codError;
	
	public ValidationException(String arg0) {
		super(arg0);
	}
	
	public ValidationException(String codError,String message) {
		super(message);
		setCodError(codError);	
	}
	
	public ValidationException(String arg0, Object... params) {
		super(String.format(arg0, params));
	}

	public String getCodError() {
		return codError;
	}

	public void setCodError(String codError) {
		this.codError = codError;
	}


	
}
