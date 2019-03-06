package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SolModificacionItem {

	private String especifica;
	private String idSolicitud;
	private Integer numIdSolicitud;
	private String motivo;
	private String idPersona;
	private String fotografia;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechaAlta;
	private String estado;
	private String idTipoModificacion; 
	private String nombre;
	private String tipoModificacion;
	private String numColegiado;
	private String codigo;

	
	@JsonProperty("especifica")
	public String getEspecifica() {
		return especifica;
	}

	public void setEspecifica(String especifica) {
		this.especifica = especifica;
	}
	
	@JsonProperty("idSolicitud")
	public String getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	
	@JsonProperty("motivo")
	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	
	@JsonProperty("fechaAlta")
	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	
	@JsonProperty("estado")
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@JsonProperty("idTipoModificacion")
	public String getIdTipoModificacion() {
		return idTipoModificacion;
	}

	public void setIdTipoModificacion(String idTipoModificacion) {
		this.idTipoModificacion = idTipoModificacion;
	}
	
	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	
	@JsonProperty("codigo")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
		return Objects.equals(this.especifica, solModificacionItem.especifica)
				&& Objects.equals(this.idSolicitud, solModificacionItem.idSolicitud)
				&& Objects.equals(this.motivo, solModificacionItem.motivo)
				&& Objects.equals(this.idPersona, solModificacionItem.idPersona)
				&& Objects.equals(this.fechaAlta, solModificacionItem.fechaAlta)
				&& Objects.equals(this.estado, solModificacionItem.estado)
				&& Objects.equals(this.idTipoModificacion, solModificacionItem.idTipoModificacion)
		&& Objects.equals(this.nombre, solModificacionItem.nombre)
		&& Objects.equals(this.tipoModificacion, solModificacionItem.tipoModificacion)
		&& Objects.equals(this.numColegiado, solModificacionItem.numColegiado) 
		&& Objects.equals(this.codigo, solModificacionItem.codigo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(especifica, idSolicitud, motivo, idPersona, fechaAlta, estado, idTipoModificacion, nombre, tipoModificacion, numColegiado, codigo);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class SolModificacionItem {\n");

		sb.append("    especifica: ").append(toIndentedString(especifica)).append("\n");
		sb.append("    idSolicitud: ").append(toIndentedString(idSolicitud)).append("\n");
		sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
		sb.append("    fechaAlta: ").append(toIndentedString(fechaAlta)).append("\n");
		sb.append("    estado: ").append(toIndentedString(estado)).append("\n");
		sb.append("    idTipoModificacion: ").append(toIndentedString(idTipoModificacion)).append("\n");
		sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
		sb.append("    tipoModificacion: ").append(toIndentedString(tipoModificacion)).append("\n");
		sb.append("    numColegiado: ").append(toIndentedString(numColegiado)).append("\n");
		sb.append("    codigo: ").append(toIndentedString(codigo)).append("\n");
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

	public Integer getNumIdSolicitud() {
		return numIdSolicitud;
	}

	public void setNumIdSolicitud(Integer numIdSolicitud) {
		this.numIdSolicitud = numIdSolicitud;
	}

	public String getFotografia() {
		return fotografia;
	}

	public void setFotografia(String fotografia) {
		this.fotografia = fotografia;
	}
}
