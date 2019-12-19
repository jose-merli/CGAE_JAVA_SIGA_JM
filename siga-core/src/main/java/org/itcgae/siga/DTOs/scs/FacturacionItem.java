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
		result = prime * result + ((idEstado == null) ? 0 : idEstado.hashCode());
		result = prime * result + ((idFacturacion == null) ? 0 : idFacturacion.hashCode());
		result = prime * result + ((idGrupo == null) ? 0 : idGrupo.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idPartidaPresupuestaria == null) ? 0 : idPartidaPresupuestaria.hashCode());
		result = prime * result + ((importePagado == null) ? 0 : importePagado.hashCode());
		result = prime * result + ((importePendiente == null) ? 0 : importePendiente.hashCode());
		result = prime * result + ((importeTotal == null) ? 0 : importeTotal.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((prevision == null) ? 0 : prevision.hashCode());
		result = prime * result + ((regularizacion == null) ? 0 : regularizacion.hashCode());
		result = prime * result + ((visible == null) ? 0 : visible.hashCode());
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
				+ ", idGrupo=" + idGrupo + ", descGrupo=" + descGrupo + ", descConcepto=" + descConcepto + "]";
	}	
}
