package org.itcgae.siga.DTOs.gen;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class DiccionarioDTO   {
  
  private List<DiccionarioItem> diccionarioItems = new ArrayList<DiccionarioItem>();
  private Error error = null;

  
  /**
   **/
  public DiccionarioDTO DiccionarioItems(List<DiccionarioItem> diccionarioItems) {
    this.diccionarioItems = diccionarioItems;
    return this;
  }
  
  
  @JsonProperty("DiccionarioItems")
  public List<DiccionarioItem> getDiccionarioItems() {
    return diccionarioItems;
  }
  public void setDiccionarioItems(List<DiccionarioItem> diccionarioItems) {
    this.diccionarioItems = diccionarioItems;
  }

  
  /**
   **/
  public DiccionarioDTO error(Error error) {
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
    DiccionarioDTO diccionarioItems = (DiccionarioDTO) o;
    return Objects.equals(this.diccionarioItems, diccionarioItems.diccionarioItems) &&
        Objects.equals(this.error, diccionarioItems.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(diccionarioItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DiccionarioDTO {\n");
    
    sb.append("    DiccionarioItems: ").append(toIndentedString(diccionarioItems)).append("\n");
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

