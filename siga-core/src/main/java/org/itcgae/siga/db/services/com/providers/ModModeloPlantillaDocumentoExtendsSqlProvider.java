package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ModModeloPlantillaDocumentoExtendsSqlProvider {
	
	public String selectInformes(Short idInstitucion, Long idModeloComunicacion, String idLenguaje){
		
		SQL sql = new SQL();		
				
		sql.SELECT("modeloPlantillaDocumento.FECHAASOCIACION");
		sql.SELECT("modeloPlantillaDocumento.SUFIJO");
		sql.SELECT("modeloPlantillaDocumento.FORMATOSALIDA");
		sql.SELECT("modeloPlantillaDocumento.NOMBREFICHEROSALIDA");
		sql.SELECT("LISTAGG(plantillaDocumento.IDIOMA, ',') WITHIN GROUP (ORDER BY plantillaDocumento.IDIOMA) idioma");
		sql.SELECT("rec.descripcion AS NOMBREFORMATO");
		sql.SELECT("rec2.descripcion AS NOMBRESUFIJO");
		
		sql.FROM("MOD_MODELO_PLANTILLADOCUMENTO modeloPlantillaDocumento");	
		sql.INNER_JOIN("MOD_PLANTILLADOCUMENTO plantillaDocumento ON modeloPlantillaDocumento.IDPLANTILLADOCUMENTO = plantillaDocumento.IDPLANTILLADOCUMENTO");
		sql.INNER_JOIN("MOD_PLANTILLADOC_FORMATO formato ON formato.IDFORMATOSALIDA = modeloPlantillaDocumento.FORMATOSALIDA");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON rec.IDRECURSO = formato.NOMBRE AND rec.idlenguaje = '" + idLenguaje + "'");
		sql.INNER_JOIN("MOD_PLANTILLADOC_SUFIJO sufijo ON sufijo.IDSUFIJO = modeloPlantillaDocumento.SUFIJO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec2 ON rec2.IDRECURSO = sufijo.NOMBRE AND rec2.idlenguaje = '" + idLenguaje+ "'");
		
		sql.WHERE("modeloPlantillaDocumento.IDMODELOCOMUNICACION = " + idModeloComunicacion);
		sql.GROUP_BY("modeloPlantillaDocumento.FECHAASOCIACION, modeloPlantillaDocumento.SUFIJO, modeloPlantillaDocumento.NOMBREFICHEROSALIDA, modeloPlantillaDocumento.FORMATOSALIDA, rec.DESCRIPCION, rec2.DESCRIPCION");
		
		return sql.toString();
	}

	public String selectPlantillasDocumento(String idModelo, String idLenguaje){
		SQL sql = new SQL();
		
		sql.SELECT("PLANTILLA.NOMBRE, PLANTILLA.IDTIPOENVIOS,"
				+ "(SELECT CAT.DESCRIPCION FROM ENV_PLANTILLASENVIOS PLA LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = PLA.NOMBRE WHERE PLA.IDTIPOENVIOS = PLANTILLA.IDTIPOENVIOS AND CAT.IDLENGUAJE = '"+ idLenguaje +"') AS TIPOENVIO");
		sql.SELECT("PLANTILLA.IDPLANTILLAENVIOS");
		sql.FROM("MOD_MODELO_PLANTILLAENVIO MODELO");
		sql.JOIN("ENV_PLANTILLASENVIOS PLANTILLA ON PLANTILLA.IDPLANTILLAENVIOS = MODELO.IDPLANTILLAENVIOS");
		sql.WHERE("MODELO.IDMODELOCOMUNICACION = '" + idModelo +"' AND MODELO.FECHABAJA IS NULL");
		
		return sql.toString();
	}
	
	public String selectPlantillasDocumentoHIST(String idModelo, String idLenguaje){
		SQL sql = new SQL();
		
		sql.SELECT("PLANTILLA.NOMBRE, PLANTILLA.IDTIPOENVIOS,"
				+ "(SELECT CAT.DESCRIPCION FROM ENV_PLANTILLASENVIOS PLA LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = PLA.NOMBRE WHERE PLA.IDTIPOENVIOS = PLANTILLA.IDTIPOENVIOS AND CAT.IDLENGUAJE = '"+ idLenguaje +"') AS TIPOENVIO");
		sql.SELECT("PLANTILLA.IDPLANTILLAENVIOS");
		sql.FROM("MOD_MODELO_PLANTILLAENVIO MODELO");
		sql.JOIN("ENV_PLANTILLASENVIOS PLANTILLA ON PLANTILLA.IDPLANTILLAENVIOS = MODELO.IDPLANTILLAENVIOS");
		sql.WHERE("MODELO.IDMODELOCOMUNICACION = '" + idModelo +"'");
		
		return sql.toString();
	}
	
}
