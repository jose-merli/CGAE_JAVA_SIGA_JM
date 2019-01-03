package org.itcgae.siga.DTOs.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

/**
 * @author DTUser
 *
 */
/**
 * @author DTUser
 *
 */
public class CargaMasivaInscripcionesDTO {

	private List<CargaMasivaInscripcionesItem> cargaMasivaInscripcionesItem = new ArrayList<CargaMasivaInscripcionesItem>();
	private Error error = null;
	
	/**
	 */
	public CargaMasivaInscripcionesDTO CargaMasivaInscripcionesItem(List<CargaMasivaInscripcionesItem> cargaMasivaInscripcionesItem){
		this.cargaMasivaInscripcionesItem = cargaMasivaInscripcionesItem;
		return this;
	}
	
	public List<CargaMasivaInscripcionesItem> getCargaMasivaInscripcionesItem() {
		return cargaMasivaInscripcionesItem;
	}
	public void setCargaMasivaInscripcionesItem(List<CargaMasivaInscripcionesItem> asistenciaCursoItem) {
		this.cargaMasivaInscripcionesItem = asistenciaCursoItem;
	}
	
	
	/**
	 */
	public CargaMasivaInscripcionesDTO error(Error error){
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
	    CargaMasivaInscripcionesDTO cargaMasivaInscripcionesDTO = (CargaMasivaInscripcionesDTO) o;
	    return Objects.equals(this.cargaMasivaInscripcionesItem, cargaMasivaInscripcionesDTO.cargaMasivaInscripcionesItem) &&
	    		Objects.equals(this.error, cargaMasivaInscripcionesDTO.error);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(cargaMasivaInscripcionesItem, error);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class CargaMasivaInscripcionesDTO {\n");
	    
	    sb.append("CargaMasivaInscripcionesItem: ").append(toIndentedString(cargaMasivaInscripcionesItem)).append("\n");
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
