package org.itcgae.siga.DTO.fac;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TiposProductosItem {
	private int idtipoproducto;
	private int idproducto;
	private String descripcion;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechamodificacion;
	private String usumodificacion;
	private int idinstitucion;
	private String descripciontipo;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechabaja;
	
	public int getIdtipoproducto() {
		return idtipoproducto;
	}
	
	public void setIdtipoproducto(int idtipoproducto) {
		this.idtipoproducto = idtipoproducto;
	}
	
	public String getDescripciontipo() {
		return descripciontipo;
	}
	
	public void setDescripciontipo(String descripciontipo) {
		this.descripciontipo = descripciontipo;
	}
	
	public int getIdproducto() {
		return idproducto;
	}
	
	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Date getFechabaja() {
		return fechabaja;
	}
	
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((descripciontipo == null) ? 0 : descripciontipo.hashCode());
		result = prime * result + ((fechabaja == null) ? 0 : fechabaja.hashCode());
		result = prime * result + ((fechamodificacion == null) ? 0 : fechamodificacion.hashCode());
		result = prime * result + idinstitucion;
		result = prime * result + idproducto;
		result = prime * result + idtipoproducto;
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
		TiposProductosItem other = (TiposProductosItem) obj;
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
		if (idproducto != other.idproducto)
			return false;
		if (idtipoproducto != other.idtipoproducto)
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
		return "TiposProductosItem [idtipoproducto=" + idtipoproducto + ", idproducto=" + idproducto + ", descripcion="
				+ descripcion + ", fechamodificacion=" + fechamodificacion + ", usumodificacion=" + usumodificacion
				+ ", idinstitucion=" + idinstitucion + ", descripciontipo=" + descripciontipo + ", fechabaja="
				+ fechabaja + "]";
	}
	
}
