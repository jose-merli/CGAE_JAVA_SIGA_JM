package org.itcgae.siga.DTO.fac;

import lombok.Data;

import java.util.Date;

@Data
public class EstadosAbonosItem {

	Date fecha;
	Date fechaModificaion;

	String identificador;
	String idAbono;
	String numeroAbono;
	String idFactura;
	String numeroFactura;
	String idPagosjg;

	String idAccion;
	String accion;

	String idEstado;
	String estado;

	String idFormaPago;
	String idCuenta;
	String cuentaBancaria;
	String idDisqueteAbono;

	Float movimiento;
	Float importePendiente;

}