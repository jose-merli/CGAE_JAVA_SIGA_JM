package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForVisibilidadcursoSqlProvider;

public class ForVisibilidadcursoSqlExtendsProvider extends ForVisibilidadcursoSqlProvider{

	public String distinctVisibilidadCurso(String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("VC.IDVISIBILIDADCURSO");
		sql.SELECT("CAT.DESCRIPCION");
		sql.FROM("FOR_VISIBILIDADCURSO VC");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON VC.DESCRIPCION = CAT.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("VC.FECHABAJA IS NULL");
		sql.WHERE("CAT.NOMBRETABLA = 'FOR_VISIBILIDADCURSO'");
		sql.ORDER_BY("CAT.DESCRIPCION");
		
		return sql.toString();
	}
	
}
