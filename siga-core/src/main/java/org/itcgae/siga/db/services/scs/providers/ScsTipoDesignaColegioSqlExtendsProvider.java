package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipodesignacolegioSqlProvider;

public class ScsTipoDesignaColegioSqlExtendsProvider extends ScsTipodesignacolegioSqlProvider {

	public String comboTipoDesignacion(Short idLenguaje, String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("tipodes.IDTIPODESIGNACOLEGIO");
		sql.SELECT("cat.descripcion");
		sql.FROM("SCS_TIPODESIGNACOLEGIO tipodes");
		sql.INNER_JOIN(
				"gen_recursos_catalogos cat on cat.IDRECURSO = tipodes.descripcion and cat.idlenguaje = '"
						+ idLenguaje + "'");
		sql.WHERE("tipodes.fecha_baja is null");
		sql.WHERE("tipodes.idinstitucion = " + idInstitucion);
		sql.ORDER_BY("cat.descripcion");

		return sql.toString();
	}
	
}
