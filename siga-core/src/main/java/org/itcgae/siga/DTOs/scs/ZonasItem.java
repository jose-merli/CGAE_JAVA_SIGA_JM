package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.ComboItem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZonasItem {

	private String idzona;
	private String descripcionzona;
	private String idsubzona;
	private String idinstitucion;
	private Long usuModificacion;
	private Date fechaModificacion;
	private String descripcionsubzona;
	private String idpartido;
	private String descripcionpartido;
	private String [] jurisdiccion;
	private ComboItem [] partidosJudiciales;
	private boolean historico;
	private String fechabaja;
	private String idPartidosJudiciales;
	private String idsConjuntoSubzonas;
	private String nombrePartidosJudiciales;

	/**
	 **/
	public ZonasItem idzona(String idzona) {
		this.idzona = idzona;
		return this;
	}

	@JsonProperty("idzona")
	public String getIdzona() {
		return idzona;
	}

	public void setIdzona(String idzona) {
		this.idzona = idzona;
	}

	/**
	 * 
	 **/
	public ZonasItem descripcionzona(String descripcionzona) {
		this.descripcionzona = descripcionzona;
		return this;
	}

	@JsonProperty("descripcionzona")
	public String getDescripcionzona() {
		return descripcionzona;
	}

	public void setDescripcionzona(String descripcionzona) {
		this.descripcionzona = descripcionzona;
	}

	/**
	 * 
	 **/
	public ZonasItem idsubzona(String idsubzona) {
		this.idsubzona = idsubzona;
		return this;
	}

	@JsonProperty("idsubzona")
	public String getIdsubzona() {
		return idsubzona;
	}

	public void setIdsubzona(String idsubzona) {
		this.idsubzona = idsubzona;
	}
	
	/**
	 * 
	 **/
	public ZonasItem idinstitucion(String idinstitucion) {
		this.idinstitucion = idinstitucion;
		return this;
	}

	@JsonProperty("idinstitucion")
	public String getIdinstitucion() {
		return idinstitucion;
	}

	public void setIdinstitucion(String idinstitucion) {
		this.idinstitucion = idinstitucion;
	}
	
	

	/**
	 * 
	 **/
	public ZonasItem usuModificacion(Long usuModificacion) {
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
	public ZonasItem fechaModificacion(Date fechaModificacion) {
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
	public ZonasItem descripcionsubzona(String descripcionsubzona) {
		this.descripcionsubzona = descripcionsubzona;
		return this;
	}

	@JsonProperty("descripcionsubzona")
	public String getDescripcionsubzona() {
		return descripcionsubzona;
	}

	public void setDescripcionsubzona(String descripcionsubzona) {
		this.descripcionsubzona = descripcionsubzona;
	}

	/**
	 * 
	 **/
	public ZonasItem idpartido(String idpartido) {
		this.idpartido = idpartido;
		return this;
	}

	@JsonProperty("idpartido")
	public String getIdpartido() {
		return idpartido;
	}

	public void setIdpartido(String idpartido) {
		this.idpartido = idpartido;
	}
	
	/**
	 * 
	 **/
	public ZonasItem descripcionpartido(String descripcionpartido) {
		this.descripcionpartido = descripcionpartido;
		return this;
	}

	@JsonProperty("descripcionpartido")
	public String getDescripcionpartido() {
		return descripcionpartido;
	}

	public void setDescripcionpartido(String descripcionpartido) {
		this.descripcionpartido = descripcionpartido;
	}
	

	/**
	 * 
	 **/
	public ZonasItem jurisdiccion(String [] jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
		return this;
	}

	public String[] getJurisdiccion() {
		return jurisdiccion;
	}

	public void setJurisdiccion(String[] jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}
	
	/**
	 * 
	 **/
	public ZonasItem partidosJudiciales(ComboItem [] partidosJudiciales) {
		this.partidosJudiciales = partidosJudiciales;
		return this;
	}

	public ComboItem[] getPartidosJudiciales() {
		return partidosJudiciales;
	}

	public void setPartidosJudiciales(ComboItem[] partidosJudiciales) {
		this.partidosJudiciales = partidosJudiciales;
	}
	
	/**
	 * 
	 **/
	public ZonasItem historico(boolean historico) {
		this.historico = historico;
		return this;
	}

	@JsonProperty("historico")
	public boolean getHistorico() {
		return historico;
	}

	public void setHistorico(boolean historico) {
		this.historico = historico;
	}
	
	/**
	 * 
	 **/
	public ZonasItem fechabaja(String fechabaja) {
		this.fechabaja = fechabaja;
		return this;
	}

	@JsonProperty("fechabaja")
	public String getFechabaja() {
		return fechabaja;
	}

	public void setFechabaja(String fechabaja) {
		this.fechabaja = fechabaja;
	}
	
	/**
	 **/
	public ZonasItem idPartidosJudiciales(String idPartidosJudiciales) {
		this.idPartidosJudiciales = idPartidosJudiciales;
		return this;
	}

	@JsonProperty("idpartidosJudiciales")
	public String getIdPartidosJudiciales() {
		return idPartidosJudiciales;
	}

	public void setIdPartidosJudiciales(String idPartidosJudiciales) {
		this.idPartidosJudiciales = idPartidosJudiciales;
	}
	
	/**
	 **/
	public ZonasItem idsConjuntoSubzonas(String idsConjuntoSubzonas) {
		this.idsConjuntoSubzonas = idsConjuntoSubzonas;
		return this;
	}

	@JsonProperty("idsConjuntoSubzonas")
	public String getIdsConjuntoSubzonas() {
		return idsConjuntoSubzonas;
	}

	public void setIdsConjuntoSubzonas(String idsConjuntoSubzonas) {
		this.idsConjuntoSubzonas = idsConjuntoSubzonas;
	}
	
	/**
	 **/
	public ZonasItem nombrePartidosJudiciales(String nombrePartidosJudiciales) {
		this.nombrePartidosJudiciales = nombrePartidosJudiciales;
		return this;
	}
	
	@JsonProperty("nombrePartidosJudiciales")
	public String getNombrePartidosJudiciales() {
		return nombrePartidosJudiciales;
	}

	public void setNombrePartidosJudiciales(String nombrePartidosJudiciales) {
		this.nombrePartidosJudiciales = nombrePartidosJudiciales;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ZonasItem zonasItem = (ZonasItem) o;
		return Objects.equals(this.idzona, zonasItem.idzona)
				&& Objects.equals(this.descripcionzona, zonasItem.descripcionzona)
				&& Objects.equals(this.idsubzona, zonasItem.idsubzona)
				&& Objects.equals(this.usuModificacion, zonasItem.usuModificacion)
				&& Objects.equals(this.fechaModificacion, zonasItem.fechaModificacion)
				&& Objects.equals(this.descripcionsubzona, zonasItem.descripcionsubzona)
				&& Objects.equals(this.jurisdiccion, zonasItem.jurisdiccion)
				&& Objects.equals(this.descripcionpartido, zonasItem.descripcionpartido)
				&& Objects.equals(this.idinstitucion, zonasItem.idinstitucion)
				&& Objects.equals(this.partidosJudiciales, zonasItem.partidosJudiciales)
				&& Objects.equals(this.historico, zonasItem.historico)
				&& Objects.equals(this.fechabaja, zonasItem.fechabaja)
				&& Objects.equals(this.idPartidosJudiciales, zonasItem.idPartidosJudiciales)
				&& Objects.equals(this.idsConjuntoSubzonas, zonasItem.idsConjuntoSubzonas)
				&& Objects.equals(this.nombrePartidosJudiciales, zonasItem.nombrePartidosJudiciales);

	}

	@Override
	public int hashCode() {
		return Objects.hash(idzona, descripcionzona, idsubzona, usuModificacion, fechaModificacion, descripcionsubzona,
				idpartido, descripcionpartido, idinstitucion, partidosJudiciales, historico, fechabaja, 
				idPartidosJudiciales, idsConjuntoSubzonas, nombrePartidosJudiciales);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ZonaItem {\n");

		sb.append("    idzona: ").append(toIndentedString(idzona)).append("\n");
		sb.append("    descripcionzona: ").append(toIndentedString(descripcionzona)).append("\n");
		sb.append("    idsubzona: ").append(toIndentedString(idsubzona)).append("\n");
		sb.append("    usuModificacion: ").append(toIndentedString(usuModificacion)).append("\n");
		sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
		sb.append("    descripcionsubzona: ").append(toIndentedString(descripcionsubzona)).append("\n");
		sb.append("    idpartido: ").append(toIndentedString(idpartido)).append("\n");
		sb.append("    descripcionpartido: ").append(toIndentedString(descripcionpartido)).append("\n");
		sb.append("    idinstitucion: ").append(toIndentedString(idinstitucion)).append("\n");
		sb.append("    partidosJudiciales: ").append(toIndentedString(partidosJudiciales)).append("\n");
		sb.append("    historico: ").append(toIndentedString(historico)).append("\n");
		sb.append("    fechabaja: ").append(toIndentedString(fechabaja)).append("\n");
		sb.append("    idPartidosJudiciales: ").append(toIndentedString(idPartidosJudiciales)).append("\n");
		sb.append("    idsConjuntoSubzonas: ").append(toIndentedString(idsConjuntoSubzonas)).append("\n");
		sb.append("    nombrePartidosJudiciales: ").append(toIndentedString(nombrePartidosJudiciales)).append("\n");

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
