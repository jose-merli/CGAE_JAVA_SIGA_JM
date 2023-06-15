package org.itcgae.siga.DTO.fac;

import java.util.Date;
import java.util.List;

public class FiltrosCompraProductosItem {
		private String idPersona;
		private Date fechaSolicitudDesde;
		private Date fechaSolicitudHasta;
		private Date fechaEfectiva;
	    private String nSolicitud;
	    private List<String> idCategoria;
	    private List<String> idTipoProducto;
	    private String descProd;
	    private List<String> idEstadoSolicitud;
	    private List<String> idEstadoFactura; //Actualmente estados: En revisión, Pendiente de pago, Pagada, Devuelta, Anulada pendiente de abono, Anulada
		
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
		public List<String> getIdCategoria() {
			return idCategoria;
		}
		public void setIdCategoria(List<String> idCategoria) {
			this.idCategoria = idCategoria;
		}
		public List<String> getIdTipoProducto() {
			return idTipoProducto;
		}
		public void setIdTipoProducto(List<String> idTipoProducto) {
			this.idTipoProducto = idTipoProducto;
		}
		public String getDescProd() {
			return descProd;
		}
		public void setDescProd(String descProd) {
			this.descProd = descProd;
		}
		public List<String> getIdEstadoSolicitud() {
			return idEstadoSolicitud;
		}
		public void setIdEstadoSolicitud(List<String> idEstadoSolicitud) {
			this.idEstadoSolicitud = idEstadoSolicitud;
		}
		public List<String> getIdEstadoFactura() {
			return idEstadoFactura;
		}
		public void setIdEstadoFactura(List<String> idEstadoFactura) {
			this.idEstadoFactura = idEstadoFactura;
		}
		public Date getFechaEfectiva() {
			return fechaEfectiva;
		}
		public void setFechaEfectiva(Date fechaEfectiva) {
			this.fechaEfectiva = fechaEfectiva;
		}
}
