package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class CenPersonaItem {

	private Long idpersona;
	
	private String nombre;
	
	private String apellidos1;
	
	private String apellidos2;
	
	private String nifcif;
	
	private Date fechamodificacion;
	
	private Integer usumodificacion;
	
	private Short idtipoidentificacion;
	
	private Date fechanacimiento;

	private Short idestadocivil;

	private String naturalde;

	private String fallecido;

	private String sexo;

	/**
	 * @return the idpersona
	 */
	public Long getIdpersona() {
		return idpersona;
	}

	/**
	 * @param idpersona the idpersona to set
	 */
	public void setIdpersona(Long idpersona) {
		this.idpersona = idpersona;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellidos1
	 */
	public String getApellidos1() {
		return apellidos1;
	}

	/**
	 * @param apellidos1 the apellidos1 to set
	 */
	public void setApellidos1(String apellidos1) {
		this.apellidos1 = apellidos1;
	}

	/**
	 * @return the apellidos2
	 */
	public String getApellidos2() {
		return apellidos2;
	}

	/**
	 * @param apellidos2 the apellidos2 to set
	 */
	public void setApellidos2(String apellidos2) {
		this.apellidos2 = apellidos2;
	}

	/**
	 * @return the nifcif
	 */
	public String getNifcif() {
		return nifcif;
	}

	/**
	 * @param nifcif the nifcif to set
	 */
	public void setNifcif(String nifcif) {
		this.nifcif = nifcif;
	}

	/**
	 * @return the fechamodificacion
	 */
	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	/**
	 * @param fechamodificacion the fechamodificacion to set
	 */
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	/**
	 * @return the usumodificacion
	 */
	public Integer getUsumodificacion() {
		return usumodificacion;
	}

	/**
	 * @param usumodificacion the usumodificacion to set
	 */
	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}

	/**
	 * @return the idtipoidentificacion
	 */
	public Short getIdtipoidentificacion() {
		return idtipoidentificacion;
	}

	/**
	 * @param idtipoidentificacion the idtipoidentificacion to set
	 */
	public void setIdtipoidentificacion(Short idtipoidentificacion) {
		this.idtipoidentificacion = idtipoidentificacion;
	}

	/**
	 * @return the fechanacimiento
	 */
	public Date getFechanacimiento() {
		return fechanacimiento;
	}

	/**
	 * @param fechanacimiento the fechanacimiento to set
	 */
	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	/**
	 * @return the idestadocivil
	 */
	public Short getIdestadocivil() {
		return idestadocivil;
	}

	/**
	 * @param idestadocivil the idestadocivil to set
	 */
	public void setIdestadocivil(Short idestadocivil) {
		this.idestadocivil = idestadocivil;
	}

	/**
	 * @return the naturalde
	 */
	public String getNaturalde() {
		return naturalde;
	}

	/**
	 * @param naturalde the naturalde to set
	 */
	public void setNaturalde(String naturalde) {
		this.naturalde = naturalde;
	}

	/**
	 * @return the fallecido
	 */
	public String getFallecido() {
		return fallecido;
	}

	/**
	 * @param fallecido the fallecido to set
	 */
	public void setFallecido(String fallecido) {
		this.fallecido = fallecido;
	}

	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	
	public CenPersonaItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CenPersonaItem(Long idpersona, String nombre, String apellidos1, String apellidos2, String nifcif,
			Date fechamodificacion, Integer usumodificacion, Short idtipoidentificacion, Date fechanacimiento,
			Short idestadocivil, String naturalde, String fallecido, String sexo) {
		super();
		this.idpersona = idpersona;
		this.nombre = nombre;
		this.apellidos1 = apellidos1;
		this.apellidos2 = apellidos2;
		this.nifcif = nifcif;
		this.fechamodificacion = fechamodificacion;
		this.usumodificacion = usumodificacion;
		this.idtipoidentificacion = idtipoidentificacion;
		this.fechanacimiento = fechanacimiento;
		this.idestadocivil = idestadocivil;
		this.naturalde = naturalde;
		this.fallecido = fallecido;
		this.sexo = sexo;
	}

	
	
}
