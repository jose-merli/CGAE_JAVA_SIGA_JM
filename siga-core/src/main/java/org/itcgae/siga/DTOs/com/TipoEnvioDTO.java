package org.itcgae.siga.DTOs.com;

import org.itcgae.siga.DTOs.gen.Error;

public class TipoEnvioDTO {
	private TipoEnvioItem tipoEnvio;	
	private Error error;
	
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public TipoEnvioItem getTipoEnvio() {
		return tipoEnvio;
	}
	public void setTipoEnvio(TipoEnvioItem tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}
	
}
