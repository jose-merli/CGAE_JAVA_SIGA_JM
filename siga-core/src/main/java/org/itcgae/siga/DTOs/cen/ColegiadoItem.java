package org.itcgae.siga.DTOs.cen;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.itcgae.siga.DTOs.gen.ComboItem;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColegiadoItem implements Comparable<ColegiadoItem>{

	private Boolean colegiado;
	
	private String idPersona;
	private String noAparecerRedAbogacia;
	private String noAparecerRedAbogacia2;
	private String noAparecerRedAbogaciaFilter;
	private Boolean searchLoggedUser;
	private String institucion;
	private String idInstitucion;
	private Date fechaEstado;
	private String nif;
	private String nombre;
	private String numColegiado;
	private String numberColegiado;
	private String residenteInscrito;
	private String situacionResidente;
	private String situacionResidenteFilter;
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
	private String[] situaciones;
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
	private ComboInstitucionItem idgrupo [];
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

	private String fechaEstadoStr;
	private Date fechaEstadoNueva;
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
	private String [] temas;
	private List<ComboItem> temasCombo;
	private String identificadords;
	private Boolean cambioEstado;
	private Boolean searchCount;
	private String count;
	
	private String cuentaContable;
	
	// Nombre del colegio que se mostrará como resultado en la tabla de busqueda
	private String colegioResultado;
	// Filtro de colegios de la pantalla de busqueda
	private String [] colegio;
	
	
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
	
	public void setFechaAltaDate(Date fechaAltaDate) {
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

	public ColegiadoItem idgrupo(ComboInstitucionItem[] idgrupo ){
	
		this.idgrupo = idgrupo;
		return this;
	}

	@JsonProperty("idgrupo")
	public ComboInstitucionItem [] getIdgrupo() {
		return idgrupo;
	}
	
	public void setIdgrupo(ComboInstitucionItem[] idgrupo) {
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
	
	public void setIncorporacionDate(Date incorporacionDate) {
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
	
	/**
	 *
	 */
	
	public ColegiadoItem identificadords(String identificadords){
		this.identificadords = identificadords;
		return this;
	}
	
	@JsonProperty("identificadords")
	public String getIdentificadords() {
		return identificadords;
	}
	
	public void setIdentificadords(String identificadords) {
		this.identificadords = identificadords;
	}
	
	/**
	 *
	 */
	public ColegiadoItem idTratamiento(String idTratamiento){
		this.idTratamiento = idTratamiento;
		return this;
	}
	
	@JsonProperty("idTratamiento")
	public String getIdTratamiento() {
		return idTratamiento;
	}

	public void setIdTratamiento(String idTratamiento) {
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
	
	public Date getFechaEstadoNueva() {
		return fechaEstadoNueva;
	}

	public void setFechaEstadoNueva(Date fechaEstadoNueva) {
		this.fechaEstadoNueva = fechaEstadoNueva;
	}
	
	public String getNoAparecerRedAbogacia2() {
		return noAparecerRedAbogacia2;
	}

	public void setNoAparecerRedAbogacia2(String noAparecerRedAbogacia2) {
		this.noAparecerRedAbogacia2 = noAparecerRedAbogacia2;
	}

	public String getNoAparecerRedAbogaciaFilter() {
		return noAparecerRedAbogaciaFilter;
	}

	public void setNoAparecerRedAbogaciaFilter(String noAparecerRedAbogaciaFilter) {
		this.noAparecerRedAbogaciaFilter = noAparecerRedAbogaciaFilter;
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


	public String[] getTemas() {
		return temas;
	}

	public void setTemas(String[] temas) {
		this.temas = temas;
	}

	public List<ComboItem> getTemasCombo() {
		return temasCombo;
	}

	public void setTemasCombo(List<ComboItem> temasCombo) {
		this.temasCombo = temasCombo;
	}

	public String[] getColegio() {
		return colegio;
	}

	public void setColegio(String[] colegio) {
		this.colegio = colegio;
	}

	public String getColegioResultado() {
		return colegioResultado;
	}

	public void setColegioResultado(String colegioResultado) {
		this.colegioResultado = colegioResultado;
	}

	public Boolean getCambioEstado() {
		return cambioEstado;
	}

	public void setCambioEstado(Boolean cambioEstado) {
		this.cambioEstado = cambioEstado;
	}

	public String getNumberColegiado() {
		return numberColegiado;
	}

	public void setNumberColegiado(String numberColegiado) {
		this.numberColegiado = numberColegiado;
	}

	public Boolean getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(Boolean searchCount) {
		this.searchCount = searchCount;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	
	public String getSituacionResidenteFilter() {
		return situacionResidenteFilter;
	}

	public void setSituacionResidenteFilter(String situacionResidenteFilter) {
		this.situacionResidenteFilter = situacionResidenteFilter;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((apellidos1 == null) ? 0 : apellidos1.hashCode());
		result = prime * result + ((apellidos2 == null) ? 0 : apellidos2.hashCode());
		result = prime * result + ((asientoContable == null) ? 0 : asientoContable.hashCode());
		result = prime * result + ((cuentaContable == null) ? 0 : cuentaContable.hashCode());
		result = prime * result + ((cambioEstado == null) ? 0 : cambioEstado.hashCode());
		result = prime * result + ((codigoPostal == null) ? 0 : codigoPostal.hashCode());
		result = prime * result + ((colegiado == null) ? 0 : colegiado.hashCode());
		result = prime * result + Arrays.hashCode(colegio);
		result = prime * result + ((colegioResultado == null) ? 0 : colegioResultado.hashCode());
		result = prime * result + ((comisiones == null) ? 0 : comisiones.hashCode());
		result = prime * result + ((comunitario == null) ? 0 : comunitario.hashCode());
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
		result = prime * result + ((denominacion == null) ? 0 : denominacion.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((domicilio == null) ? 0 : domicilio.hashCode());
		result = prime * result + ((estadoColegial == null) ? 0 : estadoColegial.hashCode());
		result = prime * result + Arrays.hashCode(etiquetas);
		result = prime * result + ((fechaAlta == null) ? 0 : fechaAlta.hashCode());
		result = prime * result + ((fechaAltaDate == null) ? 0 : fechaAltaDate.hashCode());
		result = prime * result + ((fechaBaja == null) ? 0 : fechaBaja.hashCode());
		result = prime * result + ((fechaBajaDate == null) ? 0 : fechaBajaDate.hashCode());
		result = prime * result + ((fechaEstado == null) ? 0 : fechaEstado.hashCode());
		result = prime * result + ((fechaEstadoNueva == null) ? 0 : fechaEstadoNueva.hashCode());
		result = prime * result + ((fechaEstadoStr == null) ? 0 : fechaEstadoStr.hashCode());
		result = prime * result + Arrays.hashCode(fechaIncorporacion);
		result = prime * result + ((fechaJura == null) ? 0 : fechaJura.hashCode());
		result = prime * result + ((fechaJuraDate == null) ? 0 : fechaJuraDate.hashCode());
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((fechaNacimientoDate == null) ? 0 : fechaNacimientoDate.hashCode());
		result = prime * result + Arrays.hashCode(fechaNacimientoRango);
		result = prime * result + ((fechaTitulacion == null) ? 0 : fechaTitulacion.hashCode());
		result = prime * result + ((fechaTitulacionDate == null) ? 0 : fechaTitulacionDate.hashCode());
		result = prime * result + ((fechapresentacion == null) ? 0 : fechapresentacion.hashCode());
		result = prime * result + ((fechapresentacionDate == null) ? 0 : fechapresentacionDate.hashCode());
		result = prime * result + Arrays.hashCode(grupos);
		result = prime * result + ((idEstado == null) ? 0 : idEstado.hashCode());
		result = prime * result + ((idEstadoCivil == null) ? 0 : idEstadoCivil.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idLenguaje == null) ? 0 : idLenguaje.hashCode());
		result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
		result = prime * result + ((idPoblacion == null) ? 0 : idPoblacion.hashCode());
		result = prime * result + ((idProvincia == null) ? 0 : idProvincia.hashCode());
		result = prime * result + ((idTipoIdentificacion == null) ? 0 : idTipoIdentificacion.hashCode());
		result = prime * result + ((idTiposSeguro == null) ? 0 : idTiposSeguro.hashCode());
		result = prime * result + ((idTratamiento == null) ? 0 : idTratamiento.hashCode());
		result = prime * result + ((identificadords == null) ? 0 : identificadords.hashCode());
		result = prime * result + Arrays.hashCode(idgrupo);
		result = prime * result + ((incorporacion == null) ? 0 : incorporacion.hashCode());
		result = prime * result + ((incorporacionDate == null) ? 0 : incorporacionDate.hashCode());
		result = prime * result + ((inscrito == null) ? 0 : inscrito.hashCode());
		result = prime * result + ((institucion == null) ? 0 : institucion.hashCode());
		result = prime * result + ((motivo == null) ? 0 : motivo.hashCode());
		result = prime * result + ((movil == null) ? 0 : movil.hashCode());
		result = prime * result + ((nMutualista == null) ? 0 : nMutualista.hashCode());
		result = prime * result + ((naturalDe == null) ? 0 : naturalDe.hashCode());
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		result = prime * result + ((noAparecerRedAbogacia == null) ? 0 : noAparecerRedAbogacia.hashCode());
		result = prime * result + ((noAparecerRedAbogacia2 == null) ? 0 : noAparecerRedAbogacia2.hashCode());
		result = prime * result + ((noAparecerRedAbogaciaFilter == null) ? 0 : noAparecerRedAbogaciaFilter.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((numColegiado == null) ? 0 : numColegiado.hashCode());
		result = prime * result + ((numberColegiado == null) ? 0 : numberColegiado.hashCode());
		result = prime * result + ((observaciones == null) ? 0 : observaciones.hashCode());
		result = prime * result + ((partidoJudicial == null) ? 0 : partidoJudicial.hashCode());
		result = prime * result + ((residencia == null) ? 0 : residencia.hashCode());
		result = prime * result + ((residenteInscrito == null) ? 0 : residenteInscrito.hashCode());
		result = prime * result + ((searchLoggedUser == null) ? 0 : searchLoggedUser.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result + ((situacion == null) ? 0 : situacion.hashCode());
		result = prime * result + ((situacionResidente == null) ? 0 : situacionResidente.hashCode());
		result = prime * result + ((situacionResidenteFilter == null) ? 0 : situacionResidenteFilter.hashCode());
		result = prime * result + ((soloNombre == null) ? 0 : soloNombre.hashCode());
		result = prime * result + ((subTipoCV1 == null) ? 0 : subTipoCV1.hashCode());
		result = prime * result + ((subTipoCV2 == null) ? 0 : subTipoCV2.hashCode());
		result = prime * result + Arrays.hashCode(subtipoCV);
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		result = prime * result + Arrays.hashCode(temas);
		result = prime * result + ((temasCombo == null) ? 0 : temasCombo.hashCode());
		result = prime * result + ((tipoCV == null) ? 0 : tipoCV.hashCode());
		result = prime * result + ((tipoDireccion == null) ? 0 : tipoDireccion.hashCode());
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
		ColegiadoItem other = (ColegiadoItem) obj;
		if (apellidos == null) {
			if (other.apellidos != null)
				return false;
		} else if (!apellidos.equals(other.apellidos))
			return false;
		if (apellidos1 == null) {
			if (other.apellidos1 != null)
				return false;
		} else if (!apellidos1.equals(other.apellidos1))
			return false;
		if (apellidos2 == null) {
			if (other.apellidos2 != null)
				return false;
		} else if (!apellidos2.equals(other.apellidos2))
			return false;
		if (asientoContable == null) {
			if (other.asientoContable != null)
				return false;
		} else if (!asientoContable.equals(other.asientoContable))
			return false;
		if (cuentaContable == null) {
			if (other.cuentaContable != null)
				return false;
		} else if (!cuentaContable.equals(other.cuentaContable))
			return false;
		if (cambioEstado == null) {
			if (other.cambioEstado != null)
				return false;
		} else if (!cambioEstado.equals(other.cambioEstado))
			return false;
		if (codigoPostal == null) {
			if (other.codigoPostal != null)
				return false;
		} else if (!codigoPostal.equals(other.codigoPostal))
			return false;
		if (colegiado == null) {
			if (other.colegiado != null)
				return false;
		} else if (!colegiado.equals(other.colegiado))
			return false;
		if (!Arrays.equals(colegio, other.colegio))
			return false;
		if (colegioResultado == null) {
			if (other.colegioResultado != null)
				return false;
		} else if (!colegioResultado.equals(other.colegioResultado))
			return false;
		if (comisiones == null) {
			if (other.comisiones != null)
				return false;
		} else if (!comisiones.equals(other.comisiones))
			return false;
		if (comunitario == null) {
			if (other.comunitario != null)
				return false;
		} else if (!comunitario.equals(other.comunitario))
			return false;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		if (denominacion == null) {
			if (other.denominacion != null)
				return false;
		} else if (!denominacion.equals(other.denominacion))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (domicilio == null) {
			if (other.domicilio != null)
				return false;
		} else if (!domicilio.equals(other.domicilio))
			return false;
		if (estadoColegial == null) {
			if (other.estadoColegial != null)
				return false;
		} else if (!estadoColegial.equals(other.estadoColegial))
			return false;
		if (!Arrays.equals(etiquetas, other.etiquetas))
			return false;
		if (fechaAlta == null) {
			if (other.fechaAlta != null)
				return false;
		} else if (!fechaAlta.equals(other.fechaAlta))
			return false;
		if (fechaAltaDate == null) {
			if (other.fechaAltaDate != null)
				return false;
		} else if (!fechaAltaDate.equals(other.fechaAltaDate))
			return false;
		if (fechaBaja == null) {
			if (other.fechaBaja != null)
				return false;
		} else if (!fechaBaja.equals(other.fechaBaja))
			return false;
		if (fechaBajaDate == null) {
			if (other.fechaBajaDate != null)
				return false;
		} else if (!fechaBajaDate.equals(other.fechaBajaDate))
			return false;
		if (fechaEstado == null) {
			if (other.fechaEstado != null)
				return false;
		} else if (!fechaEstado.equals(other.fechaEstado))
			return false;
		if (fechaEstadoNueva == null) {
			if (other.fechaEstadoNueva != null)
				return false;
		} else if (!fechaEstadoNueva.equals(other.fechaEstadoNueva))
			return false;
		if (fechaEstadoStr == null) {
			if (other.fechaEstadoStr != null)
				return false;
		} else if (!fechaEstadoStr.equals(other.fechaEstadoStr))
			return false;
		if (!Arrays.equals(fechaIncorporacion, other.fechaIncorporacion))
			return false;
		if (fechaJura == null) {
			if (other.fechaJura != null)
				return false;
		} else if (!fechaJura.equals(other.fechaJura))
			return false;
		if (fechaJuraDate == null) {
			if (other.fechaJuraDate != null)
				return false;
		} else if (!fechaJuraDate.equals(other.fechaJuraDate))
			return false;
		if (fechaNacimiento == null) {
			if (other.fechaNacimiento != null)
				return false;
		} else if (!fechaNacimiento.equals(other.fechaNacimiento))
			return false;
		if (fechaNacimientoDate == null) {
			if (other.fechaNacimientoDate != null)
				return false;
		} else if (!fechaNacimientoDate.equals(other.fechaNacimientoDate))
			return false;
		if (!Arrays.equals(fechaNacimientoRango, other.fechaNacimientoRango))
			return false;
		if (fechaTitulacion == null) {
			if (other.fechaTitulacion != null)
				return false;
		} else if (!fechaTitulacion.equals(other.fechaTitulacion))
			return false;
		if (fechaTitulacionDate == null) {
			if (other.fechaTitulacionDate != null)
				return false;
		} else if (!fechaTitulacionDate.equals(other.fechaTitulacionDate))
			return false;
		if (fechapresentacion == null) {
			if (other.fechapresentacion != null)
				return false;
		} else if (!fechapresentacion.equals(other.fechapresentacion))
			return false;
		if (fechapresentacionDate == null) {
			if (other.fechapresentacionDate != null)
				return false;
		} else if (!fechapresentacionDate.equals(other.fechapresentacionDate))
			return false;
		if (!Arrays.equals(grupos, other.grupos))
			return false;
		if (idEstado == null) {
			if (other.idEstado != null)
				return false;
		} else if (!idEstado.equals(other.idEstado))
			return false;
		if (idEstadoCivil == null) {
			if (other.idEstadoCivil != null)
				return false;
		} else if (!idEstadoCivil.equals(other.idEstadoCivil))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (idLenguaje == null) {
			if (other.idLenguaje != null)
				return false;
		} else if (!idLenguaje.equals(other.idLenguaje))
			return false;
		if (idPersona == null) {
			if (other.idPersona != null)
				return false;
		} else if (!idPersona.equals(other.idPersona))
			return false;
		if (idPoblacion == null) {
			if (other.idPoblacion != null)
				return false;
		} else if (!idPoblacion.equals(other.idPoblacion))
			return false;
		if (idProvincia == null) {
			if (other.idProvincia != null)
				return false;
		} else if (!idProvincia.equals(other.idProvincia))
			return false;
		if (idTipoIdentificacion == null) {
			if (other.idTipoIdentificacion != null)
				return false;
		} else if (!idTipoIdentificacion.equals(other.idTipoIdentificacion))
			return false;
		if (idTiposSeguro == null) {
			if (other.idTiposSeguro != null)
				return false;
		} else if (!idTiposSeguro.equals(other.idTiposSeguro))
			return false;
		if (idTratamiento == null) {
			if (other.idTratamiento != null)
				return false;
		} else if (!idTratamiento.equals(other.idTratamiento))
			return false;
		if (identificadords == null) {
			if (other.identificadords != null)
				return false;
		} else if (!identificadords.equals(other.identificadords))
			return false;
		if (!Arrays.equals(idgrupo, other.idgrupo))
			return false;
		if (incorporacion == null) {
			if (other.incorporacion != null)
				return false;
		} else if (!incorporacion.equals(other.incorporacion))
			return false;
		if (incorporacionDate == null) {
			if (other.incorporacionDate != null)
				return false;
		} else if (!incorporacionDate.equals(other.incorporacionDate))
			return false;
		if (inscrito == null) {
			if (other.inscrito != null)
				return false;
		} else if (!inscrito.equals(other.inscrito))
			return false;
		if (institucion == null) {
			if (other.institucion != null)
				return false;
		} else if (!institucion.equals(other.institucion))
			return false;
		if (motivo == null) {
			if (other.motivo != null)
				return false;
		} else if (!motivo.equals(other.motivo))
			return false;
		if (movil == null) {
			if (other.movil != null)
				return false;
		} else if (!movil.equals(other.movil))
			return false;
		if (nMutualista == null) {
			if (other.nMutualista != null)
				return false;
		} else if (!nMutualista.equals(other.nMutualista))
			return false;
		if (naturalDe == null) {
			if (other.naturalDe != null)
				return false;
		} else if (!naturalDe.equals(other.naturalDe))
			return false;
		if (nif == null) {
			if (other.nif != null)
				return false;
		} else if (!nif.equals(other.nif))
			return false;
		if (noAparecerRedAbogacia == null) {
			if (other.noAparecerRedAbogacia != null)
				return false;
		} else if (!noAparecerRedAbogacia.equals(other.noAparecerRedAbogacia))
			return false;
		if (noAparecerRedAbogacia2 == null) {
			if (other.noAparecerRedAbogacia2 != null)
				return false;
		} else if (!noAparecerRedAbogacia2.equals(other.noAparecerRedAbogacia2))
			return false;
		if (noAparecerRedAbogaciaFilter == null) {
			if (other.noAparecerRedAbogaciaFilter != null)
				return false;
		} else if (!noAparecerRedAbogaciaFilter.equals(other.noAparecerRedAbogaciaFilter))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (numColegiado == null) {
			if (other.numColegiado != null)
				return false;
		} else if (!numColegiado.equals(other.numColegiado))
			return false;
		if (numberColegiado == null) {
			if (other.numberColegiado != null)
				return false;
		} else if (!numberColegiado.equals(other.numberColegiado))
			return false;
		if (observaciones == null) {
			if (other.observaciones != null)
				return false;
		} else if (!observaciones.equals(other.observaciones))
			return false;
		if (partidoJudicial == null) {
			if (other.partidoJudicial != null)
				return false;
		} else if (!partidoJudicial.equals(other.partidoJudicial))
			return false;
		if (residencia == null) {
			if (other.residencia != null)
				return false;
		} else if (!residencia.equals(other.residencia))
			return false;
		if (residenteInscrito == null) {
			if (other.residenteInscrito != null)
				return false;
		} else if (!residenteInscrito.equals(other.residenteInscrito))
			return false;
		if (searchLoggedUser == null) {
			if (other.searchLoggedUser != null)
				return false;
		} else if (!searchLoggedUser.equals(other.searchLoggedUser))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		if (situacion == null) {
			if (other.situacion != null)
				return false;
		} else if (!situacion.equals(other.situacion))
			return false;
		if (situacionResidente == null) {
			if (other.situacionResidente != null)
				return false;
		} else if (!situacionResidente.equals(other.situacionResidente))
			return false;
		if (situacionResidenteFilter == null) {
			if (other.situacionResidenteFilter != null)
				return false;
		} else if (!situacionResidenteFilter.equals(other.situacionResidenteFilter))
			return false;
		if (soloNombre == null) {
			if (other.soloNombre != null)
				return false;
		} else if (!soloNombre.equals(other.soloNombre))
			return false;
		if (subTipoCV1 == null) {
			if (other.subTipoCV1 != null)
				return false;
		} else if (!subTipoCV1.equals(other.subTipoCV1))
			return false;
		if (subTipoCV2 == null) {
			if (other.subTipoCV2 != null)
				return false;
		} else if (!subTipoCV2.equals(other.subTipoCV2))
			return false;
		if (!Arrays.equals(subtipoCV, other.subtipoCV))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		if (!Arrays.equals(temas, other.temas))
			return false;
		if (temasCombo == null) {
			if (other.temasCombo != null)
				return false;
		} else if (!temasCombo.equals(other.temasCombo))
			return false;
		if (tipoCV == null) {
			if (other.tipoCV != null)
				return false;
		} else if (!tipoCV.equals(other.tipoCV))
			return false;
		if (tipoDireccion == null) {
			if (other.tipoDireccion != null)
				return false;
		} else if (!tipoDireccion.equals(other.tipoDireccion))
			return false;
		return true;
	}
	public String[] getSituaciones() {
		return situaciones;
	}

	public void setSituaciones(String[] situaciones) {
		this.situaciones = situaciones;
 
	}
	
	@Override
	public int compareTo(ColegiadoItem o) {
		return getFechaEstado().compareTo(o.getFechaEstado());
    }

	public String getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}
}

