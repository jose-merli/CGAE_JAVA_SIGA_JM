package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipoingresoSqlProvider;

public class ScsTipoIngresoSqlExtendsProvider extends ScsTipoingresoSqlProvider{

	public String getTiposIngresos(String idLenguaje) {

		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("TIPOINGRESO.IDTIPOINGRESO AS IDGRUPO");
		sql.SELECT("f_siga_getrecurso(TIPOINGRESO.descripcion,"+idLenguaje+") DESCRIPCION");
		
		sql.FROM("SCS_TIPOINGRESO TIPOINGRESO");
		
		//sql.WHERE("tiporesolucion.fechabaja is null");
		sql.WHERE("TIPOINGRESO.fecha_baja is null");

		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") AS consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}
}
