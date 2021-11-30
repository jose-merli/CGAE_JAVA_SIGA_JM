package org.itcgae.siga.DTO.fac;

import lombok.Data;

@Data
public class FacturaLineaItem {

	String descripcion;
	String precioUnitario;
	String cantidad;
	String importeNeto;//precioUnitario * cantidad
	String tipoIVA;
	String importeIVA;//precioUnitario * cantidad * iva
	String importeTotal;//importeNeto + importeIVA
	String importeAnticipado;
}