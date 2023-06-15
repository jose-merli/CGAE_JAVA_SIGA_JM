package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipoejgSqlProvider;

public class ScsTipoEJGSqlExtendsProvider extends ScsTipoejgSqlProvider {

	public String comboTipoEjg(Short idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("tipoejg.IDTIPOEJG");
		sql.SELECT("cat.descripcion");
		sql.FROM("SCS_TIPOEJG tipoejg");
		sql.INNER_JOIN(
				"gen_recursos_catalogos cat on cat.IDRECURSO = tipoejg.descripcion and cat.idlenguaje = '"
						+ idLenguaje + "'");
		sql.WHERE("tipoejg.fecha_baja is null");
		sql.ORDER_BY("cat.descripcion");

		return sql.toString();
	}
	
}
