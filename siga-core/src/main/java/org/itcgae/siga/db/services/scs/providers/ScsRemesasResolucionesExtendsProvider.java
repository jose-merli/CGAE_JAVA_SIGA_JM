package org.itcgae.siga.db.services.scs.providers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.RemesasResolucionItem;

public class ScsRemesasResolucionesExtendsProvider {

	private Logger LOGGER = Logger.getLogger(this.getClass());
	
	public String buscarRemesasResoluciones(RemesasResolucionItem remesasResolucionItem, int idInstitucion, int tamMaximo) {
		SQL sql = new SQL();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		sql.SELECT("RES.IDREMESARESOLUCION");
		sql.SELECT("RES.PREFIJO");
		sql.SELECT("RES.NUMERO");
		sql.SELECT("RES.SUFIJO");
		sql.SELECT("RES.NOMBREFICHERO");
		sql.SELECT("RES.OBSERVACIONES");
		sql.SELECT("RES.FECHACARGA");
		sql.SELECT("RES.FECHARESOLUCION");
		sql.SELECT("RES.IDREMESA");
		sql.SELECT("RES.LOGGENERADO AS LOGGENERADO");
		
		sql.FROM("CAJG_REMESARESOLUCION RES");
		
		if(remesasResolucionItem.getNumRemesaPrefijo() != null && !remesasResolucionItem.getNumRemesaPrefijo().isEmpty()) {
			sql.WHERE("RES.PREFIJO LIKE '%" + remesasResolucionItem.getNumRemesaPrefijo() + "%'");
		}
		
		if(remesasResolucionItem.getNumRemesaNumero() != null && !remesasResolucionItem.getNumRemesaNumero().isEmpty()) {
			sql.WHERE("RES.NUMERO LIKE '%" + remesasResolucionItem.getNumRemesaNumero() + "%'");
		}

		if(remesasResolucionItem.getNumRemesaSufijo() != null && !remesasResolucionItem.getNumRemesaSufijo().isEmpty()) {
			sql.WHERE("RES.SUFIJO LIKE '%" + remesasResolucionItem.getNumRemesaSufijo() + "%'");
		}
		
		if(remesasResolucionItem.getNombreFichero() != null && !remesasResolucionItem.getNombreFichero().isEmpty()) {
			sql.WHERE("RES.NOMBREFICHERO LIKE '%" + remesasResolucionItem.getNombreFichero() + "%'");
		}
		
		if(remesasResolucionItem.getFechaResolucionDesde() != null) {
			String fechaResolucionDesde = "";
			fechaResolucionDesde = dateFormat.format(remesasResolucionItem.getFechaResolucionDesde());
			sql.WHERE("TRUNC(RES.FECHARESOLUCION) >= TO_DATE('" + fechaResolucionDesde + "', 'DD/MM/RRRR')");
		}
				
		if(remesasResolucionItem.getFechaResolucionHasta() != null) {
			String fechaResolucionHasta = "";
			fechaResolucionHasta = dateFormat.format(remesasResolucionItem.getFechaResolucionHasta());
			sql.WHERE("TRUNC(RES.FECHARESOLUCION) <= TO_DATE('" + fechaResolucionHasta + "', 'DD/MM/RRRR')");
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
		sql.WHERE("ROWNUM <= " + tamMaximo);	
		
		sql.ORDER_BY("RES.FECHARESOLUCION DESC, RES.IDREMESARESOLUCION DESC");
		
		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String getMaxIdRemesaResolucion(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(idremesaresolucion)+1 as idremesaresolucion");
		sql.FROM("cajg_remesaresolucion");
		sql.WHERE("idinstitucion = " + idInstitucion.toString());

		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String getMaxIdRemesaResolucionFichero(Short idInstitucion) {
		SQL sql = new SQL();

		sql.SELECT("MAX(IDREMESARESOLUCIONFICHERO)+1 as IDREMESARESOLUCIONFICHERO");
		sql.FROM("cajg_remesaresolucionfichero");

		LOGGER.info(sql.toString());
		return sql.toString();
	}

	
	public String ecomOperacionTipoAccion(String tipoCAJG) {
		SQL sql = new SQL();

		sql.SELECT("idoperacion");
		sql.FROM("ecom_operacion_tipoaccion");
		sql.WHERE("tipo_pcajg = " + tipoCAJG);
		sql.WHERE("idtipoaccionremesa = 3");

		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String logRemesaResoluciones(String idInstitucion, String idRemesaResolucion) {
		SQL sql = new SQL();
		sql.SELECT("E.CODIGO AS codigo");
		sql.SELECT("E.DESCRIPCION AS descripcion");
		sql.SELECT("R.PARAMETROSERROR AS prametrosError");
		sql.SELECT("R.NUMEROLINEA AS numeroLinea");
		sql.FROM("CAJG_REMESARESOLUCIONFICHERO R");
		sql.FROM("CAJG_ERRORESREMESARESOL E");
		sql.WHERE("R.IDERRORESREMESARESOL = E.IDERRORESREMESARESOL");
		sql.WHERE("R.IDINSTITUCION = E.IDINSTITUCION");
		sql.WHERE("R.IDINSTITUCION = " + idInstitucion);
		sql.WHERE("R.IDREMESARESOLUCION = " + idRemesaResolucion);
		sql.ORDER_BY("R.NUMEROLINEA");
		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String nombreContador(String idInstitucion) {
		SQL sql = new SQL();
		sql.SELECT("IDCONTADOR");
		sql.FROM("CAJG_TIPOREMESA");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDTIPOREMESA = 1");
		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String contador(String idInstitucion, String idTipoRemesa) {
		SQL sql = new SQL();
		sql.SELECT("IDCONTADOR");
		sql.FROM("CAJG_TIPOREMESA");
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		sql.WHERE("IDTIPOREMESA = " + idTipoRemesa);
		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
}
