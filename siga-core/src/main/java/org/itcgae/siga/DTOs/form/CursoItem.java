package org.itcgae.siga.DTOs.form;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.itcgae.siga.DTOs.gen.ComboItem;

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
	private Date fechaInscripcionDesdeDate;
	private Date fechaInscripcionHastaDate;
	private Date fechaImparticionDesdeDate;
	private Date fechaImparticionHastaDate;
	private String fechaInscripcionDesde;
	private String fechaInscripcionHasta;
	private String fechaImparticionDesde;
	private String fechaImparticionHasta;
	private String minimoAsistencia;
	private String lugar;
	private Integer autovalidacionInscripcion;
	private Integer flagArchivado;
	private String adicional;
	private String encuesta;
	private String adjunto;
	
	//Auxiliares pantalla
	private String idEstado;
	private String idVisibilidad;
	private String idInstitucion;
	private String descripcionEstado;
	private String precioCurso;
	private String fechaInscripcion;
	private String fechaImparticion;
	private String idEventoInicioInscripcion;
	private String idEventoFinInscripcion;
	private String idTipoEvento;
	

	private String tipoServicios;
	private List<ComboItem> temasCombo;
	private String [] temas;
	private String numPlazas;
	private String inscripciones;
	private Long numeroSesiones;
	
	private String numTemas;
	
	
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
	public Date getFechaInscripcionDesdeDate() {
		return fechaInscripcionDesdeDate;
	}
	public void setFechaInscripcionDesdeDate(Date fechaInscripcionDesdeDate) {
		this.fechaInscripcionDesdeDate = fechaInscripcionDesdeDate;
	}
	public Date getFechaInscripcionHastaDate() {
		return fechaInscripcionHastaDate;
	}
	public void setFechaInscripcionHastaDate(Date fechaInscripcionHastaDate) {
		this.fechaInscripcionHastaDate = fechaInscripcionHastaDate;
	}
	public Date getFechaImparticionDesdeDate() {
		return fechaImparticionDesdeDate;
	}
	public void setFechaImparticionDesdeDate(Date fechaImparticionDesdeDate) {
		this.fechaImparticionDesdeDate = fechaImparticionDesdeDate;
	}
	public Date getFechaImparticionHastaDate() {
		return fechaImparticionHastaDate;
	}
	public void setFechaImparticionHastaDate(Date fechaImparticionHastaDate) {
		this.fechaImparticionHastaDate = fechaImparticionHastaDate;
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
	public List<ComboItem> getTemasCombo() {
		return temasCombo;
	}
	public void setTemasCombo(List<ComboItem> temasCombo) {
		this.temasCombo = temasCombo;
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
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String getMinimoAsistencia() {
		return minimoAsistencia;
	}
	public void setMinimoAsistencia(String minimoAsistencia) {
		this.minimoAsistencia = minimoAsistencia;
	}
	public Integer getAutovalidacionInscripcion() {
		return autovalidacionInscripcion;
	}
	public void setAutovalidacionInscripcion(Integer autovalidacionInscripcion) {
		this.autovalidacionInscripcion = autovalidacionInscripcion;
	}
	public String getIdEventoInicioInscripcion() {
		return idEventoInicioInscripcion;
	}
	public void setIdEventoInicioInscripcion(String idEventoInicioInscripcion) {
		this.idEventoInicioInscripcion = idEventoInicioInscripcion;
	}
	public String getIdEventoFinInscripcion() {
		return idEventoFinInscripcion;
	}
	public void setIdEventoFinInscripcion(String idEventoFinInscripcion) {
		this.idEventoFinInscripcion = idEventoFinInscripcion;
	}
	public String getIdTipoEvento() {
		return idTipoEvento;
	}
	public void setIdTipoEvento(String idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}	
	public String getTipoServicios() {
		return tipoServicios;
	}
	public void setTipoServicios(String tipoServicios) {
		this.tipoServicios = tipoServicios;
	}
	
	public String getNumPlazas() {
		return numPlazas;
	}
	public void setNumPlazas(String numPlazas) {
		this.numPlazas = numPlazas;
	}
	public String getInscripciones() {
		return inscripciones;
	}
	public void setInscripciones(String inscripciones) {
		this.inscripciones = inscripciones;
	}
	public String getAdicional() {
		return adicional;
	}
	public void setAdicional(String adicional) {
		this.adicional = adicional;
	}
	public String getEncuesta() {
		return encuesta;
	}
	public void setEncuesta(String encuesta) {
		this.encuesta = encuesta;
	}
	public String getAdjunto() {
		return adjunto;
	}
	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
	}
	public String getNumTemas() {
		return numTemas;
	}
	public void setNumTemas(String numTemas) {
		this.numTemas = numTemas;
	}
	public Long getNumeroSesiones() {
		return numeroSesiones;
	}
	public void setNumeroSesiones(Long numeroSesiones) {
		this.numeroSesiones = numeroSesiones;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adicional == null) ? 0 : adicional.hashCode());
		result = prime * result + ((adjunto == null) ? 0 : adjunto.hashCode());
		result = prime * result + ((autovalidacionInscripcion == null) ? 0 : autovalidacionInscripcion.hashCode());
		result = prime * result + ((codigoCurso == null) ? 0 : codigoCurso.hashCode());
		result = prime * result + ((colegio == null) ? 0 : colegio.hashCode());
		result = prime * result + ((descripcionEstado == null) ? 0 : descripcionEstado.hashCode());
		result = prime * result + ((encuesta == null) ? 0 : encuesta.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaImparticion == null) ? 0 : fechaImparticion.hashCode());
		result = prime * result + ((fechaImparticionDesde == null) ? 0 : fechaImparticionDesde.hashCode());
		result = prime * result + ((fechaImparticionDesdeDate == null) ? 0 : fechaImparticionDesdeDate.hashCode());
		result = prime * result + ((fechaImparticionHasta == null) ? 0 : fechaImparticionHasta.hashCode());
		result = prime * result + ((fechaImparticionHastaDate == null) ? 0 : fechaImparticionHastaDate.hashCode());
		result = prime * result + ((fechaInscripcion == null) ? 0 : fechaInscripcion.hashCode());
		result = prime * result + ((fechaInscripcionDesde == null) ? 0 : fechaInscripcionDesde.hashCode());
		result = prime * result + ((fechaInscripcionDesdeDate == null) ? 0 : fechaInscripcionDesdeDate.hashCode());
		result = prime * result + ((fechaInscripcionHasta == null) ? 0 : fechaInscripcionHasta.hashCode());
		result = prime * result + ((fechaInscripcionHastaDate == null) ? 0 : fechaInscripcionHastaDate.hashCode());
		result = prime * result + ((flagArchivado == null) ? 0 : flagArchivado.hashCode());
		result = prime * result + ((idCurso == null) ? 0 : idCurso.hashCode());
		result = prime * result + ((idEstado == null) ? 0 : idEstado.hashCode());
		result = prime * result + ((idEventoFinInscripcion == null) ? 0 : idEventoFinInscripcion.hashCode());
		result = prime * result + ((idEventoInicioInscripcion == null) ? 0 : idEventoInicioInscripcion.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idTipoEvento == null) ? 0 : idTipoEvento.hashCode());
		result = prime * result + ((idVisibilidad == null) ? 0 : idVisibilidad.hashCode());
		result = prime * result + ((inscripciones == null) ? 0 : inscripciones.hashCode());
		result = prime * result + ((lugar == null) ? 0 : lugar.hashCode());
		result = prime * result + ((minimoAsistencia == null) ? 0 : minimoAsistencia.hashCode());
		result = prime * result + ((nombreApellidosFormador == null) ? 0 : nombreApellidosFormador.hashCode());
		result = prime * result + ((nombreCurso == null) ? 0 : nombreCurso.hashCode());
		result = prime * result + ((numPlazas == null) ? 0 : numPlazas.hashCode());
		result = prime * result + ((numTemas == null) ? 0 : numTemas.hashCode());
		result = prime * result + ((numeroSesiones == null) ? 0 : numeroSesiones.hashCode());
		result = prime * result + ((plazasDisponibles == null) ? 0 : plazasDisponibles.hashCode());
		result = prime * result + ((precioCurso == null) ? 0 : precioCurso.hashCode());
		result = prime * result + ((precioDesde == null) ? 0 : precioDesde.hashCode());
		result = prime * result + ((precioHasta == null) ? 0 : precioHasta.hashCode());
		result = prime * result + Arrays.hashCode(temas);
		result = prime * result + ((temasCombo == null) ? 0 : temasCombo.hashCode());
		result = prime * result + ((tipoServicios == null) ? 0 : tipoServicios.hashCode());
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
		if (adicional == null) {
			if (other.adicional != null)
				return false;
		} else if (!adicional.equals(other.adicional))
			return false;
		if (adjunto == null) {
			if (other.adjunto != null)
				return false;
		} else if (!adjunto.equals(other.adjunto))
			return false;
		if (autovalidacionInscripcion == null) {
			if (other.autovalidacionInscripcion != null)
				return false;
		} else if (!autovalidacionInscripcion.equals(other.autovalidacionInscripcion))
			return false;
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
		if (encuesta == null) {
			if (other.encuesta != null)
				return false;
		} else if (!encuesta.equals(other.encuesta))
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
		if (fechaImparticionDesdeDate == null) {
			if (other.fechaImparticionDesdeDate != null)
				return false;
		} else if (!fechaImparticionDesdeDate.equals(other.fechaImparticionDesdeDate))
			return false;
		if (fechaImparticionHasta == null) {
			if (other.fechaImparticionHasta != null)
				return false;
		} else if (!fechaImparticionHasta.equals(other.fechaImparticionHasta))
			return false;
		if (fechaImparticionHastaDate == null) {
			if (other.fechaImparticionHastaDate != null)
				return false;
		} else if (!fechaImparticionHastaDate.equals(other.fechaImparticionHastaDate))
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
		if (fechaInscripcionDesdeDate == null) {
			if (other.fechaInscripcionDesdeDate != null)
				return false;
		} else if (!fechaInscripcionDesdeDate.equals(other.fechaInscripcionDesdeDate))
			return false;
		if (fechaInscripcionHasta == null) {
			if (other.fechaInscripcionHasta != null)
				return false;
		} else if (!fechaInscripcionHasta.equals(other.fechaInscripcionHasta))
			return false;
		if (fechaInscripcionHastaDate == null) {
			if (other.fechaInscripcionHastaDate != null)
				return false;
		} else if (!fechaInscripcionHastaDate.equals(other.fechaInscripcionHastaDate))
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
		if (idEventoFinInscripcion == null) {
			if (other.idEventoFinInscripcion != null)
				return false;
		} else if (!idEventoFinInscripcion.equals(other.idEventoFinInscripcion))
			return false;
		if (idEventoInicioInscripcion == null) {
			if (other.idEventoInicioInscripcion != null)
				return false;
		} else if (!idEventoInicioInscripcion.equals(other.idEventoInicioInscripcion))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (idTipoEvento == null) {
			if (other.idTipoEvento != null)
				return false;
		} else if (!idTipoEvento.equals(other.idTipoEvento))
			return false;
		if (idVisibilidad == null) {
			if (other.idVisibilidad != null)
				return false;
		} else if (!idVisibilidad.equals(other.idVisibilidad))
			return false;
		if (inscripciones == null) {
			if (other.inscripciones != null)
				return false;
		} else if (!inscripciones.equals(other.inscripciones))
			return false;
		if (lugar == null) {
			if (other.lugar != null)
				return false;
		} else if (!lugar.equals(other.lugar))
			return false;
		if (minimoAsistencia == null) {
			if (other.minimoAsistencia != null)
				return false;
		} else if (!minimoAsistencia.equals(other.minimoAsistencia))
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
		if (numPlazas == null) {
			if (other.numPlazas != null)
				return false;
		} else if (!numPlazas.equals(other.numPlazas))
			return false;
		if (numTemas == null) {
			if (other.numTemas != null)
				return false;
		} else if (!numTemas.equals(other.numTemas))
			return false;
		if (numeroSesiones == null) {
			if (other.numeroSesiones != null)
				return false;
		} else if (!numeroSesiones.equals(other.numeroSesiones))
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
		if (temasCombo == null) {
			if (other.temasCombo != null)
				return false;
		} else if (!temasCombo.equals(other.temasCombo))
			return false;
		if (tipoServicios == null) {
			if (other.tipoServicios != null)
				return false;
		} else if (!tipoServicios.equals(other.tipoServicios))
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
				+ ", precioDesde=" + precioDesde + ", precioHasta=" + precioHasta + ", fechaInscripcionDesdeDate="
				+ fechaInscripcionDesdeDate + ", fechaInscripcionHastaDate=" + fechaInscripcionHastaDate
				+ ", fechaImparticionDesdeDate=" + fechaImparticionDesdeDate + ", fechaImparticionHastaDate="
				+ fechaImparticionHastaDate + ", fechaInscripcionDesde=" + fechaInscripcionDesde
				+ ", fechaInscripcionHasta=" + fechaInscripcionHasta + ", fechaImparticionDesde="
				+ fechaImparticionDesde + ", fechaImparticionHasta=" + fechaImparticionHasta + ", minimoAsistencia="
				+ minimoAsistencia + ", lugar=" + lugar + ", autovalidacionInscripcion=" + autovalidacionInscripcion
				+ ", flagArchivado=" + flagArchivado + ", adicional=" + adicional + ", encuesta=" + encuesta
				+ ", adjunto=" + adjunto + ", idEstado=" + idEstado + ", idVisibilidad=" + idVisibilidad
				+ ", idInstitucion=" + idInstitucion + ", descripcionEstado=" + descripcionEstado + ", precioCurso="
				+ precioCurso + ", fechaInscripcion=" + fechaInscripcion + ", fechaImparticion=" + fechaImparticion
				+ ", idEventoInicioInscripcion=" + idEventoInicioInscripcion + ", idEventoFinInscripcion="
				+ idEventoFinInscripcion + ", idTipoEvento=" + idTipoEvento + ", tipoServicios=" + tipoServicios
				+ ", temasCombo=" + temasCombo + ", temas=" + Arrays.toString(temas) + ", numPlazas=" + numPlazas
				+ ", inscripciones=" + inscripciones + ", numeroSesiones=" + numeroSesiones + ", numTemas=" + numTemas
				+ "]";
	}

	
}
