package org.itcgae.siga.db.services.scs.providers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.RemesasBusquedaItem;

public class ScsRemesasExtendsProvider {
	
	private Logger LOGGER = Logger.getLogger(ScsRemesasExtendsProvider.class);

	public String comboEstado(String idLenguaje) {
		SQL sql = new SQL();

		sql.SELECT("IDESTADO as ID");
		sql.SELECT("F_SIGA_GETRECURSO(DESCRIPCION, " + idLenguaje + ") as DESCRIPCION");
		sql.FROM("CAJG_TIPOESTADOREMESA");
		sql.ORDER_BY("IDESTADO");

		return sql.toString();
	}

	public String buscarRemesas(RemesasBusquedaItem remesasBusquedaItem, Short idInstitucion, String idLenguaje) {
		SQL sql = new SQL();
		SQL subquery = new SQL();
		SQL subquery1 = new SQL();
		SQL subquery2 = new SQL();
		SQL subquery3 = new SQL();
		SQL subquery4 = new SQL();
		SQL subquery5 = new SQL();
		SQL subquery6 = new SQL();
		SQL subquery7 = new SQL();
		SQL subquery8 = new SQL();
		SQL fechaGeneracion = new SQL();
		SQL fechaEnvio = new SQL();
		SQL fechaRecepcion = new SQL();
		SQL fechamodificacion = new SQL();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		subquery.SELECT("fecharemesa");
		subquery.FROM("cajg_remesaestados est");
		subquery.WHERE("est.idinstitucion = rem.idinstitucion");
		subquery.WHERE("est.idremesa = rem.idremesa");
		subquery.WHERE("est.idestado = 1");
		
		subquery1.SELECT("fecharemesa");
		subquery1.FROM("cajg_remesaestados est");
		subquery1.WHERE("est.idinstitucion = rem.idinstitucion");
		subquery1.WHERE("est.idremesa = rem.idremesa");
		subquery1.WHERE("est.idestado = 2");
		
		subquery2.SELECT("fecharemesa");
		subquery2.FROM("cajg_remesaestados est");
		subquery2.WHERE("est.idinstitucion = rem.idinstitucion");
		subquery2.WHERE("est.idremesa = rem.idremesa");
		subquery2.WHERE("est.idestado = 3");
		
		subquery4.SELECT("idestado");
		subquery4.FROM("cajg_remesaestados");
		subquery4.WHERE("idinstitucion = " + idInstitucion.toString());
		subquery4.ORDER_BY("fecharemesa desc");
		subquery4.ORDER_BY("idestado desc");
		
		subquery3.SELECT("descripcion");
		subquery3.FROM("cajg_tipoestadoremesa est");
		subquery3.FROM("(" + subquery4.toString() + ") a");
		subquery3.WHERE("est.idestado = a.idestado");
		subquery3.WHERE("rownum=1");
		
		subquery5.SELECT("count(1)");
		subquery5.FROM("cajg_ejgremesa ejgr");
		subquery5.FROM("cajg_respuesta_ejgremesa resp");
		subquery5.WHERE("ejgr.idinstitucion = rem.idinstitucion");
		subquery5.WHERE("ejgr.idremesa = rem.idremesa");
		subquery5.WHERE("ejgr.idejgremesa = resp.idejgremesa");
		
		subquery6.SELECT("count(1)");
		subquery6.FROM("cajg_ejgremesa ejgr");
		subquery6.WHERE("ejgr.idinstitucion = rem.idinstitucion");
		subquery6.WHERE("ejgr.idremesa = rem.idremesa");
		
		subquery7.SELECT("1");
		subquery7.FROM("cajg_ejgremesa ejgr");
		subquery7.WHERE("ejgr.idremesa= rem.idremesa");
		subquery7.WHERE("ejgr.idinstitucion = rem.idinstitucion");
		subquery7.WHERE("numero like '%" + remesasBusquedaItem.getNumeroEJG() + "%'");
		
		subquery8.SELECT("1");
		subquery8.FROM("cajg_ejgremesa ejgr");
		subquery8.WHERE("ejgr.idremesa= rem.idremesa");
		subquery8.WHERE("ejgr.idinstitucion = rem.idinstitucion");
		subquery8.WHERE("anio = " + remesasBusquedaItem.getAnnioEJG());
		
		fechaGeneracion.SELECT("1");
		fechaGeneracion.FROM("cajg_remesaestados est");
		fechaGeneracion.WHERE("est.idinstitucion = rem.idinstitucion");
		fechaGeneracion.WHERE("est.idremesa = rem.idremesa");
		fechaGeneracion.WHERE("est.idestado = 1");
		if(remesasBusquedaItem.getFechaGeneracionDesde() != null) {
			String fechaGeneracionDesde = "";
			fechaGeneracionDesde = dateFormat.format(remesasBusquedaItem.getFechaGeneracionDesde());
			fechaGeneracion.WHERE("TRUNC(est.fecharemesa) >= TO_DATE('" + fechaGeneracionDesde + "', 'DD/MM/RRRR')");
		}
		
		if(remesasBusquedaItem.getFechaGeneracionHasta() != null) {
			String fechaGeneracionHasta = "";
			fechaGeneracionHasta = dateFormat.format(remesasBusquedaItem.getFechaGeneracionHasta());
			fechaGeneracion.WHERE("TRUNC(est.fecharemesa) <= TO_DATE('" + fechaGeneracionHasta + "', 'DD/MM/RRRR')");
		}
		
		fechaEnvio.SELECT("1");
		fechaEnvio.FROM("cajg_remesaestados est");
		fechaEnvio.WHERE("est.idinstitucion = rem.idinstitucion");
		fechaEnvio.WHERE("est.idremesa = rem.idremesa");
		fechaEnvio.WHERE("est.idestado = 2");
		if(remesasBusquedaItem.getFechaEnvioDesde() != null) {
			String fechaEnvioDesde = "";
			fechaEnvioDesde = dateFormat.format(remesasBusquedaItem.getFechaEnvioDesde());
			fechaEnvio.WHERE("TRUNC(est.fecharemesa) >= TO_DATE('" + fechaEnvioDesde + "', 'DD/MM/RRRR')");
		}
		
		if(remesasBusquedaItem.getFechaEnvioHasta() != null) {
			String fechaEnvioHasta = "";
			fechaEnvioHasta = dateFormat.format(remesasBusquedaItem.getFechaEnvioHasta());
			fechaEnvio.WHERE("TRUNC(est.fecharemesa) <= TO_DATE('" + fechaEnvioHasta + "', 'DD/MM/RRRR')");
		}
		
		fechaRecepcion.SELECT("1");
		fechaRecepcion.FROM("cajg_remesaestados est");
		fechaRecepcion.WHERE("est.idinstitucion = rem.idinstitucion");
		fechaRecepcion.WHERE("est.idremesa = rem.idremesa");
		fechaRecepcion.WHERE("est.idestado = 3");
		if(remesasBusquedaItem.getFechaRecepcionDesde() != null) {
			String fechaRecepcionDesde = "";
			fechaRecepcionDesde = dateFormat.format(remesasBusquedaItem.getFechaRecepcionDesde());
			fechaRecepcion.WHERE("TRUNC(est.fecharemesa) >= TO_DATE('" + fechaRecepcionDesde + "', 'DD/MM/RRRR')");
		}
		
		if(remesasBusquedaItem.getFechaRecepcionHasta() != null) {
			String fechaRecepcionHasta = "";
			fechaRecepcionHasta = dateFormat.format(remesasBusquedaItem.getFechaRecepcionHasta());
			fechaRecepcion.WHERE("TRUNC(est.fecharemesa) <= TO_DATE('" + fechaRecepcionHasta + "', 'DD/MM/RRRR')");
		}
		
		fechamodificacion.SELECT("MAX(fechamodificacion)");
		fechamodificacion.FROM("cajg_remesaestados est2");
		fechamodificacion.WHERE("est2.idinstitucion = rem.idinstitucion");
		fechamodificacion.WHERE("est2.idremesa = rem.idremesa");

		sql.SELECT("REM.IDREMESA IDREMESA");
		sql.SELECT("REM.IDINSTITUCION IDINSTITUCION");
		sql.SELECT("REM.PREFIJO PREFIJO");
		sql.SELECT("rem.numero NUMERO");
		sql.SELECT("REM.SUFIJO SUFIJO");
		sql.SELECT("REM.PREFIJO || rem.numero || REM.SUFIJO as NREGISTRO");
		sql.SELECT("rem.descripcion");
		sql.SELECT("(" + subquery.toString() + ") fecha_generacion");
		sql.SELECT("(" + subquery1.toString() + ") fecha_envio");
		sql.SELECT("(" + subquery2.toString() + ") fecha_recepcion");
		sql.SELECT("F_SIGA_GETRECURSO((tipoest.descripcion), " + idLenguaje + ") estado");
		sql.SELECT("(" + subquery5.toString() + ") incidencias_ejg");
		sql.SELECT("(" + subquery6.toString() + ") total_ejg");
		sql.SELECT("(" + subquery5.toString() + ") || ' / ' || (" + subquery6.toString() + ") as INCIDENCIAS");
		sql.FROM("cajg_remesa rem");
		sql.FROM("cajg_remesaestados est");
		sql.FROM("cajg_tipoestadoremesa tipoest");
		
		sql.WHERE("rem.idinstitucion = " + idInstitucion.toString()); //colegio logado
		
		sql.WHERE("est.fechamodificacion = (" + fechamodificacion.toString() + ")");
		
		sql.WHERE("tipoest.idestado = est.idestado");

		if(remesasBusquedaItem.getNumero() != 0) {
			sql.WHERE("rem.numero= " + remesasBusquedaItem.getNumero()); //numero
		}
		
		if(remesasBusquedaItem.getPrefijo() != 0) {
			sql.WHERE("rem.prefijo = " + remesasBusquedaItem.getPrefijo()); //prefijo
		}
		
		if(remesasBusquedaItem.getSufijo() != 0) {
			sql.WHERE("rem.sufijo = " + remesasBusquedaItem.getSufijo()); //sufijo
		}
		
		if(remesasBusquedaItem.getDescripcion() != null) {
			sql.WHERE("rem.descripcion like '%" + remesasBusquedaItem.getDescripcion() + "%'"); //descripcion
		}
		
		if(remesasBusquedaItem.getEstado() != null) {
			sql.WHERE("est.idestado = " + remesasBusquedaItem.getEstado()); //estado
		}
		
		if(remesasBusquedaItem.getFechaGeneracionDesde() != null || remesasBusquedaItem.getFechaGeneracionHasta() != null) {
			sql.WHERE("exists(" + fechaGeneracion + ")");
		}
			
		if(remesasBusquedaItem.getFechaEnvioDesde() != null || remesasBusquedaItem.getFechaEnvioHasta() != null) {
			sql.WHERE("exists(" + fechaEnvio + ")");
		}
		
		if(remesasBusquedaItem.getFechaRecepcionDesde() != null || remesasBusquedaItem.getFechaRecepcionHasta() != null) {
			sql.WHERE("exists(" + fechaRecepcion + ")");
		}
		
		if(remesasBusquedaItem.getNumeroEJG() != 0) {
			sql.WHERE("exists (" + subquery7.toString() + ")"); //numero deEJG
		}
		
		if(remesasBusquedaItem.getAnnioEJG() != 0) {
			sql.WHERE("exists (" + subquery8.toString() + ")"); //año deEJG
		}
		
		sql.WHERE("ROWNUM <= 200");
		
		LOGGER.info(sql.toString());

		return sql.toString();
	}
	
