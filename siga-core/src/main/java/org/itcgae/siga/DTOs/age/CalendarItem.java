package org.itcgae.siga.DTOs.age;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CalendarItem {

	private String idCalendario;
	private Short idInstitucion;
	private String descripcion;
	private Long usuModificacion;
	private Date fechaModificacion;
	private Date fechaBaja;
	private String idTipoCalendario;
	private String color;
	private Long tipoAcceso;
	private boolean checked = false;

	/**
	 **/
	public CalendarItem idCalendario(String idCalendario) {
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
	 * 
	 **/
	public CalendarItem idInstitucion(Short idInstitucion) {
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
	 **/
	public CalendarItem descripcion(String descripcion) {
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
	 **/
	public CalendarItem usuModificacion(Long usuModificacion) {
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
	public CalendarItem fechaModificacion(Date fechaModificacion) {
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
	public CalendarItem fechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
		return this;
	}

	@JsonProperty("fechaBaja")
	public Date getBaja() {
		return fechaBaja;
	}

	public void Baja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	/**
	 * 
	 **/
	public CalendarItem idTipoCalendario(String idTipoCalendario) {
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
	 * 
	 **/
	public CalendarItem color(String color) {
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
	

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public Long getTipoAcceso() {
		return tipoAcceso;
	}

	public void setTipoAcceso(Long tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CalendarItem calendarItem = (CalendarItem) o;
		return Objects.equals(this.idCalendario, calendarItem.idCalendario)
				&& Objects.equals(this.idInstitucion, calendarItem.idInstitucion)
				&& Objects.equals(this.descripcion, calendarItem.descripcion)
				&& Objects.equals(this.usuModificacion, calendarItem.usuModificacion)
				&& Objects.equals(this.fechaModificacion, calendarItem.fechaModificacion)
				&& Objects.equals(this.fechaBaja, calendarItem.fechaBaja)
				&& Objects.equals(this.idTipoCalendario, calendarItem.idTipoCalendario)
				&& Objects.equals(this.color, calendarItem.color);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idCalendario, idInstitucion, descripcion, usuModificacion, fechaModificacion, fechaBaja, idTipoCalendario, color);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CalendarItem {\n");

		sb.append("    idCalendario: ").append(toIndentedString(idCalendario)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
		sb.append("    usuModificacion: ").append(toIndentedString(usuModificacion)).append("\n");
		sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
		sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
		sb.append("    idTipoCalendario: ").append(toIndentedString(idTipoCalendario)).append("\n");
		sb.append("    color: ").append(toIndentedString(color)).append("\n");
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
