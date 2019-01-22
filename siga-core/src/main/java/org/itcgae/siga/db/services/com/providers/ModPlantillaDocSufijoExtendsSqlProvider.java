package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ModPlantillaDocSufijoExtendsSqlProvider {
	
	public String selectSufijos(String idLenguaje){
		
		SQL sql = new SQL();		
				
		sql.SELECT("sufijo.IDSUFIJO");
		sql.SELECT("rec.DESCRIPCION");
		sql.SELECT("0 AS ORDEN");
		
		sql.FROM("MOD_PLANTILLADOC_SUFIJO sufijo");
		
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = sufijo.NOMBRE AND rec.IDLENGUAJE = '" + idLenguaje + "')");
		sql.ORDER_BY("rec.DESCRIPCION");
		
		return sql.toString();
	}

}
