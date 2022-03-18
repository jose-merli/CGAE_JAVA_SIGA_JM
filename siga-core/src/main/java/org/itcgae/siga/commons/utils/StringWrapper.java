package org.itcgae.siga.commons.utils;

/**
 * Clase de utilidad que nos permite pasar valores String entre métodos.
 *
 */
public class StringWrapper {
	
	private String value;
	
	public StringWrapper(String value) {
		this.value = value;
	}
	
	public String concat(String value) {
		return this.value += value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
