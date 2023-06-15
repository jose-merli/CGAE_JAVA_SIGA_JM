package org.itcgae.siga.DTO.fac;

import lombok.Data;

import java.util.Date;

@Data
public class EstadosPagosItem {

	Date fechaModificaion;
	String idAccion;
	String accion;
	String idEstado;
	String estado;
	String iban;
	double impTotalPagado;
	double impTotalPorPagar;
	String IDSJCS;
	Boolean enlaceFactura;
	String numeroFactura;
	String idFactura;
	String idCargos;
	String idDevoluciones;
	Boolean enlaceAbono;
	String numeroAbono;
	String idAbono;
	String comentario;
	String cuentaBanco;
	Boolean comision;
	String comisionIdFactura;
	String comisionFactura;
	String modo;
}