package org.itcgae.siga.DTOs.com;

import org.itcgae.siga.DTOs.cen.ComboInstitucionItem;

public class TarjetaEtiquetasDTO {
	
	private String idEnvio;
	private ComboInstitucionItem[] etiquetasSeleccionadas;
	private ComboInstitucionItem[] etiquetasNoSeleccionadas;
	
	
	public String getIdEnvio() {
		return idEnvio;
	}
	public void setIdEnvio(String idEnvio) {
		this.idEnvio = idEnvio;
	}
	public ComboInstitucionItem[] getEtiquetasSeleccionadas() {
		return etiquetasSeleccionadas;
	}
	public void setEtiquetasSeleccionadas(ComboInstitucionItem[] etiquetasSeleccionadas) {
		this.etiquetasSeleccionadas = etiquetasSeleccionadas;
	}
	public ComboInstitucionItem[] getEtiquetasNoSeleccionadas() {
		return etiquetasNoSeleccionadas;
	}
	public void setEtiquetasNoSeleccionadas(ComboInstitucionItem[] etiquetasNoSeleccionadas) {
		this.etiquetasNoSeleccionadas = etiquetasNoSeleccionadas;
	}
	
	

}
