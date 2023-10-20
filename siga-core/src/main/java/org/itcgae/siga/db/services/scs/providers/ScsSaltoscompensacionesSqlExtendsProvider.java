package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.BusquedaLetradosGuardiaDTO;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.ScsSaltoscompensaciones;

public class ScsSaltoscompensacionesSqlExtendsProvider extends ScsSaltoscompensaciones {

	public String searchSaltosCompensaciones(SaltoCompGuardiaItem saltoItem, String idInstitucion, Integer tamMax) {

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
				+ "		SCS_GRUPOGUARDIA.NUMEROGRUPO AS GRUPO,\r\n" + "		SCS_TURNO.IDTURNO,\r\n"
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

		if (!UtilidadesString.esCadenaVacia(saltoItem.getIdSaltosTurno())) {
			sql.WHERE("IDSALTOSTURNO =" + saltoItem.getIdSaltosTurno());
		}

		if (!saltoItem.isHistorico()) {
			sql.WHERE("FECHAUSO IS NULL");
			sql.WHERE("FECHA_ANULACION IS NULL");
		}

		if (!UtilidadesString.esCadenaVacia(saltoItem.getColegiadoGrupo())) {
			sql.WHERE("( (COLEGIADO_GRUPO = " + saltoItem.getColegiadoGrupo() + "\r\n" + "	AND GRUPO IS NULL)\r\n"
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
					+ "	WHERE\r\n" + "		COLEGIADO = " + saltoItem.getColegiadoGrupo() + ") ) ) ");
		}

		sql.WHERE("IDINSTITUCION =" + idInstitucion);

		if (!UtilidadesString.esCadenaVacia(saltoItem.getIdTurno())) {
			sql.WHERE("IDTURNO IN (" + saltoItem.getIdTurno() + " )");
		}

		if (!UtilidadesString.esCadenaVacia(saltoItem.getIdGuardia())) {
			sql.WHERE("IDGUARDIA IN (" + saltoItem.getIdGuardia() + " )");
		} else {
			sql.WHERE("IDGUARDIA IS NOT NULL");
		}

		if (!UtilidadesString.esCadenaVacia(saltoItem.getFechaDesde())) {
			sql.WHERE("TRUNC(FECHA) >= TRUNC(TO_DATE('" + saltoItem.getFechaDesde() + "', 'DD/MM/YYYY'))");
		}

		if (!UtilidadesString.esCadenaVacia(saltoItem.getFechaHasta())) {
			sql.WHERE("TRUNC(FECHA) <= TRUNC(TO_DATE('" + saltoItem.getFechaHasta() + "', 'DD/MM/YYYY'))");
		}

		sql.ORDER_BY("FECHA DESC, IDSALTOSTURNO");

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
        SQL sql4 = new SQL();
        SQL sql5 = new SQL();
        
        sql.SELECT(" *");
        
        sql2.SELECT(" SCS_SALTOSCOMPENSACIONES.IDINSTITUCION");
        sql2.SELECT(" NULL AS GRUPO");
        sql2.SELECT(" SCS_TURNO.IDTURNO");
        sql2.SELECT(" SCS_TURNO.NOMBRE AS NOMBRE_TURNO");
        sql2.SELECT(" SCS_GUARDIASTURNO.IDGUARDIA");
        sql2.SELECT(" SCS_GUARDIASTURNO.NOMBRE AS NOMBREGUARDIA");
        sql2.SELECT(" SCS_SALTOSCOMPENSACIONES.IDSALTOSTURNO");
        sql2.SELECT(" DECODE(CEN_COLEGIADO.COMUNITARIO, '1', CEN_COLEGIADO.NCOMUNITARIO, CEN_COLEGIADO.NCOLEGIADO) AS COLEGIADO_GRUPO");
        sql2.SELECT("  CEN_PERSONA.APELLIDOS1 || ' ' || CEN_PERSONA.APELLIDOS2 || ', ' || CEN_PERSONA.NOMBRE AS LETRADO");
        sql2.SELECT(" SCS_SALTOSCOMPENSACIONES.SALTOOCOMPENSACION");
        sql2.SELECT(" SCS_SALTOSCOMPENSACIONES.FECHA");
        sql2.SELECT(" SCS_SALTOSCOMPENSACIONES.MOTIVOS AS MOTIVO");
        sql2.SELECT(" SCS_SALTOSCOMPENSACIONES.FECHACUMPLIMIENTO AS FECHAUSO");
        sql2.SELECT(" SCS_SALTOSCOMPENSACIONES.FECHA_ANULACION");
        sql2.SELECT(" CEN_PERSONA.IDPERSONA");
        
        sql2.FROM(" SCS_SALTOSCOMPENSACIONES");
        
        sql2.LEFT_OUTER_JOIN(" SCS_TURNO ON\r\n"
                + " SCS_TURNO.IDINSTITUCION = SCS_SALTOSCOMPENSACIONES.IDINSTITUCION\r\n"
                + " AND SCS_TURNO.IDTURNO = SCS_SALTOSCOMPENSACIONES.IDTURNO");
        sql2.LEFT_OUTER_JOIN(" SCS_GUARDIASTURNO ON\r\n"
                + " SCS_GUARDIASTURNO.IDINSTITUCION = SCS_SALTOSCOMPENSACIONES.IDINSTITUCION\r\n"
                + " AND SCS_GUARDIASTURNO.IDTURNO = SCS_SALTOSCOMPENSACIONES.IDTURNO\r\n"
                + " AND SCS_GUARDIASTURNO.IDGUARDIA = SCS_SALTOSCOMPENSACIONES.IDGUARDIA");
        sql2.JOIN(" CEN_PERSONA ON\r\n"
                + " CEN_PERSONA.IDPERSONA = SCS_SALTOSCOMPENSACIONES.IDPERSONA");
        sql2.JOIN(" CEN_COLEGIADO ON\r\n"
                + " CEN_COLEGIADO.IDPERSONA = SCS_SALTOSCOMPENSACIONES.IDPERSONA\r\n"
                + " AND CEN_COLEGIADO.IDINSTITUCION = SCS_SALTOSCOMPENSACIONES.IDINSTITUCION");
        
        sql.FROM(" (" + sql2.toString() + ") CONSULTA");
        
        if (!salto.isHistorico()) {
            sql.WHERE(" FECHAUSO IS NULL");
            sql.WHERE(" FECHA_ANULACION IS NULL");
        }    

 

        if (!UtilidadesString.esCadenaVacia(salto.getColegiadoGrupo())) {
            sql.WHERE(" ((COLEGIADO_GRUPO = '" + salto.getColegiadoGrupo() + "'");
            
            
            sql3.SELECT(" IDGRUPOGUARDIA");
            
            sql4.SELECT(" ggc.IDGRUPOGUARDIA");
            sql4.SELECT(" DECODE(col.COMUNITARIO, '1', col.NCOMUNITARIO, col.NCOLEGIADO) AS COLEGIADO");
            
            sql4.FROM(" cen_persona perso");
            sql4.FROM(" cen_colegiado col");
            sql4.FROM(" scs_grupoguardiacolegiado ggc");
            
            sql4.WHERE(" perso.idpersona = ggc.idpersona");
            sql4.WHERE(" col.idpersona = perso.idpersona");
            sql4.WHERE(" col.idinstitucion = ggc.idinstitucion");
            
            sql5.SELECT(" IDGRUPOGUARDIA");
            sql5.FROM(" SCS_GRUPOGUARDIA ))");
            
            sql4.WHERE(" ggc.idgrupoguardia IN (" + sql5.toString());
            
            sql3.FROM(" (" + sql4.toString());
            
            if (!UtilidadesString.esCadenaVacia(salto.getColegiadoGrupo())) {
                sql3.WHERE(" COLEGIADO = '" + salto.getColegiadoGrupo() + "'))");
            }
            
            sql.WHERE(" GRUPO IS NULL) OR (GRUPO IN (" + sql3.toString() + ")");
            
        }
    
        sql.WHERE("IDINSTITUCION =" + idInstitucion);
        
        if (!UtilidadesString.esCadenaVacia(salto.getIdTurno())) {
        	sql.WHERE(" IDTURNO IN (" + salto.getIdTurno() + ")");
        }
        if (!UtilidadesString.esCadenaVacia(salto.getIdGuardia())) {
        	sql.WHERE(" IDGUARDIA IN (" + salto.getIdGuardia() + ")");
        } else {
        	sql.WHERE("IDGUARDIA IS NULL");
        }
    

        if (!UtilidadesString.esCadenaVacia(salto.getFechaDesde())) {
            sql.WHERE("TRUNC(FECHA) >= TRUNC(TO_DATE('" + salto.getFechaDesde() + "', 'DD/MM/YYYY HH24:MI:SS'))");
        }

 

        if (!UtilidadesString.esCadenaVacia(salto.getFechaHasta())) {
            sql.WHERE("TRUNC(FECHA) <= TRUNC(TO_DATE('" + salto.getFechaHasta() + "', 'DD/MM/YYYY HH24:MI:SS'))");
        }
    
        sql.ORDER_BY("FECHA DESC, IDSALTOSTURNO");
        
        return sql.toString();
	}

