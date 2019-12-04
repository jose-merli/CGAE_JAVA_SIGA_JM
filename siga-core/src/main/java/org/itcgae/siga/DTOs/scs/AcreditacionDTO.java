package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AcreditacionDTO   {
  
  private List<AcreditacionItem> acreditacionItem = new ArrayList<AcreditacionItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public AcreditacionDTO acreditacionItem(List<AcreditacionItem> acreditacionItem) {
    this.acreditacionItem = acreditacionItem;
    return this;
  }
  
  @JsonProperty("acreditacionItem")
  public List<AcreditacionItem> getAcreditacionItem() {
    return acreditacionItem;
  }
  
  public void setAcreditacionItem(List<AcreditacionItem> acreditacionItem) {
    this.acreditacionItem = acreditacionItem;
  }
  
  
  /**
   * 
   **/
  public AcreditacionDTO error(Error error) {
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
    AcreditacionDTO areasDTO = (AcreditacionDTO) o;
    return Objects.equals(this.acreditacionItem, areasDTO.acreditacionItem) &&
        Objects.equals(this.error, areasDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(acreditacionItem, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class areasDTO {\n");
    
    sb.append("    areasItems: ").append(toIndentedString(acreditacionItem)).append("\n");
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

