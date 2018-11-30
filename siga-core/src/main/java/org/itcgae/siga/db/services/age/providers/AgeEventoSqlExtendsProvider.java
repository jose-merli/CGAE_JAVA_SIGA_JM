package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgeEventoSqlProvider;

public class AgeEventoSqlExtendsProvider extends  AgeEventoSqlProvider{

		
	public String selectMaxEvent() {

		SQL sql = new SQL();

		sql.SELECT("max(IDEVENTO) AS IDEVENTO");
		sql.FROM("AGE_EVENTO");
		
		return sql.toString();
	}

}
