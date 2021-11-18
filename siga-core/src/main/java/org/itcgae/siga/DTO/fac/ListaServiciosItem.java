package org.itcgae.siga.DTO.fac;

import java.util.Date;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.type.JdbcType;

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
	private String precioperiodicidad;
	private String formapago;
	private String noFacturable;
	private String idFormasPago;
	private String formasPagoInternet;
	private String solicitarBaja;
	private Date fechaBajaIva;
	private String valorIva;
	
	
	private Double valorminimo;
	private String periodominimo;
	private Double valormaximo;
	private String periodomaximo;
	
	
	public Double getValorminimo() {
		return valorminimo;
	}
	public void setValorminimo(Double valorminimo) {
		this.valorminimo = valorminimo;
	}
	public String getPeriodominimo() {
		return periodominimo;
	}
	public void setPeriodominimo(String periodominimo) {
		this.periodominimo = periodominimo;
	}
	public Double getValormaximo() {
		return valormaximo;
	}
	public void setValormaximo(Double valormaximo) {
		this.valormaximo = valormaximo;
	}
	public String getPeriodomaximo() {
		return periodomaximo;
	}
	public void setPeriodomaximo(String periodomaximo) {
		this.periodomaximo = periodomaximo;
	}
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
	public String getPrecioperiodicidad() {
		return precioperiodicidad;
	}
	public void setPrecioperiodicidad(String precioperiodicidad) {
		this.precioperiodicidad = precioperiodicidad;
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
		result = prime * result + ((periodomaximo == null) ? 0 : periodomaximo.hashCode());
		result = prime * result + ((periodominimo == null) ? 0 : periodominimo.hashCode());
		result = prime * result + ((precioperiodicidad == null) ? 0 : precioperiodicidad.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valormaximo == null) ? 0 : valormaximo.hashCode());
		result = prime * result + ((valorminimo == null) ? 0 : valorminimo.hashCode());
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
		if (periodomaximo == null) {
			if (other.periodomaximo != null)
				return false;
		} else if (!periodomaximo.equals(other.periodomaximo))
			return false;
		if (periodominimo == null) {
			if (other.periodominimo != null)
				return false;
		} else if (!periodominimo.equals(other.periodominimo))
			return false;
		if (precioperiodicidad == null) {
			if (other.precioperiodicidad != null)
				return false;
		} else if (!precioperiodicidad.equals(other.precioperiodicidad))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (valormaximo == null) {
			if (other.valormaximo != null)
				return false;
		} else if (!valormaximo.equals(other.valormaximo))
			return false;
		if (valorminimo == null) {
			if (other.valorminimo != null)
				return false;
		} else if (!valorminimo.equals(other.valorminimo))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ListaServiciosItem [idinstitucion=" + idinstitucion + ", idservicio=" + idservicio
				+ ", idtiposervicios=" + idtiposervicios + ", idserviciosinstitucion=" + idserviciosinstitucion
				+ ", descripcion=" + descripcion + ", fechabaja=" + fechabaja + ", automatico=" + automatico
				+ ", idtipoiva=" + idtipoiva + ", categoria=" + categoria + ", tipo=" + tipo + ", iva=" + iva
				+ ", precioperiodicidad=" + precioperiodicidad + ", formapago=" + formapago + ", valorminimo="
				+ valorminimo + ", periodominimo=" + periodominimo + ", valormaximo=" + valormaximo + ", periodomaximo="
				+ periodomaximo + "]";
	}
	public String getNoFacturable() {
		return noFacturable;
	}
	public void setNoFacturable(String noFacturable) {
		this.noFacturable = noFacturable;
	}
	public String getIdFormasPago() {
		return idFormasPago;
	}
	public void setIdFormasPago(String idFormasPago) {
		this.idFormasPago = idFormasPago;
	}
	public String getFormasPagoInternet() {
		return formasPagoInternet;
	}
	public void setFormasPagoInternet(String formasPagoInternet) {
		this.formasPagoInternet = formasPagoInternet;
	}
	public String getSolicitarBaja() {
		return solicitarBaja;
	}
	public void setSolicitarBaja(String solicitarBaja) {
		this.solicitarBaja = solicitarBaja;
	}
	public Date getFechaBajaIva() {
		return fechaBajaIva;
	}
	public void setFechaBajaIva(Date fechaBajaIva) {
		this.fechaBajaIva = fechaBajaIva;
	}
	public String getValorIva() {
		return valorIva;
	}
	public void setValorIva(String valorIva) {
		this.valorIva = valorIva;
	}
	
}