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

}
