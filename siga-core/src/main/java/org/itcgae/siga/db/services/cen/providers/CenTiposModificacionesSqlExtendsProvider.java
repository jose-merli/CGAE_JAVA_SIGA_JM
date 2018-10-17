package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;

public class CenTiposModificacionesSqlExtendsProvider {
	
	public String getTipoModificacion(String idLenguage) {
		SQL sql = new SQL();
		sql.SELECT("tip.IDTIPOMODIFICACION AS VALUE");
		sql.SELECT("cat.DESCRIPCION AS LABEL");
		sql.FROM("CEN_TIPOSMODIFICACIONES tip");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat on cat.IDRECURSO = tip.DESCRIPCION");
		sql.WHERE("cat.IDLENGUAJE ='"+ idLenguage + "'");
		return sql.toString();
	}

}
