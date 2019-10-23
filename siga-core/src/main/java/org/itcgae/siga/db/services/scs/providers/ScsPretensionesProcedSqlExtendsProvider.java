package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsPretensionesprocedSqlProvider;

public class ScsPretensionesProcedSqlExtendsProvider extends ScsPretensionesprocedSqlProvider {

	public String getIdPretension(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDPRETENSION) AS IDPRETENSION");
		sql.FROM("SCS_PRETENSIONESPROCED");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");

		return sql.toString();
	}
	
}
