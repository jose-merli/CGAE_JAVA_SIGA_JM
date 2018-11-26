package org.itcgae.siga.DTOs.com;

import java.util.Date;

import org.itcgae.siga.db.entities.EnvEnvios;

public class EnviosMasivosItem {
	
	private Short idInstitucion;
	private Long idEnvio;
	private String descripcion;
	private Date fecha;
	private String generarDocumento;
	private String imprimirEtiquetas;
	private Short idPlantillasEnvios;
	private Short idEstado;
	private Short idTipoEnvios;
	private Date fechaModificacion;
	private int usuModificacion;
	private Short idPlantilla;
	private Long idImpresora;
	private Date fechaProgramada;
	private String consulta;
	private String acuserecibo;
	private String idtipointercambiotelematico;
	private Short comisionajg;
	private Date fechabaja;
	private String csv;
	private String idsolicitudecos;
	
	
	public Short getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public Long getIdEnvio() {
		return idEnvio;
	}
	public void setIdEnvio(Long idEnvio) {
		this.idEnvio = idEnvio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getGenerarDocumento() {
		return generarDocumento;
	}
	public void setGenerarDocumento(String generarDocumento) {
		this.generarDocumento = generarDocumento;
	}
	public String getImprimirEtiquetas() {
		return imprimirEtiquetas;
	}
	public void setImprimirEtiquetas(String imprimirEtiquetas) {
		this.imprimirEtiquetas = imprimirEtiquetas;
	}
	public Short getIdPlantillasEnvios() {
		return idPlantillasEnvios;
	}
	public void setIdPlantillasEnvios(Short idPlantillasEnvios) {
		this.idPlantillasEnvios = idPlantillasEnvios;
	}
	public Short getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Short idEstado) {
		this.idEstado = idEstado;
	}
	public Short getIdTipoEnvios() {
		return idTipoEnvios;
	}
	public void setIdTipoEnvios(Short idTipoEnvios) {
		this.idTipoEnvios = idTipoEnvios;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public int getUsuModificacion() {
		return usuModificacion;
	}
	public void setUsuModificacion(int usuModificacion) {
		this.usuModificacion = usuModificacion;
	}
	public Short getIdPlantilla() {
		return idPlantilla;
	}
	public void setIdPlantilla(Short idPlantilla) {
		this.idPlantilla = idPlantilla;
	}
	public Long getIdImpresora() {
		return idImpresora;
	}
	public void setIdImpresora(Long idImpresora) {
		this.idImpresora = idImpresora;
	}
	public Date getFechaProgramada() {
		return fechaProgramada;
	}
	public void setFechaProgramada(Date fechaProgramada) {
		this.fechaProgramada = fechaProgramada;
	}
	public String getConsulta() {
		return consulta;
	}
	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}
	public String getAcuserecibo() {
		return acuserecibo;
	}
	public void setAcuserecibo(String acuserecibo) {
		this.acuserecibo = acuserecibo;
	}
	public String getIdtipointercambiotelematico() {
		return idtipointercambiotelematico;
	}
	public void setIdtipointercambiotelematico(String idtipointercambiotelematico) {
		this.idtipointercambiotelematico = idtipointercambiotelematico;
	}
	public Short getComisionajg() {
		return comisionajg;
	}
	public void setComisionajg(Short comisionajg) {
		this.comisionajg = comisionajg;
	}
	public Date getFechabaja() {
		return fechabaja;
	}
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}
	public String getCsv() {
		return csv;
	}
	public void setCsv(String csv) {
		this.csv = csv;
	}
	public String getIdsolicitudecos() {
		return idsolicitudecos;
	}
	public void setIdsolicitudecos(String idsolicitudecos) {
		this.idsolicitudecos = idsolicitudecos;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acuserecibo == null) ? 0 : acuserecibo.hashCode());
		result = prime * result + ((comisionajg == null) ? 0 : comisionajg.hashCode());
		result = prime * result + ((consulta == null) ? 0 : consulta.hashCode());
		result = prime * result + ((csv == null) ? 0 : csv.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((fechaModificacion == null) ? 0 : fechaModificacion.hashCode());
		result = prime * result + ((fechaProgramada == null) ? 0 : fechaProgramada.hashCode());
		result = prime * result + ((fechabaja == null) ? 0 : fechabaja.hashCode());
		result = prime * result + ((generarDocumento == null) ? 0 : generarDocumento.hashCode());
		result = prime * result + ((idEnvio == null) ? 0 : idEnvio.hashCode());
		result = prime * result + ((idEstado == null) ? 0 : idEstado.hashCode());
		result = prime * result + ((idImpresora == null) ? 0 : idImpresora.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idPlantilla == null) ? 0 : idPlantilla.hashCode());
		result = prime * result + ((idPlantillasEnvios == null) ? 0 : idPlantillasEnvios.hashCode());
		result = prime * result + ((idTipoEnvios == null) ? 0 : idTipoEnvios.hashCode());
		result = prime * result + ((idsolicitudecos == null) ? 0 : idsolicitudecos.hashCode());
		result = prime * result + ((idtipointercambiotelematico == null) ? 0 : idtipointercambiotelematico.hashCode());
		result = prime * result + ((imprimirEtiquetas == null) ? 0 : imprimirEtiquetas.hashCode());
		result = prime * result + usuModificacion;
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
		EnviosMasivosItem other = (EnviosMasivosItem) obj;
		if (acuserecibo == null) {
			if (other.acuserecibo != null)
				return false;
		} else if (!acuserecibo.equals(other.acuserecibo))
			return false;
		if (comisionajg == null) {
			if (other.comisionajg != null)
				return false;
		} else if (!comisionajg.equals(other.comisionajg))
			return false;
		if (consulta == null) {
			if (other.consulta != null)
				return false;
		} else if (!consulta.equals(other.consulta))
			return false;
		if (csv == null) {
			if (other.csv != null)
				return false;
		} else if (!csv.equals(other.csv))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (fechaModificacion == null) {
			if (other.fechaModificacion != null)
				return false;
		} else if (!fechaModificacion.equals(other.fechaModificacion))
			return false;
		if (fechaProgramada == null) {
			if (other.fechaProgramada != null)
				return false;
		} else if (!fechaProgramada.equals(other.fechaProgramada))
			return false;
		if (fechabaja == null) {
			if (other.fechabaja != null)
				return false;
		} else if (!fechabaja.equals(other.fechabaja))
			return false;
		if (generarDocumento == null) {
			if (other.generarDocumento != null)
				return false;
		} else if (!generarDocumento.equals(other.generarDocumento))
			return false;
		if (idEnvio == null) {
			if (other.idEnvio != null)
				return false;
		} else if (!idEnvio.equals(other.idEnvio))
			return false;
		if (idEstado == null) {
			if (other.idEstado != null)
				return false;
		} else if (!idEstado.equals(other.idEstado))
			return false;
		if (idImpresora == null) {
			if (other.idImpresora != null)
				return false;
		} else if (!idImpresora.equals(other.idImpresora))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (idPlantilla == null) {
			if (other.idPlantilla != null)
				return false;
		} else if (!idPlantilla.equals(other.idPlantilla))
			return false;
		if (idPlantillasEnvios == null) {
			if (other.idPlantillasEnvios != null)
				return false;
		} else if (!idPlantillasEnvios.equals(other.idPlantillasEnvios))
			return false;
		if (idTipoEnvios == null) {
			if (other.idTipoEnvios != null)
				return false;
		} else if (!idTipoEnvios.equals(other.idTipoEnvios))
			return false;
		if (idsolicitudecos == null) {
			if (other.idsolicitudecos != null)
				return false;
		} else if (!idsolicitudecos.equals(other.idsolicitudecos))
			return false;
		if (idtipointercambiotelematico == null) {
			if (other.idtipointercambiotelematico != null)
				return false;
		} else if (!idtipointercambiotelematico.equals(other.idtipointercambiotelematico))
			return false;
		if (imprimirEtiquetas == null) {
			if (other.imprimirEtiquetas != null)
				return false;
		} else if (!imprimirEtiquetas.equals(other.imprimirEtiquetas))
			return false;
		if (usuModificacion != other.usuModificacion)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "EnviosMasivosItem [idInstitucion=" + idInstitucion + ", idEnvio=" + idEnvio + ", descripcion="
				+ descripcion + ", fecha=" + fecha + ", generarDocumento=" + generarDocumento + ", imprimirEtiquetas="
				+ imprimirEtiquetas + ", idPlantillasEnvios=" + idPlantillasEnvios + ", idEstado=" + idEstado
				+ ", idTipoEnvios=" + idTipoEnvios + ", fechaModificacion=" + fechaModificacion + ", usuModificacion="
				+ usuModificacion + ", idPlantilla=" + idPlantilla + ", idImpresora=" + idImpresora
				+ ", fechaProgramada=" + fechaProgramada + ", consulta=" + consulta + ", acuserecibo=" + acuserecibo
				+ ", idtipointercambiotelematico=" + idtipointercambiotelematico + ", comisionajg=" + comisionajg
				+ ", fechabaja=" + fechabaja + ", csv=" + csv + ", idsolicitudecos=" + idsolicitudecos + "]";
	}
	

}
