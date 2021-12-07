package org.itcgae.siga.DTO.fac;

import java.util.Date;

public class ListaServiciosMonederoItem {
	
	//Columnas tabla
	private String nombre; //Nombre del servicio
	private Date fecha;//Fecha informativa en la que se añadió
	private String precioPerio; //campo informativo que muestra lo que cuesta el servicio (mismo formato que la busqueda de servicios)
	
	//Clave primaria servicio
	private Long idservicio;
	private Short idtiposervicios;
	private Long idserviciosinstitucion;
	
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getPrecioPerio() {
		return precioPerio;
	}
	public void setPrecioPerio(String precioPerio) {
		this.precioPerio = precioPerio;
	}
	public Long getIdservicio() {
		return idservicio;
	}
	public void setIdservicio(Long idservicio) {
		this.idservicio = idservicio;
	}
	public Short getIdtiposervicios() {
		return idtiposervicios;
	}
	public void setIdtiposervicios(Short idtiposervicios) {
		this.idtiposervicios = idtiposervicios;
	}
	public Long getIdserviciosinstitucion() {
		return idserviciosinstitucion;
	}
	public void setIdserviciosinstitucion(Long idserviciosinstitucion) {
		this.idserviciosinstitucion = idserviciosinstitucion;
	}

}
