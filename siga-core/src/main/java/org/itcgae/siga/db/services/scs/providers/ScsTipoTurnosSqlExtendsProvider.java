package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsTurnoSqlProvider;

public class ScsTipoTurnosSqlExtendsProvider extends ScsTurnoSqlProvider {

	public String comboTipoTurnos(String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT("SCS_TIPOTURNO.IDTIPOTURNO, GEN_RECURSOS_CATALOGOS.DESCRIPCION");
		sql.FROM("SCS_TIPOTURNO");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS ON SCS_TIPOTURNO.DESCRIPCION = GEN_RECURSOS_CATALOGOS.IDRECURSO");
		sql.WHERE("SCS_TIPOTURNO.FECHA_BAJA IS NULL AND GEN_RECURSOS_CATALOGOS.IDLENGUAJE ='"+idLenguaje+"'");
		sql.ORDER_BY("GEN_RECURSOS_CATALOGOS.DESCRIPCION");
		return sql.toString();
	}


}
