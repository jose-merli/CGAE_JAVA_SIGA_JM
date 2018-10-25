package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SolModificacionItem {

	private String idEstado;
	private String idSolicitud;
	private String tipoModificacion;
	private String numColegiado;
	private String nombre;
	private Date fechaAlta;
	private String motivo;
	
	@JsonProperty("idEstado")
	public String getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}
	
	@JsonProperty("idSolicitud")
	public String getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	
	@JsonProperty("tipoModificacion")
	public String getTipoModificacion() {
		return tipoModificacion;
	}
	public void setTipoModificacion(String tipoModificacion) {
		this.tipoModificacion = tipoModificacion;
	}
	
	@JsonProperty("numColegiado")
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	
	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@JsonProperty("fechaAlta")
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
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
	    SolModificacionItem solModificacionItem = (SolModificacionItem) o;
	    return Objects.equals(this.idEstado, solModificacionItem.idEstado) &&
	    		Objects.equals(this.idSolicitud, solModificacionItem.idSolicitud) &&
	    		Objects.equals(this.tipoModificacion, solModificacionItem.tipoModificacion) &&
	    		Objects.equals(this.numColegiado, solModificacionItem.numColegiado) &&
	    		Objects.equals(this.nombre, solModificacionItem.nombre) &&
	    		Objects.equals(this.fechaAlta, solModificacionItem.fechaAlta) &&
	    Objects.equals(this.motivo, solModificacionItem.motivo);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idEstado, idSolicitud, tipoModificacion, fechaAlta, motivo);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class SolModificacionItem {\n");
	    
	    sb.append("    idEstado: ").append(toIndentedString(idEstado)).append("\n");
	    sb.append("    idSolicitud: ").append(toIndentedString(idSolicitud)).append("\n");
	    sb.append("    tipoModificacion: ").append(toIndentedString(tipoModificacion)).append("\n");
	    sb.append("    numColegiado: ").append(toIndentedString(numColegiado)).append("\n");
	    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
	    sb.append("    fechaAlta: ").append(toIndentedString(fechaAlta)).append("\n");
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
