package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenEstadocolegialSqlProvider;

public class CenEstadocolegialSqlExtendsProvider extends CenEstadocolegialSqlProvider{

	public String distinctSituacionColegial(String idLenguaje) {
		
		SQL sql = new SQL();

		sql.SELECT_DISTINCT("EST.IDESTADO");
		sql.SELECT("CAT.DESCRIPCION");
		sql.FROM("CEN_ESTADOCOLEGIAL EST");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON EST.DESCRIPCION = CAT.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.ORDER_BY("CAT.DESCRIPCION");
		
		return sql.toString();
	}
}
