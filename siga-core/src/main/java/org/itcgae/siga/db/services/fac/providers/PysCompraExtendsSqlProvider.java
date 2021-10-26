package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.PysCompraSqlProvider;

public class PysCompraExtendsSqlProvider extends PysCompraSqlProvider {

	public String getTiposProductos(String idSerieFacturacion, Short idInstitucion, String idioma) {
		SQL sql = new SQL();

		// Select
		sql.SELECT_DISTINCT("ft.idtipoproducto");
		sql.SELECT_DISTINCT("f_siga_getrecurso( pt.descripcion, '" + idioma + "' ) descripcion");

		// From
		sql.FROM("fac_tiposproduincluenfactu ft");

		// Joins
		sql.INNER_JOIN("pys_tiposproductos pt ON (  ft.idtipoproducto = pt.idtipoproducto )");

		// Where
		sql.WHERE("ft.idinstitucion = " + idInstitucion);
		sql.WHERE("ft.idseriefacturacion = '" + idSerieFacturacion + "'");

		// Order by
		sql.ORDER_BY("ft.idtipoproducto");

		return sql.toString();
	}
	
}
