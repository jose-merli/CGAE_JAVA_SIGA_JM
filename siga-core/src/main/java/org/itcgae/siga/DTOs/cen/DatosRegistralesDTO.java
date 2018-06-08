package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class DatosRegistralesDTO {
	
	private List<DatosRegistralesItem> datosRegistralesItems = new ArrayList<DatosRegistralesItem>();
	private Error error = null;
	
	
	/**
	 */
	public DatosRegistralesDTO nif(List<DatosRegistralesItem> datosRegistralesItems){
		this.datosRegistralesItems = datosRegistralesItems;
		return this;
	}
	
	
	public List<DatosRegistralesItem> getDatosRegistralesItems() {
		return datosRegistralesItems;
	}
	public void setDatosRegistralesItems(List<DatosRegistralesItem> datosRegistralesItems) {
		this.datosRegistralesItems = datosRegistralesItems;
	}
	
	
	/**
	 */
	public DatosRegistralesDTO nif(Error error){
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
	    DatosRegistralesDTO datosRegistralesDTO = (DatosRegistralesDTO) o;
	    return Objects.equals(this.datosRegistralesItems, datosRegistralesDTO.datosRegistralesItems) &&
	    		Objects.equals(this.error, datosRegistralesDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(datosRegistralesItems, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosRegistralesDTO {\n");
	    
	    sb.append("datosRegistralesItems: ").append(toIndentedString(datosRegistralesItems)).append("\n");
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
