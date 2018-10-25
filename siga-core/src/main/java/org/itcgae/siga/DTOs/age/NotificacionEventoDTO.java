package org.itcgae.siga.DTOs.age;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificacionEventoDTO   {
  
  private List<NotificacionEventoItem> eventNotificationItems = new ArrayList<NotificacionEventoItem>();
  private NotificacionEventoItem eventNotification = new NotificacionEventoItem();
  private Error error = null;

  
  /**
   * 
   **/
  public NotificacionEventoDTO eventNotificationItems(List<NotificacionEventoItem> eventNotificationItems) {
    this.eventNotificationItems = eventNotificationItems;
    return this;
  }
  
  @JsonProperty("eventNotificationItems")
  public List<NotificacionEventoItem> getEventNotificationItems() {
    return eventNotificationItems;
  }
  
  public void setEventNotificationItems(List<NotificacionEventoItem> eventNotificationItems) {
    this.eventNotificationItems = eventNotificationItems;
  }
  
  /**
   * 
   **/
  public NotificacionEventoDTO eventNotification(NotificacionEventoItem eventNotification) {
    this.eventNotification = eventNotification;
    return this;
  }
  
  @JsonProperty("eventNotification")
  public NotificacionEventoItem eventNotification() {
    return eventNotification;
  }
  
  public void setEventNotification(NotificacionEventoItem eventNotification) {
    this.eventNotification = eventNotification;
  }

  
  /**
   * 
   **/
  public NotificacionEventoDTO error(Error error) {
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
    NotificacionEventoDTO calendarDTO = (NotificacionEventoDTO) o;
    return Objects.equals(this.eventNotificationItems, calendarDTO.eventNotificationItems) &&
        Objects.equals(this.eventNotification, calendarDTO.eventNotification)&&
        Objects.equals(this.error, calendarDTO.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eventNotificationItems, eventNotification, error);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NotificacionEventoDTO {\n");
    
    sb.append("    eventNotificationItems: ").append(toIndentedString(eventNotificationItems)).append("\n");
    sb.append("    eventNotification: ").append(toIndentedString(eventNotification)).append("\n");
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

