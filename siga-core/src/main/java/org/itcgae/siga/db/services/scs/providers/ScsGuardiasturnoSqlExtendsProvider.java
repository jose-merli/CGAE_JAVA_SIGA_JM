
package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.db.mappers.ScsGuardiasturnoSqlProvider;

public class ScsGuardiasturnoSqlExtendsProvider extends ScsGuardiasturnoSqlProvider {

	public String searchNombreTurnoGuardia(String idInstitucion, String nombreGuardia) {
		SQL sql = new SQL();

		sql.SELECT_DISTINCT("SCS_TURNO.abreviatura AS abreviaturaTurno");

		sql.SELECT_DISTINCT("SCS_GUARDIASTURNO.IDTURNO AS idturno");
		sql.SELECT_DISTINCT("SCS_GUARDIASTURNO.IDGUARDIA AS idguardia");
		sql.SELECT_DISTINCT("SCS_GUARDIASTURNO.NOMBRE AS nombreGuardia");
		sql.FROM("SCS_GUARDIASTURNO");

		sql.JOIN(
				"SCS_TURNO ON SCS_TURNO.IDTURNO = SCS_GUARDIASTURNO.IDTURNO AND SCS_GUARDIASTURNO.IDINSTITUCION = SCS_TURNO.IDINSTITUCION");
		sql.WHERE("SCS_GUARDIASTURNO.IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("SCS_GUARDIASTURNO.FECHABAJA is null");
		sql.WHERE("SCS_GUARDIASTURNO.nombre ='"+nombreGuardia+"'");

		return sql.toString();
	}
	
	public String searchGuardias2(GuardiasItem guardiaItem, String idInstitucion, String idLenguaje, Integer tamMax) {
		SQL sql = new SQL();

		sql.SELECT("SCS_TURNO.NOMBRE AS turno");

		sql.SELECT("SCS_GUARDIASTURNO.IDTURNO AS idturno");
		sql.SELECT("SCS_GUARDIASTURNO.IDGUARDIA AS idguardia");
		sql.SELECT("SCS_GUARDIASTURNO.NOMBRE AS nombre");

		sql.SELECT("GEN_RECURSOS_CATALOGOS.DESCRIPCION AS tipodeguardia");

		sql.SELECT("SCS_TURNO.GUARDIAS AS obligatoriedad");

		sql.SELECT("CONCAT(SCS_GUARDIASTURNO.DIASGUARDIA,' días') AS duracion");

		sql.SELECT(
				"DECODE(SCS_GUARDIASTURNO.PORGRUPOS,0,TO_CHAR(SCS_GUARDIASTURNO.NUMEROLETRADOSGUARDIA),'G') AS NUMEROLETRADOSGUARDIA");

		sql.SELECT("SCS_TURNO.VALIDARJUSTIFICACIONES AS VALIDARJUSTIFICACIONES");

		sql.SELECT("(SELECT COUNT(*) FROM SCS_INSCRIPCIONGUARDIA WHERE "
				+ "(SCS_INSCRIPCIONGUARDIA.IDINSTITUCION = SCS_GUARDIASTURNO.IDINSTITUCION AND "
				+ "SCS_GUARDIASTURNO.IDTURNO = SCS_INSCRIPCIONGUARDIA.IDTURNO AND SCS_GUARDIASTURNO.IDGUARDIA = SCS_INSCRIPCIONGUARDIA.IDGUARDIA "
				+ "AND SCS_INSCRIPCIONGUARDIA.FECHABAJA IS NULL)) AS numeroletradosinscritos");

		sql.SELECT("CASE \r\n"
				+ "		WHEN  'LMXJVSD' LIKE CONCAT('%',CONCAT(SELECCIONLABORABLES,'%')) AND LENGTH(SELECCIONLABORABLES)>1 \r\n"
				+ "			AND SUBSTR(SELECCIONLABORABLES,1,1) = 'L' AND SUBSTR(SELECCIONLABORABLES,LENGTH(SELECCIONLABORABLES),1) IN ('V','S','D') THEN \r\n"
				+ "			(CONCAT(CONCAT(SUBSTR(SELECCIONLABORABLES,1,1), '-'), SUBSTR(SELECCIONLABORABLES,LENGTH(SELECCIONLABORABLES),1)))\r\n"
				+ "		WHEN LENGTH(SELECCIONLABORABLES) = 1 THEN\r\n" + "			SELECCIONLABORABLES\r\n"
				+ "		ELSE SELECCIONLABORABLES\r\n" + "		END AS diaslaborables");

		sql.SELECT("CASE \r\n"
				+ "		WHEN  'LMXJVSD' LIKE CONCAT('%',CONCAT(SELECCIONFESTIVOS,'%')) AND LENGTH(SELECCIONFESTIVOS)>1 \r\n"
				+ "			AND SUBSTR(SELECCIONFESTIVOS,1,1) = 'L' AND SUBSTR(SELECCIONFESTIVOS,LENGTH(SELECCIONFESTIVOS),1) IN ('V','S','D') THEN \r\n"
				+ "			(CONCAT(CONCAT(SUBSTR(SELECCIONFESTIVOS,1,1), '-'), SUBSTR(SELECCIONFESTIVOS,LENGTH(SELECCIONFESTIVOS),1)))\r\n"
				+ "		WHEN LENGTH(SELECCIONFESTIVOS) = 1 THEN\r\n" + "			SELECCIONFESTIVOS\r\n"
				+ "		ELSE SELECCIONFESTIVOS\r\n" + "		END AS diasfestivos");

		sql.SELECT("SCS_GUARDIASTURNO.FECHABAJA AS FECHABAJA");

		sql.FROM("SCS_GUARDIASTURNO");

		sql.JOIN(
				"SCS_TURNO ON SCS_TURNO.IDTURNO = SCS_GUARDIASTURNO.IDTURNO AND SCS_GUARDIASTURNO.IDINSTITUCION = SCS_TURNO.IDINSTITUCION");

		sql.JOIN("SCS_TIPOSGUARDIAS ON SCS_TIPOSGUARDIAS.IDTIPOGUARDIA = SCS_GUARDIASTURNO.IDTIPOGUARDIA");

		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS ON GEN_RECURSOS_CATALOGOS.IDRECURSO = SCS_TIPOSGUARDIAS.DESCRIPCION AND GEN_RECURSOS_CATALOGOS.IDLENGUAJE = '"
						+ idLenguaje + "'");

		// JOINS

		// FILTRO AREA
		if (guardiaItem.getArea() != null && guardiaItem.getArea() != "") {
			sql.LEFT_OUTER_JOIN(
					"SCS_AREA ON SCS_AREA.IDAREA = SCS_TURNO.IDAREA AND SCS_AREA.IDINSTITUCION = SCS_GUARDIASTURNO.IDINSTITUCION");

			// FILTRO AREA | MATERIA
			if (guardiaItem.getMateria() != null && guardiaItem.getMateria() != "")
				sql.LEFT_OUTER_JOIN(
						"SCS_MATERIA ON SCS_MATERIA.IDAREA = SCS_AREA.IDAREA AND SCS_MATERIA.IDINSTITUCION = SCS_GUARDIASTURNO.IDINSTITUCION");
		}
		// FILTRO GRUPOZONA
		if (guardiaItem.getGrupoZona() != null && guardiaItem.getGrupoZona() != "") {

			sql.LEFT_OUTER_JOIN(
					"SCS_ZONA ON SCS_ZONA.IDINSTITUCION = SCS_GUARDIASTURNO.IDINSTITUCION AND SCS_ZONA.IDZONA = SCS_TURNO.IDZONA");

			// FILTRO GRUPOZONA | ZONA
			if (guardiaItem.getZona() != null && guardiaItem.getZona() != "")
				sql.LEFT_OUTER_JOIN(
						"SCS_SUBZONA ON SCS_ZONA.IDZONA = SCS_SUBZONA.IDZONA AND SCS_SUBZONA.IDINSTITUCION = SCS_GUARDIASTURNO.IDINSTITUCION");
		}
		// ----------------------------------------

		// WHERE
		sql.WHERE("SCS_GUARDIASTURNO.IDINSTITUCION = '" + idInstitucion + "'");

		// FILTRO HISTORICO
		if (!guardiaItem.isHistorico()) {
			sql.WHERE("SCS_GUARDIASTURNO.FECHABAJA IS NULL");
			sql.WHERE("SCS_TURNO.FECHABAJA IS NULL");
		}
		// FILTRO POR TURNO
		if (guardiaItem.getIdTurno() != null && guardiaItem.getIdTurno() != "")
			sql.WHERE("SCS_GUARDIASTURNO.IDTURNO IN (" + guardiaItem.getIdTurno() + ")");

		// FILTRO POR NOMBRE DE GUARDIA
		if (guardiaItem.getNombre() != null && guardiaItem.getNombre().trim() != "")
			sql.WHERE("UPPER(SCS_GUARDIASTURNO.NOMBRE) LIKE  UPPER('%" + guardiaItem.getNombre() + "%')");

		// FILTRO POR AREA
		if (guardiaItem.getArea() != null && guardiaItem.getArea() != "")
			sql.WHERE("SCS_AREA.IDAREA = '" + guardiaItem.getArea() + "'");

		// FILTRO POR AREA | MATERIA
		if (guardiaItem.getMateria() != null && guardiaItem.getMateria() != "")
			sql.WHERE("SCS_MATERIA.IDMATERIA ='" + guardiaItem.getMateria() + "'");

		// FILTRO POR GRUPOZONA
		if (guardiaItem.getGrupoZona() != null && guardiaItem.getGrupoZona() != "")
			sql.WHERE("SCS_ZONA.IDZONA = '" + guardiaItem.getGrupoZona() + "'");

		// FILTRO POR GRUPOZONA | ZONA
		if (guardiaItem.getZona() != null && guardiaItem.getZona() != "")
			sql.WHERE("SCS_SUBZONA.IDSUBZONA = '" + guardiaItem.getZona() + "'");

		// FILTRO POR JURISDICCION
		if (guardiaItem.getJurisdiccion() != null && guardiaItem.getJurisdiccion() != "")
			sql.WHERE("SCS_TURNO.IDJURISDICCION IN (" + guardiaItem.getJurisdiccion() + ")");

		// FILTRO POR GRUPOFACTURACION
		if (guardiaItem.getGrupoFacturacion() != null && guardiaItem.getGrupoFacturacion() != "")
			sql.WHERE("SCS_TURNO.IDGRUPOFACTURACION IN (" + guardiaItem.getGrupoFacturacion() + ")");

		// FILTRO POR PARTIDAPRESUPUESTARIA
		if (guardiaItem.getPartidaPresupuestaria() != null && guardiaItem.getPartidaPresupuestaria() != "")
			sql.WHERE("SCS_TURNO.IDPARTIDAPRESUPUESTARIA IN (" + guardiaItem.getPartidaPresupuestaria() + ")");

		// FILTRO POR TIPOTURNO
		if (guardiaItem.getTipoTurno() != null && guardiaItem.getTipoTurno() != "")
			sql.WHERE("SCS_TURNO.IDTIPOTURNO IN (" + guardiaItem.getTipoTurno() + ")");

		// FILTRO POR TIPOGUARDIA
		if (guardiaItem.getTipoGuardia() != null && guardiaItem.getTipoGuardia() != "")
			sql.WHERE("SCS_GUARDIASTURNO.IDTIPOGUARDIA IN (" + guardiaItem.getTipoGuardia() + ")");

		sql.ORDER_BY("SCS_TURNO.NOMBRE, SCS_GUARDIASTURNO.NOMBRE");

		if (tamMax != null) {
			Integer tamMaxNumber = tamMax + 1;
			sql.WHERE("rownum <= " + tamMaxNumber);
		}

		return sql.toString();
	}
	
