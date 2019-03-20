package org.itcgae.siga.DTOs.age;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificacionEventoItem {

	private String idNotificacion;
	private String idTipoNotificacion;
	private String idCalendario;
	private Short idInstitucion;
	private String idUnidadMedida;
	private Long usuModificacion;
	private Date fechaModificacion;
	private Date fechaBaja;
	private String idEvento;
	private String idPlantilla;
	private String nombrePlantilla;
	private String cuando;
	private String descripcionCuando;
	private String idTipoCuando;
	private String tipoEnvio;
	private String idTipoEnvio;
	private String nombreTipoNotificacion;
	private String descripcionMedida;
	private String descripcionAntes;
	
	/**
	 **/
	public NotificacionEventoItem idNotificacion(String idNotificacion) {
		this.idNotificacion = idNotificacion;
		return this;
	}

	@JsonProperty("idNotificacion")
	public String getIdNotificacion() {
		return idNotificacion;
	}

	public void setIdNotificacion(String idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	/**
	 **/
	public NotificacionEventoItem idTipoNotificacion(String idTipoNotificacion) {
		this.idTipoNotificacion = idTipoNotificacion;
		return this;
	}

	@JsonProperty("idTipoNotificacion")
	public String getIdTipoNotificacion() {
		return idTipoNotificacion;
	}

	public void setIdTipoNotificacion(String idTipoNotificacion) {
		this.idTipoNotificacion = idTipoNotificacion;
	}

	/**
	 **/
	public NotificacionEventoItem idCalendario(String idCalendario) {
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
	public NotificacionEventoItem idInstitucion(Short idInstitucion) {
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
	public NotificacionEventoItem idUnidadMedida(String idUnidadMedida) {
		this.idUnidadMedida = idUnidadMedida;
		return this;
	}

	@JsonProperty("idUnidadMedida")
	public String getIdUnidadMedida() {
		return idUnidadMedida;
	}

	public void setIdUnidadMedida(String idUnidadMedida) {
		this.idUnidadMedida = idUnidadMedida;
	}

	/**
	 * 
	 **/
	public NotificacionEventoItem usuModificacion(Long usuModificacion) {
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
	public NotificacionEventoItem fechaModificacion(Date fechaModificacion) {
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
	public NotificacionEventoItem fechaBaja(Date fechaBaja) {
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
	public NotificacionEventoItem idEvento(String idEvento) {
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
	 * 
	 **/
	public NotificacionEventoItem idPlantilla(String idPlantilla) {
		this.idPlantilla = idPlantilla;
		return this;
	}

	@JsonProperty("idPlantilla")
	public String getIdPlantilla() {
		return idPlantilla;
	}

	public void setIdPlantilla(String idPlantilla) {
		this.idPlantilla = idPlantilla;
	}

	/**
	 * 
	 **/
	public NotificacionEventoItem nombrePlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
		return this;
	}

	@JsonProperty("nombrePlantilla")
	public String getNombrePlantilla() {
		return nombrePlantilla;
	}

	public void setNombrePlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
	}
	
	/**
	 * 
	 **/
	public NotificacionEventoItem cuando(String cuando) {
		this.cuando = cuando;
		return this;
	}

	@JsonProperty("cuando")
	public String getCuando() {
		return cuando;
	}

	public void setCuando(String cuando) {
		this.cuando = cuando;
	}
	
	/**
	 * 
	 **/
	public NotificacionEventoItem descripcionCuando(String descripcionCuando) {
		this.descripcionCuando = descripcionCuando;
		return this;
	}

	@JsonProperty("descripcionCuando")
	public String getDescripcionCuando() {
		return descripcionCuando;
	}

	public void setDescripcionCuando(String descripcionCuando) {
		this.descripcionCuando = descripcionCuando;
	}
	
	/**
	 * 
	 **/
	public NotificacionEventoItem idTipoCuando(String idTipoCuando) {
		this.idTipoCuando = idTipoCuando;
		return this;
	}

	@JsonProperty("idTipoCuando")
	public String getIdTipoCuando() {
		return idTipoCuando;
	}

	public void setIdTipoCuando(String idTipoCuando) {
		this.idTipoCuando = idTipoCuando;
	}
	
	/**
	 * 
	 **/
	public NotificacionEventoItem tipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
		return this;
	}

	@JsonProperty("tipoEnvio")
	public String getTipoEnvio() {
		return tipoEnvio;
	}

	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}
	
	/**
	 * 
	 **/
	public NotificacionEventoItem idTipoEnvio(String idTipoEnvio) {
		this.idTipoEnvio = idTipoEnvio;
		return this;
	}

	@JsonProperty("idTipoEnvio")
	public String getIdTipoEnvio() {
		return idTipoEnvio;
	}

	public void setIdTipoEnvio(String idTipoEnvio) {
		this.idTipoEnvio = idTipoEnvio;
	}
	
	/**
	 * 
	 **/
	public NotificacionEventoItem descripcionMedida(String descripcionMedida) {
		this.descripcionMedida = descripcionMedida;
		return this;
	}

	@JsonProperty("descripcionMedida")
	public String getDescripcionMedida() {
		return descripcionMedida;
	}

	public void setDescripcionMedida(String descripcionMedida) {
		this.descripcionMedida = descripcionMedida;
	}
	
	/**
	 * 
	 **/
	public NotificacionEventoItem descripcionAntes(String descripcionAntes) {
		this.descripcionAntes = descripcionAntes;
		return this;
	}

	@JsonProperty("descripcionAntes")
	public String getDescripcionAntes() {
		return descripcionAntes;
	}

	public void setDescripcionAntes(String descripcionAntes) {
		this.descripcionAntes = descripcionAntes;
	}
	
	/**
	 * 
	 **/
	public NotificacionEventoItem 	nombreTipoNotificacion(String nombreTipoNotificacion) {
		this.nombreTipoNotificacion = nombreTipoNotificacion;
		return this;
	}

	@JsonProperty("nombreTipoNotificacion")
	public String getNombreTipoNotificacion() {
		return nombreTipoNotificacion;
	}

	public void setNombreTipoNotificacion(String nombreTipoNotificacion) {
		this.nombreTipoNotificacion = nombreTipoNotificacion;
	}


	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		NotificacionEventoItem notificacionItem = (NotificacionEventoItem) o;
		return  Objects.equals(this.idTipoNotificacion, notificacionItem.idTipoNotificacion)
				&& Objects.equals(this.idCalendario, notificacionItem.idCalendario)
				&& Objects.equals(this.idInstitucion, notificacionItem.idInstitucion)
				&& Objects.equals(this.idUnidadMedida, notificacionItem.idUnidadMedida)
				&& Objects.equals(this.usuModificacion, notificacionItem.usuModificacion)
				&& Objects.equals(this.fechaModificacion, notificacionItem.fechaModificacion)
				&& Objects.equals(this.fechaBaja, notificacionItem.fechaBaja)
				&& Objects.equals(this.idEvento, notificacionItem.idEvento)
				&& Objects.equals(this.idPlantilla, notificacionItem.idPlantilla)
				&& Objects.equals(this.nombrePlantilla, notificacionItem.nombrePlantilla)
				&& Objects.equals(this.cuando, notificacionItem.cuando)
				&& Objects.equals(this.descripcionCuando, notificacionItem.descripcionCuando)
				&& Objects.equals(this.idTipoCuando, notificacionItem.idTipoCuando)
				&& Objects.equals(this.tipoEnvio, notificacionItem.tipoEnvio)
				&& Objects.equals(this.idTipoEnvio, notificacionItem.idTipoEnvio)
				&& Objects.equals(this.nombreTipoNotificacion, notificacionItem.nombreTipoNotificacion)
				&& Objects.equals(this.descripcionMedida, notificacionItem.descripcionMedida)
				&& Objects.equals(this.descripcionAntes, notificacionItem.descripcionAntes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idNotificacion, idTipoNotificacion, idCalendario, idInstitucion, idUnidadMedida, usuModificacion, fechaModificacion, fechaBaja,
				idEvento, idPlantilla, nombrePlantilla, cuando, idTipoCuando, tipoEnvio, idTipoEnvio, nombreTipoNotificacion, descripcionMedida, descripcionAntes);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class NotificacionItem {\n");

		sb.append("    idNotificacion: ").append(toIndentedString(idNotificacion)).append("\n");
		sb.append("    idTipoNotificacion: ").append(toIndentedString(idTipoNotificacion)).append("\n");
		sb.append("    idCalendario: ").append(toIndentedString(idCalendario)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    idUnidadMedida: ").append(toIndentedString(idUnidadMedida)).append("\n");
		sb.append("    usuModificacion: ").append(toIndentedString(usuModificacion)).append("\n");
		sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
		sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
		sb.append("    idEvento: ").append(toIndentedString(idEvento)).append("\n");
		sb.append("    idPlantilla: ").append(toIndentedString(idPlantilla)).append("\n");
		sb.append("    nombrePlantilla: ").append(toIndentedString(nombrePlantilla)).append("\n");
		sb.append("    cuando: ").append(toIndentedString(cuando)).append("\n");
		sb.append("    descripcionCuando: ").append(toIndentedString(descripcionCuando)).append("\n");
		sb.append("    idTipoCuando: ").append(toIndentedString(idTipoCuando)).append("\n");
		sb.append("    tipoEnvio: ").append(toIndentedString(tipoEnvio)).append("\n");
		sb.append("    idTipoEnvio: ").append(toIndentedString(idTipoEnvio)).append("\n");
		sb.append("    nombreTipoNotificacion: ").append(toIndentedString(nombreTipoNotificacion)).append("\n");
		sb.append("    descripcionMedida: ").append(toIndentedString(descripcionMedida)).append("\n");
		sb.append("    descripcionAntes: ").append(toIndentedString(descripcionAntes)).append("\n");
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
