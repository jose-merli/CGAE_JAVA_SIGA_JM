package org.itcgae.siga.DTO.fac;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TiposServiciosItem {
	private int idtiposervicios; //Categoria
	private int idservicio; //Tipo
	private String descripcion;
	private Date fechamodificacion;
	private String usumodificacion;
	private int idinstitucion;
	private String descripciontipo; //Nombre tipo
	private Date fechabaja;
    boolean nuevo;
    
	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public int getIdtiposervicios() {
		return idtiposervicios;
	}
	
	public void setIdtiposervicios(int idtiposervicios) {
		this.idtiposervicios = idtiposervicios;
	}
	
	public int getIdservicio() {
		return idservicio;
	}
	
	public void setIdservicio(int idservicio) {
		this.idservicio = idservicio;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Date getFechamodificacion() {
		return fechamodificacion;
	}
	
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}
	
	public String getUsumodificacion() {
		return usumodificacion;
	}
	public void setUsumodificacion(String usumodificacion) {
		this.usumodificacion = usumodificacion;
	}
	
	public int getIdinstitucion() {
		return idinstitucion;
	}
	
	public void setIdinstitucion(int idinstitucion) {
		this.idinstitucion = idinstitucion;
	}
	
	public String getDescripciontipo() {
		return descripciontipo;
	}
	
	public void setDescripciontipo(String descripciontipo) {
		this.descripciontipo = descripciontipo;
	}
	
	public Date getFechabaja() {
		return fechabaja;
	}
	
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((descripciontipo == null) ? 0 : descripciontipo.hashCode());
		result = prime * result + ((fechabaja == null) ? 0 : fechabaja.hashCode());
		result = prime * result + ((fechamodificacion == null) ? 0 : fechamodificacion.hashCode());
		result = prime * result + idinstitucion;
		result = prime * result + idservicio;
		result = prime * result + idtiposervicios;
		result = prime * result + ((usumodificacion == null) ? 0 : usumodificacion.hashCode());
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
		TiposServiciosItem other = (TiposServiciosItem) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (descripciontipo == null) {
			if (other.descripciontipo != null)
				return false;
		} else if (!descripciontipo.equals(other.descripciontipo))
			return false;
		if (fechabaja == null) {
			if (other.fechabaja != null)
				return false;
		} else if (!fechabaja.equals(other.fechabaja))
			return false;
		if (fechamodificacion == null) {
			if (other.fechamodificacion != null)
				return false;
		} else if (!fechamodificacion.equals(other.fechamodificacion))
			return false;
		if (idinstitucion != other.idinstitucion)
			return false;
		if (idservicio != other.idservicio)
			return false;
		if (idtiposervicios != other.idtiposervicios)
			return false;
		if (usumodificacion == null) {
			if (other.usumodificacion != null)
				return false;
		} else if (!usumodificacion.equals(other.usumodificacion))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "TiposServiciosItem [idtiposervicios=" + idtiposervicios + ", idservicio=" + idservicio
				+ ", descripcion=" + descripcion + ", fechamodificacion=" + fechamodificacion + ", usumodificacion="
				+ usumodificacion + ", idinstitucion=" + idinstitucion + ", descripciontipo=" + descripciontipo
				+ ", fechabaja=" + fechabaja + ", nuevo=" + nuevo + "]";
	}
	
}
