package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForTiposervicioCursoSqlProvider;

public class ForTiposervicioCursoSqlExtendsProvider extends ForTiposervicioCursoSqlProvider {

	public String getServicesSpecificCourse(String idInstitucion, String idCurso,String idLenguaje) {

		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("tc.idttiposervicio");
		sql.SELECT("cat.DESCRIPCION");
		sql.FROM("FOR_TIPOSERVICIO ts");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat ON ts.DESCRIPCION = cat.IDRECURSO");
		sql.INNER_JOIN("FOR_TIPOSERVICIO_CURSO tc on tc.IDTTIPOSERVICIO = ts.IDTIPOSERVICIO and tc.idCurso = '" + idCurso + "' ");
		sql.WHERE("tc.FECHABAJA IS NULL");
		sql.WHERE("ts.idinstitucion ='" + idInstitucion + "'");
		sql.WHERE("CAT.IDLENGUAJE = '" + idLenguaje + "'");
		sql.WHERE("ts.FECHA_BAJA IS NULL");
		sql.ORDER_BY("cat.DESCRIPCION");

		return sql.toString();
	}

}
