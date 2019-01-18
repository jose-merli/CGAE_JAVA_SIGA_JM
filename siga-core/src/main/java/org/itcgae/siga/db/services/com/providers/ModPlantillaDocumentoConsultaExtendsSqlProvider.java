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
	
	public String selectConsultaPorObjetivo(Short idInstitucion, Long idModeloComunicacion, Long idPlantillaDocumento, Short idObjetivo){
		
		SQL sql = new SQL();		
		
		sql.SELECT("con_consulta.DESCRIPCION");
		
		sql.FROM("MOD_PLANTILLADOC_CONSULTA plantillaConsulta");	
		sql.INNER_JOIN("con_consulta ON con_consulta.idconsulta=plantillaConsulta.Idconsulta AND con_consulta.idinstitucion = plantillaConsulta.Idinstitucion");
		sql.WHERE("plantillaConsulta.IDPLANTILLADOCUMENTO = " + idPlantillaDocumento +" AND plantillaConsulta.IDMODELOCOMUNICACION = " + idModeloComunicacion + " AND plantillaConsulta.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("con_consulta.idobjetivo = " + idObjetivo);
		
		return sql.toString();
	}
	
	public String selectCountConsultaPorObjetivo(Short idInstitucion, Long idModeloComunicacion, Long idPlantillaDocumento, Short idObjetivo){
		
		SQL sql = new SQL();		
		
		sql.SELECT("COUNT(con_consulta.DESCRIPCION)");
		
		sql.FROM("MOD_PLANTILLADOC_CONSULTA plantillaConsulta");	
		sql.INNER_JOIN("con_consulta ON con_consulta.idconsulta=plantillaConsulta.Idconsulta AND con_consulta.idinstitucion = plantillaConsulta.Idinstitucion");
		sql.WHERE("plantillaConsulta.IDPLANTILLADOCUMENTO = " + idPlantillaDocumento +" AND plantillaConsulta.IDMODELOCOMUNICACION = " + idModeloComunicacion + " AND plantillaConsulta.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("con_consulta.idobjetivo = " + idObjetivo);
		
		return sql.toString();
	}
	
}
