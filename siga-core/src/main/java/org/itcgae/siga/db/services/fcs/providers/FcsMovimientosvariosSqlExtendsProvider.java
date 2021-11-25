package org.itcgae.siga.db.services.fcs.providers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.MovimientosVariosFacturacionItem;
import org.itcgae.siga.db.mappers.FcsMovimientosvariosSqlProvider;

public class FcsMovimientosvariosSqlExtendsProvider extends FcsMovimientosvariosSqlProvider{

	public String buscarMovimientosVarios(MovimientosVariosFacturacionItem movimientoItem, String idInstitucion) {
        
		SQL sql = new SQL();
		
		SQL subquery = new SQL();
        subquery.SELECT("SUM(aplica.importeaplicado)");
        subquery.FROM("fcs_aplica_movimientosvarios aplica");
        subquery.WHERE("fcs_aplica_movimientosvarios.idinstitucion = aplica.idinstitucion");
        subquery.WHERE("fcs_aplica_movimientosvarios.idmovimiento = aplica.idmovimiento");
        subquery.WHERE("aplica.idaplicacion <= fcs_aplica_movimientosvarios.idaplicacion"); // ??
        
        SQL subquery3 = new SQL();
        subquery3.SELECT("MAX(estado.fechaestado)");
        subquery3.FROM("fcs_pagos_estadospagos estado");
        subquery3.WHERE("estado.idinstitucion = fcs_aplica_movimientosvarios.idinstitucion");
        subquery3.WHERE("estado.idpagosjg = fcs_aplica_movimientosvarios.idpagosjg");
        
        SQL subquery2 = new SQL();
        subquery2.SELECT("fcs_pagos_estadospagos.idestadopagosjg");
        subquery2.FROM("fcs_pagos_estadospagos");
        subquery2.WHERE("fcs_pagos_estadospagos.fechaestado = ("+subquery3+")");
        subquery2.WHERE("fcs_pagos_estadospagos.idinstitucion = fcs_aplica_movimientosvarios.idinstitucion");
        subquery2.WHERE("fcs_pagos_estadospagos.idpagosjg = fcs_aplica_movimientosvarios.idpagosjg");

        
        sql.SELECT("fcs_pagosjg.nombre pagoasociado");
        sql.SELECT("fcs_aplica_movimientosvarios.idpagosjg idpagosjg");
        sql.SELECT("fcs_movimientosvarios.fechaalta fecha_orden");
        sql.SELECT("CASE WHEN (fcs_movimientosvarios.cantidad > 0 ) THEN '1' ELSE '2' END orden");
        sql.SELECT("(CASE cen_colegiado.comunitario WHEN '0'   THEN cen_colegiado.ncolegiado ELSE cen_colegiado.ncomunitario END) AS numero");
        sql.SELECT("( cen_persona.apellidos1"
        		+ "     || ' '"
        		+ "     || cen_persona.apellidos2"
        		+ "     || ' '"
        		+ "     || cen_persona.nombre ) nombre");
        sql.SELECT("cen_persona.apellidos1 apellido1");
        sql.SELECT("cen_persona.apellidos2 apellido2");
        sql.SELECT("cen_persona.nombre nombreLetrado");
        sql.SELECT("cen_persona.nifcif nif");
        sql.SELECT("cen_colegiado.ncolegiado ncolegiado");
        sql.SELECT("cen_colegiado.ncomunitario ncomunitario");
        sql.SELECT("fcs_movimientosvarios.idinstitucion idinstitucion");
        sql.SELECT("fcs_movimientosvarios.idpersona idpersona");
        sql.SELECT("fcs_movimientosvarios.cantidad cantidad");
        sql.SELECT("fcs_aplica_movimientosvarios.importeaplicado cantidadaplicada");
        sql.SELECT("(fcs_movimientosvarios.cantidad - ("+subquery+") ) cantidadrestante");
        sql.SELECT("fcs_movimientosvarios.fechaalta fechaalta");
        sql.SELECT("fcs_movimientosvarios.descripcion movimiento");
        sql.SELECT("fcs_movimientosvarios.idmovimiento idmovimiento");
        sql.SELECT("("+subquery2+") idestadopagosjg");
        sql.SELECT("fcs_movimientosvarios.idfacturacion idfacturacion");
        sql.SELECT("fcs_movimientosvarios.idgrupofacturacion idgrupofacturacion");
        sql.SELECT("fcs_facturacionjg.nombre nombrefacturacion");
        sql.SELECT("f_siga_getrecurso(scs_grupofacturacion.nombre,1) nombregrupofacturacion");
        sql.SELECT("fcs_aplica_movimientosvarios.idaplicacion");
        sql.SELECT("fcs_movimientosvarios.motivo motivo");
        
        sql.FROM("fcs_movimientosvarios");
        sql.INNER_JOIN("cen_colegiado ON fcs_movimientosvarios.idinstitucion = cen_colegiado.idinstitucion AND fcs_movimientosvarios.idpersona = cen_colegiado.idpersona");
        sql.INNER_JOIN("cen_persona ON cen_colegiado.idpersona = cen_persona.idpersona");
        sql.LEFT_OUTER_JOIN("fcs_aplica_movimientosvarios ON fcs_movimientosvarios.idinstitucion = fcs_aplica_movimientosvarios.idinstitucion AND fcs_movimientosvarios.idmovimiento = fcs_aplica_movimientosvarios.idmovimiento");
        sql.LEFT_OUTER_JOIN("fcs_pagosjg ON fcs_aplica_movimientosvarios.idinstitucion = fcs_pagosjg.idinstitucion AND fcs_aplica_movimientosvarios.idpagosjg = fcs_pagosjg.idpagosjg");
        sql.LEFT_OUTER_JOIN("fcs_facturacionjg ON fcs_movimientosvarios.idinstitucion = fcs_facturacionjg.idinstitucion AND fcs_movimientosvarios.idfacturacion = fcs_facturacionjg.idfacturacion");
        sql.LEFT_OUTER_JOIN("scs_grupofacturacion ON fcs_movimientosvarios.idinstitucion = scs_grupofacturacion.idinstitucion AND fcs_movimientosvarios.idgrupofacturacion = scs_grupofacturacion.idgrupofacturacion");
        sql.WHERE("fcs_movimientosvarios.idinstitucion = "+idInstitucion);
        
        if(!movimientoItem.isHistorico()) {
        	sql.WHERE("(fcs_movimientosvarios.cantidad - ("+subquery+") )  >= 0");
        }
        
        if(movimientoItem.getNif() != null && movimientoItem.getNif() != "") {
        	sql.WHERE("REGEXP_LIKE ( cen_persona.nifcif,'"+movimientoItem.getNif()+"')");
        }
        
        if(movimientoItem.getNcolegiado() != null && movimientoItem.getNcolegiado() != "") {
        	sql.WHERE("REGEXP_LIKE ( cen_colegiado.ncolegiado,'^[0]{0,}"+movimientoItem.getNcolegiado()+"$')");
        }
        
        if(movimientoItem.getDescripcion() != null && movimientoItem.getDescripcion() != "") {
            sql.WHERE("REGEXP_LIKE ( fcs_movimientosvarios.descripcion,'"+movimientoItem.getDescripcion()+"')");
        }
        
        if(movimientoItem.getTipo() != null && movimientoItem.getTipo() != "") {
           // sql.WHERE("fcs_movimientosvarios.descripcion movimiento ='"+movimientoItem.getDescripcion()+"'");
        }
        
        if(movimientoItem.getCertificacion() != null && movimientoItem.getCertificacion() != "") {
           // sql.WHERE("fcs_movimientosvarios.descripcion movimiento ='"+movimientoItem.getDescripcion()+"'");
        }
        
        if(movimientoItem.getIdAplicadoEnPago() != null && movimientoItem.getIdAplicadoEnPago() != "") {
            sql.WHERE("fcs_aplica_movimientosvarios.idpagosjg ="+movimientoItem.getIdAplicadoEnPago());
        }
        
        if(movimientoItem.getFechaApDesde() != null) {
           // sql.WHERE("fcs_pagosjg.fechadesde >='"+movimientoItem.getIdAplicadoEnPago()+"'");
        }
        
        if(movimientoItem.getFechaApHasta() != null) {
            //sql.WHERE("fcs_pagosjg.fechahasta <='"+movimientoItem.getIdAplicadoEnPago()+"'");
        }
        
        if(movimientoItem.getIdFacturacion() != null && movimientoItem.getIdFacturacion() != "") {
            sql.WHERE("fcs_movimientosvarios.idfacturacion ="+movimientoItem.getIdFacturacion());
        }
        
        if(movimientoItem.getIdGrupoFacturacion() != null && movimientoItem.getIdGrupoFacturacion() != "") {
            sql.WHERE("fcs_movimientosvarios.idgrupofacturacion ="+movimientoItem.getIdGrupoFacturacion());
        }
        
        if(movimientoItem.getIdConcepto() != null && movimientoItem.getIdConcepto() != "") {
          //  sql.WHERE("fcs_movimientosvarios.idhitogeneral ="+movimientoItem.getIdConcepto());
        }
        
        if(movimientoItem.getIdPartidaPresupuestaria() != null && movimientoItem.getIdPartidaPresupuestaria() != "") {
          //  sql.WHERE("fcs_movimientosvarios.idpartidapresupuestaria ="+movimientoItem.getIdPartidaPresupuestaria());
        }
        
        sql.WHERE("ROWNUM <= 200");
        
        sql.ORDER_BY("nombre,orden,fecha_orden,idaplicacion DESC");
        
			        return sql.toString();
    }
	

