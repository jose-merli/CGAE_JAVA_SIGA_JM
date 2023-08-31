package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.BusquedaInscripcionItem;
import org.itcgae.siga.DTOs.scs.BusquedaInscripcionMod;
import org.itcgae.siga.DTOs.scs.InscripcionDatosEntradaDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.ScsInscripcionguardiaKey;
import org.itcgae.siga.db.mappers.ScsInscripcionguardiaSqlProvider;

public class ScsInscripcionguardiaSqlExtendsProvider extends ScsInscripcionguardiaSqlProvider {

	private Logger LOGGER = Logger.getLogger(ScsInscripcionguardiaSqlExtendsProvider.class);

	public String getColaGuardias(String idGuardia, String idTurno, String fecha, String ultimo, String ordenaciones,
			String idInstitucion, String idgrupoguardia, Boolean porGrupos) {
		SQL sql = new SQL();
		SQL sqlListadoInscripciones = new SQL();
		SQL sqlListadoInscripcionesConRownum = new SQL();
		SQL sqlUltimoCola = new SQL();

		sqlListadoInscripciones.SELECT("ins.idinstitucion");
		sqlListadoInscripciones.SELECT("ins.idturno");
		sqlListadoInscripciones.SELECT("ins.idguardia");
		sqlListadoInscripciones.SELECT("per.idpersona");
		sqlListadoInscripciones.SELECT("Ins.fechasuscripcion AS Fechasuscripcion");
		sqlListadoInscripciones.SELECT("TO_CHAR(trunc(ins.fechavalidacion), 'DD/MM/YYYY') AS fechavalidacion");
		sqlListadoInscripciones.SELECT("TO_CHAR(trunc(ins.fechabaja), 'DD/MM/YYYY') AS fechabaja");
		sqlListadoInscripciones.SELECT("Per.Nifcif");
		sqlListadoInscripciones.SELECT("Per.Nombre AS nombre");
		sqlListadoInscripciones.SELECT("COALESCE(NULLIF(Per.Apellidos1, ''), ' ') AS apellidos1");
		sqlListadoInscripciones.SELECT("COALESCE(NULLIF(Per.Apellidos2, ''), ' ') AS apellidos2");
		sqlListadoInscripciones.SELECT(
		    "COALESCE(NULLIF(Per.Apellidos1, '') || NULLIF(' ' || Per.Apellidos2, ''), ' ') AS ALFABETICOAPELLIDOS");
		sqlListadoInscripciones
				.SELECT("TO_NUMBER(DECODE(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado)) numerocolegiado");
		sqlListadoInscripciones.SELECT("Per.Fechanacimiento FECHANACIMIENTO");
		sqlListadoInscripciones.SELECT("Ins.Fechavalidacion AS ANTIGUEDADCOLA");
		sqlListadoInscripciones
				.SELECT("Gru.IDGRUPOGUARDIACOLEGIADO AS Idgrupoguardiacolegiado");
		sqlListadoInscripciones.SELECT("Gru.IDGRUPOGUARDIA AS Grupo");
		sqlListadoInscripciones.SELECT("Grg.NUMEROGRUPO AS numeroGrupo");
		sqlListadoInscripciones.SELECT("Gru.ORDEN AS Ordengrupo");
		sqlListadoInscripciones.SELECT("(SELECT COUNT(1) numero   FROM scs_saltoscompensaciones salto"
				+ " WHERE salto.idinstitucion = gua.idinstitucion  AND  salto.idturno = gua.IDTURNO  AND"
				+ "  salto.idguardia =gua.idguardia  AND  salto.saltoocompensacion = 'S'  AND"
				+ "  salto.fechacumplimiento IS NULL  AND  salto.idpersona = ins.IDPERSONA )  as saltos");

		sqlListadoInscripciones.FROM("scs_inscripcionguardia  ins");
		sqlListadoInscripciones.INNER_JOIN("cen_persona per ON per.idpersona = ins.IDPERSONA");
		sqlListadoInscripciones.INNER_JOIN(
				"cen_colegiado col ON col.idpersona = per.idpersona and col.IDINSTITUCION = ins.IDINSTITUCION and col.IDPERSONA = ins.IDPERSONA");
		sqlListadoInscripciones.INNER_JOIN(
				"scs_guardiasturno gua ON gua.idturno = ins.idturno and gua.idguardia = ins.idguardia and gua.IDINSTITUCION = ins.IDINSTITUCION");
		sqlListadoInscripciones.LEFT_OUTER_JOIN(
				"scs_grupoguardiacolegiado gru ON gru.IDINSTITUCION = ins.IDINSTITUCION and gru.IDTURNO = ins.IDTURNO and gru.IDGUARDIA = ins.IDGUARDIA and gru.IDPERSONA = per.idpersona and gru.FECHASUSCRIPCION = ins.FECHASUSCRIPCION");
		sqlListadoInscripciones.LEFT_OUTER_JOIN("scs_grupoguardia grg ON grg.IDGRUPOGUARDIA = gru.IDGRUPOGUARDIA");

		sqlListadoInscripciones.WHERE("1=1");
		sqlListadoInscripciones.WHERE("nvl(TRUNC(TO_DATE(Ins.Fechavalidacion, 'DD/MM/RRRR')), TO_DATE('31/12/2999', 'DD/MM/RRRR')) <= NVL(TO_DATE('" + fecha
				+ "', 'DD/MM/RRRR'), trunc(sysdate))");
		sqlListadoInscripciones.WHERE("nvl(TRUNC(TO_DATE(Ins.fechabaja, 'DD/MM/RRRR')), TO_DATE('31/12/2999', 'DD/MM/RRRR')) >= NVL(TO_DATE('" + fecha
				+ "', 'DD/MM/RRRR'), trunc(sysdate))");
		sqlListadoInscripciones.WHERE("Gua.Idinstitucion = " + idInstitucion);
		sqlListadoInscripciones.WHERE("Gua.Idturno = " + idTurno);
		sqlListadoInscripciones.WHERE("Gua.idguardia = " + idGuardia);

		if (ordenaciones != null && ordenaciones.length() > 0) {

			ordenaciones = ordenaciones.trim();

			if (",".equals(ordenaciones.substring(ordenaciones.length() - 1, ordenaciones.length()))) {
				ordenaciones = ordenaciones.substring(0, (ordenaciones.trim().length() - 1));
			}

			sqlListadoInscripciones.ORDER_BY(" " + ordenaciones + " ");
			sqlListadoInscripciones.ORDER_BY("Ins.FECHASUSCRIPCION");
			sqlListadoInscripciones.ORDER_BY("Ins.Idpersona");
		}

		sqlListadoInscripcionesConRownum.SELECT("ROWNUM AS orden");
		sqlListadoInscripcionesConRownum.SELECT("linscripciones.*");
		sqlListadoInscripcionesConRownum.FROM("(" + sqlListadoInscripciones + ") linscripciones");

		sqlUltimoCola.SELECT("ROWNUM AS orden");
		sqlUltimoCola.SELECT("linscripciones.*");
		sqlUltimoCola.FROM("(" + sqlListadoInscripciones + ") linscripciones");
		sqlUltimoCola.WHERE("exists (select 1 from SCS_GUARDIASTURNO ultimo where 1=1");
		sqlUltimoCola.WHERE("  ultimo.Idinstitucion = '" + idInstitucion + "'");
		sqlUltimoCola.WHERE("  ultimo.Idturno = '" + idTurno + "'");
		sqlUltimoCola.WHERE("  ultimo.idguardia ='" + idGuardia + "'");
		sqlUltimoCola.WHERE("  fechasuscripcion = ultimo.FECHASUSCRIPCION_ULTIMO ");
		sqlUltimoCola.WHERE("  idpersona = ultimo.IDPERSONA_ULTIMO");

		sql.SELECT("ROWNUM AS  orden_cola, consulta_total.*");
		sql.FROM("(WITH linscripciones_ordenada AS (   SELECT *   FROM     (" + sqlListadoInscripcionesConRownum
				+ ")),  " + "  ultimo_cola AS (   SELECT *   FROM     (" + sqlUltimoCola + "))) " + "  SELECT "
				+ "	linscripciones_ordenada.idinstitucion," + "	linscripciones_ordenada.idturno,"
				+ "	linscripciones_ordenada.idguardia," + "	linscripciones_ordenada.idpersona,"
				+ "	linscripciones_ordenada.nombre," + "	linscripciones_ordenada.apellidos1,"
				+ "	linscripciones_ordenada.apellidos2," + "	linscripciones_ordenada.numerocolegiado,"
				+ "	linscripciones_ordenada.fechavalidacion," + "	linscripciones_ordenada.alfabeticoapellidos,"
				+ " linscripciones_ordenada.numerogrupo," + " linscripciones_ordenada.ordengrupo,"
				+ "	linscripciones_ordenada.fechabaja," + "linscripciones_ordenada.fechasuscripcion,"
				+ " linscripciones_ordenada.idgrupoguardiacolegiado"
				+ "   FROM     linscripciones_ordenada     left outer join ultimo_cola on 1=1 "
				+ "  where linscripciones_ordenada.orden > nvl(ultimo_cola.orden, 0)  " + "  UNION ALL   " + "  SELECT "
				+ "	 linscripciones_ordenada.idinstitucion," + "	 linscripciones_ordenada.idturno,"
				+ "	 linscripciones_ordenada.idguardia," + "	 linscripciones_ordenada.idpersona,"
				+ "	 linscripciones_ordenada.nombre," + "	 linscripciones_ordenada.apellidos1,"
				+ "	 linscripciones_ordenada.apellidos2," + "	 linscripciones_ordenada.numerocolegiado,"
				+ "	 linscripciones_ordenada.fechavalidacion," + "	 linscripciones_ordenada.alfabeticoapellidos,"
				+ "  linscripciones_ordenada.numerogrupo," + "  linscripciones_ordenada.ordengrupo,"
				+ "	 linscripciones_ordenada.fechabaja," + "linscripciones_ordenada.fechasuscripcion,"
				+ "  linscripciones_ordenada.idgrupoguardiacolegiado"
				+ "   FROM   linscripciones_ordenada     left outer join ultimo_cola on 1=1 "
				+ "  where linscripciones_ordenada.orden <= nvl(ultimo_cola.orden, 0)) consulta_total ");
		sql.ORDER_BY("orden_cola ASC");

		return sql.toString();
		
	}

	public String getColaGuardiasByNumColegiado(String idGuardia, String idTurno, String fechaIni, String fechaFin, String idInstitucion, String numCol) {
		SQL sql = new SQL();
        SQL sqlListadoInscripciones = new SQL();
        SQL sqlListadoInscripcionesConRownum = new SQL();
        SQL sqlUltimoCola = new SQL();

        sqlListadoInscripciones.SELECT("ins.idinstitucion");
        sqlListadoInscripciones.SELECT("ins.idturno");
        sqlListadoInscripciones.SELECT("ins.idguardia");
        sqlListadoInscripciones.SELECT("per.idpersona");
        sqlListadoInscripciones.SELECT("Ins.fechasuscripcion AS Fechasuscripcion");
        sqlListadoInscripciones.SELECT("TO_CHAR(trunc(ins.fechavalidacion), 'DD/MM/YYYY') AS fechavalidacion");
        sqlListadoInscripciones.SELECT("TO_CHAR(trunc(ins.fechabaja), 'DD/MM/YYYY') AS fechabaja");
        sqlListadoInscripciones.SELECT("Per.Nifcif");
        sqlListadoInscripciones.SELECT("Per.Nombre AS nombre");
        sqlListadoInscripciones.SELECT("Per.Apellidos1 AS apellidos1");
        sqlListadoInscripciones.SELECT("DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) apellidos2");
        sqlListadoInscripciones.SELECT(
                "Per.Apellidos1 || DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) ALFABETICOAPELLIDOS");
        sqlListadoInscripciones
                .SELECT("TO_NUMBER(DECODE(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado)) numerocolegiado");
        sqlListadoInscripciones.SELECT("Per.Fechanacimiento FECHANACIMIENTO");
        sqlListadoInscripciones.SELECT("Ins.Fechavalidacion AS ANTIGUEDADCOLA");
        sqlListadoInscripciones
                .SELECT("Gru.IDGRUPOGUARDIACOLEGIADO AS Idgrupoguardiacolegiado");
        sqlListadoInscripciones.SELECT("Gru.IDGRUPOGUARDIA AS Grupo");
        sqlListadoInscripciones.SELECT("Grg.NUMEROGRUPO AS numeroGrupo");
        sqlListadoInscripciones.SELECT("Gru.ORDEN AS Ordengrupo");
        sqlListadoInscripciones.SELECT("(SELECT COUNT(1) numero   FROM scs_saltoscompensaciones salto"
                + " WHERE salto.idinstitucion = gua.idinstitucion  AND  salto.idturno = gua.IDTURNO  AND"
                + "  salto.idguardia =gua.idguardia  AND  salto.saltoocompensacion = 'S'  AND"
                + "  salto.fechacumplimiento IS NULL  AND  salto.idpersona = ins.IDPERSONA )  as saltos");

        sqlListadoInscripciones.FROM("scs_inscripcionguardia  ins");
        sqlListadoInscripciones.INNER_JOIN("cen_persona per ON per.idpersona = ins.IDPERSONA");
        sqlListadoInscripciones.INNER_JOIN(
                "cen_colegiado col ON col.idpersona = per.idpersona and col.IDINSTITUCION = ins.IDINSTITUCION and col.IDPERSONA = ins.IDPERSONA");
        sqlListadoInscripciones.INNER_JOIN(
                "scs_guardiasturno gua ON gua.idturno = ins.idturno and gua.idguardia = ins.idguardia and gua.IDINSTITUCION = ins.IDINSTITUCION");
        sqlListadoInscripciones.LEFT_OUTER_JOIN(
                "scs_grupoguardiacolegiado gru ON gru.IDINSTITUCION = ins.IDINSTITUCION and gru.IDTURNO = ins.IDTURNO and gru.IDGUARDIA = ins.IDGUARDIA and gru.IDPERSONA = per.idpersona and gru.FECHASUSCRIPCION = ins.FECHASUSCRIPCION");
        sqlListadoInscripciones.LEFT_OUTER_JOIN("scs_grupoguardia grg ON grg.IDGRUPOGUARDIA = gru.IDGRUPOGUARDIA");

        sqlListadoInscripciones.WHERE("1=1");
        sqlListadoInscripciones.WHERE("nvl(TRUNC(TO_DATE(Ins.Fechavalidacion, 'DD/MM/RRRR')), TO_DATE('31/12/2999', 'DD/MM/RRRR')) <= NVL(TO_DATE('" + fechaIni
                + "', 'DD/MM/RRRR'), trunc(sysdate))");
        sqlListadoInscripciones.WHERE("nvl(TRUNC(TO_DATE(Ins.fechabaja, 'DD/MM/RRRR')), TO_DATE('31/12/2999', 'DD/MM/RRRR')) >= NVL(TO_DATE('" + fechaFin
                + "', 'DD/MM/RRRR'), trunc(sysdate))");
        sqlListadoInscripciones.WHERE("Gua.Idinstitucion = " + idInstitucion);
        sqlListadoInscripciones.WHERE("Gua.Idturno = " + idTurno);
        sqlListadoInscripciones.WHERE("Gua.idguardia = " + idGuardia);
        if(numCol != null)
			sqlListadoInscripciones.WHERE("col.Ncolegiado = " + numCol);

        sqlListadoInscripcionesConRownum.SELECT("ROWNUM AS orden");
        sqlListadoInscripcionesConRownum.SELECT("linscripciones.*");
        sqlListadoInscripcionesConRownum.FROM("(" + sqlListadoInscripciones + ") linscripciones");

        sqlUltimoCola.SELECT("ROWNUM AS orden");
        sqlUltimoCola.SELECT("linscripciones.*");
        sqlUltimoCola.FROM("(" + sqlListadoInscripciones + ") linscripciones");
        sqlUltimoCola.WHERE("exists (select 1 from SCS_GUARDIASTURNO ultimo where 1=1");
        sqlUltimoCola.WHERE("  ultimo.Idinstitucion = '" + idInstitucion + "'");
        sqlUltimoCola.WHERE("  ultimo.Idturno = '" + idTurno + "'");
        sqlUltimoCola.WHERE("  ultimo.idguardia ='" + idGuardia + "'");
        sqlUltimoCola.WHERE("  fechasuscripcion = ultimo.FECHASUSCRIPCION_ULTIMO ");
        sqlUltimoCola.WHERE("  idpersona = ultimo.IDPERSONA_ULTIMO");

        sql.SELECT("ROWNUM AS  orden_cola, consulta_total.*");
        sql.FROM("(WITH linscripciones_ordenada AS (   SELECT *   FROM     (" + sqlListadoInscripcionesConRownum
                + ")),  " + "  ultimo_cola AS (   SELECT *   FROM     (" + sqlUltimoCola + "))) " + "  SELECT "
                + " linscripciones_ordenada.idinstitucion," + " linscripciones_ordenada.idturno,"
                + " linscripciones_ordenada.idguardia," + " linscripciones_ordenada.idpersona,"
                + " linscripciones_ordenada.nombre," + "    linscripciones_ordenada.apellidos1,"
                + " linscripciones_ordenada.apellidos2," + "    linscripciones_ordenada.numerocolegiado,"
                + " linscripciones_ordenada.fechavalidacion," + "   linscripciones_ordenada.alfabeticoapellidos,"
                + " linscripciones_ordenada.numerogrupo," + " linscripciones_ordenada.ordengrupo,"
                + " linscripciones_ordenada.fechabaja," + "linscripciones_ordenada.fechasuscripcion,"
                + " linscripciones_ordenada.idgrupoguardiacolegiado"
                + "   FROM     linscripciones_ordenada     left outer join ultimo_cola on 1=1 "
                + "  where linscripciones_ordenada.orden > nvl(ultimo_cola.orden, 0)  " + "  UNION ALL   " + "  SELECT "
                + "  linscripciones_ordenada.idinstitucion," + "     linscripciones_ordenada.idturno,"
                + "  linscripciones_ordenada.idguardia," + "     linscripciones_ordenada.idpersona,"
                + "  linscripciones_ordenada.nombre," + "    linscripciones_ordenada.apellidos1,"
                + "  linscripciones_ordenada.apellidos2," + "    linscripciones_ordenada.numerocolegiado,"
                + "  linscripciones_ordenada.fechavalidacion," + "   linscripciones_ordenada.alfabeticoapellidos,"
                + "  linscripciones_ordenada.numerogrupo," + "  linscripciones_ordenada.ordengrupo,"
                + "  linscripciones_ordenada.fechabaja," + "linscripciones_ordenada.fechasuscripcion,"
                + "  linscripciones_ordenada.idgrupoguardiacolegiado"
                + "   FROM   linscripciones_ordenada     left outer join ultimo_cola on 1=1 "
                + "  where linscripciones_ordenada.orden <= nvl(ultimo_cola.orden, 0)) consulta_total ");
        sql.ORDER_BY("orden_cola ASC");

        return sql.toString();
    }
	
