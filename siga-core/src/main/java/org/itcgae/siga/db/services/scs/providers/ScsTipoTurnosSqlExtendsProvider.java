package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipoactuacionSqlProvider;
import org.itcgae.siga.db.mappers.ScsTurnoSqlProvider;

public class ScsTipoTurnosSqlExtendsProvider extends ScsTurnoSqlProvider {

	public String comboTipoTurnos(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("IDTURNO, NOMBRE");
		sql.FROM("SCS_TURNO");
		sql.WHERE("IDINSTITUCION = '"+idInstitucion +"'");
		sql.WHERE("FECHABAJA IS NULL");
		return sql.toString();
	}


}
