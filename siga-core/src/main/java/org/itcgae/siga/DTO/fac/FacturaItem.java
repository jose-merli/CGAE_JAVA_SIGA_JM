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

	String importeAdeudadoHasta;
	String importeAdeudadoDesde;

	//importes factura
	String importeAnticipado;
	String importeCompensado;
	String importeCaja;
	String importeBanco;
	String importePagado;
	String importeAdeudadoPendiente;

	//importes abono
	String importeAnuladoAb;
	String importeCajaAb;
	String importeBancoAb;
	String importePagadoAb;
	String importeAdeudadoPendienteAb;

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