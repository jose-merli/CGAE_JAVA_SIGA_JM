package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForRolesSqlProvider;

public class ForRolesSqlExtendsProvider extends ForRolesSqlProvider{

	public String getRolesTrainers(String idInstitucion) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("rol.IDROL");
		sql.SELECT("rol.DESCRIPCION");
		sql.FROM("FOR_ROLES rol");
		sql.WHERE("rol.FECHABAJA IS NULL");
		sql.ORDER_BY("rol.DESCRIPCION");
		
		return sql.toString();
	}
	
}
