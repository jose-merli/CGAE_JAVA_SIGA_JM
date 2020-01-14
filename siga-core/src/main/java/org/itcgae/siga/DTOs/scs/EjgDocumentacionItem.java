package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EjgDocumentacionItem {
	private Date flimite_presentacion;
	private String presentador;
	private String documentoDesc;
	private String regEntrada;
	private String regSalida;
	private Date f_presentacion;
	private String propietario;	
	private String parentesco;
	private String presentador_persona; //nombrem apellido1, apellido2 de personajg

   
	 /**
	   * 
	 **/
	  public EjgDocumentacionItem flimite_presentacion(Date flimite_presentacion) {
	    this.flimite_presentacion = flimite_presentacion;
	    return this;
	  }	  
	  @JsonProperty("flimite_presentacion")
	public Date getFlimite_presentacion() {
		return flimite_presentacion;
	}
	public void setFlimite_presentacion(Date flimite_presentacion) {
		this.flimite_presentacion = flimite_presentacion;
	}
	 /**
	   * 
	 **/
	  public EjgDocumentacionItem presentador(String presentador) {
	    this.presentador = presentador;
	    return this;
	  }	  
	  @JsonProperty("presentador")
	public String getPresentador() {
		return presentador;
	}
	public void setPresentador(String presentador) {
		this.presentador = presentador;
	}
	 /**
	   * 
	 **/
	  public EjgDocumentacionItem documentoDesc(String documentoDesc) {
	    this.documentoDesc = documentoDesc;
	    return this;
	  }	  
	  @JsonProperty("documentoDesc")
	public String getDocumentoDesc() {
		return documentoDesc;
	}
	public void setDocumentoDesc(String documentoDesc) {
		this.documentoDesc = documentoDesc;
	}
	 /**
	   * 
	 **/
	  public EjgDocumentacionItem regEntrada(String regEntrada) {
	    this.regEntrada = regEntrada;
	    return this;
	  }	  
	  @JsonProperty("regEntrada")
	public String getRegEntrada() {
		return regEntrada;
	}
	public void setRegEntrada(String regEntrada) {
		this.regEntrada = regEntrada;
	}
	 /**
	   * 
	 **/
	  public EjgDocumentacionItem regSalida(String regSalida) {
	    this.regSalida = regSalida;
	    return this;
	  }	  
	  @JsonProperty("regSalida")
	public String getRegSalida() {
		return regSalida;
	}
	public void setRegSalida(String regSalida) {
		this.regSalida = regSalida;
	}
	 /**
	   * 
	 **/
	  public EjgDocumentacionItem f_presentacion(Date f_presentacion) {
	    this.f_presentacion = f_presentacion;
	    return this;
	  }	  
	  @JsonProperty("f_presentacion")
	public Date getF_presentacion() {
		return f_presentacion;
	}
	public void setF_presentacion(Date f_presentacion) {
		this.f_presentacion = f_presentacion;
	}
	 /**
	   * 
	 **/
	  public EjgDocumentacionItem propietario(String propietario) {
	    this.propietario = propietario;
	    return this;
	  }	  
	  @JsonProperty("propietario")
	public String getPropietario() {
		return propietario;
	}
	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}
	 /**
	   * 
	 **/
	  public EjgDocumentacionItem parentesco(String parentesco) {
	    this.parentesco = parentesco;
	    return this;
	  }	  
	  @JsonProperty("parentesco")
	public String getParentesco() {
		return parentesco;
	}
	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}
	 /**
	   * 
	 **/
	  public EjgDocumentacionItem presentador_persona(String presentador_persona) {
	    this.presentador_persona = presentador_persona;
	    return this;
	  }	  
	  @JsonProperty("presentador_persona")
	public String getPresentador_persona() {
		return presentador_persona;
	}
	public void setPresentador_persona(String presentador_persona) {
		this.presentador_persona = presentador_persona;
	}
	

}
