package org.itcgae.siga.DTO.fac;

import lombok.Data;

@Data
public class LineaImpresionInformeDTO {

    private String idinstitucion;
    private String idfactura;
    private String numerolinea;
    private String cantidad_linea;
    private String ctaproductoservicio;
    private String ctaiva;
    private String precio_linea;
    private String iva_linea;
    private String fecha_compra;
    private String descripcion_linea;
    private String neto_linea;
    private String importe_iva_linea;
    private String total_linea;
    private float iva_linea_aux;
}
