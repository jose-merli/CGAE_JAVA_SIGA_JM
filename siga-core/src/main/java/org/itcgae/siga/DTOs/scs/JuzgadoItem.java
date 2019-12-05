package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JuzgadoItem {

	private String idJuzgado;
	private String nombre;
	private String domicilio;
	private String idInstitucion;
	private Long usuModificacion;
	private Date fechaModificacion;
	private String codigoPostal;
	private String idPoblacion;
	private String idProvincia;
	private boolean historico;
	private String fechabaja;
	private String telefono1;
	private String telefono2;
	private String fax;
	private String codigoExt;
	private String codigoProcurador;
	private String visible;
	private String movil;
	private String esDecano;
	private String codigoExt2;
	private String email;
	private String isCodigoEjis;
	private String visibleMovil;
	private Date fechaCodigoEjis;
	private String nombrePoblacion;
	private String nombreProvincia;
	private String codigoEjis;

	/**
	 **/
	public JuzgadoItem idJuzgado(String idJuzgado) {
		this.idJuzgado = idJuzgado;
		return this;
	}

	@JsonProperty("idJuzgado")
	public String getIdJuzgado() {
		return idJuzgado;
	}

	public void setIdJuzgado(String idJuzgado) {
		this.idJuzgado = idJuzgado;
	}

	/**
	 * 
	 **/
	public JuzgadoItem nombre(String nombre) {
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
	
	@JsonProperty("codigoEjis")
	public String getCodigoEjis() {
		return codigoEjis;
	}

	public void setCodigoEjis(String codigoEjis) {
		this.codigoEjis = codigoEjis;
	}

	/**
	 * 
	 **/
	public JuzgadoItem domicilio(String domicilio) {
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
	public JuzgadoItem idInstitucion(String idInstitucion) {
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
	public JuzgadoItem usuModificacion(Long usuModificacion) {
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
	public JuzgadoItem fechaModificacion(Date fechaModificacion) {
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
	public JuzgadoItem descripcionsubzona(String codigoPostal) {
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
	public JuzgadoItem idPoblacion(String idPoblacion) {
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
	public JuzgadoItem idProvincia(String idProvincia) {
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
	public JuzgadoItem historico(boolean historico) {
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
	public JuzgadoItem fechabaja(String fechabaja) {
		this.fechabaja = fechabaja;
		return this;
	}

	@JsonProperty("fechabaja")
	public String getFechabaja() {
		return fechabaja;
	}

	public void setFechabaja(String fechabaja) {
		this.fechabaja = fechabaja;
	}
	
	/**
	 **/
	public JuzgadoItem telefono1(String telefono1) {
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
	public JuzgadoItem telefono2(String telefono2) {
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
	public JuzgadoItem fax(String fax) {
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
	public JuzgadoItem codigoExt(String codigoExt) {
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
	public JuzgadoItem codigoProcurador(String codigoProcurador) {
		this.codigoProcurador = codigoProcurador;
		return this;
	}
	
	@JsonProperty("codigoProcurador")
	public String getCodigoProcurador() {
		return codigoProcurador;
	}

	public void setCodigoProcurador(String codigoProcurador) {
		this.codigoProcurador = codigoProcurador;
	}

	/**
	 **/
	public JuzgadoItem visible(String visible) {
		this.visible = visible;
		return this;
	}
	
	@JsonProperty("visible")
	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	/**
	 **/
	public JuzgadoItem movil(String movil) {
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
	public JuzgadoItem esDecano(String esDecano) {
		this.esDecano = esDecano;
		return this;
	}
	
	@JsonProperty("esDecano")
	public String getEsDecano() {
		return esDecano;
	}

	public void setEsDecano(String esDecano) {
		this.esDecano = esDecano;
	}
	
	/**
	 **/
	public JuzgadoItem codigoExt2(String codigoExt2) {
		this.codigoExt2 = codigoExt2;
		return this;
	}
	
	@JsonProperty("codigoExt2")
	public String getCodigoExt2() {
		return codigoExt2;
	}

	public void setCodigoExt2(String codigoExt2) {
		this.codigoExt2 = codigoExt2;
	}

	/**
	 **/
	public JuzgadoItem email(String email) {
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
	 **/
	public JuzgadoItem isCodigoEjis(String isCodigoEjis) {
		this.isCodigoEjis = isCodigoEjis;
		return this;
	}
	
	@JsonProperty("isCodigoEjis")
	public String getIsCodigoEjis() {
		return isCodigoEjis;
	}

	public void setIsCodigoEjis(String isCodigoEjis) {
		this.isCodigoEjis = isCodigoEjis;
	}

	/**
	 **/
	public JuzgadoItem visibleMovil(String visibleMovil) {
		this.visibleMovil = visibleMovil;
		return this;
	}
	
	@JsonProperty("visibleMovil")
	public String getVisibleMovil() {
		return visibleMovil;
	}

	public void setVisibleMovil(String visibleMovil) {
		this.visibleMovil = visibleMovil;
	}

	/**
	 **/
	public JuzgadoItem fechaCodigoEjis(Date fechaCodigoEjis) {
		this.fechaCodigoEjis = fechaCodigoEjis;
		return this;
	}
	
	@JsonProperty("fechaCodigoEjis")
	public Date getFechaCodigoEjis() {
		return fechaCodigoEjis;
	}

	public void setFechaCodigoEjis(Date fechaCodigoEjis) {
		this.fechaCodigoEjis = fechaCodigoEjis;
	}

	/**
	 * 
	 **/
	public JuzgadoItem nombrePoblacion(String nombrePoblacion) {
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
	public JuzgadoItem nombreProvincia(String nombreProvincia) {
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
		JuzgadoItem juzgadoItem = (JuzgadoItem) o;
		return Objects.equals(this.idJuzgado, juzgadoItem.idJuzgado)
				&& Objects.equals(this.nombre, juzgadoItem.nombre)
				&& Objects.equals(this.domicilio, juzgadoItem.domicilio)
				&& Objects.equals(this.usuModificacion, juzgadoItem.usuModificacion)
				&& Objects.equals(this.fechaModificacion, juzgadoItem.fechaModificacion)
				&& Objects.equals(this.codigoPostal, juzgadoItem.codigoPostal)
				&& Objects.equals(this.idProvincia, juzgadoItem.idProvincia)
				&& Objects.equals(this.idInstitucion, juzgadoItem.idInstitucion)
				&& Objects.equals(this.historico, juzgadoItem.historico)
				&& Objects.equals(this.fechabaja, juzgadoItem.fechabaja)
				&& Objects.equals(this.telefono1, juzgadoItem.telefono1)
				&& Objects.equals(this.telefono2, juzgadoItem.telefono2)
				&& Objects.equals(this.fax, juzgadoItem.fax)
				&& Objects.equals(this.codigoExt, juzgadoItem.codigoExt)
				&& Objects.equals(this.codigoProcurador, juzgadoItem.codigoProcurador)
				&& Objects.equals(this.visible, juzgadoItem.visible)
				&& Objects.equals(this.movil, juzgadoItem.movil)
				&& Objects.equals(this.esDecano, juzgadoItem.esDecano)
				&& Objects.equals(this.codigoExt2, juzgadoItem.codigoExt2)
				&& Objects.equals(this.email, juzgadoItem.email)
				&& Objects.equals(this.isCodigoEjis, juzgadoItem.isCodigoEjis)
				&& Objects.equals(this.visibleMovil, juzgadoItem.visibleMovil)
				&& Objects.equals(this.fechaCodigoEjis, juzgadoItem.fechaCodigoEjis)
				&& Objects.equals(this.nombrePoblacion, juzgadoItem.nombrePoblacion)
				&& Objects.equals(this.nombreProvincia, juzgadoItem.nombreProvincia);

	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(idJuzgado, nombre, domicilio, usuModificacion, fechaModificacion, codigoPostal,
				idPoblacion, idProvincia, idInstitucion, historico, fechabaja, telefono1, telefono2,
				fax, codigoExt, codigoProcurador, visible, movil, esDecano, codigoExt2, email, isCodigoEjis,
				visibleMovil, fechaCodigoEjis, nombrePoblacion, nombreProvincia);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class JuzgadoItem {\n");

		sb.append("    idJuzgado: ").append(toIndentedString(idJuzgado)).append("\n");
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
		sb.append("    fax: ").append(toIndentedString(fax)).append("\n");
		sb.append("    codigoExt: ").append(toIndentedString(codigoExt)).append("\n");
		sb.append("    codigoProcurador: ").append(toIndentedString(codigoProcurador)).append("\n");
		sb.append("    visible: ").append(toIndentedString(visible)).append("\n");
		sb.append("    movil: ").append(toIndentedString(movil)).append("\n");
		sb.append("    esDecano: ").append(toIndentedString(esDecano)).append("\n");
		sb.append("    codigoExt2: ").append(toIndentedString(codigoExt2)).append("\n");
		sb.append("    email: ").append(toIndentedString(email)).append("\n");
		sb.append("    isCodigoEjis: ").append(toIndentedString(isCodigoEjis)).append("\n");
		sb.append("    visibleMovil: ").append(toIndentedString(visibleMovil)).append("\n");
		sb.append("    fechaCodigoEjis: ").append(toIndentedString(fechaCodigoEjis)).append("\n");
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
