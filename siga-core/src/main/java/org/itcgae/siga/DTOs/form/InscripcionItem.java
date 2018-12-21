package org.itcgae.siga.DTOs.form;

import java.util.Arrays;
import java.util.Date;

/**
 * @author DTUser
 *
 */
/**
 * @author DTUser
 *
 */
public class InscripcionItem {
	
	private Long idInscripcion;
	private String visibilidad;
	private String colegio;
	private String codigoCurso;
	private String nombreCurso;
	private String estadoCurso;
	private String estadoInscripcion;
	private Date fechaInscripcionDesde;
	private Date fechaInscripcionHasta;
	private Date fechaImparticionDesde;
	private Date fechaImparticionHasta;
	private Long minimaAsistencia;
	private String [] temas;
	private Double calificacion;
	private Long pagada;
	private Long idFormador;
	private Long certificadoEmitido;
	private Long idCalificacion;
	private String precioCurso;
	private String fechaSolicitud;
	private Long idPersona;
	private String nombrePersona;
	
	// 0 --> Aprobar
	// 1 --> Cancelar
	// 2 --> Rechazar
	private Short tipoAccion;
	private String motivo;
	
	//Auxiliares pantalla
	private String idEstadoCurso;
	private String idEstadoInscripcion;
	private String idVisibilidad;
	private String idInstitucion;
	private String descripcionEstado;
	private String fechaInscripcion;
	private String fechaImparticion;
	
	private String pendientes;
	private String rechazadas;
	private String aprobadas;
	private String canceladas;
	private String totales;
	private String formaPago;
	
	private String idCurso;
	
	//Auxiliares para filtros de fechas
	private String fechaInscripcionDesdeFormat;
	private String fechaInscripcionHastaFormat;
	private String fechaImparticionDesdeFormat;
	private String fechaImparticionHastaFormat;
	
	private String errores;

	public Long getIdInscripcion() {
		return idInscripcion;
	}

