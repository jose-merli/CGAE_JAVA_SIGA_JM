package org.itcgae.siga.DTO.fac;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ListaProductosItem {
	private int idproducto;
	private int idtipoproducto;
	private int idproductoinstitucion; 
	private String idcontador;
	private String descripcion;
	private String valor;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechabaja;
	private String tipo;
	private String categoria;
	private String iva;
	private String precioiva;
	private String formapago;
	private String noFacturable;
	private String idtipoiva;
	private String valorIva;
	private Date fechaBajaIva;
	private String idFormasPago;
	private String formasPagoInternet;
	
	public int getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}
	public int getIdtipoproducto() {
		return idtipoproducto;
	}
	public void setIdtipoproducto(int idtipoproducto) {
		this.idtipoproducto = idtipoproducto;
	}
	public int getIdproductoinstitucion() {
		return idproductoinstitucion;
	}
	public void setIdproductoinstitucion(int idproductoinstitucion) {
		this.idproductoinstitucion = idproductoinstitucion;
	}
	public String getIdcontador() {
		return idcontador;
	}
	public void setIdcontador(String idcontador) {
		this.idcontador = idcontador;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public Date getFechabaja() {
		return fechabaja;
	}
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getIva() {
		return iva;
	}
	public void setIva(String iva) {
		this.iva = iva;
	}
	public String getPrecioiva() {
		return precioiva;
	}
	public void setPrecioiva(String precioiva) {
		this.precioiva = precioiva;
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
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((fechabaja == null) ? 0 : fechabaja.hashCode());
		result = prime * result + ((formapago == null) ? 0 : formapago.hashCode());
		result = prime * result + ((idcontador == null) ? 0 : idcontador.hashCode());
		result = prime * result + idproducto;
		result = prime * result + idproductoinstitucion;
		result = prime * result + idtipoproducto;
		result = prime * result + ((iva == null) ? 0 : iva.hashCode());
		result = prime * result + ((precioiva == null) ? 0 : precioiva.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		ListaProductosItem other = (ListaProductosItem) obj;
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
		if (idcontador == null) {
			if (other.idcontador != null)
				return false;
		} else if (!idcontador.equals(other.idcontador))
			return false;
		if (idproducto != other.idproducto)
			return false;
		if (idproductoinstitucion != other.idproductoinstitucion)
			return false;
		if (idtipoproducto != other.idtipoproducto)
			return false;
		if (iva == null) {
			if (other.iva != null)
				return false;
		} else if (!iva.equals(other.iva))
			return false;
		if (precioiva == null) {
			if (other.precioiva != null)
				return false;
		} else if (!precioiva.equals(other.precioiva))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ListaProductosItem [idproducto=" + idproducto + ", idtipoproducto=" + idtipoproducto
				+ ", idproductoinstitucion=" + idproductoinstitucion + ", idcontador=" + idcontador + ", descripcion="
				+ descripcion + ", valor=" + valor + ", fechabaja=" + fechabaja + ", tipo=" + tipo + ", categoria="
				+ categoria + ", iva=" + iva + ", precioiva=" + precioiva + ", formapago=" + formapago + ", idtipoiva=" 
				+ idtipoiva + ", valorIva=" + valorIva + ", noFacturable=" + noFacturable + "]";
	}
	public String getIdtipoiva() {
		return idtipoiva;
	}
	public void setIdtipoiva(String idtipoiva) {
		this.idtipoiva = idtipoiva;
	}
	public String getValorIva() {
		return valorIva;
	}
	public void setValorIva(String valorIva) {
		this.valorIva = valorIva;
	}
	public String getNoFacturable() {
		return noFacturable;
	}
	public void setNoFacturable(String noFacturable) {
		this.noFacturable = noFacturable;
	}
	public Date getFechaBajaIva() {
		return fechaBajaIva;
	}
	public void setFechaBajaIva(Date fechaBajaIva) {
		this.fechaBajaIva = fechaBajaIva;
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
	
}
