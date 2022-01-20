package org.itcgae.siga.DTO.fac;

import java.util.Date;
import java.util.Objects;

public class TiposProductosItem {
	private int idtipoproducto; //Categoria
	private int idproducto; //Tipo
	private String descripcion; //Nombre tipo
	private Date fechamodificacion;
	private String usumodificacion;
	private String descripciontipo;
	private Date fechabaja;
	boolean nuevo;
	
	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(descripcion, descripciontipo, fechabaja, fechamodificacion, idproducto, idtipoproducto,
				nuevo, usumodificacion);
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
		return Objects.equals(descripcion, other.descripcion) && Objects.equals(descripciontipo, other.descripciontipo)
				&& Objects.equals(fechabaja, other.fechabaja)
				&& Objects.equals(fechamodificacion, other.fechamodificacion) && idproducto == other.idproducto
				&& idtipoproducto == other.idtipoproducto && nuevo == other.nuevo
				&& Objects.equals(usumodificacion, other.usumodificacion);
	}

	@Override
	public String toString() {
		return "TiposProductosItem [idtipoproducto=" + idtipoproducto + ", idproducto=" + idproducto + ", descripcion="
				+ descripcion + ", fechamodificacion=" + fechamodificacion + ", usumodificacion=" + usumodificacion
				+ ", descripciontipo=" + descripciontipo + ", fechabaja=" + fechabaja + ", nuevo=" + nuevo + "]";
	}

	
	
}
