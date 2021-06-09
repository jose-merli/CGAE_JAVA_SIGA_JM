package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class RelacionesItem {
	private String sjcs;
	private String idinstitucion;
	private String anio;
	private String numero;
	private String idletrado;
	private String letrado;
	private String interesado;
	private String idturno;
	private String idturnodesigna;
	private String idtipo;
	private String codigo;
	private String desturno;
	private String destipo;
	private Date fechaDesignacion;
	private String datosinteres;
	private String idsjcs;
	

	public String getSjcs() {
		return sjcs;
	}

	public void setSjcs(String sjcs) {
		this.sjcs = sjcs;
	}
	public String getIdsjcs() {
		return idsjcs;
	}

	public void setIdsjcs(String idsjcs) {
		this.idsjcs = idsjcs;
	}

	public String getIdinstitucion() {
		return idinstitucion;
	}

	public void setIdinstitucion(String idinstitucion) {
		this.idinstitucion = idinstitucion;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getIdletrado() {
		return idletrado;
	}

	public void setIdletrado(String idletrado) {
		this.idletrado = idletrado;
	}

	public String getLetrado() {
		return letrado;
	}

	public void setLetrado(String letrado) {
		this.letrado = letrado;
	}

	public String getIdturno() {
		return idturno;
	}

	public void setIdturno(String idturno) {
		this.idturno = idturno;
	}

	public String getIdturnodesigna() {
		return idturnodesigna;
	}

	public void setIdturnodesigna(String idturnodesigna) {
		this.idturnodesigna = idturnodesigna;
	}

	public String getIdtipo() {
		return idtipo;
	}

	public void setIdtipo(String idtipo) {
		this.idtipo = idtipo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDesturno() {
		return desturno;
	}

	public void setDesturno(String desturno) {
		this.desturno = desturno;
	}

	public String getDestipo() {
		return destipo;
	}

	public void setDestipo(String destipo) {
		this.destipo = destipo;
	}
	public Date getFechaDesignacion() {
		return fechaDesignacion;
	}

	public void setFechaDesignacion(Date fechaDesignacion) {
		this.fechaDesignacion = fechaDesignacion;
	}
	public String getDatosinteres() {
		return datosinteres;
	}

	public void setDatosinteres(String datosinteres) {
		this.datosinteres = datosinteres;
	}

	
	
	public String getInteresado() {
		return interesado;
	}

	public void setInteresado(String interesado) {
		this.interesado = interesado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anio == null) ? 0 : anio.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((destipo == null) ? 0 : destipo.hashCode());
		result = prime * result + ((desturno == null) ? 0 : desturno.hashCode());
		result = prime * result + ((idinstitucion == null) ? 0 : idinstitucion.hashCode());
		result = prime * result + ((idletrado == null) ? 0 : idletrado.hashCode());
		result = prime * result + ((idtipo == null) ? 0 : idtipo.hashCode());
		result = prime * result + ((idturno == null) ? 0 : idturno.hashCode());
		result = prime * result + ((idturnodesigna == null) ? 0 : idturnodesigna.hashCode());
		result = prime * result + ((letrado == null) ? 0 : letrado.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((sjcs == null) ? 0 : sjcs.hashCode());
		result = prime * result + ((fechaDesignacion == null) ? 0 : fechaDesignacion.hashCode());
		result = prime * result + ((datosinteres == null) ? 0 : datosinteres.hashCode());
		result = prime * result + ((interesado == null) ? 0 : interesado.hashCode());
		result = prime * result + ((idsjcs == null) ? 0 : idsjcs.hashCode());
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
		RelacionesItem other = (RelacionesItem) obj;
		if (anio == null) {
			if (other.anio != null)
				return false;
		} else if (!anio.equals(other.anio))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (destipo == null) {
			if (other.destipo != null)
				return false;
		} else if (!destipo.equals(other.destipo))
			return false;
		if (desturno == null) {
			if (other.desturno != null)
				return false;
		} else if (!desturno.equals(other.desturno))
			return false;
		if (idinstitucion == null) {
			if (other.idinstitucion != null)
				return false;
		} else if (!idinstitucion.equals(other.idinstitucion))
			return false;
		if (idletrado == null) {
			if (other.idletrado != null)
				return false;
		} else if (!idletrado.equals(other.idletrado))
			return false;
		if (idtipo == null) {
			if (other.idtipo != null)
				return false;
		} else if (!idtipo.equals(other.idtipo))
			return false;
		if (idturno == null) {
			if (other.idturno != null)
				return false;
		} else if (!idturno.equals(other.idturno))
			return false;
		if (idturnodesigna == null) {
			if (other.idturnodesigna != null)
				return false;
		} else if (!idturnodesigna.equals(other.idturnodesigna))
			return false;
		if (letrado == null) {
			if (other.letrado != null)
				return false;
		} else if (!letrado.equals(other.letrado))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (sjcs == null) {
			if (other.sjcs != null)
				return false;
		} else if (!sjcs.equals(other.sjcs))
			return false;
		if (fechaDesignacion == null) {
			if (other.fechaDesignacion != null)
				return false;
		} else if (!fechaDesignacion.equals(other.fechaDesignacion))
			return false;
		if (datosinteres == null) {
			if (other.datosinteres != null)
				return false;
		} else if (!datosinteres.equals(other.datosinteres))
			return false;
		if (interesado == null) {
			if (other.interesado != null)
				return false;
		} else if (!interesado.equals(other.interesado))
			return false;
		if (idsjcs == null) {
			if (other.idsjcs != null)
				return false;
		} else if (!idsjcs.equals(other.idsjcs))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "RelacionesItem [sjcs=" + sjcs + ", idinstitucion=" + idinstitucion + ", anio=" + anio + ", numero="
				+ numero + ", idletrado=" + idletrado + ", letrado=" + letrado + ", idturno=" + idturno
				+ ", idturnodesigna=" + idturnodesigna + ", idtipo=" + idtipo + ", codigo=" + codigo + ", desturno="
				+ desturno + ", destipo=" + destipo +", interesado="+interesado+", fechaDesignacion=" 
				+ fechaDesignacion + ", datosinteres=" + datosinteres +", idsjcs=" + idsjcs +"]";
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

}
