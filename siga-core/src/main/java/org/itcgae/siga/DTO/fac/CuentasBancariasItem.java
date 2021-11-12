package org.itcgae.siga.DTO.fac;

import java.util.Date;
import java.util.Objects;

public class CuentasBancariasItem {

	private String bancosCodigo;
	private String codBanco;
	private String codSucursal;
	private Date fechaBaja;

	private String IBAN;
	private String nombre;
	private String descripcion;
	private String asientoContable;
	private String cuentaContableTarjeta;
	private String BIC;
	private String numUsos;
	private String numFicheros;

	private String comisionImporte;
	private String comisionDescripcion;
	private String idTipoIVA;

	private String configFicherosSecuencia;
	private String configFicherosEsquema;
	private String configLugaresQueMasSecuencia;
	private String configConceptoAmpliado;

	private Boolean sjcs;
	private String idSufijoSjcs;
	private String concepto;

	public String getBancosCodigo() {
		return bancosCodigo;
	}

	public void setBancosCodigo(String bancosCodigo) {
		this.bancosCodigo = bancosCodigo;
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

	public void setIBAN(String IBAN) {
		this.IBAN = IBAN;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAsientoContable() {
		return asientoContable;
	}

	public void setAsientoContable(String asientoContable) {
		this.asientoContable = asientoContable;
	}

	public String getCuentaContableTarjeta() {
		return cuentaContableTarjeta;
	}

	public void setCuentaContableTarjeta(String cuentaContableTarjeta) {
		this.cuentaContableTarjeta = cuentaContableTarjeta;
	}

	public String getBIC() {
		return BIC;
	}

	public void setBIC(String BIC) {
		this.BIC = BIC;
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

	public String getComisionImporte() {
		return comisionImporte;
	}

	public void setComisionImporte(String comisionImporte) {
		this.comisionImporte = comisionImporte;
	}

	public String getComisionDescripcion() {
		return comisionDescripcion;
	}

	public void setComisionDescripcion(String comisionDescripcion) {
		this.comisionDescripcion = comisionDescripcion;
	}

	public String getIdTipoIVA() {
		return idTipoIVA;
	}

	public void setIdTipoIVA(String idTipoIVA) {
		this.idTipoIVA = idTipoIVA;
	}

	public String getConfigFicherosSecuencia() {
		return configFicherosSecuencia;
	}

	public void setConfigFicherosSecuencia(String configFicherosSecuencia) {
		this.configFicherosSecuencia = configFicherosSecuencia;
	}

	public String getIdSufijoSjcs() {
		return idSufijoSjcs;
	}

	public void setIdSufijoSjcs(String idSufijoSjcs) {
		this.idSufijoSjcs = idSufijoSjcs;
	}

	public String getConfigFicherosEsquema() {
		return configFicherosEsquema;
	}

	public void setConfigFicherosEsquema(String configFicherosEsquema) {
		this.configFicherosEsquema = configFicherosEsquema;
	}

	public String getConfigLugaresQueMasSecuencia() {
		return configLugaresQueMasSecuencia;
	}

	public void setConfigLugaresQueMasSecuencia(String configLugaresQueMasSecuencia) {
		this.configLugaresQueMasSecuencia = configLugaresQueMasSecuencia;
	}

	public String getConfigConceptoAmpliado() {
		return configConceptoAmpliado;
	}

	public void setConfigConceptoAmpliado(String configConceptoAmpliado) {
		this.configConceptoAmpliado = configConceptoAmpliado;
	}

	public Boolean getSjcs() {
		return sjcs;
	}

	public void setSjcs(Boolean sjcs) {
		this.sjcs = sjcs;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CuentasBancariasItem that = (CuentasBancariasItem) o;
		return Objects.equals(bancosCodigo, that.bancosCodigo) &&
				Objects.equals(codBanco, that.codBanco) &&
				Objects.equals(codSucursal, that.codSucursal) &&
				Objects.equals(fechaBaja, that.fechaBaja) &&
				Objects.equals(IBAN, that.IBAN) &&
				Objects.equals(nombre, that.nombre) &&
				Objects.equals(descripcion, that.descripcion) &&
				Objects.equals(asientoContable, that.asientoContable) &&
				Objects.equals(cuentaContableTarjeta, that.cuentaContableTarjeta) &&
				Objects.equals(BIC, that.BIC) &&
				Objects.equals(numUsos, that.numUsos) &&
				Objects.equals(numFicheros, that.numFicheros) &&
				Objects.equals(comisionImporte, that.comisionImporte) &&
				Objects.equals(comisionDescripcion, that.comisionDescripcion) &&
				Objects.equals(idTipoIVA, that.idTipoIVA) &&
				Objects.equals(configFicherosSecuencia, that.configFicherosSecuencia) &&
				Objects.equals(configFicherosEsquema, that.configFicherosEsquema) &&
				Objects.equals(configLugaresQueMasSecuencia, that.configLugaresQueMasSecuencia) &&
				Objects.equals(configConceptoAmpliado, that.configConceptoAmpliado) &&
				Objects.equals(sjcs, that.sjcs) &&
				Objects.equals(idSufijoSjcs, that.idSufijoSjcs) &&
				Objects.equals(concepto, that.concepto);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bancosCodigo, codBanco, codSucursal, fechaBaja, IBAN, nombre, descripcion, asientoContable, cuentaContableTarjeta, BIC, numUsos, numFicheros, comisionImporte, comisionDescripcion, idTipoIVA, configFicherosSecuencia, configFicherosEsquema, configLugaresQueMasSecuencia, configConceptoAmpliado, sjcs, idSufijoSjcs, concepto);
	}

	@Override
	public String toString() {
		return "CuentasBancariasItem{" +
				"bancosCodigo='" + bancosCodigo + '\'' +
				", codBanco='" + codBanco + '\'' +
				", codSucursal='" + codSucursal + '\'' +
				", fechaBaja=" + fechaBaja +
				", IBAN='" + IBAN + '\'' +
				", nombre='" + nombre + '\'' +
				", descripcion='" + descripcion + '\'' +
				", asientoContable='" + asientoContable + '\'' +
				", cuentaContableTarjeta='" + cuentaContableTarjeta + '\'' +
				", BIC='" + BIC + '\'' +
				", numUsos='" + numUsos + '\'' +
				", numFicheros='" + numFicheros + '\'' +
				", comisionImporte='" + comisionImporte + '\'' +
				", comisionDescripcion='" + comisionDescripcion + '\'' +
				", idTipoIVA='" + idTipoIVA + '\'' +
				", configFicherosSecuencia='" + configFicherosSecuencia + '\'' +
				", configFicherosEsquema='" + configFicherosEsquema + '\'' +
				", configLugaresQueMasSecuencia='" + configLugaresQueMasSecuencia + '\'' +
				", configConceptoAmpliado='" + configConceptoAmpliado + '\'' +
				", sjcs='" + sjcs + '\'' +
				", idSufijoSjcs='" + idSufijoSjcs + '\'' +
				", concepto='" + concepto + '\'' +
				'}';
	}
}
