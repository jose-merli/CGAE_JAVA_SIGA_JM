package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsMaestroestadosejgSqlProvider;

public class ScsEstadoEJGSqlExtendsProvider extends ScsMaestroestadosejgSqlProvider {

	public String comboEstadoEjg(Short idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("estado.IDESTADOEJG");
		sql.SELECT("cat.descripcion");
		sql.FROM("SCS_MAESTROESTADOSEJG estado");
		sql.INNER_JOIN(
				"gen_recursos_catalogos cat on cat.IDRECURSO = estado.descripcion and cat.idlenguaje = '"
						+ idLenguaje + "'");
		sql.WHERE("estado.fecha_baja is null");
		sql.ORDER_BY("cat.descripcion");

		return sql.toString();
	}
	
}
