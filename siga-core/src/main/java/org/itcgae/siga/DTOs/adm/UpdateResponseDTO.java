package org.itcgae.siga.DTOs.adm;

import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class UpdateResponseDTO   {
  
  private String status = new String();
  private String id = new String();
  private Error error = null;

  
  /**
   **/
  public UpdateResponseDTO catalogoMaestroItem(String status) {
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
   **/
  public UpdateResponseDTO error(Error error) {
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

  
  /**
   **/
  public UpdateResponseDTO id(String id) {
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


@Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateResponseDTO updateResponseDTO = (UpdateResponseDTO) o;
    return Objects.equals(this.status, updateResponseDTO.status) &&
    		Objects.equals(this.id, updateResponseDTO.id) &&
        Objects.equals(this.error, updateResponseDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, status, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateResponseDTO {\n");
    
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

