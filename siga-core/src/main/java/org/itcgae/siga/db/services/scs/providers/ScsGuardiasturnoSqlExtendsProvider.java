
package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTO.scs.GuardiasItem;
import org.itcgae.siga.DTOs.scs.TurnosItem;
import org.itcgae.siga.db.mappers.ScsGuardiasturnoSqlProvider;

public class ScsGuardiasturnoSqlExtendsProvider extends ScsGuardiasturnoSqlProvider {

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
		sql.WHERE("SCS_TURNO.IDTURNO= '"+turnosItem.getIdturno()+"'");
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
		sql.WHERE("IDINSTITUCION = '"+idInstitucion+"'");
		sql.ORDER_BY("nombre");
		
		return sql.toString();
	}
	
	public String comboGuardiasUpdate(String idTurno, String idInstitucion) {
		SQL sql = new SQL();
		
		sql.SELECT("NOMBRE");
		sql.SELECT("IDGUARDIA");
		
		sql.FROM("SCS_GUARDIASTURNO");
		
		sql.WHERE("IDTURNO = '"+idTurno+"'");
		sql.WHERE("IDINSTITUCION = '"+idInstitucion+"'");
		sql.WHERE("fechabaja is null");
		sql.ORDER_BY("nombre");
		
		return sql.toString();
	}
	
	public String getIdGuardia() {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDGUARDIA) AS IDGUARDIA");
		sql.FROM("SCS_GUARDIASTURNO");

		return sql.toString();
	}

	

}
