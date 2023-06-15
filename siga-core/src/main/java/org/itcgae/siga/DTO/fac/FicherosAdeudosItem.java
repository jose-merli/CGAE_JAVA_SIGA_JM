package org.itcgae.siga.DTO.fac;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FicherosAdeudosItem {

	String idInstitucion;
	String idDisqueteCargos;
	String nombreFichero;
	String bancosCodigo;
	String cuentaEntidad;
	String iban;
	Date fechaCreacion;
	Date fechaCreacionDesde;
	Date fechaCreacionHasta;
	String idseriefacturacion;
	String nombreabreviado;
	String idprogramacion;
	String descripcion;
	Date fechacargo;
	String numerolineas;
	String idSufijo;
	String sufijo;
	String totalRemesa;
	String importeTotalDesde;
	String importeTotalHasta;
	String numRecibos;
	String numRecibosDesde;
	String numRecibosHasta;
	String origen;
	String facturacion;
	Date fechaPresentacion;
	Date fechaRecibosPrimeros;
	Date fechaRecibosRecurrentes;
	Date fechaRecibosCOR;
	Date fechaRecibosB2B;
	Date fechaUltimaModificacion;

	List<String> facturasGeneracion;
	List<FacFacturacionprogramadaItem> facturacionesGeneracion;

}