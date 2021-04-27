package org.itcgae.siga.DTOs.scs;

public class LetradoDesignaItem {

	private String idPersona;
	private String numeroColegiado;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String colegiado;

	/**
	 * @return the idPersona
	 */
	public String getIdPersona() {
		return idPersona;
	}

	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * @return the numeroColegiado
	 */
	public String getNumeroColegiado() {
		return numeroColegiado;
	}

	/**
	 * @param numeroColegiado the numeroColegiado to set
	 */
	public void setNumeroColegiado(String numeroColegiado) {
		this.numeroColegiado = numeroColegiado;
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
	 * @return the apellido1
	 */
	public String getApellido1() {
		return apellido1;
	}

	/**
	 * @param apellido1 the apellido1 to set
	 */
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	/**
	 * @return the apellido2
	 */
	public String getApellido2() {
		return apellido2;
	}

	/**
	 * @param apellido2 the apellido2 to set
	 */
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	/**
	 * @return the colegiado
	 */
	public String getColegiado() {
		return colegiado;
	}

	/**
	 * @param colegiado the colegiado to set
	 */
	public void setColegiado(String colegiado) {
		this.colegiado = colegiado;
	}

	@Override
	public String toString() {
		return "LetradoDesignaItem [idPersona=" + idPersona + ", numeroColegiado=" + numeroColegiado + ", nombre="
				+ nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", colegiado=" + colegiado + "]";
	}

}
