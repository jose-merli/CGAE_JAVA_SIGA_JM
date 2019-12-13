package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.db.mappers.ScsInscripcionturnoSqlProvider;
import org.itcgae.siga.db.mappers.ScsTipoactuacionSqlProvider;
import org.itcgae.siga.db.mappers.ScsTurnoSqlProvider;

public class ScsInscripcionesTurnoSqlExtendsProvider extends ScsInscripcionturnoSqlProvider {

	public String comboTurnos(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("consultaActiva.* FROM\r\n" + 
				"(\r\n" + 
				"SELECT\r\n" + 
				"    idturno,\r\n" + 
				"    abreviatura as nom\r\n" + 
				"FROM\r\n" + 
				"    scs_turno\r\n" + 
				"WHERE\r\n" + 
				"    idinstitucion = '"+idInstitucion+"'" + 
				"    AND   fechabaja IS NULL\r\n" + 
				"    ORDER BY nom\r\n" + 
				") consultaActiva     \r\n" + 
				"UNION all\r\n" + 
				"select consultaBaja.* from (\r\n" + 
				"SELECT\r\n" + 
				"    idturno,\r\n" + 
				"    DECODE(fechabaja,NULL,NULL, '(Baja) ' || abreviatura || ' ') nom");
		sql.FROM("SCS_TURNO");
		sql.WHERE("idinstitucion = '"+idInstitucion+"'" + 
				"    AND   fechabaja IS NOT NULL\r\n" + 
				"ORDER BY nom) consultaBaja)");
		
		return sql.toString();
	}

}
