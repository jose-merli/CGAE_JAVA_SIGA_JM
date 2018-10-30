package org.itcgae.siga.DTOs.age;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ComboPlantillaEnvioItem   {
  
  private String label = null;
  private NotificacionEventoItem value = null;

  
  /**
   **/
  public ComboPlantillaEnvioItem label(String label) {
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
  public ComboPlantillaEnvioItem idPlantilla(NotificacionEventoItem value) {
    this.value = value;
    return this;
  }
   
  @JsonProperty("value")
  public NotificacionEventoItem getValue() {
    return value;
  }
  
  public void setValue(NotificacionEventoItem value) {
    this.value = value;
  }
   

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ComboPlantillaEnvioItem comboItem = (ComboPlantillaEnvioItem) o;
    return Objects.equals(this.label, comboItem.label) &&
        Objects.equals(this.value, comboItem.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ComboAgeItem {\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    
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

