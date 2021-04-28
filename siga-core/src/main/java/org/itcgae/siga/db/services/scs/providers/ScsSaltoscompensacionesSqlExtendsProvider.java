package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.ScsSaltoscompensaciones;

public class ScsSaltoscompensacionesSqlExtendsProvider extends ScsSaltoscompensaciones {

	public String searchSaltosCompensaciones(SaltoCompGuardiaItem salto, String idInstitucion, Integer tamMax) {

		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql.SELECT("*\r\n" + "FROM\r\n" + "	(\r\n" + "	SELECT\r\n" + "		SCS_SALTOSCOMPENSACIONES.IDINSTITUCION,\r\n"
				+ "		NULL AS GRUPO,\r\n" + "		SCS_TURNO.IDTURNO,\r\n"
				+ "		SCS_TURNO.NOMBRE AS NOMBRE_TURNO,\r\n" + "		SCS_GUARDIASTURNO.IDGUARDIA,\r\n"
				+ "		SCS_GUARDIASTURNO.NOMBRE AS NOMBREGUARDIA,\r\n"
				+ "		SCS_SALTOSCOMPENSACIONES.IDSALTOSTURNO,\r\n"
				+ "		DECODE(CEN_COLEGIADO.COMUNITARIO, '1', CEN_COLEGIADO.NCOMUNITARIO, CEN_COLEGIADO.NCOLEGIADO) AS COLEGIADO_GRUPO,\r\n"
				+ "		CEN_PERSONA.NOMBRE || ' ' || CEN_PERSONA.APELLIDOS1 || ' ' || CEN_PERSONA.APELLIDOS2 AS LETRADO,\r\n"
				+ "		SCS_SALTOSCOMPENSACIONES.SALTOOCOMPENSACION,\r\n" + "		SCS_SALTOSCOMPENSACIONES.FECHA,\r\n"
				+ "		SCS_SALTOSCOMPENSACIONES.MOTIVOS AS MOTIVO,\r\n"
				+ "		SCS_SALTOSCOMPENSACIONES.FECHACUMPLIMIENTO AS FECHAUSO,\r\n"
				+ "		SCS_SALTOSCOMPENSACIONES.FECHA_ANULACION\r\n" + "	FROM\r\n"
				+ "		SCS_SALTOSCOMPENSACIONES\r\n" + "	LEFT JOIN SCS_TURNO ON\r\n"
				+ "		SCS_TURNO.IDINSTITUCION = SCS_SALTOSCOMPENSACIONES.IDINSTITUCION\r\n"
				+ "		AND SCS_TURNO.IDTURNO = SCS_SALTOSCOMPENSACIONES.IDTURNO\r\n"
				+ "	LEFT JOIN SCS_GUARDIASTURNO ON\r\n"
				+ "		SCS_GUARDIASTURNO.IDINSTITUCION = SCS_SALTOSCOMPENSACIONES.IDINSTITUCION\r\n"
				+ "		AND SCS_GUARDIASTURNO.IDTURNO = SCS_SALTOSCOMPENSACIONES.IDTURNO\r\n"
				+ "		AND SCS_GUARDIASTURNO.IDGUARDIA = SCS_SALTOSCOMPENSACIONES.IDGUARDIA\r\n"
				+ "	JOIN CEN_PERSONA ON\r\n" + "		CEN_PERSONA.IDPERSONA = SCS_SALTOSCOMPENSACIONES.IDPERSONA\r\n"
				+ "	JOIN CEN_COLEGIADO ON\r\n"
				+ "		CEN_COLEGIADO.IDPERSONA = SCS_SALTOSCOMPENSACIONES.IDPERSONA\r\n"
				+ "		AND CEN_COLEGIADO.IDINSTITUCION = SCS_SALTOSCOMPENSACIONES.IDINSTITUCION\r\n" + "UNION ALL\r\n"
				+ "	SELECT\r\n" + "		SCS_SALTOCOMPENSACIONGRUPO.IDINSTITUCION,\r\n"
				+ "		SCS_GRUPOGUARDIA.IDGRUPOGUARDIA AS GRUPO,\r\n" + "		SCS_TURNO.IDTURNO,\r\n"
				+ "		SCS_TURNO.NOMBRE AS NOMBRE_TURNO,\r\n" + "		SCS_GUARDIASTURNO.IDGUARDIA,\r\n"
				+ "		SCS_GUARDIASTURNO.NOMBRE AS NOMBRE_GUARDIA,\r\n"
				+ "		SCS_SALTOCOMPENSACIONGRUPO.IDSALTOCOMPENSACIONGRUPO,\r\n"
				+ "		NULL AS COLEGIADO_GRUPO,\r\n" + "		NULL AS LETRADO,\r\n"
				+ "		SCS_SALTOCOMPENSACIONGRUPO.SALTOOCOMPENSACION,\r\n"
				+ "		SCS_SALTOCOMPENSACIONGRUPO.FECHA,\r\n" + "		SCS_SALTOCOMPENSACIONGRUPO.MOTIVO,\r\n"
				+ "		SCS_SALTOCOMPENSACIONGRUPO.FECHACUMPLIMIENTO AS FECHAUSO,\r\n"
				+ "		SCS_SALTOCOMPENSACIONGRUPO.FECHA_ANULACION\r\n" + "	FROM\r\n"
				+ "		SCS_SALTOCOMPENSACIONGRUPO\r\n" + "	LEFT JOIN SCS_GRUPOGUARDIA ON\r\n"
				+ "		SCS_GRUPOGUARDIA.IDGRUPOGUARDIA = SCS_SALTOCOMPENSACIONGRUPO.IDGRUPOGUARDIA\r\n"
				+ "	JOIN SCS_GUARDIASTURNO ON\r\n"
				+ "		SCS_GUARDIASTURNO.IDINSTITUCION = SCS_GRUPOGUARDIA.IDINSTITUCION\r\n"
				+ "		AND SCS_GUARDIASTURNO.IDTURNO = SCS_GRUPOGUARDIA.IDTURNO\r\n"
				+ "		AND SCS_GUARDIASTURNO.IDGUARDIA = SCS_GRUPOGUARDIA.IDGUARDIA\r\n" + "	JOIN SCS_TURNO ON\r\n"
				+ "		SCS_TURNO.IDINSTITUCION = SCS_GUARDIASTURNO.IDINSTITUCION\r\n"
				+ "		AND SCS_TURNO.IDTURNO = SCS_GUARDIASTURNO.IDTURNO ) CONSULTA");

		if (!UtilidadesString.esCadenaVacia(salto.getIdSaltosTurno())) {
			sql.WHERE("IDSALTOSTURNO =" + salto.getIdSaltosTurno());
		}

		if (!salto.isHistorico()) {
			sql.WHERE("FECHAUSO IS NULL");
			sql.WHERE("FECHA_ANULACION IS NULL");
		}

		if (!UtilidadesString.esCadenaVacia(salto.getColegiadoGrupo())) {
			sql.WHERE("( (COLEGIADO_GRUPO = " + salto.getColegiadoGrupo() + "\r\n" + "	AND GRUPO IS NULL)\r\n"
					+ "	OR (GRUPO IN (\r\n" + "	SELECT\r\n" + "		IDGRUPOGUARDIA\r\n" + "	FROM\r\n" + "		(\r\n"
					+ "		SELECT\r\n" + "			ggc.IDGRUPOGUARDIA,\r\n"
					+ "			DECODE(col.COMUNITARIO, '1', col.NCOMUNITARIO, col.NCOLEGIADO) AS COLEGIADO\r\n"
					+ "		FROM\r\n" + "			cen_persona perso,\r\n" + "			cen_colegiado col,\r\n"
					+ "			scs_grupoguardiacolegiado ggc\r\n" + "		WHERE\r\n"
					+ "			perso.idpersona = ggc.idpersona\r\n"
					+ "			AND col.idpersona = perso.idpersona\r\n"
					+ "			AND col.idinstitucion = ggc.idinstitucion\r\n"
					+ "			AND ggc.idgrupoguardia IN (\r\n" + "			SELECT\r\n"
					+ "				IDGRUPOGUARDIA\r\n" + "			FROM\r\n" + "				SCS_GRUPOGUARDIA ))\r\n"
					+ "	WHERE\r\n" + "		COLEGIADO = " + salto.getColegiadoGrupo() + ") ) ) ");
		}

		sql.WHERE("IDINSTITUCION =" + idInstitucion);

		if (!UtilidadesString.esCadenaVacia(salto.getIdTurno())) {
			sql.WHERE("IDTURNO IN (" + salto.getIdTurno() + " )");
		}

		if (!UtilidadesString.esCadenaVacia(salto.getIdGuardia())) {
			sql.WHERE("IDGUARDIA IN (" + salto.getIdGuardia() + " )");
		}

		sql.WHERE("IDGUARDIA IS NOT NULL");

		if (!UtilidadesString.esCadenaVacia(salto.getFechaDesde())) {
			sql.WHERE("TRUNC(FECHA) >= TRUNC(TO_DATE(" + salto.getFechaDesde() + ", 'YYYY/MM/DD HH24:MI:SS'))");
		}

		if (!UtilidadesString.esCadenaVacia(salto.getFechaHasta())) {
			sql.WHERE("TRUNC(FECHA) <= TRUNC(TO_DATE(" + salto.getFechaHasta() + ", 'YYYY/MM/DD HH24:MI:SS'))");
		}

		sql.ORDER_BY("FECHA DESC, IDSALTOSTURNO DESC");

		sql2.SELECT("*");
		sql2.FROM("( " + sql.toString() + " )");

		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			sql2.WHERE("ROWNUM <= " + tamMaxNumber);
		}

