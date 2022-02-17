/*
 * Created on 11-nov-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.itcgae.siga.commons.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.commons.constants.SigaConstants;

import javax.swing.text.MaskFormatter;


/**
 * @author daniel.campos
 *
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates Modificada
 *         el 22/04/2005 por david.sanchez para incluir tratarImporte().
 */
public class UtilidadesString {

	private static Logger LOGGER = Logger.getLogger(UtilidadesString.class);

	private static List<String> DIGITOS_COMIENZO_MOVIL = Arrays.asList("6","7");
	private static Pattern patternDigitos = Pattern.compile("^[0-9]*$");


	static public String getMensajeIdioma(String idioma, String key) {

		Properties prop = new Properties();// (Properties)idiomas.get(idi);
		/*
		 * if (prop == null) { }
		 */
		String aux = prop.getProperty(key, key);

		if (aux.equals(key)) {
			return aux;
		} else {
			return caracteresJavascript(aux);
		}
	}

	/**
	 * Sustituye comillas simples por apostrofo para javascript. El problema
	 * catalán.
	 * 
	 * @param sCadena a traducir
	 * @return
	 */


	private static String caracteresJavascript(String sCadena) {
		if (sCadena == null)
			return "";

		sCadena = sCadena.replaceAll("'", "´");
		sCadena = sCadena.replaceAll("\"", "´");

		return sCadena;
	}

