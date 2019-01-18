package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TarjetaPlantillaDocumentoDTO {
	private String idModeloComunicacion;
	private String idPlantillaDocumento;
	private String idInstitucion;
	private String idClaseComunicacion;
	private String idioma;
	private String ficheroSalida;
	private String sufijo;
	private String formatoSalida;
	private String destinatarios;
	private String condicion;
	private String multiDocumento;
	private String datos;
	private Date fechaAsociacion;
	private String plantilla;
	private String idConsulta;
	private String idInforme;
	
	private List<DocumentoPlantillaItem> plantillas = new ArrayList<DocumentoPlantillaItem>();
	private List<Long> sufijos = new ArrayList<Long>();
	private List<ConsultaItem> consultas = new ArrayList<ConsultaItem>();
	
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public String getFicheroSalida() {
		return ficheroSalida;
	}
	public void setFicheroSalida(String ficheroSalida) {
		this.ficheroSalida = ficheroSalida;
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
	public String getDatos() {
		return datos;
	}
	public void setDatos(String datos) {
		this.datos = datos;
	}
	public Date getFechaAsociacion() {
		return fechaAsociacion;
	}
	public void setFechaAsociacion(Date fechaAsociacion) {
		this.fechaAsociacion = fechaAsociacion;
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
	public String getPlantilla() {
		return plantilla;
	}
	public void setPlantilla(String plantilla) {
		this.plantilla = plantilla;
	}
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getIdClaseComunicacion() {
		return idClaseComunicacion;
	}
	public void setIdClaseComunicacion(String idClaseComunicacion) {
		this.idClaseComunicacion = idClaseComunicacion;
	}
	public String getIdConsulta() {
		return idConsulta;
	}
	public void setIdConsulta(String idConsulta) {
		this.idConsulta = idConsulta;
	}
	public List<DocumentoPlantillaItem> getPlantillas() {
		return plantillas;
	}
	public void setPlantillas(List<DocumentoPlantillaItem> plantillas) {
		this.plantillas = plantillas;
	}
	public List<Long> getSufijos() {
		return sufijos;
	}
	public void setSufijos(List<Long> sufijos) {
		this.sufijos = sufijos;
	}
	public String getIdInforme() {
		return idInforme;
	}
	public void setIdInforme(String idInforme) {
		this.idInforme = idInforme;
	}
	public List<ConsultaItem> getConsultas() {
		return consultas;
	}
	public void setConsultas(List<ConsultaItem> consultas) {
		this.consultas = consultas;
	}
	
	
}
