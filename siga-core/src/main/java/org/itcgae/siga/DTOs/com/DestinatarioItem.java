package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;

public class DestinatarioItem {
	
	private String idPersona;
	private String NIFCIF;
	private String apellidos1;
	private String apellidos2;
	private String nombre;
	private String correoElectronico;
	private String movil;
	private String domicilio;
	private String nombreCompleto;
	private ArrayList<ConsultaEnvioItem> listaConsultasEnvio;
	private String tratamiento;
	private String idioma;
	private String direccion;
	private String codigoPostal;
	private String idPais;
	private String idPoblacion;
	private String idProvincia;
	private String poblacionExtranjera;
	
	
	
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getIdPais() {
		return idPais;
	}
	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}
	public String getIdPoblacion() {
		return idPoblacion;
	}
	public void setIdPoblacion(String idPoblacion) {
		this.idPoblacion = idPoblacion;
	}
	public String getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public String getMovil() {
		return movil;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public void setMovil(String movil) {
		this.movil = movil;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getNIFCIF() {
		return NIFCIF;
	}
	public void setNIFCIF(String nIFCIF) {
		NIFCIF = nIFCIF;
	}
	public String getApellidos1() {
		return apellidos1;
	}
	public void setApellidos1(String apellidos1) {
		this.apellidos1 = apellidos1;
	}
	public String getApellidos2() {
		return apellidos2;
	}
	public void setApellidos2(String apellidos2) {
		this.apellidos2 = apellidos2;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<ConsultaEnvioItem> getListaConsultasEnvio() {
		return listaConsultasEnvio;
	}
	public void setListaConsultasEnvio(ArrayList<ConsultaEnvioItem> listaConsultasEnvio) {
		this.listaConsultasEnvio = listaConsultasEnvio;
	}
	public String getTratamiento() {
		return tratamiento;
	}
	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getPoblacionExtranjera() {
		return poblacionExtranjera;
	}
	public void setPoblacionExtranjera(String poblacionExtranjera) {
		this.poblacionExtranjera = poblacionExtranjera;
	}
}
