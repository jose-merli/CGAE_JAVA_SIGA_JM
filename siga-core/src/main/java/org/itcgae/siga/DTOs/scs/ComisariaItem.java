package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComisariaItem {

	private String idComisaria;
	private String nombre;
	private String domicilio;
	private String idInstitucion;
	private Long usuModificacion;
	private Date fechaModificacion;
	private String codigoPostal;
	private String idPoblacion;
	private String idProvincia;
	private boolean historico;
	private Date fechabaja;
	private String telefono1;
	private String telefono2;
	private String fax1;
	private String codigoExt;
	private String email;
	private String nombrePoblacion;
	private String nombreProvincia;
	private Short visibleMovil;
	
	@JsonProperty("fax1")
	public String getFax1() {
		return fax1;
	}

	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}
	
	@JsonProperty("fechabaja")
	public Date getFechaBaja() {
		return fechabaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechabaja = fechaBaja;
	}
	@JsonProperty("visibleMovil")
	public Short getVisibleMovil() {
		return visibleMovil;
	}

	public void setVisibleMovil(Short visibleMovil) {
		this.visibleMovil = visibleMovil;
	}

	/**
	 **/
	public ComisariaItem idComisaria(String idComisaria) {
		this.idComisaria = idComisaria;
		return this;
	}

	@JsonProperty("idComisaria")
	public String getIdComisaria() {
		return idComisaria;
	}

	public void setIdComisaria(String idComisaria) {
		this.idComisaria = idComisaria;
	}

	/**
	 * 
	 **/
	public ComisariaItem nombre(String nombre) {
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
	public ComisariaItem domicilio(String domicilio) {
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
	public ComisariaItem idInstitucion(String idInstitucion) {
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
	public ComisariaItem usuModificacion(Long usuModificacion) {
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
	public ComisariaItem fechaModificacion(Date fechaModificacion) {
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
	public ComisariaItem descripcionsubzona(String codigoPostal) {
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
	public ComisariaItem idPoblacion(String idPoblacion) {
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
	public ComisariaItem idProvincia(String idProvincia) {
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
	public ComisariaItem historico(boolean historico) {
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
	 **/
	public ComisariaItem telefono1(String telefono1) {
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
	public ComisariaItem telefono2(String telefono2) {
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
	/**
	 **/
	public ComisariaItem codigoExt(String codigoExt) {
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
	

	/**
	 **/
	public ComisariaItem email(String email) {
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
	public ComisariaItem nombrePoblacion(String nombrePoblacion) {
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
	public ComisariaItem nombreProvincia(String nombreProvincia) {
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
		ComisariaItem prisionItem = (ComisariaItem) o;
		return Objects.equals(this.idComisaria, prisionItem.idComisaria)
				&& Objects.equals(this.nombre, prisionItem.nombre)
				&& Objects.equals(this.domicilio, prisionItem.domicilio)
				&& Objects.equals(this.usuModificacion, prisionItem.usuModificacion)
				&& Objects.equals(this.fechaModificacion, prisionItem.fechaModificacion)
				&& Objects.equals(this.codigoPostal, prisionItem.codigoPostal)
				&& Objects.equals(this.idProvincia, prisionItem.idProvincia)
				&& Objects.equals(this.idInstitucion, prisionItem.idInstitucion)
				&& Objects.equals(this.historico, prisionItem.historico)
				&& Objects.equals(this.fechabaja, prisionItem.fechabaja)
				&& Objects.equals(this.telefono1, prisionItem.telefono1)
				&& Objects.equals(this.telefono2, prisionItem.telefono2)
				&& Objects.equals(this.codigoExt, prisionItem.codigoExt)
				&& Objects.equals(this.email, prisionItem.email)
				&& Objects.equals(this.nombrePoblacion, prisionItem.nombrePoblacion)
				&& Objects.equals(this.nombreProvincia, prisionItem.nombreProvincia);

	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(idComisaria, nombre, domicilio, usuModificacion, fechaModificacion, codigoPostal,
				idPoblacion, idProvincia, idInstitucion, historico, fechabaja, telefono1, telefono2,
				codigoExt, email, nombrePoblacion, nombreProvincia);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class PrisionItem {\n");

		sb.append("    idComisaria: ").append(toIndentedString(idComisaria)).append("\n");
		sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
		sb.append("    domicilio: ").append(toIndentedString(domicilio)).append("\n");
		sb.append("    usuModificacion: ").append(toIndentedString(usuModificacion)).append("\n");
		sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
		sb.append("    codigoPostal: ").append(toIndentedString(codigoPostal)).append("\n");
		sb.append("    idPoblacion: ").append(toIndentedString(idPoblacion)).append("\n");
		sb.append("    idProvincia: ").append(toIndentedString(idProvincia)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    historico: ").append(toIndentedString(historico)).append("\n");
		sb.append("    fechabaja: ").append(toIndentedString(fechabaja)).append("\n");
		sb.append("    telefono1: ").append(toIndentedString(telefono1)).append("\n");
		sb.append("    telefono2: ").append(toIndentedString(telefono2)).append("\n");
		sb.append("    codigoExt: ").append(toIndentedString(codigoExt)).append("\n");
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
