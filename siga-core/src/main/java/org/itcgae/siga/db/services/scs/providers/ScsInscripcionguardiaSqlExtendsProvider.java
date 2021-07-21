package org.itcgae.siga.db.services.scs.providers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.BusquedaInscripcionItem;
import org.itcgae.siga.DTOs.scs.InscripcionDatosEntradaDTO;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.mappers.ScsInscripcionguardiaSqlProvider;

public class ScsInscripcionguardiaSqlExtendsProvider extends ScsInscripcionguardiaSqlProvider{
	
	public String getColaGuardias(String idGuardia, String idTurno, String fecha,String ultimo,String ordenaciones, String idInstitucion, String idgrupoguardia) {
		SQL sql = new SQL();
		
		String fechaAnd = fecha != null && !fecha.equals("") ? "AND TRUNC(Ins.Fechavalidacion) <= NVL('"+fecha+"', Ins.Fechavalidacion)\r\n":"";
		String fechaOr = fecha != null && !fecha.equals("") ? "OR TRUNC(Ins.Fechabaja) > NVL('"+fecha+"', '01/01/1900')) THEN '1'\r\n":"";
		
		
		if(!UtilidadesString.esCadenaVacia(ultimo))						
			sql.SELECT("ROWNUM AS orden_cola,\r\n" + 
					"	consulta_total.*\r\n" + 
					"FROM (WITH tabla_nueva AS (\r\n" + // ESTA ES LA QUERY QUE SE USA CUANDO LA GUARDIA TIENE UN ÚLTIMOM ASIGNADO.
					"	SELECT\r\n" + 
					"		consulta2.*\r\n" + 
					"	FROM\r\n" + 
					"		(\r\n" + 
					"		SELECT\r\n" + 
					"			ROWNUM AS orden,\r\n" + 
					"			consulta.*\r\n" + 
					"		FROM\r\n" + 
					"			(\r\n" + 
					"			SELECT\r\n" + 
					"				(CASE\r\n" + 
					"					WHEN Ins.Fechavalidacion IS NOT NULL\r\n" + 
					"					" + fechaAnd + 
					"					AND (Ins.Fechabaja IS NULL\r\n" + 
					"					" + fechaOr+ 
					"					ELSE '0'\r\n" + 
					"				END) Activo,\r\n" + 
					"				Ins.Idinstitucion,\r\n" + 
					"				Ins.Idturno,\r\n" + 
					"				Ins.Idguardia,\r\n" + 
					"				Per.Idpersona,\r\n" + 
					"				Ins.fechasuscripcion AS Fechasuscripcion,\r\n" + 
					"				TO_CHAR(TRUNC(Ins.fechavalidacion), 'DD/MM/YYYY') AS Fechavalidacion,\r\n" + 
					"				TO_CHAR(TRUNC(Ins.fechabaja), 'DD/MM/YYYY') AS Fechabaja,\r\n" + 
					"				Per.Nifcif,\r\n" + 
					"				Per.Nombre,\r\n" + 
					"				Per.Apellidos1,\r\n" + 
					"				DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) apellidos2,\r\n" + 
					"				Per.Apellidos1 || DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) ALFABETICOAPELLIDOS,\r\n" + 
					"				DECODE(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) NUMEROCOLEGIADO,\r\n" + 
					"				Per.Fechanacimiento FECHANACIMIENTO,\r\n" + 
					"				Ins.Fechavalidacion AS ANTIGUEDADCOLA,\r\n" + 
					"				Gru.IDGRUPOGUARDIACOLEGIADO AS Idgrupoguardiacolegiado,\r\n" + 
					"				Gru.IDGRUPOGUARDIA AS Grupo,\r\n" + 
					"				Grg.NUMEROGRUPO AS numeroGrupo,\r\n" + 
					"				Gru.ORDEN AS Ordengrupo,\r\n" + 
					"				(\r\n" + 
					"				SELECT\r\n" + 
					"					COUNT(1) numero\r\n" + 
					"				FROM\r\n" + 
					"					scs_saltoscompensaciones salto\r\n" + 
					"				WHERE\r\n" + 
					"					salto.idinstitucion = gua.idinstitucion\r\n" + 
					"					AND salto.idturno = gua.IDTURNO\r\n" + 
					"					AND salto.idguardia = gua.idguardia\r\n" + 
					"					AND salto.saltoocompensacion = 'S'\r\n" + 
					"					AND salto.fechacumplimiento IS NULL\r\n" + 
					"					AND salto.idpersona = ins.IDPERSONA ) AS saltos,\r\n" + 
					"				(\r\n" + 
					"				SELECT\r\n" + 
					"					COUNT(1) numero\r\n" + 
					"				FROM\r\n" + 
					"					scs_saltoscompensaciones salto\r\n" + 
					"				WHERE\r\n" + 
					"					salto.idinstitucion = gua.idinstitucion\r\n" + 
					"					AND salto.idturno = gua.IDTURNO\r\n" + 
					"					AND salto.idguardia = gua.idguardia\r\n" + 
					"					AND salto.saltoocompensacion = 'C'\r\n" + 
					"					AND salto.fechacumplimiento IS NULL\r\n" + 
					"					AND salto.idpersona = ins.IDPERSONA ) AS compensaciones\r\n" + 
					"			FROM\r\n" + 
					"				scs_inscripcionguardia ins\r\n" + 
					"			INNER JOIN cen_persona per ON\r\n" + 
					"				per.IDPERSONA = ins.IDPERSONA\r\n" + 
					"			INNER JOIN scs_guardiasturno gua ON\r\n" + 
					"				gua.idturno = ins.idturno\r\n" + 
					"				AND gua.idguardia = ins.idguardia\r\n" + 
					"				AND gua.IDINSTITUCION = ins.IDINSTITUCION\r\n" + 
					"			LEFT JOIN scs_grupoguardiacolegiado gru ON\r\n" + 
					"				gru.IDINSTITUCION = ins.IDINSTITUCION\r\n" + 
					"				AND gru.IDTURNO = ins.IDTURNO\r\n" + 
					"				AND gru.IDGUARDIA = ins.IDGUARDIA\r\n" + 
					"				AND gru.IDPERSONA = per.IDPERSONA\r\n" + 
					"				AND gru.FECHASUSCRIPCION = ins.FECHASUSCRIPCION\r\n" + 
					"			LEFT JOIN scs_grupoguardia grg ON\r\n" + 
					"				grg.IDGRUPOGUARDIA = gru.IDGRUPOGUARDIA\r\n" + 
					"			INNER JOIN cen_colegiado col ON\r\n" + 
					"				col.idpersona = per.IDPERSONA\r\n" + 
					"				AND col.IDINSTITUCION = ins.IDINSTITUCION\r\n" + 
					"				AND col.IDPERSONA = ins.IDPERSONA\r\n" + 
					"			WHERE\r\n" + 
					"				Ins.Fechavalidacion IS NOT NULL\r\n" + 
					"				AND Gua.Idinstitucion = "+idInstitucion+"\r\n" + 
					"				AND Gua.Idturno = "+idTurno+"\r\n" + 
					"				AND Gua.Idguardia = "+idGuardia+"\r\n" + 
					"			ORDER BY\r\n" + 
									ordenaciones+
					"				NOMBRE,\r\n" + 
					"				Ins.FECHASUSCRIPCION,\r\n" + 
					"				Ins.Idpersona) consulta "
					+ "WHERE\r\n" + 
					"			activo = 1) consulta2),\r\n" + 
					"	tabla_nueva2 AS (\r\n" + 
					"	SELECT\r\n" + 
					"		consulta4.*\r\n" + 
					"	FROM\r\n" + 
					"		(\r\n" + 
					"		SELECT\r\n" + 
					"			ROWNUM AS orden,\r\n" + 
					"			consulta3.*\r\n" + 
					"		FROM\r\n" + 
					"			(\r\n" + 
					"			SELECT\r\n" + 
					"				(CASE\r\n" + 
					"					WHEN Ins.Fechavalidacion IS NOT NULL\r\n" + 
					"					"+fechaAnd + 
					"					AND (Ins.Fechabaja IS NULL\r\n" + 
					"					"+fechaOr + 
					"					ELSE '0'\r\n" + 
					"				END) Activo,\r\n" + 
					"				Ins.Idinstitucion,\r\n" + 
					"				Ins.Idturno,\r\n" + 
					"				Ins.Idguardia,\r\n" + 
					"				Per.Idpersona,\r\n" + 
					"				Ins.fechasuscripcion AS Fechasuscripcion,\r\n" + 
					"				TO_CHAR(TRUNC(Ins.fechavalidacion), 'DD/MM/YYYY') AS Fechavalidacion,\r\n" + 
					"				TO_CHAR(TRUNC(Ins.fechabaja), 'DD/MM/YYYY') AS Fechabaja,\r\n" + 
					"				Per.Nifcif,\r\n" + 
					"				Per.Nombre,\r\n" + 
					"				Per.Apellidos1,\r\n" + 
					"				DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) apellidos2,\r\n" + 
					"				Per.Apellidos1 || DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) ALFABETICOAPELLIDOS,\r\n" + 
					"				DECODE(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) NUMEROCOLEGIADO,\r\n" + 
					"				Per.Fechanacimiento FECHANACIMIENTO,\r\n" + 
					"				Ins.Fechavalidacion AS ANTIGUEDADCOLA,\r\n" + 
					"				Gru.IDGRUPOGUARDIACOLEGIADO AS Idgrupoguardiacolegiado,\r\n" + 
					"				Gru.IDGRUPOGUARDIA AS Grupo,\r\n" + 
					"				Grg.NUMEROGRUPO AS numeroGrupo,\r\n" + 
					"				Gru.ORDEN AS Ordengrupo,\r\n" + 
					"				Gru.IDGRUPOGUARDIA AS IDGRUPOGUARDIA,\r\n" + 
					"				(\r\n" + 
					"				SELECT\r\n" + 
					"					COUNT(1) numero\r\n" + 
					"				FROM\r\n" + 
					"					scs_saltoscompensaciones salto\r\n" + 
					"				WHERE\r\n" + 
					"					salto.idinstitucion = gua.idinstitucion\r\n" + 
					"					AND salto.idturno = gua.IDTURNO\r\n" + 
					"					AND salto.idguardia = gua.idguardia\r\n" + 
					"					AND salto.saltoocompensacion = 'S'\r\n" + 
					"					AND salto.fechacumplimiento IS NULL\r\n" + 
					"					AND salto.idpersona = ins.IDPERSONA ) AS saltos,\r\n" + 
					"				(\r\n" + 
					"				SELECT\r\n" + 
					"					COUNT(1) numero\r\n" + 
					"				FROM\r\n" + 
					"					scs_saltoscompensaciones salto\r\n" + 
					"				WHERE\r\n" + 
					"					salto.idinstitucion = gua.idinstitucion\r\n" + 
					"					AND salto.idturno = gua.IDTURNO\r\n" + 
					"					AND salto.idguardia = gua.idguardia\r\n" + 
					"					AND salto.saltoocompensacion = 'C'\r\n" + 
					"					AND salto.fechacumplimiento IS NULL\r\n" + 
					"					AND salto.idpersona = ins.IDPERSONA ) AS compensaciones\r\n" + 
					"			FROM\r\n" + 
					"				scs_inscripcionguardia ins\r\n" + 
					"			INNER JOIN cen_persona per ON\r\n" + 
					"				per.IDPERSONA = ins.IDPERSONA\r\n" + 
					"			INNER JOIN scs_guardiasturno gua ON\r\n" + 
					"				gua.idturno = ins.idturno\r\n" + 
					"				AND gua.idguardia = ins.idguardia\r\n" + 
					"				AND gua.IDINSTITUCION = ins.IDINSTITUCION\r\n" + 
					"			LEFT JOIN scs_grupoguardiacolegiado gru ON\r\n" + 
					"				gru.IDINSTITUCION = ins.IDINSTITUCION\r\n" + 
					"				AND gru.IDTURNO = ins.IDTURNO\r\n" + 
					"				AND gru.IDGUARDIA = ins.IDGUARDIA\r\n" + 
					"				AND gru.IDPERSONA = per.IDPERSONA\r\n" + 
					"				AND gru.FECHASUSCRIPCION = ins.FECHASUSCRIPCION\r\n" + 
					"			LEFT JOIN scs_grupoguardia grg ON\r\n" + 
					"				grg.IDGRUPOGUARDIA = gru.IDGRUPOGUARDIA\r\n" + 
					"			INNER JOIN cen_colegiado col ON\r\n" + 
					"				col.idpersona = per.IDPERSONA\r\n" + 
					"				AND col.IDINSTITUCION = ins.IDINSTITUCION\r\n" + 
					"				AND col.IDPERSONA = ins.IDPERSONA\r\n" + 
					"			WHERE\r\n" + 
					"				Ins.Fechavalidacion IS NOT NULL\r\n" + 
					"				AND Gua.Idinstitucion = "+idInstitucion+"\r\n" + 
					"				AND Gua.Idturno = "+idTurno+"\r\n" + 
					"				AND Gua.Idguardia = "+idGuardia+"\r\n" + 
					"			ORDER BY\r\n" + 
					"				"+ordenaciones+"\r\n" + 
					"				NOMBRE,\r\n" + 
					"				Ins.FECHASUSCRIPCION,\r\n" + 
					"				Ins.Idpersona) consulta3\r\n" + 
					"		WHERE\r\n" + 
					"			activo = 1) consulta4\r\n" + 				
					"	"+ultimo+" "+idgrupoguardia+")\r\n" + 
					"	SELECT\r\n" + 
					"		*\r\n" + 
					"	FROM\r\n" + 
					"		(\r\n" + 
					"		SELECT\r\n" + 
					"			tabla_nueva.*\r\n" + 
					"		FROM\r\n" + 
					"			tabla_nueva,\r\n" + 
					"			tabla_nueva2\r\n" + 
					"		WHERE\r\n" + 
					"			tabla_nueva.orden>tabla_nueva2.orden\r\n" + 
					"		ORDER BY\r\n" + 
					"			tabla_nueva.orden ASC)\r\n" + 
					"UNION ALL\r\n" + 
					"	SELECT\r\n" + 
					"		*\r\n" + 
					"	FROM\r\n" + 
					"		(\r\n" + 
					"		SELECT\r\n" + 
					"			tabla_nueva.*\r\n" + 
					"		FROM\r\n" + 
					"			tabla_nueva,\r\n" + 
					"			tabla_nueva2\r\n" + 
					"		WHERE\r\n" + 
					"			tabla_nueva.orden <= tabla_nueva2.orden\r\n" + 
					"		ORDER BY\r\n" + 
					"			tabla_nueva.orden ASC) ) consulta_total");
		else 
			sql.SELECT("ROWNUM AS orden_cola, consulta.* FROM (SELECT(CASE\r\n" + 
					"                                 WHEN Ins.Fechavalidacion IS NOT NULL\r\n" + 
					"                                 "+fechaAnd+ 
					"                                 AND (Ins.Fechabaja IS NULL\r\n" + 
					"                                 "+fechaOr + 
					"                                 ELSE '0'\r\n" + 
					"                          END) Activo,\r\n" + 
					"                          Ins.Idinstitucion,\r\n" + 
					"                          Ins.Idturno,\r\n" + 
					"                          Ins.Idguardia,\r\n" + 
					"                          Per.Idpersona,\r\n" + 
					"                          Ins.fechasuscripcion AS Fechasuscripcion,\r\n" + 
					"                          TO_CHAR(TRUNC(Ins.fechavalidacion), 'DD/MM/YYYY') AS Fechavalidacion,\r\n" + 
					"                          TO_CHAR(TRUNC(Ins.fechabaja), 'DD/MM/YYYY') AS Fechabaja,\r\n" + 
					"                          Per.Nifcif,\r\n" + 
					"                          Per.Nombre,\r\n" + 
					"                          Per.Apellidos1,\r\n" + 
					"                          DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) apellidos2,\r\n" + 
					"                          Per.Apellidos1 || DECODE(Per.Apellidos2, NULL, '', ' ' || Per.Apellidos2) ALFABETICOAPELLIDOS,\r\n" + 
					"                          DECODE(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) NUMEROCOLEGIADO,\r\n" + 
					"                          Per.Fechanacimiento FECHANACIMIENTO,\r\n" + 
					"                          Ins.Fechavalidacion AS ANTIGUEDADCOLA,\r\n" + 
					"                          Gru.IDGRUPOGUARDIACOLEGIADO AS Idgrupoguardiacolegiado,\r\n" + 
					"                          Gru.IDGRUPOGUARDIA AS Grupo,\r\n" + 
					"                          Grg.NUMEROGRUPO AS numeroGrupo,\r\n" + 
					"                          Gru.ORDEN AS Ordengrupo,\r\n" + 
					"                          (\r\n" + 
					"                          SELECT\r\n" + 
					"                                 COUNT(1) numero\r\n" + 
					"                          FROM\r\n" + 
					"                                 scs_saltoscompensaciones salto\r\n" + 
					"                          WHERE\r\n" + 
					"                                 salto.idinstitucion = gua.idinstitucion\r\n" + 
					"                                 AND salto.idturno = gua.IDTURNO\r\n" + 
					"                                 AND salto.idguardia = gua.idguardia\r\n" + 
					"                                 AND salto.saltoocompensacion = 'S'\r\n" + 
					"                                 AND salto.fechacumplimiento IS NULL\r\n" + 
					"                                 AND salto.idpersona = ins.IDPERSONA ) AS saltos,\r\n" + 
					"                          (\r\n" + 
					"                          SELECT\r\n" + 
					"                                 COUNT(1) numero\r\n" + 
					"                          FROM\r\n" + 
					"                                 scs_saltoscompensaciones salto\r\n" + 
					"                          WHERE\r\n" + 
					"                                 salto.idinstitucion = gua.idinstitucion\r\n" + 
					"                                 AND salto.idturno = gua.IDTURNO\r\n" + 
					"                                 AND salto.idguardia = gua.idguardia\r\n" + 
					"                                 AND salto.saltoocompensacion = 'C'\r\n" + 
					"                                 AND salto.fechacumplimiento IS NULL\r\n" + 
					"                                 AND salto.idpersona = ins.IDPERSONA ) AS compensaciones\r\n" + 
					"                    FROM\r\n" + 
					"                          scs_inscripcionguardia ins\r\n" + 
					"                    INNER JOIN cen_persona per ON\r\n" + 
					"                          per.IDPERSONA = ins.IDPERSONA\r\n" + 
					"                    INNER JOIN scs_guardiasturno gua ON\r\n" + 
					"                          gua.idturno = ins.idturno\r\n" + 
					"                          AND gua.idguardia = ins.idguardia\r\n" + 
					"                          AND gua.IDINSTITUCION = ins.IDINSTITUCION\r\n" + 
					"                    LEFT JOIN scs_grupoguardiacolegiado gru ON\r\n" + 
					"                          gru.IDINSTITUCION = ins.IDINSTITUCION\r\n" + 
					"                          AND gru.IDTURNO = ins.IDTURNO\r\n" + 
					"                          AND gru.IDGUARDIA = ins.IDGUARDIA\r\n" + 
					"                          AND gru.IDPERSONA = per.IDPERSONA\r\n" + 
					"                          AND gru.FECHASUSCRIPCION = ins.FECHASUSCRIPCION\r\n" + 
					"                    LEFT JOIN scs_grupoguardia grg ON\r\n" + 
					"                          grg.IDGRUPOGUARDIA = gru.IDGRUPOGUARDIA\r\n" + 
					"                    INNER JOIN cen_colegiado col ON\r\n" + 
					"                          col.idpersona = per.IDPERSONA\r\n" + 
					"                          AND col.IDINSTITUCION = ins.IDINSTITUCION\r\n" + 
					"                          AND col.IDPERSONA = ins.IDPERSONA\r\n" + 
					"                    WHERE\r\n" + 
					"                          Ins.Fechavalidacion IS NOT NULL\r\n" + 
					"                          AND Gua.Idinstitucion = "+idInstitucion+"\r\n" + 
					"                          AND Gua.Idturno = "+idTurno+"\r\n" + 
					"                          AND Gua.Idguardia = "+idGuardia+"\r\n" + 
					"                    ORDER BY\r\n" + 
					"							"+ordenaciones+"\r\n" + 
					"                          NOMBRE,\r\n" + 
					"                          Ins.FECHASUSCRIPCION,\r\n" + 
					"                          Ins.Idpersona) consulta where activo = 1");
		return sql.toString();
	}