		return sql2.toString();

	}

	public String searchSaltosYCompensacionesOficio(SaltoCompGuardiaItem salto, String idInstitucion, Integer tamMax) {

		SQL sql = new SQL();
		SQL sql2 = new SQL();
		SQL sql3 = new SQL();

		sql3.SELECT("SCS_SALTOSCOMPENSACIONES.IDINSTITUCION");
		sql3.SELECT("SCS_TURNO.IDTURNO");
		sql3.SELECT("SCS_TURNO.NOMBRE AS NOMBRE_TURNO");
		sql3.SELECT("SCS_SALTOSCOMPENSACIONES.IDSALTOSTURNO");
		sql3.SELECT(
				"DECODE(CEN_COLEGIADO.COMUNITARIO, '1', CEN_COLEGIADO.NCOMUNITARIO, CEN_COLEGIADO.NCOLEGIADO) AS COLEGIADO_GRUPO");
		sql3.SELECT("CEN_PERSONA.APELLIDOS2 || ' ' || CEN_PERSONA.APELLIDOS1 || ', ' || CEN_PERSONA.NOMBRE AS LETRADO");
		sql3.SELECT("SCS_SALTOSCOMPENSACIONES.SALTOOCOMPENSACION");
		sql3.SELECT("SCS_SALTOSCOMPENSACIONES.FECHA");
		sql3.SELECT("SCS_SALTOSCOMPENSACIONES.MOTIVOS AS MOTIVO");
		sql3.SELECT("SCS_SALTOSCOMPENSACIONES.FECHACUMPLIMIENTO AS FECHAUSO");
		sql3.SELECT("SCS_SALTOSCOMPENSACIONES.FECHA_ANULACION");
		sql3.SELECT("CEN_COLEGIADO.IDPERSONA");
		sql3.SELECT("SCS_SALTOSCOMPENSACIONES.IDGUARDIA");

		sql3.FROM(
				"SCS_SALTOSCOMPENSACIONES LEFT JOIN SCS_TURNO ON SCS_TURNO.IDINSTITUCION = SCS_SALTOSCOMPENSACIONES.IDINSTITUCION AND SCS_TURNO.IDTURNO = SCS_SALTOSCOMPENSACIONES.IDTURNO JOIN CEN_PERSONA ON CEN_PERSONA.IDPERSONA = SCS_SALTOSCOMPENSACIONES.IDPERSONA JOIN CEN_COLEGIADO ON CEN_COLEGIADO.IDPERSONA = SCS_SALTOSCOMPENSACIONES.IDPERSONA AND CEN_COLEGIADO.IDINSTITUCION = SCS_SALTOSCOMPENSACIONES.IDINSTITUCION");

		sql2.SELECT("*");
		sql2.FROM("( " + sql3.toString() + " ) CONSULTA");

		if (!salto.isHistorico()) {
			sql2.WHERE("FECHAUSO IS NULL");
			sql2.WHERE("FECHA_ANULACION IS NULL");
		}

		if (!UtilidadesString.esCadenaVacia(salto.getColegiadoGrupo())) {
			sql2.WHERE("COLEGIADO_GRUPO = '" + salto.getColegiadoGrupo() + "'");
		}

		if (!UtilidadesString.esCadenaVacia(salto.getIdTurno())) {
			sql2.WHERE("IDTURNO IN (" + salto.getIdTurno() + " )");
		}

		if (!UtilidadesString.esCadenaVacia(salto.getFechaDesde())) {
			sql2.WHERE("TRUNC(FECHA) >= TRUNC(TO_DATE('" + salto.getFechaDesde() + "', 'DD/MM/YYYY HH24:MI:SS'))");
		}

		if (!UtilidadesString.esCadenaVacia(salto.getFechaHasta())) {
			sql2.WHERE("TRUNC(FECHA) <= TRUNC(TO_DATE('" + salto.getFechaHasta() + "', 'DD/MM/YYYY HH24:MI:SS'))");
		}

		sql2.WHERE("IDINSTITUCION =" + idInstitucion);
		sql2.WHERE("IDGUARDIA IS NULL");

		// INICIO - Query para comprobar si el letrado sigue inscrito al turno
		SQL sql4 = new SQL();
		sql4.SELECT("IDPERSONA");
		sql4.FROM("SCS_INSCRIPCIONTURNO IT");
		sql4.WHERE("IT.IDPERSONA = CONSULTA.IDPERSONA");
		sql4.WHERE("IT.IDINSTITUCION = CONSULTA.IDINSTITUCION");
		sql4.WHERE("IT.IDTURNO = CONSULTA.IDTURNO");
		sql4.WHERE("IT.FECHABAJA IS NULL");
		// FIN - Query para comprobar si el letrado sigue inscrito al turno

		sql2.WHERE("IDPERSONA IN ( " + sql4.toString() + " )");

		sql2.ORDER_BY("FECHA DESC, IDSALTOSTURNO DESC");

		sql.SELECT("*");
		sql.FROM("( " + sql2.toString() + " )");

		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			sql.WHERE("ROWNUM <= " + tamMaxNumber);
		}

		return sql.toString();

	}

	public String searchLetrados(SaltoCompGuardiaItem saltoItem, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("SCS_GRUPOGUARDIA.numerogrupo");
		sql.SELECT(
				"DECODE(CEN_COLEGIADO.COMUNITARIO, '1', CEN_COLEGIADO.NCOMUNITARIO, CEN_COLEGIADO.NCOLEGIADO) AS COLEGIADO");
		sql.SELECT("SCS_GRUPOGUARDIACOLEGIADO.IDGRUPOGUARDIA AS GRUPO");
		sql.SELECT(
				"(DECODE(CEN_COLEGIADO.COMUNITARIO, '1', CEN_COLEGIADO.NCOMUNITARIO, CEN_COLEGIADO.NCOLEGIADO) || '/' || LPAD(SCS_GRUPOGUARDIA.numerogrupo, 3, '0')) AS COLEGIADO_GRUPO");
		sql.SELECT(
				"(CEN_PERSONA.APELLIDOS1 || ' ' || CEN_PERSONA.APELLIDOS2 || ', ' || CEN_PERSONA.NOMBRE) AS LETRADO");

		sql.FROM("scs_grupoguardiacolegiado");

		sql.LEFT_OUTER_JOIN(
				"scs_grupoguardia ON scs_grupoguardiacolegiado.idgrupoguardia = scs_grupoguardia.idgrupoguardia");
		sql.LEFT_OUTER_JOIN("CEN_PERSONA ON CEN_PERSONA.idpersona = SCS_GRUPOGUARDIACOLEGIADO.idpersona");
		sql.LEFT_OUTER_JOIN(
				"CEN_COLEGIADO ON CEN_COLEGIADO.idpersona = CEN_PERSONA.idpersona AND CEN_COLEGIADO.idinstitucion = SCS_GRUPOGUARDIACOLEGIADO.idinstitucion");

		sql.WHERE("SCS_GRUPOGUARDIACOLEGIADO.idInstitucion = '" + idInstitucion.trim() + "'");
		sql.WHERE("SCS_GRUPOGUARDIACOLEGIADO.idturno = '" + saltoItem.getIdTurno().trim() + "'");
		sql.WHERE("SCS_GRUPOGUARDIACOLEGIADO.idguardia = '" + saltoItem.getIdGuardia().trim() + "'");
		sql.WHERE("SCS_GRUPOGUARDIACOLEGIADO.idgrupoguardia = '" + saltoItem.getGrupo().trim() + "'");

		sql.ORDER_BY("CEN_PERSONA.APELLIDOS1");

		return sql.toString();
	}

	public String searchLetradosGuardia(String idInstitucion, String idTurno, String idGuardia, boolean grupo) {

		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql2.SELECT(
				"( CASE WHEN SCS_INSCRIPCIONGUARDIA.Fechavalidacion IS NOT NULL AND TRUNC(SCS_INSCRIPCIONGUARDIA.Fechavalidacion) <= NVL(SYSDATE, SCS_INSCRIPCIONGUARDIA.Fechavalidacion) AND (SCS_INSCRIPCIONGUARDIA.Fechabaja IS NULL OR TRUNC(SCS_INSCRIPCIONGUARDIA.Fechabaja) > NVL(SYSDATE, '01/01/1900')) THEN '1' ELSE '0' END) Activo");
		sql2.SELECT("CEN_PERSONA.Idpersona");
		sql2.SELECT("CEN_PERSONA.Nombre");
		sql2.SELECT("CEN_PERSONA.Apellidos1");
		sql2.SELECT("DECODE(CEN_PERSONA.Apellidos2, NULL, '', ' ' || CEN_PERSONA.Apellidos2) apellidos2");
		sql2.SELECT(
				"CEN_PERSONA.Apellidos1 || DECODE(CEN_PERSONA.Apellidos2, NULL, '', ' ' || CEN_PERSONA.Apellidos2) ALFABETICOAPELLIDOS");
		sql2.SELECT(
				"DECODE(CEN_COLEGIADO.Comunitario, '1', CEN_COLEGIADO.Ncomunitario, CEN_COLEGIADO.Ncolegiado) NUMEROCOLEGIADO");

		if (grupo) {
			sql2.SELECT(
					"DECODE(SCS_GUARDIASTURNO.Porgrupos, '1', SCS_GRUPOGUARDIACOLEGIADO.IDGRUPOGUARDIACOLEGIADO, NULL) AS Idgrupoguardiacolegiado");
			sql2.SELECT(
					"DECODE(SCS_GUARDIASTURNO.Porgrupos, '1', SCS_GRUPOGUARDIACOLEGIADO.IDGRUPOGUARDIA, NULL) AS Grupo");
			sql2.SELECT("DECODE(SCS_GUARDIASTURNO.Porgrupos, '1', SCS_GRUPOGUARDIA.NUMEROGRUPO, NULL) AS numeroGrupo");
			sql2.SELECT(
					"DECODE(SCS_GUARDIASTURNO.Porgrupos, '1', SCS_GRUPOGUARDIACOLEGIADO.ORDEN, NULL) AS Ordengrupo");
		}

		sql2.FROM("SCS_INSCRIPCIONGUARDIA");

		sql2.LEFT_OUTER_JOIN(
				"SCS_GUARDIASTURNO ON SCS_INSCRIPCIONGUARDIA.Idinstitucion = SCS_GUARDIASTURNO.Idinstitucion AND SCS_INSCRIPCIONGUARDIA.Idturno = SCS_GUARDIASTURNO.Idturno AND SCS_INSCRIPCIONGUARDIA.Idguardia = SCS_GUARDIASTURNO.Idguardia");
		sql2.LEFT_OUTER_JOIN(
				"CEN_COLEGIADO ON SCS_INSCRIPCIONGUARDIA.Idinstitucion = CEN_COLEGIADO.Idinstitucion AND SCS_INSCRIPCIONGUARDIA.Idpersona = CEN_COLEGIADO.Idpersona");
		sql2.LEFT_OUTER_JOIN("CEN_PERSONA ON CEN_COLEGIADO.Idpersona = CEN_PERSONA.Idpersona");

		if (grupo) {
			sql2.LEFT_OUTER_JOIN(
					"SCS_GRUPOGUARDIACOLEGIADO ON SCS_INSCRIPCIONGUARDIA.Idinstitucion = SCS_GRUPOGUARDIACOLEGIADO.Idinstitucion AND SCS_INSCRIPCIONGUARDIA.Idturno = SCS_GRUPOGUARDIACOLEGIADO.Idturno AND SCS_INSCRIPCIONGUARDIA.Idguardia = SCS_GRUPOGUARDIACOLEGIADO.Idguardia AND SCS_INSCRIPCIONGUARDIA.Idpersona = SCS_GRUPOGUARDIACOLEGIADO.Idpersona AND SCS_INSCRIPCIONGUARDIA.Fechasuscripcion = SCS_GRUPOGUARDIACOLEGIADO.Fechasuscripcion");
			sql2.LEFT_OUTER_JOIN(
					"SCS_GRUPOGUARDIA ON SCS_GRUPOGUARDIACOLEGIADO.Idgrupoguardia = SCS_GRUPOGUARDIA.Idgrupoguardia");
		}

		sql2.WHERE("SCS_INSCRIPCIONGUARDIA.Fechavalidacion IS NOT NULL");
		sql2.WHERE("SCS_GUARDIASTURNO.Idinstitucion = '" + idInstitucion + "'");
		sql2.WHERE("SCS_GUARDIASTURNO.Idturno = '" + idTurno + "'");
		sql2.WHERE("SCS_GUARDIASTURNO.Idguardia = '" + idGuardia + "'");

		if (grupo) {
			sql2.ORDER_BY(
					"numeroGrupo, ordengrupo, SCS_INSCRIPCIONGUARDIA.FECHASUSCRIPCION, SCS_INSCRIPCIONGUARDIA.Idpersona");
		} else {
			sql2.ORDER_BY("SCS_INSCRIPCIONGUARDIA.FECHASUSCRIPCION, SCS_INSCRIPCIONGUARDIA.Idpersona");
		}

		sql.SELECT("*");
		sql.FROM("( " + sql2.toString() + " )");
		sql.WHERE("ACTIVO = 1");

		return sql.toString();
	}

	public String searchLetradosTurno(String idTurno, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("PER.IDPERSONA");
		sql.SELECT("PER.NOMBRE");
		sql.SELECT("PER.APELLIDOS1");
		sql.SELECT("DECODE(PER.APELLIDOS2, NULL, '', ' ' || PER.APELLIDOS2) APELLIDOS2");
		sql.SELECT("PER.APELLIDOS1 || DECODE(PER.APELLIDOS2, NULL, '', ' ' || PER.APELLIDOS2) ALFABETICOAPELLIDOS");
		sql.SELECT("DECODE(COL.COMUNITARIO, '1', COL.NCOMUNITARIO, COL.NCOLEGIADO) NUMEROCOLEGIADO");

		sql.FROM(
				"SCS_INSCRIPCIONTURNO IT LEFT JOIN CEN_PERSONA PER ON PER.IDPERSONA = IT.IDPERSONA JOIN CEN_COLEGIADO COL ON COL.IDPERSONA = IT.IDPERSONA AND COL.IDINSTITUCION = IT.IDINSTITUCION");

		sql.WHERE("IT.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IT.IDTURNO = '" + idTurno + "'");
		sql.WHERE("FECHABAJA IS NULL");

		return sql.toString();
	}

	public String selectNuevoIdSaltosCompensaciones(SaltoCompGuardiaItem saltoItem, String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT("MAX(saltos.IDSALTOSTURNO) + 1 AS IDSALTOSTURNO");
		sql.FROM("SCS_SALTOSCOMPENSACIONES saltos");
		sql.WHERE("saltos.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("saltos.IDTURNO = '" + saltoItem.getIdTurno() + "'");

		return sql.toString();
	}

	public String selectNuevoIdSaltosCompensacionesGrupo() {

		SQL sql = new SQL();

		sql.SELECT("sq_scs_saltocompensaciongrupo.NEXTVAL AS ID");
		sql.FROM("dual");

		return sql.toString();
	}

	public String guardarSaltosCompensaciones(SaltoCompGuardiaItem saltoItem, String idInstitucion,
			String idSaltosTurno, AdmUsuarios usuario) {

		SQL sql = new SQL();

		sql.INSERT_INTO("SCS_SALTOSCOMPENSACIONES");

		sql.VALUES("FECHA", "'" + saltoItem.getFecha() + "'");
		sql.VALUES("FECHACUMPLIMIENTO", "NULL");
		sql.VALUES("FECHAMODIFICACION", "SYSTIMESTAMP");
		if (!UtilidadesString.esCadenaVacia(saltoItem.getIdGuardia())) {
			sql.VALUES("IDGUARDIA", "'" + saltoItem.getIdGuardia() + "'");
		} else {
			sql.VALUES("IDGUARDIA", "NULL");
		}
		sql.VALUES("IDINSTITUCION", "'" + idInstitucion + "'");
		sql.VALUES("IDPERSONA", "'" + saltoItem.getIdPersona() + "'");
		sql.VALUES("IDSALTOSTURNO", "'" + idSaltosTurno + "'");
		sql.VALUES("IDTURNO", "'" + saltoItem.getIdTurno() + "'");
		sql.VALUES("MOTIVOS", "'" + saltoItem.getMotivo() + "'");
		sql.VALUES("SALTOOCOMPENSACION", "'" + saltoItem.getSaltoCompensacion() + "'");
		sql.VALUES("IDCALENDARIOGUARDIAS", "NULL");
		sql.VALUES("USUMODIFICACION", "'" + usuario.getIdusuario() + "'");
		sql.VALUES("IDCALENDARIOGUARDIASCREACION", "NULL");
		sql.VALUES("TIPOMANUAL", "NULL");

		return sql.toString();
	}

	public String guardarSaltosCompensacionesGrupo(SaltoCompGuardiaItem saltoItem, String idInstitucion,
			String idSalComGrupo, AdmUsuarios usuario) {

		SQL sql = new SQL();

		sql.INSERT_INTO("SCS_SALTOCOMPENSACIONGRUPO");

		sql.VALUES("IDSALTOCOMPENSACIONGRUPO", "'" + idSalComGrupo + "'");
		sql.VALUES("IDGRUPOGUARDIA", "'" + saltoItem.getGrupo() + "'");
		sql.VALUES("SALTOOCOMPENSACION", "'" + saltoItem.getSaltoCompensacion() + "'");
		sql.VALUES("FECHA", "'" + saltoItem.getFecha() + "'");
		sql.VALUES("FECHACUMPLIMIENTO", "NULL");
		sql.VALUES("MOTIVO", "'" + saltoItem.getMotivo() + "'");
		sql.VALUES("MOTIVOCUMPLIMIENTO", "NULL");
		sql.VALUES("IDINSTITUCION", "'" + idInstitucion + "'");
		sql.VALUES("IDTURNO", "'" + saltoItem.getIdGuardia() + "'");
		sql.VALUES("IDGUARDIA", "'" + saltoItem.getIdTurno() + "'");
		sql.VALUES("IDCALENDARIOGUARDIAS", "NULL");
		sql.VALUES("IDINSTITUCION_CUMPLI", "NULL");
		sql.VALUES("IDTURNO_CUMPLI", "NULL");
		sql.VALUES("IDGUARDIA_CUMPLI", "NULL");
		sql.VALUES("IDCALENDARIOGUARDIAS_CUMPLI", "NULL");
		sql.VALUES("FECHACREACION", "SYSTIMESTAMP");
		sql.VALUES("USUCREACION", "2077");
		sql.VALUES("FECHAMODIFICACION", "SYSTIMESTAMP");
		sql.VALUES("USUMODIFICACION", "'" + usuario.getIdusuario() + "'");
		sql.VALUES("TIPOMANUAL", "NULL");

		return sql.toString();
	}

	public String actualizarSaltosCompensaciones(SaltoCompGuardiaItem saltoItem, String idInstitucion,
			AdmUsuarios usuario) {

		SQL sql = new SQL();

		sql.UPDATE("SCS_SALTOSCOMPENSACIONES");

		sql.SET("FECHA = '" + saltoItem.getFecha() + "'");
		sql.SET("FECHACUMPLIMIENTO = NULL");
		sql.SET("FECHAMODIFICACION = SYSTIMESTAMP");
		if (!UtilidadesString.esCadenaVacia(saltoItem.getIdGuardia())) {
			sql.SET("IDGUARDIA = '" + saltoItem.getIdGuardia() + "'");
		} else {
			sql.SET("IDGUARDIA = NULL");
		}
		sql.SET("IDINSTITUCION = '" + idInstitucion + "'");
		sql.SET("IDPERSONA = '" + saltoItem.getIdPersona() + "'");
		sql.SET("IDSALTOSTURNO = '" + saltoItem.getIdSaltosTurno() + "'");
		sql.SET("IDTURNO = '" + saltoItem.getIdTurno() + "'");
		sql.SET("MOTIVOS = '" + saltoItem.getMotivo() + "'");
		sql.SET("SALTOOCOMPENSACION = '" + saltoItem.getSaltoCompensacion() + "'");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
		sql.SET("TIPOMANUAL = '0'");

		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDTURNO = '" + saltoItem.getIdTurno() + "'");
		sql.WHERE("IDSALTOSTURNO = '" + saltoItem.getIdSaltosTurno() + "'");

		return sql.toString();
	}

	public String actualizarSaltosCompensacionesGrupo(SaltoCompGuardiaItem saltoItem, String idInstitucion,
			AdmUsuarios usuario) {

		SQL sql = new SQL();

		sql.UPDATE("SCS_SALTOCOMPENSACIONGRUPO");

		sql.SET("IDSALTOCOMPENSACIONGRUPO = '" + saltoItem.getIdSaltosTurno() + "'");
		sql.SET("IDGRUPOGUARDIA = '" + saltoItem.getGrupo() + "'");
		sql.SET("SALTOOCOMPENSACION = '" + saltoItem.getSaltoCompensacion() + "'");
		sql.SET("FECHA = '" + saltoItem.getFecha() + "'");
		sql.SET("FECHACUMPLIMIENTO = NULL");
		sql.SET("MOTIVO = '" + saltoItem.getMotivo() + "'");
		sql.SET("IDINSTITUCION = '" + idInstitucion + "'");
		sql.SET("IDTURNO = '" + saltoItem.getIdTurno() + "'");
		sql.SET("IDGUARDIA = '" + saltoItem.getIdGuardia() + "'");
		sql.SET("FECHAMODIFICACION = SYSTIMESTAMP");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");
		sql.SET("TIPOMANUAL = '0'");

		sql.WHERE("IDSALTOCOMPENSACIONGRUPO = '" + saltoItem.getIdSaltosTurno() + "'");

		return sql.toString();
	}

	public String borrarSaltosCompensaciones(SaltoCompGuardiaItem saltoItem, String idInstitucion) {

		SQL sql = new SQL();

		sql.DELETE_FROM("SCS_SALTOSCOMPENSACIONES");

		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDTURNO = '" + saltoItem.getIdTurno() + "'");
		sql.WHERE("IDSALTOSTURNO = '" + saltoItem.getIdSaltosTurno() + "'");

		if (!UtilidadesString.esCadenaVacia(saltoItem.getIdGuardia())) {
			sql.WHERE("IDGUARDIA = '" + saltoItem.getIdGuardia() + "'");
		}

		return sql.toString();
	}

	public String borrarSaltosCompensacionesGrupo(SaltoCompGuardiaItem saltoItem) {

		SQL sql = new SQL();

		sql.DELETE_FROM("SCS_SALTOCOMPENSACIONGRUPO");

		sql.WHERE("IDSALTOCOMPENSACIONGRUPO = '" + saltoItem.getIdSaltosTurno() + "'");

		return sql.toString();
	}

	public String anularSaltosCompensaciones(SaltoCompGuardiaItem saltoItem, String idInstitucion,
			AdmUsuarios usuario) {

		SQL sql = new SQL();

		sql.UPDATE("SCS_SALTOSCOMPENSACIONES");

		sql.SET("FECHA_ANULACION = SYSTIMESTAMP");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");

		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDTURNO = '" + saltoItem.getIdTurno() + "'");
		sql.WHERE("IDSALTOSTURNO = '" + saltoItem.getIdSaltosTurno() + "'");

		if (!UtilidadesString.esCadenaVacia(saltoItem.getIdGuardia())) {
			sql.WHERE("IDGUARDIA = '" + saltoItem.getIdGuardia() + "'");
		}

		return sql.toString();
	}

	public String anularSaltosCompensacionesGrupo(SaltoCompGuardiaItem saltoItem, AdmUsuarios usuario) {

		SQL sql = new SQL();

		sql.UPDATE("SCS_SALTOCOMPENSACIONGRUPO");

		sql.SET("FECHA_ANULACION = SYSTIMESTAMP");
		sql.SET("USUMODIFICACION = '" + usuario.getIdusuario() + "'");

		sql.WHERE("IDSALTOCOMPENSACIONGRUPO = '" + saltoItem.getIdSaltosTurno() + "'");

		return sql.toString();
	}

}