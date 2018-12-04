package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;

public class CenSancionSqlExtendsProvider {

	public String getMaxIdSancion(String idTipoSancion, String idPersona) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDSANCION) AS IDSANCION");
		sql.FROM("CEN_SANCION");
		sql.WHERE("IDTIPOSANCION = '"+idTipoSancion+"'");
		sql.WHERE("IDPERSONA = '"+ idPersona +"'");
		
		return sql.toString();
	}
}