	public String searchGrupoGuardia(Short idInstitucion, String idGuardia) {

		SQL sql = new SQL();

		sql.SELECT("SCS_GRUPOGUARDIA.NUMEROGRUPO");
		sql.SELECT("SCS_GRUPOGUARDIA.IDGUARDIA");
		sql.SELECT("SCS_GRUPOGUARDIACOLEGIADO.ORDEN");
		sql.SELECT("SCS_GRUPOGUARDIACOLEGIADO.IDPERSONA");

		sql.FROM("SCS_GRUPOGUARDIA");
		sql.INNER_JOIN("SCS_GRUPOGUARDIACOLEGIADO gcolegiado on SCS_GRUPOGUARDIA.IDINSTITUCION =  SCS_GRUPOGUARDIACOLEGIADO.IDINSTITUCION"
				+ "AND SCS_GRUPOGUARDIA.IDGRUPOGUARDIA =  SCS_GRUPOGUARDIACOLEGIADO.IDGRUPOGUARDIA");

		sql.WHERE("SCS_GRUPOGUARDIA.idinstitucion = '" + idInstitucion + "'");
		sql.WHERE("SCS_GRUPOGUARDIA.IDGUARDIA IN (" + idGuardia + ")");
		
		sql.ORDER_BY("SCS_GRUPOGUARDIA.NUMEROCOLEGIADO");


		return sql.toString();
	}
	

	

