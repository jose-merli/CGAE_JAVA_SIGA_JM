package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class InscripcionTurnoItem {

	private Short idinstitucion;

	private Long idpersona;

	private Integer idturno;

	private Date fechasolicitud;

	private Date fechamodificacion;

	private Integer usumodificacion;

	private String fechavalidacion;

	private String fechabaja;

	private String observacionessolicitud;

	private String observacionesvalidacion;

	private String observacionesbaja;

	private Date fechasolicitudbaja;

	private Date fechadenegacion;

	private String observacionesdenegacion;

	private String observacionesvalbaja;

	//Campo calculado en getColaTurnoBBDD()
	private String estado;
	
	
	//Legacy
	
	private String nifcif;
	private String nombre;
	private String Apellidos1;
	private String Apellidos2;
	private String alfabeticoapellidos;
	private String numerocolegiado;
	private Date fechanacimiento;
	private Date antiguedadcola;
	
	
	
	
	
	public InscripcionTurnoItem() {
		super();
	}

	public InscripcionTurnoItem(short idInstitucion, int idTurno, Long idPersonaUltimo, Date fechaSolicitud) {
		this.idinstitucion =  idInstitucion;
		this.idturno = idTurno;
		this.idpersona = idPersonaUltimo;
		this.fechasolicitud = fechaSolicitud;

	}
	
	/**
	 * @return the idinstitucion
	 */
	public Short getIdinstitucion() {
		return idinstitucion;
	}

	/**
	 * @param idinstitucion the idinstitucion to set
	 */
	public void setIdinstitucion(Short idinstitucion) {
		this.idinstitucion = idinstitucion;
	}

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
	 * @return the idturno
	 */
	public Integer getIdturno() {
		return idturno;
	}

	/**
	 * @param idturno the idturno to set
	 */
	public void setIdturno(Integer idturno) {
		this.idturno = idturno;
	}

	/**
	 * @return the fechasolicitud
	 */
	public Date getFechasolicitud() {
		return fechasolicitud;
	}

	/**
	 * @param fechasolicitud the fechasolicitud to set
	 */
	public void setFechasolicitud(Date fechasolicitud) {
		this.fechasolicitud = fechasolicitud;
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
	 * @return the fechavalidacion
	 */
	public String getFechavalidacion() {
		return fechavalidacion;
	}

	/**
	 * @param fechavalidacion the fechavalidacion to set
	 */
	public void setFechavalidacion(String fechavalidacion) {
		this.fechavalidacion = fechavalidacion;
	}

	/**
	 * @return the fechabaja
	 */
	public String getFechabaja() {
		return fechabaja;
	}

	/**
	 * @param fechabaja the fechabaja to set
	 */
	public void setFechabaja(String fechabaja) {
		this.fechabaja = fechabaja;
	}

	/**
	 * @return the observacionessolicitud
	 */
	public String getObservacionessolicitud() {
		return observacionessolicitud;
	}

	/**
	 * @param observacionessolicitud the observacionessolicitud to set
	 */
	public void setObservacionessolicitud(String observacionessolicitud) {
		this.observacionessolicitud = observacionessolicitud;
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

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
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
		return Apellidos1;
	}

	/**
	 * @param apellidos1 the apellidos1 to set
	 */
	public void setApellidos1(String apellidos1) {
		Apellidos1 = apellidos1;
	}

	/**
	 * @return the apellidos2
	 */
	public String getApellidos2() {
		return Apellidos2;
	}

	/**
	 * @param apellidos2 the apellidos2 to set
	 */
	public void setApellidos2(String apellidos2) {
		Apellidos2 = apellidos2;
	}

	/**
	 * @return the alfabeticoapellidos
	 */
	public String getAlfabeticoapellidos() {
		return alfabeticoapellidos;
	}

	/**
	 * @param alfabeticoapellidos the alfabeticoapellidos to set
	 */
	public void setAlfabeticoapellidos(String alfabeticoapellidos) {
		this.alfabeticoapellidos = alfabeticoapellidos;
	}

	/**
	 * @return the numerocolegiado
	 */
	public String getNumerocolegiado() {
		return numerocolegiado;
	}

	/**
	 * @param numerocolegiado the numerocolegiado to set
	 */
	public void setNumerocolegiado(String numerocolegiado) {
		this.numerocolegiado = numerocolegiado;
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
	 * @return the antiguedadcola
	 */
	public Date getAntiguedadcola() {
		return antiguedadcola;
	}

	/**
	 * @param antiguedadcola the antiguedadcola to set
	 */
	public void setAntiguedadcola(Date antiguedadcola) {
		this.antiguedadcola = antiguedadcola;
	}
	
	

}