 	public String ejecutarFuncionMovVario(String idInstitucion, String idMovimiento, String funcion) {
 		
 		SQL sql = new SQL();
 		
 		sql.SELECT(funcion+"("+idMovimiento+","+idInstitucion+") AS RESULTADO");
 		sql.FROM("DUAL");
 		
 		return sql.toString();
 	}
 	
 	public String comprobarExistenciaActuacion(String nombreTabla,String idInstitucion, String idMovimiento){
 		
 		SQL sql = new SQL();
 		
 		sql.SELECT("IDINSTITUCION");
 		sql.FROM(nombreTabla);
 		sql.WHERE("IDINSTITUCION = "+idInstitucion);
 		sql.WHERE("IDMOVIMIENTO = "+idMovimiento);
 		
 		return sql.toString();
 	}
 	
 	public String quitaMovimientoVarioAsociado(String nombreTabla, String idInstitucion, String idMovimiento){
 		
 		SQL sql= new SQL();
 		
 		sql.UPDATE(nombreTabla);
 		sql.SET("IDMOVIMIENTO = NULL");
 		sql.WHERE("IDINSTITUCION = "+idInstitucion);
 		sql.WHERE("IDMOVIMIENTO = "+idMovimiento);
 		
 		return sql.toString();
 	}
 	
