package org.itcgae.siga.DTO.fac;

import java.util.Date;

public class CuentasBancariasItem {
	private short id;
	private String nombre;
	private String bancoCodigo;
	private String codBanco;
	private String codSucursal;
	private Date fechaBaja;
	private String IBAN;
	private String descripcion;
	private String sjcs;
	private String comisionImporte;
	private String numUsos;
	private String  numFicheros;

	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getBancoCodigo() {
		return bancoCodigo;
	}
	public void setBancoCodigo(String bancoCodigo) {
		this.bancoCodigo = bancoCodigo;
	}
	public String getCodBanco() {
		return codBanco;
	}
	public void setCodBanco(String codBanco) {
		this.codBanco = codBanco;
	}
	public String getCodSucursal() {
		return codSucursal;
	}
	public void setCodSucursal(String codSucursal) {
		this.codSucursal = codSucursal;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getIBAN() {
		return IBAN;
	}
	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getSjcs() {
		return sjcs;
	}
	public void setSjcs(String sjcs) {
		this.sjcs = sjcs;
	}
	public String getComisionImporte() {
		return comisionImporte;
	}
	public void setComisionImporte(String comisionImporte) {
		this.comisionImporte = comisionImporte;
	}
	public String getNumUsos() {
		return numUsos;
	}
	public void setNumUsos(String numUsos) {
		this.numUsos = numUsos;
	}
	public String getNumFicheros() {
		return numFicheros;
	}
	public void setNumFicheros(String numFicheros) {
		this.numFicheros = numFicheros;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((IBAN == null) ? 0 : IBAN.hashCode());
		result = prime * result + ((bancoCodigo == null) ? 0 : bancoCodigo.hashCode());
		result = prime * result + ((codBanco == null) ? 0 : codBanco.hashCode());
		result = prime * result + ((codSucursal == null) ? 0 : codSucursal.hashCode());
		result = prime * result + ((comisionImporte == null) ? 0 : comisionImporte.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((fechaBaja == null) ? 0 : fechaBaja.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((numFicheros == null) ? 0 : numFicheros.hashCode());
		result = prime * result + ((numUsos == null) ? 0 : numUsos.hashCode());
		result = prime * result + ((sjcs == null) ? 0 : sjcs.hashCode());
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
		CuentasBancariasItem other = (CuentasBancariasItem) obj;
		if (IBAN == null) {
			if (other.IBAN != null)
				return false;
		} else if (!IBAN.equals(other.IBAN))
			return false;
		if (bancoCodigo == null) {
			if (other.bancoCodigo != null)
				return false;
		} else if (!bancoCodigo.equals(other.bancoCodigo))
			return false;
		if (codBanco == null) {
			if (other.codBanco != null)
				return false;
		} else if (!codBanco.equals(other.codBanco))
			return false;
		if (codSucursal == null) {
			if (other.codSucursal != null)
				return false;
		} else if (!codSucursal.equals(other.codSucursal))
			return false;
		if (comisionImporte == null) {
			if (other.comisionImporte != null)
				return false;
		} else if (!comisionImporte.equals(other.comisionImporte))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fechaBaja == null) {
			if (other.fechaBaja != null)
				return false;
		} else if (!fechaBaja.equals(other.fechaBaja))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (numFicheros == null) {
			if (other.numFicheros != null)
				return false;
		} else if (!numFicheros.equals(other.numFicheros))
			return false;
		if (numUsos == null) {
			if (other.numUsos != null)
				return false;
		} else if (!numUsos.equals(other.numUsos))
			return false;
		if (sjcs == null) {
			if (other.sjcs != null)
				return false;
		} else if (!sjcs.equals(other.sjcs))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CuentasBancariasItem [nombre=" + nombre + ", bancoCodigo=" + bancoCodigo + ", codBanco=" + codBanco
				+ ", codSucursal=" + codSucursal + ", fechaBaja=" + fechaBaja + ", IBAN=" + IBAN + ", descripcion="
				+ descripcion + ", sjcs=" + sjcs + ", comisionImporte=" + comisionImporte + ", numUsos=" + numUsos
				+ ", numFicheros=" + numFicheros + "]";
	}
	

	 
}
