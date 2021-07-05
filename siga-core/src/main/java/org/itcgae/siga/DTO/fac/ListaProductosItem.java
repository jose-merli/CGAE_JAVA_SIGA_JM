package org.itcgae.siga.DTO.fac;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ListaProductosItem {
	private int idproducto;
	private int idtipoproducto;
	private String idcontador;
	private String descripcion;
	private int valor;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechabaja;
	private String tipo;
	private String categoria;
	private int iva;
	private int precioiva;
	private String formapago;
	
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
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
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
	public int getIva() {
		return iva;
	}
	public void setIva(int iva) {
		this.iva = iva;
	}
	public int getPrecioiva() {
		return precioiva;
	}
	public void setPrecioiva(int precioiva) {
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
		result = prime * result + idtipoproducto;
		result = prime * result + iva;
		result = prime * result + precioiva;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + valor;
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
		if (idtipoproducto != other.idtipoproducto)
			return false;
		if (iva != other.iva)
			return false;
		if (precioiva != other.precioiva)
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (valor != other.valor)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ListaProductosItem [idproducto=" + idproducto + ", idtipoproducto=" + idtipoproducto + ", idcontador="
				+ idcontador + ", descripcion=" + descripcion + ", valor=" + valor + ", fechabaja=" + fechabaja
				+ ", tipo=" + tipo + ", categoria=" + categoria + ", iva=" + iva + ", precioiva=" + precioiva
				+ ", formapago=" + formapago + "]";
	}
	
}
