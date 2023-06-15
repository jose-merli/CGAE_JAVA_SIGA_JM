package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.ScsEjgdesigna;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EjgDesignaDTO {
	private List<ScsEjgdesigna> scsEjgdesignas = new ArrayList<ScsEjgdesigna>();
	  private Error error = null;

	  /**
	   * 
	   **/
	  public EjgDesignaDTO ScsEjgdesignas(List<ScsEjgdesigna> scsEjgdesignas) {
	    this.scsEjgdesignas = scsEjgdesignas;
	    return this;
	  }
	  
	  @JsonProperty("ejgDesignaItems")
	  public List<ScsEjgdesigna> getScsEjgdesignas() {
	    return scsEjgdesignas;
	  }
	  
	  public void setScsEjgdesignas(List<ScsEjgdesigna> scsEjgdesignas) {
	    this.scsEjgdesignas = scsEjgdesignas;
	  }
	  
	  
	  /**
	   * 
	   **/
	  public EjgDesignaDTO error(Error error) {
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
	    EjgDesignaDTO ejgDesignaDTO = (EjgDesignaDTO) o;
	    return Objects.equals(this.scsEjgdesignas, ejgDesignaDTO.scsEjgdesignas) &&
	        Objects.equals(this.error, ejgDesignaDTO.error);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(scsEjgdesignas, error);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class EjgDesignaDTO {\n");
	    
	    sb.append("    ScsEjgdesignas: ").append(toIndentedString(scsEjgdesignas)).append("\n");
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
