package org.itcgae.siga.DTOs.cen;

import java.util.Arrays;

public class MutualidadResponseDTO {
	
	private String idSolicitud;
	private String idSolicitudRespuesta;
	private String NMutualista;
	private Byte[] PDF;
	private String valorRespuesta;
	
	
	
	public String getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(String idSolicitud) {
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
	public Byte[] getPDF() {
		return PDF;
	}
	public void setPDF(Byte[] pDF) {
		PDF = pDF;
	}
	public String getValorRespuesta() {
		return valorRespuesta;
	}
	public void setValorRespuesta(String valorRespuesta) {
		this.valorRespuesta = valorRespuesta;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NMutualista == null) ? 0 : NMutualista.hashCode());
		result = prime * result + Arrays.hashCode(PDF);
		result = prime * result + ((idSolicitud == null) ? 0 : idSolicitud.hashCode());
		result = prime * result + ((idSolicitudRespuesta == null) ? 0 : idSolicitudRespuesta.hashCode());
		result = prime * result + ((valorRespuesta == null) ? 0 : valorRespuesta.hashCode());
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
		MutualidadResponseDTO other = (MutualidadResponseDTO) obj;
		if (NMutualista == null) {
			if (other.NMutualista != null)
				return false;
		} else if (!NMutualista.equals(other.NMutualista))
			return false;
		if (!Arrays.equals(PDF, other.PDF))
			return false;
		if (idSolicitud == null) {
			if (other.idSolicitud != null)
				return false;
		} else if (!idSolicitud.equals(other.idSolicitud))
			return false;
		if (idSolicitudRespuesta == null) {
			if (other.idSolicitudRespuesta != null)
				return false;
		} else if (!idSolicitudRespuesta.equals(other.idSolicitudRespuesta))
			return false;
		if (valorRespuesta == null) {
			if (other.valorRespuesta != null)
				return false;
		} else if (!valorRespuesta.equals(other.valorRespuesta))
			return false;
		return true;
	}
}
