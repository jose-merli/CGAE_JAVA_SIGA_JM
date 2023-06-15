package org.itcgae.siga.DTO.fac;

import lombok.Data;

@Data
public class FacturaLineaItem {

	String idFactura;
	String numeroLinea;
	String descripcion;
	double precioUnitario;
	String cantidad;
	double importeNeto;
	String tipoIVA;
	String idTipoIVA;
	double importeIVA;
	double importeTotal;
	double importeAnticipado;
	String idCodigoBanco;
}