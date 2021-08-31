package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class InscripcionGuardiaItem {
	
	private String numeroGrupo;
	private String idGrupoGuardiaColegiado;
	private String orden;
	private String nColegiado;
	private String fechaValidacion;
	private String fechabaja;
	private String compensaciones;
	private String saltos;
	private String apellido1;
	private String apellido2;
	private String nombre;
	private String idPersona;
	private String ordenCola;
	private String idTurno;
	private String idGuardia;
	private String idGrupoGuardia;
	private Date fechaSuscripcion;
	private Date fechamodificacion;
	private Integer usumodificacion;
	private String observacionessuscripcion;
	private String observacionesbaja;
	private Date fechasolicitudbaja;
	private Date fechavalidacion;
	private String observacionesvalidacion;
	private Date fechadenegacion;
	private String observacionesdenegacion;
	private Date fechaBaja;
	private String observacionesvalbaja;
	// Atributos calculados
	private String	estado;
	private String idInstitucion;
	
	public InscripcionGuardiaItem(String numeroGrupo, String idGrupoGuardiaColegiado, String orden, String nColegiado,
			String fechaValidacion, String fechabaja, String compensaciones, String saltos, String apellido1,
			String apellido2, String nombre, String idPersona, String ordenCola, String idTurno, String idGuardia,
			String idGrupoGuardia, Date fechaSuscripcion, Date fechamodificacion, Integer usumodificacion,
			String observacionessuscripcion, String observacionesbaja, Date fechasolicitudbaja, Date fechavalidacion2,
			String observacionesvalidacion, Date fechadenegacion, String observacionesdenegacion,
			String observacionesvalbaja) {
		super();
		this.numeroGrupo = numeroGrupo;
		this.idGrupoGuardiaColegiado = idGrupoGuardiaColegiado;
		this.orden = orden;
		this.nColegiado = nColegiado;
		this.fechaValidacion = fechaValidacion;
		this.fechabaja = fechabaja;
		this.compensaciones = compensaciones;
		this.saltos = saltos;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.nombre = nombre;
		this.idPersona = idPersona;
		this.ordenCola = ordenCola;
		this.idTurno = idTurno;
		this.idGuardia = idGuardia;
		this.idGrupoGuardia = idGrupoGuardia;
		this.fechaSuscripcion = fechaSuscripcion;
		this.fechamodificacion = fechamodificacion;
		this.usumodificacion = usumodificacion;
		this.observacionessuscripcion = observacionessuscripcion;
		this.observacionesbaja = observacionesbaja;
		this.fechasolicitudbaja = fechasolicitudbaja;
		fechavalidacion = fechavalidacion2;
		this.observacionesvalidacion = observacionesvalidacion;
		this.fechadenegacion = fechadenegacion;
		this.observacionesdenegacion = observacionesdenegacion;
		this.observacionesvalbaja = observacionesvalbaja;
	}
	
	public InscripcionGuardiaItem( String idInstitucion, String idTurno, String idGuardia,String idPersona, Date fechaSuscripcion,
			String idGrupoGuardia) {
		super();
		this.idPersona = idPersona;
		this.idTurno = idTurno;
		this.idGuardia = idGuardia;
		this.idGrupoGuardia = idGrupoGuardia;
		this.fechaSuscripcion = fechaSuscripcion;
		this.idInstitucion = idInstitucion;
	
	}
	
	public InscripcionGuardiaItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getIdGrupoGuardia() {
		return idGrupoGuardia;
	}
	public void setIdGrupoGuardia(String idGrupoguardia) {
		this.idGrupoGuardia = idGrupoguardia;
	}
	public Date getFechaSuscripcion() {
		return fechaSuscripcion;
	}
	public void setFechaSuscripcion(Date fechaSuscripcion) {
		this.fechaSuscripcion = fechaSuscripcion;
	}
	public String getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	public String getIdGuardia() {
		return idGuardia;
	}
	public void setIdGuardia(String idGuardia) {
		this.idGuardia = idGuardia;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getOrdenCola() {
		return ordenCola;
	}
	public void setOrdenCola(String ordenCola) {
		this.ordenCola = ordenCola;
	}
	public String getNumeroGrupo() {
		return numeroGrupo;
	}
	public void setNumeroGrupo(String numeroGrupo) {
		this.numeroGrupo = numeroGrupo;
	}
	public String getIdGrupoGuardiaColegiado() {
		return idGrupoGuardiaColegiado;
	}
	public void setIdGrupoGuardiaColegiado(String idGrupoGuardiaColegiado) {
		this.idGrupoGuardiaColegiado = idGrupoGuardiaColegiado;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getnColegiado() {
		return nColegiado;
	}
	public void setnColegiado(String nColegiado) {
		this.nColegiado = nColegiado;
	}
	public String getFechaValidacion() {
		return fechaValidacion;
	}
	public void setFechaValidacion(String fechaValidacion) {
		this.fechaValidacion = fechaValidacion;
	}
	public String getFechabaja() {
		return fechabaja;
	}
	public void setFechabaja(String fechabaja) {
		this.fechabaja = fechabaja;
	}
	public String getCompensaciones() {
		return compensaciones;
	}
	public void setCompensaciones(String compensaciones) {
		this.compensaciones = compensaciones;
	}
	public String getSaltos() {
		return saltos;
	}
	public void setSaltos(String saltos) {
		this.saltos = saltos;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	 * @return the observacionessuscripcion
	 */
	public String getObservacionessuscripcion() {
		return observacionessuscripcion;
	}

	/**
	 * @param observacionessuscripcion the observacionessuscripcion to set
	 */
	public void setObservacionessuscripcion(String observacionessuscripcion) {
		this.observacionessuscripcion = observacionessuscripcion;
	}

	/**
	 * @return the observacionesbaja
	 */
	public String getObservacionesbaja() {
		return observacionesbaja;
	}

	/**
	 * @param observacionesbaja the observacionesbaja to set
	 */
	public void setObservacionesbaja(String observacionesbaja) {
		this.observacionesbaja = observacionesbaja;
	}

	/**
	 * @return the fechasolicitudbaja
	 */
	public Date getFechasolicitudbaja() {
		return fechasolicitudbaja;
	}

	/**
	 * @param fechasolicitudbaja the fechasolicitudbaja to set
	 */
	public void setFechasolicitudbaja(Date fechasolicitudbaja) {
		this.fechasolicitudbaja = fechasolicitudbaja;
	}

	/**
	 * @return the fechavalidacion
	 */
	public Date getFechavalidacion() {
		return fechavalidacion;
	}

	/**
	 * @param fechavalidacion the fechavalidacion to set
	 */
	public void setFechavalidacion(Date fechavalidacion) {
		this.fechavalidacion = fechavalidacion;
	}

	/**
	 * @return the observacionesvalidacion
	 */
	public String getObservacionesvalidacion() {
		return observacionesvalidacion;
	}

	/**
	 * @param observacionesvalidacion the observacionesvalidacion to set
	 */
	public void setObservacionesvalidacion(String observacionesvalidacion) {
		this.observacionesvalidacion = observacionesvalidacion;
	}

	/**
	 * @return the fechadenegacion
	 */
	public Date getFechadenegacion() {
		return fechadenegacion;
	}

	/**
	 * @param fechadenegacion the fechadenegacion to set
	 */
	public void setFechadenegacion(Date fechadenegacion) {
		this.fechadenegacion = fechadenegacion;
	}

	/**
	 * @return the observacionesdenegacion
	 */
	public String getObservacionesdenegacion() {
		return observacionesdenegacion;
	}

	/**
	 * @param observacionesdenegacion the observacionesdenegacion to set
	 */
	public void setObservacionesdenegacion(String observacionesdenegacion) {
		this.observacionesdenegacion = observacionesdenegacion;
	}
	

	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	/**
	 * @return the observacionesvalbaja
	 */
	public String getObservacionesvalbaja() {
		return observacionesvalbaja;
	}

	/**
	 * @param observacionesvalbaja the observacionesvalbaja to set
	 */
	public void setObservacionesvalbaja(String observacionesvalbaja) {
		this.observacionesvalbaja = observacionesvalbaja;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

}
