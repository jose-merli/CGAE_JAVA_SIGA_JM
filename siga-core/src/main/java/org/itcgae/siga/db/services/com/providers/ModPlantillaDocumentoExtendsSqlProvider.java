package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ModPlantillaDocumentoExtendsSqlProvider {
	
	public String selectPlantillasByInforme(Long idInforme, Long idModeloComunicacion){
		
		SQL sql = new SQL();		
				
		sql.SELECT("plantillaDocumento.IDPLANTILLADOCUMENTO");
		sql.SELECT("plantillaDocumento.IDIOMA");
		sql.SELECT("plantillaDocumento.PLANTILLA");
		sql.SELECT("plantillaDocumento.IDINFORME");
		
		sql.FROM("MOD_PLANTILLADOCUMENTO plantillaDocumento");	
		sql.INNER_JOIN("MOD_MODELO_PLANTILLADOCUMENTO modeloPlantillaDocumento ON modeloPlantillaDocumento.IDPLANTILLADOCUMENTO = plantillaDocumento.IDPLANTILLADOCUMENTO AND modeloPlantillaDocumento.IDINFORME = " + idInforme + " AND modeloPlantillaDocumento.IDMODELOCOMUNICACION = " + idModeloComunicacion);
		
		return sql.toString();
	}
}
