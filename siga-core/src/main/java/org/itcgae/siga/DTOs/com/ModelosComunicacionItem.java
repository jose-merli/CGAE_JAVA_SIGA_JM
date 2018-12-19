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
	
	private Long idModeloComunicacion;
	private String nombre;
	private Short orden;
	private Short idInstitucion;
	private String descripcion;
	private String preseleccionar;
	private Short idClaseComunicacion;
	private Date fechaBaja;
	
	public Long getIdModeloComunicacion() {
		return idModeloComunicacion;
	}
	public void setIdModeloComunicacion(Long idModeloComunicacion) {
		this.idModeloComunicacion = idModeloComunicacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Short getOrden() {
		return orden;
	}
	public void setOrden(Short orden) {
		this.orden = orden;
	}
	public Short getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Short idInstitucion) {
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
	public Short getIdClaseComunicacion() {
		return idClaseComunicacion;
	}
	public void setIdClaseComunicacion(Short idClaseComunicacion) {
		this.idClaseComunicacion = idClaseComunicacion;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	
}
