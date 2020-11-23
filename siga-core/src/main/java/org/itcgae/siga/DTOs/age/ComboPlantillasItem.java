package org.itcgae.siga.DTOs.age;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComboPlantillasItem   {
  
  private String label = null;
  private String value = null;
  private String subValue = null;

  
  /**
   **/
  public ComboPlantillasItem label(String label) {
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
  public ComboPlantillasItem idPlantilla(String value) {
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
  public ComboPlantillasItem subValue(String subValue) {
    this.subValue = subValue;
    return this;
  }
   
  @JsonProperty("subValue")
  public String getSubValue() {
    return subValue;
  }
  
  public void setSubValue(String subValue) {
    this.subValue = subValue;
  }
   

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ComboPlantillasItem comboItem = (ComboPlantillasItem) o;
    return Objects.equals(this.label, comboItem.label) &&
        Objects.equals(this.value, comboItem.value) &&
        Objects.equals(this.subValue, comboItem.subValue);
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
    sb.append("    subValue: ").append(toIndentedString(subValue)).append("\n");

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

