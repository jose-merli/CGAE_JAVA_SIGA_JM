package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class BusquedaPerFisicaSearchDTO {

	private String nif;
	private String idInstitucion[];
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String numeroColegiado;
	private boolean addDestinatarioIndv;
	private boolean fromDesignaciones;
	

 
	/**
	 *
	 */
	public BusquedaPerFisicaSearchDTO nif(String nif){
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
	
	
	 
	
	public boolean isFromDesignaciones() {
		return fromDesignaciones;
	}


	public void setFromDesignaciones(boolean fromDesignaciones) {
		this.fromDesignaciones = fromDesignaciones;
	}


	/**
	 *
	 */
	public BusquedaPerFisicaSearchDTO idInstitucion(String[] idInstitucion){
		this.idInstitucion = idInstitucion;
		return this;
	}
	
	
	@JsonProperty("idInstitucion")
	public String[] getIdInstitucion() {
		return idInstitucion;
	}


	public void setIdInstitucion(String[] idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	

	/**
	 *
	 */
	public BusquedaPerFisicaSearchDTO nombre(String nombre){
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
	 *
	 */
	public BusquedaPerFisicaSearchDTO primerApellido(String  primerApellido){
		this.primerApellido = primerApellido;
		return this;
	}
	
	@JsonProperty("primerApellido")
	public String getPrimerApellido() {
		return primerApellido;
	}


	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	/**
	 *
	 */
	public BusquedaPerFisicaSearchDTO segundoApellido(String  segundoApellido){
		this.segundoApellido = segundoApellido;
		return this;
	}
	@JsonProperty("segundoApellido")
	public String getSegundoApellido() {
		return segundoApellido;
	}


	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	/**
	 *
	 */
	public BusquedaPerFisicaSearchDTO numColegiado(String  numeroColegiado){
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
	 *
	 */
	public BusquedaPerFisicaSearchDTO addDestinatarioIndv(boolean  addDestinatarioIndv){
		this.addDestinatarioIndv = addDestinatarioIndv;
		return this;
	}
	@JsonProperty("addDestinatarioIndv")
	public boolean getAddDestinatarioIndv() {
		return addDestinatarioIndv;
	}


	public void setAddDestinatarioIndv(boolean addDestinatarioIndv) {
		this.addDestinatarioIndv = addDestinatarioIndv;
	}

	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    BusquedaPerFisicaSearchDTO busquedaFisicaSearchDTO = (BusquedaPerFisicaSearchDTO) o;
	    return  
	    		Objects.equals(this.nif, busquedaFisicaSearchDTO.nif) &&
	    		Objects.equals(this.idInstitucion, busquedaFisicaSearchDTO.idInstitucion)&&
	    		Objects.equals(this.nombre, busquedaFisicaSearchDTO.nombre)&&
	    		Objects.equals(this.primerApellido, busquedaFisicaSearchDTO.primerApellido)&&
	    		Objects.equals(this.segundoApellido, busquedaFisicaSearchDTO.segundoApellido)&&
	    		Objects.equals(this.numeroColegiado, busquedaFisicaSearchDTO.numeroColegiado)&&
	    		Objects.equals(this.addDestinatarioIndv, busquedaFisicaSearchDTO.addDestinatarioIndv);
	}
	
	

	@Override
	public int hashCode() {
	    return Objects.hash(nif, idInstitucion, nombre, primerApellido, segundoApellido, numeroColegiado, addDestinatarioIndv);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class BusquedaPerFisicaSearchDTO {\n");
	    
	    sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
	    sb.append("    primerApellido: ").append(toIndentedString(primerApellido)).append("\n");
	    sb.append("    segundoApellido: ").append(toIndentedString(segundoApellido)).append("\n");
	    sb.append("    numColegiado: ").append(toIndentedString(numeroColegiado)).append("\n");
	    sb.append("    addDestinatarioIndv: ").append(toIndentedString(addDestinatarioIndv)).append("\n");

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
