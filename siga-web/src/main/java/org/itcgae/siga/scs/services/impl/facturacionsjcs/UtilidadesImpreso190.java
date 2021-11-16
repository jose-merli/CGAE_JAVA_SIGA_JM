package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.openxml4j.opc.internal.FileHelper;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.PropertyReader;
import org.itcgae.siga.commons.utils.ReadProperties;
import org.itcgae.siga.commons.utils.SIGAReferences;
import org.itcgae.siga.commons.utils.SIGAReferences.RESOURCE_FILES;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.services.adm.mappers.GenParametrosExtendsMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UtilidadesImpreso190 {
	
	private static final SimpleDateFormat sdfLong = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
	private static final SimpleDateFormat sdfShort = new SimpleDateFormat("yyyyMMdd"); 
	private static final String sError = "\n***** ERROR ***** ";
	private static String fileName;
	private static int loglevel=10;
	
	private static boolean iniciado=false;
	private static boolean bStoreFile; //Indica si se escribir� en el fichero de log de la capa b�sica.
	private static boolean bConsole=true;   //Indica si se escribir� en la consola. 
	private static boolean bLog4j;     //Indica si se escribir� en el fichero de log de log4j.
	private static boolean bLogXeMail;     //Indica si se escribir� en el fichero de log de log4j.
	
	//private static Logger logger=null;
	private static Logger logger = null;
	private static Logger logXeMail = null;
	
	@Autowired
	private GenParametrosExtendsMapper genParametrosExtendsMapper;
	
	// quita los acentos
			public static String quitarAcentos (String cadena) {
				
				cadena = cadena.replaceAll("Á","A");
				cadena = cadena.replaceAll("É","E");
				cadena = cadena.replaceAll("Í","I");
				cadena = cadena.replaceAll("Ó","O");
				cadena = cadena.replaceAll("Ú","U");
				
				cadena = cadena.replaceAll("À","A");
				cadena = cadena.replaceAll("È","E");
				cadena = cadena.replaceAll("Ì","I");
				cadena = cadena.replaceAll("Ò","O");
				cadena = cadena.replaceAll("Ù","U");
				
				cadena = cadena.replaceAll("Ä","A");
				cadena = cadena.replaceAll("Ë","E");
				cadena = cadena.replaceAll("Ï","I");
				cadena = cadena.replaceAll("Ö","O");
				cadena = cadena.replaceAll("Ü","U");
				
				cadena = cadena.replaceAll("Â","A");
				cadena = cadena.replaceAll("Ê","E");
				cadena = cadena.replaceAll("Î","I");
				cadena = cadena.replaceAll("Ô","O");
				cadena = cadena.replaceAll("Û","U");
				
				return cadena;	
			}
	
	public static String replaceAllIgnoreCase (String texto, String clave, String valor) 
    {
		String t[] = {texto}; 
    	int i = 0;
		while(true){
			i = UtilidadesImpreso190.replaceFirstIgnoreCase(t, clave, valor, i);
    		if (i < 0) 
    			return t[0];
    	} 
    }
	
	 private static int replaceFirstIgnoreCase (String texto[], String clave, String valor, int posIni)
	    {
	    	if (texto==null || texto.length < 0 ||clave ==null||valor ==null){ 
	    		//writeFileLogWithoutSession("replaceFirstIgnoreCase.returnmenosuno");
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
	 
	 public void writeFileLogWithoutSession(String s, int i)
		{
		 //init();
			PrintWriter printer = null;
			try
			{
				Date dat = Calendar.getInstance().getTime();

				if (bConsole)
				{
					System.out.println(sdfLong.format(dat)+s);
				}

				if (bLog4j && logger!=null)
				{
					logger.info(s);
				}

				if(bStoreFile)
				{
					printer = new PrintWriter(new BufferedWriter(new FileWriter(fileName+sdfShort.format(dat) +".out", true)));
					printer.println(sdfLong.format(dat)+ s);
					printer.flush();
					printer.close();
				} 
			} catch(Exception _ex) {
				System.out.println("Error Escribiendo Log :"+_ex.toString());
			} finally {
			    try {
			        printer.flush();
					printer.close();
			    } catch (Exception eee) {}
			}
		}
	 
	 public static String validarModelo190 (String nombre) 
	    {
	    	// Primero tenemos que convertir a validos aquellos caracteres que queramos conservar
	    	nombre = replaceAllIgnoreCase(nombre, "ª", "A");
	    	nombre = replaceAllIgnoreCase(nombre, "º", "O");
	    	   	
	    	// Caracteres validos segun la aplicacion Informativas Modelo 190 de AEAT-> QWERTYUIOP ASDFGHJKLÑ´Ç ZXCVBNM,.- 
	    	String caracteresValidos = "QWERTYUIOP ASDFGHJKLÑ´Ç ZXCVBNM,.- ";
	    	String nombreValido = "";
	    	
	    	for (int i=0; i<nombre.length(); i++){
	    	
	    		if( caracteresValidos.indexOf(nombre.charAt(i)) >= 0){
	    			nombreValido += nombre.charAt(i);
	    		}else{
	    			nombreValido += " ";
	    		}
	    	}
	    	
	    	
	    	nombre = replaceAllIgnoreCase(nombre, "  ", " ");
	    	nombre = replaceAllIgnoreCase(nombre, "  ", " ");

	    	return nombreValido;
	    }
	 
	
			public static List<String> desdoblarDouble (Double valor) {
				List<String> salida= new ArrayList<String>();
				
				String signo = " ";
				
				if (valor == null) {
					salida.add("");
					salida.add("");
					salida.add("");
				} else {
					String sValor = null;
					if (valor.doubleValue()<0) {
						sValor = String.format("%.2f",valor).replace(",", ".");
						// le quitamos el signo
						sValor = sValor.substring(1,sValor.length());
						signo = "N";
					} else {
						sValor = String.format("%.2f",valor).replace(",", ".");
					}
						
					salida.add(signo);

					int pos = sValor.indexOf(".");
					if (pos!=-1) {
						// tiene punto
						salida.add(sValor.substring(0,pos));
						salida.add(sValor.substring(pos+1,sValor.length()));
					} else {
						// NO tiene punto
						salida.add(sValor);
						salida.add("");
					}
				}
				
				return salida;	
			}
			
			public static String formatea (Object datoOrig, int longitud, boolean numerico) {
				String salida= "";
				if (datoOrig==null) {
					if (numerico) {
						salida = relleno("0",longitud);  
					} else {
						salida = relleno(" ",longitud);
					}
				} else {
					String dato = datoOrig.toString();
					if (dato.length()==0) {
						if (numerico) {
							salida = relleno("0",longitud);  
						} else {
							salida = relleno(" ",longitud);
						}
					} else
					if (dato.length()>longitud) {
						// mayor
						if (numerico) {
							salida = dato.substring(dato.length() - longitud, dato.length());  
						} else {
							salida = dato.substring(0, longitud);
						}
					} else
					if (dato.length()<longitud) {
						// menor
						if (numerico) {
							salida = relleno("0",longitud - dato.length()) + dato;  
						} else {
							salida = dato + relleno(" ",longitud - dato.length());
						}
					} else {
						// es igual
						salida = dato;
					}
				}	
				
				salida = salida.toUpperCase();
				salida = UtilidadesImpreso190.quitarAcentos(salida);
				return salida;	
			}
			
			public static String rellenoPosiciones (String caracter, int inicio, int fin) {
				return relleno(caracter, Math.abs(fin-inicio)+1);
			}
			
			
			public static String relleno (String caracter, int longitud){
				String salida= "";
				
				for (int i=0;i<longitud;i++) {
					salida += caracter;
				}
				
				return salida;	
			}
			
			public static String formateaDecimal (Object datoOrig, int longitud) {
				String salida= datoOrig.toString();
				if (datoOrig==null) {
					salida += relleno("0",longitud);  
				} 
				else {
					salida += relleno("0",longitud - salida.length());
				}
				return salida;
			}
			
			public static String getTipoIdentificacion(int tipo){
				String sal = "";
				
				switch(tipo) {
					case SigaConstants.TIPO_IDENTIFICACION_NIF: sal="NIF";
					case SigaConstants.TIPO_IDENTIFICACION_CIF: sal="CIF";
					case SigaConstants.TIPO_IDENTIFICACION_OTRO: sal="OTRO";
					case SigaConstants.TIPO_IDENTIFICACION_PASAPORTE: sal="PASAPORTE";
					case SigaConstants.TIPO_IDENTIFICACION_TRESIDENTE: sal="NIE";
				}
				
				return sal;
			}
		

}
