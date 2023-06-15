package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActasDTO {

	private List<ActasItem> actasItems = new ArrayList<ActasItem>();
	private Error error = null;

	/**
	 * 
	 **/
	public ActasDTO actasItems(List<ActasItem> actasItems) {
		this.actasItems = actasItems;
		return this;
	}

	@JsonProperty("actasItems")
	public List<ActasItem> getActasItems() {
		return actasItems;
	}

	public void setActasItems(List<ActasItem> actasItems) {
		this.actasItems = actasItems;
	}

	/**
	 * 
	 **/
	public ActasDTO error(Error error) {
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
		ActasDTO actasDTO = (ActasDTO) o;
		return Objects.equals(this.actasItems, actasDTO.actasItems) && Objects.equals(this.error, actasDTO.error);
	}

	@Override
	public int hashCode() {
		return Objects.hash(actasItems, error);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class actasDTO {\n");

		sb.append("    actasItems: ").append(toIndentedString(actasItems)).append("\n");
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

	public void actasItem(List<ActasItem> busquedaActas) {
		this.actasItems = busquedaActas;
	}

}
