package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class DatosRegistralesItem {
	
	private String resenia;
	private String objetoSocial;
	private String numeroPoliza;
	private String companiaSeg;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaFin;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaConstitucion;
	private String sociedadProfesional;
	
	
	
	/**
	 */
	public DatosRegistralesItem resenia(String resenia){
		this.resenia = resenia;
		return this;
	}
	
	
	
	@JsonProperty("resenia")
	public String getResenia() {
		return resenia;
	}
	
	
	public void setResenia(String resenia) {
		this.resenia = resenia;
	}
	
	
	/**
	 */
	public DatosRegistralesItem objetoSocial(String objetoSocial){
		this.objetoSocial = objetoSocial;
		return this;
	}
	
	@JsonProperty("objetoSocial")
	public String getObjetoSocial() {
		return objetoSocial;
	}
	
	
	public void setObjetoSocial(String objetoSocial) {
		this.objetoSocial = objetoSocial;
	}
	
	/**
	 */
	public DatosRegistralesItem numeroPoliza(String numeroPoliza){
		this.numeroPoliza = numeroPoliza;
		return this;
	}
	
	@JsonProperty("numeroPoliza")
	public String getNumeroPoliza() {
		return numeroPoliza;
	}
	
	
	public void setNumeroPoliza(String numeroPoliza) {
		this.numeroPoliza = numeroPoliza;
	}
	
	
	/**
	 */
	public DatosRegistralesItem companiaSeg(String companiaSeg){
		this.companiaSeg = companiaSeg;
		return this;
	}
	
	@JsonProperty("companiaSeg")
	public String getCompaniaSeg() {
		return companiaSeg;
	}
	
	
	public void setCompaniaSeg(String companiaSeg) {
		this.companiaSeg = companiaSeg;
	}
	
	
	/**
	 */
	public DatosRegistralesItem fechaFin(Date fechaFin){
		this.fechaFin = fechaFin;
		return this;
	}
	
	@JsonProperty("fechaFin")
	public Date getFechaFin() {
		return fechaFin;
	}
	
	
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
	/**
	 */
	public DatosRegistralesItem fechaConstitucion(Date fechaConstitucion){
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
	 */
	public DatosRegistralesItem sociedadProfesional(String sociedadProfesional){
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
	    DatosRegistralesItem datosRegistralesItem = (DatosRegistralesItem) o;
	    return Objects.equals(this.resenia, datosRegistralesItem.resenia) &&
	    	   Objects.equals(this.objetoSocial, datosRegistralesItem.objetoSocial) &&
	    	   Objects.equals(this.numeroPoliza, datosRegistralesItem.numeroPoliza) &&
	    	   Objects.equals(this.companiaSeg, datosRegistralesItem.companiaSeg) &&
	    	   Objects.equals(this.fechaFin, datosRegistralesItem.fechaFin) &&
	    	   Objects.equals(this.fechaConstitucion, datosRegistralesItem.fechaConstitucion) &&
	    	   Objects.equals(this.sociedadProfesional, datosRegistralesItem.sociedadProfesional);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(resenia, objetoSocial, numeroPoliza, companiaSeg, fechaFin, fechaConstitucion, sociedadProfesional);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosRegistralesItem {\n");
	    
	    sb.append("    resenia: ").append(toIndentedString(resenia)).append("\n");
	    sb.append("    objetoSocial: ").append(toIndentedString(objetoSocial)).append("\n");
	    sb.append("    numeroPoliza: ").append(toIndentedString(numeroPoliza)).append("\n");
	    sb.append("    companiaSeg: ").append(toIndentedString(companiaSeg)).append("\n");
	    sb.append("    fechaFin: ").append(toIndentedString(fechaFin)).append("\n");
	    sb.append("    fechaConstitucion: ").append(toIndentedString(fechaConstitucion)).append("\n");
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