	public String searchLetrados(SaltoCompGuardiaItem saltoItem, String idInstitucion) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql2.SELECT("SCS_INSCRIPCIONGUARDIA.IDPERSONA");
		sql2.FROM("SCS_INSCRIPCIONGUARDIA");
		sql2.WHERE("SCS_INSCRIPCIONGUARDIA.idInstitucion = '" + idInstitucion.trim() + "'");
		sql2.WHERE("SCS_INSCRIPCIONGUARDIA.idturno = '" + saltoItem.getIdTurno().trim() + "'");
		sql2.WHERE("SCS_INSCRIPCIONGUARDIA.idguardia = '" + saltoItem.getIdGuardia().trim() + "'");
		sql2.WHERE("SCS_INSCRIPCIONGUARDIA.FECHABAJA IS NULL");
		
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
		sql.WHERE("SCS_GRUPOGUARDIACOLEGIADO.idgrupoguardia IN (SELECT sgg.IDGRUPOGUARDIA FROM SCS_GRUPOGUARDIA sgg WHERE sgg.NUMEROGRUPO = "+saltoItem.getGrupo().trim()+" AND IDINSTITUCION = "+idInstitucion+" AND IDGUARDIA = "+saltoItem.getIdGuardia().trim()+")");
		sql.WHERE("SCS_GRUPOGUARDIACOLEGIADO.idpersona in ( " + sql2 + " )");

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
	
