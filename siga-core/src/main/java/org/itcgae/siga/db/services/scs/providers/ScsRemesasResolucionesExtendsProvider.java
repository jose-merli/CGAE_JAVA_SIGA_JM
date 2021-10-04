package org.itcgae.siga.db.services.scs.providers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;

public class ScsRemesasResolucionesExtendsProvider {

	private Logger LOGGER = Logger.getLogger(this.getClass());
	
	public String buscarRemesasResoluciones(RemesasResolucionItem remesasResolucionItem, int idInstitucion) {
		SQL sql = new SQL();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		sql.SELECT("RES.IDREMESARESOLUCION");
		sql.SELECT("RES.PREFIJO AS PREFIJORESULTADO");
		sql.SELECT("RES.NUMERO AS NUMERORESULTADO");
		sql.SELECT("RES.SUFIJO AS SUFIJORESULTADO");
		sql.SELECT("RES.NOMBREFICHERO");
		sql.SELECT("RES.OBSERVACIONES");
		sql.SELECT("RES.FECHACARGA");
		sql.SELECT("RES.FECHARESOLUCION");
		sql.SELECT("RES.IDREMESA");
		
		sql.FROM("CAJG_REMESARESOLUCION RES");
		
		if(remesasResolucionItem.getNumRemesaPrefijo() != null && !remesasResolucionItem.getNumRemesaPrefijo().isEmpty()) {
			sql.WHERE("RES.PREFIJO = '" + remesasResolucionItem.getNumRemesaPrefijo() + "'");
		}

		if(remesasResolucionItem.getNombreFichero() != null && !remesasResolucionItem.getNombreFichero().isEmpty()) {
			sql.WHERE("RES.NOMBREFICHERO = '" + remesasResolucionItem.getNombreFichero() + "'");
		}
		
		if(remesasResolucionItem.getFechaResolucionDesde() != null) {
			String fechaResolucionDesde = "";
			fechaResolucionDesde = dateFormat.format(remesasResolucionItem.getFechaResolucionDesde());
			sql.WHERE("TRUNC(RES.FECHARESOLUCION) >= TO_DATE('" + fechaResolucionDesde + "', 'DD/MM/RRRR')");
		}
				
		if(remesasResolucionItem.getFechaResolucionHasta() != null) {
			String fechaResolucionHasta = "";
			fechaResolucionHasta = dateFormat.format(remesasResolucionItem.getFechaResolucionHasta());
			sql.WHERE("TRUNC(RES.FECHARESOLUCION) >= TO_DATE('" + fechaResolucionHasta + "', 'DD/MM/RRRR')");
		}
				
		if(remesasResolucionItem.getFechaCargaDesde() != null) {
			String fechaCargaDesde = "";
			fechaCargaDesde = dateFormat.format(remesasResolucionItem.getFechaCargaDesde());
			sql.WHERE("TRUNC(RES.FECHACARGA) >= TO_DATE('" + fechaCargaDesde + "', 'DD/MM/RRRR')");
		}
				
		if(remesasResolucionItem.getFechaCargaHasta() != null) {
			String fechaCargaHasta = "";
			fechaCargaHasta = dateFormat.format(remesasResolucionItem.getFechaCargaHasta());
			sql.WHERE("TRUNC(RES.FECHACARGA) <= TO_DATE('" + fechaCargaHasta + "', 'DD/MM/RRRR')");
		}
		
		sql.WHERE("RES.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("RES.IDTIPOREMESA = 1");
		sql.WHERE("ROWNUM <= 200");
		
		sql.ORDER_BY("RES.IDREMESARESOLUCION DESC");
		
		LOGGER.info(sql.toString());
		return sql.toString();
	}
}
