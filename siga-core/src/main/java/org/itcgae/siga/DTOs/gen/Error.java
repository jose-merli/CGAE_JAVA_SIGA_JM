package org.itcgae.siga.DTOs.gen;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


import org.itcgae.siga.DTOs.gen.ErrorDetail;
import java.util.ArrayList;
import java.util.List;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class Error   {
  
  private Integer code = null;
  private String message = null;
  private String description = null;
  private String infoURL = null;
  private List<ErrorDetail> details = new ArrayList<ErrorDetail>();

  
  /**
   * An integer coding the error type. This is given to caller so he can translate them if required.
   **/
  public Error code(Integer code) {
    this.code = code;
    return this;
  }
  
  
  @JsonProperty("code")
  public Integer getCode() {
    return code;
  }
  public void setCode(Integer code) {
    this.code = code;
  }

  
  /**
   * A short localized string that describes the error.
   **/
  public Error message(String message) {
    this.message = message;
    return this;
  }
  
  
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }

  
  /**
   * (optional) A long localized error description if needed. It can contain precise information about which parameter is missing, or what are the identifier acceptable values.
   **/
  public Error description(String description) {
    this.description = description;
    return this;
  }
  
  
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  
  /**
   * (optional) A URL to online documentation that provides more information about the error.
   **/
  public Error infoURL(String infoURL) {
    this.infoURL = infoURL;
    return this;
  }
  
  
  @JsonProperty("infoURL")
  public String getInfoURL() {
    return infoURL;
  }
  public void setInfoURL(String infoURL) {
    this.infoURL = infoURL;
  }

  
  /**
   **/
  public Error details(List<ErrorDetail> details) {
    this.details = details;
    return this;
  }
  
  
  @JsonProperty("details")
  public List<ErrorDetail> getDetails() {
    return details;
  }
  public void setDetails(List<ErrorDetail> details) {
    this.details = details;
  }

  

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Error error = (Error) o;
    return Objects.equals(this.code, error.code) &&
        Objects.equals(this.message, error.message) &&
        Objects.equals(this.description, error.description) &&
        Objects.equals(this.infoURL, error.infoURL) &&
        Objects.equals(this.details, error.details);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, message, description, infoURL, details);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    infoURL: ").append(toIndentedString(infoURL)).append("\n");
    sb.append("    details: ").append(toIndentedString(details)).append("\n");
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

