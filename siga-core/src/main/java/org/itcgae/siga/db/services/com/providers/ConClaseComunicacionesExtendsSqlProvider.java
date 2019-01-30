package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ConClaseComunicacionesExtendsSqlProvider {
	
	public String selectClaseComunicaciones (){
		
		SQL sql = new SQL();
		
		sql.SELECT("IDCLASECOMUNICACION");
		sql.SELECT("NOMBRE");
		
		sql.FROM("MOD_CLASECOMUNICACIONES");
		
		return sql.toString();
	}
	
	public String selectClaseComunicacionesByModulo(String idModulo){
		
		SQL sql = new SQL();
		
		sql.SELECT("IDCLASECOMUNICACION");
		sql.SELECT("NOMBRE");
		
		sql.FROM("MOD_CLASECOMUNICACIONES");
		
		sql.WHERE("IDMODULO = '" + idModulo + "'");
		
		return sql.toString();
	}
}
