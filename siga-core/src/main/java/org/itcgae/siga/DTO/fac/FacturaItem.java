package org.itcgae.siga.DTO.fac;

import lombok.Data;
import org.itcgae.siga.DTOs.gen.ComboItem;

import java.util.Date;
import java.util.List;

@Data
public class FacturaItem {

	String tipo;//"FACTURA" o "ABONO"

	String idFactura;
	String idAbono;
	String idInstitucion;

	//datos generales
	String numeroFactura;
	String idEstado;
	String estado;

	String idProgramacion;
	String idPersona;

	String formaCobroFactura;
	String formaCobroAbono;//Efectivo 'E', Banco 'B', Ambos 'A'

	String contabilizado;
	String numeroAbonoSJCS;//solo abono

	Date fechaEmision;
	Date fechaEmisionDesde;
	Date fechaEmisionHasta;

	double importeIVA;
	double importeNeto;

	String importefacturado;
	String importefacturadoDesde;
	String importefacturadoHasta;

	String importeAdeudadoHasta;
	String importeAdeudadoDesde;

	//Pago
	String idFormaPago;
	String nombreFormaPago;
	
	//importes factura
	double importeAnticipado;
	double importeCompensado;
	double importeCaja;
	double importeBanco;
	double importePagado;
	double importeAdeudadoPendiente;
	String idAccion;

	//importes abono
	double importeAnuladoAb;
	double importeCajaAb;
	double importeBancoAb;
	double importePagadoAb;
	double importeAdeudadoPendienteAb;

	//facturacion
	String idFacturacion;
	String idSerieFacturacion;
	String serie;
	String facturacion;
	Date fechaEmisionFacturacion;
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

	//EstadosPagos
	String estadoUlt;
	double importePorPagarUlt;
	double importePagadoUlt;
	String idAccionUlt;
	String fechaModificacionUlt;
	
	//observaciones factura
	String observacionesFactura;
	String observacionesFicheroFactura;

	//observaciones abono
	String observacionesAbono;
	String motivosAbono;

	String facturasPendientesDesde;
	String facturasPendientesHasta;

	String comunicacionesFacturas;
	String comunicacionesFacturasHasta;
	String comunicacionesFacturasDesde;

	Date ultimaComunicacion;
	String nombreInstitucion;

	String bancosCodigo;

	List<String> estadosFiltroFac;
	List<String> estadosFiltroAb;

	List<ComboItem> cuentasBanco;

}