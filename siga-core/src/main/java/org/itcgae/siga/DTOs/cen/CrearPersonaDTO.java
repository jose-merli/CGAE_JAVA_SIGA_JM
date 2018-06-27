package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class CrearPersonaDTO {

	private String nif;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String tipoIdentificacion;
	
	
	/**
	 */
	public CrearPersonaDTO nif(String nif){
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
	public CrearPersonaDTO nombre(String nombre){
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
	public CrearPersonaDTO apellido1(String apellido1){
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
	public CrearPersonaDTO apellido2(String apellido2){
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
	public CrearPersonaDTO tipoIdentificacion(String tipoIdentificacion){
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
	
	
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    CrearPersonaDTO crearPersonaDTO = (CrearPersonaDTO) o;
	    return Objects.equals(this.nif, crearPersonaDTO.nif) &&
	    		Objects.equals(this.nombre, crearPersonaDTO.nombre) &&
	    		Objects.equals(this.apellido1, crearPersonaDTO.apellido1) &&
	    		Objects.equals(this.apellido2, crearPersonaDTO.apellido2) &&
	    		Objects.equals(this.tipoIdentificacion, crearPersonaDTO.tipoIdentificacion);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(nif, nombre,apellido1,apellido2,tipoIdentificacion);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class CrearPersonaDTO {\n");
	    
	    sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
	    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
	    sb.append("    apellido1: ").append(toIndentedString(apellido1)).append("\n");
	    sb.append("    apellido2: ").append(toIndentedString(apellido2)).append("\n");
	    sb.append("    tipoIdentificacion: ").append(toIndentedString(tipoIdentificacion)).append("\n");
	    
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
