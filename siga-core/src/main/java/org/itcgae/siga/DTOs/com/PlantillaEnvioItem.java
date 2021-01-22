package org.itcgae.siga.DTOs.com;

import java.util.Date;

public class PlantillaEnvioItem {

	private String idTipoEnvios;
	private String idPlantillaEnvios;
	private String nombre;
	private String idInstitucion;
	private String acuseRecibo;
	private Date fechaBaja;
	private String asunto;
	private String cuerpo;
	private String idDireccion;
	private String idPersona;
	private String descripcion;
	private String tipoEnvio;
	private String claseComunicacion;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTipoEnvio() {
		return tipoEnvio;
	}
	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}
	public String getIdTipoEnvios() {
		return idTipoEnvios;
	}
	public void setIdTipoEnvios(String idTipoEnvios) {
		this.idTipoEnvios = idTipoEnvios;
	}
	public String getIdPlantillaEnvios() {
		return idPlantillaEnvios;
	}
	public void setIdPlantillaEnvios(String idPlantillaEnvios) {
		this.idPlantillaEnvios = idPlantillaEnvios;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getAcuseRecibo() {
		return acuseRecibo;
	}
	public void setAcuseRecibo(String acuseRecibo) {
		this.acuseRecibo = acuseRecibo;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	public String getIdDireccion() {
		return idDireccion;
	}
	public void setIdDireccion(String idDireccion) {
		this.idDireccion = idDireccion;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getClaseComunicacion() {
		return claseComunicacion;
	}
	public void setClaseComunicacion(String claseComunicacion) {
		this.claseComunicacion = claseComunicacion;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acuseRecibo == null) ? 0 : acuseRecibo.hashCode());
		result = prime * result + ((asunto == null) ? 0 : asunto.hashCode());
		result = prime * result + ((claseComunicacion == null) ? 0 : claseComunicacion.hashCode());
		result = prime * result + ((cuerpo == null) ? 0 : cuerpo.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((fechaBaja == null) ? 0 : fechaBaja.hashCode());
		result = prime * result + ((idDireccion == null) ? 0 : idDireccion.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
		result = prime * result + ((idPlantillaEnvios == null) ? 0 : idPlantillaEnvios.hashCode());
		result = prime * result + ((idTipoEnvios == null) ? 0 : idTipoEnvios.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((tipoEnvio == null) ? 0 : tipoEnvio.hashCode());
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
		PlantillaEnvioItem other = (PlantillaEnvioItem) obj;
		if (acuseRecibo == null) {
			if (other.acuseRecibo != null)
				return false;
		} else if (!acuseRecibo.equals(other.acuseRecibo))
			return false;
		if (asunto == null) {
			if (other.asunto != null)
				return false;
		} else if (!asunto.equals(other.asunto))
			return false;
		if (claseComunicacion == null) {
			if (other.claseComunicacion != null)
				return false;
		} else if (!claseComunicacion.equals(other.claseComunicacion))
			return false;
		if (cuerpo == null) {
			if (other.cuerpo != null)
				return false;
		} else if (!cuerpo.equals(other.cuerpo))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fechaBaja == null) {
			if (other.fechaBaja != null)
				return false;
		} else if (!fechaBaja.equals(other.fechaBaja))
			return false;
		if (idDireccion == null) {
			if (other.idDireccion != null)
				return false;
		} else if (!idDireccion.equals(other.idDireccion))
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
		if (idPlantillaEnvios == null) {
			if (other.idPlantillaEnvios != null)
				return false;
		} else if (!idPlantillaEnvios.equals(other.idPlantillaEnvios))
			return false;
		if (idTipoEnvios == null) {
			if (other.idTipoEnvios != null)
				return false;
		} else if (!idTipoEnvios.equals(other.idTipoEnvios))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (tipoEnvio == null) {
			if (other.tipoEnvio != null)
				return false;
		} else if (!tipoEnvio.equals(other.tipoEnvio))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "PlantillaEnvioItem [idTipoEnvios=" + idTipoEnvios + ", idPlantillaEnvios=" + idPlantillaEnvios
				+ ", nombre=" + nombre + ", idInstitucion=" + idInstitucion + ", acuseRecibo=" + acuseRecibo
				+ ", fechaBaja=" + fechaBaja + ", asunto=" + asunto + ", cuerpo=" + cuerpo + ", idDireccion="
				+ idDireccion + ", idPersona=" + idPersona + ", descripcion=" + descripcion + ", tipoEnvio=" + tipoEnvio
				+ ", claseComunicacion=" + claseComunicacion + "]";
	}
	
}
