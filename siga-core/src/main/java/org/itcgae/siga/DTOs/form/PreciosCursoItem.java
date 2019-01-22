package org.itcgae.siga.DTOs.form;

public class PreciosCursoItem {
	
	private Long idCurso;
	private String periocidad;
	private String porDefecto;
	private String importe;
	private String descripcion;
	
	public Long getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}
	public String getPeriocidad() {
		return periocidad;
	}
	public void setPeriocidad(String periocidad) {
		this.periocidad = periocidad;
	}
	public String getPorDefecto() {
		return porDefecto;
	}
	public void setPorDefecto(String porDefecto) {
		this.porDefecto = porDefecto;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((idCurso == null) ? 0 : idCurso.hashCode());
		result = prime * result + ((importe == null) ? 0 : importe.hashCode());
		result = prime * result + ((periocidad == null) ? 0 : periocidad.hashCode());
		result = prime * result + ((porDefecto == null) ? 0 : porDefecto.hashCode());
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
		PreciosCursoItem other = (PreciosCursoItem) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (idCurso == null) {
			if (other.idCurso != null)
				return false;
		} else if (!idCurso.equals(other.idCurso))
			return false;
		if (importe == null) {
			if (other.importe != null)
				return false;
		} else if (!importe.equals(other.importe))
			return false;
		if (periocidad == null) {
			if (other.periocidad != null)
				return false;
		} else if (!periocidad.equals(other.periocidad))
			return false;
		if (porDefecto == null) {
			if (other.porDefecto != null)
				return false;
		} else if (!porDefecto.equals(other.porDefecto))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "PreciosCursoItem [idCurso=" + idCurso + ", periocidad=" + periocidad + ", porDefecto=" + porDefecto
				+ ", importe=" + importe + ", descripcion=" + descripcion + "]";
	}
	
	
	

}
