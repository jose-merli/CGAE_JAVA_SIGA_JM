package org.itcgae.siga.DTOs.form;

import java.util.Arrays;

public class CursoItem {
	
	private Long idCurso;
	private String visibilidad;
	private String colegio;
	private String codigoCurso;
	private String nombreCurso;
	private String estado;
	private String plazasDisponibles;
	private String nombreApellidosFormador;
	private Double precioDesde;
	private Double precioHasta;
	private String fechaInscripcionDesde;
	private String fechaInscripcionHasta;
	private String fechaImparticionDesde;
	private String fechaImparticionHasta;
	private String [] temas;
	
	//Auxiliares pantalla
	private String idEstado;
	private String idVisibilidad;
	private String idInstitucion;
	private String descripcionEstado;
	private String precioCurso;
	private String fechaInscripcion;
	private String fechaImparticion;
	private Integer flagArchivado;
	
	public Long getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}
	public String getVisibilidad() {
		return visibilidad;
	}
	public void setVisibilidad(String visibilidad) {
		this.visibilidad = visibilidad;
	}
	public String getColegio() {
		return colegio;
	}
	public void setColegio(String colegio) {
		this.colegio = colegio;
	}
	public String getCodigoCurso() {
		return codigoCurso;
	}
	public void setCodigoCurso(String codigoCurso) {
		this.codigoCurso = codigoCurso;
	}
	public String getNombreCurso() {
		return nombreCurso;
	}
	public void setNombreCurso(String nombreCurso) {
		this.nombreCurso = nombreCurso;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getPlazasDisponibles() {
		return plazasDisponibles;
	}
	public void setPlazasDisponibles(String plazasDisponibles) {
		this.plazasDisponibles = plazasDisponibles;
	}
	public String getNombreApellidosFormador() {
		return nombreApellidosFormador;
	}
	public void setNombreApellidosFormador(String nombreApellidosFormador) {
		this.nombreApellidosFormador = nombreApellidosFormador;
	}
	public Double getPrecioDesde() {
		return precioDesde;
	}
	public void setPrecioDesde(Double precioDesde) {
		this.precioDesde = precioDesde;
	}
	public Double getPrecioHasta() {
		return precioHasta;
	}
	public void setPrecioHasta(Double precioHasta) {
		this.precioHasta = precioHasta;
	}
	public String getFechaInscripcionDesde() {
		return fechaInscripcionDesde;
	}
	public void setFechaInscripcionDesde(String fechaInscripcionDesde) {
		this.fechaInscripcionDesde = fechaInscripcionDesde;
	}
	public String getFechaInscripcionHasta() {
		return fechaInscripcionHasta;
	}
	public void setFechaInscripcionHasta(String fechaInscripcionHasta) {
		this.fechaInscripcionHasta = fechaInscripcionHasta;
	}
	public String getFechaImparticionDesde() {
		return fechaImparticionDesde;
	}
	public void setFechaImparticionDesde(String fechaImparticionDesde) {
		this.fechaImparticionDesde = fechaImparticionDesde;
	}
	public String getFechaImparticionHasta() {
		return fechaImparticionHasta;
	}
	public void setFechaImparticionHasta(String fechaImparticionHasta) {
		this.fechaImparticionHasta = fechaImparticionHasta;
	}
	public String[] getTemas() {
		return temas;
	}
	public void setTemas(String[] temas) {
		this.temas = temas;
	}
	public String getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}
	public String getIdVisibilidad() {
		return idVisibilidad;
	}
	public void setIdVisibilidad(String idVisibilidad) {
		this.idVisibilidad = idVisibilidad;
	}
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getDescripcionEstado() {
		return descripcionEstado;
	}
	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}
	public String getPrecioCurso() {
		return precioCurso;
	}
	public void setPrecioCurso(String precioCurso) {
		this.precioCurso = precioCurso;
	}
	public String getFechaInscripcion() {
		return fechaInscripcion;
	}
	public void setFechaInscripcion(String fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}
	public String getFechaImparticion() {
		return fechaImparticion;
	}
	public void setFechaImparticion(String fechaImparticion) {
		this.fechaImparticion = fechaImparticion;
	}
	public Integer getFlagArchivado() {
		return flagArchivado;
	}
	public void setFlagArchivado(Integer flagArchivado) {
		this.flagArchivado = flagArchivado;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoCurso == null) ? 0 : codigoCurso.hashCode());
		result = prime * result + ((colegio == null) ? 0 : colegio.hashCode());
		result = prime * result + ((descripcionEstado == null) ? 0 : descripcionEstado.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaImparticion == null) ? 0 : fechaImparticion.hashCode());
		result = prime * result + ((fechaImparticionDesde == null) ? 0 : fechaImparticionDesde.hashCode());
		result = prime * result + ((fechaImparticionHasta == null) ? 0 : fechaImparticionHasta.hashCode());
		result = prime * result + ((fechaInscripcion == null) ? 0 : fechaInscripcion.hashCode());
		result = prime * result + ((fechaInscripcionDesde == null) ? 0 : fechaInscripcionDesde.hashCode());
		result = prime * result + ((fechaInscripcionHasta == null) ? 0 : fechaInscripcionHasta.hashCode());
		result = prime * result + ((flagArchivado == null) ? 0 : flagArchivado.hashCode());
		result = prime * result + ((idCurso == null) ? 0 : idCurso.hashCode());
		result = prime * result + ((idEstado == null) ? 0 : idEstado.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idVisibilidad == null) ? 0 : idVisibilidad.hashCode());
		result = prime * result + ((nombreApellidosFormador == null) ? 0 : nombreApellidosFormador.hashCode());
		result = prime * result + ((nombreCurso == null) ? 0 : nombreCurso.hashCode());
		result = prime * result + ((plazasDisponibles == null) ? 0 : plazasDisponibles.hashCode());
		result = prime * result + ((precioCurso == null) ? 0 : precioCurso.hashCode());
		result = prime * result + ((precioDesde == null) ? 0 : precioDesde.hashCode());
		result = prime * result + ((precioHasta == null) ? 0 : precioHasta.hashCode());
		result = prime * result + Arrays.hashCode(temas);
		result = prime * result + ((visibilidad == null) ? 0 : visibilidad.hashCode());
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
		CursoItem other = (CursoItem) obj;
		if (codigoCurso == null) {
			if (other.codigoCurso != null)
				return false;
		} else if (!codigoCurso.equals(other.codigoCurso))
			return false;
		if (colegio == null) {
			if (other.colegio != null)
				return false;
		} else if (!colegio.equals(other.colegio))
			return false;
		if (descripcionEstado == null) {
			if (other.descripcionEstado != null)
				return false;
		} else if (!descripcionEstado.equals(other.descripcionEstado))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaImparticion == null) {
			if (other.fechaImparticion != null)
				return false;
		} else if (!fechaImparticion.equals(other.fechaImparticion))
			return false;
		if (fechaImparticionDesde == null) {
			if (other.fechaImparticionDesde != null)
				return false;
		} else if (!fechaImparticionDesde.equals(other.fechaImparticionDesde))
			return false;
		if (fechaImparticionHasta == null) {
			if (other.fechaImparticionHasta != null)
				return false;
		} else if (!fechaImparticionHasta.equals(other.fechaImparticionHasta))
			return false;
		if (fechaInscripcion == null) {
			if (other.fechaInscripcion != null)
				return false;
		} else if (!fechaInscripcion.equals(other.fechaInscripcion))
			return false;
		if (fechaInscripcionDesde == null) {
			if (other.fechaInscripcionDesde != null)
				return false;
		} else if (!fechaInscripcionDesde.equals(other.fechaInscripcionDesde))
			return false;
		if (fechaInscripcionHasta == null) {
			if (other.fechaInscripcionHasta != null)
				return false;
		} else if (!fechaInscripcionHasta.equals(other.fechaInscripcionHasta))
			return false;
		if (flagArchivado == null) {
			if (other.flagArchivado != null)
				return false;
		} else if (!flagArchivado.equals(other.flagArchivado))
			return false;
		if (idCurso == null) {
			if (other.idCurso != null)
				return false;
		} else if (!idCurso.equals(other.idCurso))
			return false;
		if (idEstado == null) {
			if (other.idEstado != null)
				return false;
		} else if (!idEstado.equals(other.idEstado))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (idVisibilidad == null) {
			if (other.idVisibilidad != null)
				return false;
		} else if (!idVisibilidad.equals(other.idVisibilidad))
			return false;
		if (nombreApellidosFormador == null) {
			if (other.nombreApellidosFormador != null)
				return false;
		} else if (!nombreApellidosFormador.equals(other.nombreApellidosFormador))
			return false;
		if (nombreCurso == null) {
			if (other.nombreCurso != null)
				return false;
		} else if (!nombreCurso.equals(other.nombreCurso))
			return false;
		if (plazasDisponibles == null) {
			if (other.plazasDisponibles != null)
				return false;
		} else if (!plazasDisponibles.equals(other.plazasDisponibles))
			return false;
		if (precioCurso == null) {
			if (other.precioCurso != null)
				return false;
		} else if (!precioCurso.equals(other.precioCurso))
			return false;
		if (precioDesde == null) {
			if (other.precioDesde != null)
				return false;
		} else if (!precioDesde.equals(other.precioDesde))
			return false;
		if (precioHasta == null) {
			if (other.precioHasta != null)
				return false;
		} else if (!precioHasta.equals(other.precioHasta))
			return false;
		if (!Arrays.equals(temas, other.temas))
			return false;
		if (visibilidad == null) {
			if (other.visibilidad != null)
				return false;
		} else if (!visibilidad.equals(other.visibilidad))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CursoItem [idCurso=" + idCurso + ", visibilidad=" + visibilidad + ", colegio=" + colegio
				+ ", codigoCurso=" + codigoCurso + ", nombreCurso=" + nombreCurso + ", estado=" + estado
				+ ", plazasDisponibles=" + plazasDisponibles + ", nombreApellidosFormador=" + nombreApellidosFormador
				+ ", precioDesde=" + precioDesde + ", precioHasta=" + precioHasta + ", fechaInscripcionDesde="
				+ fechaInscripcionDesde + ", fechaInscripcionHasta=" + fechaInscripcionHasta
				+ ", fechaImparticionDesde=" + fechaImparticionDesde + ", fechaImparticionHasta="
				+ fechaImparticionHasta + ", temas=" + Arrays.toString(temas) + ", idEstado=" + idEstado
				+ ", idVisibilidad=" + idVisibilidad + ", idInstitucion=" + idInstitucion + ", descripcionEstado="
				+ descripcionEstado + ", precioCurso=" + precioCurso + ", fechaInscripcion=" + fechaInscripcion
				+ ", fechaImparticion=" + fechaImparticion + ", flagArchivado=" + flagArchivado + "]";
	}
	
	
	
}
