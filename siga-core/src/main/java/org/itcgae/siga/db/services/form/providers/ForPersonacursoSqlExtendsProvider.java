package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForPersonaCursoSqlProvider;

public class ForPersonacursoSqlExtendsProvider extends ForPersonaCursoSqlProvider{

	public String getTrainers(String idInstitucion, String idCurso) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("cur.IDPERSONA");
		sql.SELECT("CONCAT(per.NOMBRE || ' ' || per.APELLIDOS1 || ' ', per.APELLIDOS2) AS nombre");
		sql.FROM("FOR_PERSONA_CURSO cur");
		sql.INNER_JOIN("CEN_PERSONA per ON (per.idpersona = cur.idpersona)");
		sql.WHERE("cur.IDCURSO = '" + idCurso + "'");
		sql.WHERE("cur.IDINSTITUCION = '" + idInstitucion + "'");
		sql.ORDER_BY("nombre");
		
		return sql.toString();
	}
	
}
