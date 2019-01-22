package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForTipocosteSqlProvider;

public class ForTipocosteSqlExtendsProvider extends ForTipocosteSqlProvider{

	public String getTypeCostTrainers(String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("tc.IDTIPOCOSTE");
		sql.SELECT("CAT.DESCRIPCION");
		sql.FROM("FOR_TIPOCOSTE tc");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON tc.DESCRIPCION = CAT.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("tc.FECHABAJA IS NULL");
		sql.WHERE("CAT.NOMBRETABLA = 'FOR_TIPOCOSTE'");
		sql.ORDER_BY("CAT.DESCRIPCION");
		
		return sql.toString();
	}
	
}
