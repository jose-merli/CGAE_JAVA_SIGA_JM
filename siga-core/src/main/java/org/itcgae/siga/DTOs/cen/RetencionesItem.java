package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RetencionesItem {

	private String idPersona;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaInicio;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaFin;
	
	private String idRetencion;
	private String porcentajeRetencion;
	private String recursoRetencion;
	private String descripcionRetencion;
	
	
	/**
	 *
	 */
	public RetencionesItem idPersona(String idPersona){
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
	public RetencionesItem fechaInicio(Date fechaInicio){
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
	public RetencionesItem fechaFin(Date fechaFin){
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
	public RetencionesItem idRetencion(String idRetencion){
		this.idRetencion = idRetencion;
		return this;
	}
	
	@JsonProperty("idRetencion")
	public String getIdRetencion() {
		return idRetencion;
	}
	public void setIdRetencion(String idRetencion) {
		this.idRetencion = idRetencion;
	}
	
	
	/**
	 *
	 */
	public RetencionesItem porcentajeRetencion(String porcentajeRetencion){
		this.porcentajeRetencion = porcentajeRetencion;
		return this;
	}
	
	@JsonProperty("porcentajeRetencion")
	public String getPorcentajeRetencion() {
		return porcentajeRetencion;
	}
	public void setPorcentajeRetencion(String porcentajeRetencion) {
		this.porcentajeRetencion = porcentajeRetencion;
	}
	

	/**
	 *
	 */
	public RetencionesItem recursoRetencion(String recursoRetencion){
		this.recursoRetencion = recursoRetencion;
		return this;
	}
	
	@JsonProperty("recursoRetencion")
	public String getRecursoRetencion() {
		return recursoRetencion;
	}
	public void setRecursoRetencion(String recursoRetencion) {
		this.recursoRetencion = recursoRetencion;
	}
	
	
	/**
	 *
	 */
	public RetencionesItem descripcionRetencion(String descripcionRetencion){
		this.descripcionRetencion = descripcionRetencion;
		return this;
	}
	
	@JsonProperty("descripcionRetencion")
	public String getDescripcionRetencion() {
		return descripcionRetencion;
	}
	public void setDescripcionRetencion(String descripcionRetencion) {
		this.descripcionRetencion = descripcionRetencion;
	}
	

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class FichaPersonaDTO {\n");
	    
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    fechaInicio: ").append(toIndentedString(fechaInicio)).append("\n");
	    sb.append("    fechaFin: ").append(toIndentedString(fechaFin)).append("\n");
	    sb.append("    idRetencion: ").append(toIndentedString(idRetencion)).append("\n");
	    sb.append("    porcentajeRetencion: ").append(toIndentedString(porcentajeRetencion)).append("\n");
	    sb.append("    recursoRetencion: ").append(toIndentedString(recursoRetencion)).append("\n");
	    sb.append("    descripcionRetencion: ").append(toIndentedString(descripcionRetencion)).append("\n");
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
	
	@Override
	public int hashCode() {
		return Objects.hash(idPersona, fechaInicio, fechaFin, idRetencion, porcentajeRetencion, recursoRetencion, descripcionRetencion);
	}
	
}
