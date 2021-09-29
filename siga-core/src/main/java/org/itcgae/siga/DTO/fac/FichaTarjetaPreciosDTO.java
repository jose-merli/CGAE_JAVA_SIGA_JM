package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.Error;

public class FichaTarjetaPreciosDTO {
	private int precio;
	private int periodicidad;
	private String descripcion;
	private int condicion;
	
	private Error error = null;
	
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	public int getPeriodicidad() {
		return periodicidad;
	}
	public void setPeriodicidad(int periodicidad) {
		this.periodicidad = periodicidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getCondicion() {
		return condicion;
	}
	public void setCondicion(int condicion) {
		this.condicion = condicion;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + condicion;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + periodicidad;
		result = prime * result + precio;
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
		FichaTarjetaPreciosDTO other = (FichaTarjetaPreciosDTO) obj;
		if (condicion != other.condicion)
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (periodicidad != other.periodicidad)
			return false;
		if (precio != other.precio)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "FichaTarjetaPagosDTO [precio=" + precio + ", periodicidad=" + periodicidad + ", descripcion="
				+ descripcion + ", condicion=" + condicion + ", error=" + error + "]";
	}
	
	
}
