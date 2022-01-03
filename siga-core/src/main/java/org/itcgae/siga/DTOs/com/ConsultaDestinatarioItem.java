package org.itcgae.siga.DTOs.com;

public class ConsultaDestinatarioItem {

	private String idEnvio;
	private String idSerieFacturacion;
	private String idConsulta;
	private String idInstitucion;
	
	
	public String getIdEnvio() {
		return idEnvio;
	}
	public String getIdConsulta() {
		return idConsulta;
	}
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdEnvio(String idEnvio) {
		this.idEnvio = idEnvio;
	}
	public void setIdConsulta(String idConsulta) {
		this.idConsulta = idConsulta;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getIdSerieFacturacion() {
		return idSerieFacturacion;
	}

	public void setIdSerieFacturacion(String idSerieFacturacion) {
		this.idSerieFacturacion = idSerieFacturacion;
	}
}
