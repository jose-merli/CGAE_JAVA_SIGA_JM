package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class FichaDatosColegialesDTO {
	
	private List<FichaDatosColegialesItem> fichaDatosColegialesItems = new ArrayList<FichaDatosColegialesItem>();
	private Error error = null;
	
	
	/**
	 *
	 */
	public FichaDatosColegialesDTO FichaDatosColegialesItem(List<FichaDatosColegialesItem> fichaDatosColegialesItems){
		this.fichaDatosColegialesItems = fichaDatosColegialesItems;
		return this;
	}
	
	
	@JsonProperty("fichaDatosColegialesItems")
	public List<FichaDatosColegialesItem> getfichaDatosColegialesItems() {
		return fichaDatosColegialesItems;
	}



	public void setfichaDatosColegialesItems (List<FichaDatosColegialesItem> fichaDatosColegialesItems) {
		this.fichaDatosColegialesItems = fichaDatosColegialesItems;
	}

	
	
	/**
	 *
	 */
	public FichaDatosColegialesDTO error(Error error){
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


	
//	@Override
//	public boolean equals(java.lang.Object o) {
//	    if (this == o) {
//	      return true;
//	    }
//	    if (o == null || getClass() != o.getClass()) {
//	      return false;
//	    }
//	    FichaDatosColegialesDTO busquedaJuridicaDTO = (FichaDatosColegialesDTO) o;
//	    return Objects.equals(this.fichaDatosColegialesItems, busquedaJuridicaDTO.fichaDatosColegialesItems) &&
//	        Objects.equals(this.error, fichaDatosColegialesDTO.error);
//	}
//	
//	@Override
//	public int hashCode() {
//	    return Objects.hash(fichaDatosColegialesItems, error);
//	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class BusquedaPerJuridicaDTO {\n");
	    
	    sb.append("    busquedaPerJuridicaItems: ").append(toIndentedString(fichaDatosColegialesItems)).append("\n");
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
