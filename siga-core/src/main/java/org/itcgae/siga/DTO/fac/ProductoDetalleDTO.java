package org.itcgae.siga.DTO.fac;


import java.util.Date;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ProductoDetalleDTO {
	private int idtipoproducto;
	private int idproducto;
	private int idproductoinstitucion;
	private String descripcion;
	private float valor;
	private String momentocargo;
	private String solicitarbaja;
	private String solicitaralta;
	private String cuentacontable;
	private int idimpresora;
	private int idplantilla;
	private String tipocertificado;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date fechabaja;
	private String idcontador;
	private String nofacturable;
	private int idtipoiva;
	private String codigoext;
	private String codigo_traspasonav;
	private int orden;
	
	private String categoria;
	private int valoriva;
	private String tipo;
	private Error error = null;
	
	private List<Integer> formasdepagointernet;
	private List<Integer> formasdepagosecretaria;
	private List<Integer> formasdepagointernetoriginales;
	private List<Integer> formasdepagosecretariaoriginales;

	private boolean editar;
	private ProductoDetalleDTO productooriginal;
	
	public int getIdtipoproducto() {
		return idtipoproducto;
	}

	public void setIdtipoproducto(int idtipoproducto) {
		this.idtipoproducto = idtipoproducto;
	}

	public int getIdproducto() {
		return idproducto;
	}

	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}

	public int getIdproductoinstitucion() {
		return idproductoinstitucion;
	}

	public void setIdproductoinstitucion(int idproductoinstitucion) {
		this.idproductoinstitucion = idproductoinstitucion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public String getMomentocargo() {
		return momentocargo;
	}

	public void setMomentocargo(String momentocargo) {
		this.momentocargo = momentocargo;
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

	public String getCuentacontable() {
		return cuentacontable;
	}

	public void setCuentacontable(String cuentacontable) {
		this.cuentacontable = cuentacontable;
	}

	public int getIdimpresora() {
		return idimpresora;
	}

	public void setIdimpresora(int idimpresora) {
		this.idimpresora = idimpresora;
	}

	public int getIdplantilla() {
		return idplantilla;
	}

	public void setIdplantilla(int idplantilla) {
		this.idplantilla = idplantilla;
	}

	public String getTipocertificado() {
		return tipocertificado;
	}

	public void setTipocertificado(String tipocertificado) {
		this.tipocertificado = tipocertificado;
	}

	public Date getFechabaja() {
		return fechabaja;
	}

	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}

	public String getIdcontador() {
		return idcontador;
	}

	public void setIdcontador(String idcontador) {
		this.idcontador = idcontador;
	}

	public String getNofacturable() {
		return nofacturable;
	}

	public void setNofacturable(String nofacturable) {
		this.nofacturable = nofacturable;
	}

	public int getIdtipoiva() {
		return idtipoiva;
	}

	public void setIdtipoiva(int idtipoiva) {
		this.idtipoiva = idtipoiva;
	}

	public String getCodigoext() {
		return codigoext;
	}

	public void setCodigoext(String codigoext) {
		this.codigoext = codigoext;
	}

	public String getCodigo_traspasonav() {
		return codigo_traspasonav;
	}

	public void setCodigo_traspasonav(String codigo_traspasonav) {
		this.codigo_traspasonav = codigo_traspasonav;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
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

	public List<Integer> getFormasdepagointernet() {
		return formasdepagointernet;
	}

	public void setFormasdepagointernet(List<Integer> formasdepagointernet) {
		this.formasdepagointernet = formasdepagointernet;
	}

	public List<Integer> getFormasdepagosecretaria() {
		return formasdepagosecretaria;
	}

	public void setFormasdepagosecretaria(List<Integer> formasdepagosecretaria) {
		this.formasdepagosecretaria = formasdepagosecretaria;
	}

	public List<Integer> getFormasdepagointernetoriginales() {
		return formasdepagointernetoriginales;
	}

	public void setFormasdepagointernetoriginales(List<Integer> formasdepagointernetoriginales) {
		this.formasdepagointernetoriginales = formasdepagointernetoriginales;
	}

	public List<Integer> getFormasdepagosecretariaoriginales() {
		return formasdepagosecretariaoriginales;
	}

	public void setFormasdepagosecretariaoriginales(List<Integer> formasdepagosecretariaoriginales) {
		this.formasdepagosecretariaoriginales = formasdepagosecretariaoriginales;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	public ProductoDetalleDTO getProductooriginal() {
		return productooriginal;
	}

	public void setProductooriginal(ProductoDetalleDTO productooriginal) {
		this.productooriginal = productooriginal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((codigo_traspasonav == null) ? 0 : codigo_traspasonav.hashCode());
		result = prime * result + ((codigoext == null) ? 0 : codigoext.hashCode());
		result = prime * result + ((cuentacontable == null) ? 0 : cuentacontable.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + (editar ? 1231 : 1237);
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + ((fechabaja == null) ? 0 : fechabaja.hashCode());
		result = prime * result + ((formasdepagointernet == null) ? 0 : formasdepagointernet.hashCode());
		result = prime * result
				+ ((formasdepagointernetoriginales == null) ? 0 : formasdepagointernetoriginales.hashCode());
		result = prime * result + ((formasdepagosecretaria == null) ? 0 : formasdepagosecretaria.hashCode());
		result = prime * result
				+ ((formasdepagosecretariaoriginales == null) ? 0 : formasdepagosecretariaoriginales.hashCode());
		result = prime * result + ((idcontador == null) ? 0 : idcontador.hashCode());
		result = prime * result + idimpresora;
		result = prime * result + idplantilla;
		result = prime * result + idproducto;
		result = prime * result + idproductoinstitucion;
		result = prime * result + idtipoiva;
		result = prime * result + idtipoproducto;
		result = prime * result + ((momentocargo == null) ? 0 : momentocargo.hashCode());
		result = prime * result + ((nofacturable == null) ? 0 : nofacturable.hashCode());
		result = prime * result + orden;
		result = prime * result + ((productooriginal == null) ? 0 : productooriginal.hashCode());
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
		if (codigo_traspasonav == null) {
			if (other.codigo_traspasonav != null)
				return false;
		} else if (!codigo_traspasonav.equals(other.codigo_traspasonav))
			return false;
		if (codigoext == null) {
			if (other.codigoext != null)
				return false;
		} else if (!codigoext.equals(other.codigoext))
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
		if (editar != other.editar)
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
		if (formasdepagointernet == null) {
			if (other.formasdepagointernet != null)
				return false;
		} else if (!formasdepagointernet.equals(other.formasdepagointernet))
			return false;
		if (formasdepagointernetoriginales == null) {
			if (other.formasdepagointernetoriginales != null)
				return false;
		} else if (!formasdepagointernetoriginales.equals(other.formasdepagointernetoriginales))
			return false;
		if (formasdepagosecretaria == null) {
			if (other.formasdepagosecretaria != null)
				return false;
		} else if (!formasdepagosecretaria.equals(other.formasdepagosecretaria))
			return false;
		if (formasdepagosecretariaoriginales == null) {
			if (other.formasdepagosecretariaoriginales != null)
				return false;
		} else if (!formasdepagosecretariaoriginales.equals(other.formasdepagosecretariaoriginales))
			return false;
		if (idcontador == null) {
			if (other.idcontador != null)
				return false;
		} else if (!idcontador.equals(other.idcontador))
			return false;
		if (idimpresora != other.idimpresora)
			return false;
		if (idplantilla != other.idplantilla)
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
		if (orden != other.orden)
			return false;
		if (productooriginal == null) {
			if (other.productooriginal != null)
				return false;
		} else if (!productooriginal.equals(other.productooriginal))
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
		return "ProductoDetalleDTO [idtipoproducto=" + idtipoproducto + ", idproducto=" + idproducto
				+ ", idproductoinstitucion=" + idproductoinstitucion + ", descripcion=" + descripcion + ", valor="
				+ valor + ", momentocargo=" + momentocargo + ", solicitarbaja=" + solicitarbaja + ", solicitaralta="
				+ solicitaralta + ", cuentacontable=" + cuentacontable + ", idimpresora=" + idimpresora
				+ ", idplantilla=" + idplantilla + ", tipocertificado=" + tipocertificado + ", fechabaja=" + fechabaja
				+ ", idcontador=" + idcontador + ", nofacturable=" + nofacturable + ", idtipoiva=" + idtipoiva
				+ ", codigoext=" + codigoext + ", codigo_traspasonav=" + codigo_traspasonav + ", orden=" + orden
				+ ", categoria=" + categoria + ", valoriva=" + valoriva + ", tipo=" + tipo + ", error=" + error
				+ ", formasdepagointernet=" + formasdepagointernet + ", formasdepagosecretaria="
				+ formasdepagosecretaria + ", formasdepagointernetoriginales=" + formasdepagointernetoriginales
				+ ", formasdepagosecretariaoriginales=" + formasdepagosecretariaoriginales + ", editar=" + editar
				+ ", productooriginal=" + productooriginal + "]";
	}
	
}
