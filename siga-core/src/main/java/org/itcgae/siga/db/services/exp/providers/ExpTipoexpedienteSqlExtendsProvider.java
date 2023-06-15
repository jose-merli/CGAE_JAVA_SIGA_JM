package org.itcgae.siga.db.services.exp.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ExpTipoexpedienteSqlProvider;

public class ExpTipoexpedienteSqlExtendsProvider extends ExpTipoexpedienteSqlProvider{
	public String comboTipoExpediente(String idlenguaje, String idInstitucion){
		SQL sql = new SQL();
		sql.SELECT("tipoexpediente.NOMBRE");
		sql.SELECT("tipoexpediente.IDTIPOEXPEDIENTE");
		sql.FROM("EXP_TIPOEXPEDIENTE tipoexpediente");
		sql.WHERE("tipoexpediente.idinstitucion =" + idInstitucion);
		sql.ORDER_BY("NOMBRE");
		return sql.toString();
	}

}
