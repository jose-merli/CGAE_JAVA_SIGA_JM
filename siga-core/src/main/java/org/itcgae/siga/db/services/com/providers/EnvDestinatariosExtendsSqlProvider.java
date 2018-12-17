package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class EnvDestinatariosExtendsSqlProvider {
	
	
	public String selectDestinatarios (Short idInstitucion, String idEnvio){
		
		SQL sql = new SQL();
		
		sql.SELECT("*");
		
		sql.FROM("ENV_DESTINATARIOS");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion +"'");
		sql.WHERE("IDENVIO = '" + idEnvio +"'");
		
		return sql.toString();
	}

}