	public String isEstadoRemesaIniciada(RemesasBusquedaItem remesasBusquedaItem, Short idInstitucion) {
		SQL sql = new SQL();
		SQL subquery = new SQL();
		
		subquery.SELECT("MAX(rem.fechamodificacion)");
		subquery.FROM("cajg_remesaestados rem");
		subquery.WHERE("rem.idremesa = " + remesasBusquedaItem.getIdRemesa());
		subquery.WHERE("rem.idinstitucion = " + idInstitucion.toString());
		
		sql.SELECT("rem.idremesa");
		sql.SELECT("rem.idremesa");
		sql.SELECT("REM.IDINSTITUCION");
		sql.SELECT("REM.IDESTADO");
		sql.SELECT("REM.FECHAREMESA");
		sql.FROM("cajg_remesaestados rem");
		sql.WHERE("rem.idremesa = " + remesasBusquedaItem.getIdRemesa());
		sql.WHERE("rem.idinstitucion = " + idInstitucion.toString());
		sql.WHERE("rem.idestado = 0");
		sql.WHERE("rem.fechamodificacion = (" + subquery.toString() + ")");
		
		LOGGER.info(sql.toString());
		
		return sql.toString();
	}
	
	public String listadoEstadoRemesa(RemesasBusquedaItem remesasBusquedaItem, Short idInstitucion, String idLenguaje) {
		SQL sql = new SQL();
		
		sql.SELECT("rem.idremesa");
		sql.SELECT("REM.IDINSTITUCION");
		sql.SELECT("TO_CHAR(rem.FECHAMODIFICACION, 'dd/MM/yyyy HH24:MI:SS') FECHAMODIFICACION");
		sql.SELECT("F_SIGA_GETRECURSO(tip.descripcion, " + idLenguaje + ") estado");
		sql.FROM("cajg_remesaestados rem");
		sql.FROM("cajg_tipoestadoremesa tip");
		sql.WHERE("tip.idestado = rem.idestado");
		sql.WHERE("rem.idremesa = " + remesasBusquedaItem.getIdRemesa());
		sql.WHERE("rem.idinstitucion = " + idInstitucion.toString());
		sql.ORDER_BY("rem.FECHAMODIFICACION");
		
		LOGGER.info(sql.toString());
		
		return sql.toString();
	}
	
}