package org.itcgae.siga.db.services.scs.providers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.scs.RemesaResultadoDTO;
import org.itcgae.siga.DTOs.scs.RemesasResultadoItem;
import org.itcgae.siga.db.entities.AdmBotonaccion;
import org.itcgae.siga.db.entities.CajgRemesa;

import com.ibm.wsdl.util.StringUtils;

public class ScsRemesasResultadosExtendsProvider {
	
	private Logger LOGGER = Logger.getLogger(ScsRemesasResultadosExtendsProvider.class);
	
	public String  buscarRemesasResultado(RemesasResultadoItem remesasResultadoItem, Short idInstitucion, int tamMaximo) {
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
		
		if(remesasResultadoItem.getNumRemesaPrefijo() != null && !remesasResultadoItem.getNumRemesaPrefijo().isEmpty()) {
			sql.WHERE("REM.PREFIJO LIKE '%" + remesasResultadoItem.getNumRemesaPrefijo() + "%'");
		}
		
		if(remesasResultadoItem.getNumRemesaNumero() != null && !remesasResultadoItem.getNumRemesaNumero().isEmpty()) {
			sql.WHERE("REM.NUMERO LIKE '%" + remesasResultadoItem.getNumRemesaNumero() + "%'");
		}
		
		if(remesasResultadoItem.getNumRemesaSufijo() != null && !remesasResultadoItem.getNumRemesaSufijo().isEmpty()) {
			sql.WHERE("REM.SUFIJO LIKE '%" + remesasResultadoItem.getNumRemesaSufijo() + "%'");
		}
		
		if(remesasResultadoItem.getNumRegistroPrefijo() != null && !remesasResultadoItem.getNumRegistroPrefijo().isEmpty()) {
			sql.WHERE("REMR.PREFIJO LIKE '%" + remesasResultadoItem.getNumRegistroPrefijo() + "%'");
		}
		
		if(remesasResultadoItem.getNumRegistroNumero() != null && !remesasResultadoItem.getNumRegistroNumero().isEmpty()) {
			sql.WHERE("REMR.NUMERO LIKE '%" + remesasResultadoItem.getNumRegistroNumero() + "%'");
		}
		
		if(remesasResultadoItem.getNumRegistroSufijo() != null && !remesasResultadoItem.getNumRegistroSufijo().isEmpty()) {
			sql.WHERE("REMR.SUFIJO LIKE '%" + remesasResultadoItem.getNumRegistroSufijo() + "%'");
		}
		
		if(remesasResultadoItem.getNombreFichero() != null && !remesasResultadoItem.getNombreFichero().isEmpty()) {
			sql.WHERE("REMR.NOMBREFICHERO LIKE '%" + remesasResultadoItem.getNombreFichero() + "%'");
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
		sql.WHERE("REM.IDREMESA = REMR.IDREMESA");//BORRAR PARA MOSTRAR ERRONEOS, IDREMESA TENDREA VALOR CON PROCEDIMIENTO CORRECTO, SINO, NO.
		sql.WHERE("ROWNUM <= " + tamMaximo);	
		sql.ORDER_BY("REMR.FECHARESOLUCION DESC, REMR.IDREMESARESOLUCION DESC");
		
		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String buscarLineasFichero(int idRemesaResultado, Short idInstitucion) {
		SQL sql = new SQL();
		
		
		sql.SELECT("IDREMESARESOLUCIONFICHERO");
		sql.SELECT("LINEA");
		
		
		sql.FROM("CAJG_REMESARESOLUCIONFICHERO");
		
		if(idRemesaResultado != 0) {
			sql.WHERE("IDREMESARESOLUCION =" + idRemesaResultado);
		}
			
		sql.WHERE("IDINSTITUCION = " + idInstitucion);
		
		sql.ORDER_BY("NUMEROLINEA ASC");
		
		LOGGER.info(sql.toString());
		return sql.toString();
	}
	
	public String insertSelective(RemesasResultadoItem remesasResultadoItem, Short idInstitucion, Integer idUsuario) {
		SQL sql = new SQL();

		sql.INSERT_INTO("CAJG_REMESARESOLUCION");

		sql.VALUES("IDREMESARESOLUCION", "(Select max(idremesaresolucion) + 1  from cajg_remesaresolucion)");
		sql.VALUES("IDINSTITUCION", "'" + String.valueOf(idInstitucion) + "'");
		sql.VALUES("PREFIJO", "'" + remesasResultadoItem.getPrefijoRemesa() + "'");
		if(!("").equals(remesasResultadoItem.getNumeroRemesa())) {
			sql.VALUES("NUMERO", "'" + remesasResultadoItem.getNumeroRemesa() + "'");
		} else {
			sql.VALUES("NUMERO", "'0000'");
		}
		sql.VALUES("SUFIJO", "'" + remesasResultadoItem.getSufijoRemesa() + "'");
		sql.VALUES("OBSERVACIONES", "'" + remesasResultadoItem.getObservacionesRemesaResultado() + "'");
		sql.VALUES("FECHARESOLUCION", "SYSDATE");
		sql.VALUES("FECHAMODIFICACION", "SYSDATE");
		sql.VALUES("USUMODIFICACION", "'" + idUsuario + "'");
		sql.VALUES("NOMBREFICHERO", "'" + remesasResultadoItem.getNombreFichero() + "'");
		sql.VALUES("FECHACARGA", "SYSDATE");
		sql.VALUES("LOGGENERADO", "'0'");
		sql.VALUES("IDTIPOREMESA", "'3'");
		sql.VALUES("IDREMESA", "null");

		return sql.toString();
	}

	
	
}