	public String searchGuardias(TurnosItem turnosItem, String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("SCS_TURNO.NOMBRE AS turno");

		sql.SELECT("SCS_GUARDIASTURNO.IDTURNO AS idturno");
		sql.SELECT("SCS_GUARDIASTURNO.IDGUARDIA AS idguardia");
		sql.SELECT("SCS_GUARDIASTURNO.NOMBRE AS nombre");

		sql.SELECT("GEN_RECURSOS_CATALOGOS.DESCRIPCION AS tipodeguardia");

		sql.SELECT("SCS_TURNO.GUARDIAS AS obligatoriedad");

		sql.SELECT("CONCAT(SCS_GUARDIASTURNO.DIASGUARDIA,' días') AS duracion");

		sql.SELECT(
				"DECODE(SCS_GUARDIASTURNO.PORGRUPOS,0,TO_CHAR(SCS_GUARDIASTURNO.NUMEROLETRADOSGUARDIA),'G') AS NUMEROLETRADOSGUARDIA");

		sql.SELECT("SCS_TURNO.VALIDARJUSTIFICACIONES AS VALIDARJUSTIFICACIONES");

		sql.SELECT("(SELECT COUNT(*) FROM SCS_INSCRIPCIONGUARDIA WHERE "
				+ "(SCS_INSCRIPCIONGUARDIA.IDINSTITUCION = SCS_GUARDIASTURNO.IDINSTITUCION AND "
				+ "SCS_GUARDIASTURNO.IDTURNO = SCS_INSCRIPCIONGUARDIA.IDTURNO AND SCS_GUARDIASTURNO.IDGUARDIA = SCS_INSCRIPCIONGUARDIA.IDGUARDIA "
				+ "AND SCS_INSCRIPCIONGUARDIA.FECHABAJA IS NULL)) AS numeroletradosinscritos");

		sql.SELECT("CASE \r\n"
				+ "		WHEN  'LMXJVSD' LIKE CONCAT('%',CONCAT(SELECCIONLABORABLES,'%')) AND LENGTH(SELECCIONLABORABLES)>1 \r\n"
				+ "			AND SUBSTR(SELECCIONLABORABLES,1,1) = 'L' AND SUBSTR(SELECCIONLABORABLES,LENGTH(SELECCIONLABORABLES),1) IN ('V','S','D') THEN \r\n"
				+ "			(CONCAT(CONCAT(SUBSTR(SELECCIONLABORABLES,1,1), '-'), SUBSTR(SELECCIONLABORABLES,LENGTH(SELECCIONLABORABLES),1)))\r\n"
				+ "		WHEN LENGTH(SELECCIONLABORABLES) = 1 THEN\r\n" + "			SELECCIONLABORABLES\r\n"
				+ "		ELSE SELECCIONLABORABLES\r\n" + "		END AS diaslaborables");

		sql.SELECT("CASE \r\n"
				+ "		WHEN  'LMXJVSD' LIKE CONCAT('%',CONCAT(SELECCIONFESTIVOS,'%')) AND LENGTH(SELECCIONFESTIVOS)>1 \r\n"
				+ "			AND SUBSTR(SELECCIONFESTIVOS,1,1) = 'L' AND SUBSTR(SELECCIONFESTIVOS,LENGTH(SELECCIONFESTIVOS),1) IN ('V','S','D') THEN \r\n"
				+ "			(CONCAT(CONCAT(SUBSTR(SELECCIONFESTIVOS,1,1), '-'), SUBSTR(SELECCIONFESTIVOS,LENGTH(SELECCIONFESTIVOS),1)))\r\n"
				+ "		WHEN LENGTH(SELECCIONFESTIVOS) = 1 THEN\r\n" + "			SELECCIONFESTIVOS\r\n"
				+ "		ELSE SELECCIONFESTIVOS\r\n" + "		END AS diasfestivos");

		sql.SELECT("SCS_GUARDIASTURNO.FECHABAJA AS FECHABAJA");

		sql.FROM("SCS_GUARDIASTURNO");

		sql.JOIN(
				"SCS_TURNO ON SCS_TURNO.IDTURNO = SCS_GUARDIASTURNO.IDTURNO AND SCS_GUARDIASTURNO.IDINSTITUCION = SCS_TURNO.IDINSTITUCION");

		sql.JOIN("SCS_TIPOSGUARDIAS ON SCS_TIPOSGUARDIAS.IDTIPOGUARDIA = SCS_GUARDIASTURNO.IDTIPOGUARDIA");

		sql.LEFT_OUTER_JOIN(
				"GEN_RECURSOS_CATALOGOS ON GEN_RECURSOS_CATALOGOS.IDRECURSO = SCS_TIPOSGUARDIAS.DESCRIPCION AND GEN_RECURSOS_CATALOGOS.IDLENGUAJE = '"
						+ idLenguaje + "'");
		sql.WHERE("SCS_GUARDIASTURNO.IDINSTITUCION = '" + idInstitucion + "'");
		if(turnosItem.getIdturno().contains(",")) {
			sql.WHERE("SCS_GUARDIASTURNO.IDTURNO IN ("+turnosItem.getIdturno()+")");
		}
		else sql.WHERE("SCS_GUARDIASTURNO.IDTURNO = '"+turnosItem.getIdturno()+"'");
		if(!turnosItem.isHistorico()) {
			sql.WHERE("SCS_GUARDIASTURNO.FECHABAJA is null");
		}
		
		sql.ORDER_BY("SCS_TURNO.NOMBRE, SCS_GUARDIASTURNO.NOMBRE");

		return sql.toString();
	}

