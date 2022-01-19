package org.itcgae.siga.db.services.fcs.providers;

import org.apache.ibatis.jdbc.SQL;
import org.itcgae.siga.DTOs.scs.MovimientosVariosFacturacionItem;
import org.itcgae.siga.db.entities.FcsMvariosCertificaciones;
import org.itcgae.siga.db.mappers.FcsMovimientosvariosSqlProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FcsMovimientosvariosSqlExtendsProvider extends FcsMovimientosvariosSqlProvider {


    public String buscarMVColegiado (MovimientosVariosFacturacionItem movimientoItem, String idInstitucion) {
    	
    	SQL sql = new SQL();
    	
    	
    	SQL subquery5 = new SQL();
        subquery5.SELECT("MAX(estado.fechaestado)");
        subquery5.FROM("fcs_pagos_estadospagos estado");
        subquery5.WHERE("estado.idinstitucion = fcs_aplica_movimientosvarios.idinstitucion");
        subquery5.WHERE("estado.idpagosjg = fcs_aplica_movimientosvarios.idpagosjg");

        SQL subquery4 = new SQL();
        subquery4.SELECT("fcs_pagos_estadospagos.idestadopagosjg");
        subquery4.FROM("fcs_pagos_estadospagos");
        subquery4.WHERE("fcs_pagos_estadospagos.fechaestado = (" + subquery5 + ")");
        subquery4.WHERE("fcs_pagos_estadospagos.idinstitucion = fcs_aplica_movimientosvarios.idinstitucion");
        subquery4.WHERE("fcs_pagos_estadospagos.idpagosjg = fcs_aplica_movimientosvarios.idpagosjg");
    	
    	SQL subquery3 = new SQL();
        subquery3.SELECT("SUM(aplica.importeaplicado)");
        subquery3.FROM("fcs_aplica_movimientosvarios aplica");
        subquery3.WHERE("fcs_aplica_movimientosvarios.idinstitucion = aplica.idinstitucion");
        subquery3.WHERE("fcs_aplica_movimientosvarios.idmovimiento = aplica.idmovimiento");
        subquery3.WHERE("aplica.idaplicacion <= fcs_aplica_movimientosvarios.idaplicacion"); 
        
        SQL subquery6 = new SQL();
        
        subquery6.SELECT("fcs_pagosjg.nombre pagoasociado");
        subquery6.SELECT("FCS_APLICA_MOVIMIENTOSVARIOS.IDPAGOSJG IDPAGOSJG");
        subquery6.SELECT("FCS_MOVIMIENTOSVARIOS.FECHAALTA fecha_orden");
        
        subquery6.SELECT("CASE WHEN (FCS_MOVIMIENTOSVARIOS.CANTIDAD > 0) THEN '1' ELSE '2' END orden");
        subquery6.SELECT("(CASE CEN_COLEGIADO.COMUNITARIO WHEN '0' THEN CEN_COLEGIADO.NCOLEGIADO ELSE CEN_COLEGIADO.NCOMUNITARIO END ) AS NUMERO");
        subquery6.SELECT("(CEN_PERSONA.APELLIDOS1 || ' ' || CEN_PERSONA.APELLIDOS2 || ' ' || CEN_PERSONA.NOMBRE) NOMBRE");
        subquery6.SELECT("cen_persona.apellidos1 apellido1");
        subquery6.SELECT("cen_persona.apellidos2 apellido2");
        subquery6.SELECT("cen_persona.nombre nombreLetrado");
        subquery6.SELECT("cen_persona.nifcif nif");
        subquery6.SELECT("cen_colegiado.ncolegiado ncolegiado");
        subquery6.SELECT("cen_colegiado.ncomunitario ncomunitario");
        subquery6.SELECT("fcs_movimientosvarios.idinstitucion idinstitucion");
        subquery6.SELECT("fcs_movimientosvarios.idpersona idpersona");
        subquery6.SELECT("fcs_movimientosvarios.cantidad cantidad");
        subquery6.SELECT("fcs_aplica_movimientosvarios.importeaplicado cantidadaplicada");
        subquery6.SELECT("(fcs_movimientosvarios.cantidad - (" + subquery3 + ") ) cantidadrestante");
        subquery6.SELECT("fcs_movimientosvarios.fechaalta fechaalta");
        subquery6.SELECT("fcs_movimientosvarios.descripcion movimiento");
        subquery6.SELECT("fcs_movimientosvarios.idmovimiento idmovimiento");
        subquery6.SELECT("(" + subquery4 + ") idestadopagosjg");
        subquery6.SELECT("fcs_movimientosvarios.idfacturacion idfacturacion");
        subquery6.SELECT("fcs_movimientosvarios.idgrupofacturacion idgrupofacturacion");
        subquery6.SELECT("fcs_facturacionjg.nombre nombrefacturacion");
        subquery6.SELECT("f_siga_getrecurso(scs_grupofacturacion.nombre,1) nombregrupofacturacion");
        subquery6.SELECT("fcs_aplica_movimientosvarios.idaplicacion");
        subquery6.SELECT("fcs_movimientosvarios.motivo motivo");
        subquery6.SELECT("fcs_movimientosvarios.idhitogeneral idconcepto");
        subquery6.SELECT("fcs_movimientosvarios.idpartidapresupuestaria idpartidapresupuestaria");
        subquery6.SELECT("fcs_movimientosvarios.idtipomovimiento idtipo");
        subquery6.SELECT("fmc.idcertificacion idcertificacion");  
                   
        subquery6.FROM("FCS_MOVIMIENTOSVARIOS");
        subquery6.INNER_JOIN("CEN_COLEGIADO ON FCS_MOVIMIENTOSVARIOS.IDINSTITUCION = CEN_COLEGIADO.IDINSTITUCION AND FCS_MOVIMIENTOSVARIOS.IDPERSONA = CEN_COLEGIADO.IDPERSONA");
        subquery6.INNER_JOIN("CEN_PERSONA ON CEN_COLEGIADO.IDPERSONA = CEN_PERSONA.IDPERSONA");
        subquery6.LEFT_OUTER_JOIN("FCS_APLICA_MOVIMIENTOSVARIOS ON FCS_MOVIMIENTOSVARIOS.IDINSTITUCION = FCS_APLICA_MOVIMIENTOSVARIOS.IDINSTITUCION AND FCS_MOVIMIENTOSVARIOS.IDMOVIMIENTO = FCS_APLICA_MOVIMIENTOSVARIOS.IDMOVIMIENTO");
        subquery6.LEFT_OUTER_JOIN("FCS_PAGOSJG ON FCS_APLICA_MOVIMIENTOSVARIOS.IDINSTITUCION = FCS_PAGOSJG.IDINSTITUCION AND FCS_APLICA_MOVIMIENTOSVARIOS.IDPAGOSJG = FCS_PAGOSJG.IDPAGOSJG");
        subquery6.LEFT_OUTER_JOIN("FCS_FACTURACIONJG ON FCS_MOVIMIENTOSVARIOS.IDINSTITUCION = FCS_FACTURACIONJG.IDINSTITUCION AND FCS_MOVIMIENTOSVARIOS.IDFACTURACION = FCS_FACTURACIONJG.IDFACTURACION");
        subquery6.LEFT_OUTER_JOIN("SCS_GRUPOFACTURACION ON FCS_MOVIMIENTOSVARIOS.IDINSTITUCION = SCS_GRUPOFACTURACION.IDINSTITUCION AND FCS_MOVIMIENTOSVARIOS.IDGRUPOFACTURACION = SCS_GRUPOFACTURACION.IDGRUPOFACTURACION");
        subquery6.LEFT_OUTER_JOIN("FCS_MVARIOS_CERTIFICACIONES fmc ON fmc.IDINSTITUCION = fcs_movimientosvarios.IDINSTITUCION AND fmc.IDMOVIMIENTO = fcs_movimientosvarios.IDMOVIMIENTO");

        if(movimientoItem.getTipo() != null && movimientoItem.getTipo() != "") {
        	subquery6.INNER_JOIN("fcs_movimientosvarios_tipo ON fcs_movimientosvarios.idinstitucion = fcs_movimientosvarios_tipo.idinstitucion AND fcs_movimientosvarios.idtipomovimiento = fcs_movimientosvarios_tipo.idtipomovimiento");
        }
        
        subquery6.WHERE("(fcs_movimientosvarios.cantidad - ("+subquery3+") )  < 0"); 
    	subquery6.WHERE("fcs_aplica_movimientosvarios.importeaplicado  IS NOT NULL");
        
        subquery6.WHERE("FCS_MOVIMIENTOSVARIOS.IDINSTITUCION = "+idInstitucion);

        if (movimientoItem.getNcolegiado() != null && movimientoItem.getNcolegiado() != "") {
        	subquery6.WHERE("REGEXP_LIKE ( cen_colegiado.ncolegiado,'^[0]{0,}" + movimientoItem.getNcolegiado() + "$')");
        }

        if (movimientoItem.getDescripcion() != null && movimientoItem.getDescripcion() != "") {
        	//subquery6.WHERE("REGEXP_LIKE ( fcs_movimientosvarios.descripcion,'" + movimientoItem.getDescripcion() + "')");
        	subquery6.WHERE("UPPER(FCS_MOVIMIENTOSVARIOS.DESCRIPCION) LIKE UPPER('%"+ movimientoItem.getDescripcion()+"%')");
        }
        
        if(movimientoItem.getTipo() != null && movimientoItem.getTipo() != "") {
        	subquery6.WHERE("fcs_movimientosvarios.idtipomovimiento IN("+movimientoItem.getTipo()+")");
        }

        if (movimientoItem.getCertificacion() != null && movimientoItem.getCertificacion() != "") {
        	subquery6.WHERE("fmc.IDCERTIFICACION IN("+movimientoItem.getCertificacion()+")");
        }
        
        if(movimientoItem.getIdAplicadoEnPago() != null && movimientoItem.getIdAplicadoEnPago() != "") {
        	subquery6.WHERE("fcs_aplica_movimientosvarios.idpagosjg IN("+movimientoItem.getIdAplicadoEnPago()+")");
        }

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        
        if (movimientoItem.getFechaApDesde() != null) {
        	String fechaDesde = "";
            
            fechaDesde = dateFormat.format(movimientoItem.getFechaApDesde());
            subquery6.WHERE("TRUNC(fcs_aplica_movimientosvarios.FECHAMODIFICACION) >= TO_DATE('" + fechaDesde + "', 'DD/MM/RRRR')");

        }
        
        if (movimientoItem.getFechaApHasta() != null) {
        	 String fechaHasta = "";
        	 
        	fechaHasta = dateFormat.format(movimientoItem.getFechaApHasta());
            subquery6.WHERE("TRUNC(fcs_aplica_movimientosvarios.FECHAMODIFICACION) <= TO_DATE('" + fechaHasta + "', 'DD/MM/RRRR')");

        }
        
        if(movimientoItem.getIdFacturacion() != null && movimientoItem.getIdFacturacion() != "") {
        	subquery6.WHERE("fcs_movimientosvarios.idfacturacion IN("+movimientoItem.getIdFacturacion()+")");
        }
        
        if(movimientoItem.getIdGrupoFacturacion() != null && movimientoItem.getIdGrupoFacturacion() != "") {
        	subquery6.WHERE("fcs_movimientosvarios.idgrupofacturacion IN("+movimientoItem.getIdGrupoFacturacion()+")");
        }
        
        if(movimientoItem.getIdConcepto() != null && movimientoItem.getIdConcepto() != "") {
        	subquery6.WHERE("fcs_movimientosvarios.idhitogeneral IN("+movimientoItem.getIdConcepto()+")");
        }
        
        if(movimientoItem.getIdPartidaPresupuestaria() != null && movimientoItem.getIdPartidaPresupuestaria() != "") {
        	subquery6.WHERE("fcs_movimientosvarios.idpartidapresupuestaria IN("+movimientoItem.getIdPartidaPresupuestaria()+")");
        }
        
        SQL subquery2 = new SQL();
       
        subquery2.SELECT("FCS_PAGOSJG.NOMBRE PAGOASOCIADO");
        subquery2.SELECT("FCS_APLICA_MOVIMIENTOSVARIOS.IDPAGOSJG IDPAGOSJG");
        subquery2.SELECT("FCS_MOVIMIENTOSVARIOS.FECHAALTA fecha_orden");
        subquery2.SELECT("CASE WHEN (FCS_MOVIMIENTOSVARIOS.CANTIDAD > 0) THEN '1' ELSE '2' END orden");
        subquery2.SELECT("(CASE CEN_COLEGIADO.COMUNITARIO WHEN '0' THEN CEN_COLEGIADO.NCOLEGIADO ELSE CEN_COLEGIADO.NCOMUNITARIO END ) AS NUMERO");
        subquery2.SELECT("(CEN_PERSONA.APELLIDOS1 || ' ' || CEN_PERSONA.APELLIDOS2 || ' ' || CEN_PERSONA.NOMBRE) NOMBRE");
        subquery2.SELECT("cen_persona.apellidos1 apellido1");
        subquery2.SELECT("cen_persona.apellidos2 apellido2");
        subquery2.SELECT("cen_persona.nombre nombreLetrado");
        subquery2.SELECT("cen_persona.nifcif nif");
        subquery2.SELECT("cen_colegiado.ncolegiado ncolegiado");
        subquery2.SELECT("cen_colegiado.ncomunitario ncomunitario");
        subquery2.SELECT("fcs_movimientosvarios.idinstitucion idinstitucion");
        subquery2.SELECT("fcs_movimientosvarios.idpersona idpersona");
        subquery2.SELECT("fcs_movimientosvarios.cantidad cantidad");
        subquery2.SELECT("fcs_aplica_movimientosvarios.importeaplicado cantidadaplicada");
        subquery2.SELECT("(fcs_movimientosvarios.cantidad - (" + subquery3 + ") ) cantidadrestante");
        subquery2.SELECT("fcs_movimientosvarios.fechaalta fechaalta");
        subquery2.SELECT("fcs_movimientosvarios.descripcion movimiento");
        subquery2.SELECT("fcs_movimientosvarios.idmovimiento idmovimiento");
        subquery2.SELECT("(" + subquery4 + ") idestadopagosjg");
        subquery2.SELECT("fcs_movimientosvarios.idfacturacion idfacturacion");
        subquery2.SELECT("fcs_movimientosvarios.idgrupofacturacion idgrupofacturacion");
        subquery2.SELECT("fcs_facturacionjg.nombre nombrefacturacion");
        subquery2.SELECT("f_siga_getrecurso(scs_grupofacturacion.nombre,1) nombregrupofacturacion");
        subquery2.SELECT("fcs_aplica_movimientosvarios.idaplicacion");
        subquery2.SELECT("fcs_movimientosvarios.motivo motivo");
        subquery2.SELECT("fcs_movimientosvarios.idhitogeneral idconcepto");
        subquery2.SELECT("fcs_movimientosvarios.idpartidapresupuestaria idpartidapresupuestaria");
        subquery2.SELECT("fcs_movimientosvarios.idtipomovimiento idtipo");
        subquery2.SELECT("fmc.idcertificacion idcertificacion");  
     
        subquery2.FROM("FCS_MOVIMIENTOSVARIOS");
        subquery2.INNER_JOIN("CEN_COLEGIADO ON FCS_MOVIMIENTOSVARIOS.IDINSTITUCION = CEN_COLEGIADO.IDINSTITUCION AND FCS_MOVIMIENTOSVARIOS.IDPERSONA = CEN_COLEGIADO.IDPERSONA");
        subquery2.INNER_JOIN("CEN_PERSONA ON CEN_COLEGIADO.IDPERSONA = CEN_PERSONA.IDPERSONA");
        subquery2.LEFT_OUTER_JOIN("FCS_APLICA_MOVIMIENTOSVARIOS ON FCS_MOVIMIENTOSVARIOS.IDINSTITUCION = FCS_APLICA_MOVIMIENTOSVARIOS.IDINSTITUCION AND FCS_MOVIMIENTOSVARIOS.IDMOVIMIENTO = FCS_APLICA_MOVIMIENTOSVARIOS.IDMOVIMIENTO");
        subquery2.LEFT_OUTER_JOIN("FCS_PAGOSJG ON FCS_APLICA_MOVIMIENTOSVARIOS.IDINSTITUCION = FCS_PAGOSJG.IDINSTITUCION AND FCS_APLICA_MOVIMIENTOSVARIOS.IDPAGOSJG = FCS_PAGOSJG.IDPAGOSJG");
        subquery2.LEFT_OUTER_JOIN("FCS_FACTURACIONJG ON FCS_MOVIMIENTOSVARIOS.IDINSTITUCION = FCS_FACTURACIONJG.IDINSTITUCION AND FCS_MOVIMIENTOSVARIOS.IDFACTURACION = FCS_FACTURACIONJG.IDFACTURACION");
        subquery2.LEFT_OUTER_JOIN("SCS_GRUPOFACTURACION ON FCS_MOVIMIENTOSVARIOS.IDINSTITUCION = SCS_GRUPOFACTURACION.IDINSTITUCION AND FCS_MOVIMIENTOSVARIOS.IDGRUPOFACTURACION = SCS_GRUPOFACTURACION.IDGRUPOFACTURACION");
        subquery2.LEFT_OUTER_JOIN("FCS_MVARIOS_CERTIFICACIONES fmc ON fmc.IDINSTITUCION = fcs_movimientosvarios.IDINSTITUCION AND fmc.IDMOVIMIENTO = fcs_movimientosvarios.IDMOVIMIENTO");

        if(movimientoItem.getTipo() != null && movimientoItem.getTipo() != "") {
        	subquery2.INNER_JOIN("fcs_movimientosvarios_tipo ON fcs_movimientosvarios.idinstitucion = fcs_movimientosvarios_tipo.idinstitucion AND fcs_movimientosvarios.idtipomovimiento = fcs_movimientosvarios_tipo.idtipomovimiento");
        }
        
        if (movimientoItem.getNcolegiado() != null && movimientoItem.getNcolegiado() != "") {
        	subquery2.WHERE("REGEXP_LIKE ( cen_colegiado.ncolegiado,'^[0]{0,}" + movimientoItem.getNcolegiado() + "$')");
        }

        if (movimientoItem.getDescripcion() != null && movimientoItem.getDescripcion() != "") {
        	//subquery2.WHERE("REGEXP_LIKE ( fcs_movimientosvarios.descripcion,'" + movimientoItem.getDescripcion() + "')");
        	subquery2.WHERE("UPPER(FCS_MOVIMIENTOSVARIOS.DESCRIPCION) LIKE UPPER('%"+ movimientoItem.getDescripcion()+"%')");
        }
        
        if(movimientoItem.getTipo() != null && movimientoItem.getTipo() != "") {
        	subquery2.WHERE("fcs_movimientosvarios.idtipomovimiento IN("+movimientoItem.getTipo()+")");
        }

        if (movimientoItem.getCertificacion() != null && movimientoItem.getCertificacion() != "") {
        	subquery2.WHERE("fmc.IDCERTIFICACION IN("+movimientoItem.getCertificacion()+")");
        }
        
        if(movimientoItem.getIdAplicadoEnPago() != null && movimientoItem.getIdAplicadoEnPago() != "") {
        	subquery2.WHERE("fcs_aplica_movimientosvarios.idpagosjg IN("+movimientoItem.getIdAplicadoEnPago()+")");
        }

        
        if (movimientoItem.getFechaApDesde() != null) {
        	String fechaDesde = "";
            
            fechaDesde = dateFormat.format(movimientoItem.getFechaApDesde());
            subquery2.WHERE("TRUNC(fcs_aplica_movimientosvarios.FECHAMODIFICACION) >= TO_DATE('" + fechaDesde + "', 'DD/MM/RRRR')");

        }
        
        if (movimientoItem.getFechaApHasta() != null) {
        	 String fechaHasta = "";
        	 
        	fechaHasta = dateFormat.format(movimientoItem.getFechaApHasta());
        	subquery2.WHERE("TRUNC(fcs_aplica_movimientosvarios.FECHAMODIFICACION) <= TO_DATE('" + fechaHasta + "', 'DD/MM/RRRR')");

        }
        
        if(movimientoItem.getIdFacturacion() != null && movimientoItem.getIdFacturacion() != "") {
        	subquery2.WHERE("fcs_movimientosvarios.idfacturacion IN("+movimientoItem.getIdFacturacion()+")");
        }
        
        if(movimientoItem.getIdGrupoFacturacion() != null && movimientoItem.getIdGrupoFacturacion() != "") {
        	subquery2.WHERE("fcs_movimientosvarios.idgrupofacturacion IN("+movimientoItem.getIdGrupoFacturacion()+")");
        }
        
        if(movimientoItem.getIdConcepto() != null && movimientoItem.getIdConcepto() != "") {
        	subquery2.WHERE("fcs_movimientosvarios.idhitogeneral IN("+movimientoItem.getIdConcepto()+")");
        }
        
        if(movimientoItem.getIdPartidaPresupuestaria() != null && movimientoItem.getIdPartidaPresupuestaria() != "") {
        	subquery2.WHERE("fcs_movimientosvarios.idpartidapresupuestaria IN("+movimientoItem.getIdPartidaPresupuestaria()+")");
        }
          
       
        if(movimientoItem.isHistorico()) {
        	subquery2.WHERE("(fcs_movimientosvarios.cantidad - ("+subquery3+") )  = 0"); 
         }else {
        	 subquery2.WHERE("(fcs_movimientosvarios.cantidad - ("+subquery3+") )  > 0");  	   
         }
        
        subquery2.WHERE("fcs_aplica_movimientosvarios.importeaplicado  IS NOT NULL");
        
        subquery2.WHERE("FCS_MOVIMIENTOSVARIOS.IDINSTITUCION = "+idInstitucion+") MINUS "+ subquery6);
        
       
        sql.SELECT("*");
    	sql.FROM("("+subquery2);
    	sql.WHERE("ROWNUM <= 200");
        sql.ORDER_BY("nombre,orden,fecha_orden,idaplicacion ASC");
        
    	return sql.toString();
    }

    public String ejecutarFuncionMovVario(String idInstitucion, String idMovimiento, String funcion) {

        SQL sql = new SQL();

        sql.SELECT(funcion + "(" + idMovimiento + "," + idInstitucion + ") AS RESULTADO");
        sql.FROM("DUAL");

        return sql.toString();
    }

    public String comprobarExistenciaActuacion(String nombreTabla, String idInstitucion, String idMovimiento) {

        SQL sql = new SQL();

        sql.SELECT("IDINSTITUCION");
        sql.FROM(nombreTabla);
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDMOVIMIENTO = " + idMovimiento);

        return sql.toString();
    }

    public String quitaMovimientoVarioAsociado(String nombreTabla, String idInstitucion, String idMovimiento) {

        SQL sql = new SQL();

        sql.UPDATE(nombreTabla);
        sql.SET("IDMOVIMIENTO = NULL");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDMOVIMIENTO = " + idMovimiento);

        return sql.toString();
    }

    public String delete(String idInstitucion, String idMovimiento) {

        SQL sql = new SQL();

        sql.DELETE_FROM("FCS_MOVIMIENTOSVARIOS");
        sql.WHERE("IDINSTITUCION = " + idInstitucion);
        sql.WHERE("IDMOVIMIENTO = " + idMovimiento);

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
    
    public String comboCertificacionSJCS(String idInstitucion) {


        SQL sql = new SQL();
        sql.SELECT("IDCERTIFICACION AS ID");
        sql.SELECT("NOMBRE AS DESCRIPCION");
        sql.FROM("FCS_CERTIFICACIONES ffc");
        sql.WHERE("IDINSTITUCION ='" + idInstitucion + "'");

        return sql.toString();
    }


    public String saveClienteMovimientosVarios(MovimientosVariosFacturacionItem movimiento, String idInstitucion) {

        SQL sql = new SQL();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/mm/yyyy");
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

    public String updateClienteMovimientosVarios(MovimientosVariosFacturacionItem movimiento, String idInstitucion) {

        SQL sql = new SQL();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/mm/yyyy");
        String today = formateador.format(new Date());

        sql.UPDATE("FCS_MOVIMIENTOSVARIOS");

        if (movimiento.getIdPersona() != null && movimiento.getIdPersona() != "") {
            sql.SET("IDPERSONA ='" + movimiento.getIdPersona() + "'");
        }

        sql.SET("FECHAMODIFICACION = TO_DATE('" + today + "','DD/MM/RRRR')");

        sql.WHERE("IDMOVIMIENTO ='" + movimiento.getIdMovimiento() + "'");
        sql.WHERE("IDINSTITUCION ='" + movimiento.getIdInstitucion() + "'");

        return sql.toString();
    }


    public String getListadoPagos(MovimientosVariosFacturacionItem movimientos, String idInstitucion) {

        SQL sql = new SQL();

        sql.SELECT("p.importepagado importeaplicado");
        sql.SELECT("p.fechamodificacion fechamodificacion");
        sql.SELECT("p.nombre nombrepago");
        sql.SELECT("(p.importerepartir - p.importepagado) importerestante");
        sql.FROM("FCS_MOVIMIENTOSVARIOS m");
        sql.INNER_JOIN("FCS_APLICA_MOVIMIENTOSVARIOS a ON m.IDINSTITUCION = a.IDINSTITUCION AND m.IDMOVIMIENTO = a.IDMOVIMIENTO");
        sql.INNER_JOIN("FCS_PAGO_COLEGIADO c ON a.IDPAGOSJG = c.IDPAGOSJG AND a.IDINSTITUCION = c.IDINSTITUCION AND a.IDPERSONA = c.IDPERORIGEN");
        sql.INNER_JOIN("FCS_PAGOSJG p ON p.IDPAGOSJG = c.IDPAGOSJG AND p.IDINSTITUCION = c.IDINSTITUCION");
        sql.WHERE("m.IDINSTITUCION ='" + idInstitucion + "'");
        sql.WHERE("m.IDMOVIMIENTO ='" + movimientos.getIdMovimiento() + "'");
        sql.ORDER_BY("fechamodificacion");

        return sql.toString();

    }

    public String selectMaxIdMovimientoByIdInstitucion(String idInstitucion) {
        SQL sql = new SQL();

        sql.SELECT("MAX(IDMOVIMIENTO) as IDMOVIMIENTO");
        sql.FROM("FCS_MOVIMIENTOSVARIOS");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

        return sql.toString();
    }

    public String selectMaxIdCertificacionByIdInstitucion(String idInstitucion) {
        SQL sql = new SQL();

        sql.SELECT("MAX(IDCERTIFICACION) as IDCERTIFICACION");
        sql.FROM("FCS_MVARIOS_CERTIFICACIONES");
        sql.WHERE("IDINSTITUCION = '" + idInstitucion + "'");

        return sql.toString();
    }
    
    public String selectIdPersonaByNif(String nif) {
        SQL sql = new SQL();

        sql.SELECT("IDPERSONA as IDPERSONA");
        sql.FROM("CEN_PERSONA");
        sql.WHERE("NIFCIF = '" + nif + "'");

        return sql.toString();
    }

    public String getMovimientoVarioPorId(String idMovimiento, Short idInstitucion) {

        SQL sql = new SQL();
        sql.SELECT("M.IDINSTITUCION");
        sql.SELECT("M.IDMOVIMIENTO");
        sql.SELECT("M.IDPERSONA");
        sql.SELECT("(P.APELLIDOS2 || ' ' || P.APELLIDOS1 || ', ' || P.NOMBRE) NOMBRE");
        sql.SELECT("M.DESCRIPCION MOVIMIENTO");
        sql.SELECT("M.CANTIDAD");
        sql.SELECT("DECODE(C.COMUNITARIO, 1, C.NCOMUNITARIO, C.NCOLEGIADO) NCOLEGIADO");
        sql.SELECT("P.APELLIDOS1 APELLIDO1");
        sql.SELECT("P.APELLIDOS2 APELLIDO2");
        sql.SELECT("P.NOMBRE NOMBRELETRADO");
        sql.SELECT("P.NIFCIF NIF");
        sql.SELECT("M.MOTIVO");
        sql.SELECT("M.IDFACTURACION");
        sql.SELECT("M.IDGRUPOFACTURACION");
        sql.SELECT("M.IDPARTIDAPRESUPUESTARIA");
        sql.SELECT("M.IDHITOGENERAL");
        sql.FROM("FCS_MOVIMIENTOSVARIOS M");
        sql.JOIN("CEN_PERSONA P ON M.IDPERSONA = P.IDPERSONA");
        sql.JOIN("CEN_COLEGIADO C ON C.IDPERSONA = P.IDPERSONA AND C.IDINSTITUCION = M.IDINSTITUCION");
        sql.WHERE("M.IDINSTITUCION = " + idInstitucion);
        sql.WHERE("M.IDMOVIMIENTO = " + idMovimiento);

        return sql.toString();
    }
    
}
