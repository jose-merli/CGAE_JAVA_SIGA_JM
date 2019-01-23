package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ModClasecomunicacionRutaExtendsSqlProvider {

   public String selectClasesComunicacion (String rutaClaseComunicacion){
	   SQL sql = new SQL();
	   
	   sql.SELECT("CLASE.NOMBRE AS LABEL, RUTACLASE.IDCLASECOMUNICACION AS VALUE");
	   sql.FROM("MOD_CLASECOMUNICACION_RUTA RUTACLASE");
	   sql.JOIN("MOD_CLASECOMUNICACIONES CLASE ON CLASE.IDCLASECOMUNICACION = RUTACLASE.IDCLASECOMUNICACION");
	   sql.WHERE("RUTACLASE.RUTA ='" + rutaClaseComunicacion + "'");
	   return sql.toString();
   }
}