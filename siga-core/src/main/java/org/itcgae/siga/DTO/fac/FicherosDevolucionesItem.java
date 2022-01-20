package org.itcgae.siga.DTO.fac;

import lombok.Data;

import java.util.Date;

@Data
public class FicherosDevolucionesItem {

	String idInstitucion;
	String idDisqueteDevoluciones;
	Date fechaCreacion;
	String nombreFichero;
	Date fechaUltimaModificacion;
	String bancosCodigo;

	String cuentaEntidad;
	Date fechaCreacionDesde;
	Date fechaCreacionHasta;
	String importeTotalDesde;
	String importeTotalHasta;
	String numRecibosDesde;
	String numRecibosHasta;

	String facturacion;
	String numRecibos;

	String sufijo;
}