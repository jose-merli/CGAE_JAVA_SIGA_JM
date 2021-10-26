package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacFacturacionsuscripcionSqlProvider;

public class FacFacturacionsuscripcionExtendsSqlProvider extends FacFacturacionsuscripcionSqlProvider {

	public String getTiposServicios(String idSerieFacturacion, Short idInstitucion, String idioma) {
		SQL sql = new SQL();

		// Select
		sql.SELECT_DISTINCT("ft.idtiposervicios");
		sql.SELECT_DISTINCT("f_siga_getrecurso( pt.descripcion, '" + idioma + "' ) descripcion");

		// From
		sql.FROM("fac_tiposservinclsenfact ft");

		// Joins
		sql.INNER_JOIN("pys_tiposervicios pt ON ( ft.idtiposervicios = pt.idtiposervicios )");

		// Where
		sql.WHERE("ft.idinstitucion = " + idInstitucion);
		sql.WHERE("ft.idseriefacturacion = '" + idSerieFacturacion + "'");

		// Order by
		sql.ORDER_BY("ft.idtiposervicios");

		return sql.toString();
	}

}
