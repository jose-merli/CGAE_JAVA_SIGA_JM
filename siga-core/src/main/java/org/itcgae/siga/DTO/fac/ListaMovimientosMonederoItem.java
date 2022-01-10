package org.itcgae.siga.DTO.fac;

import java.math.BigDecimal;
import java.util.Date;

public class ListaMovimientosMonederoItem {
	
	private Date fecha;
	private String concepto;
	private String cuentaContable;
	private BigDecimal impOp;
	private String impTotal;
	private Short idLinea;

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

	public Short getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Short idLinea) {
		this.idLinea = idLinea;
	}

}
