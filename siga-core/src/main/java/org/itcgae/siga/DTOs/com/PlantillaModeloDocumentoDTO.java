package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlantillaModeloDocumentoDTO {
	private String nombreFicheroSalida;
	private String idModeloComunicacion;
	private String idPlantillaDocumento;
	private String sufijo;
	private String formatoSalida;
	private String destinatarios;
	private String condicion;
	private String multiDocumento;
	private int datos;
	private Date fechaAsociacion;
	private String idioma;
	private String idIdioma;
	private String idInforme;
	private String idPlantillas;
	private String idFormatoSalida;
	private List<PlantillaDocumentoDTO> plantillasDocumentos = new ArrayList<PlantillaDocumentoDTO>();
	private List<SufijoItem> sufijos = new ArrayList<SufijoItem>();
	
	public String getNombreFicheroSalida() {
		return nombreFicheroSalida;
	}
	public void setNombreFicheroSalida(String nombreFicheroSalida) {
		this.nombreFicheroSalida = nombreFicheroSalida;
	}
	public String getIdModeloComunicacion() {
		return idModeloComunicacion;
	}
	public void setIdModeloComunicacion(String idModeloComunicacion) {
		this.idModeloComunicacion = idModeloComunicacion;
	}
	public String getIdPlantillaDocumento() {
		return idPlantillaDocumento;
	}
	public void setIdPlantillaDocumento(String idPlantillaDocumento) {
		this.idPlantillaDocumento = idPlantillaDocumento;
	}
	public String getSufijo() {
		return sufijo;
	}
	public void setSufijo(String sufijo) {
		this.sufijo = sufijo;
	}
	public String getFormatoSalida() {
		return formatoSalida;
	}
	public void setFormatoSalida(String formatoSalida) {
		this.formatoSalida = formatoSalida;
	}
	public String getDestinatarios() {
		return destinatarios;
	}
	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}
	public String getCondicion() {
		return condicion;
	}
	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}
	public String getMultiDocumento() {
		return multiDocumento;
	}
	public void setMultiDocumento(String multiDocumento) {
		this.multiDocumento = multiDocumento;
	}
	public Date getFechaAsociacion() {
		return fechaAsociacion;
	}
	public void setFechaAsociacion(Date fechaAsociacion) {
		this.fechaAsociacion = fechaAsociacion;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public List<PlantillaDocumentoDTO> getPlantillasDocumentos() {
		return plantillasDocumentos;
	}
	public void setPlantillasDocumentos(List<PlantillaDocumentoDTO> plantillasDocumentos) {
		this.plantillasDocumentos = plantillasDocumentos;
	}
	public String getIdInforme() {
		return idInforme;
	}
	public void setIdInforme(String idInforme) {
		this.idInforme = idInforme;
	}
	public List<SufijoItem> getSufijos() {
		return sufijos;
	}
	public void setSufijos(List<SufijoItem> sufijos) {
		this.sufijos = sufijos;
	}
	public int getDatos() {
		return datos;
	}
	public void setDatos(int datos) {
		this.datos = datos;
	}
	public String getIdPlantillas() {
		return idPlantillas;
	}
	public void setIdPlantillas(String idPlantillas) {
		this.idPlantillas = idPlantillas;
	}
	public String getIdFormatoSalida() {
		return idFormatoSalida;
	}
	public void setIdFormatoSalida(String idFormatoSalida) {
		this.idFormatoSalida = idFormatoSalida;
	}
	public String getIdIdioma() {
		return idIdioma;
	}
	public void setIdIdioma(String idIdioma) {
		this.idIdioma = idIdioma;
	}
	
	
	
}
