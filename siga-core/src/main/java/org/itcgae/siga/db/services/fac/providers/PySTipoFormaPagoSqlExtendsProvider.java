package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;

public class PySTipoFormaPagoSqlExtendsProvider {
	
	public String comboTipoFormaPago(String idioma) {
		SQL sql = new SQL();
		
		sql.SELECT("IDFORMAPAGO AS ID");
		sql.SELECT("f_siga_getrecurso (DESCRIPCION,'" + idioma + "') AS DESCRIPCION");
		
		sql.FROM("PYS_FORMAPAGO");
		
		sql.ORDER_BY("DESCRIPCION");
		
		return sql.toString();
	}

}
