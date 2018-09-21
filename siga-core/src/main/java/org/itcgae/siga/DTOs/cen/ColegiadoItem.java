package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

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
	private Date fechaBaja;
	
	private String  apellidos;
	private Date [] fechaIncorporacion;
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
	private Date incorporacion;
	private Date fechaJura;
	private Date fechaTitulacion;

	
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
	public ColegiadoItem fechaBaja(Date fechaBaja){
		this.fechaBaja = fechaBaja;
		return this;
	}

	@JsonProperty("fechaBaja")
	public Date getFechaBaja() {
		return fechaBaja;
	}
	
	public void setFechaBaja(Date fechaBaja) {
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
	public String getNMutualista() {
		return nMutualista;
	}
	
	public void setNMutualista(String nMutualista) {
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
	public ColegiadoItem incorporacion(Date incorporacion){
		this.incorporacion = incorporacion;
		return this;
	}

	@JsonProperty("incorporacion")
	public Date getIncorporacion() {
		return incorporacion;
	}
	
	public void setIncorporacion(Date incorporacion) {
		this.incorporacion = incorporacion;
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
	
	/**
	 *
	 */
	public ColegiadoItem fechaJura(Date fechaJura){
		this.fechaJura = fechaJura;
		return this;
	}

	@JsonProperty("fechaJura")
	public Date getFechaJura() {
		return fechaJura;
	}
	
	public void setFechaJura(Date fechaJura) {
		this.fechaJura = fechaJura;
	}

	/**
	 *
	 */
	public ColegiadoItem fechaTitulacion(Date fechaTitulacion){
		this.fechaTitulacion = fechaTitulacion;
		return this;
	}

	@JsonProperty("fechaTitulacion")
	public Date getFechaTitulacion() {
		return fechaTitulacion;
	}
	
	public void setFechaTitulacion(Date fechaTitulacion) {
		this.fechaTitulacion = fechaTitulacion;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idPersona, idInstitucion, nif, nombre, numColegiado, residenteInscrito,	fechaNacimiento, correo, telefono, movil, estadoColegial,
	    		fechaBaja, apellidos, fechaIncorporacion, situacion, residencia, inscrito, idProvincia, idPoblacion, codigoPostal, tipoDireccion, sexo, 
	    		estadoCivil, domicilio, tipoCV, idgrupo, denominacion, soloNombre, apellidos1, apellidos2,idTipoIdentificacion, naturalDe, idLenguaje,
	    		asientoContable, nMutualista, idTiposSeguro, partidoJudicial, comisiones, incorporacion, fechaJura, fechaTitulacion);
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
	    sb.append("    estadoCivil: ").append(toIndentedString(estadoCivil)).append("\n");
	    sb.append("    domicilio: ").append(toIndentedString(domicilio)).append("\n");
	    sb.append("    tipoCV: ").append(toIndentedString(tipoCV)).append("\n");
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
	
}