 	public String delete (String idInstitucion, String idMovimiento) {
 		
 		SQL sql= new SQL();
 		
 		sql.DELETE_FROM("FCS_MOVIMIENTOSVARIOS");
 		sql.WHERE("IDINSTITUCION = "+idInstitucion);
 		sql.WHERE("IDMOVIMIENTO = "+idMovimiento);
 		
 		return sql.toString();
 	}
 	
   public String comboTiposMovimientos(String idInstitucion) {

    	
        SQL sql = new SQL();
        sql.SELECT("IDTIPOMOVIMIENTO AS ID");
        sql.SELECT("TRIM( SUBSTR(F_SIGA_GETRECURSO(fe.NOMBRE , 1) ,0,LENGTH(F_SIGA_GETRECURSO(fe.NOMBRE , 1))-3)) AS DESCRIPCION");
        sql.FROM("FCS_MOVIMIENTOSVARIOS_TIPO fe");
        sql.WHERE("IDINSTITUCION ='" + idInstitucion + "'");
        sql.ORDER_BY("DESCRIPCION");
        		 

        return sql.toString();
    }
   
  
   
   public String saveClienteMovimientosVarios( MovimientosVariosFacturacionItem movimiento, String idInstitucion){
		
		SQL sql= new SQL();
		SimpleDateFormat formateador= new SimpleDateFormat("dd/mm/yyyy");
		String today = formateador.format(new Date());
		
		sql.INSERT_INTO("FCS_MOVIMIENTOSVARIOS");
		sql.SET("IDINSTITUCION ='" + movimiento.getIdInstitucion() + "'");
		sql.SET("IDMOVIMIENTO ='" + movimiento.getIdMovimiento() + "'");
		sql.SET("IDPERSONA ='" + movimiento.getIdPersona() + "'");
		sql.SET("DESCRIPCION = NULL");
		sql.SET("MOTIVO = NULL");
		sql.SET("FECHAALTA = TO_DATE('" + today + "','DD/MM/RRRR')");
		sql.SET("CANTIDAD = NULL");
		sql.SET("FECHAMODIFICACION = TO_DATE('" + today + "','DD/MM/RRRR')");
		sql.SET("USUMODIFICACION = 0");
		sql.SET("CONTABILIZADO = NULL");
		sql.SET("IDTIPOMOVIMIENTO = NULL");
		
		return sql.toString();
	}
   
