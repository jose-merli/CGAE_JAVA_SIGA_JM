package org.itcgae.siga.db.services.scs.mappers;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.itcgae.siga.db.mappers.ScsCalendarioguardiasMapper;
import org.itcgae.siga.db.services.scs.providers.ScsCalendarioguardiasSqlExtendsProvider;
import org.itcgae.siga.db.services.scs.providers.ScsGuardiasturnoSqlExtendsProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public interface ScsCalendarioguardiasExtendsMapper extends ScsCalendarioguardiasMapper {

	

	
	@SelectProvider(type = ScsCalendarioguardiasSqlExtendsProvider.class, method = "setLogName")
	@Results(
			)
	String setLogName(String idInstitucion, String idCG, String observaciones, String fechaIni, String fechaFin, String logName, String idTurno, String idGuardia);
	
	
//	@SelectProvider(type = ScsCalendarioguardiasSqlExtendsProvider.class, method = "addLogName")
//	@Results(
//			)
//	String addLogName(String idInstitucion, String idCG, String observaciones, String fechaIni, String fechaFin, String logName, String idTurno, String idGuardia);
//	
	
	@SelectProvider(type = ScsCalendarioguardiasSqlExtendsProvider.class, method = "getLogName")
	@Results(
			)
	String getLogName(String idInstitucion, String idTurno, String idGuardia, String idCalendarioGuardias);
	
	@SelectProvider(type = ScsCalendarioguardiasSqlExtendsProvider.class, method = "getGeneracionEnProceso")
	@Results(
			)
	List<String> getGeneracionEnProceso();
	
	@SelectProvider(type = ScsCalendarioguardiasSqlExtendsProvider.class, method = "getGenerado")
	@Results(
			)
	String getGenerado(String idProgCal, Short idInstitucion);
	
	@InsertProvider(type = ScsCalendarioguardiasSqlExtendsProvider.class, method = "setGeneracionEnProceso")
	@Results()
	int setGeneracionEnProceso(String idProgCal, String procesando);
	
	@SelectProvider(type=ScsCalendarioguardiasSqlExtendsProvider.class, method="getTotalGuardiasColegiadoInsertados")
	@Results({@Result(column = "TOTAL", property = "TOTAL", jdbcType = JdbcType.DECIMAL)})
	int getTotalGuardiasColegiadoInsertados(String idInstitucion, String idTurno, String idGuardia, String fechaDesde, String FechaHasta);

	@SelectProvider(type = ScsCalendarioguardiasSqlExtendsProvider.class, method = "getNextIdCalendarioGuardias")
	@Results()
	String getNextIdCalendarioGuardias();
	
}
