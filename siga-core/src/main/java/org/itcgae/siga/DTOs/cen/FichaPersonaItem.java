package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FichaPersonaItem {
	
	private String colegio;
	private String idPersona;
	private String nif;
	private String nombre;
	private String numeroColegiado;
	private String residente;
	private String situacion;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private String fechaAlta;
	
	
	/**
	 */
	public FichaPersonaItem colegio(String colegio){
		this.colegio = colegio;
		return this;
	}
	
	
	@JsonProperty("colegio")
	public String getColegio() {
		return colegio;
	}
	public void setColegio(String colegio) {
		this.colegio = colegio;
	}
	
	
	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	
	@JsonProperty("nif")
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@JsonProperty("numeroColegiado")
	public String getNumeroColegiado() {
		return numeroColegiado;
	}
	public void setNumeroColegiado(String numeroColegiado) {
		this.numeroColegiado = numeroColegiado;
	}
	
	@JsonProperty("residente")
	public String getResidente() {
		return residente;
	}
	public void setResidente(String residente) {
		this.residente = residente;
	}
	
	@JsonProperty("situacion")
	public String getSituacion() {
		return situacion;
	}
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	
	@JsonProperty("fechaAlta")
	public String getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class FichaPersonaItem {\n");
	    
	    sb.append("    colegio: ").append(toIndentedString(colegio)).append("\n");
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
	    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
	    sb.append("    numeroColegiado: ").append(toIndentedString(numeroColegiado)).append("\n");
	    sb.append("    residente: ").append(toIndentedString(residente)).append("\n");
	    sb.append("    situacion: ").append(toIndentedString(situacion)).append("\n");
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

	
	@Override
	public int hashCode() {
		return Objects.hash(colegio, idPersona, nif, nombre, numeroColegiado, residente, situacion, fechaAlta);
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	
}
