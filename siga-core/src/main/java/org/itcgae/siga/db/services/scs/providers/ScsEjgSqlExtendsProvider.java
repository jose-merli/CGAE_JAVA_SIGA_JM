package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsEjgSqlProvider;

public class ScsEjgSqlExtendsProvider extends ScsEjgSqlProvider {
	
	public String comboCreadoDesde(String idLenguaje, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("ejg.ORIGENAPERTURA");
		sql.FROM("scs_ejg ejg");
		sql.WHERE("ejg.idinstitucion =" + idInstitucion);
		sql.ORDER_BY("ORIGENAPERTURA");
		return sql.toString();
	}
}
