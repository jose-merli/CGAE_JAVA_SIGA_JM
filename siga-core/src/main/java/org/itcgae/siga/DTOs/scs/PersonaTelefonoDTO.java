package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;
import org.itcgae.siga.db.entities.ScsTelefonospersona;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonaTelefonoDTO {

	 private List<ScsTelefonospersona> telefonospersonas = new ArrayList<ScsTelefonospersona>();
	  private Error error = null;

	  
	  /**
	   * 
	   **/
	  public PersonaTelefonoDTO telefonospersonas(List<ScsTelefonospersona> telefonospersonas) {
	    this.telefonospersonas = telefonospersonas;
	    return this;
	  }
	  
	  @JsonProperty("telefonospersonas")
	  public List<ScsTelefonospersona> getTelefonospersona() {
	    return telefonospersonas;
	  }
	  
	  public void setTelefonospersona(List<ScsTelefonospersona> telefonospersonas) {
	    this.telefonospersonas = telefonospersonas;
	  }
	  
	  
	  /**
	   * 
	   **/
	  public PersonaTelefonoDTO error(Error error) {
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
	    PersonaTelefonoDTO justiciableTelefonoDTO = (PersonaTelefonoDTO) o;
	    return Objects.equals(this.telefonospersonas, justiciableTelefonoDTO.telefonospersonas) &&
	        Objects.equals(this.error, justiciableTelefonoDTO.error);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(telefonospersonas, error);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class JusticiableTelefonoDTO {\n");
	    
	    sb.append("    telefonospersonas: ").append(toIndentedString(telefonospersonas)).append("\n");
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
