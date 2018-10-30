package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;


public class CertificadoDTO {

	private List<CertificadoItem> certificadoItem = new ArrayList<CertificadoItem>();
	private Error error = null;
	
	/**
	 */
	public CertificadoDTO CertificadoItem(List<CertificadoItem> certificadoItem){
		this.certificadoItem = certificadoItem;
		return this;
	}
	
	
	public List<CertificadoItem> getCertificadoItem() {
		return certificadoItem;
	}
	public void setCertificadoItem(List<CertificadoItem> certificadoItem) {
		this.certificadoItem = certificadoItem;
	}
	
	
	/**
	 */
	public CertificadoDTO error(Error error){
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
	    CertificadoDTO certificadoDTO = (CertificadoDTO) o;
	    return Objects.equals(this.certificadoItem, certificadoDTO.certificadoItem) &&
	    		Objects.equals(this.error, certificadoDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(certificadoItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class CertificadoDTO {\n");
	    
	    sb.append("certificadoItem: ").append(toIndentedString(certificadoItem)).append("\n");
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
