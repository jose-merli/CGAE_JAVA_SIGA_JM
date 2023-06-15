package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.List;

public class LetradoInscripcionItem {

	// Atributos legacy
	private List<String> periodoGuardias;
	private String idSaltoCompensacion;
	private BajasTemporalesItem bajaTemporal;
	private String idSaltoCompensacionGrupo;
	private CenPersonaItem persona;
	private InscripcionGuardiaItem inscripcionGuardia;
	private InscripcionTurnoItem inscripcionTurno;
	private Integer posicion;
	private Long idGrupoGuardiaColegiado;
	private Integer grupo;
	private Integer ordenGrupo;
	private String numeroGrupo;
	
	//Entity
	private Short idinstitucion;

	private Integer idTurno;

	private Long idpersona;

	private String saltoocompensacion;

	private Date fecha;

	private Date fechamodificacion;

	private Integer usumodificacion;

	private Integer idGuardia;

	private String motivos;

	private Date fechacumplimiento;

	private Integer idcalendarioguardias;

	private Integer idcalendarioguardiascreacion;

	private Short tipomanual;
	
	//¿?
	private Date fecha_anulacion;
	
	
	
	
	public LetradoInscripcionItem() {
		super();
	}

	public LetradoInscripcionItem(CenPersonaItem perBean, Integer idInstitucion, Integer idTurno, Integer idGuardia,
			String saltoCompensacion) {
		this.persona = perBean;
		this.idpersona = perBean.getIdpersona();

		this.idinstitucion = Short.valueOf(idInstitucion.toString());
		this.idTurno = idTurno;
		this.idGuardia = idGuardia;
		this.saltoocompensacion = saltoCompensacion;
	}

	public LetradoInscripcionItem(InscripcionTurnoItem inscripcionTurno)
	{
		// inicializando letrado
		//this.persona = inscripcionTurno.getPersona();
		this.idpersona = inscripcionTurno.getIdpersona();

		if(inscripcionTurno.getIdinstitucion() != null ) {
			this.idinstitucion = Short.valueOf(inscripcionTurno.getIdinstitucion().toString());
		}
		
		this.idTurno = inscripcionTurno.getIdturno();
		this.saltoocompensacion = null;
		this.inscripcionTurno = inscripcionTurno;

	}

	/**
	 * @return the periodoGuardias
	 */
	public List<String> getPeriodoGuardias() {
		return periodoGuardias;
	}

	/**
	 * @param periodoGuardias the periodoGuardias to set
	 */
	public void setPeriodoGuardias(List<String> periodoGuardias) {
		this.periodoGuardias = periodoGuardias;
	}

	/**
	 * @return the idSaltoCompensacion
	 */
	public String getIdSaltoCompensacion() {
		return idSaltoCompensacion;
	}

	/**
	 * @param idSaltoCompensacion the idSaltoCompensacion to set
	 */
	public void setIdSaltoCompensacion(String idSaltoCompensacion) {
		this.idSaltoCompensacion = idSaltoCompensacion;
	}

	/**
	 * @return the bajaTemporal
	 */
	public BajasTemporalesItem getBajaTemporal() {
		return bajaTemporal;
	}

	/**
	 * @param bajaTemporal the bajaTemporal to set
	 */
	public void setBajaTemporal(BajasTemporalesItem bajaTemporal) {
		this.bajaTemporal = bajaTemporal;
	}

	/**
	 * @return the idSaltoCompensacionGrupo
	 */
	public String getIdSaltoCompensacionGrupo() {
		return idSaltoCompensacionGrupo;
	}

	/**
	 * @param idSaltoCompensacionGrupo the idSaltoCompensacionGrupo to set
	 */
	public void setIdSaltoCompensacionGrupo(String idSaltoCompensacionGrupo) {
		this.idSaltoCompensacionGrupo = idSaltoCompensacionGrupo;
	}

	/**
	 * @return the persona
	 */
	public CenPersonaItem getPersona() {
		return persona;
	}

	/**
	 * @param persona the persona to set
	 */
	public void setPersona(CenPersonaItem persona) {
		this.persona = persona;
	}

	/**
	 * @return the inscripcionGuardia
	 */
	public InscripcionGuardiaItem getInscripcionGuardia() {
		return inscripcionGuardia;
	}

	/**
	 * @param inscripcionGuardia the inscripcionGuardia to set
	 */
	public void setInscripcionGuardia(InscripcionGuardiaItem inscripcionGuardia) {
		this.inscripcionGuardia = inscripcionGuardia;
	}

