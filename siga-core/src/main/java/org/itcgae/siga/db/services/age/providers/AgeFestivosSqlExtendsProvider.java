package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgeFestivosSqlProvider;

public class AgeFestivosSqlExtendsProvider extends AgeFestivosSqlProvider {

	public String getFestivos(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("AGE.IDINSTITUCION");
		sql.SELECT("AGE.FECHA");
		sql.SELECT("AGE.DENOMINACION");
		sql.SELECT("AGE.TIPOFESTIVO");
		sql.SELECT("DECODE(AGE.TIPOFESTIVO,'Fiesta Local',AGE.IDPARTIDO,null) AS LUGAR");

		sql.FROM("AGE_FESTIVOS AGE");
		sql.WHERE("AGE.IDINSTITUCION = '" + String.valueOf(idInstitucion) + "'");

		return sql.toString();
	}

	public String getFechaFestivos(Short idInstitucion) {

		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("TO_CHAR(AGE.FECHA, 'YYYY-MM-dd') AS FECHA");

		sql.FROM("AGE_FESTIVOS AGE");
		sql.WHERE("AGE.IDINSTITUCION = '" + String.valueOf(idInstitucion) + "'");

		return sql.toString();
	}

}
