package org.itcgae.siga.DTOs.cen;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NoColegiadoItem {

	private String idPersona;
	private String idInstitucion;
	private String nif;
	private String nombre;
	private String fechaNacimiento;
	private String correo;
	private String telefono;
	private String movil;
	private String fechaBaja;

	private String  idProvincia;
	private String  idPoblacion;
	private String  codigoPostal;
	private String  tipoDireccion;
	private String  apellidos;
	private String  etiquetas [];
	private String  sexo;
	private String  estadoCivil;
	private String  subCategoria;
	private String domicilio;
	private String idcv;
	private boolean  historico;
	
	/**
	 *
	 */
	public NoColegiadoItem idPersona(String idPersona){
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
	public NoColegiadoItem idInstitucion(String idInstitucion){
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
	public NoColegiadoItem nif(String nif){
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
	public NoColegiadoItem nombre(String nombre){
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
	public NoColegiadoItem fechaNacimiento(String fechaNacimiento){
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
	public NoColegiadoItem correo(String correo){
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
	public NoColegiadoItem telefono(String telefono){
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
	public NoColegiadoItem movil(String movil){
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
	public NoColegiadoItem fechaBaja(String fechaBaja){
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
	public NoColegiadoItem idProvincia(String idProvincia){
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
	public NoColegiadoItem idPoblacion(String idPoblacion){
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
	public NoColegiadoItem codigoPostal(String codigoPostal){
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
	public NoColegiadoItem tipoDireccion(String tipoDireccion){
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
	public NoColegiadoItem apellidos(String apellidos){
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
	public NoColegiadoItem etiquetas(String etiquetas []){
		this.etiquetas = etiquetas;
		return this;
	}

	@JsonProperty("etiquetas")
	public String [] getEtiquetas() {
		return etiquetas;
	}
	
	public void setEtiquetas(String etiquetas []) {
		this.etiquetas = etiquetas;
	}
	
	/**
	 *
	 */
	public NoColegiadoItem sexo(String sexo){
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
	public NoColegiadoItem estadoCivil(String estadoCivil){
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
	public NoColegiadoItem subCategoria(String subCategoria){
		this.subCategoria = subCategoria;
		return this;
	}

	@JsonProperty("subCategoria")
	public String getSubCategoria() {
		return subCategoria;
	}
	
	public void setSubCategoria(String subCategoria) {
		this.subCategoria = subCategoria;
	}
	
	/**
	 *
	 */
	public NoColegiadoItem domicilio(String domicilio){
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
	public NoColegiadoItem idcv(String idcv){
		this.idcv = idcv;
		return this;
	}

	@JsonProperty("idcv")
	public String getIdcv() {
		return idcv;
	}
	
	public void setIdcv(String idcv) {
		this.idcv = idcv;
	}
	
	/**
	 *
	 */
	public NoColegiadoItem historico(boolean historico){
		this.historico = historico;
		return this;
	}
	

	@JsonProperty("historico")
	public boolean isHistorico() {
		return historico;
	}
	
	public void setHistorico(boolean historico) {
		this.historico = historico;
	}
	
	
}