	/**
	 * Valida el campo url/ruta de cualquier formulario.
	 * 
	 * @param url valor del campo del formulario,
	 * @return boolean En funci�n del resultado, retorna true si se cumple o false
	 *         si la comprobaci�n es incorrecta
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

	public static String getPrimeraMayuscula(String cadena) {
		if (cadena != null)
			return cadena.substring(0, 1).toUpperCase() + cadena.substring(1).toLowerCase();

		else
			return "";

	}


	/**
	 * Funcion que elimina acentos y caracteres especiales de una cadena de texto.
	 * 
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
		} // for i
		return output;
	}


	public static Calendar toCalendar(Date date) {

		if (null != date) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		} else {
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


	public static Date toDate(String string) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		return format.parse(string);


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

	public static List<String> formateaListaPerfiles(List<String> perfiles) {

		List<String> listaPerfilesFormat = new ArrayList<String>();

		for (String cadena : perfiles) {
			cadena = cadena.replace("'", "");
			listaPerfilesFormat.add(cadena);
		}

		return listaPerfilesFormat;
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

	public static Date getDate(String sDate, String format, String defaultValue) {
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



	public static String traduceNota(Double nota) {
		String notaString = "";

		// Controlamos que la nota no sea null y que sea una nota correcta entre 0 y 10
		if (nota == null || (nota < 0 || nota > 10)) {
			return null;
		} else {
			if (nota >= 0 && nota < 5)
				notaString = SigaConstants.NOTA_SUSPENSO;

			if (nota == 5)
				notaString = SigaConstants.NOTA_APROBADO;

			if (nota == 6)
				notaString = SigaConstants.NOTA_BIEN;

			if (nota >= 7 && nota < 9)
				notaString = SigaConstants.NOTA_NOTABLE;

			if (nota >= 9 && nota <= 10)
				notaString = SigaConstants.NOTA_SOBRESALIENTE;
		}

		return notaString;
	}

	public static String replaceFirstIgnoreCase(String texto, String clave, String valor) {
		if (texto != null && clave != null && valor != null) {

			String t = texto.toUpperCase();
			int ini = t.indexOf(clave.toUpperCase());
			if (ini < 0)
				return texto;

			t = texto.substring(0, ini) + valor + texto.substring(ini + clave.length());
			return t;

		} else {
			LOGGER.info("replaceFirstIgnoreCase Devolvemos espacio");
			return "";
		}
	}


	private static int replaceFirstIgnoreCase(String texto[], String clave, String valor, int posIni) {
		if (texto == null || texto.length < 0 || clave == null || valor == null) {
			LOGGER.info("replaceFirstIgnoreCase retornamos menos 1");
			return -1;
		}

		String t = texto[0].toUpperCase();
		int ini = t.indexOf(clave.toUpperCase(), posIni);
		if (ini < 0)
			return -1;

		t = texto[0].substring(0, ini) + valor + texto[0].substring(ini + clave.length());
		texto[0] = t;
		return ini + valor.length();
	}


	public static String replaceAllIgnoreCase(String texto, String clave, String valor) {
		String t[] = { texto };
		int i = 0;
		while (true) {
			i = UtilidadesString.replaceFirstIgnoreCase(t, clave, valor, i);
			if (i < 0)
				return t[0];
		}
	}

	public static String isNifNie(String nif) {
		String tipo;
		if (nif.length() != 9) {
			return null;
		} else {
			// si es NIE, eliminar la x,y,z inicial para tratarlo como nif
			if (nif.toUpperCase().startsWith("X") || nif.toUpperCase().startsWith("Y")
					|| nif.toUpperCase().startsWith("Z")) {
				nif = nif.substring(1);
				tipo = "NIE";
			} else {
				tipo = "NIF";
			}
		}

		Pattern nifPattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
		Matcher m = nifPattern.matcher(nif);
		if (m.matches()) {
			String letra = m.group(2);
			// Extraer letra del NIF
			String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
			int dni = Integer.parseInt(m.group(1));
			dni = dni % 23;
			String reference = letras.substring(dni, dni + 1);

			if (reference.equalsIgnoreCase(letra)) {
				return tipo;
			} else {
				return tipo;
			}
		} else
			return null;
	}

	public static String getFileContent(File plantillaFO) throws IOException, Exception {

		String content = null;

		if (!plantillaFO.exists()) {
			throw new IOException("El fichero " + plantillaFO.getName() + " no existe");
		}
		InputStream is = null;
		try {
			is = new FileInputStream(plantillaFO);
			int nBytes = is.available();
			byte buffer[] = new byte[nBytes];
			is.read(buffer, 0, nBytes);
			content = new String(buffer);
			is.close();
			return content;
		} catch (IOException e) {
			try {





				is.close();

			} catch (Exception eee) {}

			throw new Exception("Error en la lectura del fichero", e);
		}


	}

	public static void setFileContent(File ficheroFOP, String sPlantillaFO) throws Exception {
		try {
			StringReader is = new StringReader(sPlantillaFO);
			OutputStream os = new FileOutputStream(ficheroFOP);
			int car;
			while ((car = is.read()) != -1) {
				os.write(car);
			}
			os.flush();
			os.close();
			is.close();

		} catch (IOException e) {
			throw new Exception("Error al insertar contenido en fichero", e);
		}

	}

	public static String reemplazaEntreMarcasCon(String texto, String marcaInicial, String marcaFinal,
			String insTexto) {
		String retorno = null;
		int inicio = texto.indexOf(marcaInicial);
		int fin = texto.indexOf(marcaFinal);

		if (inicio != -1 && fin != -1) {
			retorno = texto.substring(0, inicio) + insTexto + texto.substring(fin + marcaFinal.length());
		} else {
			retorno = texto;
		}
		return retorno;
	}

	public static String reemplazaParametros(String texto, String marca, Hashtable<String, String> properties) {

		StringBuffer finalText = new StringBuffer();
		int dif = marca.length();
		String prop;
		int pos1 = 0;
		int pos2 = 0;
		int index = 0;
		if (texto == null)
			texto = "";
		// while parameters in the string
		while ((pos1 = texto.indexOf(marca, index)) != -1) {
			// search the parameter betwen the control characters
			// and replace by its value
			pos2 = texto.indexOf(marca, pos1 + dif);
			prop = texto.substring(pos1 + dif, pos2);

			finalText.append(texto.substring(index, pos1));
			String propValue = (String) properties.get(prop.toUpperCase());
			if (propValue != null) {
				propValue = UtilidadesString.formato_ISO_8859_1(propValue);
				finalText.append(propValue);
			}
			// searching on from the last control character
			index = pos2 + dif;
		}

		finalText.append(texto.substring(index, texto.length()));
		return finalText.toString();
	}

	public static String formato_ISO_8859_1(String s) {
		StringBuffer buf = new StringBuffer("");
		char[] mChars = s.toCharArray();
		for (int i = 0; i < mChars.length; i++) {
			int code = mChars[i];
			if (code > 47 && code < 58) {// digito
				buf.append(mChars[i]);
			} else if (code > 64 && code < 91) {// mayuscula
				buf.append(mChars[i]);
			} else if (code > 96 && code < 123) {// minuscula
				buf.append(mChars[i]);
			} else if (code == 128) {// excepcion del euro
				// LMS 08/08/2006
				// En lugar de utilizar el formato decimal, se usa el hexadecimal para definir
				// el símbolo del euro.
				// buf.append("&#x20AC;");
				buf.append("&#8364;");
			} else {// resto de caracteres
				if (code != 0)
					buf.append("&#x" + Integer.toHexString(code) + ";");
			}
		}
		return buf.toString();
	}

	public static String encuentraEntreMarcas(String texto, String marcaInicial, String marcaFinal) {
		int inicio = texto.indexOf(marcaInicial);
		int fin = texto.indexOf(marcaFinal);
		String retorno = null;
		if (inicio != -1 && fin != -1) {
			retorno = texto.substring(inicio + marcaInicial.length(), fin);
		}
		return retorno;
	}
	
	/**
	 * Metodo de utilidad para rellenar una cadena size veces con el caracter filler
	 *
	 * @param text
	 * @param size
	 * @param filler
	 * @return
	 */
	public static String fillCadena(String text, int size, String filler){
		String cadena = "";

		if (!esCadenaVacia(text) && !esCadenaVacia(filler)) {
			if (text != "") {
				int lengthCad = size - text.length();

				if (lengthCad == 0 || lengthCad < 0) {
					return text;
				} else if (lengthCad >= 1) {
					for (int i = 0; i < lengthCad; i++) {
						cadena += filler;
					}

					return cadena + text;
				}
			} else {
				return text;
			}
		}

		return text;
	}
	
	
	public static String getCampoMultidioma (String campo, String idioma){
		if (esCadenaVacia(campo)) return "";
		if (esCadenaVacia(idioma)) idioma = "1";

		String alias = campo;
		int i = campo.indexOf(".");
		if (i >= 0) {
			alias = campo.substring(i+1);
		}
		return " F_SIGA_GETRECURSO (" + campo + "," + idioma + ") " + alias + " ";
	}

