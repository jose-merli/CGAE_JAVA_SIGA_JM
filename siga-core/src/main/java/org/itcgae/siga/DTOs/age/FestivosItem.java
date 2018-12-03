package org.itcgae.siga.DTOs.age;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FestivosItem {
	
	private Long idFestivo;
	private Short idInstitucion;
	private Date fechaBaja;
	private Date fechaModificacion;
	private Long usuarioModificacion;
	private Date fecha;
	private String denominacion;
	private String tipoFestivo;
	private Long idPartido;
	private String lugar;

	@JsonProperty("idFestivo")
	public Long getIdFestivo() {
		return idFestivo;
	}

	public void setIdFestivo(Long idFestivo) {
		this.idFestivo = idFestivo;
	}

	@JsonProperty("idInstitucion")
	public Short getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	@JsonProperty("fechaBaja")
	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	@JsonProperty("fechaModificacion")
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	@JsonProperty("usuarioModificacion")
	public Long getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Long usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	@JsonProperty("fecha")
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@JsonProperty("denominacion")
	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	@JsonProperty("tipoFestivo")
	public String getTipoFestivo() {
		return tipoFestivo;
	}

	public void setTipoFestivo(String tipoFestivo) {
		this.tipoFestivo = tipoFestivo;
	}

	@JsonProperty("idPartido")
	public Long getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(Long idPartido) {
		this.idPartido = idPartido;
	}	

	@JsonProperty("lugar")
	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		FestivosItem festivosItem = (FestivosItem) o;
		return Objects.equals(this.idFestivo, festivosItem.idFestivo)
				&& Objects.equals(this.idInstitucion, festivosItem.idInstitucion)
				&& Objects.equals(this.fechaBaja, festivosItem.fechaBaja)
				&& Objects.equals(this.fechaModificacion, festivosItem.fechaModificacion)
				&& Objects.equals(this.usuarioModificacion, festivosItem.usuarioModificacion)
				&& Objects.equals(this.fechaBaja, festivosItem.fechaBaja)
				&& Objects.equals(this.fecha, festivosItem.fecha)
				&& Objects.equals(this.denominacion, festivosItem.denominacion)
				&& Objects.equals(this.idPartido, festivosItem.idPartido)
				&& Objects.equals(this.lugar, festivosItem.lugar)
				&& Objects.equals(this.tipoFestivo, festivosItem.tipoFestivo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idFestivo, idInstitucion, fechaBaja, fechaModificacion, usuarioModificacion, fechaBaja, fecha, denominacion, idPartido, tipoFestivo, lugar);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CalendarItem {\n");

		sb.append("    idFestivo: ").append(toIndentedString(idFestivo)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
		sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
		sb.append("    usuarioModificacion: ").append(toIndentedString(usuarioModificacion)).append("\n");
		sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
		sb.append("    fecha: ").append(toIndentedString(fecha)).append("\n");
		sb.append("    denominacion: ").append(toIndentedString(denominacion)).append("\n");
		sb.append("    idPartido: ").append(toIndentedString(idPartido)).append("\n");
		sb.append("    lugar: ").append(toIndentedString(lugar)).append("\n");
		sb.append("    tipoFestivo: ").append(toIndentedString(tipoFestivo)).append("\n");
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
