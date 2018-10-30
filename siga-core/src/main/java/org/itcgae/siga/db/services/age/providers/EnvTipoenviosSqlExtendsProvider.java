package org.itcgae.siga.db.services.age.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.EnvTipoenviosSqlProvider;

public class EnvTipoenviosSqlExtendsProvider extends EnvTipoenviosSqlProvider {

	public String getTypeSend(String idPlantillaEnvio, String idTipoEnvio, String idInstitucion, String idLenguaje) {

		SQL sql = new SQL();

		sql.SELECT_DISTINCT("tipo.IDTIPOENVIOS");
		sql.SELECT("rec.DESCRIPCION");
		sql.FROM("ENV_TIPOENVIOS tipo");
		sql.INNER_JOIN("ENV_PLANTILLASENVIOS plantilla on (tipo.idtipoenvios = plantilla.idtipoenvios)");
		sql.INNER_JOIN("GEN_RECURSOS_CATALOGOS rec ON (rec.IDRECURSO = tipo.NOMBRE AND rec.IDLENGUAJE = '"
				+ idLenguaje + "')");
		sql.WHERE("plantilla.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("plantilla.idplantillaenvios = '" + idPlantillaEnvio + "'");
		sql.WHERE("plantilla.idtipoenvios = '" + idTipoEnvio + "'");
		sql.ORDER_BY("DESCRIPCION");

		return sql.toString();
	}
	
}
