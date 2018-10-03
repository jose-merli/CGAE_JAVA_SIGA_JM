package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenClienteSqlProvider;

public class CenClienteSqlExtendsProvider extends CenClienteSqlProvider {

	public String getIdPersonaWithNif(String personaNif, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("p.idpersona");
		sql.FROM("cen_cliente cli ");
		sql.INNER_JOIN("cen_persona p on P.IDPERSONA = CLI.IDPERSONA");
		sql.WHERE("CLI.IDINSTITUCION = '" + idInstitucion.shortValue() + "'");
		sql.WHERE("p.nifcif = '" + personaNif + "'");

		return sql.toString();
	}

	public String getIdPersona(String colegiadoNumero, String personaNif, Short idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("p.idpersona");
		sql.FROM("cen_persona p");
		sql.INNER_JOIN("CEN_COLEGIADO col on P.IDPERSONA = COL.IDPERSONA");
		sql.WHERE("COL.IDINSTITUCION = '"+ idInstitucion.shortValue()  + "'");
		sql.WHERE("NVL(col.NCOLEGIADO, col.NCOMUNITARIO) = '"+ colegiadoNumero + "'");
		
		if(personaNif != null && personaNif != "") {
			sql.WHERE("p.nifcif = '"+ personaNif + "'");
		}
	
		
		return sql.toString();
	}

}
