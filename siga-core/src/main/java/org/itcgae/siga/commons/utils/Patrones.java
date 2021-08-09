package org.itcgae.siga.commons.utils;

/**
 * Clase que contiene diferentes patrones de expresiones regulares utilizados para validar parámetros.
 *
 */
public class Patrones {

	/**
	 * Patron de expresión regular para validar un NIF.
	 */
	public final static String NIF = "[XYZxyz]?[0-9]{7,8}[a-zA-Z]";
	
	/**
	 * Patron de expresión regular para validar un CIF.
	 */
	public final static String CIF = "[a-zA-Z][0-9]{7}[a-zA-Z0-9]";
	
	/**
	 * Patron de expresión regular para validar un NIE.
	 */
	public final static String NIE = "[X-Zx-z]\\d{1,7}[A-Za-z]";
}
