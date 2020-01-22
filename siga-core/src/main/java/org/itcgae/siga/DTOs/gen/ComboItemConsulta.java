package org.itcgae.siga.DTOs.gen;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class ComboItemConsulta {

	private String label = null;
	private String value = null;
	private String idInstitucion = null;
	private String idClaseComunicacion = null;
	private String claseComunicacion = null;

	/**
	   **/
	public ComboItemConsulta idInstitucion(String idInstitucion) {
		this.label = idInstitucion;
		return this;
	}

	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	   **/
	public ComboItemConsulta label(String label) {
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
	public ComboItemConsulta value(String value) {
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
	 **/
	public ComboItemConsulta idClaseComunicacion(String idClaseComunicacion) {
		this.idClaseComunicacion = idClaseComunicacion;
		return this;
	}

	@JsonProperty("idClaseComunicacion")
	public String getIdClaseComunicacion() {
		return idClaseComunicacion;
	}

	public void setIdClaseComunicacion(String idClaseComunicacion) {
		this.idClaseComunicacion = idClaseComunicacion;
	}

	/**
	 **/
	public ComboItemConsulta claseComunicacion(String claseComunicacion) {
		this.claseComunicacion = claseComunicacion;
		return this;
	}

	@JsonProperty("claseComunicacion")
	public String getClaseComunicacion() {
		return claseComunicacion;
	}

	public void setClaseComunicacion(String claseComunicacion) {
		this.claseComunicacion = claseComunicacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((claseComunicacion == null) ? 0 : claseComunicacion.hashCode());
		result = prime * result + ((idClaseComunicacion == null) ? 0 : idClaseComunicacion.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComboItemConsulta other = (ComboItemConsulta) obj;
		if (claseComunicacion == null) {
			if (other.claseComunicacion != null)
				return false;
		} else if (!claseComunicacion.equals(other.claseComunicacion))
			return false;
		if (idClaseComunicacion == null) {
			if (other.idClaseComunicacion != null)
				return false;
		} else if (!idClaseComunicacion.equals(other.idClaseComunicacion))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ComboItemConsulta [label=" + label + ", value=" + value + ", idInstitucion=" + idInstitucion
				+ ", idClaseComunicacion=" + idClaseComunicacion + ", claseComunicacion=" + claseComunicacion + "]";
	}

	
}
