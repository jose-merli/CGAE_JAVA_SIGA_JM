package org.itcgae.siga.DTO.fac;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ListaServiciosItem {
	
	private int idinstitucion;
	private int idservicio;
	private int idtiposervicios;
	private int idserviciosinstitucion;
	private String descripcion;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechabaja;
	private String automatico;
	private int idtipoiva;
	private String categoria;
	private String tipo;
	private String iva;
	private String precioivames;
	private String formapago;
	
	public int getIdinstitucion() {
		return idinstitucion;
	}
	public void setIdinstitucion(int idinstitucion) {
		this.idinstitucion = idinstitucion;
	}
	public int getIdservicio() {
		return idservicio;
	}
	public void setIdservicio(int idservicio) {
		this.idservicio = idservicio;
	}
	public int getIdtiposervicios() {
		return idtiposervicios;
	}
	public void setIdtiposervicios(int idtiposervicios) {
		this.idtiposervicios = idtiposervicios;
	}
	public int getIdserviciosinstitucion() {
		return idserviciosinstitucion;
	}
	public void setIdserviciosinstitucion(int idserviciosinstitucion) {
		this.idserviciosinstitucion = idserviciosinstitucion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechabaja() {
		return fechabaja;
	}
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}
	public String getAutomatico() {
		return automatico;
	}
	public void setAutomatico(String automatico) {
		this.automatico = automatico;
	}
	public int getIdtipoiva() {
		return idtipoiva;
	}
	public void setIdtipoiva(int idtipoiva) {
		this.idtipoiva = idtipoiva;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getIva() {
		return iva;
	}
	public void setIva(String iva) {
		this.iva = iva;
	}
	public String getPrecioivames() {
		return precioivames;
	}
	public void setPrecioivames(String precioivames) {
		this.precioivames = precioivames;
	}
	public String getFormapago() {
		return formapago;
	}
	public void setFormapago(String formapago) {
		this.formapago = formapago;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((automatico == null) ? 0 : automatico.hashCode());
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((fechabaja == null) ? 0 : fechabaja.hashCode());
		result = prime * result + ((formapago == null) ? 0 : formapago.hashCode());
		result = prime * result + idinstitucion;
		result = prime * result + idservicio;
		result = prime * result + idserviciosinstitucion;
		result = prime * result + idtipoiva;
		result = prime * result + idtiposervicios;
		result = prime * result + ((iva == null) ? 0 : iva.hashCode());
		result = prime * result + ((precioivames == null) ? 0 : precioivames.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		ListaServiciosItem other = (ListaServiciosItem) obj;
		if (automatico == null) {
			if (other.automatico != null)
				return false;
		} else if (!automatico.equals(other.automatico))
			return false;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fechabaja == null) {
			if (other.fechabaja != null)
				return false;
		} else if (!fechabaja.equals(other.fechabaja))
			return false;
		if (formapago == null) {
			if (other.formapago != null)
				return false;
		} else if (!formapago.equals(other.formapago))
			return false;
		if (idinstitucion != other.idinstitucion)
			return false;
		if (idservicio != other.idservicio)
			return false;
		if (idserviciosinstitucion != other.idserviciosinstitucion)
			return false;
		if (idtipoiva != other.idtipoiva)
			return false;
		if (idtiposervicios != other.idtiposervicios)
			return false;
		if (iva == null) {
			if (other.iva != null)
				return false;
		} else if (!iva.equals(other.iva))
			return false;
		if (precioivames == null) {
			if (other.precioivames != null)
				return false;
		} else if (!precioivames.equals(other.precioivames))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ListaServiciosItem [idinstitucion=" + idinstitucion + ", idservicio=" + idservicio
				+ ", idtiposervicios=" + idtiposervicios + ", idserviciosinstitucion=" + idserviciosinstitucion
				+ ", descripcion=" + descripcion + ", fechabaja=" + fechabaja + ", automatico=" + automatico
				+ ", idtipoiva=" + idtipoiva + ", categoria=" + categoria + ", tipo=" + tipo + ", iva=" + iva
				+ ", precioivames=" + precioivames + ", formapago=" + formapago + "]";
	}
	
}