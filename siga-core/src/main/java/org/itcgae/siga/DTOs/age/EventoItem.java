package org.itcgae.siga.DTOs.age;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventoItem {

	private String idEvento;
	private String idCalendario;
	private Short idInstitucion;
	private String titulo;
	private Date fechaInicio;
	private Date fechaFin;
	private boolean allDay = false;
	private String color;
	private Long tipoAcceso;
	private String lugar;
	private String descripcion;
	private String recursos;
	private String idEstadoEvento;
	private String idTipoEvento;
	private Date fechaBaja;
	private Long usuModificacion;
	private Date fechaModificacion;
		
	/**
	 **/
	public EventoItem idEvento(String idEvento) {
		this.idEvento = idEvento;
		return this;
	}

	@JsonProperty("idEvento")
	public String getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}
	
	/**
	 **/
	public EventoItem idCalendario(String idCalendario) {
		this.idCalendario = idCalendario;
		return this;
	}

	@JsonProperty("idCalendario")
	public String getIdCalendario() {
		return idCalendario;
	}

	public void setIdCalendario(String idCalendario) {
		this.idCalendario = idCalendario;
	}
	
	/**
	 **/
	public EventoItem idInstitucion(Short idInstitucion) {
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
	 **/
	public EventoItem titulo(String titulo) {
		this.titulo = titulo;
		return this;
	}

	@JsonProperty("title")
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	/**
	 **/
	public EventoItem fechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
		return this;
	}

	@JsonProperty("start")
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 **/
	public EventoItem fechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
		return this;
	}

	@JsonProperty("end")
	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	/**
	 **/
	public EventoItem allDay(boolean allDay) {
		this.allDay = allDay;
		return this;
	}

	@JsonProperty("allDay")
	public boolean getAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
	
	/**
	 **/
	public EventoItem lugar(String lugar) {
		this.lugar = lugar;
		return this;
	}

	@JsonProperty("lugar")
	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	
	/**
	 **/
	public EventoItem color(String color) {
		this.color = color;
		return this;
	}

	@JsonProperty("color")
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	/**
	 **/
	public EventoItem descripcion(String descripcion) {
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
	
	public Long getTipoAcceso() {
		return tipoAcceso;
	}

	public void setTipoAcceso(Long tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}

	/**
	 **/
	public EventoItem recursos(String recursos) {
		this.recursos = recursos;
		return this;
	}

	@JsonProperty("recursos")
	public String getRecursos() {
		return recursos;
	}

	public void setRecursos(String recursos) {
		this.recursos = recursos;
	}
	
	/**
	 **/
	public EventoItem idEstadoEvento(String idEstadoEvento) {
		this.idEstadoEvento = idEstadoEvento;
		return this;
	}

	@JsonProperty("idEstadoEvento")
	public String getIdEstadoEvento() {
		return idEstadoEvento;
	}

	public void setIdEstadoEvento(String idEstadoEvento) {
		this.idEstadoEvento = idEstadoEvento;
	}
	
	/**
	 **/
	public EventoItem idTipoEvento(String idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
		return this;
	}

	@JsonProperty("idTipoEvento")
	public String getIdTipoEvento() {
		return idTipoEvento;
	}

	public void setIdTipoEvento(String idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}
	
	/**
	 * 
	 **/
	public EventoItem usuModificacion(Long usuModificacion) {
		this.usuModificacion = usuModificacion;
		return this;
	}

	@JsonProperty("usuModificacion")
	public Long getUsuModificacion() {
		return usuModificacion;
	}

	public void setUsuModificacion(Long usuModificacion) {
		this.usuModificacion = usuModificacion;
	}

	/**
	 * 
	 **/
	public EventoItem fechaModificacion(Date fechaModificacion) {
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
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		EventoItem eventoItem = (EventoItem) o;
		return Objects.equals(this.idEvento, eventoItem.idEvento)
				&& Objects.equals(this.idCalendario, eventoItem.idCalendario)
				&& Objects.equals(this.idInstitucion, eventoItem.idInstitucion)
				&& Objects.equals(this.titulo, eventoItem.titulo)
				&& Objects.equals(this.fechaInicio, eventoItem.fechaInicio)
				&& Objects.equals(this.fechaFin, eventoItem.fechaFin)
				&& Objects.equals(this.allDay, eventoItem.allDay)
				&& Objects.equals(this.color, eventoItem.color)
				&& Objects.equals(this.lugar, eventoItem.lugar)
				&& Objects.equals(this.descripcion, eventoItem.descripcion)
				&& Objects.equals(this.recursos, eventoItem.recursos)
				&& Objects.equals(this.idEstadoEvento, eventoItem.idEstadoEvento)
				&& Objects.equals(this.idTipoEvento, eventoItem.idTipoEvento)
				&& Objects.equals(this.usuModificacion, eventoItem.usuModificacion)
				&& Objects.equals(this.fechaModificacion, eventoItem.fechaModificacion)
				&& Objects.equals(this.tipoAcceso, eventoItem.tipoAcceso)
				&& Objects.equals(this.fechaBaja, eventoItem.fechaBaja);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idEvento, idCalendario, idInstitucion, titulo, fechaInicio, fechaFin, allDay, color, lugar,
				descripcion, recursos, idEstadoEvento, idTipoEvento, usuModificacion, fechaModificacion, fechaBaja, tipoAcceso);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class EventoItem {\n");

		sb.append("    idEvento: ").append(toIndentedString(idEvento)).append("\n");
		sb.append("    idCalendario: ").append(toIndentedString(idCalendario)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    titulo: ").append(toIndentedString(titulo)).append("\n");
		sb.append("    fechaInicio: ").append(toIndentedString(fechaInicio)).append("\n");
		sb.append("    fechaFin: ").append(toIndentedString(fechaFin)).append("\n");
		sb.append("    allDay: ").append(toIndentedString(allDay)).append("\n");
		sb.append("    color: ").append(toIndentedString(color)).append("\n");
		sb.append("    lugar: ").append(toIndentedString(lugar)).append("\n");
		sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
		sb.append("    recursos: ").append(toIndentedString(recursos)).append("\n");
		sb.append("    idEstadoEvento: ").append(toIndentedString(idEstadoEvento)).append("\n");
		sb.append("    idTipoEvento: ").append(toIndentedString(idTipoEvento)).append("\n");
		sb.append("    usuModificacion: ").append(toIndentedString(usuModificacion)).append("\n");
		sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
		sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
		sb.append("    tipoAcceso: ").append(toIndentedString(tipoAcceso)).append("\n");
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
