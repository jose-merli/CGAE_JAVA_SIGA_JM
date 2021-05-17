package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
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
					"					"+fechaAnd + 
					"					AND (Ins.Fechabaja IS NULL\r\n" + 
					"					" +fechaOr+ 
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
}
