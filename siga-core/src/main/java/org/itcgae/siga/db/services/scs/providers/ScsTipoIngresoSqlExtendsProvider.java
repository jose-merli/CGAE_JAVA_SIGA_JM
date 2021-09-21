package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipoingresoSqlProvider;

public class ScsTipoIngresoSqlExtendsProvider extends ScsTipoingresoSqlProvider{

	public String getTiposIngresos(String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("TIPOINGRESO.IDTIPOINGRESO AS IDGRUPO");
		sql.SELECT("catalogoTIPOINGRESO.descripcion");

		sql.FROM("SCS_TIPOINGRESO TIPOINGRESO");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS catalogoTIPOINGRESO on catalogoTIPOINGRESO.idrecurso = TIPOINGRESO.DESCRIPCION and catalogoTIPOINGRESO.idlenguaje = "+idLenguaje);

		//sql.WHERE("tiporesolucion.fechabaja is null");
		sql.WHERE("TIPOINGRESO.fecha_baja is null");

		sql.ORDER_BY("catalogoTIPOINGRESO.descripcion");

		return sql.toString();
	}
}
