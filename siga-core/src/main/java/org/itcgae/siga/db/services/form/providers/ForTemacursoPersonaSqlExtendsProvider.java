package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForTemacursoSqlProvider;

public class ForTemacursoPersonaSqlExtendsProvider extends ForTemacursoSqlProvider {
	
	public String getTopicsSpecificPerson(String idInstitucion, String idPersona, String idLenguaje) {

		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("tc.IDTEMACURSO");
		sql.SELECT("cat.DESCRIPCION");
		sql.FROM("FOR_TEMACURSO tm");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat ON tm.DESCRIPCION = cat.IDRECURSO");
		sql.INNER_JOIN("FOR_TEMACURSO_PERSONA tc on tc.IDTEMACURSO = tm.IDTEMACURSO and tc.IDPERSONA = '" + idPersona + "' ");
		sql.WHERE("tc.FECHABAJA IS NULL");
		sql.WHERE("tm.idinstitucion ='" + idInstitucion + "'");
		sql.WHERE("tm.FECHABAJA IS NULL");
		sql.WHERE("cat.idlenguaje ='" + idLenguaje + "'");
		sql.ORDER_BY("cat.DESCRIPCION");

		return sql.toString();
	}

}
