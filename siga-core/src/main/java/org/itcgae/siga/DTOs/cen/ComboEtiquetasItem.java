package org.itcgae.siga.DTOs.cen;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComboEtiquetasItem {
	private String idGrupo;
	private String fechaInicio;
	private String fechaBaja;
	private String label;
	private String backgroundColor;
	
	@JsonProperty("idGrupo")
	public String getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}
	
	
	@JsonProperty("fechaInicio")
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	@JsonProperty("fechaBaja")
	public String getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(String fechaFin) {
		this.fechaBaja = fechaFin;
	}
	
	@JsonProperty("label")
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	@JsonProperty("backgroundColor")
	public String getColor() {
		return backgroundColor;
	}
	public void setColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}