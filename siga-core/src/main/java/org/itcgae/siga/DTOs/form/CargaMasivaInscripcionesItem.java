package org.itcgae.siga.DTOs.form;

/**
 * @author DTUser
 *
 */
/**
 * @author DTUser
 *
 */
public class CargaMasivaInscripcionesItem {
	
	private String idCargaInscripcion;
	private String nombreFichero;
	private String idCurso;
	private String idInstitucion;
	private String fechaCarga;
	private String numeroLineasTotales;
	private String inscripcionesCorrectas;
	private Long idFichero;
	private Long idFicheroLog;
	private String fechaBaja;
	
	public Long getIdFichero() {
		return idFichero;
	}

	public void setIdFichero(Long idFichero) {
		this.idFichero = idFichero;
	}

	public Long getIdFicheroLog() {
		return idFicheroLog;
	}

	public void setIdFicheroLog(Long idFicheroLog) {
		this.idFicheroLog = idFicheroLog;
	}

	public String getIdCargaInscripcion() {
		return idCargaInscripcion;
	}
	
	public void setIdCargaInscripcion(String idCargaInscripcion) {
		this.idCargaInscripcion = idCargaInscripcion;
	}
	
	public String getNombreFichero() {
		return nombreFichero;
	}
	
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	
	public String getIdCurso() {
		return idCurso;
	}
	
	public void setIdCurso(String idCurso) {
		this.idCurso = idCurso;
	}
	
	public String getIdInstitucion() {
		return idInstitucion;
	}
	
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
	public String getFechaCarga() {
		return fechaCarga;
	}
	
	public void setFechaCarga(String fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	
	public String getNumeroLineasTotales() {
		return numeroLineasTotales;
	}
	
	public void setNumeroLineasTotales(String numeroLineasTotales) {
		this.numeroLineasTotales = numeroLineasTotales;
	}
	
	public String getInscripcionesCorrectas() {
		return inscripcionesCorrectas;
	}
	
	public void setInscripcionesCorrectas(String inscripcionesCorrectas) {
		this.inscripcionesCorrectas = inscripcionesCorrectas;
	}
	
	
	public String getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaCarga == null) ? 0 : fechaCarga.hashCode());
		result = prime * result + ((idCurso == null) ? 0 : idCurso.hashCode());
		result = prime * result + ((idCargaInscripcion == null) ? 0 : idCargaInscripcion.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((nombreFichero == null) ? 0 : nombreFichero.hashCode());
		result = prime * result + ((inscripcionesCorrectas == null) ? 0 : inscripcionesCorrectas.hashCode());
		result = prime * result + ((numeroLineasTotales == null) ? 0 : numeroLineasTotales.hashCode());
		result = prime * result + ((fechaBaja == null) ? 0 : fechaBaja.hashCode());

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
		CargaMasivaInscripcionesItem other = (CargaMasivaInscripcionesItem) obj;
		if (fechaCarga == null) {
			if (other.fechaCarga != null)
				return false;
		} else if (!fechaCarga.equals(other.fechaCarga))
			return false;
		if (idCurso == null) {
			if (other.idCurso != null)
				return false;
		} else if (!idCurso.equals(other.idCurso))
			return false;
		if (idCargaInscripcion == null) {
			if (other.idCargaInscripcion != null)
				return false;
		} else if (!idCargaInscripcion.equals(other.idCargaInscripcion))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (nombreFichero == null) {
			if (other.nombreFichero != null)
				return false;
		} else if (!nombreFichero.equals(other.nombreFichero))
			return false;
		if (inscripcionesCorrectas == null) {
			if (other.inscripcionesCorrectas != null)
				return false;
		} else if (!inscripcionesCorrectas.equals(other.inscripcionesCorrectas))
			return false;
		if (numeroLineasTotales == null) {
			if (other.numeroLineasTotales != null)
				return false;
		} else if (!numeroLineasTotales.equals(other.numeroLineasTotales))
			return false;
		if (fechaBaja == null) {
			if (other.fechaBaja != null)
				return false;
		} else if (!fechaBaja.equals(other.fechaBaja))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "CargaMasivaInscripcionesItem [idCargaInscripcion=" + idCargaInscripcion + ", nombreFichero=" + nombreFichero
				+ ", idCurso=" + idCurso + ", idInstitucion=" + idInstitucion + ", fechaCarga=" + fechaCarga
				+ ", numeroLineasTotales=" + numeroLineasTotales + ", inscripcionesCorrectas=" + inscripcionesCorrectas
				+ ", fechaBaja=" + fechaBaja + "]";
	}

	
	
}
