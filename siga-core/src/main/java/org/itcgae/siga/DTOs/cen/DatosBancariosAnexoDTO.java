package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class DatosBancariosAnexoDTO {
	
	private List<DatosBancariosAnexoItem> datosBancariosAnexoItem = new ArrayList<DatosBancariosAnexoItem>();
	private Error error = null;
	
	
	/**
	 */
	public DatosBancariosAnexoDTO nif(List<DatosBancariosAnexoItem> datosBancariosAnexoItem){
		this.datosBancariosAnexoItem = datosBancariosAnexoItem;
		return this;
	}
	
	
	public List<DatosBancariosAnexoItem> getDatosBancariosAnexoItem() {
		return datosBancariosAnexoItem;
	}
	public void setDatosBancariosAnexoItem(List<DatosBancariosAnexoItem> datosBancariosAnexoItem) {
		this.datosBancariosAnexoItem = datosBancariosAnexoItem;
	}
	
	
	/**
	 */
	public DatosBancariosAnexoDTO nif(Error error){
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
	    DatosBancariosAnexoDTO datosBancariosAnexoDTO = (DatosBancariosAnexoDTO) o;
	    return Objects.equals(this.datosBancariosAnexoItem, datosBancariosAnexoDTO.datosBancariosAnexoItem) &&
	    		Objects.equals(this.error, datosBancariosAnexoDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(datosBancariosAnexoItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosBancariosAnexoDTO {\n");
	    
	    sb.append("datosBancariosAnexoItem: ").append(toIndentedString(datosBancariosAnexoItem)).append("\n");
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
