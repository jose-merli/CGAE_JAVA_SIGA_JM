package org.itcgae.siga.DTOs.cen;

import java.util.Arrays;
import java.util.List;

public class AlterMutuaResponseDTO {

	private int identificador;
	private byte[] documento;
	private boolean error;
	private String mensaje;
	private List<PropuestaDTO> propuestas;
	
	
	public int getIdentificador() {
		return identificador;
	}
	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}
	public byte[] getDocumento() {
		return documento;
	}
	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public List<PropuestaDTO> getPropuestas() {
		return propuestas;
	}
	public void setPropuestas(List<PropuestaDTO> propuestas) {
		this.propuestas = propuestas;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(documento);
		result = prime * result + (error ? 1231 : 1237);
		result = prime * result + identificador;
		result = prime * result + ((mensaje == null) ? 0 : mensaje.hashCode());
		result = prime * result + ((propuestas == null) ? 0 : propuestas.hashCode());
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
		AlterMutuaResponseDTO other = (AlterMutuaResponseDTO) obj;
		if (!Arrays.equals(documento, other.documento))
			return false;
		if (error != other.error)
			return false;
		if (identificador != other.identificador)
			return false;
		if (mensaje == null) {
			if (other.mensaje != null)
				return false;
		} else if (!mensaje.equals(other.mensaje))
			return false;
		if (propuestas == null) {
			if (other.propuestas != null)
				return false;
		} else if (!propuestas.equals(other.propuestas))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "alterMutuaResponseDTO [identificador=" + identificador + ", documento=" + Arrays.toString(documento)
				+ ", error=" + error + ", mensaje=" + mensaje + ", propuestas=" + propuestas + "]";
	}
	
	
	
}
