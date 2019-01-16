package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ModPlantillaDocumentoConsultaExtendsSqlProvider {
	
	public String selectPlantillaDocConsultas(Short idInstitucion, Long idModeloComunicacion, Long idPlantillaDocumento, boolean historico){
		
		SQL sql = new SQL();		
				
		sql.SELECT("plantilla.IDCONSULTA");
		sql.SELECT("consulta.DESCRIPCION");
		sql.SELECT("consulta.IDOBJETIVO");
		
		sql.FROM("MOD_PLANTILLADOC_CONSULTA plantilla");	
		sql.INNER_JOIN("CON_CONSULTA consulta ON consulta.IDCONSULTA = plantilla.IDCONSULTA AND consulta.IDINSTITUCION = plantilla.IDINSTITUCION");
		
		sql.WHERE("plantilla.IDMODELOCOMUNICACION = " + idModeloComunicacion + " AND plantilla.IDPLANTILLADOCUMENTO " + idPlantillaDocumento + "AND plantilla.IDINSTITUCION " + idInstitucion);
				
		if(!historico){
			sql.WHERE("plantilla.FECHABAJA IS NULL");
		}		
		
		return sql.toString();
	}
	
}
