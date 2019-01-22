package org.itcgae.siga.DTOs.age;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComboPlantillaEnvioDTO   {
  
  private List<ComboPlantillaEnvioItem> comboItems = new ArrayList<ComboPlantillaEnvioItem>();
  private Error error = null;

  
  /**
   **/
  public ComboPlantillaEnvioDTO comboItems(List<ComboPlantillaEnvioItem> comboItems) {
    this.comboItems = comboItems;
    return this;
  }
  
  
  @JsonProperty("comboItems")
  public List<ComboPlantillaEnvioItem> getComboAgeItems() {
    return comboItems;
  }
  public void setComboAgeItems(List<ComboPlantillaEnvioItem> comboItems) {
    this.comboItems = comboItems;
  }

  
  /**
   **/
  public ComboPlantillaEnvioDTO error(Error error) {
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
    ComboPlantillaEnvioDTO comboDTO = (ComboPlantillaEnvioDTO) o;
    return Objects.equals(this.comboItems, comboDTO.comboItems) &&
        Objects.equals(this.error, comboDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comboItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ComboAgeDTO {\n");
    
    sb.append("    comboItems: ").append(toIndentedString(comboItems)).append("\n");
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

