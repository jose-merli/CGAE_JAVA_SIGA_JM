package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class EtiquetaUpdateDTO {

	private String nif;
	private String tipo;
	private String denominacion;
	private String idioma;
	private String cuentaContable;
	private String anotaciones;
	private String [] grupos;
	private String idPersona;
	private String abreviatura;
	private String motivo;
	
	
	/**
	 */
	public EtiquetaUpdateDTO abreviatura(String abreviatura){
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
	 */
	public EtiquetaUpdateDTO nif(String nif){
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
	public EtiquetaUpdateDTO tipo(String tipo){
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
	 */
	public EtiquetaUpdateDTO denominacion(String denominacion){
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
	 */
	public EtiquetaUpdateDTO idioma(String idioma){
		this.denominacion = idioma;
		return this;
	}
	
	@JsonProperty("idioma")
	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	
	/**
	 */
	public EtiquetaUpdateDTO cuentaContable(String cuentaContable){
		this.cuentaContable = cuentaContable;
		return this;
	}

	@JsonProperty("cuentaContable")
	public String getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	
	/**
	 */
	public EtiquetaUpdateDTO anotaciones(String anotaciones){
		this.anotaciones = anotaciones;
		return this;
	}
	
	@JsonProperty("anotaciones")
	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	/**
	 */
	public EtiquetaUpdateDTO grupos(String [] grupos){
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
	 */
	public EtiquetaUpdateDTO idPersona(String idPersona){
		this.nif = idPersona;
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
	public EtiquetaUpdateDTO motivo(String motivo){
		this.motivo = motivo;
		return this;
	}
	
	@JsonProperty("motivo")
	public String getMotivo() {
		return motivo;
	}


	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    EtiquetaUpdateDTO etiquetaUpdateDTO = (EtiquetaUpdateDTO) o;
	    return Objects.equals(this.tipo, etiquetaUpdateDTO.tipo) &&
	    		Objects.equals(this.nif, etiquetaUpdateDTO.nif) &&
	    		Objects.equals(this.denominacion, etiquetaUpdateDTO.denominacion) &&
	    		Objects.equals(this.idioma, etiquetaUpdateDTO.idioma) &&
	    		Objects.equals(this.cuentaContable, etiquetaUpdateDTO.cuentaContable) &&
	    		Objects.equals(this.anotaciones, etiquetaUpdateDTO.anotaciones) &&
	    		Objects.equals(this.grupos, etiquetaUpdateDTO.grupos) &&
	    		Objects.equals(this.idPersona, etiquetaUpdateDTO.idPersona) &&
	    		Objects.equals(this.motivo, etiquetaUpdateDTO.motivo);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(tipo, nif, denominacion, idioma, cuentaContable, anotaciones, grupos, idPersona, motivo);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class EtiquetaUpdateDTO {\n");
	    
	    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
	    sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
	    sb.append("    denominacion: ").append(toIndentedString(denominacion)).append("\n");
	    sb.append("    idioma: ").append(toIndentedString(idioma)).append("\n");
	    sb.append("    cuentaContable: ").append(toIndentedString(cuentaContable)).append("\n");
	    sb.append("    anotaciones: ").append(toIndentedString(anotaciones)).append("\n");
	    sb.append("    grupos: ").append(toIndentedString(grupos)).append("\n");
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    motivo: ").append(toIndentedString(motivo)).append("\n");
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
