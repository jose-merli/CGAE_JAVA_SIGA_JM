package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class SoliModiDireccionesDTO {

	private List<SoliModiDireccionesItem> soliModiDireccionesItems = new ArrayList<SoliModiDireccionesItem>();
	private Error error = null;
	
	
	/**
	 */
	public SoliModiDireccionesDTO DatosDireccionesItem(List<SoliModiDireccionesItem> soliModiDireccionesItems){
		this.soliModiDireccionesItems = soliModiDireccionesItems;
		return this;
	}
	
	
	public List<SoliModiDireccionesItem> getSoliModiDireccionesItem() {
		return soliModiDireccionesItems;
	}
	public void setSoliModiDireccionesItem(List<SoliModiDireccionesItem> soliModiDireccionesItems) {
		this.soliModiDireccionesItems = soliModiDireccionesItems;
	}
	
	
	/**
	 */
	public SoliModiDireccionesDTO error(Error error){
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
	    SoliModiDireccionesDTO SoliModiDireccionesDTO = (SoliModiDireccionesDTO) o;
	    return Objects.equals(this.soliModiDireccionesItems, SoliModiDireccionesDTO.soliModiDireccionesItems) &&
	    		Objects.equals(this.error, SoliModiDireccionesDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(soliModiDireccionesItems, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class SoliModiDireccionesDTO {\n");
	    
	    sb.append("soliModiDireccionesItems: ").append(toIndentedString(soliModiDireccionesItems)).append("\n");
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