   public String updateClienteMovimientosVarios( MovimientosVariosFacturacionItem movimiento, String idInstitucion){
		
		SQL sql= new SQL();
		SimpleDateFormat formateador= new SimpleDateFormat("dd/mm/yyyy");
		String today = formateador.format(new Date());
		
		sql.UPDATE("FCS_MOVIMIENTOSVARIOS");
		
		if(movimiento.getIdPersona() != null &&  movimiento.getIdPersona() != "") {
			sql.SET("IDPERSONA ='" + movimiento.getIdPersona() + "'");
		}

		sql.SET("FECHAMODIFICACION = TO_DATE('" + today + "','DD/MM/RRRR')");
		
		sql.WHERE("IDMOVIMIENTO ='" + movimiento.getIdMovimiento() + "'");
		sql.WHERE("IDINSTITUCION ='" + movimiento.getIdInstitucion() + "'");
		
		return sql.toString();
	}
   
   
   public String getListadoPagos (MovimientosVariosFacturacionItem movimientos, String idInstitucion) {
	   
	   SQL sql = new SQL();
	   
	   sql.SELECT("p.importepagado importeaplicado");
	   sql.SELECT("p.fechamodificacion fechamodificacion");
	   sql.SELECT("p.nombre nombrepago");
	   sql.SELECT("(p.importerepartir - p.importepagado) importerestante");
	   sql.FROM("FCS_MOVIMIENTOSVARIOS m");
	   sql.INNER_JOIN("FCS_APLICA_MOVIMIENTOSVARIOS a ON m.IDINSTITUCION = a.IDINSTITUCION AND m.IDMOVIMIENTO = a.IDMOVIMIENTO");
	   sql.INNER_JOIN("FCS_PAGO_COLEGIADO c ON a.IDPAGOSJG = c.IDPAGOSJG AND a.IDINSTITUCION = c.IDINSTITUCION AND a.IDPERSONA = c.IDPERORIGEN");
	   sql.INNER_JOIN("FCS_PAGOSJG p ON p.IDPAGOSJG = c.IDPAGOSJG AND p.IDINSTITUCION = c.IDINSTITUCION");
	   sql.WHERE("m.IDINSTITUCION ='"+idInstitucion+"'");
	   sql.WHERE("m.IDMOVIMIENTO ='"+movimientos.getIdMovimiento()+"'");	
	   sql.ORDER_BY("fechamodificacion");
	  	   
	   return sql.toString();
	  
   }
   
   public String selectMaxIdMovimientoByIdInstitucion(String idInstitucion) {
	   SQL sql = new SQL();

	   sql.SELECT("MAX(IDMOVIMIENTO) as IDMOVIMIENTO");
	   sql.FROM("FCS_MOVIMIENTOSVARIOS");
	   sql.WHERE("IDINSTITUCION = '"+idInstitucion+"'");

	   return sql.toString();
   }
   
   public String selectIdPersonaByNif(String nif) {
	   SQL sql = new SQL();

	   sql.SELECT("IDPERSONA as IDPERSONA");
	   sql.FROM("CEN_PERSONA");
	   sql.WHERE("NIFCIF = '"+nif+"'");

	   return sql.toString();
   }
   
}
