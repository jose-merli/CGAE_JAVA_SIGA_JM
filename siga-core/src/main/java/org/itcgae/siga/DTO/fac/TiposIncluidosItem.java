package org.itcgae.siga.DTO.fac;

import java.util.Objects;

public class TiposIncluidosItem {

	private String idTipoIncluido;
	private String descripcion;
	private String idSerieFacturacion;
	
	public String getIdTipoIncluido() {
		return idTipoIncluido;
	}
	
	public void setIdTipoIncluido(String idTipoIncluido) {
		this.idTipoIncluido = idTipoIncluido;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getIdSerieFacturacion() {
		return idSerieFacturacion;
	}
	
	public void setIdSerieFacturacion(String idSerieFacturacion) {
		this.idSerieFacturacion = idSerieFacturacion;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(descripcion, idSerieFacturacion, idTipoIncluido);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TiposIncluidosItem other = (TiposIncluidosItem) obj;
		return Objects.equals(descripcion, other.descripcion)
				&& Objects.equals(idSerieFacturacion, other.idSerieFacturacion)
				&& Objects.equals(idTipoIncluido, other.idTipoIncluido);
	}
	
	@Override
	public String toString() {
		return "TiposIncluidosItem [idTipoIncluido=" + idTipoIncluido + ", descripcion=" + descripcion
				+ ", idSerieFacturacion=" + idSerieFacturacion + "]";
	}
	
}
