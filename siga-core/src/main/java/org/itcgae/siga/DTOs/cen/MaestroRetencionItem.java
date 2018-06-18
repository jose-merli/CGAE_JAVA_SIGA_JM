package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaestroRetencionItem {
	
	
	private String value;
	private String label;
	private String porcentajeRetencion;
	
	
	
	
	/**
	 *
	 */
	public MaestroRetencionItem value(String value){
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
	
	
	/**
	 *
	 */
	public MaestroRetencionItem label(String label){
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
	 *
	 */
	public MaestroRetencionItem porcentajeRetencion(String porcentajeRetencion){
		this.porcentajeRetencion = porcentajeRetencion;
		return this;
	}
	
	@JsonProperty("porcentajeRetencion")
	public String getPorcentajeRetencion() {
		return porcentajeRetencion;
	}
	public void setPorcentajeRetencion(String porcentajeRetencion) {
		this.porcentajeRetencion = porcentajeRetencion;
	}
	
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class MaestroRetencionItem {\n");
	    sb.append("    value: ").append(toIndentedString(value)).append("\n");
	    sb.append("    label: ").append(toIndentedString(label)).append("\n");
	    sb.append("    porcentajeRetencion: ").append(toIndentedString(porcentajeRetencion)).append("\n");
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

	
	@Override
	public int hashCode() {
		return Objects.hash(value, label, porcentajeRetencion);
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
}
