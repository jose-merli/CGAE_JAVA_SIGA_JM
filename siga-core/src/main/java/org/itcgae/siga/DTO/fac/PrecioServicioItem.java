package org.itcgae.siga.DTO.fac;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrecioServicioItem {
	

	  private String label = null;
	  private String value = null;
	  private String idPeriodicidad = null;

	  
	  /**
	   **/
	  public PrecioServicioItem label(String label) {
	    this.label = label;
	    return this;
	  }
	  
	  
	  @JsonProperty("label")
	  public String getLabel() {
	    return label;
	  }
	  public void setLabel(String label) {
	    this.label = label;
	  }

	  
	  /**
	   **/
	  public PrecioServicioItem value(String value) {
	    this.value = value;
	    return this;
	  }
	  
	  
	  @JsonProperty("value")
	  public String getValue() {
	    return value;
	  }
	  public void setValue(String value) {
	    this.value = value;
	  }
	  
	  /**
	   **/
	  public PrecioServicioItem idPeriodicidad(String idPeriodicidad) {
	    this.idPeriodicidad = idPeriodicidad;
	    return this;
	  }
	  
	  
	  @JsonProperty("idPeriodicidad")
	  public String getIdPeriodicidad() {
	    return idPeriodicidad;
	  }
	  public void setIdPeriodicidad(String idPeriodicidad) {
	    this.idPeriodicidad = idPeriodicidad;
	  }
	  

	  @Override
	  public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    PrecioServicioItem precioServicioItem = (PrecioServicioItem) o;
	    return Objects.equals(this.label, precioServicioItem.label) &&
	    		 Objects.equals(this.value, precioServicioItem.value) &&
	    		 Objects.equals(this.idPeriodicidad, precioServicioItem.idPeriodicidad);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(value, label);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class PrecioServicioItem {\n");
	    sb.append("    label: ").append(toIndentedString(label)).append("\n");
	    sb.append("    value: ").append(toIndentedString(value)).append("\n");
	    sb.append("    idPeriodicidad: ").append(toIndentedString(idPeriodicidad)).append("\n");

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
