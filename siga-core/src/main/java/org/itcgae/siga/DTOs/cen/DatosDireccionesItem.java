package org.itcgae.siga.DTOs.cen;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class DatosDireccionesItem {

	private String fechaBaja;
	private String tipoDireccion;
	private String idDireccion;
	private String codigoPostal;
	private String domicilio;
	private String domicilioLista;
	private String idPoblacion;
	private String idProvincia;
	private String idPais;
	private String telefono;
	private String fax;
	private String movil;
	private String correoElectronico;
	private String idExternoPais;
	private String nombrePais;
	private String idExternoPoblacion;
	private String nombrePoblacion;
	private String idExternoProvincia;
	private String nombreProvincia;
	private String paginaWeb;
	private String idPersona;
	private String otraProvincia;
	private String[] idTipoDireccion;
	private String idTipoDireccionList;
	private String fechaModificacion;
	private String motivo;
	private boolean esColegiado;
	private String nombrepartido;
	
	
	
	@JsonProperty("motivo")
	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	

	public String getTipoDireccion() {
		return tipoDireccion;
	}

	public void setTipoDireccion(String tipoDireccion) {
		this.tipoDireccion = tipoDireccion;
	}

	public String getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(String idDireccion) {
		this.idDireccion = idDireccion;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	

	public String getIdPoblacion() {
		return idPoblacion;
	}

	public void setIdPoblacion(String idPoblacion) {
		this.idPoblacion = idPoblacion;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getIdPais() {
		return idPais;
	}

	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}

	

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getIdExternoPais() {
		return idExternoPais;
	}

	public void setIdExternoPais(String idExternoPais) {
		this.idExternoPais = idExternoPais;
	}

	public String getNombrePais() {
		return nombrePais;
	}

	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}

	public String getIdExternoPoblacion() {
		return idExternoPoblacion;
	}

	public void setIdExternoPoblacion(String idExternoPoblacion) {
		this.idExternoPoblacion = idExternoPoblacion;
	}

	public String getNombrePoblacion() {
		return nombrePoblacion;
	}

	public void setNombrePoblacion(String nombrePoblacion) {
		this.nombrePoblacion = nombrePoblacion;
	}

	public String getIdExternoProvincia() {
		return idExternoProvincia;
	}

	public void setIdExternoProvincia(String idExternoProvincia) {
		this.idExternoProvincia = idExternoProvincia;
	}

	public String getNombreProvincia() {
		return nombreProvincia;
	}

	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}
	
	private String idInstitucion;
	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}


	public String getPaginaWeb() {
		return paginaWeb;
	}

	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	public String getDomicilioLista() {
		return domicilioLista;
	}

	public void setDomicilioLista(String domicilioLista) {
		this.domicilioLista = domicilioLista;
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getOtraProvincia() {
		return otraProvincia;
	}

	public void setOtraProvincia(String otraProvincia) {
		this.otraProvincia = otraProvincia;
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

	public String[] getIdTipoDireccion() {
		return idTipoDireccion;
	}

	public void setIdTipoDireccion(String[] idTipoDireccion) {
		this.idTipoDireccion = idTipoDireccion;
	}
	
	public String getIdTipoDireccionList() {
		return idTipoDireccionList;
	}

	public void setIdTipoDireccionList(String idTipoDireccionList) {
		this.idTipoDireccionList = idTipoDireccionList;
	}

	public String getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
	public boolean isEsColegiado() {
		return esColegiado;
	}

	public void setEsColegiado(boolean esColegiado) {
		this.esColegiado = esColegiado;
	}

	public String getNombrepartido() {
		return nombrepartido;
	}

	public void setNombrepartido(String nombrepartido) {
		this.nombrepartido = nombrepartido;
	}
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    DatosDireccionesItem datosDireccionesItem = (DatosDireccionesItem) o;
	    return Objects.equals(this.idInstitucion, datosDireccionesItem.idInstitucion) &&
	    		Objects.equals(this.tipoDireccion, datosDireccionesItem.tipoDireccion) &&
	    		Objects.equals(this.idDireccion, datosDireccionesItem.idDireccion) &&
	    		Objects.equals(this.codigoPostal, datosDireccionesItem.codigoPostal) &&
	    		Objects.equals(this.domicilio, datosDireccionesItem.domicilio) &&
	    		Objects.equals(this.domicilioLista, datosDireccionesItem.domicilioLista) &&
	    		Objects.equals(this.idPoblacion, datosDireccionesItem.idPoblacion) &&
	    		Objects.equals(this.idPersona, datosDireccionesItem.idPersona) &&
	    		Objects.equals(this.idProvincia, datosDireccionesItem.idProvincia) &&
	    		Objects.equals(this.idPais, datosDireccionesItem.idPais) &&
	    		Objects.equals(this.telefono, datosDireccionesItem.telefono) &&
	    		Objects.equals(this.fechaBaja, datosDireccionesItem.fechaBaja) &&
	    		Objects.equals(this.movil, datosDireccionesItem.movil) &&
	    		Objects.equals(this.correoElectronico, datosDireccionesItem.correoElectronico) &&
	    		Objects.equals(this.idExternoPais, datosDireccionesItem.idExternoPais) &&
	    		Objects.equals(this.nombrePais, datosDireccionesItem.nombrePais) &&
	    		Objects.equals(this.idExternoPoblacion, datosDireccionesItem.idExternoPoblacion) &&
	    		Objects.equals(this.nombrePoblacion, datosDireccionesItem.nombrePoblacion) &&
	    		Objects.equals(this.idExternoProvincia, datosDireccionesItem.idExternoProvincia) &&
	    		Objects.equals(this.nombreProvincia, datosDireccionesItem.nombreProvincia) &&
	    		Objects.equals(this.paginaWeb, datosDireccionesItem.paginaWeb) &&
	    		Objects.equals(this.otraProvincia, datosDireccionesItem.otraProvincia) &&
	    		Objects.equals(this.idTipoDireccion, datosDireccionesItem.idTipoDireccion) &&
	    		Objects.equals(this.fechaModificacion, datosDireccionesItem.fechaModificacion) &&
	    		Objects.equals(this.fax, datosDireccionesItem.fax) &&
	    		Objects.equals(this.motivo, datosDireccionesItem.motivo) &&
	    		Objects.equals(this.esColegiado, datosDireccionesItem.esColegiado) &&
	    		Objects.equals(this.nombrepartido, datosDireccionesItem.nombrepartido);

	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(idInstitucion,tipoDireccion,idDireccion,codigoPostal,domicilio,domicilioLista,idPoblacion,idProvincia,idPais,telefono,fechaBaja,movil,
	    		correoElectronico, idExternoPais, nombrePais, idExternoPoblacion, nombrePoblacion, idExternoProvincia, fechaModificacion,nombreProvincia, fax,paginaWeb,idPersona,otraProvincia,idTipoDireccion,
	    		motivo, esColegiado, nombrepartido);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class DatosDireccionesItem {\n");
	    
	    sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
	    sb.append("    tipoDireccion: ").append(toIndentedString(tipoDireccion)).append("\n");
	    sb.append("    idDireccion: ").append(toIndentedString(idDireccion)).append("\n");
	    sb.append("    codigoPostal: ").append(toIndentedString(codigoPostal)).append("\n");
	    sb.append("    domicilio: ").append(toIndentedString(domicilio)).append("\n");
	    sb.append("    domicilioLista: ").append(toIndentedString(domicilioLista)).append("\n");
	    sb.append("    idPoblacion: ").append(toIndentedString(idPoblacion)).append("\n");
	    sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
	    sb.append("    idProvincia: ").append(toIndentedString(idProvincia)).append("\n");
	    sb.append("    idPais: ").append(toIndentedString(idPais)).append("\n");
	    sb.append("    telefono: ").append(toIndentedString(telefono)).append("\n");
	    sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
	    sb.append("    movil: ").append(toIndentedString(movil)).append("\n");
	    sb.append("    correoElectronico: ").append(toIndentedString(correoElectronico)).append("\n");
	    sb.append("    idExternoPais: ").append(toIndentedString(idExternoPais)).append("\n");
	    sb.append("    nombrePais: ").append(toIndentedString(nombrePais)).append("\n");
	    sb.append("    idExternoPoblacion: ").append(toIndentedString(idExternoPoblacion)).append("\n");
	    sb.append("    nombrePoblacion: ").append(toIndentedString(nombrePoblacion)).append("\n");
	    sb.append("    nombreProvincia: ").append(toIndentedString(nombreProvincia)).append("\n");
	    sb.append("    idExternoProvincia: ").append(toIndentedString(idExternoProvincia)).append("\n");
	    sb.append("    fax: ").append(toIndentedString(fax)).append("\n");
	    sb.append("    otraProvincia: ").append(toIndentedString(otraProvincia)).append("\n");
	    sb.append("    idTipoDireccion: ").append(toIndentedString(idTipoDireccion)).append("\n");
	    sb.append("    paginaweb: ").append(toIndentedString(paginaWeb)).append("\n");
	    sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
	    sb.append("    motivo: ").append(toIndentedString(motivo)).append("\n");
	    sb.append("    esColegiado: ").append(toIndentedString(esColegiado)).append("\n");
	    sb.append("    nombrepartido: ").append(toIndentedString(nombrepartido)).append("\n");

	    
	    sb.append("}");
	    return sb.toString();
	}


	
}
