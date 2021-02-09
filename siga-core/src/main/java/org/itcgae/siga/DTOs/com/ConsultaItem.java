package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.Date;

public class ConsultaItem {
	private String idInstitucion;
	private String idConsulta;
	private String idConsultaAnterior;
	private String nombre;
	private String generica;
	private String tipoConsulta;
	private String idModulo;
	private Date fechaModificacion;
	private String usuModificacion;
	private String bases;
	private String idTabla;
	private String experta;
	private String descripcion;
	private Date fechaBaja;


	private String idObjetivo;
	private String idClase;
	private String idClaseComunicacion;
	private String modulo;
	private String objetivo;
	private String claseComunicacion;
	private String region;
	
	private String idPlantillaConsulta;
	private String idModeloComunicacion;
	private String idPlantillaDocumento;
	private String idPlantillaEnvios;
	private String idTipoEnvios;

	private String sentencia;
	
	private String idPlantillasConsultas;
	
	private String finalidad;
	private String idInstitucionConsulta;
	private String idioma;
	
	private ArrayList<CampoDinamicoItem> camposDinamicos = new ArrayList<CampoDinamicoItem>();

	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(String idConsulta) {
		this.idConsulta = idConsulta;
	}
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getGenerica() {
		return generica;
	}

	public void setGenerica(String generica) {
		this.generica = generica;
	}

	public String getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(String tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public String getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(String idModulo) {
		this.idModulo = idModulo;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getUsuModificacion() {
		return usuModificacion;
	}

	public void setUsuModificacion(String usuModificacion) {
		this.usuModificacion = usuModificacion;
	}

	public String getBases() {
		return bases;
	}

	public void setBases(String bases) {
		this.bases = bases;
	}

	public String getIdTabla() {
		return idTabla;
	}

	public void setIdTabla(String idTabla) {
		this.idTabla = idTabla;
	}

	public String getExperta() {
		return experta;
	}

	public void setExperta(String experta) {
		this.experta = experta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getIdObjetivo() {
		return idObjetivo;
	}

	public void setIdObjetivo(String idObjetivo) {
		this.idObjetivo = idObjetivo;
	}

	public String getIdClase() {
		return idClase;
	}

	public void setIdClase(String idClase) {
		this.idClase = idClase;
	}

	public String getIdClaseComunicacion() {
		return idClaseComunicacion;
	}

	public void setIdClaseComunicacion(String idClaseComunicacion) {
		this.idClaseComunicacion = idClaseComunicacion;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getClaseComunicacion() {
		return claseComunicacion;
	}

	public void setClaseComunicacion(String claseComunicacion) {
		this.claseComunicacion = claseComunicacion;
	}

	public String getSentencia() {
		return sentencia;
	}

	public void setSentencia(String sentencia) {
		this.sentencia = sentencia;
	}

	public String getIdPlantillaConsulta() {
		return idPlantillaConsulta;
	}

	public void setIdPlantillaConsulta(String idPlantillaConsulta) {
		this.idPlantillaConsulta = idPlantillaConsulta;
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

	public String getIdConsultaAnterior() {
		return idConsultaAnterior;
	}

	public void setIdConsultaAnterior(String idConsultaAnterior) {
		this.idConsultaAnterior = idConsultaAnterior;
	}

	public String getIdPlantillasConsultas() {
		return idPlantillasConsultas;
	}

	public void setIdPlantillasConsultas(String idPlantillasConsultas) {
		this.idPlantillasConsultas = idPlantillasConsultas;
	}

	public String getFinalidad() {
		return finalidad;
	}

	public void setFinalidad(String finalidad) {
		this.finalidad = finalidad;
	}

	public ArrayList<CampoDinamicoItem> getCamposDinamicos() {
		return camposDinamicos;
	}

	public void setCamposDinamicos(ArrayList<CampoDinamicoItem> camposDinamicos) {
		this.camposDinamicos = camposDinamicos;
	}

	public String getIdPlantillaEnvios() {
		return idPlantillaEnvios;
	}

	public void setIdPlantillaEnvios(String idPlantillaEnvios) {
		this.idPlantillaEnvios = idPlantillaEnvios;
	}

	public String getIdTipoEnvios() {
		return idTipoEnvios;
	}

	public void setIdTipoEnvios(String idTipoEnvios) {
		this.idTipoEnvios = idTipoEnvios;
	}

	public String getIdInstitucionConsulta() {
		return idInstitucionConsulta;
	}

	public void setIdInstitucionConsulta(String idInstitucionConsulta) {
		this.idInstitucionConsulta = idInstitucionConsulta;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	

	

	
}
