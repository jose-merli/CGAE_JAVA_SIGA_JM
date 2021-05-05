package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenInfluenciaSqlProvider;

public class CenInfluenciaSqlExtendsProvider extends CenInfluenciaSqlProvider {

	public String getJudicialDistrict(String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("ju.IDPARTIDO");
		sql.SELECT("ju.NOMBRE");
		sql.FROM("CEN_INFLUENCIA inf");
		sql.INNER_JOIN("CEN_PARTIDOJUDICIAL ju ON inf.idpartido = ju.idpartido");
		

		// Colegio
		sql.WHERE("inf.idinstitucion = '" + idInstitucion + "'");

		// Consejo

		

		sql.ORDER_BY("NOMBRE");
		return sql.toString();
	}



	
}
