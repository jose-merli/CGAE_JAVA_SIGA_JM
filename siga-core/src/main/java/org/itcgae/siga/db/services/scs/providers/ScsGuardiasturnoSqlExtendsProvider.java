
package org.itcgae.siga.db.services.scs.providers;

import java.util.Hashtable;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.CalendariosProgDatosEntradaItem;
import org.itcgae.siga.DTOs.scs.DatosCalendarioProgramadoItem;
import org.itcgae.siga.DTOs.scs.GuardiaCalendarioItem;
import org.itcgae.siga.DTOs.scs.GuardiasCalendarioItem;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.GuardiasTurnoItem;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaGrupoItem;
import org.itcgae.siga.DTOs.scs.SaltoCompGuardiaItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.CenPersona;
import org.itcgae.siga.db.entities.ScsGrupoguardiacolegiado;
import org.itcgae.siga.db.entities.ScsSaltoscompensaciones;
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
	public String searchNombreTurnoGuardiaNoAbrev(String idInstitucion, String nombreGuardia) {
		SQL sql = new SQL();

		sql.SELECT_DISTINCT("SCS_TURNO.nombre AS nombreTurno");

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
		//La inscripcion en el turno obliga a inscribirse en guardias: 2- A elegir; 1-Todas o ninguna; 0-Obligatorias

		sql.SELECT("SCS_TURNO.NOMBRE AS turno");

		sql.SELECT("SCS_GUARDIASTURNO.IDTURNO AS idturno");
		sql.SELECT("SCS_GUARDIASTURNO.IDGUARDIA AS idguardia");
		sql.SELECT("SCS_GUARDIASTURNO.NOMBRE AS nombre");

		sql.SELECT("GEN_RECURSOS_CATALOGOS.DESCRIPCION AS tipodeguardia");

		sql.SELECT("CASE \n" +
				"            WHEN SCS_TURNO.GUARDIAS = 0 THEN 'Obligatorias'\n" +
				"            WHEN SCS_TURNO.GUARDIAS = 1 THEN 'Todas o ninguna'\n" +
				"            ELSE 'A elegir'\n" +
				"        END AS obligatoriedad");

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
			sql.WHERE("SCS_AREA.IDAREA IN (" + guardiaItem.getArea() + ")");

		// FILTRO POR AREA | MATERIA
		if (guardiaItem.getMateria() != null && guardiaItem.getMateria() != "")
			sql.WHERE("SCS_MATERIA.IDMATERIA IN (" + guardiaItem.getMateria() + ")");

		// FILTRO POR GRUPOZONA
		if (guardiaItem.getGrupoZona() != null && guardiaItem.getGrupoZona() != "")
			sql.WHERE("SCS_ZONA.IDZONA IN (" + guardiaItem.getGrupoZona() + ")");

		// FILTRO POR GRUPOZONA | ZONA
		if (guardiaItem.getZona() != null && guardiaItem.getZona() != "")
			sql.WHERE("SCS_SUBZONA.IDSUBZONA IN (" + guardiaItem.getZona() + ")");

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
		if (guardiaItem.getIdTipoGuardia() != null && guardiaItem.getIdTipoGuardia() != "")
			sql.WHERE("SCS_GUARDIASTURNO.IDTIPOGUARDIA IN (" + guardiaItem.getIdTipoGuardia() + ")");

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
		if (idTurno != null &&  !idTurno.isEmpty() && idTurno != "undefined") {
			sql.WHERE("IDTURNO IN (" + idTurno + ")");
		}
		if (idInstitucion != null &&  !idInstitucion.isEmpty()) {
			sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		}
		sql.ORDER_BY("nombre");

		return sql.toString();
	}

	public String comboGuardiasNoBaja(String idTurno, String idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("NOMBRE");
		sql.SELECT("IDGUARDIA");

		sql.FROM("SCS_GUARDIASTURNO");
		if (idTurno != null &&  !idTurno.isEmpty()) {
			sql.WHERE("IDTURNO IN (" + idTurno + ")");
		}
		if (idInstitucion != null &&  !idInstitucion.isEmpty()) {
			sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");
		}
		sql.WHERE("FECHABAJA IS NULL");
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
	
	public String comboGuardiasGrupo(String idTurno, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("NOMBRE");
		sql.SELECT("IDGUARDIA");
		
		sql.FROM("SCS_GUARDIASTURNO");
		
		sql.WHERE("IDTURNO IN (" + idTurno + ")");
		sql.WHERE("IDINSTITUCION = '"+idInstitucion+"'");
		sql.WHERE("PORGRUPOS = 1");
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
		
//		SQL subquery = new SQL();
//		subquery.SELECT_DISTINCT("h.IDCONJUNTOGUARDIA");
//		subquery.FROM("SCS_HCO_CONF_PROG_CALENDARIOS h");
//		if (idInstitucion != null && !idInstitucion.isEmpty()){
//		subquery.WHERE("h.idinstitucion = " + idInstitucion);
//		}
//		if (idTurno != null && !idTurno.isEmpty()){
//		subquery.WHERE("h.idTurno IN (" + idTurno + ')');
//		}
//		if (idGuardia != null && !idGuardia.isEmpty()){
//		subquery.WHERE("h.idGuardia IN (" + idGuardia + ')');
//		}
//		
//		sql.SELECT("CG.OBSERVACIONES AS OBSERVACIONES");
//		sql.FROM("SCS_CONJUNTOGUARDIAS CG");
//		sql.WHERE("CG.IDINSTITUCION = " + idInstitucion);
//		sql.WHERE("CG.IDCONJUNTOGUARDIA IN (" + subquery + ')');
	
		return sql.toString();
	}
	
	   public String setObservaciones(String idInstitucion, String idCG, String observaciones, String idGuardia, String idTurno, String fechaIni, String fechaFin) {
//		   SQL subquery = new SQL();
//			subquery.SELECT_DISTINCT("h.IDCONJUNTOGUARDIA");
//			subquery.FROM("SCS_HCO_CONF_PROG_CALENDARIOS h");
//			if (idInstitucion != null && !idInstitucion.isEmpty()){
//			subquery.WHERE("h.idinstitucion = " + idInstitucion);
//			}
//			if (idTurno != null && !idTurno.isEmpty()){
//			subquery.WHERE("h.idTurno IN (" + idTurno + ')');
//			}
//			if (idGuardia != null && !idGuardia.isEmpty()){
//			subquery.WHERE("h.idGuardia IN (" + idGuardia + ')');
//			}
		SQL sql = new SQL();
//		sql.UPDATE("SCS_CONJUNTOGUARDIAS");
//		sql.SET("OBSERVACIONES = '" + observaciones + "'");
//		sql.WHERE("IDINSTITUCION = " + idInstitucion);
//		sql.WHERE("IDCONJUNTOGUARDIA IN (" + subquery + ')');
		   
		   
			sql.UPDATE("SCS_CALENDARIOGUARDIAS");
			sql.SET("OBSERVACIONES = '" + observaciones + "'");
			sql.WHERE("SCS_CALENDARIOGUARDIAS.FECHAINICIO = TO_DATE('" + fechaIni+ "', 'dd/MM/yyyy')");
			sql.WHERE("SCS_CALENDARIOGUARDIAS.FECHAFIN = TO_DATE('" + fechaFin + "', 'dd/MM/yyyy')");
			sql.WHERE("SCS_CALENDARIOGUARDIAS.IDINSTITUCION = " + idInstitucion);
			sql.WHERE("SCS_CALENDARIOGUARDIAS.IDTURNO IN ( " + idTurno + ")");
			sql.WHERE("SCS_CALENDARIOGUARDIAS.IDGUARDIA IN ( " + idGuardia + ")");
		return sql.toString();
	}
	   
	   public String setObservaciones2(String idInstitucion, String idCG, String observaciones, String fechaIni, String fechaFin) {

		SQL sql = new SQL();
		 
		   
			sql.UPDATE("SCS_PROG_CALENDARIOS PC");
			sql.SET("OBSERVACIONES = '" + observaciones + "'");
			sql.WHERE("PC.FECHACALINICIO = TO_DATE('" + fechaIni+ "', 'dd/MM/yyyy')");
			sql.WHERE("PC.FECHACALFIN = TO_DATE('" + fechaFin + "', 'dd/MM/yyyy')");
			sql.WHERE("PC.IDPROGCALENDARIO = " + idCG);

		return sql.toString();
	}
	   
	   
	   public String setLogName(String idInstitucion, String idCG, String observaciones, String fechaIni, String fechaFin, String logName, String idTurno, String idGuardia) {

		SQL sql = new SQL();
		 
		sql.UPDATE("SCS_CALENDARIOS_PROG_LOG VCE");
			if (logName != null && !logName.isEmpty()) {
			sql.SET("LOG_GENERACION_NAME = '" + logName + "'");
			}
//			if (fechaIni != null && !fechaIni.isEmpty()) {
//			sql.WHERE("VCE.FECHADESDE = TO_DATE('" + fechaIni+ "', 'dd/MM/yyyy')");
//			}
//			if (fechaFin != null && !fechaFin.isEmpty()) {
//			sql.WHERE("VCE.FECHAHASTA = TO_DATE('" + fechaFin + "', 'dd/MM/yyyy')");
//			}
			if (idCG != null && !idCG.isEmpty()) {
			sql.WHERE("VCE.IDCALENDARIOPROGRAMADO = " + idCG);
			}
			if (idInstitucion != null && !idInstitucion.isEmpty()) {
			sql.WHERE("VCE.IDINSTITUCION = " + idInstitucion);
			}
			if (idTurno != null && !idTurno.isEmpty()) {
			sql.WHERE("VCE.IDTURNO = " + idTurno);
			}
			if (idGuardia != null && !idGuardia.isEmpty()) {
			sql.WHERE("VCE.IDGUARDIA = " + idGuardia);
			}

		return sql.toString();
	}
	   
	   public String getLogName(String idInstitucion, String idCG, String observaciones, String fechaIni, String fechaFin, String idTurno, String idGuardia) {

		SQL sql = new SQL();
			sql.SELECT("LOG_GENERACION_NAME");
			sql.FROM("SCS_CALENDARIOS_PROG_LOG VCE");
//			if (fechaIni != null && !fechaIni.isEmpty()) {
//			sql.WHERE("VCE.FECHADESDE = TO_DATE('" + fechaIni+ "', 'dd/MM/yyyy')");
//			}
//			if (fechaFin != null && !fechaFin.isEmpty()) {
//			sql.WHERE("VCE.FECHAHASTA = TO_DATE('" + fechaFin + "', 'dd/MM/yyyy')");
//			}
			if (idCG != null && !idCG.isEmpty()) {
			sql.WHERE("VCE.IDCALENDARIOPROGRAMADO = " + idCG);
			}
//			if (idInstitucion != null && !idInstitucion.isEmpty()) {
//			sql.WHERE("VCE.IDINSTITUCION = " + idInstitucion);
//			}
			if (idTurno != null && !idTurno.isEmpty()) {
			sql.WHERE("VCE.IDTURNO = " + idTurno);
			}
			if (idGuardia != null && !idGuardia.isEmpty()) {
			sql.WHERE("VCE.IDGUARDIA = " + idGuardia);
			}

		return sql.toString();
	}
	
	public String getGuardiasFromCalendar(String idCalendar, String idInstitucion) {
		SQL sql = new SQL();
		 sql.SELECT("t.nombre as TURNO, g.nombre as GUARDIA, DECODE(c.ESTADO, 2, 'No', 'Si') as GENERADO, COUNT(*) as NUMGUARDIAS ");
	        sql.FROM("scs_calendarioguardias c");
	        sql.INNER_JOIN("scs_turno  t on c.idturno = t.idturno and c.idinstitucion = t.idinstitucion");
	        sql.INNER_JOIN("scs_guardiasturno  g on c.idguardia = g.idguardia and c.idinstitucion = g.idinstitucion");
	        sql.WHERE("c.idinstitucion = " + idInstitucion);
	        sql.WHERE("c.IdCalendarioGuardias = " + idCalendar);
	        
	    return sql.toString();
	}
	
	public String getGuardiasFromCalendarProg(String idCalendar, String idInstitucion) {
		SQL sql = new SQL();
		 sql.SELECT("t.nombre as TURNO, g.nombre as GUARDIA, DECODE(PG.ESTADO, 2, 'No', 'Si') as GENERADO, g.IDGUARDIA as IDGUARDIA, t.IDTURNO as IDTURNO");
	        sql.FROM("SCS_HCO_CONF_PROG_CALENDARIOS PG");
	        sql.INNER_JOIN("scs_turno  t on PG.idturno = t.idturno and PG.idinstitucion = t.idinstitucion");
	        sql.INNER_JOIN("scs_guardiasturno  g on PG.idguardia = g.idguardia and PG.idinstitucion = g.idinstitucion");
	        sql.WHERE("PG.idinstitucion = " + idInstitucion);
	        sql.WHERE("PG.idProgCalendario = " + idCalendar);
	        
	    return sql.toString();
	}
	
	public String getNumGuardiasFromCalendarProg(String idCalendar, String idInstitucion) {
		SQL sql = new SQL();
		 sql.SELECT("count(g.nombre) as NUMGUARDIAS");
	        sql.FROM("SCS_HCO_CONF_PROG_CALENDARIOS PG");
	        sql.INNER_JOIN("scs_guardiasturno  g on PG.idguardia = g.idguardia and PG.idinstitucion = g.idinstitucion");
	        sql.WHERE("PG.idinstitucion = " + idInstitucion);
	        sql.WHERE("PG.idProgCalendario = " + idCalendar);
	        
	    return sql.toString();
	}

	public String getTurnoCalProg(String idTurno, String idCalG, String idInstitucion){
		
		SQL sql = new SQL();
		
			if (idTurno != null && idTurno != "") {
				sql.SELECT_DISTINCT("t.nombre as turno");
				//sql.FROM("scs_guardiasturno g");
				sql.FROM("scs_turno t");
				//sql.JOIN("scs_turno t on t.idturno = g.idturno and t.idinstitucion = g.idinstitucion");
//				sql.WHERE("g.idturno = " + idTurno);
//				sql.WHERE("g.idinstitucion = " + idInstitucion);
				sql.WHERE("t.idturno = " + idTurno);
				sql.WHERE("t.idinstitucion = " + idInstitucion);
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
				//sql.JOIN("scs_turno t on t.idturno = g.idturno and t.idinstitucion = g.idinstitucion");
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
				sql.WHERE("G.FECHABAJA IS NULL");
			}
		
		
		return sql.toString();
	}
	
	public String getguardiasFromConjuntoGuardiasId(String idConjuntoGuardia, String idInstitucion){
		
		SQL sql = new SQL();	
			
		 sql.SELECT("t.nombre as TURNO, g.nombre as GUARDIA, DECODE(PG.ESTADO, 2, 'No', 'Si') as GENERADO, g.IDGUARDIA as IDGUARDIA, t.idturno as IDTURNO");
	        sql.FROM("SCS_HCO_CONF_PROG_CALENDARIOS PG");
	        sql.INNER_JOIN("scs_turno  t on PG.idturno = t.idturno and PG.idinstitucion = t.idinstitucion");
	        sql.INNER_JOIN("scs_guardiasturno  g on PG.idguardia = g.idguardia and PG.idinstitucion = g.idinstitucion");
	        sql.INNER_JOIN("SCS_CONF_CONJUNTO_GUARDIAS CG on g.idGuardia = CG.idGuardia and g.idinstitucion = CG.idinstitucion");
	        sql.WHERE("PG.idinstitucion = " + idInstitucion);
	        sql.WHERE("CG.idinstitucion = " + idInstitucion);
	        sql.WHERE("CG.IDCONJUNTOGUARDIA = " + idConjuntoGuardia);
				
		return sql.toString();
	}
	
	public String setguardiaInConjuntoGuardias(String idConjuntoGuardia, String idInstitucion, String today, GuardiaCalendarioItem item){
		
		SQL subquery = new SQL();
		if (idConjuntoGuardia != null) {
			subquery.SELECT_DISTINCT("USUMODIFICACION");
			subquery.FROM("SCS_CONF_CONJUNTO_GUARDIAS CG");
			subquery.WHERE("CG.IDCONJUNTOGUARDIA = " + idConjuntoGuardia);
		}
		SQL sql2 = new SQL();
		sql2.INSERT_INTO("SCS_CONF_CONJUNTO_GUARDIAS CG");
		if (idConjuntoGuardia != null) {
			sql2.VALUES("IDCONJUNTOGUARDIA", idConjuntoGuardia);
		}
		if (idInstitucion != null) {
			sql2.VALUES("IDINSTITUCION", idInstitucion);
		}
		if (item.getTurno() != null) {
			sql2.VALUES("IDTURNO", item.getTurno());
		}
		if (item.getGuardia()!= null) {
			sql2.VALUES("IDGUARDIA", item.getGuardia());
		}
		if (item.getOrden() != null) {
			sql2.VALUES("ORDEN", item.getOrden());
		}
		if (today != null) {
			sql2.VALUES("FECHAMODIFICACION", "'" + today + "'");
		}
		if (idConjuntoGuardia != null) {
			sql2.VALUES("USUMODIFICACION", "( " + subquery.toString() + " )");
		}
		return sql2.toString();
		
	}
	
	public String deleteguardiaFromConjuntoGuardias(String idConjuntoGuardia, String idInstitucion, String today, GuardiaCalendarioItem item){
		
		SQL subquery = new SQL();
		if (idConjuntoGuardia != null) {
			subquery.SELECT_DISTINCT("USUMODIFICACION");
			subquery.FROM("SCS_CONF_CONJUNTO_GUARDIAS CG");
			subquery.WHERE("CG.IDCONJUNTOGUARDIA = " + idConjuntoGuardia);
		}
		SQL sql2 = new SQL();
		sql2.DELETE_FROM("SCS_CONF_CONJUNTO_GUARDIAS CG");
		if (idConjuntoGuardia != null) {
			sql2.WHERE("IDCONJUNTOGUARDIA = " + idConjuntoGuardia);
		}
		if (idInstitucion != null) {
			sql2.WHERE("IDINSTITUCION = "+ idInstitucion);
		}
		if (item.getIdTurno() != null) {
			sql2.WHERE("IDTURNO = "+ item.getIdTurno());
		}
		if (item.getIdGuardia()!= null) {
			sql2.WHERE("IDGUARDIA = "+ item.getIdGuardia());
		}
//		if (item.getOrden() != null) {
//			sql2.WHERE("ORDEN = "+ item.getOrden());
//		}
//		if (idConjuntoGuardia != null) {
//			sql2.WHERE("USUMODIFICACION IN " + "( " + subquery.toString() + " )");
//		}
		return sql2.toString();
		
	}
	
public String deleteguardiaFromLog(String idConjuntoGuardia, String idInstitucion, String today, GuardiaCalendarioItem item){
		

		SQL sql2 = new SQL();
		sql2.DELETE_FROM("SCS_CALENDARIOS_PROG_LOG");
		if (idConjuntoGuardia != null) {
			sql2.WHERE("IDCONJUNTOGUARDIA = " + idConjuntoGuardia);
		}
		if (idInstitucion != null) {
			sql2.WHERE("IDINSTITUCION = "+ idInstitucion);
		}
		if (item.getIdTurno() != null) {
			sql2.WHERE("IDTURNO = "+ item.getIdTurno());
		}
		if (item.getIdGuardia()!= null) {
			sql2.WHERE("IDGUARDIA = "+ item.getIdGuardia());
		}
		return sql2.toString();
		
	}
	
	public String getConjuntoFromCalendarId(String idCalendar, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT_DISTINCT("IDCONJUNTOGUARDIA");
		sql.FROM("SCS_HCO_CONF_PROG_CALENDARIOS PG");
		sql.WHERE("PG.IDPROGCALENDARIO = " + idCalendar);
		sql.WHERE("PG.IDINSTITUCION = " + idInstitucion);
		return sql.toString();
	}
	public String setGuardiaInCalendario(String idCalendar, String idConjuntoGuardia, String idInstitucion, String today, GuardiaCalendarioItem item) {
		SQL subquery = new SQL();
		if (idConjuntoGuardia != null) {
			subquery.SELECT_DISTINCT("USUMODIFICACION");
			subquery.FROM("SCS_CONF_CONJUNTO_GUARDIAS CG");
			subquery.WHERE("CG.IDCONJUNTOGUARDIA = " + idConjuntoGuardia);
		}
		SQL sql2 = new SQL();
		sql2.INSERT_INTO("SCS_HCO_CONF_PROG_CALENDARIOS H");
		if (idCalendar != null) {
			sql2.VALUES("IDPROGCALENDARIO", idCalendar);
		}
		if (idConjuntoGuardia != null) {
			sql2.VALUES("IDCONJUNTOGUARDIA", idConjuntoGuardia);
		}
		if (idInstitucion != null) {
			sql2.VALUES("IDINSTITUCION", idInstitucion);
		}
		if (item.getTurno() != null) {
			sql2.VALUES("IDTURNO", item.getTurno());
		}
		if (item.getGuardia()!= null) {
			sql2.VALUES("IDGUARDIA", item.getGuardia());
		}
		if (item.getOrden() != null) {
			sql2.VALUES("ORDEN", item.getOrden());
		}
		if (today != null) {
			sql2.VALUES("FECHAMODIFICACION", "'" + today + "'");
		}
		if (idConjuntoGuardia != null) {
			sql2.VALUES("USUMODIFICACION", "( " + subquery.toString() + " )");
		}
		
//		if (item.getGenerado() != null) {
//			sql2.VALUES("ESTADO", DECODE(PG.ESTADO, 2, 'No', 'Si'));
//		}
		return sql2.toString();
	}
	
	public String deleteGuardiaFromCalendario(String idCalendar, String idConjuntoGuardia, String idInstitucion, String today, GuardiaCalendarioItem item) {
		SQL subquery = new SQL();
		if (idConjuntoGuardia != null) {
			subquery.SELECT_DISTINCT("USUMODIFICACION");
			subquery.FROM("SCS_CONF_CONJUNTO_GUARDIAS CG");
			subquery.WHERE("CG.IDCONJUNTOGUARDIA = " + idConjuntoGuardia);
		}
		SQL sql2 = new SQL();
		sql2.DELETE_FROM("SCS_HCO_CONF_PROG_CALENDARIOS H");
		if (idCalendar != null) {
			sql2.WHERE("IDPROGCALENDARIO = "+ idCalendar);
		}
		if (idConjuntoGuardia != null) {
			sql2.WHERE("IDCONJUNTOGUARDIA = "+ idConjuntoGuardia);
		}
		if (idInstitucion != null) {
			sql2.WHERE("IDINSTITUCION = "+ idInstitucion);
		}
		if (item.getIdTurno() != null) {
			sql2.WHERE("IDTURNO = "+ item.getIdTurno());
		}
		if (item.getIdGuardia()!= null) {
			sql2.WHERE("IDGUARDIA = "+ item.getIdGuardia());
		}
		
//		if (item.getGenerado() != null) {
//			sql2.VALUES("ESTADO", DECODE(PG.ESTADO, 2, 'No', 'Si'));
//		}
		return sql2.toString();
	}
	public String getConjuntoGuardiasIdFromGuardiaId(String idGuardia, String idInstitucion){
		
		SQL sql = new SQL();	
			
		 sql.SELECT("CG.IDCONJUNTOGUARDIA");
	        sql.FROM("SCS_CONF_CONJUNTO_GUARDIAS CG");
	        sql.JOIN("scs_guardiasturno  g on CG.idguardia = g.idguardia and CG.idinstitucion = g.idinstitucion");
	        sql.WHERE("CG.idinstitucion = " + idInstitucion);
	        sql.WHERE("g.idGuardia = " + idGuardia);
	        sql.WHERE("g.idGuardia = CG.idGuardia");
				
		return sql.toString();
	}
	
	public String getFechasProgramacionFromIdConjuntoGuardia(String idConjunto){
		SQL sql = new SQL();	
		sql.SELECT_DISTINCT("PC.FECHACALINICIO AS fechaDesde");
		sql.SELECT_DISTINCT("PC.FECHACALFIN AS fechaHasta");     
		sql.FROM("SCS_PROG_CALENDARIOS PC");
		sql.WHERE("IDCONJUNTOGUARDIA = " + idConjunto);
		return sql.toString();
		
	}
	public String getNumGuardiasCalProg(String idCalG, String idCalendario, String idInstitucion){
	
		SQL sql = new SQL();
//		sql.SELECT("COUNT(*) numGuardias FROM SCS_CONF_CONJUNTO_GUARDIAS");
//		sql.WHERE("SCS_CONF_CONJUNTO_GUARDIAS.IDCONJUNTOGUARDIA IN (" +  idCalG + ")");
		
		sql.SELECT("COUNT(*) numGuardias FROM SCS_HCO_CONF_PROG_CALENDARIOS");
		if (idCalendario != null) {
		sql.WHERE("IDPROGCALENDARIO = " + idCalendario);
		}
		if (idInstitucion != null) {
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		}
		
		return sql.toString();
	}
	
	public String getGuardiasAsociadasCalProg(String idCalG, String idCalendario, String idInstitucion){
		
		SQL sql = new SQL();
//		sql.SELECT("COUNT(*) numGuardias FROM SCS_CONF_CONJUNTO_GUARDIAS");
//		sql.WHERE("SCS_CONF_CONJUNTO_GUARDIAS.IDCONJUNTOGUARDIA IN (" +  idCalG + ")");
		
		sql.SELECT("IDGUARDIA");
		sql.FROM("SCS_HCO_CONF_PROG_CALENDARIOS");
		if (idCalendario != null) {
		sql.WHERE("IDPROGCALENDARIO = " + idCalendario);
		}
		if (idInstitucion != null) {
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		}
		
		return sql.toString();
	}
	
	public String getFacturada(String idGuardia) {
		SQL sql = new SQL();
		sql.SELECT("FACTURADO");
		sql.FROM("SCS_GUARDIASCOLEGIADO");
		if (idGuardia != null && !idGuardia.isEmpty()) {
		sql.WHERE("IDGUARDIA = " + idGuardia);
		}
		return sql.toString();
	}
	
	public String getAsistencias(String idGuardia) {
		SQL sql = new SQL();
		sql.SELECT("COUNT(*) numasistencias");
		sql.FROM("SCS_ASISTENCIA");
		if (idGuardia != null && !idGuardia.isEmpty()) {
		sql.WHERE("IDGUARDIA = " + idGuardia);
		}
		return sql.toString();
	}
	

	public String getNumGuardiasCalProg2(String idInstitucion, String idTurno, String idGuardia, String idCalendarioGuardias){
		SQL sql = new SQL();
		sql.SELECT("COUNT(*) numGuardias FROM SCS_CABECERAGUARDIAS");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDTURNO = " + idTurno);
		sql.WHERE("IDGUARDIA = " + idGuardia);
		sql.WHERE("IDCALENDARIOGUARDIAS = " + idCalendarioGuardias);
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
	
	
	
	public String getCalendariosGeneradosSigaClassique(CalendariosProgDatosEntradaItem calendarioItem, String idInstitucion) {

		
		String consulta_siga_classique = "SELECT * FROM ( SELECT T.NOMBRE NOMBRETURNO,  GT.NOMBRE NOMBREGUARDIA,  CG.FECHAINICIO,  CG.FECHAFIN,  CG.OBSERVACIONES,  DECODE(GT.NUMEROLETRADOSGUARDIA,  0,  " +
				"DECODE((SELECT count(1) TOTAL  FROM SCS_CALENDARIOGUARDIAS CALG  WHERE CALG.IDINSTITUCION = CG.IDINSTITUCION  AND CALG.IDTURNO = CG.IDTURNO  AND CALG.IDGUARDIA = CG.IDGUARDIA),  " +
				"0, 2, 3 ),  DECODE((SELECT count(1) TOTAL  FROM SCS_CABECERAGUARDIAS CAG  WHERE CAG.IDINSTITUCION = CG.IDINSTITUCION  AND CAG.IDTURNO = CG.IDTURNO  AND CAG.IDGUARDIA = CG.IDGUARDIA  " +
				"AND CAG.IDCALENDARIOGUARDIAS =  CG.IDCALENDARIOGUARDIAS),  0, 2, 3 )) ESTADO ,CG.IDTURNO ,CG.IDGUARDIA ,CG.IDCALENDARIOGUARDIAS ,CG.IDINSTITUCION  FROM SCS_CALENDARIOGUARDIAS CG, " +
				"SCS_GUARDIASTURNO GT, SCS_TURNO T  WHERE CG.IDINSTITUCION = GT.IDINSTITUCION  AND CG.IDTURNO = GT.IDTURNO  AND CG.IDGUARDIA = GT.IDGUARDIA  AND GT.IDINSTITUCION = T.IDINSTITUCION  " +
				"AND GT.IDTURNO = T.IDTURNO  AND CG.IDINSTITUCION = " + idInstitucion + " UNION  SELECT T.NOMBRE NOMBRETURNO,  GT.NOMBRE NOMBREGUARDIA,PC.FECHACALINICIO FECHAINICIO,PC.FECHACALFIN FECHAFIN , " +
				"'Conjunto de guardias: '||CG.DESCRIPCION OBSERVACIONES,  NVL(HPC.ESTADO, 0) ESTADO ,null IDTURNO ,null IDGUARDIA ,null IDCALENDARIOGUARDIAS ,PC.IDINSTITUCION  " +
				"FROM SCS_PROG_CALENDARIOS          PC,  SCS_HCO_CONF_PROG_CALENDARIOS HPC,  SCS_CONJUNTOGUARDIAS          CG,  SCS_GUARDIASTURNO             GT,  SCS_TURNO                     T  " +
				"WHERE PC.IDCONJUNTOGUARDIA = CG.IDCONJUNTOGUARDIA  AND PC.IDINSTITUCION = CG.IDINSTITUCION  AND HPC.IDINSTITUCION = GT.IDINSTITUCION  AND HPC.IDTURNO = GT.IDTURNO  " +
				"AND HPC.IDGUARDIA = GT.IDGUARDIA  AND GT.IDINSTITUCION = T.IDINSTITUCION  AND GT.IDTURNO = T.IDTURNO  AND HPC.IDINSTITUCION = PC.IDINSTITUCION  " +
				"AND HPC.IDPROGCALENDARIO = PC.IDPROGCALENDARIO  AND not exists (select * from SCS_CALENDARIOGUARDIAS CAL where HPC.IDTURNO = CAL.IDTURNO AND HPC.IDGUARDIA = CAL.IDGUARDIA " +
				"AND HPC.IDINSTITUCION = CAL.IDINSTITUCION AND PC.Fechacalinicio = CAL.Fechainicio AND PC.Fechacalfin = CAL.Fechafin)  AND HPC.IDINSTITUCION = " + idInstitucion + " )ORDER BY NOMBRETURNO, " +
				"NOMBREGUARDIA,FECHAINICIO,FECHAFIN ";
		//return sql.toString();
				return consulta_siga_classique;

	}
	
	public String getCalendariosProgramadosSigaClassique(CalendariosProgDatosEntradaItem calendarioItem, String idInstitucion) {

		SQL sql2 = new SQL();
		sql2.SELECT("1");
		sql2.FROM("SCS_HCO_CONF_PROG_CALENDARIOS HPC");
		sql2.WHERE("HPC.IDINSTITUCION = PC.IDINSTITUCION AND HPC.IDPROGCALENDARIO = PC.IDPROGCALENDARIO AND HPC.IDCONJUNTOGUARDIA = PC.IDCONJUNTOGUARDIA");
		if (calendarioItem.getIdTurno() != null && calendarioItem.getIdTurno() != "") {
		sql2.WHERE("HPC.IDTURNO IN ( " + calendarioItem.getIdTurno() + " )");
		}
		if (calendarioItem.getIdGuardia() != null && calendarioItem.getIdGuardia() != "") {
		sql2.WHERE("HPC.IDGUARDIA IN (" + calendarioItem.getIdGuardia()+" ) ");
		//sql2.ORDER_BY("HPC.ORDEN");
		}
		
		SQL sqlGuardia = new SQL();
//		if (idGuardia != null && idGuardia != "") {
			sqlGuardia.SELECT_DISTINCT("g.nombre as guardia");
			sqlGuardia.FROM("scs_guardiasturno g");
//			sqlGuardia.WHERE("g.idguardia = " + idGuardia);
			sqlGuardia.WHERE("g.idguardia = CG.IDGUARDIA");
			sqlGuardia.WHERE("g.idinstitucion = " + idInstitucion);
			
//		}else {
//			sqlGuardia.SELECT_DISTINCT("g.nombre as guardia");
//			sqlGuardia.FROM("scs_guardiasturno g");
//			sqlGuardia.JOIN("SCS_CONF_CONJUNTO_GUARDIAS CG on g.idGuardia = CG.idGuardia and g.idinstitucion = CG.idinstitucion");
//			sqlGuardia.WHERE("CG.IDCONJUNTOGUARDIA = " + idCalG);
//			sqlGuardia.WHERE("CG.idinstitucion = " + idInstitucion);
//			sqlGuardia.WHERE("g.idGuardia = CG.idGuardia");
//			sqlGuardia.WHERE("G.IDtURNO = " + idTurno);
//			sqlGuardia.WHERE("G.FECHABAJA IS NULL");
//		}
			
//			if (idTurno != null && idTurno != "") {
			SQL sqlTurno = new SQL();
				sqlTurno.SELECT_DISTINCT("t.nombre as turno");
				sqlTurno.FROM("scs_turno t");
				sqlTurno.WHERE("t.idturno = CG.IDTURNO");
				sqlTurno.WHERE("t.idinstitucion = " + idInstitucion);
				sqlTurno.WHERE("t.FECHABAJA IS NULL");
//			}
//			else {
//
//				sql.SELECT_DISTINCT("t.nombre as turno");
//				sql.FROM("SCS_CONF_CONJUNTO_GUARDIAS CG");
//				sql.JOIN("scs_turno t on t.idturno = CG.idturno and t.idinstitucion = CG.idinstitucion");
//				sql.WHERE("CG.IDCONJUNTOGUARDIA = " + idCalG);
//				sql.WHERE("CG.idinstitucion = " + idInstitucion);
//				sql.WHERE("t.FECHABAJA IS NULL");
//			}
				SQL sqlNumGuardias = new SQL();
				sqlNumGuardias.SELECT("COUNT(*) numGuardias FROM SCS_HCO_CONF_PROG_CALENDARIOS");
				//if (idCalendario != null) {
				sqlNumGuardias.WHERE("IDPROGCALENDARIO = PC.IDPROGCALENDARIO");
//				}
//				if (idInstitucion != null) {
				sqlNumGuardias.WHERE("IDINSTITUCION = " + idInstitucion);
				//}
				
				SQL sqlGuardColeg = new SQL();
				sqlGuardColeg.SELECT("IDGUARDIA");
				sqlGuardColeg.FROM("SCS_HCO_CONF_PROG_CALENDARIOS");
				sqlGuardColeg.WHERE("IDPROGCALENDARIO = PC.IDPROGCALENDARIO");
				if (idInstitucion != null) {
					sqlGuardColeg.WHERE("IDINSTITUCION = " + idInstitucion);
				}
				SQL sqlFact = new SQL();
				sqlFact.SELECT("FACTURADO");
				sqlFact.FROM("SCS_GUARDIASCOLEGIADO");
				sqlFact.WHERE("IDGUARDIA IN ( " + sqlGuardColeg + ")");
				sqlFact.WHERE("rownum <= 1");
				SQL sqlAs = new SQL();
				sqlAs.SELECT("COUNT(*) numasistencias");
				sqlAs.FROM("SCS_ASISTENCIA");
				sqlAs.WHERE("IDGUARDIA IN ( " + sqlGuardColeg + ")");
//		SELECT  CG.IDTURNO as idTurno, CG.IDGUARDIA as idGuardia, PC.IDPROGCALENDARIO,  PC.IDCONJUNTOGUARDIA ,  PC.IDINSTITUCION  ,  PC.FECHAPROGRAMACION ,  PC.FECHACALINICIO  ,  PC.FECHACALFIN      ,  
//		PC.ESTADO,GG.DESCRIPCION NOMBRECONJUNTOGUARDIAS  FROM SCS_CONJUNTOGUARDIAS GG,SCS_PROG_CALENDARIOS PC, SCS_CONF_CONJUNTO_GUARDIAS CG
//		WHERE (GG.IDINSTITUCION = PC.IDINSTITUCION  AND GG.IDCONJUNTOGUARDIA = PC.IDCONJUNTOGUARDIA AND PC.IDINSTITUCION = 2005 AND CG.IDTURNO IN ( 799 ) AND CG.IDGUARDIA = 681 AND EXISTS (SELECT 1
//		FROM SCS_HCO_CONF_PROG_CALENDARIOS HPC
//		WHERE (HPC.IDINSTITUCION = PC.IDINSTITUCION AND HPC.IDPROGCALENDARIO = PC.IDPROGCALENDARIO AND HPC.IDCONJUNTOGUARDIA = PC.IDCONJUNTOGUARDIA AND HPC.IDTURNO IN ( 799 ) AND HPC.IDGUARDIA = 681) ) AND rownum <= 200
//		ORDER BY PC.FECHAPROGRAMACION
		SQL sql = new SQL();
		sql.SELECT("CG.IDINSTITUCION AS INSTITUCION, CG.IDTURNO as idTurno, CG.IDGUARDIA as idGuardia, PC.IDPROGCALENDARIO as idCalendarioProgramado,  PC.IDCONJUNTOGUARDIA AS idCalG,  PC.IDINSTITUCION  , TO_CHAR(PC.FECHAPROGRAMACION,'dd/MM/yyyy HH24:mi:ss') AS FECHAPROGRAMACION,  PC.FECHACALINICIO  AS fechaDesde,  PC.FECHACALFIN   AS fechaHasta   ,  PC.ESTADO AS estado, GG.DESCRIPCION AS listaGuardias, DECODE(PC.ESTADO, 2, 'No', 'Si') AS GENERADO, COALESCE(PC.observaciones, '') AS OBSERVACIONES, ( " + sqlGuardia + " ) as guardia, ( " + sqlTurno + " ) as turno, ( " + sqlNumGuardias + " ) as numGuardias, ( " + sqlFact + " ) as facturado, ( " + sqlAs + " ) as asistenciasAsociadas");
		sql.FROM("scs_conjuntoguardias         gg JOIN scs_prog_calendarios         pc ON gg.idinstitucion = pc.idinstitucion "
				+ " AND gg.idconjuntoguardia = pc.idconjuntoguardia"
	    + " JOIN scs_conf_conjunto_guardias   cg ON gg.idinstitucion = cg.idinstitucion"
		+ " AND gg.idconjuntoguardia = cg.idconjuntoguardia");
		
		if (idInstitucion != null && idInstitucion != "") {
		sql.WHERE("PC.IDINSTITUCION = " + idInstitucion);
		}
		if (calendarioItem.getIdConjuntoGuardia() != null && calendarioItem.getIdConjuntoGuardia() != "") {
		sql.WHERE("PC.IDCONJUNTOGUARDIA IN  (" +  calendarioItem.getIdConjuntoGuardia()+")");
		}
		if (calendarioItem.getEstado() != null && calendarioItem.getEstado() != "") {
		sql.WHERE("PC.ESTADO IN (" + calendarioItem.getEstado()+")");
		}
//		else {
//		sql.WHERE("PC.ESTADO  IN (1 , 5)");
//		}
		if (calendarioItem.getFechaProgramadaDesde() != null && calendarioItem.getFechaProgramadaDesde() != "") {
		sql.WHERE("PC.FECHAPROGRAMACION >= "+ "TO_DATE('" +  calendarioItem.getFechaProgramadaDesde() + "','dd/MM/yyyy HH24:mi:ss')");
		}
		if (calendarioItem.getFechaProgramadaHasta() != null && calendarioItem.getFechaProgramadaHasta() != "") {
		sql.WHERE("PC.FECHAPROGRAMACION <= " + "TO_DATE('" +  calendarioItem.getFechaProgramadaHasta() + "','dd/MM/yyyy HH24:mi:ss')");
		}
		if (calendarioItem.getFechaCalendarioDesde() != null && calendarioItem.getFechaCalendarioDesde() != "") {
		sql.WHERE("PC.FECHACALINICIO >= " + "TO_DATE('" + calendarioItem.getFechaCalendarioDesde()+ "','DD/MM/YYYY')");
		}
		if (calendarioItem.getFechaCalendarioHasta() != null && calendarioItem.getFechaCalendarioHasta() != "") {
			sql.WHERE("PC.FECHACALINICIO <= " + "TO_DATE('" + calendarioItem.getFechaCalendarioHasta() + "','DD/MM/YYYY')");
			}
//		if (calendarioItem.getFechaCalendarioHasta() != null && calendarioItem.getFechaCalendarioHasta() != "") {
//		sql.WHERE("PC.FECHACALFIN <= " + "TO_DATE('" + calendarioItem.getFechaCalendarioHasta() + "','DD/MM/YYYY')");
//		}
		if (calendarioItem.getIdTurno() != null && calendarioItem.getIdTurno() != "") {
		sql.WHERE("CG.IDTURNO IN ( " + calendarioItem.getIdTurno() + " )");
		}
		if (calendarioItem.getIdGuardia() != null && calendarioItem.getIdGuardia() != "") {
		sql.WHERE("CG.IDGUARDIA IN (" + calendarioItem.getIdGuardia()+")");
		}
		if (idInstitucion != null && idInstitucion != "") {
		sql.WHERE("CG.IDINSTITUCION = " + idInstitucion);
		}
		sql.WHERE("EXISTS (" + sql2 +" )");
		//sql.WHERE("rownum <= 200");
		//sql.ORDER_BY("(PC.FECHAPROGRAMACION, PC.FECHACALINICIO, PC.FECHACALFIN) desc");
		sql.ORDER_BY("PC.FECHACALINICIO desc, PC.FECHACALFIN desc, PC.FECHAPROGRAMACION desc");
//		String consulta_siga_classique = "SELECT HPC.IDTURNO as idTurno, HPC.IDGUARDIA as idGuardia, PC.IDPROGCALENDARIO as idCalendarioProgramado,  PC.IDCONJUNTOGUARDIA AS idCalG,  PC.IDINSTITUCION  ,  PC.FECHAPROGRAMACION AS FECHAPROGRAMACION,  PC.FECHACALINICIO  AS fechaDesde,  PC.FECHACALFIN   AS fechaHasta   ,  PC.ESTADO AS GENERADO, "+
//		" GG.DESCRIPCION AS listaGuardias  FROM SCS_CONJUNTOGUARDIAS GG,SCS_PROG_CALENDARIOS PC, SCS_HCO_CONF_PROG_CALENDARIOS HPC  "
//		+ "WHERE GG.IDINSTITUCION = PC.IDINSTITUCION  AND GG.IDCONJUNTOGUARDIA = PC.IDCONJUNTOGUARDIA " +
//		" AND PC.IDINSTITUCION = " + idInstitucion +  " AND PC.IDCONJUNTOGUARDIA =  " +  calendarioItem.getIdConjuntoGuardia() + " AND PC.ESTADO = " + calendarioItem.getEstado() + " AND PC.FECHAPROGRAMACION >= ' " + calendarioItem.getFechaProgramadaDesde() + " ' " + "AND PC.FECHAPROGRAMACION <= ' " + calendarioItem.getFechaProgramadaHasta() + "' " + 
//		" AND PC.FECHACALINICIO >= ' " + calendarioItem.getFechaCalendarioDesde() + "'" +  " AND PC.FECHACALFIN <= ' " + calendarioItem.getFechaCalendarioHasta() + "'" + " AND EXISTS (SELECT 1 FROM SCS_HCO_CONF_PROG_CALENDARIOS HPC WHERE HPC.IDINSTITUCION = PC.IDINSTITUCION " + 
//		" AND HPC.IDPROGCALENDARIO = PC.IDPROGCALENDARIO  AND HPC.IDTURNO IN ( " + calendarioItem.getIdTurno() + " ) AND HPC.IDGUARDIA = " + calendarioItem.getIdGuardia() + " )  ORDER BY PC.FECHAPROGRAMACION  ";
//		return consulta_siga_classique;
		
		return sql.toString();
	}
	
	public String getLastCalendariosProgramadosSigaClassique(CalendariosProgDatosEntradaItem calendarioItem, String idInstitucion) {

		SQL sql2 = new SQL();
		sql2.SELECT("1");
		sql2.FROM("SCS_HCO_CONF_PROG_CALENDARIOS HPC");
		sql2.WHERE("HPC.IDINSTITUCION = PC.IDINSTITUCION AND HPC.IDPROGCALENDARIO = PC.IDPROGCALENDARIO AND HPC.IDCONJUNTOGUARDIA = PC.IDCONJUNTOGUARDIA");
		if (calendarioItem.getIdTurno() != null && calendarioItem.getIdTurno() != "") {
		sql2.WHERE("HPC.IDTURNO IN ( " + calendarioItem.getIdTurno() + " )");
		}
		if (calendarioItem.getIdGuardia() != null && calendarioItem.getIdGuardia() != "") {
		sql2.WHERE("HPC.IDGUARDIA = " + calendarioItem.getIdGuardia());

		}
		
		SQL sqlGuardia = new SQL();

			sqlGuardia.SELECT_DISTINCT("g.nombre as guardia");
			sqlGuardia.FROM("scs_guardiasturno g");
			sqlGuardia.WHERE("g.idguardia = CG.IDGUARDIA");
			sqlGuardia.WHERE("g.idinstitucion = " + idInstitucion);

			SQL sqlTurno = new SQL();
				sqlTurno.SELECT_DISTINCT("t.nombre as turno");
				sqlTurno.FROM("scs_turno t");
				sqlTurno.WHERE("t.idturno = CG.IDTURNO");
				sqlTurno.WHERE("t.idinstitucion = " + idInstitucion);
				sqlTurno.WHERE("t.FECHABAJA IS NULL");

				SQL sqlNumGuardias = new SQL();
				sqlNumGuardias.SELECT("COUNT(*) numGuardias FROM SCS_HCO_CONF_PROG_CALENDARIOS");
				sqlNumGuardias.WHERE("IDPROGCALENDARIO = PC.IDPROGCALENDARIO");
				sqlNumGuardias.WHERE("IDINSTITUCION = " + idInstitucion);
				
				SQL sqlGuardColeg = new SQL();
				sqlGuardColeg.SELECT("IDGUARDIA");
				sqlGuardColeg.FROM("SCS_HCO_CONF_PROG_CALENDARIOS");
				sqlGuardColeg.WHERE("IDPROGCALENDARIO = PC.IDPROGCALENDARIO");
				if (idInstitucion != null) {
					sqlGuardColeg.WHERE("IDINSTITUCION = " + idInstitucion);
				}
				SQL sqlFact = new SQL();
				sqlFact.SELECT("FACTURADO");
				sqlFact.FROM("SCS_GUARDIASCOLEGIADO");
				sqlFact.WHERE("IDGUARDIA IN ( " + sqlGuardColeg + ")");
				sqlFact.WHERE("rownum <= 1");
				SQL sqlAs = new SQL();
				sqlAs.SELECT("COUNT(*) numasistencias");
				sqlAs.FROM("SCS_ASISTENCIA");
				sqlAs.WHERE("IDGUARDIA IN ( " + sqlGuardColeg + ")");

		SQL sql = new SQL();
		sql.SELECT("CG.IDINSTITUCION AS INSTITUCION, CG.IDTURNO as idTurno, CG.IDGUARDIA as idGuardia, PC.IDPROGCALENDARIO as idCalendarioProgramado,  PC.IDCONJUNTOGUARDIA AS idCalG,  PC.IDINSTITUCION  , TO_CHAR(PC.FECHAPROGRAMACION,'dd/MM/yyyy HH24:mi:ss') AS FECHAPROGRAMACION,  PC.FECHACALINICIO  AS fechaDesde,  PC.FECHACALFIN   AS fechaHasta   ,  PC.ESTADO AS estado, GG.DESCRIPCION AS listaGuardias, DECODE(PC.ESTADO, 2, 'No', 'Si') AS GENERADO, COALESCE(PC.observaciones, '') AS OBSERVACIONES, ( " + sqlGuardia + " ) as guardia, ( " + sqlTurno + " ) as turno, ( " + sqlNumGuardias + " ) as numGuardias, ( " + sqlFact + " ) as facturado, ( " + sqlAs + " ) as asistenciasAsociadas, PC.FECHAMODIFICACION");
		sql.FROM("scs_conjuntoguardias         gg JOIN scs_prog_calendarios         pc ON gg.idinstitucion = pc.idinstitucion "
				+ " AND gg.idconjuntoguardia = pc.idconjuntoguardia"
	    + " JOIN scs_conf_conjunto_guardias   cg ON gg.idinstitucion = cg.idinstitucion"
		+ " AND gg.idconjuntoguardia = cg.idconjuntoguardia");
		
		if (idInstitucion != null && idInstitucion != "") {
		sql.WHERE("PC.IDINSTITUCION = " + idInstitucion);
		}
		if (calendarioItem.getIdConjuntoGuardia() != null && calendarioItem.getIdConjuntoGuardia() != "") {
		sql.WHERE("PC.IDCONJUNTOGUARDIA =  " +  calendarioItem.getIdConjuntoGuardia());
		}
		if (calendarioItem.getEstado() != null && calendarioItem.getEstado() != "") {
		sql.WHERE("PC.ESTADO = " + calendarioItem.getEstado());
		}
		if (calendarioItem.getFechaCalendarioDesde() != null && calendarioItem.getFechaCalendarioDesde() != "") {
		sql.WHERE("PC.FECHACALINICIO >= " + "TO_DATE('" + calendarioItem.getFechaCalendarioDesde()+ "','DD/MM/YYYY')");
		}
		if (calendarioItem.getFechaCalendarioHasta() != null && calendarioItem.getFechaCalendarioHasta() != "") {
			sql.WHERE("PC.FECHACALINICIO <= " + "TO_DATE('" + calendarioItem.getFechaCalendarioHasta() + "','DD/MM/YYYY')");
		}
		if (calendarioItem.getIdTurno() != null && calendarioItem.getIdTurno() != "") {
		sql.WHERE("CG.IDTURNO IN ( " + calendarioItem.getIdTurno() + " )");
		}
		if (calendarioItem.getIdGuardia() != null && calendarioItem.getIdGuardia() != "") {
		sql.WHERE("CG.IDGUARDIA = " + calendarioItem.getIdGuardia());
		}
		if (idInstitucion != null && idInstitucion != "") {
		sql.WHERE("CG.IDINSTITUCION = " + idInstitucion);
		}
		sql.WHERE("EXISTS (" + sql2 +" )");
		sql.ORDER_BY("PC.FECHAPROGRAMACION desc, PC.FECHAMODIFICACION desc");

		SQL mainSql = new SQL();
		mainSql.SELECT("*");
		mainSql.FROM("( " + sql + " )");
		mainSql.WHERE("rownum = 1");
		return mainSql.toString();
	}
	
	public String getAllCalendariosProgramadosSigaClassiquePendiente () {

		SQL sql2 = new SQL();
		sql2.SELECT("1");
		sql2.FROM("SCS_HCO_CONF_PROG_CALENDARIOS HPC");
		sql2.WHERE("HPC.IDINSTITUCION = PC.IDINSTITUCION AND HPC.IDPROGCALENDARIO = PC.IDPROGCALENDARIO AND HPC.IDCONJUNTOGUARDIA = PC.IDCONJUNTOGUARDIA");
		SQL sql = new SQL();
		sql.SELECT("PC.IDINSTITUCION AS IDINSTITUCION, CG.IDTURNO as idTurno, CG.IDGUARDIA as idGuardia, PC.IDPROGCALENDARIO as idCalendarioProgramado,  PC.IDCONJUNTOGUARDIA AS idCalG,  PC.IDINSTITUCION  ,  PC.FECHAPROGRAMACION AS FECHAPROGRAMACION,  PC.FECHACALINICIO  AS fechaDesde,  PC.FECHACALFIN   AS fechaHasta   ,  PC.ESTADO AS estado, GG.DESCRIPCION AS listaGuardias, DECODE(PC.ESTADO, 2, 'No', 'Si') AS GENERADO, PC.observaciones AS OBSERVACIONES");
		sql.FROM("SCS_CONJUNTOGUARDIAS GG,SCS_PROG_CALENDARIOS PC, SCS_CONF_CONJUNTO_GUARDIAS CG");
		sql.WHERE("GG.IDINSTITUCION = PC.IDINSTITUCION  AND GG.IDCONJUNTOGUARDIA = PC.IDCONJUNTOGUARDIA ");
		sql.WHERE("PC.ESTADO = 5"); // pendiente = reprogramado = estado 5
		sql.WHERE("EXISTS (" + sql2 +" )");
		sql.WHERE("rownum <= 200");
		sql.ORDER_BY("PC.FECHAPROGRAMACION");
		
		return sql.toString();
	}
	
	public String updateCalendarioProgramado1(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion) {
		SQL sql = new SQL();
		sql.UPDATE("GG");
		if (calendarioItem.getListaGuardias() != null) {
			sql.SET("GG.DESCRIPCION = '" + calendarioItem.getListaGuardias() + "'");
		}
		sql.FROM("SCS_CONJUNTOGUARDIAS GG");
		sql.JOIN("SCS_PROG_CALENDARIOS PC ON GG.IDINSTITUCION = PC.IDINSTITUCION AND GG.IDCONJUNTOGUARDIA = PC.IDCONJUNTOGUARDIA");
		sql.WHERE("PC.IDPROGCALENDARIO = " + calendarioItem.getIdCalendarioProgramado());
		return sql.toString();

	}
	
	public String updateCalendarioProgramado2(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_PROG_CALENDARIOS PC");
		if (calendarioItem.getIdCalendarioProgramado() != null) {
			sql.SET("PC.IDPROGCALENDARIO = " + calendarioItem.getIdCalendarioProgramado());
		}
		if (calendarioItem.getIdCalG() != null) {
			sql.SET("PC.IDCONJUNTOGUARDIA  = " + calendarioItem.getIdCalG());
		}
		if (idInstitucion != null) {
			sql.SET("PC.IDINSTITUCION = " + idInstitucion);
		}
		if (calendarioItem.getFechaProgramacion() != null) {
			sql.SET("PC.FECHAPROGRAMACION = '" + calendarioItem.getFechaProgramacion()+ "'"); 
		}
		if (calendarioItem.getFechaDesde() != null) {
			sql.SET("PC.FECHACALINICIO = '" + calendarioItem.getFechaDesde() + "'");
		}
		if (calendarioItem.getFechaHasta() != null) {
			sql.SET("PC.FECHACALFIN = '" + calendarioItem.getFechaHasta() + "'");  
		}
		if (calendarioItem.getEstado() != null) {
			sql.SET("PC.ESTADO = " + calendarioItem.getEstado());
		}
		sql.WHERE("PC.IDPROGCALENDARIO = " + calendarioItem.getIdCalendarioProgramado());
		return sql.toString();

	}
	
	
	public String updateConfCalendarioProgramado2(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_HCO_CONF_PROG_CALENDARIOS HCO");
			sql.SET("HCO.FECHAMODIFICACION = SYSDATE"); 

		if (calendarioItem.getEstado() != null) {
			sql.SET("HCO.ESTADO = " + calendarioItem.getEstado());
		}
		sql.WHERE("HCO.IDPROGCALENDARIO = " + calendarioItem.getIdCalendarioProgramado());
		if (idInstitucion != null) {
			sql.WHERE("HCO.IDINSTITUCION = " + idInstitucion);
		}
		if (calendarioItem.getIdTurno() != null) {
			sql.WHERE("HCO.IDTURNO = '" + calendarioItem.getIdTurno()+ "'"); 
		}
		if (calendarioItem.getIdGuardia() != null) {
			sql.WHERE("HCO.IDGUARDIA = '" + calendarioItem.getIdGuardia()+ "'"); 
		}	
		if (calendarioItem.getIdCalG() != null) {
			sql.WHERE("HCO.IDCONJUNTOGUARDIA  = " + calendarioItem.getIdCalG());
		}
		return sql.toString();

	}
	
	public String updateCalendarioProgramado3(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion) {
//		SQL sql = new SQL();
//		sql.UPDATE("CG");
//
//		if (calendarioItem.getIdTurno() != null) {
//			sql.SET("CG.IDTURNO = " + calendarioItem.getIdTurno());
//		}
//		if (calendarioItem.getIdGuardia() != null) {
//			sql.SET("CG.IDGUARDIA = " + calendarioItem.getIdGuardia());
//		}
//		sql.FROM("SCS_CONF_CONJUNTO_GUARDIAS CG");
//		sql.JOIN("SCS_PROG_CALENDARIOS PC ON CG.IDINSTITUCION = PC.IDINSTITUCION AND CG.IDCONJUNTOGUARDIA = PC.IDCONJUNTOGUARDIA");
//		sql.WHERE("PC.IDPROGCALENDARIO = " + calendarioItem.getIdCalendarioProgramado());
//		return sql.toString();
		
		SQL sql = new SQL();
		sql.UPDATE("SCS_CALENDARIOGUARDIAS CG");

		if (calendarioItem.getIdTurno() != null) {
			sql.SET("CG.FECHAINICIO = '"+ calendarioItem.getFechaDesde()+ "'");
		}
		if (calendarioItem.getIdGuardia() != null) {
			sql.SET("CG.FECHAFIN = '"+ calendarioItem.getFechaHasta() + "'");
		}
		//sql.FROM("SCS_CALENDARIOGUARDIAS");
		
		sql.WHERE("CG.IDTURNO =" + calendarioItem.getIdTurno());
		sql.WHERE("CG.IDGUARDIA =" + calendarioItem.getIdGuardia());
		return sql.toString();

	}
	
	public String nextIdCalprog() {
		return "select SEQ_SCSPROGCALENDARIOS.Nextval as ID from dual";
	}
	
	
	public String generateCalendarioProgramado(String idCalculado, DatosCalendarioProgramadoItem calendarioItem, String idInstitucion, String today, String usuModif) {
		
//		SQL sub2 = new SQL();
//		sub2.SELECT_DISTINCT("SEQ_SCSPROGCALENDARIOS.NEXTVAL");
//		sub2.FROM("DUAL");
		
		SQL sub3 = new SQL();
		sub3.SELECT_DISTINCT("MAX(FECHAMODIFICACION)");
		sub3.FROM("SCS_PROG_CALENDARIOS");
		sub3.WHERE("IDINSTITUCION = " + idInstitucion);
		
		SQL sub = new SQL();
		sub.SELECT_DISTINCT("USUMODIFICACION");
		sub.FROM("SCS_PROG_CALENDARIOS");
		sub.WHERE("IDINSTITUCION = " + idInstitucion);
		sub.WHERE("FECHAMODIFICACION = ( " + sub3.toString() + " )");
		
		SQL sql = new SQL();

//		sql.INSERT_INTO("SCS_CONJUNTOGUARDIAS");
//		if (calendarioItem.getListaGuardias() != null) {
//			sql.VALUES("DESCRIPCION", calendarioItem.getListaGuardias());
//		}
		sql.INSERT_INTO("SCS_PROG_CALENDARIOS");
//		if (calendarioItem.getIdCalendarioProgramado() != null) {
			sql.VALUES("IDPROGCALENDARIO", idCalculado);
//		}
		if (calendarioItem.getIdCalG() != null) {
			sql.VALUES("IDCONJUNTOGUARDIA", calendarioItem.getIdCalG());
		}
		if (idInstitucion != null) {
			sql.VALUES("IDINSTITUCION", idInstitucion);
		}
		if (calendarioItem.getFechaProgramacion() != null) {
			sql.VALUES("FECHAPROGRAMACION", "TO_DATE('" + calendarioItem.getFechaProgramacion() + "','DD/MM/YYYY HH24:MI:SS')");
		}
		if (calendarioItem.getFechaDesde() != null) {
			sql.VALUES("FECHACALINICIO", "TO_DATE('" + calendarioItem.getFechaDesde() + "','DD/MM/YYYY')");
		}
		if (calendarioItem.getFechaHasta() != null) {
			sql.VALUES("FECHACALFIN", "TO_DATE('" + calendarioItem.getFechaHasta() + "','DD/MM/YYYY')");
		}
		if (calendarioItem.getEstado() != null) {
			sql.VALUES("ESTADO", "'" + calendarioItem.getEstado() + "'");
		}
		if (calendarioItem.getObservaciones() != null) {
			sql.VALUES("OBSERVACIONES", "'" + calendarioItem.getObservaciones() + "'");
		}
		if (today != null) {
			sql.VALUES("FECHAMODIFICACION", "TO_DATE('" + today + "','DD/MM/YYYY')");
		}
		if (usuModif != null) {
			sql.VALUES("USUMODIFICACION", usuModif );
		}
		sql.VALUES("IDFICHEROCALENDARIO", "null");
		

//		insert(",SCS_PROG_CALENDARIOS PC, SCS_CONF_CONJUNTO_GUARDIAS CG");
//
//		sql.SET("CG.IDTURNO = " + calendarioItem.getIdTurno());
//		sql.SET("CG.IDGUARDIA = " + calendarioItem.getIdGuardia());
//		sql.SET("PC.IDINSTITUCION = " + idInstitucion);
//		sql.SET("PC.FECHAPROGRAMACION = " + calendarioItem.getFechaProgramacion()); 
//		sql.SET("PC.FECHACALINICIO = " + calendarioItem.getFechaDesde());
//		sql.SET("PC.FECHACALFIN = " + calendarioItem.getFechaHasta());      
//		sql.SET("PC.ESTADO = " + calendarioItem.getEstado());
		
		
//		INSERT INTO SCS_PROG_CALENDARIOS
//		 (IDPROGCALENDARIO, IDCONJUNTOGUARDIA, IDINSTITUCION, FECHAPROGRAMACION, FECHACALINICIO, FECHACALFIN, ESTADO, FECHAMODIFICACION, USUMODIFICACION, IDFICHEROCALENDARIO)
//		VALUES (( SELECT MAX(IDPROGCALENDARIO)
//		FROM SCS_PROG_CALENDARIOS ) + 1, 643, 2005, TO_DATE('17/06/2021','DD/MM/YYYY'), TO_DATE('17/06/2021','DD/MM/YYYY'), TO_DATE('17/06/2021','DD/MM/YYYY'), 1, TO_DATE('17/06/2021','DD/MM/YYYY'), ( SELECT MAX(USUMODIFICACION)
//		FROM SCS_PROG_CALENDARIOS
//		WHERE (IDINSTITUCION = 2005) ), null)

		return sql.toString();

	}

	public String getLastProgramacion( String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT_DISTINCT("MAX(IDPROGCALENDARIO)");
		sql.FROM("SCS_PROG_CALENDARIOS");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		return sql.toString();
	}

	public String getLastCalendar( String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT_DISTINCT("MAX(IDCALENDARIOGUARDIAS)");
		sql.FROM("SCS_CALENDARIOGUARDIAS");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
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
	
	public String searchLetradosInscripcion(String idInstitucion, String idGuardia) {
		
		SQL sql = new SQL();
		
		sql.SELECT("cen_persona.idpersona");
		sql.SELECT("cen_persona.nombre");
		sql.SELECT("cen_persona.apellidos1");
		sql.SELECT("decode(cen_persona.apellidos2, NULL, '', ' ' || cen_persona.apellidos2) apellidos2");
		sql.SELECT("cen_persona.apellidos1 || decode(cen_persona.apellidos2, NULL, '', ' ' || cen_persona.apellidos2) alfabeticoapellidos");
		sql.SELECT("decode(cen_colegiado.comunitario, '1', cen_colegiado.ncomunitario, cen_colegiado.ncolegiado) numerocolegiado");
		sql.SELECT("scs_grupoguardiacolegiado.idgrupoguardiacolegiado   AS idgrupoguardiacolegiado");
		sql.SELECT("scs_grupoguardiacolegiado.idgrupoguardia            AS grupo");
		sql.SELECT("scs_grupoguardia.numerogrupo                        AS numerogrupo");
		sql.SELECT("scs_grupoguardiacolegiado.orden                     AS ordengrupo");
		
		sql.FROM("scs_grupoguardiacolegiado");
		sql.JOIN("scs_grupoguardia ON scs_grupoguardiacolegiado.idgrupoguardia = scs_grupoguardia.idgrupoguardia"
				+ "                AND scs_grupoguardia.idturno = scs_grupoguardiacolegiado.idturno"
				+ "                AND scs_grupoguardia.idguardia = scs_grupoguardiacolegiado.idguardia"
				+ "                AND scs_grupoguardia.idinstitucion = scs_grupoguardiacolegiado.idinstitucion"
				+ "");
		
		sql.JOIN("scs_guardiasturno ON scs_guardiasturno.idturno = scs_grupoguardiacolegiado.idturno"
				+ "                                         AND scs_guardiasturno.idguardia = scs_grupoguardiacolegiado.idguardia"
				+ "                                         AND scs_guardiasturno.idinstitucion = scs_grupoguardiacolegiado.idinstitucion");
		
		sql.JOIN("scs_inscripcionguardia ON scs_inscripcionguardia.idinstitucion = scs_grupoguardiacolegiado.idinstitucion"
				+ "                                              AND scs_inscripcionguardia.idturno = scs_grupoguardiacolegiado.idturno"
				+ "                                              AND scs_inscripcionguardia.idguardia = scs_grupoguardiacolegiado.idguardia"
				+ "                                              AND scs_inscripcionguardia.idpersona = scs_grupoguardiacolegiado.idpersona");
		
		sql.JOIN("cen_persona ON cen_persona.idpersona = scs_grupoguardiacolegiado.idpersona");
		
		sql.JOIN("cen_colegiado ON cen_colegiado.idpersona = scs_grupoguardiacolegiado.idpersona AND cen_colegiado.idinstitucion = scs_grupoguardiacolegiado.idinstitucion");
		
		sql.WHERE("scs_grupoguardiacolegiado.idinstitucion ="+idInstitucion);
		
		sql.WHERE("scs_guardiasturno.porgrupos = '1'");
		
		sql.WHERE("scs_guardiasturno.idguardia ="+idGuardia);
		
		sql.WHERE("scs_guardiasturno.idinstitucion ="+idInstitucion);
		
		sql.WHERE("scs_inscripcionguardia.fechavalidacion IS NOT NULL");
		
		sql.WHERE("scs_inscripcionguardia.fechabaja IS NULL OR scs_inscripcionguardia.fechabaja >= sysdate");
		
		sql.WHERE("trunc(scs_inscripcionguardia.fechavalidacion) <= nvl(sysdate, scs_inscripcionguardia.fechavalidacion)");
		
		sql.WHERE("scs_inscripcionguardia.fechabaja IS NULL OR trunc(scs_inscripcionguardia.fechabaja) > nvl(sysdate, '01/01/1900')");
		
		sql.ORDER_BY("numerogrupo, ordengrupo , scs_inscripcionguardia.fechasuscripcion, scs_inscripcionguardia.idpersona");
	
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
	
	
	
//GENERAR CALENDARIOS:
	//1: OBTENGO LA COLA PARA CADA DIA
//1.1 Obtenemos la configuracion de cada guardia del calendario a generar haciendo un foreach del listado
	public String getConfiguracionDiasFromGuardia(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion){
		SQL sql = new SQL();
	        sql.SELECT("GT.PORGRUPO AS PORGRUPO, GT.NUMEROLETRADOSGUARDIA AS NUMEROLETRADOSGUARDIA, "
	        		+ "GT.DIASGUARDIA AS DURACION, GT.TIPODIASGUARDIA AS TIPO_DURACION, GT.DIASPERIODO AS PERIODO, GT.TIPODIASPERIODO AS TIPO_PERIODO, "
	        		+ "GT.PORGRUPOS AS PORGRUPOS, GT.ROTARCOMPONENTES AS ROTAR, GT.SELECCIONLABORABLES LABORABLES, GT.SELECCIONFESTIVOS FESTIVOS");
	        sql.FROM("SCS_INSRIPCIONESGUARDIAS IG");
	        sql.WHERE("IG.IDTURNO = " + calendarioItem.getIdTurno());
	        sql.WHERE("IG.IDGUARDIA = " + calendarioItem.getIdGuardia());
	        sql.WHERE("IG.IDINSTITUCION = " + idInstitucion);
		return sql.toString();
	}
//	1.2 forEach del periodo configurado en la programacion y pasamos dia a dia para obtener la cola de letrados
	public String getColasFromGuardia(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion, String dia){
		SQL sql = new SQL();
	        sql.SELECT("IG.IDPERSONA AS LETRADO");
	        sql.FROM("SCS_INSRIPCIONESGUARDIAS IG");
	        sql.WHERE("IG.IDTURNO = " + calendarioItem.getIdTurno());
	        sql.WHERE("IG.IDGUARDIA = " + calendarioItem.getIdGuardia());
	        sql.WHERE("IG.IDINSTITUCION = " + idInstitucion);
	        //sql.WHERE("IG.FECHASUSCRIPCION = " +  dia);
		return sql.toString();
	}
	//Recorro la lista anterior y si PORGRUPO = 1:
	public String getConfigColasGuardia(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion, String idPersona){
		SQL sql = new SQL();
	        sql.SELECT("CG.IDPERSONA AS LETRADO, GT.PORGRUPO AS PORGRUPO, GT.NUMEROLETRADOSGUARDIA AS NUMEROLETRADOSGUARDIA");
	        sql.FROM("SCS_INSRIPCIONESGUARDIAS IG");
	        sql.JOIN("SCS_GRUPOGUARDIASCOLEGIADO GGC ON IG.IDTURNO = GGC.IDTURNO AND IG.IDGUARDIA = GGC.IDGUARDIA AND IG.IDINSTITUCION = GGC.IDINSTITUCION"
	        		+ "AND IG.IDPERSONA = GGC.IDINSTITUCION AND IG.FECHASUSCRIPCION = GGC.IDINSTITUCION");
	        sql.WHERE("CG.IDTURNO = " + calendarioItem.getIdTurno());
	        sql.WHERE("CG.IDGUARDIA = " + calendarioItem.getIdGuardia());
	        sql.WHERE("CG.IDINSTITUCION = " + idInstitucion);
//	        sql.WHERE("CG.FECHAINICIO <= " + dia);
//	        sql.WHERE("CG.FECHAFIN >= " + dia);
		return sql.toString();
	}
	
	//Recorro la lista anterior y si grupo = 0
	
	
//	public String getConfigGruposGuardia(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion){
//		SQL sql = new SQL();
//	        sql.SELECT("PORGRUPO");
//	        sql.FROM("SCS_GUARDIASTURNO");
//	        sql.WHERE("IDTURNO = " + calendarioItem.getIdTurno());
//	        sql.WHERE("IDGUARDIA = " + calendarioItem.getIdGuardia());
//	        sql.WHERE("IDINSTITUCION = " + idInstitucion);
//	        sql.WHERE("FECHAINICIO >= TO_DATE('" + calendarioItem.getFechaDesde() + "','DD/MM/YYYY')");
//	        sql.WHERE("FECHAFIN <= TO_DATE('" + calendarioItem.getFechaHasta() + "','DD/MM/YYYY')");
//		return sql.toString();
//	}
	//hago foreach de 
	public String getColegiadosFromGuardia(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion){
		SQL sql = new SQL();
	        sql.SELECT("IDPERSONA");
//	        sql.SELECT("DIASGUARDIA");
	        sql.FROM("SCS_GUARDIASCOLEGIADO");
	        sql.WHERE("IDTURNO = " + calendarioItem.getIdTurno());
	        sql.WHERE("IDGUARDIA = " + calendarioItem.getIdGuardia());
	        sql.WHERE("IDINSTITUCION = " + idInstitucion);
	        sql.WHERE("FECHAINICIO >= TO_DATE('" + calendarioItem.getFechaDesde() + "','DD/MM/YYYY')");
	        sql.WHERE("FECHAFIN <= TO_DATE('" + calendarioItem.getFechaHasta() + "','DD/MM/YYYY')");
		return sql.toString();
	}
	
	
	
	
	
	
	//FLUJO SIGA CLASSIQUE:
	
	public String checkHistorico(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("SCS_HCO_CONF_PROG_CALENDARIOS");
		sql.WHERE("IDPROGCALENDARIO = " + calendarioItem.getIdCalendarioProgramado());
		sql.WHERE("IDCONJUNTOGUARDIA = " + calendarioItem.getIdCalG());
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDTURNO = " +  calendarioItem.getIdTurno());
		sql.WHERE("IDGUARDIA = " + calendarioItem.getIdGuardia());
		return sql.toString();
	}
	public String insertarHistorico(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion){
//		INSERT INTO SCS_HCO_CONF_PROG_CALENDARIOS  (SELECT PC.IDPROGCALENDARIO,CG.IDCONJUNTOGUARDIA,  CG.IDINSTITUCION ,  CG.IDTURNO ,  CG.IDGUARDIA,  
//				 CG.ORDEN, SYSDATE,0 , 0 FROM SCS_PROG_CALENDARIOS PC, SCS_CONF_CONJUNTO_GUARDIAS CG  WHERE  PC.IDCONJUNTOGUARDIA = CG.IDCONJUNTOGUARDIA  
//				 AND PC.IDINSTITUCION = CG.IDINSTITUCION AND PC.IDPROGCALENDARIO = 438 ) 
		SQL subquery = new SQL();
		subquery.SELECT("PC.IDPROGCALENDARIO,CG.IDCONJUNTOGUARDIA,  CG.IDINSTITUCION ,  CG.IDTURNO ,  CG.IDGUARDIA,  \r\n" + 
				"				 CG.ORDEN, PC.FECHAMODIFICACION as FECHAMODIFICACION, CG.USUMODIFICACION , PC.ESTADO");
		subquery.FROM("SCS_PROG_CALENDARIOS PC, SCS_CONF_CONJUNTO_GUARDIAS CG");
		subquery.WHERE("PC.IDCONJUNTOGUARDIA = CG.IDCONJUNTOGUARDIA" + 
				"				 AND PC.IDINSTITUCION = CG.IDINSTITUCION AND PC.IDPROGCALENDARIO = " + calendarioItem.getIdCalendarioProgramado() + " AND CG.IDTURNO = " + calendarioItem.getIdTurno() + " AND CG.IDGUARDIA = " + calendarioItem.getIdGuardia());
		SQL sql = new SQL();
	        sql.INSERT_INTO("SCS_HCO_CONF_PROG_CALENDARIOS (IDPROGCALENDARIO, IDCONJUNTOGUARDIA, IDINSTITUCION, IDTURNO, IDGUARDIA, ORDEN, FECHAMODIFICACION, USUMODIFICACION, ESTADO) " + subquery.toString());
		return sql.toString();
	}
	
	public String updateEstado(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion) {
//		UPDATE [ESTADO, FECHAMODIFICACION, USUMODIFICACION] SCS_PROG_CALENDARIOS CLAVES = [IDPROGCALENDARIO, IDINSTITUCION]
		SQL sql = new SQL();
		sql.UPDATE("SCS_PROG_CALENDARIOS PC");
		if (calendarioItem.getEstado() != null) {
			sql.SET("ESTADO = " + calendarioItem.getEstado());
		}
		sql.SET("FECHAMODIFICACION = SYSDATE");
		sql.SET("USUMODIFICACION = 0");
		if (calendarioItem.getIdCalendarioProgramado() != null) {
			sql.WHERE("PC.IDPROGCALENDARIO = " + calendarioItem.getIdCalendarioProgramado());
		}
		if (idInstitucion != null) {
			sql.WHERE("PC.IDINSTITUCION = " + idInstitucion);
		}
		return sql.toString();

	}
	
	public String getNextGuardiaProgramadaNoGenerada(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion){
//		SELECT * FROM ( SELECT  PC.IDPROGCALENDARIO,PC.IDCONJUNTOGUARDIA,  PC.IDINSTITUCION,PC.IDTURNO,  PC.IDGUARDIA,	PC.ORDEN,	  PC.ESTADO  ,GT.NOMBRE	,GT.NUMEROLETRADOSGUARDIA	
//				 ,GT.NUMEROSUSTITUTOSGUARDIA  ,GT.DIASGUARDIA	,GT.DIASPAGADOS	,GT.VALIDARJUSTIFICACIONES,GT.DIASSEPARACIONGUARDIAS  ,GT.IDORDENACIONCOLAS,GT.NUMEROASISTENCIAS,GT.DESCRIPCION,
//				 GT.DESCRIPCIONFACTURACION  ,GT.DESCRIPCIONPAGO	,GT.IDPARTIDAPRESUPUESTARIA,GT.NUMEROACTUACIONES  ,GT.IDPERSONA_ULTIMO,GT.TIPODIASGUARDIA,GT.DIASPERIODO,GT.TIPODIASPERIODO  ,
//				 GT.FESTIVOS,GT.SELECCIONLABORABLES,GT.SELECCIONFESTIVOS,GT.IDTURNOSUSTITUCION  ,GT.IDGUARDIASUSTITUCION,GT.IDTIPOGUARDIA,GT.PORGRUPOS  ,GT.ROTARCOMPONENTES,GT.IDINSTITUCIONPRINCIPAL,
//				 GT.IDTURNOPRINCIPAL  ,GT.IDGUARDIAPRINCIPAL,GT.FECHASUSCRIPCION_ULTIMO,GT.IDGRUPOGUARDIA_ULTIMO  
//				 FROM SCS_HCO_CONF_PROG_CALENDARIOS PC,SCS_GUARDIASTURNO GT 
//				 WHERE   PC.IDINSTITUCION = GT.IDINSTITUCION AND PC.IDGUARDIA = GT.IDGUARDIA AND PC.IDTURNO = GT.IDTURNO AND PC.IDINSTITUCION = :1 AND PC.IDPROGCALENDARIO=:2 AND 
//				 ESTADO IN (programado ,reprogramado ) ORDER BY ORDEN   ) WHERE  ROWNUM=1
	SQL subquery = new SQL();
	subquery.SELECT("PC.IDPROGCALENDARIO,PC.IDCONJUNTOGUARDIA,  PC.IDINSTITUCION,PC.IDTURNO,  PC.IDGUARDIA,	PC.ORDEN,	  PC.ESTADO  ,GT.NOMBRE	,GT.NUMEROLETRADOSGUARDIA, "
							+ "GT.NUMEROSUSTITUTOSGUARDIA  ,GT.DIASGUARDIA	,GT.DIASPAGADOS	,GT.VALIDARJUSTIFICACIONES,GT.DIASSEPARACIONGUARDIAS  ,GT.IDORDENACIONCOLAS,GT.NUMEROASISTENCIAS,GT.DESCRIPCION, "
							+ "GT.DESCRIPCIONFACTURACION  ,GT.DESCRIPCIONPAGO	,GT.IDPARTIDAPRESUPUESTARIA,GT.NUMEROACTUACIONES  ,GT.IDPERSONA_ULTIMO,GT.TIPODIASGUARDIA,GT.DIASPERIODO,GT.TIPODIASPERIODO , "
							+ "GT.FESTIVOS,GT.SELECCIONLABORABLES,GT.SELECCIONFESTIVOS,GT.IDTURNOSUSTITUCION  ,GT.IDGUARDIASUSTITUCION,GT.IDTIPOGUARDIA,GT.PORGRUPOS  ,GT.ROTARCOMPONENTES,GT.IDINSTITUCIONPRINCIPAL, "
							+ "GT.IDTURNOPRINCIPAL  ,GT.IDGUARDIAPRINCIPAL,GT.FECHASUSCRIPCION_ULTIMO,GT.IDGRUPOGUARDIA_ULTIMO");
	subquery.FROM("SCS_HCO_CONF_PROG_CALENDARIOS PC,SCS_GUARDIASTURNO GT");
	subquery.WHERE("PC.IDINSTITUCION = GT.IDINSTITUCION AND PC.IDGUARDIA = GT.IDGUARDIA AND PC.IDTURNO = GT.IDTURNO AND PC.IDINSTITUCION = " + idInstitucion + " AND PC.IDPROGCALENDARIO= " + calendarioItem.getIdCalendarioProgramado() + " AND" + 
							"				 ESTADO IN (1 , 5)");
	subquery.ORDER_BY("ORDEN");
		SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("( "  + subquery.toString() +  " )");
        sql.WHERE("ROWNUM=1"); 
	return sql.toString();
	}
	
	public String getCalGuardiavVector(DatosCalendarioProgramadoItem calendarioItem, String idInstitucion){
//		SELECT IDINSTITUCION, IDTURNO, IDGUARDIA, IDCALENDARIOGUARDIAS, FECHAFIN, FECHAINICIO, OBSERVACIONES, FECHAMODIFICACION, USUMODIFICACION, 
//		IDPERSONA_ULTIMOANTERIOR, IDGRUPOGUARDIA_ULTIMOANTERIOR, FECHASUSC_ULTIMOANTERIOR, IDTURNOPRINCIPAL, IDGUARDIAPRINCIPAL, IDCALENDARIOGUARDIASPRINCIPAL   
//		FROM SCS_CALENDARIOGUARDIAS  
//		WHERE IDINSTITUCION =2005 AND IDTURNO =809 AND IDGUARDIA =701 AND TRUNC(FECHAINICIO) ='12/07/2021' AND TRUNC(FECHAFIN) ='24/07/2021' 
//		ORDER BY IDINSTITUCION, IDTURNO, IDGUARDIA, IDCALENDARIOGUARDIAS 
		
		SQL sql = new SQL();
		sql.SELECT("IDINSTITUCION, IDTURNO, IDGUARDIA, IDCALENDARIOGUARDIAS, FECHAFIN, FECHAINICIO, OBSERVACIONES, FECHAMODIFICACION, USUMODIFICACION, " + 
				"		IDPERSONA_ULTIMOANTERIOR, IDGRUPOGUARDIA_ULTIMOANTERIOR, FECHASUSC_ULTIMOANTERIOR, IDTURNOPRINCIPAL, IDGUARDIAPRINCIPAL, IDCALENDARIOGUARDIASPRINCIPAL   ");
		sql.FROM("SCS_CALENDARIOGUARDIAS");
		if(calendarioItem.getIdTurno() != null) {
			sql.WHERE("IDTURNO = " + calendarioItem.getIdTurno());
		}
		if(calendarioItem.getIdGuardia() != null) {
			sql.WHERE("IDGUARDIA = " + calendarioItem.getIdGuardia());
		}
		if(idInstitucion != null) {
			sql.WHERE("IDINSTITUCION = " + idInstitucion);
		}
		if(calendarioItem.getFechaDesde() != null) {
			sql.WHERE("FECHAINICIO >= TO_DATE('" + calendarioItem.getFechaDesde() + "','DD/MM/YYYY')");
		}
		if(calendarioItem.getFechaHasta() != null) {
			sql.WHERE("FECHAFIN <= TO_DATE('" + calendarioItem.getFechaHasta() + "','DD/MM/YYYY')");
		}
		sql.ORDER_BY("IDINSTITUCION, IDTURNO, IDGUARDIA, IDCALENDARIOGUARDIAS");
        return sql.toString();
	}
	
	public String getCalGuardiavVector2(String idTurno, String idGuardia, String fechaDesde, String fechaHasta, String idInstitucion){
//		SELECT IDINSTITUCION, IDTURNO, IDGUARDIA, IDCALENDARIOGUARDIAS, FECHAFIN, FECHAINICIO, OBSERVACIONES, FECHAMODIFICACION, USUMODIFICACION, 
//		IDPERSONA_ULTIMOANTERIOR, IDGRUPOGUARDIA_ULTIMOANTERIOR, FECHASUSC_ULTIMOANTERIOR, IDTURNOPRINCIPAL, IDGUARDIAPRINCIPAL, IDCALENDARIOGUARDIASPRINCIPAL   
//		FROM SCS_CALENDARIOGUARDIAS  
//		WHERE IDINSTITUCION =2005 AND IDTURNO =809 AND IDGUARDIA =701 AND TRUNC(FECHAINICIO) ='12/07/2021' AND TRUNC(FECHAFIN) ='24/07/2021' 
//		ORDER BY IDINSTITUCION, IDTURNO, IDGUARDIA, IDCALENDARIOGUARDIAS 
		
		SQL sql = new SQL();
		sql.SELECT("IDINSTITUCION, IDTURNO, IDGUARDIA, IDCALENDARIOGUARDIAS, FECHAFIN, FECHAINICIO, OBSERVACIONES, FECHAMODIFICACION, USUMODIFICACION, " + 
				"		IDPERSONA_ULTIMOANTERIOR, IDGRUPOGUARDIA_ULTIMOANTERIOR, FECHASUSC_ULTIMOANTERIOR, IDTURNOPRINCIPAL, IDGUARDIAPRINCIPAL, IDCALENDARIOGUARDIASPRINCIPAL   ");
		sql.FROM("SCS_CALENDARIOGUARDIAS");
		if(idTurno != null) {
			sql.WHERE("IDTURNO = " + idTurno);
		}
		if(idGuardia != null) {
			sql.WHERE("IDGUARDIA = " + idGuardia);
		}
		if(idInstitucion != null) {
			sql.WHERE("IDINSTITUCION = " + idInstitucion);
		}
		if(fechaDesde != null) {
			sql.WHERE("FECHAINICIO >= TO_DATE('" + fechaDesde + "','DD/MM/YYYY')");
		}
		if(fechaHasta != null) {
			sql.WHERE("FECHAFIN <= TO_DATE('" + fechaHasta + "','DD/MM/YYYY')");
		}
		sql.ORDER_BY("IDINSTITUCION, IDTURNO, IDGUARDIA, IDCALENDARIOGUARDIAS");
        return sql.toString();
	}
	public String cabGuardiavVector(GuardiasCalendarioItem calendarioItem, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("SCS_CABECERAGUARDIAS");
		if(calendarioItem.getIdturno() != null) {
			sql.WHERE("IDTURNO = " + calendarioItem.getIdturno());
		}
		if(calendarioItem.getIdguardia() != null) {
			sql.WHERE("IDGUARDIA = " + calendarioItem.getIdguardia());
		}
		if(idInstitucion != null) {
			sql.WHERE("IDINSTITUCION = " + idInstitucion);
		}
		if(calendarioItem.getIdcalendarioguardias() != null) {
			sql.WHERE("IDCALENDARIOGUARDIAS = " + calendarioItem.getIdcalendarioguardias());
		}
		if(calendarioItem.getFechainicio() != null) {
			sql.WHERE("FECHAINICIO = to_date('" + calendarioItem.getFechainicio() + "', 'dd/MM/yyyy')");
		}
		if(calendarioItem.getFechafin() != null) {
			sql.WHERE("FECHA_FIN = to_date( '" +calendarioItem.getFechafin() + "' , 'dd/MM/yyyy')");
		}
        sql.ORDER_BY("IDINSTITUCION, IDTURNO, IDGUARDIA, IDCALENDARIOGUARDIAS");
        return sql.toString();
	}
	
	public String getIdPersonaUltimoAnterior(String idTurno, String idGuardia, String idInstitucion) {
//		SELECT IDINSTITUCION, IDTURNO, IDGUARDIA, NOMBRE, NUMEROLETRADOSGUARDIA, NUMEROSUSTITUTOSGUARDIA, DIASGUARDIA, DIASPAGADOS, VALIDARJUSTIFICACIONES, 
//		DIASSEPARACIONGUARDIAS, NUMEROASISTENCIAS, NUMEROACTUACIONES, DESCRIPCION, DESCRIPCIONFACTURACION, DESCRIPCIONPAGO, IDORDENACIONCOLAS, 
//		IDPARTIDAPRESUPUESTARIA, IDPERSONA_ULTIMO, IDGRUPOGUARDIA_ULTIMO, FECHASUSCRIPCION_ULTIMO, DIASPERIODO, TIPODIASPERIODO, FECHAMODIFICACION, 
//		USUMODIFICACION, SELECCIONLABORABLES, SELECCIONFESTIVOS, IDGUARDIASUSTITUCION, IDTURNOSUSTITUCION, PORGRUPOS, ROTARCOMPONENTES, IDINSTITUCIONPRINCIPAL, 
//		IDTURNOPRINCIPAL, IDGUARDIAPRINCIPAL, TIPODIASGUARDIA, IDTIPOGUARDIA, ENVIOCENTRALITA   FROM SCS_GUARDIASTURNO WHERE IDINSTITUCION=2005 AND IDTURNO=809 
//		AND IDGUARDIA=701 ORDER BY IDINSTITUCION, IDTURNO, IDGUARDIA  
		SQL sql = new SQL();
		sql.SELECT("IDINSTITUCION, IDTURNO, IDGUARDIA, NOMBRE, NUMEROLETRADOSGUARDIA, NUMEROSUSTITUTOSGUARDIA, DIASGUARDIA, DIASPAGADOS, VALIDARJUSTIFICACIONES, \r\n" + 
				"		DIASSEPARACIONGUARDIAS, NUMEROASISTENCIAS, NUMEROACTUACIONES, DESCRIPCION, DESCRIPCIONFACTURACION, DESCRIPCIONPAGO, IDORDENACIONCOLAS, \r\n" + 
				"		IDPARTIDAPRESUPUESTARIA, IDPERSONA_ULTIMO, IDGRUPOGUARDIA_ULTIMO, FECHASUSCRIPCION_ULTIMO, DIASPERIODO, TIPODIASPERIODO, FECHAMODIFICACION, \r\n" + 
				"		USUMODIFICACION, SELECCIONLABORABLES, SELECCIONFESTIVOS, IDGUARDIASUSTITUCION, IDTURNOSUSTITUCION, PORGRUPOS, ROTARCOMPONENTES, IDINSTITUCIONPRINCIPAL, \r\n" + 
				"		IDTURNOPRINCIPAL, IDGUARDIAPRINCIPAL, TIPODIASGUARDIA, IDTIPOGUARDIA, ENVIOCENTRALITA");
		sql.FROM("SCS_GUARDIASTURNO");
		if(idTurno != null) {
			sql.WHERE("IDTURNO = " + idTurno);
		}
		if(idGuardia != null) {
			sql.WHERE("IDGUARDIA = " + idGuardia);
		}
		if(idInstitucion != null) {
			sql.WHERE("IDINSTITUCION = " + idInstitucion);
		}
		sql.ORDER_BY("IDINSTITUCION, IDTURNO, IDGUARDIA");
        return sql.toString();
	}
	
	
	public String getIdCalendarioGuardias(String idTurno, String idGuardia, String idInstitucion) {
//		SELECT MAX(IDCALENDARIOGUARDIAS) + 1 AS IDCALENDARIOGUARDIAS FROM SCS_CALENDARIOGUARDIAS WHERE IDINSTITUCION=2005 AND IDTURNO=809 AND 
//				IDGUARDIA=701
		SQL sql = new SQL();
		sql.SELECT("MAX(IDCALENDARIOGUARDIAS) + 1 AS IDCALENDARIOGUARDIAS");
		sql.FROM("SCS_CALENDARIOGUARDIAS");
		if(idInstitucion != null) {
			sql.WHERE("IDINSTITUCION = " + idInstitucion);
		}
		if(idTurno != null) {
			sql.WHERE("IDTURNO = " + idTurno);
		}
		if(idGuardia != null) {
			sql.WHERE("IDGUARDIA = " + idGuardia);
		}
		return sql.toString();
	}
		
	
	public String insertarRegistroCalendarioGuardias(String idTurnoPrincipal, String idGuardiaPrincipal, String idCalendarioPrincipal, String observaciones, String idTurno, String idGuardia, String fechaHasta, String fechaDesde, String idCalendarioProgramado, String idInstitucion, String idPersonaUltimoAnterior, String today, String fechaSuscUltimoAnterior, String idGrupoUltimoAnterior, String usuModif){
//		INSERT INTO SCS_CALENDARIOGUARDIAS {OBSERVACIONES=Calendario generado automáticamente desde la programación de calendarios, 
//				IDPERSONA_ULTIMOANTERIOR=2000002109, FECHAMODIFICACION=sysdate, FECHASUSC_ULTIMOANTERIOR=2015/09/09 15:20:27, IDTURNO=809, 
//				USUMODIFICACION=0, IDGUARDIA=701, FECHAFIN=2021/07/24 00:00:00, IDGRUPOGUARDIA_ULTIMOANTERIOR=, IDINSTITUCION=2005, 
//				FECHAINICIO=2021/07/12 00:00:00, IDCALENDARIOGUARDIAS=26}
		SQL sql = new SQL();
	        sql.INSERT_INTO("SCS_CALENDARIOGUARDIAS");
	        if (observaciones != null)
			sql.VALUES("OBSERVACIONES ", "'" + observaciones + "'");
	        if (idPersonaUltimoAnterior != null && !idPersonaUltimoAnterior.isEmpty())
			sql.VALUES("IDPERSONA_ULTIMOANTERIOR", idPersonaUltimoAnterior);
	        if (today != null)
			sql.VALUES("FECHAMODIFICACION", "TO_DATE('" +today+ "', 'YYYY-MM-DD HH24:MI:SS')");
	        if (fechaSuscUltimoAnterior != null && ! fechaSuscUltimoAnterior.isEmpty())
			sql.VALUES("FECHASUSC_ULTIMOANTERIOR", "TO_DATE('" +fechaSuscUltimoAnterior+ "', 'YYYY-MM-DD HH24:MI:SS')");
	        if (idTurno != null)
			sql.VALUES("IDTURNO", idTurno);
	        if (usuModif != null)
			sql.VALUES("USUMODIFICACION", usuModif);
	        if (idGuardia != null)
			sql.VALUES("IDGUARDIA", idGuardia);
	        if (fechaHasta != null)
			sql.VALUES("FECHAFIN", "TO_DATE('" +fechaHasta+ "', 'YYYY-MM-DD HH24:MI:SS')");
	        if (idGrupoUltimoAnterior != null && !idGrupoUltimoAnterior.isEmpty())
			sql.VALUES("IDGRUPOGUARDIA_ULTIMOANTERIOR", idGrupoUltimoAnterior);
	        if (idInstitucion != null)
			sql.VALUES("IDINSTITUCION", idInstitucion);
	        if (fechaDesde != null)
			sql.VALUES("FECHAINICIO", "TO_DATE('" +fechaDesde+ "', 'YYYY-MM-DD HH24:MI:SS')");
	        if (idCalendarioProgramado != null)
			sql.VALUES("IDCALENDARIOGUARDIAS", idCalendarioProgramado);
			if (idTurnoPrincipal != null) {
				sql.VALUES("IDTURNOPRINCIPAL", idTurnoPrincipal);
				sql.VALUES("IDGUARDIAPRINCIPAL", idGuardiaPrincipal);
				sql.VALUES("IDCALENDARIOGUARDIASPRINCIPAL", idCalendarioPrincipal);
			}
		return sql.toString();
	}
	
	public String getGuardia(String idGuardia, String idTurno, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("SCS_GUARDIASTURNO");
		if(idInstitucion != null) {
			sql.WHERE("IDINSTITUCION = " + idInstitucion);
		}
		if(idTurno != null) {
			sql.WHERE("IDTURNO = " + idTurno);
		}
		if(idGuardia != null) {
			sql.WHERE("IDGUARDIA = " + idGuardia);
		}
		return sql.toString();
	}
	
	public String getTotalLetrados(String idInstitucion, String idCalendarioGuardias, String idTurno, String idGuardia, String today) {
//		select count(*) AS TOTAL from SCS_CABECERAGUARDIAS where IDINSTITUCION=2005 and IDTURNO=809 
//				and IDGUARDIA=701 and IDCALENDARIOGUARDIAS=26 and trunc(FECHA_FIN) < trunc(sysdate)
		SQL sql = new SQL();
		sql.SELECT("count(*) AS TOTAL");
		sql.FROM("SCS_CABECERAGUARDIAS");
		if(idInstitucion != null) {
			sql.WHERE("IDINSTITUCION = " + idInstitucion);
		}
		if(idTurno != null) {
			sql.WHERE("IDTURNO = " + idTurno);
		}
		if(idGuardia != null) {
			sql.WHERE("IDGUARDIA = " + idGuardia);
		}
		if(idCalendarioGuardias != null) {
			sql.WHERE("IDCALENDARIOGUARDIAS = " + idCalendarioGuardias);
		}
		if(today != null) {
			sql.WHERE("FECHA_FIN < TO_DATE('" + today + "','DD/MM/YYYY')");
		}
		return sql.toString();
	}
	
	public String getGuardiasVnculadas(String idInstitucion, String idTurno, String idGuardia) {
//		SELECT IDINSTITUCION, IDTURNO, IDGUARDIA, NOMBRE, NUMEROLETRADOSGUARDIA, NUMEROSUSTITUTOSGUARDIA, DIASGUARDIA, DIASPAGADOS, VALIDARJUSTIFICACIONES, 
//		DIASSEPARACIONGUARDIAS, NUMEROASISTENCIAS, NUMEROACTUACIONES, DESCRIPCION, DESCRIPCIONFACTURACION, DESCRIPCIONPAGO, IDORDENACIONCOLAS, IDPARTIDAPRESUPUESTARIA, 
//		IDPERSONA_ULTIMO, IDGRUPOGUARDIA_ULTIMO, FECHASUSCRIPCION_ULTIMO, DIASPERIODO, TIPODIASPERIODO, FECHAMODIFICACION, USUMODIFICACION, SELECCIONLABORABLES, 
//		SELECCIONFESTIVOS, IDGUARDIASUSTITUCION, IDTURNOSUSTITUCION, PORGRUPOS, ROTARCOMPONENTES, IDINSTITUCIONPRINCIPAL, IDTURNOPRINCIPAL, IDGUARDIAPRINCIPAL, 
//		TIPODIASGUARDIA, IDTIPOGUARDIA, ENVIOCENTRALITA   FROM SCS_GUARDIASTURNO  WHERE idinstitucionprincipal = 2005 AND idturnoprincipal = 809 
//		AND idguardiaprincipal = 701 ORDER BY IDINSTITUCION, IDTURNO, IDGUARDIA 
		
		SQL sql = new SQL();
		sql.SELECT("IDINSTITUCION, IDTURNO, IDGUARDIA, NOMBRE, NUMEROLETRADOSGUARDIA, NUMEROSUSTITUTOSGUARDIA, DIASGUARDIA, DIASPAGADOS, VALIDARJUSTIFICACIONES, \r\n" + 
				"		DIASSEPARACIONGUARDIAS, NUMEROASISTENCIAS, NUMEROACTUACIONES, DESCRIPCION, DESCRIPCIONFACTURACION, DESCRIPCIONPAGO, IDORDENACIONCOLAS, IDPARTIDAPRESUPUESTARIA, \r\n" + 
				"		IDPERSONA_ULTIMO, IDGRUPOGUARDIA_ULTIMO, FECHASUSCRIPCION_ULTIMO, DIASPERIODO, TIPODIASPERIODO, FECHAMODIFICACION, USUMODIFICACION, SELECCIONLABORABLES, \r\n" + 
				"		SELECCIONFESTIVOS, IDGUARDIASUSTITUCION, IDTURNOSUSTITUCION, PORGRUPOS, ROTARCOMPONENTES, IDINSTITUCIONPRINCIPAL, IDTURNOPRINCIPAL, IDGUARDIAPRINCIPAL, \r\n" + 
				"		TIPODIASGUARDIA, IDTIPOGUARDIA, ENVIOCENTRALITA");
		sql.FROM("SCS_GUARDIASTURNO");
		if(idInstitucion != null) {
			sql.WHERE("idinstitucionprincipal = " + idInstitucion);
		}
		if(idTurno != null) {
			sql.WHERE("idturnoprincipal = " + idTurno);
		}
		if(idGuardia != null) {
			sql.WHERE("idguardiaprincipal = " + idGuardia);
		}
		sql.ORDER_BY("IDINSTITUCION, IDTURNO, IDGUARDIA");
		return sql.toString();
	}
	
	public String getAllDatosGuardia(String idInstitucion, String idTurno, String idGuardia){
//				SELECT IDINSTITUCION, IDTURNO, IDGUARDIA, NOMBRE, NUMEROLETRADOSGUARDIA, NUMEROSUSTITUTOSGUARDIA, DIASGUARDIA, DIASPAGADOS, 
//				VALIDARJUSTIFICACIONES, DIASSEPARACIONGUARDIAS, NUMEROASISTENCIAS, NUMEROACTUACIONES, DESCRIPCION, DESCRIPCIONFACTURACION, 
//				DESCRIPCIONPAGO, IDORDENACIONCOLAS, IDPARTIDAPRESUPUESTARIA, IDPERSONA_ULTIMO, IDGRUPOGUARDIA_ULTIMO, FECHASUSCRIPCION_ULTIMO, 
//				DIASPERIODO, TIPODIASPERIODO, FECHAMODIFICACION, USUMODIFICACION, SELECCIONLABORABLES, SELECCIONFESTIVOS, IDGUARDIASUSTITUCION, 
//				IDTURNOSUSTITUCION, PORGRUPOS, ROTARCOMPONENTES, IDINSTITUCIONPRINCIPAL, IDTURNOPRINCIPAL, IDGUARDIAPRINCIPAL, TIPODIASGUARDIA, 
//				IDTIPOGUARDIA, ENVIOCENTRALITA   FROM SCS_GUARDIASTURNO  WHERE IDINSTITUCION=2005 AND IDTURNO=809 AND IDGUARDIA=701 ORDER BY IDINSTITUCION, IDTURNO, IDGUARDIA

				SQL sql = new SQL();
				sql.SELECT("IDINSTITUCION, IDTURNO, IDGUARDIA, NOMBRE, NUMEROLETRADOSGUARDIA, NUMEROSUSTITUTOSGUARDIA, DIASGUARDIA, DIASPAGADOS, \r\n" + 
						"				VALIDARJUSTIFICACIONES, DIASSEPARACIONGUARDIAS, NUMEROASISTENCIAS, NUMEROACTUACIONES, DESCRIPCION, DESCRIPCIONFACTURACION, \r\n" + 
						"				DESCRIPCIONPAGO, IDORDENACIONCOLAS, IDPARTIDAPRESUPUESTARIA, IDPERSONA_ULTIMO, IDGRUPOGUARDIA_ULTIMO, FECHASUSCRIPCION_ULTIMO, \r\n" + 
						"				DIASPERIODO, TIPODIASPERIODO, FECHAMODIFICACION, USUMODIFICACION, SELECCIONLABORABLES, SELECCIONFESTIVOS, IDGUARDIASUSTITUCION, \r\n" + 
						"				IDTURNOSUSTITUCION, PORGRUPOS, ROTARCOMPONENTES, IDINSTITUCIONPRINCIPAL, IDTURNOPRINCIPAL, IDGUARDIAPRINCIPAL, TIPODIASGUARDIA, \r\n" + 
						"				IDTIPOGUARDIA, ENVIOCENTRALITA");
				sql.FROM("SCS_GUARDIASTURNO");
				if(idInstitucion != null) {
					sql.WHERE("IDINSTITUCION = " + idInstitucion);
				}
				if(idTurno != null) {
					sql.WHERE("IDTURNO = " + idTurno);
				}
				if(idGuardia != null) {
					sql.WHERE("IDGUARDIA = " + idGuardia);
				}
				sql.ORDER_BY("IDINSTITUCION, IDTURNO, IDGUARDIA");
				return sql.toString();
	}
	
	public String getCalGuardias(String idInstitucion, String idTurno, String idGuardia, String idCalendarioGuardias) {
//				SELECT IDINSTITUCION, IDTURNO, IDGUARDIA, IDCALENDARIOGUARDIAS, FECHAFIN, FECHAINICIO, OBSERVACIONES, FECHAMODIFICACION, USUMODIFICACION, IDPERSONA_ULTIMOANTERIOR, 
//				IDGRUPOGUARDIA_ULTIMOANTERIOR, FECHASUSC_ULTIMOANTERIOR, IDTURNOPRINCIPAL, IDGUARDIAPRINCIPAL, IDCALENDARIOGUARDIASPRINCIPAL   FROM SCS_CALENDARIOGUARDIAS  
//				WHERE IDINSTITUCION=2005 AND IDTURNO=809 AND IDGUARDIA=701 AND IDCALENDARIOGUARDIAS=26 ORDER BY IDINSTITUCION, IDTURNO, IDGUARDIA, IDCALENDARIOGUARDIAS  
				SQL sql = new SQL();
				sql.SELECT("IDINSTITUCION, IDTURNO, IDGUARDIA, IDCALENDARIOGUARDIAS, FECHAFIN, FECHAINICIO, OBSERVACIONES, FECHAMODIFICACION, USUMODIFICACION, IDPERSONA_ULTIMOANTERIOR, \r\n" + 
						"				IDGRUPOGUARDIA_ULTIMOANTERIOR, FECHASUSC_ULTIMOANTERIOR, IDTURNOPRINCIPAL, IDGUARDIAPRINCIPAL, IDCALENDARIOGUARDIASPRINCIPAL");
				sql.FROM("SCS_CALENDARIOGUARDIAS");
				if(idInstitucion != null) {
					sql.WHERE("IDINSTITUCION = " + idInstitucion);
				}
				if(idTurno != null) {
					sql.WHERE("IDTURNO = " + idTurno);
				}
				if(idGuardia != null) {
					sql.WHERE("IDGUARDIA = " + idGuardia);
				}
				if(idCalendarioGuardias != null) {
					sql.WHERE("IDCALENDARIOGUARDIAS = " + idCalendarioGuardias);
				}
				sql.ORDER_BY("IDINSTITUCION, IDTURNO, IDGUARDIA, IDCALENDARIOGUARDIAS");
				return sql.toString();
	}
	
	public String getFestivosTurno(String fechaInicio, String fechaFin, String idInstitucion1, String idInstitucion2, String idTurno) {
//		SELECT cal.FECHA   FROM SCS_CALENDARIOLABORAL cal   WHERE cal.FECHA             between TO_DATE('01/07/2021', 'DD/MM/YYYY') 
//		AND TO_DATE('30/08/2021', 'DD/MM/YYYY')     AND (cal.IDINSTITUCION = 2005         OR cal.IDINSTITUCION = 2000        )     AND (cal.IDPARTIDO IS NULL         
//				OR cal.IDPARTIDO IN                 (SELECT p.IDPARTIDO                  FROM SCS_TURNO t,       	                SCS_SUBZONAPARTIDO p                  
//				WHERE p.IDINSTITUCION = t.IDINSTITUCION                   AND p.IDZONA = t.IDZONA                   AND p.IDSUBZONA = t.IDSUBZONA                   
//				AND t.IDINSTITUCION = 2005                   AND t.IDTURNO = 3298               )        )
				
		SQL subquery = new SQL();
		subquery.SELECT("p.IDPARTIDO");
		subquery.FROM("SCS_TURNO t, SCS_SUBZONAPARTIDO p");
		subquery.WHERE("p.IDINSTITUCION = t.IDINSTITUCION");
		subquery.WHERE("p.IDZONA = t.IDZONA");
		subquery.WHERE("p.IDSUBZONA = t.IDSUBZONA");
		if(idInstitucion1 != null) {
			subquery.WHERE("t.IDINSTITUCION = " + idInstitucion1);
		}
		if(idTurno != null) {
			subquery.WHERE("t.IDTURNO = " + idTurno); 
		}         
		
		SQL sql = new SQL();
		sql.SELECT("to_char(cal.FECHA, 'dd/mm/yyyy') FECHA");
		sql.FROM("SCS_CALENDARIOLABORAL cal");
		if(fechaFin != null) {
			sql.WHERE("cal.FECHA <=  TO_DATE('" + fechaFin + "','DD/MM/YYYY')" );
		}
		if(fechaInicio != null) {
			sql.WHERE("cal.FECHA >=  TO_DATE('" + fechaInicio + "','DD/MM/YYYY')" );
		}
		if(idInstitucion1 != null || idInstitucion2 != null) {
			sql.WHERE("( cal.IDINSTITUCION = " + idInstitucion1 + " OR cal.IDINSTITUCION = " + idInstitucion2 + ")");
		}
			sql.WHERE("( cal.IDPARTIDO IS NULL OR cal.IDPARTIDO IN (" + subquery + " ) )");

			//sql.ORDER_BY("IDINSTITUCION, IDTURNO, IDGUARDIA, IDCALENDARIOGUARDIAS");
		return sql.toString();
	}
					
	public String getDiasASeparar(String idGuardia, String idTurno, String idInstitucion, String agrupar) {
//		 SELECT DIASAPLICABLES FROM SCS_HITOFACTURABLEGUARDIA WHERE IDINSTITUCION = :1 AND IDTURNO = :2 AND IDGUARDIA = :3 AND NVL(AGRUPAR ,0)  = :4 
//				 AND  DIASAPLICABLES IS NOT NULL   GROUP BY DIASAPLICABLES
		SQL sql = new SQL();
		sql.SELECT("DIASAPLICABLES");
		sql.FROM("SCS_HITOFACTURABLEGUARDIA");
		if(idInstitucion != null) {
			sql.WHERE("IDINSTITUCION = " + idInstitucion);
		}
		if(idTurno != null) {
			sql.WHERE("IDTURNO = " + idTurno); 
		} 
		if(idGuardia != null) {
			sql.WHERE("IDGUARDIA = " + idGuardia);
		}
		if(agrupar != null) {
			sql.WHERE("NVL(AGRUPAR ,0) = " + agrupar);
		}
		sql.WHERE("DIASAPLICABLES IS NOT NULL");
		sql.GROUP_BY("DIASAPLICABLES");
		return sql.toString();
	}
	
	public String getNextGuardiaConfigurada(String idInstitucion, String idProgCalendario) {
//		SELECT * FROM 
//		(
//				SELECT 
//				PC.IDPROGCALENDARIO,PC.IDCONJUNTOGUARDIA, 
//				PC.IDINSTITUCION,PC.IDTURNO, 
//				PC.IDGUARDIA,	PC.ORDEN,	  PC.ESTADO 
//				
//				,GT.NOMBRE	,GT.NUMEROLETRADOSGUARDIA	,GT.NUMEROSUSTITUTOSGUARDIA 
//				,GT.DIASGUARDIA	,GT.DIASPAGADOS	,GT.VALIDARJUSTIFICACIONES,GT.DIASSEPARACIONGUARDIAS 
//				,GT.IDORDENACIONCOLAS,GT.NUMEROASISTENCIAS,GT.DESCRIPCION,GT.DESCRIPCIONFACTURACION 
//				,GT.DESCRIPCIONPAGO	,GT.IDPARTIDAPRESUPUESTARIA,GT.NUMEROACTUACIONES 
//				,GT.IDPERSONA_ULTIMO,GT.TIPODIASGUARDIA,GT.DIASPERIODO,GT.TIPODIASPERIODO        
//				,GT.FESTIVOS,GT.SELECCIONLABORABLES,GT.SELECCIONFESTIVOS,GT.IDTURNOSUSTITUCION     
//				,GT.IDGUARDIASUSTITUCION,GT.IDTIPOGUARDIA,GT.PORGRUPOS           
//				,GT.ROTARCOMPONENTES,GT.IDINSTITUCIONPRINCIPAL,GT.IDTURNOPRINCIPAL       
//				,GT.IDGUARDIAPRINCIPAL,GT.FECHASUSCRIPCION_ULTIMO,GT.IDGRUPOGUARDIA_ULTIMO 
//				
//				
//				FROM SCS_HCO_CONF_PROG_CALENDARIOS PC,SCS_GUARDIASTURNO GT WHERE  
//				PC.IDINSTITUCION = GT.IDINSTITUCION
//				AND PC.IDGUARDIA = GT.IDGUARDIA
//				AND PC.IDTURNO = GT.IDTURNO
//				AND PC.IDINSTITUCION = :
//				contador++;
//				sql.append(contador);
//				codigosHashtable.put(new Integer(contador),progCalendariosVO.getIdInstitucion());
//				AND PC.IDPROGCALENDARIO");
//				sql.append("=:");
//				contador++;
//				sql.append(contador);
//				codigosHashtable.put(new Integer(contador),progCalendariosVO.getIdProgrCalendario());
//				
//				 AND ESTADO IN (				
//					contador++;
//					sql.append(contador);
//					codigosHashtable.put(new Integer(contador),ScsHcoConfProgCalendariosBean.estadoProgramado);
//					sql.append(" ,:");
//					contador++;
//					sql.append(contador);
//					codigosHashtable.put(new Integer(contador),ScsHcoConfProgCalendariosBean.estadoReprogramado);
//				)
//		
//				ORDER BY ORDEN
//		) 
//		WHERE  ROWNUM=1
		SQL subquery = new SQL();
		subquery.SELECT("PC.IDPROGCALENDARIO, PC.IDCONJUNTOGUARDIA, PC.IDINSTITUCION, PC.IDTURNO, PC.IDGUARDIA, PC.ORDEN, PC.ESTADO,GT.NOMBRE	,"
				+ "GT.NUMEROLETRADOSGUARDIA	,GT.NUMEROSUSTITUTOSGUARDIA "
				+ ",GT.DIASGUARDIA	,GT.DIASPAGADOS	,GT.VALIDARJUSTIFICACIONES,GT.DIASSEPARACIONGUARDIAS "
				+ ",GT.IDORDENACIONCOLAS,GT.NUMEROASISTENCIAS,GT.DESCRIPCION,GT.DESCRIPCIONFACTURACION "
				+ ",GT.DESCRIPCIONPAGO	,GT.IDPARTIDAPRESUPUESTARIA,GT.NUMEROACTUACIONES "
				+ ",GT.IDPERSONA_ULTIMO,GT.TIPODIASGUARDIA,GT.DIASPERIODO,GT.TIPODIASPERIODO   "     
				+ ",GT.FESTIVOS,GT.SELECCIONLABORABLES,GT.SELECCIONFESTIVOS,GT.IDTURNOSUSTITUCION    " 
				+ ",GT.IDGUARDIASUSTITUCION,GT.IDTIPOGUARDIA,GT.PORGRUPOS           "
				+ ",GT.ROTARCOMPONENTES,GT.IDINSTITUCIONPRINCIPAL,GT.IDTURNOPRINCIPAL       "
				+ ",GT.IDGUARDIAPRINCIPAL,GT.FECHASUSCRIPCION_ULTIMO,GT.IDGRUPOGUARDIA_ULTIMO " );
		subquery.FROM("SCS_HCO_CONF_PROG_CALENDARIOS PC,SCS_GUARDIASTURNO GT");
		subquery.WHERE("PC.IDINSTITUCION = GT.IDINSTITUCION");
		subquery.WHERE("PC.IDGUARDIA = GT.IDGUARDIA");
		subquery.WHERE("PC.IDTURNO = GT.IDTURNO");
		subquery.WHERE("PC.IDINSTITUCION = " + idInstitucion);
		subquery.WHERE("PC.IDPROGCALENDARIO = " + idProgCalendario);
		subquery.WHERE("ESTADO IN (1, 5)");
		subquery.ORDER_BY("ORDEN");
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("( " + subquery + " )");
		sql.WHERE("ROWNUM=1");
		return sql.toString();
	}
	
	public String getBajasTemporalesGuardias(String idInstitucion, String idTurno, String idGuardia, String fechaDesde, String fechaHasta) {
//		select.append(" SELECT BAJAS.* ");
//		select.append("   FROM CEN_BAJASTEMPORALES BAJAS, SCS_INSCRIPCIONGUARDIA INS ");
//		select.append("  WHERE BAJAS.IDINSTITUCION = INS.IDINSTITUCION ");
//		select.append("    AND BAJAS.IDPERSONA = INS.IDPERSONA ");
//		select.append("    AND BAJAS.VALIDADO = 1 ");
//		select.append("    AND INS.IDINSTITUCION = :");
//		keyContador++;
//		select.append(keyContador);
//		htCodigos.put(new Integer(keyContador), idInstitucion);
//		select.append("    AND INS.IDTURNO = :");
//		keyContador++;
//		select.append(keyContador);
//		htCodigos.put(new Integer(keyContador), idTurno);
//		select.append("    AND INS.IDGUARDIA = :");
//		keyContador++;
//		select.append(keyContador);
//		htCodigos.put(new Integer(keyContador), idGuardia);
//		select.append("    AND TRUNC(BAJAS.FECHABT) BETWEEN :");
//		keyContador++;
//		select.append(keyContador);
//		htCodigos.put(new Integer(keyContador), fechaDesde);
//		select.append("                             AND :");
//		keyContador++;
//		select.append(keyContador);
//		htCodigos.put(new Integer(keyContador), fechaHasta);
		SQL sql = new SQL();
		sql.SELECT("BAJAS.*");
		sql.FROM("CEN_BAJASTEMPORALES BAJAS, SCS_INSCRIPCIONGUARDIA INS");
		sql.WHERE("BAJAS.IDINSTITUCION = INS.IDINSTITUCION");
		sql.WHERE("BAJAS.IDPERSONA = INS.IDPERSONA");
		sql.WHERE("BAJAS.VALIDADO = 1");
		sql.WHERE("INS.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("INS.IDTURNO = " + idTurno);
		sql.WHERE("INS.IDGUARDIA = " + idGuardia);
		sql.WHERE("TRUNC(BAJAS.FECHABT) BETWEEN '" + fechaDesde + "' AND '" + fechaHasta +  "'");
		return sql.toString();
	}
	
	
	public String insertGrupoGuardiaColegiadoCalendario(String idCalendarioGuardias, String idTurno, String idInstitucion, String idGuardia) {
//		String sql = "INSERT INTO SCS_GRUPOGUARDIACOLEGIADO_HIST ( " +
//		" IDINSTITUCION, " +
//		" IDTURNO, " +
//		" IDGUARDIA, " +
//		" IDCALENDARIOGUARDIAS, " +
//		" IDGRUPOGUARDIACOLEGIADO, " +
//		" ORDEN, " +				
//		" IDGRUPOGUARDIA, " +
//		" NUMEROGRUPO, " +
//		" FECHACREACION, " +
//		" USUCREACION) " +	
//	" (SELECT " + 
//		" IG.IDINSTITUCION, " +
//		" IG.IDTURNO, " +
//		" IG.IDGUARDIA, " +  
//		this.idCalendarioGuardias + " AS IDCALENDARIOGUARDIAS, " +
//		" GGC.IDGRUPOGUARDIACOLEGIADO, " +
//		" GGC.ORDEN, " +
//		" GG.IDGRUPOGUARDIA, " +
//		" GG.NUMEROGRUPO, " +
//		" GGC.FECHAMODIFICACION AS FECHACREACION, " +
//		" GGC.USUMODIFICACION AS USUCREACION " +
//		
//	" FROM SCS_GUARDIASTURNO GT, " +
//		" SCS_INSCRIPCIONGUARDIA IG, " +
//		" SCS_GRUPOGUARDIACOLEGIADO GGC, " +
//		" SCS_GRUPOGUARDIA GG " +
//		
//	" WHERE GT.IDINSTITUCION = " + this.idInstitucion +
//		" AND GT.IDTURNO = " + this.idTurno +
//		" AND GT.IDGUARDIA = " + this.idGuardia +
//		" AND GT.PORGRUPOS = '1' " +
//		" AND GT.IDINSTITUCION = IG.IDINSTITUCION " +
//		" AND GT.IDTURNO = IG.IDTURNO " +
//		" AND GT.IDGUARDIA = IG.IDGUARDIA " +				
//		" AND IG.IDINSTITUCION = GGC.IDINSTITUCION " +
//		" AND IG.IDTURNO = GGC.IDTURNO " +
//		" AND IG.IDGUARDIA = GGC.IDGUARDIA " +
//		" AND IG.IDPERSONA = GGC.IDPERSONA " +
//		" AND IG.FECHASUSCRIPCION = GGC.FECHASUSCRIPCION " +
//		" AND GGC.IDGRUPOGUARDIA = GG.IDGRUPOGUARDIA " +
//		" AND (IG.FECHAVALIDACION IS NOT NULL AND IG.FECHAVALIDACION <= SYSDATE) " + 
//		" AND (IG.FECHABAJA IS NULL OR IG.FECHABAJA > SYSDATE))";	
		SQL subquery = new SQL();
		subquery.SELECT(" IG.IDINSTITUCION, " +
				" IG.IDTURNO, " +
				" IG.IDGUARDIA, " +  
				idCalendarioGuardias + " AS IDCALENDARIOGUARDIAS, " +
				" GGC.IDGRUPOGUARDIACOLEGIADO, " +
				" GGC.ORDEN, " +
				" GG.IDGRUPOGUARDIA, " +
				" GG.NUMEROGRUPO, " +
				" GGC.FECHAMODIFICACION AS FECHACREACION, " +
				" GGC.USUMODIFICACION AS USUCREACION ");
		subquery.FROM("SCS_GUARDIASTURNO GT, " + 
				"				 SCS_INSCRIPCIONGUARDIA IG," + 
				"				 SCS_GRUPOGUARDIACOLEGIADO GGC, " + 
				"				 SCS_GRUPOGUARDIA GG");
		subquery.WHERE("GT.IDINSTITUCION = " + idInstitucion  + 
				"				 AND GT.IDTURNO = " + idTurno  + 
				"				 AND GT.IDGUARDIA = " + idGuardia  + 
				"				 AND GT.PORGRUPOS = '1' " + 
				"				 AND GT.IDINSTITUCION = IG.IDINSTITUCION " + 
				"				 AND GT.IDTURNO = IG.IDTURNO " + 
				"				 AND GT.IDGUARDIA = IG.IDGUARDIA " + 
				"				 AND IG.IDINSTITUCION = GGC.IDINSTITUCION " + 
				"				AND IG.IDTURNO = GGC.IDTURNO " + 
				"				AND IG.IDGUARDIA = GGC.IDGUARDIA " + 
				"				AND IG.IDPERSONA = GGC.IDPERSONA " + 
				"				AND IG.FECHASUSCRIPCION = GGC.FECHASUSCRIPCION " + 
				"				AND GGC.IDGRUPOGUARDIA = GG.IDGRUPOGUARDIA " + 
				"				AND (IG.FECHAVALIDACION IS NOT NULL AND IG.FECHAVALIDACION <= SYSDATE) " + 
				"				AND (IG.FECHABAJA IS NULL OR IG.FECHABAJA > SYSDATE)");
		SQL sql = new SQL();
		sql.INSERT_INTO("SCS_GRUPOGUARDIACOLEGIADO_HIST ( "+
				"				IDINSTITUCION, " + 
				"				IDTURNO, " + 
				"				IDGUARDIA, " + 
				"				IDCALENDARIOGUARDIAS, " + 
				"				IDGRUPOGUARDIACOLEGIADO, "  + 
				"				ORDEN, " +		
				"				IDGRUPOGUARDIA, " + 
				"				NUMEROGRUPO, "  + 
				"				FECHACREACION, "  + 
				"				USUCREACION )" +
				"( " + subquery + " )");
		return sql.toString();
	}
	
	public String getSaltosCompensacionesGrupo(String idTurno, String idInstitucion, String idGuardia) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("SCS_SALTOCOMPENSACIONGRUPO");
		//sql.WHERE("SALTOOCOMPENSACION = '" + saltoOcompensacion + "'");
		sql.WHERE("FECHACUMPLIMIENTO is null");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDTURNO = " + idTurno);
		sql.WHERE("IDGUARDIA = " + idGuardia);
		return sql.toString();
	}
	
	public String getLetradosGrupos(String fechaGuardia, String idTurno, String idInstitucion, String idGuardia, String idGrupoGuardia) {
		String consulta ="Select " ;
		if(fechaGuardia!=null && !fechaGuardia.equals("")){
			consulta+=" (case "+
		         " when Ins.Fechavalidacion Is Not Null And "+
		              " Trunc(Ins.Fechavalidacion) <= "+
		              " nvl('"+fechaGuardia+"', Ins.Fechavalidacion) And "+
		              " (Ins.Fechabaja Is Null Or "+
		              " Trunc(Ins.Fechabaja) > nvl('"+fechaGuardia+"', '01/01/1900')) then "+
		          " '1' "+
		         " else "+
		          " '0' "+
		       " end) Activo, ";
		}else{
			consulta+="'1' Activo,";
		}
		consulta+="       Ins.Idinstitucion,"+
				"       Ins.Idturno, " +
				"       Ins.Idguardia, " +
				"       Per.Idpersona, " +
				"       Ins.fechasuscripcion As Fechasuscripcion, "+
				"       TO_CHAR(TRUNC(Ins.fechavalidacion),'DD/MM/YYYY') As Fechavalidacion, "+
			    "       TO_CHAR(trunc(Ins.fechabaja),'DD/MM/YYYY') As Fechabaja, "+
			    
			    "       Per.Nifcif,"+
				"       Per.Nombre, " +
				"       Per.Apellidos1, " +
				"       Decode(Per.Apellidos2, Null, '', ' ' || Per.Apellidos2) apellidos2, " +
				"       Per.Apellidos1 || " +
				"       Decode(Per.Apellidos2, Null, '', ' ' || Per.Apellidos2) ALFABETICOAPELLIDOS, " +
				"       Decode(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) NUMEROCOLEGIADO, " +
				"       Per.Fechanacimiento FECHANACIMIENTO, " +
				"       Ins.Fechavalidacion AS ANTIGUEDADCOLA, " +
				"       Decode(Gua.Porgrupos, '1', Gru.IDGRUPOGUARDIACOLEGIADO, Null) As Idgrupoguardiacolegiado, " +
				"       Decode(Gua.Porgrupos, '1', Gru.IDGRUPO, Null) As Grupo, " +
				"       Decode(Gua.Porgrupos, '1', Grg.NUMEROGRUPO, Null) As numeroGrupo, " +
				"       Decode(Gua.Porgrupos, '1', Gru.ORDEN, Null) As Ordengrupo " +
				"  From Scs_Guardiasturno         Gua, " +
				"       Cen_Colegiado             Col, " +
				"       Cen_Persona               Per, " +
				"       Scs_Inscripcionguardia    Ins, " +
				"       SCS_GRUPOGUARDIACOLEGIADO Gru, " +
				"       SCS_GRUPOGUARDIA Grg " +
				" Where Col.Idpersona = Per.Idpersona " +
				"   And Ins.Idinstitucion = Gua.Idinstitucion " +
				"   And Ins.Idturno = Gua.Idturno " +
				"   And Ins.Idguardia = Gua.Idguardia " +
				"   And Ins.Idinstitucion = Col.Idinstitucion " +
				"   And Ins.Idpersona = Col.Idpersona " +
				"   And Ins.Idinstitucion = Gru.Idinstitucion(+) " +
				"   And Ins.Idturno = Gru.Idturno(+) " +
				"   And Ins.Idguardia = Gru.Idguardia(+) " +
				"   And Ins.Idpersona = Gru.Idpersona(+) " +
				"   And Ins.Fechasuscripcion = Gru.Fechasuscripcion(+) " +
				"   And Gru.Idgrupoguardia = Grg.Idgrupoguardia(+) "+
			"   And Gru.IDINSTITUCION = "+idInstitucion+" " +
			"   And Gru.IDTURNO = "+idTurno+" " +
			"   And Gru.IDGUARDIA = "+idGuardia+" " +
			"   And Gru.IDGRUPO = "+idGrupoGuardia+" ";
		consulta+=" ORDER BY ORDENGRUPO ";
		return consulta;
	}
	
	
	public String getUltimoColegiadoGrupo(String idTurno, String idInstitucion, String idGuardia) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT idpersona, Fechasuscripcion, IdgrupoguardiaColegiado ");
		sql.append("   FROM  ");
		sql.append(" ( ");
		sql.append(" SELECT gru.idpersona, gru.Fechasuscripcion, Gru.IdgrupoguardiaColegiado, gru.orden, gru.idgrupoguardia ");
		sql.append("   FROM Scs_Grupoguardiacolegiado Gru, ");
		sql.append("        Scs_Guardiasturno         Gua, ");
		sql.append("        Scs_Grupoguardiacolegiado gruult ");

		// ... dado el grupo que figura como ultimo en la guardia ...
		sql.append("  WHERE Gruult.Idinstitucion = Gua.Idinstitucion ");
		sql.append("    AND Gruult.Idturno = Gua.Idturno ");
		sql.append("    AND Gruult.Idguardia = Gua.Idguardia ");
		sql.append("    and gruult.idgrupoguardiacolegiado = Gua.Idgrupoguardia_Ultimo ");
		
		// ... se sacan los colegiados de dicho grupo ...
		sql.append("    and gruult.idgrupoguardia = gru.idgrupoguardia ");
		sql.append("    and gruult.Idinstitucion = Gru.Idinstitucion ");
		sql.append("    AND gruult.Idturno = Gru.Idturno ");
		sql.append("    AND gruult.Idguardia = Gru.Idguardia ");

		sql.append("    AND Gru.Idinstitucion = " + idInstitucion + " ");
		sql.append("    AND Gru.Idturno = " + idTurno + " ");
		sql.append("    AND Gru.Idguardia = " + idGuardia);
		
		// ... se ordenan por su orden de forma descendente ...
		sql.append("  ORDER BY Gru.Orden desc ");
		sql.append("  ) ");
		
		// ... y asi podemos obtener el ultimo colegiado del grupo ...
		sql.append("  WHERE ROWNUM = 1 ");
		
		return sql.toString();
	}
	
	
	
	public String updateGuardiasTurno(GuardiasTurnoItem item) {
		SQL sql = new SQL();
		sql.UPDATE("GT");
		if (item.getIdInstitucion()!= null) {
			sql.SET("GT.IDINSTITUCION = " + item.getIdInstitucion());
		}
		if (item.getIdTurno()!= null) {
			sql.SET("GT.IDTURNO = " + item.getIdTurno());
		}
		if (item.getIdGuardia()!= null) {
			sql.SET("GT.IDGUARDIA = " + item.getIdGuardia());
		}
		if (item.getNombre()!= null) {
			sql.SET("GT.NOMBRE = " + item.getNombre());
		}
		if (item.getNumeroLetradosGuardia()!= null) {
			sql.SET("GT.NUMEROLETRADOSGUARDIA = " + item.getNumeroLetradosGuardia());
		}
		if (item.getNumeroSustitutosGuardia()!= null) {
			sql.SET("GT.NUMEROSUSTITUTOSGUARDIA = " + item.getNumeroSustitutosGuardia());
		}
		if (item.getDiasGuardia()!= null) {
			sql.SET("GT.DIASGUARDIA = " + item.getDiasGuardia());
		}
		//...
		if (item.getIdPersona_Ultimo()!= null) {
			sql.SET("GT.IDPERSONA_ULTIMO = " + item.getIdPersona_Ultimo());
		}
		if (item.getIdGrupoGuardiaColegiado_Ultimo()!= null) {
			sql.SET("GT.IDGRUPOGUARDIA_ULTIMO = " + item.getIdGrupoGuardiaColegiado_Ultimo());
		}
		if (item.getFechaSuscripcion_Ultimo()!= null) {
			sql.SET("GT.FECHASUSCRIPCION_ULTIMO = " + item.getFechaSuscripcion_Ultimo());
		}
				
		sql.FROM("SCS_GUARDIASTURNO GT");
		return sql.toString();

		
//		sql.SELECT(", , , , , , , DIASPAGADOS, VALIDARJUSTIFICACIONES, \r\n" + 
//				"		DIASSEPARACIONGUARDIAS, NUMEROASISTENCIAS, NUMEROACTUACIONES, DESCRIPCION, DESCRIPCIONFACTURACION, DESCRIPCIONPAGO, IDORDENACIONCOLAS, \r\n" + 
//				"		IDPARTIDAPRESUPUESTARIA, DIASPERIODO, TIPODIASPERIODO, FECHAMODIFICACION, \r\n" + 
//				"		USUMODIFICACION, SELECCIONLABORABLES, SELECCIONFESTIVOS, IDGUARDIASUSTITUCION, IDTURNOSUSTITUCION, PORGRUPOS, ROTARCOMPONENTES, IDINSTITUCIONPRINCIPAL, \r\n" + 
//				"		IDTURNOPRINCIPAL, IDGUARDIAPRINCIPAL, TIPODIASGUARDIA, IDTIPOGUARDIA, ENVIOCENTRALITA");

	}
	
	public String getColaGuardia(String fechaInicio, String fechaFin, String idinstitucion, String idturno, String idguardia, String order) {
		StringBuffer consulta = new StringBuffer();
		consulta.append("Select ");
		consulta.append("       (case when Ins.Fechavalidacion Is Not Null ");
		consulta.append("              And Trunc(Ins.Fechavalidacion) <= nvl("+fechaInicio+",  Ins.Fechavalidacion) ");
		consulta.append("              And (Ins.Fechabaja Is Null Or ");
		consulta.append("                   Trunc(Ins.Fechabaja) > nvl("+fechaFin+", '01/01/1900')) ");
		consulta.append("             then '1' ");
		consulta.append("             else '0' ");
		consulta.append("        end) Activo, ");
		consulta.append("       Ins.Idinstitucion,"+
		"       Ins.Idturno, " +
		"       Ins.Idguardia, " +
		"       Per.Idpersona, " +
		"       Ins.fechasuscripcion As Fechasuscripcion, "+
		"       TO_CHAR(TRUNC(Ins.fechavalidacion),'DD/MM/YYYY') As Fechavalidacion, "+
	    "       TO_CHAR(trunc(Ins.fechabaja),'DD/MM/YYYY') As Fechabaja, "+
	    
	    "       Per.Nifcif,"+
		"       Per.Nombre, " +
		"       Per.Apellidos1, " +
		"       Decode(Per.Apellidos2, Null, '', ' ' || Per.Apellidos2) apellidos2, " +
		"       Per.Apellidos1 || " +
		"       Decode(Per.Apellidos2, Null, '', ' ' || Per.Apellidos2) ALFABETICOAPELLIDOS, " +
		"       Decode(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) NUMEROCOLEGIADO, " +
		"       Per.Fechanacimiento FECHANACIMIENTO, " +
		"       Ins.Fechavalidacion AS ANTIGUEDADCOLA, " +
		"       Decode(Gua.Porgrupos, '1', Gru.IDGRUPOGUARDIACOLEGIADO, Null) As Idgrupoguardiacolegiado, " +
		"       Decode(Gua.Porgrupos, '1', Grg.IDGRUPOGUARDIA, Null) As Grupo, " +
		"       Decode(Gua.Porgrupos, '1', Grg.NUMEROGRUPO, Null) As numeroGrupo, " +
		"       Decode(Gua.Porgrupos, '1', Gru.ORDEN, Null) As Ordengrupo " +
		"  From Scs_Guardiasturno         Gua, " +
		"       Cen_Colegiado             Col, " +
		"       Cen_Persona               Per, " +
		"       Scs_Inscripcionguardia    Ins, " +
		"       SCS_GRUPOGUARDIACOLEGIADO Gru, " +
		"       SCS_GRUPOGUARDIA Grg " +
		" Where Col.Idpersona = Per.Idpersona " +
		"   And Ins.Idinstitucion = Gua.Idinstitucion " +
		"   And Ins.Idturno = Gua.Idturno " +
		"   And Ins.Idguardia = Gua.Idguardia " +
		"   And Ins.Idinstitucion = Col.Idinstitucion " +
		"   And Ins.Idpersona = Col.Idpersona " +
		"   And Ins.Idinstitucion = Gru.Idinstitucion(+) " +
		"   And Ins.Idturno = Gru.Idturno(+) " +
		"   And Ins.Idguardia = Gru.Idguardia(+) " +
		"   And Ins.Idpersona = Gru.Idpersona(+) " +
		"   And Ins.Fechasuscripcion = Gru.Fechasuscripcion(+) " +
		"   And Gru.Idgrupoguardia = Grg.Idgrupoguardia(+) " );
		
		consulta.append("   And Ins.Fechavalidacion Is Not Null ");
		consulta.append("   And Gua.Idinstitucion = "+idinstitucion+" ");
		consulta.append("   And Gua.Idturno = "+idturno+" ");
		consulta.append("   And Gua.Idguardia = "+idguardia+" ");
		
		consulta.append(" order by " + order);
		// Para el caso de que coincida el orden establecido, añadimos un orden que siempre deberia ser diferente: la fecha de suscripcion
		consulta.append(", Ins.FECHASUSCRIPCION, Ins.Idpersona ");
		
		return consulta.toString();
	}
	
	public String getOrdenacionColas(String idOrdenacionColas) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("SCS_ORDENACIONCOLAS OC");
		sql.WHERE("IDORDENACIONCOLAS = " + idOrdenacionColas);
		return sql.toString();
	}
	
	public String getNuevoId(String idinstitucion, String idturno){
		String consulta = "SELECT MAX(saltos.IDSALTOSTURNO) +1 AS IDSALTOSTURNO";
		consulta += " FROM SCS_SALTOSCOMPENSACIONES saltos";
		consulta += " WHERE ";
		consulta += " saltos.IDINSTITUCION = "+idinstitucion;
		consulta += " AND saltos.IDTURNO ="+idturno;
		return consulta;
	}
	
	
	public String guardarSaltosCompensacionesGrupo(SaltoCompGuardiaGrupoItem saltoItem, String idInstitucion, Integer usuario) {

		SQL sql = new SQL();

		sql.INSERT_INTO("SCS_SALTOCOMPENSACIONGRUPO");

		sql.VALUES("IDSALTOCOMPENSACIONGRUPO", "'" + saltoItem.getIdSaltoCompensacionGrupo() + "'");
		sql.VALUES("IDGRUPOGUARDIA", "'" + saltoItem.getIdGrupoGuardia() + "'");
		sql.VALUES("SALTOOCOMPENSACION", "'" + saltoItem.getSaltoCompensacion() + "'");
		sql.VALUES("FECHA", "'" + saltoItem.getFecha() + "'");
		sql.VALUES("FECHACUMPLIMIENTO", saltoItem.getFechaCumplimiento());
		sql.VALUES("MOTIVO", "'" + saltoItem.getMotivo() + "'");
		sql.VALUES("MOTIVOCUMPLIMIENTO", saltoItem.getMotivoCumplimiento());
		sql.VALUES("IDINSTITUCION", "'" + idInstitucion + "'");
		sql.VALUES("IDTURNO", "'" + saltoItem.getIdTurno() + "'");
		sql.VALUES("IDGUARDIA", "'" + saltoItem.getIdGuardia() + "'");
		sql.VALUES("IDCALENDARIOGUARDIAS", saltoItem.getIdCalendarioGuardias());
		sql.VALUES("IDINSTITUCION_CUMPLI", saltoItem.getIdInstitucion_Cumpli());
		sql.VALUES("IDTURNO_CUMPLI", saltoItem.getIdTurno_Cumpli());
		sql.VALUES("IDGUARDIA_CUMPLI", saltoItem.getIdGuardia_Cumpli());
		sql.VALUES("IDCALENDARIOGUARDIAS_CUMPLI", saltoItem.getIdCalendarioGuardias_Cumpli());
		sql.VALUES("FECHACREACION", "SYSTIMESTAMP");
		sql.VALUES("USUCREACION", usuario.toString());
		sql.VALUES("FECHAMODIFICACION", "SYSTIMESTAMP");
		sql.VALUES("USUMODIFICACION",  usuario.toString() );
		sql.VALUES("TIPOMANUAL", saltoItem.getTipoManual());

		return sql.toString();
	}
	
	
	public String nextIdSaltoOComp() {
		return "select sq_scs_saltocompensaciongrupo.Nextval as ID from dual";
	}
	
	public String diasSeparacionEntreGuardias(String idpersona,String idinstitucion, String idturno, String idguardia, Boolean esPermuta, String fechaPeriodoPrimerDiaOriginal, String fechaPeriodoPrimerDia, String fechaPeriodoUltimoDia) {
		
			SQL sqlMax = new SQL();
			sqlMax.SELECT("TO_CHAR(MAX(trunc(FECHAFIN)), 'dd/MM/yyyy') AS MAXIMA");
			sqlMax.FROM("SCS_GUARDIASCOLEGIADO");
			if (idpersona != null) {
				sqlMax.WHERE("IDPERSONA="+idpersona);
			}
			if (idinstitucion != null) {
				sqlMax.WHERE(" IDINSTITUCION ="+idinstitucion);
			}
			if (idturno != null) {
				sqlMax.WHERE(" IDTURNO ="+idturno);
			}
			if (idguardia != null) {
				sqlMax.WHERE(" IDGUARDIA ="+idguardia);
			}
			if (esPermuta)
				sqlMax.WHERE(" trunc(FECHAINICIO) <> TO_DATE('"+fechaPeriodoPrimerDiaOriginal+"','DD/MM/YYYY')");
			sqlMax.WHERE(" trunc(FECHAFIN) <= TO_DATE('"+fechaPeriodoPrimerDia+"','DD/MM/YYYY')");
		

			SQL sqlMin = new SQL();
			sqlMin.SELECT("TO_CHAR(MIN(trunc(FECHAFIN)), 'dd/MM/yyyy') AS MINIMA");
			sqlMin.FROM("SCS_GUARDIASCOLEGIADO");
			if (idpersona != null) {
				sqlMin.WHERE("IDPERSONA="+idpersona);
			}
			if (idinstitucion != null) {
				sqlMin.WHERE(" IDINSTITUCION ="+idinstitucion);
			}
			if (idturno != null) {
				sqlMin.WHERE(" IDTURNO ="+idturno);
			}
			if (idguardia != null) {
				sqlMin.WHERE(" IDGUARDIA ="+idguardia);
			}
			if (esPermuta)
				sqlMin.WHERE(" trunc(FECHAINICIO) <> TO_DATE('"+fechaPeriodoPrimerDiaOriginal+"','DD/MM/YYYY')");
			sqlMin.WHERE(" trunc(FECHAFIN) <= TO_DATE('"+fechaPeriodoUltimoDia+"','DD/MM/YYYY')");

		
		
		SQL sql = new SQL();
		sql.SELECT("COUNT(coleg.IDINSTITUCION) AS TOTAL");
		sql.FROM("SCS_GUARDIASCOLEGIADO coleg");
		sql.JOIN("SCS_GUARDIASTURNO guard ON coleg.IDINSTITUCION =guard.IDINSTITUCION AND coleg.IDTURNO=guard.IDTURNO AND coleg.IDGUARDIA=guard.IDGUARDIA");
		if (idpersona != null) {
		sql.WHERE("coleg.IDPERSONA= " + idpersona);
		}
		if (idinstitucion != null) {
		sql.WHERE("coleg.IDINSTITUCION= " + idinstitucion);
		}
		if (idturno != null) {
			sql.WHERE("coleg.IDTURNO = "+ idturno);
		}
		if (idguardia != null) {
			sql.WHERE("coleg.IDGUARDIA = "+idguardia);
		}
		sql.WHERE("coleg.RESERVA='N'");

		sql.WHERE ( "       (abs(TO_DATE(("+sqlMax+"),'DD/MM/YYYY') - TO_DATE('"+fechaPeriodoPrimerDia+"','DD/MM/YYYY')) <= guard.DIASSEPARACIONGUARDIAS)"
			+"       OR "
			+ "       (abs(TO_DATE(("+sqlMin+"),'DD/MM/YYYY') - TO_DATE('"+fechaPeriodoUltimoDia+"','DD/MM/YYYY')) <= guard.DIASSEPARACIONGUARDIAS)");
		return sql.toString();
		
//		String consulta = "SELECT COUNT(coleg.IDINSTITUCION) AS TOTAL";
//		consulta += " FROM ";
//		consulta += "SCS_GUARDIASCOLEGIADO coleg ,";
//		consulta += "SCS_GUARDIASTURNO guard "; 
//		consulta += " WHERE ";
//		consulta += " coleg.IDINSTITUCION =guard.IDINSTITUCION";
//		consulta += " AND coleg.IDTURNO=guard.IDTURNO";
//		consulta += " AND coleg.IDGUARDIA=guard.IDGUARDIA";		
//		if (idpersona != null) {
//			consulta += " AND coleg.IDPERSONA= " + idpersona;
//		}
//		if (idinstitucion != null) {
//			consulta += " AND coleg.IDINSTITUCION= " + idinstitucion;
//		}
//		if (idturno != null) {
//			consulta += " AND coleg.IDTURNO = "+ idturno;
//		}
//		if (idguardia != null) {
//			consulta += " AND coleg.IDGUARDIA = "+idguardia;
//		}
//			consulta += " AND coleg.RESERVA='N'";
//		if (!fechaMIN.equals("") && !fechaMAX.equals("")) {
//			consulta += " AND ( ";
//			consulta += "       (abs(TO_DATE('"+fechaMAX+"','DD/MM/YYYY') - TO_DATE('"+fechaPeriodoPrimerDia+"','DD/MM/YYYY')) <= guard.DIASSEPARACIONGUARDIAS)";
//			consulta += "       OR ";
//			consulta += "       (abs(TO_DATE('"+fechaMIN+"','DD/MM/YYYY') - TO_DATE('"+fechaPeriodoUltimoDia+"','DD/MM/YYYY')) <= guard.DIASSEPARACIONGUARDIAS)";
//			consulta += " ) ";
//		} else {
//			if (fechaMIN.equals("") && !fechaMAX.equals(""))
//				consulta += " AND (abs(TO_DATE('"+fechaMAX+"','DD/MM/YYYY') - TO_DATE('"+fechaPeriodoPrimerDia+"','DD/MM/YYYY')) <= guard.DIASSEPARACIONGUARDIAS)";
//			else if (!fechaMIN.equals("") && fechaMAX.equals(""))
//					consulta += " AND (abs(TO_DATE('"+fechaMIN+"','DD/MM/YYYY') - TO_DATE('"+fechaPeriodoUltimoDia+"','DD/MM/YYYY')) <= guard.DIASSEPARACIONGUARDIAS)";
//		}
//		return consulta;
	}
	
	public String maxFechaInicioPeriodoCabGuardia(String idpersona,String idinstitucion, String idturno, String idguardia, Boolean esPermuta, String fechaPeriodoPrimerDiaOriginal, String fechaPeriodoPrimerDia){

		SQL sql = new SQL();
		sql.SELECT("MAX(trunc(FECHAFIN)) AS MAXIMA");
		sql.FROM("SCS_GUARDIASCOLEGIADO");
		if (idpersona != null) {
			sql.WHERE("IDPERSONA="+idpersona);
		}
		if (idinstitucion != null) {
			sql.WHERE(" IDINSTITUCION ="+idinstitucion);
		}
		if (idturno != null) {
			sql.WHERE(" IDTURNO ="+idturno);
		}
		if (idguardia != null) {
			sql.WHERE(" IDGUARDIA ="+idguardia);
		}
		if (esPermuta)
			sql.WHERE(" trunc(FECHAINICIO) <> TO_DATE('"+fechaPeriodoPrimerDiaOriginal+"','DD/MM/YYYY')");
		sql.WHERE(" trunc(FECHAFIN) <= TO_DATE('"+fechaPeriodoPrimerDia+"','DD/MM/YYYY')");

		return sql.toString();
	}
	
	public String minFechaInicioPeriodoCabGuardia(String idpersona,String idinstitucion, String idturno, String idguardia, Boolean esPermuta, String fechaPeriodoPrimerDiaOriginal, String fechaPeriodoUltimoDia){
	//Consulto la minima fecha inicio para el periodo en la cabecera de guardias:
		SQL sql = new SQL();
		sql.SELECT("MIN(trunc(FECHAFIN)) AS MINIMA");
		sql.FROM("SCS_GUARDIASCOLEGIADO");
		if (idpersona != null) {
			sql.WHERE("IDPERSONA="+idpersona);
		}
		if (idinstitucion != null) {
			sql.WHERE(" IDINSTITUCION ="+idinstitucion);
		}
		if (idturno != null) {
			sql.WHERE(" IDTURNO ="+idturno);
		}
		if (idguardia != null) {
			sql.WHERE(" IDGUARDIA ="+idguardia);
		}
		if (esPermuta)
			sql.WHERE(" trunc(FECHAINICIO) <> TO_DATE('"+fechaPeriodoPrimerDiaOriginal+"','DD/MM/YYYY')");
		sql.WHERE(" trunc(FECHAFIN) <= TO_DATE('"+fechaPeriodoUltimoDia+"','DD/MM/YYYY')");

		return sql.toString();
	}
	
	public String checkIncompatibilidadesCalendario(String idPersona,String idInstitucion, String idTurno, String idGuardia, String fechaGuardia){
		StringBuffer consulta = new StringBuffer();
		
		consulta.append (" select gc.FECHAFIN");
/*		consulta.append ("        + g_incompatibles."+ScsIncompatibilidadGuardiasBean.C_DIASSEPARACIONGUARDIAS+" ");
		consulta.append ("        as FECHAFIN ");
*/		consulta.append ("   from SCS_GUARDIASCOLEGIADO gc, ");
		consulta.append ("        (select IDINSTITUCION, ");
		consulta.append ("                IDTURNO_INCOMPATIBLE, ");
		consulta.append ("                IDGUARDIA_INCOMPATIBLE, ");
		consulta.append ("                DIASSEPARACIONGUARDIAS ");
		consulta.append ("           from SCS_INCOMPATIBILIDADGUARDIAS ");
		consulta.append ("          where IDINSTITUCION = "+idInstitucion);
		consulta.append ("            and IDTURNO = "+idTurno);
		consulta.append ("            and IDGUARDIA = "+idGuardia);
		consulta.append ("         UNION "); //OJO: es necesario mirar ambas, para que funcione igual que se muestra
		consulta.append ("         select IDINSTITUCION, ");
		consulta.append ("               IDTURNO, ");
		consulta.append ("                IDGUARDIA, ");
		consulta.append ("                DIASSEPARACIONGUARDIAS ");
		consulta.append ("           from SCS_INCOMPATIBILIDADGUARDIAS ");
		consulta.append ("          where IDINSTITUCION = "+idInstitucion);
		consulta.append ("            and IDTURNO_INCOMPATIBLE= "+idTurno);
		consulta.append ("            and IDGUARDIA_INCOMPATIBLE = "+idGuardia);
		consulta.append ("        ) g_incompatibles ");
		consulta.append ("  where gc.IDINSTITUCION = g_incompatibles.IDINSTITUCION ");
		consulta.append ("    and gc.IDTURNO = g_incompatibles.IDTURNO_INCOMPATIBLE ");
		consulta.append ("    and gc.IDGUARDIA = g_incompatibles.IDGUARDIA_INCOMPATIBLE ");
		consulta.append ("    and gc.IDPERSONA = "+idPersona);
		consulta.append (" and abs (gc.FECHAFIN - to_date('"+fechaGuardia+"', 'DD/MM/YYYY')) ");
		consulta.append (" <= g_incompatibles.DIASSEPARACIONGUARDIAS ");
	
		return consulta.toString();
	}
	
	
	public String updateSaltosCompensacionesGrupo(SaltoCompGuardiaGrupoItem saltoItem, String idInstitucion, Integer usuario) {

		SQL sql = new SQL();

		sql.UPDATE("SCS_SALTOCOMPENSACIONGRUPO");

		sql.VALUES("FECHACUMPLIMIENTO", saltoItem.getFechaCumplimiento());
		sql.VALUES("MOTIVOCUMPLIMIENTO", saltoItem.getMotivoCumplimiento());
		sql.VALUES("IDINSTITUCION_CUMPLI", saltoItem.getIdInstitucion_Cumpli());
		sql.VALUES("IDTURNO_CUMPLI", saltoItem.getIdTurno_Cumpli());
		sql.VALUES("IDGUARDIA_CUMPLI", saltoItem.getIdGuardia_Cumpli());
		sql.VALUES("IDCALENDARIOGUARDIAS_CUMPLI", saltoItem.getIdCalendarioGuardias_Cumpli());
		sql.VALUES("FECHAMODIFICACION", "SYSTIMESTAMP");
		sql.VALUES("USUMODIFICACION",  usuario.toString() );
		sql.WHERE("IDSALTOCOMPENSACIONGRUPO = " + "'" + saltoItem.getIdSaltoCompensacionGrupo() + "'");
		return sql.toString();
	}
	
	
	public String marcarSaltoCompensacion(Integer usuario, String idturno, ScsSaltoscompensaciones saltoCompensacion,String s_idpersona, String s_idinstitucion, String s_idturno, String s_idguardia,String s_saltocompensacion, String fechaCumplimiento) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE ");
		sql.append("SCS_SALTOSCOMPENSACIONES");
		sql.append(" SET ");
		sql.append("FECHACUMPLIMIENTO");
		sql.append(" = ");
		sql.append("TO_DATE( '" + fechaCumplimiento + "', dd/MM/yy)");
		sql.append(", ");
		sql.append("USUMODIFICACION");
		sql.append(" = ");
		sql.append(usuario.toString());
		sql.append(",");
		sql.append("FECHAMODIFICACION");
		sql.append(" = SYSDATE  ");
		if (saltoCompensacion.getIdguardia() != null) {
			sql.append(" , ");
			sql.append("IDCALENDARIOGUARDIAS");
			sql.append(" = ");
			sql.append(saltoCompensacion.getIdcalendarioguardias());
			sql.append(" ");
		}
		if (saltoCompensacion.getMotivos() != null && !saltoCompensacion.getMotivos().equals("")) {
			sql.append(" , ");
			sql.append("MOTIVOS");
			sql.append(" = ");
			sql.append("MOTIVOS");
			sql.append(" || '");
			sql.append(saltoCompensacion.getMotivos());
			sql.append("' ");
		}

		
		StringBuilder where = new StringBuilder();
		where.append(" WHERE ");
		where.append("IDINSTITUCION");
		where.append("=");
		where.append(s_idinstitucion);
		if (idturno != null && !s_idturno.equals("")) {
			where.append("   AND ");
			where.append("IDTURNO");
			where.append("=");
			where.append(s_idturno);
		}
		where.append("   AND ");
		where.append("IDGUARDIA");
		if (s_idguardia != null && !s_idguardia.equals("")) {
			where.append("=");
			where.append(s_idguardia);
		} else {
			where.append(" IS NULL ");
		}
		if (s_idpersona != null && !s_idpersona.equals("")) {
			where.append("   AND ");
			where.append("IDPERSONA");
			where.append("=");
			where.append(s_idpersona);
		}
		if (s_saltocompensacion.charAt(0) != ' ') {
			where.append("   AND ");
			where.append("SALTOOCOMPENSACION");
			where.append("= '");
			where.append(s_saltocompensacion.charAt(0));
			where.append("'");
		}
		where.append("   AND ");
		where.append("FECHACUMPLIMIENTO");
		where.append(" IS NULL ");
		sql.append(where);

		if (saltoCompensacion.getIdsaltosturno() != null) {
			sql.append(" AND ");
			sql.append("IDSALTOSTURNO");
			sql.append(" = '");
			sql.append(saltoCompensacion.getIdsaltosturno());
		}

		sql.append(" AND rownum=1");
		
		return sql.toString();
	}
	
	public String getGrupoData( String idGrupo) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT gg.idgrupoguardia, ggc.idgrupoguardiacolegiado, orden ");
		sql.append(" FROM scs_grupoguardiacolegiado ggc, scs_grupoguardia gg ");
		sql.append(" WHERE ggc.idgrupoguardia = gg.idgrupoguardia ");
		sql.append(" AND gg.idgrupoguardia ="+idGrupo+" ");
		sql.append(" ORDER BY gg.idgrupoguardia, orden");
		return sql.toString();
	}
	
	
	public String getSaltoCompensacionesActivo( String s_idinstitucion, String s_idturno, String s_idguardia, String s_idpersona, String s_saltocompensacion) {
	
		StringBuilder where = new StringBuilder();
	
		where.append(" WHERE ");
		where.append("IDINSTITUCION");
		where.append("=");
		where.append(s_idinstitucion);
		if (s_idturno != null && !s_idturno.equals("")) {
			where.append("   AND ");
			where.append("IDTURNO");
			where.append("=");
			where.append(s_idturno);
		}
		where.append("   AND ");
		where.append("IDGUARDIA");
		if (s_idguardia != null && !s_idguardia.equals("")) {
			where.append("=");
			where.append(s_idguardia);
		} else {
			where.append(" IS NULL ");
		}
		if (s_idpersona != null && !s_idpersona.equals("")) {
			where.append("   AND ");
			where.append("IDPERSONA");
			where.append("=");
			where.append(s_idpersona);
		}
		if (s_saltocompensacion.charAt(0) != ' ') {
			where.append("   AND ");
			where.append("SALTOOCOMPENSACION");
			where.append("= '");
			where.append(s_saltocompensacion.charAt(0));
			where.append("'");
		}
			where.append("   AND ");
			where.append("FECHACUMPLIMIENTO");
			where.append(" IS NULL ");
			
		
			StringBuilder sql = new StringBuilder();
			sql.append(" select * ");
			sql.append("   from ");
			sql.append("SCS_SALTOSCOMPENSACIONES");
			sql.append(where.toString());
			
		
			sql.append(" order by ");
			sql.append("FECHA");
			sql.append(", ");
			sql.append("IDSALTOSTURNO");
	
		return sql.toString();
	}
	
	public String getPersonaById (String idPersona) {
		if (idPersona != null && !idPersona.isEmpty()){
		String sql = "select * from CEN_PERSONA WHERE idpersona IN (" + idPersona + ")";
		return sql;
		}else {
			return null;
		}
		
	}
	
	public String getInscripcionesTurnoActiva(String idPersona,String idInstitucion, String idTurno, String fecha) {
		String consulta = 
				"Select " +
						"       Ins.Idinstitucion,"+
						"       Ins.Idturno, " +
						"       Per.Idpersona, " +
						"       Ins.fechasolicitud, "+
						"       TO_CHAR(TRUNC(Ins.fechavalidacion),'DD/MM/YYYY') As Fechavalidacion, "+
					    "       TO_CHAR(trunc(Ins.fechabaja),'DD/MM/YYYY') As Fechabaja, "+
					    
						"       Per.Nombre, " +
						"       Per.Apellidos1, " +
						"       Decode(Per.Apellidos2, Null, '', ' ' || Per.Apellidos2) apellidos2, " +
						"       Per.Apellidos1 || " +
						"       Decode(Per.Apellidos2, Null, '', ' ' || Per.Apellidos2) ALFABETICOAPELLIDOS, " +
						"       Decode(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) NUMEROCOLEGIADO, " +
						"       Per.Fechanacimiento FECHANACIMIENTO, " +
						"       Ins.Fechavalidacion AS ANTIGUEDADCOLA " +
						"  From Scs_Turno              Tur, " +
						"       Cen_Colegiado          Col, " +
						"       Cen_Persona            Per, " +
						"       Scs_Inscripcionturno   Ins " +
						" Where Col.Idpersona = Per.Idpersona " +
						"   And Ins.Idinstitucion = Tur.Idinstitucion " +
						"   And Ins.Idturno = Tur.Idturno " +
						"   And Ins.Idinstitucion = Col.Idinstitucion " +
						"   And Ins.Idpersona = Col.Idpersona " +
				
				"   And Ins.Fechavalidacion Is Not Null " +
				"   And Tur.Idinstitucion = "+idInstitucion+" " +
				"   And Tur.Idturno = "+idTurno+" " +
				
			    "   And Ins.Fechavalidacion Is Not Null " +
				"   And Trunc(Ins.Fechavalidacion) <= nvl("+fecha+",  Ins.Fechavalidacion) " +
				"   And (Ins.Fechabaja Is Null Or " +
				"        Trunc(Ins.Fechabaja) > nvl("+fecha+", '01/01/1900')) "+
				"    and Ins.idpersona ="+idPersona;
				return consulta;
	}
	
	
	public String getInscripcionesGuardiaActiva(String idPersona,String idInstitucion, String idGuardia, String fecha, String idTurno) {
	String consulta = 
			"Select " +
					"       Ins.Idinstitucion,"+
					"       Ins.Idturno, " +
					"       Ins.Idguardia, " +
					"       Per.Idpersona, " +
					"       Ins.fechasuscripcion As Fechasuscripcion, "+
					"       TO_CHAR(TRUNC(Ins.fechavalidacion),'DD/MM/YYYY') As Fechavalidacion, "+
				    "       TO_CHAR(trunc(Ins.fechabaja),'DD/MM/YYYY') As Fechabaja, "+
				    
				    "       Per.Nifcif,"+
					"       Per.Nombre, " +
					"       Per.Apellidos1, " +
					"       Decode(Per.Apellidos2, Null, '', ' ' || Per.Apellidos2) apellidos2, " +
					"       Per.Apellidos1 || " +
					"       Decode(Per.Apellidos2, Null, '', ' ' || Per.Apellidos2) ALFABETICOAPELLIDOS, " +
					"       Decode(Col.Comunitario, '1', Col.Ncomunitario, Col.Ncolegiado) NUMEROCOLEGIADO, " +
					"       Per.Fechanacimiento FECHANACIMIENTO, " +
					"       Ins.Fechavalidacion AS ANTIGUEDADCOLA, " +
					"       Decode(Gua.Porgrupos, '1', Gru.IDGRUPOGUARDIACOLEGIADO, Null) As Idgrupoguardiacolegiado, " +
					"       Decode(Gua.Porgrupos, '1', Gru.IDGRUPOGUARDIA, Null) As Grupo, " +
					"       Decode(Gua.Porgrupos, '1', Grg.NUMEROGRUPO, Null) As numeroGrupo, " +
					"       Decode(Gua.Porgrupos, '1', Gru.ORDEN, Null) As Ordengrupo " +
					"  From Scs_Guardiasturno         Gua, " +
					"       Cen_Colegiado             Col, " +
					"       Cen_Persona               Per, " +
					"       Scs_Inscripcionguardia    Ins, " +
					"       SCS_GRUPOGUARDIACOLEGIADO Gru, " +
					"       SCS_GRUPOGUARDIA Grg " +
					" Where Col.Idpersona = Per.Idpersona " +
					"   And Ins.Idinstitucion = Gua.Idinstitucion " +
					"   And Ins.Idturno = Gua.Idturno " +
					"   And Ins.Idguardia = Gua.Idguardia " +
					"   And Ins.Idinstitucion = Col.Idinstitucion " +
					"   And Ins.Idpersona = Col.Idpersona " +
					"   And Ins.Idinstitucion = Gru.Idinstitucion(+) " +
					"   And Ins.Idturno = Gru.Idturno(+) " +
					"   And Ins.Idguardia = Gru.Idguardia(+) " +
					"   And Ins.Idpersona = Gru.Idpersona(+) " +
					"   And Ins.Fechasuscripcion = Gru.Fechasuscripcion(+) " +
					"   And Gru.Idgrupoguardia = Grg.Idgrupoguardia(+) "+
			
			"   And Ins.Fechavalidacion Is Not Null " +
			"   And Gua.Idinstitucion = "+idInstitucion+" " +
			"   And Gua.Idturno = "+idTurno+" " +
			"   And Gua.Idguardia = "+idGuardia+" " +
			
		    "   And Ins.Fechavalidacion Is Not Null " +
			"   And Trunc(Ins.Fechavalidacion) <= nvl("+fecha+",  Ins.Fechavalidacion) " +
			"   And (Ins.Fechabaja Is Null Or " +
			"        Trunc(Ins.Fechabaja) > nvl("+fecha+", '01/01/1900')) "+
			"    and Ins.idpersona in ("+idPersona + ")";
	
	return consulta;
	}
	
	public String cambiarUltimoCola2(String sIdinstitucion, String sIdTurno, String sIdGuardia, String sIdpersona, String sIdGrupoGuardiaColegiado_Ultimo, 
			String sFechaSusc, String usu) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_GUARDIASTURNO");
		if (sIdGrupoGuardiaColegiado_Ultimo != null && sIdGrupoGuardiaColegiado_Ultimo != "null" && !sIdGrupoGuardiaColegiado_Ultimo.isEmpty() ) {
		sql.SET("IDGRUPOGUARDIA_ULTIMO = " + sIdGrupoGuardiaColegiado_Ultimo);
		}
		if (sFechaSusc != null) {
		sql.SET("FECHASUSCRIPCION_ULTIMO = '" + sFechaSusc + "'");
		}
		if (usu != null) {
		sql.SET("USUMODIFICACION = " + usu);
		}
		sql.SET("FECHAMODIFICACION = SYSDATE");

		sql.WHERE("IDINSTITUCION = " + sIdinstitucion);

		sql.WHERE("IDTURNO = " + sIdTurno);

		sql.WHERE("IDGUARDIA = " + sIdGuardia);
		
		if (sIdpersona != null && sIdpersona != "null" && !sIdpersona.isEmpty() ) {
		sql.WHERE("IDPERSONA_ULTIMO = " + sIdpersona);
		}

		
	return sql.toString();
	}
	
	public String validaGuardiaLetradoPeriodo(String idPersona, String idInstitucion, String idTurno, String idGuardia, String fechaInicio, String fechaFin) {
		String consulta = "SELECT NVL(COUNT(*),0) EXISTEGUARDIA ";
		consulta += " FROM SCS_CABECERAGUARDIAS" ;					
		consulta += " WHERE ";
		consulta += "IDPERSONA ="+idPersona;
		consulta += " AND IDINSTITUCION ="+idInstitucion;
		consulta += " AND IDTURNO ="+idTurno;
		consulta += " AND IDGUARDIA ="+idGuardia;
		consulta += " AND TRUNC(FECHAINICIO) >= '"+fechaInicio+"'";
		consulta += " AND TRUNC(FECHA_FIN) <= '"+fechaFin+"'";
		return consulta;
	}
	
	public String getInstitucionParam(String idModulo, String idParametro)  {
		SQL sql = new SQL();
		sql.SELECT("IDINSTITUCION");
		sql.FROM("GEN_PARAMETROS");
		sql.WHERE("MODULO = '" + idModulo + "'");
		sql.WHERE("PARAMETRO = '" + idParametro + "'");
		return sql.toString();
	}
	
	public String reordenarRestoGrupoLetrados(String idGrupo) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT gg.idgrupoguardia, ggc.idgrupoguardiacolegiado, orden ");
		sql.append(" FROM scs_grupoguardiacolegiado ggc, scs_grupoguardia gg ");
		sql.append(" WHERE ggc.idgrupoguardia = gg.idgrupoguardia ");
		sql.append(" AND gg.idgrupoguardia ="+idGrupo+" ");
		sql.append(" AND ggc.orden > "+30000+ " ");
		sql.append(" ORDER BY gg.idgrupoguardia, orden");
		return sql.toString();
	}
	
	public String updateByPrimaryKeySelective2(ScsGrupoguardiacolegiado record) {
		SQL sql = new SQL();
		sql.UPDATE("SCS_GRUPOGUARDIACOLEGIADO");
		if (record.getIdinstitucion() != null) {
			sql.SET("IDINSTITUCION = #{idinstitucion,jdbcType=DECIMAL}");
		}
		if (record.getIdturno() != null) {
			sql.SET("IDTURNO = #{idturno,jdbcType=DECIMAL}");
		}
		if (record.getIdguardia() != null) {
			sql.SET("IDGUARDIA = #{idguardia,jdbcType=DECIMAL}");
		}
		if (record.getIdpersona() != null) {
			sql.SET("IDPERSONA = #{idpersona,jdbcType=DECIMAL}");
		}
		if (record.getFechasuscripcion() != null) {
			sql.SET("FECHASUSCRIPCION = #{fechasuscripcion,jdbcType=TIMESTAMP}");
		}
		if (record.getIdgrupoguardia() != null) {
			sql.SET("IDGRUPOGUARDIA = #{idgrupoguardia,jdbcType=DECIMAL}");
		}
		if (record.getOrden() != null) {
			sql.SET("ORDEN = #{orden,jdbcType=DECIMAL}");
		}
		if (record.getFechacreacion() != null) {
			sql.SET("FECHACREACION = #{fechacreacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsucreacion() != null) {
			sql.SET("USUCREACION = #{usucreacion,jdbcType=DECIMAL}");
		}
		if (record.getFechamodificacion() != null) {
			sql.SET("FECHAMODIFICACION = #{fechamodificacion,jdbcType=TIMESTAMP}");
		}
		if (record.getUsumodificacion() != null) {
			sql.SET("USUMODIFICACION = #{usumodificacion,jdbcType=DECIMAL}");
		}
		sql.WHERE("IDGRUPOGUARDIACOLEGIADO = #{idgrupoguardiacolegiado,jdbcType=DECIMAL}");
		return sql.toString();
	}
	
	
	public String getGuardiasToProg(DatosCalendarioProgramadoItem programacion, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("COUNT (*) GUARDIAS");
		sql.FROM("SCS_GUARDIASCOLEGIADO");
		sql.WHERE("FECHAINICIO >= TO_DATE('" + programacion.getFechaDesde() + "', 'dd/MM/yyyy')");
		sql.WHERE("FECHAFIN <= TO_DATE('" + programacion.getFechaHasta() + "', 'dd/MM/yyyy')");
		return sql.toString();
	}

	public String getTipoDiaGuardia(String idTurno, String idGuardia, Short idInstitucion) {
		SQL SQL = new SQL();
		SQL.SELECT(new String[]{"CASE  WHEN  'LMXJVSD' LIKE CONCAT('%',CONCAT(gt.SELECCIONLABORABLES,'%')) AND LENGTH(gt.SELECCIONLABORABLES)>1  AND SUBSTR(gt.SELECCIONLABORABLES,1,1) = 'L' AND SUBSTR(gt.SELECCIONLABORABLES,LENGTH(gt.SELECCIONLABORABLES),1) IN ('V','S','D') THEN  (CONCAT(CONCAT(SUBSTR(gt.SELECCIONLABORABLES,1,1), '-'), SUBSTR(gt.SELECCIONLABORABLES,LENGTH(gt.SELECCIONLABORABLES),1)))  WHEN LENGTH(gt.SELECCIONLABORABLES) = 1 THEN gt.SELECCIONLABORABLES  ELSE gt.SELECCIONLABORABLES END AS diaslaborables", "CASE  WHEN  'LMXJVSD' LIKE CONCAT('%',CONCAT(SELECCIONFESTIVOS,'%')) AND LENGTH(SELECCIONFESTIVOS)>1  AND SUBSTR(SELECCIONFESTIVOS,1,1) = 'L' AND SUBSTR(SELECCIONFESTIVOS,LENGTH(SELECCIONFESTIVOS),1) IN ('V','S','D') THEN  (CONCAT(CONCAT(SUBSTR(SELECCIONFESTIVOS,1,1), '-'), SUBSTR(SELECCIONFESTIVOS,LENGTH(SELECCIONFESTIVOS),1))) WHEN LENGTH(SELECCIONFESTIVOS) = 1 THEN SELECCIONFESTIVOS  ELSE SELECCIONFESTIVOS END AS diasfestivos"});
		SQL.FROM("SCS_GUARDIASTURNO gt");
		SQL.WHERE(new String[]{"gt.idturno = '" + idTurno + "'", "gt.idguardia = '" + idGuardia + "'", "gt.idinstitucion = " + idInstitucion});
		return SQL.toString();
	}

	public String getGuardiasToProgByDates(String fechaDesde, String fechaHasta, String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("COUNT (*) GUARDIAS");
		sql.FROM("SCS_GUARDIASCOLEGIADO");
		sql.WHERE("FECHAINICIO >= TO_DATE(' " + fechaDesde + "', 'dd/MM/yyyy')");
		sql.WHERE("FECHAFIN <= TO_DATE(' " + fechaHasta + "', 'dd/MM/yyyy')");
		return sql.toString();
	}

	public String getConjuntoGuardiaFromGuardiaTurno(String idGuardia, String idTurno, String idInstitucion) {
	SQL sql = new SQL();

	 sql.SELECT("CG.IDCONJUNTOGUARDIA");
       sql.FROM("SCS_CONF_CONJUNTO_GUARDIAS CG");
       sql.JOIN("scs_guardiasturno  g on CG.idguardia = g.idguardia and CG.idinstitucion = g.idinstitucion");
       sql.WHERE("CG.idinstitucion = " + idInstitucion);
       sql.WHERE("CG.idTurno = " + idTurno);
       sql.WHERE("g.idGuardia = " + idGuardia);
       sql.WHERE("g.idGuardia = CG.idGuardia");
       return sql.toString();
	}

	public String getIdGuardiaByName( String name) {
		SQL sql = new SQL();
		sql.SELECT("IDGUARDIA");
		sql.FROM("SCS_GUARDIASTURNO");
		sql.WHERE("NOMBRE = '"+ name + "'");
//		sql.WHERE("FECHABAJA IS NULL");
		return sql.toString();
	}

	public String getIdTurnoByName( String name) {
		SQL sql = new SQL();
		sql.SELECT("IDTURNO");
		sql.FROM("SCS_TURNO");
		sql.WHERE("NOMBRE = '"+ name+ "'");
//		sql.WHERE("FECHABAJA IS NULL");
		return sql.toString();
	}
	public String insertHistoricoCalendario(String idCalendar, String idConjuntoGuardia, String idInstitucion, String today, GuardiaCalendarioItem item, String usuModif) {
		SQL sql2 = new SQL();
		sql2.INSERT_INTO("SCS_HCO_CONF_PROG_CALENDARIOS H");
		if (idCalendar != null) {
			sql2.VALUES("IDPROGCALENDARIO", idCalendar);
		}
		if (idConjuntoGuardia != null) {
			sql2.VALUES("IDCONJUNTOGUARDIA", idConjuntoGuardia);
		}
		if (idInstitucion != null) {
			sql2.VALUES("IDINSTITUCION", idInstitucion);
		}
		if (item.getIdTurno() != null) {
			sql2.VALUES("IDTURNO", item.getIdTurno());
		}
		if (item.getIdGuardia()!= null) {
			sql2.VALUES("IDGUARDIA", item.getIdGuardia());
		}
		if (item.getOrden() != null) {
			sql2.VALUES("ORDEN", item.getOrden());
		}
		if (today != null) {
			sql2.VALUES("FECHAMODIFICACION", "'" + today + "'");
		}
		if (usuModif != null) {
			sql2.VALUES("USUMODIFICACION", usuModif);
		}

	//	if (item.getGenerado() != null) {
	//		sql2.VALUES("ESTADO", DECODE(PG.ESTADO, 2, 'No', 'Si'));
	//	}
		return sql2.toString();
	}

	public String getGuardiaColeg(GuardiasItem guardiaItem, String idInstitucion, String idLenguaje) {
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

		// FILTRO POR TURNO
		if (guardiaItem.getIdTurno() != null && guardiaItem.getIdTurno() != "")
			sql.WHERE("SCS_GUARDIASTURNO.IDTURNO = " + guardiaItem.getIdTurno());

		if (guardiaItem.getIdGuardia() != null && guardiaItem.getIdGuardia() != "")
			sql.WHERE("SCS_GUARDIASTURNO.IDGUARDIA = " + guardiaItem.getIdGuardia());


		sql.ORDER_BY("SCS_TURNO.NOMBRE, SCS_GUARDIASTURNO.NOMBRE");


		return sql.toString();
	}


}
