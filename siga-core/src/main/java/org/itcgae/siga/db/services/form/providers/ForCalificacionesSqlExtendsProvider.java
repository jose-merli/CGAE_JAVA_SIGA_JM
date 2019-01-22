package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForCalificacionesSqlProvider;

public class ForCalificacionesSqlExtendsProvider extends ForCalificacionesSqlProvider{

	public String getQualificationsCourse(String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("CAL.IDCALIFICACION");
		sql.SELECT("CAT.DESCRIPCION");
		sql.FROM("FOR_CALIFICACIONES CAL");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON CAL.DESCRIPCION = CAT.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("CAL.FECHABAJA IS NULL");
		sql.WHERE("CAT.NOMBRETABLA = 'FOR_CALIFICACIONES'");
		sql.ORDER_BY("CAL.IDCALIFICACION");
		
		return sql.toString();
	}
	
}
