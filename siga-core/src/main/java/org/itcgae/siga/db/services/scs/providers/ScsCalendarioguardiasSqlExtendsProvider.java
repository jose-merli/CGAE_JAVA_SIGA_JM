package org.itcgae.siga.db.services.scs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.db.mappers.ScsCalendarioguardiasSqlProvider;

public class ScsCalendarioguardiasSqlExtendsProvider extends ScsCalendarioguardiasSqlProvider{

	private Logger LOGGER = Logger.getLogger(this.getClass());
	   public String setLogName(String idInstitucion, String idCG, String observaciones, String fechaIni, String fechaFin, String logName, String idTurno, String idGuardia) {

		SQL sql = new SQL();
		 
		sql.UPDATE("SCS_CALENDARIOGUARDIAS PC");
			if (logName != null && !logName.isEmpty()) {
				sql.SET("LOG_GENERACION_NAME = '" + logName + "'");
			}
			if (idCG != null && !idCG.isEmpty()) {
				sql.WHERE("PC.IDCALENDARIOGUARDIAS = " + idCG);
			}
			if (idInstitucion != null && !idInstitucion.isEmpty()) {
				sql.WHERE("PC.IDINSTITUCION = " + idInstitucion);
			}
			if (idTurno != null && !idTurno.isEmpty()) {
				sql.WHERE("PC.IDTURNO = " + idTurno);
			}
			if (idGuardia != null && !idGuardia.isEmpty()) {
				sql.WHERE("PC.IDGUARDIA = " + idGuardia);
			}

		return sql.toString();
	}

//	   public String addLogName(String idInstitucion, String idCG, String observaciones, String fechaIni, String fechaFin, String logName, String idTurno, String idGuardia) {
//
//		SQL sql = new SQL();
//		 
//		sql.INSERT_INTO("SCS_CALENDARIOGUARDIAS");
//			if (logName != null && !logName.isEmpty()) {
//			sql.VALUES("LOG_GENERACION_NAME", logName);
//			}
//			if (fechaIni != null && !fechaIni.isEmpty()) {
//			sql.WHERE("FECHACALINICIO", "TO_DATE('" + fechaIni+ "', 'dd/MM/yyyy')");
//			}
//			if (fechaFin != null && !fechaFin.isEmpty()) {
//			sql.WHERE("FECHACALFIN", "TO_DATE('" + fechaFin + "', 'dd/MM/yyyy')");
//			}
//			if (idCG != null && !idCG.isEmpty()) {
//			sql.VALUES("IDCALENDARIOGUARDIAS" , idCG);
//			}
//			if (idInstitucion != null && !idInstitucion.isEmpty()) {
//			sql.VALUES("IDINSTITUCION" , idInstitucion);
//			}
//			if (idTurno != null && !idTurno.isEmpty()) {
//			sql.VALUES("IDTURNO" , idTurno);
//			}
//			if (idGuardia != null && !idGuardia.isEmpty()) {
//			sql.VALUES("IDGUARDIA" , idGuardia);
//			}
//
//		return sql.toString();
//	}  
	   
	   public String getLogName(String idInstitucion, String idTurno, String idGuardia, String idCalendarioGuardias) {

		SQL sql = new SQL();
			sql.SELECT("LOG_GENERACION_NAME");
			sql.FROM("SCS_CALENDARIOGUARDIAS");
//			if (idCG != null && !idCG.isEmpty()) {
//				sql.WHERE("IDCALENDARIOGUARDIAS = " + idCG);
//			}
			if (idInstitucion != null && !idInstitucion.isEmpty()) {
				sql.WHERE("IDINSTITUCION = " + idInstitucion);
			}
			if (idTurno != null && !idTurno.isEmpty()) {
				sql.WHERE("IDTURNO = " + idTurno);
			}
			if (idGuardia != null && !idGuardia.isEmpty()) {
				sql.WHERE("IDGUARDIA = " + idGuardia);
			}
			if (idCalendarioGuardias != null && !idCalendarioGuardias.isEmpty()) {
				sql.WHERE("idCalendarioGuardias = " + idCalendarioGuardias);
			}
		return sql.toString();
	}
	   
	   public String getGenerado(String idProgCal, Short idInstitucion) {
		   SQL sql = new SQL();
			sql.SELECT("DECODE(PC.ESTADO, 2, 'Si', 3, 'Si', 'No') AS GENERADO");
			sql.FROM("SCS_PROG_CALENDARIOS PC");
			sql.WHERE("IDPROGCALENDARIO = " + idProgCal);
			sql.WHERE("IDINSTITUCION = " + idInstitucion);
			return sql.toString();
	   }
	   
	   public String getGeneracionEnProceso() {
		   SQL sql = new SQL();
		   
			sql.SELECT("IDPROGCALENDARIO");
			sql.FROM("SCS_PROG_CALENDARIOS PC");
			sql.WHERE("PROCESANDOGENERACION = " + 1);
			
			return sql.toString();
	   }
	   
	   public String setGeneracionEnProceso(String idProgCal, String procesando) {
		   SQL sql = new SQL();
		   	sql.UPDATE("SCS_PROG_CALENDARIOS PC");
			sql.SET("PROCESANDOGENERACION = " +  procesando);
			sql.WHERE("IDPROGCALENDARIO = " + idProgCal);
			return sql.toString();
	   }
	   
	   public String getTotalGuardiasColegiadoInsertados(String idInstitucion, String idTurno, String idGuardia, String fechaDesde, String FechaHasta) {
		   SQL sql = new SQL();
		   	sql.SELECT("COUNT(*) AS TOTAL");
			sql.FROM("SCS_GUARDIASCOLEGIADO");
			sql.WHERE("IDINSTITUCION = " + idInstitucion);
			sql.WHERE("IDTURNO = " + idTurno);
			sql.WHERE("IDGUARDIA = " + idGuardia);
			sql.WHERE("FECHAINICIO >= TO_DATE('" + fechaDesde+ "', 'dd-MM-yyyy')");
			sql.WHERE("FECHAFIN <= TO_DATE('" + FechaHasta + "', 'dd-MM-yyyy')");
			LOGGER.info(sql.toString());
			return sql.toString();
	   }
	   
}

