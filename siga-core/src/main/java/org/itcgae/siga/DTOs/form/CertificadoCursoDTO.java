package org.itcgae.siga.DTOs.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;


public class CertificadoCursoDTO {

	private List<CertificadoCursoItem> certificadoCursoItem = new ArrayList<CertificadoCursoItem>();
	private Error error = null;
	
	/**
	 */
	public CertificadoCursoDTO certificadoCursoItem(List<CertificadoCursoItem> certificadoCursoItem){
		this.certificadoCursoItem = certificadoCursoItem;
		return this;
	}
	
	public List<CertificadoCursoItem> getCertificadoCursoItem() {
		return certificadoCursoItem;
	}
	public void setCertificadoCursoItem(List<CertificadoCursoItem> certificadoCursoItem) {
		this.certificadoCursoItem = certificadoCursoItem;
	}
	
	
	/**
	 */
	public CertificadoCursoDTO error(Error error){
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
	    CertificadoCursoDTO certifcadoCursoDTO = (CertificadoCursoDTO) o;
	    return Objects.equals(this.certificadoCursoItem, certifcadoCursoDTO.certificadoCursoItem) &&
	    		Objects.equals(this.error, certifcadoCursoDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(certificadoCursoItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class CertifcadoCursoDTO {\n");
	    
	    sb.append("CertificadoCursoItem: ").append(toIndentedString(certificadoCursoItem)).append("\n");
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
