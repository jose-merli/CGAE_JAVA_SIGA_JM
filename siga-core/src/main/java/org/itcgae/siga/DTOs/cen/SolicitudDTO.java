package org.itcgae.siga.DTOs.cen;



public class SolicitudDTO {
	
	private int idPaquete;
	private String observaciones;
	private AseguradoDTO asegurado;
	
	
	public int getIdPaquete() {
		return idPaquete;
	}
	public void setIdPaquete(int idPaquete) {
		this.idPaquete = idPaquete;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
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
