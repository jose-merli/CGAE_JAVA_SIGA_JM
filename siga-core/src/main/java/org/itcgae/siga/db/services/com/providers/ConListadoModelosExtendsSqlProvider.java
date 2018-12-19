package org.itcgae.siga.db.services.com.providers;

import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;

public class ConListadoModelosExtendsSqlProvider {
	public String selectListadoModelos(Short idInstitucion, String idConsulta) {

		SQL sql = new SQL();
		// Formateo de fecha para sentencia sql
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		// TODO: Consulta real

		
		return sql.toString();
	}


}
