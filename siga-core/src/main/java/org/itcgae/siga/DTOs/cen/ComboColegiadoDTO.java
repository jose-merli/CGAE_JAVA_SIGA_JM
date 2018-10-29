package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComboColegiadoDTO {
	private List<ComboColegiadoItem> comboColegiadoItems = new ArrayList<ComboColegiadoItem>();
	private Error error = null;

	/**
	 **/
	public ComboColegiadoDTO combooItems(List<ComboColegiadoItem> comboColegiadoItems) {
		this.comboColegiadoItems = comboColegiadoItems;
		return this;
	}

	@JsonProperty("comboColegiadoItems")
	public List<ComboColegiadoItem> getCombooItems() {
		return comboColegiadoItems;
	}

	public void setCombooItems(List<ComboColegiadoItem> comboColegiadoItems) {
		this.comboColegiadoItems = comboColegiadoItems;
	}

	/**
	 **/
	public ComboColegiadoDTO error(Error error) {
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
		ComboColegiadoDTO comboColegiadoDTO = (ComboColegiadoDTO) o;
		return Objects.equals(this.comboColegiadoItems, comboColegiadoDTO.comboColegiadoItems)
				&& Objects.equals(this.error, comboColegiadoDTO.error);
	}

	@Override
	public int hashCode() {
		return Objects.hash(comboColegiadoItems, error);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ComboColegiadoDTO {\n");

		sb.append("    comboColegiadoItems: ").append(toIndentedString(comboColegiadoItems)).append("\n");
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
