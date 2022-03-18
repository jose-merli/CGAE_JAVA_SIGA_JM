package org.itcgae.siga.commons.utils;

/**
 * Clase de utilidad que nos permite pasar valores primitivos int entre métodos.
 *
 */
public class IntegerWrapper {

	private int value;
	
	public IntegerWrapper (int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
