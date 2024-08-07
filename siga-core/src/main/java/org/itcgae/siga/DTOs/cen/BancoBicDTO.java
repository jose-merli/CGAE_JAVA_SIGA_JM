package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class BancoBicDTO {
	
	private List<BancoBicItem> bancoBicItem = new ArrayList<BancoBicItem>();
	private Error error = null;
	
	
	/**
	 *
	 */
	public BancoBicDTO bancoBicItem(List<BancoBicItem> bancoBicItem){
		this.bancoBicItem = bancoBicItem;
		return this;
	}
	
	
	@JsonProperty("bancoBicItem")
	public List<BancoBicItem> getBancoBicItem() {
		return bancoBicItem;
	}


	public void setBancoBicItem(List<BancoBicItem> bancoBicItem) {
		this.bancoBicItem = bancoBicItem;
	}
	
	
	/**
	 *
	 */
	public BancoBicDTO error(Error error){
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
	    BancoBicDTO BusquedaPerFisicaDTO = (BancoBicDTO) o;
	    return Objects.equals(this.bancoBicItem, BusquedaPerFisicaDTO.bancoBicItem) &&
	        Objects.equals(this.error, BusquedaPerFisicaDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(bancoBicItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class BusquedaFisicaDTO {\n");
	    
	    sb.append("    bancoBicItem: ").append(toIndentedString(bancoBicItem)).append("\n");
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
