package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacFacturacionsuscripcionSqlProvider;
import org.itcgae.siga.db.mappers.FacTiposservinclsenfactSqlProvider;

public class FacTiposservinclsenfactExtendsSqlProvider extends FacTiposservinclsenfactSqlProvider {

	public String getTiposServicios(String idSerieFacturacion, Short idInstitucion, String idioma) {
		SQL sql = new SQL();

		// Select
		sql.SELECT_DISTINCT("(ft.idtiposervicios || '-' || ft.idservicio) value");
		sql.SELECT_DISTINCT("pt.descripcion as label");

		// From
		sql.FROM("fac_tiposservinclsenfact ft");

		// Joins
		sql.INNER_JOIN("pys_servicios pt ON ( ft.idtiposervicios = pt.idtiposervicios AND ft.idservicio = pt.idservicio AND ft.idinstitucion = pt.idinstitucion )");

		// Where
		sql.WHERE("ft.idinstitucion = " + idInstitucion);
		sql.WHERE("ft.idseriefacturacion = '" + idSerieFacturacion + "'");

		// Order by
		sql.ORDER_BY("label");

		return sql.toString();
	}

}
