package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ModModeloPlantillaDocumentoExtendsSqlProvider {
	
	public String selectInformes(Short idInstitucion, Long idModeloComunicacion){
		
		SQL sql = new SQL();		
				
		sql.SELECT("plantillaDocumento.IDIOMA");
		sql.SELECT("modeloPlantillaDocumento.FECHAASOCIACION");
		sql.SELECT("plantillaDocumento.PLANTILLA");
		sql.SELECT("modeloPlantillaDocumento.SUFIJO");
		sql.SELECT("modeloPlantillaDocumento.FORMATOSALIDA");
		
		sql.FROM("MOD_MODELO_PLANTILLADOCUMENTO modeloPlantillaDocumento");	
		sql.INNER_JOIN("MOD_PLANTILLADOCUMENTO plantillaDocumento ON modeloPlantillaDocumento.IDPLANTILLADOCUMENTO = plantillaDocumento.IDPLANTILLADOCUMENTO");
		
		sql.WHERE("plantillaDocumento.IDMODELOCOMUNICACION = " + idModeloComunicacion);
		
		return sql.toString();
	}

	public String selectPlantillasDocumento(String idModelo, String idLenguaje){
		SQL sql = new SQL();
		
		sql.SELECT("PLANTILLA.NOMBRE, PLANTILLA.IDTIPOENVIOS,"
				+ "(SELECT CAT.DESCRIPCION FROM ENV_PLANTILLASENVIOS PLA LEFT JOIN GEN_RECURSOS_CATALOGOS CAT ON CAT.IDRECURSO = PLA.NOMBRE WHERE PLA.IDTIPOENVIOS = PLANTILLA.IDTIPOENVIOS AND CAT.IDLENGUAJE = '"+ idLenguaje +"') AS TIPOENVIO");
		sql.FROM("MOD_MODELO_PLANTILLAENVIO MODELO");
		sql.JOIN("ENV_PLANTILLASENVIOS PLANTILLA ON PLANTILLA.IDPLANTILLAENVIOS = MODELO.IDPLANTILLAENVIOS");
		sql.WHERE("MODELO.IDMODELOCOMUNICACION = '" + idModelo +"'");
		
		return sql.toString();
	}
}
