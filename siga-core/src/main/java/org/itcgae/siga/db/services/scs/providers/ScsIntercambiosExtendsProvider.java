package org.itcgae.siga.db.services.scs.providers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.CargaMasivaProcuradorBusquedaItem;

public class ScsIntercambiosExtendsProvider {
	
	private Logger LOGGER = Logger.getLogger(ScsRemesasExtendsProvider.class);
	
	public String listadoCargaMasivaProcuradores(CargaMasivaProcuradorBusquedaItem cargaMasivaItem, Short idInstitucion) {
		SQL sql = new SQL();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		sql.SELECT("IDCARGAMASIVA");
		sql.SELECT("TIPOCARGA");
		sql.SELECT("IDINSTITUCION");
		sql.SELECT("FECHACARGA");
		sql.SELECT("IDFICHERO");
		sql.SELECT("IDFICHEROLOG");
		sql.SELECT("FECHAMODIFICACION");
		sql.SELECT("NUMREGISTROS");
		sql.SELECT("NOMBREFICHERO");
		sql.SELECT("NUMREGISTROSERRONEOS");
		sql.FROM("cen_cargamasiva");
		sql.WHERE("tipocarga = 'PD'");
		sql.WHERE("idinstitucion = " + idInstitucion.toString());
		
		if(cargaMasivaItem.getFechaCarga() != null) {
			String fechaCarga = "";
			fechaCarga = dateFormat.format(cargaMasivaItem.getFechaCarga());
			sql.WHERE("TRUNC(fechacarga) <= TO_DATE('" + fechaCarga + "', 'DD/MM/RRRR')");
		}
		
		LOGGER.debug(sql.toString());

		return sql.toString();
	}
	
	public String getMaxIdEstadoPorEJG(String anio, Short idInstitucion, String numero, String idTipoEJG) {
		SQL sql = new SQL();

		sql.SELECT("max(idestadoporejg) as IDESTADOPOREJG");
		sql.FROM("scs_estadoejg");
		sql.WHERE("idtipoejg = " + idTipoEJG);
		sql.WHERE("idinstitucion = " + idInstitucion.toString());
		sql.WHERE("anio = " + anio);
		sql.WHERE("numero = " + numero);
	
		LOGGER.debug(sql.toString());

		return sql.toString();
	}

}
