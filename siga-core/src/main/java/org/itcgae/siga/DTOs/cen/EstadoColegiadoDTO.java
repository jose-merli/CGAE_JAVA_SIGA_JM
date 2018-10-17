package org.itcgae.siga.DTOs.cen;

public class EstadoColegiadoDTO {
	
	private int tipoIdentificador;
	private String identificador;
	
	
	public int getTipoIdentificador() {
		return tipoIdentificador;
	}
	public void setTipoIdentificador(int tipoIdentificador) {
		this.tipoIdentificador = tipoIdentificador;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identificador == null) ? 0 : identificador.hashCode());
		result = prime * result + tipoIdentificador;
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
		EstadoColegiadoDTO other = (EstadoColegiadoDTO) obj;
		if (identificador == null) {
			if (other.identificador != null)
				return false;
		} else if (!identificador.equals(other.identificador))
			return false;
		if (tipoIdentificador != other.tipoIdentificador)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "EstadoColegiadoDTO [tipoIdentificador=" + tipoIdentificador + ", identificador=" + identificador + "]";
	}

}
