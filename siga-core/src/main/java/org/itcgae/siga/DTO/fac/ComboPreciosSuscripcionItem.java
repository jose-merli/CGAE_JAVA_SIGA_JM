package org.itcgae.siga.DTO.fac;

public class ComboPreciosSuscripcionItem {
	
	private int idpreciosservicios;
	private int idserviciosinstitucion;
	private int idtiposervicios;
	private int idservicio;
	private double precio;
	private int idperiodicidad;
	private int periodicidadValor;
	private String descripcionprecio;
	private int idcondicion;
	private String descripcionperiodicidad;
	private String descripcionconsulta;
	private String pordefecto;
	private String valido;
	
	
	public int getIdpreciosservicios() {
		return idpreciosservicios;
	}
	public void setIdpreciosservicios(int idpreciosservicios) {
		this.idpreciosservicios = idpreciosservicios;
	}
	public int getIdserviciosinstitucion() {
		return idserviciosinstitucion;
	}
	public void setIdserviciosinstitucion(int idserviciosinstitucion) {
		this.idserviciosinstitucion = idserviciosinstitucion;
	}
	public int getIdtiposervicios() {
		return idtiposervicios;
	}
	public void setIdtiposervicios(int idtiposervicios) {
		this.idtiposervicios = idtiposervicios;
	}
	public int getIdservicio() {
		return idservicio;
	}
	public void setIdservicio(int idservicio) {
		this.idservicio = idservicio;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getIdperiodicidad() {
		return idperiodicidad;
	}
	public void setIdperiodicidad(int idperiodicidad) {
		this.idperiodicidad = idperiodicidad;
	}
	public int getPeriodicidadValor() {
		return periodicidadValor;
	}
	public void setPeriodicidadValor(int periodicidadValor) {
		this.periodicidadValor = periodicidadValor;
	}
	public String getDescripcionprecio() {
		return descripcionprecio;
	}
	public void setDescripcionprecio(String descripcionprecio) {
		this.descripcionprecio = descripcionprecio;
	}
	public int getIdcondicion() {
		return idcondicion;
	}
	public void setIdcondicion(int idcondicion) {
		this.idcondicion = idcondicion;
	}
	public String getDescripcionperiodicidad() {
		return descripcionperiodicidad;
	}
	public void setDescripcionperiodicidad(String descripcionperiodicidad) {
		this.descripcionperiodicidad = descripcionperiodicidad;
	}
	public String getDescripcionconsulta() {
		return descripcionconsulta;
	}
	public void setDescripcionconsulta(String descripcionconsulta) {
		this.descripcionconsulta = descripcionconsulta;
	}
	public String getPordefecto() {
		return pordefecto;
	}
	public void setPordefecto(String pordefecto) {
		this.pordefecto = pordefecto;
	}
	public String getValido() {
		return valido;
	}
	public void setValido(String valido) {
		this.valido = valido;
	}

}
