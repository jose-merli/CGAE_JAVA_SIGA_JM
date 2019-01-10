package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgeAsistenciaEventoSqlProvider;

public class AgeAsistenciaeventoSqlExtendsProvider extends  AgeAsistenciaEventoSqlProvider{

	
	public String getEntryListCourse(String idCurso) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("asis.IDASISTENCIAEVENTO");
		sql.SELECT_DISTINCT("inscripcion.IDINSCRIPCION");
		sql.SELECT_DISTINCT("curso.IDCURSO");
		sql.SELECT("inscripcion.idpersona");
		sql.SELECT("CONCAT(per.NOMBRE || ' ' || per.APELLIDOS1 || ' ', per.APELLIDOS2) AS nombrePersona");
		sql.SELECT("nvl(asis.asistencia,0) as asistencia");
		sql.FROM("FOR_CURSO curso");
		sql.INNER_JOIN("FOR_INSCRIPCION inscripcion on curso.idcurso = inscripcion.idcurso");
		sql.INNER_JOIN("CEN_PERSONA per ON (per.idpersona = inscripcion.idpersona)");
		sql.LEFT_OUTER_JOIN("AGE_ASISTENCIA_EVENTO asis on asis.idinscripcion = inscripcion.IDINSCRIPCION");
		
		sql.WHERE("curso.idcurso = '" + idCurso + "' and inscripcion.idpersona is not null");
		sql.WHERE("inscripcion.IDESTADOINSCRIPCION = '3'");
		
		sql.ORDER_BY("nombrePersona");

		return sql.toString();
		
	}
	
}
