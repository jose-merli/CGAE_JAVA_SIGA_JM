package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class PerJuridicaDatosRegistralesUpdateDTO {

	private String resena;
	private String objetoSocial;
	private String numeroPoliza;
	private String companiaAseg;
	//@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaFin;
	//@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaConstitucion;
	private String sociedadProfesional;
	private String [] actividades;
	private String idPersona;
	private String prefijoNumsspp;
	private String contadorNumsspp;
	private String sufijoNumsspp;
	private Date fechaBaja;
	

	/**
	 */
	public PerJuridicaDatosRegistralesUpdateDTO prefijoNumsspp(String prefijoNumsspp){
		this.prefijoNumsspp = prefijoNumsspp;
		return this;
	}
	
	@JsonProperty("prefijoNumsspp")
	public String getPrefijoNumsspp() {
		return prefijoNumsspp;
	}



	public void setPrefijoNumsspp(String prefijoNumsspp) {
		this.prefijoNumsspp = prefijoNumsspp;
	}


	/**
	 */
	public PerJuridicaDatosRegistralesUpdateDTO contadorNumsspp(String contadorNumsspp){
		this.contadorNumsspp = contadorNumsspp;
		return this;
	}

	@JsonProperty("contadorNumsspp")
	public String getContadorNumsspp() {
		return contadorNumsspp;
	}



	public void setContadorNumsspp(String contadorNumsspp) {
		this.contadorNumsspp = contadorNumsspp;
	}

	

	/**
	 */
	public PerJuridicaDatosRegistralesUpdateDTO sufijoNumsspp(String sufijoNumsspp){
		this.sufijoNumsspp = sufijoNumsspp;
		return this;
	}

	@JsonProperty("sufijoNumsspp")
	public String getSufijoNumsspp() {
		return sufijoNumsspp;
	}



	public void setSufijoNumsspp(String sufijoNumsspp) {
		this.sufijoNumsspp = sufijoNumsspp;
	}

	
	
	

	
	/**
	 *
	 */
	public PerJuridicaDatosRegistralesUpdateDTO resena(String resena){
		this.resena = resena;
		return this;
	}
	
	@JsonProperty("resena")
	public String getResena() {
		return resena;
	}
	
	
	public void setResena(String resena) {
		this.resena = resena;
	}
	
	
	/**
	 *
	 */
	public PerJuridicaDatosRegistralesUpdateDTO objetoSocial(String objetoSocial){
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
	 *
	 */
	public PerJuridicaDatosRegistralesUpdateDTO numeroPoliza(String numeroPoliza){
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
	 *
	 */
	public PerJuridicaDatosRegistralesUpdateDTO companiaAseg(String companiaAseg){
		this.companiaAseg = companiaAseg;
		return this;
	}
	
	
	@JsonProperty("companiaAseg")
	public String getCompaniaAseg() {
		return companiaAseg;
	}
	
	
	public void setCompaniaAseg(String companiaAseg) {
		this.companiaAseg = companiaAseg;
	}
	
	
	/**
	 *
	 */
	public PerJuridicaDatosRegistralesUpdateDTO fechaFin(Date fechaFin){
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
	 *
	 */
	public PerJuridicaDatosRegistralesUpdateDTO fechaConstitucion(Date fechaConstitucion){
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
	public PerJuridicaDatosRegistralesUpdateDTO fechaBaja(Date fechaBaja){
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
	public PerJuridicaDatosRegistralesUpdateDTO sociedadProfesional(String sociedadProfesional){
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
	
	/**
	 *
	 */
	public PerJuridicaDatosRegistralesUpdateDTO actividades(String [] actividades){
		this.actividades = actividades;
		return this;
	}
	
	
	@JsonProperty("actividades")
	public String[] getActividades() {
		return actividades;
	}
	
	
	public void setActividades(String[] actividades) {
		this.actividades = actividades;
	}
	
	
	/**
	 *
	 */
	public PerJuridicaDatosRegistralesUpdateDTO idPersona(String idPersona){
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
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    PerJuridicaDatosRegistralesUpdateDTO perJuridicaDatosRegistralesUpdateDTO = (PerJuridicaDatosRegistralesUpdateDTO) o;
	    return Objects.equals(this.resena, perJuridicaDatosRegistralesUpdateDTO.resena) &&
	    		Objects.equals(this.objetoSocial, perJuridicaDatosRegistralesUpdateDTO.objetoSocial) &&
	    		Objects.equals(this.numeroPoliza, perJuridicaDatosRegistralesUpdateDTO.numeroPoliza) &&
	    		Objects.equals(this.companiaAseg, perJuridicaDatosRegistralesUpdateDTO.companiaAseg) &&
	    		Objects.equals(this.fechaFin, perJuridicaDatosRegistralesUpdateDTO.fechaFin) &&
	    		Objects.equals(this.fechaConstitucion, perJuridicaDatosRegistralesUpdateDTO.fechaConstitucion) &&
	    		Objects.equals(this.sociedadProfesional, perJuridicaDatosRegistralesUpdateDTO.sociedadProfesional) &&
	    		Objects.equals(this.actividades, perJuridicaDatosRegistralesUpdateDTO.actividades) &&
	    		Objects.equals(this.idPersona, perJuridicaDatosRegistralesUpdateDTO.idPersona) &&
	    		Objects.equals(this.prefijoNumsspp, perJuridicaDatosRegistralesUpdateDTO.prefijoNumsspp) &&
	    		Objects.equals(this.contadorNumsspp, perJuridicaDatosRegistralesUpdateDTO.contadorNumsspp) &&
	    		Objects.equals(this.sufijoNumsspp, perJuridicaDatosRegistralesUpdateDTO.sufijoNumsspp);
	}
 
	
	@Override
	public int hashCode() {
	    return Objects.hash(resena, objetoSocial, numeroPoliza, companiaAseg, fechaFin, fechaConstitucion, sociedadProfesional, actividades, idPersona, prefijoNumsspp, contadorNumsspp, sufijoNumsspp);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class PerJuridicaDatosRegistralesUpdateDTO {\n");
	    
	    sb.append("    resena: ").append(toIndentedString(resena)).append("\n");
	    sb.append("    objetoSocial: ").append(toIndentedString(objetoSocial)).append("\n");
	    sb.append("    numeroPoliza: ").append(toIndentedString(numeroPoliza)).append("\n");
	    sb.append("    companiaAseg: ").append(toIndentedString(companiaAseg)).append("\n");
	    sb.append("    fechaFin: ").append(toIndentedString(fechaFin)).append("\n");
	    sb.append("    fechaConstitucion: ").append(toIndentedString(fechaConstitucion)).append("\n");
	    sb.append("    sociedadProfesional: ").append(toIndentedString(sociedadProfesional)).append("\n");
	    sb.append("    actividades: ").append(toIndentedString(actividades)).append("\n");
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    prefijoNumsspp: ").append(toIndentedString(prefijoNumsspp)).append("\n");
	    sb.append("    contadorNumsspp: ").append(toIndentedString(contadorNumsspp)).append("\n");
	    sb.append("    sufijoNumsspp: ").append(toIndentedString(sufijoNumsspp)).append("\n");
	    
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
