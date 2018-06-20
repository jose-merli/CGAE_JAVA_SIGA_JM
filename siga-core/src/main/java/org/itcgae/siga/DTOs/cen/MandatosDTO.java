package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class MandatosDTO {
	
	private List<MandatosItem> mandatosItem = new ArrayList<MandatosItem>();
	private Error error = null;
	
	
	/**
	 */
	public MandatosDTO nif(List<MandatosItem> mandatosItem ){
		this.mandatosItem  = mandatosItem ;
		return this;
	}
	
	
	public List<MandatosItem> getMandatosItem() {
		return mandatosItem ;
	}
	public void setMandatosItem (List<MandatosItem> mandatosItem ) {
		this.mandatosItem  = mandatosItem ;
	}
	
	
	/**
	 */
	public MandatosDTO nif(Error error){
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
	    MandatosDTO datosRegistralesDTO = (MandatosDTO) o;
	    return Objects.equals(this.mandatosItem , datosRegistralesDTO.mandatosItem ) &&
	    		Objects.equals(this.error, datosRegistralesDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(mandatosItem , error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosBancariosDTO {\n");
	    
	    sb.append("datosRegistralesItems: ").append(toIndentedString(mandatosItem )).append("\n");
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
