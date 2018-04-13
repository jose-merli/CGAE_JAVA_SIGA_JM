package org.itcgae.siga.DTOs.adm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class ParametroDTO {

	private List<ParametroItem> parametrosItems = new ArrayList<ParametroItem>();
	private Error error = null;
	
	
	/**
	 **/
	public ParametroDTO parametrosItems(List<ParametroItem> parametrosItems) {
		this.parametrosItems = parametrosItems;
		return this;
	}
	
	
	@JsonProperty("parametrosItems")
	public List<ParametroItem> getParametrosItems() {
		return parametrosItems;
	}
	
	
	public void setParametrosItems(List<ParametroItem> parametrosItems) {
		this.parametrosItems = parametrosItems;
	}
	
	
	/**
	 **/
	public ParametroDTO error(Error error) {
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
		ParametroDTO parametroDTO = (ParametroDTO) o;
		return Objects.equals(this.parametrosItems, parametroDTO.parametrosItems) && Objects.equals(this.error, parametroDTO.error);
	}

	@Override
	public int hashCode() {
		return Objects.hash(parametrosItems, error);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ParametroDTO {\n");

		sb.append("    parametrosItems: ").append(toIndentedString(parametrosItems)).append("\n");
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
