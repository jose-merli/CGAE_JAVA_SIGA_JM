package org.itcgae.siga.DTOs.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class MenuItem   {
  
  private String label = null;
  private String routerLink = null;
  private String idclass = null;
  private List<MenuItem> items = new ArrayList<MenuItem>();

  
  /**
   **/
  public MenuItem label(String label) {
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
  public MenuItem routerLink(String routerLink) {
    this.routerLink = routerLink;
    return this;
  }
  
  
  @JsonProperty("routerLink")
  public String getRouterLink() {
    return routerLink;
  }
  public void setRouterLink(String routerLink) {
    this.routerLink = routerLink;
  }

  
  /**
   **/
  public MenuItem items(List<MenuItem> items) {
    this.items = items;
    return this;
  }
  
  
  @JsonProperty("items")
  public List<MenuItem> getItems() {
    return items;
  }
  public void setItems(List<MenuItem> items) {
    this.items = items;
  }

  
  
  /**
   **/
  public MenuItem idclass(String idclass) {
    this.idclass = idclass;
    return this;
  }
  
  
  @JsonProperty("idClass")
  public String getIdclass() {
    return idclass;
  }
  public void setIdclass(String idclass) {
    this.idclass = idclass;
  }
  

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MenuItem menuItem = (MenuItem) o;
    return Objects.equals(this.label, menuItem.label) &&
        Objects.equals(this.routerLink, menuItem.routerLink) &&
        Objects.equals(this.idclass, menuItem.idclass) &&
        Objects.equals(this.items, menuItem.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, routerLink, items, idclass);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MenuItem {\n");
    
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    routerLink: ").append(toIndentedString(routerLink)).append("\n");
    sb.append("    idclass: ").append(toIndentedString(idclass)).append("\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
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

