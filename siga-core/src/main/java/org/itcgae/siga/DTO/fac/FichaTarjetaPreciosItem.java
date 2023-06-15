package org.itcgae.siga.DTO.fac;

public class FichaTarjetaPreciosItem {
	
	private int idpreciosservicios;
	private int idserviciosinstitucion;
	private int idtiposervicios;
	private int idservicio;
	private String precio;
	private int idperiodicidad;
	private int periodicidadValor;
	private String descripcionprecio;
	private int idcondicion;
	private String descripcionperiodicidad;
	private String descripcionconsulta;
	private String pordefecto;
	
	private int idperiodicidadoriginal;
	private String nuevo = "0";
	
	
	private double valor;

	
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getNuevo() {
		return nuevo;
	}
	public void setNuevo(String nuevo) {
		this.nuevo = nuevo;
	}
	public int getIdperiodicidadoriginal() {
		return idperiodicidadoriginal;
	}
	public void setIdperiodicidadoriginal(int idperiodicidadoriginal) {
		this.idperiodicidadoriginal = idperiodicidadoriginal;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public int getIdperiodicidad() {
		return idperiodicidad;
	}
	public void setIdperiodicidad(int idperiodicidad) {
		this.idperiodicidad = idperiodicidad;
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
	public int getIdpreciosservicios() {
		return idpreciosservicios;
	}
	public void setIdpreciosservicios(int idpreciosservicios) {
		this.idpreciosservicios = idpreciosservicios;
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
	public String getPordefecto() {
		return pordefecto;
	}
	public void setPordefecto(String pordefecto) {
		this.pordefecto = pordefecto;
	}
	public void setDescripcionconsulta(String descripcionconsulta) {
		this.descripcionconsulta = descripcionconsulta;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcionconsulta == null) ? 0 : descripcionconsulta.hashCode());
		result = prime * result + ((descripcionperiodicidad == null) ? 0 : descripcionperiodicidad.hashCode());
		result = prime * result + ((descripcionprecio == null) ? 0 : descripcionprecio.hashCode());
		result = prime * result + idcondicion;
		result = prime * result + idperiodicidad;
		result = prime * result + idperiodicidadoriginal;
		result = prime * result + idpreciosservicios;
		result = prime * result + idservicio;
		result = prime * result + idserviciosinstitucion;
		result = prime * result + idtiposervicios;
		result = prime * result + ((nuevo == null) ? 0 : nuevo.hashCode());
		result = prime * result + ((pordefecto == null) ? 0 : pordefecto.hashCode());
		result = prime * result + ((precio == null) ? 0 : precio.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		FichaTarjetaPreciosItem other = (FichaTarjetaPreciosItem) obj;
		if (descripcionconsulta == null) {
			if (other.descripcionconsulta != null)
				return false;
		} else if (!descripcionconsulta.equals(other.descripcionconsulta))
			return false;
		if (descripcionperiodicidad == null) {
			if (other.descripcionperiodicidad != null)
				return false;
		} else if (!descripcionperiodicidad.equals(other.descripcionperiodicidad))
			return false;
		if (descripcionprecio == null) {
			if (other.descripcionprecio != null)
				return false;
		} else if (!descripcionprecio.equals(other.descripcionprecio))
			return false;
		if (idcondicion != other.idcondicion)
			return false;
		if (idperiodicidad != other.idperiodicidad)
			return false;
		if (idperiodicidadoriginal != other.idperiodicidadoriginal)
			return false;
		if (idpreciosservicios != other.idpreciosservicios)
			return false;
		if (idservicio != other.idservicio)
			return false;
		if (idserviciosinstitucion != other.idserviciosinstitucion)
			return false;
		if (idtiposervicios != other.idtiposervicios)
			return false;
		if (nuevo == null) {
			if (other.nuevo != null)
				return false;
		} else if (!nuevo.equals(other.nuevo))
			return false;
		if (pordefecto == null) {
			if (other.pordefecto != null)
				return false;
		} else if (!pordefecto.equals(other.pordefecto))
			return false;
		if (precio == null) {
			if (other.precio != null)
				return false;
		} else if (!precio.equals(other.precio))
			return false;
		if (Double.doubleToLongBits(valor) != Double.doubleToLongBits(other.valor))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "FichaTarjetaPreciosItem [idpreciosservicios=" + idpreciosservicios + ", idserviciosinstitucion="
				+ idserviciosinstitucion + ", idtiposervicios=" + idtiposervicios + ", idservicio=" + idservicio
				+ ", precio=" + precio + ", idperiodicidad=" + idperiodicidad + ", descripcionprecio="
				+ descripcionprecio + ", idcondicion=" + idcondicion + ", descripcionperiodicidad="
				+ descripcionperiodicidad + ", descripcionconsulta=" + descripcionconsulta + ", pordefecto="
				+ pordefecto + ", idperiodicidadoriginal=" + idperiodicidadoriginal + ", nuevo=" + nuevo + ", valor="
				+ valor + "]";
	}
	public int getPeriodicidadValor() {
		return periodicidadValor;
	}
	public void setPeriodicidadValor(int periodicidadValor) {
		this.periodicidadValor = periodicidadValor;
	}
	
}
