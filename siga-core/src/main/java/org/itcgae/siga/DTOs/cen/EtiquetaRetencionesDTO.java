package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EtiquetaRetencionesDTO {

	private String idPersona;
	private String idInstitucion;
	private String fechaInicio;
	private String fechaFin;
	private String idRetencion;
	private String idUsuario;
	private Date fechaModificacion;
	 
	
	
	/**
	 *
	 */
	public EtiquetaRetencionesDTO idPersona(String idPersona){
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
	public EtiquetaRetencionesDTO idInstitucion(String idInstitucion){
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
	public EtiquetaRetencionesDTO fechaInicio(String fechaInicio){
		this.fechaInicio = fechaInicio;
		return this;
	}
	
	@JsonProperty("fechaInicio")
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	/**
	 *
	 */
	public EtiquetaRetencionesDTO fechaFin(String fechaFin){
		this.fechaFin = fechaFin;
		return this;
	}
	
	
	@JsonProperty("fechaFin")
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
	/**
	 *
	 */
	public EtiquetaRetencionesDTO idRetencion(String idRetencion){
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
	public EtiquetaRetencionesDTO idUsuario(String idUsuario){
		this.idUsuario = idUsuario;
		return this;
	}
	
	@JsonProperty("idUsuario")
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
	/**
	 *
	 */
	public EtiquetaRetencionesDTO fechaModificacion(Date fechaModificacion){
		this.fechaModificacion = fechaModificacion;
		return this;
	}
	
	@JsonProperty("fechaModificacion")
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class EtiquetaRetencionesDTO {\n");
	    
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("    fechaInicio: ").append(toIndentedString(fechaInicio)).append("\n");
	    sb.append("    fechaFin: ").append(toIndentedString(fechaFin)).append("\n");
	    sb.append("    idRetencion: ").append(toIndentedString(idRetencion)).append("\n");
	    sb.append("    idUsuario: ").append(toIndentedString(idUsuario)).append("\n");
	    sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
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
		return Objects.hash(idPersona, idInstitucion, fechaInicio, fechaFin, idRetencion, idUsuario, fechaModificacion);
	}

}
