package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsOrigencajgSqlProvider;

public class ScsOrigencajgExtendsProvider extends ScsOrigencajgSqlProvider {
	public String comboOrigen(String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("idorigencajg," + 
				" descripcion");
		sql.FROM("scs_origencajg");
		sql.ORDER_BY("descripcion");
		return sql.toString();
	}
}
