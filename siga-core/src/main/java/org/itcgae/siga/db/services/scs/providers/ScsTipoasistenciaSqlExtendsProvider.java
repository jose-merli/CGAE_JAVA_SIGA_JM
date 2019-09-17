package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTipoasistenciaSqlProvider;

public class ScsTipoasistenciaSqlExtendsProvider extends ScsTipoasistenciaSqlProvider {

	public String getComboAsistencia(String idLenguaje, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("tipoasistencia.idtipoasistencia");
		sql.SELECT("asisdes.descripcion");
		sql.FROM("SCS_TIPOASISTENCIA tipoasistencia");
		sql.INNER_JOIN("gen_recursos_catalogos asisdes on asisdes.IDRECURSO = tipoasistencia.descripcion and asisdes.idlenguaje = '" + idLenguaje + "'" );
		sql.ORDER_BY("asisdes.descripcion");

		return sql.toString();
	}

}
