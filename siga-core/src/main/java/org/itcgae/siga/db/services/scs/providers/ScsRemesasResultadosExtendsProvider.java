package org.itcgae.siga.db.services.scs.providers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.RemesasResultadoItem;

public class ScsRemesasResultadosExtendsProvider {
	
	private Logger LOGGER = Logger.getLogger(ScsRemesasResultadosExtendsProvider.class);
	
	public String  buscarRemesasResultado(RemesasResultadoItem remesasResultadoItem, Short idInstitucion) {
		SQL sql = new SQL();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		sql.SELECT("REMR.IDREMESARESOLUCION");
		sql.SELECT("REMR.PREFIJO AS NUMREMESAPREFIJO");
		sql.SELECT("REMR.SUFIJO AS NUMREMESASUFIJO");
		sql.SELECT("REMR.NUMERO AS NUMREMESANUMERO");
		sql.SELECT("REMR.NOMBREFICHERO");
		sql.SELECT("REMR.OBSERVACIONES AS OBSERVACIONESREMESARESULTADO");
		sql.SELECT("REMR.FECHACARGA AS FECHACARGAREMESARESULTADO");
		sql.SELECT("REMR.FECHARESOLUCION AS FECHARESOLUCIONREMESARESULTADO");
		sql.SELECT("REMR.IDREMESA");
		sql.SELECT("REM.PREFIJO AS PREFIJOREMESA");
		sql.SELECT("REM.SUFIJO AS SUFIJOREMESA");
		sql.SELECT("REM.NUMERO AS NUMEROREMESA");
		sql.SELECT("REM.DESCRIPCION AS DESCRIPCIONREMESA");
		
		sql.FROM("CAJG_REMESARESOLUCION REMR");
		sql.LEFT_OUTER_JOIN("CAJG_REMESA REM ON REM.IDINSTITUCION = REMR.IDINSTITUCION");
		
		if(remesasResultadoItem.getNumRemesaPrefijo() != null) {
			sql.WHERE("REM.PREFIJO = '" + remesasResultadoItem.getNumRemesaPrefijo() + "'");
		}
		
		if(remesasResultadoItem.getNumRemesaNumero() != null) {
			sql.WHERE("REM.NUMERO = '" + remesasResultadoItem.getNumRemesaNumero() + "'");
		}
		
		if(remesasResultadoItem.getNumRemesaSufijo() != null) {
			sql.WHERE("REM.SUFIJO = '" + remesasResultadoItem.getNumRemesaSufijo() + "'");
		}
		
		if(remesasResultadoItem.getNumRegistroPrefijo() != null) {
			sql.WHERE("REMR.PREFIJO = '" + remesasResultadoItem.getNumRegistroPrefijo() + "'");
		}
		
		if(remesasResultadoItem.getNumRegistroNumero() != null) {
			sql.WHERE("REMR.NUMERO = '" + remesasResultadoItem.getNumRegistroNumero() + "'");
		}
		
		if(remesasResultadoItem.getNumRegistroSufijo() != null) {
			sql.WHERE("REMR.SUFIJO = '" + remesasResultadoItem.getNumRegistroSufijo() + "'");
		}
		
		if(remesasResultadoItem.getNombreFichero() != null) {
			sql.WHERE("REMR.NOMBREFICHERO = '" + remesasResultadoItem.getNombreFichero() + "'");
		}
		
		if(remesasResultadoItem.getFechaRemesaDesde() != null) {
			String fechaRemesaDesde = "";
			fechaRemesaDesde = dateFormat.format(remesasResultadoItem.getFechaRemesaDesde());
			sql.WHERE("TRUNC(REMR.FECHARESOLUCION) >= TO_DATE('" + fechaRemesaDesde + "', 'DD/MM/RRRR')");
		}
		
		if(remesasResultadoItem.getFechaRemesaHasta() != null) {
			String fechaRemesaHasta = "";
			fechaRemesaHasta = dateFormat.format(remesasResultadoItem.getFechaRemesaHasta());
			sql.WHERE("TRUNC(REMR.FECHARESOLUCION) <= TO_DATE('" + fechaRemesaHasta + "', 'DD/MM/RRRR')");
		}
		
		if(remesasResultadoItem.getFechaCargaDesde() != null) {
			String fechaCargaDesde = "";
			fechaCargaDesde = dateFormat.format(remesasResultadoItem.getFechaCargaDesde());
			sql.WHERE("TRUNC(REMR.FECHACARGA) >= TO_DATE('" + fechaCargaDesde + "', 'DD/MM/RRRR')");
		}
		
		if(remesasResultadoItem.getFechaCargaHasta() != null) {
			String fechaCargaHasta = "";
			fechaCargaHasta = dateFormat.format(remesasResultadoItem.getFechaCargaHasta());
			sql.WHERE("TRUNC(REMR.FECHACARGA) <= TO_DATE('" + fechaCargaHasta + "', 'DD/MM/RRRR')");
		}
		
		sql.WHERE("REMR.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("REMR.IDTIPOREMESA = 3");
		sql.WHERE("REM.IDINSTITUCION = REMR.IDINSTITUCION");
		sql.WHERE("REM.IDREMESA = REMR.IDREMESA");
		sql.WHERE("ROWNUM <= 200");
		
		sql.ORDER_BY("REMR.IDREMESARESOLUCION DESC");
		
		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
}
