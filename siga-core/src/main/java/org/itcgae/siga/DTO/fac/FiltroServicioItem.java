package org.itcgae.siga.DTO.fac;

public class FiltroServicioItem {
	
	private String categoria;
	private String tipo;
	private String servicio;
	private String codigo;
	private String precioDesde;
	private String precioHasta;
	private String iva;
	private String formaPago;
	private String tipoSuscripcion;
	
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
	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getPrecioDesde() {
		return precioDesde;
	}
	public void setPrecioDesde(String precioDesde) {
		this.precioDesde = precioDesde;
	}
	public String getPrecioHasta() {
		return precioHasta;
	}
	public void setPrecioHasta(String precioHasta) {
		this.precioHasta = precioHasta;
	}
	public String getIva() {
		return iva;
	}
	public void setIva(String iva) {
		this.iva = iva;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public String getTipoSuscripcion() {
		return tipoSuscripcion;
	}
	public void setTipoSuscripcion(String tipoSuscripcion) {
		this.tipoSuscripcion = tipoSuscripcion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((formaPago == null) ? 0 : formaPago.hashCode());
		result = prime * result + ((iva == null) ? 0 : iva.hashCode());
		result = prime * result + ((precioDesde == null) ? 0 : precioDesde.hashCode());
		result = prime * result + ((precioHasta == null) ? 0 : precioHasta.hashCode());
		result = prime * result + ((servicio == null) ? 0 : servicio.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((tipoSuscripcion == null) ? 0 : tipoSuscripcion.hashCode());
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
		FiltroServicioItem other = (FiltroServicioItem) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (formaPago == null) {
			if (other.formaPago != null)
				return false;
		} else if (!formaPago.equals(other.formaPago))
			return false;
		if (iva == null) {
			if (other.iva != null)
				return false;
		} else if (!iva.equals(other.iva))
			return false;
		if (precioDesde == null) {
			if (other.precioDesde != null)
				return false;
		} else if (!precioDesde.equals(other.precioDesde))
			return false;
		if (precioHasta == null) {
			if (other.precioHasta != null)
				return false;
		} else if (!precioHasta.equals(other.precioHasta))
			return false;
		if (servicio == null) {
			if (other.servicio != null)
				return false;
		} else if (!servicio.equals(other.servicio))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (tipoSuscripcion == null) {
			if (other.tipoSuscripcion != null)
				return false;
		} else if (!tipoSuscripcion.equals(other.tipoSuscripcion))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "FiltroServicioItem [categoria=" + categoria + ", tipo=" + tipo + ", servicio=" + servicio + ", codigo="
				+ codigo + ", precioDesde=" + precioDesde + ", precioHasta=" + precioHasta + ", iva=" + iva
				+ ", formaPago=" + formaPago + ", tipoSuscripcion=" + tipoSuscripcion + "]";
	}
	
}