	public String comboGuardias(String idTurno, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("NOMBRE");
		sql.SELECT("IDGUARDIA");

		sql.FROM("SCS_GUARDIASTURNO");

		sql.WHERE("IDTURNO IN (" + idTurno + ")");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.ORDER_BY("nombre");

		return sql.toString();
	}
	
	public String comboGuardiasNoGrupo(String idTurno, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("NOMBRE");
		sql.SELECT("IDGUARDIA");
		
		sql.FROM("SCS_GUARDIASTURNO");
		
		sql.WHERE("IDTURNO IN (" + idTurno + ")");
		sql.WHERE("IDINSTITUCION = '"+idInstitucion+"'");
		sql.WHERE("PORGRUPOS = 0");
		sql.ORDER_BY("nombre");
		
		return sql.toString();
	}
	
	public String comboGuardiasUpdate(String idTurno, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("NOMBRE");
		sql.SELECT("IDGUARDIA");
		
		sql.FROM("SCS_GUARDIASTURNO");
		
		if(!idTurno.contains(","))sql.WHERE("IDTURNO = '"+idTurno+"'");
		else sql.WHERE("IDTURNO IN ("+idTurno+")");
		sql.WHERE("IDINSTITUCION = '"+idInstitucion+"'");
		sql.WHERE("fechabaja is null");
		sql.ORDER_BY("nombre");
		
		return sql.toString();
	}

	public String comboListasGuardias(String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("NOMBRE");
		sql.SELECT("IDLISTA");

		sql.FROM("SCS_LISTAGUARDIAS");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.ORDER_BY("nombre");

		return sql.toString();
	}
	
	public String comboConjuntoGuardias(String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("DESCRIPCION");
		sql.SELECT("IDCONJUNTOGUARDIA");

		sql.FROM("SCS_CONJUNTOGUARDIAS");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.ORDER_BY("DESCRIPCION");

		return sql.toString();
	}

	public String getIdGuardia() {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDGUARDIA) AS IDGUARDIA");
		sql.FROM("SCS_GUARDIASTURNO");

