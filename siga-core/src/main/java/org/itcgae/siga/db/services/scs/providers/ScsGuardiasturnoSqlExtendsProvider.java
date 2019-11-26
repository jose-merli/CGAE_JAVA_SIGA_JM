
package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.GuardiasItem;
import org.itcgae.siga.db.mappers.ScsGuardiasturnoSqlProvider;

public class ScsGuardiasturnoSqlExtendsProvider extends ScsGuardiasturnoSqlProvider {

	public String searchGuardias(GuardiasItem guardiaItem, String idInstitucion, String idLenguaje) {
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
			sql.WHERE("SCS_GUARDIASTURNO.IDTURNO ='" + guardiaItem.getIdTurno() + "'");

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
			sql.WHERE("SCS_TURNO.IDJURISDICCION = '" + guardiaItem.getJurisdiccion() + "'");

		// FILTRO POR GRUPOFACTURACION
		if (guardiaItem.getGrupoFacturacion() != null && guardiaItem.getGrupoFacturacion() != "")
			sql.WHERE("SCS_TURNO.IDGRUPOFACTURACION = '" + guardiaItem.getGrupoFacturacion() + "'");

		// FILTRO POR PARTIDAPRESUPUESTARIA
		if (guardiaItem.getPartidaPresupuestaria() != null && guardiaItem.getPartidaPresupuestaria() != "")
			sql.WHERE("SCS_TURNO.IDPARTIDAPRESUPUESTARIA = '" + guardiaItem.getPartidaPresupuestaria() + "'");

		// FILTRO POR TIPOTURNO
		if (guardiaItem.getTipoTurno() != null && guardiaItem.getTipoTurno() != "")
			sql.WHERE("SCS_TURNO.IDTIPOTURNO = '" + guardiaItem.getTipoTurno() + "'");

		// FILTRO POR TIPOGUARDIA
		if (guardiaItem.getTipoGuardia() != null && guardiaItem.getTipoGuardia() != "")
			sql.WHERE("SCS_GUARDIASTURNO.IDTIPOGUARDIA = " + guardiaItem.getTipoGuardia());
		
		sql.ORDER_BY("SCS_TURNO.NOMBRE, SCS_GUARDIASTURNO.NOMBRE");

		return sql.toString();
	}
	
	public String comboGuardias(String idTurno, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("NOMBRE");
		sql.SELECT("IDGUARDIA");
		
		sql.FROM("SCS_GUARDIASTURNO");
		
		sql.WHERE("IDTURNO = '"+idTurno+"'");
		sql.WHERE("IDINSTITUCION = '"+idInstitucion+"'");
		sql.ORDER_BY("nombre");
		
		return sql.toString();
	}
	

}
