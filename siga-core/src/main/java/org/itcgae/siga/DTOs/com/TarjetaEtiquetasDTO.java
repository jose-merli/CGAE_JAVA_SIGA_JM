package org.itcgae.siga.DTOs.com;

public class TarjetaEtiquetasDTO {
	
	private String idEnvio;
	private String[] etiquetasSeleccionadas;
	private String[] etiquetasNoSeleccionadas;
	
	
	public String getIdEnvio() {
		return idEnvio;
	}
	public void setIdEnvio(String idEnvio) {
		this.idEnvio = idEnvio;
	}
	public String[] getEtiquetasSeleccionadas() {
		return etiquetasSeleccionadas;
	}
	public void setEtiquetasSeleccionadas(String[] etiquetasSeleccionadas) {
		this.etiquetasSeleccionadas = etiquetasSeleccionadas;
	}
	public String[] getEtiquetasNoSeleccionadas() {
		return etiquetasNoSeleccionadas;
	}
	public void setEtiquetasNoSeleccionadas(String[] etiquetasNoSeleccionadas) {
		this.etiquetasNoSeleccionadas = etiquetasNoSeleccionadas;
	}
	
	

}