	public String selectIdSiguienteSaltosCompensacionesGrupo() {

		SQL sql = new SQL();
		//SELECT max(ss.IDSALTOCOMPENSACIONGRUPO)+1 AS ID FROM SCS_SALTOCOMPENSACIONGRUPO ss;
		/*
		sql.SELECT("sq_scs_saltocompensaciongrupo.NEXTVAL AS ID");
		sql.FROM("dual");
		*/
		sql.SELECT("max(ss.IDSALTOCOMPENSACIONGRUPO)+1 AS ID");
		sql.FROM("SCS_SALTOCOMPENSACIONGRUPO ss");

		return sql.toString();
	}

	public String guardarSaltosCompensaciones(SaltoCompGuardiaItem saltoItem, String idInstitucion,
											  String idSaltosTurno, AdmUsuarios usuario) {

		SQL sql = new SQL();

		sql.INSERT_INTO("SCS_SALTOSCOMPENSACIONES");

		sql.VALUES("FECHA", "TO_DATE('"+saltoItem.getFecha() + "','DD/MM/RRRR')");
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
		sql.VALUES("IDTURNO", "'" + saltoItem.getIdTurno() + "'");
		sql.VALUES("IDGUARDIA", "'" + saltoItem.getIdGuardia() + "'");
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

	public String isGrupo(BusquedaLetradosGuardiaDTO letrado) {
		SQL sql = new SQL();
		sql.SELECT("SCS_GUARDIASTURNO.PORGRUPOS");
		sql.FROM("SCS_GUARDIASTURNO");
		sql.WHERE("IDTURNO = '" + letrado.getIdTurno() + "'");
		sql.WHERE("IDGUARDIA = '" + letrado.getIdGuardia() + "'");
		return sql.toString();
	}

	public String searchSaltosOCompensacionesOficio(String idInstitucion, String idTurno, String idGuardia, String saltoocompensacion) {
		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql2.SELECT("IDPERSONA, count(1) NUMERO ");

		// Los siguientes minimos se usan para ordenar en la lista de saltos/compensaciones (SCs).
		// el orden principal es por cantidad de saltos/compensaciones de forma descendente.
		// Sin embargo, como ayuda, también se añade el orden por fecha y orden de creacion. Pero este orden solo será válido si cada colegiado tiene 1 unico SC. En caso contrario, el orden no se garantiza.
		sql2.SELECT("min(FECHA) MINFECHA, min(IDSALTOSTURNO) MINSALTO");
		sql2.FROM("SCS_SALTOSCOMPENSACIONES");
		sql2.WHERE("idinstitucion = " + idInstitucion);
		if(idTurno != null)
			sql2.WHERE("idturno =" + idTurno);
		if(idGuardia != null) {
			sql2.WHERE("idguardia = " + idGuardia);
		} else {
			sql2.WHERE("idguardia IS NULL");
		}

		if (saltoocompensacion != " ") {
			sql2.WHERE("SALTOOCOMPENSACION = '" + saltoocompensacion + "'");
		}
		sql2.WHERE("FECHACUMPLIMIENTO IS NULL ");
		sql2.GROUP_BY("IDPERSONA");

		sql.SELECT("decode(C.COMUNITARIO,'1', C.NCOMUNITARIO,NCOLEGIADO) NCOLEGIADO");
		sql.SELECT("P.NOMBRE");
		sql.SELECT("P.APELLIDOS1");
		sql.SELECT("P.APELLIDOS2");
		sql.SELECT("SC.NUMERO");
		sql.FROM("CEN_COLEGIADO C");
		sql.FROM("CEN_PERSONA P");
		sql.FROM("(" + sql2 +") SC");

		sql.WHERE("SC.IDPERSONA = " + "C.IDPERSONA");
		sql.WHERE("SC.IDPERSONA = P.IDPERSONA");
		sql.WHERE("C.IDINSTITUCION = " + idInstitucion);

		// El orden principal es por cantidad. Los siguientes campos de orden son solo orientativos (ver origen arriba)
		sql.ORDER_BY("SC.NUMERO desc, SC.MINFECHA, SC.MINSALTO");

		return sql.toString();
	}
	
	
	public String insertManual(ScsSaltoscompensaciones record, String fechaFormat) {
		SQL sql = new SQL();
		sql.INSERT_INTO("SCS_SALTOSCOMPENSACIONES");

		if (record.getIdinstitucion() != null) {
			sql.VALUES("IDINSTITUCION", record.getIdinstitucion().toString());
		}

		if (record.getIdturno() != null) {
			sql.VALUES("IDTURNO", record.getIdturno().toString());
		}

		if (record.getIdsaltosturno() != null) {
			sql.VALUES("IDSALTOSTURNO", record.getIdsaltosturno().toString());
		}

		if (record.getIdpersona() != null) {
			sql.VALUES("IDPERSONA", record.getIdpersona().toString());
		}

		if (record.getSaltoocompensacion() != null) {
			sql.VALUES("SALTOOCOMPENSACION", "'" +record.getSaltoocompensacion().toString()+ "'");
		}

		if (record.getFecha() != null) {
			sql.VALUES("FECHA", "'" +fechaFormat+ "'");
		}

		if (record.getFechamodificacion() != null) {
			sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		}

		if (record.getUsumodificacion() != null) {
			sql.VALUES("USUMODIFICACION", record.getUsumodificacion().toString());
		}

		if (record.getIdguardia() != null) {
			sql.VALUES("IDGUARDIA", record.getIdguardia().toString());
		}

		if (record.getMotivos() != null) {
			sql.VALUES("MOTIVOS", "' " + record.getMotivos().toString() + " '");
		}

		if (record.getFechacumplimiento() != null) {
			sql.VALUES("FECHACUMPLIMIENTO", "'" +record.getFechacumplimiento().toString());
		}

		if (record.getIdcalendarioguardias() != null) {
			sql.VALUES("IDCALENDARIOGUARDIAS", record.getIdcalendarioguardias().toString());
		}

		if (record.getIdcalendarioguardiascreacion() != null) {
			sql.VALUES("IDCALENDARIOGUARDIASCREACION", record.getIdcalendarioguardiascreacion().toString());
		}

		if (record.getTipomanual() != null) {
			sql.VALUES("TIPOMANUAL", record.getTipomanual().toString());
		}

		return sql.toString();
	}

	public String deleteSaltosCompensacionesCalendariosInexistentes(Integer idInstitucion, Integer idTurno, Integer idGuardia) {

		SQL sql = new SQL();
		SQL sql2 = new SQL();

		sql2.SELECT("1");
		sql2.FROM("SCS_CALENDARIOGUARDIAS cg");
		sql2.WHERE("cg.IDINSTITUCION = " + idInstitucion);
		sql2.WHERE("cg.IDTURNO = " + idTurno);
		sql2.WHERE("cg.IDGUARDIA = " + idGuardia);
		sql2.WHERE("cg.IDCALENDARIOGUARDIAS = sc.IDCALENDARIOGUARDIASCREACION");

		sql.DELETE_FROM("SCS_SALTOSCOMPENSACIONES sc");
		sql.WHERE("sc.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("sc.IDTURNO = " + idTurno);
		sql.WHERE("sc.IDGUARDIA = " + idGuardia);
		sql.WHERE("sc.IDCALENDARIOGUARDIAS IS NULL");
		sql.WHERE("sc.IDCALENDARIOGUARDIASCREACION IS NOT NULL");
		sql.WHERE("NOT EXISTS ( " + sql2.toString() + ")");

		return sql.toString();

	}

	public String updateSaltosCompensacionesCumplidos(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia, Integer usuario) {
		SQL sql = new SQL();

		sql.UPDATE("SCS_SALTOSCOMPENSACIONES");
		sql.SET("FECHACUMPLIMIENTO = NULL");
		sql.SET("IDCALENDARIOGUARDIAS = NULL");
		sql.SET("FECHAMODIFICACION = SYSDATE");
		sql.SET("USUMODIFICACION = " + usuario);
		sql.SET("MOTIVOS = REGEXP_REPLACE(MOTIVOS, ':id=" + idCalendarioGuardias + ":.*:finid=" + idCalendarioGuardias + ":',' ')");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDTURNO = " + idTurno);
		sql.WHERE("IDGUARDIA = " + idGuardia);
		sql.WHERE("IDCALENDARIOGUARDIAS = " + idCalendarioGuardias);

		return sql.toString();
	}

	public String deleteSaltosCompensacionesCreadosEnCalendario(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia) {
		SQL sql = new SQL();

		sql.DELETE_FROM("SCS_SALTOSCOMPENSACIONES");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDCALENDARIOGUARDIASCREACION = " + idCalendarioGuardias);
		sql.WHERE("(IDCALENDARIOGUARDIAS = " + idCalendarioGuardias + " OR IDCALENDARIOGUARDIAS IS NULL)");
		sql.WHERE("IDTURNO = " + idTurno);
		sql.WHERE("IDGUARDIA = " + idGuardia);

		return sql.toString();
	}
	public String deleteSaltosCompensacionesGrupoCreadosEnCalendario(Integer idInstitucion, Integer idCalendarioGuardias, Integer idTurno, Integer idGuardia) {
		SQL sql = new SQL();

		sql.DELETE_FROM("SCS_SALTOCOMPENSACIONGRUPO");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDCALENDARIOGUARDIASCREACION = " + idCalendarioGuardias);
		sql.WHERE("(IDCALENDARIOGUARDIAS = " + idCalendarioGuardias + " OR IDCALENDARIOGUARDIAS IS NULL)");
		sql.WHERE("IDTURNO = " + idTurno);
		sql.WHERE("IDGUARDIA = " + idGuardia);

		return sql.toString();
	}
}
