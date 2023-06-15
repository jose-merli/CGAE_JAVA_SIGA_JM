package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FichaPersonaItem {
	
	private String colegio;
	private String idPersona;
	private String nif;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String numeroColegiado;
	private String residente;
	private String situacion;
	private String tipoIdentificacion;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaAlta;
	private String colegiado;
	
	
	/**
	 */
	public FichaPersonaItem colegio(String colegio){
		this.colegio = colegio;
		return this;
	}
	
	
	@JsonProperty("colegio")
	public String getColegio() {
		return colegio;
	}
	public void setColegio(String colegio) {
		this.colegio = colegio;
	}
	
	
	/**
	 */
	public FichaPersonaItem idPersona(String idPersona){
		this.idPersona = idPersona;
		return this;
	}
	
	
	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	
	
	/**
	 */
	public FichaPersonaItem nif(String nif){
		this.nif = nif;
		return this;
	}
	
	
	@JsonProperty("nif")
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	
	/**
	 */
	public FichaPersonaItem nombre(String nombre){
		this.nombre = nombre;
		return this;
	}
	
	
	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	/**
	 */
	public FichaPersonaItem numeroColegiado(String numeroColegiado){
		this.numeroColegiado = numeroColegiado;
		return this;
	}
	
	
	@JsonProperty("numeroColegiado")
	public String getNumeroColegiado() {
		return numeroColegiado;
	}
	public void setNumeroColegiado(String numeroColegiado) {
		this.numeroColegiado = numeroColegiado;
	}
	
	
	/**
	 */
	public FichaPersonaItem residente(String residente){
		this.residente = residente;
		return this;
	}
	
	@JsonProperty("residente")
	public String getResidente() {
		return residente;
	}
	public void setResidente(String residente) {
		this.residente = residente;
	}
	
	

	/**
	 */
	public FichaPersonaItem situacion(String situacion){
		this.situacion = situacion;
		return this;
	}
	
	@JsonProperty("situacion")
	public String getSituacion() {
		return situacion;
	}
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	
	/**
	 */
	public FichaPersonaItem fechaAlta(Date fechaAlta){
		this.fechaAlta = fechaAlta;
		return this;
	}
	
	@JsonProperty("fechaAlta")
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	
	/**
	 */
	public FichaPersonaItem apellido1(String apellido1){
		this.apellido1 = apellido1;
		return this;
	}

	
	
	@JsonProperty("apellido1")
	public String getApellido1() {
		return apellido1;
	}


	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	
	
	/**
	 */
	public FichaPersonaItem apellido2(String apellido2){
		this.apellido2 = apellido2;
		return this;
	}

	
	@JsonProperty("apellido2")
	public String getApellido2() {
		return apellido2;
	}


	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}


	
	/**
	 */
	public FichaPersonaItem tipoIdentificacion(String tipoIdentificacion){
		this.tipoIdentificacion = tipoIdentificacion;
		return this;
	}
	
	@JsonProperty("tipoIdentificacion")
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}


	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	
	/**
	 */
	
	@JsonProperty("colegiado")	
	public String getColegiado() {
		return colegiado;
	}


	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    FichaPersonaItem fichaPersonaItem = (FichaPersonaItem) o;
	    return Objects.equals(this.colegio, fichaPersonaItem.colegio) &&
	    		Objects.equals(this.idPersona, fichaPersonaItem.idPersona) &&
	    		Objects.equals(this.nif, fichaPersonaItem.nif) &&
	    		Objects.equals(this.nombre, fichaPersonaItem.nombre) &&
	    		Objects.equals(this.apellido1, fichaPersonaItem.apellido1) &&
	    		Objects.equals(this.apellido2, fichaPersonaItem.apellido2) &&
	    		Objects.equals(this.numeroColegiado, fichaPersonaItem.numeroColegiado) &&
	    		Objects.equals(this.residente, fichaPersonaItem.residente) &&
	    		Objects.equals(this.situacion, fichaPersonaItem.situacion) &&
	    		Objects.equals(this.tipoIdentificacion, fichaPersonaItem.tipoIdentificacion) &&
	    		Objects.equals(this.fechaAlta, fichaPersonaItem.fechaAlta);
	}
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(colegio, idPersona, nif, nombre, apellido1, apellido2, numeroColegiado, residente, situacion, tipoIdentificacion, fechaAlta);
	}
	
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class FichaPersonaItem {\n");
	    
	    sb.append("    colegio: ").append(toIndentedString(colegio)).append("\n");
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
	    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
	    sb.append("    apellido1: ").append(toIndentedString(apellido1)).append("\n");
	    sb.append("    apellido2: ").append(toIndentedString(apellido2)).append("\n");
	    sb.append("    numeroColegiado: ").append(toIndentedString(numeroColegiado)).append("\n");
	    sb.append("    residente: ").append(toIndentedString(residente)).append("\n");
	    sb.append("    situacion: ").append(toIndentedString(situacion)).append("\n");
	    sb.append("    tipoIdentificacion: ").append(toIndentedString(tipoIdentificacion)).append("\n");
	    sb.append("    fechaAlta: ").append(toIndentedString(fechaAlta)).append("\n");

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

	
	
	
	
}