package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenProvinciasSqlProvider;

public class CenProvinciasSqlExtendsProvider extends CenProvinciasSqlProvider{

	public String selectDistinctProvinces() {
		SQL sql= new SQL();
		
		sql.SELECT_DISTINCT("IDPROVINCIA");
		sql.SELECT_DISTINCT("NOMBRE");
		sql.FROM("CEN_PROVINCIAS");
		sql.ORDER_BY("NOMBRE");
		
		return sql.toString();
	}
}
