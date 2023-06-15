package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

public class DatosEnvioDTO {

	private List<ConsultaEnvioItem> consultas = new ArrayList<ConsultaEnvioItem>();
	private List<DestinatarioItem> destinatarios = new ArrayList<DestinatarioItem>();
	
	
	public List<ConsultaEnvioItem> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<ConsultaEnvioItem> consultas) {
		this.consultas = consultas;
	}

	public List<DestinatarioItem> getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(List<DestinatarioItem> destinatarios) {
		this.destinatarios = destinatarios;
	}

}
