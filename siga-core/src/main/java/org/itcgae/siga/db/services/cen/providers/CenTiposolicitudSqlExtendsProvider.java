package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;

public class CenTiposolicitudSqlExtendsProvider {


	public String getTipoSolicitud(String idLenguage, String isEXEA) {
		SQL sql = new SQL();
		sql.SELECT("E.IDTIPOSOLICITUD AS VALUE");
		sql.SELECT("INITCAP(F_SIGA_GETRECURSO(DESCRIPCION," + idLenguage +")) AS LABEL");
		sql.FROM("CEN_TIPOSOLICITUD E");
		sql.WHERE("EXEA ='"+isEXEA+"'");
		sql.ORDER_BY("LABEL");
		return sql.toString();
	}

}