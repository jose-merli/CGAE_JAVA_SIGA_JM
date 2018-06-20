package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class DatosBancariosDTO {
	
	private List<DatosBancariosItem> datosBancariosItem = new ArrayList<DatosBancariosItem>();
	private Error error = null;
	
	
	/**
	 */
	public DatosBancariosDTO nif(List<DatosBancariosItem> datosBancariosItem){
		this.datosBancariosItem = datosBancariosItem;
		return this;
	}
	
	
	public List<DatosBancariosItem> getDatosBancariosItem() {
		return datosBancariosItem;
	}
	public void setDatosBancariosItem(List<DatosBancariosItem> datosBancariosItem) {
		this.datosBancariosItem = datosBancariosItem;
	}
	
	
	/**
	 */
	public DatosBancariosDTO nif(Error error){
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
	    DatosBancariosDTO datosRegistralesDTO = (DatosBancariosDTO) o;
	    return Objects.equals(this.datosBancariosItem, datosRegistralesDTO.datosBancariosItem) &&
	    		Objects.equals(this.error, datosRegistralesDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(datosBancariosItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosBancariosDTO {\n");
	    
	    sb.append("datosRegistralesItems: ").append(toIndentedString(datosBancariosItem)).append("\n");
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
