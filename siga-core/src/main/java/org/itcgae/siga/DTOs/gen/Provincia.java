package org.itcgae.siga.DTOs.gen;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Provincia {
 private String codigo;
 ComunidadAutonoma comunidadAutonoma = new ComunidadAutonoma();
 private String denominacion;


 // Getter Methods 
 @JsonProperty("codigo") 
 public String getCodigo() {
  return codigo;
 }
 @JsonProperty("comunidadAutonoma") 
 public ComunidadAutonoma getComunidadAutonoma() {
  return comunidadAutonoma;
 }
 @JsonProperty("denominacion") 
 public String getDenominacion() {
  return denominacion;
 }

 // Setter Methods 

 public void setCodigo(String codigo) {
  this.codigo = codigo;
 }

 public void setComunidadAutonoma(ComunidadAutonoma comunidadAutonoma) {
  this.comunidadAutonoma = comunidadAutonoma;
 }

 public void setDenominacion(String denominacion) {
  this.denominacion = denominacion;
 }
}
