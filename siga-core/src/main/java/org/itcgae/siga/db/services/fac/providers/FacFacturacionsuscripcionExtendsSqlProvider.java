package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacFacturacionsuscripcionSqlProvider;

public class FacFacturacionsuscripcionExtendsSqlProvider extends FacFacturacionsuscripcionSqlProvider {

	public String getTiposServicios(Short idInstitucion, String idioma) {
		SQL sql = new SQL();

		// Select
		sql.SELECT_DISTINCT("fs.idtiposervicios");
		sql.SELECT_DISTINCT("f_siga_getrecurso( ts.descripcion, '" + idioma + "' ) descripcion");
		sql.SELECT_DISTINCT("sf.idseriefacturacion");

		// From
		sql.FROM("fac_facturacionsuscripcion fs");

		// Joins
		sql.INNER_JOIN("pys_tiposervicios ts ON ( fs.idtiposervicios = ts.idtiposervicios )");
		sql.INNER_JOIN("fac_factura f ON ( f.idinstitucion = fs.idinstitucion AND f.idfactura = fs.idfactura )");
		sql.INNER_JOIN("fac_seriefacturacion sf ON ( sf.idinstitucion = fs.idinstitucion AND sf.idseriefacturacion = f.idseriefacturacion )");

		// Where
		sql.WHERE("fs.idinstitucion = " + idInstitucion);

		// Order by
		sql.ORDER_BY("sf.idseriefacturacion, fs.idtiposervicios");

		return sql.toString();
	}

}
