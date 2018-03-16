package org.itcgae.siga.db.entities;

import java.util.Date;

public class CenDirecciones extends CenDireccionesKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.FECHAMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Date fechamodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.USUMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Integer usumodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.PREFERENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String preferente;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.DOMICILIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String domicilio;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.CODIGOPOSTAL
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String codigopostal;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.TELEFONO1
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String telefono1;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.TELEFONO2
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String telefono2;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.MOVIL
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String movil;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.FAX1
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String fax1;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.FAX2
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String fax2;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.CORREOELECTRONICO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String correoelectronico;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.PAGINAWEB
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String paginaweb;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.FECHABAJA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Date fechabaja;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.IDPAIS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String idpais;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.IDPROVINCIA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String idprovincia;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.IDPOBLACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String idpoblacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.FECHACARGA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Date fechacarga;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.IDINSTITUCIONALTA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Short idinstitucionalta;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.IDDIRECCIONALTA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Long iddireccionalta;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.POBLACIONEXTRANJERA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String poblacionextranjera;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DIRECCIONES.OTRAPROVINCIA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Short otraprovincia;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.FECHAMODIFICACION
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.FECHAMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.FECHAMODIFICACION
	 * @param fechamodificacion  the value for USCGAE_DESA.CEN_DIRECCIONES.FECHAMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.USUMODIFICACION
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.USUMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Integer getUsumodificacion() {
		return usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.USUMODIFICACION
	 * @param usumodificacion  the value for USCGAE_DESA.CEN_DIRECCIONES.USUMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.PREFERENTE
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.PREFERENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getPreferente() {
		return preferente;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.PREFERENTE
	 * @param preferente  the value for USCGAE_DESA.CEN_DIRECCIONES.PREFERENTE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setPreferente(String preferente) {
		this.preferente = preferente;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.DOMICILIO
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.DOMICILIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getDomicilio() {
		return domicilio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.DOMICILIO
	 * @param domicilio  the value for USCGAE_DESA.CEN_DIRECCIONES.DOMICILIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.CODIGOPOSTAL
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.CODIGOPOSTAL
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getCodigopostal() {
		return codigopostal;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.CODIGOPOSTAL
	 * @param codigopostal  the value for USCGAE_DESA.CEN_DIRECCIONES.CODIGOPOSTAL
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setCodigopostal(String codigopostal) {
		this.codigopostal = codigopostal;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.TELEFONO1
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.TELEFONO1
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getTelefono1() {
		return telefono1;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.TELEFONO1
	 * @param telefono1  the value for USCGAE_DESA.CEN_DIRECCIONES.TELEFONO1
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.TELEFONO2
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.TELEFONO2
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getTelefono2() {
		return telefono2;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.TELEFONO2
	 * @param telefono2  the value for USCGAE_DESA.CEN_DIRECCIONES.TELEFONO2
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.MOVIL
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.MOVIL
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getMovil() {
		return movil;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.MOVIL
	 * @param movil  the value for USCGAE_DESA.CEN_DIRECCIONES.MOVIL
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setMovil(String movil) {
		this.movil = movil;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.FAX1
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.FAX1
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getFax1() {
		return fax1;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.FAX1
	 * @param fax1  the value for USCGAE_DESA.CEN_DIRECCIONES.FAX1
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.FAX2
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.FAX2
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getFax2() {
		return fax2;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.FAX2
	 * @param fax2  the value for USCGAE_DESA.CEN_DIRECCIONES.FAX2
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setFax2(String fax2) {
		this.fax2 = fax2;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.CORREOELECTRONICO
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.CORREOELECTRONICO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getCorreoelectronico() {
		return correoelectronico;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.CORREOELECTRONICO
	 * @param correoelectronico  the value for USCGAE_DESA.CEN_DIRECCIONES.CORREOELECTRONICO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setCorreoelectronico(String correoelectronico) {
		this.correoelectronico = correoelectronico;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.PAGINAWEB
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.PAGINAWEB
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getPaginaweb() {
		return paginaweb;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.PAGINAWEB
	 * @param paginaweb  the value for USCGAE_DESA.CEN_DIRECCIONES.PAGINAWEB
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setPaginaweb(String paginaweb) {
		this.paginaweb = paginaweb;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.FECHABAJA
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.FECHABAJA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Date getFechabaja() {
		return fechabaja;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.FECHABAJA
	 * @param fechabaja  the value for USCGAE_DESA.CEN_DIRECCIONES.FECHABAJA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.IDPAIS
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.IDPAIS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getIdpais() {
		return idpais;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.IDPAIS
	 * @param idpais  the value for USCGAE_DESA.CEN_DIRECCIONES.IDPAIS
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setIdpais(String idpais) {
		this.idpais = idpais;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.IDPROVINCIA
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.IDPROVINCIA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getIdprovincia() {
		return idprovincia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.IDPROVINCIA
	 * @param idprovincia  the value for USCGAE_DESA.CEN_DIRECCIONES.IDPROVINCIA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setIdprovincia(String idprovincia) {
		this.idprovincia = idprovincia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.IDPOBLACION
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.IDPOBLACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getIdpoblacion() {
		return idpoblacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.IDPOBLACION
	 * @param idpoblacion  the value for USCGAE_DESA.CEN_DIRECCIONES.IDPOBLACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setIdpoblacion(String idpoblacion) {
		this.idpoblacion = idpoblacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.FECHACARGA
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.FECHACARGA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Date getFechacarga() {
		return fechacarga;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.FECHACARGA
	 * @param fechacarga  the value for USCGAE_DESA.CEN_DIRECCIONES.FECHACARGA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setFechacarga(Date fechacarga) {
		this.fechacarga = fechacarga;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.IDINSTITUCIONALTA
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.IDINSTITUCIONALTA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Short getIdinstitucionalta() {
		return idinstitucionalta;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.IDINSTITUCIONALTA
	 * @param idinstitucionalta  the value for USCGAE_DESA.CEN_DIRECCIONES.IDINSTITUCIONALTA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setIdinstitucionalta(Short idinstitucionalta) {
		this.idinstitucionalta = idinstitucionalta;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.IDDIRECCIONALTA
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.IDDIRECCIONALTA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Long getIddireccionalta() {
		return iddireccionalta;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.IDDIRECCIONALTA
	 * @param iddireccionalta  the value for USCGAE_DESA.CEN_DIRECCIONES.IDDIRECCIONALTA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setIddireccionalta(Long iddireccionalta) {
		this.iddireccionalta = iddireccionalta;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.POBLACIONEXTRANJERA
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.POBLACIONEXTRANJERA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getPoblacionextranjera() {
		return poblacionextranjera;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.POBLACIONEXTRANJERA
	 * @param poblacionextranjera  the value for USCGAE_DESA.CEN_DIRECCIONES.POBLACIONEXTRANJERA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setPoblacionextranjera(String poblacionextranjera) {
		this.poblacionextranjera = poblacionextranjera;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DIRECCIONES.OTRAPROVINCIA
	 * @return  the value of USCGAE_DESA.CEN_DIRECCIONES.OTRAPROVINCIA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Short getOtraprovincia() {
		return otraprovincia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DIRECCIONES.OTRAPROVINCIA
	 * @param otraprovincia  the value for USCGAE_DESA.CEN_DIRECCIONES.OTRAPROVINCIA
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setOtraprovincia(Short otraprovincia) {
		this.otraprovincia = otraprovincia;
	}
}