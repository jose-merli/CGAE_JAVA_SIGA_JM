package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipoejgSqlProvider;

public class ScsTipoEJGSqlExtendsProvider extends ScsTipoejgSqlProvider {

	public String comboTipoEjg(Short idLenguaje) {

		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("tipoejg.IDTIPOEJG");
		sql.SELECT("f_siga_getrecurso(tipoejg.descripcion,"+idLenguaje+") DESCRIPCION");
		sql.FROM("SCS_TIPOEJG tipoejg");
		sql.WHERE("tipoejg.fecha_baja is null");
		
		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") AS consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}
	
}
