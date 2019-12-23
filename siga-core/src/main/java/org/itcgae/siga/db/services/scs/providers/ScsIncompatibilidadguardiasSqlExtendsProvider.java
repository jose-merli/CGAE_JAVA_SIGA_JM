package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.GuardiasItem;
import org.itcgae.siga.db.mappers.ScsIncompatibilidadguardiasSqlProvider;

public class ScsIncompatibilidadguardiasSqlExtendsProvider extends ScsIncompatibilidadguardiasSqlProvider{

	public String tarjetaIncompatibilidades(String idGuardia, String idInstitucion) {
		// SQL auxiliar para el UNION
		
		SQL sqlUnion = new SQL();
		sqlUnion.SELECT("turnos.NOMBRE AS TURNO");
		sqlUnion.SELECT("guardi.NOMBRE AS GUARDIA");
		sqlUnion.SELECT("guardi.SELECCIONLABORABLES");
		sqlUnion.SELECT("guardi.SELECCIONFESTIVOS");
		sqlUnion.SELECT("CASE \r\n" + 
				"			WHEN  'LMXJVSD' LIKE CONCAT('%',CONCAT(SELECCIONLABORABLES,'%')) AND LENGTH(SELECCIONLABORABLES)>1 \r\n" + 
				"				AND SUBSTR(SELECCIONLABORABLES,1,1) = 'L' AND SUBSTR(SELECCIONLABORABLES,LENGTH(SELECCIONLABORABLES),1) IN ('V','S','D') THEN \r\n" + 
				"				(CONCAT(CONCAT(SUBSTR(SELECCIONLABORABLES,1,1), '-'), SUBSTR(SELECCIONLABORABLES,LENGTH(SELECCIONLABORABLES),1)))\r\n" + 
				"			WHEN LENGTH(SELECCIONLABORABLES) = 1 THEN\r\n" + 
				"				SELECCIONLABORABLES\r\n" + 
				"			ELSE SELECCIONLABORABLES\r\n" + 
				"			END AS diaslaborables,\r\n" + 
				"		CASE \r\n" + 
				"			WHEN  'LMXJVSD' LIKE CONCAT('%',CONCAT(SELECCIONFESTIVOS,'%')) AND LENGTH(SELECCIONFESTIVOS)>1 \r\n" + 
				"				AND SUBSTR(SELECCIONFESTIVOS,1,1) = 'L' AND SUBSTR(SELECCIONFESTIVOS,LENGTH(SELECCIONFESTIVOS),1) IN ('V','S','D') THEN \r\n" + 
				"				(CONCAT(CONCAT(SUBSTR(SELECCIONFESTIVOS,1,1), '-'), SUBSTR(SELECCIONFESTIVOS,LENGTH(SELECCIONFESTIVOS),1)))\r\n" + 
				"			WHEN LENGTH(SELECCIONFESTIVOS) = 1 THEN\r\n" + 
				"				SELECCIONFESTIVOS\r\n" + 
				"			ELSE SELECCIONFESTIVOS\r\n" + 
				"			END AS diasfestivos");
		sqlUnion.SELECT("incomp.MOTIVOS");
		sqlUnion.SELECT("incomp.DIASSEPARACIONGUARDIAS");
		
		sqlUnion.FROM("SCS_INCOMPATIBILIDADGUARDIAS incomp");

		sqlUnion.JOIN("SCS_GUARDIASTURNO guardi ON incomp.idguardia = guardi.idguardia AND INCOMP.idturno = guardi.idturno AND INCOMP.idinstitucion = guardi.idinstitucion");
		sqlUnion.JOIN("SCS_TURNO turnos ON turnos.idturno = guardi.idturno AND turnos.idinstitucion = guardi.idinstitucion");
		
		sqlUnion.WHERE("(incomp.IDGUARDIA = "+idGuardia+" AND incomp.IDINSTITUCION = "+idInstitucion);
		
		
		
		SQL sql = new SQL();

		sql.SELECT("turnos.NOMBRE AS TURNO");
		sql.SELECT("guardi.NOMBRE AS GUARDIA");
		sql.SELECT("guardi.SELECCIONLABORABLES");
		sql.SELECT("guardi.SELECCIONFESTIVOS");
		sql.SELECT("CASE \r\n" + 
				"			WHEN  'LMXJVSD' LIKE CONCAT('%',CONCAT(SELECCIONLABORABLES,'%')) AND LENGTH(SELECCIONLABORABLES)>1 \r\n" + 
				"				AND SUBSTR(SELECCIONLABORABLES,1,1) = 'L' AND SUBSTR(SELECCIONLABORABLES,LENGTH(SELECCIONLABORABLES),1) IN ('V','S','D') THEN \r\n" + 
				"				(CONCAT(CONCAT(SUBSTR(SELECCIONLABORABLES,1,1), '-'), SUBSTR(SELECCIONLABORABLES,LENGTH(SELECCIONLABORABLES),1)))\r\n" + 
				"			WHEN LENGTH(SELECCIONLABORABLES) = 1 THEN\r\n" + 
				"				SELECCIONLABORABLES\r\n" + 
				"			ELSE SELECCIONLABORABLES\r\n" + 
				"			END AS diaslaborables,\r\n" + 
				"		CASE \r\n" + 
				"			WHEN  'LMXJVSD' LIKE CONCAT('%',CONCAT(SELECCIONFESTIVOS,'%')) AND LENGTH(SELECCIONFESTIVOS)>1 \r\n" + 
				"				AND SUBSTR(SELECCIONFESTIVOS,1,1) = 'L' AND SUBSTR(SELECCIONFESTIVOS,LENGTH(SELECCIONFESTIVOS),1) IN ('V','S','D') THEN \r\n" + 
				"				(CONCAT(CONCAT(SUBSTR(SELECCIONFESTIVOS,1,1), '-'), SUBSTR(SELECCIONFESTIVOS,LENGTH(SELECCIONFESTIVOS),1)))\r\n" + 
				"			WHEN LENGTH(SELECCIONFESTIVOS) = 1 THEN\r\n" + 
				"				SELECCIONFESTIVOS\r\n" + 
				"			ELSE SELECCIONFESTIVOS\r\n" + 
				"			END AS diasfestivos");
		sql.SELECT("incomp.MOTIVOS");
		sql.SELECT("incomp.DIASSEPARACIONGUARDIAS");
		
		sql.FROM("SCS_INCOMPATIBILIDADGUARDIAS incomp");

		sql.JOIN("SCS_GUARDIASTURNO guardi ON incomp.idguardia = guardi.idguardia AND INCOMP.idturno = guardi.idturno AND INCOMP.idinstitucion = guardi.idinstitucion");
		sql.JOIN("SCS_TURNO turnos ON turnos.idturno = guardi.idturno AND turnos.idinstitucion = guardi.idinstitucion");
		
		sql.WHERE("incomp.IDGUARDIA_INCOMPATIBLE = "+idGuardia+" AND incomp.IDINSTITUCION = "+idInstitucion + ") UNION "+sqlUnion.toString());
		return sql.toString();
	}
	
