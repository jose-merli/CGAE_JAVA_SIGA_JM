package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForTemacursoSqlProvider;

public class ForTemacursoSqlExtendsProvider extends ForTemacursoSqlProvider{

	public String distinctTemaCurso(String idLenguaje) {
		
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("TC.IDTEMACURSO");
		sql.SELECT("CAT.DESCRIPCION");
		sql.FROM("FOR_TEMACURSO TC");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON TC.DESCRIPCION = CAT.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("TC.FECHABAJA IS NULL");
		sql.WHERE("CAT.NOMBRETABLA = 'FOR_TEMACURSO'");
		sql.ORDER_BY("CAT.DESCRIPCION");
		
		return sql.toString();
		
	}
	
}
