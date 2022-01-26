package org.itcgae.siga.DTOs.cen;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BusquedaSancionesItem implements Comparable<BusquedaSancionesItem>{

	private String[] idColegios;
	private String colegio;
	private String idColegio;
	private String idInstitucionS;
	private String nombre;
	private String tipoSancion;
	private String estado;
	private String rehabilitado;
	private String refConsejo;
	private String firmeza;
	private String multa;
	private String refColegio;
	private Date fechaDesdeDate;
	private String fechaDesde;
	private Date fechaAcuerdoDate;
	private String fechaAcuerdo;
	private Date fechaFirmezaDate;
	private String fechaFirmeza;
	private Date fechaRehabilitadoDate;
	private String fechaRehabilitado;
	private Date fechaArchivadaDate;
	private String fechaArchivada;
	private Date fechaHastaDate;
	private String archivada;
	private String fechaHasta;
	private String texto;
	private String observaciones;
	private String idPersona;
	private String idSancion;
	private String nif;
	private String fechaNacimiento;
	private Date fechaNacimientoDate;
	
	private boolean restablecer;
	private String numExpediente;

	@JsonProperty("numExpediente")
	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	@JsonProperty("colegio")
	public String getColegio() {
		return colegio;
	}
	public void setColegio(String colegio) {
		this.colegio = colegio;
	}
	
	@JsonProperty("idColegio")
	public String getIdColegio() {
		return idColegio;
	}
	public void setIdColegio(String idColegio) {
		this.idColegio = idColegio;
	}
	
	@JsonProperty("idInstitucionS")
	public String getIdInstitucionS() {
		return idInstitucionS;
	}
	public void setIdInstitucionS(String idInstitucionS) {
		this.idInstitucionS = idInstitucionS;
	}
	
	@JsonProperty("multa")
	public String getMulta() {
		return multa;
	}
	public void setMulta(String multa) {
		this.multa = multa;
	}
	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@JsonProperty("tipoSancion")
	public String getTipoSancion() {
		return tipoSancion;
	}
	public void setTipoSancion(String tipoSancion) {
		this.tipoSancion = tipoSancion;
	}
	@JsonProperty("estado")
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	@JsonProperty("refConsejo")
	public String getRefConsejo() {
		return refConsejo;
	}
	public void setRefConsejo(String refConsejo) {
		this.refConsejo = refConsejo;
	}
	@JsonProperty("refColegio")
	public String getRefColegio() {
		return refColegio;
	}
	public void setRefColegio(String refColegio) {
		this.refColegio = refColegio;
	}
	
	@JsonProperty("idColegios")
	public String[] getIdColegios() {
		return idColegios;
	}
	public void setIdColegios(String[] idColegios) {
		this.idColegios = idColegios;
	}
	
	@JsonProperty("fechaDesdeDate")
	public Date getFechaDesdeDate() {
		return fechaDesdeDate;
	}
	public void setFechaDesdeDate(Date fechaDesdeDate) {
		this.fechaDesdeDate = fechaDesdeDate;
	}
	@JsonProperty("fechaDesde")
	public String getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	@JsonProperty("fechaAcuerdoDate")
	public Date getFechaAcuerdoDate() {
		return fechaAcuerdoDate;
	}
	public void setFechaAcuerdoDate(Date fechaAcuerdoDate) {
		this.fechaAcuerdoDate = fechaAcuerdoDate;
	}
	@JsonProperty("fechaAcuerdo")
	public String getFechaAcuerdo() {
		return fechaAcuerdo;
	}
	public void setFechaAcuerdo(String fechaAcuerdo) {
		this.fechaAcuerdo = fechaAcuerdo;
	}
	@JsonProperty("fechaFirmezaDate")
	public Date getFechaFirmezaDate() {
		return fechaFirmezaDate;
	}
	public void setFechaFirmezaDate(Date fechaFirmezaDate) {
		this.fechaFirmezaDate = fechaFirmezaDate;
	}
	@JsonProperty("fechaRehabilitadoDate")
	public Date getFechaRehabilitadoDate() {
		return fechaRehabilitadoDate;
	}
	public void setFechaRehabilitadoDate(Date fechaRehabilitadoDate) {
		this.fechaRehabilitadoDate = fechaRehabilitadoDate;
	}
	@JsonProperty("fechaRehabilitado")
	public String getFechaRehabilitado() {
		return fechaRehabilitado;
	}
	public void setFechaRehabilitado(String fechaRehabilitado) {
		this.fechaRehabilitado = fechaRehabilitado;
	}
	@JsonProperty("fechaArchivadaDate")
	public Date getFechaArchivadaDate() {
		return fechaArchivadaDate;
	}
	public void setFechaArchivadaDate(Date fechaArchivadaDate) {
		this.fechaArchivadaDate = fechaArchivadaDate;
	}
	@JsonProperty("fechaArchivada")
	public String getFechaArchivada() {
		return fechaArchivada;
	}
	public void setFechaArchivada(String fechaArchivada) {
		this.fechaArchivada = fechaArchivada;
	}
	@JsonProperty("archivada")
	public String getArchivada() {
		return archivada;
	}
	public void setArchivada(String archivada) {
		this.archivada = archivada;
	}
	@JsonProperty("fechaHastaDate")
	public Date getFechaHastaDate() {
		return fechaHastaDate;
	}
	public void setFechaHastaDate(Date fechaHastaDate) {
		this.fechaHastaDate = fechaHastaDate;
	}
	@JsonProperty("fechaHasta")
	public String getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	@JsonProperty("fechaFirmeza")
	public String getFechaFirmeza() {
		return fechaFirmeza;
	}
	public void setFechaFirmeza(String fechaFirmeza) {
		this.fechaFirmeza = fechaFirmeza;
	}
	@JsonProperty("rehabilitado")
	public String getRehabilitado() {
		return rehabilitado;
	}
	public void setRehabilitado(String rehabilitado) {
		this.rehabilitado = rehabilitado;
	}

	@JsonProperty("firmeza")
	public String getFirmeza() {
		return firmeza;
	}
	public void setFirmeza(String firmeza) {
		this.firmeza = firmeza;
	}	
	
	@JsonProperty("texto")
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}	
	
	@JsonProperty("observaciones")
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}	
	
	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	
	@JsonProperty("idSancion")
	public String getIdSancion() {
		return idSancion;
	}
	public void setIdSancion(String idSancion) {
		this.idSancion = idSancion;
	}
	
	@JsonProperty("nif")
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	@JsonProperty("fechaNacimiento")
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	@JsonProperty("fechaNacimientoDate")
	public Date getFechaNacimientoDate() {
		return fechaNacimientoDate;
	}
	public void setFechaNacimientoDate(Date fechaNacimientoDate) {
		this.fechaNacimientoDate = fechaNacimientoDate;
	}
	
	@JsonProperty("restablecer")
	public boolean getIsRestablecer() {
		return restablecer;
	}
	public void setIsRestablecer(boolean restablecer) {
		this.restablecer = restablecer;
	}
	
	@Override
	public int compareTo(BusquedaSancionesItem o) {
		return o.getFechaDesdeDate().compareTo(getFechaDesdeDate());
    }
}
