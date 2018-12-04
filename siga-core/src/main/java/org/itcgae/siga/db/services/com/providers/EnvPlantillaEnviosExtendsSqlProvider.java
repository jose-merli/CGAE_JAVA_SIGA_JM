package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class EnvPlantillaEnviosExtendsSqlProvider {
	
	public String selectPlantillas(String idInstitucion, String idTipoEnvio){
		
		SQL sql = new SQL();
		
		sql.SELECT("IDPLANTILLAENVIOS");
		sql.SELECT("NOMBRE");
		
		sql.FROM("ENV_PLANTILLASENVIOS");
		
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDTIPOENVIOS = '" + idTipoEnvio + "'");
		
		return sql.toString();
	}

}
