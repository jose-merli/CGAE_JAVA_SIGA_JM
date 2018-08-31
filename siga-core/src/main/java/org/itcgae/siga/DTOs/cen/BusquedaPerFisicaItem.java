package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class BusquedaPerFisicaItem {

	private String colegio;
	private String idPersona;
	private String nif;
	private String nombre;
	private String apellidos;
	private String primerApellido;
	private String segundoApellido;
	private String numeroColegiado;
	private String residente;
	private String situacion;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaNacimiento;
	private String numeroInstitucion;
	private String idProvincia;
	private String idActividadProfesional;
	
	
	

	/**
	 *
	 */
	public BusquedaPerFisicaItem busquedaFisicaItems(String colegio){
		this.colegio = colegio;
		return this;
	}
	
	
	@JsonProperty("colegio")
	public String getColegio() {
		return colegio;
	}
	
	
	public void setColegio(String colegio) {
		this.colegio = colegio;
	}
	
	/**
	 *
	 */
	public BusquedaPerFisicaItem idPersona(String idPersona){
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
	public BusquedaPerFisicaItem nif(String nif){
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
	public BusquedaPerFisicaItem nombre(String nombre){
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
	public BusquedaPerFisicaItem apellidos(String apellidos){
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
	public BusquedaPerFisicaItem primerApellido(String primerApellido){
		this.primerApellido = primerApellido;
		return this;
	}
	
	
	@JsonProperty("primerApellido")
	public String getPrimerApellido() {
		return primerApellido;
	}
	
	
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	
	
	/**
	 *
	 */
	public BusquedaPerFisicaItem segundoApellido(String segundoApellido){
		this.segundoApellido = segundoApellido;
		return this;
	}
	
	
	@JsonProperty("segundoApellido")
	public String getSegundoApellido() {
		return segundoApellido;
	}
	
	
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	
	
	/**
	 *
	 */  
	
		
	public BusquedaPerFisicaItem numeroColegiado(String numeroColegiado){
		this.numeroColegiado = numeroColegiado;
		return this;
	}
	
	
	@JsonProperty("numeroColegiado")
	public String getNumeroColegiado() {
		return numeroColegiado;
	}
	
	
	public void setNumeroColegiado(String numeroColegiado) {
		this.numeroColegiado = numeroColegiado;
	}
	
	
	/**
	 *
	 */
	public BusquedaPerFisicaItem residente(String residente){
		this.residente = residente;
		return this;
	}
	
	
	@JsonProperty("residente")
	public String getResidente() {
		return residente;
	}
	
	
	public void setResidente(String residente) {
		this.residente = residente;
	}
	
	
	/**
	 *
	 */
	public BusquedaPerFisicaItem situacion(String situacion){
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
	public BusquedaPerFisicaItem fechaNacimiento(Date fechaNacimiento){
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
	public BusquedaPerFisicaItem numeroInstitucion(String numeroInstitucion){
		this.numeroInstitucion = numeroInstitucion;
		return this;
	}


	@JsonProperty("numeroInstitucion")
	public String getNumeroInstitucion() {
		return numeroInstitucion;
	}


	public void setNumeroInstitucion(String numeroInstitucion) {
		this.numeroInstitucion = numeroInstitucion;
	}
	
	/**
	 *
	 */
	public BusquedaPerFisicaItem idProvincia(String idProvincia){
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
	public BusquedaPerFisicaItem idActividadProfesional(String idActividadProfesional){
		this.idActividadProfesional = idActividadProfesional;
		return this;
	}
	
	@JsonProperty("idActividadProfesional")
	public String getIdActividadProfesional() {
		return idActividadProfesional;
	}


	public void setIdActividadProfesional(String idActividadProfesional) {
		this.idActividadProfesional = idActividadProfesional;
	}

	 
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    BusquedaPerFisicaItem busquedaFisicaItem = (BusquedaPerFisicaItem) o;
	    return Objects.equals(this.colegio, busquedaFisicaItem.colegio) &&
	    		Objects.equals(this.idPersona, busquedaFisicaItem.idPersona) &&
	    		Objects.equals(this.nif, busquedaFisicaItem.nif) &&
	    		Objects.equals(this.nombre, busquedaFisicaItem.nombre) &&
	    		Objects.equals(this.apellidos, busquedaFisicaItem.apellidos) &&
	    		Objects.equals(this.primerApellido, busquedaFisicaItem.primerApellido) &&
	    		Objects.equals(this.segundoApellido, busquedaFisicaItem.segundoApellido) &&
	    		Objects.equals(this.numeroColegiado, busquedaFisicaItem.numeroColegiado) &&
	    		Objects.equals(this.residente, busquedaFisicaItem.residente) &&
	    		Objects.equals(this.situacion, busquedaFisicaItem.situacion) &&
	    		Objects.equals(this.fechaNacimiento, busquedaFisicaItem.fechaNacimiento) &&
	    		Objects.equals(this.numeroInstitucion, busquedaFisicaItem.numeroInstitucion) &&
	    		Objects.equals(this.idProvincia, busquedaFisicaItem.idProvincia) &&
	    		Objects.equals(this.idActividadProfesional, busquedaFisicaItem.idActividadProfesional);
	}
 
	
	@Override
	public int hashCode() {
	    return Objects.hash(colegio, idPersona, nif, nombre, apellidos, primerApellido, segundoApellido, numeroColegiado, residente, situacion, fechaNacimiento, numeroInstitucion, idProvincia, idActividadProfesional);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class BusquedaPerFisicaItem {\n");
	    
	    sb.append("    colegio: ").append(toIndentedString(colegio)).append("\n");
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
	    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
	    sb.append("    apellidos: ").append(toIndentedString(apellidos)).append("\n");
	    sb.append("    primerApellido: ").append(toIndentedString(primerApellido)).append("\n");
	    sb.append("    segundoApellido: ").append(toIndentedString(segundoApellido)).append("\n");
	    sb.append("    numeroColegiado: ").append(toIndentedString(numeroColegiado)).append("\n");
	    sb.append("    residente: ").append(toIndentedString(residente)).append("\n");
	    sb.append("    situacion: ").append(toIndentedString(situacion)).append("\n");
	    sb.append("    fechaNacimiento: ").append(toIndentedString(fechaNacimiento)).append("\n");
	    sb.append("    numeroInstitucion: ").append(toIndentedString(numeroInstitucion)).append("\n");
	    sb.append("    idProvincia: ").append(toIndentedString(idProvincia)).append("\n");
	    sb.append("    idActividadProfesional: ").append(toIndentedString(idActividadProfesional)).append("\n");
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
