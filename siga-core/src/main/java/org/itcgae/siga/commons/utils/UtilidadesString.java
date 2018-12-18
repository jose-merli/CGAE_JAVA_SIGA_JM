/*
 * Created on 11-nov-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.itcgae.siga.commons.utils;


import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.itcgae.siga.commons.constants.SigaConstants;







/**
 * @author daniel.campos
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * Modificada el 22/04/2005 por david.sanchez para incluir tratarImporte().
 */
public class UtilidadesString {
	
	


	private static Logger LOGGER = Logger.getLogger(UtilidadesString.class);
	static public String getMensajeIdioma (String idioma, String key)	{

		Properties prop = new Properties();//(Properties)idiomas.get(idi); 
		if (prop == null)	{
		}
		String aux = prop.getProperty(key,key);
		if (aux.equals(key)) {
		    return aux;
		} else {
		    return caracteresJavascript(aux);
		}
	}
	
	  /**
	   * Sustituye comillas simples por apostrofo para javascript. El problema catalán.
	   * @param sCadena a traducir
	   * @return
	   */
	  
	  private static String caracteresJavascript(String sCadena)
	  {
	    if (sCadena==null) return "";

	    sCadena = sCadena.replaceAll("'", "´");
	    sCadena = sCadena.replaceAll("\"", "´");

	    return sCadena;
	  }
	/**
	 * Valida el campo url/ruta de cualquier formulario.
	 * @param url valor del campo del formulario,
	 * @return boolean En funci�n del resultado, retorna true si se cumple o false si la comprobaci�n es incorrecta
	 */
	public static boolean isValidURL(String url) {  
	
	    URL u = null;
	
	    try {  
	        u = new URL(url);  
	    } catch (MalformedURLException e) {  
	        return false;  
	    }
	
	    try {  
	        u.toURI();  
	    } catch (URISyntaxException e) {  
	        return false;  
	    }  
	
	    return true;  
	} 
	public static String getPrimeraMayuscula(String cadena){
		if(cadena!=null)
			return cadena.substring(0,1).toUpperCase() + cadena.substring(1).toLowerCase(); 
		else 
			return "";
		
	}
		
	
	/**
	 * Funci�n que elimina acentos y caracteres especiales de una cadena de texto.
	 * @param input
	 * @return cadena de texto limpia de acentos y caracteres especiales.
	 */
	public static String eliminarAcentosYCaracteresEspeciales(String input) {
		// Cadena de caracteres original a sustituir.
		String original = "��������������u�������������������!\"#$%&'()*+-,./:;<=>?@[\\]^_`{|}~";	

		// Cadena de caracteres ASCII que reemplazar�n los originales.
		String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC                                ";
		String output = input;
		for (int i = 0; i < original.length(); i++) {
			// Reemplazamos los caracteres especiales.
			output = output.replace(original.charAt(i), ascii.charAt(i));
		}// for i
		return output;
	}
	
	public static Calendar toCalendar(Date date){
		if (null != date) {
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
		}else {
			return null;
		}
	}
	
	
	public static String filtroTextoBusquedas(String columna, String cadena) {
		StringBuilder cadenaWhere = new StringBuilder();
		cadenaWhere.append(" (TRANSLATE(LOWER( " + columna + "),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN') ");
		cadenaWhere.append(" LIKE");
		cadenaWhere.append(" TRANSLATE(LOWER('%" + cadena + "%'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')) ");
		return cadenaWhere.toString();
		
	}
	
	public static boolean esCadenaVacia(final String cadena) {

		return cadena == null || cadena.trim().isEmpty();
	}

	public static boolean isNullOrBlank(final Object obj) {

		return obj == null || "".equals(obj.toString().trim());
	}
	
	public static Date toDate(String string) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		
		return	format.parse(string);
		
	}
	
	public static String prepararPerfiles(List<String> perfiles) {
		String idPerfiles = "";
		for (int i = 0; i < perfiles.size(); i++) {
			String contructPerfil = "";
			if (perfiles.size() == 1) {
				contructPerfil += perfiles.get(i);
			} else {
				if (i != perfiles.size() - 1) {
					contructPerfil += perfiles.get(i);

					contructPerfil += ",";

				} else {
					contructPerfil += perfiles.get(i);
				}
			}
			idPerfiles += contructPerfil;
		}
		
		return idPerfiles;
	}
	
    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    public static Date getDate(String sDate, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (null != sDate)
			return sdf.parse(sDate);
		else
			return null;
	}

	public static Date getDate(String sDate, String format, String defaultValue)  {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (null != sDate)
			try {
				return sdf.parse(sDate);
			} catch (ParseException e) {
				return null;
			}
		else
			return null;
	}
	
	
	// Los siguientes son los tipos que no pueden repetirse dentro de un cliente
	public static final Integer[] tiposDireccionUnicos = { 
			SigaConstants.TIPO_DIRECCION_CENSOWEB, SigaConstants.TIPO_DIRECCION_GUARDIA,
			SigaConstants.TIPO_DIRECCION_FACTURACION, SigaConstants.TIPO_DIRECCION_TRASPASO_OJ
			};
	
	  



}
