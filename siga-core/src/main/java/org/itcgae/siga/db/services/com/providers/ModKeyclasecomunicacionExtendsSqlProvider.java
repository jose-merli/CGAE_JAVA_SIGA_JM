package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ModKeyclasecomunicacionExtendsSqlProvider {
	
	public String selectKeyClase(Short idClaseComunicacion){
		
		SQL sql = new SQL();		
				
		sql.SELECT("keyClase.IDCLASECOMUNICACION");
		sql.SELECT("keyClase.IDKEY");
		sql.SELECT("keyClase.TABLA");
		sql.SELECT("key.NOMBRE");
		
		sql.FROM("MOD_KEYCLASECOMUNICACION keyClase");
		
		sql.INNER_JOIN("MOD_CAMPOSKEY key ON key.IDKEY = keyClase.IDKEY");
		sql.WHERE("keyClase.IDCLASECOMUNICACION = " + idClaseComunicacion);
		
		sql.ORDER_BY("key.IDKEY");
		
		return sql.toString();
	}

}
