package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CargaMasivaItem {


	private String nombreFichero;
	private String usuario;
	private Short registrosCorrectos;
	private String fechaCarga;
	private String fechaCargaDesde ;
	private String fechaCargaHasta;
	private String fechaSolicitudDesde;
	private String tipoCarga;
	private Long idFichero;
	private Long idFicheroLog;
	private Short registrosErroneos;
	

	/**
	 *
	 */
	public CargaMasivaItem nombreFichero(String nombreFichero){
		this.nombreFichero = nombreFichero;
		return this;
	}
	
	@JsonProperty("nombreFichero")
	public String getNombreFichero() {
		return nombreFichero;
	}
	
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	
	/**
	 *
	 */
	public CargaMasivaItem usuario(String usuario){
		this.usuario = usuario;
		return this;
	}
	
	@JsonProperty("usuario")
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
		
	/**
	 *
	 */
	public CargaMasivaItem registrosCorrectos(Short registrosCorrectos){
		this.registrosCorrectos = registrosCorrectos;
		return this;
	}
	
	@JsonProperty("registrosCorrectos")
	public Short getRegistrosCorrectos() {
		return registrosCorrectos;
	}
	public void setRegistrosCorrectos(Short registrosCorrectos) {
		this.registrosCorrectos = registrosCorrectos;
	}
	
	/**
	 *
	 */
	public CargaMasivaItem fechaCarga(String fechaCarga){
		this.fechaCarga = fechaCarga;
		return this;
	}

	@JsonProperty("fechaCarga")
	public String getFechaCarga() {
		return fechaCarga;
	}
	
	public void setFechaCarga(String fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	
	/**
	 *
	 */
	public CargaMasivaItem tipoCarga(String tipoCarga){
		this.tipoCarga = tipoCarga;
		return this;
	}
	
	@JsonProperty("tipoCarga")
	public String getTipoCarga() {
		return tipoCarga;
	}
	
	public void setTipoCarga(String tipoCarga) {
		this.tipoCarga = tipoCarga;
	}
	
	/**
	 *
	 */
	public CargaMasivaItem idFichero(Long idFichero){
		this.idFichero = idFichero;
		return this;
	}

	@JsonProperty("idFichero")
	public Long getIdFichero() {
		return idFichero;
	}
	
	public void setIdFichero(Long idFichero) {
		this.idFichero = idFichero;
	}
	
	/**
	 *
	 */
	public CargaMasivaItem idFicheroLog(Long idFicheroLog){
		this.idFicheroLog = idFicheroLog;
		return this;
	}
	
	@JsonProperty("idFicheroLog")
	public Long getIdFicheroLog() {
		return idFicheroLog;
	}
	
	public void setIdFicheroLog(Long idFicheroLog) {
		this.idFicheroLog = idFicheroLog;
	}
	
	/**
	 *
	 */
	public CargaMasivaItem registrosErroneos(Short registrosErroneos){
		this.registrosErroneos = registrosErroneos;
		return this;
	}
	
	@JsonProperty("registrosErroneos")
	public Short getRegistrosErroneos() {
		return registrosErroneos;
	}
	
	public void setRegistrosErroneos(Short registrosErroneos) {
		this.registrosErroneos = registrosErroneos;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(nombreFichero, usuario, registrosCorrectos, fechaCarga, tipoCarga, idFichero, idFicheroLog, registrosErroneos);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class CargaMasivaItem {\n");
	    
	    sb.append("    nombreFichero: ").append(toIndentedString(nombreFichero)).append("\n");
	    sb.append("    usuario: ").append(toIndentedString(usuario)).append("\n");
	    sb.append("    registros: ").append(toIndentedString(registrosCorrectos)).append("\n");
	    sb.append("    fechaCarga: ").append(toIndentedString(fechaCarga)).append("\n");
	    sb.append("    tipoCarga: ").append(toIndentedString(tipoCarga)).append("\n");
	    sb.append("    idFichero: ").append(toIndentedString(idFichero)).append("\n");
	    sb.append("    idFicheroLog: ").append(toIndentedString(idFicheroLog)).append("\n");
	    sb.append("    registrosErroneos: ").append(toIndentedString(registrosErroneos)).append("\n");
	    sb.append("}");
	    return sb.toString();
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

	public String getFechaCargaDesde() {
		return fechaCargaDesde;
	}

	public void setFechaCargaDesde(String fechaCargaDesde) {
		this.fechaCargaDesde = fechaCargaDesde;
	}

	public String getFechaCargaHasta() {
		return fechaCargaHasta;
	}

	public void setFechaCargaHasta(String fechaCargaHasta) {
		this.fechaCargaHasta = fechaCargaHasta;
	}

	public String getFechaSolicitudDesde() {
		return fechaSolicitudDesde;
	}

	public void setFechaSolicitudDesde(String fechaSolicitudDesde) {
		this.fechaSolicitudDesde = fechaSolicitudDesde;
	}



	
}
