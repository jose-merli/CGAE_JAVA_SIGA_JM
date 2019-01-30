package org.itcgae.siga.DTOs.form;

import java.util.Date;

/**
 * @author DTUser
 *
 */
/**
 * @author DTUser
 *
 */
public class AgePersonaEventoItem {

	private String idFormadorEvento;
	private String idPersona;
	private String idEvento;
	private String idInstitucion;
	private String usuModificacion;
	private Date fechaModificacion;
	private Date fechabaja;

	public String getIdFormadorEvento() {
		return idFormadorEvento;
	}

	public void setIdFormadorEvento(String idFormadorEvento) {
		this.idFormadorEvento = idFormadorEvento;
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}

	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getUsuModificacion() {
		return usuModificacion;
	}

	public void setUsuModificacion(String usuModificacion) {
		this.usuModificacion = usuModificacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Date getFechabaja() {
		return fechabaja;
	}

	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaModificacion == null) ? 0 : fechaModificacion.hashCode());
		result = prime * result + ((fechabaja == null) ? 0 : fechabaja.hashCode());
		result = prime * result + ((idEvento == null) ? 0 : idEvento.hashCode());
		result = prime * result + ((idFormadorEvento == null) ? 0 : idFormadorEvento.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
		result = prime * result + ((usuModificacion == null) ? 0 : usuModificacion.hashCode());
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
		AgePersonaEventoItem other = (AgePersonaEventoItem) obj;
		if (fechaModificacion == null) {
			if (other.fechaModificacion != null)
				return false;
		} else if (!fechaModificacion.equals(other.fechaModificacion))
			return false;
		if (fechabaja == null) {
			if (other.fechabaja != null)
				return false;
		} else if (!fechabaja.equals(other.fechabaja))
			return false;
		if (idEvento == null) {
			if (other.idEvento != null)
				return false;
		} else if (!idEvento.equals(other.idEvento))
			return false;
		if (idFormadorEvento == null) {
			if (other.idFormadorEvento != null)
				return false;
		} else if (!idFormadorEvento.equals(other.idFormadorEvento))
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
		if (usuModificacion == null) {
			if (other.usuModificacion != null)
				return false;
		} else if (!usuModificacion.equals(other.usuModificacion))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AgePersonaEventoItem [idFormadorEvento=" + idFormadorEvento + ", idPersona=" + idPersona + ", idEvento="
				+ idEvento + ", idInstitucion=" + idInstitucion + ", usuModificacion=" + usuModificacion
				+ ", fechaModificacion=" + fechaModificacion + ", fechabaja=" + fechabaja + "]";
	}
}
