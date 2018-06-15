package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EtiquetaRetencionesDTO {

	 private String idPersona;
	 private String idInstitucion;
	 
	 private Date fechaInicio;
	 private Date fechaFin;
	 private String idRetencion;
	 private String idUsuario;
	 private Date fechaModificacion;
	 
	 @JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	
	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
	@JsonProperty("fechaInicio")
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	@JsonProperty("fechaFin")
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	@JsonProperty("idRetencion")
	public String getIdRetencion() {
		return idRetencion;
	}
	public void setIdRetencion(String idRetencion) {
		this.idRetencion = idRetencion;
	}
	
	@JsonProperty("idUsuario")
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
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
