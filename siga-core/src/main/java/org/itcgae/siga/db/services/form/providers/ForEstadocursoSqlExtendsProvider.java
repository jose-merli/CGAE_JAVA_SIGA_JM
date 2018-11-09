package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForEstadocursoSqlProvider;

public class ForEstadocursoSqlExtendsProvider extends ForEstadocursoSqlProvider{

	public String distinctEstadoCurso(String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("EC.IDESTADOCURSO");
		sql.SELECT("CAT.DESCRIPCION");
		sql.FROM("FOR_ESTADOCURSO EC");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON EC.DESCRIPCION = CAT.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("EC.FECHABAJA IS NULL");
		sql.WHERE("CAT.NOMBRETABLA = 'FOR_ESTADOCURSO'");
		sql.ORDER_BY("CAT.DESCRIPCION");
		
		return sql.toString();
	}
	
}
