package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenColegioprocuradorSqlProvider;

public class CenColegioprocuradorSqlExtendsProvider extends CenColegioprocuradorSqlProvider{

	public String selectDistinctColegios() {
		SQL sql= new SQL();
		
		sql.SELECT_DISTINCT("IDCOLPROCURADOR");
		sql.SELECT_DISTINCT("NOMBRE");
		sql.FROM("CEN_COLEGIOPROCURADOR");
		sql.ORDER_BY("NOMBRE");
		
		return sql.toString();
	}
}
