package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProcuradorDTO   {
  
  private List<ProcuradorItem> procuradorItems = new ArrayList<ProcuradorItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public ProcuradorDTO procuradorItems(List<ProcuradorItem> procuradorItems) {
    this.procuradorItems = procuradorItems;
    return this;
  }
  
  @JsonProperty("procuradorItems")
  public List<ProcuradorItem> getProcuradorItems() {
    return procuradorItems;
  }
  
  public void setProcuradorItems(List<ProcuradorItem> procuradorItems) {
    this.procuradorItems = procuradorItems;
  }
  
  
  /**
   * 
   **/
  public ProcuradorDTO error(Error error) {
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
    ProcuradorDTO procuradorDTO = (ProcuradorDTO) o;
    return Objects.equals(this.procuradorItems, procuradorDTO.procuradorItems) &&
        Objects.equals(this.error, procuradorDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(procuradorItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProcuradorDTO {\n");
    
    sb.append("    procuradorItems: ").append(toIndentedString(procuradorItems)).append("\n");
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

