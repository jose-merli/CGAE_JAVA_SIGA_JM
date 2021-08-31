package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsSituacionSqlProvider;

public class ScsSituacionSqlExtendsProvider extends ScsSituacionSqlProvider{
	
	public String comboSituaciones(String idLenguaje) {
		SQL sql = new SQL();
		
		sql.SELECT("situacion.IDsituacion");
		sql.SELECT("catalogoSituacion.DESCRIPCION");

		sql.FROM("scs_situacion situacion");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogoSituacion on catalogoSituacion.idrecurso = situacion.DESCRIPCION and catalogoSituacion.idlenguaje ="+idLenguaje);
		sql.WHERE("situacion.fecha_baja is null");
		sql.ORDER_BY("descripcion");
		
		return sql.toString();
	}

}
