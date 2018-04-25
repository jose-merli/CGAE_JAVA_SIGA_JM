package org.itcgae.siga.DTOs.gen;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


import org.itcgae.siga.DTOs.gen.PermisoEntity;
import java.util.ArrayList;
import java.util.List;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class PermisoEntity   {
  
  private String label = null;
  private String data = null;
  private String parent = null;
  private String derechoacceso = null;


  
  /**
   **/
  public PermisoEntity label(String label) {
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
  public PermisoEntity data(String data) {
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
  public PermisoEntity parent(String parent) {
    this.parent = parent;
    return this;
  }
  
  
  @JsonProperty("parent")
  public String getParent() {
    return parent;
  }
  public void setParentn(String parent) {
    this.parent = parent;
  }
  
 
  /**
   **/
  public PermisoEntity derechoacceso(String derechoacceso) {
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
    PermisoEntity permisoEntity = (PermisoEntity) o;
    return Objects.equals(this.label, permisoEntity.label) &&
        Objects.equals(this.data, permisoEntity.data) &&
        Objects.equals(this.parent, permisoEntity.parent) &&
        Objects.equals(this.derechoacceso, permisoEntity.derechoacceso);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, data, parent,derechoacceso);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PermisoEntity {\n");
    
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
    sb.append("    derechoacceso: ").append(toIndentedString(derechoacceso)).append("\n");
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