		return sql.toString();
	}
	
	public String busquedaGuardiasCMO(String turnos, String guardias, Short idInstitucion) {
		
		SQL sql = new SQL();
		
		sql.SELECT_DISTINCT("SCS_TURNO.NOMBRE AS NOMBRE_TURNO");
		sql.SELECT_DISTINCT("SCS_GUARDIASTURNO.NOMBRE AS NOMBRE_GUARDIA");
		sql.SELECT_DISTINCT("SCS_GUARDIASTURNO.PORGRUPOS");
		sql.SELECT_DISTINCT("SCS_GUARDIASTURNO.IDORDENACIONCOLAS");
//		sql.SELECT_DISTINCT("CEN_COLEGIADO.NCOLEGIADO");
//		sql.SELECT_DISTINCT("SCS_INSCRIPCIONGUARDIA.FECHABAJA");
//		sql.SELECT_DISTINCT("SCS_INSCRIPCIONGUARDIA.FECHAVALIDACION");
		sql.FROM("SCS_GUARDIASTURNO ");
		sql.JOIN("SCS_TURNO ON SCS_TURNO.IDTURNO=SCS_GUARDIASTURNO.IDTURNO AND SCS_TURNO.IDINSTITUCION=SCS_GUARDIASTURNO.IDINSTITUCION");
//				+ "JOIN SCS_INSCRIPCIONGUARDIA ON SCS_INSCRIPCIONGUARDIA.IDGUARDIA=SCS_GUARDIASTURNO.IDGUARDIA AND SCS_INSCRIPCIONGUARDIA.IDINSTITUCION=SCS_GUARDIASTURNO.IDINSTITUCION "
//				+ "JOIN CEN_COLEGIADO ON CEN_COLEGIADO.IDPERSONA=SCS_INSCRIPCIONGUARDIA.IDPERSONA");
		if(turnos.contains(",")) sql.WHERE("SCS_GUARDIASTURNO.IDTURNO IN ("+turnos+")");
		else sql.WHERE("SCS_GUARDIASTURNO.IDTURNO = '"+turnos+"'");
		if(guardias.contains(",")) sql.WHERE("SCS_GUARDIASTURNO.IDGUARDIA IN ("+guardias+")");
		else sql.WHERE("SCS_GUARDIASTURNO.IDGUARDIA = '"+guardias+"'");
		sql.WHERE("SCS_GUARDIASTURNO.IDINSTITUCION = '"+idInstitucion.toString()+"'");
		
		return sql.toString();
	}

	public String getResumen(String idGuardia, String idTurno, String idInstitucion, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("SCS_GUARDIASTURNO.NOMBRE AS nombreguardia");
		sql.SELECT("SCS_TURNO.NOMBRE AS nombreturno");
		sql.SELECT("GEN_RECURSOS_CATALOGOS.DESCRIPCION AS tipoguardia");
		sql.SELECT("(\r\n" + "	SELECT\r\n" + "		COUNT(*)\r\n" + "\r\n" + "		FROM SCS_INSCRIPCIONGUARDIA\r\n"
				+ "	WHERE\r\n" + "		idinstitucion = SCS_GUARDIASTURNO.IDINSTITUCION\r\n"
				+ "		AND idturno = SCS_GUARDIASTURNO.IDTURNO\r\n"
				+ "		AND idguardia = SCS_GUARDIASTURNO.IDGUARDIA\r\n"
				+ "		AND (fechavalidacion IS NOT NULL\r\n"
				+ "		AND TRUNC(fechavalidacion)<= TRUNC(SYSDATE))\r\n" + "		AND (FECHABAJA IS NULL\r\n"
				+ "		OR TRUNC(FECHABAJA)>TRUNC(SYSDATE))) AS NLETRADOSINSCRITOS");
		sql.FROM("SCS_GUARDIASTURNO");

		sql.JOIN("SCS_TURNO ON\r\n" + "	scs_turno.IDTURNO = scs_guardiasturno.IDTURNO\r\n"
				+ "	AND scs_turno.IDINSTITUCION = scs_guardiasturno.IDINSTITUCION");
		sql.JOIN("SCS_TIPOSGUARDIAS ON\r\n" + "	SCS_TIPOSGUARDIAS.IDTIPOGUARDIA = SCS_GUARDIASTURNO.IDTIPOGUARDIA");
		sql.JOIN(
				"GEN_RECURSOS_CATALOGOS ON\r\n" + "	SCS_TIPOSGUARDIAS.DESCRIPCION = GEN_RECURSOS_CATALOGOS.IDRECURSO");

		sql.WHERE("SCS_GUARDIASTURNO.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("SCS_GUARDIASTURNO.IDGUARDIA = " + idGuardia);
		sql.WHERE("SCS_GUARDIASTURNO.IDTURNO = " + idTurno);
		sql.WHERE("IDLENGUAJE = " + idLenguaje);

		return sql.toString();

	}
//
//	public String getResumenConfCola(String idGuardia, String idInstitucion) {
//		SQL sql = new SQL();
//
//		sql.SELECT("ABS(valor)AS numero");
//		sql.SELECT("\r\n"
//				+ "DECODE(POR_FILAS, 'ALFABETICOAPELLIDOS', 'Apellidos y nombre ', DECODE(POR_FILAS, 'NUMEROCOLEGIADO', 'Nº colegiado ', DECODE(POR_FILAS, 'ANTIGUEDADCOLA', 'Antigüedad en la cola ', DECODE(POR_FILAS"
//				+ ", 'FECHANACIMIENTO', 'Edad colegiado ', POR_FILAS )) )) AS POR_FILAS_FORMATEADO");
//		sql.SELECT("DECODE(SIGN(valor), '-1', 'descendente', DECODE(SIGN(valor), 0, '', 'ascendente')) AS orden");
//
//		sql.FROM("SCS_ORDENACIONCOLAS unpivot (valor FOR POR_FILAS IN (alfabeticoapellidos,\r\n"
//				+ "			fechanacimiento,\r\n" + "			numerocolegiado,\r\n" + "			ANTIGUEDADCOLA))");
//		sql.WHERE("idordenacioncolas = (\r\n" + "			SELECT\r\n" + "				IDORDENACIONCOLAS\r\n"
//				+ "			FROM\r\n" + "				SCS_GUARDIASTURNO\r\n" + "			WHERE\r\n"
//				+ "				idguardia =" + idGuardia + "				AND idInstitucion =" + idInstitucion
//				+ "		ORDER BY\r\n" + "			numero DESC)\r\n" + "	WHERE\r\n"
//				+ "		NUMERO > 0)),'Por grupos') AS ordenacion,\r\n" + "		numeroletradosguardia\r\n"
//				+ "	FROM\r\n" + "		SCS_GUARDIASTURNO\r\n" + "	WHERE\r\n" + "		idguardia = " + idGuardia
//				+ "		AND idInstitucion = " + idInstitucion);
//		return sql.toString();
//	}

	public String getBaremos(String idGuardia, String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("GEN_RECURSOS_CATALOGOS.DESCRIPCION AS DESCRIPCION");
		sql.SELECT("SCS_HITOFACTURABLEGUARDIA.PRECIOHITO AS PRECIO");

		sql.FROM("SCS_HITOFACTURABLEGUARDIA");

		sql.JOIN("JOIN SCS_HITOFACTURABLE ON SCS_HITOFACTURABLEGUARDIA.IDHITO = SCS_HITOFACTURABLE.IDHITO");
		sql.LEFT_OUTER_JOIN(
				"LEFT JOIN GEN_RECURSOS_CATALOGOS ON SCS_HITOFACTURABLE.DESCRIPCION = GEN_RECURSOS_CATALOGOS.IDRECURSO");

		sql.WHERE("SCS_HITOFACTURABLEGUARDIA.IDGUARDIA'" + idGuardia + "'");
		sql.WHERE("GEN_RECURSOS_CATALOGOS.IDLENGUAJE = '" + idLenguaje + "'");

		return sql.toString();
	}

	public String resumenIncompatibilidades(String idGuardia, String idTurno, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("count (*) AS TOTAL_INCOMPATIBILIDADES");

		sql.FROM("(SELECT\r\n" + "	turnos.NOMBRE AS TURNO,\r\n" + "	guardi.NOMBRE AS GUARDIA,\r\n"
				+ "	guardi.SELECCIONLABORABLES,\r\n" + "	guardi.SELECCIONFESTIVOS,\r\n" + "	incomp.MOTIVOS,\r\n"
				+ "	incomp.DIASSEPARACIONGUARDIAS,\r\n" + "	incomp.IDINSTITUCION\r\n" + "FROM\r\n"
				+ "	SCS_INCOMPATIBILIDADGUARDIAS incomp\r\n"
				+ "	JOIN SCS_GUARDIASTURNO guardi ON incomp.idguardia = guardi.idguardia AND INCOMP.idturno = guardi.idturno AND INCOMP.idinstitucion = guardi.idinstitucion\r\n"
				+ "	JOIN SCS_TURNO turnos ON turnos.idturno = guardi.idturno AND turnos.idinstitucion = guardi.idinstitucion\r\n"
				+ "WHERE\r\n" + "	incomp.IDINSTITUCION = " + idInstitucion + "\r\n" + "	AND incomp.IDTURNO = "
				+ idTurno + "\r\n" + "	AND incomp.IDGUARDIA = " + idGuardia + "\r\n" + "UNION\r\n" + "SELECT\r\n"
				+ "	turnos.NOMBRE AS TURNO,\r\n" + "	guardi.NOMBRE AS GUARDIA,\r\n"
				+ "	guardi.SELECCIONLABORABLES,\r\n" + "	guardi.SELECCIONFESTIVOS,\r\n" + "	incomp.MOTIVOS,\r\n"
				+ "	incomp.DIASSEPARACIONGUARDIAS,\r\n" + "	incomp.IDINSTITUCION\r\n" + "FROM\r\n"
				+ "	SCS_INCOMPATIBILIDADGUARDIAS incomp\r\n"
				+ "	JOIN SCS_GUARDIASTURNO guardi ON incomp.idguardia = guardi.idguardia AND INCOMP.idturno = guardi.idturno AND INCOMP.idinstitucion = guardi.idinstitucion\r\n"
				+ "	JOIN SCS_TURNO turnos ON turnos.idturno = guardi.idturno AND turnos.idinstitucion = guardi.idinstitucion\r\n"
				+ "WHERE\r\n" + "	incomp.IDINSTITUCION =  " + idInstitucion + "\r\n"
				+ "	AND incomp.IDTURNO_INCOMPATIBLE = " + idTurno + "\r\n" + "	AND incomp.IDGUARDIA_INCOMPATIBLE = "
				+ idGuardia + ")");

		return sql.toString();
	}

	public String resumenConfCola(String idGuardia, String idTurno, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("DECODE(PORGRUPOS, 0, (SELECT\r\n" + "	LISTAGG(ORDENACION, ', ') WITHIN GROUP (\r\n"
				+ "ORDER BY\r\n" + "	ORDEN DESC) \"Product_Listing\"\r\n" + "FROM\r\n" + "	(\r\n" + "	SELECT\r\n"
				+ "		CONCAT(POR_FILAS_FORMATEADO, ORDEN) AS ORDENACION,\r\n" + "		NUMERO AS ORDEN\r\n"
				+ "	FROM\r\n" + "		(\r\n" + "		SELECT\r\n" + "			ABS(valor)AS numero,\r\n"
				+ "			DECODE(POR_FILAS, 'ALFABETICOAPELLIDOS', 'Apellidos y nombre ', DECODE(POR_FILAS, 'NUMEROCOLEGIADO', 'Nº colegiado ', DECODE(POR_FILAS, 'ANTIGUEDADCOLA', 'Antigüedad en la cola ', DECODE(POR_FILAS, 'FECHANACIMIENTO', 'Edad colegiado ', DECODE(POR_FILAS, 'ORDENACIONMANUAL', 'Ordenación manual', POR_FILAS ) )) )) AS POR_FILAS_FORMATEADO,\r\n"
				+ "			DECODE(POR_FILAS,'ORDENACIONMANUAL','',DECODE(SIGN(valor), '-1', 'descendente', DECODE(SIGN(valor), 0, '', 'ascendente'))) AS orden\r\n"
				+ "		FROM\r\n"
				+ "			SCS_ORDENACIONCOLAS unpivot (valor FOR POR_FILAS IN (alfabeticoapellidos,\r\n"
				+ "			fechanacimiento,\r\n" + "			numerocolegiado,\r\n"
				+ "			ANTIGUEDADCOLA, ORDENACIONMANUAL))\r\n" + "		WHERE\r\n"
				+ "			idordenacioncolas = (\r\n" + "			SELECT\r\n" + "				IDORDENACIONCOLAS\r\n"
				+ "			FROM\r\n" + "				SCS_GUARDIASTURNO\r\n" + "			WHERE\r\n"
				+ "				idguardia = " + idGuardia + "\r\n" + "				AND idInstitucion = "
				+ idInstitucion + " AND idturno =" + idTurno + ")\r\n" + "		ORDER BY\r\n"
				+ "			numero DESC)\r\n" + "	WHERE\r\n" + "		NUMERO > 0)),'Por grupos') AS ordenacion,\r\n"
				+ "		numeroletradosguardia\r\n" + "	FROM\r\n" + "		SCS_GUARDIASTURNO\r\n" + "	WHERE\r\n"
				+ "		idguardia = " + idGuardia + "\r\n" + "		AND idInstitucion = " + idInstitucion);

		return sql.toString();
	}

	public String getCalendario(String idGuardia) {
		SQL sql = new SQL();

		sql.SELECT("consulta.*,\r\n" + "	DECODE(consulta.ESTADO, 2, 'No', 'Si') AS GENERADO\r\n" + "FROM\r\n"
				+ "	(\r\n" + "	SELECT\r\n" + "		CG.IDCALENDARIOGUARDIAS,\r\n" + "		CG.FECHAINICIO,\r\n"
				+ "		CG.FECHAFIN,\r\n"
				+ "		DECODE(GT.NUMEROLETRADOSGUARDIA, 0, DECODE((SELECT COUNT(1) TOTAL FROM SCS_CALENDARIOGUARDIAS CALG WHERE CALG.IDINSTITUCION = CG.IDINSTITUCION AND CALG.IDTURNO = CG.IDTURNO AND CALG.IDGUARDIA = CG.IDGUARDIA), 0, 2, 3 ), DECODE((SELECT COUNT(1) TOTAL FROM SCS_CABECERAGUARDIAS CAG WHERE CAG.IDINSTITUCION = CG.IDINSTITUCION AND CAG.IDTURNO = CG.IDTURNO AND CAG.IDGUARDIA = CG.IDGUARDIA AND CAG.IDCALENDARIOGUARDIAS = CG.IDCALENDARIOGUARDIAS), 0, 2, 3 )) ESTADO\r\n"
				+ "	FROM\r\n" + "    		SCS_GUARDIASTURNO GT,\r\n" + "		SCS_CALENDARIOGUARDIAS CG,\r\n"
				+ "		SCS_TURNO T\r\n" + "	WHERE\r\n" + "		CG.IDINSTITUCION = GT.IDINSTITUCION\r\n"
				+ "		AND CG.IDTURNO = GT.IDTURNO\r\n" + "		AND CG.IDGUARDIA = GT.IDGUARDIA\r\n"
				+ "		AND GT.IDINSTITUCION = T.IDINSTITUCION\r\n" + "		AND GT.IDTURNO = T.IDTURNO\r\n"
				+ "		AND GT.IDGUARDIA = " + idGuardia + "\r\n" + "	ORDER BY\r\n" + "		CG.FECHAINICIO,\r\n"
				+ "		CG.FECHAFIN) consulta\r\n" + "WHERE\r\n" + "	ROWNUM <= 1");

		return sql.toString();
	}
	
	public String getObservacionesCalendario(String idGuardia, String idTurno, String idInstitucion, String fechaIni, String fechaFin) {
		SQL sql = new SQL();

		sql.SELECT("SCS_CALENDARIOGUARDIAS.OBSERVACIONES AS OBSERVACIONES");
		sql.FROM("SCS_CALENDARIOGUARDIAS");
		sql.WHERE("SCS_CALENDARIOGUARDIAS.FECHAINICIO = TO_DATE('" + fechaIni+ "', 'YYYY-MM-dd')");
		sql.WHERE("SCS_CALENDARIOGUARDIAS.FECHAFIN = TO_DATE('" + fechaFin + "', 'YYYY-MM-dd')");
		sql.WHERE("SCS_CALENDARIOGUARDIAS.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("SCS_CALENDARIOGUARDIAS.IDTURNO IN ( " + idTurno + ")");
		sql.WHERE("SCS_CALENDARIOGUARDIAS.IDGUARDIA IN ( " + idGuardia + ")");

		return sql.toString();
	}
	public String getTurnoCalProg(String idTurno, String idCalG, String idInstitucion){
		
		SQL sql = new SQL();
		
			if (idTurno != null && idTurno != "") {
				sql.SELECT_DISTINCT("t.nombre as turno");
				sql.FROM("scs_guardiasturno g");
				sql.JOIN("scs_turno t on t.idturno = g.idturno and t.idinstitucion = g.idinstitucion");
				sql.WHERE("g.idturno = " + idTurno);
				sql.WHERE("g.idinstitucion = " + idInstitucion);
				sql.WHERE("t.FECHABAJA IS NULL");
			}
			else {

				sql.SELECT_DISTINCT("t.nombre as turno");
				sql.FROM("SCS_CONF_CONJUNTO_GUARDIAS CG");
				sql.JOIN("scs_turno t on t.idturno = CG.idturno and t.idinstitucion = CG.idinstitucion");
				sql.WHERE("CG.IDCONJUNTOGUARDIA = " + idCalG);
				sql.WHERE("CG.idinstitucion = " + idInstitucion);
				sql.WHERE("t.FECHABAJA IS NULL");
			}
		
		
		return sql.toString();
	}
	
	
	
	public String getGuardiaCalProg(String idTurno, String idGuardia, String idCalG, String idInstitucion){
		
		SQL sql = new SQL();	
			
			if (idGuardia != null && idGuardia != "") {
				sql.SELECT_DISTINCT("g.nombre as guardia");
				sql.FROM("scs_guardiasturno g");
				sql.JOIN("scs_turno t on t.idturno = g.idturno and t.idinstitucion = g.idinstitucion");
				sql.WHERE("g.idguardia = " + idGuardia);
				sql.WHERE("g.idinstitucion = " + idInstitucion);
				
			}else {
				sql.SELECT_DISTINCT("g.nombre as guardia");
				sql.FROM("scs_guardiasturno g");
				sql.JOIN("SCS_CONF_CONJUNTO_GUARDIAS CG on g.idGuardia = CG.idGuardia and g.idinstitucion = CG.idinstitucion");
				sql.WHERE("CG.IDCONJUNTOGUARDIA = " + idCalG);
				sql.WHERE("CG.idinstitucion = " + idInstitucion);
				sql.WHERE("g.idGuardia = CG.idGuardia");
				sql.WHERE("G.IDtURNO = " + idTurno);
				sql.WHERE("scs_turno.FECHABAJA IS NULL");
			}
		
		
		return sql.toString();
	}
	public String getNumGuardiasCalProg(String idCalG){
	
		SQL sql = new SQL();
//		sql.SELECT("COUNT(1) SCS_CALENDARIOGUARDIAS.IDGUARDIA");
//		sql.FROM("SCS_CALENDARIOGUARDIAS");
//		sql.WHERE("SCS_CALENDARIOGUARDIAS.IDCALENDARIOGUARDIAS IN (" +  idCalG + ")");
		sql.SELECT("COUNT(*) numGuardias FROM SCS_CONF_CONJUNTO_GUARDIAS");
		sql.WHERE("SCS_CONF_CONJUNTO_GUARDIAS.IDCONJUNTOGUARDIA IN (" +  idCalG + ")");
		return sql.toString();
	}
	public String getCalendarioProgramado(CalendariosProgDatosEntradaItem calendarioItem, String idInstitucion) {
		SQL sql = new SQL();

//		sql.SELECT("DECODE(SCS_PROG_CALENDARIOS.ESTADO, 2, 'No', 'Si') AS GENERADO");
//		
//		sql.SELECT("SCS_TURNO.NOMBRE AS turno");
//
//		sql.SELECT("SCS_GUARDIASTURNO.nombre AS guardia");
//		
//		sql.SELECT("SCS_CALENDARIOGUARDIAS.OBSERVACIONES AS observaciones");
//		
//		sql.SELECT("SCS_CALENDARIOGUARDIAS.IDCALENDARIOGUARDIAS AS idCalG");
//		
//		sql.SELECT("SCS_PROG_CALENDARIOS.FECHAPROGRAMACION AS fechaProgramacion");
//		
//		sql.SELECT("SCS_PROG_CALENDARIOS.FECHACALINICIO AS fechaDesde");
//		
//		sql.SELECT("SCS_PROG_CALENDARIOS.FECHACALFIN AS fechaHasta");
//		
//		sql.SELECT("SCS_PROG_CALENDARIOS.ESTADO AS estado");
//		
//		sql.SELECT("SCS_LISTAGUARDIAS.NOMBRE AS listaGuardias ");
//
//		sql.FROM("SCS_PROG_CALENDARIOS");
//
//		sql.JOIN("SCS_TURNO ON SCS_PROG_CALENDARIOS.IDINSTITUCION = SCS_TURNO.IDINSTITUCION");
//		
//		sql.JOIN("SCS_LISTAGUARDIAS ON SCS_PROG_CALENDARIOS.IDINSTITUCION = SCS_LISTAGUARDIAS.IDINSTITUCION");
//		
//		sql.JOIN("SCS_GUARDIASTURNO ON SCS_PROG_CALENDARIOS.IDINSTITUCION = SCS_GUARDIASTURNO.IDINSTITUCION");
//		
//		sql.JOIN("SCS_CALENDARIOGUARDIAS ON SCS_PROG_CALENDARIOS.IDINSTITUCION = SCS_CALENDARIOGUARDIAS.IDINSTITUCION");
//		
//
//		sql.WHERE("SCS_PROG_CALENDARIOS.IDINSTITUCION = '" + idInstitucion + "'");
//
//		if (calendarioItem.getListaGuardias() != null) {
//			sql.WHERE("SCS_LISTAGUARDIAS.IDLISTA IN (" + calendarioItem.getListaGuardias() + ")");
//		}
//		if (calendarioItem.getIdTurno() != null && calendarioItem.getIdTurno() != "") {
//			sql.WHERE("SCS_TURNO.IDTURNO IN (" + calendarioItem.getIdTurno() + ")");
//		}
//		if (calendarioItem.getFechaProgramadaDesde() != null && calendarioItem.getFechaProgramadaDesde() != "" && calendarioItem.getFechaProgramadaHasta() != null && calendarioItem.getFechaProgramadaHasta() != "") {
//			sql.WHERE("SCS_PROG_CALENDARIOS.FECHAPROGRAMACION between " + calendarioItem.getFechaProgramadaDesde() + "and " +  calendarioItem.getFechaProgramadaHasta());
//		}else{
//			
//			if (calendarioItem.getFechaProgramadaDesde() != null && calendarioItem.getFechaProgramadaDesde() != "") {
//				sql.WHERE("SCS_PROG_CALENDARIOS.FECHAPROGRAMACION >= " + calendarioItem.getFechaProgramadaDesde());
//			}
//			if(calendarioItem.getFechaProgramadaHasta() != null && calendarioItem.getFechaProgramadaHasta() != "") {
//				sql.WHERE("SCS_PROG_CALENDARIOS.FECHAPROGRAMACION <= " + calendarioItem.getFechaProgramadaHasta());
//			}
//		}
//		if (calendarioItem.getEstado() != null && calendarioItem.getEstado() != "") {
//			sql.WHERE("SCS_PROG_CALENDARIOS.ESTADO IN (" + calendarioItem.getEstado() + ")");	
//		}
//		if (calendarioItem.getFechaCalendarioDesde() != null && calendarioItem.getFechaCalendarioDesde() != "") {
//			sql.WHERE("SCS_PROG_CALENDARIOS.FECHACALINICIO IN (" + calendarioItem.getFechaCalendarioDesde() + ")");	
//		}
//		if (calendarioItem.getFechaCalendarioHasta() != null && calendarioItem.getFechaCalendarioHasta() != "") {
//			sql.WHERE("SCS_PROG_CALENDARIOS.FECHACALFIN IN (" + calendarioItem.getFechaCalendarioHasta() + ")");	
//		}
//		if (calendarioItem.getGuardia() != null && calendarioItem.getGuardia() != "") {
//			sql.WHERE("SCS_GUARDIASTURNO.IDGUARDIA IN (" + calendarioItem.getGuardia() + ")");	
//		}
//		if (calendarioItem.getIdTurno() != null && calendarioItem.getIdTurno() != "") {
//			sql.WHERE("SCS_GUARDIASTURNO.IDTURNO IN (" + calendarioItem.getIdTurno() + ")");	
//		}
//		if (calendarioItem.getGuardia() != null && calendarioItem.getGuardia() != "") {
//			sql.WHERE("SCS_CALENDARIOGUARDIAS.IDGUARDIA IN (" + calendarioItem.getGuardia() + ")");	
//		}
//		if (calendarioItem.getIdTurno() != null && calendarioItem.getIdTurno() != "") {
//			sql.WHERE("SCS_CALENDARIOGUARDIAS.IDTURNO IN (" + calendarioItem.getIdTurno() + ")");	
//		}
//				
//		sql.ORDER_BY("SCS_PROG_CALENDARIOS.FECHAPROGRAMACION");
		
		
		
		sql.SELECT_DISTINCT("PC.IDPROGCALENDARIO as idCalendarioProgramado");
		sql.SELECT_DISTINCT("DECODE(PC.ESTADO, 2, 'No', 'Si') AS GENERADO");
		sql.SELECT_DISTINCT("PC.IDCONJUNTOGUARDIA  AS idCalG");
		sql.SELECT_DISTINCT("CG.IDTURNO as idTurno");
		sql.SELECT_DISTINCT("CG.IDGUARDIA as idGuardia");
		sql.SELECT_DISTINCT("PC.IDINSTITUCION");
		sql.SELECT_DISTINCT("PC.FECHAPROGRAMACION AS FECHAPROGRAMACION"); 
		sql.SELECT_DISTINCT("PC.FECHACALINICIO AS fechaDesde");
		sql.SELECT_DISTINCT("PC.FECHACALFIN AS fechaHasta");      
		sql.SELECT_DISTINCT("PC.ESTADO as estado");
		sql.SELECT_DISTINCT("GG.DESCRIPCION AS listaGuardias");
		sql.FROM("SCS_CONJUNTOGUARDIAS GG,SCS_PROG_CALENDARIOS PC, SCS_CONF_CONJUNTO_GUARDIAS CG");
		sql.WHERE("GG.IDINSTITUCION = PC.IDINSTITUCION");
		sql.WHERE("GG.IDCONJUNTOGUARDIA = PC.IDCONJUNTOGUARDIA");
		sql.WHERE("PC.IDINSTITUCION = '" + idInstitucion + "'");
		if (calendarioItem.getIdConjuntoGuardia() != null && calendarioItem.getIdConjuntoGuardia() != "") {
			sql.WHERE("PC.IDCONJUNTOGUARDIA = " + calendarioItem.getIdConjuntoGuardia());
		}
		if (calendarioItem.getEstado() != null && calendarioItem.getEstado() != "") {
			sql.WHERE("PC.ESTADO IN (" + calendarioItem.getEstado() + ")");	
		}
		if (calendarioItem.getFechaProgramadaDesde() != null && calendarioItem.getFechaProgramadaDesde() != "" && calendarioItem.getFechaProgramadaHasta() != null && calendarioItem.getFechaProgramadaHasta() != "") {
			sql.WHERE("PC.FECHAPROGRAMACION between " + calendarioItem.getFechaProgramadaDesde() + "and " +  calendarioItem.getFechaProgramadaHasta());
		}else{
			
			if (calendarioItem.getFechaProgramadaDesde() != null && calendarioItem.getFechaProgramadaDesde() != "") {
				sql.WHERE("PC.FECHAPROGRAMACION >= " + calendarioItem.getFechaProgramadaDesde());
			}
			if(calendarioItem.getFechaProgramadaHasta() != null && calendarioItem.getFechaProgramadaHasta() != "") {
				sql.WHERE("PC.FECHAPROGRAMACION <= " + calendarioItem.getFechaProgramadaHasta());
			}
		}
		
		if (calendarioItem.getFechaCalendarioDesde() != null && calendarioItem.getFechaCalendarioDesde() != "") {
				sql.WHERE("PC.FECHACALINICIO = " + calendarioItem.getFechaCalendarioDesde());
		}
		
		if(calendarioItem.getFechaCalendarioHasta() != null && calendarioItem.getFechaCalendarioHasta() != "") {
				sql.WHERE("PC.FECHACALFIN = " + calendarioItem.getFechaCalendarioHasta());
		}
		if (calendarioItem.getIdTurno() != null && calendarioItem.getIdTurno() != "") {
		sql.WHERE("CG.IDTURNO in ( " + calendarioItem.getIdTurno()+ ")");
		}
		if (calendarioItem.getIdGuardia() != null && calendarioItem.getIdGuardia() != "") {
		sql.WHERE("CG.IDGUARDIA in (" + calendarioItem.getIdGuardia()+ ")");
		}
//		if (calendarioItem.getIdTurno() != null && calendarioItem.getIdTurno() != "") {
//			sql.WHERE("EXISTS (SELECT 1 FROM SCS_HCO_CONF_PROG_CALENDARIOS HPC, SCS_TURNO T WHERE T.IDTURNO = HPC.IDTURNO AND T.FECHABAJA IS NULL AND HPC.IDINSTITUCION = PC.IDINSTITUCION AND HPC.IDPROGCALENDARIO = PC.IDPROGCALENDARIO  AND HPC.IDTURNO IN ( " + calendarioItem.getIdTurno() + ") and HPC.IDINSTITUCION =" + idInstitucion + " )");
//		}
		sql.ORDER_BY("PC.FECHAPROGRAMACION");


		return sql.toString();

	}

	public String getGuardiasVinculadas(String idGuardia, String idTurno, String idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("g.nombre guardiavinculada");
		sql.SELECT("t.nombre turnovinculado");

		sql.FROM("scs_guardiasturno g");
		sql.JOIN("scs_turno t on t.idturno = g.idturno and t.idinstitucion = g.idinstitucion");
		sql.WHERE("g.idguardiaprincipal = " + idGuardia);
		sql.WHERE("g.idturnoprincipal = " + idTurno);
		sql.WHERE("g.idinstitucion = " + idInstitucion);

		return sql.toString();

	}

	public String getGuardiaPrincipal(String idGuardiaPrincipal, String idTurnoPrincipal, String idInstitucion) {

		SQL sql = new SQL();
		sql.SELECT("g.nombre guardiaprincipal");
		sql.SELECT("t.nombre turnoprincipal");

		sql.FROM("scs_guardiasturno g");
		sql.JOIN("scs_turno t on t.idturno = g.idturno and t.idinstitucion = g.idinstitucion");
		sql.WHERE("g.idguardia = " + idGuardiaPrincipal);
		sql.WHERE("g.idturno = " + idTurnoPrincipal);
		sql.WHERE("g.idinstitucion = " + idInstitucion);

		return sql.toString();

	}

	public String searchLetradosGuardia(String idInstitucion, String idTurno, String idGuardia) {

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
		sql2.SELECT(
				"DECODE(SCS_GUARDIASTURNO.Porgrupos, '1', SCS_GRUPOGUARDIACOLEGIADO.IDGRUPOGUARDIACOLEGIADO, NULL) AS Idgrupoguardiacolegiado");
		sql2.SELECT(
				"DECODE(SCS_GUARDIASTURNO.Porgrupos, '1', SCS_GRUPOGUARDIACOLEGIADO.IDGRUPOGUARDIA, NULL) AS Grupo");
		sql2.SELECT("DECODE(SCS_GUARDIASTURNO.Porgrupos, '1', SCS_GRUPOGUARDIA.NUMEROGRUPO, NULL) AS numeroGrupo");
		sql2.SELECT("DECODE(SCS_GUARDIASTURNO.Porgrupos, '1', SCS_GRUPOGUARDIACOLEGIADO.ORDEN, NULL) AS Ordengrupo");

		sql2.FROM("SCS_INSCRIPCIONGUARDIA");

		sql2.LEFT_OUTER_JOIN(
				"SCS_GUARDIASTURNO ON SCS_INSCRIPCIONGUARDIA.Idinstitucion = SCS_GUARDIASTURNO.Idinstitucion AND SCS_INSCRIPCIONGUARDIA.Idturno = SCS_GUARDIASTURNO.Idturno AND SCS_INSCRIPCIONGUARDIA.Idguardia = SCS_GUARDIASTURNO.Idguardia");
		sql2.LEFT_OUTER_JOIN(
				"CEN_COLEGIADO ON SCS_INSCRIPCIONGUARDIA.Idinstitucion = CEN_COLEGIADO.Idinstitucion AND SCS_INSCRIPCIONGUARDIA.Idpersona = CEN_COLEGIADO.Idpersona");
		sql2.LEFT_OUTER_JOIN("CEN_PERSONA ON CEN_COLEGIADO.Idpersona = CEN_PERSONA.Idpersona");
		sql2.LEFT_OUTER_JOIN(
				"SCS_GRUPOGUARDIACOLEGIADO ON SCS_INSCRIPCIONGUARDIA.Idinstitucion = SCS_GRUPOGUARDIACOLEGIADO.Idinstitucion AND SCS_INSCRIPCIONGUARDIA.Idturno = SCS_GRUPOGUARDIACOLEGIADO.Idturno AND SCS_INSCRIPCIONGUARDIA.Idguardia = SCS_GRUPOGUARDIACOLEGIADO.Idguardia AND SCS_INSCRIPCIONGUARDIA.Idpersona = SCS_GRUPOGUARDIACOLEGIADO.Idpersona AND SCS_INSCRIPCIONGUARDIA.Fechasuscripcion = SCS_GRUPOGUARDIACOLEGIADO.Fechasuscripcion");
		sql2.LEFT_OUTER_JOIN(
				"SCS_GRUPOGUARDIA ON SCS_GRUPOGUARDIACOLEGIADO.Idgrupoguardia = SCS_GRUPOGUARDIA.Idgrupoguardia");

		sql2.WHERE("SCS_INSCRIPCIONGUARDIA.Fechavalidacion IS NOT NULL");
		sql2.WHERE("SCS_GUARDIASTURNO.Idinstitucion = '" + idInstitucion + "'");
		sql2.WHERE("SCS_GUARDIASTURNO.Idturno = '" + idTurno + "'");
		sql2.WHERE("SCS_GUARDIASTURNO.Idguardia = '" + idGuardia + "'");

		sql2.ORDER_BY(
				"numeroGrupo, ordengrupo, SCS_INSCRIPCIONGUARDIA.FECHASUSCRIPCION, SCS_INSCRIPCIONGUARDIA.Idpersona");

		sql.SELECT("*");
		sql.FROM("( " + sql2.toString() + " )");
		sql.WHERE("ACTIVO = 1");

		return sql.toString();
	}

	//
	// public String separarGuardias(String idGuardia, String idTurno, String
	// idInstitucion) {
	// SQL sql = new SQL();
	//
	// sql.SELECT("DIASAPLICABLES");
	// sql.FROM("SCS_HITOFACTURABLEGUARDIA");
	// sql.WHERE("IDINSTITUCION ="+idInstitucion);
	// sql.WHERE("IDTURNO = "+idTurno);
	// sql.WHERE("IDGUARDIA = "+ idGuardia);
	// sql.WHERE("NVL(AGRUPAR , 0) = 0");
	// sql.WHERE("DIASAPLICABLES IS NOT NULL");
	//
	// sql.GROUP_BY("DIASAPLICABLES");
	//
	// return sql.toString();
	// }

	public String searchCalendarios(CalendariosProgDatosEntradaItem calendarioItem, String idInstitucion) {
		SQL sql = new SQL();

//		sql.SELECT("SCS_TURNO.NOMBRE AS turno");
//
//		sql.SELECT("SCS_LISTAGUARDIAS.NOMBRE AS nombre");
//		sql.SELECT("SCS_LISTAGUARDIAS.LUGAR AS lugar");
//		sql.SELECT("SCS_LISTAGUARDIAS.OBSERVACIONES AS observaciones");
//
//		sql.FROM("SCS_LISTAGUARDIAS");
//
//		sql.JOIN("SCS_TURNO ON SCS_LISTAGUARDIAS.IDINSTITUCION = SCS_TURNO.IDINSTITUCION");
//
//		sql.WHERE("SCS_LISTAGUARDIAS.IDINSTITUCION = '" + idInstitucion + "'");
//
//		if (calendarioItem.getListaGuardias() != null) {
//			sql.WHERE("SCS_LISTAGUARDIAS.IDLISTA IN (" + calendarioItem.getListaGuardias() + ")");
//		}
//		if (calendarioItem.getIdTurno() != null && calendarioItem.getIdTurno() != "")
//			sql.WHERE("SCS_TURNO.IDTURNO IN (" + calendarioItem.getIdTurno() + ")");
//
//		sql.ORDER_BY("SCS_TURNO.NOMBRE");

		return sql.toString();
	}
	
	public String selectGuardiaTurnoByTurno(Short idInstitucion, String idTurno) {

		SQL sql = new SQL();

		sql.SELECT("DISTINCT IDGUARDIA,FECHASUSCRIPCION, IDPERSONA, IDINSTITUCION, IDTURNO");
		sql.FROM("SCS_INSCRIPCIONGUARDIA");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDTURNO = '" + idTurno + "'");
		sql.WHERE("FECHABAJA IS NULL");

		return sql.toString();
	}
	
	public String selectGuardiaConfiguradasTurno(Short idInstitucion, String idTurno) {

		SQL sql = new SQL();

		sql.SELECT("DISTINCT IDGUARDIA, IDTURNO, IDINSTITUCION");
		sql.FROM("SCS_GUARDIASTURNO");
		sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		sql.WHERE("IDTURNO = '" + idTurno + "'");
		sql.WHERE("FECHABAJA IS NULL");

		return sql.toString();
	}
	
}
