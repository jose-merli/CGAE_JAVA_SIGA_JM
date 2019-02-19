package org.itcgae.siga.DTOs.com;

import org.itcgae.siga.DTOs.gen.Error;

public class TipoEnvioItem {
	private String tipoEnvio;
	private String idTipoEnvio;
	
	private Error error;
	
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public String getTipoEnvio() {
		return tipoEnvio;
	}
	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}
	public String getIdTipoEnvio() {
		return idTipoEnvio;
	}
	public void setIdTipoEnvio(String idTipoEnvio) {
		this.idTipoEnvio = idTipoEnvio;
	}
	
}
