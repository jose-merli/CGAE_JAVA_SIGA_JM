package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BusquedaSancionesDTO {
	private List<BusquedaSancionesItem> busquedaSancionesItem = new ArrayList<BusquedaSancionesItem>();
	private Error error = null;
	
	@JsonProperty("busquedaSancionesItem")
	public List<BusquedaSancionesItem> getBusquedaSancionesItem() {
		return busquedaSancionesItem;
	}
	public void setBusquedaSancionesItem(List<BusquedaSancionesItem> busquedaSancionesItem) {
		this.busquedaSancionesItem = busquedaSancionesItem;
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
	    BusquedaSancionesDTO busquedaSancionesDTO = (BusquedaSancionesDTO) o;
	    return Objects.equals(this.busquedaSancionesItem, busquedaSancionesDTO.busquedaSancionesItem) &&
	        Objects.equals(this.error, busquedaSancionesDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(busquedaSancionesItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class BusquedaSancionesDTO {\n");
	    
	    sb.append("    busquedaSancionesItem: ").append(toIndentedString(busquedaSancionesItem)).append("\n");
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
