package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForPersonaCursoSqlProvider;

public class ForPersonacursoSqlExtendsProvider extends ForPersonaCursoSqlProvider {

	public String getTrainersLabels(String idInstitucion, String idCurso) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("cur.IDPERSONA");
		sql.SELECT("CONCAT(per.NOMBRE || ' ' || per.APELLIDOS1 || ' ', per.APELLIDOS2) AS nombre");
		sql.FROM("FOR_PERSONA_CURSO cur");
		sql.INNER_JOIN("CEN_PERSONA per ON (per.idpersona = cur.idpersona)");
		sql.WHERE("cur.IDCURSO = '" + idCurso + "'");
		sql.WHERE("cur.FECHABAJA IS NULL");
		sql.WHERE("cur.IDINSTITUCION = '" + idInstitucion + "'");
		sql.ORDER_BY("nombre");

		return sql.toString();
	}
	
	public String getTrainersSession(String idEvento) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("perev.IDPERSONA");
		sql.SELECT("CONCAT(per.NOMBRE || ' ' || per.APELLIDOS1 || ' ', per.APELLIDOS2) AS nombre");
		sql.FROM("AGE_PERSONA_EVENTO perev");
		sql.INNER_JOIN("CEN_PERSONA per ON (per.idpersona = perev.idpersona)");
		sql.WHERE("perev.fechabaja is null");
		sql.WHERE("perev.idevento = '" + idEvento + "'");
		sql.ORDER_BY("nombre");

		return sql.toString();

	}

	public String getTrainersCourse(String idInstitucion, String idCurso, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("cur.IDFORMADOR");
		sql.SELECT_DISTINCT("cur.IDPERSONA");
		sql.SELECT("per.NOMBRE");
		sql.SELECT("CONCAT(per.APELLIDOS1 || ' ', per.APELLIDOS2) AS apellidos");
		sql.SELECT("cur.idcurso");
		sql.SELECT("cur.tarifa");
		sql.SELECT("cur.idtipocoste");
		sql.SELECT("rec.descripcion as tipocoste");
		sql.SELECT("cur.idrol as idrol");
		sql.SELECT("rec2.descripcion as rol");
		sql.SELECT("cur.tutor");
		sql.SELECT("cur.fechabaja");
		sql.FROM("FOR_PERSONA_CURSO cur");
		sql.INNER_JOIN("CEN_PERSONA per ON (per.idpersona = cur.idpersona)");
		sql.INNER_JOIN("FOR_TIPOCOSTE tip ON (tip.idtipocoste = cur.idtipocoste)");
		sql.LEFT_OUTER_JOIN("FOR_ROLES rol ON (rol.idrol = cur.idrol)");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tip.DESCRIPCION AND rec.IDLENGUAJE = '" + idLenguaje + "')");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS rec2 ON rol.DESCRIPCION = rec2.IDRECURSO");
		sql.WHERE("cur.IDCURSO = '" + idCurso + "'");
		sql.WHERE("cur.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("cur.FECHABAJA IS NULL");
		sql.ORDER_BY("apellidos");

		return sql.toString();
	}

}
