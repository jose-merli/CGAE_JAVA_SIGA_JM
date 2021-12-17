package org.itcgae.siga.DTO.fac;

import lombok.Data;

@Data
public class FacturasIncluidasItem {

	String estado;
	String formaPago;
	String numeroFacturas;
	String importeTotal;
	String pendienteTotal;
}