package org.itcgae.siga.DTOs.cen;

import java.math.BigDecimal;

public class PropuestaDTO {
	
	private int idPaquete;
	private String nombre;
	private BigDecimal tarifa;
	private String breve;
	private String descripcion;
	private boolean familiares;
	private boolean herederos;
	
	
	
	public int getIdPaquete() {
		return idPaquete;
	}
	public void setIdPaquete(int idPaquete) {
		this.idPaquete = idPaquete;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public BigDecimal getTarifa() {
		return tarifa;
	}
	public void setTarifa(BigDecimal tarifa) {
		this.tarifa = tarifa;
	}
	public String getBreve() {
		return breve;
	}
	public void setBreve(String breve) {
		this.breve = breve;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean isFamiliares() {
		return familiares;
	}
	public void setFamiliares(boolean familiares) {
		this.familiares = familiares;
	}
	public boolean isHerederos() {
		return herederos;
	}
	public void setHerederos(boolean herederos) {
		this.herederos = herederos;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((breve == null) ? 0 : breve.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + (familiares ? 1231 : 1237);
		result = prime * result + (herederos ? 1231 : 1237);
		result = prime * result + idPaquete;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((tarifa == null) ? 0 : tarifa.hashCode());
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
		PropuestaDTO other = (PropuestaDTO) obj;
		if (breve == null) {
			if (other.breve != null)
				return false;
		} else if (!breve.equals(other.breve))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (familiares != other.familiares)
			return false;
		if (herederos != other.herederos)
			return false;
		if (idPaquete != other.idPaquete)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (tarifa == null) {
			if (other.tarifa != null)
				return false;
		} else if (!tarifa.equals(other.tarifa))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "PropuestaDTO [idPaquete=" + idPaquete + ", nombre=" + nombre + ", tarifa=" + tarifa + ", breve=" + breve
				+ ", descripcion=" + descripcion + ", familiares=" + familiares + ", herederos=" + herederos + "]";
	}
	
	
}
