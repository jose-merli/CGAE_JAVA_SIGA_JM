package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsProfesionSqlProvider;

public class ScsProfesionSqlExtendsProvider extends ScsProfesionSqlProvider {

	public String getProfesiones(String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("profesion.IDPROFESION");
		sql.SELECT("rec.DESCRIPCION");
		sql.FROM("scs_profesion profesion");
		sql.INNER_JOIN(
				"gen_recursos_catalogos rec on rec.IDRECURSO = profesion.descripcion and rec.idlenguaje = '"
						+ idLenguaje + "'");
		sql.WHERE("profesion.fecha_baja is null");
		sql.ORDER_BY("rec.descripcion");

		return sql.toString();
	}

}
