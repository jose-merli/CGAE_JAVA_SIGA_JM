package org.itcgae.siga.db.services.cen.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.CenColacambioletradoSqlProvider;

public class CenColacambioletradoSqlExtendsProvider extends CenColacambioletradoSqlProvider{


	public String selectNuevoId(Integer idInstitucion, Long idPersona){
		SQL sql = new SQL();
		
		sql.SELECT("NVL(MAX(IDCAMBIO),0) +1 AS ID");
		sql.FROM("cen_colacambioletrado");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("idPERSONA = "+ idPersona);
		
		return sql.toString();
	}
}


