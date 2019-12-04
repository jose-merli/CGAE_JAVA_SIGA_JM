package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AsuntosJusticiableDTO {

	private List<AsuntosJusticiableItem> asuntosJusticiableItems = new ArrayList<AsuntosJusticiableItem>();
	private Error error = null;

	/**
	 **/
	public AsuntosJusticiableDTO asuntosJusticiableItems(List<AsuntosJusticiableItem> asuntosJusticiableItems) {
		this.asuntosJusticiableItems = asuntosJusticiableItems;
		return this;
	}

	@JsonProperty("asuntosJusticiableItems")
	public List<AsuntosJusticiableItem> getAsuntosJusticiableItems() {
		return asuntosJusticiableItems;
	}

	public void setAsuntosJusticiableItems(List<AsuntosJusticiableItem> asuntosJusticiableItems) {
		this.asuntosJusticiableItems = asuntosJusticiableItems;
	}

	/**
	 **/
	public AsuntosJusticiableDTO error(Error error) {
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
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class AsuntosJusticiableDTO {\n");

		sb.append("    asuntosJusticiableItems: ").append(toIndentedString(asuntosJusticiableItems)).append("\n");
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
