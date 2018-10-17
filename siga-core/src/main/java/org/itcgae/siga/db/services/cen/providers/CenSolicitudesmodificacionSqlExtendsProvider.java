package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;

public class CenSolicitudesmodificacionSqlExtendsProvider {

	public String getTipoModificacion(String idLenguage) {
		SQL sql = new SQL();
		sql.SELECT("E.IDTIPOSOLICITUD AS VALUE");
		sql.SELECT("F_SIGA_GETRECURSO(DESCRIPCION," + idLenguage +") AS LABEL");
		sql.FROM("CEN_TIPOSOLICITUD E");
		return sql.toString();
	}
}
