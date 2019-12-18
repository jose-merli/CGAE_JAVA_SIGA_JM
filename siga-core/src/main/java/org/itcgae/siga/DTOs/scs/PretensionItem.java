package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PretensionItem {

	private String idPretension;
	private String idInstitucion;
	private String descripcion;
	private Long usuModificacion;
	private Date fechaModificacion;
	private String idJurisdiccion;
	private String codigoExt;
	private String fechabaja;
	private Boolean historico;
	private String codigoEjis;
	private String bloqueado;
	private String descripcionJurisdiccion;
	

	
	@JsonProperty("descripcionJurisdiccion")
	public String getDescripcionJurisdiccion() {
		return descripcionJurisdiccion;
	}

	public void setDescripcionJuristiccion(String descripcionJurisdiccion) {
		this.descripcionJurisdiccion = descripcionJurisdiccion;
	}

	@JsonProperty("codigoEjis")
	public String getCodigoEjis() {
		return codigoEjis;
	}

	public void setCodigoEjis(String codigoEjis) {
		this.codigoEjis = codigoEjis;
	}

	@JsonProperty("bloqueado")
	public String getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(String bloqueado) {
		this.bloqueado = bloqueado;
	}

	/**
	 **/
	public PretensionItem idPretension(String idPretension) {
		this.idPretension = idPretension;
		return this;
	}

	@JsonProperty("idPretension")
	public String getIdPretension() {
		return idPretension;
	}

	public void setIdPretension(String idPretension) {
		this.idPretension = idPretension;
	}

	/**
	 * 
	 **/
	public PretensionItem descripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	@JsonProperty("descripcion")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion= descripcion;
	}
	/**
	 * 
	 **/
	public PretensionItem idInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
		return this;
	}

	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
	

	/**
	 * 
	 **/
	public PretensionItem usuModificacion(Long usuModificacion) {
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
	public PretensionItem fechaModificacion(Date fechaModificacion) {
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
	public PretensionItem idJurisdiccion(String idJurisdiccion) {
		this.idJurisdiccion= idJurisdiccion;
		return this;
	}

	@JsonProperty("idJurisdiccion")
	public String getIdJurisdiccion() {
		return idJurisdiccion;
	}

	public void setIdJurisdiccion(String idJurisdiccion) {
		this.idJurisdiccion= idJurisdiccion;
	}

	/**
	 **/
	public PretensionItem codigoExt(String codigoExt) {
		this.codigoExt = codigoExt;
		return this;
	}
	
	@JsonProperty("codigoExt")
	public String getCodigoExt() {
		return codigoExt;
	}

	public void setCodigoExt(String codigoExt) {
		this.codigoExt= codigoExt;
	}

	/**
	 **/
	public PretensionItem fechaBaja(String fechabaja) {
		this.fechabaja= fechabaja;
		return this;
	}	
	
	@JsonProperty("historico")
	public Boolean getHistorico() {
		return historico;
	}

	public void setHistorico(Boolean historico) {
		this.historico = historico;
	}

	@JsonProperty("fechabaja")
	public String getFechaBaja() {
		return fechabaja;
	}

	public void setFechaBaja(String fechabaja) {
		this.fechabaja= fechabaja;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PretensionItem juzgadoItem = (PretensionItem) o;
		return Objects.equals(this.idPretension, juzgadoItem.idPretension)
				&& Objects.equals(this.descripcion, juzgadoItem.descripcion)
				&& Objects.equals(this.fechabaja, juzgadoItem.fechabaja)
				&& Objects.equals(this.usuModificacion, juzgadoItem.usuModificacion)
				&& Objects.equals(this.fechaModificacion, juzgadoItem.fechaModificacion)
				&& Objects.equals(this.idJurisdiccion, juzgadoItem.idJurisdiccion)
				&& Objects.equals(this.idInstitucion, juzgadoItem.idInstitucion)
				&& Objects.equals(this.codigoExt, juzgadoItem.codigoExt);
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(idPretension, descripcion, usuModificacion, fechaModificacion, idJurisdiccion,
				idInstitucion, codigoExt, fechabaja);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ProcedimientoItem {\n");

		sb.append("    idPretension: ").append(toIndentedString(idPretension)).append("\n");
		sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
		sb.append("    usuModificacion: ").append(toIndentedString(usuModificacion)).append("\n");
		sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
		sb.append("    idJurisdiccion: ").append(toIndentedString(idJurisdiccion)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    codigoExt: ").append(toIndentedString(codigoExt)).append("\n");
		sb.append("    fechabaja: ").append(toIndentedString(fechabaja)).append("\n");

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
