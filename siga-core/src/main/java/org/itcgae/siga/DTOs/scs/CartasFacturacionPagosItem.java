package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartasFacturacionPagosItem {

	private String idInstitucion;
	private String idTurno;
	private String idFacturacion;
	private String idConcepto;
	private Date fechaDesde;
	private Date fechaHasta;
	private String nombreFac;
	private String nombreCol;
	private String ncolegiado;
	private String regularizacion;
	private String desEstado;
	private String idEstado;
	private Date fechaEstado;
	private String importeTotal;
	private String importeGuardia;
	private String importeOficio;
	private String importeEjg;
	private String importeSoj;
	private String idPartidaPresupuestaria;
	private String importePendiente;
	private String prevision;
	private String visible;
	private String idPersona;
	private String nombrePago;
	private String nombreDest;
	private String idPago;
	private String importeTotalMovimientos;
	private String importeTotalRetenciones;
	private String totalImportesjcs;
	private String totalImporteBruto;
	private String totalImporteIrpf;
	private String formaDePago;
	
	@JsonProperty("visible")
	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	@JsonProperty("prevision")
	public String getPrevision() {
		return prevision;
	}

	public void setPrevision(String prevision) {
		this.prevision = prevision;
	}
	
	@JsonProperty("importePendiente")
	public String getImportePendiente() {
		return importePendiente;
	}

	public void setImportePendiente(String importePendiente) {
		this.importePendiente = importePendiente;
	}

	@JsonProperty("idPartidaPresupuestaria")
	public String getIdPartidaPresupuestaria() {
		return idPartidaPresupuestaria;
	}

	public void setIdPartidaPresupuestaria(String idPartidaPresupuestaria) {
		this.idPartidaPresupuestaria = idPartidaPresupuestaria;
	}

	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}
	
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
	@JsonProperty("idTurno")
	public String getIdTurno() {
		return idTurno;
	}
	
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	
	@JsonProperty("idFacturacion")
	public String getIdFacturacion() {
		return idFacturacion;
	}
	
	public void setIdFacturacion(String idFacturacion) {
		this.idFacturacion = idFacturacion;
	}
	
	@JsonProperty("idConcepto")
	public String getIdConcepto() {
		return idConcepto;
	}
	
	public void setIdConcepto(String idConcepto) {
		this.idConcepto = idConcepto;
	}
	
	@JsonProperty("fechaDesde")
	public Date getFechaDesde() {
		return fechaDesde;
	}
	
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	
	@JsonProperty("fechaHasta")
	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	
	@JsonProperty("nombreFac")
	public String getNombreFac() {
		return nombreFac;
	}
	
	public void setNombreFac(String nombreFac) {
		this.nombreFac = nombreFac;
	}
	
	@JsonProperty("nombreCol")
	public String getNombreCol() {
		return nombreCol;
	}
	
	public void setNombreCol(String nombreCol) {
		this.nombreCol = nombreCol;
	}
	
	@JsonProperty("ncolegiado")
	public String getNcolegiado() {
		return ncolegiado;
	}
	
	public void setNcolegiado(String ncolegiado) {
		this.ncolegiado = ncolegiado;
	}
	
	@JsonProperty("regularizacion")
	public String getRegularizacion() {
		return regularizacion;
	}
	
	public void setRegularizacion(String regularizacion) {
		this.regularizacion = regularizacion;
	}
	
	@JsonProperty("desEstado")
	public String getDesEstado() {
		return desEstado;
	}
	
	public void setDesEstado(String desEstado) {
		this.desEstado = desEstado;
	}
	
	@JsonProperty("idEstado")
	public String getIdEstado() {
		return idEstado;
	}
	
	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}
	
	@JsonProperty("fechaEstado")
	public Date getFechaEstado() {
		return fechaEstado;
	}
	
	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}
	
	@JsonProperty("importeTotal")
	public String getImporteTotal() {
		return importeTotal;
	}
	
	public void setImporteTotal(String importeTotal) {
		this.importeTotal = importeTotal;
	}
	
	@JsonProperty("importeGuardia")
	public String getImporteGuardia() {
		return importeGuardia;
	}

	public void setImporteGuardia(String importeGuardia) {
		this.importeGuardia = importeGuardia;
	}

	@JsonProperty("importeOficio")
	public String getImporteOficio() {
		return importeOficio;
	}

	public void setImporteOficio(String importeOficio) {
		this.importeOficio = importeOficio;
	}

	@JsonProperty("importeEjg")
	public String getImporteEjg() {
		return importeEjg;
	}

	public void setImporteEjg(String importeEjg) {
		this.importeEjg = importeEjg;
	}

	@JsonProperty("importeSoj")
	public String getImporteSoj() {
		return importeSoj;
	}

	public void setImporteSoj(String importeSoj) {
		this.importeSoj = importeSoj;
	}

	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	@JsonProperty("nombrePago")
	public String getNombrePago() {
		return nombrePago;
	}

	public void setNombrePago(String nombrePago) {
		this.nombrePago = nombrePago;
	}

	@JsonProperty("nombreDest")
	public String getNombreDest() {
		return nombreDest;
	}

	public void setNombreDest(String nombreDest) {
		this.nombreDest = nombreDest;
	}

	@JsonProperty("idPago")
	public String getIdPago() {
		return idPago;
	}

	public void setIdPago(String idPago) {
		this.idPago = idPago;
	}

	@JsonProperty("importeTotalMovimientos")
	public String getImporteTotalMovimientos() {
		return importeTotalMovimientos;
	}

	public void setImporteTotalMovimientos(String importeTotalMovimientos) {
		this.importeTotalMovimientos = importeTotalMovimientos;
	}

	@JsonProperty("importeTotalRetenciones")
	public String getImporteTotalRetenciones() {
		return importeTotalRetenciones;
	}

	public void setImporteTotalRetenciones(String importeTotalRetenciones) {
		this.importeTotalRetenciones = importeTotalRetenciones;
	}

	@JsonProperty("totalImportesjcs")
	public String getTotalImportesjcs() {
		return totalImportesjcs;
	}

	public void setTotalImportesjcs(String totalImportesjcs) {
		this.totalImportesjcs = totalImportesjcs;
	}

	@JsonProperty("totalImporteBruto")
	public String getTotalImporteBruto() {
		return totalImporteBruto;
	}

	public void setTotalImporteBruto(String totalImporteBruto) {
		this.totalImporteBruto = totalImporteBruto;
	}

	@JsonProperty("totalImporteIrpf")
	public String getTotalImporteIrpf() {
		return totalImporteIrpf;
	}

	public void setTotalImporteIrpf(String totalImporteIrpf) {
		this.totalImporteIrpf = totalImporteIrpf;
	}

	@JsonProperty("formaDePago")
	public String getFormaDePago() {
		return formaDePago;
	}

	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desEstado == null) ? 0 : desEstado.hashCode());
		result = prime * result + ((fechaDesde == null) ? 0 : fechaDesde.hashCode());
		result = prime * result + ((fechaEstado == null) ? 0 : fechaEstado.hashCode());
		result = prime * result + ((fechaHasta == null) ? 0 : fechaHasta.hashCode());
		result = prime * result + ((formaDePago == null) ? 0 : formaDePago.hashCode());
		result = prime * result + ((idConcepto == null) ? 0 : idConcepto.hashCode());
		result = prime * result + ((idEstado == null) ? 0 : idEstado.hashCode());
		result = prime * result + ((idFacturacion == null) ? 0 : idFacturacion.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idPago == null) ? 0 : idPago.hashCode());
		result = prime * result + ((idPartidaPresupuestaria == null) ? 0 : idPartidaPresupuestaria.hashCode());
		result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
		result = prime * result + ((idTurno == null) ? 0 : idTurno.hashCode());
		result = prime * result + ((importeEjg == null) ? 0 : importeEjg.hashCode());
		result = prime * result + ((importeGuardia == null) ? 0 : importeGuardia.hashCode());
		result = prime * result + ((importeOficio == null) ? 0 : importeOficio.hashCode());
		result = prime * result + ((importePendiente == null) ? 0 : importePendiente.hashCode());
		result = prime * result + ((importeSoj == null) ? 0 : importeSoj.hashCode());
		result = prime * result + ((importeTotal == null) ? 0 : importeTotal.hashCode());
		result = prime * result + ((importeTotalMovimientos == null) ? 0 : importeTotalMovimientos.hashCode());
		result = prime * result + ((importeTotalRetenciones == null) ? 0 : importeTotalRetenciones.hashCode());
		result = prime * result + ((ncolegiado == null) ? 0 : ncolegiado.hashCode());
		result = prime * result + ((nombreCol == null) ? 0 : nombreCol.hashCode());
		result = prime * result + ((nombreDest == null) ? 0 : nombreDest.hashCode());
		result = prime * result + ((nombreFac == null) ? 0 : nombreFac.hashCode());
		result = prime * result + ((nombrePago == null) ? 0 : nombrePago.hashCode());
		result = prime * result + ((prevision == null) ? 0 : prevision.hashCode());
		result = prime * result + ((regularizacion == null) ? 0 : regularizacion.hashCode());
		result = prime * result + ((totalImporteBruto == null) ? 0 : totalImporteBruto.hashCode());
		result = prime * result + ((totalImporteIrpf == null) ? 0 : totalImporteIrpf.hashCode());
		result = prime * result + ((totalImportesjcs == null) ? 0 : totalImportesjcs.hashCode());
		result = prime * result + ((visible == null) ? 0 : visible.hashCode());
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
		CartasFacturacionPagosItem other = (CartasFacturacionPagosItem) obj;
		if (desEstado == null) {
			if (other.desEstado != null)
				return false;
		} else if (!desEstado.equals(other.desEstado))
			return false;
		if (fechaDesde == null) {
			if (other.fechaDesde != null)
				return false;
		} else if (!fechaDesde.equals(other.fechaDesde))
			return false;
		if (fechaEstado == null) {
			if (other.fechaEstado != null)
				return false;
		} else if (!fechaEstado.equals(other.fechaEstado))
			return false;
		if (fechaHasta == null) {
			if (other.fechaHasta != null)
				return false;
		} else if (!fechaHasta.equals(other.fechaHasta))
			return false;
		if (formaDePago == null) {
			if (other.formaDePago != null)
				return false;
		} else if (!formaDePago.equals(other.formaDePago))
			return false;
		if (idConcepto == null) {
			if (other.idConcepto != null)
				return false;
		} else if (!idConcepto.equals(other.idConcepto))
			return false;
		if (idEstado == null) {
			if (other.idEstado != null)
				return false;
		} else if (!idEstado.equals(other.idEstado))
			return false;
		if (idFacturacion == null) {
			if (other.idFacturacion != null)
				return false;
		} else if (!idFacturacion.equals(other.idFacturacion))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (idPago == null) {
			if (other.idPago != null)
				return false;
		} else if (!idPago.equals(other.idPago))
			return false;
		if (idPartidaPresupuestaria == null) {
			if (other.idPartidaPresupuestaria != null)
				return false;
		} else if (!idPartidaPresupuestaria.equals(other.idPartidaPresupuestaria))
			return false;
		if (idPersona == null) {
			if (other.idPersona != null)
				return false;
		} else if (!idPersona.equals(other.idPersona))
			return false;
		if (idTurno == null) {
			if (other.idTurno != null)
				return false;
		} else if (!idTurno.equals(other.idTurno))
			return false;
		if (importeEjg == null) {
			if (other.importeEjg != null)
				return false;
		} else if (!importeEjg.equals(other.importeEjg))
			return false;
		if (importeGuardia == null) {
			if (other.importeGuardia != null)
				return false;
		} else if (!importeGuardia.equals(other.importeGuardia))
			return false;
		if (importeOficio == null) {
			if (other.importeOficio != null)
				return false;
		} else if (!importeOficio.equals(other.importeOficio))
			return false;
		if (importePendiente == null) {
			if (other.importePendiente != null)
				return false;
		} else if (!importePendiente.equals(other.importePendiente))
			return false;
		if (importeSoj == null) {
			if (other.importeSoj != null)
				return false;
		} else if (!importeSoj.equals(other.importeSoj))
			return false;
		if (importeTotal == null) {
			if (other.importeTotal != null)
				return false;
		} else if (!importeTotal.equals(other.importeTotal))
			return false;
		if (importeTotalMovimientos == null) {
			if (other.importeTotalMovimientos != null)
				return false;
		} else if (!importeTotalMovimientos.equals(other.importeTotalMovimientos))
			return false;
		if (importeTotalRetenciones == null) {
			if (other.importeTotalRetenciones != null)
				return false;
		} else if (!importeTotalRetenciones.equals(other.importeTotalRetenciones))
			return false;
		if (ncolegiado == null) {
			if (other.ncolegiado != null)
				return false;
		} else if (!ncolegiado.equals(other.ncolegiado))
			return false;
		if (nombreCol == null) {
			if (other.nombreCol != null)
				return false;
		} else if (!nombreCol.equals(other.nombreCol))
			return false;
		if (nombreDest == null) {
			if (other.nombreDest != null)
				return false;
		} else if (!nombreDest.equals(other.nombreDest))
			return false;
		if (nombreFac == null) {
			if (other.nombreFac != null)
				return false;
		} else if (!nombreFac.equals(other.nombreFac))
			return false;
		if (nombrePago == null) {
			if (other.nombrePago != null)
				return false;
		} else if (!nombrePago.equals(other.nombrePago))
			return false;
		if (prevision == null) {
			if (other.prevision != null)
				return false;
		} else if (!prevision.equals(other.prevision))
			return false;
		if (regularizacion == null) {
			if (other.regularizacion != null)
				return false;
		} else if (!regularizacion.equals(other.regularizacion))
			return false;
		if (totalImporteBruto == null) {
			if (other.totalImporteBruto != null)
				return false;
		} else if (!totalImporteBruto.equals(other.totalImporteBruto))
			return false;
		if (totalImporteIrpf == null) {
			if (other.totalImporteIrpf != null)
				return false;
		} else if (!totalImporteIrpf.equals(other.totalImporteIrpf))
			return false;
		if (totalImportesjcs == null) {
			if (other.totalImportesjcs != null)
				return false;
		} else if (!totalImportesjcs.equals(other.totalImportesjcs))
			return false;
		if (visible == null) {
			if (other.visible != null)
				return false;
		} else if (!visible.equals(other.visible))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CartasFacturacionPagosItem [idInstitucion=" + idInstitucion + ", idTurno=" + idTurno
				+ ", idFacturacion=" + idFacturacion + ", idConcepto=" + idConcepto + ", fechaDesde=" + fechaDesde
				+ ", fechaHasta=" + fechaHasta + ", nombreFac=" + nombreFac + ", nombreCol=" + nombreCol
				+ ", ncolegiado=" + ncolegiado + ", regularizacion=" + regularizacion + ", desEstado=" + desEstado
				+ ", idEstado=" + idEstado + ", fechaEstado=" + fechaEstado + ", importeTotal=" + importeTotal
				+ ", importeGuardia=" + importeGuardia + ", importeOficio=" + importeOficio + ", importeEjg="
				+ importeEjg + ", importeSoj=" + importeSoj + ", idPartidaPresupuestaria=" + idPartidaPresupuestaria
				+ ", importePendiente=" + importePendiente + ", prevision=" + prevision + ", visible=" + visible
				+ ", idPersona=" + idPersona + ", nombrePago=" + nombrePago + ", nombreDest=" + nombreDest + ", idPago="
				+ idPago + ", importeTotalMovimientos=" + importeTotalMovimientos + ", importeTotalRetenciones="
				+ importeTotalRetenciones + ", totalImportesjcs=" + totalImportesjcs + ", totalImporteBruto="
				+ totalImporteBruto + ", totalImporteIrpf=" + totalImporteIrpf + ", formaDePago=" + formaDePago + "]";
	}

	
	
	
	
}
