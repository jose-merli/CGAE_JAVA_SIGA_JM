package org.itcgae.siga.DTOs.gen;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;







@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class EntornoDTO   {
  
  private String entorno = new String();
  private Error error = null;

  
  /**
   **/
  public EntornoDTO entorno(String entorno) {
    this.entorno = entorno;
    return this;
  }
  
  
  @JsonProperty("entorno")
  public String getentorno() {
    return entorno;
  }
  public void setentorno(String entorno) {
    this.entorno = entorno;
  }

  
  /**
   **/
  public EntornoDTO error(Error error) {
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
    EntornoDTO EntornoDTO = (EntornoDTO) o;
    return Objects.equals(this.entorno, EntornoDTO.entorno) &&
        Objects.equals(this.error, EntornoDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(entorno, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EntornoDTO {\n");
    
    sb.append("    entorno: ").append(toIndentedString(entorno)).append("\n");
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

