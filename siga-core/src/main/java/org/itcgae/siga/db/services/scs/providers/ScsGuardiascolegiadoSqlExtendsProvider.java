package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsCabeceraguardiasSqlProvider;

public class ScsGuardiascolegiadoSqlExtendsProvider extends ScsCabeceraguardiasSqlProvider{

	public String getTurnosGuardias(String idPersona) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();
		SQL sql3 = new SQL();
		sql.SELECT("COUNT(1)");
		sql.FROM("scs_inscripcionguardia");
		sql.WHERE("idpersona = " + idPersona);
		sql.WHERE("fechasuscripcion IS NOT NULL");
		sql.WHERE("fechabaja IS NULL");
		sql.WHERE("fechadenegacion IS NULL");
		sql.WHERE("fechavalidacion IS NOT NULL");
		
		sql2.SELECT("COUNT(1)");
		sql2.FROM("scs_inscripcionturno");
		sql2.WHERE("idpersona = " + idPersona);
		sql2.WHERE("fechasolicitud IS NOT NULL");
		sql2.WHERE("fechabaja IS NULL");
		sql2.WHERE("fechadenegacion IS NULL");
		sql2.WHERE("fechavalidacion IS NOT NULL");
		
		sql3.SELECT("(" + sql.toString() + ") + (" + sql2.toString() + ") COUNT");
		sql3.FROM("dual");

		return sql3.toString();
	}
}

