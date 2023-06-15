package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JuzgadoDTO   {
  
  private List<JuzgadoItem> juzgadoItems = new ArrayList<JuzgadoItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public JuzgadoDTO juzgadoItems(List<JuzgadoItem> juzgadoItems) {
    this.juzgadoItems = juzgadoItems;
    return this;
  }
  
  @JsonProperty("juzgadoItems")
  public List<JuzgadoItem> getJuzgadoItems() {
    return juzgadoItems;
  }
  
  public void setJuzgadoItems(List<JuzgadoItem> juzgadoItems) {
    this.juzgadoItems = juzgadoItems;
  }
  
  
  /**
   * 
   **/
  public JuzgadoDTO error(Error error) {
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
    JuzgadoDTO juzgadoDTO = (JuzgadoDTO) o;
    return Objects.equals(this.juzgadoItems, juzgadoDTO.juzgadoItems) &&
        Objects.equals(this.error, juzgadoDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(juzgadoItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JuzgadoDTO {\n");
    
    sb.append("    juzgadoItems: ").append(toIndentedString(juzgadoItems)).append("\n");
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

