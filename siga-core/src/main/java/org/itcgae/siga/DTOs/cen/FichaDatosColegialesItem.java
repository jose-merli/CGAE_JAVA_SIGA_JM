package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class FichaDatosColegialesItem {

	private String idInstitucion;
	private String idPersona;
	private String nif;
	private String denominacion;
	private String abreviatura;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaConstitucion;
	private String tipo;
	private String numeroIntegrantes;
	private String nombresIntegrantes;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaBaja;
	private String idTipoIdentificacion;
	private String sociedadProfesional;
	
	

	/**
	 *
	 */
	public FichaDatosColegialesItem busquedaJuridicaItems(String idInstitucion){
		this.idInstitucion = idInstitucion;
		return this;
	}
	
	
	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}
	
	
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
	
	
	/**
	 *
	 */
	public FichaDatosColegialesItem idPersona(String idPersona){
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
	 *
	 */
	public FichaDatosColegialesItem nif(String nif){
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
	public FichaDatosColegialesItem denominacion(String denominacion){
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
	public FichaDatosColegialesItem abreviatura(String abreviatura){
		this.abreviatura = abreviatura;
		return this;
	}
	
	
	@JsonProperty("abreviatura")
	public String getAbreviatura() {
		return abreviatura;
	}
	
	
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	
	
	/**
	 *
	 */
	public FichaDatosColegialesItem fechaConstitucion(Date fechaConstitucion){
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
	public FichaDatosColegialesItem tipo(String tipo){
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
	public FichaDatosColegialesItem numeroIntegrantes(String numeroIntegrantes){
		this.numeroIntegrantes = numeroIntegrantes;
		return this;
	}
	
	
	@JsonProperty("numeroIntegrantes")
	public String getNumeroIntegrantes() {
		return numeroIntegrantes;
	}
	
	
	public void setNumeroIntegrantes(String numeroIntegrantes) {
		this.numeroIntegrantes = numeroIntegrantes;
	}
	
	
	/**
	 *
	 */
	public FichaDatosColegialesItem nombresIntegrantes(String nombresIntegrantes){
		this.nombresIntegrantes = nombresIntegrantes;
		return this;
	}
	
	
	@JsonProperty("nombresIntegrantes")
	public String getNombresIntegrantes() {
		return nombresIntegrantes;
	}
	
	
	public void setNombresIntegrantes(String nombresIntegrantes) {
		this.nombresIntegrantes = nombresIntegrantes;
	}
	
	
	/**
	 *
	 */
	public FichaDatosColegialesItem fechaBaja(Date fechaBaja){
		this.fechaBaja = fechaBaja;
		return this;
	}
	
	
	@JsonProperty("fechaBaja")
	public Date getFechaBaja() {
		return fechaBaja;
	}
	
	
	
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	
	/**
	 *
	 */
	public FichaDatosColegialesItem idTipoIdentificacion(String idTipoIdentificacion){
		this.idTipoIdentificacion = idTipoIdentificacion;
		return this;
	}
	

	@JsonProperty("idTipoIdentificacion")
	public String getIdTipoIdentificacion() {
		return idTipoIdentificacion;
	}


	public void setIdTipoIdentificacion(String idTipoIdentificacion) {
		this.idTipoIdentificacion = idTipoIdentificacion;
	}

	
	/**
	 *
	 */
	public FichaDatosColegialesItem sociedadProfesional(String sociedadProfesional){
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
	    FichaDatosColegialesItem busquedaJuridicaItem = (FichaDatosColegialesItem) o;
	    return Objects.equals(this.idInstitucion, busquedaJuridicaItem.idInstitucion) &&
	    		Objects.equals(this.idPersona, busquedaJuridicaItem.idPersona) &&
	    		Objects.equals(this.nif, busquedaJuridicaItem.nif) &&
	    		Objects.equals(this.denominacion, busquedaJuridicaItem.denominacion) &&
	    		Objects.equals(this.abreviatura, busquedaJuridicaItem.abreviatura) &&
	    		Objects.equals(this.fechaConstitucion, busquedaJuridicaItem.fechaConstitucion) &&
	    		Objects.equals(this.tipo, busquedaJuridicaItem.tipo) &&
	    		Objects.equals(this.numeroIntegrantes, busquedaJuridicaItem.numeroIntegrantes) &&
	    		Objects.equals(this.nombresIntegrantes, busquedaJuridicaItem.nombresIntegrantes) &&
	    		Objects.equals(this.fechaBaja, busquedaJuridicaItem.fechaBaja) &&  
	    		Objects.equals(this.idTipoIdentificacion, busquedaJuridicaItem.idTipoIdentificacion) &&
	    		Objects.equals(this.sociedadProfesional, busquedaJuridicaItem.sociedadProfesional);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idInstitucion, idPersona, nif, denominacion, abreviatura, fechaConstitucion, tipo, numeroIntegrantes, nombresIntegrantes, fechaBaja, idTipoIdentificacion, sociedadProfesional);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class BusquedaPerJuridicaItem {\n");
	    
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
	    sb.append("    denominacion: ").append(toIndentedString(denominacion)).append("\n");
	    sb.append("    abreviatura: ").append(toIndentedString(abreviatura)).append("\n");
	    sb.append("    fechaConstitucion: ").append(toIndentedString(fechaConstitucion)).append("\n");
	    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
	    sb.append("    numeroIntegrantes: ").append(toIndentedString(numeroIntegrantes)).append("\n");
	    sb.append("    nombresIntegrantes: ").append(toIndentedString(nombresIntegrantes)).append("\n");
	    sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
	    sb.append("    idTipoIdentificacion: ").append(toIndentedString(idTipoIdentificacion)).append("\n");
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