	public static String getCampoMultidiomaSimple (String campo, String idioma){
		if (esCadenaVacia(campo)) return "";
		if (esCadenaVacia(idioma)) idioma = "1";

		return " F_SIGA_GETRECURSO (" + campo + "," + idioma + ")  ";
	}

	/**
	 * formatea un dato a una longitud rellenando por la izquierda a ceros
	 * o por la derecha a blancos en funcion de si es numerico
	 */
	public static String formatea(Object datoOrig, int longitud, boolean numerico) {
		String salida = "";
		if (datoOrig == null) {
			if (numerico) {
				salida = relleno("0", longitud);
			} else {
				salida = relleno(" ", longitud);
			}
		} else {
			String dato = datoOrig.toString();
			if (dato.length() == 0) {
				if (numerico) {
					salida = relleno("0", longitud);
				} else {
					salida = relleno(" ", longitud);
				}
			} else if (dato.length() > longitud) {
				// mayor
				if (numerico) {
					salida = dato.substring(dato.length() - longitud, dato.length());
				} else {
					salida = dato.substring(0, longitud);
				}
			} else if (dato.length() < longitud) {
				// menor
				if (numerico) {
					salida = relleno("0", longitud - dato.length()) + dato;
				} else {
					salida = dato + relleno(" ", longitud - dato.length());
				}
			} else {
				// es igual
				salida = dato;
			}
		}

		return salida;
	}
	

	/**
	 * @param nombreFichero Debe ser unicamente el nombre del fichero 'fichero.txt' sin ruta
	 * @return
	 */
	public static String validarNombreFichero(String nombreFichero) {
		char caracter = '_';
		nombreFichero = nombreFichero.replace('\\', caracter);
		nombreFichero = nombreFichero.replace('/', caracter);
		nombreFichero = nombreFichero.replace(':', caracter);
		nombreFichero = nombreFichero.replace('*', caracter);
		nombreFichero = nombreFichero.replace('?', caracter);
		nombreFichero = nombreFichero.replace('\"', caracter);
		nombreFichero = nombreFichero.replace('<', caracter);
		nombreFichero = nombreFichero.replace('>', caracter);
		nombreFichero = nombreFichero.replace('|', caracter);
		return nombreFichero;
	}

	/**
	 * Crea un string de longitud x relleno del caracter indicado
	 */
	public static String relleno(String caracter, int longitud) {
		String salida = "";

		for (int i = 0; i < longitud; i++) {
			salida += caracter;
		}

		return salida;
	}
	
	public static Error creaError(String mensaje) {
		Error error = new Error();
		
		error.setCode(500);
		error.setDescription("general.mensaje.error.bbdd");
		error.setMessage(mensaje);
		return error;

	}

	public static Error creaInfoResultados() {
		Error error = new Error();

		error.setMessage("general.message.consulta.resultados");

		return error;
	}

	/**
	 * Reemplaza una cadea de caracteres por otro
	 * 
	 * @param cadenaOld, cadena a reemplazar
	 * @param cadenaNew, nueva cadea de caracteres
	 */
	public static String reemplazaString(String cadenaOld, String cadenaNew, String frase) {

		final Pattern pattern = Pattern.compile(cadenaOld);
		final Matcher matcher = pattern.matcher(frase);
		frase = matcher.replaceAll(cadenaNew);
		return frase;
	}

