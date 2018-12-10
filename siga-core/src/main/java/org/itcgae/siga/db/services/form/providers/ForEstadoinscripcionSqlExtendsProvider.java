package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForEstadoinscripcionSqlProvider;

public class ForEstadoinscripcionSqlExtendsProvider extends ForEstadoinscripcionSqlProvider{

	public String distinctEstadoInscripcion(String idLenguaje) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("EI.IDESTADOINSCRIPCION");
		sql.SELECT("CAT.DESCRIPCION");
		sql.FROM("FOR_ESTADOINSCRIPCION EI");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON EI.DESCRIPCION = CAT.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("EI.FECHABAJA IS NULL");
		sql.WHERE("CAT.NOMBRETABLA = 'FOR_ESTADOINSCRIPCION'");
		sql.ORDER_BY("CAT.DESCRIPCION");
		
		return sql.toString();
	}
}
