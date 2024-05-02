package org.itcgae.siga.DTOs.scs;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JusticiableItem {

	private String idPersona;
	private String idInstitucion;
	private String nif;
	private String nombre;
	private String asuntos;
	private Date fechaModificacion;
	private Date fechaNacimiento;
	private Date fechaAlta;
	private String idPais;
	private String apellidos;
	private String apellido1;
	private String apellido2;
	private String direccion;
	private String codigoPostal;
	private Short idProfesion;
	private String regimen_conyugal;
	private String idProvincia;
	private String idPoblacion;
	private Short idEstadoCivil;
	private String tipoPersonaJG;
	private Short idTipoIdentificacion;
	private String observaciones;
	private Long idRepresentantejg;
	private Short idTipoencalidad;
	private String sexo;
	private String idLenguaje;
	private Short numeroHijos;
	private String fax;
	private String correoElectronico;
	private Short edad;
	private Short idMinusvalia;
	private String existeDomicilio;
	private String idProvincia2;
	private String idPoblacion2;
	private String direccion2;
	private String codigoPostal2;
	private String idTipodir;
	private String idTipodir2;
	private String cnae;
	private String idTipoVia;
	private String numeroDir;
	private String escaleraDir;
	private String pisoDir;
	private String puertaDir;
	private String idTipoVia2;
	private String numeroDir2;
	private String escaleraDir2;
	private String pisoDir2;
	private String puertaDir2;
	private String idpaisDir1;
	private String idpaisDir2;
	private String descPaisDir1;
	private String descPaisDir2;
	private String idTipoIdentificacionotros;
	private String asistidoSolicitajg;
	private String asistidoAutorizaeejg;
	private String autorizaAvisoTelematico;
	private List<JusticiableTelefonoItem> telefonos;
	private String parentesco;
	private String tipoJusticiable;
	private boolean checkNoInformadaDireccion;

	private AsuntosJusticiableItem [] datosAsuntos;
	private boolean validacionRepeticion;
	private Boolean asociarRepresentante;

	
	/**
	 **/
	public JusticiableItem idPersona(String idPersona) {
		this.idPersona = idPersona;
		return this;
	}

	@JsonProperty("idpersona")
	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 **/
	public JusticiableItem idInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
		return this;
	}

	@JsonProperty("idinstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 **/
	public JusticiableItem nif(String nif) {
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
	 **/
	public JusticiableItem nombre(String nombre) {
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
	 **/
	public JusticiableItem asuntos(String asuntos) {
		this.asuntos = asuntos;
		return this;
	}

	@JsonProperty("asuntos")
	public String getAsuntos() {
		return asuntos;
	}

	public void setAsuntos(String asuntos) {
		this.asuntos = asuntos;
	}

	/**
	 **/
	public JusticiableItem fechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
		return this;
	}

	@JsonProperty("fechamodificacion")
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
	/**
	 **/
	public JusticiableItem fechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
		return this;
	}

	@JsonProperty("fechaalta")
	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	/**
	 **/
	public JusticiableItem idPoblacion(String idPoblacion) {
		this.idPoblacion = idPoblacion;
		return this;
	}

	@JsonProperty("idpoblacion")
	public String getIdPoblacion() {
		return idPoblacion;
	}

	public void setIdPoblacion(String idPoblacion) {
		this.idPoblacion = idPoblacion;
	}

	/**
	 **/
	public JusticiableItem idProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
		return this;
	}

	@JsonProperty("idprovincia")
	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	/**
	 **/
	public JusticiableItem codigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
		return this;
	}

	@JsonProperty("codigopostal")
	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 **/
	public JusticiableItem fechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
		return this;
	}

	@JsonProperty("fechanacimiento")
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 **/
	public JusticiableItem idPais(String idPais) {
		this.idPais = idPais;
		return this;
	}

	@JsonProperty("idpais")
	public String getIdPais() {
		return idPais;
	}

	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}

	/**
	 **/
	public JusticiableItem apellidos(String apellidos) {
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
	 **/
	public JusticiableItem apellido1(String apellido1) {
		this.apellido1 = apellido1;
		return this;
	}

	@JsonProperty("apellido1")
	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	/**
	 **/
	public JusticiableItem apellido2(String apellido2) {
		this.apellido2 = apellido2;
		return this;
	}

	@JsonProperty("apellido2")
	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	/**
	 **/
	public JusticiableItem direccion(String direccion) {
		this.direccion = direccion;
		return this;
	}

	@JsonProperty("direccion")
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 **/
	public JusticiableItem idProfesion(Short idProfesion) {
		this.idProfesion = idProfesion;
		return this;
	}

	@JsonProperty("idprofesion")
	public Short getIdProfesion() {
		return idProfesion;
	}

	public void setIdProfesion(Short idProfesion) {
		this.idProfesion = idProfesion;
	}

	/**
	 **/
	public JusticiableItem regimen_conyugal(String regimen_conyugal) {
		this.regimen_conyugal = regimen_conyugal;
		return this;
	}

	@JsonProperty("regimenConyugal")
	public String getRegimen_conyugal() {
		return regimen_conyugal;
	}

	public void setRegimen_conyugal(String regimen_conyugal) {
		this.regimen_conyugal = regimen_conyugal;
	}

	/**
	 **/
	public JusticiableItem idEstadoCivil(Short idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
		return this;
	}

	@JsonProperty("idestadocivil")
	public Short getIdEstadoCivil() {
		return idEstadoCivil;
	}

	public void setIdEstadoCivil(Short idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}

	/**
	 **/
	public JusticiableItem tipoPersonajg(String tipoPersonajg) {
		this.tipoPersonaJG = tipoPersonajg;
		return this;
	}

	@JsonProperty("tipopersonajg")
	public String getTipoPersonajg() {
		return tipoPersonaJG;
	}

	public void setTipoPersonajg(String tipoPersonajg) {
		this.tipoPersonaJG = tipoPersonajg;
	}

	/**
	 **/
	public JusticiableItem idTipoIdentificacion(Short idTipoIdentificacion) {
		this.idTipoIdentificacion = idTipoIdentificacion;
		return this;
	}

	@JsonProperty("idtipoidentificacion")
	public Short getIdTipoIdentificacion() {
		return idTipoIdentificacion;
	}

	public void setIdTipoIdentificacion(Short idTipoIdentificacion) {
		this.idTipoIdentificacion = idTipoIdentificacion;
	}

	/**
	 **/
	public JusticiableItem observaciones(String observaciones) {
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
	 **/
	public JusticiableItem idRepresentantejg(Long idRepresentantejg) {
		this.idRepresentantejg = idRepresentantejg;
		return this;
	}

	@JsonProperty("idrepresentantejg")
	public Long getIdRepresentantejg() {
		return idRepresentantejg;
	}

	public void setIdRepresentantejg(Long idRepresentantejg) {
		this.idRepresentantejg = idRepresentantejg;
	}

	/**
	 **/
	public JusticiableItem idTipoencalidad(Short idTipoencalidad) {
		this.idTipoencalidad = idTipoencalidad;
		return this;
	}

	@JsonProperty("idtipoencalidad")
	public Short getIdTipoencalidad() {
		return idTipoencalidad;
	}

	public void setIdTipoencalidad(Short idTipoencalidad) {
		this.idTipoencalidad = idTipoencalidad;
	}

	/**
	 **/
	public JusticiableItem sexo(String sexo) {
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
	 **/
	public JusticiableItem idLenguaje(String idLenguaje) {
		this.idLenguaje = idLenguaje;
		return this;
	}

	@JsonProperty("idlenguaje")
	public String getIdLenguaje() {
		return idLenguaje;
	}

	public void setIdLenguaje(String idLenguaje) {
		this.idLenguaje = idLenguaje;
	}

	/**
	 **/
	public JusticiableItem numeroHijos(Short numeroHijos) {
		this.numeroHijos = numeroHijos;
		return this;
	}

	@JsonProperty("numerohijos")
	public Short getNumeroHijos() {
		return numeroHijos;
	}

	public void setNumeroHijos(Short numeroHijos) {
		this.numeroHijos = numeroHijos;
	}

	/**
	 **/
	public JusticiableItem fax(String fax) {
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
	public JusticiableItem correoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
		return this;
	}

	@JsonProperty("correoelectronico")
	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	/**
	 **/
	public JusticiableItem edad(Short edad) {
		this.edad = edad;
		return this;
	}

	@JsonProperty("edad")
	public Short getEdad() {
		return edad;
	}

	public void setEdad(Short edad) {
		this.edad = edad;
	}

	/**
	 **/
	public JusticiableItem idMinusvalia(Short idMinusvalia) {
		this.idMinusvalia = idMinusvalia;
		return this;
	}

	@JsonProperty("idminusvalia")
	public Short getIdMinusvalia() {
		return idMinusvalia;
	}

	public void setIdMinusvalia(Short idMinusvalia) {
		this.idMinusvalia = idMinusvalia;
	}

	/**
	 **/
	public JusticiableItem existeDomicilio(String existeDomicilio) {
		this.existeDomicilio = existeDomicilio;
		return this;
	}

	@JsonProperty("existedomicilio")
	public String getExisteDomicilio() {
		return existeDomicilio;
	}

	public void setExisteDomicilio(String existeDomicilio) {
		this.existeDomicilio = existeDomicilio;
	}

	/**
	 **/
	public JusticiableItem idProvincia2(String idProvincia2) {
		this.idProvincia2 = idProvincia2;
		return this;
	}

	@JsonProperty("idprovincia2")
	public String getIdProvincia2() {
		return idProvincia2;
	}

	public void setIdProvincia2(String idProvincia2) {
		this.idProvincia2 = idProvincia2;
	}

	/**
	 **/
	public JusticiableItem idPoblacion2(String idPoblacion2) {
		this.idPoblacion2 = idPoblacion2;
		return this;
	}

	@JsonProperty("idpoblacion2")
	public String getIdPoblacion2() {
		return idPoblacion2;
	}

	public void setIdPoblacion2(String idPoblacion2) {
		this.idPoblacion2 = idPoblacion2;
	}

	/**
	 **/
	public JusticiableItem direccion2(String direccion2) {
		this.direccion2 = direccion2;
		return this;
	}

	@JsonProperty("direccion2")
	public String getDireccion2() {
		return direccion2;
	}

	public void setDireccion2(String direccion2) {
		this.direccion2 = direccion2;
	}

	/**
	 **/
	public JusticiableItem codigoPostal2(String codigoPostal2) {
		this.codigoPostal2 = codigoPostal2;
		return this;
	}

	@JsonProperty("codigopostal2")
	public String getCodigoPostal2() {
		return codigoPostal2;
	}

	public void setCodigoPostal2(String codigoPostal2) {
		this.codigoPostal2 = codigoPostal2;
	}

	/**
	 **/
	public JusticiableItem idTipodir(String idTipodir) {
		this.idTipodir = idTipodir;
		return this;
	}

	@JsonProperty("idtipodir")
	public String getIdTipodir() {
		return idTipodir;
	}

	public void setIdTipodir(String idTipodir) {
		this.idTipodir = idTipodir;
	}

	/**
	 **/
	public JusticiableItem idTipodir2(String idTipodir2) {
		this.idTipodir2 = idTipodir2;
		return this;
	}

	@JsonProperty("idtipodir2")
	public String getIdTipodir2() {
		return idTipodir2;
	}

	public void setIdTipodir2(String idTipodir2) {
		this.idTipodir2 = idTipodir2;
	}

	/**
	 **/
	public JusticiableItem cnae(String cnae) {
		this.cnae = cnae;
		return this;
	}

	@JsonProperty("cnae")
	public String getCnae() {
		return cnae;
	}

	public void setCnae(String cnae) {
		this.cnae = cnae;
	}

	/**
	 **/
	public JusticiableItem idTipoVia(String idTipoVia) {
		this.idTipoVia = idTipoVia;
		return this;
	}

	@JsonProperty("idtipovia")
	public String getIdTipoVia() {
		return idTipoVia;
	}

	public void setIdTipoVia(String idTipoVia) {
		this.idTipoVia = idTipoVia;
	}

	/**
	 **/
	public JusticiableItem numeroDir(String numeroDir) {
		this.numeroDir = numeroDir;
		return this;
	}

	@JsonProperty("numerodir")
	public String getNumeroDir() {
		return numeroDir;
	}

	public void setNumeroDir(String numeroDir) {
		this.numeroDir = numeroDir;
	}

	/**
	 **/
	public JusticiableItem escaleraDir(String escaleraDir) {
		this.escaleraDir = escaleraDir;
		return this;
	}

	@JsonProperty("escaleradir")
	public String getEscaleraDir() {
		return escaleraDir;
	}

	public void setEscaleraDir(String escaleraDir) {
		this.escaleraDir = escaleraDir;
	}

	/**
	 **/
	public JusticiableItem pisoDir(String pisoDir) {
		this.pisoDir = pisoDir;
		return this;
	}

	@JsonProperty("pisodir")
	public String getPisoDir() {
		return pisoDir;
	}

	public void setPisoDir(String pisoDir) {
		this.pisoDir = pisoDir;
	}

	/**
	 **/
	public JusticiableItem puertaDir(String puertaDir) {
		this.puertaDir = puertaDir;
		return this;
	}

	@JsonProperty("puertadir")
	public String getPuertaDir() {
		return puertaDir;
	}

	public void setPuertaDir(String puertaDir) {
		this.puertaDir = puertaDir;
	}

	/**
	 **/
	public JusticiableItem idTipoVia2(String idTipoVia2) {
		this.idTipoVia2 = idTipoVia2;
		return this;
	}

	@JsonProperty("idtipovia2")
	public String getIdTipoVia2() {
		return idTipoVia2;
	}

	public void setIdTipoVia2(String idTipoVia2) {
		this.idTipoVia2 = idTipoVia2;
	}

	/**
	 **/
	public JusticiableItem numeroDir2(String numeroDir2) {
		this.numeroDir2 = numeroDir2;
		return this;
	}

	@JsonProperty("numerodir2")
	public String getNumeroDir2() {
		return numeroDir2;
	}

	public void setNumeroDir2(String numeroDir2) {
		this.numeroDir2 = numeroDir2;
	}

	/**
	 **/
	public JusticiableItem escaleraDir2(String escaleraDir2) {
		this.escaleraDir2 = escaleraDir2;
		return this;
	}

	@JsonProperty("escaleradir2")
	public String getEscaleraDir2() {
		return escaleraDir2;
	}

	public void setEscaleraDir2(String escaleraDir2) {
		this.escaleraDir2 = escaleraDir2;
	}

	/**
	 **/
	public JusticiableItem pisoDir2(String pisoDir2) {
		this.pisoDir2 = pisoDir2;
		return this;
	}

	@JsonProperty("pisodir2")
	public String getPisoDir2() {
		return pisoDir2;
	}

	public void setPisoDir2(String pisoDir2) {
		this.pisoDir2 = pisoDir2;
	}

	/**
	 **/
	public JusticiableItem puertaDir2(String puertaDir2) {
		this.puertaDir2 = puertaDir2;
		return this;
	}

	@JsonProperty("puertadir2")
	public String getPuertaDir2() {
		return puertaDir2;
	}

	public void setPuertaDir2(String puertaDir2) {
		this.puertaDir2 = puertaDir2;
	}

	/**
	 **/
	public JusticiableItem idpaisDir1(String idpaisDir1) {
		this.idpaisDir1 = idpaisDir1;
		return this;
	}

	@JsonProperty("idpaisdir1")
	public String getIdpaisDir1() {
		return idpaisDir1;
	}

	public void setIdpaisDir1(String idpaisDir1) {
		this.idpaisDir1 = idpaisDir1;
	}

	/**
	 **/
	public JusticiableItem idpaisDir2(String idpaisDir2) {
		this.idpaisDir2 = idpaisDir2;
		return this;
	}

	@JsonProperty("idpaisdir2")
	public String getIdpaisDir2() {
		return idpaisDir2;
	}

	public void setIdpaisDir2(String idpaisDir2) {
		this.idpaisDir2 = idpaisDir2;
	}

	/**
	 **/
	public JusticiableItem descPaisDir1(String descPaisDir1) {
		this.descPaisDir1 = descPaisDir1;
		return this;
	}

	@JsonProperty("descpaisdir1")
	public String getDescPaisDir1() {
		return descPaisDir1;
	}

	public void setDescPaisDir1(String descPaisDir1) {
		this.descPaisDir1 = descPaisDir1;
	}

	/**
	 **/
	public JusticiableItem descPaisDir2(String descPaisDir2) {
		this.descPaisDir2 = descPaisDir2;
		return this;
	}

	@JsonProperty("descpaisdir2")
	public String getDescPaisDir2() {
		return descPaisDir2;
	}

	public void setDescPaisDir2(String descPaisDir2) {
		this.descPaisDir2 = descPaisDir2;
	}

	/**
	 **/
	public JusticiableItem idTipoIdentificacionotros(String idTipoIdentificacionotros) {
		this.idTipoIdentificacionotros = idTipoIdentificacionotros;
		return this;
	}

	@JsonProperty("idtipoidentificacionotros")
	public String getIdTipoIdentificacionotros() {
		return idTipoIdentificacionotros;
	}

	public void setIdTipoIdentificacionotros(String idTipoIdentificacionotros) {
		this.idTipoIdentificacionotros = idTipoIdentificacionotros;
	}

	/**
	 **/
	public JusticiableItem asistidoSolicitajg(String asistidoSolicitajg) {
		this.asistidoSolicitajg = asistidoSolicitajg;
		return this;
	}

	@JsonProperty("asistidosolicitajg")
	public String getAsistidoSolicitajg() {
		return asistidoSolicitajg;
	}

	public void setAsistidoSolicitajg(String asistidoSolicitajg) {
		this.asistidoSolicitajg = asistidoSolicitajg;
	}

	/**
	 **/
	public JusticiableItem asistidoAutorizaeejg(String asistidoAutorizaeejg) {
		this.asistidoAutorizaeejg = asistidoAutorizaeejg;
		return this;
	}

	@JsonProperty("asistidoautorizaeejg")
	public String getAsistidoAutorizaeejg() {
		return asistidoAutorizaeejg;
	}

	public void setAsistidoAutorizaeejg(String asistidoAutorizaeejg) {
		this.asistidoAutorizaeejg = asistidoAutorizaeejg;
	}

	/**
	 **/
	public JusticiableItem autorizaAvisoTelematico(String autorizaAvisoTelematico) {
		this.autorizaAvisoTelematico = autorizaAvisoTelematico;
		return this;
	}

	@JsonProperty("autorizaavisotelematico")
	public String getAutorizaAvisoTelematico() {
		return autorizaAvisoTelematico;
	}

	public void setAutorizaAvisoTelematico(String autorizaAvisoTelematico) {
		this.autorizaAvisoTelematico = autorizaAvisoTelematico;
	}

	/**
	 **/
	public JusticiableItem telefonos(List<JusticiableTelefonoItem> telefonos) {
		this.telefonos = telefonos;
		return this;
	}

	@JsonProperty("telefonos")
	public List<JusticiableTelefonoItem> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(List<JusticiableTelefonoItem> telefonos) {
		this.telefonos = telefonos;
	}
	
	/**
	 **/
	public JusticiableItem parentesco(String parentesco) {
		this.parentesco = parentesco;
		return this;
	}

	@JsonProperty("parentesco")
	public String getParentesco() {
		return parentesco;
	}

	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}
	
	/**
	 **/
	public JusticiableItem tipoJusticiable(String tipoJusticiable) {
		this.tipoJusticiable = tipoJusticiable;
		return this;
	}

	@JsonProperty("tipojusticiable")
	public String getTipoJusticiable() {
		return tipoJusticiable;
	}

	public void setTipoJusticiable(String tipoJusticiable) {
		this.tipoJusticiable = tipoJusticiable;
	}
	
	/**
	 **/
	public JusticiableItem checkNoInformadaDireccion(boolean checkNoInformadaDireccion) {
		this.checkNoInformadaDireccion = checkNoInformadaDireccion;
		return this;
	}

	@JsonProperty("checkNoInformadaDireccion")
	public boolean isCheckNoInformadaDireccion() {
		return checkNoInformadaDireccion;
	}

	public void setCheckNoInformadaDireccion(boolean checkNoInformadaDireccion) {
		this.checkNoInformadaDireccion = checkNoInformadaDireccion;
	}
	
	/**
	 **/
	public JusticiableItem datosAsuntos(AsuntosJusticiableItem[] datosAsuntos) {
		this.datosAsuntos = datosAsuntos;
		return this;
	}

	@JsonProperty("datosAsuntos")
	public AsuntosJusticiableItem[] getDatosAsuntos() {
		return datosAsuntos;
	}

	public void setDatosAsuntos(AsuntosJusticiableItem[] datosAsuntos) {
		this.datosAsuntos = datosAsuntos;
	}
	
	/**
	 **/
	public JusticiableItem validacionRepeticion(boolean validacionRepeticion) {
		this.validacionRepeticion = validacionRepeticion;
		return this;
	}

	@JsonProperty("validacionRepeticion")
	public boolean isValidacionRepeticion() {
		return validacionRepeticion;
	}

	public void setValidacionRepeticion(boolean validacionRepeticion) {
		this.validacionRepeticion = validacionRepeticion;
	}

	/**
	 **/
	public JusticiableItem asociarRepresentante(Boolean asociarRepresentante) {
		this.asociarRepresentante = asociarRepresentante;
		return this;
	}

	@JsonProperty("asociarRepresentante")
	public Boolean getAsociarRepresentante() {
		return asociarRepresentante;
	}

	public void setAsociarRepresentante(Boolean asociarRepresentante) {
		this.asociarRepresentante = asociarRepresentante;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellido1 == null) ? 0 : apellido1.hashCode());
		result = prime * result + ((apellido2 == null) ? 0 : apellido2.hashCode());
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((asistidoAutorizaeejg == null) ? 0 : asistidoAutorizaeejg.hashCode());
		result = prime * result + ((asistidoSolicitajg == null) ? 0 : asistidoSolicitajg.hashCode());
		result = prime * result + ((asociarRepresentante == null) ? 0 : asociarRepresentante.hashCode());
		result = prime * result + ((asuntos == null) ? 0 : asuntos.hashCode());
		result = prime * result + ((autorizaAvisoTelematico == null) ? 0 : autorizaAvisoTelematico.hashCode());
		result = prime * result + (checkNoInformadaDireccion ? 1231 : 1237);
		result = prime * result + ((cnae == null) ? 0 : cnae.hashCode());
		result = prime * result + ((codigoPostal == null) ? 0 : codigoPostal.hashCode());
		result = prime * result + ((codigoPostal2 == null) ? 0 : codigoPostal2.hashCode());
		result = prime * result + ((correoElectronico == null) ? 0 : correoElectronico.hashCode());
		result = prime * result + Arrays.hashCode(datosAsuntos);
		result = prime * result + ((descPaisDir1 == null) ? 0 : descPaisDir1.hashCode());
		result = prime * result + ((descPaisDir2 == null) ? 0 : descPaisDir2.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((direccion2 == null) ? 0 : direccion2.hashCode());
		result = prime * result + ((edad == null) ? 0 : edad.hashCode());
		result = prime * result + ((escaleraDir == null) ? 0 : escaleraDir.hashCode());
		result = prime * result + ((escaleraDir2 == null) ? 0 : escaleraDir2.hashCode());
		result = prime * result + ((existeDomicilio == null) ? 0 : existeDomicilio.hashCode());
		result = prime * result + ((fax == null) ? 0 : fax.hashCode());
		result = prime * result + ((fechaAlta == null) ? 0 : fechaAlta.hashCode());
		result = prime * result + ((fechaModificacion == null) ? 0 : fechaModificacion.hashCode());
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((idEstadoCivil == null) ? 0 : idEstadoCivil.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idLenguaje == null) ? 0 : idLenguaje.hashCode());
		result = prime * result + ((idMinusvalia == null) ? 0 : idMinusvalia.hashCode());
		result = prime * result + ((idPais == null) ? 0 : idPais.hashCode());
		result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
		result = prime * result + ((idPoblacion == null) ? 0 : idPoblacion.hashCode());
		result = prime * result + ((idPoblacion2 == null) ? 0 : idPoblacion2.hashCode());
		result = prime * result + ((idProfesion == null) ? 0 : idProfesion.hashCode());
		result = prime * result + ((idProvincia == null) ? 0 : idProvincia.hashCode());
		result = prime * result + ((idProvincia2 == null) ? 0 : idProvincia2.hashCode());
		result = prime * result + ((idRepresentantejg == null) ? 0 : idRepresentantejg.hashCode());
		result = prime * result + ((idTipoIdentificacion == null) ? 0 : idTipoIdentificacion.hashCode());
		result = prime * result + ((idTipoIdentificacionotros == null) ? 0 : idTipoIdentificacionotros.hashCode());
		result = prime * result + ((idTipoVia == null) ? 0 : idTipoVia.hashCode());
		result = prime * result + ((idTipoVia2 == null) ? 0 : idTipoVia2.hashCode());
		result = prime * result + ((idTipodir == null) ? 0 : idTipodir.hashCode());
		result = prime * result + ((idTipodir2 == null) ? 0 : idTipodir2.hashCode());
		result = prime * result + ((idTipoencalidad == null) ? 0 : idTipoencalidad.hashCode());
		result = prime * result + ((idpaisDir1 == null) ? 0 : idpaisDir1.hashCode());
		result = prime * result + ((idpaisDir2 == null) ? 0 : idpaisDir2.hashCode());
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((numeroDir == null) ? 0 : numeroDir.hashCode());
		result = prime * result + ((numeroDir2 == null) ? 0 : numeroDir2.hashCode());
		result = prime * result + ((numeroHijos == null) ? 0 : numeroHijos.hashCode());
		result = prime * result + ((observaciones == null) ? 0 : observaciones.hashCode());
		result = prime * result + ((parentesco == null) ? 0 : parentesco.hashCode());
		result = prime * result + ((pisoDir == null) ? 0 : pisoDir.hashCode());
		result = prime * result + ((pisoDir2 == null) ? 0 : pisoDir2.hashCode());
		result = prime * result + ((puertaDir == null) ? 0 : puertaDir.hashCode());
		result = prime * result + ((puertaDir2 == null) ? 0 : puertaDir2.hashCode());
		result = prime * result + ((regimen_conyugal == null) ? 0 : regimen_conyugal.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result + ((telefonos == null) ? 0 : telefonos.hashCode());
		result = prime * result + ((tipoJusticiable == null) ? 0 : tipoJusticiable.hashCode());
		result = prime * result + ((tipoPersonaJG == null) ? 0 : tipoPersonaJG.hashCode());
		result = prime * result + (validacionRepeticion ? 1231 : 1237);
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
		JusticiableItem other = (JusticiableItem) obj;
		if (apellido1 == null) {
			if (other.apellido1 != null)
				return false;
		} else if (!apellido1.equals(other.apellido1))
			return false;
		if (apellido2 == null) {
			if (other.apellido2 != null)
				return false;
		} else if (!apellido2.equals(other.apellido2))
			return false;
		if (apellidos == null) {
			if (other.apellidos != null)
				return false;
		} else if (!apellidos.equals(other.apellidos))
			return false;
		if (asistidoAutorizaeejg == null) {
			if (other.asistidoAutorizaeejg != null)
				return false;
		} else if (!asistidoAutorizaeejg.equals(other.asistidoAutorizaeejg))
			return false;
		if (asistidoSolicitajg == null) {
			if (other.asistidoSolicitajg != null)
				return false;
		} else if (!asistidoSolicitajg.equals(other.asistidoSolicitajg))
			return false;
		if (asociarRepresentante == null) {
			if (other.asociarRepresentante != null)
				return false;
		} else if (!asociarRepresentante.equals(other.asociarRepresentante))
			return false;
		if (asuntos == null) {
			if (other.asuntos != null)
				return false;
		} else if (!asuntos.equals(other.asuntos))
			return false;
		if (autorizaAvisoTelematico == null) {
			if (other.autorizaAvisoTelematico != null)
				return false;
		} else if (!autorizaAvisoTelematico.equals(other.autorizaAvisoTelematico))
			return false;
		if (checkNoInformadaDireccion != other.checkNoInformadaDireccion)
			return false;
		if (cnae == null) {
			if (other.cnae != null)
				return false;
		} else if (!cnae.equals(other.cnae))
			return false;
		if (codigoPostal == null) {
			if (other.codigoPostal != null)
				return false;
		} else if (!codigoPostal.equals(other.codigoPostal))
			return false;
		if (codigoPostal2 == null) {
			if (other.codigoPostal2 != null)
				return false;
		} else if (!codigoPostal2.equals(other.codigoPostal2))
			return false;
		if (correoElectronico == null) {
			if (other.correoElectronico != null)
				return false;
		} else if (!correoElectronico.equals(other.correoElectronico))
			return false;
		if (!Arrays.equals(datosAsuntos, other.datosAsuntos))
			return false;
		if (descPaisDir1 == null) {
			if (other.descPaisDir1 != null)
				return false;
		} else if (!descPaisDir1.equals(other.descPaisDir1))
			return false;
		if (descPaisDir2 == null) {
			if (other.descPaisDir2 != null)
				return false;
		} else if (!descPaisDir2.equals(other.descPaisDir2))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (direccion2 == null) {
			if (other.direccion2 != null)
				return false;
		} else if (!direccion2.equals(other.direccion2))
			return false;
		if (edad == null) {
			if (other.edad != null)
				return false;
		} else if (!edad.equals(other.edad))
			return false;
		if (escaleraDir == null) {
			if (other.escaleraDir != null)
				return false;
		} else if (!escaleraDir.equals(other.escaleraDir))
			return false;
		if (escaleraDir2 == null) {
			if (other.escaleraDir2 != null)
				return false;
		} else if (!escaleraDir2.equals(other.escaleraDir2))
			return false;
		if (existeDomicilio == null) {
			if (other.existeDomicilio != null)
				return false;
		} else if (!existeDomicilio.equals(other.existeDomicilio))
			return false;
		if (fax == null) {
			if (other.fax != null)
				return false;
		} else if (!fax.equals(other.fax))
			return false;
		if (fechaAlta == null) {
			if (other.fechaAlta != null)
				return false;
		} else if (!fechaAlta.equals(other.fechaAlta))
			return false;
		if (fechaModificacion == null) {
			if (other.fechaModificacion != null)
				return false;
		} else if (!fechaModificacion.equals(other.fechaModificacion))
			return false;
		if (fechaNacimiento == null) {
			if (other.fechaNacimiento != null)
				return false;
		} else if (!fechaNacimiento.equals(other.fechaNacimiento))
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
		if (idMinusvalia == null) {
			if (other.idMinusvalia != null)
				return false;
		} else if (!idMinusvalia.equals(other.idMinusvalia))
			return false;
		if (idPais == null) {
			if (other.idPais != null)
				return false;
		} else if (!idPais.equals(other.idPais))
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
		if (idPoblacion2 == null) {
			if (other.idPoblacion2 != null)
				return false;
		} else if (!idPoblacion2.equals(other.idPoblacion2))
			return false;
		if (idProfesion == null) {
			if (other.idProfesion != null)
				return false;
		} else if (!idProfesion.equals(other.idProfesion))
			return false;
		if (idProvincia == null) {
			if (other.idProvincia != null)
				return false;
		} else if (!idProvincia.equals(other.idProvincia))
			return false;
		if (idProvincia2 == null) {
			if (other.idProvincia2 != null)
				return false;
		} else if (!idProvincia2.equals(other.idProvincia2))
			return false;
		if (idRepresentantejg == null) {
			if (other.idRepresentantejg != null)
				return false;
		} else if (!idRepresentantejg.equals(other.idRepresentantejg))
			return false;
		if (idTipoIdentificacion == null) {
			if (other.idTipoIdentificacion != null)
				return false;
		} else if (!idTipoIdentificacion.equals(other.idTipoIdentificacion))
			return false;
		if (idTipoIdentificacionotros == null) {
			if (other.idTipoIdentificacionotros != null)
				return false;
		} else if (!idTipoIdentificacionotros.equals(other.idTipoIdentificacionotros))
			return false;
		if (idTipoVia == null) {
			if (other.idTipoVia != null)
				return false;
		} else if (!idTipoVia.equals(other.idTipoVia))
			return false;
		if (idTipoVia2 == null) {
			if (other.idTipoVia2 != null)
				return false;
		} else if (!idTipoVia2.equals(other.idTipoVia2))
			return false;
		if (idTipodir == null) {
			if (other.idTipodir != null)
				return false;
		} else if (!idTipodir.equals(other.idTipodir))
			return false;
		if (idTipodir2 == null) {
			if (other.idTipodir2 != null)
				return false;
		} else if (!idTipodir2.equals(other.idTipodir2))
			return false;
		if (idTipoencalidad == null) {
			if (other.idTipoencalidad != null)
				return false;
		} else if (!idTipoencalidad.equals(other.idTipoencalidad))
			return false;
		if (idpaisDir1 == null) {
			if (other.idpaisDir1 != null)
				return false;
		} else if (!idpaisDir1.equals(other.idpaisDir1))
			return false;
		if (idpaisDir2 == null) {
			if (other.idpaisDir2 != null)
				return false;
		} else if (!idpaisDir2.equals(other.idpaisDir2))
			return false;
		if (nif == null) {
			if (other.nif != null)
				return false;
		} else if (!nif.equals(other.nif))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (numeroDir == null) {
			if (other.numeroDir != null)
				return false;
		} else if (!numeroDir.equals(other.numeroDir))
			return false;
		if (numeroDir2 == null) {
			if (other.numeroDir2 != null)
				return false;
		} else if (!numeroDir2.equals(other.numeroDir2))
			return false;
		if (numeroHijos == null) {
			if (other.numeroHijos != null)
				return false;
		} else if (!numeroHijos.equals(other.numeroHijos))
			return false;
		if (observaciones == null) {
			if (other.observaciones != null)
				return false;
		} else if (!observaciones.equals(other.observaciones))
			return false;
		if (parentesco == null) {
			if (other.parentesco != null)
				return false;
		} else if (!parentesco.equals(other.parentesco))
			return false;
		if (pisoDir == null) {
			if (other.pisoDir != null)
				return false;
		} else if (!pisoDir.equals(other.pisoDir))
			return false;
		if (pisoDir2 == null) {
			if (other.pisoDir2 != null)
				return false;
		} else if (!pisoDir2.equals(other.pisoDir2))
			return false;
		if (puertaDir == null) {
			if (other.puertaDir != null)
				return false;
		} else if (!puertaDir.equals(other.puertaDir))
			return false;
		if (puertaDir2 == null) {
			if (other.puertaDir2 != null)
				return false;
		} else if (!puertaDir2.equals(other.puertaDir2))
			return false;
		if (regimen_conyugal == null) {
			if (other.regimen_conyugal != null)
				return false;
		} else if (!regimen_conyugal.equals(other.regimen_conyugal))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		if (telefonos == null) {
			if (other.telefonos != null)
				return false;
		} else if (!telefonos.equals(other.telefonos))
			return false;
		if (tipoJusticiable == null) {
			if (other.tipoJusticiable != null)
				return false;
		} else if (!tipoJusticiable.equals(other.tipoJusticiable))
			return false;
		if (tipoPersonaJG == null) {
			if (other.tipoPersonaJG != null)
				return false;
		} else if (!tipoPersonaJG.equals(other.tipoPersonaJG))
			return false;
		if (validacionRepeticion != other.validacionRepeticion)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JusticiableItem [idPersona=" + idPersona + ", idInstitucion=" + idInstitucion + ", nif=" + nif
				+ ", nombre=" + nombre + ", asuntos=" + asuntos + ", fechaModificacion=" + fechaModificacion
				+ ", fechaNacimiento=" + fechaNacimiento + ", fechaAlta=" + fechaAlta + ", idPais=" + idPais
				+ ", apellidos=" + apellidos + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", direccion="
				+ direccion + ", codigoPostal=" + codigoPostal + ", idProfesion=" + idProfesion + ", regimen_conyugal="
				+ regimen_conyugal + ", idProvincia=" + idProvincia + ", idPoblacion=" + idPoblacion
				+ ", idEstadoCivil=" + idEstadoCivil + ", tipoPersonajg=" + tipoPersonaJG + ", idTipoIdentificacion="
				+ idTipoIdentificacion + ", observaciones=" + observaciones + ", idRepresentantejg=" + idRepresentantejg
				+ ", idTipoencalidad=" + idTipoencalidad + ", sexo=" + sexo + ", idLenguaje=" + idLenguaje
				+ ", numeroHijos=" + numeroHijos + ", fax=" + fax + ", correoElectronico=" + correoElectronico
				+ ", edad=" + edad + ", idMinusvalia=" + idMinusvalia + ", existeDomicilio=" + existeDomicilio
				+ ", idProvincia2=" + idProvincia2 + ", idPoblacion2=" + idPoblacion2 + ", direccion2=" + direccion2
				+ ", codigoPostal2=" + codigoPostal2 + ", idTipodir=" + idTipodir + ", idTipodir2=" + idTipodir2
				+ ", cnae=" + cnae + ", idTipoVia=" + idTipoVia + ", numeroDir=" + numeroDir + ", escaleraDir="
				+ escaleraDir + ", pisoDir=" + pisoDir + ", puertaDir=" + puertaDir + ", idTipoVia2=" + idTipoVia2
				+ ", numeroDir2=" + numeroDir2 + ", escaleraDir2=" + escaleraDir2 + ", pisoDir2=" + pisoDir2
				+ ", puertaDir2=" + puertaDir2 + ", idpaisDir1=" + idpaisDir1 + ", idpaisDir2=" + idpaisDir2
				+ ", descPaisDir1=" + descPaisDir1 + ", descPaisDir2=" + descPaisDir2 + ", idTipoIdentificacionotros="
				+ idTipoIdentificacionotros + ", asistidoSolicitajg=" + asistidoSolicitajg + ", asistidoAutorizaeejg="
				+ asistidoAutorizaeejg + ", autorizaAvisoTelematico=" + autorizaAvisoTelematico + ", telefonos="
				+ telefonos + ", parentesco=" + parentesco + ", tipoJusticiable=" + tipoJusticiable
				+ ", checkNoInformadaDireccion=" + checkNoInformadaDireccion + ", datosAsuntos="
				+ Arrays.toString(datosAsuntos) + ", validacionRepeticion=" + validacionRepeticion
				+ ", asociarRepresentante=" + asociarRepresentante + "]";
	}



	

}
