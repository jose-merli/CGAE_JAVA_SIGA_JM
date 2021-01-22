package org.itcgae.siga.DTOs.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class MenuDTO   {
  
  private List<MenuItem> menuItems = new ArrayList<MenuItem>();
  private Error error = null;

  
  /**
   **/
  public MenuDTO menuItems(List<MenuItem> menuItems) {
    this.menuItems = menuItems;
    return this;
  }
  
  
  @JsonProperty("menuItems")
  public List<MenuItem> getMenuItems() {
    return menuItems;
  }
  public void setMenuItems(List<MenuItem> menuItems) {
    this.menuItems = menuItems;
  }

  
  /**
   **/
  public MenuDTO error(Error error) {
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
    MenuDTO menuDTO = (MenuDTO) o;
    return Objects.equals(this.menuItems, menuDTO.menuItems) &&
        Objects.equals(this.error, menuDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(menuItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MenuDTO {\n");
    
    sb.append("    menuItems: ").append(toIndentedString(menuItems)).append("\n");
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

