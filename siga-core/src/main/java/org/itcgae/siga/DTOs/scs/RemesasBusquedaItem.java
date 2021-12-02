package org.itcgae.siga.DTOs.scs;

import java.sql.Date;

public class RemesasBusquedaItem {
	
	private int idRemesa;
	private String prefijo;
	private String numero; 
	private String sufijo;
	private String descripcion;
	private int numeroEJG; 
	private int annioEJG;
	private String estado;
	private Date fechaGeneracionDesde;
	private Date fechaEnvioDesde;
	private Date fechaRecepcionDesde;
	private Date fechaGeneracionHasta;
	private Date fechaEnvioHasta;
	private Date fechaRecepcionHasta;
	private boolean ficha;
	private boolean informacionEconomica;
		
	public boolean isInformacionEconomica() {
		return informacionEconomica;
	}

	public void setInformacionEconomica(boolean informacionEconomica) {
		this.informacionEconomica = informacionEconomica;
	}

	public boolean isFicha() {
		return ficha;
	}

	public void setFicha(boolean ficha) {
		this.ficha = ficha;
	}

	public int getIdRemesa() {
		return idRemesa;
	}

	public void setIdRemesa(int idRemesa) {
		this.idRemesa = idRemesa;
	}

	public String getPrefijo() {
		return prefijo;
	}
	
	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getSufijo() {
		return sufijo;
	}
	
	public void setSufijo(String sufijo) {
		this.sufijo = sufijo;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public int getNumeroEJG() {
		return numeroEJG;
	}

	public void setNumeroEJG(int numeroEJG) {
		this.numeroEJG = numeroEJG;
	}

	public int getAnnioEJG() {
		return annioEJG;
	}

	public void setAnnioEJG(int annioEJG) {
		this.annioEJG = annioEJG;
	}

	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaGeneracionDesde() {
		return fechaGeneracionDesde;
	}

	public void setFechaGeneracionDesde(Date fechaGeneracionDesde) {
		this.fechaGeneracionDesde = fechaGeneracionDesde;
	}

	public Date getFechaEnvioDesde() {
		return fechaEnvioDesde;
	}

	public void setFechaEnvioDesde(Date fechaEnvioDesde) {
		this.fechaEnvioDesde = fechaEnvioDesde;
	}

	public Date getFechaRecepcionDesde() {
		return fechaRecepcionDesde;
	}

	public void setFechaRecepcionDesde(Date fechaRecepcionDesde) {
		this.fechaRecepcionDesde = fechaRecepcionDesde;
	}

	public Date getFechaGeneracionHasta() {
		return fechaGeneracionHasta;
	}

	public void setFechaGeneracionHasta(Date fechaGeneracionHasta) {
		this.fechaGeneracionHasta = fechaGeneracionHasta;
	}

	public Date getFechaEnvioHasta() {
		return fechaEnvioHasta;
	}

	public void setFechaEnvioHasta(Date fechaEnvioHasta) {
		this.fechaEnvioHasta = fechaEnvioHasta;
	}

	public Date getFechaRecepcionHasta() {
		return fechaRecepcionHasta;
	}

	public void setFechaRecepcionHasta(Date fechaRecepcionHasta) {
		this.fechaRecepcionHasta = fechaRecepcionHasta;
	}
	
}