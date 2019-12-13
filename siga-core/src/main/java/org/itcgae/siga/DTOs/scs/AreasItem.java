package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AreasItem {

	private String idArea;
	private Short idInstitucion;
	private String idMateria;
	private String contenido;
	private String nombreMateria;
	private String nombreArea;
//	private ComboItem [] partidosJudiciales;
	private String jurisdicciones;
	private String jurisdiccion;
	private Date fechabaja;
	private boolean historico;
	
	/**
	 * 
	 **/
	public AreasItem idInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
		return this;
	}

	@JsonProperty("idInstitucion")
	public Short getidInstitucion() {
		return idInstitucion;
	}

	public void setidInstitucion(Short idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
	


	/**
	 * 
	 **/
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AreasItem zonasItem = (AreasItem) o;
		return Objects.equals(this.idArea, zonasItem.idArea)
				&& Objects.equals(this.idInstitucion, zonasItem.idInstitucion)
				&& Objects.equals(this.contenido, zonasItem.contenido)
				&& Objects.equals(this.nombreMateria, zonasItem.nombreMateria)
				&& Objects.equals(this.jurisdicciones, zonasItem.jurisdicciones);

	}

	@Override
	public int hashCode() {
		return Objects.hash(idArea, idInstitucion, nombreMateria, contenido, jurisdicciones);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ZonaItem {\n");

		sb.append("    idArea: ").append(toIndentedString(idArea)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    nombreMateria: ").append(toIndentedString(nombreMateria)).append("\n");
		sb.append("    contenido: ").append(toIndentedString(contenido)).append("\n");
		sb.append("    jurisdicciones: ").append(toIndentedString(jurisdicciones)).append("\n");
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

	public String getIdArea() {
		return idArea;
	}

	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getNombreMateria() {
		return nombreMateria;
	}

	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}

	public String getJurisdicciones() {
		return jurisdicciones;
	}

	public void setJurisdicciones(String jurisdicciones) {
		this.jurisdicciones = jurisdicciones;
	}

	public String getNombreArea() {
		return nombreArea;
	}

	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}

	public String getJurisdiccion() {
		return jurisdiccion;
	}

	public void setJurisdiccion(String jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}

	public Date getFechabaja() {
		return fechabaja;
	}

	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}

	public boolean isHistorico() {
		return historico;
	}

	public void setHistorico(boolean historico) {
		this.historico = historico;
	}

	public String getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(String idMateria) {
		this.idMateria = idMateria;
	}
}
