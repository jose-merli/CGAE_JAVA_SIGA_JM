package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SociedadCreateDTO {

	private String nif;
	private String tipo;
	private Date fechaConstitucion;
	private Date fechaAlta;
	private Date fechaBaja;
	private String denominacion;
	private String abreviatura;
	private String idioma;
	private String cuentaContable;
	private String anotaciones;
	private String [] grupos;
	private ComboEtiquetasItem[] etiquetas;
	private String idPersona;
	private String motivo;
	
	
	/**
	 */
	public SociedadCreateDTO fechaAlta(Date fechaAlta){
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
	public SociedadCreateDTO fechaConstitucion(Date fechaConstitucion){
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
	public SociedadCreateDTO fechaBaja(Date fechaBaja){
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
	public SociedadCreateDTO abreviatura(String abreviatura){
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
	public SociedadCreateDTO nif(String nif){
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
	public SociedadCreateDTO tipo(String tipo){
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
	public SociedadCreateDTO denominacion(String denominacion){
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
	public SociedadCreateDTO idioma(String idioma){
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
	public SociedadCreateDTO cuentaContable(String cuentaContable){
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
	public SociedadCreateDTO anotaciones(String anotaciones){
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
	public SociedadCreateDTO grupos(String [] grupos){
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
	public SociedadCreateDTO etiquetas(ComboEtiquetasItem[] etiquetas){
		this.etiquetas = etiquetas;
		return this;
	}
	
	@JsonProperty("etiquetas")
	public ComboEtiquetasItem[] getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(ComboEtiquetasItem[] etiquetas) {
		this.etiquetas = etiquetas;
	}
	
	
	
	/**
	 */
	public SociedadCreateDTO idPersona(String idPersona){
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
	public SociedadCreateDTO motivo(String motivo){
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
	    SociedadCreateDTO sociedadCreateDTO = (SociedadCreateDTO) o;
	    return Objects.equals(this.tipo, sociedadCreateDTO.tipo) &&
	    		Objects.equals(this.nif, sociedadCreateDTO.nif) &&
	    		Objects.equals(this.denominacion, sociedadCreateDTO.denominacion) &&
	    		Objects.equals(this.idioma, sociedadCreateDTO.idioma) &&
	    		Objects.equals(this.cuentaContable, sociedadCreateDTO.cuentaContable) &&
	    		Objects.equals(this.anotaciones, sociedadCreateDTO.anotaciones) &&
	    		Objects.equals(this.grupos, sociedadCreateDTO.grupos) &&
	    		Objects.equals(this.idPersona, sociedadCreateDTO.idPersona) &&
	    		Objects.equals(this.fechaConstitucion, sociedadCreateDTO.fechaConstitucion) &&
	    		Objects.equals(this.fechaBaja, sociedadCreateDTO.fechaBaja) &&
	    		Objects.equals(this.motivo, sociedadCreateDTO.motivo);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(tipo, nif, denominacion, idioma, cuentaContable, anotaciones, grupos, idPersona, fechaConstitucion, fechaBaja, motivo);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class SociedadCreateDTO {\n");
	    
	    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
	    sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
	    sb.append("    denominacion: ").append(toIndentedString(denominacion)).append("\n");
	    sb.append("    idioma: ").append(toIndentedString(idioma)).append("\n");
	    sb.append("    cuentaContable: ").append(toIndentedString(cuentaContable)).append("\n");
	    sb.append("    anotaciones: ").append(toIndentedString(anotaciones)).append("\n");
	    sb.append("    grupos: ").append(toIndentedString(grupos)).append("\n");
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n"); 
	    sb.append("    fechaConstitucion: ").append(toIndentedString(fechaConstitucion)).append("\n");
	    sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
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