	public String listadoInscripciones(InscripcionDatosEntradaDTO inscripciones, String idInstitucion) {
		
		SQL sql = new SQL();
		
		SQL subQuerysql = new SQL();
		subQuerysql.SELECT("MAX(it2.fechasuscripcion)");
		subQuerysql.FROM("scs_inscripcionguardia it2");
		subQuerysql.WHERE("it2.idinstitucion = ins.idinstitucion");
		subQuerysql.WHERE("it2.idturno = ins.idturno");
		subQuerysql.WHERE("it2.idpersona = ins.idpersona");
		
		SQL subQuerysql2 = new SQL();
		subQuerysql2.SELECT("guar.nombre");
		subQuerysql2.FROM("scs_guardiasturno guar2");
		subQuerysql2.WHERE("guar2.idinstitucion = ins.idinstitucion");
		subQuerysql2.WHERE("guar2.idturno = ins.idturno");
		subQuerysql2.WHERE("guar2.idguardia = guar.idguardia");
		
		sql.SELECT("( CASE WHEN ins.fechadenegacion IS NOT NULL"
                + "     AND ins.fechabaja IS NOT NULL"
                + "     AND ins.fechasolicitudbaja IS NOT NULL"
                + "     AND ins.fechavalidacion IS NOT NULL THEN '4' /*Denegada*/ "
                + "WHEN ins.fechadenegacion IS NULL AND ins.fechabaja IS NOT NULL"
                + "     AND ins.fechasolicitudbaja IS NOT NULL"
                + "     AND ins.fechavalidacion IS NOT NULL THEN '3' /*Baja*/"
                + "WHEN ins.fechadenegacion IS NULL AND ins.fechabaja IS NULL"
                + "     AND ins.fechasolicitudbaja IS NOT NULL"
                + "     AND ins.fechavalidacion IS NOT NULL THEN '2' /*Pendiente de Baja*/"
                + "WHEN ins.fechadenegacion IS NULL AND ins.fechabaja IS NULL"
                + "     AND ins.fechasolicitudbaja IS NULL"
                + "     AND ins.fechavalidacion IS NOT NULL THEN '1' /*Alta*/"
                + "ELSE '0' /*Pendiente de Alta*/" + " END) estado");
		
		sql.SELECT("tur.nombre");
		sql.SELECT("tur.abreviatura abreviatura");
		sql.SELECT("tur.validarinscripciones");
		
		sql.SELECT("guar.nombre NOMBREGUARDIA");
		sql.SELECT("guar.idguardia IDGUARDIA");
		
		sql.SELECT("per.apellidos1 || DECODE(per.apellidos2,NULL,'',' ' || per.apellidos2) || ', ' || per.nombre apellidosnombre");
		sql.SELECT("DECODE(col.comunitario,'1',col.ncomunitario,col.ncolegiado) ncolegiado");
		sql.SELECT("per.nombre");
		sql.SELECT("per.apellidos1");
		sql.SELECT("per.apellidos2");
		sql.SELECT("ins.idinstitucion");
		sql.SELECT("ins.idpersona");
		sql.SELECT("ins.idturno");
		sql.SELECT("ins.fechasuscripcion");
		sql.SELECT("ins.observacionessuscripcion");
		sql.SELECT("ins.fechavalidacion");
		sql.SELECT("ins.observacionesvalidacion");
		sql.SELECT("ins.fechasolicitudbaja");
		sql.SELECT("ins.observacionesbaja");
		sql.SELECT("ins.fechabaja");
		sql.SELECT("ins.observacionesvalbaja");
		sql.SELECT("ins.fechadenegacion");
		sql.SELECT("ins.observacionesdenegacion");
		sql.SELECT("DECODE(col.comunitario,'1',col.ncomunitario,col.ncolegiado) ncolegiado");
		sql.SELECT("TO_CHAR(nvl(ins.fechadenegacion,ins.fechavalidacion),'dd/mm/yyyy') fechavalidacion");
		sql.SELECT("TO_CHAR(nvl(ins.fechadenegacion,ins.fechabaja),'dd/mm/yyyy') fechabaja");

        sql.FROM("scs_inscripcionguardia ins");
        sql.INNER_JOIN("cen_colegiado col ON col.idpersona = ins.idpersona AND col.idinstitucion = ins.idinstitucion");
        sql.INNER_JOIN("cen_persona per ON per.idpersona = col.idpersona");
        sql.INNER_JOIN("scs_turno tur ON tur.idturno = ins.idturno AND tur.idinstitucion = ins.idinstitucion");
        sql.INNER_JOIN("scs_guardiasturno guar ON guar.idturno = tur.idturno AND guar.idinstitucion = ins.idinstitucion");

 
        if(idInstitucion != null) {
        	sql.WHERE("ins.idinstitucion = " + idInstitucion);
        }
        
        if(inscripciones.getIdTurno() != null) {
        	sql.WHERE("ins.idturno in ("+ inscripciones.getIdTurno()+")");
        }
        
        if(inscripciones.getIdEstado() != null) {
            String condestados = "(";
            String[] estados = inscripciones.getIdEstado().split(",");
            for(int i = 0; i< estados.length; i++) {
                if(i>0) condestados+=" or ";
                // Pendiente de alta
                if(estados[i].equals("0")) {
                    condestados+=" (ins.fechavalidacion is null and ins.fechadenegacion is null)" ;
                }
                // Alta
                else if(estados[i].equals("1")) {
                    condestados+=" (ins.fechadenegacion IS NULL AND ins.fechabaja IS NULL" + 
                            " AND ins.fechasolicitudbaja IS NULL AND ins.fechavalidacion IS NOT NULL)" ;
                }
                // Pendiente de Baja
                else if(estados[i].equals("2")) {
                    condestados+=" (ins.fechadenegacion IS NULL AND ins.fechabaja IS NULL" + 
                            " AND ins.fechasolicitudbaja IS NOT NULL AND ins.fechavalidacion IS NOT NULL)" ;
                }
                // Baja
                else if(estados[i].equals("3")) {
                    condestados+=" (ins.fechadenegacion IS NULL AND ins.fechabaja IS NOT NULL"
                            + " AND ins.fechasolicitudbaja IS NOT NULL AND ins.fechavalidacion IS NOT NULL )" ;
                }
                // Denegada
                else if(estados[i].equals("4")) {
                    condestados+=" (ins.fechadenegacion is not null)" ;
                }
            }
            condestados+=")";
            sql.WHERE(condestados);
        }
        
        sql.WHERE("ins.fechasuscripcion = (" + subQuerysql + ")");
        
        sql.WHERE("guar.nombre = (" + subQuerysql2 + ")");
        
        sql.WHERE("fechadenegacion IS NULL");
        sql.WHERE("fechavalidacion IS NOT NULL");
        
        if(inscripciones.getFechaDesde() != null) {
        	sql.WHERE("ins.fechasuscripcion >= TO_DATE('" + inscripciones.getFechaDesde() + "','DD/MM/RRRR')");
        }
        
        if(inscripciones.getFechaHasta() != null) {
        	 sql.WHERE("ins.fechasuscripcion <= TO_DATE('" + inscripciones.getFechaHasta() + "','DD/MM/RRRR')");
        }
        
        sql.WHERE("ROWNUM <= 200");
       
        sql.ORDER_BY("tur.nombre");
		
		return sql.toString();

	}

