package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.JusticiableItem;
import org.itcgae.siga.db.mappers.ScsTelefonospersonaSqlProvider;

public class ScsTelefonosPersonaSqlExtendsProvider extends ScsTelefonospersonaSqlProvider {

	public String getIdTelefono(JusticiableItem justiciableItem, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDTELEFONO) AS IDTELEFONO");
		sql.FROM("SCS_TELEFONOSPERSONA");
		sql.WHERE("IDINSTITUCION = '"+ idInstitucion +"'");
		sql.WHERE("IDPERSONA = '"+ justiciableItem.getIdPersona() +"'");

		return sql.toString();
	}

}
