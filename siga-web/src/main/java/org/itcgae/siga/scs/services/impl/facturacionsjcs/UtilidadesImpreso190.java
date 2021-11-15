package org.itcgae.siga.scs.services.impl.facturacionsjcs;

import java.util.ArrayList;
import java.util.List;

public class UtilidadesImpreso190 {
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
	    		//ClsLogging.writeFileLogWithoutSession("replaceFirstIgnoreCase.returnmenosuno");
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
}
