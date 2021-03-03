package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.ScsSaltoscompensaciones;

public class ScsSaltoscompensacionesSqlExtendsProvider extends ScsSaltoscompensaciones {

	public String searchSaltosCompensaciones(SaltoCompGuardiaItem salto, String idInstitucion) {
		SQL sql = new SQL();
		String hist = "";
		if(salto.isHistorico()) 
			hist = "WHERE FECHAUSO IS NOT NULL OR FECHA_ANULACION IS NOT NULL";
		else
			hist = "WHERE FECHAUSO IS NULL";
		
		if(!UtilidadesString.esCadenaVacia(salto.getColegiadoGrupo()))
			salto.setColegiadoGrupo("");
		else 
			salto.setColegiadoGrupo(" AND ( (COLEGIADO_GRUPO = "+salto.getColegiadoGrupo()+"\r\n" + 
					"	AND GRUPO IS NULL)\r\n" + 
					"	OR (GRUPO IN (\r\n" + 
					"	SELECT\r\n" + 
					"		IDGRUPOGUARDIA\r\n" + 
					"	FROM\r\n" + 
					"		(\r\n" + 
					"		SELECT\r\n" + 
					"			ggc.IDGRUPOGUARDIA,\r\n" + 
					"			DECODE(col.COMUNITARIO, '1', col.NCOMUNITARIO, col.NCOLEGIADO) AS COLEGIADO\r\n" + 
					"		FROM\r\n" + 
					"			cen_persona perso,\r\n" + 
					"			cen_colegiado col,\r\n" + 
					"			scs_grupoguardiacolegiado ggc\r\n" + 
					"		WHERE\r\n" + 
					"			perso.idpersona = ggc.idpersona\r\n" + 
					"			AND col.idpersona = perso.idpersona\r\n" + 
					"			AND col.idinstitucion = ggc.idinstitucion\r\n" + 
					"			AND ggc.idgrupoguardia IN (\r\n" + 
					"			SELECT\r\n" + 
					"				IDGRUPOGUARDIA\r\n" + 
					"			FROM\r\n" + 
					"				SCS_GRUPOGUARDIA ))\r\n" + 
					"	WHERE\r\n" + 
					"		COLEGIADO = "+salto.getColegiadoGrupo()+") ) ) ");
		
		
		
		sql.SELECT("*\r\n" + 
				"FROM\r\n" + 
				"	(\r\n" + 
				"	SELECT\r\n" + 
				"		SCS_SALTOSCOMPENSACIONES.IDINSTITUCION,\r\n" + 
				"		NULL AS GRUPO,\r\n" + 
				"		SCS_TURNO.IDTURNO,\r\n" + 
				"		SCS_TURNO.NOMBRE AS NOMBRE_TURNO,\r\n" + 
				"		SCS_GUARDIASTURNO.IDGUARDIA,\r\n" + 
				"		SCS_GUARDIASTURNO.NOMBRE AS NOMBREGUARDIA,\r\n" + 
				"		SCS_SALTOSCOMPENSACIONES.IDSALTOSTURNO,\r\n" + 
				"		DECODE(CEN_COLEGIADO.COMUNITARIO, '1', CEN_COLEGIADO.NCOMUNITARIO, CEN_COLEGIADO.NCOLEGIADO) AS COLEGIADO_GRUPO,\r\n" + 
				"		CEN_PERSONA.NOMBRE || ' ' || CEN_PERSONA.APELLIDOS1 || ' ' || CEN_PERSONA.APELLIDOS2 AS LETRADO,\r\n" + 
				"		SCS_SALTOSCOMPENSACIONES.SALTOOCOMPENSACION,\r\n" + 
				"		SCS_SALTOSCOMPENSACIONES.FECHA,\r\n" + 
				"		SCS_SALTOSCOMPENSACIONES.MOTIVOS AS MOTIVO,\r\n" + 
				"		SCS_SALTOSCOMPENSACIONES.FECHACUMPLIMIENTO AS FECHAUSO\r\n" + 
				"	FROM\r\n" + 
				"		SCS_SALTOSCOMPENSACIONES\r\n" + 
				"	LEFT JOIN SCS_TURNO ON\r\n" + 
				"		SCS_TURNO.IDINSTITUCION = SCS_SALTOSCOMPENSACIONES.IDINSTITUCION\r\n" + 
				"		AND SCS_TURNO.IDTURNO = SCS_SALTOSCOMPENSACIONES.IDTURNO\r\n" + 
				"	LEFT JOIN SCS_GUARDIASTURNO ON\r\n" + 
				"		SCS_GUARDIASTURNO.IDINSTITUCION = SCS_SALTOSCOMPENSACIONES.IDINSTITUCION\r\n" + 
				"		AND SCS_GUARDIASTURNO.IDTURNO = SCS_SALTOSCOMPENSACIONES.IDTURNO\r\n" + 
				"		AND SCS_GUARDIASTURNO.IDGUARDIA = SCS_SALTOSCOMPENSACIONES.IDGUARDIA\r\n" + 
				"	JOIN CEN_PERSONA ON\r\n" + 
				"		CEN_PERSONA.IDPERSONA = SCS_SALTOSCOMPENSACIONES.IDPERSONA\r\n" + 
				"	JOIN CEN_COLEGIADO ON\r\n" + 
				"		CEN_COLEGIADO.IDPERSONA = SCS_SALTOSCOMPENSACIONES.IDPERSONA\r\n" + 
				"		AND CEN_COLEGIADO.IDINSTITUCION = SCS_SALTOSCOMPENSACIONES.IDINSTITUCION\r\n" + 
				"UNION ALL\r\n" + 
				"	SELECT\r\n" + 
				"		SCS_SALTOCOMPENSACIONGRUPO.IDINSTITUCION,\r\n" + 
				"		SCS_GRUPOGUARDIA.IDGRUPOGUARDIA AS GRUPO,\r\n" + 
				"		SCS_TURNO.IDTURNO,\r\n" + 
				"		SCS_TURNO.NOMBRE AS NOMBRE_TURNO,\r\n" + 
				"		SCS_GUARDIASTURNO.IDGUARDIA,\r\n" + 
				"		SCS_GUARDIASTURNO.NOMBRE AS NOMBRE_GUARDIA,\r\n" + 
				"		SCS_SALTOCOMPENSACIONGRUPO.IDSALTOCOMPENSACIONGRUPO,\r\n" + 
				"		NULL AS COLEGIADO_GRUPO,\r\n" + 
				"		NULL AS LETRADO,\r\n" + 
				"		SCS_SALTOCOMPENSACIONGRUPO.SALTOOCOMPENSACION,\r\n" + 
				"		SCS_SALTOCOMPENSACIONGRUPO.FECHA,\r\n" + 
				"		SCS_SALTOCOMPENSACIONGRUPO.MOTIVO,\r\n" + 
				"		SCS_SALTOCOMPENSACIONGRUPO.FECHACUMPLIMIENTO AS FECHAUSO\r\n" + 
				"	FROM\r\n" + 
				"		SCS_SALTOCOMPENSACIONGRUPO\r\n" + 
				"	LEFT JOIN SCS_GRUPOGUARDIA ON\r\n" +
				"		SCS_GRUPOGUARDIA.IDGRUPOGUARDIA = SCS_SALTOCOMPENSACIONGRUPO.IDGRUPOGUARDIA\r\n" + 
				"	JOIN SCS_GUARDIASTURNO ON\r\n" + 
				"		SCS_GUARDIASTURNO.IDINSTITUCION = SCS_GRUPOGUARDIA.IDINSTITUCION\r\n" + 
				"		AND SCS_GUARDIASTURNO.IDTURNO = SCS_GRUPOGUARDIA.IDTURNO\r\n" + 
				"		AND SCS_GUARDIASTURNO.IDGUARDIA = SCS_GRUPOGUARDIA.IDGUARDIA\r\n" + 
				"	JOIN SCS_TURNO ON\r\n" + 
				"		SCS_TURNO.IDINSTITUCION = SCS_GUARDIASTURNO.IDINSTITUCION\r\n" + 
				"		AND SCS_TURNO.IDTURNO = SCS_GUARDIASTURNO.IDTURNO ) CONSULTA "
				+ hist + " " +salto.getColegiadoGrupo()
						+ " AND IDINSTITUCION ="+idInstitucion+"\r\n" + 
						"	AND IDTURNO = "+salto.getIdTurno()+"\r\n" + 
						"	AND IDGUARDIA = "+salto.getIdGuardia()+"\r\n" + 
						"	AND IDGUARDIA IS NOT NULL");
		sql.ORDER_BY("FECHA DESC, IDSALTOSTURNO DESC");
		
		return sql.toString();
	}
}
