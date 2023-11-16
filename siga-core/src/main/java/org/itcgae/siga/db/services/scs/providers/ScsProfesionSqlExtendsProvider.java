package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsProfesionSqlProvider;

public class ScsProfesionSqlExtendsProvider extends ScsProfesionSqlProvider {

	public String getProfesiones(String idLenguaje) {

		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("profesion.IDPROFESION");
		sql.SELECT("f_siga_getrecurso(profesion.descripcion,"+idLenguaje+") DESCRIPCION");
		sql.FROM("scs_profesion profesion");
		sql.WHERE("profesion.fecha_baja is null");

		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") AS consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}

}
