package org.itcgae.siga.DTOs.com;

import java.util.Date;

public class PlantillaEnvioItem {

//	IDTIPOENVIOS      NOT NULL NUMBER(2)     
//	IDPLANTILLAENVIOS NOT NULL NUMBER(3)     
//	NOMBRE            NOT NULL VARCHAR2(100) 
//	IDINSTITUCION     NOT NULL NUMBER(4)     
//	FECHAMODIFICACION NOT NULL DATE          
//	USUMODIFICACION   NOT NULL NUMBER(5)     
//	ACUSERECIBO                VARCHAR2(1)   
//	FECHABAJA                  DATE          
//	ASUNTO                     CLOB          
//	CUERPO                     CLOB          
//	IDDIRECCION                NUMBER(10)    
//	IDPERSONA                  NUMBER(10) 
	
	private String idTipoEnvios;
	private String idPlantillaEnvios;
	private String nombre;
	private String idInstitucion;
	private String acuseRecibo;
	private Date fechaBaja;
	private String asunto;
	private String cuerpo;
	private String idDireccion;
	private String idPersona;
	public String getIdTipoEnvios() {
		return idTipoEnvios;
	}
	public void setIdTipoEnvios(String idTipoEnvios) {
		this.idTipoEnvios = idTipoEnvios;
	}
	public String getIdPlantillaEnvios() {
		return idPlantillaEnvios;
	}
	public void setIdPlantillaEnvios(String idPlantillaEnvios) {
		this.idPlantillaEnvios = idPlantillaEnvios;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getAcuseRecibo() {
		return acuseRecibo;
	}
	public void setAcuseRecibo(String acuseRecibo) {
		this.acuseRecibo = acuseRecibo;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	public String getIdDireccion() {
		return idDireccion;
	}
	public void setIdDireccion(String idDireccion) {
		this.idDireccion = idDireccion;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	
	
	
	
}
