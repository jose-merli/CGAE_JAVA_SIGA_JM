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
	
}
