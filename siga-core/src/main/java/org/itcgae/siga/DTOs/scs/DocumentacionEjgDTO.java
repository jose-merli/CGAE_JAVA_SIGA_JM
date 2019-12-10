package org.itcgae.siga.DTOs.scs;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;



public class DocumentacionEjgDTO {
	 private List<DocumentacionEjgItem> documentacionejgItems = new ArrayList<DocumentacionEjgItem>();
	  private Error error = null;
	  
	  /**
	   * 
	   **/
	  public DocumentacionEjgDTO documentacionejgItems(List<DocumentacionEjgItem> documentacionejgItems) {
	    this.documentacionejgItems = documentacionejgItems;
	    return this;
	  }
	  
	  @JsonProperty("documentacionejgItems")
	  public List<DocumentacionEjgItem> getDocumentacionEjgItems() {
	    return documentacionejgItems;
	  }
	  
	  public void setDocumentacionEjgItems(List<DocumentacionEjgItem> documentacionejgItems) {
	    this.documentacionejgItems = documentacionejgItems;
	  }
	  
	  
	  /**
	   * 
	   **/
	  public DocumentacionEjgDTO error(Error error) {
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
	    DocumentacionEjgDTO documentacionejgDTO = (DocumentacionEjgDTO) o;
	    return Objects.equals(this.documentacionejgItems, documentacionejgDTO.documentacionejgItems) &&
	        Objects.equals(this.error, documentacionejgDTO.error);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(documentacionejgItems, error);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DocumentacionEjgDTO {\n");
	    
	    sb.append("    documentacionejgItems: ").append(toIndentedString(documentacionejgItems)).append("\n");
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
