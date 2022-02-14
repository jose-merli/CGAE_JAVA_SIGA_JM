package org.itcgae.siga.DTO.fac;

import lombok.Data;

@Data
public class DatoImpresionInformeFacturaDTO {

    private String idinstitucion;
    private String idfactura;
    private String numerofactura;
    private String fechaemision;
    private String observaciones;
    private String observinforme;
    private String idpersona;
    private String idcuenta;
    private String idcuentadeudor;
    private String iban;
    private String cbo_codigo;
    private String codigosucursal;
    private String digitocontrol;
    private String importe_neto;
    private String importe_iva;
    private String total_factura;
    private String total_pagos;
    private String anticipado;
    private String compensado;
    private String por_banco;
    private String por_caja;
    private String por_solocaja;
    private String por_solotarjeta;
    private String pendiente_pagar;
    private String numerocuenta;
    private String titular;
    private String idmandato;
    private String comisionidfactura;
    private String descripcion_estado;
    private String refmandatosepa;

}
