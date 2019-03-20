package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class EnvDestConsultaEnvioExtendsSqlProviders {
	
	public String consultasDestAsociadasEnvio(Short idInsitucion, String idEnvio) {
		
		SQL sql = new SQL();
		
		sql.SELECT("CONSULTA.IDINSTITUCION");
		sql.SELECT("CONSULTA.IDCONSULTA");
		sql.SELECT("CONSULTA.GENERAL");
		sql.SELECT("CONSULTA.DESCRIPCION");
		sql.SELECT("CONSULTA.OBSERVACIONES");
		sql.SELECT("CONSULTA.TIPOCONSULTA");
		sql.SELECT("CM.IDMODULO");
		sql.SELECT("CONSULTA.IDCLASECOMUNICACION");
		sql.SELECT("CONSULTA.IDOBJETIVO");
		sql.SELECT("CONSULTA.SENTENCIA");
		
		sql.FROM("ENV_DEST_CONSULTA_ENVIO DEST");
		sql.JOIN("CON_CONSULTA CONSULTA ON CONSULTA.IDCONSULTA = DEST.IDCONSULTA AND CONSULTA.IDINSTITUCION = DEST.IDINSTITUCION_CONSULTA");
		sql.LEFT_OUTER_JOIN("con_modulo cm on CONSULTA.idmodulo = cm.idmodulo");
		sql.WHERE("DEST.FECHABAJA IS NULL AND DEST.IDENVIO = " + idEnvio + " AND DEST.IDINSTITUCION = "+idInsitucion);
		
		return sql.toString();
	}
	
}
