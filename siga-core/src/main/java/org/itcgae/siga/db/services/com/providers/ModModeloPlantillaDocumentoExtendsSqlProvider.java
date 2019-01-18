package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ModModeloPlantillaDocumentoExtendsSqlProvider {
	
	public String selectInformes(Short idInstitucion, Long idModeloComunicacion, String idLenguaje){
		
		SQL sql = new SQL();		
				
		sql.SELECT("modeloPlantillaDocumento.FECHAASOCIACION");
		sql.SELECT("modeloPlantillaDocumento.FORMATOSALIDA");
		sql.SELECT("modeloPlantillaDocumento.NOMBREFICHEROSALIDA");
		sql.SELECT("modeloPlantillaDocumento.IDINFORME");
		sql.SELECT("LISTAGG(plantillaDocumento.IDIOMA, ',') WITHIN GROUP (ORDER BY plantillaDocumento.IDIOMA) idioma");
		sql.SELECT("LISTAGG(plantillaDocumento.Idplantilladocumento, ',') WITHIN GROUP (ORDER BY plantillaDocumento.Idplantilladocumento) idPlantillas");
		sql.SELECT("rec.descripcion AS NOMBREFORMATO");
		
		sql.FROM("MOD_MODELO_PLANTILLADOCUMENTO modeloPlantillaDocumento");	
		sql.INNER_JOIN("MOD_PLANTILLADOCUMENTO plantillaDocumento ON modeloPlantillaDocumento.IDPLANTILLADOCUMENTO = plantillaDocumento.IDPLANTILLADOCUMENTO");
		sql.INNER_JOIN("MOD_PLANTILLADOC_FORMATO formato ON formato.IDFORMATOSALIDA = modeloPlantillaDocumento.FORMATOSALIDA");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON rec.IDRECURSO = formato.NOMBRE AND rec.idlenguaje = '" + idLenguaje + "'");
		
		sql.WHERE("modeloPlantillaDocumento.IDMODELOCOMUNICACION = " + idModeloComunicacion);
		sql.GROUP_BY("modeloPlantillaDocumento.FECHAASOCIACION, modeloPlantillaDocumento.NOMBREFICHEROSALIDA, modeloPlantillaDocumento.FORMATOSALIDA, modeloPlantillaDocumento.IDINFORME, rec.DESCRIPCION");
		
		return sql.toString();
	}

	public String selectMaxInforme(Short idInstitucion, Long idModeloComunicacion){
		SQL sql = new SQL();		
		
		sql.SELECT("MAX(modeloPlantillaDocumento.IDINFORME) AS IDINFORME");
		
		sql.FROM("MOD_MODELO_PLANTILLADOCUMENTO modeloPlantillaDocumento");	
		
		sql.WHERE("modeloPlantillaDocumento.IDMODELOCOMUNICACION = " + idModeloComunicacion);
		
		return sql.toString();
	}
	
}
