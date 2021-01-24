package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CertificadoItem {

	private String idSolicitud;
	private String idPersona;
	private String descripcion;
	private String fechaEmision;
	

	
	/**
	 *
	 */
	
	public CertificadoItem idPersona(String idPersona){
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
	

	
	public CertificadoItem descripcion(String descripcion){
		this.descripcion = descripcion;
		return this;
	}
	
	@JsonProperty("descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	/**
	 *
	 */
	public CertificadoItem idSolicitud(String idSolicitud){
		this.idSolicitud = idSolicitud;
		return this;
	}
	
	@JsonProperty("idSolicitud")
	public String getIdSolicitud() {
		return idSolicitud;
	}
	
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	
	/**
	 *
	 */
	public CertificadoItem fechaEmision(String fechaEmision){
		this.fechaEmision = fechaEmision;
		return this;
	}
	
	@JsonProperty("fechaEmision")
	public String getFechaEmision() {
		return fechaEmision;
	}
	
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
		
	/**
	 *
	 */
	

	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idSolicitud, descripcion, fechaEmision);
	}


	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosDireccionesItem {\n");
	    
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    fechaEmision: ").append(toIndentedString(fechaEmision)).append("\n");
	    sb.append("    idSolicitud: ").append(toIndentedString(idSolicitud)).append("\n");
	    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");

	    
		
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
