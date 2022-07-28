package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;

public class CenSolicitudAlterSqlExtendsProvider {
	

	public String getMaxIdSolicitud(){
		SQL sql = new SQL();
		sql.SELECT("MAX(IDSOLICITUD) +1 AS IDSOLICITUD");
		sql.FROM("CEN_SOLICITUDALTER");
		return sql.toString();
	}

}
