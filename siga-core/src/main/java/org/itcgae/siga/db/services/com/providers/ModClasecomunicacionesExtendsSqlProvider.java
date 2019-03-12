package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ModClasecomunicacionesExtendsSqlProvider {

   public String selectClaseComunicacionModulo (String idModeloComunicacion){
	   SQL sql = new SQL();
	   
	   sql.SELECT("CLASE.IDCLASECOMUNICACION, CLASE.RUTAPLANTILLA, CLASE.NOMBRE");
	   sql.FROM("MOD_CLASECOMUNICACIONES CLASE");
	   sql.JOIN("MOD_MODELOCOMUNICACION MODELO ON CLASE.IDCLASECOMUNICACION = MODELO.IDCLASECOMUNICACION");
	   sql.WHERE("MODELO.IDMODELOCOMUNICACION ='" + idModeloComunicacion + "'");
	   return sql.toString();
   }
   

}