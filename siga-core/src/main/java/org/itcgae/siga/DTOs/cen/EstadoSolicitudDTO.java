package org.itcgae.siga.DTOs.cen;

public class EstadoSolicitudDTO {
	
	private int idSolicitud;
	
	private boolean duplicado;

	public int getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(int idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public boolean isDuplicado() {
		return duplicado;
	}

	public void setDuplicado(boolean duplicado) {
		this.duplicado = duplicado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (duplicado ? 1231 : 1237);
		result = prime * result + idSolicitud;
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
		EstadoSolicitudDTO other = (EstadoSolicitudDTO) obj;
		if (duplicado != other.duplicado)
			return false;
		if (idSolicitud != other.idSolicitud)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EstadoColegiadoDTO [idSolicitud=" + idSolicitud + ", duplicado=" + duplicado + "]";
	}
	
	

}
