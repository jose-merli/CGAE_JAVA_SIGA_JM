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

	String accion;
	String estado;
	String cuentaBancaria;

	Float movimiento;
	Float importePendiente;

}