package org.itcgae.siga.DTOs.gen;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TipoDeFestivo {
	 private String codigo;
	 private String denominacion;


	 // Getter Methods 
	 @JsonProperty("codigo") 
	 public String getCodigo() {
	  return codigo;
	 }
	 @JsonProperty("denominacion") 
	 public String getDenominacion() {
	  return denominacion;
	 }

	 // Setter Methods 

	 public void setCodigo(String codigo) {
	  this.codigo = codigo;
	 }

	 public void setDenominacion(String denominacion) {
	  this.denominacion = denominacion;
	 }
}
