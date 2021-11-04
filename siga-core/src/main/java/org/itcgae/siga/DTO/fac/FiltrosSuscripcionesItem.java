package org.itcgae.siga.DTO.fac;

import java.util.Date;

public class FiltrosSuscripcionesItem {

	private String idPersona;
	private Date fechaSolicitudDesde;
	private Date fechaSolicitudHasta;
	private Date aFechaDe;
    private String nSolicitud;
    private String idCategoria;
    private String idTipoServicio;
    private String descServ;
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
	public String getIdTipoServicio() {
		return idTipoServicio;
	}
	public void setIdTipoServicio(String idTipoServicio) {
		this.idTipoServicio = idTipoServicio;
	}
	public String getDescServ() {
		return descServ;
	}
	public void setDescServ(String descServ) {
		this.descServ = descServ;
	}
	public Date getaFechaDe() {
		return aFechaDe;
	}
	public void setaFechaDe(Date aFechaDe) {
		this.aFechaDe = aFechaDe;
	}
}
