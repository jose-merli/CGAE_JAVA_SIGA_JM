package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipoactuacionSqlProvider;

public class ScsTipoactuacionSqlExtendsProvider extends ScsTipoactuacionSqlProvider {

	public String getComboActuacion(String idTipoAsistencia, String idLenguaje, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("tipoactuacion.idtipoactuacion");
		sql.SELECT("actuades.descripcion");
		sql.FROM("SCS_TIPOACTUACION tipoactuacion");
		sql.INNER_JOIN("gen_recursos_catalogos actuades on actuades.IDRECURSO = tipoactuacion.descripcion and actuades.idlenguaje = '" + idLenguaje + "'" );
		sql.WHERE("tipoactuacion.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("tipoactuacion.idtipoasistencia = '" + idTipoAsistencia + "'");
		sql.ORDER_BY("actuades.descripcion");

		return sql.toString();
	}

}
