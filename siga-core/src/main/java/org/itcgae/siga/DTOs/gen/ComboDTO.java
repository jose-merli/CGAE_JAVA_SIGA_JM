package org.itcgae.siga.DTOs.gen;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.DTOs.gen.Error;
import java.util.ArrayList;
import java.util.List;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class ComboDTO   {
  
  private List<ComboItem> combooItems = new ArrayList<ComboItem>();
  private Error error = null;

  
  /**
   **/
  public ComboDTO combooItems(List<ComboItem> combooItems) {
    this.combooItems = combooItems;
    return this;
  }
  
  
  @JsonProperty("combooItems")
  public List<ComboItem> getCombooItems() {
    return combooItems;
  }
  public void setCombooItems(List<ComboItem> combooItems) {
    this.combooItems = combooItems;
  }

  
  /**
   **/
  public ComboDTO error(Error error) {
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
    ComboDTO comboDTO = (ComboDTO) o;
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
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

