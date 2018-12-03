package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class EnvEstadoEnvioExtendsSqlProvider {
	
	public String selectEstadoEnvios(String idLenguaje){
		
		SQL sql = new SQL();
		sql.SELECT_DISTINCT("estado.IDESTADO, rec.DESCRIPCION");
		sql.FROM("ENV_ESTADOENVIO estado");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = estado.NOMBRE AND rec.IDLENGUAJE = '"+ idLenguaje + "')");
		sql.ORDER_BY("rec.DESCRIPCION");
		
		return sql.toString();
	}

}