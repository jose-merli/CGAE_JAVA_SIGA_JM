package org.itcgae.siga.DTOs.gen;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


import org.itcgae.siga.DTOs.gen.PermisoItem;
import java.util.ArrayList;
import java.util.List;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class PermisoItem   {
  
  private String label = null;
  private String data = null;
  private String expandedIcon = null;
  private String collapsedIcon = null;
  private String derechoacceso = null;
  private List<PermisoItem> children = new ArrayList<PermisoItem>();

  
  /**
   **/
  public PermisoItem label(String label) {
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
  public PermisoItem data(String data) {
    this.data = data;
    return this;
  }
  
  
  @JsonProperty("data")
  public String getData() {
    return data;
  }
  public void setData(String data) {
    this.data = data;
  }

  
  /**
   **/
  public PermisoItem children(List<PermisoItem> children) {
    this.children = children;
    return this;
  }
  
  
  @JsonProperty("children")
  public List<PermisoItem> getChildren() {
    return children;
  }
  public void setChildren(List<PermisoItem> children) {
    this.children = children;
  }

  
  /**
   **/
  public PermisoItem expandedIcon(String expandedIcon) {
    this.expandedIcon = expandedIcon;
    return this;
  }
  
  
  @JsonProperty("expandedIcon")
  public String getExpandedIcon() {
    return expandedIcon;
  }
  public void setExpandedIcon(String expandedIcon) {
    this.expandedIcon = expandedIcon;
  }
  
  /**
   **/
  public PermisoItem collapsedIcon(String collapsedIcon) {
    this.collapsedIcon = collapsedIcon;
    return this;
  }
  
  
  @JsonProperty("collapsedIcon")
  public String getCollapsedIcon() {
    return collapsedIcon;
  }
  public void setCollapsedIcon(String collapsedIcon) {
    this.collapsedIcon = collapsedIcon;
  }
  
  
  /**
   **/
  public PermisoItem derechoacceso(String derechoacceso) {
    this.derechoacceso = derechoacceso;
    return this;
  }
  
  
  @JsonProperty("derechoacceso")
  public String getDerechoacceso() {
    return derechoacceso;
  }
  public void setDerechoacceso(String derechoacceso) {
    this.derechoacceso = derechoacceso;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PermisoItem permisoItem = (PermisoItem) o;
    return Objects.equals(this.label, permisoItem.label) &&
        Objects.equals(this.data, permisoItem.data) &&
        Objects.equals(this.expandedIcon, permisoItem.expandedIcon) &&
        Objects.equals(this.collapsedIcon, permisoItem.collapsedIcon) &&
        Objects.equals(this.derechoacceso, permisoItem.derechoacceso) &&
        Objects.equals(this.children, permisoItem.children);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, data, children,expandedIcon,collapsedIcon,derechoacceso);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PermisoItem {\n");
    
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    expandedIcon: ").append(toIndentedString(expandedIcon)).append("\n");
    sb.append("    collapsedIcon: ").append(toIndentedString(collapsedIcon)).append("\n");
    sb.append("    derechoacceso: ").append(toIndentedString(derechoacceso)).append("\n");
    sb.append("    children: ").append(toIndentedString(children)).append("\n");
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

