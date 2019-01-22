package org.itcgae.siga.DTOs.age;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PermisosPerfilesCalendarDTO   {
  
  private List<PermisosPerfilesCalendarItem> permisosPerfilesCalendar = new ArrayList<PermisosPerfilesCalendarItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public PermisosPerfilesCalendarDTO calendarItems(List<PermisosPerfilesCalendarItem> permisosPerfilesCalendar) {
    this.permisosPerfilesCalendar = permisosPerfilesCalendar;
    return this;
  }
  
  @JsonProperty("permisosPerfilesCalendar")
  public List<PermisosPerfilesCalendarItem> permisosPerfilesCalendar() {
    return permisosPerfilesCalendar;
  }
  
  public void setPermisosPerfilesCalendar(List<PermisosPerfilesCalendarItem> permisosPerfilesCalendar) {
    this.permisosPerfilesCalendar = permisosPerfilesCalendar;
  }

  
  /**
   * 
   **/
  public PermisosPerfilesCalendarDTO error(Error error) {
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
    PermisosPerfilesCalendarDTO permisosPerfilesCalendarDTO = (PermisosPerfilesCalendarDTO) o;
    return Objects.equals(this.permisosPerfilesCalendar, permisosPerfilesCalendarDTO.permisosPerfilesCalendar) &&
        Objects.equals(this.error, permisosPerfilesCalendarDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(permisosPerfilesCalendar, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class permisosPerfilesCalendarDTO {\n");
    
    sb.append("    permisosPerfilesCalendar: ").append(toIndentedString(permisosPerfilesCalendar)).append("\n");
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

