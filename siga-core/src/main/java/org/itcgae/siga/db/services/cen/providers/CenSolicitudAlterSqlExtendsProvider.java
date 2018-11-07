package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;

public class CenSolicitudAlterSqlExtendsProvider {
	

	public String getMaxIdSolicitud(){
		SQL sql = new SQL();
		sql.SELECT("MAX(IDSOLICITUD) AS IDSOLICITUD");
		sql.FROM("CEN_SOLICITUDINCORPORACION");
		return sql.toString();
	}

}
