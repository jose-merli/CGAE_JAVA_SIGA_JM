/**
 * 
 */
package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author pjarana
 *
 */
public class PreAsistenciaDTO {
	
	private List<PreAsistenciaItem> preasistenciaItems = new ArrayList<PreAsistenciaItem>();
	private Error error;
	/**
	 * @return the preasistenciaItems
	 */
	@JsonProperty("preasistenciaItems")
	public List<PreAsistenciaItem> getPreasistenciaItems() {
		return preasistenciaItems;
	}
	/**
	 * @param preasistenciaItems the preasistenciaItems to set
	 */
	public void setPreasistenciaItems(List<PreAsistenciaItem> preasistenciaItems) {
		this.preasistenciaItems = preasistenciaItems;
	}
	/**
	 * @return the error
	 */
	@JsonProperty("error")
	public Error getError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(Error error) {
		this.error = error;
	}
	@Override
	public int hashCode() {
		return Objects.hash(error, preasistenciaItems);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PreAsistenciaDTO other = (PreAsistenciaDTO) obj;
		return Objects.equals(error, other.error) && Objects.equals(preasistenciaItems, other.preasistenciaItems);
	}
	@Override
	public String toString() {
		return "PreAsistenciaDTO [preasistenciaItems=" + preasistenciaItems + ", error=" + error + "]";
	}
	
	
	
}
