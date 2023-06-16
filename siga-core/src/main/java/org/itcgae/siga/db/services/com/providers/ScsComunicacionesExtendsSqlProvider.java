package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ScsComunicacionesExtendsSqlProvider {
	
	public String nextIdComunicacion (){
		
		SQL sql = new SQL();
		sql.SELECT("MAX(IDCOMUNICACION) + 1 as ID");
		sql.FROM("scs_comunicaciones");
		
		return sql.toString();
		
	}
	
}
