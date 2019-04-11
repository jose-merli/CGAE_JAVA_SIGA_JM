package org.itcgae.siga.DTOs.age;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComboPlantillasDTO   {
  
  private List<ComboPlantillasItem> comboPlantillasItems = new ArrayList<ComboPlantillasItem>();
  private Error error = null;

  
  /**
   **/
  public ComboPlantillasDTO comboPlantillasItems(List<ComboPlantillasItem> comboPlantillasItems) {
    this.comboPlantillasItems = comboPlantillasItems;
    return this;
  }
  
  
  @JsonProperty("comboPlantillasItems")
  public List<ComboPlantillasItem> getComboPlantillasItems() {
    return comboPlantillasItems;
  }
  public void setComboPlantillasItems(List<ComboPlantillasItem> comboPlantillasItems) {
    this.comboPlantillasItems = comboPlantillasItems;
  }

  
  /**
   **/
  public ComboPlantillasDTO error(Error error) {
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
    ComboPlantillasDTO comboDTO = (ComboPlantillasDTO) o;
    return Objects.equals(this.comboPlantillasItems, comboDTO.comboPlantillasItems) &&
        Objects.equals(this.error, comboDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comboPlantillasItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ComboAgeDTO {\n");
    
    sb.append("    comboItems: ").append(toIndentedString(comboPlantillasItems)).append("\n");
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
}

