package org.itcgae.siga.DTOs.cen;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class BusquedaJuridicaSearchDTO {

	private String tipo;
	private String nif;
	private String denominacion;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaConstitucion;
	private String integrante;
	private String [] grupos;
	
	/**
	 *
	 */
	public BusquedaJuridicaSearchDTO tipo(String tipo){
		this.tipo = tipo;
		return this;
	}
	
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getDenominacion() {
		return denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	public Date getFechaConstitucion() {
		return fechaConstitucion;
	}
	public void setFechaConstitucion(Date fechaConstitucion) {
		this.fechaConstitucion = fechaConstitucion;
	}
	public String getIntegrante() {
		return integrante;
	}
	public void setIntegrante(String integrante) {
		this.integrante = integrante;
	}
	public String[] getGrupos() {
		return grupos;
	}
	public void setGrupos(String[] grupos) {
		this.grupos = grupos;
	}
	
	
}
