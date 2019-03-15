package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ModPlantillaDocumentoConsultaExtendsSqlProvider {
	
	public String selectPlantillaDocConsultas(Short idInstitucion, Long idModeloComunicacion, Long idPlantillaDocumento, boolean historico){
		
		SQL sql = new SQL();		
				
		sql.SELECT("plantilla.IDCONSULTA");
		sql.SELECT("consulta.DESCRIPCION");
		sql.SELECT("consulta.IDOBJETIVO");
		sql.SELECT("consulta.IDINSTITUCION");
		
		sql.FROM("MOD_PLANTILLADOC_CONSULTA plantilla");	
		sql.INNER_JOIN("CON_CONSULTA consulta ON consulta.IDCONSULTA = plantilla.IDCONSULTA AND consulta.IDINSTITUCION = plantilla.IDINSTITUCION_CONSULTA");
		
		sql.WHERE("plantilla.IDMODELOCOMUNICACION = " + idModeloComunicacion + " AND plantilla.IDPLANTILLADOCUMENTO = " + idPlantillaDocumento + " AND plantilla.IDINSTITUCION = " + idInstitucion);
				
		if(!historico){
			sql.WHERE("plantilla.FECHABAJA IS NULL");
		}		
		
		return sql.toString();
	}
	
	public String selectConsultasByInforme(Short idInstitucion, Long idModeloComunicacion, Long idInforme, String idLenguaje, boolean historico){
		
		SQL sql = new SQL();		
		
		sql.SELECT("DISTINCT plantilla.idconsulta, consulta.descripcion, consulta.idobjetivo, rec.descripcion AS OBJETIVO, consulta.idInstitucion, consulta.GENERAL");
		sql.SELECT("consulta.OBSERVACIONES");
		sql.SELECT("consulta.TIPOCONSULTA");
		sql.SELECT("modulo.IDMODULO");
		sql.SELECT("consulta.IDCLASECOMUNICACION");
		sql.SELECT("to_char(consulta.SENTENCIA) AS SENTENCIA");
		
		sql.SELECT("LISTAGG(plantilla.idplantillaconsulta, ',') WITHIN GROUP (ORDER BY plantilla.idplantillaconsulta) idplantillaconsulta");
		sql.SELECT("TRUNC(plantilla.FECHABAJA) AS FECHABAJA");		
		
		
		sql.FROM("MOD_PLANTILLADOC_CONSULTA plantilla");	
		sql.INNER_JOIN("CON_CONSULTA consulta ON consulta.IDCONSULTA = plantilla.IDCONSULTA AND consulta.IDINSTITUCION = plantilla.IDINSTITUCION_CONSULTA");
		sql.INNER_JOIN("(select * from mod_modelo_plantilladocumento where rownum=1 AND mod_modelo_plantilladocumento.idmodelocomunicacion = " + idModeloComunicacion +" AND mod_modelo_plantilladocumento.idinforme = " + idInforme + " AND mod_modelo_plantilladocumento.fechabaja is null order by idplantilladocumento asc) modelo ON modelo.idmodelocomunicacion = plantilla.idmodelocomunicacion AND modelo.idplantilladocumento = plantilla.idplantilladocumento");
		sql.INNER_JOIN("con_objetivo objetivo ON consulta.idobjetivo = objetivo.idobjetivo");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON rec.idrecurso = objetivo.nombre AND rec.idlenguaje = " + idLenguaje);				
		sql.LEFT_OUTER_JOIN("con_modulo modulo on consulta.idmodulo = modulo.idmodulo");
		sql.WHERE("plantilla.IDMODELOCOMUNICACION = " + idModeloComunicacion + " AND modelo.idinforme = " + idInforme + " AND plantilla.IDINSTITUCION = " + idInstitucion);
		sql.GROUP_BY("plantilla.idconsulta, consulta.descripcion, consulta.idobjetivo, consulta.idInstitucion, rec.descripcion, TRUNC(plantilla.FECHABAJA), consulta.GENERAL, consulta.OBSERVACIONES, consulta.TIPOCONSULTA, modulo.IDMODULO, consulta.IDCLASECOMUNICACION, to_char(consulta.SENTENCIA)");
		
		if(!historico){
			sql.WHERE("consulta.FECHABAJA IS NULL AND plantilla.FECHABAJA IS NULL");
		}
		
		sql.ORDER_BY("case when consulta.idObjetivo = 3 then 1 when consulta.idObjetivo = 1 then 2 when consulta.idObjetivo = 2 then 3 when consulta.idObjetivo = 4 then 4 else 5 end");
		
		return sql.toString();
	}
	
	public String selectConsultaPorObjetivo(Short idInstitucion, Long idModeloComunicacion, String idPlantillaDocumento, Long idObjetivo){
		
		SQL sql = new SQL();		
		
		sql.SELECT("con_consulta.DESCRIPCION");
		sql.SELECT("con_consulta.IDCONSULTA");
		sql.SELECT("con_consulta.SENTENCIA");
		sql.SELECT("con_consulta.IDINSTITUCION");
		
		sql.FROM("MOD_PLANTILLADOC_CONSULTA plantillaConsulta");	
		sql.INNER_JOIN("con_consulta ON con_consulta.idconsulta=plantillaConsulta.Idconsulta AND con_consulta.idinstitucion = plantillaConsulta.Idinstitucion_consulta");
		sql.WHERE("plantillaConsulta.IDPLANTILLADOCUMENTO IN (" + idPlantillaDocumento +") AND plantillaConsulta.IDMODELOCOMUNICACION = " + idModeloComunicacion + " AND plantillaConsulta.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("con_consulta.idobjetivo = " + idObjetivo);
		sql.WHERE("plantillaConsulta.FECHABAJA IS NULL");
		
		return sql.toString();
	}
	
	public String selectCountConsultaPorObjetivo(Short idInstitucion, Long idModeloComunicacion, Long idPlantillaDocumento, Long idObjetivo){
		
		SQL sql = new SQL();		
		
		sql.SELECT("COUNT(con_consulta.DESCRIPCION)");
		
		sql.FROM("MOD_PLANTILLADOC_CONSULTA plantillaConsulta");	
		sql.INNER_JOIN("con_consulta ON con_consulta.idconsulta=plantillaConsulta.Idconsulta AND con_consulta.idinstitucion = plantillaConsulta.Idinstitucion_consulta");
		sql.WHERE("plantillaConsulta.IDPLANTILLADOCUMENTO = " + idPlantillaDocumento +" AND plantillaConsulta.IDMODELOCOMUNICACION = " + idModeloComunicacion + " AND plantillaConsulta.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("con_consulta.idobjetivo = " + idObjetivo);
		sql.WHERE("plantillaConsulta.FECHABAJA IS NULL");
		
		return sql.toString();
	}
	
	public String selectConsultaByIdConsulta(Short idInstitucion, Long idModeloComunicacion, Long idInforme, Long idConsulta, Long idPlantillaDocumento){
		SQL sql = new SQL();		
		
		sql.SELECT("consulta.IDPLANTILLACONSULTA");
		sql.SELECT("consulta.REGION");
		
		sql.FROM("MOD_PLANTILLADOC_CONSULTA consulta");	
		sql.INNER_JOIN("MOD_MODELO_PLANTILLADOCUMENTO modelo ON consulta.idplantilladocumento=modelo.idplantilladocumento");
		sql.WHERE("consulta.idinstitucion = " + idInstitucion +" AND modelo.idmodelocomunicacion = " + idModeloComunicacion + " AND modelo.idinforme = " + idInforme + " AND consulta.IDCONSULTA = " + idConsulta);
		sql.WHERE("consulta.FECHABAJA IS NULL");
		
		if(idPlantillaDocumento != null){
			sql.WHERE("consulta.idplantilladocumento = " + idPlantillaDocumento);
		}

		return sql.toString();
	}
	
}
