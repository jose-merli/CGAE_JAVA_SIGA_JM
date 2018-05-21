package org.itcgae.siga.DTOs.cen;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class BusquedaJuridicaItem {

	private String idInstitucion;
	private String idPersona;
	private String nif;
	private String denominacion;
	private String abreviatura;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaConstitucion;
	private String tipo;
	private String numeroIntegrantes;
	private String nombresIntegrantes;
	private Date fechaBaja;
	
	
}
