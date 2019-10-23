package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class BusquedaPerJuridicaDTO {
	
	private List<BusquedaPerJuridicaItem> busquedaPerJuridicaItems = new ArrayList<BusquedaPerJuridicaItem>();
	private Error error = null;
	private Boolean onlyNif = false;
	
	/**
	 *
	 */
	public BusquedaPerJuridicaDTO busquedaJuridicaItems(List<BusquedaPerJuridicaItem> busquedaPerJuridicaItems){
		this.busquedaPerJuridicaItems = busquedaPerJuridicaItems;
		return this;
	}
	
	
	@JsonProperty("busquedaPerJuridicaItems")
	public List<BusquedaPerJuridicaItem> getBusquedaJuridicaItems() {
		return busquedaPerJuridicaItems;
	}



	public void setBusquedaPerJuridicaItems(List<BusquedaPerJuridicaItem> busquedaPerJuridicaItems) {
		this.busquedaPerJuridicaItems = busquedaPerJuridicaItems;
	}

	
	
	/**
	 *
	 */
	public BusquedaPerJuridicaDTO error(Error error){
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
	    BusquedaPerJuridicaDTO busquedaJuridicaDTO = (BusquedaPerJuridicaDTO) o;
	    return Objects.equals(this.busquedaPerJuridicaItems, busquedaJuridicaDTO.busquedaPerJuridicaItems) &&
	        Objects.equals(this.error, busquedaJuridicaDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(busquedaPerJuridicaItems, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class BusquedaPerJuridicaDTO {\n");
	    
	    sb.append("    busquedaPerJuridicaItems: ").append(toIndentedString(busquedaPerJuridicaItems)).append("\n");
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
	
	public Boolean getOnlyNif() {
		return onlyNif;
	}


	public void setOnlyNif(Boolean onlyNif) {
		this.onlyNif = onlyNif;
	}
}
