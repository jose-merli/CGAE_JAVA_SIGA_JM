package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.cen.PartidasJudicialesItem;
import org.itcgae.siga.db.entities.CenInfluencia;
import org.itcgae.siga.db.mappers.CenPartidojudicialSqlProvider;

public class CenPartidasJudicialSqlExtendsProvider extends CenPartidojudicialSqlProvider {

	public String searchProcess(String idLenguaje, Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("proc.idinstitucion");
		sql.SELECT("proc.idprocedimiento");
		sql.SELECT("proc.nombre");
		sql.SELECT("proc.codigo");
		sql.SELECT("proc.precio as importe");
		sql.SELECT("juris.descripcion as jurisdiccion");

		sql.FROM("SCS_PROCEDIMIENTOS proc");
		sql.INNER_JOIN("SCS_JURISDICCION jurisdiccion on jurisdiccion.IDJURISDICCION =  proc.IDJURISDICCION");
		sql.LEFT_OUTER_JOIN("GEN_RECURSOS_CATALOGOS juris on (juris.idrecurso = jurisdiccion.DESCRIPCION and idlenguaje = '"
				+ idLenguaje + "')");
		;

		sql.WHERE("proc.idinstitucion = '" + idInstitucion + "'");

		sql.ORDER_BY("proc.nombre");

		return sql.toString();
	}

	public String searchPartida(PartidasJudicialesItem partidasJudicialesItems) {

		SQL sql = new SQL();

		sql.SELECT("infl.idinstitucion");
		sql.SELECT("part.idpartido");
		sql.SELECT("part.nombre");

		sql.FROM("CEN_PARTIDOJUDICIAL part");

		if (partidasJudicialesItems.getNombre() == null || partidasJudicialesItems.getNombre() == "") {
//			sql.WHERE("infl.idinstitucion = '" + partidasJudicialesItems.getIdinstitucion() + "'");
			sql.INNER_JOIN("cen_influencia infl on part.idpartido = infl.idpartido");
		} else {
			sql.INNER_JOIN("cen_influencia infl on part.idpartido = infl.idpartido");
//			sql.WHERE("infl.idinstitucion = '" + partidasJudicialesItems.getIdinstitucion() + "'");
			sql.WHERE("UPPER(part.NOMBRE) like UPPER('%" + partidasJudicialesItems.getNombre() + "%')");
		}
		if(!partidasJudicialesItems.getIdinstitucion().equals("2000")) {
			sql.WHERE("infl.idinstitucion = '" + partidasJudicialesItems.getIdinstitucion() + "'");
		}
		sql.ORDER_BY("part.NOMBRE");

		return sql.toString();
	}

	public String getPartidosJudiciales() {

		SQL sql = new SQL();

		sql.SELECT("PAR.IDPARTIDO");
		sql.SELECT("PAR.NOMBRE");

		sql.FROM("CEN_PARTIDOJUDICIAL PART");
		sql.INNER_JOIN("cen_influencia infl on part.idpartido = infl.idpartido");
		// sql.WHERE("infl.idinstitucion = '" +
		// partidasJudicialesItems.getIdinstitucion() + "'");
		sql.ORDER_BY("PART.NOMBRE");

		return sql.toString();
	}

	public String searchInfluencia(CenInfluencia ejemplo2) {

		SQL sql = new SQL();

		sql.SELECT("infl.idinstitucion");
		sql.SELECT("part.idpartido");
		sql.SELECT("part.nombre");

		sql.FROM("CEN_PARTIDOJUDICIAL part");
		sql.WHERE("infl.idinstitucion = '" + ejemplo2.getIdinstitucion() + "'");
		sql.WHERE("part.idpartido ='" + ejemplo2.getIdpartido() + "'");
		sql.INNER_JOIN("cen_influencia infl on part.idpartido = infl.idpartido");
		sql.ORDER_BY("part.NOMBRE");

		return sql.toString();
	}
}
