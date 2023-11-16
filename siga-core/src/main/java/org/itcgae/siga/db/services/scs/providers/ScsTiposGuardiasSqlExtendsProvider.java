package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTiposguardiasSqlProvider;

public class ScsTiposGuardiasSqlExtendsProvider extends ScsTiposguardiasSqlProvider {

	public String comboTiposGuardia(String idLenguaje) {

		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("SCS_TIPOSGUARDIAS.IDTIPOGUARDIA");
		sql.SELECT("f_siga_getrecurso(SCS_TIPOSGUARDIAS.descripcion,"+idLenguaje+") DESCRIPCION");
		sql.FROM("SCS_TIPOSGUARDIAS");
		sql.WHERE("SCS_TIPOSGUARDIAS.FECHA_BAJA IS NULL");
		
		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}


}
