package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ConClaseComunicacionesExtendsSqlProvider {
	
	public String selectClaseComunicaciones (){
		
		SQL sql = new SQL();
		
		sql.SELECT("IDCLASECOMUNICACION");
		sql.SELECT("NOMBRE");
		
		sql.FROM("MOD_CLASECOMUNICACION");
		
		return sql.toString();
	}

}
