package org.itcgae.siga.db.services.form.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ForRolesSqlProvider;

public class ForRolesSqlExtendsProvider extends ForRolesSqlProvider{

	public String getRolesTrainers(String idInstitucion) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("rol.IDROL");
		sql.SELECT("cat.DESCRIPCION");
		sql.FROM("FOR_ROLES rol");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS cat ON rol.DESCRIPCION = cat.IDRECURSO");
		sql.WHERE("rol.FECHA_BAJA IS NULL");
		sql.WHERE("rol.idinstitucion ='"+ idInstitucion +"'");
		sql.ORDER_BY("cat.DESCRIPCION");
		
		return sql.toString();
	}
	
}
