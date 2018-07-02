package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class SociedadesEditadasDTO {
	
	
	private String sociedadNif;
	private String sociedadDenominacion;
	private String sociedadFormaSocial;
	private Date sociedadFechaAlta;
	private String notarioIdentificacion;
	private String notarioNombre;
	private String notarioApellido1;
	private String notarioApellido2;
	private String resena;
	private String objetoSocial;
	private Date fechaCancelacion;
	private String identificacionRegistro;
	private String numeroRegistro;
	private Date fechaBaja;
	private Date fechaConstitucion;
	private String sociedadProfesional;
	private String idPersona;

	public String getSociedadNif() {
		return sociedadNif;
	}


	public void setSociedadNif(String sociedadNif) {
		this.sociedadNif = sociedadNif;
	}


	public String getSociedadDenominacion() {
		return sociedadDenominacion;
	}


	public void setSociedadDenominacion(String sociedadDenominacion) {
		this.sociedadDenominacion = sociedadDenominacion;
	}


	public String getSociedadFormaSocial() {
		return sociedadFormaSocial;
	}


	public void setSociedadFormaSocial(String sociedadFormaSocial) {
		this.sociedadFormaSocial = sociedadFormaSocial;
	}


	public Date getSociedadFechaAlta() {
		return sociedadFechaAlta;
	}


	public void setSociedadFechaAlta(Date sociedadFechaAlta) {
		this.sociedadFechaAlta = sociedadFechaAlta;
	}


	public String getNotarioIdentificacion() {
		return notarioIdentificacion;
	}


	public void setNotarioIdentificacion(String notarioIdentificacion) {
		this.notarioIdentificacion = notarioIdentificacion;
	}


	public String getNotarioNombre() {
		return notarioNombre;
	}


	public void setNotarioNombre(String notarioNombre) {
		this.notarioNombre = notarioNombre;
	}


	public String getNotarioApellido1() {
		return notarioApellido1;
	}


	public void setNotarioApellido1(String notarioApellido1) {
		this.notarioApellido1 = notarioApellido1;
	}


	public String getNotarioApellido2() {
		return notarioApellido2;
	}


	public void setNotarioApellido2(String notarioApellido2) {
		this.notarioApellido2 = notarioApellido2;
	}


	public String getResena() {
		return resena;
	}


	public void setResena(String resena) {
		this.resena = resena;
	}


	public String getObjetoSocial() {
		return objetoSocial;
	}


	public void setObjetoSocial(String objetoSocial) {
		this.objetoSocial = objetoSocial;
	}


	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}


	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}


	public String getIdentificacionRegistro() {
		return identificacionRegistro;
	}


	public void setIdentificacionRegistro(String identificacionRegistro) {
		this.identificacionRegistro = identificacionRegistro;
	}


	public String getNumeroRegistro() {
		return numeroRegistro;
	}


	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}


	
	
	
	
	
	/**
	 */
	public SociedadesEditadasDTO fechaConstitucion(Date fechaConstitucion){
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
	public SociedadesEditadasDTO fechaBaja(Date fechaBaja){
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
	 */
	public SociedadesEditadasDTO sociedadProfesional(String sociedadProfesional){
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
	    SociedadesEditadasDTO sociedadesEditadasDTO = (SociedadesEditadasDTO) o;
	    return Objects.equals(this.sociedadNif, sociedadesEditadasDTO.sociedadNif) &&
	    		Objects.equals(this.sociedadDenominacion, sociedadesEditadasDTO.sociedadDenominacion) &&
	    		Objects.equals(this.sociedadFormaSocial, sociedadesEditadasDTO.sociedadFormaSocial) &&
	    		Objects.equals(this.sociedadFechaAlta, sociedadesEditadasDTO.sociedadFechaAlta) &&
	    		Objects.equals(this.notarioIdentificacion, sociedadesEditadasDTO.notarioIdentificacion) &&
	    		Objects.equals(this.notarioNombre, sociedadesEditadasDTO.notarioNombre) &&
	    		Objects.equals(this.notarioApellido1, sociedadesEditadasDTO.notarioApellido1) &&
	    		Objects.equals(this.notarioApellido2, sociedadesEditadasDTO.notarioApellido2) &&
	    		Objects.equals(this.resena, sociedadesEditadasDTO.resena) &&
	    		Objects.equals(this.fechaCancelacion, sociedadesEditadasDTO.fechaCancelacion) &&
	    		Objects.equals(this.identificacionRegistro, sociedadesEditadasDTO.identificacionRegistro) &&
	    		Objects.equals(this.numeroRegistro, sociedadesEditadasDTO.numeroRegistro) &&
	    		Objects.equals(this.fechaBaja, sociedadesEditadasDTO.fechaBaja) &&
	    		Objects.equals(this.fechaConstitucion, sociedadesEditadasDTO.fechaConstitucion) &&
	    		Objects.equals(this.idPersona, sociedadesEditadasDTO.idPersona) &&
	    		Objects.equals(this.sociedadProfesional, sociedadesEditadasDTO.sociedadProfesional);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(sociedadNif, sociedadDenominacion, sociedadFormaSocial, sociedadFechaAlta, notarioIdentificacion, notarioNombre, notarioApellido1, notarioApellido2, resena,idPersona, fechaBaja,fechaConstitucion, identificacionRegistro, numeroRegistro, sociedadProfesional, fechaCancelacion);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class SociedadesEditadasDTO {\n");
	    
	    sb.append("    sociedadNif: ").append(toIndentedString(sociedadNif)).append("\n");
	    sb.append("    sociedadDenominacion: ").append(toIndentedString(sociedadDenominacion)).append("\n");
	    sb.append("    sociedadFormaSocial: ").append(toIndentedString(sociedadFormaSocial)).append("\n");
	    sb.append("    sociedadFechaAlta: ").append(toIndentedString(sociedadFechaAlta)).append("\n");
	    sb.append("    notarioIdentificacion: ").append(toIndentedString(notarioIdentificacion)).append("\n");
	    sb.append("    notarioNombre: ").append(toIndentedString(notarioNombre)).append("\n");
	    sb.append("    notarioApellido1: ").append(toIndentedString(notarioApellido1)).append("\n");
	    sb.append("    notarioApellido2: ").append(toIndentedString(notarioApellido2)).append("\n");
	    sb.append("    resena: ").append(toIndentedString(resena)).append("\n");
	    sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
	    sb.append("    identificacionRegistro: ").append(toIndentedString(identificacionRegistro)).append("\n");
	    sb.append("    numeroRegistro: ").append(toIndentedString(numeroRegistro)).append("\n");
	    sb.append("    sociedadProfesional: ").append(toIndentedString(sociedadProfesional)).append("\n");
	    sb.append("    fechaCancelacion: ").append(toIndentedString(fechaCancelacion)).append("\n"); 
	    sb.append("    fechaConstitucion: ").append(toIndentedString(fechaConstitucion)).append("\n"); 
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n"); 
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
