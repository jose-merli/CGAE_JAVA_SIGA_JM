package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;

public class CenSancionSqlExtendsProvider {

	public String getMaxIdSancion(String idInstitucion, String idPersona) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDSANCION) AS IDSANCION");
		sql.FROM("CEN_SANCION");
		sql.WHERE("IDPERSONA = '"+ idPersona +"'");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		
		return sql.toString();
	}
}
