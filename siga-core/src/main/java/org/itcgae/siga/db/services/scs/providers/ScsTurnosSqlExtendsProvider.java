package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.mappers.ScsTurnoSqlProvider;

public class ScsTurnosSqlExtendsProvider extends ScsTurnoSqlProvider {

	public String comboTurnos(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("IDTURNO, NOMBRE");
		sql.FROM("SCS_TURNO");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("FECHABAJA IS NULL");
		sql.ORDER_BY("NOMBRE");
		return sql.toString();
	}

	public String busquedaTurnos(TurnosItem turnosItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT(
				"turnos.idturno idturno, turnos.nombre nombre, turnos.abreviatura abreviatura, area.nombre area, materi.nombre materia, zona.nombre zona, subzon.nombre subzona, cat.descripcion as grupofacturacion,(SELECT COUNT(*)from scs_inscripcionturno ins where ins.idinstitucion = turnos.idinstitucion and ins.idturno = turnos.idturno and (ins.fechabaja is null or trunc(ins.fechabaja) > trunc(sysdate) ) and (ins.fechavalidacion is not null and trunc(ins.fechavalidacion) <= trunc(sysdate) ) ) as nletrados, turnos.fechabaja as fechabaja,  LISTAGG(parjud.nombre, ';') WITHIN GROUP (ORDER BY parjud.idpartido) AS nombrePartidosJudiciales from scs_turno turnos ");

		sql.INNER_JOIN(
				"scs_materia materi ON materi.idinstitucion = turnos.idinstitucion and materi.idmateria = turnos.idmateria and materi.idarea = turnos.idarea");
		sql.INNER_JOIN("scs_area area ON area.idinstitucion = materi.idinstitucion and area.idarea = materi.idarea");
		sql.INNER_JOIN("scs_ordenacioncolas on scs_ordenacioncolas.idordenacioncolas = turnos.idordenacioncolas");
		sql.LEFT_OUTER_JOIN(
				"scs_subzona subzon ON subzon.idinstitucion = turnos.idinstitucion and subzon.idzona = turnos.idzona and subzon.idsubzona = turnos.idsubzona");
		sql.INNER_JOIN("scs_zona zona ON  zona.idinstitucion = turnos.idinstitucion and  zona.idzona = turnos.idzona");
		sql.INNER_JOIN(
				"scs_grupofacturacion grupof ON grupof.idinstitucion = turnos.idinstitucion and grupof.idgrupofacturacion = turnos.idgrupofacturacion");
		sql.LEFT_OUTER_JOIN(
				"scs_subzonapartido subpar ON subpar.idsubzona = turnos.idsubzona and subpar.idinstitucion = turnos.idinstitucion and turnos.idzona = subpar.idzona");
		sql.LEFT_OUTER_JOIN("cen_partidojudicial parjud ON  parjud.idpartido = subpar.idpartido");
		sql.LEFT_OUTER_JOIN(
				"scs_partidapresupuestaria partid ON partid.IDPARTIDAPRESUPUESTARIA = turnos.IDPARTIDAPRESUPUESTARIA and partid.idinstitucion = turnos.idinstitucion");
		sql.INNER_JOIN("gen_recursos_catalogos cat ON  cat.IDRECURSO = grupof.nombre and cat.IDLENGUAJE= 1");
		sql.WHERE("turnos.idinstitucion = '" + idInstitucion + "'");
		if (turnosItem.getAbreviatura() != null && turnosItem.getAbreviatura().toString() != "") {
			sql.WHERE("UPPER(turnos.abreviatura) like UPPER('%" + turnosItem.getAbreviatura() + "%')");
		}
		if (turnosItem.getNombre() != null && turnosItem.getNombre().toString() != "") {
			sql.WHERE("UPPER(turnos.nombre) like UPPER('%" + turnosItem.getNombre() + "%')");
		}
		if (turnosItem.getJurisdiccion() != null && turnosItem.getJurisdiccion() != "") {
			sql.WHERE("turnos.idjurisdiccion ='" + turnosItem.getJurisdiccion() + "'");
		}
		if (turnosItem.getIdtipoturno() != null && turnosItem.getIdtipoturno() != "") {
			sql.WHERE("turnos.idtipoturno = '" + turnosItem.getIdtipoturno() + "'");
		}
		if (turnosItem.getIdarea() != null && turnosItem.getIdarea() != "") {
			sql.WHERE("area.idarea = '" + turnosItem.getIdarea() + "'");
		}
		if (turnosItem.getIdmateria() != null && turnosItem.getIdmateria() != "") {
			sql.WHERE("materi.idmateria = '" + turnosItem.getIdmateria() + "'");
		}
		if (turnosItem.getIdzona() != null && turnosItem.getIdzona() != "") {
			sql.WHERE("zona.idzona = '" + turnosItem.getIdzona() + "'");
		}
		if (turnosItem.getIdzubzona() != null && turnosItem.getIdzubzona() != "") {
			sql.WHERE("subzon.idsubzona = '" + turnosItem.getIdzubzona() + "'");
		}
		if (turnosItem.getIdpartidapresupuestaria() != null && turnosItem.getIdpartidapresupuestaria() != "") {
			sql.WHERE("partid.IDPARTIDAPRESUPUESTARIA = '" + turnosItem.getIdpartidapresupuestaria() + "'");
		}
		if (turnosItem.getGrupofacturacion() != null && turnosItem.getGrupofacturacion() != "") {
			sql.WHERE("grupof.idgrupofacturacion  = '" + turnosItem.getGrupofacturacion() + "'");
		}
		if (!turnosItem.isHistorico()) {
			sql.WHERE("turnos.fechabaja is null");
		}
		sql.GROUP_BY("turnos.idturno ,\r\n" + "    turnos.nombre ,\r\n" + "    turnos.abreviatura ,\r\n"
				+ "    area.nombre ,\r\n" + "    materi.nombre ,\r\n" + "    zona.nombre ,\r\n"
				+ "    subzon.nombre ,  \r\n" + "    grupof.idgrupofacturacion ,\r\n"
				+ "    cat.DESCRIPCION , turnos.fechabaja,\r\n" + "     turnos.idinstitucion");
		sql.ORDER_BY("nombre");

		return sql.toString();

	}

