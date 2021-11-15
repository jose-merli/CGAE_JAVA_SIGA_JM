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
	    		// Si el caracter del nombre esta entre los validos
	    		if( caracteresValidos.indexOf(nombre.charAt(i)) >= 0){
	    			nombreValido += nombre.charAt(i);
	    		}else{
	    			nombreValido += " ";
	    		}
	    	}
	    	
	    	// Finalmente quitamos espacios dobles y triples (ocurre al quitar ciertos caracteres)
	    	nombre = replaceAllIgnoreCase(nombre, "  ", " ");
	    	nombre = replaceAllIgnoreCase(nombre, "  ", " ");

	    	return nombreValido;
	    }
	 
	// vector con el signo, la parte entera y la parte decimal (dos posiciones decimales) para el impreso 190
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
			
			/** 
			 * Crea un string de longitud x relleno de caracteres para el impreso 190
			 * @param caracter
			 * @param longitud
			 * @return
			 * @throws ClsExceptions
			 */
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
		
//			private static synchronized void init() {
//		
//				if(!iniciado) {
//					iniciado=true;
//					//ClsLogging.writeFileLog("CLSLOGGING INIT() SE EJECUTA",3);
//				    ReadProperties rp= new ReadProperties(SIGAReferences.RESOURCE_FILES.SIGA);
////					ReadProperties rp=new ReadProperties("SIGA.properties");
//
//					//LMS 29/08/2006
//					//Esto viene de la capa b�sica, pero se ha adaptado a SIGA.
//					//bStoreFile=rp.returnProperty("LOG.run").equals("1");
//					bStoreFile=rp.returnProperty("LOG.fichero").equals("1");
//					bConsole=rp.returnProperty("LOG.consola").equals("1");
//					bLog4j=rp.returnProperty("LOG.log4j").equals("1");
//					bLogXeMail=rp.returnProperty("LOG.email").equals("1");
//					
//					try {
//						StringBuilder strBld = new StringBuilder();
//						strBld.append(SIGAReferences.RESOURCE_FILES.WEB_INF_DIR.getFileName())
//								.append(rp.returnProperty("LOG.dir")).append("/").append(rp.returnProperty("LOG.name"));
//						fileName=SIGAReferences.getReference(strBld.toString());
//					} catch (Exception e){
//						e.printStackTrace();
//					}
//					
//					try{
//						loglevel = Integer.parseInt(rp.returnProperty("LOG.level").trim());
//					}catch (Exception nfe){
//						nfe.printStackTrace();
//					}
//					
//					try{
//						PropertyConfigurator.configure(PropertyReader.getProperties(RESOURCE_FILES.SIGA.LOG4J));
//						logger = Logger.getLogger("SIGA");
//					}catch(Exception e){
//						logger=null;
//						e.printStackTrace();
//					}
//					
//					try{
//						logXeMail = Logger.getLogger("EMAIL");
//					}catch(Exception e){
//						logXeMail=null;
//						e.printStackTrace();
//					}
//					
//					//System.out.println("GESTION DEL LOG: logLevel Init="+loglevel);
//					ClsLogging.writeFileLog("--------------------",3);
//					ClsLogging.writeFileLog("Info del LOG de SIGA",3);
//					ClsLogging.writeFileLog("--------------------",3);
//					ClsLogging.writeFileLog("- Fichero: " + (bStoreFile?"ACTIVADO":"DESACTIVADO"),3);
//					ClsLogging.writeFileLog("- Consola: " + (bConsole?"ACTIVADA":"DESACTIVADA"),3);
//					ClsLogging.writeFileLog("- Log4j:   " + (bLog4j?"ACTIVADO":"DESACTIVADO"),3);
//					ClsLogging.writeFileLog("- LogXeMail:   " + (bLogXeMail?"ACTIVADO":"DESACTIVADO"),3);
//					ClsLogging.writeFileLog("--------------------",3);
//				}
//			}
//			
//			public static void reset(){
//				iniciado = false;
//				init();
//			}
//			
//			public static void writeFileLog(String s, int i) {
//				writeFileLog(s, null, i);  
//			}
//			
//			public static void writeFileLog(String s) {
//				init();
//				writeFileLogWithoutSession(s);  
//			}
//			
//			public static void writeFileLog(String s, HttpServletRequest httpservletrequest, int i)
//			{
//				if (httpservletrequest==null)
//				{
//					init();
//					if(i <= loglevel)  {
//						writeFileLogWithoutSession(s,i);
//					}
//				} else {
//					HttpSession httpsession = httpservletrequest.getSession(true);
//					writeFileLog(s, httpservletrequest.getHeader("user-agent"), httpsession.getId(),
//							(UsrBean)httpsession.getAttribute("USRBEAN"),
//							httpservletrequest.getRemoteAddr(), i, false);
//				}
//			}
}
