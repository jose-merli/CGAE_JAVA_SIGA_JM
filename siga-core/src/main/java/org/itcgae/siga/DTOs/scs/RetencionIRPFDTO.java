package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RetencionIRPFDTO   {
  
  private List<RetencionIRPFItem> retencionItems = new ArrayList<RetencionIRPFItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public RetencionIRPFDTO procuradorItems(List<RetencionIRPFItem> retencionItems) {
    this.retencionItems = retencionItems;
    return this;
  }
  
  @JsonProperty("retencionItems")
  public List<RetencionIRPFItem> getRetencionIrpfItems() {
    return retencionItems;
  }
  
  public void setRetencionIrpfItems(List<RetencionIRPFItem> retencionItems) {
    this.retencionItems = retencionItems;
  }
  
  
  /**
   * 
   **/
  public RetencionIRPFDTO error(Error error) {
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
    RetencionIRPFDTO procuradorDTO = (RetencionIRPFDTO) o;
    return Objects.equals(this.retencionItems, procuradorDTO.retencionItems) &&
        Objects.equals(this.error, procuradorDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(retencionItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProcuradorDTO {\n");
    
    sb.append("    retencionItems: ").append(toIndentedString(retencionItems)).append("\n");
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

