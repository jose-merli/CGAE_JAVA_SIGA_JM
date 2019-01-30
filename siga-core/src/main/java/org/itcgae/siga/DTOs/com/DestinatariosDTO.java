package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class DestinatariosDTO {
	
	private List<DestinatarioItem> destinatarios = new ArrayList<DestinatarioItem>();
	private Error error = null;
	
	
	public List<DestinatarioItem> getDestinatarios() {
		return destinatarios;
	}
	public void setDestinatarios(List<DestinatarioItem> destinatarios) {
		this.destinatarios = destinatarios;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
	

}
