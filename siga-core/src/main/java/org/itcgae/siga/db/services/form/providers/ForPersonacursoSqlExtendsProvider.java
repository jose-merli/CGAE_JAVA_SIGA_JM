package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.form.FormadorCursoItem;
import org.itcgae.siga.db.mappers.ForPersonaCursoSqlProvider;

public class ForPersonacursoSqlExtendsProvider extends ForPersonaCursoSqlProvider {

	public String getTrainersLabels(String idInstitucion, String idCurso, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("cur.IDPERSONA");
		sql.SELECT("CONCAT(per.NOMBRE || ' ' || per.APELLIDOS1 || ' ', per.APELLIDOS2) AS nombre");
		sql.SELECT("rec.descripcion as rol");
		sql.FROM("FOR_PERSONA_CURSO cur");
		sql.INNER_JOIN("CEN_PERSONA per ON (per.idpersona = cur.idpersona)");
		sql.LEFT_OUTER_JOIN("FOR_ROLES rol ON (rol.idrol = cur.idrol)");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rol.DESCRIPCION = rec.IDRECURSO AND rec.IDLENGUAJE = '" + idLenguaje + "')");
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
		sql.SELECT("cur.idinstitucion");
		sql.SELECT("cur.fechabaja");
		sql.FROM("FOR_PERSONA_CURSO cur");
		sql.INNER_JOIN("CEN_PERSONA per ON (per.idpersona = cur.idpersona)");
		sql.INNER_JOIN("FOR_TIPOCOSTE tip ON (tip.idtipocoste = cur.idtipocoste)");
		sql.LEFT_OUTER_JOIN("FOR_ROLES rol ON (rol.idrol = cur.idrol and cur.idinstitucion = rol.idinstitucion)");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tip.DESCRIPCION AND rec.IDLENGUAJE = '" + idLenguaje + "')");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS rec2 ON (rol.DESCRIPCION = rec2.IDRECURSO AND rec2.IDLENGUAJE = '" + idLenguaje + "')");
		sql.WHERE("cur.IDCURSO = '" + idCurso + "'");
		sql.WHERE("cur.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("cur.FECHABAJA IS NULL");
		sql.ORDER_BY("apellidos");

		return sql.toString();
	}
	
	public String selectSesionesFormador(FormadorCursoItem formador) {
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("PERSONA.*");
		sql.FROM("FOR_CURSO  CURSO");
		sql.INNER_JOIN(" FOR_EVENTO_CURSO EVENTO ON CURSO.IDCURSO = EVENTO.IDCURSO") ; 
		sql.INNER_JOIN(" AGE_PERSONA_EVENTO PERSONA ON EVENTO.IDEVENTO = PERSONA.IDEVENTO");
		sql.WHERE("CURSO.IDCURSO = '" + formador.getIdCurso() + "'");
		sql.WHERE("CURSO.IDINSTITUCION = '" + formador.getIdInstitucion() + "'");
		sql.WHERE("PERSONA.IDPERSONA = '" + formador.getIdPersona() + "'");
		sql.WHERE("PERSONA.FECHABAJA IS NULL");
		return sql.toString();
	}
	

}
