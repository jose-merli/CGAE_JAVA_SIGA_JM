package org.itcgae.siga.db.services.scs.providers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.CargaMasivaProcuradorBusquedaItem;

public class ScsIntercambiosExtendsProvider {
	
	private Logger LOGGER = Logger.getLogger(ScsRemesasExtendsProvider.class);
	
	public String listadoCargaMasivaProcuradores(CargaMasivaProcuradorBusquedaItem cargaMasivaItem, Short idInstitucion, Integer tamMaximo) {
		SQL sql = new SQL();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		sql.SELECT("cm.IDCARGAMASIVA");
		sql.SELECT("cm.TIPOCARGA");
		sql.SELECT("cm.IDINSTITUCION");
		sql.SELECT("cm.FECHACARGA");
		sql.SELECT("cm.IDFICHERO");
		sql.SELECT("cm.IDFICHEROLOG");
		sql.SELECT("cm.FECHAMODIFICACION");
		sql.SELECT("cm.NUMREGISTROS");
		sql.SELECT("cm.NOMBREFICHERO");
		sql.SELECT("cm.NUMREGISTROSERRONEOS");
		sql.SELECT("usu.DESCRIPCION");
		sql.FROM("cen_cargamasiva cm");
		sql.FROM("adm_usuarios usu");
		sql.WHERE("cm.USUMODIFICACION = usu.idusuario");
		sql.WHERE("cm.idinstitucion = usu.idinstitucion");
		sql.WHERE("cm.tipocarga = 'PD'");
		sql.WHERE("cm.idinstitucion = " + idInstitucion.toString());
		sql.ORDER_BY("cm.fechacarga desc");
		
		if(cargaMasivaItem.getFechaCarga() != null) {
			String fechaCarga = "";
			fechaCarga = dateFormat.format(cargaMasivaItem.getFechaCarga());
			sql.WHERE("TRUNC(fechacarga) = TO_DATE('" + fechaCarga + "', 'DD/MM/RRRR')");
		}
		
		if(tamMaximo != null)
			sql.WHERE("ROWNUM <= " + tamMaximo);
		
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
