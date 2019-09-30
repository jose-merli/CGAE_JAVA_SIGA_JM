package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.ComisariaItem;
import org.itcgae.siga.db.mappers.ScsComisariaSqlProvider;

public class ScsComisariaSqlExtendsProvider extends ScsComisariaSqlProvider {

	public String searchComisarias(ComisariaItem comisariaItem, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("comisaria.idinstitucion");
		sql.SELECT("comisaria.idcomisaria");
		sql.SELECT("comisaria.nombre");
		sql.SELECT("comisaria.domicilio");
		sql.SELECT("comisaria.codigopostal");
		sql.SELECT("comisaria.idpoblacion");
		sql.SELECT("comisaria.idprovincia");
		sql.SELECT("comisaria.telefono1");
		sql.SELECT("comisaria.telefono2");
		sql.SELECT("comisaria.fechabaja");
		sql.SELECT("comisaria.codigoext");
		sql.SELECT("comisaria.email");
		sql.SELECT("comisaria.visiblemovil");
		sql.SELECT("POBLACION.NOMBRE AS NOMBREPOBLACION");
		sql.SELECT("PROVINCIAS.NOMBRE AS NOMBREPROVINCIA");

		sql.FROM("SCS_COMISARIA comisaria");
		sql.LEFT_OUTER_JOIN("CEN_PROVINCIAS PROVINCIAS ON PROVINCIAS.IDPROVINCIA = comisaria.IDPROVINCIA");
		sql.LEFT_OUTER_JOIN("CEN_POBLACIONES POBLACION ON POBLACION.IDPOBLACION = comisaria.IDPOBLACION");

		sql.WHERE("idinstitucion = '" + idInstitucion + "'");

		if (comisariaItem.getNombre() != null && comisariaItem.getNombre() != "") {
			sql.WHERE("(TRANSLATE(LOWER( COMISARIA.NOMBRE),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN')  LIKE TRANSLATE(LOWER('%"
					+ comisariaItem.getNombre() + "%'),'áéíóúüñÁÉÍÓÚÜÑ','aeiouunAEIOUUN'))");
		}

		if (comisariaItem.getCodigoExt() != null && comisariaItem.getCodigoExt() != "") {
			sql.WHERE("UPPER(comisaria.codigoext) = UPPER('" + comisariaItem.getCodigoExt() + "')");
		}

		if (comisariaItem.getIdPoblacion() != null && comisariaItem.getIdPoblacion() != "") {
			sql.WHERE("comisaria.idpoblacion = '" + comisariaItem.getIdPoblacion() + "'");
		}

		if (comisariaItem.getIdProvincia() != null && comisariaItem.getIdProvincia() != "") {
			sql.WHERE("comisaria.idprovincia = '" + comisariaItem.getIdProvincia() + "'");
		}

		if (!comisariaItem.getHistorico()) {
			sql.WHERE("fechabaja is null");
		}

		sql.ORDER_BY("comisaria.nombre");

		return sql.toString();
	}

	public String getIdComisaria(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDCOMISARIA) AS IDCOMISARIA");
		sql.FROM("SCS_COMISARIA");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

}
