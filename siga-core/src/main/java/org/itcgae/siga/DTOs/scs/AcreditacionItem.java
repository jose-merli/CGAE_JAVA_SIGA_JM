package org.itcgae.siga.DTOs.scs;


import com.fasterxml.jackson.annotation.JsonProperty;

public class AcreditacionItem {

	private String idInstitucion;
	private String codSubTarifa;
	private String porcentaje;
	private String idAcreditacion;
	private String descripcion;
	private String codigoext;
	private String nig_numprocedimiento;
	private String idprocedimiento;
private boolean nigProcedimiento;
	
	public String getCodSubTarifa() {
		return codSubTarifa;
	}

	public void setCodSubTarifa(String codSubTarifa) {
		this.codSubTarifa = codSubTarifa;
	}

	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getIdAcreditacion() {
		return idAcreditacion;
	}

	public void setIdAcreditacion(String idAcreditacion) {
		this.idAcreditacion = idAcreditacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getCodigoext() {
		return codigoext;
	}

	public void setCodigoext(String codigoext) {
		this.codigoext = codigoext;
	}


	/**
	 * 
	 **/
	public AcreditacionItem idInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
		return this;
	}

	@JsonProperty("idInstitucion")
	public String getidInstitucion() {
		return idInstitucion;
	}

	public void setidInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getNig_numprocedimiento() {
		return nig_numprocedimiento;
	}

	public void setNig_numprocedimiento(String nig_numprocedimiento) {
		this.nig_numprocedimiento = nig_numprocedimiento;
	}

	public String getIdprocedimiento() {
		return idprocedimiento;
	}

	public void setIdprocedimiento(String idprocedimiento) {
		this.idprocedimiento = idprocedimiento;
	}

	public boolean isNigProcedimiento() {
		return nigProcedimiento;
	}

	public void setNigProcedimiento(boolean nigProcedimiento) {
		this.nigProcedimiento = nigProcedimiento;
	}
	
	


	/**
	 * 
	 **/

}
