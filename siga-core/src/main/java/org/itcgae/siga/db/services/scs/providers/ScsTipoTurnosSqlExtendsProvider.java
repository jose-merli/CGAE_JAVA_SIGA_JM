package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTurnoSqlProvider;

public class ScsTipoTurnosSqlExtendsProvider extends ScsTurnoSqlProvider {

	public String comboTipoTurnos(String idLenguaje) {

		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		sql.SELECT("SCS_TIPOTURNO.IDTIPOTURNO, f_siga_getrecurso(SCS_TIPOTURNO.DESCRIPCION,"+idLenguaje+") DESCRIPCION");
		sql.FROM("SCS_TIPOTURNO");
		sql.WHERE("SCS_TIPOTURNO.FECHA_BAJA IS NULL");
		
		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") AS consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}


}
