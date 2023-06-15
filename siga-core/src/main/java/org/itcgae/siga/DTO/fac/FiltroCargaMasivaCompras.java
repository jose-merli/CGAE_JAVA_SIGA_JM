package org.itcgae.siga.DTO.fac;

import java.util.Date;

public class FiltroCargaMasivaCompras {
	
	private Date fechaCargaDesde;
	private Date fechaCargaHasta;
	
	public Date getFechaCargaDesde() {
		return fechaCargaDesde;
	}
	public void setFechaCargaDesde(Date fechaCargaDesde) {
		this.fechaCargaDesde = fechaCargaDesde;
	}
	
	public Date getFechaCargaHasta() {
		return fechaCargaHasta;
	}
	public void setFechaCargaHasta(Date fechaCargaHasta) {
		this.fechaCargaHasta = fechaCargaHasta;
	}

}
