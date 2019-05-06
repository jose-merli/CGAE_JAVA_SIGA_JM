package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComboTiposCVDTO {
	private List<ComboTipoCVItem> comboTipoCVItem = new ArrayList<ComboTipoCVItem>();
	private Error error = null;

	/**
	 **/
	public ComboTiposCVDTO comboTipoCVItem(List<ComboTipoCVItem> comboTipoCVItem) {
		this.comboTipoCVItem = comboTipoCVItem;
		return this;
	}

	@JsonProperty("combooItems")
	public List<ComboTipoCVItem> getComboTipoCVItem() {
		return comboTipoCVItem;
	}

	public void setComboTipoCVItem(List<ComboTipoCVItem> comboTipoCVItem) {
		this.comboTipoCVItem = comboTipoCVItem;
	}

	/**
	 **/
	public ComboTiposCVDTO error(Error error) {
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
		ComboTiposCVDTO comboSubtipoCVDTO = (ComboTiposCVDTO) o;
		return Objects.equals(this.comboTipoCVItem, comboSubtipoCVDTO.comboTipoCVItem)
				&& Objects.equals(this.error, comboSubtipoCVDTO.error);
	}

	@Override
	public int hashCode() {
		return Objects.hash(comboTipoCVItem, error);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ComboSubtiposCVDTO {\n");

		sb.append("    comboSubtipoCVItem: ").append(toIndentedString(comboTipoCVItem)).append("\n");
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
