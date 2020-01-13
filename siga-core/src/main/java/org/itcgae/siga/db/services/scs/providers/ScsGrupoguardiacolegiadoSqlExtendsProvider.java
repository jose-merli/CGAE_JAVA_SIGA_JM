package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsGrupoguardiacolegiadoSqlProvider;

public class ScsGrupoguardiacolegiadoSqlExtendsProvider extends ScsGrupoguardiacolegiadoSqlProvider{
	
	public String getLastId(String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("MAX(IDGRUPOGUARDIACOLEGIADO) AS IDGRUPOGUARDIACOLEGIADO");
		sql.FROM("SCS_GRUPOGUARDIACOLEGIADO");
		sql.WHERE("idinstitucion = '"+ idInstitucion +"'");
		
		return sql.toString();
	}
}
