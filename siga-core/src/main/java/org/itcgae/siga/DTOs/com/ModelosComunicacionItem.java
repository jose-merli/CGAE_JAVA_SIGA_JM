package org.itcgae.siga.DTOs.com;

import java.util.Date;

public class ModelosComunicacionItem {
//	IDMODELOCOMUNICACION NOT NULL NUMBER(10)     
//	NOMBRE                        VARCHAR2(100)  
//	ORDEN                         NUMBER(3)      
//	IDINSTITUCION                 NUMBER(4)      
//	DESCRIPCION                   VARCHAR2(4000) 
//	PRESELECCIONAR                VARCHAR2(2)    
//	IDCLASECOMUNICACION  NOT NULL NUMBER(4)      
//	FECHABAJA                     DATE           
//	FECHAMODIFICACION             DATE           
//	USUMODIFICACION               NUMBER(5)  
	
	private String idModeloComunicacion;
	private String nombre;
	private String orden;
	private String idInstitucion;
	private String descripcion;
	private String preseleccionar;
	private String idClaseComunicacion;
	private Date fechaBaja;
	
	private String visible;
	private String institucion;
	private String clase;
	
	public String getIdModeloComunicacion() {
		return idModeloComunicacion;
	}
	public void setIdModeloComunicacion(String idModeloComunicacion) {
		this.idModeloComunicacion = idModeloComunicacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getPreseleccionar() {
		return preseleccionar;
	}
	public void setPreseleccionar(String preseleccionar) {
		this.preseleccionar = preseleccionar;
	}
	public String getIdClaseComunicacion() {
		return idClaseComunicacion;
	}
	public void setIdClaseComunicacion(String idClaseComunicacion) {
		this.idClaseComunicacion = idClaseComunicacion;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	
	
}
