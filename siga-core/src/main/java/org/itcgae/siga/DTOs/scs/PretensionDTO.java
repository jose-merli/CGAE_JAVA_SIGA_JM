package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PretensionDTO   {
  
  private List<PretensionItem> pretensionItems = new ArrayList<PretensionItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public PretensionDTO pretensionItems(List<PretensionItem> pretensionItems) {
    this.pretensionItems = pretensionItems;
    return this;
  }
  
  @JsonProperty("pretensionItems")
  public List<PretensionItem> getPretensionItems() {
    return pretensionItems;
  }
  
  public void setPretensionItems(List<PretensionItem> pretensionItems) {
    this.pretensionItems = pretensionItems;
  }
  
  
  /**
   * 
   **/
  public PretensionDTO error(Error error) {
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
    PretensionDTO pretensionDTO = (PretensionDTO) o;
    return Objects.equals(this.pretensionItems, pretensionDTO.pretensionItems) &&
        Objects.equals(this.error, pretensionDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pretensionItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PretensionDTO {\n");
    
    sb.append("    pretensionItems: ").append(toIndentedString(pretensionItems)).append("\n");
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

