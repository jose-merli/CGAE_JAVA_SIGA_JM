package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ModPlantillaEnvioConsultaExtendsSqlProvider {
	
	public String selectPlantillaEnvioConsultas(Short idInstitucion, Integer idPlantillaEnvios, Short idTipoEnvios){
		
		SQL sql = new SQL();		
		
		sql.SELECT("plantilla.IDCONSULTA");
		sql.SELECT("plantilla.IDPLANTILLAENVIOS");
		sql.SELECT("plantilla.IDTIPOENVIOS");
		sql.SELECT("consulta.SENTENCIA");
		sql.SELECT("consulta.IDOBJETIVO");
		sql.SELECT("consulta.IDINSTITUCION");
		
		sql.FROM("MOD_PLANTILLAENVIO_CONSULTA plantilla");	
		sql.INNER_JOIN("CON_CONSULTA consulta ON consulta.IDCONSULTA = plantilla.IDCONSULTA AND consulta.IDINSTITUCION = plantilla.IDINSTITUCION_CONSULTA");
		
		sql.WHERE("plantilla.IDPLANTILLAENVIOS = " + idPlantillaEnvios + " AND plantilla.IDTIPOENVIOS = " + idTipoEnvios + " AND plantilla.IDINSTITUCION = " + idInstitucion);
				
		sql.WHERE("plantilla.FECHABAJA IS NULL");	
		
		return sql.toString();
	}
}
