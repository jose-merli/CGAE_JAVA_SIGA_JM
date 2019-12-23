package org.itcgae.siga.DTOs.scs;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class UnidadFamiliarEJGItem {
	
    private String uf_idTipoejg;
    private String uf_anio;
    private String uf_numero;
    private String uf_idPersona;
    private String uf_solicitante;
    private String uf_enCalidad;
    private String pjg_nif;
    private String pjg_nombre;
    private String pjg_ape1;
    private String pjg_ape2;
    private String pjg_direccion;
    private String nombrePrincipal;
    private String apellido1Principal;
    private String apellido2Principal;
    private String pd_descripcion;
    private String estado;
    private Date fechaSolicitud;
    

	/**
	 **/
	public UnidadFamiliarEJGItem uf_idTipoejg(String uf_idTipoejg) {
		this.uf_idTipoejg = uf_idTipoejg;
		return this;
	}

	@JsonProperty("uf_idTipoejg")
	public String getUf_idTipoejg() {
		return uf_idTipoejg;
	}
	public void setUf_idTipoejg(String uf_idTipoejg) {
		this.uf_idTipoejg = uf_idTipoejg;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem uf_anio(String uf_anio) {
		this.uf_anio = uf_anio;
		return this;
	}

	@JsonProperty("uf_anio")
	public String getUf_anio() {
		return uf_anio;
	}
	public void setUf_anio(String uf_anio) {
		this.uf_anio = uf_anio;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem uf_numero(String uf_numero) {
		this.uf_numero = uf_numero;
		return this;
	}

	@JsonProperty("uf_numero")
	public String getUf_numero() {
		return uf_numero;
	}
	public void setUf_numero(String uf_numero) {
		this.uf_numero = uf_numero;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem uf_idPersona(String uf_idPersona) {
		this.uf_idPersona = uf_idPersona;
		return this;
	}

	@JsonProperty("uf_idPersona")
	public String getUf_idPersona() {
		return uf_idPersona;
	}
	public void setUf_idPersona(String uf_idPersona) {
		this.uf_idPersona = uf_idPersona;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem uf_solicitante(String uf_solicitante) {
		this.uf_solicitante = uf_solicitante;
		return this;
	}

	@JsonProperty("uf_solicitante")
	public String getUf_solicitante() {
		return uf_solicitante;
	}
	public void setUf_solicitante(String uf_solicitante) {
		this.uf_solicitante = uf_solicitante;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem uf_enCalidad(String uf_enCalidad) {
		this.uf_enCalidad = uf_enCalidad;
		return this;
	}

	@JsonProperty("uf_enCalidad")
	public String getUf_enCalidad() {
		return uf_enCalidad;
	}
	public void setUf_enCalidad(String uf_enCalidad) {
		this.uf_enCalidad = uf_enCalidad;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem pjg_nif(String pjg_nif) {
		this.pjg_nif = pjg_nif;
		return this;
	}

	@JsonProperty("pjg_nif")
	public String getPjg_nif() {
		return pjg_nif;
	}
	public void setPjg_nif(String pjg_nif) {
		this.pjg_nif = pjg_nif;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem pjg_nombre(String pjg_nombre) {
		this.pjg_nombre = pjg_nombre;
		return this;
	}

	@JsonProperty("pjg_nombre")
	public String getPjg_nombre() {
		return pjg_nombre;
	}
	public void setPjg_nombre(String pjg_nombre) {
		this.pjg_nombre = pjg_nombre;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem pjg_ape1(String pjg_ape1) {
		this.pjg_ape1 = pjg_ape1;
		return this;
	}

	@JsonProperty("pjg_ape1")
	public String getPjg_ape1() {
		return pjg_ape1;
	}
	public void setPjg_ape1(String pjg_ape1) {
		this.pjg_ape1 = pjg_ape1;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem pjg_ape2(String pjg_ape2) {
		this.pjg_ape2 = pjg_ape2;
		return this;
	}

	@JsonProperty("pjg_ape2")
	public String getPjg_ape2() {
		return pjg_ape2;
	}
	public void setPjg_ape2(String pjg_ape2) {
		this.pjg_ape2 = pjg_ape2;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem pjg_direccion(String pjg_direccion) {
		this.pjg_direccion = pjg_direccion;
		return this;
	}

	@JsonProperty("pjg_direccion")
	public String getPjg_direccion() {
		return pjg_direccion;
	}
	public void setPjg_direccion(String pjg_direccion) {
		this.pjg_direccion = pjg_direccion;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem nombrePrincipal(String nombrePrincipal) {
		this.nombrePrincipal = nombrePrincipal;
		return this;
	}

	@JsonProperty("nombrePrincipal")
	public String getNombrePrincipal() {
		return nombrePrincipal;
	}
	public void setNombrePrincipal(String nombrePrincipal) {
		this.nombrePrincipal = nombrePrincipal;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem apellido1Principal(String apellido1Principal) {
		this.apellido1Principal = apellido1Principal;
		return this;
	}

	@JsonProperty("apellido1Principal")
	public String getApellido1Principal() {
		return apellido1Principal;
	}
	public void setApellido1Principal(String apellido1Principal) {
		this.apellido1Principal = apellido1Principal;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem apellido2Principal(String apellido2Principal) {
		this.apellido2Principal = apellido2Principal;
		return this;
	}

	@JsonProperty("apellido2Principal")
	public String getApellido2Principal() {
		return apellido2Principal;
	}
	public void setApellido2Principal(String apellido2Principal) {
		this.apellido2Principal = apellido2Principal;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem pd_descripcion(String pd_descripcion) {
		this.pd_descripcion = pd_descripcion;
		return this;
	}

	@JsonProperty("pd_descripcion")
	public String getPd_descripcion() {
		return pd_descripcion;
	}
	public void setPd_descripcion(String pd_descripcion) {
		this.pd_descripcion = pd_descripcion;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem estado(String estado) {
		this.estado = estado;
		return this;
	}

	@JsonProperty("estado")
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 **/
	public UnidadFamiliarEJGItem fechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
		return this;
	}

	@JsonProperty("fechaSolicitud")
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
    
    
}
