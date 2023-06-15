package org.itcgae.siga.DTO.fac;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BorrarSuscripcionBajaItem {
	private int idtiposervicios; //Categoria servicio
	private int idservicio; //Tipo Servicio
	private int idserviciosinstitucion; //Servicio
	private String opcionaltasbajas;
	private boolean checkboxDialogServicioAutomaticoAManual;
	private Date fechaeliminacionaltas;
	private String incluirbajasmanuales;
	
	public int getIdtiposervicios() {
		return idtiposervicios;
	}
	public void setIdtiposervicios(int idtiposervicios) {
		this.idtiposervicios = idtiposervicios;
	}
	public int getIdservicio() {
		return idservicio;
	}
	public boolean getcheckboxDialogServicioAutomaticoAManual() {
		return checkboxDialogServicioAutomaticoAManual;
	}
	public void setIdservicio(int idservicio) {
		this.idservicio = idservicio;
	}
	public int getIdserviciosinstitucion() {
		return idserviciosinstitucion;
	}
	public void setIdserviciosinstitucion(int idserviciosinstitucion) {
		this.idserviciosinstitucion = idserviciosinstitucion;
	}
	public String getOpcionaltasbajas() {
		return opcionaltasbajas;
	}
	public void setOpcionaltasbajas(String opcionaltasbajas) {
		this.opcionaltasbajas = opcionaltasbajas;
	}
	public Date getFechaeliminacionaltas() {
		return fechaeliminacionaltas;
	}
	public void setFechaeliminacionaltas(Date fechaeliminacionaltas) {
		this.fechaeliminacionaltas = fechaeliminacionaltas;
	}
	public String getIncluirbajasmanuales() {
		return incluirbajasmanuales;
	}
	public void setIncluirbajasmanuales(String incluirbajasmanuales) {
		this.incluirbajasmanuales = incluirbajasmanuales;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaeliminacionaltas == null) ? 0 : fechaeliminacionaltas.hashCode());
		result = prime * result + idservicio;
		result = prime * result + idserviciosinstitucion;
		result = prime * result + idtiposervicios;
		result = prime * result + ((incluirbajasmanuales == null) ? 0 : incluirbajasmanuales.hashCode());
		result = prime * result + ((opcionaltasbajas == null) ? 0 : opcionaltasbajas.hashCode());
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
		BorrarSuscripcionBajaItem other = (BorrarSuscripcionBajaItem) obj;
		if (fechaeliminacionaltas == null) {
			if (other.fechaeliminacionaltas != null)
				return false;
		} else if (!fechaeliminacionaltas.equals(other.fechaeliminacionaltas))
			return false;
		if (idservicio != other.idservicio)
			return false;
		if (idserviciosinstitucion != other.idserviciosinstitucion)
			return false;
		if (idtiposervicios != other.idtiposervicios)
			return false;
		if (incluirbajasmanuales == null) {
			if (other.incluirbajasmanuales != null)
				return false;
		} else if (!incluirbajasmanuales.equals(other.incluirbajasmanuales))
			return false;
		if (opcionaltasbajas == null) {
			if (other.opcionaltasbajas != null)
				return false;
		} else if (!opcionaltasbajas.equals(other.opcionaltasbajas))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "BorrarSuscripcionBajaItem [idtiposervicios=" + idtiposervicios + ", idservicio=" + idservicio
				+ ", idserviciosinstitucion=" + idserviciosinstitucion + ", opcionaltasbajas=" + opcionaltasbajas
				+ ", fechaeliminacionaltas=" + fechaeliminacionaltas + ", incluirbajasmanuales=" + incluirbajasmanuales
				+ "]";
	}
	
}
