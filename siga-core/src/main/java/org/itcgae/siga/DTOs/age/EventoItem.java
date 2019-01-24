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
	private Date realEnd;
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

	private String idTipoCalendario;
	
	private String idRepeticionEvento;
	private Integer valoresRepeticion [];
	private String valoresRepeticionString;
	private String tipoDiasRepeticion;
	private String tipoRepeticion;
	private Date fechaInicioRepeticion;
	private Date fechaFinRepeticion;

	private Long idPartidoJudicial;
	private String idCurso;
	private String estadoEvento;
	
	private String fechaHoraInicio;
	private String fechaHoraFin;

		
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
	
	/**
	 **/
	public EventoItem idTipoCalendario(String idTipoCalendario) {
		this.idTipoCalendario = idTipoCalendario;
		return this;
	}

	@JsonProperty("idTipoCalendario")
	public String getIdTipoCalendario() {
		return idTipoCalendario;
	}

	public void setIdTipoCalendario(String idTipoCalendario) {
		this.idTipoCalendario = idTipoCalendario;
	}
	
	/**
	 **/
	public EventoItem idRepeticionEvento(String idRepeticionEvento) {
		this.idRepeticionEvento = idRepeticionEvento;
		return this;
	}

	@JsonProperty("idRepeticionEvento")
	public String getIdRepeticionEvento() {
		return idRepeticionEvento;
	}

	public void setIdRepeticionEvento(String idRepeticionEvento) {
		this.idRepeticionEvento = idRepeticionEvento;
	}
	
	
	/**
	 **/
	public EventoItem valoresRepeticion(Integer valoresRepeticion []) {
		this.valoresRepeticion = valoresRepeticion;
		return this;
	}

	@JsonProperty("valoresRepeticion")
	public Integer [] getValoresRepeticion() {
		return valoresRepeticion;
	}

	public void setValoresRepeticion(Integer valoresRepeticion []) {
		this.valoresRepeticion = valoresRepeticion;
	}
	
	/**
	 **/
	public EventoItem valoresRepeticionString(String valoresRepeticionString) {
		this.valoresRepeticionString = valoresRepeticionString;
		return this;
	}

	@JsonProperty("valoresRepeticionString")
	public String getValoresRepeticionString() {
		return valoresRepeticionString;
	}

	public void setValoresRepeticionString(String valoresRepeticionString) {
		this.valoresRepeticionString = valoresRepeticionString;
	}
	
	/**
	 **/
	public EventoItem tipoDiasRepeticion(String tipoDiasRepeticion) {
		this.tipoDiasRepeticion = tipoDiasRepeticion;
		return this;
	}

	@JsonProperty("tipoDiasRepeticion")
	public String getTipoDiasRepeticion() {
		return tipoDiasRepeticion;
	}

	public void setTipoDiasRepeticion(String tipoDiasRepeticion) {
		this.tipoDiasRepeticion = tipoDiasRepeticion;
	}
	
	/**
	 **/
	public EventoItem tipoRepeticion(String tipoRepeticion) {
		this.tipoRepeticion = tipoRepeticion;
		return this;
	}

	@JsonProperty("tipoRepeticion")
	public String getTipoRepeticion() {
		return tipoRepeticion;
	}


	public void setTipoRepeticion(String tipoRepeticion) {
		this.tipoRepeticion = tipoRepeticion;
	}
	
	
	/**
	 **/
	public EventoItem fechaInicioRepeticion(Date fechaInicioRepeticion) {
		this.fechaInicioRepeticion = fechaInicioRepeticion;
		return this;
	}

	@JsonProperty("fechaInicioRepeticion")
	public Date getFechaInicioRepeticion() {
		return fechaInicioRepeticion;
	}

	public void setFechaInicioRepeticion(Date fechaInicioRepeticion) {
		this.fechaInicioRepeticion = fechaInicioRepeticion;
	}

	/**
	 **/
	public EventoItem fechaFinRepeticion(Date fechaFinRepeticion) {
		this.fechaFinRepeticion = fechaFinRepeticion;
		return this;
	}

	@JsonProperty("fechaFinRepeticion")
	public Date getFechaFinRepeticion() {
		return fechaFinRepeticion;
	}

	public void setFechaFinRepeticion(Date fechaFinRepeticion) {
		this.fechaFinRepeticion = fechaFinRepeticion;
	}
			

	@JsonProperty("fechaBaja")
	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	@JsonProperty("i")
	public Long getIdPartidoJudicial() {
		return idPartidoJudicial;
	}

	public void setIdPartidoJudicial(Long idPartidoJudicial) {
		this.idPartidoJudicial = idPartidoJudicial;
	}
	
	/**
	 **/
	public EventoItem idCurso(String idCurso) {
		this.idCurso = idCurso;
		return this;
	}

	@JsonProperty("idCurso")
	public String getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(String idCurso) {
		this.idCurso = idCurso;
	}
	
	/**
	 **/
	public EventoItem fechaHoraInicio(String fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
		return this;
	}

	@JsonProperty("fechaHoraInicio")
	public String getFechaHoraInicio() {
		return fechaHoraInicio;
	}

	public void setFechaHoraInicio(String fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}
	
	/**
	 **/
	public EventoItem fechaHoraFin(String fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
		return this;
	}

	@JsonProperty("fechaHoraFin")
	public String getFechaHoraFin() {
		return fechaHoraFin;
	}

	public void setFechaHoraFin(String fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}
	
	/**
	 **/
	public EventoItem estadoEvento(String estadoEvento) {
		this.estadoEvento = estadoEvento;
		return this;
	}

	@JsonProperty("estadoEvento")
	public String getEstadoEvento() {
		return estadoEvento;
	}

	public void setEstadoEvento(String estadoEvento) {
		this.estadoEvento = estadoEvento;
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
				&& Objects.equals(this.recursos, eventoItem.recursos)
				&& Objects.equals(this.idEstadoEvento, eventoItem.idEstadoEvento)
				&& Objects.equals(this.idTipoEvento, eventoItem.idTipoEvento)
				&& Objects.equals(this.tipoAcceso, eventoItem.tipoAcceso)
				&& Objects.equals(this.fechaBaja, eventoItem.fechaBaja)
				&& Objects.equals(this.fechaModificacion, eventoItem.fechaModificacion)
				&& Objects.equals(this.idTipoCalendario, eventoItem.idTipoCalendario)
				&& Objects.equals(this.idRepeticionEvento, eventoItem.idRepeticionEvento)
				&& Objects.equals(this.valoresRepeticion, eventoItem.valoresRepeticion)
				&& Objects.equals(this.valoresRepeticionString, eventoItem.valoresRepeticionString)
				&& Objects.equals(this.tipoDiasRepeticion, eventoItem.tipoDiasRepeticion)
				&& Objects.equals(this.tipoRepeticion, eventoItem.tipoRepeticion)
				&& Objects.equals(this.fechaInicioRepeticion, eventoItem.fechaInicioRepeticion)
				&& Objects.equals(this.fechaFinRepeticion, eventoItem.fechaFinRepeticion)
				&& Objects.equals(this.idPartidoJudicial, eventoItem.idPartidoJudicial)
				&& Objects.equals(this.idCurso, eventoItem.idCurso)
				&& Objects.equals(this.fechaHoraFin, eventoItem.fechaHoraFin)
				&& Objects.equals(this.fechaHoraInicio, eventoItem.fechaHoraInicio)
				&& Objects.equals(this.estadoEvento, eventoItem.estadoEvento);
		
	}

	@Override
	public int hashCode() {

		return Objects.hash(idEvento, idCalendario, idInstitucion, titulo, fechaInicio, fechaFin, allDay, color, lugar,
				descripcion, recursos, idEstadoEvento, idTipoEvento, usuModificacion, fechaModificacion, fechaBaja, idTipoCalendario, tipoAcceso,
				idRepeticionEvento, valoresRepeticion, valoresRepeticionString, tipoDiasRepeticion, tipoRepeticion, fechaInicioRepeticion,
				fechaFinRepeticion,idPartidoJudicial, idCurso,fechaHoraInicio, fechaHoraFin, estadoEvento);
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
		sb.append("    idTipoCalendario: ").append(toIndentedString(idTipoCalendario)).append("\n");
		sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
		sb.append("    tipoAcceso: ").append(toIndentedString(tipoAcceso)).append("\n");
		sb.append("    idRepeticionEvento: ").append(toIndentedString(idRepeticionEvento)).append("\n");
		sb.append("    valoresRepeticion: ").append(toIndentedString(valoresRepeticion)).append("\n");
		sb.append("    valoresRepeticionString: ").append(toIndentedString(valoresRepeticionString)).append("\n");
		sb.append("    tipoDiasRepeticion: ").append(toIndentedString(tipoDiasRepeticion)).append("\n");
		sb.append("    tipoRepeticion: ").append(toIndentedString(tipoRepeticion)).append("\n");
		sb.append("    fechaInicioRepeticion: ").append(toIndentedString(fechaInicioRepeticion)).append("\n");
		sb.append("    fechaFinRepeticion: ").append(toIndentedString(fechaFinRepeticion)).append("\n");
		sb.append("    idPartidoJudicial: ").append(toIndentedString(idPartidoJudicial)).append("\n");
		sb.append("    idCurso: ").append(toIndentedString(idCurso)).append("\n");
		sb.append("    fechaHoraInicio: ").append(toIndentedString(fechaHoraInicio)).append("\n");
		sb.append("    fechaHoraFin: ").append(toIndentedString(fechaHoraFin)).append("\n");
		sb.append("    estadoEvento: ").append(toIndentedString(estadoEvento)).append("\n");
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

	public Date getRealEnd() {
		return realEnd;
	}

	public void setRealEnd(Date realEnd) {
		this.realEnd = realEnd;
	}
	
}
