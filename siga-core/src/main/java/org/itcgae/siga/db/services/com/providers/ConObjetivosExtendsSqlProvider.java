package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ConObjetivosExtendsSqlProvider {

	public String selectObjetivos(String idLenguaje){
		
		SQL sql = new SQL();
		sql.SELECT_DISTINCT("objetivo.IDOBJETIVO, rec.DESCRIPCION");
		sql.FROM("CON_OBJETIVO objetivo");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = objetivo.NOMBRE AND rec.IDLENGUAJE = '"+ idLenguaje + "')");
		sql.ORDER_BY("rec.DESCRIPCION");
		
		return sql.toString();
	}
}
