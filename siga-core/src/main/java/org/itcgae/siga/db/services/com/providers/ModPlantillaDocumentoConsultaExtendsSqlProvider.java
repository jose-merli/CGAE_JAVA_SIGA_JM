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
	
	public String selectConsultasByInforme(Short idInstitucion, Long idModeloComunicacion, Long idInforme, String idLenguaje, boolean historico){
		
		SQL sql = new SQL();		
		
		sql.SELECT("DISTINCT plantilla.idconsulta, consulta.descripcion, consulta.idobjetivo, rec.descripcion AS OBJETIVO");
		sql.SELECT("LISTAGG(plantilla.idplantillaconsulta, ',') WITHIN GROUP (ORDER BY plantilla.idplantillaconsulta) idplantillaconsulta");
		
		sql.FROM("MOD_PLANTILLADOC_CONSULTA plantilla");	
		sql.INNER_JOIN("CON_CONSULTA consulta ON consulta.IDCONSULTA = plantilla.IDCONSULTA AND consulta.IDINSTITUCION = plantilla.IDINSTITUCION");
		sql.INNER_JOIN("(select * from mod_modelo_plantilladocumento where rownum=1 order by idplantilladocumento desc) modelo ON modelo.idmodelocomunicacion = plantilla.idmodelocomunicacion");
		sql.INNER_JOIN("con_objetivo objetivo ON consulta.idobjetivo = objetivo.idobjetivo");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON rec.idrecurso = objetivo.nombre AND rec.idlenguaje = " + idLenguaje);				
		sql.WHERE("plantilla.IDMODELOCOMUNICACION = " + idModeloComunicacion + " AND modelo.idinforme = " + idInforme + " AND plantilla.IDINSTITUCION = " + idInstitucion);
		sql.GROUP_BY("plantilla.idconsulta, consulta.descripcion, consulta.idobjetivo, rec.descripcion");
		
		if(!historico){
			sql.WHERE("plantilla.FECHABAJA IS NULL");
		}
		
		sql.ORDER_BY("case when consulta.idObjetivo = 3 then 1 when consulta.idObjetivo = 1 then 2 when consulta.idObjetivo = 2 then 3 when consulta.idObjetivo = 4 then 4 else 5 end");
		
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