	public String busquedaFichaTurnos(TurnosItem turnosItem, Short idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT(
				"turnos.idturno idturno, turnos.guardias idguardias,turnos.nombre nombre, turnos.idordenacioncolas idordenacioncolas,turnos.idjurisdiccion,turnos.abreviatura abreviatura,turnos.validarjustificaciones validarjustificaciones,turnos.idtipoturno idtipoturno,turnos.validarinscripciones validarinscripciones,turnos.designadirecta designadirecta,turnos.requisitos requisitos,turnos.idarea idarea,turnos.idmateria idmateria,turnos.idzona idzona,turnos.idpartidapresupuestaria idpartidapresupuestaria,turnos.idsubzona idsubzona,turnos.idpersona_ultimo idpersona_ultimo, turnos.descripcion descripcion,turnos.activarretriccionacredit activarretriccionacredit, turnos.letradoasistencias letradoasistencias,turnos.letradoactuaciones letradoactuaciones,turnos.codigoext codigoext, turnos.fechasolicitud_ultimo fechasolicitud_ultimo,turnos.visiblemovil visiblemovil,area.nombre area, materi.nombre materia, zona.nombre zona, subzon.nombre subzona,grupof.idgrupofacturacion idgrupofacturacion, cat.descripcion as grupofacturacion,(SELECT COUNT(*)from scs_inscripcionturno ins where ins.idinstitucion = turnos.idinstitucion and ins.idturno = turnos.idturno and (ins.fechabaja is null or trunc(ins.fechabaja) > trunc(sysdate) ) and (ins.fechavalidacion is not null and trunc(ins.fechavalidacion) <= trunc(sysdate) ) ) as nletrados, turnos.fechabaja as fechabaja from scs_turno turnos ");
		sql.INNER_JOIN("scs_ordenacioncolas on scs_ordenacioncolas.idordenacioncolas = turnos.idordenacioncolas");
		sql.INNER_JOIN(
				"scs_materia materi ON materi.idinstitucion = turnos.idinstitucion and materi.idmateria = turnos.idmateria and materi.idarea = turnos.idarea");
		sql.INNER_JOIN("scs_area area ON area.idinstitucion = materi.idinstitucion and area.idarea = materi.idarea");
		sql.LEFT_OUTER_JOIN(
				"scs_subzona subzon ON subzon.idinstitucion = turnos.idinstitucion and subzon.idzona = turnos.idzona and subzon.idsubzona = turnos.idsubzona");
		sql.INNER_JOIN("scs_zona zona ON  zona.idinstitucion = turnos.idinstitucion and  zona.idzona = turnos.idzona");
		sql.INNER_JOIN(
				"scs_grupofacturacion grupof ON grupof.idinstitucion = turnos.idinstitucion and grupof.idgrupofacturacion = turnos.idgrupofacturacion");
		sql.LEFT_OUTER_JOIN("cen_partidojudicial parjud ON  parjud.idpartido = subzon.idpartido");
		sql.LEFT_OUTER_JOIN(
				"scs_partidapresupuestaria partid ON partid.IDPARTIDAPRESUPUESTARIA = turnos.IDPARTIDAPRESUPUESTARIA and partid.idinstitucion = turnos.idinstitucion");
		sql.INNER_JOIN("gen_recursos_catalogos cat ON  cat.IDRECURSO = grupof.nombre and cat.IDLENGUAJE= 1");
		sql.WHERE("turnos.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("turnos.idturno ='" + turnosItem.getIdturno() + "'");
		sql.ORDER_BY("nombre");

		return sql.toString();

	}

	public String getIdTurno(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDTURNO) AS IDTURNO");
		sql.FROM("SCS_TURNO");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

		return sql.toString();
	}

