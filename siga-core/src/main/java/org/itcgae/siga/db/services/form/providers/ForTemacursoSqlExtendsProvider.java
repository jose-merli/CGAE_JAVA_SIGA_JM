package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForTemacursoSqlProvider;

public class ForTemacursoSqlExtendsProvider extends ForTemacursoSqlProvider {

	public String distinctTemaCurso(Short idInstitucion, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("TC.IDTEMACURSO");
		sql.SELECT("CAT.DESCRIPCION");
		sql.FROM("FOR_TEMACURSO TC");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON TC.DESCRIPCION = CAT.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("TC.FECHABAJA IS NULL");
		sql.WHERE("CAT.NOMBRETABLA = 'FOR_TEMACURSO'");
		sql.WHERE("tc.idinstitucion ='" + idInstitucion + "'");
		sql.ORDER_BY("CAT.DESCRIPCION");

		return sql.toString();

	}

	public String getTopicsCourse(String idLenguaje, String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("TC.IDTEMACURSO");
		sql.SELECT("CAT.DESCRIPCION");
		sql.FROM("FOR_TEMACURSO TC");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS CAT ON TC.DESCRIPCION = CAT.IDRECURSO");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("TC.FECHABAJA IS NULL");
		sql.WHERE("CAT.NOMBRETABLA = 'FOR_TEMACURSO'");
		sql.WHERE("tc.idinstitucion ='" + idInstitucion + "'");
		sql.ORDER_BY("CAT.DESCRIPCION");

		return sql.toString();

	}
	
	public String getTopicsSpecificCourse(String idLenguaje,String idInstitucion, String idCurso) {

		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("tc.IDTEMACURSO");
		sql.SELECT("cat.DESCRIPCION");
		sql.FROM("FOR_TEMACURSO tm");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat ON tm.DESCRIPCION = cat.IDRECURSO");
		sql.INNER_JOIN("FOR_TEMACURSO_CURSO tc on tc.IDTEMACURSO = tm.IDTEMACURSO and tc.idCurso = '" + idCurso + "' ");
		sql.WHERE("tc.FECHABAJA IS NULL");
		sql.WHERE("tm.idinstitucion ='" + idInstitucion + "'");
		sql.WHERE("tm.FECHABAJA IS NULL");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.ORDER_BY("cat.DESCRIPCION");

		return sql.toString();
	}

}
