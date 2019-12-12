package org.itcgae.siga.DTO.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EjgItem {
	private String idEJG;
    private String colegio;
    private String annio;
    private String numero;
    private String tipoEJG;
    private String tipoEJGColegio;
    private String creadoDesde;
    private String fechaAperturaDesd;
    private String fechaAperturaHast;
    private Date fechaApertura;
	private Date fechaModificacion;
	private String idPersona;
    private String estadoEJG;
    private String fechaEstadoDesd;
    private String fechaEstadoHast;
    private String fechaLimiteDesd;
    private String fechaLimiteHast;

    private String[] dictamen;
    private String dictamenSing;
    private String fundamentoCalif;
    private String fechaDictamenDesd;
    private String fechaDictamenHast;
    private String resolucion;
    private String fundamentoJuridico;
    private String fechaResolucionDesd;
    private String fechaResolucionHast;
    private String impugnacion;
    private String fundamentoImpuganacion;
    private String fechaImpugnacionDesd;
    private String fechaImpugnacionHast;

    private String juzgado;
    private String asunto;
    private String calidad;
    private String perceptivo;
    private String renuncia;
    private String numAnnioProcedimiento;
    private String procedimiento;
    private String nig;

    private String annioCAJG;
    private String numCAJG;
    private String annioActa;
    private String numActa;
    private String ponente;
    private String fechaPonenteDesd;
    private String fechaPonenteHast;
    private String numRegRemesa;
    private String numRegRemesa1;
    private String numRegRemesa2;
    private String numRegRemesa3;

    private String nif;
    private String apellidos;
    private String nombre;
    private String rol;

    private String turno;
    private String guardia;
    private String numColegiado;
    private String apellidosYNombre; 
    private String tipoLetrado;
    
    private String turnoDes;
    private String nombreApeSolicitante;
//    private String correoelectronico;
//    private Date fechanacimiento;

//    private String idInstitucion;


	
	/**
	 **/
	public EjgItem idEJG(String idEJG) {
		this.idEJG = idEJG;
		return this;
	}

	@JsonProperty("idEJG")
	public String getIdEJG() {
		return idEJG;
	}

	public void setIdEJG(String idEJG) {
		this.idEJG = idEJG;
	}
	/**
	 **/
	public EjgItem colegio(String colegio) {
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
	 **/
	public EjgItem annio(String annio) {
		this.annio = annio;
		return this;
	}

	@JsonProperty("annio")
	public String getAnnio() {
		return annio;
	}

	public void setAnnio(String annio) {
		this.annio = annio;
	}
	/**
	 **/
	public EjgItem numero(String numero) {
		this.numero = numero;
		return this;
	}

	@JsonProperty("numero")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	/**
	 **/
	public EjgItem tipoEJG(String tipoEJG) {
		this.tipoEJG = tipoEJG;
		return this;
	}

	@JsonProperty("tipoEJG")
	public String getTipoEJG() {
		return tipoEJG;
	}

	public void setTipoEJG(String tipoEJG) {
		this.tipoEJG = tipoEJG;
	}
	/**
	 **/
	public EjgItem tipoEJGColegio(String tipoEJGColegio) {
		this.tipoEJGColegio = tipoEJGColegio;
		return this;
	}

	@JsonProperty("tipoEJGColegio")
	public String getTipoEJGColegio() {
		return tipoEJGColegio;
	}

	public void setTipoEJGColegio(String tipoEJGColegio) {
		this.tipoEJGColegio = tipoEJGColegio;
	}
	/**
	 **/
	public EjgItem creadoDesde(String creadoDesde) {
		this.creadoDesde = creadoDesde;
		return this;
	}

	@JsonProperty("creadoDesde")
	public String getCreadoDesde() {
		return creadoDesde;
	}

	public void setCreadoDesde(String creadoDesde) {
		this.creadoDesde = creadoDesde;
	}
	/**
	 **/
	public EjgItem fechaAperturaDesd(String fechaAperturaDesd) {
		this.fechaAperturaDesd = fechaAperturaDesd;
		return this;
	}

	@JsonProperty("fechaAperturaDesd")
	public String getFechaAperturaDesd() {
		return fechaAperturaDesd;
	}

	public void setFechaAperturaDesd(String fechaAperturaDesd) {
		this.fechaAperturaDesd = fechaAperturaDesd;
	}
	/**
	 **/
	public EjgItem fechaAperturaHast(String fechaAperturaHast) {
		this.fechaAperturaHast = fechaAperturaHast;
		return this;
	}

	@JsonProperty("fechaAperturaHast")
	public String getFechaAperturaHast() {
		return fechaAperturaHast;
	}

	public void setFechaAperturaHast(String fechaAperturaHast) {
		this.fechaAperturaHast = fechaAperturaHast;
	}
	/**
	 **/
	public EjgItem fechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
		return this;
	}

	@JsonProperty("fechaApertura")
	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}
	/**
	 **/
	public EjgItem fechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
		return this;
	}

	@JsonProperty("fechaModificacion")
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setfechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	/**
	 **/
	public EjgItem estadoEJG(String estadoEJG) {
		this.estadoEJG = estadoEJG;
		return this;
	}

	@JsonProperty("estadoEJG")
	public String getEstadoEJG() {
		return estadoEJG;
	}

	public void setEstadoEJG(String estadoEJG) {
		this.estadoEJG = estadoEJG;
	}
	/**
	 **/
	public EjgItem fechaEstadoDesd(String fechaEstadoDesd) {
		this.fechaEstadoDesd = fechaEstadoDesd;
		return this;
	}

	@JsonProperty("fechaEstadoDesd")
	public String getFechaEstadoDesd() {
		return fechaEstadoDesd;
	}

	public void setFechaEstadoDesd(String fechaEstadoDesd) {
		this.fechaEstadoDesd = fechaEstadoDesd;
	}
	/**
	 **/
	public EjgItem fechaEstadoHast(String fechaEstadoHast) {
		this.fechaEstadoHast = fechaEstadoHast;
		return this;
	}

	@JsonProperty("fechaEstadoHast")
	public String getFechaEstadoHast() {
		return fechaEstadoHast;
	}

	public void setFechaEstadoHast(String fechaEstadoHast) {
		this.fechaEstadoHast = fechaEstadoHast;
	}
	/**
	 **/
	public EjgItem fechaLimiteDesd(String fechaLimiteDesd) {
		this.fechaLimiteDesd = fechaLimiteDesd;
		return this;
	}

	@JsonProperty("fechaLimiteDesd")
	public String getFechaLimiteDesd() {
		return fechaLimiteDesd;
	}

	public void setFechaLimiteDesd(String fechaLimiteDesd) {
		this.fechaLimiteDesd = fechaLimiteDesd;
	}
	/**
	 **/
	public EjgItem fechaLimiteHast(String fechaLimiteHast) {
		this.fechaLimiteHast = fechaLimiteHast;
		return this;
	}

	@JsonProperty("fechaLimiteHast")
	public String getFechaLimiteHast() {
		return fechaLimiteHast;
	}

	public void setFechaLimiteHast(String fechaLimiteHast) {
		this.fechaLimiteHast = fechaLimiteHast;
	}
	/**
	 **/
	public EjgItem dictamen(String[] dictamen) {
		this.dictamen = dictamen;
		return this;
	}

	@JsonProperty("dictamen")
	public String[] getDictamen() {
		return dictamen;
	}

	public void setDictamen(String[] dictamen) {
		this.dictamen = dictamen;
	}
	/**
	 **/
	public EjgItem fundamentoCalif(String fundamentoCalif) {
		this.fundamentoCalif = fundamentoCalif;
		return this;
	}

	@JsonProperty("fundamentoCalif")
	public String getFundamentoCalif() {
		return fundamentoCalif;
	}

	public void setFundamentoCalif(String fundamentoCalif) {
		this.fundamentoCalif = fundamentoCalif;
	}
	/**
	 **/
	public EjgItem fechaDictamenDesd(String fechaDictamenDesd) {
		this.fechaDictamenDesd = fechaDictamenDesd;
		return this;
	}

	@JsonProperty("fechaDictamenDesd")
	public String getFechaDictamenDesd() {
		return fechaDictamenDesd;
	}

	public void setFechaDictamenDesd(String fechaDictamenDesd) {
		this.fechaDictamenDesd = fechaDictamenDesd;
	}
	/**
	 **/
	public EjgItem fechaDictamenHast(String fechaDictamenHast) {
		this.fechaDictamenHast = fechaDictamenHast;
		return this;
	}

	@JsonProperty("fechaDictamenHast")
	public String getFechaDictamenHast() {
		return fechaDictamenHast;
	}

	public void setFechaDictamenHast(String fechaDictamenHast) {
		this.fechaDictamenHast = fechaDictamenHast;
	}
	/**
	 **/
	public EjgItem resolucion(String resolucion) {
		this.resolucion = resolucion;
		return this;
	}

	@JsonProperty("resolucion")
	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}
	/**
	 **/
	public EjgItem fundamentoJuridico(String fundamentoJuridico) {
		this.fundamentoJuridico = fundamentoJuridico;
		return this;
	}

	@JsonProperty("fundamentoJuridico")
	public String getFundamentoJuridico() {
		return fundamentoJuridico;
	}

	public void setFundamentoJuridico(String fundamentoJuridico) {
		this.fundamentoJuridico = fundamentoJuridico;
	}
	/**
	 **/
	public EjgItem fechaResolucionDesd(String fechaResolucionDesd) {
		this.fechaResolucionDesd = fechaResolucionDesd;
		return this;
	}

	@JsonProperty("fechaResolucionDesd")
	public String getFechaResolucionDesd() {
		return fechaResolucionDesd;
	}

	public void setFechaResolucionDesd(String fechaResolucionDesd) {
		this.fechaResolucionDesd = fechaResolucionDesd;
	}
	/**
	 **/
	public EjgItem fechaResolucionHast(String fechaResolucionHast) {
		this.fechaResolucionHast = fechaResolucionHast;
		return this;
	}

	@JsonProperty("fechaResolucionHast")
	public String getFechaResolucionHast() {
		return fechaResolucionHast;
	}

	public void setFechaResolucionHast(String fechaResolucionHast) {
		this.fechaResolucionHast = fechaResolucionHast;
	}
	/**
	 **/
	public EjgItem impugnacion(String impugnacion) {
		this.impugnacion = impugnacion;
		return this;
	}

	@JsonProperty("impugnacion")
	public String getImpugnacion() {
		return impugnacion;
	}

	public void setImpugnacion(String impugnacion) {
		this.impugnacion = impugnacion;
	}
	/**
	 **/
	public EjgItem fundamentoImpuganacion(String fundamentoImpuganacion) {
		this.fundamentoImpuganacion = fundamentoImpuganacion;
		return this;
	}

	@JsonProperty("fundamentoImpuganacion")
	public String getFundamentoImpuganacion() {
		return fundamentoImpuganacion;
	}

	public void setFundamentoImpuganacion(String fundamentoImpuganacion) {
		this.fundamentoImpuganacion = fundamentoImpuganacion;
	}
	/**
	 **/
	public EjgItem fechaImpugnacionDesd(String fechaImpugnacionDesd) {
		this.fechaImpugnacionDesd = fechaImpugnacionDesd;
		return this;
	}

	@JsonProperty("fechaImpugnacionDesd")
	public String getFechaImpugnacionDesd() {
		return fechaImpugnacionDesd;
	}

	public void setFechaImpugnacionDesd(String fechaImpugnacionDesd) {
		this.fechaImpugnacionDesd = fechaImpugnacionDesd;
	}
	/**
	 **/
	public EjgItem fechaImpugnacionHast(String fechaImpugnacionHast) {
		this.fechaImpugnacionHast = fechaImpugnacionHast;
		return this;
	}

	@JsonProperty("fechaImpugnacionHast")
	public String getFechaImpugnacionHast() {
		return fechaImpugnacionHast;
	}

	public void setFechaImpugnacionHast(String fechaImpugnacionHast) {
		this.fechaImpugnacionHast = fechaImpugnacionHast;
	}
	/**
	 **/
	public EjgItem juzgado(String juzgado) {
		this.juzgado = juzgado;
		return this;
	}

	@JsonProperty("juzgado")
	public String getJuzgado() {
		return juzgado;
	}

	public void setJuzgado(String juzgado) {
		this.juzgado = juzgado;
	}
	/**
	 **/
	public EjgItem asunto(String asunto) {
		this.asunto = asunto;
		return this;
	}

	@JsonProperty("asunto")
	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	/**
	 **/
	public EjgItem calidad(String calidad) {
		this.calidad = calidad;
		return this;
	}

	@JsonProperty("calidad")
	public String getCalidad() {
		return calidad;
	}

	public void setCalidad(String calidad) {
		this.calidad = calidad;
	}
	/**
	 **/
	public EjgItem perceptivo(String perceptivo) {
		this.perceptivo = perceptivo;
		return this;
	}

	@JsonProperty("perceptivo")
	public String getPerceptivo() {
		return perceptivo;
	}

	public void setPerceptivo(String perceptivo) {
		this.perceptivo = perceptivo;
	}
	/**
	 **/
	public EjgItem renuncia(String renuncia) {
		this.renuncia = renuncia;
		return this;
	}

	@JsonProperty("renuncia")
	public String getRenuncia() {
		return renuncia;
	}

	public void setRenuncia(String renuncia) {
		this.renuncia = renuncia;
	}
	/**
	 **/
	public EjgItem numAnnioProcedimiento(String numAnnioProcedimiento) {
		this.numAnnioProcedimiento = numAnnioProcedimiento;
		return this;
	}

	@JsonProperty("numAnnioProcedimiento")
	public String getNumAnnioProcedimiento() {
		return numAnnioProcedimiento;
	}

	public void setNumAnnioProcedimiento(String numAnnioProcedimiento) {
		this.numAnnioProcedimiento = numAnnioProcedimiento;
	}
	/**
	 **/
	public EjgItem procedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
		return this;
	}

	@JsonProperty("procedimiento")
	public String getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}
	/**
	 **/
	public EjgItem nig(String nig) {
		this.nig = nig;
		return this;
	}

	@JsonProperty("nig")
	public String getNig() {
		return nig;
	}

	public void setNig(String nig) {
		this.nig = nig;
	}
	/**
	 **/
	public EjgItem annioCAJG(String annioCAJG) {
		this.annioCAJG = annioCAJG;
		return this;
	}

	@JsonProperty("annioCAJG")
	public String getAnnioCAJG() {
		return annioCAJG;
	}

	public void setAnnioCAJG(String annioCAJG) {
		this.annioCAJG = annioCAJG;
	}
	/**
	 **/
	public EjgItem numCAJG(String numCAJG) {
		this.numCAJG = numCAJG;
		return this;
	}

	@JsonProperty("numCAJG")
	public String getNumCAJG() {
		return numCAJG;
	}

	public void setNumCAJG(String numCAJG) {
		this.numCAJG = numCAJG;
	}
	/**
	 **/
	public EjgItem annioActa(String annioActa) {
		this.annioActa = annioActa;
		return this;
	}

	@JsonProperty("annioActa")
	public String getAnnioActa() {
		return annioActa;
	}

	public void setAnnioActa(String annioActa) {
		this.annioActa = annioActa;
	}
	/**
	 **/
	public EjgItem numActa(String numActa) {
		this.numActa = numActa;
		return this;
	}

	@JsonProperty("numActa")
	public String getNumActa() {
		return numActa;
	}

	public void setNumActa(String numActa) {
		this.numActa = numActa;
	}
	/**
	 **/
	public EjgItem ponente(String ponente) {
		this.ponente = ponente;
		return this;
	}

	@JsonProperty("ponente")
	public String getPonente() {
		return ponente;
	}

	public void setPonente(String ponente) {
		this.ponente = ponente;
	}
	/**
	 **/
	public EjgItem fechaPonenteDesd(String fechaPonenteDesd) {
		this.fechaPonenteDesd = fechaPonenteDesd;
		return this;
	}

	@JsonProperty("fechaPonenteDesd")
	public String getFechaPonenteDesd() {
		return fechaPonenteDesd;
	}

	public void setFechaPonenteDesd(String fechaPonenteDesd) {
		this.fechaPonenteDesd = fechaPonenteDesd;
	}
	/**
	 **/
	public EjgItem fechaPonenteHast(String fechaPonenteHast) {
		this.fechaPonenteHast = fechaPonenteHast;
		return this;
	}

	@JsonProperty("fechaPonenteHast")
	public String getFechaPonenteHast() {
		return fechaPonenteHast;
	}

	public void setFechaPonenteHast(String fechaPonenteHast) {
		this.fechaPonenteHast = fechaPonenteHast;
	}
	/**
	 **/
	public EjgItem numRegRemesa(String numRegRemesa) {
		this.numRegRemesa = numRegRemesa;
		return this;
	}

	@JsonProperty("numRegRemesa")
	public String getNumRegRemesa() {
		return numRegRemesa;
	}

	public void setNumRegRemesa(String numRegRemesa) {
		this.numRegRemesa = numRegRemesa;
	}
	/**
	 **/
	public EjgItem numRegRemesa1(String numRegRemesa1) {
		this.numRegRemesa1 = numRegRemesa1;
		return this;
	}

	@JsonProperty("numRegRemesa1")	
	public String getNumRegRemesa1() {
		return numRegRemesa1;
	}

	public void setNumRegRemesa1(String numRegRemesa1) {
		this.numRegRemesa1 = numRegRemesa1;
	}
	/**
	 **/
	public EjgItem numRegRemesa2(String numRegRemesa2) {
		this.numRegRemesa2 = numRegRemesa2;
		return this;
	}

	@JsonProperty("numRegRemesa2")
	public String getNumRegRemesa2() {
		return numRegRemesa2;
	}

	public void setNumRegRemesa2(String numRegRemesa2) {
		this.numRegRemesa2 = numRegRemesa2;
	}
	/**
	 **/
	public EjgItem numRegRemesa3(String numRegRemesa3) {
		this.numRegRemesa3 = numRegRemesa3;
		return this;
	}

	@JsonProperty("numRegRemesa3")
	public String getNumRegRemesa3() {
		return numRegRemesa3;
	}

	public void setNumRegRemesa3(String numRegRemesa3) {
		this.numRegRemesa3 = numRegRemesa3;
	}
	/**
	 **/
	public EjgItem nif(String nif) {
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
	public EjgItem apellidos(String apellidos) {
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
	public EjgItem nombre(String nombre) {
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
	public EjgItem rol(String rol) {
		this.rol = rol;
		return this;
	}

	@JsonProperty("rol")
	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	/**
	 **/
	public EjgItem turno(String turno) {
		this.turno = turno;
		return this;
	}

	@JsonProperty("turno")
	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}
	/**
	 **/
	public EjgItem guardia(String guardia) {
		this.guardia = guardia;
		return this;
	}

	@JsonProperty("guardia")
	public String getGuardia() {
		return guardia;
	}

	public void setGuardia(String guardia) {
		this.guardia = guardia;
	}
	/**
	 **/
	public EjgItem numColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
		return this;
	}

	@JsonProperty("numColegiado")
	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	/**
	 **/
	public EjgItem apellidosYNombre(String apellidosYNombre) {
		this.apellidosYNombre = apellidosYNombre;
		return this;
	}

	@JsonProperty("apellidosYNombre")
	public String getApellidosYNombre() {
		return apellidosYNombre;
	}

	public void setApellidosYNombre(String apellidosYNombre) {
		this.apellidosYNombre = apellidosYNombre;
	}
	/**
	 **/
	public EjgItem tipoLetrado(String tipoLetrado) {
		this.tipoLetrado = tipoLetrado;
		return this;
	}

	@JsonProperty("tipoLetrado")
	public String getTipoLetrado() {
		return tipoLetrado;
	}

	public void setTipoLetrado(String tipoLetrado) {
		this.tipoLetrado = tipoLetrado;
	}
	/**
	 **/
	public EjgItem idPersona(String idPersona) {
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
	 **/
	public EjgItem turnoDes(String turnoDes) {
		this.turnoDes = turnoDes;
		return this;
	}

	@JsonProperty("turnoDes")
	public String getTurnoDes() {
		return turnoDes;
	}

	public void setTurnoDes(String turnoDes) {
		this.turnoDes = turnoDes;
	}
	/**
	 **/
	public EjgItem nombreApeSolicitante(String nombreApeSolicitante) {
		this.nombreApeSolicitante = nombreApeSolicitante;
		return this;
	}

	@JsonProperty("nombreApeSolicitante")
	public String getNombreApeSolicitante() {
		return nombreApeSolicitante;
	}

	public void setNombreApeSolicitante(String nombreApeSolicitante) {
		this.nombreApeSolicitante = nombreApeSolicitante;
	}
	/**
	 **/
	public EjgItem dictamenSing(String dictamenSing) {
		this.dictamenSing = dictamenSing;
		return this;
	}

	@JsonProperty("dictamenSing")
	public String getDictamenSing() {
		return dictamenSing;
	}

	public void setDictamenSing(String dictamenSing) {
		this.dictamenSing = dictamenSing;
	}

//	/**
//	 **/
//	public EjgItem correoelectronico(String correoelectronico) {
//		this.correoelectronico = correoelectronico;
//		return this;
//	}
//
//	@JsonProperty("correoelectronico")
//	public String getCorreoelectronico() {
//		return correoelectronico;
//	}
//
//	public void setCorreoelectronico(String correoelectronico) {
//		this.correoelectronico = correoelectronico;
//	}
//	/**
//	 **/
//	public EjgItem fechanacimiento(Date fechanacimiento) {
//		this.fechanacimiento = fechanacimiento;
//		return this;
//	}
//
//	@JsonProperty("fechanacimiento")
//	public Date getFechanacimiento() {
//		return fechanacimiento;
//	}
//
//	public void setFechanacimiento(Date fechanacimiento) {
//		this.fechanacimiento = fechanacimiento;
//	}


}