	/**
	 * funciona para validar el iban
	 * 
	 * @param cuenta
	 * @return
	 */
	public static boolean validarIBAN(String cuenta) {
		boolean esValido = cuenta != null && cuenta.length() == 24 && cuenta.substring(0, 2).equals("ES");

		for (int i = 2; i < cuenta.length() && esValido; i++) {
			esValido = Character.isDigit(cuenta.charAt(i));
		}

		if (esValido) {
			BigInteger cuentaBancaria = new BigInteger(cuenta.substring(4, 24) + "1428" + cuenta.substring(2, 4));
			Integer resto = cuentaBancaria.mod(new BigInteger("97")).intValue();

			esValido = resto == 1;
		}

		return esValido;
	}
	
	  public static String sustituirParaExcel(String sCadena)
	  {
	    String sSalida="";

	    if (sCadena==null) return sSalida;
	    sSalida = sCadena.replaceAll("\t", " ");
	    sSalida = sSalida.replaceAll("\n\r", " ");
	    sSalida = sSalida.replaceAll("\n", " ");
	    sSalida = sSalida.replaceAll("\r", " ");
	    //sSalida = sSalida.replaceAll("", "\u20ac");
	    return sSalida;
	  }

	/**
	 * Valida un numero de movil para enviar sms por el servicio ECOS
	 * El numero debe de tener el formato (+xx)6xxxxxxxx o (+xx)7xxxxxxxx
	 *
	 * @param numeroTelefono
	 * @return
	 */
	public static boolean esNumMovilECOS(String numeroTelefono) {
		boolean bEsNumMovilECOS = false;
		if (numeroTelefono != null && numeroTelefono.length() >= 9) {
			String numeroSinPrefijo = "";
			if (numeroTelefono.startsWith("(+34)"))
				numeroSinPrefijo = numeroTelefono.substring(5);
			else
				numeroSinPrefijo = getNumeroConPrefijoECOS(numeroTelefono).substring(5);
			if (numeroSinPrefijo.length() == 9 && sonDigitos(numeroSinPrefijo) &&
					DIGITOS_COMIENZO_MOVIL.contains(Character.toString(numeroSinPrefijo.charAt(0))))
				bEsNumMovilECOS = true;
		}
		return bEsNumMovilECOS;
	}

	public static boolean sonDigitos(String numero) {
		if (numero == null)
			return false;
		else
			return patternDigitos.matcher(numero).find();
	}

	/**
	 * Devuelve un numero de telefono con el prefijo de Espana entre parentesis (+34).
	 * Si ya tiene prefijo y no es el de espana (contiene el + pero no
	 * seguido de 34/034/0034) se obviara el + y se tomara como un numero sin prefijo.
	 *
	 * @param numero
	 * @return
	 */
	public static String getNumeroConPrefijoECOS(String numero) {
		String prefijo = "+34";
		if (numero.startsWith("+")) {
			numero = numero.substring(1);
			if (numero.startsWith("34"))
				numero = numero.substring(2);
			else if (numero.startsWith("034"))
				numero = numero.substring(3);
			else if (numero.startsWith("0034"))
				numero = numero.substring(4);
		}
		numero = "(" + prefijo + ")" + numero;
		LOGGER.info("NUMERO DE TELEFONO CON PREFIJO:" + numero);
		return numero;
	}

	public static String mostrarIBANConAsteriscos (String iban) {
		try	{
			// Verficamos si el iban tiene espacios
			iban = iban.trim().replaceAll(" ", "");
			String ibanInicio = iban.substring(0,8);
			String ibanFin = iban.substring(iban.length()-4);
			String asteriscos = "";
			int longitudIbanMedio = iban.substring(8,iban.length()-4).length();

			for(int i = 0; i < longitudIbanMedio; i++) {
				asteriscos = "*" + asteriscos;
			}
			return mostrarDatoMascara(ibanInicio + asteriscos + ibanFin,"AA AA AAAA **** **** **** **** **** **** **");
		}
		catch (Exception e) {
			return "";
		}
	}

	public static String mostrarDatoMascara (Object o,String mask) {
		String a = "";
		try {
			if (o!=null) {
				MaskFormatter mf = new MaskFormatter(mask);
				mf.setValueContainsLiteralCharacters(false);
				a = mf.valueToString(o.toString());
			}
		} catch (ParseException e) {
			LOGGER.error("Error al utilizar la mascara");
			return "";
		}

		return mostrarDatoJSP(a);
	}

