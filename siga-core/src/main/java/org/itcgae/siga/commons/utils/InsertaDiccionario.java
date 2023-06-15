package org.itcgae.siga.commons.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;

public class InsertaDiccionario {

	private static Connection con = null;
	private static ResultSet rs = null;
	private static Statement st = null;

	public static void main(String[] args) throws Exception {
     
	try {
		String file = null;
		
		if (args == null || args.length == 0) {
			InputStreamReader isr=new InputStreamReader(System.in);
			BufferedReader br=new BufferedReader(isr);	        
	        System.out.println("No se ha pasado como parámetro la ruta y nombre del fichero properties. Ruta [/messages.properties]:");
		    file = br.readLine();
		    if (file == null || file.trim().equals("")) {
		    	file = "/messages.properties";
		    }
		} else {
			file = args[0];
		}
     	
	    conecta();
     				
		Properties properties = new Properties();
	    properties.load(new FileInputStream(file));
	    
	    Enumeration enumer = properties.keys();

	    StringBuffer trace = new StringBuffer();

	    while (enumer.hasMoreElements()) {
	    	String clave = (String) enumer.nextElement();
	    	String valor = (String)properties.getProperty(clave);
	    	if (valor!=null) {
	    	    if (!existeEnDiccionario(clave)) {
		    		//trace.append("\n delete GEN_DICCIONARIO where IDRECURSO='"+clave+"';");
	    	    	insertGenDiccionario(trace, clave, valor);
	    	    } else {
	    	        // caso de pregunta o update
	    		    InputStreamReader isr=new InputStreamReader(System.in);
	    	        BufferedReader br=new BufferedReader(isr);
	    	        System.out.println("Se ha encontrado la clave "+clave+" en GEN_DICCIONARIO");
	    	        System.out.println("Es una modificación de recurso? (s/n)");
	    		    String texto = br.readLine();
	    	        if (texto.equals("s")) {
	    	        	updateGenDiccionario(trace, clave, valor);
	    	        } else {
	    	            System.out.println("ATENCIÓN!!! Estas usando un idrecurso en GEN_DICCIONARIO que ya existe. TE LO VAS A CARGAR!!");
	    	            System.out.println("Anda, empieza de nuevo. La clave duplicada es "+clave);
	    	            System.out.println("Adiós");
	    	            break;
	    	            
	    	        }
	    	        
	    	    }
	    	    
	    	}
	    }
	    try {
		    if (args.length > 1 && args[1]!=null) {
			    PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(args[1], false)));
		        printer.println(trace.toString());
		        printer.flush();
		        printer.close();
		    } else {
		    	System.out.println("NO EXISTE FICHERO DE SALIDA");		    	
		    }
		
	    } catch (IOException e) {
	    	System.out.println(e.toString());
	    	throw e;
        }
	    
	    System.out.println(trace);
	    System.out.println("fin correcto");

		
	} finally {
		if (con != null) {
			con.close();
		}
	}
	}

	private static void conecta() throws Exception {		
		String cadenaConexion = "jdbc:oracle:thin:@192.168.18.18:1546/SIGADES";
		String usuario = "uscgae_int";
		String clave = "oradesaINT19";

		con = null;
		Class.forName(oracle.jdbc.driver.OracleDriver.class.getName());
		con = DriverManager.getConnection(cadenaConexion, usuario, clave);
	}

	private static ResultSet consulta(String query) throws Exception {
		st = null;
		st = con.createStatement();
		rs = st.executeQuery(query);
		return rs;

	}

	private static boolean existeEnDiccionario(String id) throws Exception {
		try {

			String query = "SELECT IDRECURSO FROM GEN_DICCIONARIO WHERE upper(IDRECURSO)=UPPER('"
					+ id + "')";
			rs = consulta(query);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}

		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				st.close();
			} catch (Exception e) {
			}
		}
	}
	
	private static void insertGenDiccionario(StringBuffer trace, String clave, String valor) {
		trace.append("\ninsert into GEN_DICCIONARIO (IDRECURSO, DESCRIPCION, ERROR, IDLENGUAJE, FECHAMODIFICACION, USUMODIFICACION, IDPROPIEDAD) values ('"+clave+"', '"+valor+"', 0, '1', sysdate, 0, '19');");
		trace.append("\ninsert into GEN_DICCIONARIO (IDRECURSO, DESCRIPCION, ERROR, IDLENGUAJE, FECHAMODIFICACION, USUMODIFICACION, IDPROPIEDAD) values ('"+clave+"', '"+valor+"#CA', 0, '2', sysdate, 0, '19');");
		trace.append("\ninsert into GEN_DICCIONARIO (IDRECURSO, DESCRIPCION, ERROR, IDLENGUAJE, FECHAMODIFICACION, USUMODIFICACION, IDPROPIEDAD) values ('"+clave+"', '"+valor+"#EU', 0, '3', sysdate, 0, '19');");
		trace.append("\ninsert into GEN_DICCIONARIO (IDRECURSO, DESCRIPCION, ERROR, IDLENGUAJE, FECHAMODIFICACION, USUMODIFICACION, IDPROPIEDAD) values ('"+clave+"', '"+valor+"#GL', 0, '4', sysdate, 0, '19');");
		trace.append("\n");
		
	}

	private static void updateGenDiccionario(StringBuffer trace, String clave, String valor) {
		trace.append("\nupdate GEN_DICCIONARIO set fechamodificacion=sysdate, DESCRIPCION='"+valor+"' where idrecurso='"+clave+"' and idlenguaje='1';"); 
		trace.append("\nupdate GEN_DICCIONARIO set fechamodificacion=sysdate, DESCRIPCION='"+valor+"#CA' where idrecurso='"+clave+"' and idlenguaje='2';"); 
		trace.append("\nupdate GEN_DICCIONARIO set fechamodificacion=sysdate, DESCRIPCION='"+valor+"#EU' where idrecurso='"+clave+"' and idlenguaje='3';"); 
		trace.append("\nupdate GEN_DICCIONARIO set fechamodificacion=sysdate, DESCRIPCION='"+valor+"#GL' where idrecurso='"+clave+"' and idlenguaje='4';"); 
		trace.append("\n");
		
	}

}
