package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.PysCompraSqlProvider;

public class PysCompraExtendsSqlProvider extends PysCompraSqlProvider {

	public String getTiposProductos(String idSerieFacturacion, Short idInstitucion, String idioma) {
		SQL sql = new SQL();

		// Select
		sql.SELECT_DISTINCT("c.idtipoproducto");
		sql.SELECT_DISTINCT("f_siga_getrecurso( p.descripcion, '" + idioma + "' ) descripcion");
		sql.SELECT_DISTINCT("sf.idseriefacturacion");

		// From
		sql.FROM("pys_compra c");

		// Joins
		sql.INNER_JOIN("fac_factura f ON ( f.idfactura = c.idfactura AND f.idinstitucion = c.idinstitucion )");
		sql.INNER_JOIN("pys_tiposproductos p ON ( p.idtipoproducto = c.idtipoproducto )");
		sql.INNER_JOIN("fac_seriefacturacion sf ON ( f.idseriefacturacion = sf.idseriefacturacion AND sf.idinstitucion = c.idinstitucion )");

		// Where
		sql.WHERE("c.idinstitucion = " + idInstitucion);
		sql.WHERE("sf.idseriefacturacion = '" + idSerieFacturacion + "'");

		// Order by
		sql.ORDER_BY("sf.idseriefacturacion, c.idtipoproducto");

		return sql.toString();
	}
	
}
