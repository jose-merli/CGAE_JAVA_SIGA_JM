package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenGruposclienteClienteSqlProvider;

public class CenGruposclienteClienteSqlExtendsProvider extends CenGruposclienteClienteSqlProvider{

		
	public String insertSelectiveForCreateLegalPerson(String idInstitucion, String grupo, String idUsuario) {
		
		SQL sql = new SQL();
		
		sql.INSERT_INTO("CEN_GRUPOSCLIENTE_CLIENTE");
		sql.VALUES("IDPERSONA", "(Select max(idpersona)  from cen_persona)");
		sql.VALUES("IDINSTITUCION", idInstitucion);
		if(!grupo.equals("")) {
			sql.VALUES("IDGRUPO", grupo);
		}
		else {
			sql.VALUES("IDGRUPO", "(SELECT MAX(IDGRUPO) FROM CEN_GRUPOSCLIENTE)");
		}
		
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", idUsuario);
		sql.VALUES("IDINSTITUCION_GRUPO", idInstitucion);
		sql.VALUES("FECHA_BAJA", null);
		sql.VALUES("FECHA_INICIO", "SYSDATE");
		return sql.toString();
	}
	
	
}

