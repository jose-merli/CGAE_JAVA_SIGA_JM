package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class EnvDestConsultaEnvioExtendsSqlProviders {
	
	public String consultasDestAsociadasEnvio(String idEnvio, Short idInsitucion) {
		
		SQL sql = new SQL();
		
		sql.SELECT("DEST.IDCONSULTA, DEST.IDINSTITUCION, CONSULTA.DESCRIPCION");
		sql.FROM("ENV_DEST_CONSULTA_ENVIO DEST");
		sql.JOIN("CON_CONSULTA CONSULTA ON CONSULTA.IDCONSULTA = DEST.IDCONSULTA AND CONSULTA.IDINSTITUCION = DEST.IDINSTITUCION");
		sql.WHERE("DEST.FECHABAJA IS NULL AND DEST.IDENVIO = " + idEnvio + " AND DEST.IDINSTITUCION = "+idInsitucion);
		
		return sql.toString();
	}
	
}
