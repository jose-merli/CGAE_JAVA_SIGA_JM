package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipodesignacolegioSqlProvider;

public class ScsTipoDesignaColegioSqlExtendsProvider extends ScsTipodesignacolegioSqlProvider {

	public String comboTipoDesignacion(Short idLenguaje, String idInstitucion) {

		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("tipodes.IDTIPODESIGNACOLEGIO");
		sql.SELECT("f_siga_getrecurso(tipodes.descripcion,"+idLenguaje+") DESCRIPCION");
		sql.FROM("SCS_TIPODESIGNACOLEGIO tipodes");
		sql.WHERE("tipodes.fecha_baja is null");
		sql.WHERE("tipodes.idinstitucion = " + idInstitucion);

		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") AS consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}
	
}
