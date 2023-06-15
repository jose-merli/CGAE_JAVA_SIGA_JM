package org.itcgae.siga.DTOs.com;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.itcgae.siga.DTOs.gen.ComboItem;

public class ModelosComunicacionItem {
	
	private String idModeloComunicacion;
	private String nombre;
	private Integer orden;
	private String idInstitucion;
	private String descripcion;
	private String preseleccionar;
	private String idClaseComunicacion;
	private Date fechaBaja;
	private String visible;
	private String institucion;
	private String claseComunicacion;
	private String idPlantillaEnvio;
	private String idTipoEnvio;
	private String porDefecto;
	private String tipoEnvio;
	private String generacionExcel;
	private Short generarExcel;
	private String informeUnico;
	
	private List<ComboItem> plantillas = new ArrayList<ComboItem>();
	
	public String getIdModeloComunicacion() {
		return idModeloComunicacion;
	}
	public void setIdModeloComunicacion(String idModeloComunicacion) {
		this.idModeloComunicacion = idModeloComunicacion;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getOrden() {
		return orden;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getPreseleccionar() {
		return preseleccionar;
	}
	public void setPreseleccionar(String preseleccionar) {
		this.preseleccionar = preseleccionar;
	}
	public String getIdClaseComunicacion() {
		return idClaseComunicacion;
	}
	public void setIdClaseComunicacion(String idClaseComunicacion) {
		this.idClaseComunicacion = idClaseComunicacion;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
	public String getInstitucion() {
		return institucion;
	}
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
	public String getClaseComunicacion() {
		return claseComunicacion;
	}
	public void setClaseComunicacion(String claseComunicacion) {
		this.claseComunicacion = claseComunicacion;
	}
	public String getIdPlantillaEnvio() {
		return idPlantillaEnvio;
	}
	public void setIdPlantillaEnvio(String idPlantillaEnvio) {
		this.idPlantillaEnvio = idPlantillaEnvio;
	}
	public String getIdTipoEnvio() {
		return idTipoEnvio;
	}
	public void setIdTipoEnvio(String idTipoEnvio) {
		this.idTipoEnvio = idTipoEnvio;
	}
	public String getPorDefecto() {
		return porDefecto;
	}
	public void setPorDefecto(String porDefecto) {
		this.porDefecto = porDefecto;
	}
	public List<ComboItem> getPlantillas() {
		return plantillas;
	}
	public void setPlantillas(List<ComboItem> plantillas) {
		this.plantillas = plantillas;
	}
	public String getTipoEnvio() {
		return tipoEnvio;
	}
	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}
	public String getGeneracionExcel() {
		return generacionExcel;
	}
	public void setGeneracionExcel(String generacionExcel) {
		this.generacionExcel = generacionExcel;
	}
	public Short getGenerarExcel() {
		return generarExcel;
	}
	public void setGenerarExcel(Short generarExcel) {
		this.generarExcel = generarExcel;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((claseComunicacion == null) ? 0 : claseComunicacion.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((fechaBaja == null) ? 0 : fechaBaja.hashCode());
		result = prime * result + ((generacionExcel == null) ? 0 : generacionExcel.hashCode());
		result = prime * result + ((generarExcel == null) ? 0 : generarExcel.hashCode());
		result = prime * result + ((idClaseComunicacion == null) ? 0 : idClaseComunicacion.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idModeloComunicacion == null) ? 0 : idModeloComunicacion.hashCode());
		result = prime * result + ((idPlantillaEnvio == null) ? 0 : idPlantillaEnvio.hashCode());
		result = prime * result + ((idTipoEnvio == null) ? 0 : idTipoEnvio.hashCode());
		result = prime * result + ((institucion == null) ? 0 : institucion.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((orden == null) ? 0 : orden.hashCode());
		result = prime * result + ((plantillas == null) ? 0 : plantillas.hashCode());
		result = prime * result + ((porDefecto == null) ? 0 : porDefecto.hashCode());
		result = prime * result + ((preseleccionar == null) ? 0 : preseleccionar.hashCode());
		result = prime * result + ((tipoEnvio == null) ? 0 : tipoEnvio.hashCode());
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
		ModelosComunicacionItem other = (ModelosComunicacionItem) obj;
		if (claseComunicacion == null) {
			if (other.claseComunicacion != null)
				return false;
		} else if (!claseComunicacion.equals(other.claseComunicacion))
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
		if (generacionExcel == null) {
			if (other.generacionExcel != null)
				return false;
		} else if (!generacionExcel.equals(other.generacionExcel))
			return false;
		if (generarExcel == null) {
			if (other.generarExcel != null)
				return false;
		} else if (!generarExcel.equals(other.generarExcel))
			return false;
		if (idClaseComunicacion == null) {
			if (other.idClaseComunicacion != null)
				return false;
		} else if (!idClaseComunicacion.equals(other.idClaseComunicacion))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (idModeloComunicacion == null) {
			if (other.idModeloComunicacion != null)
				return false;
		} else if (!idModeloComunicacion.equals(other.idModeloComunicacion))
			return false;
		if (idPlantillaEnvio == null) {
			if (other.idPlantillaEnvio != null)
				return false;
		} else if (!idPlantillaEnvio.equals(other.idPlantillaEnvio))
			return false;
		if (idTipoEnvio == null) {
			if (other.idTipoEnvio != null)
				return false;
		} else if (!idTipoEnvio.equals(other.idTipoEnvio))
			return false;
		if (institucion == null) {
			if (other.institucion != null)
				return false;
		} else if (!institucion.equals(other.institucion))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (orden == null) {
			if (other.orden != null)
				return false;
		} else if (!orden.equals(other.orden))
			return false;
		if (plantillas == null) {
			if (other.plantillas != null)
				return false;
		} else if (!plantillas.equals(other.plantillas))
			return false;
		if (porDefecto == null) {
			if (other.porDefecto != null)
				return false;
		} else if (!porDefecto.equals(other.porDefecto))
			return false;
		if (preseleccionar == null) {
			if (other.preseleccionar != null)
				return false;
		} else if (!preseleccionar.equals(other.preseleccionar))
			return false;
		if (tipoEnvio == null) {
			if (other.tipoEnvio != null)
				return false;
		} else if (!tipoEnvio.equals(other.tipoEnvio))
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
		return "ModelosComunicacionItem [idModeloComunicacion=" + idModeloComunicacion + ", nombre=" + nombre
				+ ", orden=" + orden + ", idInstitucion=" + idInstitucion + ", descripcion=" + descripcion
				+ ", preseleccionar=" + preseleccionar + ", idClaseComunicacion=" + idClaseComunicacion + ", fechaBaja="
				+ fechaBaja + ", visible=" + visible + ", institucion=" + institucion + ", claseComunicacion="
				+ claseComunicacion + ", idPlantillaEnvio=" + idPlantillaEnvio + ", idTipoEnvio=" + idTipoEnvio
				+ ", porDefecto=" + porDefecto + ", tipoEnvio=" + tipoEnvio + ", generacionExcel=" + generacionExcel
				+ ", generarExcel=" + generarExcel + ", plantillas=" + plantillas + "]";
	}
	public String getInformeUnico() {
		return informeUnico;
	}
	public void setInformeUnico(String informeUnico) {
		this.informeUnico = informeUnico;
	}
	
	
	
}
