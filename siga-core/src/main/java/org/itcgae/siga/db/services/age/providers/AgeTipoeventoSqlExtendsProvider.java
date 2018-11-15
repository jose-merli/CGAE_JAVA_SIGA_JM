package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgeTipoeventosSqlProvider;

public class AgeTipoeventoSqlExtendsProvider extends  AgeTipoeventosSqlProvider{

	
	public String getTypeEvent(String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("tipoeventos.IDTIPOEVENTO");
		sql.SELECT("rec.DESCRIPCION");
		sql.FROM("AGE_TIPOEVENTOS tipoeventos");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tipoeventos.DESCRIPCION AND rec.IDLENGUAJE = '"
				+ idLenguaje + "')");
		sql.ORDER_BY("rec.DESCRIPCION");
		
		return sql.toString();
		
	}
	
}
