package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.db.mappers.ScsCostefijoSqlProvider;

public class ScsCostefijoSqlExtendsProvider extends ScsCostefijoSqlProvider {

	public String getComboCosteFijos(String idLenguaje, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("coste.idcostefijo");
		sql.SELECT("costedes.descripcion");
		sql.FROM("SCS_COSTEFIJO coste");
		sql.INNER_JOIN(
				"gen_recursos_catalogos costedes on costedes.IDRECURSO = coste.descripcion and costedes.idlenguaje = '"
						+ idLenguaje + "'");
		sql.WHERE("coste.fecha_baja is null");
		sql.WHERE("coste.idinstitucion = '" + idInstitucion + "'");
		sql.ORDER_BY("costedes.descripcion");

		return sql.toString();
	}

	public String searchCosteFijos(boolean historico, String idLenguaje, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("coste.idcostefijo");
		sql.SELECT("coste.idinstitucion");
		sql.SELECT("costedes.descripcion");
		sql.SELECT("coste.idinstitucion");
		sql.SELECT("tipoact.fechabaja");
		sql.SELECT("tipoact.importe");
		sql.SELECT("tipoact.idtipoasistencia");
		sql.SELECT("tipoact.idtipoactuacion");
		sql.SELECT("asisdes.descripcion as tipoasistencia");
		sql.SELECT("actuades.descripcion as tipoactuacion");

		sql.FROM("SCS_COSTEFIJO coste");
		sql.INNER_JOIN(
				"gen_recursos_catalogos costedes on costedes.IDRECURSO = coste.descripcion and costedes.idlenguaje = '"
						+ idLenguaje + "'");
		sql.INNER_JOIN(
				"SCS_TIPOACTUACIONCOSTEFIJO tipoact on tipoact.idcostefijo = coste.idcostefijo and tipoact.idinstitucion = coste.idinstitucion");
		sql.INNER_JOIN(
				"scs_tipoasistencia tipoasistencia on tipoasistencia.idtipoasistencia = tipoact.idtipoasistencia");
		sql.INNER_JOIN(
				"gen_recursos_catalogos asisdes on asisdes.IDRECURSO = tipoasistencia.descripcion and asisdes.idlenguaje = '"
						+ idLenguaje + "'");
		sql.INNER_JOIN(
				"scs_tipoactuacion tipoactuacion on tipoactuacion.idtipoactuacion = tipoact.idtipoactuacion and tipoact.idinstitucion = tipoactuacion.idinstitucion and tipoact.idtipoasistencia = tipoactuacion.idtipoasistencia");
		sql.INNER_JOIN(
				"gen_recursos_catalogos actuades on actuades.IDRECURSO = tipoactuacion.descripcion and actuades.idlenguaje = '"
						+ idLenguaje + "'");

		if (!historico) {
			sql.WHERE("tipoact.fechabaja is null");
		}

		sql.WHERE("coste.idinstitucion = '" + idInstitucion + "'");
		sql.ORDER_BY("costedes.descripcion, asisdes.descripcion, actuades.DESCRIPCION");

		return sql.toString();
	}

}
