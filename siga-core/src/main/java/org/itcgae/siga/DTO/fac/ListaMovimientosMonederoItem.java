package org.itcgae.siga.DTO.fac;

import java.math.BigDecimal;
import java.util.Date;

public class ListaMovimientosMonederoItem {
	
	private Date fecha;
	private String concepto;
	private String cuentaContable;
	private BigDecimal impOp;
	private String impTotal;
	private String liquidacion;
	private String idFactura;
	private Long nLineaFactura;
	private String contabilizado;
	
	//Información del servicio asociado
	private Long idServicio;
	private Long idServiciosInstitucion;
	private Short idTipoServicios;

	private boolean nuevo; //Se utiliza para determinar si es editable o no en la tabla de movimientos de la ficha Monedero

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public BigDecimal getImpOp() {
		return impOp;
	}

	public void setImpOp(BigDecimal impOp) {
		this.impOp = impOp;
	}

	public String getImpTotal() {
		return impTotal;
	}

	public void setImpTotal(String impTotal) {
		this.impTotal = impTotal;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public String getLiquidacion() {
		return liquidacion;
	}

	public void setLiquidacion(String liquidacion) {
		this.liquidacion = liquidacion;
	}

	public String getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(String idFactura) {
		this.idFactura = idFactura;
	}

	public Long getnLineaFactura() {
		return nLineaFactura;
	}

	public void setnLineaFactura(Long nLineaFactura) {
		this.nLineaFactura = nLineaFactura;
	}

	public String getContabilizado() {
		return contabilizado;
	}

	public void setContabilizado(String contabilizado) {
		this.contabilizado = contabilizado;
	}

	public Long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}

	public Long getIdServiciosInstitucion() {
		return idServiciosInstitucion;
	}

	public void setIdServiciosInstitucion(Long idServiciosInstitucion) {
		this.idServiciosInstitucion = idServiciosInstitucion;
	}

	public Short getIdTipoServicios() {
		return idTipoServicios;
	}

	public void setIdTipoServicios(Short idTipoServicios) {
		this.idTipoServicios = idTipoServicios;
	}

}
