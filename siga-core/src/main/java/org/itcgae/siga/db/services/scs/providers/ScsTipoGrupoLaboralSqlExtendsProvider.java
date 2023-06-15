package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipogrupolaboralSqlProvider;

public class ScsTipoGrupoLaboralSqlExtendsProvider extends ScsTipogrupolaboralSqlProvider {

	public String getGruposLaborales(Short idInstitucion, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("TIPOGRUPOLAB.IDTIPOGRUPOLAB AS IDGRUPO");
		sql.SELECT("catalogogrupolab.descripcion");

		sql.FROM("SCS_TIPOGRUPOLABORAL TIPOGRUPOLAB");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS catalogoGRUPOLAB on catalogoGRUPOLAB.idrecurso = TIPOGRUPOLAB.DESCRIPCION and catalogoGRUPOLAB.idlenguaje = "+idLenguaje);

		//sql.WHERE("tiporesolucion.fechabaja is null");
		sql.WHERE("tipoGRUPOLAB.fecha_baja is null");
		sql.WHERE("tipoGRUPOLAB.idinstitucion ="+idInstitucion);

		sql.ORDER_BY("catalogoGRUPOLAB.descripcion");

		return sql.toString();
	}
}
