package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComboInstitucionDTO {
	private List<ComboInstitucionItem> comboInstitucionItem = new ArrayList<ComboInstitucionItem>();
	private Error error = null;

	/**
	 **/
	public ComboInstitucionDTO comboItems(List<ComboInstitucionItem> comboInstitucionItem) {
		this.comboInstitucionItem = comboInstitucionItem;
		return this;
	}

	@JsonProperty("comboItems")
	public List<ComboInstitucionItem> getComboInstitucionItem() {
		return comboInstitucionItem;
	}

	public void setComboInstitucionItem(List<ComboInstitucionItem> comboInstitucionItem) {
		this.comboInstitucionItem = comboInstitucionItem;
	}

	/**
	 **/
	public ComboInstitucionDTO error(Error error) {
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
		ComboInstitucionDTO comboInstitucionDTO = (ComboInstitucionDTO) o;
		return Objects.equals(this.comboInstitucionItem, comboInstitucionDTO.comboInstitucionItem)
				&& Objects.equals(this.error, comboInstitucionDTO.error);
	}

	@Override
	public int hashCode() {
		return Objects.hash(comboInstitucionItem, error);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ComboInstitucionDTO {\n");

		sb.append("    comboInstitucionItem: ").append(toIndentedString(comboInstitucionItem)).append("\n");
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
