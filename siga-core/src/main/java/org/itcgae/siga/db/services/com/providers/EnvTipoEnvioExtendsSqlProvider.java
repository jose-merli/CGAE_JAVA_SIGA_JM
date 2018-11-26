package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class EnvTipoEnvioExtendsSqlProvider {
	
	public String selectTipoEnvios (String idLenguaje){
		
		SQL sql = new SQL();
		sql.SELECT_DISTINCT("tipo.IDTIPOENVIOS, rec.DESCRIPCION");
		sql.FROM("ENV_TIPOENVIOS tipo");
		sql.INNER_JOIN("JOIN GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tipo.NOMBRE AND rec.IDLENGUAJE = '" + idLenguaje + "')");
		sql.ORDER_BY("rec.DESCRIPCION");
			
		return sql.toString();
	}

}
