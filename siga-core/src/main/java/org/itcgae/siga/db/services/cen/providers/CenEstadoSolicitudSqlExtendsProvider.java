package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;

public class CenEstadoSolicitudSqlExtendsProvider {


	public String getEstadoSolicitud(String idLenguage) {
		SQL sql = new SQL();
		
		sql.SELECT("E.IDESTADO AS VALUE");
		sql.SELECT("F_SIGA_GETRECURSO(DESCRIPCION," + idLenguage +") AS LABEL");
		sql.FROM("CEN_ESTADOSOLICITUD E");
		
		return sql.toString();
	}

}