package org.itcgae.siga.DTOs.adm;

import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class InsertResponseDTO   {
  
  private String status = new String();
  private String id = new String();
  private Error error = null;

  
  /**
   **/
  public InsertResponseDTO status(String status) {
    this.status = status;
    return this;
  }
  
  
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * EstadosPagosDTO
   **/
  public InsertResponseDTO id(String id) {
    this.id = id;
    return this;
  }
  
  
  @JsonProperty("id")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }


  /**
   */
  public InsertResponseDTO error(Error error) {
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
    InsertResponseDTO insertResponseDTO = (InsertResponseDTO) o;
    return Objects.equals(this.status, insertResponseDTO.status) &&
    	Objects.equals(this.id, insertResponseDTO.id) &&
        Objects.equals(this.error, insertResponseDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, error,id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InsertResponseDTO {\n");
    
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

