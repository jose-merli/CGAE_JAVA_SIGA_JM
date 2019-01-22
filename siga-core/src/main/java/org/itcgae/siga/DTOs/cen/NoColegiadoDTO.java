package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;


public class NoColegiadoDTO {

	private List<NoColegiadoItem> noColegiadoItem = new ArrayList<NoColegiadoItem>();
	private Error error = null;
	
	/**
	 */
	public NoColegiadoDTO noColegiadoItem(List<NoColegiadoItem> noColegiadoItem){
		this.noColegiadoItem = noColegiadoItem;
		return this;
	}
	
	
	public List<NoColegiadoItem> getNoColegiadoItem() {
		return noColegiadoItem;
	}
	public void setNoColegiadoItem(List<NoColegiadoItem> noColegiadoItem) {
		this.noColegiadoItem = noColegiadoItem;
	}
	
	
	/**
	 */
	public NoColegiadoDTO error(Error error){
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
	    NoColegiadoDTO noColegiadoDTO = (NoColegiadoDTO) o;
	    return Objects.equals(this.noColegiadoItem, noColegiadoDTO.noColegiadoItem) &&
	    		Objects.equals(this.error, noColegiadoDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(noColegiadoItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class NoColegiadoDTO {\n");
	    
	    sb.append("noColegiadoItem: ").append(toIndentedString(noColegiadoItem)).append("\n");
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
