package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

public class DatosEnvioDTO {

	private List<ConsultaEnvioItem> consultas = new ArrayList<ConsultaEnvioItem>();
	private DestinatarioItem destinatario;
	
	
	public List<ConsultaEnvioItem> getConsultas() {
		return consultas;
	}
	public DestinatarioItem getDestinatario() {
		return destinatario;
	}
	public void setConsultas(List<ConsultaEnvioItem> consultas) {
		this.consultas = consultas;
	}
	public void setDestinatario(DestinatarioItem destinatario) {
		this.destinatario = destinatario;
	}
	
}
