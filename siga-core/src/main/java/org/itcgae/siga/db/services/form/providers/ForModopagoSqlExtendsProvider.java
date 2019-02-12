package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForModopagoSqlProvider;

public class ForModopagoSqlExtendsProvider extends ForModopagoSqlProvider{

	public String getPaymentMode(String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("mp.IDMODOPAGO");
		sql.SELECT("CAT.DESCRIPCION");
		sql.FROM("FOR_MODOPAGO mp");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON mp.DESCRIPCION = CAT.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("mp.FECHABAJA IS NULL");
		sql.WHERE("CAT.NOMBRETABLA = 'FOR_MODOPAGO'");
		sql.ORDER_BY("CAT.DESCRIPCION");
		
		return sql.toString();
	}
	
}
