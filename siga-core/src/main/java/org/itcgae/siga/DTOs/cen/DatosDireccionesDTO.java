package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class DatosDireccionesDTO {
	
	private List<DatosDireccionesItem> datosDireccionesItem = new ArrayList<DatosDireccionesItem>();
	private Error error = null;
	
	
	/**
	 */
	public DatosDireccionesDTO DatosDireccionesItem(List<DatosDireccionesItem> datosDireccionesItem){
		this.datosDireccionesItem = datosDireccionesItem;
		return this;
	}
	
	
	public List<DatosDireccionesItem> getDatosDireccionesItem() {
		return datosDireccionesItem;
	}
	public void setDatosDireccionesItem(List<DatosDireccionesItem> datosDireccionesItem) {
		this.datosDireccionesItem = datosDireccionesItem;
	}
	
	
	/**
	 */
	public DatosDireccionesDTO error(Error error){
		this.error = error;
		return this;
	}
	
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
	    DatosDireccionesDTO datosDireccionesDTO = (DatosDireccionesDTO) o;
	    return Objects.equals(this.datosDireccionesItem, datosDireccionesDTO.datosDireccionesItem) &&
	    		Objects.equals(this.error, datosDireccionesDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(datosDireccionesItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosDireccionesDTO {\n");
	    
	    sb.append("datosDireccionesItem: ").append(toIndentedString(datosDireccionesItem)).append("\n");
	    sb.append("error: ").append(toIndentedString(error)).append("\n");
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
