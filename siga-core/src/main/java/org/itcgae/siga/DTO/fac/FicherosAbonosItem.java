package org.itcgae.siga.DTO.fac;

import java.util.Date;

import lombok.Data;

@Data
public class FicherosAbonosItem {

	String idInstitucion;
	String idDisqueteAbono;
	Date fechaCreacion;
	String bancosCodigo;
	String nombreFichero;
	String sufijo;

	String cuentaEntidad;
	Date fechaCreacionDesde;
	Date fechaCreacionHasta;
	String importeTotalDesde;
	String importeTotalHasta;
	String importeTotal;
	String numRecibosDesde;
	String numRecibosHasta;

	String idSufijo;
	String numRecibos;

	String propSEPA;
	String propOtros;
}