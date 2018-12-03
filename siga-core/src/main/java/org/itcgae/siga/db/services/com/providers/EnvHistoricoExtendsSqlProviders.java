package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class EnvHistoricoExtendsSqlProviders {
	
	public String selectMaxIDHistorico(){
		
		SQL sql = new SQL();
		sql.SELECT("MAX(IDHISTORICO)+1 AS IDMAX");
		sql.FROM("ENV_HISTORICOESTADOENVIO");
		return sql.toString();
	}

}
