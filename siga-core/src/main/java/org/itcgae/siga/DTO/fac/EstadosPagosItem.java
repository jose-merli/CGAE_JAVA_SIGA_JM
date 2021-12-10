package org.itcgae.siga.DTO.fac;

import lombok.Data;

import java.util.Date;

@Data
public class EstadosPagosItem {

	Date fechaModificaion;
	String accion;
	String estado;
	String iban;
	String impTotalPagado;
	String impTotalPorPagar;
	String IDSJCS;
	Boolean enlaceFactura;
	String numeroFactura;
	String idFactura;
	String idCargos;
	String idDevoluciones;
	Boolean enlaceAbono;
	String numeroAbono;
	String idAbono;
}