package org.itcgae.siga.DTOs.cen;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ComboTipoCVItem {

	private String label = null;
	private String value = null;
	private String idInstitucion = null;

	@JsonProperty("label")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@JsonProperty("value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ComboTipoCVItem comboColegiadoItem = (ComboTipoCVItem) o;
		return Objects.equals(this.label, comboColegiadoItem.label)
				&& Objects.equals(this.value, comboColegiadoItem.value)
				&& Objects.equals(this.idInstitucion, comboColegiadoItem.idInstitucion);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, label, idInstitucion);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ComboTipoCVItem {\n");
		sb.append("    label: ").append(toIndentedString(label)).append("\n");
		sb.append("    value: ").append(toIndentedString(value)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");

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
