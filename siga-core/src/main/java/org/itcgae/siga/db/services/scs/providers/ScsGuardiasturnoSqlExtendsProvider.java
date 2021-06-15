package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsGuardiasturnoSqlProvider;

public class ScsGuardiasturnoSqlExtendsProvider extends ScsGuardiasturnoSqlProvider{
	
	public String comboGuardias(String idTurno, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("NOMBRE");
		sql.SELECT("IDGUARDIA");
		
		sql.FROM("SCS_GUARDIASTURNO");
		
		sql.WHERE("IDTURNO IN (" + idTurno + ")");
		sql.WHERE("IDINSTITUCION = '"+idInstitucion+"'");
		sql.ORDER_BY("nombre");
		
		return sql.toString();
	}

}
