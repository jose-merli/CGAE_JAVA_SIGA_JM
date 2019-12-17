package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsPrestacionSqlProvider;

public class ScsPrestacionSqlExtendsProvider extends ScsPrestacionSqlProvider {
	public String comboPrestaciones(String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("prestacion.IDPRESTACION");
		sql.SELECT("catalogoPrestacion.DESCRIPCION");

		sql.FROM("scs_prestacion prestacion");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS catalogoPrestacion on catalogoPrestacion.idrecurso = prestacion.DESCRIPCION and catalogoPrestacion.idlenguaje ="+idLenguaje);
		sql.WHERE("prestacion.fecha_baja is null");
		sql.WHERE("prestacion.idinstitucion = "+idInstitucion);
		sql.ORDER_BY("descripcion");
		return sql.toString();
	}
}
