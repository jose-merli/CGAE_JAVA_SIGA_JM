package org.itcgae.siga.DTOs.age;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CalendarDTO   {
  
  private List<CalendarItem> calendarItems = new ArrayList<CalendarItem>();
  private Error error = null;

  
  /**
   * 
   **/
  public CalendarDTO calendarItems(List<CalendarItem> calendarItems) {
    this.calendarItems = calendarItems;
    return this;
  }
  
  @JsonProperty("calendarItems")
  public List<CalendarItem> calendarItems() {
    return calendarItems;
  }
  
  public void setCalendarItems(List<CalendarItem> calendarItems) {
    this.calendarItems = calendarItems;
  }

  
  /**
   * 
   **/
  public CalendarDTO error(Error error) {
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
    CalendarDTO calendarDTO = (CalendarDTO) o;
    return Objects.equals(this.calendarItems, calendarDTO.calendarItems) &&
        Objects.equals(this.error, calendarDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(calendarItems, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CalendarDTO {\n");
    
    sb.append("    calendarItems: ").append(toIndentedString(calendarItems)).append("\n");
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

