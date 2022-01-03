package org.itcgae.siga.DTOs.gen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class ComboItemAnioActa {

	private String label = null;
	private String value = null;
	private String anioacta = null;

	public String getAnioacta() {
		return anioacta;
	}

	public void setAnioacta(String anioacta) {
		this.anioacta = anioacta;
	}

	/**
	   **/
	public ComboItemAnioActa label(String label) {
		this.label = label;
		return this;
	}

	@JsonProperty("label")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 **/
	public ComboItemAnioActa value(String value) {
		this.value = value;
		return this;
	}

	@JsonProperty("value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ComboItemAnioActa comboItem = (ComboItemAnioActa) o;
		return Objects.equals(this.label, comboItem.label) && Objects.equals(this.value, comboItem.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, label);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ComboItem {\n");
		sb.append("    label: ").append(toIndentedString(label)).append("\n");
		sb.append("    value: ").append(toIndentedString(value)).append("\n");

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
