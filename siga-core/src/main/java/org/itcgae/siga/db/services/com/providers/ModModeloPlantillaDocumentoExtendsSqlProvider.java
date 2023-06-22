package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ModModeloPlantillaDocumentoExtendsSqlProvider {
	
	public String selectInformes(Short idInstitucion, Long idModeloComunicacion, String idLenguaje){
		
		SQL sql = new SQL();		
				
		sql.SELECT("modeloPlantillaDocumento.FORMATOSALIDA");
		sql.SELECT("modeloPlantillaDocumento.NOMBREFICHEROSALIDA");
		sql.SELECT("modeloPlantillaDocumento.IDINFORME");
		sql.SELECT("LISTAGG(C.DESCRIPCION, ',') WITHIN GROUP (ORDER BY C.DESCRIPCION) idioma");
		sql.SELECT("LISTAGG(plantillaDocumento.Idplantilladocumento, ',') WITHIN GROUP (ORDER BY plantillaDocumento.Idplantilladocumento) idPlantillas");
		sql.SELECT("rec.descripcion AS NOMBREFORMATO");
		
		sql.FROM("MOD_MODELO_PLANTILLADOCUMENTO modeloPlantillaDocumento");	
		sql.INNER_JOIN("MOD_PLANTILLADOCUMENTO plantillaDocumento ON modeloPlantillaDocumento.IDPLANTILLADOCUMENTO = plantillaDocumento.IDPLANTILLADOCUMENTO");
		sql.INNER_JOIN("MOD_PLANTILLADOC_FORMATO formato ON formato.IDFORMATOSALIDA = modeloPlantillaDocumento.FORMATOSALIDA");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON rec.IDRECURSO = formato.NOMBRE AND rec.idlenguaje = '" + idLenguaje + "'");
		sql.INNER_JOIN("ADM_LENGUAJES lenguajes ON lenguajes.idlenguaje = plantillaDocumento.Idioma");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS C on lenguajes.DESCRIPCION = C.idRecurso");
				
		sql.WHERE("modeloPlantillaDocumento.IDMODELOCOMUNICACION = " + idModeloComunicacion);
		sql.WHERE("C.IDLENGUAJE = '" + idLenguaje + "' AND CODIGOEJIS is not null AND lenguajes.FECHA_BAJA is null");
		sql.WHERE("modeloPlantillaDocumento.FECHABAJA IS NULL");
		sql.GROUP_BY("modeloPlantillaDocumento.FORMATOSALIDA, modeloPlantillaDocumento.NOMBREFICHEROSALIDA, modeloPlantillaDocumento.IDINFORME, rec.DESCRIPCION");
		
		return sql.toString();
	}

	public String selectMaxInforme(Short idInstitucion, Long idModeloComunicacion){
		SQL sql = new SQL();		
		
		sql.SELECT("MAX(modeloPlantillaDocumento.IDINFORME) AS IDINFORME");
		
		sql.FROM("MOD_MODELO_PLANTILLADOCUMENTO modeloPlantillaDocumento");	
		
		sql.WHERE("modeloPlantillaDocumento.IDMODELOCOMUNICACION = " + idModeloComunicacion);
		
		return sql.toString();
	}
	
	public String selectInformesGenerar(Long idModeloComunicacion, String idLenguaje){
		SQL sql = new SQL();		
		
		sql.SELECT("modPlantilla.IDINFORME");
		sql.SELECT("modPlantilla.IDPLANTILLADOCUMENTO AS idPlantillaDocumento");
		sql.SELECT("LISTAGG(modPlantilla.Idplantilladocumento, ',') WITHIN GROUP (ORDER BY modPlantilla.Idplantilladocumento) idplantillas");
		sql.SELECT("modPlantilla.NOMBREFICHEROSALIDA");
		sql.SELECT("modPlantilla.FORMATOSALIDA");
		
		sql.FROM("mod_modelo_plantilladocumento modPlantilla");	
		sql.INNER_JOIN("mod_plantilladocumento ON mod_plantilladocumento.idplantilladocumento = modPlantilla.idplantilladocumento AND mod_plantilladocumento.idioma = '"+idLenguaje+"'");
		sql.WHERE("modPlantilla.IDMODELOCOMUNICACION = " + idModeloComunicacion + " AND modPlantilla.FECHABAJA IS NULL");
		sql.GROUP_BY("modPlantilla.IDINFORME,modPlantilla.NOMBREFICHEROSALIDA, modPlantilla.FORMATOSALIDA, modPlantilla.IDPLANTILLADOCUMENTO");
		
		return sql.toString();
	}
	
	public String selectInformesGenerarConDest(Long idModeloComunicacion, String idLenguaje){
		SQL sql = new SQL();		
		
		sql.SELECT("modPlantilla.IDINFORME");
		sql.SELECT("modPlantilla.IDPLANTILLADOCUMENTO AS idPlantillaDocumento");
		sql.SELECT("LISTAGG(modPlantilla.Idplantilladocumento, ',') WITHIN GROUP (ORDER BY modPlantilla.Idplantilladocumento) idplantillas");
		sql.SELECT("modPlantilla.NOMBREFICHEROSALIDA");
		sql.SELECT("modPlantilla.FORMATOSALIDA");
		
		sql.FROM("mod_modelo_plantilladocumento modPlantilla");	
		sql.INNER_JOIN("mod_plantilladocumento ON mod_plantilladocumento.idplantilladocumento = modPlantilla.idplantilladocumento AND mod_plantilladocumento.idioma = '"+idLenguaje+"'");
		sql.INNER_JOIN(" MOD_PLANTILLADOC_CONSULTA ON  MOD_PLANTILLADOC_CONSULTA.IDPLANTILLADOCUMENTO = modPlantilla.IDPLANTILLADOCUMENTO");
		sql.INNER_JOIN("con_consulta ON con_consulta.idconsulta=MOD_PLANTILLADOC_CONSULTA.Idconsulta AND con_consulta.idinstitucion = MOD_PLANTILLADOC_CONSULTA.Idinstitucion_consulta and con_consulta.fechabaja is null");
		sql.WHERE("modPlantilla.IDMODELOCOMUNICACION = " + idModeloComunicacion + " AND modPlantilla.FECHABAJA IS NULL and con_consulta.idobjetivo = 1");
		sql.GROUP_BY("modPlantilla.IDINFORME,modPlantilla.NOMBREFICHEROSALIDA, modPlantilla.FORMATOSALIDA, modPlantilla.IDPLANTILLADOCUMENTO");
		
		return sql.toString();
	}
	
	public String selectPlantillaGenerar(Long idModeloComunicacion, Long idPlantillaDocumento){
		SQL sql = new SQL();		
		
		sql.SELECT("modPlantilla.IDINFORME");
		sql.SELECT("LISTAGG(modPlantilla.Idplantilladocumento, ',') WITHIN GROUP (ORDER BY modPlantilla.Idplantilladocumento) idplantillas");
		sql.SELECT("modPlantilla.NOMBREFICHEROSALIDA");
		sql.SELECT("modPlantilla.FORMATOSALIDA");
		
		sql.FROM("mod_modelo_plantilladocumento modPlantilla");	
		
		sql.WHERE("modPlantilla.IDMODELOCOMUNICACION = " + idModeloComunicacion + " AND modPlantilla.FECHABAJA IS NULL");
		sql.WHERE("modPlantilla.Idplantilladocumento = " + idPlantillaDocumento);
		sql.GROUP_BY("modPlantilla.IDINFORME,modPlantilla.NOMBREFICHEROSALIDA, modPlantilla.FORMATOSALIDA");
		
		return sql.toString();
	}
	
}
