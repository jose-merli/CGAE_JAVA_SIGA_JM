package org.itcgae.siga.DTOs.gen;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class ComboDTO2 {
  
  private List<ComboItem2> combooItems = new ArrayList<ComboItem2>();
  private Error error = null;

  
  /**
   **/
  public ComboDTO2 combooItems(List<ComboItem2> combooItems) {
    this.combooItems = combooItems;
    return this;
  }
  
  
  @JsonProperty("combooItems")
  public List<ComboItem2> getCombooItems() {
    return combooItems;
  }
  public void setCombooItems(List<ComboItem2> combooItems) {
    this.combooItems = combooItems;
  }

  
  /**
   **/
  public ComboDTO2 error(Error error) {
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ComboDTO2 comboDTO = (ComboDTO2) o;
    return Objects.equals(this.combooItems, comboDTO.combooItems) &&
        Objects.equals(this.error, comboDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(combooItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ComboDTO {\n");
    
    sb.append("    combooItems: ").append(toIndentedString(combooItems)).append("\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

