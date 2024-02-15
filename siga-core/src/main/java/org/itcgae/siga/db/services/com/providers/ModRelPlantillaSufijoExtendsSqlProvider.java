package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ModRelPlantillaSufijoExtendsSqlProvider {
	
	public String selectSufijosPlantilla(Long idModeloComunicacion, Long idInforme, Long idPlantillaDocumento, String idLenguaje){
		
		SQL sql = new SQL();		
				
		sql.SELECT("relSufijo.IDSUFIJO");
		sql.SELECT("relSufijo.ORDEN");
		sql.SELECT("rec.DESCRIPCION AS NOMBRESUFIJO");
		
		sql.FROM("MOD_REL_PLANTILLA_SUFIJO relSufijo");

		sql.INNER_JOIN("MOD_PLANTILLADOC_SUFIJO sufijo ON sufijo.IDSUFIJO = relSufijo.IDSUFIJO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON rec.IDRECURSO = sufijo.NOMBRE AND rec.idlenguaje = '" + idLenguaje+ "'");
		sql.WHERE("relSufijo.Idmodelocomunicacion = " + idModeloComunicacion + " AND relSufijo.IDINFORME = " + idInforme + "  AND relSufijo.IDPLANTILLADOCUMENTO = " + idPlantillaDocumento);
		
		sql.GROUP_BY("relSufijo.Idsufijo,rec.DESCRIPCION, relSufijo.orden");
		sql.ORDER_BY("relSufijo.orden ASC");
		
		return sql.toString();
	}

}
