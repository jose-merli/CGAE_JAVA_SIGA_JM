package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsGrupoguardiaSqlProvider;

public class ScsGrupoguardiaSqlExtendsProvider extends ScsGrupoguardiaSqlProvider{
	
	public String getLastId(String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("MAX(IDGRUPOGUARDIA) AS IDGRUPOGUARDIA");
		sql.FROM("SCS_GRUPOGUARDIA");
		sql.WHERE("idinstitucion = '"+ idInstitucion +"'");
		
		return sql.toString();
	}
}
