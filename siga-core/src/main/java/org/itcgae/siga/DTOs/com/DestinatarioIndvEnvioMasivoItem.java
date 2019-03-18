package org.itcgae.siga.DTOs.com;

public class DestinatarioIndvEnvioMasivoItem {
	
	private String idEnvio;
	private String idPersona;
	private String idTipoEnvios;
	private String idDireccion;
	
	public String getIdDireccion() {
		return idDireccion;
	}
	public void setIdDireccion(String idDireccion) {
		this.idDireccion = idDireccion;
	}
	public String getIdEnvio() {
		return idEnvio;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public String getIdTipoEnvios() {
		return idTipoEnvios;
	}
	public void setIdEnvio(String idEnvio) {
		this.idEnvio = idEnvio;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public void setIdTipoEnvios(String idTipoEnvios) {
		this.idTipoEnvios = idTipoEnvios;
	}

}
