package org.itcgae.siga.DTOs.form;

public class CertificadoCursoItem {
	
	private String idCertificadoCurso;
	private String idTipoProducto;
	private String idProducto;
	private String idProductoInstitucion;
	private String descripcion;
	private String precio;
	private String idCalificacion;
	private String value;
	private String label;
	private String idCurso;
	private String nombreCertificado;
	private String calificacion;
	private String clave;
	
	
	public String getIdCertificadoCurso() {
		return idCertificadoCurso;
	}
	public void setIdCertificadoCurso(String idCertificadoCurso) {
		this.idCertificadoCurso = idCertificadoCurso;
	}
	public String getIdTipoProducto() {
		return idTipoProducto;
	}
	public void setIdTipoProducto(String idTipoProducto) {
		this.idTipoProducto = idTipoProducto;
	}
	public String getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}
	public String getIdProductoInstitucion() {
		return idProductoInstitucion;
	}
	public void setIdProductoInstitucion(String idProductoInstitucion) {
		this.idProductoInstitucion = idProductoInstitucion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getIdCalificacion() {
		return idCalificacion;
	}
	public void setIdCalificacion(String idCalificacion) {
		this.idCalificacion = idCalificacion;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(String idCurso) {
		this.idCurso = idCurso;
	}
	public String getNombreCertificado() {
		return nombreCertificado;
	}
	public void setNombreCertificado(String nombreCertificado) {
		this.nombreCertificado = nombreCertificado;
	}
	public String getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calificacion == null) ? 0 : calificacion.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((idCalificacion == null) ? 0 : idCalificacion.hashCode());
		result = prime * result + ((idCertificadoCurso == null) ? 0 : idCertificadoCurso.hashCode());
		result = prime * result + ((idCurso == null) ? 0 : idCurso.hashCode());
		result = prime * result + ((idProducto == null) ? 0 : idProducto.hashCode());
		result = prime * result + ((idProductoInstitucion == null) ? 0 : idProductoInstitucion.hashCode());
		result = prime * result + ((idTipoProducto == null) ? 0 : idTipoProducto.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((nombreCertificado == null) ? 0 : nombreCertificado.hashCode());
		result = prime * result + ((precio == null) ? 0 : precio.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		CertificadoCursoItem other = (CertificadoCursoItem) obj;
		if (calificacion == null) {
			if (other.calificacion != null)
				return false;
		} else if (!calificacion.equals(other.calificacion))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (idCalificacion == null) {
			if (other.idCalificacion != null)
				return false;
		} else if (!idCalificacion.equals(other.idCalificacion))
			return false;
		if (idCertificadoCurso == null) {
			if (other.idCertificadoCurso != null)
				return false;
		} else if (!idCertificadoCurso.equals(other.idCertificadoCurso))
			return false;
		if (idCurso == null) {
			if (other.idCurso != null)
				return false;
		} else if (!idCurso.equals(other.idCurso))
			return false;
		if (idProducto == null) {
			if (other.idProducto != null)
				return false;
		} else if (!idProducto.equals(other.idProducto))
			return false;
		if (idProductoInstitucion == null) {
			if (other.idProductoInstitucion != null)
				return false;
		} else if (!idProductoInstitucion.equals(other.idProductoInstitucion))
			return false;
		if (idTipoProducto == null) {
			if (other.idTipoProducto != null)
				return false;
		} else if (!idTipoProducto.equals(other.idTipoProducto))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (nombreCertificado == null) {
			if (other.nombreCertificado != null)
				return false;
		} else if (!nombreCertificado.equals(other.nombreCertificado))
			return false;
		if (precio == null) {
			if (other.precio != null)
				return false;
		} else if (!precio.equals(other.precio))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "CertificadoCursoItem [idCertificadoCurso=" + idCertificadoCurso + ", idTipoProducto=" + idTipoProducto
				+ ", idProducto=" + idProducto + ", idProductoInstitucion=" + idProductoInstitucion + ", descripcion="
				+ descripcion + ", precio=" + precio + ", idCalificacion=" + idCalificacion + ", value=" + value
				+ ", label=" + label + ", idCurso=" + idCurso + ", nombreCertificado=" + nombreCertificado
				+ ", calificacion=" + calificacion + "]";
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	
	
	
}