	/**
	 * @return the inscripcionTurno
	 */
	public InscripcionTurnoItem getInscripcionTurno() {
		return inscripcionTurno;
	}

	/**
	 * @param inscripcionTurno the inscripcionTurno to set
	 */
	public void setInscripcionTurno(InscripcionTurnoItem inscripcionTurno) {
		this.inscripcionTurno = inscripcionTurno;
	}

	/**
	 * @return the posicion
	 */
	public Integer getPosicion() {
		return posicion;
	}

	/**
	 * @param posicion the posicion to set
	 */
	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	/**
	 * @return the idGrupoGuardiaColegiado
	 */
	public Long getIdGrupoGuardiaColegiado() {
		return idGrupoGuardiaColegiado;
	}

	/**
	 * @param idGrupoGuardiaColegiado the idGrupoGuardiaColegiado to set
	 */
	public void setIdGrupoGuardiaColegiado(Long idGrupoGuardiaColegiado) {
		this.idGrupoGuardiaColegiado = idGrupoGuardiaColegiado;
	}

	/**
	 * @return the grupo
	 */
	public Integer getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo the grupo to set
	 */
	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}

	/**
	 * @return the ordenGrupo
	 */
	public Integer getOrdenGrupo() {
		return ordenGrupo;
	}

	/**
	 * @param ordenGrupo the ordenGrupo to set
	 */
	public void setOrdenGrupo(Integer ordenGrupo) {
		this.ordenGrupo = ordenGrupo;
	}

	/**
	 * @return the numeroGrupo
	 */
	public String getNumeroGrupo() {
		return numeroGrupo;
	}

	/**
	 * @param numeroGrupo the numeroGrupo to set
	 */
	public void setNumeroGrupo(String numeroGrupo) {
		this.numeroGrupo = numeroGrupo;
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
	 * @return the saltoocompensacion
	 */
	public String getSaltoocompensacion() {
		return saltoocompensacion;
	}

	/**
	 * @param saltoocompensacion the saltoocompensacion to set
	 */
	public void setSaltoocompensacion(String saltoocompensacion) {
		this.saltoocompensacion = saltoocompensacion;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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


	public Integer getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(Integer idTurno) {
		this.idTurno = idTurno;
	}

	public Integer getIdGuardia() {
		return idGuardia;
	}

	public void setIdGuardia(Integer idGuardia) {
		this.idGuardia = idGuardia;
	}

	/**
	 * @return the motivos
	 */
	public String getMotivos() {
		return motivos;
	}

	/**
	 * @param motivos the motivos to set
	 */
	public void setMotivos(String motivos) {
		this.motivos = motivos;
	}

	/**
	 * @return the fechacumplimiento
	 */
	public Date getFechacumplimiento() {
		return fechacumplimiento;
	}

	/**
	 * @param fechacumplimiento the fechacumplimiento to set
	 */
	public void setFechacumplimiento(Date fechacumplimiento) {
		this.fechacumplimiento = fechacumplimiento;
	}

	/**
	 * @return the idcalendarioguardias
	 */
	public Integer getIdcalendarioguardias() {
		return idcalendarioguardias;
	}

	/**
	 * @param idcalendarioguardias the idcalendarioguardias to set
	 */
	public void setIdcalendarioguardias(Integer idcalendarioguardias) {
		this.idcalendarioguardias = idcalendarioguardias;
	}

	/**
	 * @return the idcalendarioguardiascreacion
	 */
	public Integer getIdcalendarioguardiascreacion() {
		return idcalendarioguardiascreacion;
	}

	/**
	 * @param idcalendarioguardiascreacion the idcalendarioguardiascreacion to set
	 */
	public void setIdcalendarioguardiascreacion(Integer idcalendarioguardiascreacion) {
		this.idcalendarioguardiascreacion = idcalendarioguardiascreacion;
	}

	/**
	 * @return the tipomanual
	 */
	public Short getTipomanual() {
		return tipomanual;
	}

	/**
	 * @param tipomanual the tipomanual to set
	 */
	public void setTipomanual(Short tipomanual) {
		this.tipomanual = tipomanual;
	}

	/**
	 * @return the fecha_anulacion
	 */
	public Date getFecha_anulacion() {
		return fecha_anulacion;
	}

	/**
	 * @param fecha_anulacion the fecha_anulacion to set
	 */
	public void setFecha_anulacion(Date fecha_anulacion) {
		this.fecha_anulacion = fecha_anulacion;
	}

	

}
