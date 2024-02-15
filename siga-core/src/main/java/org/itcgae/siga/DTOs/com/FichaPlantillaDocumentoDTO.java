package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;

public class FichaPlantillaDocumentoDTO {
	private String idInstitucion;
	private String idClaseComunicacion;
	private String idModeloComunicacion;
	private String idPlantillaDocumento;
	private String idIdioma;
	private String formatoSalida;
	private String idFormatoSalida;
	private String nombreFicheroSalida;
	private List<String> idSufijo=new ArrayList<String>();
	private String idTipoEjecucion;
	private String idInforme;
	private List<PlantillaDocumentoDTO> plantillas=new ArrayList<PlantillaDocumentoDTO>();
	private List<SufijoItem> sufijos = new ArrayList<SufijoItem>();
	
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
	public String getIdIdioma() {
		return idIdioma;
	}
	public void setIdIdioma(String idIdioma) {
		this.idIdioma = idIdioma;
	}
	public String getFormatoSalida() {
		return formatoSalida;
	}
	public void setFormatoSalida(String formatoSalida) {
		this.formatoSalida = formatoSalida;
	}
	public String getIdFormatoSalida() {
		return idFormatoSalida;
	}
	public void setIdFormatoSalida(String idFormatoSalida) {
		this.idFormatoSalida = idFormatoSalida;
	}
	public String getNombreFicheroSalida() {
		return nombreFicheroSalida;
	}
	public void setNombreFicheroSalida(String nombreFicheroSalida) {
		this.nombreFicheroSalida = nombreFicheroSalida;
	}
	public List<String> getIdSufijo() {
		return idSufijo;
	}
	public void setIdSufijo(List<String> idSufijo) {
		this.idSufijo = idSufijo;
	}
	public String getIdTipoEjecucion() {
		return idTipoEjecucion;
	}
	public void setIdTipoEjecucion(String idTipoEjecucion) {
		this.idTipoEjecucion = idTipoEjecucion;
	}
	public String getIdInforme() {
		return idInforme;
	}
	public void setIdInforme(String idInforme) {
		this.idInforme = idInforme;
	}
	public List<PlantillaDocumentoDTO> getPlantillas() {
		return plantillas;
	}
	public void setPlantillas(List<PlantillaDocumentoDTO> plantillas) {
		this.plantillas = plantillas;
	}
	public List<SufijoItem> getSufijos() {
		return sufijos;
	}
	public void setSufijos(List<SufijoItem> sufijos) {
		this.sufijos = sufijos;
	}

	
	
//	  idInstitucion: String;
//	  idClaseComunicacion: String;
//	  idModeloComunicacion: String;
//	  idPlantillaDocumento: String;
//	  idIdioma: String;
//	  formatoSalida: String;
//	  idFormatoSalida: String;
//	  nombreFicheroSalida: String;
//	  idSufijo: String[];
//	  idFinalidad: String;
//	  idTipoEjecucion: String;
//	  idInforme: String;
//	  plantillas: PlantillaDocumentoItem[] = [];
//	  consultas: ConsultasPlantillasInformesItem[] = [];
//	  sufijos: SufijoItem[] = [];
//	  generacionExcel:number;  
}
