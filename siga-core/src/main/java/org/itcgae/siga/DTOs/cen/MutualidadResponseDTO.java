package org.itcgae.siga.DTOs.cen;


public class MutualidadResponseDTO {
	
	private long idSolicitud;
	private String idSolicitudRespuesta;
	private String NMutualista;
	private byte[] PDF;
	private String valorRespuesta;
	
	
	public long getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public String getIdSolicitudRespuesta() {
		return idSolicitudRespuesta;
	}
	public void setIdSolicitudRespuesta(String idSolicitudRespuesta) {
		this.idSolicitudRespuesta = idSolicitudRespuesta;
	}
	public String getNMutualista() {
		return NMutualista;
	}
	public void setNMutualista(String nMutualista) {
		NMutualista = nMutualista;
	}
	public byte[] getPDF() {
		return PDF;
	}
	public void setPDF(byte[] pDF) {
		PDF = pDF;
	}
	public String getValorRespuesta() {
		return valorRespuesta;
	}
	public void setValorRespuesta(String valorRespuesta) {
		this.valorRespuesta = valorRespuesta;
	}
	
}
