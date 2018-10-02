package org.itcgae.siga.DTOs.cen;

import java.util.Date;

public class FicheroVo{
	private Long idfichero;
    private Short idinstitucion;
	private String nombre;
	private String descripcion;
	private String directorio;
	
    private String extension;
    private Date fechamodificacion;
    private Integer usumodificacion;
    private byte[] fichero;
    
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public Date getFechamodificacion() {
		return fechamodificacion;
	}
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}
	public Integer getUsumodificacion() {
		return usumodificacion;
	}
	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}
	public byte[] getFichero() {
		return fichero;
	}
	public void setFichero(byte[] fichero) {
		this.fichero = fichero;
	}
	public Long getIdfichero() {
		return idfichero;
	}
	public void setIdfichero(Long idfichero) {
		this.idfichero = idfichero;
	}
	public Short getIdinstitucion() {
		return idinstitucion;
	}
	public void setIdinstitucion(Short idinstitucion) {
		this.idinstitucion = idinstitucion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDirectorio() {
		return directorio;
	}
	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}
}