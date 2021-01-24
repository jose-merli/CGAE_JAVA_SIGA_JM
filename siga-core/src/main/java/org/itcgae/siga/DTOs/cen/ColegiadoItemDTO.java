package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;


public class ColegiadoItemDTO {

	private List<ColegiadoItem> colegiadoItemEstados = new ArrayList<ColegiadoItem>();
	private ColegiadoItem colegiadoItem = new ColegiadoItem();
	private ColegiadoItem nuevocolegiadoItem = new ColegiadoItem();
	private Error error = null;
	
	/**
	 */
	public ColegiadoItemDTO ColegiadoItem(ColegiadoItem colegiadoItem){
		this.colegiadoItem = colegiadoItem;
		return this;
	}
	
	
	public ColegiadoItem getColegiadoItem() {
		return colegiadoItem;
	}
	public void setColegiadoItem(ColegiadoItem colegiadoItem) {
		this.colegiadoItem = colegiadoItem;
	}
	
	
	/**
	 */
	public ColegiadoItemDTO error(Error error){
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
	    ColegiadoItemDTO colegiadoDTO = (ColegiadoItemDTO) o;
	    return Objects.equals(this.colegiadoItem, colegiadoDTO.colegiadoItem) &&
	    		Objects.equals(this.error, colegiadoDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(colegiadoItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class ColegiadoDTO {\n");
	    
	    sb.append("colegiadoItem: ").append(toIndentedString(colegiadoItem)).append("\n");
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





	public List<ColegiadoItem> getColegiadoItemEstados() {
		return colegiadoItemEstados;
	}


	public void setColegiadoItemEstados(List<ColegiadoItem> colegiadoItemEstados) {
		this.colegiadoItemEstados = colegiadoItemEstados;
	}


	public ColegiadoItem getNuevocolegiadoItem() {
		return nuevocolegiadoItem;
	}


	public void setNuevocolegiadoItem(ColegiadoItem nuevocolegiadoItem) {
		this.nuevocolegiadoItem = nuevocolegiadoItem;
	}
	
	
	
}
