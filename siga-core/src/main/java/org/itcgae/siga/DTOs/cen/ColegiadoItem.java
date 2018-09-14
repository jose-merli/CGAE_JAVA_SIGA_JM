package org.itcgae.siga.DTOs.cen;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ColegiadoItem {


	private String idPersona;
	private String idInstitucion;
	private String nif;
	private String nombre;
	private String numColegiado;
	private String residenteInscrito;
	private Date fechaNacimiento;
	private String correo;
	private String telefono;
	private String movil;
	private String estadoColegial;
	private String fechaBaja;

	private String  apellidos;
	private Date fechaIncorporacionDesde;
	private Date fechaIncorporacionHasta;
	private Date fechaIncorporacion;
	private String situacion;
	private String residencia;
	private String inscrito;	
	private String  idProvincia;
	private String  idPoblacion;
	private String  codigoPostal;
	private String  tipoDireccion;
	private String  sexo;
	private String estadoCivil;
	private String domicilio;
	private String tipoCV;
	private String  idgrupo [];
	private String  denominacion;
	
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
	public ColegiadoItem fechaNacimiento(Date fechaNacimiento){
		this.fechaNacimiento = fechaNacimiento;
		return this;
	}

	@JsonProperty("fechaNacimiento")
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	/**
	 *
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
	public ColegiadoItem fechaIncorporacionDesde(Date fechaIncorporacionDesde){
		this.fechaIncorporacionDesde = fechaIncorporacionDesde;
		return this;
	}

	@JsonProperty("fechaIncorporacionDesde")
	public Date getFechaIncorporacionDesde() {
		return fechaIncorporacionDesde;
	}
	
	public void setFechaIncorporacionDesde(Date fechaIncorporacionDesde) {
		this.fechaIncorporacionDesde = fechaIncorporacionDesde;
	}
	
	/**
	 *
	 */
	public ColegiadoItem fechaIncorporacionHasta (Date fechaIncorporacionHasta){
		this.fechaIncorporacionHasta = fechaIncorporacionHasta;
		return this;
	}

	@JsonProperty("fechaIncorporacionHasta")
	public Date getFechaIncorporacionHasta() {
		return fechaIncorporacionHasta;
	}
	
	public void setFechaIncorporacionHasta(Date fechaIncorporacionHasta) {
		this.fechaIncorporacionHasta = fechaIncorporacionHasta;
	}
	
	/**
	 *
	 */
	public ColegiadoItem fechaIncorporacion (Date fechaIncorporacion){
		this.fechaIncorporacion = fechaIncorporacion;
		return this;
	}

	@JsonProperty("fechaIncorporacion")
	public Date getFechaIncorporacion() {
		return fechaIncorporacion;
	}
	
	public void setFechaIncorporacion(Date fechaIncorporacion) {
		this.fechaIncorporacion = fechaIncorporacion;
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
	
	/**
	 *
	 */
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
	public ColegiadoItem estadoCivil(String estadoCivil){
		this.estadoCivil = estadoCivil;
		return this;
	}

	@JsonProperty("estadoCivil")
	public String getEstadoCivil() {
		return estadoCivil;
	}
	
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
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
	public ColegiadoItem idgrupo(String idgrupo []){
		this.idgrupo = idgrupo;
		return this;
	}

	@JsonProperty("idgrupo")
	public String [] getIdgrupo() {
		return idgrupo;
	}
	
	public void setIdgrupo(String idgrupo []) {
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

	
	
}
