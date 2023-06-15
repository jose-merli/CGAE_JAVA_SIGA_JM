package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsPreceptivoSqlProvider;

public class ScsPreceptivoSqlExtendsProvider extends ScsPreceptivoSqlProvider{
	
	public String comboPreceptivo(String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("preceptivo.IDPRECEPTIVO");
		sql.SELECT("catalogoPreceptivo.DESCRIPCION");

		sql.FROM("scs_preceptivo preceptivo");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogoPreceptivo on catalogoPreceptivo.idrecurso = preceptivo.DESCRIPCION and catalogoPreceptivo.idlenguaje ="+idLenguaje);
		sql.WHERE("preceptivo.fecha_baja is null");
		sql.ORDER_BY("descripcion");
		return sql.toString();
	}
}
