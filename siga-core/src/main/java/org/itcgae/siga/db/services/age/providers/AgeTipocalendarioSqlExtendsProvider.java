package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgeTipocalendarioSqlProvider;

public class AgeTipocalendarioSqlExtendsProvider extends  AgeTipocalendarioSqlProvider{

	
	public String getCalendarType(String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("tipocalendario.IDTIPOCALENDARIO");
		sql.SELECT("rec.DESCRIPCION");
		sql.FROM("AGE_TIPOCALENDARIO tipocalendario");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON (REC.IDRECURSO = tipocalendario.DESCRIPCION " + 
				"		AND REC.IDLENGUAJE = '" + idLenguaje + "')"); 
		sql.ORDER_BY("tipocalendario.IDTIPOCALENDARIO");
		return sql.toString();
	}
	
}
