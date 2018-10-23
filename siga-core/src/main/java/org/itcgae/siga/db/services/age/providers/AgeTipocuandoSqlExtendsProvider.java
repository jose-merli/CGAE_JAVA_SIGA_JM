package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.AgeTipocuandoSqlProvider;

public class AgeTipocuandoSqlExtendsProvider extends  AgeTipocuandoSqlProvider{

		
	public String getTypeWhere(String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("tipocuando.IDTIPOCUANDO");
		sql.SELECT("rec.DESCRIPCION");
		sql.FROM("AGE_TIPOCUANDO tipocuando");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tipocuando.DESCRIPCION AND rec.IDLENGUAJE = '"
				+ idLenguaje + "')");
		sql.ORDER_BY("DESCRIPCION");
		
		return sql.toString();
	}

}
