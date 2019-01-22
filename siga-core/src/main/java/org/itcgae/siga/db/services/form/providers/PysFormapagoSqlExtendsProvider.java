package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.PysFormapagoSqlProvider;

public class PysFormapagoSqlExtendsProvider extends PysFormapagoSqlProvider {

	public String getWayToPay(String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("cat.DESCRIPCION");
		sql.FROM("PYS_FORMAPAGO formaPago");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat ON formaPago.DESCRIPCION = cat.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.ORDER_BY("cat.DESCRIPCION");

		return sql.toString();
	}

	public String getWayToPayWithIdFormapago(String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("formaPago.IDFORMAPAGO");
		sql.SELECT("cat.DESCRIPCION");
		sql.FROM("PYS_FORMAPAGO formaPago");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat ON formaPago.DESCRIPCION = cat.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.ORDER_BY("cat.DESCRIPCION");

		return sql.toString();
	}

}
