package org.itcgae.siga.DTOs.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class ComboCatalogoDTO   {
  
  private List<ComboCatalogoItem> comboCatalogoItems = new ArrayList<ComboCatalogoItem>();
  private Error error = null;

  
  /**
   **/
  public ComboCatalogoDTO comboCatalogoItems(List<ComboCatalogoItem> comboCatalogoItems) {
    this.comboCatalogoItems = comboCatalogoItems;
    return this;
  }
  
  
  @JsonProperty("comboCatalogoItems")
  public List<ComboCatalogoItem> getCombCatalogoItems() {
    return comboCatalogoItems;
  }
  public void setComboCatalogoItems(List<ComboCatalogoItem> comboCatalogoItems) {
    this.comboCatalogoItems = comboCatalogoItems;
  }

  
  /**
   **/
  public ComboCatalogoDTO error(Error error) {
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
    ComboCatalogoDTO comboCatalogoDTO = (ComboCatalogoDTO) o;
    return Objects.equals(this.comboCatalogoItems, comboCatalogoDTO.comboCatalogoItems) &&
        Objects.equals(this.error, comboCatalogoDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comboCatalogoItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ComboCatalogoDTO {\n");
    
    sb.append("    comboCatalogoItems: ").append(toIndentedString(comboCatalogoItems)).append("\n");
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

