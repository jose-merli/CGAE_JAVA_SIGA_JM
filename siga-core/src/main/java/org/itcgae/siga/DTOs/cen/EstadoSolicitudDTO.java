package org.itcgae.siga.DTOs.cen;

public class EstadoSolicitudDTO {
	
	private long idSolicitud;
	private String identificador;
	private boolean duplicado;



	public long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public boolean isDuplicado() {
		return duplicado;
	}

	public void setDuplicado(boolean duplicado) {
		this.duplicado = duplicado;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}


}
