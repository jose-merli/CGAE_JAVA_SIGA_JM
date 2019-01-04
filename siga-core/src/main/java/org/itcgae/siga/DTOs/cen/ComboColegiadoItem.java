package org.itcgae.siga.DTOs.cen;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ComboColegiadoItem {

	private String label = null;
	private String value = null;
	private String nColegiado = null;

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

	@JsonProperty("nColegiado")
	public String getnColegiado() {
		return nColegiado;
	}

	public void setnColegiado(String nColegiado) {
		this.nColegiado = nColegiado;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ComboColegiadoItem comboColegiadoItem = (ComboColegiadoItem) o;
		return Objects.equals(this.label, comboColegiadoItem.label)
				&& Objects.equals(this.value, comboColegiadoItem.value)
				&& Objects.equals(this.nColegiado, comboColegiadoItem.nColegiado);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, label, nColegiado);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ComboColegiadoItem {\n");
		sb.append("    label: ").append(toIndentedString(label)).append("\n");
		sb.append("    value: ").append(toIndentedString(value)).append("\n");
		sb.append("    nColegiado: ").append(toIndentedString(nColegiado)).append("\n");

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
