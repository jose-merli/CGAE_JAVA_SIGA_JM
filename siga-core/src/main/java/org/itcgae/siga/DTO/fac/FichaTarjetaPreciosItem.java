package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.Error;

public class FichaTarjetaPreciosItem {
	private String precio;
	private int idperiodicidad;
	private String descripcionprecio;
	private int idcondicion;
	private String descripcionperiodicidad;
	private String descripcionconsulta;
	private String pordefecto;
	
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public int getIdperiodicidad() {
		return idperiodicidad;
	}
	public void setIdperiodicidad(int idperiodicidad) {
		this.idperiodicidad = idperiodicidad;
	}
	public String getDescripcionprecio() {
		return descripcionprecio;
	}
	public void setDescripcionprecio(String descripcionprecio) {
		this.descripcionprecio = descripcionprecio;
	}
	public int getIdcondicion() {
		return idcondicion;
	}
	public void setIdcondicion(int idcondicion) {
		this.idcondicion = idcondicion;
	}
	public String getDescripcionperiodicidad() {
		return descripcionperiodicidad;
	}
	public void setDescripcionperiodicidad(String descripcionperiodicidad) {
		this.descripcionperiodicidad = descripcionperiodicidad;
	}
	public String getDescripcionconsulta() {
		return descripcionconsulta;
	}
	public String getPordefecto() {
		return pordefecto;
	}
	public void setPordefecto(String pordefecto) {
		this.pordefecto = pordefecto;
	}
	public void setDescripcionconsulta(String descripcionconsulta) {
		this.descripcionconsulta = descripcionconsulta;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcionconsulta == null) ? 0 : descripcionconsulta.hashCode());
		result = prime * result + ((descripcionperiodicidad == null) ? 0 : descripcionperiodicidad.hashCode());
		result = prime * result + ((descripcionprecio == null) ? 0 : descripcionprecio.hashCode());
		result = prime * result + idcondicion;
		result = prime * result + idperiodicidad;
		result = prime * result + ((pordefecto == null) ? 0 : pordefecto.hashCode());
		result = prime * result + ((precio == null) ? 0 : precio.hashCode());
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
		FichaTarjetaPreciosItem other = (FichaTarjetaPreciosItem) obj;
		if (descripcionconsulta == null) {
			if (other.descripcionconsulta != null)
				return false;
		} else if (!descripcionconsulta.equals(other.descripcionconsulta))
			return false;
		if (descripcionperiodicidad == null) {
			if (other.descripcionperiodicidad != null)
				return false;
		} else if (!descripcionperiodicidad.equals(other.descripcionperiodicidad))
			return false;
		if (descripcionprecio == null) {
			if (other.descripcionprecio != null)
				return false;
		} else if (!descripcionprecio.equals(other.descripcionprecio))
			return false;
		if (idcondicion != other.idcondicion)
			return false;
		if (idperiodicidad != other.idperiodicidad)
			return false;
		if (pordefecto == null) {
			if (other.pordefecto != null)
				return false;
		} else if (!pordefecto.equals(other.pordefecto))
			return false;
		if (precio == null) {
			if (other.precio != null)
				return false;
		} else if (!precio.equals(other.precio))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "FichaTarjetaPreciosItem [precio=" + precio + ", idperiodicidad=" + idperiodicidad
				+ ", descripcionprecio=" + descripcionprecio + ", idcondicion=" + idcondicion
				+ ", descripcionperiodicidad=" + descripcionperiodicidad + ", descripcionconsulta="
				+ descripcionconsulta + ", pordefecto=" + pordefecto + "]";
	}

}
