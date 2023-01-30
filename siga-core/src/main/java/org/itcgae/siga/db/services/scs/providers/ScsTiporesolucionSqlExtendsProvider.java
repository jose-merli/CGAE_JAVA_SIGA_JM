package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTiporesolucionSqlProvider;

public class ScsTiporesolucionSqlExtendsProvider extends ScsTiporesolucionSqlProvider{

	
	public String getResoluciones(String idLenguaje, String origen) {

		SQL sql = new SQL();

		sql.SELECT("tiporesolucion.idtiporesolucion");
		sql.SELECT("catalogoResolucion.descripcion");

		sql.FROM("SCS_TIPORESOLUCION tiporesolucion");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS catalogoResolucion on catalogoResolucion.idrecurso = tiporesolucion.DESCRIPCION and catalogoResolucion.idlenguaje = "
						+ idLenguaje);

		sql.WHERE("tiporesolucion.fechabaja is null");
		sql.WHERE("tiporesolucion.fecha_baja is null");
		if(origen.equals("ficha")) {
			sql.WHERE("tiporesolucion.idtiporesolucion <> 12");
		}

		sql.ORDER_BY("catalogoResolucion.descripcion");
		return sql.toString();
	}
	
	public String getResoluciones2(String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("tiporesolucion.idtiporesolucion");
		sql.SELECT("catalogoResolucion.descripcion");

		sql.FROM("SCS_TIPORESOLUCION tiporesolucion");
		sql.INNER_JOIN(
				"GEN_RECURSOS_CATALOGOS catalogoResolucion on catalogoResolucion.idrecurso = tiporesolucion.DESCRIPCION and catalogoResolucion.idlenguaje = "
						+ idLenguaje);

		sql.WHERE("tiporesolucion.fechabaja is null");
		sql.WHERE("tiporesolucion.fecha_baja is null");

		sql.ORDER_BY("catalogoResolucion.descripcion");
		return sql.toString();
	}
}
