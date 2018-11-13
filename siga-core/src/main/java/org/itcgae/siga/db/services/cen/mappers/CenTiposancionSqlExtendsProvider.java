package org.itcgae.siga.db.services.cen.mappers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenTiposancionSqlProvider;

public class CenTiposancionSqlExtendsProvider extends CenTiposancionSqlProvider {

	public String getComboTipoSancion() {
		SQL sql = new SQL();
		sql.SELECT("IDTIPOSANCION");
		sql.SELECT("DESCRIPCION");
		sql.FROM("CEN_TIPOSANCION");
		sql.ORDER_BY("DESCRIPCION");
		return sql.toString();
	}
}