	private String saltosOCompensaciones(Boolean porGrupos) {
		return !porGrupos
				? ("				(\r\n" + "				SELECT\r\n" + "					COUNT(1) numero\r\n"
						+ "				FROM\r\n" + "					scs_saltoscompensaciones salto\r\n"
						+ "				WHERE\r\n" + "					salto.idinstitucion = gua.idinstitucion\r\n"
						+ "					AND salto.idturno = gua.IDTURNO\r\n"
						+ "					AND salto.idguardia = gua.idguardia\r\n"
						+ "					AND salto.saltoocompensacion = 'S'\r\n"
						+ "					AND salto.fechacumplimiento IS NULL\r\n"
						+ "					AND salto.idpersona = ins.IDPERSONA ) AS saltos,\r\n"
						+ "				(\r\n" + "				SELECT\r\n" + "					COUNT(1) numero\r\n"
						+ "				FROM\r\n" + "					scs_saltoscompensaciones salto\r\n"
						+ "				WHERE\r\n" + "					salto.idinstitucion = gua.idinstitucion\r\n"
						+ "					AND salto.idturno = gua.IDTURNO\r\n"
						+ "					AND salto.idguardia = gua.idguardia\r\n"
						+ "					AND salto.saltoocompensacion = 'C'\r\n"
						+ "					AND salto.fechacumplimiento IS NULL\r\n"
						+ "					AND salto.idpersona = ins.IDPERSONA ) AS compensaciones\r\n")
				: ("				(\r\n" + "				SELECT\r\n" + "					COUNT(1) numero\r\n"
						+ "				FROM\r\n" + "					scs_saltocompensaciongrupo salto\r\n"
						+ "				WHERE\r\n" + "					salto.idinstitucion = gua.idinstitucion\r\n"
						+ "					AND salto.idturno = gua.IDTURNO\r\n"
						+ "					AND salto.idguardia = gua.idguardia\r\n"
						+ "					AND salto.saltoocompensacion = 'S'\r\n"
						+ "					AND salto.fechacumplimiento IS NULL\r\n"
						+ "					AND salto.idgrupoguardia =  Gru.IDGRUPOGUARDIA ) AS saltos,\r\n"
						+ "				(\r\n" + "				SELECT\r\n" + "					COUNT(1) numero\r\n"
						+ "				FROM\r\n" + "					scs_saltocompensaciongrupo salto\r\n"
						+ "				WHERE\r\n" + "					salto.idinstitucion = gua.idinstitucion\r\n"
						+ "					AND salto.idturno = gua.IDTURNO\r\n"
						+ "					AND salto.idguardia = gua.idguardia\r\n"
						+ "					AND salto.saltoocompensacion = 'C'\r\n"
						+ "					AND salto.fechacumplimiento IS NULL\r\n"
						+ "					AND salto.idgrupoguardia =  Gru.IDGRUPOGUARDIA ) AS compensaciones\r\n");
	}