	public String searchGrupo(String grupo, Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("SCS_GRUPOGUARDIA.NUMEROGRUPO");
		sql.SELECT("SCS_GRUPOGUARDIA.IDGUARDIA");
		sql.SELECT("SCS_GRUPOGUARDIACOLEGIADO.ORDEN");
		sql.SELECT("SCS_GRUPOGUARDIACOLEGIADO.IDPERSONA");

		sql.FROM("SCS_GRUPOGUARDIA");
		sql.INNER_JOIN("SCS_GRUPOGUARDIACOLEGIADO gcolegiado on SCS_GRUPOGUARDIA.IDINSTITUCION =  SCS_GRUPOGUARDIACOLEGIADO.IDINSTITUCION"
				+ "AND SCS_GRUPOGUARDIA.IDGRUPOGUARDIA =  SCS_GRUPOGUARDIACOLEGIADO.IDGRUPOGUARDIA");

		sql.WHERE("SCS_GRUPOGUARDIA.idinstitucion = '" + idInstitucion + "'");
		//sql.WHERE("SCS_GRUPOGUARDIA.idPersona = '" + idPersona + "'");
		sql.WHERE("SCS_GRUPOGUARDIA.NUMEROGRUPO = '" + grupo + "'");


		return sql.toString();
	}
	
