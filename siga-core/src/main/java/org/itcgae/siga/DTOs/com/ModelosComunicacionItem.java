package org.itcgae.siga.DTOs.com;


import java.util.Date;
import java.util.List;

import org.itcgae.siga.DTOs.gen.ComboDTO;
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
	private String tipoEnvio;
	private List<ComboItem> plantillas;
	
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
	public String getTipoEnvio() {
		return tipoEnvio;
	}
	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}
	public List<ComboItem> getPlantillas() {
		return plantillas;
	}
	public void setPlantillas(List<ComboItem> plantillas) {
		this.plantillas = plantillas;
	}
	
	
	
}
