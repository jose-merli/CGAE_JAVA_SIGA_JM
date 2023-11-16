package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipogrupolaboralSqlProvider;

public class ScsTipoGrupoLaboralSqlExtendsProvider extends ScsTipogrupolaboralSqlProvider {

	public String getGruposLaborales(Short idInstitucion, String idLenguaje) {

		SQL sql = new SQL();
		SQL sqlfinal = new SQL();
		
		sql.SELECT("TIPOGRUPOLAB.IDTIPOGRUPOLAB AS IDGRUPO");
		sql.SELECT("f_siga_getrecurso(TIPOGRUPOLAB.descripcion,"+idLenguaje+") DESCRIPCION");
		
		sql.FROM("SCS_TIPOGRUPOLABORAL TIPOGRUPOLAB");
		
		//sql.WHERE("tiporesolucion.fechabaja is null");
		sql.WHERE("tipoGRUPOLAB.fecha_baja is null");
		sql.WHERE("tipoGRUPOLAB.idinstitucion ="+idInstitucion);

		sqlfinal.SELECT("*");
		sqlfinal.FROM("(" + sql.toString() + ") AS consulta");
		sqlfinal.ORDER_BY("consulta.DESCRIPCION");
		return sqlfinal.toString();
	}
}
