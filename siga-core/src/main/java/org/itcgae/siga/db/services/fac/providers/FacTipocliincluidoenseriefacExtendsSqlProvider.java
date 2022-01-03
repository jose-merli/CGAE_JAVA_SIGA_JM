package org.itcgae.siga.db.services.fac.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.FacTipocliincluidoenseriefacSqlProvider;

public class FacTipocliincluidoenseriefacExtendsSqlProvider extends FacTipocliincluidoenseriefacSqlProvider {

	public String getEtiquetasSerie(String idSerieFacturacion, Short idInstitucion, String idioma) {
		SQL sql = new SQL();

		// Select
		sql.SELECT("t.idgrupo");
		sql.SELECT("f_siga_getrecurso( gp.nombre, '" + idioma + "' ) descripcion");

		// From
		sql.FROM("fac_tipocliincluidoenseriefac t");

		// Joins
		sql.INNER_JOIN("cen_gruposcliente gp ON ( t.idgrupo = gp.idgrupo AND gp.idinstitucion = t.idinstitucion )");

		// Where
		sql.WHERE("t.idinstitucion = " + idInstitucion);
		sql.WHERE("t.idseriefacturacion = '" + idSerieFacturacion + "'");

		// Order by
		sql.ORDER_BY("t.idgrupo");

		return sql.toString();
	}
	
}
