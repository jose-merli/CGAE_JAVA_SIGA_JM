package org.itcgae.siga.DTOs.age;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatosRepeticionEventoItem {

	private String idEvento;
	private String idRepeticionEvento;
	private Short idInstitucion;
	private String valoresRepeticion [];
	private String tipoDiasRepeticion;
	private String tipoRepeticion;
	private Date fechaInicio;
	private Date fechaFin;
	private Long usuModificacion;
	private Date fechaModificacion;
	private Date fechaBaja;
	
	/**
	 **/
	public DatosRepeticionEventoItem idEvento(String idEvento) {
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
	public DatosRepeticionEventoItem idRepeticionEvento(String idRepeticionEvento) {
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
	public DatosRepeticionEventoItem idInstitucion(Short idInstitucion) {
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
	public DatosRepeticionEventoItem valoresRepeticion(String valoresRepeticion []) {
		this.valoresRepeticion = valoresRepeticion;
		return this;
	}

	@JsonProperty("valoresRepeticion")
	public String [] getValoresRepeticion() {
		return valoresRepeticion;
	}

	public void setValoresRepeticion(String valoresRepeticion []) {
		this.valoresRepeticion = valoresRepeticion;
	}
	
	/**
	 **/
	public DatosRepeticionEventoItem tipoDiasRepeticion(String tipoDiasRepeticion) {
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
	public DatosRepeticionEventoItem tipoRepeticion(String tipoRepeticion) {
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
	public DatosRepeticionEventoItem fechaInicio(Date fechaInicio) {
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
	 **/
	public DatosRepeticionEventoItem fechaFin(Date fechaFin) {
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
	 **/
	public DatosRepeticionEventoItem usuModificacion(Long usuModificacion) {
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
	public DatosRepeticionEventoItem fechaModificacion(Date fechaModificacion) {
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
	 * 
	 **/
	public DatosRepeticionEventoItem fechaBaja(Date fechaBaja) {
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

	
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DatosRepeticionEventoItem datosRepeticionEventoItem = (DatosRepeticionEventoItem) o;
		return Objects.equals(this.idEvento, datosRepeticionEventoItem.idEvento)
				&& Objects.equals(this.idRepeticionEvento, datosRepeticionEventoItem.idRepeticionEvento)
				&& Objects.equals(this.idInstitucion, datosRepeticionEventoItem.idInstitucion)
				&& Objects.equals(this.valoresRepeticion, datosRepeticionEventoItem.valoresRepeticion)
				&& Objects.equals(this.tipoDiasRepeticion, datosRepeticionEventoItem.tipoDiasRepeticion)
				&& Objects.equals(this.tipoRepeticion, datosRepeticionEventoItem.tipoRepeticion)
				&& Objects.equals(this.fechaInicio, datosRepeticionEventoItem.fechaInicio)
				&& Objects.equals(this.fechaFin, datosRepeticionEventoItem.fechaFin)
				&& Objects.equals(this.usuModificacion, datosRepeticionEventoItem.usuModificacion)
				&& Objects.equals(this.fechaModificacion, datosRepeticionEventoItem.fechaModificacion)
				&& Objects.equals(this.fechaBaja, datosRepeticionEventoItem.fechaBaja);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idEvento, idRepeticionEvento, idInstitucion, valoresRepeticion, tipoDiasRepeticion, tipoRepeticion, fechaInicio, fechaFin, 
				usuModificacion, fechaModificacion, fechaBaja);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class DatosRepeticionEventoItem {\n");

		sb.append("    idEvento: ").append(toIndentedString(idEvento)).append("\n");
		sb.append("    idRepeticionEvento: ").append(toIndentedString(idRepeticionEvento)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    valoresRepeticion: ").append(toIndentedString(valoresRepeticion)).append("\n");
		sb.append("    tipoDiasRepeticion: ").append(toIndentedString(tipoDiasRepeticion)).append("\n");
		sb.append("    tipoRepeticion: ").append(toIndentedString(tipoRepeticion)).append("\n");
		sb.append("    fechaInicio: ").append(toIndentedString(fechaInicio)).append("\n");
		sb.append("    fechaFin: ").append(toIndentedString(fechaFin)).append("\n");
		sb.append("    usuModificacion: ").append(toIndentedString(usuModificacion)).append("\n");
		sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
		sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
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
