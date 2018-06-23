package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class DatosIntegrantesDTO {
	
	private List<DatosIntegrantesItem> datosIntegrantesItem = new ArrayList<DatosIntegrantesItem>();
	private Error error = null;
	
	
	/**
	 */
	public DatosIntegrantesDTO datosIntegrantesItem(List<DatosIntegrantesItem> datosIntegrantesItem){
		this.datosIntegrantesItem = datosIntegrantesItem;
		return this;
	}
	
	
	public List<DatosIntegrantesItem> getDatosIntegrantesItem() {
		return datosIntegrantesItem;
	}
	public void setDatosIntegrantesItem(List<DatosIntegrantesItem> datosIntegrantesItem) {
		this.datosIntegrantesItem = datosIntegrantesItem;
	}
	
	
	/**
	 */
	public DatosIntegrantesDTO error(Error error){
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
	    DatosIntegrantesDTO datosBancariosDTO = (DatosIntegrantesDTO) o;
	    return Objects.equals(this.datosIntegrantesItem, datosBancariosDTO.datosIntegrantesItem) &&
	    		Objects.equals(this.error, datosBancariosDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(datosIntegrantesItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosIntegrantesDTO {\n");
	    
	    sb.append("datosIntegrantesItem: ").append(toIndentedString(datosIntegrantesItem)).append("\n");
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
