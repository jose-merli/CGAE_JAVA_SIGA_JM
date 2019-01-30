package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ColegiadoItem {

	private Boolean colegiado;
	
	private String idPersona;
	private String noAparecerRedAbogacia;
	private Boolean searchLoggedUser;
	private String institucion;
	private String idInstitucion;
	private String nif;
	private String nombre;
	private String numColegiado;
	private String residenteInscrito;
	private String situacionResidente;
	private String comunitario;
	private String correo;
	private String telefono;
	private String movil;
	private String estadoColegial;	
	private String [] grupos;
	private String  apellidos;
	private Date [] fechaIncorporacion;
	private Date [] fechaNacimientoRango;
	private String situacion;
	private String residencia;
	private String inscrito;	
	private String  idProvincia;
	private String  idPoblacion;
	private String  codigoPostal;
	private String  tipoDireccion;
	private String  sexo;
	private String idEstadoCivil;

	private String idEstado;
	private String domicilio;
	private String tipoCV;
	private String subTipoCV1;
	private String subTipoCV2;
	private String  idgrupo [];
	private String  denominacion;
	private ComboEtiquetasItem[] etiquetas;
	private String soloNombre;
	private String apellidos1;
	private String apellidos2;
	private String idTipoIdentificacion;
	private String naturalDe;
	private String idLenguaje;
	private String asientoContable;
	private String nMutualista;
	private String idTiposSeguro;
	private String partidoJudicial;
	private String comisiones;
	private String incorporacion;
	private String fechaJura;
	private String fechaAlta;
	private String fechaTitulacion;
	private String fechapresentacion;
	private String fechaNacimiento;
	private String fechaBaja;

	private Date fechaEstado;
	private String fechaEstadoStr;
	private Date incorporacionDate;
	private Date fechaJuraDate;
	private Date fechaAltaDate;
	private Date fechaTitulacionDate;
	private Date fechapresentacionDate;
	private Date fechaNacimientoDate;
	private Date fechaBajaDate;
	private String idTratamiento;
	private String descripcion;
	private String observaciones;
	private String motivo;
	private String subtipoCV [];
	
	
	
	
	/**
	 *
	 */
	
	public ColegiadoItem idPersona(String idPersona){
		this.idPersona = idPersona;
		return this;
	}
	
	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}
	
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	
	/**
	 *
	 */
	

	
	public ColegiadoItem descripcion(String descripcion){
		this.descripcion = descripcion;
		return this;
	}
	
	@JsonProperty("descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	/**
	 */
	public ColegiadoItem grupos(String [] grupos){
		this.grupos = grupos;
		return this;
	}
	
	@JsonProperty("grupos")
	public String[] getGrupos() {
		return grupos;
	}

	public void setGrupos(String[] grupos) {
		this.grupos = grupos;
	}
	
	
	/**
	 *
	 */
	public ColegiadoItem colegiado(Boolean colegiado){
		this.colegiado = colegiado;
		return this;
	}
	
	@JsonProperty("colegiado")
	public Boolean getColegiado() {
		return colegiado;
	}
	
	public void setColegiado(Boolean colegiado) {
		this.colegiado = colegiado;
	}
	
	/**
	 *
	 */
	public ColegiadoItem idInstitucion(String idInstitucion){
		this.idInstitucion = idInstitucion;
		return this;
	}
	
	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}
	
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
		
	/**
	 *
	 */
	
	public ColegiadoItem observaciones(String observaciones){
		this.observaciones = observaciones;
		return this;
	}
	
	@JsonProperty("observaciones")
	public String getObservaciones() {
		return observaciones;
	}
	
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	/**
	 *
	 */
	public ColegiadoItem nif(String nif){
		this.nif = nif;
		return this;
	}
	
	@JsonProperty("nif")
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	/**
	 *
	 */
	public ColegiadoItem nombre(String nombre){
		this.nombre = nombre;
		return this;
	}

	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 *
	 */
	public ColegiadoItem numColegiado(String numColegiado){
		this.numColegiado = numColegiado;
		return this;
	}
	
	@JsonProperty("numColegiado")
	public String getNumColegiado() {
		return numColegiado;
	}
	
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	
	/**
	 *
	 */
	public ColegiadoItem residenteInscrito(String residenteInscrito){
		this.residenteInscrito = residenteInscrito;
		return this;
	}
	
	@JsonProperty("residenteInscrito")
	public String getResidenteInscrito() {
		return residenteInscrito;
	}
	
	public void setResidenteInscrito(String residenteInscrito) {
		this.residenteInscrito = residenteInscrito;
	}
	
	/**
	 *
	 */
	public ColegiadoItem fechaNacimiento(String fechaNacimiento){
		this.fechaNacimiento = fechaNacimiento;
		return this;
	}

	@JsonProperty("fechaNacimiento")
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	/**
	 *
	 */
	public ColegiadoItem fechaNacimientoDate(Date fechaNacimientoDate){
		this.fechaNacimientoDate = fechaNacimientoDate;
		return this;
	}

	@JsonProperty("fechaNacimientoDate")
	public Date getFechaNacimientoDate() {
		return fechaNacimientoDate;
	}
	
	public void setFechaNacimientoDate(Date fechaNacimientoDate) {
		this.fechaNacimientoDate = fechaNacimientoDate;
	}
	/**
	 *
	 */
	public ColegiadoItem fechaAlta(String fechaAlta){
		this.fechaAlta = fechaAlta;
		return this;
	}

	@JsonProperty("fechaAlta")
	public String getFechaAlta() {
		return fechaAlta;
	}
	
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	/**
	 *
	 */
	public ColegiadoItem fechaAltaDate(Date fechaAltaDate){
		this.fechaAltaDate = fechaAltaDate;
		return this;
	}

	@JsonProperty("fechaAltaDate")
	public Date getFechaAltaDate() {
		return fechaAltaDate;
	}
	
	public void setFechaAlta(Date fechaAltaDate) {
		this.fechaAltaDate = fechaAltaDate;
	}
	
	
	/**
	 *fechaAlta
	 */
	public ColegiadoItem correo(String correo){
		this.correo = correo;
		return this;
	}

	@JsonProperty("correo")
	public String getCorreo() {
		return correo;
	}
	
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	/**
	 *
	 */
	public ColegiadoItem telefono(String telefono){
		this.telefono = telefono;
		return this;
	}

	@JsonProperty("telefono")
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	/**
	 *
	 */
	public ColegiadoItem movil(String movil){
		this.movil = movil;
		return this;
	}

	@JsonProperty("movil")
	public String getMovil() {
		return movil;
	}
	
	public void setMovil(String movil) {
		this.movil = movil;
	}
	
	/**
	 *
	 */
	public ColegiadoItem estadoColegial(String estadoColegial){
		this.estadoColegial = estadoColegial;
		return this;
	}
	
	@JsonProperty("estadoColegial")
	public String getEstadoColegial() {
		return estadoColegial;
	}

	public void setEstadoColegial(String estadoColegial) {
		this.estadoColegial = estadoColegial;
	}

	/**
	 *
	 */
	public ColegiadoItem fechaBaja(String fechaBaja){
		this.fechaBaja = fechaBaja;
		return this;
	}

	@JsonProperty("fechaBaja")
	public String getFechaBaja() {
		return fechaBaja;
	}
	
	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	
	/**
	 *
	 */
	public ColegiadoItem fechaBajaDate(Date fechaBajaDate){
		this.fechaBajaDate = fechaBajaDate;
		return this;
	}

	@JsonProperty("fechaBajaDate")
	public Date getFechaBajaDate() {
		return fechaBajaDate;
	}
	
	public void setFechaBajaDate(Date fechaBajaDate) {
		this.fechaBajaDate = fechaBajaDate;
	}
	
	/**
	 *
	 */
	public ColegiadoItem apellidos(String apellidos){
		this.apellidos = apellidos;
		return this;
	}

	@JsonProperty("apellidos")
	public String getApellidos() {
		return apellidos;
	}
	
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
		
	/**
	 *
	 */
	public ColegiadoItem fechaIncorporacion (Date [] fechaIncorporacion){
		this.fechaIncorporacion = fechaIncorporacion;
		return this;
	}

	@JsonProperty("fechaIncorporacion")
	public Date [] getFechaIncorporacion() {
		return fechaIncorporacion;
	}
	
	public void setFechaIncorporacion(Date [] fechaIncorporacion) {
		this.fechaIncorporacion = fechaIncorporacion;
	}
	
	/**
	 *
	 */
	public ColegiadoItem fechaNacimientoRango (Date [] fechaNacimientoRango){
		this.fechaNacimientoRango = fechaNacimientoRango;
		return this;
	}

	@JsonProperty("fechaNacimientoRango")
	public Date [] getFechaNacimientoRango() {
		return fechaNacimientoRango;
	}
	
	public void setFechaNacimientoRango(Date [] fechaNacimientoRango) {
		this.fechaNacimientoRango = fechaNacimientoRango;
	}
	
	/**
	 *
	 */
	public ColegiadoItem situacion(String situacion){
		this.situacion = situacion;
		return this;
	}
	
	@JsonProperty("situacion")
	public String getSituacion() {
		return situacion;
	}
	
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	
	/**
	 *
	 */
	public ColegiadoItem residencia(String residencia){
		this.residencia = residencia;
		return this;
	}
	
	@JsonProperty("residencia")
	public String getResidencia() {
		return residencia;
	}
	
	public void setResidencia(String residencia) {
		this.residencia = residencia;
	}
	
	/**
	 *
	 */
	public ColegiadoItem inscrito(String inscrito){
		this.inscrito = inscrito;
		return this;
	}
	
	@JsonProperty("inscrito")
	public String getInscrito() {
		return inscrito;
	}
	
	public void setInscrito(String inscrito) {
		this.inscrito = inscrito;
	}
		
	/**
	 *
	 */
	public ColegiadoItem idProvincia(String idProvincia){
		this.idProvincia = idProvincia;
		return this;
	}

	@JsonProperty("idProvincia")
	public String getIdProvincia() {
		return idProvincia;
	}
	
	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
	
	/**
	 *
	 */
	public ColegiadoItem idPoblacion(String idPoblacion){
		this.idPoblacion = idPoblacion;
		return this;
	}

	@JsonProperty("idPoblacion")
	public String getIdPoblacion() {
		return idPoblacion;
	}
	
	public void setIdPoblacion(String idPoblacion) {
		this.idPoblacion = idPoblacion;
	}

	public ColegiadoItem codigoPostal(String codigoPostal){
		this.codigoPostal = codigoPostal;
		return this;
	}

	@JsonProperty("codigoPostal")
	public String getCodigoPostal() {
		return codigoPostal;
	}
	
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	
	/**
	 *
	 */
	public ColegiadoItem tipoDireccion(String tipoDireccion){
		this.tipoDireccion = tipoDireccion;
		return this;
	}

	@JsonProperty("tipoDireccion")
	public String getTipoDireccion() {
		return tipoDireccion;
	}
	
	public void setTipoDireccion(String tipoDireccion) {
		this.tipoDireccion = tipoDireccion;
	}
		
	/**
	 *
	 */
	public ColegiadoItem sexo(String sexo){
		this.sexo = sexo;
		return this;
	}

	@JsonProperty("sexo")
	public String getSexo() {
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	/**
	 *
	 */
	public ColegiadoItem idEstadoCivil(String idEstadoCivil){
		this.idEstadoCivil = idEstadoCivil;
		return this;
	}

	@JsonProperty("idEstadoCivil")
	public String getIdEstadoCivil() {
		return idEstadoCivil;
	}
	
	public void setIdEstadoCivil(String idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}
	
	/**
	 *
	 */
	public ColegiadoItem domicilio(String domicilio){
		this.domicilio = domicilio;
		return this;
	}

	@JsonProperty("domicilio")
	public String getDomicilio() {
		return domicilio;
	}
	
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	
	/**
	 *
	 */
	public ColegiadoItem tipoCV(String tipoCV){
		this.tipoCV = tipoCV;
		return this;
	}

	@JsonProperty("tipoCV")
	public String getTipoCV() {
		return tipoCV;
	}
	
	public void setTipoCV(String tipoCV) {
		this.tipoCV = tipoCV;
	}	
	
	/**
	 *
	 */
	public ColegiadoItem subTipoCV1(String subTipoCV1){
		this.subTipoCV1 = subTipoCV1;
		return this;
	}

	@JsonProperty("subTipoCV1")
	public String getSubTipoCV1() {
		return subTipoCV1;
	}
	
	public void setSubTipoCV1(String subTipoCV1) {
		this.subTipoCV1 = subTipoCV1;
	}	
	
	/**
	 *
	 */
	public ColegiadoItem subTipoCV2(String subTipoCV2){
		this.subTipoCV2 = subTipoCV2;
		return this;
	}

	@JsonProperty("subTipoCV2")
	public String getSubTipoCV2() {
		return subTipoCV2;
	}
	
	public void setSubTipoCV2(String subTipoCV2) {
		this.subTipoCV2 = subTipoCV2;
	}	
	
	/**
	 *
	 */

	public ColegiadoItem idgrupo(String[] idgrupo ){
	
		this.idgrupo = idgrupo;
		return this;
	}

	@JsonProperty("idgrupo")
	public String [] getIdgrupo() {
		return idgrupo;
	}
	
	public void setIdgrupo(String[] idgrupo) {
		this.idgrupo = idgrupo;
	}
	
	/**
	 *
	 */
	public ColegiadoItem denominacion(String denominacion){
		this.denominacion = denominacion;
		return this;
	}

	@JsonProperty("denominacion")
	public String getDenominacion() {
		return denominacion;
	}
	
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
		
	/**
	 *
	 */
	public ColegiadoItem soloNombre(String soloNombre){
		this.soloNombre = soloNombre;
		return this;
	}

	@JsonProperty("soloNombre")
	public String getSoloNombre() {
		return soloNombre;
	}
	
	public void setSoloNombre(String soloNombre) {
		this.soloNombre = soloNombre;
	}
	
	/**
	 *
	 */
	public ColegiadoItem apellidos1(String apellidos1){
		this.apellidos1 = apellidos1;
		return this;
	}

	@JsonProperty("apellidos1")
	public String getApellidos1() {
		return apellidos1;
	}
	
	public void setApellidos1(String apellidos1) {
		this.apellidos1 = apellidos1;
	}

	/**
	 *
	 */
	public ColegiadoItem apellidos2(String apellidos2){
		this.apellidos2 = apellidos2;
		return this;
	}

	@JsonProperty("apellidos2")
	public String getApellidos2() {
		return apellidos2;
	}
	
	public void setApellidos2(String apellidos2) {
		this.apellidos2 = apellidos2;
	}
	
	/**
	 *
	 */
	public ColegiadoItem idTipoIdentificacion(String idTipoIdentificacion){
		this.idTipoIdentificacion = idTipoIdentificacion;
		return this;
	}

	@JsonProperty("idTipoIdentificacion")
	public String getIdTipoIdentificacion() {
		return idTipoIdentificacion;
	}
	
	public void setIdTipoIdentificacion(String idTipoIdentificacion) {
		this.idTipoIdentificacion = idTipoIdentificacion;
	}
	
	/**
	 *
	 */
	public ColegiadoItem naturalDe(String naturalDe){
		this.naturalDe = naturalDe;
		return this;
	}

	@JsonProperty("naturalDe")
	public String getNaturalDe() {
		return naturalDe;
	}
	
	public void setNaturalDe(String naturalDe) {
		this.naturalDe = naturalDe;
	}
	
	/**
	 *
	 */
	public ColegiadoItem idLenguaje(String idLenguaje){
		this.naturalDe = idLenguaje;
		return this;
	}

	@JsonProperty("idLenguaje")
	public String getIdLenguaje() {
		return idLenguaje;
	}
	
	public void setIdLenguaje(String idLenguaje) {
		this.idLenguaje = idLenguaje;
	}
	
	/**
	 *
	 */
	public ColegiadoItem asientoContable(String asientoContable){
		this.asientoContable = asientoContable;
		return this;
	}

	@JsonProperty("asientoContable")
	public String getAsientoContable() {
		return asientoContable;
	}
	
	public void setAsientoContable(String asientoContable) {
		this.asientoContable = asientoContable;
	}
	
	
	/**
	 *
	 */
	public ColegiadoItem nMutualista(String nMutualista){
		this.nMutualista = nMutualista;
		return this;
	}
	

	@JsonProperty("nMutualista")
	public String getnMutualista() {
		return nMutualista;
	}

	public void setnMutualista(String nMutualista) {
		this.nMutualista = nMutualista;
	}
	
	
	/**
	 *
	 */
	public ColegiadoItem idTiposSeguro(String idTiposSeguro){
		this.idTiposSeguro = idTiposSeguro;
		return this;
	}

	@JsonProperty("idTiposSeguro")
	public String getIdTiposSeguro() {
		return idTiposSeguro;
	}
	
	public void setIdTiposSeguro(String idTiposSeguro) {
		this.idTiposSeguro = idTiposSeguro;
	}
	
	/**
	 *
	 */
	public ColegiadoItem incorporacion(String incorporacion){
		this.incorporacion = incorporacion;
		return this;
	}

	@JsonProperty("incorporacion")
	public String getIncorporacion() {
		return incorporacion;
	}
	
	public void setIncorporacion(String incorporacion) {
		this.incorporacion = incorporacion;
	}
	
	/**
	 *
	 */
	public ColegiadoItem incorporacionDate(Date incorporacionDate){
		this.incorporacionDate = incorporacionDate;
		return this;
	}

	@JsonProperty("incorporacionDate")
	public Date getIncorporacionDate() {
		return incorporacionDate;
	}
	
	public void setIncorporacion(Date incorporacionDate) {
		this.incorporacionDate = incorporacionDate;
	}
	
	/**
	 *
	 */
	public ColegiadoItem partidoJudicial(String partidoJudicial){
		this.partidoJudicial = partidoJudicial;
		return this;
	}

	@JsonProperty("partidoJudicial")
	public String getPartidoJudicial() {
		return partidoJudicial;
	}
	
	public void setPartidoJudicial(String partidoJudicial) {
		this.partidoJudicial = partidoJudicial;
	}
	

	/**
	 *
	 */
	public ColegiadoItem comisiones(String comisiones){
		this.comisiones = comisiones;
		return this;
	}

	@JsonProperty("comisiones")
	public String getComisiones() {
		return comisiones;
	}
	
	public void setComisiones(String comisiones) {
		this.comisiones = comisiones;
	}
	
	public ColegiadoItem situacionResidente(String situacionResidente){
		this.situacionResidente = situacionResidente;
		return this;
	}

	@JsonProperty("situacionResidente")
	public String getSituacionResidente() {
		return situacionResidente;
	}

	public void setSituacionResidente(String situacionResidente) {
		this.situacionResidente = situacionResidente;
	}

	public String getComunitario() {
		return comunitario;
	}

	public void setComunitario(String comunitario) {
		this.comunitario = comunitario;
	}


	/**
	 *
	 */
	public ColegiadoItem fechaJura(String fechaJura){
		this.fechaJura = fechaJura;
		return this;
	}

	@JsonProperty("fechaJura")
	public String getFechaJura() {
		return fechaJura;
	}
	
	public void setFechaJura(String fechaJura) {
		this.fechaJura = fechaJura;
	}

	/**
	 *
	 */
	public ColegiadoItem fechaJuraDate(Date fechaJuraDate){
		this.fechaJuraDate = fechaJuraDate;
		return this;
	}

	@JsonProperty("fechaJuraDate")
	public Date getFechaJuraDate() {
		return fechaJuraDate;
	}
	
	public void setFechaJuraDate(Date fechaJuraDate) {
		this.fechaJuraDate = fechaJuraDate;
	}

	
	/**
	 *
	 */
	public ColegiadoItem fechaTitulacion(String fechaTitulacion){
		this.fechaTitulacion = fechaTitulacion;
		return this;
	}

	@JsonProperty("fechaTitulacion")
	public String getFechaTitulacion() {
		return fechaTitulacion;
	}
	
	public void setFechaTitulacion(String fechaTitulacion) {
		this.fechaTitulacion = fechaTitulacion;
	}
	
	
	/**
	 *
	 */
	public ColegiadoItem fechaTitulacionDate(Date fechaTitulacionDate){
		this.fechaTitulacionDate = fechaTitulacionDate;
		return this;
	}

	@JsonProperty("fechaTitulacionDate")
	public Date getFechaTitulacionDate() {
		return fechaTitulacionDate;
	}
	
	public void setFechaTitulacionDate(Date fechaTitulacionDate) {
		this.fechaTitulacionDate = fechaTitulacionDate;
	}
	
	
	
	/**
	 *
	 */
	public ColegiadoItem fechapresentacion(String fechapresentacion){
		this.fechapresentacion = fechapresentacion;
		return this;
	}
	

	@JsonProperty("fechapresentacion")
	public String getFechapresentacion() {
		return fechapresentacion;
	}

	public void setFechapresentacion(String fechapresentacion) {
		this.fechapresentacion = fechapresentacion;
	}
	
	
	/**
	 *
	 */
	public ColegiadoItem fechapresentacionDate(Date fechapresentacionDate){
		this.fechapresentacionDate = fechapresentacionDate;
		return this;
	}
	

	@JsonProperty("fechapresentacionDate")
	public Date getFechapresentacionDate() {
		return fechapresentacionDate;
	}

	public void setFechapresentacionDate(Date fechapresentacionDate) {
		this.fechapresentacionDate = fechapresentacionDate;
	}
	
	

	public ColegiadoItem idTratamiento(String idTratamiento){
		this.idTratamiento = idTratamiento;
		return this;
	}
	
	@JsonProperty("idTratamiento")
	public String getIdtratamiento() {
		return idTratamiento;
	}

	public void setIdtratamiento(String idTratamiento) {
		this.idTratamiento = idTratamiento;
	}
	
	
	public ColegiadoItem subtipoCV(String[] subtipoCV ){
		
		this.subtipoCV = subtipoCV;
		return this;
	}

	@JsonProperty("subtipoCV")
	public String [] getSubtipoCV() {
		return subtipoCV;
	}
	
	public void setSubtipoCV(String[] subtipoCV) {
		this.subtipoCV = subtipoCV;
	}
	
	public ComboEtiquetasItem[] getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(ComboEtiquetasItem[] etiquetas) {
		this.etiquetas = etiquetas;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Date getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	public String getFechaEstadoStr() {
		return fechaEstadoStr;
	}

	public void setFechaEstadoStr(String fechaEstadoStr) {
		this.fechaEstadoStr = fechaEstadoStr;
	}
	
	public String getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}
	

	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idInstitucion, nif, nombre, numColegiado, residenteInscrito,	fechaNacimiento, correo, telefono, movil, estadoColegial,
	    		fechaBaja, apellidos, fechaIncorporacion, situacion, residencia, inscrito, idProvincia, idPoblacion, codigoPostal, tipoDireccion, sexo, 
	    		idEstadoCivil, domicilio, tipoCV, subTipoCV1, subTipoCV2, idgrupo, denominacion, soloNombre, apellidos1, apellidos2,idTipoIdentificacion, naturalDe, idLenguaje,
	    		asientoContable, nMutualista, idTiposSeguro, partidoJudicial, comisiones, incorporacion, fechaJura, fechaTitulacion, fechapresentacion, 
	    		idTratamiento, incorporacionDate, fechaJuraDate, fechaAltaDate, fechaTitulacionDate, fechapresentacionDate, fechaNacimientoDate, fechaBajaDate, observaciones, subtipoCV,
	    		fechaNacimientoRango);
	}


	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosDireccionesItem {\n");
	    
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
	    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
	    sb.append("    numColegiado: ").append(toIndentedString(numColegiado)).append("\n");
	    sb.append("    residenteInscrito: ").append(toIndentedString(residenteInscrito)).append("\n");
	    sb.append("    fechaNacimiento: ").append(toIndentedString(fechaNacimiento)).append("\n");
	    sb.append("    correo: ").append(toIndentedString(correo)).append("\n");
	    sb.append("    telefono: ").append(toIndentedString(telefono)).append("\n");
	    sb.append("    movil: ").append(toIndentedString(movil)).append("\n");
	    sb.append("    estadoColegial: ").append(toIndentedString(estadoColegial)).append("\n");
	    sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
	    sb.append("    apellidos: ").append(toIndentedString(apellidos)).append("\n");
	    sb.append("    fechaIncorporacion: ").append(toIndentedString(fechaIncorporacion)).append("\n");
	    sb.append("    situacion: ").append(toIndentedString(situacion)).append("\n");
	    sb.append("    residencia: ").append(toIndentedString(residencia)).append("\n");
	    sb.append("    inscrito: ").append(toIndentedString(inscrito)).append("\n");
	    sb.append("    idProvincia: ").append(toIndentedString(idProvincia)).append("\n");
	    sb.append("    idPoblacion: ").append(toIndentedString(idPoblacion)).append("\n");
	    sb.append("    codigoPostal: ").append(toIndentedString(codigoPostal)).append("\n");
	    sb.append("    domicilio: ").append(toIndentedString(domicilio)).append("\n");
	    sb.append("    tipoDireccion: ").append(toIndentedString(tipoDireccion)).append("\n");
	    sb.append("    sexo: ").append(toIndentedString(sexo)).append("\n");
	    sb.append("    idEstadoCivil: ").append(toIndentedString(idEstadoCivil)).append("\n");
	    sb.append("    domicilio: ").append(toIndentedString(domicilio)).append("\n");
	    sb.append("    tipoCV: ").append(toIndentedString(tipoCV)).append("\n");
	    sb.append("    subTipoCV1: ").append(toIndentedString(subTipoCV1)).append("\n");
	    sb.append("    subTipoCV2: ").append(toIndentedString(subTipoCV2)).append("\n");
	    sb.append("    idgrupo: ").append(toIndentedString(idgrupo)).append("\n");
	    sb.append("    denominacion: ").append(toIndentedString(denominacion)).append("\n");
	    sb.append("    soloNombre: ").append(toIndentedString(soloNombre)).append("\n");
	    sb.append("    apellidos1: ").append(toIndentedString(apellidos1)).append("\n");
	    sb.append("    apellidos2: ").append(toIndentedString(apellidos2)).append("\n");
	    sb.append("    idTipoIdentificacion: ").append(toIndentedString(idTipoIdentificacion)).append("\n");
	    sb.append("    naturalDe: ").append(toIndentedString(naturalDe)).append("\n");
	    sb.append("    idLenguaje: ").append(toIndentedString(idLenguaje)).append("\n");
	    sb.append("    asientoContable: ").append(toIndentedString(asientoContable)).append("\n");
	    sb.append("    nMutualista: ").append(toIndentedString(nMutualista)).append("\n");
	    sb.append("    idTiposSeguro: ").append(toIndentedString(idTiposSeguro)).append("\n");
	    sb.append("    partidoJudicial: ").append(toIndentedString(partidoJudicial)).append("\n");
	    sb.append("    comisiones: ").append(toIndentedString(comisiones)).append("\n");
	    sb.append("    incorporacion: ").append(toIndentedString(incorporacion)).append("\n");
	    sb.append("    fechaJura: ").append(toIndentedString(fechaJura)).append("\n");
	    sb.append("    fechaTitulacion: ").append(toIndentedString(fechaTitulacion)).append("\n");
	    sb.append("    fechapresentacion: ").append(toIndentedString(fechapresentacion)).append("\n");
	    sb.append("    idTratamiento: ").append(toIndentedString(idTratamiento)).append("\n");
	    sb.append("    fechaAlta: ").append(toIndentedString(fechaAlta)).append("\n");

	    sb.append("    fechaJuraDate: ").append(toIndentedString(fechaJuraDate)).append("\n");
	    sb.append("    fechaTitulacionDate: ").append(toIndentedString(fechaTitulacionDate)).append("\n");
	    sb.append("    fechapresentacionDate: ").append(toIndentedString(fechapresentacionDate)).append("\n");
	    sb.append("    fechaNacimientoDate: ").append(toIndentedString(fechaNacimientoDate)).append("\n");
	    sb.append("    fechaBajaDate: ").append(toIndentedString(fechaBajaDate)).append("\n");
	    sb.append("    fechaAltaDate: ").append(toIndentedString(fechaAltaDate)).append("\n");
	    sb.append("    incorporacionDate: ").append(toIndentedString(incorporacionDate)).append("\n");
	    sb.append("    observaciones: ").append(toIndentedString(observaciones)).append("\n");
	    sb.append("    subtipoCV: ").append(toIndentedString(subtipoCV)).append("\n");
	    sb.append("    fechaNacimientoRango: ").append(toIndentedString(subtipoCV)).append("\n");

	    
		
	    sb.append("}");
	    return sb.toString();
	}
	
	/**
	* Convert the given object to string with each line indented by 4 spaces
	* (except the first line).
	*/
	private String toIndentedString(java.lang.Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	}

	public Boolean getSearchLoggedUser() {
		return searchLoggedUser;
	}

	public void setSearchLoggedUser(Boolean searchLoggedUser) {
		this.searchLoggedUser = searchLoggedUser;
	}

	public String getNoAparecerRedAbogacia() {
		return noAparecerRedAbogacia;
	}

	public void setNoAparecerRedAbogacia(String noAparecerRedAbogacia) {
		this.noAparecerRedAbogacia = noAparecerRedAbogacia;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}


	

}

