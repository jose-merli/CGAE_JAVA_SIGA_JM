package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.DesignaItem;
import org.itcgae.siga.DTOs.scs.PrisionItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsPrisionSqlProvider;

public class ScsPrisionSqlExtendsProvider extends ScsPrisionSqlProvider {

	public String searchPrisiones(PrisionItem prisionItem, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("prision.idinstitucion");
		sql.SELECT("prision.idprision");
		sql.SELECT("prision.nombre");
		sql.SELECT("prision.domicilio");
		sql.SELECT("prision.codigopostal");
		sql.SELECT("prision.idpoblacion");
		sql.SELECT("prision.idprovincia");
		sql.SELECT("prision.telefono1");
		sql.SELECT("prision.telefono2");
		sql.SELECT("prision.fax1");
		sql.SELECT("prision.email");
		sql.SELECT("prision.fechabaja");
		sql.SELECT("prision.codigoext");
		sql.SELECT("prision.visiblemovil");
		sql.SELECT("POBLACION.NOMBRE AS NOMBREPOBLACION");
		sql.SELECT("PROVINCIAS.NOMBRE AS NOMBREPROVINCIA");

		sql.FROM("SCS_PRISION prision");
		sql.LEFT_OUTER_JOIN("CEN_PROVINCIAS PROVINCIAS ON PROVINCIAS.IDPROVINCIA = prision.IDPROVINCIA");
		sql.LEFT_OUTER_JOIN("CEN_POBLACIONES POBLACION ON POBLACION.IDPOBLACION = prision.IDPOBLACION");
		if (idInstitucion != 2000) {
			sql.WHERE("idinstitucion = '" + idInstitucion + "'");
		}
		if (prisionItem.getNombre() != null && prisionItem.getNombre() != "") {

			String columna = "PRISION.NOMBRE";
			String cadena = prisionItem.getNombre();

			sql.WHERE(UtilidadesString.filtroTextoBusquedas(columna, cadena));

		}

		if (prisionItem.getCodigoExt() != null && prisionItem.getCodigoExt() != "") {
			sql.WHERE("UPPER(prision.codigoext) LIKE UPPER('%" + prisionItem.getCodigoExt() + "%')");
		}

		if (prisionItem.getIdPoblacion() != null && prisionItem.getIdPoblacion() != "") {
			sql.WHERE("prision.idpoblacion = '" + prisionItem.getIdPoblacion() + "'");
		}

		if (prisionItem.getIdProvincia() != null && prisionItem.getIdProvincia() != "") {
			sql.WHERE("prision.idprovincia = '" + prisionItem.getIdProvincia() + "'");
		}

		if (!prisionItem.getHistorico()) {
			sql.WHERE("fechabaja is null");
		}

		sql.ORDER_BY("prision.nombre");

		return sql.toString();
	}

	public String getIdPrision(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDPRISION) AS IDPRISION");
		sql.FROM("SCS_PRISION");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	public String getComboPrisiones(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("IDPRISION");
		sql.SELECT("NOMBRE");
		sql.FROM("SCS_PRISION");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}
	

}
