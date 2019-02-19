package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class EnvConsultasEnvioExtendsSqlProvider {
	
	
	public String selectPlantillasByEnvio (String idInstitucion, String idEnvio){
		
		SQL sql = new SQL();
		sql.SELECT_DISTINCT("IDPLANTILLADOCUMENTO");

		sql.FROM("ENV_CONSULTASENVIO");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDENVIO = " + idEnvio + " AND IDPLANTILLADOCUMENTO IS NOT NULL");
		
		return sql.toString();
	}

}
