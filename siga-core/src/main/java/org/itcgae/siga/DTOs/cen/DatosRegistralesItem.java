package org.itcgae.siga.DTOs.cen;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class DatosRegistralesItem {
	
	private String resena;
	private String objetoSocial;
	private String numeroPoliza;
	private String companiaAseg;
	//@JsonFormat(pattern = "dd-MM-yyyy")
	private String fechaFin;
	//@JsonFormat(pattern = "dd-MM-yyyy")
	private String fechaConstitucion;
	private String sociedadProfesional;
	private String fechaBaja;
	private Long idDatosRegistro;
	private String numRegistro;
	private Date fechaInscripcion;
	private Date fechaCancelacion;
	private String identificacionReg;
	private String prefijoNumsspp;
    private String contadorNumsspp;
    private String sufijoNumsspp;
	  
	@JsonProperty("prefijoNumsspp")
	public String getPrefijoNumsspp() {
		return prefijoNumsspp;
	}

	public void setPrefijoNumsspp(String prefijoNumsspp) {
		this.prefijoNumsspp = prefijoNumsspp;
	}
	@JsonProperty("contadorNumsspp")
	public String getContadorNumsspp() {
		return contadorNumsspp;
	}

	public void setContadorNumsspp(String contadorNumsspp) {
		this.contadorNumsspp = contadorNumsspp;
	}
	@JsonProperty("sufijoNumsspp")
	public String getSufijoNumsspp() {
		return sufijoNumsspp;
	}

	public void setSufijoNumsspp(String sufijoNumsspp) {
		this.sufijoNumsspp = sufijoNumsspp;
	}
	
	/**
	 */
	public DatosRegistralesItem identificacionReg(String identificacionReg){
		this.identificacionReg = identificacionReg;
		return this;
	}
	
	@JsonProperty("identificacionReg")
	public String getIdentificacionReg() {
		return identificacionReg;
	}

	public void setIdentificacionReg(String identificacionReg) {
		this.identificacionReg = identificacionReg;
	}


	/**
	 */
	public DatosRegistralesItem fechaCancelacion(Date fechaCancelacion){
		this.fechaCancelacion = fechaCancelacion;
		return this;
	}
	@JsonProperty("fechaCancelacion")
	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}
	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	/**
	 */
	public DatosRegistralesItem numRegistro(String numRegistro){
		this.numRegistro = numRegistro;
		return this;
	}
	@JsonProperty("numRegistro")
	public String getNumRegistro() {
		return numRegistro;
	}

	public void setNumRegistro(String numRegistro) {
		this.numRegistro = numRegistro;
	}
	
	/**
	 */
	public DatosRegistralesItem fechaInscripcion(Date fechaInscripcion){
		this.fechaInscripcion = fechaInscripcion;
		return this;
	}
	public Date getFechaInscripcion() {
		return fechaInscripcion;
	}
	public void setFechaInscripcion(Date fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}
	

	/**
	 */
	public DatosRegistralesItem resena(String resena){
		this.resena = resena;
		return this;
	}
	
	
	
	@JsonProperty("resena")
	public String getResena() {
		return resena;
	}
	
	
	public void setResena(String resena) {
		this.resena = resena;
	}
	
	
	/**
	 */
	public DatosRegistralesItem objetoSocial(String objetoSocial){
		this.objetoSocial = objetoSocial;
		return this;
	}
	
	@JsonProperty("objetoSocial")
	public String getObjetoSocial() {
		return objetoSocial;
	}
	
	
	public void setObjetoSocial(String objetoSocial) {
		this.objetoSocial = objetoSocial;
	}
	
	/**
	 */
	public DatosRegistralesItem numeroPoliza(String numeroPoliza){
		this.numeroPoliza = numeroPoliza;
		return this;
	}
	
	@JsonProperty("numeroPoliza")
	public String getNumeroPoliza() {
		return numeroPoliza;
	}
	
	
	public void setNumeroPoliza(String numeroPoliza) {
		this.numeroPoliza = numeroPoliza;
	}
	
	
	/**
	 */
	public DatosRegistralesItem companiaAseg(String companiaAseg){
		this.companiaAseg = companiaAseg;
		return this;
	}
	
	@JsonProperty("companiaAseg")
	public String getCompaniaAseg() {
		return companiaAseg;
	}
	
	
	public void setCompaniaAseg(String companiaAseg) {
		this.companiaAseg = companiaAseg;
	}
	
	
	/**
	 */
	public DatosRegistralesItem fechaBaja(String fechaBaja){
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
	 */
	public DatosRegistralesItem fechaFin(String fechaFin){
		this.fechaFin = fechaFin;
		return this;
	}
	
	@JsonProperty("fechaFin")
	public String getFechaFin() {
		return fechaFin;
	}
	
	
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
	/**
	 */
	public DatosRegistralesItem fechaConstitucion(String fechaConstitucion){
		this.fechaConstitucion = fechaConstitucion;
		return this;
	}
	
	@JsonProperty("fechaConstitucion")
	public String getFechaConstitucion() {
		return fechaConstitucion;
	}
	
	
	public void setFechaConstitucion(String fechaConstitucion) {
		this.fechaConstitucion = fechaConstitucion;
	}
	
	
	/**
	 */
	public DatosRegistralesItem sociedadProfesional(Long idDatosRegistro){
		this.idDatosRegistro = idDatosRegistro;
		return this;
	}
	
	@JsonProperty("idDatosRegistro")
	public Long getidDatosRegistro() {
		return idDatosRegistro;
	}
	
	
	public void setidDatosRegistro(Long idDatosRegistro) {
		this.idDatosRegistro = idDatosRegistro;
	}
	
	/**
	 */
	public DatosRegistralesItem sociedadProfesional(String sociedadProfesional){
		this.sociedadProfesional = sociedadProfesional;
		return this;
	}
	
	@JsonProperty("sociedadProfesional")
	public String getSociedadProfesional() {
		return sociedadProfesional;
	}
	
	
	public void setSociedadProfesional(String sociedadProfesional) {
		this.sociedadProfesional = sociedadProfesional;
	}
	
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    DatosRegistralesItem datosRegistralesItem = (DatosRegistralesItem) o;
	    return Objects.equals(this.resena, datosRegistralesItem.resena) &&
	    	   Objects.equals(this.objetoSocial, datosRegistralesItem.objetoSocial) &&
	    	   Objects.equals(this.numeroPoliza, datosRegistralesItem.numeroPoliza) &&
	    	   Objects.equals(this.companiaAseg, datosRegistralesItem.companiaAseg) &&
	    	   Objects.equals(this.fechaFin, datosRegistralesItem.fechaFin) &&
	    	   Objects.equals(this.fechaBaja, datosRegistralesItem.fechaBaja) &&
	    	   Objects.equals(this.fechaConstitucion, datosRegistralesItem.fechaConstitucion) &&
	    	   Objects.equals(this.sociedadProfesional, datosRegistralesItem.sociedadProfesional);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(resena, objetoSocial, numeroPoliza, companiaAseg, fechaFin, fechaBaja, fechaConstitucion, sociedadProfesional);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosRegistralesItem {\n");
	    
	    sb.append("    resena: ").append(toIndentedString(resena)).append("\n");
	    sb.append("    objetoSocial: ").append(toIndentedString(objetoSocial)).append("\n");
	    sb.append("    numeroPoliza: ").append(toIndentedString(numeroPoliza)).append("\n");
	    sb.append("    companiaAseg: ").append(toIndentedString(companiaAseg)).append("\n");
	    sb.append("    fechaFin: ").append(toIndentedString(fechaFin)).append("\n");
	    sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
	    sb.append("    fechaConstitucion: ").append(toIndentedString(fechaConstitucion)).append("\n");
	    sb.append("    sociedadProfesional: ").append(toIndentedString(sociedadProfesional)).append("\n");
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
