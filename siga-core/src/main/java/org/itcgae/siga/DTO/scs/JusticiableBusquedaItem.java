package org.itcgae.siga.DTO.scs;

import java.util.Date;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.ComboItem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JusticiableBusquedaItem {

	private String idPersona;
	private String idInstitucion;
	private String nif;
	private String nombre;
	private String asuntos;
	private Date fechaModificacion;
	private String idRol;
	private String anio;
	private String anioHasta;
	private String anioDesde;
	private String idPoblacion;
	private String idProvincia;
	private String codigoPostal;
	
	
	/**
	 **/
	public JusticiableBusquedaItem idPersona(String idPersona) {
		this.idPersona = idPersona;
		return this;
	}

	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	
	/**
	 **/
	public JusticiableBusquedaItem idInstitucion(String idInstitucion) {
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
	 **/
	public JusticiableBusquedaItem nif(String nif) {
		this.nif = nif;
		return this;
	}

	@JsonProperty("nif")
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 **/
	public JusticiableBusquedaItem nombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 **/
	public JusticiableBusquedaItem asuntos(String asuntos) {
		this.asuntos = asuntos;
		return this;
	}

	@JsonProperty("asuntos")
	public String getAsuntos() {
		return asuntos;
	}

	public void setAsuntos(String asuntos) {
		this.asuntos = asuntos;
	}

	/**
	 **/
	public JusticiableBusquedaItem fechaModificacion(Date fechaModificacion) {
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
	public JusticiableBusquedaItem idRol(String idRol) {
		this.idRol = idRol;
		return this;
	}

	@JsonProperty("idRol")
	public String getIdRol() {
		return idRol;
	}

	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}

	/**
	 **/
	public JusticiableBusquedaItem anio(String anio) {
		this.anio = anio;
		return this;
	}

	@JsonProperty("anio")
	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	/**
	 **/
	public JusticiableBusquedaItem anioHasta(String anioHasta) {
		this.anioHasta = anioHasta;
		return this;
	}

	@JsonProperty("anioHasta")
	public String getAnioHasta() {
		return anioHasta;
	}

	public void setAnioHasta(String anioHasta) {
		this.anioHasta = anioHasta;
	}

	/**
	 **/
	public JusticiableBusquedaItem anioDesde(String anioDesde) {
		this.anioDesde = anioDesde;
		return this;
	}

	@JsonProperty("anioDesde")
	public String getAnioDesde() {
		return anioDesde;
	}

	public void setAnioDesde(String anioDesde) {
		this.anioDesde = anioDesde;
	}

	/**
	 **/
	public JusticiableBusquedaItem idPoblacion(String idPoblacion) {
		this.idPoblacion = idPoblacion;
		return this;
	}

	@JsonProperty("idPoblacion")
	public String getIdPoblacion() {
		return idPoblacion;
	}

	public void setIdPoblacion(String idPoblacion) {
		this.idPoblacion = idPoblacion;
	}

	/**
	 **/
	public JusticiableBusquedaItem idProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
		return this;
	}

	@JsonProperty("idProvincia")
	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	/**
	 **/
	public JusticiableBusquedaItem codigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
		return this;
	}

	@JsonProperty("codigoPostal")
	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		JusticiableBusquedaItem justiciableBusquedaItem = (JusticiableBusquedaItem) o;
		return Objects.equals(this.idPersona, justiciableBusquedaItem.idPersona)
				&& Objects.equals(this.idInstitucion, justiciableBusquedaItem.idInstitucion)
				&& Objects.equals(this.nif, justiciableBusquedaItem.nif)
				&& Objects.equals(this.nombre, justiciableBusquedaItem.nombre)
				&& Objects.equals(this.fechaModificacion, justiciableBusquedaItem.fechaModificacion)
				&& Objects.equals(this.asuntos, justiciableBusquedaItem.asuntos)
				&& Objects.equals(this.idRol, justiciableBusquedaItem.idRol)
				&& Objects.equals(this.anio, justiciableBusquedaItem.anio)
				&& Objects.equals(this.anioHasta, justiciableBusquedaItem.anioHasta)
				&& Objects.equals(this.anioDesde, justiciableBusquedaItem.anioDesde)
				&& Objects.equals(this.idPoblacion, justiciableBusquedaItem.idPoblacion)
				&& Objects.equals(this.idProvincia, justiciableBusquedaItem.idProvincia)
				&& Objects.equals(this.codigoPostal, justiciableBusquedaItem.codigoPostal);
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(idPersona, idInstitucion, nif, nombre, fechaModificacion, asuntos, idRol,
				anio, anioHasta, anioDesde, idPoblacion, idProvincia, codigoPostal);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ProcedimientoItem {\n");

		sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
		sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
		sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
		sb.append("    asuntos: ").append(toIndentedString(asuntos)).append("\n");
		sb.append("    idRol: ").append(toIndentedString(idRol)).append("\n");
		sb.append("    anio: ").append(toIndentedString(anio)).append("\n");
		sb.append("    anioHasta: ").append(toIndentedString(anioHasta)).append("\n");
		sb.append("    anioDesde: ").append(toIndentedString(anioDesde)).append("\n");
		sb.append("    idPoblacion: ").append(toIndentedString(idPoblacion)).append("\n");
		sb.append("    idProvincia: ").append(toIndentedString(idProvincia)).append("\n");
		sb.append("    codigoPostal: ").append(toIndentedString(codigoPostal)).append("\n");

		
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
