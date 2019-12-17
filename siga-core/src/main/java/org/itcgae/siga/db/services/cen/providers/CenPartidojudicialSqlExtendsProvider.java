package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenPartidojudicialSqlProvider;

public class CenPartidojudicialSqlExtendsProvider extends CenPartidojudicialSqlProvider {

	public String getPartidoByInstitucion(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("PAR.IDPARTIDO");
		sql.SELECT("PAR.NOMBRE");
		sql.SELECT("PAR.FECHAMODIFICACION");
		sql.SELECT("PAR.USUMODIFICACION");
		sql.SELECT("PAR.IDINSTITUCIONPROPIETARIO");
		sql.SELECT("PAR.CODIGOEXT");

		sql.FROM("CEN_PARTIDOJUDICIAL PAR");
		sql.INNER_JOIN("CEN_INFLUENCIA INF ON PAR.IDPARTIDO = INF.IDPARTIDO");

		sql.WHERE("INF.IDINSTITUCION = '" + String.valueOf(idInstitucion) + "'");
		sql.ORDER_BY("PAR.NOMBRE");
		return sql.toString();
	}

	public String getPartidosJudiciales() {

		SQL sql = new SQL();

		sql.SELECT("PAR.IDPARTIDO");
		sql.SELECT("PAR.NOMBRE");

		sql.FROM("CEN_PARTIDOJUDICIAL PAR");
		sql.ORDER_BY("PAR.NOMBRE");

		return sql.toString();
	}
	
	public String getPartidos(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("PAR.IDPARTIDO");
		sql.SELECT("PAR.NOMBRE");

		sql.FROM("CEN_PARTIDOJUDICIAL PAR");
		sql.INNER_JOIN("CEN_INFLUENCIA INF ON PAR.IDPARTIDO = INF.IDPARTIDO");

		sql.WHERE("INF.IDINSTITUCION = '" + String.valueOf(idInstitucion) + "'");
		sql.ORDER_BY("PAR.NOMBRE");

		return sql.toString();
	}
	
}
