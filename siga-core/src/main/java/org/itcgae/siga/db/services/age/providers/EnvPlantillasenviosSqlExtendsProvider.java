package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.EnvPlantillasenviosSqlProvider;

public class EnvPlantillasenviosSqlExtendsProvider extends  EnvPlantillasenviosSqlProvider{

		
	public String getTemplates(String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("IDPLANTILLAENVIOS");
		sql.SELECT("NOMBRE");
		sql.SELECT("IDTIPOENVIOS");
		sql.FROM("ENV_PLANTILLASENVIOS");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.ORDER_BY("NOMBRE");
		
		return sql.toString();
	}
	
	public String getPlantillas(String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("IDPLANTILLAENVIOS");
		sql.SELECT("NOMBRE");
		sql.SELECT("IDTIPOENVIOS");
		sql.FROM("ENV_PLANTILLASENVIOS");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.ORDER_BY("NOMBRE");
		
		return sql.toString();
	}

}
