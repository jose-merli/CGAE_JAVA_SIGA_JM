package org.itcgae.siga.DTOs.com;

public class TarjetaPerfilesDTO {
	
	private String idModeloComunicacion;
	private String[] perfilesSeleccionados;
	private String[] perfilesNoSeleccionados;
	
	public String getIdModeloComunicacion() {
		return idModeloComunicacion;
	}
	public void setIdModeloComunicacion(String idModeloComunicacion) {
		this.idModeloComunicacion = idModeloComunicacion;
	}
	public String[] getPerfilesSeleccionados() {
		return perfilesSeleccionados;
	}
	public void setPerfilesSeleccionados(String[] perfilesSeleccionados) {
		this.perfilesSeleccionados = perfilesSeleccionados;
	}
	public String[] getPerfilesNoSeleccionados() {
		return perfilesNoSeleccionados;
	}
	public void setPerfilesNoSeleccionados(String[] perfilesNoSeleccionados) {
		this.perfilesNoSeleccionados = perfilesNoSeleccionados;
	}

}
