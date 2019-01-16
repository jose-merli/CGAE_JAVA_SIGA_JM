package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class ModPlantillaDocFormatoExtendsSqlProvider {
	
	public String selectFormatos(String idLenguaje){
		
		SQL sql = new SQL();		
				
		sql.SELECT("formato.IDFORMATOSALIDA");
		sql.SELECT("rec.DESCRIPCION");
		
		sql.FROM("MOD_PLANTILLADOC_FORMATO formato");
		
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = formato.NOMBRE AND rec.IDLENGUAJE = '" + idLenguaje + "')");
		sql.ORDER_BY("rec.DESCRIPCION");
		
		return sql.toString();
	}

}
