package org.itcgae.siga.DTOs.scs;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JusticiableItem {

	@JsonProperty("idpersona")
	@Size(max = 10)
	@Pattern(regexp = "\\d+", message = "El id persona debe contener solo números")
	private String idPersona;

	@JsonProperty("idinstitucion")
	@Size(max = 4)
	@Pattern(regexp = "\\d+", message = "El id institucion debe contener solo números")
	private String idInstitucion;

	/************** DATOS PERSONALES *****************/

	@JsonProperty("nif")
	@Size(max = 20)
	private String nif;

	@JsonProperty("idtipoidentificacion")
	@Max(value = 99)
	@Min(value = 0)
	private Short idTipoIdentificacion;

	@JsonProperty("tipopersonajg")
	@Size(max = 1)
	@NotBlank(message = "El tipo persona del justiciable no puede estar en blanco")
	@Pattern(regexp = "[FJ]", message = "El tipo persona debe ser F o J")
	private String tipoPersonaJG;

	@JsonProperty("nombre")
	@NotBlank(message = "El nombre del justiciable no puede estar en blanco")
	@Size(max = 100)
	private String nombre;

	@JsonProperty("apellido1")
	@NotBlank(message = "El primer apellido del justiciable no puede estar en blanco")
	@Size(max = 100)
	private String apellido1;

	@JsonProperty("apellido2")
	@Size(max = 100)
	private String apellido2;

	@JsonProperty("fechanacimiento")
	private Date fechaNacimiento;

	@JsonProperty("edad")
	@Max(value = 999)
	@Min(value = 0)
	private Short edad;

	@JsonProperty("idpais")
	@Size(max = 3)
	@Pattern(regexp = "^$|\\d+", message = "El id pais debe contener solo números")
	private String idPais;

	@JsonProperty("fechaalta")
	@NotNull(message = "La fecha de alta del justiciable no puede estar en blanco")
	private Date fechaAlta;

	@JsonProperty("idlenguaje")
	@Size(max = 3)
	@Pattern(regexp = "\\d+", message = "El id de lenguaje debe contener solo números")
	private String idLenguaje;

	@JsonProperty("sexo")
	@Size(max = 1)
	@NotBlank(message = "El sexo del justiciable no puede estar en blanco")
	@Pattern(regexp = "[HMN]", message = "El sexo debe ser H, M o N")
	private String sexo;

	@JsonProperty("idestadocivil")
	@Max(value = 99)
	@Min(value = 0)
	private Short idEstadoCivil;

	@JsonProperty("regimenConyugal")
	@Size(max = 1)
	@Pattern(regexp = "[IGS]", message = "El regimen conyungal debe ser I, G o S")
	private String regimen_conyugal;

	@JsonProperty("idprofesion")
	@Max(value = 999)
	@Min(value = 0)
	private Short idProfesion;

	@JsonProperty("idminusvalia")
	@Max(value = 999)
	@Min(value = 0)
	private Short idMinusvalia;

	/************** DATOS DE CONTACTO *****************/

	@JsonProperty("idtipovia")
	@Size(max = 3)
	@Pattern(regexp = "^$|\\d+", message = "El id del tipo via debe contener solo números")
	private String idTipoVia;

	@JsonProperty("direccion")
	@Size(max = 100)
	private String direccion;

	@JsonProperty("direccionExtranjera")
	private String direccionExtranjera;

	@JsonProperty("numerodir")
	@Size(max = 10)
	private String numeroDir;

	@JsonProperty("escaleradir")
	@Size(max = 10)
	private String escaleraDir;

	@JsonProperty("pisodir")
	@Size(max = 10)
	private String pisoDir;

	@JsonProperty("puertadir")
	@Size(max = 10)
	private String puertaDir;

	@JsonProperty("idpaisdir1")
	@Size(max = 10)
	@Pattern(regexp = "^$|\\d+", message = "El pais debe contener solo números")
	private String idpaisDir1;
	
	@JsonProperty("codigopostal")
	@Pattern(regexp = "^$|\\d+", message = "El código postal debe contener solo números")
	@Size(max = 5)
	private String codigoPostal;

	@JsonProperty("idprovincia")
	@Size(max = 2)
	@Pattern(regexp = "^$|\\d+", message = "El idprovincia debe contener solo números")
	private String idProvincia;

	@JsonProperty("idpoblacion")
	@Size(max = 11)
	@Pattern(regexp = "^$|\\d+", message = "El id poblacion debe contener solo números")
	private String idPoblacion;

	@JsonProperty("correoelectronico")
	@Size(max = 100)
	@Email(message = "Debe ser una dirección de correo electrónico válida")
	private String correoElectronico;

	@JsonProperty("fax")
	@Size(max = 20)
	@Pattern(regexp = "^$|^(\\(\\+[0-9]{2}\\)|[0-9]{4})?[ ]?[0-9]{9}$", message = "El fax formato no valido")
	private String fax;

	@JsonProperty("telefonos")
	@Valid
	private List<JusticiableTelefonoItem> telefonos;

	/************** DATOS DE SOLICITUD *****************/

	@JsonProperty("asistidosolicitajg")
	@Size(max = 1)
	@Pattern(regexp = "[01]")
	private String asistidoSolicitajg;

	@JsonProperty("asistidoautorizaeejg")
	@Size(max = 1)
	@Pattern(regexp = "[01]")
	private String asistidoAutorizaeejg;

	@JsonProperty("autorizaavisotelematico")
	@Size(max = 1)
	@Pattern(regexp = "[01]")
	private String autorizaAvisoTelematico;

	/*************** DATOS REPRESENTANTE *******************/

	@JsonProperty("idrepresentantejg")
	private Long idRepresentantejg;

	/************** ASUNTO ******************************/

	@JsonProperty("datosAsuntos")
	private AsuntosJusticiableItem[] datosAsuntos;

	/****************************************************************** OTROS ****************************************************/

	@JsonProperty("asuntos")
	private String asuntos;

	@JsonProperty("fechamodificacion")
	private Date fechaModificacion;

	@JsonProperty("apellidos")
	private String apellidos;

	@JsonProperty("tipojusticiable")
	private String tipoJusticiable;

	@JsonProperty("validacionRepeticion")
	@NotNull()
	private boolean validacionRepeticion;

	@JsonProperty("asociarRepresentante")
	private Boolean asociarRepresentante;

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public Short getIdTipoIdentificacion() {
		return idTipoIdentificacion;
	}

	public void setIdTipoIdentificacion(Short idTipoIdentificacion) {
		this.idTipoIdentificacion = idTipoIdentificacion;
	}

	public String getTipoPersonaJG() {
		return tipoPersonaJG;
	}

	public void setTipoPersonaJG(String tipoPersonaJG) {
		this.tipoPersonaJG = tipoPersonaJG;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Short getEdad() {
		return edad;
	}

	public void setEdad(Short edad) {
		this.edad = edad;
	}

	public String getIdPais() {
		return idPais;
	}

	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getIdLenguaje() {
		return idLenguaje;
	}

	public void setIdLenguaje(String idLenguaje) {
		this.idLenguaje = idLenguaje;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Short getIdEstadoCivil() {
		return idEstadoCivil;
	}

	public void setIdEstadoCivil(Short idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}

	public String getRegimen_conyugal() {
		return regimen_conyugal;
	}

	public void setRegimen_conyugal(String regimen_conyugal) {
		this.regimen_conyugal = regimen_conyugal;
	}

	public Short getIdProfesion() {
		return idProfesion;
	}

	public void setIdProfesion(Short idProfesion) {
		this.idProfesion = idProfesion;
	}

	public Short getIdMinusvalia() {
		return idMinusvalia;
	}

	public void setIdMinusvalia(Short idMinusvalia) {
		this.idMinusvalia = idMinusvalia;
	}

	public String getIdTipoVia() {
		return idTipoVia;
	}

	public void setIdTipoVia(String idTipoVia) {
		this.idTipoVia = idTipoVia;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDireccionExtranjera() {
		return direccionExtranjera;
	}

	public void setDireccionExtranjera(String direccionExtranjera) {
		this.direccionExtranjera = direccionExtranjera;
	}

	public String getNumeroDir() {
		return numeroDir;
	}

	public void setNumeroDir(String numeroDir) {
		this.numeroDir = numeroDir;
	}

	public String getEscaleraDir() {
		return escaleraDir;
	}

	public void setEscaleraDir(String escaleraDir) {
		this.escaleraDir = escaleraDir;
	}

	public String getPisoDir() {
		return pisoDir;
	}

	public void setPisoDir(String pisoDir) {
		this.pisoDir = pisoDir;
	}

	public String getPuertaDir() {
		return puertaDir;
	}

	public void setPuertaDir(String puertaDir) {
		this.puertaDir = puertaDir;
	}

	public String getIdpaisDir1() {
		return idpaisDir1;
	}

	public void setIdpaisDir1(String idpaisDir1) {
		this.idpaisDir1 = idpaisDir1;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getIdPoblacion() {
		return idPoblacion;
	}

	public void setIdPoblacion(String idPoblacion) {
		this.idPoblacion = idPoblacion;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public List<JusticiableTelefonoItem> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(List<JusticiableTelefonoItem> telefonos) {
		this.telefonos = telefonos;
	}

	public String getAsistidoSolicitajg() {
		return asistidoSolicitajg;
	}

	public void setAsistidoSolicitajg(String asistidoSolicitajg) {
		this.asistidoSolicitajg = asistidoSolicitajg;
	}

	public String getAsistidoAutorizaeejg() {
		return asistidoAutorizaeejg;
	}

	public void setAsistidoAutorizaeejg(String asistidoAutorizaeejg) {
		this.asistidoAutorizaeejg = asistidoAutorizaeejg;
	}

	public String getAutorizaAvisoTelematico() {
		return autorizaAvisoTelematico;
	}

	public void setAutorizaAvisoTelematico(String autorizaAvisoTelematico) {
		this.autorizaAvisoTelematico = autorizaAvisoTelematico;
	}

	public Long getIdRepresentantejg() {
		return idRepresentantejg;
	}

	public void setIdRepresentantejg(Long idRepresentantejg) {
		this.idRepresentantejg = idRepresentantejg;
	}

	public AsuntosJusticiableItem[] getDatosAsuntos() {
		return datosAsuntos;
	}

	public void setDatosAsuntos(AsuntosJusticiableItem[] datosAsuntos) {
		this.datosAsuntos = datosAsuntos;
	}

	public String getAsuntos() {
		return asuntos;
	}

	public void setAsuntos(String asuntos) {
		this.asuntos = asuntos;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTipoJusticiable() {
		return tipoJusticiable;
	}

	public void setTipoJusticiable(String tipoJusticiable) {
		this.tipoJusticiable = tipoJusticiable;
	}

	public boolean isValidacionRepeticion() {
		return validacionRepeticion;
	}

	public void setValidacionRepeticion(boolean validacionRepeticion) {
		this.validacionRepeticion = validacionRepeticion;
	}

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
		// result = prime * result + (checkNoInformadaDireccion ? 1231 : 1237);
		// result = prime * result + ((cnae == null) ? 0 : cnae.hashCode());
		result = prime * result + ((codigoPostal == null) ? 0 : codigoPostal.hashCode());
		// result = prime * result + ((codigoPostal2 == null) ? 0 : codigoPostal2.hashCode());
		result = prime * result + ((correoElectronico == null) ? 0 : correoElectronico.hashCode());
		result = prime * result + Arrays.hashCode(datosAsuntos);
		// result = prime * result + ((descPaisDir1 == null) ? 0 : descPaisDir1.hashCode());
		// result = prime * result + ((descPaisDir2 == null) ? 0 : descPaisDir2.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		// result = prime * result + ((direccion2 == null) ? 0 : direccion2.hashCode());
		result = prime * result + ((edad == null) ? 0 : edad.hashCode());
		result = prime * result + ((escaleraDir == null) ? 0 : escaleraDir.hashCode());
		// result = prime * result + ((escaleraDir2 == null) ? 0 : escaleraDir2.hashCode());
		// result = prime * result + ((existeDomicilio == null) ? 0 : existeDomicilio.hashCode());
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
		// result = prime * result + ((idPoblacion2 == null) ? 0 : idPoblacion2.hashCode());
		result = prime * result + ((idProfesion == null) ? 0 : idProfesion.hashCode());
		result = prime * result + ((idProvincia == null) ? 0 : idProvincia.hashCode());
		// result = prime * result + ((idProvincia2 == null) ? 0 : idProvincia2.hashCode());
		result = prime * result + ((idRepresentantejg == null) ? 0 : idRepresentantejg.hashCode());
		result = prime * result + ((idTipoIdentificacion == null) ? 0 : idTipoIdentificacion.hashCode());
		// result = prime * result + ((idTipoIdentificacionotros == null) ? 0 : idTipoIdentificacionotros.hashCode());
		result = prime * result + ((idTipoVia == null) ? 0 : idTipoVia.hashCode());
		// result = prime * result + ((idTipoVia2 == null) ? 0 : idTipoVia2.hashCode());
		// result = prime * result + ((idTipodir == null) ? 0 : idTipodir.hashCode());
		// result = prime * result + ((idTipodir2 == null) ? 0 : idTipodir2.hashCode());
		// result = prime * result + ((idTipoencalidad == null) ? 0 : idTipoencalidad.hashCode());
		result = prime * result + ((idpaisDir1 == null) ? 0 : idpaisDir1.hashCode());
		// result = prime * result + ((idpaisDir2 == null) ? 0 : idpaisDir2.hashCode());
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((numeroDir == null) ? 0 : numeroDir.hashCode());
		// result = prime * result + ((numeroDir2 == null) ? 0 : numeroDir2.hashCode());
		// result = prime * result + ((numeroHijos == null) ? 0 : numeroHijos.hashCode());
		// result = prime * result + ((observaciones == null) ? 0 : observaciones.hashCode());
		// result = prime * result + ((parentesco == null) ? 0 : parentesco.hashCode());
		result = prime * result + ((pisoDir == null) ? 0 : pisoDir.hashCode());
		// result = prime * result + ((pisoDir2 == null) ? 0 : pisoDir2.hashCode());
		result = prime * result + ((puertaDir == null) ? 0 : puertaDir.hashCode());
		// result = prime * result + ((puertaDir2 == null) ? 0 : puertaDir2.hashCode());
		result = prime * result + ((regimen_conyugal == null) ? 0 : regimen_conyugal.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result + ((telefonos == null) ? 0 : telefonos.hashCode());
		result = prime * result + ((tipoJusticiable == null) ? 0 : tipoJusticiable.hashCode());
		result = prime * result + ((tipoPersonaJG == null) ? 0 : tipoPersonaJG.hashCode());
		result = prime * result + ((direccionExtranjera == null) ? 0 : direccionExtranjera.hashCode());
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
		if (codigoPostal == null) {
			if (other.codigoPostal != null)
				return false;
		} else if (!codigoPostal.equals(other.codigoPostal))
			return false;
		if (correoElectronico == null) {
			if (other.correoElectronico != null)
				return false;
		} else if (!correoElectronico.equals(other.correoElectronico))
			return false;
		if (!Arrays.equals(datosAsuntos, other.datosAsuntos))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
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
		if (idTipoVia == null) {
			if (other.idTipoVia != null)
				return false;
		} else if (!idTipoVia.equals(other.idTipoVia))
			return false;
		if (idpaisDir1 == null) {
			if (other.idpaisDir1 != null)
				return false;
		} else if (!idpaisDir1.equals(other.idpaisDir1))
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
		if (pisoDir == null) {
			if (other.pisoDir != null)
				return false;
		} else if (!pisoDir.equals(other.pisoDir))
			return false;
		if (puertaDir == null) {
			if (other.puertaDir != null)
				return false;
		} else if (!puertaDir.equals(other.puertaDir))
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
		if (direccionExtranjera == null) {
			if (other.direccionExtranjera != null)
				return false;
		} else if (!direccionExtranjera.equals(other.direccionExtranjera))
			return false;
		if (validacionRepeticion != other.validacionRepeticion)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JusticiableItem [idPersona=" + idPersona + ", idInstitucion=" + idInstitucion + ", nif=" + nif + ", nombre=" + nombre + ", asuntos=" + asuntos + ", fechaModificacion=" + fechaModificacion + ", fechaNacimiento=" + fechaNacimiento + ", fechaAlta=" + fechaAlta + ", idPais=" + idPais + ", apellidos=" + apellidos + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", direccion=" + direccion + ", codigoPostal=" + codigoPostal + ", idProfesion=" + idProfesion + ", regimen_conyugal=" + regimen_conyugal + ", idProvincia=" + idProvincia + ", idPoblacion=" + idPoblacion + ", idEstadoCivil=" + idEstadoCivil + ", tipoPersonajg=" + tipoPersonaJG + ", idTipoIdentificacion=" + idTipoIdentificacion + ", idRepresentantejg=" + idRepresentantejg + ", sexo=" + sexo + ", idLenguaje=" + idLenguaje + ", fax=" + fax + ", correoElectronico=" + correoElectronico + ", edad=" + edad + ", idMinusvalia=" + idMinusvalia + ", idTipoVia=" + idTipoVia + ", numeroDir=" + numeroDir
				+ ", escaleraDir=" + escaleraDir + ", pisoDir=" + pisoDir + ", puertaDir=" + puertaDir + ", idpaisDir1=" + idpaisDir1 + ", asistidoSolicitajg=" + asistidoSolicitajg + ", asistidoAutorizaeejg=" + asistidoAutorizaeejg + ", autorizaAvisoTelematico=" + autorizaAvisoTelematico + ", telefonos=" + telefonos + ", tipoJusticiable=" + tipoJusticiable + ", datosAsuntos=" + Arrays.toString(datosAsuntos) + ", validacionRepeticion=" + validacionRepeticion + ", asociarRepresentante=" + asociarRepresentante + ", direccionExtranjera=" + direccionExtranjera + "]";
	}
}