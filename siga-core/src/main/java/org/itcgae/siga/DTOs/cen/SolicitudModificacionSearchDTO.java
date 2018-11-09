package org.itcgae.siga.DTOs.cen;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SolicitudModificacionSearchDTO {

	private String idPersona;
	private String idInstitucion;
	private String tipoModificacion;
	private String estado;
	private Date fechaHasta;
	private Date fechaDesde;
	
	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	@JsonProperty("tipoModificacion")
	public String getTipoModificacion() {
		return tipoModificacion;
	}
	
	public void setTipoModificacion(String tipoModificacion) {
		this.tipoModificacion = tipoModificacion;
	}
	
	@JsonProperty("estado")
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@JsonProperty("fechaHasta")
	public Date getFechaHasta() {
		return fechaHasta;
	}
	
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	
	@JsonProperty("fechaDesde")
	public Date getFechaDesde() {
		return fechaDesde;
	}
	
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaDesde == null) ? 0 : fechaDesde.hashCode());
		result = prime * result + ((fechaHasta == null) ? 0 : fechaHasta.hashCode());
		result = prime * result + ((tipoModificacion == null) ? 0 : tipoModificacion.hashCode());
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
		SolicitudModificacionSearchDTO other = (SolicitudModificacionSearchDTO) obj;
		if (idPersona == null) {
			if (other.idPersona != null)
				return false;
		} else if (!idPersona.equals(other.idPersona))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaDesde == null) {
			if (other.fechaDesde != null)
				return false;
		} else if (!fechaDesde.equals(other.fechaDesde))
			return false;
		if (fechaHasta == null) {
			if (other.fechaHasta != null)
				return false;
		} else if (!fechaHasta.equals(other.fechaHasta))
			return false;
		if (tipoModificacion == null) {
			if (other.tipoModificacion != null)
				return false;
		} else if (!tipoModificacion.equals(other.tipoModificacion))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "SolicitudModificacionSearchDTO [idPersona=" + idPersona + ", idInstitucion =" + idInstitucion + ", tipoModificacion=" + tipoModificacion + ", estado=" + estado
				+ ", fechaHasta=" + fechaHasta + ", fechaDesde=" + fechaDesde + "]";
	}
}
