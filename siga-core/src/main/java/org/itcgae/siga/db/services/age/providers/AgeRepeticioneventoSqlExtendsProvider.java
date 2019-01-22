package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgeRepeticioneventoSqlProvider;

public class AgeRepeticioneventoSqlExtendsProvider extends  AgeRepeticioneventoSqlProvider{

		
	public String selectMaxRepetitionEvent() {

		SQL sql = new SQL();

		sql.SELECT("max(IDREPETICIONEVENTO) AS IDREPETICIONEVENTO");
		sql.FROM("AGE_REPETICIONEVENTO");
		
		return sql.toString();
	}

}
