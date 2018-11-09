package org.itcgae.siga.DTOs.cen;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SolicitudDTO {
	
	private int idPaquete;
	private String observaciones;
	private AseguradoDTO asegurado;
	private List<PersonaDTO> familiares;
	private List<PersonaDTO> herederos;
	
	@JsonProperty("familiares")
	public List<PersonaDTO> getFamiliares() {
		return familiares;
	}
	public void setFamiliares(List<PersonaDTO> familiares) {
		this.familiares = familiares;
	}
	
	@JsonProperty("herederos")
	public List<PersonaDTO> getHerederos() {
		return herederos;
	}
	public void setHerederos(List<PersonaDTO> herederos) {
		this.herederos = herederos;
	}
	@JsonProperty("idPaquete")
	public int getIdPaquete() {
		return idPaquete;
	}
	public void setIdPaquete(int idPaquete) {
		this.idPaquete = idPaquete;
	}
	@JsonProperty("observaciones")
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	@JsonProperty("asegurado")
	public AseguradoDTO getAsegurado() {
		return asegurado;
	}
	public void setAsegurado(AseguradoDTO asegurado) {
		this.asegurado = asegurado;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idPaquete;
		result = prime * result + ((observaciones == null) ? 0 : observaciones.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SolicitudDTO other = (SolicitudDTO) obj;
		if (idPaquete != other.idPaquete)
			return false;
		if (observaciones == null) {
			if (other.observaciones != null)
				return false;
		} else if (!observaciones.equals(other.observaciones))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "SolicitudDTO [idPaquete=" + idPaquete + ", observaciones=" + observaciones + "]";
	}
	
	

}
