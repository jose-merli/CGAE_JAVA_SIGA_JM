package org.itcgae.siga.DTOs.com;

import java.util.List;

import org.itcgae.siga.DTOs.cen.DatosDireccionesItem;
import org.itcgae.siga.DTOs.gen.Error;

public class RemitenteDTO {
	
	private String idPlantillaEnvios;
	private String idTipoEnvios;
	private String idPersona;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String descripcion;
	private List<DatosDireccionesItem> direccion;
	private Error error;
	private String correoElectronico;
	
	
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public String getIdPlantillaEnvios() {
		return idPlantillaEnvios;
	}
	public String getIdTipoEnvios() {
		return idTipoEnvios;
	}
	public String getIdPersona() {
		return idPersona;
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
	public List<DatosDireccionesItem> getDireccion() {
		return direccion;
	}
	public Error getError() {
		return error;
	}
	public void setIdPlantillaEnvios(String idPlantillaEnvios) {
		this.idPlantillaEnvios = idPlantillaEnvios;
	}
	public void setIdTipoEnvios(String idTipoEnvios) {
		this.idTipoEnvios = idTipoEnvios;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
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
	public void setDireccion(List<DatosDireccionesItem> direccion) {
		this.direccion = direccion;
	}
	public void setError(Error error) {
		this.error = error;
	}
	

	

}
