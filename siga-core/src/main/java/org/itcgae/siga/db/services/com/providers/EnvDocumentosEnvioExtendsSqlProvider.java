package org.itcgae.siga.db.services.com.providers;

import org.apache.ibatis.jdbc.SQL;

public class EnvDocumentosEnvioExtendsSqlProvider {
	
	public String selectDocumentosEnvio(Short idInstitucion, String idEnvio){
		
		SQL sql = new SQL();
		
		sql.SELECT("IDENVIO");
		sql.SELECT("IDDOCUMENTO");
		sql.SELECT("IDINSTITUCION");
		
		sql.SELECT("DESCRIPCION AS NOMBREDOCUMENTO");
		sql.SELECT("PATHDOCUMENTO");
		
		
		sql.FROM("ENV_DOCUMENTOS");
		
		sql.WHERE("IDINSTITUCION = '"+idInstitucion+"'");
		sql.WHERE("IDENVIO = '" + idEnvio + "'");
		
		return sql.toString();
	}

	public String getNewIdDocumento(String idInstitucion, String idEnvio) {
		SQL sql = new SQL();
		sql.SELECT(" (NVL(MAX(IDDOCUMENTO), 0) + 1) AS MAXVALOR");
		sql.FROM("ENV_DOCUMENTOS");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDENVIO = " + idEnvio);
		return sql.toString();
	}

}
