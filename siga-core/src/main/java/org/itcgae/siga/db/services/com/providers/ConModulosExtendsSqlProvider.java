package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ConModulosExtendsSqlProvider {

	
public String selectModulos(){
		
		SQL sql = new SQL();
		sql.SELECT_DISTINCT("modulo.IDMODULO,modulo.NOMBRE ");
		sql.FROM("CON_MODULO modulo");
		sql.ORDER_BY("modulo.NOMBRE");
		
		return sql.toString();
	}
}
