package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class EnviosMasivosDTO {
	
	private List<EnviosMasivosItem> enviosMasivosItem = new ArrayList<EnviosMasivosItem>();
	private Error error = null;
	
	
	public List<EnviosMasivosItem> getEnviosMasivosItem() {
		return enviosMasivosItem;
	}
	public void setEnviosMasivosItem(List<EnviosMasivosItem> enviosMasivosItem) {
		this.enviosMasivosItem = enviosMasivosItem;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
}