	public String searchGrupoGuardia(Short idInstitucion, String idGuardia, String idPersona) {

		SQL sql = new SQL();

		sql.SELECT("SCS_GRUPOGUARDIA.NUMEROGRUPO");
		sql.SELECT("SCS_GRUPOGUARDIA.IDGUARDIA");
		sql.SELECT("gcolegiado.ORDEN");
		sql.SELECT("gcolegiado.IDPERSONA");

		sql.FROM("SCS_GRUPOGUARDIA");
		sql.INNER_JOIN(
				"SCS_GRUPOGUARDIACOLEGIADO gcolegiado on SCS_GRUPOGUARDIA.IDINSTITUCION =  gcolegiado.IDINSTITUCION"
						+ " AND SCS_GRUPOGUARDIA.IDGRUPOGUARDIA =  gcolegiado.IDGRUPOGUARDIACOLEGIADO");

		sql.WHERE("SCS_GRUPOGUARDIA.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("SCS_GRUPOGUARDIA.IDGUARDIA IN (" + idGuardia + ")");
		if (idPersona != null) {
			sql.WHERE("gcolegiado.IDPERSONA = " + idPersona);
		}
		sql.ORDER_BY("SCS_GRUPOGUARDIA.NUMEROGRUPO");

		return sql.toString();
	}

	public String listadoInscripciones(InscripcionDatosEntradaDTO inscripciones, String idInstitucion) {

		SQL sql = new SQL();

		// SQL subQuerysql = new SQL();
		// subQuerysql.SELECT("MAX(it2.fechasuscripcion)");
		// subQuerysql.FROM("scs_inscripcionguardia it2");
		// subQuerysql.WHERE("it2.idinstitucion = ins.idinstitucion");
		// subQuerysql.WHERE("it2.idturno = ins.idturno");
		// subQuerysql.WHERE("it2.idguardia = ins.idguardia");
		// subQuerysql.WHERE("it2.idpersona = ins.idpersona");

		// SQL subQuerysql2 = new SQL();
		// subQuerysql2.SELECT("guar.nombre");
		// subQuerysql2.FROM("scs_guardiasturno guar2");
		// subQuerysql2.WHERE("guar2.idinstitucion = ins.idinstitucion");
		// subQuerysql2.WHERE("guar2.idturno = ins.idturno");
		// subQuerysql2.WHERE("guar2.idguardia = guar.idguardia");

		// SQL subQuerysql3 = new SQL();
		// subQuerysql3.SELECT("guar.descripcion");
		// subQuerysql3.FROM("scs_guardiasturno guar3");
		// subQuerysql3.WHERE("guar3.idinstitucion = ins.idinstitucion");
		// subQuerysql3.WHERE("guar3.idturno = ins.idturno");
		// subQuerysql3.WHERE("guar3.idguardia = guar.idguardia");

		// SQL subQuerysql4 = new SQL();
		// subQuerysql4.SELECT("tur.validarjustificaciones");
		// subQuerysql4.FROM("scs_turno tur2");
		// subQuerysql4.WHERE("tur2.idinstitucion = ins.idinstitucion");
		// subQuerysql4.WHERE("tur2.idturno = ins.idturno");

		// OJO CON EL ORDEN, ES IMPORTANTE YA QUE ES COMO UN IF-ELSE
		sql.SELECT("(CASE" + 
				"			 WHEN ins.fechasolicitudbaja IS NULL AND ins.fechavalidacion IS NULL AND ins.fechadenegacion IS NOT NULL THEN '4' /*Alta - denegada*/\r\n" +
				"			 WHEN ins.fechasolicitudbaja IS NULL AND ins.fechavalidacion IS NULL AND ins.fechadenegacion IS NULL THEN '6' /*Alta - pendiente*/\r\n" +
				"			 WHEN ins.fechasolicitudbaja IS NULL AND ins.fechavalidacion IS NOT NULL THEN '2' /*Alta - confrimada*/\r\n" +
				"			 WHEN ins.fechasolicitudbaja IS NOT NULL AND ins.fechabaja IS NULL AND ins.fechadenegacion IS NOT NULL THEN '5' /*Baja - denegada*/\r\n" +
				"			 WHEN ins.fechasolicitudbaja IS NOT NULL AND ins.fechabaja IS NULL AND ins.fechadenegacion IS NULL THEN '7' /*Baja - pendiente*/\r\n" +		
				"			 WHEN ins.fechasolicitudbaja IS NOT NULL AND ins.fechabaja IS NOT NULL THEN '3' /*Baja - confirmada*/\r\n" +
				"            WHEN ins.fechasolicitudbaja IS NULL THEN '0' /*Alta*/\r\n" + 
				"			 WHEN ins.fechasolicitudbaja IS NOT NULL THEN '1' /*Baja*/\r\n" + 
				"            ELSE ''" + "        END" + "    ) estado");

		sql.SELECT("tur.nombre nombre");
		sql.SELECT("tur.abreviatura abreviatura");
		sql.SELECT("tur.validarinscripciones");
		sql.SELECT("tur.validarjustificaciones");

		sql.SELECT("guar.nombre NOMBREGUARDIA");
		sql.SELECT("guar.idguardia IDGUARDIA");
		sql.SELECT("guar.descripcion DESCRIPCIONGUARDIA");

		sql.SELECT(
				"per.apellidos1 || DECODE(per.apellidos2,NULL,'',' ' || per.apellidos2) || ', ' || per.nombre apellidosnombre");
		sql.SELECT("DECODE(col.comunitario,'1',col.ncomunitario,col.ncolegiado) ncolegiado");
		// sql.SELECT("per.nombre");
		sql.SELECT("per.apellidos1 as apellidos");
		sql.SELECT("per.apellidos2");
		sql.SELECT("ins.idinstitucion");
		sql.SELECT("ins.idpersona");
		sql.SELECT("ins.idturno");
		sql.SELECT("to_char(ins.FECHASUSCRIPCION,'DD/MM/YYYY HH24:MI:SS') AS fechasuscripcion");
		sql.SELECT("ins.observacionessuscripcion AS observacionessolicitud");
		sql.SELECT("ins.fechavalidacion");
		sql.SELECT("ins.observacionesvalidacion");
		sql.SELECT("ins.fechasolicitudbaja");
		sql.SELECT("ins.observacionesbaja");
		// sql.SELECT("ins.fechabaja");
		sql.SELECT("ins.observacionesvalbaja");
		sql.SELECT("ins.fechadenegacion");
		sql.SELECT("ins.observacionesdenegacion");
		// sql.SELECT("DECODE(col.comunitario,'1',col.ncomunitario,col.ncolegiado)
		// ncolegiado");
		// sql.SELECT("TO_CHAR(nvl(ins.fechadenegacion,ins.fechavalidacion),'dd/mm/yyyy')
		// fechavalidacion");
		sql.SELECT("TO_DATE(TO_CHAR(nvl(ins.fechadenegacion,ins.fechabaja),'dd/mm/yyyy'), 'dd/mm/yyyy') fechabaja");
		sql.SELECT(
				"DECODE(tur.guardias,0,'Obligatorias',DECODE(tur.guardias,2,'A elegir','Todas o ninguna') ) AS descripcion_obligatoriedad");

		sql.FROM("scs_inscripcionguardia ins");
		sql.INNER_JOIN(
				"SCS_GUARDIASTURNO guar on guar.IDGUARDIA = ins.IDGUARDIA and guar.IDINSTITUCION = ins.IDINSTITUCION");
		sql.INNER_JOIN("cen_colegiado col ON col.idpersona = ins.idpersona AND col.idinstitucion = ins.idinstitucion");
		sql.INNER_JOIN("cen_persona per ON per.idpersona = col.idpersona");
		sql.INNER_JOIN("scs_turno tur ON tur.idturno = ins.idturno AND tur.idinstitucion = ins.idinstitucion");

		if (idInstitucion != null) {
			sql.WHERE("ins.idinstitucion = " + idInstitucion);
		}

		if (inscripciones.getIdturno() != null) {
			sql.WHERE("ins.idturno in (" + inscripciones.getIdturno() + ")");
		}

		if (inscripciones.getIdGuardia() != null) {
			sql.WHERE("ins.idguardia in (" + inscripciones.getIdGuardia() + ")");
		}

		/*
		 * if(inscripciones.getaFechaDe() != null) { sql.WHERE("ins.estado in (1,2)");
		 * }else {
		 */

		if (inscripciones.getIdEstado() != null) {

			String condestados = "(";
			String[] estados = inscripciones.getIdEstado().split(",");
			for (int i = 0; i < estados.length; i++) {
				if (i > 0)
					condestados += " or ";
				
				switch(estados[i]) {
					//Alta
					case "0": 
						condestados+="(ins.fechasolicitudbaja IS NULL)" ; break;
					//Baja
					case "1":
						condestados+="(ins.fechasolicitudbaja IS NOT NULL)" ; break;
					//Confimada Alta
					case "2":
						condestados+="(ins.fechasolicitudbaja IS NULL AND ins.fechavalidacion IS NOT NULL)" ; break;
					//Confimada Baja
					case "3":
						condestados+="(ins.fechasolicitudbaja IS NOT NULL AND ins.fechabaja IS NOT NULL)" ; break;
					//Denegada Alta
					case "4":
						condestados+="(ins.fechasolicitudbaja IS NULL AND ins.fechavalidacion IS NULL AND ins.fechadenegacion IS NOT NULL)" ; break;
					//Denegada Baja
					case "5":
						condestados+="(ins.fechasolicitudbaja IS NOT NULL AND ins.fechabaja IS NULL AND ins.fechadenegacion IS NOT NULL)" ; break;
					//Pendiente Alta
					case "6":
						condestados+="(ins.fechasolicitudbaja IS NULL AND ins.fechavalidacion IS NULL AND ins.fechadenegacion IS NULL)" ; break;
					//Pendiente Baja
					case "7":
						condestados+="(ins.fechasolicitudbaja IS NOT NULL AND ins.fechabaja IS NULL AND ins.fechadenegacion IS NULL)" ; break;
					default: break;
				}
			}
			condestados += ")";
			sql.WHERE(condestados);
		}

		// }

		// sql.WHERE("ins.fechasuscripcion = (" + subQuerysql + ")");

//        sql.WHERE("guar.nombre = (" + subQuerysql2 + ")");
//        
//        sql.WHERE("guar.descripcion = (" + subQuerysql3 + ")");

		// sql.WHERE("tur.validarjustificaciones = (" + subQuerysql4 + ")");

		if (inscripciones.getaFechaDe() != null) {
			sql.WHERE("trunc(TO_DATE('" + inscripciones.getaFechaDe()
					+ "','DD/MM/RRRR')) between trunc(ins.FECHAVALIDACION) and nvl(trunc(ins.FECHABAJA), '31/12/2999')");
		}

		if (inscripciones.getFechaDesde() != null) {
			if (inscripciones.getIdEstado() != null) {
				if((inscripciones.getIdEstado().indexOf('1') != -1) || (inscripciones.getIdEstado().indexOf('3') != -1) ||
						(inscripciones.getIdEstado().indexOf('5') != -1) || (inscripciones.getIdEstado().indexOf('7') != -1)) {
					sql.WHERE("ins.fechasolicitudbaja >= TO_DATE('" + inscripciones.getFechaDesde() + " 00:00:00','DD/MM/YYYY HH24:MI:SS')");
					if (inscripciones.getFechaHasta() != null) {
						sql.WHERE("ins.fechasolicitudbaja <= TO_DATE('" + inscripciones.getFechaHasta() + " 23:59:59','DD/MM/YYYY HH24:MI:SS')");
					}
				} else {
					sql.WHERE("ins.fechasuscripcion >= TO_DATE('" + inscripciones.getFechaDesde() + " 00:00:00','DD/MM/YYYY HH24:MI:SS')");
					if (inscripciones.getFechaHasta() != null) {
						sql.WHERE("ins.fechasuscripcion <= TO_DATE('" + inscripciones.getFechaHasta() + " 23:59:59','DD/MM/YYYY HH24:MI:SS')");
					}
				}
				
			} else {
				sql.WHERE("ins.fechasuscripcion >= TO_DATE('" + inscripciones.getFechaDesde() + " 00:00:00','DD/MM/YYYY HH24:MI:SS')");
				if (inscripciones.getFechaHasta() != null) {
					sql.WHERE("ins.fechasuscripcion <= TO_DATE('" + inscripciones.getFechaHasta() + " 23:59:59','DD/MM/YYYY HH24:MI:SS')");
				}
			}			
		}

		if (inscripciones.getnColegiado() != null) {
			sql.WHERE("(col.ncolegiado = " + inscripciones.getnColegiado() + " OR col.ncomunitario = "
					+ inscripciones.getnColegiado() + ")");
		}

		sql.WHERE("ROWNUM <= 200");

		sql.ORDER_BY("tur.nombre");

		// LOGGER.info("++++ [SIGA TEST] - ScsInscripcionguardiaSqlExtendsProvider /
		// listadoInscripciones -> query = " + sql.toString());
		return sql.toString();

	}

	public String getLastInscripciones(String idGuardia, String idTurno, String idPersona, String idInstitucion) {

		SQL sql = new SQL();

		sql.SELECT(
				"IDINSTITUCION, IDPERSONA, IDTURNO, IDGUARDIA, FECHASUSCRIPCION, FECHABAJA, OBSERVACIONESSUSCRIPCION, OBSERVACIONESBAJA, FECHASOLICITUDBAJA, FECHAVALIDACION, OBSERVACIONESVALIDACION, FECHADENEGACION, OBSERVACIONESDENEGACION, OBSERVACIONESVALBAJA");

		sql.FROM("scs_inscripcionguardia");

		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDTURNO = " + idTurno);
		sql.WHERE("IDGUARDIA = " + idGuardia);
		sql.WHERE("IDPERSONA = " + idPersona);
		sql.WHERE("FECHADENEGACION IS NULL");
		sql.WHERE("FECHASOLICITUDBAJA IS NULL");
		sql.WHERE("FECHABAJA IS NULL");
		sql.WHERE("ROWNUM = 1");

		sql.ORDER_BY("FECHASUSCRIPCION DESC");

		return sql.toString();
	}

	public String searchGrupo(String grupo, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("SCS_GRUPOGUARDIA.NUMEROGRUPO");
		sql.SELECT("SCS_GRUPOGUARDIA.IDGUARDIA");
		sql.SELECT("gcolegiado.ORDEN");
		sql.SELECT("gcolegiado.IDPERSONA");

		sql.FROM("SCS_GRUPOGUARDIA");
		sql.INNER_JOIN(
				"SCS_GRUPOGUARDIACOLEGIADO gcolegiado on SCS_GRUPOGUARDIA.IDINSTITUCION =  gcolegiado.IDINSTITUCION"
						+ " AND SCS_GRUPOGUARDIA.IDGRUPOGUARDIA =  gcolegiado.IDGRUPOGUARDIACOLEGIADO");

		sql.WHERE("SCS_GRUPOGUARDIA.idinstitucion = '" + idInstitucion + "'");
		// sql.WHERE("SCS_GRUPOGUARDIA.idPersona = '" + idPersona + "'");
		sql.WHERE("SCS_GRUPOGUARDIA.NUMEROGRUPO = '" + grupo + "'");

		return sql.toString();
	}

	public String comboGuardiasInscritoLetrado(Short idInstitucion, String idPersona, String idTurno) {
		SQL sql = new SQL();
		sql.SELECT("gt.IDGUARDIA", "gt.NOMBRE");
		sql.FROM("scs_guardiasturno gt");
		sql.INNER_JOIN(
				"scs_inscripcionguardia ig on gt.idinstitucion = ig.idinstitucion and gt.idguardia = ig.idguardia and gt.idturno = ig.idturno");
		sql.WHERE("gt.idinstitucion = " + idInstitucion, "ig.fechabaja is null", "ig.fechavalidacion is not null",
				"ig.idpersona = '" + idPersona + "'", "ig.idturno = '" + idTurno + "'");
		return sql.toString();
	}

	public String validarInscripcionesCampo(String idinstitucion, String idturno, String idguardia, String idpersona) {
		// TODO Auto-generated method stub

		SQL sql = new SQL();

		sql.SELECT("validarinscripciones");
		sql.FROM("scs_turno");

		if (idinstitucion != null && !idinstitucion.isEmpty()) {
			sql.WHERE("IDINSTITUCION =" + idinstitucion);
		}

		if (idturno != null && !idturno.isEmpty()) {
			sql.WHERE("IDTURNO =" + idturno);
		}

		return sql.toString();
	}

	public String requeridaValidacionCampo(String idinstitucion, String idturno, String idguardia, String idpersona) {
		// TODO Auto-generated method stub

		SQL sql = new SQL();

		sql.SELECT("requeridavalidacion");
		sql.FROM("scs_guardiasturno");

		if (idinstitucion != null && !idinstitucion.isEmpty()) {
			sql.WHERE("IDINSTITUCION =" + idinstitucion);
		}

		if (idguardia != null && !idguardia.isEmpty()) {
			sql.WHERE("IDGUARDIA =" + idguardia);
		}

		if (idturno != null && !idturno.isEmpty()) {
			sql.WHERE("IDTURNO =" + idturno);
		}

		return sql.toString();
	}

	public String eliminarSaltoCompensacion(String idinstitucion, String idturno, String idguardia, String idpersona,
			String saltooCompensacion) {

		SQL sql = new SQL();

		sql.DELETE_FROM("SCS_SALTOSCOMPENSACIONES");

		if (idinstitucion != null && !idinstitucion.isEmpty()) {
			sql.WHERE("IDINSTITUCION =" + idinstitucion);
		}

		if (idpersona != null && !idpersona.isEmpty()) {
			sql.WHERE("IDPERSONA =" + idpersona);
		}

		if (idturno != null && !idturno.isEmpty()) {
			sql.WHERE("IDTURNO =" + idturno);
		}

		if (idguardia != null && !idguardia.isEmpty()) {
			sql.WHERE("IDGUARDIA =" + idguardia);
		}

		if (saltooCompensacion != null && !saltooCompensacion.isEmpty()) {
			sql.WHERE("SALTOOCOMPENSACION =" + saltooCompensacion);
		}

		return sql.toString();
	}

	public String UpdateInscripcionGuardia(String idinstitucion, String idturno, String idguardia,
			BusquedaInscripcionMod inscripciones, String FECHABAJA) {

		SQL sql = new SQL();

		sql.UPDATE("SCS_INSCRIPCIONGUARDIA");

		sql.SET("FECHABAJA = TO_DATE('" + FECHABAJA + "','DD,MM/RRRR')");

		if (idinstitucion != null && !idinstitucion.isEmpty()) {
			sql.WHERE("IDINSTITUCION =" + idinstitucion);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String today = formatter.format(new Date());

		if (today != null) {
			sql.SET("FECHAMODIFICACION = TO_DATE('" + today + "','DD,MM/RRRR')");
		}

		if (idturno != null && !idturno.isEmpty()) {
			sql.WHERE("IDTURNO =" + idturno);
		}

		if (idguardia != null && !idguardia.isEmpty()) {
			sql.WHERE("IDGUARDIA =" + idguardia);
		}

		if (inscripciones.getIdpersona() != null) {
			sql.WHERE("IDPERSONA =" + inscripciones.getIdpersona());
		}

		return sql.toString();
	}

	public String UpdateInscripcionTurno(String idinstitucion, String idturno, BusquedaInscripcionMod inscripciones,
			String FECHABAJA) {

		SQL sql = new SQL();

		sql.UPDATE("SCS_INSCRIPCIONTURNO");

		if (inscripciones.getFechabaja() != null) {
			sql.SET("FECHABAJA = TO_DATE('" + FECHABAJA + "','DD,MM/RRRR')");
		}

		if (idinstitucion != null && !idinstitucion.isEmpty()) {
			sql.WHERE("IDINSTITUCION =" + idinstitucion);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String today = formatter.format(new Date());

		if (today != null) {
			sql.SET("FECHAMODIFICACION = TO_DATE('" + today + "','DD,MM/RRRR')");
		}

		if (idturno != null && !idturno.isEmpty()) {
			sql.WHERE("IDTURNO =" + idturno);
		}

		if (inscripciones.getIdpersona() != null) {
			sql.WHERE("IDPERSONA =" + inscripciones.getIdpersona());
		}

		return sql.toString();
	}

	public String busquedaTrabajosGuardias(String idpersona, String idturno, String idguardia, Short idInstitucion,
			String fechaActual) {

		SQL sql = new SQL();

		sql.SELECT("TO_CHAR(gc.fechainicio,'dd/mm/yyyy')\r\n" + "    || ' - '\r\n"
				+ "    || TO_CHAR(gc.fecha_fin,'dd/mm/yyyy') fecha,\r\n" + "    gt.nombre incidencia,\r\n"
				+ "    'Guardia' descripcion");
		sql.FROM("scs_cabeceraguardias gc,\r\n" + "    scs_guardiasturno gt");
		sql.WHERE("gc.idinstitucion = gt.idinstitucion\r\n" + "    AND   gc.idturno = gt.idturno\r\n"
				+ "    AND   gc.idguardia = gt.idguardia\r\n" + "    AND   gc.idinstitucion = '" + idInstitucion + "'"
				+ "    AND   gc.idpersona = '" + idpersona + "'" + "    AND   trunc(gc.fecha_fin) > '" + fechaActual
				+ "'" + "    AND   gc.idturno = '" + idturno + "'" + "	AND gc.idguardia= " + idguardia);
		sql.ORDER_BY("gc.fechainicio DESC");

		return sql.toString();
	}

	public String busquedaTrabajosPendientes(String idpersona, String idturno, Short idInstitucion,
			String fechaActual) {

		SQL sql = new SQL();

		sql.SELECT("des.anio\r\n" + "    || '/'\r\n" + "    || des.codigo incidencia,\r\n"
				+ "    TO_CHAR(des.fechaentrada,'dd/mm/yyyy') fecha,\r\n" + "    'Designación' descripcion");
		sql.FROM("scs_designa des,\r\n" + "    scs_designasletrado deslet");
		sql.WHERE("des.idinstitucion = '" + idInstitucion + "'"
				+ "    AND   des.estado <> ' f '    AND   deslet.idpersona = '" + idpersona + "'"
				+ "    AND   deslet.fecharenuncia IS NULL\r\n" + "    AND   deslet.idturno = '" + idturno + "'"
				+ "    AND   des.idinstitucion = deslet.idinstitucion" + "    AND   des.idturno = deslet.idturno"
				+ "    AND   des.anio = deslet.anio" + "    AND   des.numero = deslet.numero"
				+ "    AND   trunc(des.fechaentrada) > '" + fechaActual + "'");

		return sql.toString();
	}

	public String buscarTrabajosSJCS(String idinstitucion, String idturno, String idguardia, String idpersona,
			String fechadesde, String fechahasta) {

		SQL sql = new SQL();

		SQL subQuerysql1 = new SQL();
		subQuerysql1.SELECT("descripcion");
		subQuerysql1.FROM("gen_recursos");
		subQuerysql1.WHERE("idrecurso = 'gratuita.gestionInscripciones.guardia.literal'");
		subQuerysql1.WHERE("idlenguaje = 1");

		SQL subQuerysql = new SQL();
		subQuerysql.SELECT(
				"to_date(gc.fechainicio, 'dd/mm/yyyy') || ' - ' || to_date(gc.fecha_fin, 'dd/mm/yyyy') fecha,gt.nombre incidencia, ("
						+ subQuerysql1 + ") descripcion");
		subQuerysql.FROM("scs_cabeceraguardias gc");
		subQuerysql.FROM("scs_guardiasturno  gt");
		subQuerysql.WHERE("gc.idinstitucion = gt.idinstitucion");
		subQuerysql.WHERE("gc.idturno = gt.idturno");
		subQuerysql.WHERE("gc.idguardia = gt.idguardia");

		if (idinstitucion != null) {
			subQuerysql.WHERE("gc.idinstitucion =" + idinstitucion);
		}

		if (idpersona != null) {
			subQuerysql.WHERE("gc.idPersona =" + idpersona);
		}

		if (idturno != null) {
			subQuerysql.WHERE("gc.idturno =" + idturno);
		}

		if (idguardia != null) {
			subQuerysql.WHERE("gc.idguardia =" + idguardia);
		}

		subQuerysql.ORDER_BY("gc.fechainicio DESC");

		SQL subQuerysql2 = new SQL();
		subQuerysql2.SELECT("des.anio || '/'  || des.codigo incidencia,to_date(des.fechaentrada, 'dd/mm/yyyy') fecha, ("
				+ subQuerysql1 + ") descripcion");
		subQuerysql2.FROM("scs_designa des");
		subQuerysql2.FROM("scs_designasletrado  deslet");

		if (idinstitucion != null) {
			subQuerysql2.WHERE("des.idinstitucion =" + idinstitucion);
		}

		subQuerysql2.WHERE(" des.ESTADO <> 'F'");

		if (idpersona != null) {
			subQuerysql2.WHERE("deslet.idPersona =" + idpersona);
		}

		subQuerysql2.WHERE("deslet.FECHARENUNCIA IS NULL");

		if (idturno != null) {
			subQuerysql2.WHERE("deslet.idturno =" + idturno);
		}

		subQuerysql2.WHERE("des.idinstitucion = deslet.idinstitucion");
		subQuerysql2.WHERE("des.idturno = deslet.idturno");
		subQuerysql2.WHERE("des.anio = deslet.anio");
		subQuerysql2.WHERE("des.numero = deslet.numero  ");

		if (fechadesde != null && fechahasta != null) {
			subQuerysql2.WHERE("TRUNC(des.FECHAENTRADA) BETWEEN" + fechahasta + "AND" + fechadesde);
		} else if (fechahasta != null) {
			if (fechahasta.equalsIgnoreCase("sysdate"))
				subQuerysql2.WHERE("des.FECHAENTRADA > sysdate ");
			else
				subQuerysql2.WHERE("TRUNC(des.FECHAENTRADA) >" + fechadesde);

		}
		;

		sql.SELECT("guardias.* FROM (" + subQuerysql + ") guardias UNION SELECT designas.* FROM (" + subQuerysql2
				+ ") designas");

		return sql.toString();
	}

	public String buscarGuardiasAsocTurnos(String idinstitucion, String idturno, String idguardia, String idpersona) {

		SQL subquery = new SQL();
		subquery.SELECT("GUAR.IDGUARDIA");
		subquery.FROM("SCS_GUARDIASTURNO GUAR");

		if (idinstitucion != null) {
			subquery.WHERE("GUAR.IDINSTITUCION = " + idinstitucion);
		}
		if (idturno != null) {
			subquery.WHERE("GUAR.IDTURNO = " + idturno);
		}

		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("SCS_INSCRIPCIONGUARDIA ins");
		sql.WHERE("ins.IDGUARDIA IN (" + subquery + ")");
		sql.WHERE("ins.IDPERSONA = " + idpersona);

		return sql.toString();
	}

	public String insertarInscripcion(Short idInstitucion, BusquedaInscripcionItem inscripcion,
			AdmUsuarios admUsuarios) {
		SQL sql = new SQL();

		sql.INSERT_INTO("scs_inscripcionguardia");

		sql.VALUES("IDPERSONA", inscripcion.getIdpersona());
		sql.VALUES("IDINSTITUCION", "'" + idInstitucion + "'");
		sql.VALUES("IDTURNO", inscripcion.getIdturno());
		sql.VALUES("IDGUARDIA", inscripcion.getIdguardia());
		sql.VALUES("FECHASUSCRIPCION", "SYSTIMESTAMP");
		sql.VALUES("FECHAMODIFICACION", "SYSTIMESTAMP");
		sql.VALUES("USUMODIFICACION", "'" + admUsuarios.getIdusuario() + "'");
		// sql.VALUES("FECHABAJA", inscripcion.getFechabaja());
		sql.VALUES("OBSERVACIONESSUSCRIPCION", inscripcion.getObservacionessolicitud());
		sql.VALUES("OBSERVACIONESBAJA", inscripcion.getObservacionesbaja());
//		sql.VALUES("FECHASOLICITUDBAJA", "NULL");
//		sql.VALUES("FECHAVALIDACION", inscripcion.getFechaValidacion());
		sql.VALUES("OBSERVACIONESVALIDACION", inscripcion.getObservacionesvalidacion());
//		sql.VALUES("FECHADENEGACION", "NULL");
		sql.VALUES("OBSERVACIONESDENEGACION", inscripcion.getObservacionesdenegacion());
		sql.VALUES("OBSERVACIONESVALBAJA", inscripcion.getObservacionesvalbaja());
		return sql.toString();
	}

	public String buscarInscripcion(Short idInstitucion, BusquedaInscripcionItem inscripcion, AdmUsuarios admUsuarios) {
		SQL sql = new SQL();
		sql.SELECT("IDPERSONA", "IDINSTITUCION", "IDTURNO", "IDGUARDIA", "FECHASUSCRIPCION", "FECHAMODIFICACION",
				"USUMODIFICACION", "FECHABAJA", "OBSERVACIONESSUSCRIPCION", "OBSERVACIONESBAJA", "FECHASOLICITUDBAJA",
				"FECHAVALIDACION", "OBSERVACIONESVALIDACION", "FECHADENEGACION", "OBSERVACIONESDENEGACION",
				"OBSERVACIONESVALBAJA");
		sql.FROM("scs_inscripcionguardia");
		sql.WHERE("IDINSTITUCION =" + idInstitucion);
		sql.WHERE("IDPERSONA =" + inscripcion.getIdpersona());
		sql.WHERE("IDTURNO =" + inscripcion.getIdturno());
		sql.WHERE("IDGUARDIA =" + inscripcion.getIdguardia());
		sql.ORDER_BY("FECHASUSCRIPCION DESC");
		return sql.toString();
	}

	public String inscripcionesDisponibles(Short idInstitucion, AdmUsuarios admUsuarios,
			BusquedaInscripcionItem inscripcion) {
		SQL sql = new SQL();
		sql.SELECT("scs_guardiasturno.idguardia AS idguardia", "scs_guardiasturno.idturno AS idturno",
				"scs_grupoguardia.numerogrupo AS numerogrupo",
				"DECODE(cen_colegiado.comunitario,'1',cen_colegiado.ncomunitario,cen_colegiado.ncolegiado) AS colegiado",
				"( DECODE(cen_colegiado.comunitario,'1',cen_colegiado.ncomunitario,cen_colegiado.ncolegiado)"
						+ "    || '/'" + "    || lpad(scs_grupoguardia.numerogrupo,3,'0') ) AS colegiado_grupo",
				"( cen_persona.apellidos1" + "    || ' '" + "    || cen_persona.apellidos2" + "    || ', '"
						+ "    || cen_persona.nombre ) AS letrado",
				"scs_turno.nombre AS nombre_turno", "scs_turno.idzona AS idzona", "scs_zona.nombre AS nombre_zona",
				"scs_turno.idsubzona AS idsubzona", "scs_subzona.nombre AS nombre_subzona",
				"scs_turno.idarea AS idarea", "scs_area.nombre AS nombre_area", "scs_turno.idmateria AS idmateria",
				"scs_materia.nombre AS nombre_materia", "scs_guardiasturno.nombre AS nombre_guardia",
				"F_SIGA_GETRECURSO(SCS_TIPOSGUARDIAS.DESCRIPCION," + admUsuarios.getIdlenguaje()
						+ ") AS descripcion_tipo_guardia",
				"scs_turno.guardias AS obligatoriedad_inscripcion",
				"DECODE(scs_turno.guardias,0,'Obligatorias',DECODE(scs_turno.guardias,2,'A elegir','Todas o ninguna') ) AS descripcion_obligatoriedad,"
						+ "scs_inscripcionguardia.fechasuscripcion");

		sql.FROM("scs_inscripcionguardia");
		// Left Join
		sql.JOIN(
				"scs_turno ON scs_turno.idinstitucion = scs_inscripcionguardia.idinstitucion AND scs_turno.idturno = scs_inscripcionguardia.idturno");
		sql.JOIN(
				"scs_guardiasturno ON scs_inscripcionguardia.idguardia = scs_guardiasturno.idguardia AND scs_inscripcionguardia.idinstitucion = scs_guardiasturno.idinstitucion AND scs_inscripcionguardia.idturno = scs_guardiasturno.idturno");
		sql.JOIN("cen_persona ON cen_persona.idpersona = scs_inscripcionguardia.idpersona");
		sql.JOIN(
				"cen_colegiado ON cen_colegiado.idpersona = scs_inscripcionguardia.idpersona AND cen_colegiado.idinstitucion = scs_inscripcionguardia.idinstitucion");
		sql.JOIN("scs_grupoguardia ON scs_guardiasturno.idguardia = scs_grupoguardia.idguardia");
		sql.JOIN("scs_zona ON scs_zona.idinstitucion = scs_turno.idinstitucion AND scs_zona.idzona = scs_turno.idzona");
		sql.JOIN(
				"scs_subzona ON scs_subzona.idinstitucion = scs_turno.idinstitucion AND scs_subzona.idzona = scs_turno.idzona AND scs_subzona.idsubzona = scs_turno.idsubzona");
		sql.JOIN("scs_area ON scs_area.idinstitucion = scs_turno.idinstitucion AND scs_area.idarea = scs_turno.idarea");
		sql.JOIN(
				"scs_materia ON scs_materia.idinstitucion = scs_turno.idinstitucion AND scs_materia.idmateria = scs_turno.idmateria AND scs_materia.idarea = scs_turno.idarea");
		sql.JOIN("scs_tiposguardias ON scs_guardiasturno.idtipoguardia = scs_tiposguardias.idtipoguardia");

		sql.WHERE("scs_guardiasturno.porgrupos = 1" + " AND scs_guardiasturno.fechabaja IS NULL"
				+ " AND scs_turno.fechabaja IS NULL" + " AND scs_inscripcionguardia.idpersona <> "
				+ inscripcion.getIdpersona() + " AND scs_inscripcionguardia.idinstitucion = " + idInstitucion);
		return sql.toString();
	}

	public String busquedaTarjetaInscripcionesGuardia(Short idInstitucion, AdmUsuarios admUsuarios,
			BusquedaInscripcionItem inscripcion) {

		SQL sql = new SQL();
		String where = "";
		String select = "";
		// Inscripciones de turnos a secas, sin asignacion a ninguna guardia
		/*
		 * if(inscripcion.getIdguardia() == null && inscripcion.getIdturno() != null) {
		 * select+= " scs_guardiasturno.idguardia   AS idguardia, " +
		 * " scs_inscripcionguardia.idinstitucion   AS idinstitucion, "+
		 * "scs_guardiasturno.nombre AS nombre_guardia, " +
		 * "          SCS_TURNO.NOMBRE AS NOMBRE_TURNO,\r\n" +
		 * "          SCS_TURNO.IDZONA,\r\n" +
		 * "          SCS_ZONA.NOMBRE AS NOMBRE_ZONA,\r\n" +
		 * "          SCS_TURNO.IDSUBZONA,\r\n" +
		 * "          SCS_SUBZONA.NOMBRE AS NOMBRE_SUBZONA,\r\n" +
		 * "          SCS_TURNO.IDAREA,\r\n" +
		 * "          SCS_AREA.NOMBRE AS NOMBRE_AREA,\r\n" +
		 * "          SCS_TURNO.IDMATERIA,\r\n" +
		 * "          SCS_MATERIA.NOMBRE AS NOMBRE_MATERIA,\r\n" +
		 * "          SCS_TURNO.IDTURNO,\r\n" +
		 * "          SCS_TURNO.GUARDIAS AS OBLIGATORIEDAD_INSCRIPCION,  --La inscripcion en el turno obliga a inscribirse en guardias: 2- A elegir; 1-Todas o ninguna; 0-Obligatorias\r\n"
		 * + "F_SIGA_GETRECURSO(SCS_TIPOSGUARDIAS.DESCRIPCION,"+
		 * admUsuarios.getIdlenguaje() +") AS descripcion_tipo_guardia, "+
		 * "DECODE(scs_turno.guardias,0,'Obligatorias',DECODE(scs_turno.guardias,2,'A elegir','Todas o ninguna') ) AS descripcion_obligatoriedad "
		 * + "FROM\r\n" + "          SCS_INSCRIPCIONGUARDIA\r\n" +
		 * "          JOIN SCS_TURNO ON SCS_TURNO.IDINSTITUCION = SCS_INSCRIPCIONGUARDIA.IDINSTITUCION AND SCS_TURNO.IDTURNO = SCS_INSCRIPCIONGUARDIA.IDTURNO\r\n"
		 * +
		 * " 		   JOIN scs_guardiasturno ON scs_inscripcionguardia.idinstitucion = scs_guardiasturno.idinstitucion AND scs_inscripcionguardia.idturno = scs_guardiasturno.idturno"
		 * +
		 * "          JOIN SCS_ZONA ON SCS_ZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_ZONA.IDZONA = SCS_TURNO.IDZONA\r\n"
		 * +
		 * "          JOIN SCS_SUBZONA ON SCS_SUBZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_SUBZONA.IDZONA = SCS_TURNO.IDZONA AND SCS_SUBZONA.IDSUBZONA = SCS_TURNO.IDSUBZONA\r\n"
		 * +
		 * "          JOIN SCS_AREA ON SCS_AREA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_AREA.IDAREA = SCS_TURNO.IDAREA\r\n"
		 * +
		 * "          JOIN SCS_MATERIA ON SCS_MATERIA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_MATERIA.IDMATERIA = SCS_TURNO.IDMATERIA AND SCS_MATERIA.IDAREA = SCS_TURNO.IDAREA \r\n"
		 * +
		 * "          JOIN SCS_TIPOSGUARDIAS ON SCS_GUARDIASTURNO.IDTIPOGUARDIA = SCS_TIPOSGUARDIAS.IDTIPOGUARDIA\r\n"
		 * ; where +="          SCS_TURNO.IDINSTITUCION ='"+idInstitucion+"'\r\n" ; }
		 * else { select+= "*\r\n" + "  FROM (SELECT          " +
		 * " scs_guardiasturno.idguardia   AS idguardia, " +
		 * " scs_inscripcionguardia.idinstitucion   AS idinstitucion, " +
		 * "scs_guardiasturno.nombre  AS nombre_guardia,\r\n" +
		 * " SCS_TURNO.NOMBRE AS NOMBRE_TURNO, " + "          SCS_TURNO.IDZONA,\r\n" +
		 * "          SCS_ZONA.NOMBRE AS NOMBRE_ZONA,\r\n" +
		 * "          SCS_TURNO.IDSUBZONA,\r\n" +
		 * "          SCS_SUBZONA.NOMBRE AS NOMBRE_SUBZONA,\r\n" +
		 * "          SCS_TURNO.IDAREA,\r\n" +
		 * "          SCS_AREA.NOMBRE AS NOMBRE_AREA,\r\n" +
		 * "          SCS_TURNO.IDMATERIA,\r\n" +
		 * "          SCS_MATERIA.NOMBRE AS NOMBRE_MATERIA,\r\n" +
		 * "          SCS_TURNO.IDTURNO,\r\n"+
		 * "          SCS_TURNO.GUARDIAS AS OBLIGATORIEDAD_INSCRIPCION,  --La inscripcion en el turno obliga a inscribirse en guardias: 2- A elegir; 1-Todas o ninguna; 0-Obligatorias\r\n"
		 * + "F_SIGA_GETRECURSO(SCS_TIPOSGUARDIAS.DESCRIPCION,"+
		 * admUsuarios.getIdlenguaje() +") AS descripcion_tipo_guardia, "+
		 * "DECODE(scs_turno.guardias,0,'Obligatorias',DECODE(scs_turno.guardias,2,'A elegir','Todas o ninguna') ) AS descripcion_obligatoriedad "
		 * + "FROM\r\n" + "          SCS_INSCRIPCIONGUARDIA\r\n" +
		 * "          JOIN SCS_GUARDIASTURNO ON SCS_INSCRIPCIONGUARDIA.IDINSTITUCION = SCS_GUARDIASTURNO.IDINSTITUCION AND SCS_INSCRIPCIONGUARDIA.IDTURNO = SCS_GUARDIASTURNO.IDTURNO\r\n"
		 * +
		 * "          JOIN SCS_TURNO ON SCS_TURNO.IDINSTITUCION = SCS_INSCRIPCIONGUARDIA.IDINSTITUCION AND SCS_TURNO.IDTURNO = SCS_INSCRIPCIONGUARDIA.IDTURNO\r\n"
		 * +
		 * "          JOIN SCS_ZONA ON SCS_ZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_ZONA.IDZONA = SCS_TURNO.IDZONA\r\n"
		 * +
		 * "          JOIN SCS_SUBZONA ON SCS_SUBZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_SUBZONA.IDZONA = SCS_TURNO.IDZONA AND SCS_SUBZONA.IDSUBZONA = SCS_TURNO.IDSUBZONA\r\n"
		 * +
		 * "          JOIN SCS_AREA ON SCS_AREA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_AREA.IDAREA = SCS_TURNO.IDAREA\r\n"
		 * +
		 * "          JOIN SCS_MATERIA ON SCS_MATERIA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_MATERIA.IDMATERIA = SCS_TURNO.IDMATERIA AND SCS_MATERIA.IDAREA = SCS_TURNO.IDAREA\r\n"
		 * +
		 * "          JOIN SCS_TIPOSGUARDIAS ON SCS_GUARDIASTURNO.IDTIPOGUARDIA = SCS_TIPOSGUARDIAS.IDTIPOGUARDIA\r\n"
		 * ; where +="          SCS_TURNO.IDINSTITUCION ='"+idInstitucion+"'";
		 * //"          AND GEN_RECURSOS_CATALOGOS.IDLENGUAJE = '"+idLenguaje+"'\r\n" ;
		 * } if(inscripcion.getIdturno() == null) { where += "          AND ( \r\n" +
		 * "          --Esté de baja\r\n" +
		 * "          (SCS_INSCRIPCIONGUARDIA.IDPERSONA = '"+inscripcion.getIdpersona()
		 * +"' AND SCS_INSCRIPCIONGUARDIA.FECHABAJA IS NOT NULL)\r\n"+
		 * "          --está inscrito pero que tengan alguna guardia a la que no estén inscritos\r\n"
		 * + ")" ; } else { where+=
		 * "          AND SCS_INSCRIPCIONGUARDIA.IDTURNO = '"+inscripcion.getIdturno()+
		 * "'" +
		 * "          AND SCS_INSCRIPCIONTURNO.IDPERSONA = '"+inscripcion.getIdpersona()
		 * +"'"; }
		 */

		select += "*\r\n" + "  FROM (SELECT          " + " NULL   AS idguardia, "
				+ " si1.idinstitucion   AS idinstitucion, " + " NULL  AS nombre_guardia,\r\n"
				+ " SCS_TURNO.NOMBRE AS NOMBRE_TURNO, " + "          SCS_TURNO.IDZONA,\r\n"
				+ "          SCS_ZONA.NOMBRE AS NOMBRE_ZONA,\r\n" + "          SCS_TURNO.IDSUBZONA,\r\n"
				+ "          SCS_SUBZONA.NOMBRE AS NOMBRE_SUBZONA,\r\n" + "          SCS_TURNO.IDAREA,\r\n"
				+ "          SCS_AREA.NOMBRE AS NOMBRE_AREA,\r\n" + "          SCS_TURNO.IDMATERIA,\r\n"
				+ "          SCS_MATERIA.NOMBRE AS NOMBRE_MATERIA,\r\n" + "          SCS_TURNO.IDTURNO,\r\n"
				+ "          SCS_TURNO.GUARDIAS AS OBLIGATORIEDAD_INSCRIPCION,  --La inscripcion en el turno obliga a inscribirse en guardias: 2- A elegir; 1-Todas o ninguna; 0-Obligatorias\r\n"
				+ " NULL AS descripcion_tipo_guardia, "
				+ "DECODE(scs_turno.guardias,0,'Obligatorias',DECODE(scs_turno.guardias,2,'A elegir','Todas o ninguna') ) AS descripcion_obligatoriedad "
				+ "FROM\r\n" + "          SCS_INSCRIPCIONTURNO si1 \r\n"
				+ "          JOIN SCS_TURNO ON SCS_TURNO.IDINSTITUCION = si1.IDINSTITUCION AND SCS_TURNO.IDTURNO = si1.IDTURNO\r\n"
				+ "          JOIN SCS_ZONA ON SCS_ZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_ZONA.IDZONA = SCS_TURNO.IDZONA\r\n"
				+ "          JOIN SCS_SUBZONA ON SCS_SUBZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_SUBZONA.IDZONA = SCS_TURNO.IDZONA AND SCS_SUBZONA.IDSUBZONA = SCS_TURNO.IDSUBZONA\r\n"
				+ "          JOIN SCS_AREA ON SCS_AREA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_AREA.IDAREA = SCS_TURNO.IDAREA\r\n"
				+ "          JOIN SCS_MATERIA ON SCS_MATERIA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_MATERIA.IDMATERIA = SCS_TURNO.IDMATERIA AND SCS_MATERIA.IDAREA = SCS_TURNO.IDAREA\r\n";
		where += "          SCS_TURNO.IDINSTITUCION ='" + idInstitucion + "'";
		where += "          AND ( \r\n" + "          --No esté de alta\r\n" + "          si1.IDPERSONA = '"
				+ inscripcion.getIdpersona() + "' "
				+ "			AND NOT EXISTS ( SELECT 1 FROM SCS_INSCRIPCIONTURNO si2 "
				+ "							WHERE si1.IDINSTITUCION = si2.IDINSTITUCION "
				+ "							AND si1.IDTURNO = si2.IDTURNO "
				+ "							AND si1.IDPERSONA = si2.IDPERSONA "
				+ "							AND si2.fechadenegacion IS NULL "
				+ "							AND si2.fechabaja IS NULL "
				+ "							AND si2.fechasolicitudbaja IS NULL "
				+ "							AND si2.fechavalidacion IS NOT NULL)"
				+ "			AND EXISTS ( SELECT ig.idturno "
				+ "							FROM scs_inscripcionguardia ig "
				+ "							WHERE ig.idinstitucion = si1.IDINSTITUCION "
				+ "							AND ig.idpersona = si1.IDPERSONA "
				+ "							AND ig.IDTURNO = si1.IDTURNO "
				+ "							AND ig.FECHABAJA IS NULL "
				+ "							AND ig.FECHAVALIDACION IS NOT NULL) " + " )";

		sql.SELECT_DISTINCT(select);
		sql.WHERE(where);
		if (inscripcion.getIdturno() == null) {
			sql.ORDER_BY("NOMBRE_TURNO)");
		} else {
			sql.ORDER_BY("NOMBRE_TURNO");
		}

		String result1 = sql.toString();

		SQL sql2 = new SQL();
		String prevariables = "TURNOS_ASIGNADOS (IDTURNO, IDINSTITUCION) AS (SELECT DISTINCT SCS_INSCRIPCIONGUARDIA.IDTURNO, SCS_INSCRIPCIONGUARDIA.IDINSTITUCION\r\n"
				+ "	FROM  SCS_INSCRIPCIONGUARDIA\r\n"
				+ "    JOIN SCS_TURNO ON SCS_TURNO.IDINSTITUCION = SCS_INSCRIPCIONGUARDIA.IDINSTITUCION AND SCS_TURNO.IDTURNO=SCS_INSCRIPCIONGUARDIA.IDTURNO\r\n"
				+ "    JOIN SCS_INSCRIPCIONTURNO ON SCS_INSCRIPCIONTURNO.IDINSTITUCION = SCS_INSCRIPCIONGUARDIA.IDINSTITUCION AND SCS_INSCRIPCIONTURNO.IDTURNO=SCS_INSCRIPCIONGUARDIA.IDTURNO"
				+ " AND SCS_INSCRIPCIONTURNO.IDPERSONA = SCS_INSCRIPCIONGUARDIA.IDPERSONA\r\n"
				+ "     WHERE SCS_TURNO.IDINSTITUCION = '" + idInstitucion + "' AND SCS_INSCRIPCIONGUARDIA.IDPERSONA='"
				+ inscripcion.getIdpersona() + "' "
				+ " AND SCS_INSCRIPCIONTURNO.FECHAVALIDACION IS NOT NULL AND SCS_INSCRIPCIONTURNO.FECHABAJA IS NULL ),\r\n"
				+ "    TURNOS_NO_ASIGNADOS (IDTURNO, IDINSTITUCION) AS(SELECT DISTINCT SCS_TURNO.IDTURNO, SCS_TURNO.IDINSTITUCION\r\n"
				+ "	FROM  SCS_TURNO\r\n" + "    WHERE SCS_TURNO.IDINSTITUCION = '" + idInstitucion
				+ "' AND IDTURNO NOT IN (\r\n" + "    SELECT IDTURNO FROM TURNOS_ASIGNADOS)), "
				+ " guardia_asignados ( " + "	    idguardia, " + "	    idinstitucion " + "	) AS ( "
				+ "	    SELECT DISTINCT " + "	        scs_inscripcionguardia.idguardia, "
				+ "	        scs_inscripcionguardia.idinstitucion " + "	    FROM " + "	        scs_inscripcionguardia "
				+ "	        JOIN scs_guardiasturno ON scs_guardiasturno.idinstitucion = scs_inscripcionguardia.idinstitucion "
				+ "									  AND scs_guardiasturno.IDTURNO = scs_inscripcionguardia.IDTURNO"
				+ "	                                  AND scs_guardiasturno.idguardia = scs_inscripcionguardia.idguardia "
				+ "	    WHERE " + "	        scs_guardiasturno.idinstitucion = '" + idInstitucion + "' "
				+ "	        AND scs_inscripcionguardia.idpersona = '" + inscripcion.getIdpersona() + "' AND scs_inscripcionguardia.fechabaja IS NULL"
				+ "	AND scs_inscripcionguardia.fechavalidacion IS NOT NULL ), guardia_no_asignados ( " + "	    idguardia, " + "	    idinstitucion " + "	) AS ( "
				+ "	    SELECT DISTINCT " + "	        scs_guardiasturno.idguardia, "
				+ "	        scs_guardiasturno.idinstitucion " + "	    FROM " + "	        scs_guardiasturno "
				+ "	    WHERE " + "	        scs_guardiasturno.idinstitucion = '" + idInstitucion + "' "
				+ "	        AND idguardia NOT IN ( " + "	            SELECT " + "	                idguardia "
				+ "	            FROM " + "	                guardia_asignados " + "	        ) " + "	) ";

		sql2.SELECT("* \r\n" + "FROM(\r\n" + "SELECT          " + " scs_guardiasturno.idguardia   AS idguardia, "
				+ "scs_guardiasturno.idinstitucion   AS idinstitucion, "
				+ "scs_guardiasturno.nombre AS nombre_guardia, " + "SCS_TURNO.NOMBRE AS NOMBRE_TURNO,\r\n"
				+ "          SCS_TURNO.IDZONA,\r\n" + "          SCS_ZONA.NOMBRE AS NOMBRE_ZONA,\r\n"
				+ "          SCS_TURNO.IDSUBZONA,\r\n" + "          SCS_SUBZONA.NOMBRE AS NOMBRE_SUBZONA,\r\n"
				+ "          SCS_TURNO.IDAREA,\r\n" + "          SCS_AREA.NOMBRE AS NOMBRE_AREA,\r\n"
				+ "          SCS_TURNO.IDMATERIA,\r\n" + "          SCS_MATERIA.NOMBRE AS NOMBRE_MATERIA,\r\n"
				+ "          SCS_TURNO.IDTURNO,\r\n"
				+ "          SCS_TURNO.GUARDIAS AS OBLIGATORIEDAD_INSCRIPCION, --La inscripcion en el turno obliga a inscribirse en guardias: 2- A elegir; 1-Todas o ninguna; 0-Obligatorias\r\n"
				+ "F_SIGA_GETRECURSO(SCS_TIPOSGUARDIAS.DESCRIPCION," + admUsuarios.getIdlenguaje()
				+ ") AS descripcion_tipo_guardia, "
				+ "DECODE(scs_turno.guardias,0,'Obligatorias',DECODE(scs_turno.guardias,2,'A elegir','Todas o ninguna') ) AS descripcion_obligatoriedad ");

		sql2.FROM("scs_guardiasturno\r\n"
				+ " RIGHT OUTER JOIN scs_turno ON scs_turno.idinstitucion = scs_guardiasturno.idinstitucion\r\n"
				+ "                              AND scs_turno.idturno = scs_guardiasturno.idturno\r\n"
				+ "          JOIN SCS_ZONA ON SCS_ZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_ZONA.IDZONA = SCS_TURNO.IDZONA\r\n"
				+ "          JOIN SCS_SUBZONA ON SCS_SUBZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_SUBZONA.IDZONA = SCS_TURNO.IDZONA AND SCS_SUBZONA.IDSUBZONA = SCS_TURNO.IDSUBZONA\r\n"
				+ "          JOIN SCS_AREA ON SCS_AREA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_AREA.IDAREA = SCS_TURNO.IDAREA\r\n"
				+ "          JOIN SCS_MATERIA ON SCS_MATERIA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_MATERIA.IDMATERIA = SCS_TURNO.IDMATERIA AND SCS_MATERIA.IDAREA = SCS_TURNO.IDAREA\r\n"
				+ "          LEFT OUTER JOIN SCS_TIPOSGUARDIAS ON SCS_GUARDIASTURNO.IDTIPOGUARDIA = SCS_TIPOSGUARDIAS.IDTIPOGUARDIA\r\n"

		);
		sql2.WHERE("          SCS_TURNO.IDINSTITUCION ='" + idInstitucion + "'"
				+ "          AND SCS_TURNO.IDTURNO IN (\r\n"
				+ "    SELECT IDTURNO FROM TURNOS_NO_ASIGNADOS)  AND (scs_guardiasturno.idguardia IN (\r\n"
				+ "                        SELECT\r\n" + "                            idguardia\r\n"
				+ "                        FROM\r\n" + "                            guardia_no_asignados\r\n"
				+ "                    ) OR scs_guardiasturno.idguardia IS NULL)");
		sql2.ORDER_BY("NOMBRE_TURNO)");

		String sql3 = "WITH " + prevariables + "\r\n";

		// sql 1 Elementos de baja, sql2 turnos a los que no se ha solicitado y sql 3
		// turnos en los que se ha suscrito a parte de las guardias.
		String final_result;
		String sub_final_result;
		if (inscripcion.getIdturno() == null && inscripcion.getIdpersona() != null) {

			sub_final_result = sql2.toString() + " \r\n UNION \r\n " + result1;
			/*
			 * final_result = sql3 + " \r\n " + "SELECT idgrupoguardia AS numerogrupo, \r\n"
			 * +
			 * "colegiado || '/' || NVL(lpad(idgrupoguardia, 3, '0'),'Sin grupo') AS colegiado_grupo,\r\n"
			 * + " 	idguardia,\r\n" + "	nombre_guardia,\r\n" + "	nombre_turno,\r\n" +
			 * "	nombre_zona,\r\n" + "	nombre_subzona,\r\n" + "	nombre_area,\r\n" +
			 * "	nombre_materia,\r\n" + "	obligatoriedad_inscripcion,\r\n" +
			 * "	idturno,\r\n" + "	idzona,\r\n" + "	idsubzona,\r\n" + "	idarea,\r\n"
			 * + "	idmateria,\r\n" + "	descripcion_tipo_guardia,\r\n" +
			 * "	descripcion_obligatoriedad\r\n" + "FROM(\r\n" + " SELECT\r\n" +
			 */
			final_result = sql3 + " SELECT DISTINCT \r\n" +
			// " sg.IDGRUPOGUARDIA,\r\n" +
			// " COUNT(decode(col.comunitario, '1', col.ncomunitario, col.ncolegiado)) AS
			// colegiado, "+
					"fullquery.idguardia, " + "nombre_guardia, " + "nombre_turno, " + "nombre_zona, "
					+ "nombre_subzona, " + "nombre_area, " + "nombre_materia, " + "obligatoriedad_inscripcion, "
					+ "fullquery.idturno, " + "idzona, " + "idsubzona, " + "idarea, " + "idmateria, "
					+ "descripcion_tipo_guardia, " + "descripcion_obligatoriedad " + "FROM " + "(" + sub_final_result
					+ ") fullquery " + "	ORDER BY nombre_turno, nombre_guardia";
			/*
			 * +
			 * "LEFT OUTER JOIN SCS_INSCRIPCIONGUARDIA ON SCS_INSCRIPCIONGUARDIA.IDINSTITUCION  = fullquery.IDINSTITUCION AND SCS_INSCRIPCIONGUARDIA.IDTURNO = fullquery.IDTURNO AND SCS_INSCRIPCIONGUARDIA.IDGUARDIA = fullquery.IDGUARDIA \r\n"
			 * +
			 * "	LEFT OUTER JOIN SCS_GRUPOGUARDIACOLEGIADO sg ON	SCS_INSCRIPCIONGUARDIA.idinstitucion = sg.idinstitucion AND SCS_INSCRIPCIONGUARDIA.idturno = sg.idturno AND SCS_INSCRIPCIONGUARDIA.idguardia = sg.idguardia AND sg.IDPERSONA = SCS_INSCRIPCIONGUARDIA.IDPERSONA \r\n"
			 * +
			 * "	LEFT OUTER JOIN cen_colegiado col ON col.idinstitucion = fullquery.idinstitucion AND col.IDPERSONA = SCS_INSCRIPCIONGUARDIA.IDPERSONA  \r\n"
			 * + "WHERE SCS_INSCRIPCIONGUARDIA.FECHAVALIDACION IS NOT NULL \r\n" +
			 * "AND (SCS_INSCRIPCIONGUARDIA.FECHABAJA IS NULL OR SCS_INSCRIPCIONGUARDIA.FECHABAJA > SYSDATE )\r\n"
			 * + "GROUP BY sg.IDGRUPOGUARDIA,\r\n" + "	fullquery.idguardia,\r\n" +
			 * "	nombre_guardia,\r\n" + "	nombre_turno,\r\n" + "	nombre_zona,\r\n" +
			 * "	nombre_subzona,\r\n" + "	nombre_area,\r\n" + "	nombre_materia,\r\n"
			 * + "	obligatoriedad_inscripcion,\r\n" + "	fullquery.idturno,\r\n" +
			 * "	idzona,\r\n" + "	idsubzona,\r\n" + "	idarea,\r\n" +
			 * "	idmateria,\r\n" + "	descripcion_tipo_guardia,\r\n" +
			 * "	descripcion_obligatoriedad\r\n" +
			 * "	ORDER BY nombre_turno, nombre_guardia)";
			 */
		} else {
			final_result = result1;
		}
		// SIGARNV-2009@DTT.JAMARTIN@05/08/2021@FIN
		return final_result;
	}

	public String busquedaTarjetaInscripcionesTurnosConGuardia(Short idInstitucion, AdmUsuarios admUsuarios,
			BusquedaInscripcionItem inscripcion) {

		SQL sql = new SQL();
		String where = "";
		String select = "";
		// Inscripciones de turnos a secas, sin asignacion a ninguna guardia
		if (inscripcion.getIdguardia() == null && inscripcion.getIdturno() != null) {
			select += " scs_guardiasturno.idguardia   AS idguardia, "
					+ " scs_inscripcionguardia.idinstitucion   AS idinstitucion, "
					+ "scs_guardiasturno.nombre AS nombre_guardia, " + "          SCS_TURNO.NOMBRE AS NOMBRE_TURNO,\r\n"
					+ "          SCS_TURNO.IDZONA,\r\n" + "          SCS_ZONA.NOMBRE AS NOMBRE_ZONA,\r\n"
					+ "          SCS_TURNO.IDSUBZONA,\r\n" + "          SCS_SUBZONA.NOMBRE AS NOMBRE_SUBZONA,\r\n"
					+ "          SCS_TURNO.IDAREA,\r\n" + "          SCS_AREA.NOMBRE AS NOMBRE_AREA,\r\n"
					+ "          SCS_TURNO.IDMATERIA,\r\n" + "          SCS_MATERIA.NOMBRE AS NOMBRE_MATERIA,\r\n"
					+ "          SCS_TURNO.IDTURNO,\r\n"
					+ "          SCS_TURNO.GUARDIAS AS OBLIGATORIEDAD_INSCRIPCION,  --La inscripcion en el turno obliga a inscribirse en guardias: 2- A elegir; 1-Todas o ninguna; 0-Obligatorias\r\n"
					+ "F_SIGA_GETRECURSO(SCS_TIPOSGUARDIAS.DESCRIPCION," + admUsuarios.getIdlenguaje()
					+ ") AS descripcion_tipo_guardia, "
					+ "DECODE(scs_turno.guardias,0,'Obligatorias',DECODE(scs_turno.guardias,2,'A elegir','Todas o ninguna') ) AS descripcion_obligatoriedad "
					+ "FROM\r\n" + "          SCS_INSCRIPCIONGUARDIA\r\n"
					+ "          JOIN SCS_TURNO ON SCS_TURNO.IDINSTITUCION = SCS_INSCRIPCIONGUARDIA.IDINSTITUCION AND SCS_TURNO.IDTURNO = SCS_INSCRIPCIONGUARDIA.IDTURNO\r\n"
					+ " 		   JOIN scs_guardiasturno ON scs_inscripcionguardia.idinstitucion = scs_guardiasturno.idinstitucion AND scs_inscripcionguardia.idturno = scs_guardiasturno.idturno"
					+ "          LEFT JOIN SCS_ZONA ON SCS_ZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_ZONA.IDZONA = SCS_TURNO.IDZONA\r\n"
					+ "          LEFT JOIN SCS_SUBZONA ON SCS_SUBZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_SUBZONA.IDZONA = SCS_TURNO.IDZONA AND SCS_SUBZONA.IDSUBZONA = SCS_TURNO.IDSUBZONA\r\n"
					+ "          LEFT JOIN SCS_AREA ON SCS_AREA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_AREA.IDAREA = SCS_TURNO.IDAREA\r\n"
					+ "          LEFT JOIN SCS_MATERIA ON SCS_MATERIA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_MATERIA.IDMATERIA = SCS_TURNO.IDMATERIA AND SCS_MATERIA.IDAREA = SCS_TURNO.IDAREA \r\n"
					+ "          LEFT JOIN SCS_TIPOSGUARDIAS ON SCS_GUARDIASTURNO.IDTIPOGUARDIA = SCS_TIPOSGUARDIAS.IDTIPOGUARDIA\r\n";
			where += "          SCS_TURNO.IDINSTITUCION ='" + idInstitucion + "'\r\n";
		} else {
			select += "*\r\n" + "  FROM (SELECT          " + " scs_guardiasturno.idguardia   AS idguardia, "
					+ " scs_inscripcionguardia.idinstitucion   AS idinstitucion, "
					+ "scs_guardiasturno.nombre  AS nombre_guardia,\r\n" + " SCS_TURNO.NOMBRE AS NOMBRE_TURNO, "
					+ "          SCS_TURNO.IDZONA,\r\n" + "          SCS_ZONA.NOMBRE AS NOMBRE_ZONA,\r\n"
					+ "          SCS_TURNO.IDSUBZONA,\r\n" + "          SCS_SUBZONA.NOMBRE AS NOMBRE_SUBZONA,\r\n"
					+ "          SCS_TURNO.IDAREA,\r\n" + "          SCS_AREA.NOMBRE AS NOMBRE_AREA,\r\n"
					+ "          SCS_TURNO.IDMATERIA,\r\n" + "          SCS_MATERIA.NOMBRE AS NOMBRE_MATERIA,\r\n"
					+ "          SCS_TURNO.IDTURNO,\r\n"
					+ "          SCS_TURNO.GUARDIAS AS OBLIGATORIEDAD_INSCRIPCION,  --La inscripcion en el turno obliga a inscribirse en guardias: 2- A elegir; 1-Todas o ninguna; 0-Obligatorias\r\n"
					+ "F_SIGA_GETRECURSO(SCS_TIPOSGUARDIAS.DESCRIPCION," + admUsuarios.getIdlenguaje()
					+ ") AS descripcion_tipo_guardia, "
					+ "DECODE(scs_turno.guardias,0,'Obligatorias',DECODE(scs_turno.guardias,2,'A elegir','Todas o ninguna') ) AS descripcion_obligatoriedad "
					+ "FROM\r\n" + "          SCS_INSCRIPCIONGUARDIA\r\n"
					+ "          JOIN SCS_GUARDIASTURNO ON SCS_INSCRIPCIONGUARDIA.IDINSTITUCION = SCS_GUARDIASTURNO.IDINSTITUCION AND SCS_INSCRIPCIONGUARDIA.IDTURNO = SCS_GUARDIASTURNO.IDTURNO\r\n"
					+ "          JOIN SCS_TURNO ON SCS_TURNO.IDINSTITUCION = SCS_INSCRIPCIONGUARDIA.IDINSTITUCION AND SCS_TURNO.IDTURNO = SCS_INSCRIPCIONGUARDIA.IDTURNO\r\n"
					+ "          LEFT JOIN SCS_ZONA ON SCS_ZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_ZONA.IDZONA = SCS_TURNO.IDZONA\r\n"
					+ "          LEFT JOIN SCS_SUBZONA ON SCS_SUBZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_SUBZONA.IDZONA = SCS_TURNO.IDZONA AND SCS_SUBZONA.IDSUBZONA = SCS_TURNO.IDSUBZONA\r\n"
					+ "          LEFT JOIN SCS_AREA ON SCS_AREA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_AREA.IDAREA = SCS_TURNO.IDAREA\r\n"
					+ "          LEFT JOIN SCS_MATERIA ON SCS_MATERIA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_MATERIA.IDMATERIA = SCS_TURNO.IDMATERIA AND SCS_MATERIA.IDAREA = SCS_TURNO.IDAREA\r\n"
					+ "          LEFT JOIN SCS_TIPOSGUARDIAS ON SCS_GUARDIASTURNO.IDTIPOGUARDIA = SCS_TIPOSGUARDIAS.IDTIPOGUARDIA\r\n";
			where += "          SCS_TURNO.IDINSTITUCION ='" + idInstitucion + "'";
			// " AND GEN_RECURSOS_CATALOGOS.IDLENGUAJE = '"+idLenguaje+"'\r\n" ;
		}
		if (inscripcion.getIdturno() == null) {
			where += "          AND ( \r\n" + "          --Esté de baja\r\n"
					+ "          (SCS_INSCRIPCIONGUARDIA.IDPERSONA = '" + inscripcion.getIdpersona()
					+ "' AND SCS_INSCRIPCIONGUARDIA.FECHABAJA IS NOT NULL)\r\n"
					+ "          --está inscrito pero que tengan alguna guardia a la que no estén inscritos\r\n" + ")";
		} else {
			where += "          AND SCS_INSCRIPCIONGUARDIA.IDTURNO = '" + inscripcion.getIdturno() + "'"
					+ "          AND SCS_INSCRIPCIONTURNO.IDPERSONA = '" + inscripcion.getIdpersona() + "'";
		}

		sql.SELECT_DISTINCT(select);
		sql.WHERE(where);
		if (inscripcion.getIdturno() == null) {
			sql.ORDER_BY("NOMBRE_TURNO)");
		} else {
			sql.ORDER_BY("NOMBRE_TURNO");
		}

		String result1 = sql.toString();

		SQL sql2 = new SQL();
		String prevariables = "TURNOS_ASIGNADOS (IDTURNO, IDINSTITUCION) AS (SELECT DISTINCT SCS_INSCRIPCIONGUARDIA.IDTURNO, SCS_INSCRIPCIONGUARDIA.IDINSTITUCION\r\n"
				+ "	FROM  SCS_INSCRIPCIONGUARDIA\r\n"
				+ "    JOIN SCS_TURNO ON SCS_TURNO.IDINSTITUCION = SCS_INSCRIPCIONGUARDIA.IDINSTITUCION AND SCS_TURNO.IDTURNO=SCS_INSCRIPCIONGUARDIA.IDTURNO\r\n"
				+ "     WHERE SCS_TURNO.IDINSTITUCION = '" + idInstitucion + "' AND SCS_INSCRIPCIONGUARDIA.IDPERSONA='"
				+ inscripcion.getIdpersona() + "' "
				+ " AND (SCS_INSCRIPCIONGUARDIA.FECHABAJA IS NOT NULL OR SCS_INSCRIPCIONGUARDIA.FECHADENEGACION IS NOT NULL) ),\r\n"
				+ "    TURNOS_NO_ASIGNADOS (IDTURNO, IDINSTITUCION) AS(SELECT DISTINCT SCS_TURNO.IDTURNO, SCS_TURNO.IDINSTITUCION\r\n"
				+ "	FROM  SCS_TURNO\r\n" + "    WHERE SCS_TURNO.IDINSTITUCION = '" + idInstitucion
				+ "' AND IDTURNO NOT IN (\r\n" + "    SELECT IDTURNO FROM TURNOS_ASIGNADOS)), "
				+ " guardia_asignados ( " + "	    idguardia, " + "	    idinstitucion " + "	) AS ( "
				+ "	    SELECT DISTINCT " + "	        scs_inscripcionguardia.idguardia, "
				+ "	        scs_inscripcionguardia.idinstitucion " + "	    FROM " + "	        scs_inscripcionguardia "
				+ "	        JOIN scs_guardiasturno ON scs_guardiasturno.idinstitucion = scs_inscripcionguardia.idinstitucion "
				+ "	                                  AND scs_guardiasturno.idguardia = scs_inscripcionguardia.idguardia "
				+ "	    WHERE " + "	        scs_guardiasturno.idinstitucion = '" + idInstitucion + "' "
				+ "	        AND scs_inscripcionguardia.idpersona = '" + inscripcion.getIdpersona() + "' "
				+ "	), guardia_no_asignados ( " + "	    idguardia, " + "	    idinstitucion " + "	) AS ( "
				+ "	    SELECT DISTINCT " + "	        scs_guardiasturno.idguardia, "
				+ "	        scs_guardiasturno.idinstitucion " + "	    FROM " + "	        scs_guardiasturno "
				+ "	    WHERE " + "	        scs_guardiasturno.idinstitucion = '" + idInstitucion + "' "
				+ "	        AND idguardia NOT IN ( " + "	            SELECT " + "	                idguardia "
				+ "	            FROM " + "	                guardia_asignados " + "	        ) " + "	) ";

		sql2.SELECT("* \r\n" + "FROM(\r\n" + "SELECT          " + " scs_guardiasturno.idguardia   AS idguardia, "
				+ "scs_guardiasturno.idinstitucion   AS idinstitucion, "
				+ "scs_guardiasturno.nombre AS nombre_guardia, " + "SCS_TURNO.NOMBRE AS NOMBRE_TURNO,\r\n"
				+ "          SCS_TURNO.IDZONA,\r\n" + "          SCS_ZONA.NOMBRE AS NOMBRE_ZONA,\r\n"
				+ "          SCS_TURNO.IDSUBZONA,\r\n" + "          SCS_SUBZONA.NOMBRE AS NOMBRE_SUBZONA,\r\n"
				+ "          SCS_TURNO.IDAREA,\r\n" + "          SCS_AREA.NOMBRE AS NOMBRE_AREA,\r\n"
				+ "          SCS_TURNO.IDMATERIA,\r\n" + "          SCS_MATERIA.NOMBRE AS NOMBRE_MATERIA,\r\n"
				+ "          SCS_TURNO.IDTURNO,\r\n"
				+ "          SCS_TURNO.GUARDIAS AS OBLIGATORIEDAD_INSCRIPCION, --La inscripcion en el turno obliga a inscribirse en guardias: 2- A elegir; 1-Todas o ninguna; 0-Obligatorias\r\n"
				+ "F_SIGA_GETRECURSO(SCS_TIPOSGUARDIAS.DESCRIPCION," + admUsuarios.getIdlenguaje()
				+ ") AS descripcion_tipo_guardia, "
				+ "DECODE(scs_turno.guardias,0,'Obligatorias',DECODE(scs_turno.guardias,2,'A elegir','Todas o ninguna') ) AS descripcion_obligatoriedad ");

		sql2.FROM("scs_guardiasturno\r\n"
				+ " JOIN scs_turno ON scs_turno.idinstitucion = scs_guardiasturno.idinstitucion\r\n"
				+ "                              AND scs_turno.idturno = scs_guardiasturno.idturno\r\n"
				+ "          LEFT JOIN SCS_ZONA ON SCS_ZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_ZONA.IDZONA = SCS_TURNO.IDZONA\r\n"
				+ "          LEFT JOIN SCS_SUBZONA ON SCS_SUBZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_SUBZONA.IDZONA = SCS_TURNO.IDZONA AND SCS_SUBZONA.IDSUBZONA = SCS_TURNO.IDSUBZONA\r\n"
				+ "          LEFT JOIN SCS_AREA ON SCS_AREA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_AREA.IDAREA = SCS_TURNO.IDAREA\r\n"
				+ "          LEFT JOIN SCS_MATERIA ON SCS_MATERIA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_MATERIA.IDMATERIA = SCS_TURNO.IDMATERIA AND SCS_MATERIA.IDAREA = SCS_TURNO.IDAREA\r\n"
				+ "          LEFT JOIN SCS_TIPOSGUARDIAS ON SCS_GUARDIASTURNO.IDTIPOGUARDIA = SCS_TIPOSGUARDIAS.IDTIPOGUARDIA\r\n"

		);
		sql2.WHERE("          SCS_TURNO.IDINSTITUCION ='" + idInstitucion + "'"
				+ "          AND SCS_TURNO.IDTURNO IN (\r\n"
				+ "    SELECT IDTURNO FROM TURNOS_NO_ASIGNADOS)  AND (scs_guardiasturno.idguardia IN (\r\n"
				+ "                        SELECT\r\n" + "                            idguardia\r\n"
				+ "                        FROM\r\n" + "                            guardia_no_asignados\r\n"
				+ "                    ) OR scs_guardiasturno.idguardia IS NULL)");
		sql2.ORDER_BY("NOMBRE_TURNO)");

		String sql3 = "WITH " + prevariables + "\r\n";

		// sql 1 Elementos de baja, sql2 turnos a los que no se ha solicitado y sql 3
		// turnos en los que se ha suscrito a parte de las guardias.
		String final_result;
		String sub_final_result;
		if (inscripcion.getIdturno() == null && inscripcion.getIdpersona() != null) {

			sub_final_result = sql2.toString() + " \r\n UNION \r\n " + result1;
			/*
			 * final_result = sql3 + " \r\n " + "SELECT idgrupoguardia AS numerogrupo, \r\n"
			 * +
			 * "colegiado || '/' || NVL(lpad(idgrupoguardia, 3, '0'),'Sin grupo') AS colegiado_grupo,\r\n"
			 * + " 	idguardia,\r\n" + "	nombre_guardia,\r\n" + "	nombre_turno,\r\n" +
			 * "	nombre_zona,\r\n" + "	nombre_subzona,\r\n" + "	nombre_area,\r\n" +
			 * "	nombre_materia,\r\n" + "	obligatoriedad_inscripcion,\r\n" +
			 * "	idturno,\r\n" + "	idzona,\r\n" + "	idsubzona,\r\n" + "	idarea,\r\n"
			 * + "	idmateria,\r\n" + "	descripcion_tipo_guardia,\r\n" +
			 * "	descripcion_obligatoriedad\r\n" + "FROM(\r\n" + " SELECT\r\n" +
			 */
			final_result = sql3 + " SELECT\r\n" +
			// " sg.IDGRUPOGUARDIA,\r\n" +
			// " COUNT(decode(col.comunitario, '1', col.ncomunitario, col.ncolegiado)) AS
			// colegiado, "+
					"fullquery.idguardia, " + "nombre_guardia, " + "nombre_turno, " + "nombre_zona, "
					+ "nombre_subzona, " + "nombre_area, " + "nombre_materia, " + "obligatoriedad_inscripcion, "
					+ "fullquery.idturno, " + "idzona, " + "idsubzona, " + "idarea, " + "idmateria, "
					+ "descripcion_tipo_guardia, " + "descripcion_obligatoriedad " + "FROM " + "(" + sub_final_result
					+ ") fullquery " + "	ORDER BY nombre_turno, nombre_guardia";
			/*
			 * +
			 * "LEFT OUTER JOIN SCS_INSCRIPCIONGUARDIA ON SCS_INSCRIPCIONGUARDIA.IDINSTITUCION  = fullquery.IDINSTITUCION AND SCS_INSCRIPCIONGUARDIA.IDTURNO = fullquery.IDTURNO AND SCS_INSCRIPCIONGUARDIA.IDGUARDIA = fullquery.IDGUARDIA \r\n"
			 * +
			 * "	LEFT OUTER JOIN SCS_GRUPOGUARDIACOLEGIADO sg ON	SCS_INSCRIPCIONGUARDIA.idinstitucion = sg.idinstitucion AND SCS_INSCRIPCIONGUARDIA.idturno = sg.idturno AND SCS_INSCRIPCIONGUARDIA.idguardia = sg.idguardia AND sg.IDPERSONA = SCS_INSCRIPCIONGUARDIA.IDPERSONA \r\n"
			 * +
			 * "	LEFT OUTER JOIN cen_colegiado col ON col.idinstitucion = fullquery.idinstitucion AND col.IDPERSONA = SCS_INSCRIPCIONGUARDIA.IDPERSONA  \r\n"
			 * + "WHERE SCS_INSCRIPCIONGUARDIA.FECHAVALIDACION IS NOT NULL \r\n" +
			 * "AND (SCS_INSCRIPCIONGUARDIA.FECHABAJA IS NULL OR SCS_INSCRIPCIONGUARDIA.FECHABAJA > SYSDATE )\r\n"
			 * + "GROUP BY sg.IDGRUPOGUARDIA,\r\n" + "	fullquery.idguardia,\r\n" +
			 * "	nombre_guardia,\r\n" + "	nombre_turno,\r\n" + "	nombre_zona,\r\n" +
			 * "	nombre_subzona,\r\n" + "	nombre_area,\r\n" + "	nombre_materia,\r\n"
			 * + "	obligatoriedad_inscripcion,\r\n" + "	fullquery.idturno,\r\n" +
			 * "	idzona,\r\n" + "	idsubzona,\r\n" + "	idarea,\r\n" +
			 * "	idmateria,\r\n" + "	descripcion_tipo_guardia,\r\n" +
			 * "	descripcion_obligatoriedad\r\n" +
			 * "	ORDER BY nombre_turno, nombre_guardia)";
			 */
		} else {
			final_result = result1;
		}
		// SIGARNV-2009@DTT.JAMARTIN@05/08/2021@FIN
		return final_result;
	}

	public String busquedaTarjetaInscripcionesGuardia2(Short idInstitucion, AdmUsuarios admUsuarios,
			BusquedaInscripcionItem inscripcion) {

		SQL sql = new SQL();
		String where = "";
		String select = "";
		// Inscripciones de turnos a secas, sin asignacion a ninguna guardia
		if (inscripcion.getIdguardia() == null && inscripcion.getIdturno() != null) {
			select += " scs_guardiasturno.idguardia   AS idguardia, "
					+ " scs_inscripcionguardia.idinstitucion   AS idinstitucion, "
					+ "scs_guardiasturno.nombre AS nombre_guardia, " + "          SCS_TURNO.NOMBRE AS NOMBRE_TURNO,\r\n"
					+ "          SCS_TURNO.IDZONA,\r\n" + "          SCS_ZONA.NOMBRE AS NOMBRE_ZONA,\r\n"
					+ "          SCS_TURNO.IDSUBZONA,\r\n" + "          SCS_SUBZONA.NOMBRE AS NOMBRE_SUBZONA,\r\n"
					+ "          SCS_TURNO.IDAREA,\r\n" + "          SCS_AREA.NOMBRE AS NOMBRE_AREA,\r\n"
					+ "          SCS_TURNO.IDMATERIA,\r\n" + "          SCS_MATERIA.NOMBRE AS NOMBRE_MATERIA,\r\n"
					+ "          SCS_TURNO.IDTURNO,\r\n"
					+ "          SCS_TURNO.GUARDIAS AS OBLIGATORIEDAD_INSCRIPCION,  --La inscripcion en el turno obliga a inscribirse en guardias: 2- A elegir; 1-Todas o ninguna; 0-Obligatorias\r\n"
					+ "gen_recursos_catalogos.descripcion AS descripcion_tipo_guardia, "
					+ "DECODE(scs_turno.guardias,0,'Obligatorias',DECODE(scs_turno.guardias,2,'A elegir','Todas o ninguna') ) AS descripcion_obligatoriedad "
					+ "FROM\r\n" + "          SCS_INSCRIPCIONGUARDIA\r\n"
					+ "          JOIN SCS_TURNO ON SCS_TURNO.IDINSTITUCION = SCS_INSCRIPCIONGUARDIA.IDINSTITUCION AND SCS_TURNO.IDTURNO = SCS_INSCRIPCIONGUARDIA.IDTURNO\r\n"
					+ " 		   JOIN scs_guardiasturno ON scs_inscripcionguardia.idinstitucion = scs_guardiasturno.idinstitucion AND scs_inscripcionguardia.idturno = scs_guardiasturno.idturno"
					+ "          LEFT JOIN SCS_ZONA ON SCS_ZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_ZONA.IDZONA = SCS_TURNO.IDZONA\r\n"
					+ "          LEFT JOIN SCS_SUBZONA ON SCS_SUBZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_SUBZONA.IDZONA = SCS_TURNO.IDZONA AND SCS_SUBZONA.IDSUBZONA = SCS_TURNO.IDSUBZONA\r\n"
					+ "          LEFT JOIN SCS_AREA ON SCS_AREA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_AREA.IDAREA = SCS_TURNO.IDAREA\r\n"
					+ "          LEFT JOIN SCS_MATERIA ON SCS_MATERIA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_MATERIA.IDMATERIA = SCS_TURNO.IDMATERIA AND SCS_MATERIA.IDAREA = SCS_TURNO.IDAREA \r\n"
					+ "          LEFT JOIN SCS_TIPOSGUARDIAS ON SCS_GUARDIASTURNO.IDTIPOGUARDIA = SCS_TIPOSGUARDIAS.IDTIPOGUARDIA\r\n"
					+ "          LEFT JOIN GEN_RECURSOS_CATALOGOS ON SCS_TIPOSGUARDIAS.DESCRIPCION = GEN_RECURSOS_CATALOGOS.IDRECURSO \r\n";
			where += "          SCS_TURNO.IDINSTITUCION ='" + idInstitucion + "'\r\n";
		} else {
			select += "*\r\n" + "  FROM (SELECT          " + " scs_guardiasturno.idguardia   AS idguardia, "
					+ " scs_inscripcionguardia.idinstitucion   AS idinstitucion, "
					+ "SCS_TURNO.NOMBRE AS NOMBRE_TURNO,\r\n" + " scs_guardiasturno.nombre  AS nombre_guardia, "
					+ "          SCS_TURNO.IDZONA,\r\n" + "          SCS_ZONA.NOMBRE AS NOMBRE_ZONA,\r\n"
					+ "          SCS_TURNO.IDSUBZONA,\r\n" + "          SCS_SUBZONA.NOMBRE AS NOMBRE_SUBZONA,\r\n"
					+ "          SCS_TURNO.IDAREA,\r\n" + "          SCS_AREA.NOMBRE AS NOMBRE_AREA,\r\n"
					+ "          SCS_TURNO.IDMATERIA,\r\n" + "          SCS_MATERIA.NOMBRE AS NOMBRE_MATERIA,\r\n"
					+ "          SCS_TURNO.IDTURNO,\r\n"
					+ "          SCS_TURNO.GUARDIAS AS OBLIGATORIEDAD_INSCRIPCION,  --La inscripcion en el turno obliga a inscribirse en guardias: 2- A elegir; 1-Todas o ninguna; 0-Obligatorias\r\n"
					+ "gen_recursos_catalogos.descripcion AS descripcion_tipo_guardia, "
					+ "DECODE(scs_turno.guardias,0,'Obligatorias',DECODE(scs_turno.guardias,2,'A elegir','Todas o ninguna') ) AS descripcion_obligatoriedad "
					+ "FROM\r\n" + "          SCS_INSCRIPCIONGUARDIA\r\n"
					+ "          JOIN SCS_GUARDIASTURNO ON SCS_INSCRIPCIONGUARDIA.IDINSTITUCION = SCS_GUARDIASTURNO.IDINSTITUCION AND SCS_INSCRIPCIONGUARDIA.IDTURNO = SCS_GUARDIASTURNO.IDTURNO\r\n"
					+ "          JOIN SCS_TURNO ON SCS_TURNO.IDINSTITUCION = SCS_INSCRIPCIONGUARDIA.IDINSTITUCION AND SCS_TURNO.IDTURNO = SCS_INSCRIPCIONGUARDIA.IDTURNO\r\n"
					+ "          LEFT JOIN SCS_ZONA ON SCS_ZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_ZONA.IDZONA = SCS_TURNO.IDZONA\r\n"
					+ "          LEFT JOIN SCS_SUBZONA ON SCS_SUBZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_SUBZONA.IDZONA = SCS_TURNO.IDZONA AND SCS_SUBZONA.IDSUBZONA = SCS_TURNO.IDSUBZONA\r\n"
					+ "          LEFT JOIN SCS_AREA ON SCS_AREA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_AREA.IDAREA = SCS_TURNO.IDAREA\r\n"
					+ "          LEFT JOIN SCS_MATERIA ON SCS_MATERIA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_MATERIA.IDMATERIA = SCS_TURNO.IDMATERIA AND SCS_MATERIA.IDAREA = SCS_TURNO.IDAREA\r\n"
					+ "          LEFT JOIN SCS_TIPOSGUARDIAS ON SCS_GUARDIASTURNO.IDTIPOGUARDIA = SCS_TIPOSGUARDIAS.IDTIPOGUARDIA\r\n"
					+ "          LEFT JOIN GEN_RECURSOS_CATALOGOS ON SCS_TIPOSGUARDIAS.DESCRIPCION = GEN_RECURSOS_CATALOGOS.IDRECURSO \r\n";
			where += "          SCS_TURNO.IDINSTITUCION ='" + idInstitucion + "'";
			// " AND GEN_RECURSOS_CATALOGOS.IDLENGUAJE = '"+idLenguaje+"'\r\n" ;
		}
		if (inscripcion.getIdturno() == null) {
			where += "          AND ( \r\n" + "          --Esté de baja\r\n"
					+ "          (SCS_INSCRIPCIONGUARDIA.IDPERSONA = '" + inscripcion.getIdpersona()
					+ "' AND SCS_INSCRIPCIONGUARDIA.FECHABAJA IS NOT NULL)\r\n"
					+ "          --está inscrito pero que tengan alguna guardia a la que no estén inscritos\r\n" + ")";
		} else {
			where += "          AND SCS_INSCRIPCIONGUARDIA.IDTURNO = '" + inscripcion.getIdturno() + "'"
					+ "          AND SCS_INSCRIPCIONTURNO.IDPERSONA = '" + inscripcion.getIdpersona() + "'";
		}

		sql.SELECT_DISTINCT(select);
		sql.WHERE(where);
		if (inscripcion.getIdturno() == null) {
			sql.ORDER_BY("NOMBRE_TURNO)");
		} else {
			sql.ORDER_BY("NOMBRE_TURNO");
		}

		String result1 = sql.toString();

		SQL sql2 = new SQL();
		String prevariables = "TURNOS_ASIGNADOS (IDTURNO, IDINSTITUCION) AS (SELECT DISTINCT SCS_INSCRIPCIONGUARDIA.IDTURNO, SCS_INSCRIPCIONGUARDIA.IDINSTITUCION\r\n"
				+ "	FROM  SCS_INSCRIPCIONGUARDIA\r\n"
				+ "    JOIN SCS_TURNO ON SCS_TURNO.IDINSTITUCION = SCS_INSCRIPCIONGUARDIA.IDINSTITUCION AND SCS_TURNO.IDTURNO=SCS_INSCRIPCIONGUARDIA.IDTURNO\r\n"
				+ "     WHERE SCS_TURNO.IDINSTITUCION = '" + idInstitucion + "' AND SCS_INSCRIPCIONGUARDIA.IDPERSONA='"
				+ inscripcion.getIdpersona() + "' ),\r\n"
				+ "    TURNOS_NO_ASIGNADOS (IDTURNO, IDINSTITUCION) AS(SELECT DISTINCT SCS_TURNO.IDTURNO, SCS_TURNO.IDINSTITUCION\r\n"
				+ "	FROM  SCS_TURNO\r\n" + "    WHERE SCS_TURNO.IDINSTITUCION = '" + idInstitucion
				+ "' AND IDTURNO NOT IN (\r\n" + "    SELECT IDTURNO FROM TURNOS_ASIGNADOS)), "
				+ " guardia_asignados ( " + "	    idguardia, " + "	    idinstitucion " + "	) AS ( "
				+ "	    SELECT DISTINCT " + "	        scs_inscripcionguardia.idguardia, "
				+ "	        scs_inscripcionguardia.idinstitucion " + "	    FROM " + "	        scs_inscripcionguardia "
				+ "	        JOIN scs_guardiasturno ON scs_guardiasturno.idinstitucion = scs_inscripcionguardia.idinstitucion "
				+ "	                                  AND scs_guardiasturno.idguardia = scs_inscripcionguardia.idguardia "
				+ "	    WHERE " + "	        scs_guardiasturno.idinstitucion = '2005' "
				+ "	        AND scs_inscripcionguardia.idpersona = '3500000039' " + "	), guardia_no_asignados ( "
				+ "	    idguardia, " + "	    idinstitucion " + "	) AS ( " + "	    SELECT DISTINCT "
				+ "	        scs_guardiasturno.idguardia, " + "	        scs_guardiasturno.idinstitucion "
				+ "	    FROM " + "	        scs_guardiasturno " + "	    WHERE "
				+ "	        scs_guardiasturno.idinstitucion = '2005' " + "	        AND idguardia NOT IN ( "
				+ "	            SELECT " + "	                idguardia " + "	            FROM "
				+ "	                guardia_asignados " + "	        ) " + "	) ";

		sql2.SELECT("* \r\n" + "FROM(\r\n" + "SELECT          " + " scs_guardiasturno.idguardia   AS idguardia, "
				+ "scs_guardiasturno.idinstitucion   AS idinstitucion, "
				+ "scs_guardiasturno.nombre AS nombre_guardia, " + "SCS_TURNO.NOMBRE AS NOMBRE_TURNO,\r\n"
				+ "          SCS_TURNO.IDZONA,\r\n" + "          SCS_ZONA.NOMBRE AS NOMBRE_ZONA,\r\n"
				+ "          SCS_TURNO.IDSUBZONA,\r\n" + "          SCS_SUBZONA.NOMBRE AS NOMBRE_SUBZONA,\r\n"
				+ "          SCS_TURNO.IDAREA,\r\n" + "          SCS_AREA.NOMBRE AS NOMBRE_AREA,\r\n"
				+ "          SCS_TURNO.IDMATERIA,\r\n" + "          SCS_MATERIA.NOMBRE AS NOMBRE_MATERIA,\r\n"
				+ "          SCS_TURNO.IDTURNO,\r\n"
				+ "          SCS_TURNO.GUARDIAS AS OBLIGATORIEDAD_INSCRIPCION, --La inscripcion en el turno obliga a inscribirse en guardias: 2- A elegir; 1-Todas o ninguna; 0-Obligatorias\r\n"
				+ "gen_recursos_catalogos.descripcion AS descripcion_tipo_guardia, "
				+ "DECODE(scs_turno.guardias,0,'Obligatorias',DECODE(scs_turno.guardias,2,'A elegir','Todas o ninguna') ) AS descripcion_obligatoriedad ");

		sql2.FROM("scs_guardiasturno\r\n"
				+ "			JOIN scs_turno ON scs_turno.idinstitucion = scs_guardiasturno.idinstitucion\r\n"
				+ "                              AND scs_turno.idturno = scs_guardiasturno.idturno\r\n"
				+ "          LEFT JOIN SCS_ZONA ON SCS_ZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_ZONA.IDZONA = SCS_TURNO.IDZONA\r\n"
				+ "          LEFT JOIN SCS_SUBZONA ON SCS_SUBZONA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_SUBZONA.IDZONA = SCS_TURNO.IDZONA AND SCS_SUBZONA.IDSUBZONA = SCS_TURNO.IDSUBZONA\r\n"
				+ "          LEFT JOIN SCS_AREA ON SCS_AREA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_AREA.IDAREA = SCS_TURNO.IDAREA\r\n"
				+ "          LEFT JOIN SCS_MATERIA ON SCS_MATERIA.IDINSTITUCION = SCS_TURNO.IDINSTITUCION AND SCS_MATERIA.IDMATERIA = SCS_TURNO.IDMATERIA AND SCS_MATERIA.IDAREA = SCS_TURNO.IDAREA\r\n"
				+ "          LEFT JOIN SCS_TIPOSGUARDIAS ON SCS_GUARDIASTURNO.IDTIPOGUARDIA = SCS_TIPOSGUARDIAS.IDTIPOGUARDIA\r\n"
				+ "          LEFT JOIN GEN_RECURSOS_CATALOGOS ON SCS_TIPOSGUARDIAS.DESCRIPCION = GEN_RECURSOS_CATALOGOS.IDRECURSO \r\n"

		);
		sql2.WHERE("          SCS_TURNO.IDINSTITUCION ='" + idInstitucion + "'"
				+ "          AND SCS_TURNO.IDTURNO IN (\r\n"
				+ "    SELECT IDTURNO FROM TURNOS_NO_ASIGNADOS)  AND scs_guardiasturno.idguardia IN (\r\n"
				+ "                        SELECT\r\n" + "                            idguardia\r\n"
				+ "                        FROM\r\n" + "                            guardia_no_asignados\r\n"
				+ "                    )");
		sql2.ORDER_BY("NOMBRE_TURNO)");

		String sql3 = "WITH " + prevariables + "\r\n";

		// sql 1 Elementos de baja, sql2 turnos a los que no se ha solicitado y sql 3
		// turnos en los que se ha suscrito a parte de las guardias.
		String final_result;
		String sub_final_result;
		if (inscripcion.getIdturno() == null) {
			sub_final_result = sql2.toString() + " \r\n UNION \r\n " + result1;
			final_result = sql3 + " \r\n " + "SELECT " + "scs_grupoguardia.numerogrupo AS numerogrupo, "
					+ "decode(cen_colegiado.comunitario, '1', cen_colegiado.ncomunitario, cen_colegiado.ncolegiado) AS colegiado, "
					+ " ( decode(cen_colegiado.comunitario, '1', cen_colegiado.ncomunitario, cen_colegiado.ncolegiado) "
					+ "  || '/' " + "   || lpad(scs_grupoguardia.numerogrupo, 3, '0') ) AS colegiado_grupo, "
					+ " ( cen_persona.apellidos1 " + "|| ' ' " + "|| cen_persona.apellidos2 " + "|| ', ' "
					+ "|| cen_persona.nombre ) AS letrado, " + "scs_inscripcionguardia.fechasuscripcion, "
					+ "idguardia, " + "nombre_guardia, " + "nombre_turno, " + "nombre_zona, " + "nombre_subzona, "
					+ "nombre_area, " + "nombre_materia, " + "obligatoriedad_inscripcion, " + "idturno, " + "idzona, "
					+ "idsubzona, " + "idarea, " + "idmateria, " + "descripcion_tipo_guardia, "
					+ "descripcion_obligatoriedad " + "FROM " + "(" + sub_final_result + ") fullquery "
					+ "JOIN scs_inscripcionguardia ON scs_inscripcionguardia.idinstitucion = fullquery.idinstitucion\r\n"
					+ "                                   AND scs_inscripcionguardia.idturno = fullquery.idturno\r\n"
					+ "    JOIN scs_grupoguardia ON fullquery.idguardia = scs_grupoguardia.idguardia\r\n"
					+ "    JOIN cen_persona ON cen_persona.idpersona = scs_inscripcionguardia.idpersona\r\n"
					+ "    JOIN cen_colegiado ON cen_colegiado.idpersona = scs_inscripcionguardia.idpersona\r\n"
					+ "                          AND cen_colegiado.idinstitucion = fullquery.idinstitucion";
		} else {
			final_result = result1;
		}
		// SIGARNV-2009@DTT.JAMARTIN@05/08/2021@FIN
		return final_result;
	}

	public String inscripcionPorguardia(Short idInstitucion, AdmUsuarios admUsuarios, String guardia,
			String idpersona) {
		SQL sql = new SQL();
		sql.SELECT("scs_inscripcionguardia.idguardia AS idguardia", "scs_inscripcionguardia.idturno AS idturno",
				"scs_grupoguardia.numerogrupo AS numerogrupo",
				"(" + "        SELECT"
						+ "            decode(cen_colegiado.comunitario, '1', cen_colegiado.ncomunitario, cen_colegiado.ncolegiado)"
						+ "        FROM" + "            cen_colegiado" + "        WHERE"
						+ "            cen_colegiado.idpersona = scs_inscripcionguardia.idpersona"
						+ "            AND cen_colegiado.idinstitucion = scs_inscripcionguardia.idinstitucion"
						+ "    ) AS colegiado",
				"(" + "        SELECT"
						+ "            decode(cen_colegiado.comunitario, '1', cen_colegiado.ncomunitario, cen_colegiado.ncolegiado)"
						+ "            || '/'" + "            || lpad(scs_grupoguardia.numerogrupo, 3, '0')"
						+ "        FROM" + "            cen_colegiado" + "        WHERE"
						+ "            cen_colegiado.idpersona = scs_inscripcionguardia.idpersona"
						+ "            AND cen_colegiado.idinstitucion = scs_inscripcionguardia.idinstitucion"
						+ "    ) AS colegiado_grupo",
				"(" + "        SELECT" + "            cen_persona.apellidos1" + "            || ' '"
						+ "            || cen_persona.apellidos2" + "            || ', '"
						+ "            || cen_persona.nombre" + "        FROM" + "            cen_persona"
						+ "        WHERE" + "            cen_persona.idpersona = scs_inscripcionguardia.idpersona"
						+ "    ) AS letrado, " + "scs_turno.nombre AS nombre_turno",
				"scs_turno.idzona AS idzona",
				"(" + "        SELECT" + "            scs_zona.nombre" + "        FROM" + "            scs_zona"
						+ "        WHERE" + "            scs_zona.idinstitucion = scs_turno.idinstitucion"
						+ "            AND scs_zona.idzona = scs_turno.idzona" + "    ) AS nombre_zona",
				"scs_turno.idsubzona AS idsubzona",
				"(" + "        SELECT" + "            scs_subzona.nombre" + "        FROM" + "            scs_subzona"
						+ "        WHERE" + "            scs_subzona.idinstitucion = scs_turno.idinstitucion"
						+ "            AND scs_subzona.idzona = scs_turno.idzona"
						+ "            AND scs_subzona.idsubzona = scs_turno.idsubzona" + "    ) AS nombre_subzona",
				"scs_turno.idarea AS idarea",
				"(" + "        SELECT" + "            scs_area.nombre" + "        FROM" + "            scs_area"
						+ "        WHERE" + "            scs_area.idinstitucion = scs_turno.idinstitucion"
						+ "            AND scs_area.idarea = scs_turno.idarea" + "    ) AS nombre_area",
				"scs_turno.idmateria AS idmateria",
				"(" + "        SELECT" + "            scs_materia.nombre" + "        FROM" + "            scs_materia"
						+ "        WHERE" + "            scs_materia.idinstitucion = scs_turno.idinstitucion"
						+ "            AND scs_materia.idmateria = scs_turno.idmateria"
						+ "            AND scs_materia.idarea = scs_turno.idarea" + "    ) AS nombre_materia",
				"scs_guardiasturno.nombre AS nombre_guardia",
				"gen_recursos_catalogos.descripcion AS descripcion_tipo_guardia",
				"scs_turno.guardias AS obligatoriedad_inscripcion",
				"DECODE(scs_turno.guardias,0,'Obligatorias',DECODE(scs_turno.guardias,2,'A elegir','Todas o ninguna') ) AS descripcion_obligatoriedad");

		sql.FROM("scs_inscripcionguardia");
		// Left Join
		sql.JOIN(
				"scs_turno ON scs_turno.idinstitucion = scs_inscripcionguardia.idinstitucion AND scs_turno.idturno = scs_inscripcionguardia.idturno");
		sql.JOIN(
				"scs_guardiasturno ON scs_inscripcionguardia.idguardia = scs_guardiasturno.idguardia AND scs_inscripcionguardia.idinstitucion = scs_guardiasturno.idinstitucion AND scs_inscripcionguardia.idturno = scs_guardiasturno.idturno");
		sql.LEFT_OUTER_JOIN("scs_grupoguardia ON scs_guardiasturno.idguardia = scs_grupoguardia.idguardia");
		sql.JOIN("scs_tiposguardias ON scs_guardiasturno.idtipoguardia = scs_tiposguardias.idtipoguardia");
		// Left Join
		sql.JOIN("gen_recursos_catalogos ON scs_tiposguardias.descripcion = gen_recursos_catalogos.idrecurso");

		sql.WHERE("scs_inscripcionguardia.idpersona = " + idpersona);
		sql.WHERE("scs_inscripcionguardia.idinstitucion = " + idInstitucion);
		sql.WHERE("scs_inscripcionguardia.IDGUARDIA = " + guardia);
		sql.WHERE("gen_recursos_catalogos.idlenguaje = " + admUsuarios.getIdlenguaje());
		return sql.toString();
	}

	public String getInscripcionByTurnoGuardiaNcolegiado(String usuModif, String idTurno, String idGuardia,
			String numColegiado) {

		SQL sql2 = new SQL();
		sql2.SELECT("IDPERSONA");
		sql2.FROM("CEN_COLEGIADO");
		if (!numColegiado.isEmpty() && numColegiado != null) {
			sql2.WHERE("NCOLEGIADO = " + numColegiado);
		}
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("SCS_INSCRIPCIONGUARDIA");
//	    if (!usuModif.isEmpty() && usuModif != null) {
//	    sql.WHERE("USUMODIFICACION = "+ usuModif);
//	    }
		if (!idTurno.isEmpty() && idTurno != null) {
			sql.WHERE("IDTURNO = " + idTurno);
		}
		sql.WHERE("IDPERSONA in ( " + sql2 + ")");
		if (!idGuardia.isEmpty() && idGuardia != null) {
			sql.WHERE("IDGUARDIA = " + idGuardia);
		}
		sql.WHERE("FECHABAJA IS NULL");
		// sql.SELECT("FECHASUSCRIPCION");
		return sql.toString();
	}

	public String checkInscripcionesRangoFecha(BusquedaInscripcionMod inscripciones, String idInstitucion,
			String fechaInicio, String fechaFin) {

		SQL sql = new SQL();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		sql.SELECT("*");
		sql.FROM("scs_inscripcionguardia");

//		if (fechaFin != null) {
//			sql.WHERE("trunc(FECHASUSCRIPCION) <= TO_DATE('" + fechaFin + "','dd/MM/yyyy')");//baja >(despues) final, borrar se controla en 1499
//		}
//		if (fechaInicio != null) {
//			sql.WHERE("trunc(FECHASUSCRIPCION) >= TO_DATE('" + fechaInicio + "','dd/MM/yyyy')");  //suscripcion <(antes) inicio, borrar se controla en 1495
//		}

		if (idInstitucion != null) {
			sql.WHERE("IDINSTITUCION =" + idInstitucion);
		}

		if (inscripciones.getIdpersona() != null) {
			sql.WHERE("IDPERSONA =" + inscripciones.getIdpersona());
		}

		if (inscripciones.getIdturno() != null) {
			sql.WHERE("IDTURNO =" + inscripciones.getIdturno());
		}

		if (inscripciones.getIdguardia() != null) {
			sql.WHERE("IDGUARDIA =" + inscripciones.getIdguardia());
		}
		if (fechaInicio != null) {
			// fechavalidacion <= fechaInicio && (fechabaja >= fechafin || fechabaja is
			// null)
			sql.WHERE("trunc(FECHAVALIDACION) <= TO_DATE('" + fechaInicio + "','dd/MM/yyyy')");
		}
		if (fechaFin != null) {
			sql.WHERE(
					"(trunc(FECHABAJA) >= TO_DATE('" + fechaFin + "','dd/MM/yyyy') OR FECHABAJA IS NULL) AND FECHADENEGACION IS NULL"); // nvl(fechabaja,
																															// 'fechafin')
		}
//		sql.WHERE("ROWNUM <= 200");
		return sql.toString();
	}

	public String getColegiadosInscritosGuardia(ScsInscripcionguardiaKey key) {
		// TODO Auto-generated method stub
		SQL sql = new SQL();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String today = formatter.format(new Date());

		sql.SELECT("COLE.NCOLEGIADO");
		sql.FROM("SCS_INSCRIPCIONGUARDIA INS");
		sql.LEFT_OUTER_JOIN(
				"CEN_COLEGIADO COLE ON COLE.IDPERSONA = INS.IDPERSONA AND COLE.IDINSTITUCION = INS.IDINSTITUCION");

		if (String.valueOf(key.getIdinstitucion()) != null) {
			sql.WHERE("INS.IDINSTITUCION = " + "'" + String.valueOf(key.getIdinstitucion()) + "'");
		}

		if (String.valueOf(key.getIdturno()) != null) {
			sql.WHERE("INS.IDTURNO = " + String.valueOf(key.getIdturno()));
		}

		if (String.valueOf(key.getIdguardia()) != null) {
			sql.WHERE("INS.IDGUARDIA = " + String.valueOf(key.getIdguardia()));
		}

		sql.WHERE("INS.FECHABAJA IS NULL");

		return sql.toString();
	}

	public String updateOrdenInscripciones(String idTurno, String idGuardia, String idPersona, String idInstitucion,
			String ordenBD) {
		SQL sql = new SQL();
		if (ordenBD != null) {
			sql.UPDATE("SCS_INSCRIPCIONGUARDIA");
			sql.SET("ORDEN = " + ordenBD);
			if (idTurno != null) {
				sql.WHERE("IDTURNO = " + idTurno);
			}
			if (idGuardia != null) {
				sql.WHERE("IDGUARDIA = " + idGuardia);
			}
			if (idPersona != null) {
				sql.WHERE("IDPERSONA = " + idPersona);
			}
			if (idTurno != null) {
				sql.WHERE("IDINSTITUCION = " + idInstitucion);
			}
		}
		return sql.toString();
	}
}
