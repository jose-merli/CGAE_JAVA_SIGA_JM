package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenBancosSqlProvider;

public class CenBancosSqlExtendsProvider extends CenBancosSqlProvider{

	public String getMaxCode() {
		SQL sql = new SQL();
		sql.SELECT("MAX(BANCOS.CODIGO) + 1 AS CODIGO");
		sql.FROM("CEN_BANCOS BANCOS");
		sql.WHERE("BANCOS.NOMBRE = 'BANCO EXTRANJERO'");
	
		return sql.toString();
	}
}
