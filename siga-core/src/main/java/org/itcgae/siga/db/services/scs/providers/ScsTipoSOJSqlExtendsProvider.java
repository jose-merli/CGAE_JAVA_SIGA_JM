package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTiposojSqlProvider;

public class ScsTipoSOJSqlExtendsProvider extends ScsTiposojSqlProvider {

	public String comboTipoSOJ(Short idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("tiposoj.IDTIPOSOJ");
		sql.SELECT("cat.descripcion");
		sql.FROM("SCS_TIPOSOJ tiposoj");
		sql.INNER_JOIN(
				"gen_recursos_catalogos cat on cat.IDRECURSO = tiposoj.descripcion and cat.idlenguaje = '"
						+ idLenguaje + "'");
		sql.WHERE("tiposoj.fecha_baja is null");
//		sql.WHERE("tiposoj.idinstitucion = " + idInstitucion);
		sql.ORDER_BY("cat.descripcion");

		return sql.toString();
	}
	
}