	public void setIdInscripcion(Long idInscripcion) {
		this.idInscripcion = idInscripcion;
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

	public String getEstadoCurso() {
		return estadoCurso;
	}

	public void setEstadoCurso(String estadoCurso) {
		this.estadoCurso = estadoCurso;
	}
	
	public String getFechaInscripcionDesdeFormat() {
		return fechaInscripcionDesdeFormat;
	}

	public void setFechaInscripcionDesdeFormat(String fechaInscripcionDesdeFormat) {
		this.fechaInscripcionDesdeFormat = fechaInscripcionDesdeFormat;
	}

	public String getFechaInscripcionHastaFormat() {
		return fechaInscripcionHastaFormat;
	}

	public void setFechaInscripcionHastaFormat(String fechaInscripcionHastaFormat) {
		this.fechaInscripcionHastaFormat = fechaInscripcionHastaFormat;
	}

	public String getFechaImparticionDesdeFormat() {
		return fechaImparticionDesdeFormat;
	}

	public void setFechaImparticionDesdeFormat(String fechaImparticionDesdeFormat) {
		this.fechaImparticionDesdeFormat = fechaImparticionDesdeFormat;
	}

	public String getFechaImparticionHastaFormat() {
		return fechaImparticionHastaFormat;
	}

	public void setFechaImparticionHastaFormat(String fechaImparticionHastaFormat) {
		this.fechaImparticionHastaFormat = fechaImparticionHastaFormat;
	}

	public Date getFechaInscripcionDesde() {
		return fechaInscripcionDesde;
	}

	public void setFechaInscripcionDesde(Date fechaInscripcionDesde) {
		this.fechaInscripcionDesde = fechaInscripcionDesde;
	}

	public Date getFechaInscripcionHasta() {
		return fechaInscripcionHasta;
	}

	public void setFechaInscripcionHasta(Date fechaInscripcionHasta) {
		this.fechaInscripcionHasta = fechaInscripcionHasta;
	}

	public Date getFechaImparticionDesde() {
		return fechaImparticionDesde;
	}

	public void setFechaImparticionDesde(Date fechaImparticionDesde) {
		this.fechaImparticionDesde = fechaImparticionDesde;
	}

	public Date getFechaImparticionHasta() {
		return fechaImparticionHasta;
	}

	public void setFechaImparticionHasta(Date fechaImparticionHasta) {
		this.fechaImparticionHasta = fechaImparticionHasta;
	}

	public String[] getTemas() {
		return temas;
	}

	public void setTemas(String[] temas) {
		this.temas = temas;
	}

	public String getIdEstadoCurso() {
		return idEstadoCurso;
	}

	public void setIdEstadoCurso(String idEstadoCurso) {
		this.idEstadoCurso = idEstadoCurso;
	}

	public String getIdEstadoInscripcion() {
		return idEstadoInscripcion;
	}

	public void setIdEstadoInscripcion(String idEstadoInscripcion) {
		this.idEstadoInscripcion = idEstadoInscripcion;
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
	
	public String getEstadoInscripcion() {
		return estadoInscripcion;
	}

	public void setEstadoInscripcion(String estadoInscripcion) {
		this.estadoInscripcion = estadoInscripcion;
	}
	
	public Long getMinimaAsistencia() {
		return minimaAsistencia;
	}

	public void setMinimaAsistencia(Long minimaAsistencia) {
		this.minimaAsistencia = minimaAsistencia;
	}

	public Double getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Double calificacion) {
		this.calificacion = calificacion;
	}
	
	public Long getPagada() {
		return pagada;
	}

	public void setPagada(Long pagada) {
		this.pagada = pagada;
	}

	public Long getIdFormador() {
		return idFormador;
	}

	public void setIdFormador(Long idFormador) {
		this.idFormador = idFormador;
	}

	public Long getCertificadoEmitido() {
		return certificadoEmitido;
	}

	public void setCertificadoEmitido(Long certificadoEmitido) {
		this.certificadoEmitido = certificadoEmitido;
	}

	public Long getIdCalificacion() {
		return idCalificacion;
	}

	public void setIdCalificacion(Long idCalificacion) {
		this.idCalificacion = idCalificacion;
	}
	
	public String getPrecioCurso() {
		return precioCurso;
	}

	public void setPrecioCurso(String precioCurso) {
		this.precioCurso = precioCurso;
	}

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	
	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public Short getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(Short tipoAccion) {
		this.tipoAccion = tipoAccion;
	}
	
	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getPendientes() {
		return pendientes;
	}

	public void setPendientes(String pendientes) {
		this.pendientes = pendientes;
	}

	public String getRechazadas() {
		return rechazadas;
	}

	public void setRechazadas(String rechazadas) {
		this.rechazadas = rechazadas;
	}

	public String getAprobadas() {
		return aprobadas;
	}

	public void setAprobadas(String aprobadas) {
		this.aprobadas = aprobadas;
	}

	public String getCanceladas() {
		return canceladas;
	}

	public void setCanceladas(String canceladas) {
		this.canceladas = canceladas;
	}

	public String getTotales() {
		return totales;
	}

	public void setTotales(String totales) {
		this.totales = totales;
	}

	public String getNombrePersona() {
		return nombrePersona;
	}

	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	
	public String getErrores() {
		return errores;
	}

	public void setErrores(String errores) {
		this.errores = errores;
	}
	
	public String getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(String idCurso) {
		this.idCurso = idCurso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calificacion == null) ? 0 : calificacion.hashCode());
		result = prime * result + ((certificadoEmitido == null) ? 0 : certificadoEmitido.hashCode());
		result = prime * result + ((codigoCurso == null) ? 0 : codigoCurso.hashCode());
		result = prime * result + ((colegio == null) ? 0 : colegio.hashCode());
		result = prime * result + ((descripcionEstado == null) ? 0 : descripcionEstado.hashCode());
		result = prime * result + ((estadoCurso == null) ? 0 : estadoCurso.hashCode());
		result = prime * result + ((estadoInscripcion == null) ? 0 : estadoInscripcion.hashCode());
		result = prime * result + ((fechaImparticion == null) ? 0 : fechaImparticion.hashCode());
		result = prime * result + ((fechaImparticionDesde == null) ? 0 : fechaImparticionDesde.hashCode());
		result = prime * result + ((fechaImparticionDesdeFormat == null) ? 0 : fechaImparticionDesdeFormat.hashCode());
		result = prime * result + ((fechaImparticionHasta == null) ? 0 : fechaImparticionHasta.hashCode());
		result = prime * result + ((fechaImparticionHastaFormat == null) ? 0 : fechaImparticionHastaFormat.hashCode());
		result = prime * result + ((fechaInscripcion == null) ? 0 : fechaInscripcion.hashCode());
		result = prime * result + ((fechaInscripcionDesde == null) ? 0 : fechaInscripcionDesde.hashCode());
		result = prime * result + ((fechaInscripcionDesdeFormat == null) ? 0 : fechaInscripcionDesdeFormat.hashCode());
		result = prime * result + ((fechaInscripcionHasta == null) ? 0 : fechaInscripcionHasta.hashCode());
		result = prime * result + ((fechaInscripcionHastaFormat == null) ? 0 : fechaInscripcionHastaFormat.hashCode());
		result = prime * result + ((fechaSolicitud == null) ? 0 : fechaSolicitud.hashCode());
		result = prime * result + ((idCalificacion == null) ? 0 : idCalificacion.hashCode());
		result = prime * result + ((idEstadoCurso == null) ? 0 : idEstadoCurso.hashCode());
		result = prime * result + ((idEstadoInscripcion == null) ? 0 : idEstadoInscripcion.hashCode());
		result = prime * result + ((idFormador == null) ? 0 : idFormador.hashCode());
		result = prime * result + ((idInscripcion == null) ? 0 : idInscripcion.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
		result = prime * result + ((idVisibilidad == null) ? 0 : idVisibilidad.hashCode());
		result = prime * result + ((minimaAsistencia == null) ? 0 : minimaAsistencia.hashCode());
		result = prime * result + ((motivo == null) ? 0 : motivo.hashCode());
		result = prime * result + ((nombreCurso == null) ? 0 : nombreCurso.hashCode());
		result = prime * result + ((pagada == null) ? 0 : pagada.hashCode());
		result = prime * result + ((precioCurso == null) ? 0 : precioCurso.hashCode());
		result = prime * result + Arrays.hashCode(temas);
		result = prime * result + ((tipoAccion == null) ? 0 : tipoAccion.hashCode());
		result = prime * result + ((visibilidad == null) ? 0 : visibilidad.hashCode());
		result = prime * result + ((pendientes == null) ? 0 : pendientes.hashCode());
		result = prime * result + ((rechazadas == null) ? 0 : rechazadas.hashCode());
		result = prime * result + ((aprobadas == null) ? 0 : aprobadas.hashCode());
		result = prime * result + ((canceladas == null) ? 0 : canceladas.hashCode());
		result = prime * result + ((totales == null) ? 0 : totales.hashCode());
		result = prime * result + ((nombrePersona == null) ? 0 : nombrePersona.hashCode());
		result = prime * result + ((formaPago == null) ? 0 : formaPago.hashCode());
		result = prime * result + ((errores == null) ? 0 : errores.hashCode());
		result = prime * result + ((idCurso == null) ? 0 : idCurso.hashCode());
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
		InscripcionItem other = (InscripcionItem) obj;
		if (calificacion == null) {
			if (other.calificacion != null)
				return false;
		} else if (!calificacion.equals(other.calificacion))
			return false;
		if (certificadoEmitido == null) {
			if (other.certificadoEmitido != null)
				return false;
		} else if (!certificadoEmitido.equals(other.certificadoEmitido))
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
		if (estadoCurso == null) {
			if (other.estadoCurso != null)
				return false;
		} else if (!estadoCurso.equals(other.estadoCurso))
			return false;
		if (estadoInscripcion == null) {
			if (other.estadoInscripcion != null)
				return false;
		} else if (!estadoInscripcion.equals(other.estadoInscripcion))
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
		if (fechaImparticionDesdeFormat == null) {
			if (other.fechaImparticionDesdeFormat != null)
				return false;
		} else if (!fechaImparticionDesdeFormat.equals(other.fechaImparticionDesdeFormat))
			return false;
		if (fechaImparticionHasta == null) {
			if (other.fechaImparticionHasta != null)
				return false;
		} else if (!fechaImparticionHasta.equals(other.fechaImparticionHasta))
			return false;
		if (fechaImparticionHastaFormat == null) {
			if (other.fechaImparticionHastaFormat != null)
				return false;
		} else if (!fechaImparticionHastaFormat.equals(other.fechaImparticionHastaFormat))
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
		if (fechaInscripcionDesdeFormat == null) {
			if (other.fechaInscripcionDesdeFormat != null)
				return false;
		} else if (!fechaInscripcionDesdeFormat.equals(other.fechaInscripcionDesdeFormat))
			return false;
		if (fechaInscripcionHasta == null) {
			if (other.fechaInscripcionHasta != null)
				return false;
		} else if (!fechaInscripcionHasta.equals(other.fechaInscripcionHasta))
			return false;
		if (fechaInscripcionHastaFormat == null) {
			if (other.fechaInscripcionHastaFormat != null)
				return false;
		} else if (!fechaInscripcionHastaFormat.equals(other.fechaInscripcionHastaFormat))
			return false;
		if (fechaSolicitud == null) {
			if (other.fechaSolicitud != null)
				return false;
		} else if (!fechaSolicitud.equals(other.fechaSolicitud))
			return false;
		if (idCalificacion == null) {
			if (other.idCalificacion != null)
				return false;
		} else if (!idCalificacion.equals(other.idCalificacion))
			return false;
		if (idEstadoCurso == null) {
			if (other.idEstadoCurso != null)
				return false;
		} else if (!idEstadoCurso.equals(other.idEstadoCurso))
			return false;
		if (idEstadoInscripcion == null) {
			if (other.idEstadoInscripcion != null)
				return false;
		} else if (!idEstadoInscripcion.equals(other.idEstadoInscripcion))
			return false;
		if (idFormador == null) {
			if (other.idFormador != null)
				return false;
		} else if (!idFormador.equals(other.idFormador))
			return false;
		if (idInscripcion == null) {
			if (other.idInscripcion != null)
				return false;
		} else if (!idInscripcion.equals(other.idInscripcion))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (idPersona == null) {
			if (other.idPersona != null)
				return false;
		} else if (!idPersona.equals(other.idPersona))
			return false;
		if (idVisibilidad == null) {
			if (other.idVisibilidad != null)
				return false;
		} else if (!idVisibilidad.equals(other.idVisibilidad))
			return false;
		if (minimaAsistencia == null) {
			if (other.minimaAsistencia != null)
				return false;
		} else if (!minimaAsistencia.equals(other.minimaAsistencia))
			return false;
		if (motivo == null) {
			if (other.motivo != null)
				return false;
		} else if (!motivo.equals(other.motivo))
			return false;
		if (nombreCurso == null) {
			if (other.nombreCurso != null)
				return false;
		} else if (!nombreCurso.equals(other.nombreCurso))
			return false;
		if (pagada == null) {
			if (other.pagada != null)
				return false;
		} else if (!pagada.equals(other.pagada))
			return false;
		if (precioCurso == null) {
			if (other.precioCurso != null)
				return false;
		} else if (!precioCurso.equals(other.precioCurso))
			return false;
		if (!Arrays.equals(temas, other.temas))
			return false;
		if (tipoAccion == null) {
			if (other.tipoAccion != null)
				return false;
		} else if (!tipoAccion.equals(other.tipoAccion))
			return false;
		if (visibilidad == null) {
			if (other.visibilidad != null)
				return false;
		} else if (!visibilidad.equals(other.visibilidad))
			return false;
		if (pendientes == null) {
			if (other.pendientes != null)
				return false;
		} else if (!pendientes.equals(other.pendientes))
			return false;
		if (rechazadas == null) {
			if (other.rechazadas != null)
				return false;
		} else if (!rechazadas.equals(other.rechazadas))
			return false;
		if (aprobadas == null) {
			if (other.aprobadas != null)
				return false;
		} else if (!aprobadas.equals(other.aprobadas))
			return false;
		if (canceladas == null) {
			if (other.canceladas != null)
				return false;
		} else if (!canceladas.equals(other.canceladas))
			return false;
		if (totales == null) {
			if (other.totales != null)
				return false;
		} else if (!totales.equals(other.totales))
			return false;
		if (nombrePersona == null) {
			if (other.nombrePersona != null)
				return false;
		} else if (!nombrePersona.equals(other.nombrePersona))
			return false;
		if (formaPago == null) {
			if (other.formaPago != null)
				return false;
		} else if (!formaPago.equals(other.formaPago))
			return false;
		if (errores == null) {
			if (other.errores != null)
				return false;
		} else if (!errores.equals(other.errores))
			return false;
		if (idCurso == null) {
			if (other.idCurso != null)
				return false;
		} else if (!idCurso.equals(other.idCurso))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "InscripcionItem [idInscripcion=" + idInscripcion + ", visibilidad=" + visibilidad + ", colegio="
				+ colegio + ", codigoCurso=" + codigoCurso + ", nombreCurso=" + nombreCurso + ", estadoCurso="
				+ estadoCurso + ", estadoInscripcion=" + estadoInscripcion + ", fechaInscripcionDesde="
				+ fechaInscripcionDesde + ", fechaInscripcionHasta=" + fechaInscripcionHasta
				+ ", fechaImparticionDesde=" + fechaImparticionDesde + ", fechaImparticionHasta="
				+ fechaImparticionHasta + ", minimaAsistencia=" + minimaAsistencia + ", temas=" + Arrays.toString(temas)
				+ ", calificacion=" + calificacion + ", pagada=" + pagada + ", idFormador=" + idFormador
				+ ", certificadoEmitido=" + certificadoEmitido + ", idCalificacion=" + idCalificacion + ", precioCurso="
				+ precioCurso + ", fechaSolicitud=" + fechaSolicitud + ", idEstadoCurso=" + idEstadoCurso
				+ ", idEstadoInscripcion=" + idEstadoInscripcion + ", idVisibilidad=" + idVisibilidad
				+ ", idInstitucion=" + idInstitucion + ", descripcionEstado=" + descripcionEstado
				+ ", fechaInscripcion=" + fechaInscripcion + ", fechaImparticion=" + fechaImparticion + ", pendientes="
				+ pendientes + ", rechazadas=" + rechazadas + ", aceptadas=" + aprobadas + ", canceladas=" + canceladas
				+ ", totales=" + totales + ", fechaInscripcionDesdeFormat=" + fechaInscripcionDesdeFormat
				+ ", fechaInscripcionHastaFormat=" + fechaInscripcionHastaFormat + ", fechaImparticionDesdeFormat="
				+ fechaImparticionDesdeFormat + ", fechaImparticionHastaFormat=" + fechaImparticionHastaFormat
				+ ", nombrePersona=" + nombrePersona + ", formaPago=" + formaPago + ", errores=" + errores + ", idCurso=" + idCurso  + "]";

	}
	
	
}
