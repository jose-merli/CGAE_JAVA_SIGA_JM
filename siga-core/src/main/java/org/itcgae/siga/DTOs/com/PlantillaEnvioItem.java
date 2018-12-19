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
	
	private Short idTipoEnvios;
	private Short idPlantillaEnvios;
	private String nombre;
	private Short idInstitucion;
	private String acuseRecibo;
	private Date fechaBaja;
	private String asunto;
	private String cuerpo;
	private Long idDireccion;
	private Long idPersona;
	
	public Short getIdTipoEnvios() {
		return idTipoEnvios;
	}
	public void setIdTipoEnvios(Short idTipoEnvios) {
		this.idTipoEnvios = idTipoEnvios;
	}
	public Short getIdPlantillaEnvios() {
		return idPlantillaEnvios;
	}
	public void setIdPlantillaEnvios(Short idPlantillaEnvios) {
		this.idPlantillaEnvios = idPlantillaEnvios;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Short getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Short idInstitucion) {
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
	public Long getIdDireccion() {
		return idDireccion;
	}
	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	
	
}
