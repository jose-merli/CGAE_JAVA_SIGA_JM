package org.itcgae.siga.DTOs.gen;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


import org.itcgae.siga.DTOs.gen.DiccionarioItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class DiccionarioItem   {
  
  private HashMap<String,HashMap<String,String>> diccionario = null;



  
  /**
   **/
  public DiccionarioItem diccionario(HashMap<String,HashMap<String,String>> diccionario) {
    this.diccionario = diccionario;
    return this;
  }
  
  
  @JsonProperty("diccionario")
  public HashMap<String,HashMap<String,String>> getDiccionario() {
    return diccionario;
  }
  public void setDiccionario(HashMap<String,HashMap<String,String>> diccionario) {
    this.diccionario = diccionario;
  }

 
  

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DiccionarioItem DiccionarioItem = (DiccionarioItem) o;
    return Objects.equals(this.diccionario, DiccionarioItem.diccionario);
  }

  @Override
  public int hashCode() {
    return Objects.hash(diccionario);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DiccionarioItem {\n");
    
    sb.append("    diccionario: ").append(toIndentedString(diccionario)).append("\n");

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