	public static String mostrarDatoJSP (Object o) {
		String a = "";
		if (o!=null) {
			a = o.toString();
		}
		if (a.trim().equals(""))
			return "&nbsp";
		else
			return entidades(a);
	}

	public static String entidades(String sCadena) {
		String sSalida="";

		if (sCadena==null) return sSalida;

		sSalida = sCadena.replaceAll("&", "&amp;");
		sSalida = sSalida.replaceAll("á", "&aacute;");
		sSalida = sSalida.replaceAll("Á", "&Aacute;");
		sSalida = sSalida.replaceAll("é", "&eacute;");
		sSalida = sSalida.replaceAll("É", "&Eacute;");
		sSalida = sSalida.replaceAll("í", "&iacute;");
		sSalida = sSalida.replaceAll("Í", "&Iacute;");
		sSalida = sSalida.replaceAll("ó", "&oacute;");
		sSalida = sSalida.replaceAll("Ó", "&Oacute;");
		sSalida = sSalida.replaceAll("ú", "&uacute;");
		sSalida = sSalida.replaceAll("Ú", "&Uacute;");
		sSalida = sSalida.replaceAll("ñ", "&ntilde;");
		sSalida = sSalida.replaceAll("Ñ", "&Ntilde;");
		sSalida = sSalida.replaceAll("<", "&lt;");
		sSalida = sSalida.replaceAll(">", "&gt;");
		sSalida = sSalida.replaceAll("\"", "&quot;");
		sSalida = sSalida.replaceAll("¡", "&iexcl;");
		sSalida = sSalida.replaceAll("´", "&acute;");
		sSalida = sSalida.replaceAll("`", "&acute;");
		sSalida = sSalida.replaceAll("¿", "&iquest;");
		sSalida = sSalida.replaceAll("¨", "&uml;");
		sSalida = sSalida.replaceAll("À", "&Agrave;");
		sSalida = sSalida.replaceAll("à", "&agrave;");
		sSalida = sSalida.replaceAll("Â", "&Acirc;");
		sSalida = sSalida.replaceAll("â", "&acirc;");
		sSalida = sSalida.replaceAll("Ä", "&Auml;");
		sSalida = sSalida.replaceAll("ä", "&auml;");
		sSalida = sSalida.replaceAll("È", "&Egrave;");
		sSalida = sSalida.replaceAll("Ê", "&Ecirc;");
		sSalida = sSalida.replaceAll("Ë", "&Euml;");
		sSalida = sSalida.replaceAll("è", "&egrave;");
		sSalida = sSalida.replaceAll("ê", "&ecirc;");
		sSalida = sSalida.replaceAll("ë", "&euml;");
		sSalida = sSalida.replaceAll("Ì", "&Igrave;");
		sSalida = sSalida.replaceAll("Î", "&Icirc;");
		sSalida = sSalida.replaceAll("Ï", "&Iuml;");
		sSalida = sSalida.replaceAll("ì", "&igrave;");
		sSalida = sSalida.replaceAll("î", "&icirc;");
		sSalida = sSalida.replaceAll("ï", "&iuml;");
		sSalida = sSalida.replaceAll("Ò", "&Ograve;");
		sSalida = sSalida.replaceAll("Ô", "&Ocirc;");
		sSalida = sSalida.replaceAll("Ö", "&Ouml;");
		sSalida = sSalida.replaceAll("ò", "&ograve;");
		sSalida = sSalida.replaceAll("ô", "&ocirc;");
		sSalida = sSalida.replaceAll("ö", "&ouml;");
		sSalida = sSalida.replaceAll("Ù", "&Ugrave;");
		sSalida = sSalida.replaceAll("Û", "&Ucirc;");
		sSalida = sSalida.replaceAll("Ü", "&Uuml;");
		sSalida = sSalida.replaceAll("ù", "&ugrave;");
		sSalida = sSalida.replaceAll("û", "&ucirc;");
		sSalida = sSalida.replaceAll("ü", "&uuml;");
		sSalida = sSalida.replaceAll("ý", "&yacute;");
		sSalida = sSalida.replaceAll("Ý", "&Yacute;");
		sSalida = sSalida.replaceAll("ÿ", "&yuml;");
		sSalida = sSalida.replaceAll("ç", "&ccedil;");
		sSalida = sSalida.replaceAll("Ç", "&Ccedil;");
		//sSalida = sSalida.replaceAll("'", "&apos;");
		sSalida = sSalida.replaceAll("\r\n", "<BR>");
		sSalida = sSalida.replaceAll("\n\r", "<BR>");
		sSalida = sSalida.replaceAll("\n", "<BR>");
		return sSalida;
	}

}
