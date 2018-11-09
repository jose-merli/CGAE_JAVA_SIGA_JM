package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;

public class CenEstadoSolicitudModifSqlExtendsProvider {

	public String getEstado(String idLenguage) {
		SQL sql = new SQL();
		sql.SELECT("est.IDESTADOSOLIC AS VALUE");
		sql.SELECT("cat.DESCRIPCION AS LABEL");
		sql.FROM("CEN_ESTADOSOLICITUDMODIF est");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat on cat.idRecurso = est.descripcion");
		sql.WHERE("cat.IDLENGUAJE ='"+ idLenguage + "'");
		return sql.toString();
	}
}
