package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacTiposproduincluenfactuSqlProvider;
import org.itcgae.siga.db.mappers.PysCompraSqlProvider;

public class FacTiposproduincluenfactuExtendsSqlProvider extends FacTiposproduincluenfactuSqlProvider {

	public String getTiposProductos(String idSerieFacturacion, Short idInstitucion, String idioma) {
		SQL sql = new SQL();

		// Select
		sql.SELECT_DISTINCT("(ft.idtipoproducto || '-' || ft.idproducto ) value");
		sql.SELECT_DISTINCT("pt.descripcion as label");

		// From
		sql.FROM("fac_tiposproduincluenfactu ft");

		// Joins
		sql.INNER_JOIN("pys_productos pt ON (  ft.idtipoproducto = pt.idtipoproducto AND ft.idproducto = pt.idproducto AND ft.idinstitucion = pt.idinstitucion )");

		// Where
		sql.WHERE("ft.idinstitucion = " + idInstitucion);
		sql.WHERE("ft.idseriefacturacion = '" + idSerieFacturacion + "'");

		// Order by
		sql.ORDER_BY("label");

		return sql.toString();
	}
	
}
