package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class BusquedaPerJuridicaSearchDTO {

	private String tipo;
	private String nif;
	private String denominacion;
	private String[] idInstitucion;
	private String numColegiado;
	private String abreviatura;
	private Error error = null;
	
	/**
	 *
	 */
	public BusquedaPerJuridicaSearchDTO tipo(String tipo) {
		this.tipo = tipo;
		return this;
	}

	@JsonProperty("tipo")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 *
	 */
	public BusquedaPerJuridicaSearchDTO nif(String nif) {
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
	 *
	 */
	public BusquedaPerJuridicaSearchDTO denominacion(String denominacion) {
		this.denominacion = denominacion;
		return this;
	}

	@JsonProperty("denominacion")
	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	/**
	 *
	 */
	public BusquedaPerJuridicaSearchDTO idInstitucion(String[] idInstitucion) {
		this.idInstitucion = idInstitucion;
		return this;
	}

	@JsonProperty("idInstitucion")
	public String[] getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String[] idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 *
	 */
	public BusquedaPerJuridicaSearchDTO numColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
		return this;
	}

	@JsonProperty("numColegiado")
	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	
	/**
	 *
	 */
	public BusquedaPerJuridicaSearchDTO error(Error error){
		this.error = error;
		return this;
	}

	@JsonProperty("error")
	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BusquedaPerJuridicaSearchDTO busquedaJuridicaSearchDTO = (BusquedaPerJuridicaSearchDTO) o;
		return Objects.equals(this.tipo, busquedaJuridicaSearchDTO.tipo)
				&& Objects.equals(this.nif, busquedaJuridicaSearchDTO.nif)
				&& Objects.equals(this.denominacion, busquedaJuridicaSearchDTO.denominacion)
				&& Objects.equals(this.idInstitucion, busquedaJuridicaSearchDTO.idInstitucion)
				&& Objects.equals(this.abreviatura, busquedaJuridicaSearchDTO.abreviatura)
				&& Objects.equals(this.numColegiado, busquedaJuridicaSearchDTO.numColegiado)
        		&& Objects.equals(this.error, this.error);


	}

	@Override
	public int hashCode() {
		return Objects.hash(tipo, nif, denominacion, idInstitucion, numColegiado);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class BusquedaPerJuridicaSearchDTO {\n");

		sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
		sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
		sb.append("    denominacion: ").append(toIndentedString(denominacion)).append("\n");
		sb.append("    abreviatura: ").append(toIndentedString(abreviatura)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    numColegiado: ").append(toIndentedString(numColegiado)).append("\n");
	    sb.append("    error: ").append(toIndentedString(error)).append("\n");
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

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

}
