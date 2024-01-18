package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FacturacionItem {

	private String idInstitucion;
	private String abreviatura;
	private String idFacturacion;
	private String idConcepto;
	private Date fechaDesde;
	private Date fechaHasta;
	private String nombre;
	private String regularizacion;
	private String desEstado;
	private String idEstado;
	private Date fechaEstado;
	private String importeTotal;
	private String importePagado;
	private String idPartidaPresupuestaria;
	private String importePendiente;
	private String prevision;
	private String visible;
	private String idGrupo;
	private String descGrupo;
	private String descConcepto;
	private String idGrupoOld;
	private String idConceptoOld;
	private String observaciones;
	private String nombreUsuModificacion;
	private String importeOficio;
	private String importeGuardia;
	private String importeSoj;
	private String importeEjg;
	private String borrarPorGrupo;
	private String borrarPorEstado;
	private boolean archivada;

	@JsonProperty("archivada")
	public boolean isArchivada() {
		return archivada;
	}

	public void setArchivada(boolean archivada) {
		this.archivada = archivada;
	}

	@JsonProperty("idGrupo")
	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	@JsonProperty("descGrupo")
	public String getDescGrupo() {
		return descGrupo;
	}

	public void setDescGrupo(String descGrupo) {
		this.descGrupo = descGrupo;
	}

	@JsonProperty("descConcepto")
	public String getDescConcepto() {
		return descConcepto;
	}

	public void setDescConcepto(String descConcepto) {
		this.descConcepto = descConcepto;
	}

	@JsonProperty("visible")
	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	@JsonProperty("prevision")
	public String getPrevision() {
		return prevision;
	}

	public void setprevision(String prevision) {
		this.prevision = prevision;
	}

	@JsonProperty("importePendiente")
	public String getImportePendiente() {
		return importePendiente;
	}

	public void setImportePendiente(String importePendiente) {
		this.importePendiente = importePendiente;
	}

	@JsonProperty("idPartidaPresupuestaria")
	public String getIdPartidaPresupuestaria() {
		return idPartidaPresupuestaria;
	}

	public void setIdPartidaPresupuestaria(String idPartidaPresupuestaria) {
		this.idPartidaPresupuestaria = idPartidaPresupuestaria;
	}

	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	@JsonProperty("abreviatura")
	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	@JsonProperty("idFacturacion")
	public String getIdFacturacion() {
		return idFacturacion;
	}

	public void setIdFacturacion(String idFacturacion) {
		this.idFacturacion = idFacturacion;
	}

	@JsonProperty("idConcepto")
	public String getIdConcepto() {
		return idConcepto;
	}

	public void setIdConcepto(String idConcepto) {
		this.idConcepto = idConcepto;
	}

	@JsonProperty("fechaDesde")
	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	@JsonProperty("fechaHasta")
	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@JsonProperty("regularizacion")
	public String getRegularizacion() {
		return regularizacion;
	}

	public void setRegularizacion(String regularizacion) {
		this.regularizacion = regularizacion;
	}

	@JsonProperty("desEstado")
	public String getDesEstado() {
		return desEstado;
	}

	public void setDesEstado(String desEstado) {
		this.desEstado = desEstado;
	}

	@JsonProperty("idEstado")
	public String getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}

	@JsonProperty("fechaEstado")
	public Date getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	@JsonProperty("importeTotal")
	public String getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(String importeTotal) {
		this.importeTotal = importeTotal;
	}

	@JsonProperty("importePagado")
	public String getImportePagado() {
		return importePagado;
	}

	public void setImportePagado(String importePagado) {
		this.importePagado = importePagado;
	}

	@JsonProperty("idConceptoOld")
	public String getIdConceptoOld() {
		return idConceptoOld;
	}

	public void setIdConceptoOld(String idConceptoOld) {
		this.idConceptoOld = idConceptoOld;
	}

	@JsonProperty("idGrupoOld")
	public String getIdGrupoOld() {
		return idGrupoOld;
	}

	public void setIdGrupoOld(String idGrupoOld) {
		this.idGrupoOld = idGrupoOld;
	}

	@JsonProperty("observaciones")
	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return the nombreUsuModificacion
	 */
	public String getNombreUsuModificacion() {
		return nombreUsuModificacion;
	}

	/**
	 * @param nombreUsuModificacion the nombreUsuModificacion to set
	 */
	public void setNombreUsuModificacion(String nombreUsuModificacion) {
		this.nombreUsuModificacion = nombreUsuModificacion;
	}

	@JsonProperty("importeOficio")
	public String getImporteOficio() {
		return importeOficio;
	}

	public void setImporteOficio(String importeOficio) {
		this.importeOficio = importeOficio;
	}

	@JsonProperty("importeGuardia")
	public String getImporteGuardia() {
		return importeGuardia;
	}

	public void setImporteGuardia(String importeGuardia) {
		this.importeGuardia = importeGuardia;
	}

	@JsonProperty("importeSoj")
	public String getImporteSoj() {
		return importeSoj;
	}

	public void setImporteSoj(String importeSoj) {
		this.importeSoj = importeSoj;
	}

	@JsonProperty("importeEjg")
	public String getImporteEjg() {
		return importeEjg;
	}

	public void setImporteEjg(String importeEjg) {
		this.importeEjg = importeEjg;
	}
	
	@JsonProperty("borrarPorGrupo")
	public String getBorrarPorGrupo() {
		return borrarPorGrupo;
	}

	public void setBorrarPorGrupo(String borrarPorGrupo) {
		this.borrarPorGrupo = borrarPorGrupo;
	}

	@JsonProperty("borrarPorEstado")
	public String getBorrarPorEstado() {
		return borrarPorEstado;
	}

	public void setBorrarPorEstado(String borrarPorEstado) {
		this.borrarPorEstado = borrarPorEstado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abreviatura == null) ? 0 : abreviatura.hashCode());
		result = prime * result + ((desEstado == null) ? 0 : desEstado.hashCode());
		result = prime * result + ((descConcepto == null) ? 0 : descConcepto.hashCode());
		result = prime * result + ((descGrupo == null) ? 0 : descGrupo.hashCode());
		result = prime * result + ((fechaDesde == null) ? 0 : fechaDesde.hashCode());
		result = prime * result + ((fechaEstado == null) ? 0 : fechaEstado.hashCode());
		result = prime * result + ((fechaHasta == null) ? 0 : fechaHasta.hashCode());
		result = prime * result + ((idConcepto == null) ? 0 : idConcepto.hashCode());
		result = prime * result + ((idConceptoOld == null) ? 0 : idConceptoOld.hashCode());
		result = prime * result + ((idEstado == null) ? 0 : idEstado.hashCode());
		result = prime * result + ((idFacturacion == null) ? 0 : idFacturacion.hashCode());
		result = prime * result + ((idGrupo == null) ? 0 : idGrupo.hashCode());
		result = prime * result + ((idGrupoOld == null) ? 0 : idGrupoOld.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idPartidaPresupuestaria == null) ? 0 : idPartidaPresupuestaria.hashCode());
		result = prime * result + ((importePagado == null) ? 0 : importePagado.hashCode());
		result = prime * result + ((importePendiente == null) ? 0 : importePendiente.hashCode());
		result = prime * result + ((importeTotal == null) ? 0 : importeTotal.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((nombreUsuModificacion == null) ? 0 : nombreUsuModificacion.hashCode());
		result = prime * result + ((observaciones == null) ? 0 : observaciones.hashCode());
		result = prime * result + ((prevision == null) ? 0 : prevision.hashCode());
		result = prime * result + ((regularizacion == null) ? 0 : regularizacion.hashCode());
		result = prime * result + ((visible == null) ? 0 : visible.hashCode());
		result = prime * result + ((importeOficio == null) ? 0 : importeOficio.hashCode());
		result = prime * result + ((importeGuardia == null) ? 0 : importeGuardia.hashCode());
		result = prime * result + ((importeSoj == null) ? 0 : importeSoj.hashCode());
		result = prime * result + ((importeEjg == null) ? 0 : importeEjg.hashCode());	
		result = prime * result + ((borrarPorGrupo == null) ? 0 : borrarPorGrupo.hashCode());
		result = prime * result + ((borrarPorEstado == null) ? 0 : borrarPorEstado.hashCode());
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
		FacturacionItem other = (FacturacionItem) obj;
		if (abreviatura == null) {
			if (other.abreviatura != null)
				return false;
		} else if (!abreviatura.equals(other.abreviatura))
			return false;
		if (desEstado == null) {
			if (other.desEstado != null)
				return false;
		} else if (!desEstado.equals(other.desEstado))
			return false;
		if (descConcepto == null) {
			if (other.descConcepto != null)
				return false;
		} else if (!descConcepto.equals(other.descConcepto))
			return false;
		if (descGrupo == null) {
			if (other.descGrupo != null)
				return false;
		} else if (!descGrupo.equals(other.descGrupo))
			return false;
		if (fechaDesde == null) {
			if (other.fechaDesde != null)
				return false;
		} else if (!fechaDesde.equals(other.fechaDesde))
			return false;
		if (fechaEstado == null) {
			if (other.fechaEstado != null)
				return false;
		} else if (!fechaEstado.equals(other.fechaEstado))
			return false;
		if (fechaHasta == null) {
			if (other.fechaHasta != null)
				return false;
		} else if (!fechaHasta.equals(other.fechaHasta))
			return false;
		if (idConcepto == null) {
			if (other.idConcepto != null)
				return false;
		} else if (!idConcepto.equals(other.idConcepto))
			return false;
		if (idConceptoOld == null) {
			if (other.idConceptoOld != null)
				return false;
		} else if (!idConceptoOld.equals(other.idConceptoOld))
			return false;
		if (idEstado == null) {
			if (other.idEstado != null)
				return false;
		} else if (!idEstado.equals(other.idEstado))
			return false;
		if (idFacturacion == null) {
			if (other.idFacturacion != null)
				return false;
		} else if (!idFacturacion.equals(other.idFacturacion))
			return false;
		if (idGrupo == null) {
			if (other.idGrupo != null)
				return false;
		} else if (!idGrupo.equals(other.idGrupo))
			return false;
		if (idGrupoOld == null) {
			if (other.idGrupoOld != null)
				return false;
		} else if (!idGrupoOld.equals(other.idGrupoOld))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (idPartidaPresupuestaria == null) {
			if (other.idPartidaPresupuestaria != null)
				return false;
		} else if (!idPartidaPresupuestaria.equals(other.idPartidaPresupuestaria))
			return false;
		if (importePagado == null) {
			if (other.importePagado != null)
				return false;
		} else if (!importePagado.equals(other.importePagado))
			return false;
		if (importePendiente == null) {
			if (other.importePendiente != null)
				return false;
		} else if (!importePendiente.equals(other.importePendiente))
			return false;
		if (importeTotal == null) {
			if (other.importeTotal != null)
				return false;
		} else if (!importeTotal.equals(other.importeTotal))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (nombreUsuModificacion == null) {
			if (other.nombreUsuModificacion != null)
				return false;
		} else if (!nombreUsuModificacion.equals(other.nombreUsuModificacion))
			return false;
		if (observaciones == null) {
			if (other.observaciones != null)
				return false;
		} else if (!observaciones.equals(other.observaciones))
			return false;
		if (prevision == null) {
			if (other.prevision != null)
				return false;
		} else if (!prevision.equals(other.prevision))
			return false;
		if (regularizacion == null) {
			if (other.regularizacion != null)
				return false;
		} else if (!regularizacion.equals(other.regularizacion))
			return false;
		if (visible == null) {
			if (other.visible != null)
				return false;
		} else if (!visible.equals(other.visible))
			return false;
		if (importeOficio == null) {
			if (other.importeOficio != null)
				return false;
		} else if (!importeOficio.equals(other.importeOficio))
			return false;
		if (importeGuardia == null) {
			if (other.importeGuardia != null)
				return false;
		} else if (!importeGuardia.equals(other.importeGuardia))
			return false;
		if (importeSoj == null) {
			if (other.importeSoj != null)
				return false;
		} else if (!importeSoj.equals(other.importeSoj))
			return false;
		if (importeEjg == null) {
			if (other.importeEjg != null)
				return false;
		} else if (!importeEjg.equals(other.importeEjg))
			return false;
		if (borrarPorGrupo == null) {
			if (other.borrarPorGrupo != null)
				return false;
		} else if (!borrarPorGrupo.equals(other.borrarPorGrupo))
			return false;
		if (borrarPorEstado == null) {
			if (other.borrarPorEstado != null)
				return false;
		} else if (!borrarPorEstado.equals(other.borrarPorEstado)) {
			return false;
		}
		if(archivada != other.archivada) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "FacturacionItem [idInstitucion=" + idInstitucion + ", abreviatura=" + abreviatura + ", idFacturacion="
				+ idFacturacion + ", idConcepto=" + idConcepto + ", fechaDesde=" + fechaDesde + ", fechaHasta="
				+ fechaHasta + ", nombre=" + nombre + ", regularizacion=" + regularizacion + ", desEstado=" + desEstado
				+ ", idEstado=" + idEstado + ", fechaEstado=" + fechaEstado + ", importeTotal=" + importeTotal
				+ ", importePagado=" + importePagado + ", idPartidaPresupuestaria=" + idPartidaPresupuestaria
				+ ", importePendiente=" + importePendiente + ", prevision=" + prevision + ", visible=" + visible
				+ ", idGrupo=" + idGrupo + ", descGrupo=" + descGrupo + ", descConcepto=" + descConcepto
				+ ", idGrupoOld=" + idGrupoOld + ", idConceptoOld=" + idConceptoOld + ", observaciones=" + observaciones
				+ ", nombreUsuModificacion=" + nombreUsuModificacion
				+ ", importeOficio=" + importeOficio
				+ ", importeGuardia=" + importeGuardia
				+ ", importeSoj=" + importeSoj
				+ ", importeEjg=" + importeEjg
				+ ", borrarPorGrupo=" + borrarPorGrupo
				+ ", borrarPorEstado=" + borrarPorEstado+", archivada=" + (archivada ? "true": "false") + "]";
	}
}