	public String comboGuardiasInscritoLetrado(Short idInstitucion, String idPersona, String idTurno) {
		SQL sql = new SQL();
		sql.SELECT("gt.IDGUARDIA", "gt.NOMBRE");
		sql.FROM("scs_guardiasturno gt");
		sql.INNER_JOIN("scs_inscripcionguardia ig on gt.idinstitucion = ig.idinstitucion and gt.idguardia = ig.idguardia and gt.idturno = ig.idturno");
		sql.WHERE("gt.idinstitucion = "+idInstitucion,
				"ig.fechabaja is null",
				"ig.fechavalidacion is not null",
				"ig.idpersona = '"+idPersona+"'",
				"ig.idturno = '"+idTurno+"'");
		return sql.toString();
	}
	
	public String validarInscripciones(BusquedaInscripcionItem inscripciones, String idInstitucion) {
		SQL sql = new SQL();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(new Date());
        
		sql.UPDATE("scs_inscripcionguardia");
		
		if (idInstitucion != null) {
			sql.SET("IDINSTITUCION = "+idInstitucion);
		}
		if (inscripciones.getIdpersona() != null) {
			sql.SET("IDPERSONA = "+inscripciones.getIdpersona());
		}
		if (inscripciones.getIdTurno() != null) {
			sql.SET("IDTURNO = "+inscripciones.getIdTurno());
		}
		if (inscripciones.getIdguardia() != null) {
			sql.SET("IDGUARDIA = "+inscripciones.getIdguardia());
		}
		if (inscripciones.getFechasolicitud() != null) {
			sql.SET("FECHASUSCRIPCION = "+inscripciones.getFechasolicitud());
		}
		if (today != null) {
			sql.SET("FECHAMODIFICACION = "+today);
		}
		
		sql.SET("USUMODIFICACION = "+0);
		
		if (inscripciones.getFechabaja() != null) {
			sql.SET("FECHABAJA = "+inscripciones.getFechabaja());
		}
		if (inscripciones.getObservacionessolicitud() != null) {
			sql.SET("OBSERVACIONESSUSCRIPCION = "+inscripciones.getObservacionessolicitud());
		}
		if (inscripciones.getObservacionesbaja() != null) {
			sql.SET("OBSERVACIONESBAJA = "+inscripciones.getObservacionesbaja());
		}
		
		if (inscripciones.getFechasolicitudbaja() != null) {
			sql.SET("FECHASOLICITUDBAJA = "+inscripciones.getFechasolicitudbaja());
		}
		
		if (inscripciones.getFechavalidacion() != null) {
			sql.SET("FECHAVALIDACION = "+inscripciones.getFechavalidacion());
		}
		if (inscripciones.getObservacionesvalidacion() != null) {
			sql.SET("OBSERVACIONESVALIDACION = "+inscripciones.getObservacionesvalidacion());
		}
		if (inscripciones.getFechadenegacion() != null) {
			sql.SET("FECHADENEGACION = "+inscripciones.getFechadenegacion());
		}
		if (inscripciones.getObservacionesdenegacion() != null) {
			sql.SET("OBSERVACIONESDENEGACION = "+inscripciones.getObservacionesdenegacion());
		}
		if (inscripciones.getObservacionesvalbaja() != null) {
			sql.SET("OBSERVACIONESVALBAJA = "+inscripciones.getObservacionesvalbaja());
		}		
		
		return sql.toString();
	}
	
