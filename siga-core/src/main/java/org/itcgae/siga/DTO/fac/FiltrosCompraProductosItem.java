package org.itcgae.siga.DTO.fac;

import java.util.Date;

public class FiltrosCompraProductosItem {
		private String idPersona;
		private Date fechaSolicitudDesde;
		private Date fechaSolicitudHasta;
	    private String nSolicitud;
	    private String idCategoria;
	    private String idTipoProducto;
	    private String descProd;
	    private String idEstadoSolicitud;
	    private String idEstadoFactura; //Actualmente estados: En revisión, Pendiente de pago, Pagada, Devuelta, Anulada pendiente de abono, Anulada
		
	    public String getIdpersona() {
			return idPersona;
		}
		public void setIdpersona(String idpersona) {
			this.idPersona = idpersona;
		}
		public Date getFechaSolicitudDesde() {
			return fechaSolicitudDesde;
		}
		public void setFechaSolicitudDesde(Date fechaSolicitudDesde) {
			this.fechaSolicitudDesde = fechaSolicitudDesde;
		}
		public Date getFechaSolicitudHasta() {
			return fechaSolicitudHasta;
		}
		public void setFechaSolicitudHasta(Date fechaSolicitudHasta) {
			this.fechaSolicitudHasta = fechaSolicitudHasta;
		}
		public String getnSolicitud() {
			return nSolicitud;
		}
		public void setnSolicitud(String nSolicitud) {
			this.nSolicitud = nSolicitud;
		}
		public String getIdCategoria() {
			return idCategoria;
		}
		public void setIdCategoria(String idCategoria) {
			this.idCategoria = idCategoria;
		}
		public String getIdTipoProducto() {
			return idTipoProducto;
		}
		public void setIdTipoProducto(String idTipoProducto) {
			this.idTipoProducto = idTipoProducto;
		}
		public String getDescProd() {
			return descProd;
		}
		public void setDescProd(String descProd) {
			this.descProd = descProd;
		}
		public String getIdEstadoSolicitud() {
			return idEstadoSolicitud;
		}
		public void setIdEstadoSolicitud(String idEstadoSolicitud) {
			this.idEstadoSolicitud = idEstadoSolicitud;
		}
		public String getIdEstadoFactura() {
			return idEstadoFactura;
		}
		public void setIdEstadoFactura(String idEstadoFactura) {
			this.idEstadoFactura = idEstadoFactura;
		}
}
