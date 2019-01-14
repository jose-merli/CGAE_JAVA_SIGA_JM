package org.itcgae.siga.DTOs.com;

import java.util.Map;

import org.itcgae.siga.DTOs.gen.Error;

public class RemitenteDTO {
	
	private String idPlantillaEnvios;
	private String idTipoEnvios;
	private String idPersona;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String descripcion;
	private String idDireccion;
	private String domicilio;
	private String idPoblacion;
	private String idProvincia;
	private String idPais;
	private String codigoPostal;
	private Map<String,String> contactos;
	private Error error;
	
	

	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public String getNombre() {
		return nombre;
	}
	public String getApellido1() {
		return apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public String getDescripcion() {
		return descripcion;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}
	public Map<String, String> getContactos() {
		return contactos;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getIdPais() {
		return idPais;
	}
	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public void setContactos(Map<String, String> contactos) {
		this.contactos = contactos;
	}
	public String getIdPlantillaEnvios() {
		return idPlantillaEnvios;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public String getIdDireccion() {
		return idDireccion;
	}
	public String getIdTipoEnvios() {
		return idTipoEnvios;
	}
	public void setIdPlantillaEnvios(String idPlantillaEnvios) {
		this.idPlantillaEnvios = idPlantillaEnvios;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public void setIdDireccion(String idDireccion) {
		this.idDireccion = idDireccion;
	}
	public void setIdTipoEnvios(String idTipoEnvios) {
		this.idTipoEnvios = idTipoEnvios;
	}
	public String getIdPoblacion() {
		return idPoblacion;
	}
	public String getIdProvincia() {
		return idProvincia;
	}
	public void setIdPoblacion(String idPoblacion) {
		this.idPoblacion = idPoblacion;
	}
	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
	

}
