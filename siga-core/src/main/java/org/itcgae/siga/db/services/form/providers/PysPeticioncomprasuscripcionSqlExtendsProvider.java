package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.PysPeticioncomprasuscripcionSqlProvider;

public class PysPeticioncomprasuscripcionSqlExtendsProvider extends PysPeticioncomprasuscripcionSqlProvider {

	public String selectMaxIdPeticion(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("NVL(max(IDPETICION) +1, 0) AS IDPETICION");
		sql.FROM("PYS_PETICIONCOMPRASUSCRIPCION");
		sql.WHERE("idInstitucion =" + idInstitucion);
		
		return sql.toString();
	}

	public String selectMaxNumOperacion(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("NVL(max(NUM_OPERACION) +1, 0) AS NUM_OPERACION");
		sql.FROM("PYS_PETICIONCOMPRASUSCRIPCION");
		sql.WHERE("idInstitucion =" + idInstitucion);
		
		return sql.toString();
	}
}
