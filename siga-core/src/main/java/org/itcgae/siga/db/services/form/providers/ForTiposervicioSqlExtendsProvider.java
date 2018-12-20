package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForTiposervicioSqlProvider;

public class ForTiposervicioSqlExtendsProvider extends ForTiposervicioSqlProvider {

	public String getServicesCourse(String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("ts.idtiposervicio");
		sql.SELECT("cat.DESCRIPCION");
		sql.FROM("FOR_TIPOSERVICIO ts");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat ON ts.DESCRIPCION = cat.IDRECURSO");
		sql.WHERE("ts.FECHA_BAJA IS NULL");
		sql.WHERE("ts.idinstitucion ='" + idInstitucion + "'");
		sql.ORDER_BY("cat.DESCRIPCION");

		return sql.toString();
	}

	

}