	public String getIdOrdenacion(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDORDENACIONCOLAS) AS IDORDENACIONCOLAS");
		sql.FROM("scs_ordenacioncolas");
		return sql.toString();
	}

	public String resumenTurnoColaGuardia(String idTurno, String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("SCS_TURNO.nombre");
		sql.SELECT("SCS_ZONA.IDZONA");
		sql.SELECT("SCS_TURNO.IDSUBZONA");
		sql.SELECT("SCS_MATERIA.NOMBRE AS NOMBRE_MATERIA");
		sql.SELECT("SCS_ZONA.NOMBRE AS NOMBRE_ZONA");
		sql.SELECT("(\r\n" + "		SELECT\r\n" + "			COUNT(*)\r\n" + "		FROM\r\n"
				+ "			SCS_INSCRIPCIONTURNO\r\n" + "		WHERE\r\n"
				+ "			(SCS_INSCRIPCIONTURNO.FECHABAJA IS NULL\r\n"
				+ "			OR TRUNC(SCS_INSCRIPCIONTURNO.FECHABAJA)>TRUNC(SYSDATE))\r\n"
				+ "			AND (SCS_INSCRIPCIONTURNO.FECHAVALIDACION IS NOT NULL\r\n"
				+ "			AND TRUNC(SCS_INSCRIPCIONTURNO.FECHAVALIDACION)<= TRUNC(SYSDATE))\r\n"
				+ "			AND SCS_INSCRIPCIONTURNO.IDINSTITUCION = SCS_TURNO.IDINSTITUCION\r\n"
				+ "			AND SCS_INSCRIPCIONTURNO.IDTURNO = SCS_TURNO.IDTURNO ) AS NUMEROINSCRITOSTURNO");
		sql.FROM("SCS_TURNO");
		sql.JOIN("SCS_MATERIA ON\r\n" + "		SCS_TURNO.IDINSTITUCION = SCS_MATERIA.IDINSTITUCION\r\n"
				+ "		AND SCS_TURNO.IDAREA = SCS_MATERIA.IDAREA\r\n"
				+ "		AND SCS_TURNO.IDMATERIA = SCS_MATERIA.IDMATERIA");
		sql.JOIN("SCS_ZONA ON\r\n" + "		SCS_TURNO.IDINSTITUCION = SCS_ZONA.IDINSTITUCION\r\n"
				+ "		AND SCS_TURNO.IDZONA = SCS_ZONA.IDZONA");
		sql.WHERE("IDTURNO=" + idTurno);
		sql.WHERE("SCS_TURNO.IDINSTITUCION=" + idInstitucion);

		return sql.toString();
	}

	public String comboTurnosBusqueda(Short idInstitucion, String pantalla) {

		SQL sql = new SQL();

		sql.SELECT("IDTURNO, NOMBRE");
		sql.FROM("SCS_TURNO");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("FECHABAJA IS NULL");

		if (SigaConstants.EJG.equalsIgnoreCase(pantalla)) {
			sql.WHERE("IDTIPOTURNO <> 2 AND IDTIPOTURNO IS NOT NULL");
		}

		if (SigaConstants.GUARDIA.equalsIgnoreCase(pantalla) || SigaConstants.OFICIO.equalsIgnoreCase(pantalla)) {
			sql.WHERE("IDTIPOTURNO <> 1 AND IDTIPOTURNO IS NOT NULL");
		}

		sql.ORDER_BY("NOMBRE");

		return sql.toString();
	}
	
	public String comboEstados(Short idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("VALOR");
		sql.FROM("GEN_CATALOGOS_WS");
		return sql.toString();
	}

}
