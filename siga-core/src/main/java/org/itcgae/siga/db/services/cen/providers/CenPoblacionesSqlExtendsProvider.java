package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenPoblacionesSqlProvider;

public class CenPoblacionesSqlExtendsProvider extends CenPoblacionesSqlProvider{

	public String getComboPoblaciones(String idProvincia) {
		
		SQL sql = new SQL();

		sql.SELECT("IDPOBLACION");
		sql.SELECT("NOMBRE");
		sql.FROM("CEN_POBLACIONES");
		sql.WHERE("IDPROVINCIA ='" + idProvincia + "'");
		sql.ORDER_BY("NOMBRE");

		return sql.toString();
		
	}
}