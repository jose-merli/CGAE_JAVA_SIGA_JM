package org.itcgae.siga.DTOs.cen;

import java.util.Arrays;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NoColegiadoItem {

	private String idPersona;
	private String[] idPersonas;
	private String idInstitucion;
	private String nif;
	private String nombre;
	private String fechaNacimiento;
	private String correo;
	private String telefono;
	private String movil;
	private String fechaBaja;
	private String noAparecerRedAbogacia;
	
	private String idProvincia;
	private String idPoblacion;
	private String codigoPostal;
	private String tipoDireccion;
	private String apellidos;
	private ComboEtiquetasItem[] etiquetas;
	private String[] idGrupo;
	private String sexo;
	private String estadoCivil;
	private String idEstadoCivil;
	private String subCategoria;
	private String domicilio;
	private String situacion;
	private String idcv;
	private boolean historico;

	private String comisiones;
	private String soloNombre;
	private String apellidos1;
	private String apellidos2;
	private String idTipoIdentificacion;
	private String naturalDe;
	private String idLenguaje;
	private String asientoContable;
	private String idTratamiento;
	private String anotaciones;
	private String publicidad;
	private String guiaJudicial;
	private Date fechaNacimientoDate;
	private Date [] fechaNacimientoRango;
	private String motivo;

	private String subtipoCV[];
	private String tipoCV;
	private String subTipoCV1;
	private String subTipoCV2;
	
	private String [] colegio;

	/**
	 *
	 */
	public NoColegiadoItem idPersona(String idPersona) {
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
	public NoColegiadoItem idPersonas(String[] idPersonas) {
		this.idPersonas = idPersonas;
		return this;
	}

	@JsonProperty("idPersonas")
	public String[] getIdPersonas() {
		return idPersonas;
	}

	public void setIdPersonas(String[] idPersonas) {
		this.idPersonas = idPersonas;
	}

	/**
	 *
	 */
	public NoColegiadoItem idEstadoCivil(String idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
		return this;
	}

	@JsonProperty("idEstadoCivil")
	public String getidEstadoCivil() {
		return idEstadoCivil;
	}

	public void setidEstadoCivil(String idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}

	/**
	 *
	 */
	public NoColegiadoItem fechaNacimientoDate(Date fechaNacimientoDate) {
		this.fechaNacimientoDate = fechaNacimientoDate;
		return this;
	}

	@JsonProperty("fechaNacimientoDate")
	public Date getFechaNacimientoDate() {
		return fechaNacimientoDate;
	}

	public void setFechaNacimientoDate(Date fechaNacimientoDate) {
		this.fechaNacimientoDate = fechaNacimientoDate;
	}
	
	/**
	 *
	 */
	public NoColegiadoItem fechaNacimientoRango (Date [] fechaNacimientoRango){
		this.fechaNacimientoRango = fechaNacimientoRango;
		return this;
	}

	@JsonProperty("fechaNacimientoRango")
	public Date [] getFechaNacimientoRango() {
		return fechaNacimientoRango;
	}
	
	public void setFechaNacimientoRango(Date [] fechaNacimientoRango) {
		this.fechaNacimientoRango = fechaNacimientoRango;
	}

	/**
	 *
	 */
	public NoColegiadoItem idInstitucion(String idInstitucion) {
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

	public NoColegiadoItem comisiones(String comisiones) {
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

	public NoColegiadoItem nif(String nif) {
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
	public NoColegiadoItem nombre(String nombre) {
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
	public NoColegiadoItem fechaNacimiento(String fechaNacimiento) {
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
	public NoColegiadoItem correo(String correo) {
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
	public NoColegiadoItem telefono(String telefono) {
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
	public NoColegiadoItem tipoCV(String tipoCV) {
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
	public NoColegiadoItem subtipoCV(String[] subtipoCV) {

		this.subtipoCV = subtipoCV;
		return this;
	}

	@JsonProperty("subtipoCV")
	public String[] getSubtipoCV() {
		return subtipoCV;
	}

	public void setSubtipoCV(String[] subtipoCV) {
		this.subtipoCV = subtipoCV;
	}

	/**
	 *
	 */
	public NoColegiadoItem movil(String movil) {
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
	public NoColegiadoItem fechaBaja(String fechaBaja) {
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
	public NoColegiadoItem idProvincia(String idProvincia) {
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
	public NoColegiadoItem idPoblacion(String idPoblacion) {
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
	public NoColegiadoItem codigoPostal(String codigoPostal) {
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
	public NoColegiadoItem tipoDireccion(String tipoDireccion) {
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
	public NoColegiadoItem apellidos(String apellidos) {
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
	public NoColegiadoItem etiquetas(ComboEtiquetasItem[] etiquetas) {
		this.etiquetas = etiquetas;
		return this;
	}

	@JsonProperty("etiquetas")
	public ComboEtiquetasItem[] getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(ComboEtiquetasItem[] etiquetas) {
		this.etiquetas = etiquetas;
	}

	/**
	 *
	 */
	public NoColegiadoItem idGrupo(String[] idGrupo) {
		this.idGrupo = idGrupo;
		return this;
	}

	@JsonProperty("idgrupo")
	public String[] getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String[] idGrupo) {
		this.idGrupo = idGrupo;
	}

	/**
	 *
	 */
	public NoColegiadoItem sexo(String sexo) {
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
	public NoColegiadoItem estadoCivil(String estadoCivil) {
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
	public NoColegiadoItem subCategoria(String subCategoria) {
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
	public NoColegiadoItem domicilio(String domicilio) {
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
	public NoColegiadoItem situacion(String situacion) {
		this.domicilio = situacion;
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
	public NoColegiadoItem idcv(String idcv) {
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
	public NoColegiadoItem historico(boolean historico) {
		this.historico = historico;
		return this;
	}

	public String getGuiaJudicial() {
		return guiaJudicial;
	}

	public void setGuiaJudicial(String guiaJudicial) {
		this.guiaJudicial = guiaJudicial;
	}

	public String getPublicidad() {
		return publicidad;
	}

	public void setPublicidad(String publicidad) {
		this.publicidad = publicidad;
	}

	@JsonProperty("historico")
	public boolean isHistorico() {
		return historico;
	}

	public void setHistorico(boolean historico) {
		this.historico = historico;
	}

	/**
	 *
	 */
	public NoColegiadoItem soloNombre(String soloNombre) {
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
	public NoColegiadoItem apellidos1(String apellidos1) {
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
	public NoColegiadoItem apellidos2(String apellidos2) {
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
	public NoColegiadoItem idTipoIdentificacion(String idTipoIdentificacion) {
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
	public NoColegiadoItem naturalDe(String naturalDe) {
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
	public NoColegiadoItem idLenguaje(String idLenguaje) {
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
	public NoColegiadoItem asientoContable(String asientoContable) {
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
	public NoColegiadoItem idTratamiento(String idTratamiento) {
		this.idTratamiento = idTratamiento;
		return this;
	}

	@JsonProperty("idTratamiento")
	public String getidTratamiento() {
		return idTratamiento;
	}

	public void setidTratamiento(String idTratamiento) {
		this.idTratamiento = idTratamiento;
	}
	
	/**
	 *
	 */
	public NoColegiadoItem subTipoCV1(String subTipoCV1){
		this.subTipoCV1 = subTipoCV1;
		return this;
	}

	@JsonProperty("subTipoCV1")
	public String getSubTipoCV1() {
		return subTipoCV1;
	}
	
	public void setSubTipoCV1(String subTipoCV1) {
		this.subTipoCV1 = subTipoCV1;
	}	
	
	/**
	 *
	 */
	public NoColegiadoItem subTipoCV2(String subTipoCV2){
		this.subTipoCV2 = subTipoCV2;
		return this;
	}

	@JsonProperty("subTipoCV2")
	public String getSubTipoCV2() {
		return subTipoCV2;
	}
	
	public void setSubTipoCV2(String subTipoCV2) {
		this.subTipoCV2 = subTipoCV2;
	}	

	public NoColegiadoItem anotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
		return this;
	}

	@JsonProperty("anotaciones")
	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anotaciones == null) ? 0 : anotaciones.hashCode());
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((apellidos1 == null) ? 0 : apellidos1.hashCode());
		result = prime * result + ((apellidos2 == null) ? 0 : apellidos2.hashCode());
		result = prime * result + ((asientoContable == null) ? 0 : asientoContable.hashCode());
		result = prime * result + ((codigoPostal == null) ? 0 : codigoPostal.hashCode());
		result = prime * result + ((comisiones == null) ? 0 : comisiones.hashCode());
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
		result = prime * result + ((domicilio == null) ? 0 : domicilio.hashCode());
		result = prime * result + ((estadoCivil == null) ? 0 : estadoCivil.hashCode());
		result = prime * result + Arrays.hashCode(etiquetas);
		result = prime * result + ((fechaBaja == null) ? 0 : fechaBaja.hashCode());
		result = prime * result + ((fechaNacimiento == null) ? 0 : fechaNacimiento.hashCode());
		result = prime * result + ((fechaNacimientoDate == null) ? 0 : fechaNacimientoDate.hashCode());
		result = prime * result + Arrays.hashCode(fechaNacimientoRango);
		result = prime * result + ((guiaJudicial == null) ? 0 : guiaJudicial.hashCode());
		result = prime * result + (historico ? 1231 : 1237);
		result = prime * result + ((idEstadoCivil == null) ? 0 : idEstadoCivil.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idLenguaje == null) ? 0 : idLenguaje.hashCode());
		result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
		result = prime * result + Arrays.hashCode(idPersonas);
		result = prime * result + ((idPoblacion == null) ? 0 : idPoblacion.hashCode());
		result = prime * result + ((idProvincia == null) ? 0 : idProvincia.hashCode());
		result = prime * result + ((idTipoIdentificacion == null) ? 0 : idTipoIdentificacion.hashCode());
		result = prime * result + ((idTratamiento == null) ? 0 : idTratamiento.hashCode());
		result = prime * result + ((idcv == null) ? 0 : idcv.hashCode());
		result = prime * result + ((motivo == null) ? 0 : motivo.hashCode());
		result = prime * result + ((movil == null) ? 0 : movil.hashCode());
		result = prime * result + ((naturalDe == null) ? 0 : naturalDe.hashCode());
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((publicidad == null) ? 0 : publicidad.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result + ((situacion == null) ? 0 : situacion.hashCode());
		result = prime * result + ((soloNombre == null) ? 0 : soloNombre.hashCode());
		result = prime * result + ((subCategoria == null) ? 0 : subCategoria.hashCode());
		result = prime * result + Arrays.hashCode(subtipoCV);
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		result = prime * result + ((tipoCV == null) ? 0 : tipoCV.hashCode());
		result = prime * result + ((subTipoCV1 == null) ? 0 : subTipoCV1.hashCode());
		result = prime * result + ((subTipoCV2 == null) ? 0 : subTipoCV2.hashCode());
		result = prime * result + ((tipoDireccion == null) ? 0 : tipoDireccion.hashCode());
		result = prime * result + Arrays.hashCode(idGrupo);
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
		NoColegiadoItem other = (NoColegiadoItem) obj;
		if (anotaciones == null) {
			if (other.anotaciones != null)
				return false;
		} else if (!anotaciones.equals(other.anotaciones))
			return false;
		if (apellidos == null) {
			if (other.apellidos != null)
				return false;
		} else if (!apellidos.equals(other.apellidos))
			return false;
		if (apellidos1 == null) {
			if (other.apellidos1 != null)
				return false;
		} else if (!apellidos1.equals(other.apellidos1))
			return false;
		if (apellidos2 == null) {
			if (other.apellidos2 != null)
				return false;
		} else if (!apellidos2.equals(other.apellidos2))
			return false;
		if (asientoContable == null) {
			if (other.asientoContable != null)
				return false;
		} else if (!asientoContable.equals(other.asientoContable))
			return false;
		if (codigoPostal == null) {
			if (other.codigoPostal != null)
				return false;
		} else if (!codigoPostal.equals(other.codigoPostal))
			return false;
		if (comisiones == null) {
			if (other.comisiones != null)
				return false;
		} else if (!comisiones.equals(other.comisiones))
			return false;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		if (domicilio == null) {
			if (other.domicilio != null)
				return false;
		} else if (!domicilio.equals(other.domicilio))
			return false;
		if (estadoCivil == null) {
			if (other.estadoCivil != null)
				return false;
		} else if (!estadoCivil.equals(other.estadoCivil))
			return false;
		if (!Arrays.equals(etiquetas, other.etiquetas))
			return false;
		if (fechaBaja == null) {
			if (other.fechaBaja != null)
				return false;
		} else if (!fechaBaja.equals(other.fechaBaja))
			return false;
		if (fechaNacimiento == null) {
			if (other.fechaNacimiento != null)
				return false;
		} else if (!fechaNacimiento.equals(other.fechaNacimiento))
			return false;
		if (fechaNacimientoDate == null) {
			if (other.fechaNacimientoDate != null)
				return false;
		} else if (!fechaNacimientoDate.equals(other.fechaNacimientoDate))
			return false;
		if (!Arrays.equals(fechaNacimientoRango, other.fechaNacimientoRango))
			return false;
		if (guiaJudicial == null) {
			if (other.guiaJudicial != null)
				return false;
		} else if (!guiaJudicial.equals(other.guiaJudicial))
			return false;
		if (historico != other.historico)
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
		if (idPersona == null) {
			if (other.idPersona != null)
				return false;
		} else if (!idPersona.equals(other.idPersona))
			return false;
		if (!Arrays.equals(idPersonas, other.idPersonas))
			return false;
		if (idPoblacion == null) {
			if (other.idPoblacion != null)
				return false;
		} else if (!idPoblacion.equals(other.idPoblacion))
			return false;
		if (idProvincia == null) {
			if (other.idProvincia != null)
				return false;
		} else if (!idProvincia.equals(other.idProvincia))
			return false;
		if (idTipoIdentificacion == null) {
			if (other.idTipoIdentificacion != null)
				return false;
		} else if (!idTipoIdentificacion.equals(other.idTipoIdentificacion))
			return false;
		if (idTratamiento == null) {
			if (other.idTratamiento != null)
				return false;
		} else if (!idTratamiento.equals(other.idTratamiento))
			return false;
		if (idcv == null) {
			if (other.idcv != null)
				return false;
		} else if (!idcv.equals(other.idcv))
			return false;
		if (motivo == null) {
			if (other.motivo != null)
				return false;
		} else if (!motivo.equals(other.motivo))
			return false;
		if (movil == null) {
			if (other.movil != null)
				return false;
		} else if (!movil.equals(other.movil))
			return false;
		if (naturalDe == null) {
			if (other.naturalDe != null)
				return false;
		} else if (!naturalDe.equals(other.naturalDe))
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
		if (publicidad == null) {
			if (other.publicidad != null)
				return false;
		} else if (!publicidad.equals(other.publicidad))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		if (situacion == null) {
			if (other.situacion != null)
				return false;
		} else if (!situacion.equals(other.situacion))
			return false;
		if (soloNombre == null) {
			if (other.soloNombre != null)
				return false;
		} else if (!soloNombre.equals(other.soloNombre))
			return false;
		if (subCategoria == null) {
			if (other.subCategoria != null)
				return false;
		} else if (!subCategoria.equals(other.subCategoria))
			return false;
		if (!Arrays.equals(subtipoCV, other.subtipoCV))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		if (tipoCV == null) {
			if (other.tipoCV != null)
				return false;
		} else if (!tipoCV.equals(other.tipoCV))
			return false;
		if (subTipoCV1 == null) {
			if (other.subTipoCV1 != null)
				return false;
		} else if (!subTipoCV1.equals(other.subTipoCV1))
			return false;
		if (subTipoCV2 == null) {
			if (other.subTipoCV2 != null)
				return false;
		} else if (!subTipoCV2.equals(other.subTipoCV2))
			return false;
		if (tipoDireccion == null) {
			if (other.tipoDireccion != null)
				return false;
		} else if (!tipoDireccion.equals(other.tipoDireccion))
			return false;
		if (!Arrays.equals(idGrupo, other.idGrupo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NoColegiadoItem [idPersona=" + idPersona + ", idPersonas=" + Arrays.toString(idPersonas)
				+ ", idInstitucion=" + idInstitucion + ", nif=" + nif + ", nombre=" + nombre + ", fechaNacimiento="
				+ fechaNacimiento + ", correo=" + correo + ", telefono=" + telefono + ", movil=" + movil
				+ ", fechaBaja=" + fechaBaja + ", idProvincia=" + idProvincia + ", idPoblacion=" + idPoblacion
				+ ", codigoPostal=" + codigoPostal + ", tipoDireccion=" + tipoDireccion + ", apellidos=" + apellidos
				+ ", etiquetas=" + Arrays.toString(etiquetas) + ", idGrupo=" + Arrays.toString(idGrupo)
				+ ", sexo=" + sexo + ", estadoCivil=" + estadoCivil + ", idEstadoCivil=" + idEstadoCivil
				+ ", subCategoria=" + subCategoria + ", domicilio=" + domicilio + ", situacion=" + situacion + ", idcv="
				+ idcv + ", historico=" + historico + ", comisiones=" + comisiones + ", soloNombre=" + soloNombre
				+ ", apellidos1=" + apellidos1 + ", apellidos2=" + apellidos2 + ", idTipoIdentificacion="
				+ idTipoIdentificacion + ", naturalDe=" + naturalDe + ", idLenguaje=" + idLenguaje
				+ ", asientoContable=" + asientoContable + ", idTratamiento=" + idTratamiento + ", anotaciones="
				+ anotaciones + ", publicidad=" + publicidad + ", guiaJudicial=" + guiaJudicial
				+ ", fechaNacimientoDate=" + fechaNacimientoDate + ", fechaNacimientoRango="
				+ Arrays.toString(fechaNacimientoRango) + ", motivo=" + motivo + ", subtipoCV="
				+ Arrays.toString(subtipoCV) + ", tipoCV=" + tipoCV + ", subTipoCV1=" + subTipoCV1 + ", subTipoCV2=" + subTipoCV2 + "]";
	}

	public String getNoAparecerRedAbogacia() {
		return noAparecerRedAbogacia;
	}

	public void setNoAparecerRedAbogacia(String noAparecerRedAbogacia) {
		this.noAparecerRedAbogacia = noAparecerRedAbogacia;
	}

	public String[] getColegio() {
		return colegio;
	}

	public void setColegio(String[] colegio) {
		this.colegio = colegio;
	}
}