	public String resumenIncompatibilidades(GuardiasItem guardia, String idInstitucion)  {
		SQL sql = new SQL();
		
		sql.SELECT("count (*) AS TOTAL_INCOMPATIBILIDADES");
		
		sql.FROM("(SELECT\r\n" + 
				"	turnos.NOMBRE AS TURNO,\r\n" + 
				"	guardi.NOMBRE AS GUARDIA,\r\n" + 
				"	guardi.SELECCIONLABORABLES,\r\n" + 
				"	guardi.SELECCIONFESTIVOS,\r\n" + 
				"	incomp.MOTIVOS,\r\n" + 
				"	incomp.DIASSEPARACIONGUARDIAS,\r\n" + 
				"	incomp.IDINSTITUCION\r\n" + 
				"FROM\r\n" + 
				"	SCS_INCOMPATIBILIDADGUARDIAS incomp\r\n" + 
				"	JOIN SCS_GUARDIASTURNO guardi ON incomp.idguardia = guardi.idguardia AND INCOMP.idturno = guardi.idturno AND INCOMP.idinstitucion = guardi.idinstitucion\r\n" + 
				"	JOIN SCS_TURNO turnos ON turnos.idturno = guardi.idturno AND turnos.idinstitucion = guardi.idinstitucion\r\n" + 
				"WHERE\r\n" + 
				"	incomp.IDINSTITUCION = "+idInstitucion+"\r\n" + 
				"	AND incomp.IDTURNO = "+guardia.getIdTurno()+"\r\n" + 
				"	AND incomp.IDGUARDIA = "+guardia.getIdGuardia()+"\r\n" + 
				"UNION\r\n" + 
				"SELECT\r\n" + 
				"	turnos.NOMBRE AS TURNO,\r\n" + 
				"	guardi.NOMBRE AS GUARDIA,\r\n" + 
				"	guardi.SELECCIONLABORABLES,\r\n" + 
				"	guardi.SELECCIONFESTIVOS,\r\n" + 
				"	incomp.MOTIVOS,\r\n" + 
				"	incomp.DIASSEPARACIONGUARDIAS,\r\n" + 
				"	incomp.IDINSTITUCION\r\n" + 
				"FROM\r\n" + 
				"	SCS_INCOMPATIBILIDADGUARDIAS incomp\r\n" + 
				"	JOIN SCS_GUARDIASTURNO guardi ON incomp.idguardia = guardi.idguardia AND INCOMP.idturno = guardi.idturno AND INCOMP.idinstitucion = guardi.idinstitucion\r\n" + 
				"	JOIN SCS_TURNO turnos ON turnos.idturno = guardi.idturno AND turnos.idinstitucion = guardi.idinstitucion\r\n" + 
				"WHERE\r\n" + 
				"	incomp.IDINSTITUCION = "+idInstitucion+"\r\n" + 
				"	AND incomp.IDTURNO_INCOMPATIBLE = "+guardia.getIdTurno()+"\r\n" + 
				"	AND incomp.IDGUARDIA_INCOMPATIBLE = "+guardia.getIdGuardia()+")");
		
		
		return sql.toString();
	}
}
