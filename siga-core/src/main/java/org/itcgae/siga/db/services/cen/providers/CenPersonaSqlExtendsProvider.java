package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenPersonaSqlProvider;

public class CenPersonaSqlExtendsProvider extends CenPersonaSqlProvider{

	public String loadPhotography(String idPersona) {
		SQL sql = new SQL();
		
		sql.SELECT("PER.NIFCIF");
		sql.SELECT("CLI.FOTOGRAFIA");
		sql.FROM(" CEN_CLIENTE CLI");
		sql.INNER_JOIN("  CEN_PERSONA PER ON PER.IDPERSONA = CLI.IDPERSONA ");
		sql.WHERE(" PER.IDPERSONA ='" + idPersona +"'");
		
		return sql.toString();
	}
}
