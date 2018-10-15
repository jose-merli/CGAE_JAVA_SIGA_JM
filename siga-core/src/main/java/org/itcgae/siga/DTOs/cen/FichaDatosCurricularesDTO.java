package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class FichaDatosCurricularesDTO {

	private List<FichaDatosCurricularesItem> fichaDatosCurricularesItem = new ArrayList<FichaDatosCurricularesItem>();
	private Error error = null;
	

	/**
	 *
	 */
	public FichaDatosCurricularesDTO fichaDatosCurricularesItem(List<FichaDatosCurricularesItem> fichaDatosCurricularesItem){
		this.fichaDatosCurricularesItem = fichaDatosCurricularesItem;
		return this;
	}
	
	
	
	
	@JsonProperty("fichaDatosCurricularesItem")
	public List<FichaDatosCurricularesItem> getFichaDatosCurricularesItem() {
		return fichaDatosCurricularesItem;
	}





	public void setFichaDatosCurricularesItem(List<FichaDatosCurricularesItem> fichaDatosCurricularesItem) {
		this.fichaDatosCurricularesItem = fichaDatosCurricularesItem;
	}





	/**
	 *
	 */
	public FichaDatosCurricularesDTO error(Error error){
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


	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    FichaDatosCurricularesDTO fichaDatosCurricularesDTO = (FichaDatosCurricularesDTO) o;
	    return Objects.equals(this.fichaDatosCurricularesItem, fichaDatosCurricularesDTO.fichaDatosCurricularesItem) &&
	        Objects.equals(this.error, fichaDatosCurricularesDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(fichaDatosCurricularesItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class FichaDatosCurricularesDTO {\n");
	    
	    sb.append("    fichaDatosCurricularesItem: ").append(toIndentedString(fichaDatosCurricularesItem)).append("\n");
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
