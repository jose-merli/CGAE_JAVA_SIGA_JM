package org.itcgae.siga.DTOs.adm;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class MultiidiomaCatalogoItem {

	private String idRecurso;
	private String descripcionBusqueda;
	private String idLenguajeBuscar;
	private String idLenguajeTraducir;
	private String descripcionTraduccion;
	private String nombreTabla;
	private String idRecursoAlias;
	
	
	/**
	**/
	public MultiidiomaCatalogoItem idRecurso(String idRecurso) {
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
	public MultiidiomaCatalogoItem descripcionBusqueda(String descripcionBusqueda) {
		this.descripcionBusqueda = descripcionBusqueda;
		return this;
	}
	
	
	@JsonProperty("descripcionBusqueda")
	public String getDescripcionBusqueda() {
		return descripcionBusqueda;
	}
	
	
	public void setDescripcionBusqueda(String descripcionBusqueda) {
		this.descripcionBusqueda = descripcionBusqueda;
	}
	
	
	/**
	**/
	public MultiidiomaCatalogoItem idLenguajeBuscar(String idLenguajeBuscar) {
		this.idLenguajeBuscar = idLenguajeBuscar;
		return this;
	}
	
	
	@JsonProperty("idLenguajeBuscar")
	public String getIdLenguajeBuscar() {
		return idLenguajeBuscar;
	}
	
	
	public void setIdLenguajeBuscar(String idLenguajeBuscar) {
		this.idLenguajeBuscar = idLenguajeBuscar;
	}
	
	
	/**
	**/
	public MultiidiomaCatalogoItem idLenguajeTraducir(String idLenguajeTraducir) {
		this.idLenguajeTraducir = idLenguajeTraducir;
		return this;
	}
	
	
	@JsonProperty("idLenguajeTraducir")
	public String getIdLenguajeTraducir() {
		return idLenguajeTraducir;
	}
	
	
	public void setIdLenguajeTraducir(String idLenguajeTraducir) {
		this.idLenguajeTraducir = idLenguajeTraducir;
	}
	
	
	/**
	**/
	public MultiidiomaCatalogoItem descripcionTraduccion(String descripcionTraduccion) {
		this.descripcionTraduccion = descripcionTraduccion;
		return this;
	}
	
	
	@JsonProperty("descripcionTraduccion")
	public String getDescripcionTraduccion() {
		return descripcionTraduccion;
	}
	
	
	public void setDescripcionTraduccion(String descripcionTraduccion) {
		this.descripcionTraduccion = descripcionTraduccion;
	}
	
	
	/**
	**/
	public MultiidiomaCatalogoItem nombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
		return this;
	}
	
	
	@JsonProperty("nombreTabla")
	public String getNombreTabla() {
		return nombreTabla;
	}
	
	
	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}
	
	
	/**
	**/
	public MultiidiomaCatalogoItem idRecursoAlias(String idRecursoAlias) {
		this.idRecursoAlias = idRecursoAlias;
		return this;
	}
	
	
	@JsonProperty("idRecursoAlias")
	public String getIdRecursoAlias() {
		return idRecursoAlias;
	}
	
	
	public void setIdRecursoAlias(String idRecursoAlias) {
		this.idRecursoAlias = idRecursoAlias;
	}
	
	
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		MultiidiomaCatalogoItem multiidiomaCatalogoItem = (MultiidiomaCatalogoItem) o;
		return Objects.equals(this.idRecurso, multiidiomaCatalogoItem.idRecurso) &&
        Objects.equals(this.descripcionBusqueda, multiidiomaCatalogoItem.descripcionBusqueda) &&
        Objects.equals(this.idLenguajeBuscar, multiidiomaCatalogoItem.idLenguajeBuscar) &&
        Objects.equals(this.idLenguajeTraducir, multiidiomaCatalogoItem.idLenguajeTraducir) &&
        Objects.equals(this.descripcionTraduccion, multiidiomaCatalogoItem.descripcionTraduccion) &&
        Objects.equals(this.nombreTabla, multiidiomaCatalogoItem.nombreTabla) &&
        Objects.equals(this.idRecursoAlias, multiidiomaCatalogoItem.idRecursoAlias);
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(idRecurso, descripcionBusqueda, idLenguajeBuscar, idLenguajeTraducir, descripcionTraduccion, nombreTabla, idRecursoAlias);
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class MultiidiomaCatalogoItem {\n");
    
		sb.append("    idRecurso: ").append(toIndentedString(idRecurso)).append("\n");
		sb.append("    descripcionBusqueda: ").append(toIndentedString(descripcionBusqueda)).append("\n");
		sb.append("    idLenguajeBuscar: ").append(toIndentedString(idLenguajeBuscar)).append("\n");
		sb.append("    idLenguajeTraducir: ").append(toIndentedString(idLenguajeTraducir)).append("\n");
		sb.append("    descripcionTraduccion: ").append(toIndentedString(descripcionTraduccion)).append("\n");
		sb.append("    nombreTabla: ").append(toIndentedString(nombreTabla)).append("\n");
		sb.append("    idRecursoAlias: ").append(toIndentedString(idRecursoAlias)).append("\n");
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
