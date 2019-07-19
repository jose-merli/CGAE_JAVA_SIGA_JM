package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgeEstadoeventosSqlProvider;

public class AgeEstadoeventosSqlExtendsProvider extends  AgeEstadoeventosSqlProvider{

	
	public String getEventStates(String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("estadoeventos.IDESTADOEVENTO");
		sql.SELECT("INITCAP(rec.DESCRIPCION) AS DESCRIPCION");
		sql.FROM("AGE_ESTADOEVENTOS estadoeventos");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = estadoeventos.DESCRIPCION AND rec.IDLENGUAJE = '"
				+ idLenguaje + "')");
		sql.ORDER_BY("DESCRIPCION");
		
		return sql.toString();
		
	}
	
}
