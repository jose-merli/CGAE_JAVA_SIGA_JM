package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;


public class CargaMasivaDTO {

	private List<CargaMasivaItem> cargaMasivaItem = new ArrayList<CargaMasivaItem>();
	private Error error = null;
	
	/**
	 */
	public CargaMasivaDTO CargaMasivaItem(List<CargaMasivaItem> cargaMasivaItem){
		this.cargaMasivaItem = cargaMasivaItem;
		return this;
	}
	
	
	public List<CargaMasivaItem> getCargaMasivaItem() {
		return cargaMasivaItem;
	}
	public void setCargaMasivaItem(List<CargaMasivaItem> cargaMasivaItem) {
		this.cargaMasivaItem = cargaMasivaItem;
	}
	
	
	/**
	 */
	public CargaMasivaDTO error(Error error){
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
	    CargaMasivaDTO cargaMasivaDTO = (CargaMasivaDTO) o;
	    return Objects.equals(this.cargaMasivaItem, cargaMasivaDTO.cargaMasivaItem) &&
	    		Objects.equals(this.error, cargaMasivaDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(cargaMasivaItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class CargaMasivaDTO {\n");
	    
	    sb.append("cargaMasivaItem: ").append(toIndentedString(cargaMasivaItem)).append("\n");
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
