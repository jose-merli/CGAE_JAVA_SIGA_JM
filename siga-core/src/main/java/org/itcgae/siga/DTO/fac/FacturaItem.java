package org.itcgae.siga.DTO.fac;

import lombok.Data;

import java.util.Date;

@Data
public class FacturaItem {

	String idFactura;
	String idInstitucion;
	String numeroFactura;
	String estado;
	String formaCobroFactura;
	String formaCobroAbono;//Efectivo 'E', Banco 'B', Ambos 'A'
	String numeroAbonoSJCS;
	Date fechaEmision;
	Date fechaEmisionDesde;
	Date fechaEmisionHasta;
	String importefacturado;
	String importefacturadoDesde;
	String importefacturadoHasta;
	String contabilizado;

	String serie;
	String facturacion;
	String identificadorAdeudos;
	String identificadorTransferencia;
	String identificadorDevolucion;

	//String colegio;
	String numeroColegiado;
	String numeroIdentificacion;
	String apellidos;
	//String apellidos2;
	String nombre;

	String facturasPendientesDesde;
	String facturasPendientesHasta;
	String importeAdeudadoPendiente;
	String importeAdeudadoHasta;
	String importeAdeudadoDesde;
	String comunicacionesFacturas;
	String comunicacionesFacturasHasta;
	String comunicacionesFacturasDesde;

	String tipo;
	Date ultimaComunicacion;
	String nombreInstitucion;

}