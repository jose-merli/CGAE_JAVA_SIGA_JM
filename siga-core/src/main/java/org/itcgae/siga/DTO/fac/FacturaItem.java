package org.itcgae.siga.DTO.fac;

import lombok.Data;

import java.util.Date;

@Data
public class FacturaItem {

	String tipo;//"FACTURA" o "ABONO"

	String idFactura;
	String idInstitucion;

	//datos generales
	String numeroFactura;
	String estado;

	String formaCobroFactura;
	String formaCobroAbono;//Efectivo 'E', Banco 'B', Ambos 'A'

	String contabilizado;
	String numeroAbonoSJCS;//solo abono

	Date fechaEmision;
	Date fechaEmisionDesde;
	Date fechaEmisionHasta;

	String importeIVA;
	String importeNeto;

	String importefacturado;
	String importefacturadoDesde;
	String importefacturadoHasta;

	//importes ambos
	String importeCaja;
	String importeBanco;
	String importePagado;

	String importeAdeudadoPendiente;
	String importeAdeudadoHasta;
	String importeAdeudadoDesde;

	//importes factura
	String importeAnticipado;
	String importeCompensado;

	//importes Abono
	String importeAnulado;

	//facturacion
	String idFacturacion;
	String idSerieFacturacion;
	String serie;
	String facturacion;
	Date fechaEminionFacturacion;
	String identificadorAdeudos;
	String identificadorTransferencia;
	String identificadorDevolucion;

	//cliente
	String idCliente;
	String numeroColegiado;
	String numeroIdentificacion;
	String apellidos;
	String nombre;

	//deudor
	String idDeudor;
	String identificacionDeudor;
	String descripcionDeudor;
	String abreviaturaDeudor;

	String facturasPendientesDesde;
	String facturasPendientesHasta;

	String comunicacionesFacturas;
	String comunicacionesFacturasHasta;
	String comunicacionesFacturasDesde;

	Date ultimaComunicacion;
	String nombreInstitucion;

	//observaciones factura
	String observacionesFactura;
	String observacionesFicheroFactura;

	//observaciones abono
	String observacionesAbono;
	String motivosAbono;
}