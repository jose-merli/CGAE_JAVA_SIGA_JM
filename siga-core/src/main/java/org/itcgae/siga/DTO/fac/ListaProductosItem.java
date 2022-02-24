package org.itcgae.siga.DTO.fac;

import java.util.Date;
import java.util.Objects;

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
	private String solicitarAlta;
	private String solicitarBaja;
	
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

	public String getSolicitarAlta() {
		return solicitarAlta;
	}

	public void setSolicitarAlta(String solicitarAlta) {
		this.solicitarAlta = solicitarAlta;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ListaProductosItem that = (ListaProductosItem) o;
		return idproducto == that.idproducto &&
				idtipoproducto == that.idtipoproducto &&
				idproductoinstitucion == that.idproductoinstitucion &&
				Objects.equals(idcontador, that.idcontador) &&
				Objects.equals(descripcion, that.descripcion) &&
				Objects.equals(valor, that.valor) &&
				Objects.equals(fechabaja, that.fechabaja) &&
				Objects.equals(tipo, that.tipo) &&
				Objects.equals(categoria, that.categoria) &&
				Objects.equals(iva, that.iva) &&
				Objects.equals(precioiva, that.precioiva) &&
				Objects.equals(formapago, that.formapago) &&
				Objects.equals(noFacturable, that.noFacturable) &&
				Objects.equals(idtipoiva, that.idtipoiva) &&
				Objects.equals(valorIva, that.valorIva) &&
				Objects.equals(fechaBajaIva, that.fechaBajaIva) &&
				Objects.equals(idFormasPago, that.idFormasPago) &&
				Objects.equals(formasPagoInternet, that.formasPagoInternet) &&
				Objects.equals(solicitarAlta, that.solicitarAlta) &&
				Objects.equals(solicitarBaja, that.solicitarBaja);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idproducto, idtipoproducto, idproductoinstitucion, idcontador, descripcion, valor, fechabaja, tipo, categoria, iva, precioiva, formapago, noFacturable, idtipoiva, valorIva, fechaBajaIva, idFormasPago, formasPagoInternet, solicitarAlta, solicitarBaja);
	}

	@Override
	public String toString() {
		return "ListaProductosItem{" +
				"idproducto=" + idproducto +
				", idtipoproducto=" + idtipoproducto +
				", idproductoinstitucion=" + idproductoinstitucion +
				", idcontador='" + idcontador + '\'' +
				", descripcion='" + descripcion + '\'' +
				", valor='" + valor + '\'' +
				", fechabaja=" + fechabaja +
				", tipo='" + tipo + '\'' +
				", categoria='" + categoria + '\'' +
				", iva='" + iva + '\'' +
				", precioiva='" + precioiva + '\'' +
				", formapago='" + formapago + '\'' +
				", noFacturable='" + noFacturable + '\'' +
				", idtipoiva='" + idtipoiva + '\'' +
				", valorIva='" + valorIva + '\'' +
				", fechaBajaIva=" + fechaBajaIva +
				", idFormasPago='" + idFormasPago + '\'' +
				", formasPagoInternet='" + formasPagoInternet + '\'' +
				", solicitarAlta='" + solicitarAlta + '\'' +
				", solicitarBaja='" + solicitarBaja + '\'' +
				'}';
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
	public String getSolicitarBaja() {
		return solicitarBaja;
	}
	public void setSolicitarBaja(String solicitarBaja) {
		this.solicitarBaja = solicitarBaja;
	}
	
}
