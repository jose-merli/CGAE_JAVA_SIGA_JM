package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class BusquedaJuridicaSearchDTO {

	private String tipo;
	private String nif;
	private String denominacion;
	private Date fechaConstitucion;
	private String integrante;
	private String [] grupos;
	private String sociedadProfesional;
	

	/**
	 *
	 */
	public BusquedaJuridicaSearchDTO tipo(String tipo){
		this.tipo = tipo;
		return this;
	}
	
	
	@JsonProperty("tipo")
	public String getTipo() {
		return tipo;
	}
	
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	/**
	 *
	 */
	public BusquedaJuridicaSearchDTO nif(String nif){
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
	 *
	 */
	public BusquedaJuridicaSearchDTO denominacion(String denominacion){
		this.denominacion = denominacion;
		return this;
	}
	
	
	@JsonProperty("denominacion")
	public String getDenominacion() {
		return denominacion;
	}
	
	
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	
	
	
	/**
	 *
	 */
	public BusquedaJuridicaSearchDTO fechaConstitucion(Date fechaConstitucion){
		this.fechaConstitucion = fechaConstitucion;
		return this;
	}
	
	
	
	@JsonProperty("fechaConstitucion")
	public Date getFechaConstitucion() {
		return fechaConstitucion;
	}
	
	
	public void setFechaConstitucion(Date fechaConstitucion) {
		this.fechaConstitucion = fechaConstitucion;
	}
	
	
	/**
	 *
	 */
	public BusquedaJuridicaSearchDTO integrante(String integrante){
		this.integrante = integrante;
		return this;
	}
	
	
	@JsonProperty("integrante")
	public String getIntegrante() {
		return integrante;
	}
	
	
	public void setIntegrante(String integrante) {
		this.integrante = integrante;
	}
	
	
	/**
	 *
	 */
	public BusquedaJuridicaSearchDTO grupos(String[] grupos){
		this.grupos = grupos;
		return this;
	}
	
	
	@JsonProperty("grupos")
	public String[] getGrupos() {
		return grupos;
	}
	
	
	public void setGrupos(String[] grupos) {
		this.grupos = grupos;
	}
	
	
	/**
	 *
	 */
	public BusquedaJuridicaSearchDTO sociedadProfesional(String sociedadProfesional){
		this.sociedadProfesional = sociedadProfesional;
		return this;
	}
	
	
	@JsonProperty("sociedadProfesional")
	public String getSociedadProfesional() {
		return sociedadProfesional;
	}


	public void setSociedadProfesional(String sociedadProfesional) {
		this.sociedadProfesional = sociedadProfesional;
	}
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    BusquedaJuridicaSearchDTO busquedaJuridicaSearchDTO = (BusquedaJuridicaSearchDTO) o;
	    return Objects.equals(this.tipo, busquedaJuridicaSearchDTO.tipo) &&
	    		Objects.equals(this.nif, busquedaJuridicaSearchDTO.nif) &&
	    		Objects.equals(this.denominacion, busquedaJuridicaSearchDTO.denominacion) &&
	    		Objects.equals(this.fechaConstitucion, busquedaJuridicaSearchDTO.fechaConstitucion) &&
	    		Objects.equals(this.integrante, busquedaJuridicaSearchDTO.integrante) &&
	    		Objects.equals(this.grupos, busquedaJuridicaSearchDTO.grupos) &&
	    		Objects.equals(this.sociedadProfesional, busquedaJuridicaSearchDTO.sociedadProfesional);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(tipo, nif, denominacion, fechaConstitucion, integrante, grupos, sociedadProfesional);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class BusquedaJuridicaSearchDTO {\n");
	    
	    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
	    sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
	    sb.append("    denominacion: ").append(toIndentedString(denominacion)).append("\n");
	    sb.append("    fechaConstitucion: ").append(toIndentedString(fechaConstitucion)).append("\n");
	    sb.append("    integrante: ").append(toIndentedString(integrante)).append("\n");
	    sb.append("    grupos: ").append(toIndentedString(grupos)).append("\n");
	    sb.append("    sociedadProfesional: ").append(toIndentedString(sociedadProfesional)).append("\n");
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