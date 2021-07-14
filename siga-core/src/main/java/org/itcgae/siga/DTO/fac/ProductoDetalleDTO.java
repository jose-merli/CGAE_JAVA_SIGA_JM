package org.itcgae.siga.DTO.fac;

import java.util.Date;
import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ProductoDetalleDTO {
	private int idproducto;
	private int idtipoproducto;
	private int idproductoinstitucion;
	private String idcontador;
	private String descripcion;
	private String cuentacontable;
	private float valor;
	private int idtipoiva;
	private String momentocargo;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechabaja;
	private String solicitarbaja;
	private String solicitaralta;
	private String tipocertificado;
	private String nofacturable;
	private String categoria;
	private int valoriva;
	private String tipo;
	private Error error = null;
	
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
	public String getCuentacontable() {
		return cuentacontable;
	}
	public void setCuentacontable(String cuentacontable) {
		this.cuentacontable = cuentacontable;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public int getIdtipoiva() {
		return idtipoiva;
	}
	public void setIdtipoiva(int idtipoiva) {
		this.idtipoiva = idtipoiva;
	}
	public String getMomentocargo() {
		return momentocargo;
	}
	public void setMomentocargo(String momentocargo) {
		this.momentocargo = momentocargo;
	}
	public Date getFechabaja() {
		return fechabaja;
	}
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}
	public String getSolicitarbaja() {
		return solicitarbaja;
	}
	public void setSolicitarbaja(String solicitarbaja) {
		this.solicitarbaja = solicitarbaja;
	}
	public String getSolicitaralta() {
		return solicitaralta;
	}
	public void setSolicitaralta(String solicitaralta) {
		this.solicitaralta = solicitaralta;
	}
	public String getTipocertificado() {
		return tipocertificado;
	}
	public void setTipocertificado(String tipocertificado) {
		this.tipocertificado = tipocertificado;
	}
	public String getNofacturable() {
		return nofacturable;
	}
	public void setNofacturable(String nofacturable) {
		this.nofacturable = nofacturable;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public int getValoriva() {
		return valoriva;
	}
	public void setValoriva(int valoriva) {
		this.valoriva = valoriva;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((cuentacontable == null) ? 0 : cuentacontable.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + ((fechabaja == null) ? 0 : fechabaja.hashCode());
		result = prime * result + ((idcontador == null) ? 0 : idcontador.hashCode());
		result = prime * result + idproducto;
		result = prime * result + idproductoinstitucion;
		result = prime * result + idtipoiva;
		result = prime * result + idtipoproducto;
		result = prime * result + ((momentocargo == null) ? 0 : momentocargo.hashCode());
		result = prime * result + ((nofacturable == null) ? 0 : nofacturable.hashCode());
		result = prime * result + ((solicitaralta == null) ? 0 : solicitaralta.hashCode());
		result = prime * result + ((solicitarbaja == null) ? 0 : solicitarbaja.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((tipocertificado == null) ? 0 : tipocertificado.hashCode());
		result = prime * result + Float.floatToIntBits(valor);
		result = prime * result + valoriva;
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
		ProductoDetalleDTO other = (ProductoDetalleDTO) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (cuentacontable == null) {
			if (other.cuentacontable != null)
				return false;
		} else if (!cuentacontable.equals(other.cuentacontable))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (fechabaja == null) {
			if (other.fechabaja != null)
				return false;
		} else if (!fechabaja.equals(other.fechabaja))
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
		if (idtipoiva != other.idtipoiva)
			return false;
		if (idtipoproducto != other.idtipoproducto)
			return false;
		if (momentocargo == null) {
			if (other.momentocargo != null)
				return false;
		} else if (!momentocargo.equals(other.momentocargo))
			return false;
		if (nofacturable == null) {
			if (other.nofacturable != null)
				return false;
		} else if (!nofacturable.equals(other.nofacturable))
			return false;
		if (solicitaralta == null) {
			if (other.solicitaralta != null)
				return false;
		} else if (!solicitaralta.equals(other.solicitaralta))
			return false;
		if (solicitarbaja == null) {
			if (other.solicitarbaja != null)
				return false;
		} else if (!solicitarbaja.equals(other.solicitarbaja))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (tipocertificado == null) {
			if (other.tipocertificado != null)
				return false;
		} else if (!tipocertificado.equals(other.tipocertificado))
			return false;
		if (Float.floatToIntBits(valor) != Float.floatToIntBits(other.valor))
			return false;
		if (valoriva != other.valoriva)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductoDetalleDTO [idproducto=" + idproducto + ", idtipoproducto=" + idtipoproducto
				+ ", idproductoinstitucion=" + idproductoinstitucion + ", idcontador=" + idcontador + ", descripcion="
				+ descripcion + ", cuentacontable=" + cuentacontable + ", valor=" + valor + ", idtipoiva=" + idtipoiva
				+ ", momentocargo=" + momentocargo + ", fechabaja=" + fechabaja + ", solicitarbaja=" + solicitarbaja
				+ ", solicitaralta=" + solicitaralta + ", tipocertificado=" + tipocertificado + ", nofacturable="
				+ nofacturable + ", categoria=" + categoria + ", valoriva=" + valoriva + ", tipo=" + tipo + ", error="
				+ error + "]";
	}
	
}
