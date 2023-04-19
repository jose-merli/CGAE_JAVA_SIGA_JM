package org.itcgae.siga.db.mappers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.entities.AdmUsuarios;

public class AdmUsuarioEfectivoSqlExtendsProvider extends AdmUsuarioEfectivoSqlProvider {
	
	public String isLetrado(AdmUsuarios usuario) {

		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("ADMR.LETRADO");
		sql.FROM("ADM_USUARIO_EFECTIVO ADMUE");
		sql.INNER_JOIN("ADM_USUARIOS ADMU ON ADMUE.IDUSUARIO = ADMU.IDUSUARIO");
		sql.INNER_JOIN("ADM_ROL ADMR ON ADMUE.IDROL = ADMR.IDROL");
		sql.WHERE("ADMU.NIF = '" + usuario.getNif() + "'");
		sql.WHERE("ADMU.IDINSTITUCION = '" + usuario.getIdinstitucion() + "'");

		return sql.toString();
	}
}
