package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsCostefijoSqlProvider;

public class ScsRolesJusticiablesSqlExtendsProvider extends ScsCostefijoSqlProvider {

	public String getComboRoles(String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("rol.idRol");
		sql.SELECT("roldes.descripcion");
		sql.FROM("SCS_ROLESJUSTICIABLES rol");
		sql.INNER_JOIN(
				"GEN_DICCIONARIO roldes on roldes.IDRECURSO = rol.descripcion and roldes.idlenguaje = '"+ idLenguaje + "'");
		
		sql.ORDER_BY("roldes.descripcion");

		return sql.toString();
	}

}
