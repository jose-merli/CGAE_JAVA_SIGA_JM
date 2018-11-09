package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgeCalendarioSqlProvider;

public class AgeCalendarioSqlExtendsProvider extends  AgeCalendarioSqlProvider{

		
	public String selectMaxCalendar() {

		SQL sql = new SQL();

		sql.SELECT("max(IDCALENDARIO) AS IDCALENDARIO");
		sql.FROM("AGE_CALENDARIO");
		
		return sql.toString();
	}
	
	public String getCalendars(String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("cal.IDCALENDARIO");
		sql.SELECT("cal.DESCRIPCION");
		sql.FROM("AGE_CALENDARIO cal");
//		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tipocuando.DESCRIPCION AND rec.IDLENGUAJE = '"
//				+ idLenguaje + "')");
		sql.WHERE("cal.IDINSTITUCION = '" + idInstitucion + "'");
		sql.ORDER_BY("cal.DESCRIPCION");
		
		return sql.toString();
	}
	
}
