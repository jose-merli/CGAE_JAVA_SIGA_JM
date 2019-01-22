package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CargaMasivaDatosGFItem {
	
	private Short idGrupo;
	private String nombreGrupo;
	private Short idInstitucion;
	private String personaNif;
	private String colegiadoNumero;
	private String accion;
	private boolean general;
	private String personaNombre;
	private Long idPersona;
	private Short idInstitucionGrupo;
	private String errores;
	private Date fechaInicio;
	private Date fechaFin;
	
	private String codIdioma;
	private Integer usumodificacion;
	private String nombreFichero;
	
	/**
	 *
	 */
	public CargaMasivaDatosGFItem idGrupo(Short idGrupo){
		this.idGrupo = idGrupo;
		return this;
	}
	
	@JsonProperty("idGrupo")
	public Short getIdGrupo() {
		return idGrupo;
	}
	
	public void setIdGrupo(Short idGrupo) {
		this.idGrupo = idGrupo;
	}
	
	/**
	 *
	 */
	public CargaMasivaDatosGFItem personaNif(String personaNif){
		this.personaNif = personaNif;
		return this;
	}
	
	@JsonProperty("personaNif")
	public String getPersonaNif() {
		return personaNif;
	}
	
	public void setPersonaNif(String personaNif) {
		this.personaNif = personaNif;
	}
	
	/**
	 *
	 */
	public CargaMasivaDatosGFItem nombreGrupo(String nombreGrupo){
		this.nombreGrupo = nombreGrupo;
		return this;
	}
	
	@JsonProperty("nombreGrupo")
	public String getNombreGrupo() {
		return nombreGrupo;
	}
	
	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	
	/**
	 *
	 */
	public CargaMasivaDatosGFItem general(boolean general){
		this.general = general;
		return this;
	}
	
	@JsonProperty("general")
	public boolean getGeneral() {
		return general;
	}
	
	public void setGeneral(boolean general) {
		this.general = general;
	}
	
	/**
	 *
	 */
	public CargaMasivaDatosGFItem accion(String accion){
		this.accion = accion;
		return this;
	}
	
	@JsonProperty("accion")
	public String getAccion() {
		return accion;
	}
	
	public void setAccion(String accion) {
		this.accion = accion;
	}
	
	/**
	 *
	 */
	public CargaMasivaDatosGFItem idInstitucion(Short idInstitucion){
		this.idInstitucion = idInstitucion;
		return this;
	}
	
	@JsonProperty("idInstitucion")
	public Short getIdInstitucion() {
		return idInstitucion;
	}
	
	public void setIdInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
	/**
	 *
	 */
	public CargaMasivaDatosGFItem colegiadoNumero(String colegiadoNumero){
		this.colegiadoNumero = colegiadoNumero;
		return this;
	}
	
	@JsonProperty("colegiadoNumero")
	public String getColegiadoNumero() {
		return colegiadoNumero;
	}
	
	public void setColegiadoNumero(String colegiadoNumero) {
		this.colegiadoNumero = colegiadoNumero;
	}
	
	/**
	 *
	 */
	public CargaMasivaDatosGFItem error(String errores){
		this.errores = errores;
		return this;
	}
	
	@JsonProperty("errores")
	public String getErrores() {
		return errores;
	}
	
	public void setErrores(String errores) {
		this.errores = errores;
	}
	
	/**
	 *
	 */
	public CargaMasivaDatosGFItem fechaInicio(Date fechaInicio){
		this.fechaInicio = fechaInicio;
		return this;
	}
	
	@JsonProperty("fechaInicio")
	public Date getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	/**
	 *
	 */
	public CargaMasivaDatosGFItem fechaFin(Date fechaFin){
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
	public CargaMasivaDatosGFItem idPersona(Long idPersona){
		this.idPersona = idPersona;
		return this;
	}
	
	@JsonProperty("idPersona")
	public Long getIdPersona() {
		return idPersona;
	}
	
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	
	/**
	 *
	 */
	public CargaMasivaDatosGFItem personaNombre(String personaNombre){
		this.personaNombre = personaNombre;
		return this;
	}
	
	@JsonProperty("personaNombre")
	public String getPersonaNombre() {
		return personaNombre;
	}
	
	public void setPersonaNombre(String personaNombre) {
		this.personaNombre = personaNombre;
	}
	
	/**
	 *
	 */
	public CargaMasivaDatosGFItem idInstitucionGrupo(Short idInstitucionGrupo){
		this.idInstitucionGrupo = idInstitucionGrupo;
		return this;
	}
	
	@JsonProperty("idInstitucionGrupo")
	public Short getIdInstitucionGrupo() {
		return idInstitucionGrupo;
	}
	
	public void setIdInstitucionGrupo(Short idInstitucionGrupo) {
		this.idInstitucionGrupo = idInstitucionGrupo;
	}
	
	/**
	 *
	 */
	public CargaMasivaDatosGFItem codIdioma(String codIdioma){
		this.codIdioma = codIdioma;
		return this;
	}
	
	@JsonProperty("codIdioma")
	public String getCodIdioma() {
		return codIdioma;
	}
	
	public void setCodIdioma(String codIdioma) {
		this.codIdioma = codIdioma;
	}
	
	/**
	 *
	 */
	public CargaMasivaDatosGFItem usumodificacion(Integer usumodificacion){
		this.usumodificacion = usumodificacion;
		return this;
	}
	
	@JsonProperty("usumodificacion")
	public Integer getUsumodificacion() {
		return usumodificacion;
	}
	
	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}
	
	/**
	 *
	 */
	public CargaMasivaDatosGFItem nombreFichero(String nombreFichero){
		this.nombreFichero = nombreFichero;
		return this;
	}
	
	@JsonProperty("nombreFichero")
	public String getNombreFichero() {
		return nombreFichero;
	}
	
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idInstitucion, idGrupo, nombreGrupo, personaNif, colegiadoNumero, accion, general, personaNombre, idPersona, idInstitucionGrupo,
	    		errores, fechaInicio, fechaFin, codIdioma, usumodificacion, nombreFichero);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class CargaMasivaDatosGFItem {\n");
	    
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("    idGrupo: ").append(toIndentedString(idGrupo)).append("\n");
	    sb.append("    nombreGrupo: ").append(toIndentedString(nombreGrupo)).append("\n");
	    sb.append("    personaNif: ").append(toIndentedString(personaNif)).append("\n");
	    sb.append("    colegiadoNumero: ").append(toIndentedString(colegiadoNumero)).append("\n");
	    sb.append("    accion: ").append(toIndentedString(accion)).append("\n");
	    sb.append("    general: ").append(toIndentedString(general)).append("\n");
	    sb.append("    personaNombre: ").append(toIndentedString(personaNombre)).append("\n");
	    sb.append("    idInstitucionGrupo: ").append(toIndentedString(idInstitucionGrupo)).append("\n");
	    sb.append("    errores: ").append(toIndentedString(errores)).append("\n");
	    sb.append("    fechaInicio: ").append(toIndentedString(fechaInicio)).append("\n");
	    sb.append("    fechaFin: ").append(toIndentedString(fechaFin)).append("\n");
	    sb.append("    codIdioma: ").append(toIndentedString(codIdioma)).append("\n");
	    sb.append("    usumodificacion: ").append(toIndentedString(usumodificacion)).append("\n");
	    sb.append("    nombreFichero: ").append(toIndentedString(nombreFichero)).append("\n");

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
