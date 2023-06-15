package org.itcgae.siga.DTO.fac;

import java.util.Date;

public class ListaServiciosMonederoItem {
	
	//Columnas tabla
	private String nombre; //Nombre del servicio
	private Date fecha;//Fecha informativa en la que se añadió
	private String precioPerio; //campo informativo que muestra lo que cuesta el servicio (mismo formato que la busqueda de servicios)
	
	//Clave primaria servicio
	private Long idServicio;
	private Short idTipoServicios;
	private Long idServiciosInstitucion;
	
	
	
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
	
	public Long getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}
	public Short getIdTipoServicios() {
		return idTipoServicios;
	}
	public void setIdTipoServicios(Short idTipoServicios) {
		this.idTipoServicios = idTipoServicios;
	}
	public Long getIdServiciosInstitucion() {
		return idServiciosInstitucion;
	}
	public void setIdServiciosInstitucion(Long idServiciosInstitucion) {
		this.idServiciosInstitucion = idServiciosInstitucion;
	}

}
