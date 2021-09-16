/*
 * Created on 11-nov-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.itcgae.siga.commons.utils;


import org.apache.log4j.Logger;
import org.itcgae.siga.commons.constants.SigaConstants;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author daniel.campos
 * <p>
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * Modificada el 22/04/2005 por david.sanchez para incluir tratarImporte().
 */
public class UtilidadesString {


    private static Logger LOGGER = Logger.getLogger(UtilidadesString.class);

    static public String getMensajeIdioma(String idioma, String key) {

        Properties prop = new Properties();//(Properties)idiomas.get(idi);
		/*if (prop == null)	{
		}*/
        String aux = prop.getProperty(key, key);
        if (aux.equals(key)) {
            return aux;
        } else {
            return caracteresJavascript(aux);
        }
    }

    /**
     * Sustituye comillas simples por apostrofo para javascript. El problema catalán.
     *
     * @param sCadena a traducir
     * @return
     */

    private static String caracteresJavascript(String sCadena) {
        if (sCadena == null) return "";

        sCadena = sCadena.replaceAll("'", "´");
        sCadena = sCadena.replaceAll("\"", "´");

        return sCadena;
    }

    /**
     * Valida el campo url/ruta de cualquier formulario.
     *
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

    public static String getPrimeraMayuscula(String cadena) {
        if (cadena != null)
            return cadena.substring(0, 1).toUpperCase() + cadena.substring(1).toLowerCase();
        else
            return "";

    }


    /**
     * Funci�n que elimina acentos y caracteres especiales de una cadena de texto.
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
        }// for i
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
        String t[] = {texto};
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
            if (nif.toUpperCase().startsWith("X") || nif.toUpperCase().startsWith("Y") || nif.toUpperCase().startsWith("Z")) {
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

}
