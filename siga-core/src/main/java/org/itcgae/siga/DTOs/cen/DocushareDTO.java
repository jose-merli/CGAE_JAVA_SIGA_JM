package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;


public class DocushareDTO {
	private List<DocuShareObjectVO> docuShareObjectVOItem = new ArrayList<DocuShareObjectVO>();
	private Error error = null;
	private String identificadorDS = null;
	
	
	public String getIdentificadorDS() {
		return identificadorDS;
	}


	public void setIdentificadorDS(String identificadorDS) {
		this.identificadorDS = identificadorDS;
	}


	/**
	 */
	public DocushareDTO nif(List<DocuShareObjectVO> docuShareObjectVOItem){
		this.docuShareObjectVOItem = docuShareObjectVOItem;
		return this;
	}
	
	
	public List<DocuShareObjectVO> getDocuShareObjectVO() {
		return docuShareObjectVOItem;
	}
	public void setDocuShareObjectVO(List<DocuShareObjectVO> docuShareObjectVOItem) {
		this.docuShareObjectVOItem = docuShareObjectVOItem;
	}
	
	
	/**
	 */
	public DocushareDTO nif(Error error){
		this.error = error;
		return this;
	}
	
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
	public void sort() {
		Collections.sort(docuShareObjectVOItem);
	}
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    DocushareDTO datosBancariosDTO = (DocushareDTO) o;
	    return Objects.equals(this.docuShareObjectVOItem, datosBancariosDTO.docuShareObjectVOItem) &&
	    		Objects.equals(this.error, datosBancariosDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(docuShareObjectVOItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DocuShareObjectVO {\n");
	    
	    sb.append("docushareItems: ").append(toIndentedString(docuShareObjectVOItem)).append("\n");
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
