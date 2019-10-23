package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.mappers.CenInfluenciaSqlProvider;

public class CenInfluenciaSqlExtendsProvider extends CenInfluenciaSqlProvider {

	public String getJudicialDistrict(String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("ju.IDPARTIDO");
		sql.SELECT("ju.NOMBRE");
		sql.FROM("CEN_PARTIDOJUDICIAL ju");
		sql.INNER_JOIN("CEN_INFLUENCIA inf ON inf.idpartido = ju.idpartido");
		sql.INNER_JOIN("CEN_INSTITUCION ins ON ins.IDINSTITUCION = inf.IDINSTITUCION");
		

		// Colegio
		if (Short.valueOf(idInstitucion) > Short.parseShort("2001")
				&& Short.valueOf(idInstitucion) < Short.parseShort("2100")) {
			sql.WHERE("ins.idinstitucion = '" + idInstitucion + "'");

		// Consejo
		} else if (!Short.valueOf(idInstitucion).equals(SigaConstants.IDINSTITUCION_2000)) {
			sql.WHERE("ins.CEN_INST_IDINSTITUCION = '" + idInstitucion + "'");

		}
		

		sql.ORDER_BY("NOMBRE");
		return sql.toString();
	}

	public String getIdZona(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDPARTIDO) AS IDPARTIDO");
		sql.FROM("CEN_INFLUENCIA");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}
}
