package org.itcgae.siga.DTOs.com;

public class TarjetaRemitenteDTO {
	
	private String idPersona;
	private String idDireccion;
	private String idPlantillaEnvios;
	private String idTipoEnvios;
	
	public String getIdPersona() {
		return idPersona;
	}
	public String getIdDireccion() {
		return idDireccion;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public void setIdDireccion(String idDireccion) {
		this.idDireccion = idDireccion;
	}
	public String getIdPlantillaEnvios() {
		return idPlantillaEnvios;
	}
	public String getIdTipoEnvios() {
		return idTipoEnvios;
	}
	public void setIdPlantillaEnvios(String idPlantillaEnvios) {
		this.idPlantillaEnvios = idPlantillaEnvios;
	}
	public void setIdTipoEnvios(String idTipoEnvios) {
		this.idTipoEnvios = idTipoEnvios;
	}
	
}
