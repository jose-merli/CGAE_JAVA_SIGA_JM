package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgeDiassemanaSqlProvider;

public class AgeDiassemanaSqlExtendsProvider extends  AgeDiassemanaSqlProvider{

	
	public String getDaysWeek(String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("ds.IDDIASSEMANA");
		sql.SELECT("rec.DESCRIPCION");
		sql.FROM("AGE_DIASSEMANA ds");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = ds.DESCRIPCION AND rec.IDLENGUAJE = '"
				+ idLenguaje + "')");
		sql.ORDER_BY("ds.IDDIASSEMANA");
		
		return sql.toString();
	}
	
}
