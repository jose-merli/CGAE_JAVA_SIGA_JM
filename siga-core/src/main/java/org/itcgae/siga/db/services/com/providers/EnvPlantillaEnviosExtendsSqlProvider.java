package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class EnvPlantillaEnviosExtendsSqlProvider {
	
	public String selectPlantillas(Short idInstitucion, String idTipoEnvio){
		
		SQL sql = new SQL();
		
		sql.SELECT("TO_CHAR(IDPLANTILLAENVIOS) AS IDPLANTILLAENVIOS");
		sql.SELECT("NOMBRE");
		
		sql.FROM("ENV_PLANTILLASENVIOS");
		
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDTIPOENVIOS = '" + idTipoEnvio + "'");
		
		return sql.toString();
	}
	
	public String selectMaxIDPlantillaEnvio(){
		
		SQL sql = new SQL();
		sql.SELECT("MAX(IDPLANTILLAENVIOS)+1 AS IDMAX");
		sql.FROM("ENV_PLANTILLASENVIOS");
		return sql.toString();
	}

}
