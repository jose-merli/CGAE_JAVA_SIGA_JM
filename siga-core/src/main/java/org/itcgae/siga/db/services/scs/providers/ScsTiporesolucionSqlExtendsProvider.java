package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTiporesolucionSqlProvider;

public class ScsTiporesolucionSqlExtendsProvider extends ScsTiporesolucionSqlProvider{

	
	public String getResoluciones(String idLenguaje, String origen) {

		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("tiporesolucion.idtiporesolucion");
		sql.SELECT("f_siga_getrecurso(tiporesolucion.descripcion,"+idLenguaje+") DESCRIPCION");
		
		sql.FROM("SCS_TIPORESOLUCION tiporesolucion");

		sql.WHERE("tiporesolucion.fechabaja is null");
		sql.WHERE("tiporesolucion.fecha_baja is null");
		if(origen.equals("ficha")) {
			sql.WHERE("tiporesolucion.idtiporesolucion <> 12");
		}

		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}
	
	public String getResoluciones2(String idLenguaje) {

		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("tiporesolucion.idtiporesolucion");
		sql.SELECT("f_siga_getrecurso(tiporesolucion.descripcion,"+idLenguaje+") DESCRIPCION");
		
		sql.FROM("SCS_TIPORESOLUCION tiporesolucion");

		sql.WHERE("tiporesolucion.fechabaja is null");
		sql.WHERE("tiporesolucion.fecha_baja is null");

		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}
}
