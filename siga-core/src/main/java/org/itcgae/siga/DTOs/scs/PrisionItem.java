package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrisionItem {

	private String idPrision;
	private String nombre;
	private String domicilio;
	private String idInstitucion;
	private Long usuModificacion;
	private Date fechaModificacion;
	private String codigoPostal;
	private String idPoblacion;
	private String idProvincia;
	private boolean historico;
	private String fechaBaja;
	private String telefono1;
	private String telefono2;
	private String fax;
	private String codigoExt;
	private String movil;
	private String email;
	private String nombrePoblacion;
	private String nombreProvincia;
	private Short visibleMovil;
	

	public Short getVisibleMovil() {
		return visibleMovil;
	}

	public void setVisibleMovil(Short visibleMovil) {
		this.visibleMovil = visibleMovil;
	}

	/**
	 **/
	public PrisionItem idPrision(String idPrision) {
		this.idPrision = idPrision;
		return this;
	}

	@JsonProperty("idPrision")
	public String getIdPrision() {
		return idPrision;
	}

	public void setIdPrision(String idJuzgado) {
		this.idPrision = idJuzgado;
	}

	/**
	 * 
	 **/
	public PrisionItem nombre(String nombre) {
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
	 **/
	public PrisionItem domicilio(String domicilio) {
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
	 **/
	public PrisionItem idInstitucion(String idInstitucion) {
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
	 **/
	public PrisionItem usuModificacion(Long usuModificacion) {
		this.usuModificacion = usuModificacion;
		return this;
	}

	@JsonProperty("usuModificacion")
	public Long getUsuModificacion() {
		return usuModificacion;
	}

	public void setUsuModificacion(Long usuModificacion) {
		this.usuModificacion = usuModificacion;
	}

	/**
	 * 
	 **/
	public PrisionItem fechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
		return this;
	}

	@JsonProperty("fechaModificacion")
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	/**
	 * 
	 **/
	public PrisionItem descripcionsubzona(String codigoPostal) {
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
	 **/
	public PrisionItem idPoblacion(String idPoblacion) {
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
	 **/
	public PrisionItem idProvincia(String idProvincia) {
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
	 **/
	public PrisionItem historico(boolean historico) {
		this.historico = historico;
		return this;
	}

	@JsonProperty("historico")
	public boolean getHistorico() {
		return historico;
	}

	public void setHistorico(boolean historico) {
		this.historico = historico;
	}
	
	/**
	 * 
	 **/
	public PrisionItem fechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
		return this;
	}

	@JsonProperty("fechaBaja")
	public String getFechabaja() {
		return fechaBaja;
	}

	public void setFechabaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	/**
	 **/
	public PrisionItem telefono1(String telefono1) {
		this.telefono1 = telefono1;
		return this;
	}

	@JsonProperty("telefono1")
	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}
	
	/**
	 **/
	public PrisionItem telefono2(String telefono2) {
		this.telefono2 = telefono2;
		return this;
	}

	@JsonProperty("telefono2")
	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}
	
	/**
	 **/
	public PrisionItem fax(String fax) {
		this.fax = fax;
		return this;
	}
	
	@JsonProperty("fax")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	/**
	 **/
	public PrisionItem codigoExt(String codigoExt) {
		this.codigoExt = codigoExt;
		return this;
	}
	
	@JsonProperty("codigoExt")
	public String getCodigoExt() {
		return codigoExt;
	}

	public void setCodigoExt(String codigoExt) {
		this.codigoExt = codigoExt;
	}

	/**
	 **/
	public PrisionItem movil(String movil) {
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
	 **/
	public PrisionItem email(String email) {
		this.email = email;
		return this;
	}
	
	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 **/
	public PrisionItem nombrePoblacion(String nombrePoblacion) {
		this.nombrePoblacion = nombrePoblacion;
		return this;
	}

	@JsonProperty("nombrePoblacion")	
	public String getNombrePoblacion() {
		return nombrePoblacion;
	}

	public void setNombrePoblacion(String nombrePoblacion) {
		this.nombrePoblacion = nombrePoblacion;
	}

	/**
	 * 
	 **/
	public PrisionItem nombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
		return this;
	}

	@JsonProperty("nombreProvincia")
	public String getNombreProvincia() {
		return nombreProvincia;
	}

	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PrisionItem prisionItem = (PrisionItem) o;
		return Objects.equals(this.idPrision, prisionItem.idPrision)
				&& Objects.equals(this.nombre, prisionItem.nombre)
				&& Objects.equals(this.domicilio, prisionItem.domicilio)
				&& Objects.equals(this.usuModificacion, prisionItem.usuModificacion)
				&& Objects.equals(this.fechaModificacion, prisionItem.fechaModificacion)
				&& Objects.equals(this.codigoPostal, prisionItem.codigoPostal)
				&& Objects.equals(this.idProvincia, prisionItem.idProvincia)
				&& Objects.equals(this.idInstitucion, prisionItem.idInstitucion)
				&& Objects.equals(this.historico, prisionItem.historico)
				&& Objects.equals(this.fechaBaja, prisionItem.fechaBaja)
				&& Objects.equals(this.telefono1, prisionItem.telefono1)
				&& Objects.equals(this.telefono2, prisionItem.telefono2)
				&& Objects.equals(this.fax, prisionItem.fax)
				&& Objects.equals(this.codigoExt, prisionItem.codigoExt)
				&& Objects.equals(this.movil, prisionItem.movil)
				&& Objects.equals(this.email, prisionItem.email)
				&& Objects.equals(this.nombrePoblacion, prisionItem.nombrePoblacion)
				&& Objects.equals(this.nombreProvincia, prisionItem.nombreProvincia);

	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(idPrision, nombre, domicilio, usuModificacion, fechaModificacion, codigoPostal,
				idPoblacion, idProvincia, idInstitucion, historico, fechaBaja, telefono1, telefono2,
				fax, codigoExt, movil, email, nombrePoblacion, nombreProvincia);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class PrisionItem {\n");

		sb.append("    idPrision: ").append(toIndentedString(idPrision)).append("\n");
		sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
		sb.append("    domicilio: ").append(toIndentedString(domicilio)).append("\n");
		sb.append("    usuModificacion: ").append(toIndentedString(usuModificacion)).append("\n");
		sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
		sb.append("    codigoPostal: ").append(toIndentedString(codigoPostal)).append("\n");
		sb.append("    idPoblacion: ").append(toIndentedString(idPoblacion)).append("\n");
		sb.append("    idProvincia: ").append(toIndentedString(idProvincia)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    historico: ").append(toIndentedString(historico)).append("\n");
		sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
		sb.append("    telefono1: ").append(toIndentedString(telefono1)).append("\n");
		sb.append("    telefono2: ").append(toIndentedString(telefono2)).append("\n");
		sb.append("    fax: ").append(toIndentedString(fax)).append("\n");
		sb.append("    codigoExt: ").append(toIndentedString(codigoExt)).append("\n");
		sb.append("    movil: ").append(toIndentedString(movil)).append("\n");
		sb.append("    email: ").append(toIndentedString(email)).append("\n");
		sb.append("    nombrePoblacion: ").append(toIndentedString(nombrePoblacion)).append("\n");
		sb.append("    nombreProvincia: ").append(toIndentedString(nombreProvincia)).append("\n");

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
