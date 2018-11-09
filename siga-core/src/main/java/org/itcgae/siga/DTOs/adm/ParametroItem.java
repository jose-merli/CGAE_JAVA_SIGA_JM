package org.itcgae.siga.DTOs.adm;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class ParametroItem {

	private String modulo;
	private String parametro;
	private String valor;
	private String idInstitucion;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaBaja;
	private String idRecurso;
	private String descripcion;
	private String posibleEliminar;
	private String idinstitucionActual;
	
	


	/**
	**/
	public ParametroItem modulo(String modulo) {
		this.modulo = modulo;
		return this;
	}
	
	@JsonProperty("modulo")
	public String getModulo() {
		return modulo;
	}
	
	
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	
	
	/**
	**/
	public ParametroItem parametro(String parametro) {
		this.parametro = parametro;
		return this;
	}
	
	
	@JsonProperty("parametro")
	public String getParametro() {
		return parametro;
	}
	
	
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
	
	
	/**
	**/
	public ParametroItem valor(String valor) {
		this.valor = valor;
		return this;
	}
	
	
	
	@JsonProperty("valor")
	public String getValor() {
		return valor;
	}
	
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
	/**
	**/
	public ParametroItem idInstitucion(String idInstitucion) {
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
	public ParametroItem fechaBaja(Date fechaBaja) {
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
	
	
	/**
	**/
	public ParametroItem idRecurso(String idRecurso) {
		this.idRecurso = idRecurso;
		return this;
	}
	
	
	@JsonProperty("idRecurso")
	public String getIdRecurso() {
		return idRecurso;
	}

	public void setIdRecurso(String idRecurso) {
		this.idRecurso = idRecurso;
	}

	
	/**
	**/
	public ParametroItem descripcion(String descripcion) {
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
	**/
	public ParametroItem posibleEliminar(String posibleEliminar) {
		this.posibleEliminar = posibleEliminar;
		return this;
	}
	
	@JsonProperty("posibleEliminar")
	public String getPosibleEliminar() {
		return posibleEliminar;
	}

	public void setPosibleEliminar(String posibleEliminar) {
		this.posibleEliminar = posibleEliminar;
	}
	
	/**
	**/
	public ParametroItem idinstitucionActual(String idinstitucionActual) {
		this.idinstitucionActual = idinstitucionActual;
		return this;
	}

	@JsonProperty("idinstitucionActual")
	public String getIdinstitucionActual() {
		return idinstitucionActual;
	}

	public void setIdinstitucionActual(String idinstitucionActual) {
		this.idinstitucionActual = idinstitucionActual;
	}
	
	
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ParametroItem parametroItem = (ParametroItem) o;
		return Objects.equals(this.modulo, parametroItem.modulo) && 
				Objects.equals(this.parametro, parametroItem.parametro) &&
				Objects.equals(this.valor, parametroItem.valor) &&
				Objects.equals(this.idInstitucion, parametroItem.idInstitucion) &&
				Objects.equals(this.fechaBaja, parametroItem.fechaBaja) &&
				Objects.equals(this.idRecurso, parametroItem.idRecurso) &&
				Objects.equals(this.descripcion, parametroItem.descripcion) &&
				Objects.equals(this.posibleEliminar, parametroItem.posibleEliminar) &&
				Objects.equals(this.idinstitucionActual, parametroItem.idinstitucionActual);
	}

	@Override
	public int hashCode() {
		return Objects.hash(modulo, parametro, valor, idInstitucion, fechaBaja, idRecurso, descripcion, posibleEliminar, idinstitucionActual);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ParametroItem {\n");

		sb.append("    modulo: ").append(toIndentedString(modulo)).append("\n");
		sb.append("    parametro: ").append(toIndentedString(parametro)).append("\n");
		sb.append("    valor: ").append(toIndentedString(valor)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
		sb.append("    idRecurso: ").append(toIndentedString(idRecurso)).append("\n");
		sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
		sb.append("    posibleEliminar: ").append(toIndentedString(posibleEliminar)).append("\n");
		sb.append("    idinstitucionActual: ").append(toIndentedString(idinstitucionActual)).append("\n");
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