	public String denegarInscripciones(BusquedaInscripcionItem inscripciones, String idInstitucion) {
		SQL sql = new SQL();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(new Date());
        
		sql.UPDATE("scs_inscripcionguardia");
		
		if (idInstitucion != null) {
			sql.SET("IDINSTITUCION = "+idInstitucion);
		}
		if (inscripciones.getIdpersona() != null) {
			sql.SET("IDPERSONA = "+inscripciones.getIdpersona());
		}
		if (inscripciones.getIdTurno() != null) {
			sql.SET("IDTURNO = "+inscripciones.getIdTurno());
		}
		if (inscripciones.getIdguardia() != null) {
			sql.SET("IDGUARDIA = "+inscripciones.getIdguardia());
		}
		if (inscripciones.getFechasolicitud() != null) {
			sql.SET("FECHASUSCRIPCION = "+inscripciones.getFechasolicitud());
		}
		if (today != null) {
			sql.SET("FECHAMODIFICACION = "+today);
		}
		
		sql.SET("USUMODIFICACION = "+0);
		
		if (inscripciones.getFechabaja() != null) {
			sql.SET("FECHABAJA = "+inscripciones.getFechabaja());
		}
		if (inscripciones.getObservacionessolicitud() != null) {
			sql.SET("OBSERVACIONESSUSCRIPCION = "+inscripciones.getObservacionessolicitud());
		}
		if (inscripciones.getObservacionesbaja() != null) {
			sql.SET("OBSERVACIONESBAJA = "+inscripciones.getObservacionesbaja());
		}
		
		if (inscripciones.getFechasolicitudbaja() != null) {
			sql.SET("FECHASOLICITUDBAJA = "+inscripciones.getFechasolicitudbaja());
		}
		
		if (inscripciones.getFechavalidacion() != null) {
			sql.SET("FECHAVALIDACION = "+inscripciones.getFechavalidacion());
		}
		if (inscripciones.getObservacionesvalidacion() != null) {
			sql.SET("OBSERVACIONESVALIDACION = "+inscripciones.getObservacionesvalidacion());
		}
		if (inscripciones.getFechadenegacion() != null) {
			sql.SET("FECHADENEGACION = "+inscripciones.getFechadenegacion());
		}
		if (inscripciones.getObservacionesdenegacion() != null) {
			sql.SET("OBSERVACIONESDENEGACION = "+inscripciones.getObservacionesdenegacion());
		}
		if (inscripciones.getObservacionesvalbaja() != null) {
			sql.SET("OBSERVACIONESVALBAJA = "+inscripciones.getObservacionesvalbaja());
		}		
		
		return sql.toString();
	}
	
}
