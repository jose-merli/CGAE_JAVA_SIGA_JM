package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgeTipocalendarioSqlProvider;

public class AgeTipocalendarioSqlExtendsProvider extends  AgeTipocalendarioSqlProvider{

	
	public String getCalendarType(String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("IDTIPOCALENDARIO");
		sql.SELECT("DESCRIPCION");
		sql.FROM("AGE_TIPOCALENDARIO");
//		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS REC ON (REC.IDRECURSO = tipocalendario.DESCRIPCION " + 
//				"		AND REC.IDLENGUAJE = '"+idLenguaje+"')"); Añadir cuando guardemos la traducción
		sql.ORDER_BY("DESCRIPCION");
		return sql.toString();
	}
	
}
