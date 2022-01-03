package org.itcgae.siga.DTO.fac;

import lombok.Data;

@Data
public class FacturaLineaItem {

	String idFactura;
	String numeroLinea;
	String descripcion;
	String precioUnitario;
	String cantidad;
	String importeNeto;
	String tipoIVA;
	String idTipoIVA;
	String importeIVA;
	String importeTotal;
	String importeAnticipado;
	String idCodigoBanco;
}